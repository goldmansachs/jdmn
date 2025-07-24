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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;

public class TupleType implements Type, FEELType {
    private final List<Type> types = new ArrayList<>();

    public TupleType() {
    }

    public TupleType(List<Type> types) {
        if (types != null) {
            this.types.addAll(types);
        }
    }

    public List<Type> getTypes() {
        return types;
    }

    @Override
    public boolean equivalentTo(Type other) {
        return other instanceof TupleType
                && com.gs.dmn.el.analysis.semantics.type.Type.equivalentTo(this.types, ((TupleType) other).types);
    }

    @Override
    public boolean conformsTo(Type other) {
        return other == ANY
                || other instanceof TupleType && com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(this.types, ((TupleType) other).types)
                || equivalentTo(other)
                ;
    }

    @Override
    public boolean isFullySpecified() {
        if (types.isEmpty()) {
            return false;
        }
        return types.stream().noneMatch(com.gs.dmn.el.analysis.semantics.type.Type::isNullOrAny);
    }

    @Override
    public String typeExpression() {
        String types = this.types.stream().map(Type::typeExpression).collect(Collectors.joining(", "));
        return String.format("tuple<%s>", types);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TupleType tupleType = (TupleType) o;

        return types.equals(tupleType.types);

    }

    @Override
    public int hashCode() {
        return types.hashCode();
    }

    @Override
    public String toString() {
        String types = this.types.stream().map(Type::toString).collect(Collectors.joining(", "));
        return String.format("TupleType(%s)", types);
    }
}
