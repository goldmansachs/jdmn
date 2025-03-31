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

import com.gs.dmn.feel.analysis.syntax.ConversionKind;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class NamedParameters<T> extends Parameters<T> {
    private final Map<String, Expression<T>> parameters;
    private NamedArguments<T> originalArguments;
    private NamedParameterConversions<T> parameterConversions;
    private NamedParameterTypes<T> parameterTypes;
    private NamedArguments<T> convertedArguments;

    public NamedParameters(Map<String, Expression<T>> params) {
        if (params == null) {
            params = new LinkedHashMap<>();
        }
        this.parameters = params;
    }

    public Map<String, Expression<T>> getParameters() {
        return this.parameters;
    }

    @Override
    public boolean isEmpty() {
        return this.parameters.isEmpty();
    }

    @Override
    public ParameterTypes<T> getSignature() {
        Map<String, T> signature = new LinkedHashMap<>();
        this.parameters.forEach((key, value) -> signature.put(key, value.getType()));
        return new NamedParameterTypes<>(signature);
    }

    @Override
    public void setOriginalArguments(Arguments<T> originalArguments) {
        this.originalArguments = (NamedArguments<T>) originalArguments;
    }

    @Override
    public void setParameterConversions(ParameterConversions<T> parameterConversions) {
        this.parameterConversions = (NamedParameterConversions<T>) parameterConversions;
    }

    @Override
    public void setConvertedParameterTypes(ParameterTypes<T> parameterTypes) {
        this.parameterTypes = (NamedParameterTypes<T>) parameterTypes;
    }

    @Override
    public Arguments<T> convertArguments(BiFunction<Object, Conversion<T>, Object> convertArgument) {
        if (requiresConversion()) {
            this.convertedArguments = new NamedArguments<>();
            for (Map.Entry<String, Conversion<T>> entry : this.parameterConversions.getConversions().entrySet()) {
                String key = entry.getKey();
                Object arg = this.originalArguments.getArguments().get(key);
                Conversion<T> conversion = entry.getValue();
                Object convertedArg = convertArgument.apply(arg, conversion);
                this.convertedArguments.add(key, convertedArg);
            }
        } else {
            this.convertedArguments = this.originalArguments;
        }
        return this.convertedArguments;
    }

    @Override
    public Expression<T> getParameter(int position, String name) {
        return this.parameters.get(name);
    }

    @Override
    public T getParameterType(int position, String name) {
        Expression<T> expression = this.getParameters().get(name);
        if (expression == null) {
            throw new DMNRuntimeException(String.format("Cannot find parameter '%s'", name));
        }
        return expression.getType();
    }

    private boolean requiresConversion() {
        if (this.parameterConversions == null) {
            return false;
        }
        return this.parameterConversions.getConversions().values().stream().anyMatch(c -> c.getKind() != ConversionKind.NONE);
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        String opd = this.parameters.entrySet().stream().map(e -> String.format("%s : %s", e.getKey(), e.getValue().toString())).collect(Collectors.joining(", "));
        return String.format("%s(%s)", getClass().getSimpleName(), opd);
    }

}
