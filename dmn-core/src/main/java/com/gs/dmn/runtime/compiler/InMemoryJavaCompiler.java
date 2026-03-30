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

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation: compiles in-memory from provided source strings.
 */
public class InMemoryJavaCompiler {
    /**
     * Compile the given sources (map of fully-qualified-class-name -> source).
     * Returns the binary code (map of fully-qualified-class-name -> binary code).
     */
    public Map<String, byte[]> compile(Map<String, String> sources) {
        if (sources == null || sources.isEmpty()) {
            throw new IllegalArgumentException("Missing sources");
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("No system Java compiler available. Make sure you're running on a JDK, not a JRE.");
        }

        // Prepare parameters for compilation
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(diagnostics, null, null);
        InMemoryFileManager fileManager = new InMemoryFileManager(stdFileManager);
        List<JavaFileObject> compilationUnits = new ArrayList<>();
        for (Map.Entry<String, String> e : sources.entrySet()) {
            compilationUnits.add(new SourceJavaFileObject(e.getKey(), e.getValue()));
        }

        // Use current classpath so tests can reference project classes and junit jars
        List<String> options = Arrays.asList("-classpath", System.getProperty("java.class.path"));

        // Compile
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits);
        Boolean ok = task.call();
        if (ok == null || !ok) {
            StringBuilder sb = new StringBuilder();
            for (Diagnostic<? extends JavaFileObject> d : diagnostics.getDiagnostics()) {
                sb.append(d.getKind()).append(": ").append(d.getMessage(null)).append(" at ")
                        .append(d.getSource() == null ? "<unknown>" : d.getSource().getName())
                        .append(":").append(d.getLineNumber()).append("\n");
            }
            throw new IllegalStateException("Compilation failed:\n" + sb);
        }
        return fileManager.getAllClassBytes();
    }

    // JavaFileObject to hold source code
    static class SourceJavaFileObject extends SimpleJavaFileObject {
        final String source;

        SourceJavaFileObject(String className, String source) {
            super(uriForClass(className), Kind.SOURCE);
            this.source = source;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return source;
        }

        static URI uriForClass(String className) {
            return URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension);
        }
    }


    // FileManager that stores compiled bytecode in memory
    static class InMemoryFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {
        private final Map<String, ByteArrayJavaFileObject> classFileObjects = new HashMap<>();

        protected InMemoryFileManager(StandardJavaFileManager stdFileManager) {
            super(stdFileManager);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
            ByteArrayJavaFileObject fileObject = new ByteArrayJavaFileObject(className, kind);
            classFileObjects.put(className, fileObject);
            return fileObject;
        }

        Map<String, byte[]> getAllClassBytes() {
            return classFileObjects.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getBytes()));
        }
    }

    // JavaFileObject to hold compiled bytecode
    static class ByteArrayJavaFileObject extends SimpleJavaFileObject {
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ByteArrayJavaFileObject(String className, Kind kind) {
            super(URI.create("bytes:///" + className.replace('.', '/') + kind.extension), kind);
        }

        @Override
        public OutputStream openOutputStream() {
            return baos;
        }

        byte[] getBytes() {
            return baos.toByteArray();
        }
    }
}

