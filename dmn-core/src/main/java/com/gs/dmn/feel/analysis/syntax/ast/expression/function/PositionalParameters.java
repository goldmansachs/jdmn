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
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class PositionalParameters<C> extends Parameters<C> {
    private final List<Expression<C>> parameters;
    private PositionalArguments<C> originalArguments;
    private PositionalParameterConversions<C> parameterConversions;
    private PositionalParameterTypes<C> parameterTypes;
    private PositionalArguments<C> convertedArguments;

    public PositionalParameters(List<Expression<C>> params) {
        if (params == null) {
            params = new ArrayList<>();
        }
        this.parameters = params;
    }

    public List<Expression<C>> getParameters() {
        return this.parameters;
    }

    @Override
    public boolean isEmpty() {
        return this.parameters.isEmpty();
    }

    @Override
    public ParameterTypes<C> getSignature() {
        return new PositionalParameterTypes<>(this.parameters.stream().map(Expression::getType).collect(Collectors.toList()));
    }

    @Override
    public Arguments<C> getOriginalArguments() {
        return this.originalArguments;
    }

    @Override
    public void setOriginalArguments(Arguments<C> originalArguments) {
        this.originalArguments = (PositionalArguments<C>) originalArguments;
    }

    @Override
    public ParameterConversions<C> getParameterConversions() {
        return this.parameterConversions;
    }

    @Override
    public void setParameterConversions(ParameterConversions<C> parameterConversions) {
        this.parameterConversions = (PositionalParameterConversions<C>) parameterConversions;
    }

    @Override
    public ParameterTypes<C> getConvertedParameterTypes() {
        return this.parameterTypes;
    }

    @Override
    public void setConvertedParameterTypes(ParameterTypes<C> parameterTypes) {
        this.parameterTypes = (PositionalParameterTypes<C>) parameterTypes;
    }

    @Override
    public Arguments<C> getConvertedArguments() {
        return this.convertedArguments;
    }

    @Override
    public Arguments<C> convertArguments(BiFunction<Object, Conversion, Object> convertArgument) {
        if (requiresConversion()) {
            this.convertedArguments = new PositionalArguments<>();
            for (int i = 0; i< this.parameterConversions.getConversions().size(); i++) {
                Object arg = this.originalArguments.getArguments().get(i);
                Conversion conversion = this.parameterConversions.getConversions().get(i);
                Object convertedArg = convertArgument.apply(arg, conversion);
                this.convertedArguments.add(convertedArg);
            }
        } else {
            this.convertedArguments = this.originalArguments;
        }
        return this.convertedArguments;
    }

    @Override
    public Expression<C> getParameter(int position, String name) {
        if (0 > position || position > this.parameters.size()) {
            return null;
        } else {
            return this.parameters.get(position);
        }
    }

    @Override
    public Type getParameterType(int position, String name) {
        if (0 > position || position > this.parameters.size()) {
            return null;
        } else {
            return this.parameters.get(position).getType();
        }
    }

    private boolean requiresConversion() {
        if (this.parameterConversions == null) {
            return false;
        }
        return this.parameterConversions.getConversions().stream().anyMatch(c -> c.getKind() != ConversionKind.NONE);
    }

    @Override
    public Object accept(Visitor<C> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        String opd = this.parameters.stream().map(Object::toString).collect(Collectors.joining(", "));
        return String.format("%s(%s)", getClass().getSimpleName(), opd);
    }
}
