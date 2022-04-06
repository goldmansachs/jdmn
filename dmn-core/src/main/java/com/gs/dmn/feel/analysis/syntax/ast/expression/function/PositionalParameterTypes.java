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
package com.gs.dmn.feel.analysis.syntax.ast.expression.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PositionalParameterTypes<T, C> extends ParameterTypes<T, C> {
    private List<T> types = new ArrayList<>();

    public PositionalParameterTypes(List<T> types) {
        if (types != null) {
            this.types = types;
        }
    }

    public List<T> getTypes() {
        return this.types;
    }

    @Override
    public int size() {
        return this.types.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionalParameterTypes<?, ?> that = (PositionalParameterTypes<?, ?>) o;
        return Objects.equals(this.types, that.types);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.types);
    }

    @Override
    public String toString() {
        String opd = this.types.stream().map(Object::toString).collect(Collectors.joining(", "));
        return String.format("%s(%s)", getClass().getSimpleName(), opd);
    }
}
