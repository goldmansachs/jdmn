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
import com.gs.dmn.runtime.DMNContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class PositionalParameters extends Parameters {
    private final List<Expression> parameters;
    private PositionalArguments originalArguments;
    private PositionalParameterConversions parameterConversions;
    private PositionalParameterTypes parameterTypes;
    private PositionalArguments convertedArguments;

    public PositionalParameters(List<Expression> params) {
        if (params == null) {
            params = new ArrayList<>();
        }
        this.parameters = params;
    }

    public List<Expression> getParameters() {
        return this.parameters;
    }

    @Override
    public boolean isEmpty() {
        return this.parameters.isEmpty();
    }

    @Override
    public ParameterTypes getSignature() {
        return new PositionalParameterTypes(this.parameters.stream().map(Expression::getType).collect(Collectors.toList()));
    }

    @Override
    public Arguments getOriginalArguments() {
        return this.originalArguments;
    }

    @Override
    public void setOriginalArguments(Arguments originalArguments) {
        this.originalArguments = (PositionalArguments) originalArguments;
    }

    @Override
    public ParameterConversions getParameterConversions() {
        return this.parameterConversions;
    }

    @Override
    public void setParameterConversions(ParameterConversions parameterConversions) {
        this.parameterConversions = (PositionalParameterConversions) parameterConversions;
    }

    @Override
    public ParameterTypes getConvertedParameterTypes() {
        return this.parameterTypes;
    }

    @Override
    public void setConvertedParameterTypes(ParameterTypes parameterTypes) {
        this.parameterTypes = (PositionalParameterTypes) parameterTypes;
    }

    @Override
    public Arguments getConvertedArguments() {
        return this.convertedArguments;
    }

    @Override
    public Arguments convertArguments(BiFunction<Object, Conversion, Object> convertArgument) {
        if (requiresConversion()) {
            this.convertedArguments = new PositionalArguments();
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
    public Expression getParameter(int position, String name) {
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
    public Object accept(Visitor visitor, DMNContext context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        String opd = this.parameters.stream().map(Object::toString).collect(Collectors.joining(", "));
        return String.format("%s(%s)", getClass().getSimpleName(), opd);
    }
}
