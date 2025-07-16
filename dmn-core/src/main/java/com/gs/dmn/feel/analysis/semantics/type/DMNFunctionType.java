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

import com.gs.dmn.ast.TBusinessKnowledgeModel;
import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TFunctionDefinition;
import com.gs.dmn.ast.TFunctionKind;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterConversions;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterTypes;
import com.gs.dmn.runtime.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DMNFunctionType extends FunctionType implements com.gs.dmn.el.analysis.semantics.type.DMNFunctionType {
    private final TDRGElement drgElement;
    private final TFunctionDefinition functionDefinition;

    public DMNFunctionType(List<FormalParameter<Type>> formalParameters, Type outputType) {
        this(formalParameters, outputType, null);
    }

    public DMNFunctionType(List<FormalParameter<Type>> parameters, Type returnType, TDRGElement drgElement) {
        this(parameters, returnType, drgElement, drgElement instanceof TBusinessKnowledgeModel ? ((TBusinessKnowledgeModel) drgElement).getEncapsulatedLogic() : null);
    }

    public DMNFunctionType(List<FormalParameter<Type>> parameters, Type returnType, TDRGElement drgElement, TFunctionDefinition functionDefinition) {
        super(parameters, returnType);
        this.drgElement = drgElement;
        this.functionDefinition = functionDefinition;
    }

    @Override
    public TDRGElement getDRGElement() {
        return this.drgElement;
    }

    public DMNFunctionType attachElement(TDRGElement element) {
        return new DMNFunctionType(this.getParameters(), this.getReturnType(), element);
    }

    @Override
    public TFunctionKind getKind() {
        return this.functionDefinition == null ? null : this.functionDefinition.getKind();
    }

    @Override
    public boolean equivalentTo(Type other) {
        return other instanceof DMNFunctionType
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

        DMNFunctionType that = (DMNFunctionType) o;

        if (this.parameters != null ? !this.parameters.equals(that.parameters) : that.parameters != null) {
            return false;
        }
        return Objects.equals(this.returnType, that.returnType);
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
        return String.format("%s(%s, %s)", getClass().getSimpleName(), types, this.returnType);
    }
}
