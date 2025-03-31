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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class PositionalParameters<T> extends Parameters<T> {
    private final List<Expression<T>> parameters;
    private PositionalArguments<T> originalArguments;
    private PositionalParameterConversions<T> parameterConversions;
    private PositionalParameterTypes<T> parameterTypes;
    private PositionalArguments<T> convertedArguments;

    public PositionalParameters(List<Expression<T>> params) {
        if (params == null) {
            params = new ArrayList<>();
        }
        this.parameters = params;
    }

    public List<Expression<T>> getParameters() {
        return this.parameters;
    }

    @Override
    public boolean isEmpty() {
        return this.parameters.isEmpty();
    }

    @Override
    public ParameterTypes<T> getSignature() {
        return new PositionalParameterTypes<>(this.parameters.stream().map(Expression::getType).collect(Collectors.toList()));
    }

    @Override
    public void setOriginalArguments(Arguments<T> originalArguments) {
        this.originalArguments = (PositionalArguments<T>) originalArguments;
    }

    @Override
    public void setParameterConversions(ParameterConversions<T> parameterConversions) {
        this.parameterConversions = (PositionalParameterConversions<T>) parameterConversions;
    }

    @Override
    public void setConvertedParameterTypes(ParameterTypes<T> parameterTypes) {
        this.parameterTypes = (PositionalParameterTypes<T>) parameterTypes;
    }

    @Override
    public Arguments<T> convertArguments(BiFunction<Object, Conversion<T>, Object> convertArgument) {
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
    public Expression<T> getParameter(int position, String name) {
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
            Expression<T> expression = this.parameters.get(position);
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
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        String opd = this.parameters.stream().map(Object::toString).collect(Collectors.joining(", "));
        return String.format("%s(%s)", getClass().getSimpleName(), opd);
    }
}
