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

import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.Signature;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class FunctionType extends Type {
    protected final List<FormalParameter> parameters = new ArrayList<>();
    protected final List<Type> parameterTypes = new ArrayList<>();
    protected Type returnType;

    public FunctionType(List<FormalParameter> parameters, Type returnType) {
        this.returnType = returnType;
        if (parameters != null) {
            this.parameters.addAll(parameters);
            this.parameterTypes.addAll(parameters.stream().map(FormalParameter::getType).collect(Collectors.toList()));
        }
    }

    public List<FormalParameter> getParameters() {
        return parameters;
    }

    public List<Type> getParameterTypes() {
        return parameterTypes;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public abstract boolean match(Signature signature);

    @Override
    public boolean isValid() {
        if (returnType == null) {
            return false;
        }
        return parameterTypes.stream().allMatch(Type::isValid)
                && returnType.isValid();
    }

    public boolean isStaticTyped() {
        return parameters.stream().allMatch(p -> p.getType() != null && p.getType() != AnyType.ANY);
    }
}
