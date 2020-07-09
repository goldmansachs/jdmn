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

import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.runtime.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static com.gs.dmn.feel.analysis.syntax.ast.expression.function.ConversionKind.LIST_TO_ELEMENT;
import static com.gs.dmn.feel.analysis.syntax.ast.expression.function.ConversionKind.NONE;

public class NamedParameterTypes extends ParameterTypes {
    private Map<String, Type> namedTypes = new LinkedHashMap<>();

    public NamedParameterTypes(Map<String, Type> namedTypes) {
        if (namedTypes != null) {
            this.namedTypes = namedTypes;
        }
    }

    @Override
    public int size() {
        return namedTypes == null ? 0 : namedTypes.size();
    }

    @Override
    public boolean compatible(List<FormalParameter> parameters) {
        if (size() != parameters.size()) {
            return false;
        }
        for (FormalParameter formalParameter : parameters) {
            Type argumentType = namedTypes.get(formalParameter.getName());
            Type parameterType = formalParameter.getType();
            if (argumentType == null || !argumentType.conformsTo(parameterType)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Pair<ParameterTypes, ParameterConversions>> candidates() {
        Set<Pair<ParameterTypes, ParameterConversions>> result = new LinkedHashSet<>();
        int n = namedTypes.size();
        List<String> paramNames = new ArrayList<>(namedTypes.keySet());
        int m = ConversionKind.values().length;
        int[] conversionMap = init(n);
        conversionMap = next(conversionMap, n, m);
        while (conversionMap != null) {
            // Calculate new types and conversions
            Map<String, Type> newTypes = new LinkedHashMap<>();
            NamedParameterConversions conversions = new NamedParameterConversions();

            // For every possible conversion sequence
            boolean different = false;
            for (int i = 0; i < n; i++) {
                // Compute new type and conversion
                ConversionKind kind = ConversionKind.values()[conversionMap[i]];
                String paramName = paramNames.get(i);
                Type type = namedTypes.get(paramName);
                Type newType = type;
                Conversion conversion = new Conversion(NONE, newType);
                if (kind == LIST_TO_ELEMENT) {
                    if (type instanceof ListType) {
                        newType = ((ListType) type).getElementType();
                        conversion = new Conversion(kind, newType);

                        different = true;
                    }
                }
                newTypes.put(paramName, newType);
                conversions.add(paramName, conversion);
            }

            // Add new candidate
            if (different) {
                NamedParameterTypes newSignature = new NamedParameterTypes(newTypes);
                result.add(new Pair<>(newSignature, conversions));
            }

            // Next sequence
            conversionMap = next(conversionMap, n, m);
        }
        return new ArrayList<>(result);
    }

    public Type getType(String name) {
        return namedTypes.get(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedParameterTypes that = (NamedParameterTypes) o;
        return Objects.equals(namedTypes, that.namedTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namedTypes);
    }

    @Override
    public String toString() {
        String opd = namedTypes.entrySet().stream().map(e -> String.format("%s : %s", e.getKey(), e.getValue().toString())).collect(Collectors.joining(", "));
        return String.format("NamedParameterTypes(%s)", opd);
    }
}
