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

import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class NamedParameters<T, C> extends Parameters<T, C> {
    private final Map<String, Expression<T, C>> parameters;
    private NamedArguments<T, C> originalArguments;
    private NamedParameterConversions<T, C> parameterConversions;
    private NamedParameterTypes<T, C> parameterTypes;
    private NamedArguments<T, C> convertedArguments;

    public NamedParameters(Map<String, Expression<T, C>> params) {
        if (params == null) {
            params = new LinkedHashMap<>();
        }
        this.parameters = params;
    }

    public Map<String, Expression<T, C>> getParameters() {
        return this.parameters;
    }

    @Override
    public boolean isEmpty() {
        return this.parameters.isEmpty();
    }

    @Override
    public ParameterTypes<T, C> getSignature() {
        Map<String, T> signature = new LinkedHashMap<>();
        this.parameters.forEach((key, value) -> signature.put(key, value.getType()));
        return new NamedParameterTypes<>(signature);
    }

    @Override
    public Arguments<T, C> getOriginalArguments() {
        return this.originalArguments;
    }

    @Override
    public void setOriginalArguments(Arguments<T, C> originalArguments) {
        this.originalArguments = (NamedArguments<T, C>) originalArguments;
    }

    @Override
    public ParameterConversions<T, C> getParameterConversions() {
        return this.parameterConversions;
    }

    @Override
    public void setParameterConversions(ParameterConversions<T, C> parameterConversions) {
        this.parameterConversions = (NamedParameterConversions<T, C>) parameterConversions;
    }

    @Override
    public ParameterTypes<T, C> getConvertedParameterTypes() {
        return this.parameterTypes;
    }

    @Override
    public void setConvertedParameterTypes(ParameterTypes<T, C> parameterTypes) {
        this.parameterTypes = (NamedParameterTypes<T, C>) parameterTypes;
    }

    @Override
    public Arguments<T, C> getConvertedArguments() {
        return this.convertedArguments;
    }

    @Override
    public Arguments<T, C> convertArguments(BiFunction<Object, Conversion<T>, Object> convertArgument) {
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
    public Expression<T, C> getParameter(int position, String name) {
        return this.parameters.get(name);
    }

    @Override
    public T getParameterType(int position, String name) {
        Expression<T, C> expression = this.getParameters().get(name);
        if (expression == null) {
            throw new DMNRuntimeException(String.format("Cannot find parameter '%s'", name));
        }
        return expression.getType();
    }

    private boolean requiresConversion() {
        if (this.parameterConversions == null) {
            return false;
        }
        return this.parameterConversions.getConversions().values().stream().anyMatch(c -> c.getKind().isImplicit());
    }

    @Override
    public Object accept(Visitor<T, C> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        String opd = this.parameters.entrySet().stream().map(e -> String.format("%s : %s", e.getKey(), e.getValue().toString())).collect(Collectors.joining(", "));
        return String.format("%s(%s)", getClass().getSimpleName(), opd);
    }

}
