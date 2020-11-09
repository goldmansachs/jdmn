/*
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.runtime.compiler;

import com.gs.dmn.feel.analysis.semantics.type.FunctionType;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.native_.NativeFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JavaxToolsCompiler extends JavaCompilerImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaxToolsCompiler.class);

    private final File classesDir;

    public JavaxToolsCompiler() {
        this.classesDir = makeTempFolder();
    }

    public JavaxToolsCompiler(File classesDir) {
        this.classesDir = classesDir;
    }

    @Override
    public ClassData makeClassData(FunctionDefinition element, FEELContext context, BasicDMNToNativeTransformer dmnTransformer, FEELTranslator feelTranslator, String libClassName) {
        FunctionType functionType = (FunctionType) element.getType();

        // Apply method parts
        String signature = "Object... args";
        boolean convertToContext = true;
        String body = feelTranslator.expressionToNative(element.getBody(), context);
        NativeFactory nativeFactory = dmnTransformer.getNativeFactory();
        String applyMethod = nativeFactory.applyMethod(functionType, signature, convertToContext, body);

        // Class parts
        String packageName = "com.gs.dmn.runtime";
        String className = String.format("LambdaExpression%sImpl", uniqueName());
        String returnType = dmnTransformer.toNativeType(dmnTransformer.convertType(functionType.getReturnType(), convertToContext));
        String javaClassText = classText(packageName, className, libClassName, returnType, applyMethod);

        return new JavaxToolsClassData(packageName, className, javaClassText);
    }

    private String uniqueName() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String classText(String packageName, String className, String lib, String returnType, String applyMethod) {
        return String.format(
                "package %s;\n" +
                "public class %s extends %s implements com.gs.dmn.runtime.LambdaExpression<%s> {\n" +
                "    %s" +
                "}",
                packageName, className, lib, returnType, applyMethod);
    }

    private String applyMethod(FunctionDefinition element, String returnType, String parametersAssignment, String body) {
        return String.format(
                "public %s apply() {" +
                        "%s" +
                        "return %s;" +
                        "}",
                returnType, parametersAssignment, body);
    }

    @Override
    public Class<?> compile(ClassData classData) throws Exception {
        String packageName = classData.getPackageName();
        String className = classData.getClassName();
        String classText = ((JavaxToolsClassData)classData).getClassText();
        String qualifiedClassName = StringUtils.isBlank(packageName) ? className : packageName + "." + className;

        // Initialize compiler
        javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticCollector, null, null);
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singleton(classesDir));

        // Create java source file
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        out.println(classText);
        out.close();
        JavaFileObject file = new JavaSourceFromString(qualifiedClassName, writer.toString());

        // Compile
        String[] compileOptions = new String[] {
                // set target folder
                "-d", classesDir.getAbsolutePath(),
                // set compiler's classpath to be same as the runtime's
                "-classpath", System.getProperty("java.class.path")
        };
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
        CompilationTask task = compiler.getTask(null, fileManager, diagnosticCollector, Arrays.asList(compileOptions), null, compilationUnits);
        task.call();

        // Check diagnostics
        List<Diagnostic<? extends JavaFileObject>> errors = diagnosticCollector.getDiagnostics().stream().filter(d -> d.getKind() == Diagnostic.Kind.ERROR).collect(Collectors.toList());
        if (!errors.isEmpty()) {
            throw new DMNRuntimeException(errors.toString());
        }

        // Load generated class
        ClassLoader classLoader = fileManager.getClassLoader(StandardLocation.CLASS_OUTPUT);
        return classLoader.loadClass(qualifiedClassName);
    }

    @Override
    public void deleteLambdaClass(Object lambdaExpression) {
        Class<?> cls = lambdaExpression.getClass();
        String qName = cls.getName();
        String path = qName.replace('.', '/');
        File clsFile = new File(this.classesDir, path + ".class");

        LOGGER.debug("Deleting .class file " + clsFile.getAbsolutePath());

        FileUtils.deleteQuietly(clsFile);
    }

    public static void main(String[] args) throws Exception {
        String text =
                "public class HelloWorld {" +
                        "    public static void main(String args[]) {" +
                        "       System.out.println(\"This is in another java file\");" +
                        "    }" +
                        "}";
        JavaxToolsClassData compilerArgs = new JavaxToolsClassData(null, "HelloWorld", text);
        Class<?> cls = new JavaxToolsCompiler(new File(".")).compile(compilerArgs);

        try {
            cls.getDeclaredMethod("main", new Class[]{String[].class})
                    .invoke(null, new Object[]{null});
        } catch (NoSuchMethodException e) {
            System.err.println("No such method: " + e);
        } catch (IllegalAccessException e) {
            System.err.println("Illegal access: " + e);
        } catch (InvocationTargetException e) {
            System.err.println("Invocation target: " + e);
        }
    }

    private File makeTempFolder() {
        try {
            Path tmpDir = Files.createTempDirectory("jdmn_");
            LOGGER.debug("Created temp folder as: " + tmpDir);
            File file = tmpDir.toFile();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> FileUtils.deleteQuietly(file)));
            return file;
        } catch (IOException e) {
            throw new DMNRuntimeException("Cannot create temporary folder for JIT code");
        }
    }
}

