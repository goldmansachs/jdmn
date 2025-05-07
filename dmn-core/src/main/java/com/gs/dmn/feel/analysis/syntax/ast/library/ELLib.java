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
package com.gs.dmn.feel.analysis.syntax.ast.library;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.context.environment.VariableDeclaration;
import com.gs.dmn.feel.analysis.semantics.type.LibraryFunctionType;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.function.LibraryFunction;
import com.gs.dmn.transformation.basic.ImportContextType;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ELLib {
    private final LibraryMetadata metadata;
    private final String namespace;
    private final String name;
    private final List<Declaration> declarations;

    public ELLib(LibraryMetadata metadata, String namespace, String name, List<Declaration> declarations) {
        this.metadata = metadata;
        Objects.requireNonNull(namespace, "Missing namespace");
        Objects.requireNonNull(name, "Missing name");
        Objects.requireNonNull(declarations, "Missing declarations");

        this.namespace = namespace;
        this.name = name;
        this.declarations = declarations;
        updateDeclarations();
    }

    public LibraryMetadata getMetadata() {
        return metadata;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getName() {
        return name;
    }

    public List<Declaration> getDeclarations() {
        return declarations;
    }

    private void updateDeclarations() {
        for (Declaration declaration : this.declarations) {
            LibraryFunctionType type = (LibraryFunctionType) declaration.getType();
            type.setLibrary(this);
        }
    }

    public static void bind(List<Pair<String, ELLib>> importedLibraries, DMNContext libraryContext) {
        for (Pair<String, ELLib> importedLib : importedLibraries) {
            String prefix = importedLib.getLeft();
            ELLib lib = importedLib.getRight();
            if (StringUtils.isBlank(prefix)) {
                // Add declarations
                Map<String, List<Declaration>> libraryDeclarations = lib.getDeclarations().stream().collect(Collectors.groupingBy(Declaration::getName));
                for (Map.Entry<String, List<Declaration>> entry: libraryDeclarations.entrySet()) {
                    // Add declarations
                    for (Declaration declaration : entry.getValue()) {
                        libraryContext.addDeclaration(declaration);
                    }
                    // Bind value
                    libraryContext.bind(entry.getKey(), LibraryFunction.of(entry.getValue()));
                }
            } else {
                // Add declaration
                libraryContext.addDeclaration(lib.getContextDeclaration(prefix));
                // Bind value
                libraryContext.bind(prefix, lib.getContextValue());
            }
        }
    }

    private VariableDeclaration getContextDeclaration(String prefix) {
        ImportContextType contextType = new ImportContextType(prefix);
        VariableDeclaration cDeclaration = new VariableDeclaration(prefix, contextType);
        for (Declaration declaration : this.declarations) {
            contextType.addMember(declaration.getName(), Arrays.asList(), declaration.getType());
        }
        return cDeclaration;
    }

    private Object getContextValue() {
        Context result = new Context();
        Map<String, List<Declaration>> libraryDeclarations = this.declarations.stream().collect(Collectors.groupingBy(Declaration::getName));
        for (Map.Entry<String, List<Declaration>> entry: libraryDeclarations.entrySet()) {
            result.add(entry.getKey(), LibraryFunction.of(entry.getValue()));
        }
        return result;
    }
}
