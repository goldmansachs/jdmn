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

import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterConversions;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterTypes;
import com.gs.dmn.runtime.Pair;

import java.util.List;

public class BuiltinOverloadedFunctionType extends FunctionType {
    private final List<Declaration> declarations;

    public BuiltinOverloadedFunctionType(List<Declaration> declarations) {
        super(null, null);
        this.declarations = declarations;
    }

    @Override
    public boolean equivalentTo(Type other) {
        for (Declaration d: this.declarations) {
            Type type = d.getType();
            if (type.equivalentTo(other)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean conformsTo(Type other) {
        for (Declaration d: this.declarations) {
            Type type = d.getType();
            if (type.conformsTo(other)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<FormalParameter<Type>> getParameters() {
        throw new SemanticError("Not supported yet");
    }

    @Override
    public List<Type> getParameterTypes() {
        throw new SemanticError("Not supported yet");
    }

    @Override
    public Type getReturnType() {
        throw new SemanticError("Not supported yet");
    }

    @Override
    public void setReturnType(Type returnType) {
        throw new SemanticError("Not supported yet");
    }

    @Override
    public boolean isFullySpecified() {
        throw new SemanticError("Not supported yet");
    }

    @Override
    public boolean match(ParameterTypes<Type> parameterTypes) {
        throw new SemanticError("Not supported yet");
    }

    @Override
    protected List<Pair<ParameterTypes<Type>, ParameterConversions<Type>>> matchCandidates(List<Type> argumentTypes) {
        throw new SemanticError("Not supported yet");
    }

    @Override
    public String toString() {
        return String.format("%s%s", this.getClass().getSimpleName(), declarations);
    }
}
