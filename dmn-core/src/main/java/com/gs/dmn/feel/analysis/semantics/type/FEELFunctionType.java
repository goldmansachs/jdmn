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
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterConversions;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterTypes;
import com.gs.dmn.runtime.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FEELFunctionType extends FunctionType {
    private final FunctionDefinition<Type> functionDefinition;
    private final boolean external;

    public FEELFunctionType(List<FormalParameter<Type>> parameters, Type returnType, boolean external) {
        this(parameters, returnType, external, null);
    }

    public FEELFunctionType(List<FormalParameter<Type>> parameters, Type returnType, boolean external, FunctionDefinition<Type> functionDefinition) {
        super(parameters, returnType);
        this.functionDefinition = functionDefinition;
        this.external = external;
    }

    public FunctionDefinition<Type> getFunctionDefinition() {
        return this.functionDefinition;
    }

    public boolean isExternal() {
        return this.external;
    }

    @Override
    public boolean equivalentTo(Type other) {
        return other instanceof FEELFunctionType
                && com.gs.dmn.el.analysis.semantics.type.Type.equivalentTo(this.returnType, ((FunctionType) other).returnType)
                && com.gs.dmn.el.analysis.semantics.type.Type.equivalentTo(this.parameterTypes, ((FunctionType) other).parameterTypes);
    }

    @Override
    public boolean conformsTo(Type other) {
        // “contravariant function argument type” and “covariant function return type”
        return other == FunctionType.FUNCTION ||
                other instanceof FunctionType
                && com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(this.returnType, ((FunctionType) other).returnType)
                && com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(((FunctionType) other).parameterTypes, this.parameterTypes);
    }

    @Override
    public boolean match(ParameterTypes<Type> parameterTypes) {
        List<FormalParameter<Type>> parameters = getParameters();
        if (parameters.size() != parameterTypes.size()) {
            return false;
        }
        return compatible(parameterTypes, parameters);
    }

    @Override
    protected List<Pair<ParameterTypes<Type>, ParameterConversions<Type>>> matchCandidates(List<Type> argumentTypes) {
        // check size constraint
        if (argumentTypes.size() != this.parameterTypes.size()) {
            return new ArrayList<>();
        }
        // calculate candidates
        return calculateCandidates(this.parameterTypes, argumentTypes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FEELFunctionType that = (FEELFunctionType) o;

        if (this.parameters != null ? !this.parameters.equals(that.parameters) : that.parameters != null)
            return false;
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
        return String.format("FEELFunctionType(%s, %s, %s)", types, this.returnType, this.external);
    }
}
