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

import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.feel.analysis.semantics.type.LibraryFunctionType;

import java.util.List;
import java.util.Objects;

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
}
