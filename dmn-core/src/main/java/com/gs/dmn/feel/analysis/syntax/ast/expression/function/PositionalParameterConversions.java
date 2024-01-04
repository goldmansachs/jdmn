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

public class PositionalParameterConversions<T> extends ParameterConversions<T> {
    private final List<Conversion<T>> conversions = new ArrayList<>();

    public PositionalParameterConversions() {
    }

    public PositionalParameterConversions(List<T> types) {
        for (T type: types) {
            this.conversions.add(new Conversion<>(ConversionKind.NONE, type));
        }
    }

    public List<Conversion<T>> getConversions() {
        return this.conversions;
    }

    public void add(Conversion<T> conversion) {
        this.conversions.add(conversion);
    }

    public void set(int i, Conversion<T> conversion) {
        this.conversions.set(i, conversion);
    }

    @Override
    public List<Conversion<T>> getConversions(List<FormalParameter<T>> formalParameters) {
        return this.conversions;
    }

    @Override
    public boolean hasConversion(ConversionKind kind) {
        return this.conversions.stream().anyMatch(c -> c.getKind() == kind);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionalParameterConversions<?> that = (PositionalParameterConversions<?>) o;
        return Objects.equals(this.conversions, that.conversions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.conversions);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), this.conversions);
    }
}

