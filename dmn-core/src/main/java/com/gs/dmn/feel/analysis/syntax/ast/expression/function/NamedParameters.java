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
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.runtime.interpreter.Arguments;
import com.gs.dmn.runtime.interpreter.NamedArguments;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class NamedParameters extends Parameters {
    private final Map<String, Expression> parameters;
    private NamedArguments originalArguments;
    private NamedParameterConversions parameterConversions;
    private NamedParameterTypes parameterTypes;
    private NamedArguments convertedArguments;

    public NamedParameters(Map<String, Expression> params) {
        if (params == null) {
            params = new LinkedHashMap<>();
        }
        this.parameters = params;
    }

    public Map<String, Expression> getParameters() {
        return parameters;
    }

    @Override
    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    @Override
    public ParameterTypes getSignature() {
        Map<String, Type> signature = new LinkedHashMap<>();
        parameters.forEach((key, value) -> signature.put(key, value.getType()));
        return new NamedParameterTypes(signature);
    }

    @Override
    public Arguments getOriginalArguments() {
        return originalArguments;
    }

    @Override
    public void setOriginalArguments(Arguments originalArguments) {
        this.originalArguments = (NamedArguments) originalArguments;
    }

    @Override
    public ParameterConversions getParameterConversions() {
        return parameterConversions;
    }

    @Override
    void setParameterConversions(ParameterConversions parameterConversions) {
        this.parameterConversions = (NamedParameterConversions) parameterConversions;
    }

    @Override
    public ParameterTypes getConvertedParameterTypes() {
        return this.parameterTypes;
    }

    @Override
    void setConvertedParameterTypes(ParameterTypes parameterTypes) {
        this.parameterTypes = (NamedParameterTypes) parameterTypes;
    }

    @Override
    public Arguments getConvertedArguments() {
        return convertedArguments;
    }

    @Override
    public Arguments convertArguments(BiFunction<Object, Conversion, Object> convertArgument) {
        if (requiresConversion()) {
            this.convertedArguments = new NamedArguments();
            for (Map.Entry<String, Conversion> entry : parameterConversions.getConversions().entrySet()) {
                String key = entry.getKey();
                Object arg = originalArguments.getArguments().get(key);
                Conversion conversion = entry.getValue();
                Object convertedArg = convertArgument.apply(arg, conversion);
                convertedArguments.add(key, convertedArg);
            }
        } else {
            this.convertedArguments = originalArguments;
        }
        return convertedArguments;
    }

    private boolean requiresConversion() {
        if (parameterConversions == null) {
            return false;
        }
        return parameterConversions.getConversions().values().stream().anyMatch(c -> c.getKind() != ConversionKind.NONE);
    }

    @Override
    public Object accept(Visitor visitor, FEELContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        String opd = parameters.entrySet().stream().map(e -> String.format("%s : %s", e.getKey(), e.getValue().toString())).collect(Collectors.joining(", "));
        return String.format("NamedParameters(%s)", opd);
    }

}
