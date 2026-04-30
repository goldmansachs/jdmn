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
package com.gs.dmn.feel.analysis.syntax.ast.library.json;

import com.gs.dmn.error.SemanticErrorException;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.library.Library;
import com.gs.dmn.feel.analysis.syntax.ast.library.LibraryRepository;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.JsonSerializer;
import com.gs.dmn.transformation.InputParameters;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonDescriptionGenerator {
    public void generate(String libPath, Path outputFolder) throws Exception {
        // Parse library
        LibraryRepository libraryRepository = new LibraryRepository(new InputParameters());
        Library<?> library = libraryRepository.parseLibrary(libPath);

        // Serialize library
        serialize(library, jsonFile(libPath, outputFolder));
    }

    private File jsonFile(String libPath, Path outputFolder) throws IOException {
        // Find file name
        Path path = Paths.get(libPath);
        String fileName = path.getFileName().toString();
        String jsonFileName = fileName.substring(0, fileName.lastIndexOf('.')) + ".json";
        Path outFile = outputFolder.resolve(jsonFileName);
        Files.createDirectories(outFile.getParent());
        return outFile.toFile();
    }

    private void serialize(Library<?> library, File jsonFile) throws Exception {
        try (Writer writer = new FileWriter(jsonFile)) {
            JsonSerializer.OBJECT_MAPPER.writeValue(writer, toJson(library));
        }
    }

    FEELLibrary toJson(Library<?> library) throws Exception {
        // Transform the library into a serializable format
        String name =  library.getName();
        List<FunctionDeclaration> functions = new ArrayList<>();
        library.getFunctions().forEach(function -> {
           FunctionDeclaration jsonFunction = toJson(function);
           functions.add(jsonFunction);
        });

        // Add documentation for the library
        FEELLibrary feelLibrary = new FEELLibrary(name, functions);
        augment(feelLibrary);
        return feelLibrary;
    }

    private void augment(FEELLibrary feelLibrary) throws Exception {
        // Read docs.json file as List of maps (avoid raw types)
        String libraryName = feelLibrary.name();
        String fileName = String.format("/feel/json/%s-docs.json", libraryName);
        com.fasterxml.jackson.core.type.TypeReference<java.util.List<java.util.Map<String, String>>> typeRef =
                new com.fasterxml.jackson.core.type.TypeReference<java.util.List<java.util.Map<String, String>>>() {};
        java.util.List<java.util.Map<String, String>> docs = JsonSerializer.OBJECT_MAPPER.readValue(getClass().getResourceAsStream(fileName), typeRef);

        // Scan feelLibrary functions and add documentation using the signature
        for (FunctionDeclaration function : feelLibrary.functions()) {
            // Find corresponding doc in docs.json using the signature
            String signature = function.getSignature();
            boolean found = false;
            for (java.util.Map<String, String> map : docs) {
                String sgn = map.get("signature");
                String info = map.get("description");
                if (signature.equals(sgn) && !StringUtils.isBlank(info)) {
                    found = true;
                    function.setDescription(info);
                    break;
                }
            }
            if (!found) {
                throw new SemanticErrorException(String.format("Documentation for function '%s' not found in '%s'", signature, fileName));
            }
        }
    }

    private FunctionDeclaration toJson(com.gs.dmn.feel.analysis.syntax.ast.library.FunctionDeclaration<?> function) {
        String name = function.getName();
        String returnType = toJson(function.getReturnTypeExpression());
        List<Parameter> parameters = new ArrayList<>();
        for (FormalParameter<?> formalParameter : function.getFormalParameters()) {
            Parameter jsonParameter = toJson(formalParameter);
            parameters.add(jsonParameter);
        }
        return new FunctionDeclaration(name, parameters, returnType);
    }

    private Parameter toJson(FormalParameter<?> formalParameter) {
        String name = formalParameter.getName();
        String type = toJson(formalParameter.getTypeExpression());
        if (formalParameter.isOptional()) {
            type += "?";
        } else if (formalParameter.isVarArg()) {
            type += "...";
        }
        return new Parameter(name, type);
    }

    private String toJson(TypeExpression<?> typeExpression) {
        if (typeExpression instanceof NamedTypeExpression<?>) {
            return ((NamedTypeExpression<?>) typeExpression).getQualifiedName();
        } else if (typeExpression instanceof RangeTypeExpression<?>) {
            return String.format("range<%s>", toJson(((RangeTypeExpression<?>) typeExpression).getElementTypeExpression()));
        } else if (typeExpression instanceof ListTypeExpression<?>) {
            return String.format("list<%s>", toJson(((ListTypeExpression<?>) typeExpression).getElementTypeExpression()));
        } else if (typeExpression instanceof ContextTypeExpression<?>) {
            ContextTypeExpression<?> contextTypeExpression = (ContextTypeExpression<?>) typeExpression;
            List<String> entries = new ArrayList<>();
            for (Pair<String, ? extends TypeExpression<?>> entry : contextTypeExpression.getMembers()) {
                entries.add(String.format("%s: %s", entry.getLeft(), toJson(entry.getRight())));
            }
            return String.format("context<%s>", String.join(", ", entries));
        } else if (typeExpression instanceof FunctionTypeExpression<?>) {
            FunctionTypeExpression<?> functionTypeExpression = (FunctionTypeExpression<?>) typeExpression;
            String returnType = toJson(functionTypeExpression.getReturnType());
            List<String> parameterTypes = new ArrayList<>();
            for (TypeExpression<?> parameterType : functionTypeExpression.getParameters()) {
                parameterTypes.add(toJson(parameterType));
            }
            return String.format("function<%s> -> %s", String.join(", ", parameterTypes), returnType);
        } else {
            throw new SemanticErrorException(String.format("Unsupported type expression '%s'", typeExpression.getClass().getName()));
        }
    }

    public static void main(String[] args) throws Exception {
        Path outputFolder = Paths.get("dmn-core/src/main/resources/feel/json/");
        JsonDescriptionGenerator generator = new JsonDescriptionGenerator();
        generator.generate("feel/library/builtin.lib", outputFolder);
        generator.generate("feel/library/signavio.lib", outputFolder);
    }
}

