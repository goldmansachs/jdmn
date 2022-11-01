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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class PositionalParameters<T, C> extends Parameters<T, C> {
    private final List<Expression<T, C>> parameters;
    private PositionalArguments<T, C> originalArguments;
    private PositionalParameterConversions<T, C> parameterConversions;
    private PositionalParameterTypes<T, C> parameterTypes;
    private PositionalArguments<T, C> convertedArguments;

    public PositionalParameters(List<Expression<T, C>> params) {
        if (params == null) {
            params = new ArrayList<>();
        }
        this.parameters = params;
    }

    public List<Expression<T, C>> getParameters() {
        return this.parameters;
    }

    @Override
    public boolean isEmpty() {
        return this.parameters.isEmpty();
    }

    @Override
    public ParameterTypes<T, C> getSignature() {
        return new PositionalParameterTypes<>(this.parameters.stream().map(Expression::getType).collect(Collectors.toList()));
    }

    @Override
    public Arguments<T, C> getOriginalArguments() {
        return this.originalArguments;
    }

    @Override
    public void setOriginalArguments(Arguments<T, C> originalArguments) {
        this.originalArguments = (PositionalArguments<T, C>) originalArguments;
    }

    @Override
    public ParameterConversions<T, C> getParameterConversions() {
        return this.parameterConversions;
    }

    @Override
    public void setParameterConversions(ParameterConversions<T, C> parameterConversions) {
        this.parameterConversions = (PositionalParameterConversions<T, C>) parameterConversions;
    }

    @Override
    public ParameterTypes<T, C> getConvertedParameterTypes() {
        return this.parameterTypes;
    }

    @Override
    public void setConvertedParameterTypes(ParameterTypes<T, C> parameterTypes) {
        this.parameterTypes = (PositionalParameterTypes<T, C>) parameterTypes;
    }

    @Override
    public Arguments<T, C> getConvertedArguments() {
        return this.convertedArguments;
    }

    @Override
    public Arguments<T, C> convertArguments(BiFunction<Object, Conversion<T>, Object> convertArgument) {
        if (requiresConversion()) {
            this.convertedArguments = new PositionalArguments<>();
            for (int i = 0; i< this.parameterConversions.getConversions().size(); i++) {
                Object arg = this.originalArguments.getArguments().get(i);
                Conversion<T> conversion = this.parameterConversions.getConversions().get(i);
                Object convertedArg = convertArgument.apply(arg, conversion);
                this.convertedArguments.add(convertedArg);
            }
        } else {
            this.convertedArguments = this.originalArguments;
        }
        return this.convertedArguments;
    }

    @Override
    public Expression<T, C> getParameter(int position, String name) {
        if (0 > position || position > this.parameters.size()) {
            return null;
        } else {
            return this.parameters.get(position);
        }
    }

    @Override
    public T getParameterType(int position, String name) {
        if (0 > position || position > this.parameters.size()) {
            return null;
        } else {
            Expression<T, C> expression = this.parameters.get(position);
            if (expression == null) {
                throw new DMNRuntimeException(String.format("Cannot find parameter '%s'", name));
            }
            return expression.getType();
        }
    }

    private boolean requiresConversion() {
        if (this.parameterConversions == null) {
            return false;
        }
        return this.parameterConversions.getConversions().stream().anyMatch(c -> c.getKind() != ConversionKind.NONE);
    }

    @Override
    public Object accept(Visitor<T, C> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        String opd = this.parameters.stream().map(Object::toString).collect(Collectors.joining(", "));
        return String.format("%s(%s)", getClass().getSimpleName(), opd);
    }
}
