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

public class PositionalParameterTypes extends ParameterTypes {
    private List<Type> types = new ArrayList<>();

    public PositionalParameterTypes(List<Type> types) {
        if (types != null) {
            this.types = types;
        }
    }

    public List<Type> getTypes() {
        return this.types;
    }

    @Override
    public int size() {
        return types.size();
    }

    @Override
    public boolean compatible(List<FormalParameter> parameters) {
        if (size() != parameters.size()) {
            return false;
        }
        for (int i = 0; i < parameters.size(); i++) {
            if (!types.get(i).conformsTo(parameters.get(i).getType())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Pair<ParameterTypes, ParameterConversions>> candidates() {
        Set<Pair<ParameterTypes, ParameterConversions>> result = new LinkedHashSet<>();
        int n = types.size();
        int m = ConversionKind.values().length;
        int[] conversionMap = init(n);
        conversionMap = next(conversionMap, n, m);
        while (conversionMap != null) {
            // Calculate new types and conversions
            List<Type> newTypes = new ArrayList<>();
            PositionalParameterConversions conversions = new PositionalParameterConversions();

            // For every possible conversion sequence
            boolean different = false;
            for (int i = 0; i < n; i++) {
                // Compute new type and conversion
                ConversionKind kind = ConversionKind.values()[conversionMap[i]];
                Type type = types.get(i);
                Type newType = type;
                Conversion conversion = new Conversion(NONE, newType);
                if (kind == LIST_TO_ELEMENT) {
                    if (type instanceof ListType) {
                        newType = ((ListType) type).getElementType();
                        conversion = new Conversion(kind, newType);

                        different = true;
                    }
                }
                newTypes.add(newType);
                conversions.add(conversion);
            }

            // Add new candidate
            if (different) {
                PositionalParameterTypes newSignature = new PositionalParameterTypes(newTypes);
                result.add(new Pair<>(newSignature, conversions));
            }

            // Next sequence
            conversionMap = next(conversionMap, n, m);
        }
        return new ArrayList<>(result);
    }

    @Override
    public String toString() {
        String opd = types.stream().map(Type::toString).collect(Collectors.joining(", "));
        return String.format("PositionalParameterTypes(%s)", opd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionalParameterTypes that = (PositionalParameterTypes) o;
        return Objects.equals(types, that.types);
    }

    @Override
    public int hashCode() {
        return Objects.hash(types);
    }
}
