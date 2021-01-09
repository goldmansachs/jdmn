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
import com.gs.dmn.runtime.DMNContext;
import com.gs.dmn.runtime.interpreter.Arguments;
import com.gs.dmn.runtime.interpreter.PositionalArguments;

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
        return parameters;
    }

    @Override
    public boolean isEmpty() {
        return this.parameters.isEmpty();
    }

    @Override
    public ParameterTypes getSignature() {
        return new PositionalParameterTypes(parameters.stream().map(Expression::getType).collect(Collectors.toList()));
    }

    @Override
    public Arguments getOriginalArguments() {
        return originalArguments;
    }

    @Override
    public void setOriginalArguments(Arguments originalArguments) {
        this.originalArguments = (PositionalArguments) originalArguments;
    }

    @Override
    public ParameterConversions getParameterConversions() {
        return parameterConversions;
    }

    @Override
    void setParameterConversions(ParameterConversions parameterConversions) {
        this.parameterConversions = (PositionalParameterConversions) parameterConversions;
    }

    @Override
    public ParameterTypes getConvertedParameterTypes() {
        return this.parameterTypes;
    }

    @Override
    void setConvertedParameterTypes(ParameterTypes parameterTypes) {
        this.parameterTypes = (PositionalParameterTypes) parameterTypes;
    }

    @Override
    public Arguments getConvertedArguments() {
        return convertedArguments;
    }

    @Override
    public Arguments convertArguments(BiFunction<Object, Conversion, Object> convertArgument) {
        if (requiresConversion()) {
            this.convertedArguments = new PositionalArguments();
            for (int i=0; i<parameterConversions.getConversions().size(); i++) {
                Object arg = originalArguments.getArguments().get(i);
                Conversion conversion = parameterConversions.getConversions().get(i);
                Object convertedArg = convertArgument.apply(arg, conversion);
                convertedArguments.add(convertedArg);
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
        return parameterConversions.getConversions().stream().anyMatch(c -> c.getKind() != ConversionKind.NONE);
    }

    @Override
    public Object accept(Visitor visitor, DMNContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        String opd = parameters.stream().map(Object::toString).collect(Collectors.joining(", "));
        return String.format("PositionalParameters(%s)", opd);
    }
}
