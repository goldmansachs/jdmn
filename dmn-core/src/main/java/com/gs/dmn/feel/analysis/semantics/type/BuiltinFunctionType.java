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
package com.gs.dmn.feel.analysis.semantics.type;

import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.*;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BuiltinFunctionType extends FunctionType {
    private final int totalParamsCount;
    private final int mandatoryParamsCount;
    private final boolean hasOptionalParams;
    private final boolean hasVarArgs;

    public BuiltinFunctionType(Type type, FormalParameter<Type>... parameters) {
        this(Arrays.asList(parameters), type);
    }

    public BuiltinFunctionType(List<FormalParameter<Type>> parameters, Type returnType) {
        super(new ArrayList<>(parameters), returnType);
        this.totalParamsCount = parameters.size();
        this.mandatoryParamsCount = (int) parameters.stream().filter(p -> !p.isOptional() && !p.isVarArg()).count();
        this.hasOptionalParams = parameters.stream().anyMatch(FormalParameter::isOptional);
        this.hasVarArgs = parameters.stream().anyMatch(FormalParameter::isVarArg);
    }

    @Override
    protected List<Pair<ParameterTypes<Type>, ParameterConversions<Type>>> matchCandidates(List<Type> argumentTypes) {
        if (this.hasOptionalParams) {
            // check size constraint
            if (!(this.mandatoryParamsCount <= argumentTypes.size() && argumentTypes.size() <= this.totalParamsCount)) {
                return new ArrayList<>();
            }
            return calculateCandidates(this.parameterTypes, argumentTypes);
        } else if (this.hasVarArgs) {
            // check size constraint
            if (argumentTypes.size() < this.mandatoryParamsCount) {
                return new ArrayList<>();
            }
            // calculate candidates
            Type varArgType = this.parameters.get(this.mandatoryParamsCount).getType();
            List<Type> formalParameters = new ArrayList<>(this.parameterTypes);
            for (int i = this.mandatoryParamsCount; i < argumentTypes.size(); i++) {
                formalParameters.add(varArgType);
            }
            return calculateCandidates(formalParameters, argumentTypes);
        } else {
            // check size constraint
            if (argumentTypes.size() != this.mandatoryParamsCount) {
                return new ArrayList<>();
            }
            // calculate candidates
            return calculateCandidates(this.parameterTypes, argumentTypes);
        }
    }

    @Override
    public boolean match(ParameterTypes<Type> parameterTypes) {
        if (parameterTypes instanceof PositionalParameterTypes) {
            return match((PositionalParameterTypes<Type>) parameterTypes);
        } else {
            return match((NamedParameterTypes<Type>) parameterTypes);
        }
    }

    private boolean match(PositionalParameterTypes<Type> parameterTypes) {
        List<Type> argumentTypes = parameterTypes.getTypes();
        if (this.hasOptionalParams) {
            // check mandatory parameters
            if (argumentTypes.size() < this.mandatoryParamsCount) {
                return false;
            }
            if (!compatibleMandatoryParameters(argumentTypes)) {
                return false;
            }
            // Check optional parameters
            if (this.totalParamsCount < argumentTypes.size()) {
                return false;
            }
            for (int i = this.mandatoryParamsCount; i < argumentTypes.size(); i++) {
                if (!com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(argumentTypes.get(i), this.parameterTypes.get(i))) {
                    return false;
                }
            }
            return true;
        } else if (this.hasVarArgs) {
            // check mandatory parameters
            if (argumentTypes.size() < this.mandatoryParamsCount) {
                return false;
            }
            if (!compatibleMandatoryParameters(argumentTypes)) {
                return false;
            }
            // Check varArgs
            Type varArgType = this.parameters.get(this.mandatoryParamsCount).getType();
            for (int i = this.mandatoryParamsCount; i < argumentTypes.size(); i++) {
                if (!com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(argumentTypes.get(i), varArgType)) {
                    return false;
                }
            }
            return true;
        } else {
            // same length and compatible types
            if (argumentTypes.size() != this.mandatoryParamsCount) {
                return false;
            }
            return compatibleMandatoryParameters(argumentTypes);
        }
    }

    private boolean compatibleMandatoryParameters(List<Type> argumentTypes) {
        for (int i = 0; i < this.mandatoryParamsCount; i++) {
            if (!com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(argumentTypes.get(i), this.parameterTypes.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean match(NamedParameterTypes<Type> namedParameterTypes) {
        for (String argName: namedParameterTypes.getNames()) {
            Type argType = namedParameterTypes.getType(argName);
            boolean found = false;
            for(FormalParameter<Type> parameter: this.parameters) {
                if (parameter.getName().equals(argName)) {
                    found = true;
                    Type parType = parameter.getType();
                    if (parameter.isVarArg()) {
                        throw new DMNRuntimeException("Vararg parameters are not supported yet in named calls");
                    }
                    if (!com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(argType, parType)) {
                        return false;
                    }
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equivalentTo(Type other) {
        return this == other;
    }

    @Override
    public boolean conformsTo(Type other) {
        // “contravariant function argument type” and “covariant function return type”
        return other == FunctionType.ANY_FUNCTION ||
                other instanceof FunctionType
                && com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(this.returnType, ((FunctionType) other).returnType)
                && com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(((FunctionType) other).parameterTypes, this.parameterTypes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuiltinFunctionType that = (BuiltinFunctionType) o;

        if (this.parameters != null ? !this.parameters.equals(that.parameters) : that.parameters != null) return false;
        return this.returnType != null ? this.returnType.equals(that.returnType) : Type.isNull(that.returnType);
    }

    @Override
    public int hashCode() {
        int result = this.parameters != null ? this.parameters.hashCode() : 0;
        result = 31 * result + (this.returnType != null ? this.returnType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String types = this.parameters.stream().map(p -> p == null ? "null" : p.toString()).collect(Collectors.joining(", "));
        return String.format("BuiltinFunctionType(%s, %s)", types, this.returnType);
    }
}
