/**
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

import com.gs.dmn.feel.analysis.semantics.environment.Conversion;
import com.gs.dmn.feel.analysis.semantics.environment.ConversionKind;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.NamedType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.runtime.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PositionalSignature extends Signature {
    private final List<Type> types = new ArrayList<>();

    public PositionalSignature(List<Type> types) {
        if (types != null) {
            this.types.addAll(types);
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
    public List<Pair<Signature, List<Conversion>>> candidates() {
        // TODO extends to all parameters and conversions
        if (size() == 1) {
            Type type = types.get(0);
            if (type instanceof ListType) {
                Type elementType = ((ListType) type).getElementType();
                List<Conversion> conversions = Arrays.asList(new Conversion(ConversionKind.LIST_TO_ELEMENT, elementType));
                List<Type> newSig = new ArrayList<>();
                newSig.add(elementType);
                PositionalSignature newSignature = new PositionalSignature(newSig);
                return Arrays.asList(new Pair(newSignature, conversions));
            }
        }
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        String opd = types.stream().map(Type::toString).collect(Collectors.joining(", "));
        return String.format("PositionalSignature(%s)", opd);
    }
}
