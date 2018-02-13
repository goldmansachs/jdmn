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

import java.util.List;
import java.util.stream.Collectors;

public class DMNFunctionType extends FunctionType {
    public DMNFunctionType(List<FormalParameter> parameters, Type returnType) {
        super(parameters, returnType);
    }

    @Override
    public boolean equivalentTo(Type other) {
        return other instanceof DMNFunctionType
                && returnType.equivalentTo(((DMNFunctionType) other).returnType)
                && equalNames(this.parameterTypes, ((DMNFunctionType) other).parameterTypes);
    }

    @Override
    public boolean match(Signature signature) {
        List<FormalParameter> parameters = getParameters();
        if (parameters.size() != signature.size()) {
            return false;
        }
        return signature.compatible(parameters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DMNFunctionType that = (DMNFunctionType) o;

        if (parameters != null ? !parameters.equals(that.parameters) : that.parameters != null)
            return false;
        return returnType != null ? returnType.equals(that.returnType) : that.returnType == null;

    }

    @Override
    public int hashCode() {
        int result = parameters != null ? parameters.hashCode() : 0;
        result = 31 * result + (returnType != null ? returnType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String types = parameters.stream().map(p -> p == null ? "null" : p.toString()).collect(Collectors.joining(", "));
        return String.format("DMNFunctionType(%s, %s)", types, returnType);
    }

    private boolean equalNames(List<Type> list1, List<Type> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list1.size(); i++) {
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }
        return true;
    }
}
