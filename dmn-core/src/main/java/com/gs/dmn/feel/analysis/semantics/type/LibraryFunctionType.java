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
package com.gs.dmn.feel.analysis.semantics.type;

import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.library.ELLib;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryFunctionType extends BuiltinFunctionType {
    private ELLib lib;

    public void setLibrary(ELLib elLib) {
        this.lib = elLib;
    }

    public LibraryFunctionType(List<FormalParameter<Type>> parameters, Type returnType) {
        super(new ArrayList<>(parameters), returnType);
    }

    public ELLib getLib() {
        return lib;
    }

    @Override
    public String toString() {
        String types = this.parameters.stream().map(p -> p == null ? "null" : p.toString()).collect(Collectors.joining(", "));
        return String.format("LibraryFunctionType(%s, %s, %s)", types, this.returnType, lib == null ? null : lib.getName());
    }
}
