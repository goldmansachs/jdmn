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
package com.gs.dmn.feel.analysis.semantics.type;

import com.gs.dmn.feel.analysis.semantics.environment.Parameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.NamedSignature;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.PositionalSignature;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.Signature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;

public class BuiltinFunctionType extends FunctionType {
    private final int totalParamsCount;
    private final int mandatoryParamsCount;
    private final boolean hasOptionalParams;
    private final boolean hasVarArgs;

    public BuiltinFunctionType(Type type, Parameter... parameters) {
        this(Arrays.asList(parameters), type);
    }

    public BuiltinFunctionType(List<Parameter> parameters, Type returnType) {
        super(new ArrayList<FormalParameter>(parameters), returnType);
        this.totalParamsCount = parameters.size();
        this.mandatoryParamsCount = parameters.stream().filter(p -> !p.isOptional() && !p.isVarArg()).collect(Collectors.toList()).size();
        this.hasOptionalParams = parameters.stream().anyMatch(Parameter::isOptional);
        this.hasVarArgs = parameters.stream().anyMatch(Parameter::isVarArg);
    }

    @Override
    public boolean match(Signature signature) {
        if (signature instanceof PositionalSignature) {
            List<Type> argumentTypes = ((PositionalSignature)signature).getTypes();
            return match(argumentTypes);
        } else {
            NamedSignature namedSignature = (NamedSignature)signature;
            List<Type> argumentTypes = new ArrayList<>();
            for(FormalParameter parameter: parameters) {
                Type type = namedSignature.getType(parameter.getName());
                if (type != null) {
                    argumentTypes.add(type);
                } else {
                    if (parameter instanceof Parameter) {
                        if (((Parameter)parameter).isOptional()) {
                        } else if (((Parameter)parameter).isVarArg()) {
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
            return match(argumentTypes);
        }
    }

    public boolean match(List<Type> argumentTypes) {
        if (hasOptionalParams) {
            // check mandatory parameters
            if (argumentTypes.size() < mandatoryParamsCount) {
                return false;
            }
            if (!compatibleMandatoryParameters(argumentTypes)) {
                return false;
            }
            // Check optional parameters
            if (totalParamsCount < argumentTypes.size()) {
                return false;
            }
            for (int i = mandatoryParamsCount; i < argumentTypes.size(); i++) {
                if (!conformsTo(argumentTypes, getParameterTypes(), i)) {
                    return false;
                }
            }
            return true;
        } else if (hasVarArgs) {
            // check mandatory parameters
            if (argumentTypes.size() < mandatoryParamsCount) {
                return false;
            }
            if (!compatibleMandatoryParameters(argumentTypes)) {
                return false;
            }
            // Check varArgs
            Type varArgType = parameters.get(mandatoryParamsCount).getType();
            for (int i = mandatoryParamsCount; i < argumentTypes.size(); i++) {
                if (!argumentTypes.get(i).conformsTo(varArgType)) {
                    return false;
                }
            }
            return true;
        } else {
            // same length and compatible types
            if (argumentTypes.size() != mandatoryParamsCount) {
                return false;
            }
            return compatibleMandatoryParameters(argumentTypes);
        }
    }

    private boolean compatibleMandatoryParameters(List<Type> argumentTypes) {
        for (int i = 0; i < mandatoryParamsCount; i++) {
            if (!conformsTo(argumentTypes, parameterTypes, i)) {
                return false;
            }
        }
        return true;
    }

    private boolean conformsTo(List<Type> argumentTypes, List<Type> parameterTypes, int position) {
        Type argumentType = argumentTypes.get(position);
        Type parameterType = parameterTypes.get(position);
        return argumentType.conformsTo(parameterType);
    }

    @Override
    public boolean equivalentTo(Type other) {
        return false;
    }

    @Override
    public boolean conformsTo(Type other) {
        return other instanceof BuiltinFunctionType && this.equivalentTo(other)
                || other == ANY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuiltinFunctionType that = (BuiltinFunctionType) o;

        if (totalParamsCount != that.totalParamsCount) return false;
        if (mandatoryParamsCount != that.mandatoryParamsCount) return false;
        if (hasOptionalParams != that.hasOptionalParams) return false;
        if (hasVarArgs != that.hasVarArgs) return false;
        if (parameters != null ? !parameters.equals(that.parameters) : that.parameters != null) return false;
        return returnType != null ? returnType.equals(that.returnType) : that.returnType == null;
    }

    @Override
    public int hashCode() {
        int result = parameters != null ? parameters.hashCode() : 0;
        result = 31 * result + (returnType != null ? returnType.hashCode() : 0);
        result = 31 * result + totalParamsCount;
        result = 31 * result + mandatoryParamsCount;
        result = 31 * result + (hasOptionalParams ? 1 : 0);
        result = 31 * result + (hasVarArgs ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        String types = parameters.stream().map(p -> p == null ? "null" : p.toString()).collect(Collectors.joining(", "));
        return String.format("BuiltinFunctionType(%s, %s)", types, returnType);
    }
}
