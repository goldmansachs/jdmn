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

import com.gs.dmn.feel.analysis.semantics.type.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PositionalParameterConversions extends ParameterConversions {
    private final List<Conversion> conversions = new ArrayList<>();

    public PositionalParameterConversions() {
    }

    public PositionalParameterConversions(List<Type> types) {
        for (Type type: types) {
            conversions.add(new Conversion(ConversionKind.NONE, type));
        }
    }

    public List<Conversion> getConversions() {
        return conversions;
    }

    public void add(Conversion conversion) {
        this.conversions.add(conversion);
    }

    public void set(int i, Conversion conversion) {
        this.conversions.set(i, conversion);
    }

    @Override
    public List<Conversion> getConversions(List<FormalParameter> formalParameters) {
        return this.conversions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionalParameterConversions that = (PositionalParameterConversions) o;
        return Objects.equals(conversions, that.conversions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conversions);
    }

    @Override
    public String toString() {
        return String.format("PositionalParameterConversions(%s)", conversions);
    }
}

