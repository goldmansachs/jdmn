/**
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
import com.gs.dmn.feel.lib.DefaultFEELLib;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.runtime.LambdaExpression;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import javassist.*;

public class JavaAssistCompiler extends JavaCompilerImpl {
    @Override
    public ClassData makeClassData(FunctionDefinition element, FEELContext context, BasicDMN2JavaTransformer dmnTransformer, FEELTranslator feelTranslator, String libClassName) {
        FunctionType functionType = (FunctionType) element.getType();

        // Apply method parts
        String signature = "Object[] args";
        boolean convertToContext = true;
        String body = feelTranslator.expressionToJava(element.getBody(), context);
        String applyMethod = dmnTransformer.applyMethod(functionType, signature, convertToContext, body);

        String bridgeMethodText = bridgeMethodText();

        // Class parts
        String packageName = "com.gs.dmn.runtime";
        String className = "LambdaExpressionImpl" + System.currentTimeMillis();

        return new JavaAssistClassData(packageName, className, applyMethod, bridgeMethodText);
    }

    private String bridgeMethodText() {
        return "public Object apply(Object[] args) {\n" +
               "   return apply(args);\n" +
               "}\n";
    }

    @Override
    public Class<?> compile(ClassData classData) throws Exception {
        String packageName = classData.getPackageName();
        String className = classData.getClassName();
        String methodText = ((JavaAssistClassData)classData).getMethodText();
        String bridgeMethodText = ((JavaAssistClassData)classData).getBridgeMethodText();

        ClassPool classPool = new ClassPool(true);

        CtClass superClass = resolveClass(DefaultFEELLib.class);
        CtClass superInterface = resolveClass(LambdaExpression.class);

        CtClass lambdaImplClass = classPool.makeClass(packageName + "." + className);
        lambdaImplClass.setSuperclass(superClass);
        lambdaImplClass.addInterface(superInterface);

        CtMethod applyMethod = CtNewMethod.make(methodText, lambdaImplClass);
        applyMethod.setModifiers(applyMethod.getModifiers() | Modifier.VARARGS);
        lambdaImplClass.addMethod(applyMethod);

        CtMethod applyBridgeMethod = CtMethod.make(bridgeMethodText, lambdaImplClass);
        lambdaImplClass.addMethod(applyBridgeMethod);

        return lambdaImplClass.toClass();
    }

    private CtClass resolveClass(Class<?> cls) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        return pool.get(cls.getName());
    }
}
