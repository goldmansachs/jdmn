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

import java.util.*;

public class NamedParameterConversions<T, C> extends ParameterConversions<T, C> {
    public static <T, C> NamedParameterConversions<T, C> toNamedParameterConversions(PositionalParameterConversions<T, C> candidateParameterConversions, List<FormalParameter<T, C>> formalParameters) {
        NamedParameterConversions<T, C> matchParameterConversions = new NamedParameterConversions<>(formalParameters);
        List<Conversion<T>> conversions = candidateParameterConversions.getConversions(formalParameters);
        for (int i = 0; i< formalParameters.size(); i++) {
            FormalParameter<T, C> p = formalParameters.get(i);
            matchParameterConversions.add(p.getName(), conversions.get(i));
        }
        return matchParameterConversions;
    }

    private final Map<String, Conversion<T>> conversions;

    public NamedParameterConversions() {
        this(new LinkedHashMap<>());
    }

    public NamedParameterConversions(Map<String, Conversion<T>> conversions) {
        if (conversions == null) {
            conversions = new LinkedHashMap<>();
        }
        this.conversions = conversions;
    }

    public NamedParameterConversions(List<FormalParameter<T, C>> parameters) {
        this.conversions = new LinkedHashMap<>();
        for (FormalParameter<T, C> parameter: parameters) {
            this.conversions.put(parameter.getName(), new Conversion<>(ConversionKind.NONE, parameter.getType()));
        }
    }

    public Map<String, Conversion<T>> getConversions() {
        return this.conversions;
    }

    public void add(String key, Conversion<T> conversion) {
        this.conversions.put(key, conversion);
    }

    @Override
    public List<Conversion<T>> getConversions(List<FormalParameter<T, C>> formalParameters) {
        List<Conversion<T>> conversions = new ArrayList<>();
        for(FormalParameter<T, C> parameter: formalParameters) {
            conversions.add(this.conversions.get(parameter.getName()));
        }
        return conversions;
    }

    @Override
    public boolean hasConversion(ConversionKind kind) {
        return this.conversions.values().stream().anyMatch(c -> c.getKind() == kind);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedParameterConversions<?, ?> that = (NamedParameterConversions<?, ?>) o;
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
