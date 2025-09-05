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
package com.gs.dmn.feel.lib.reference;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FunctionType implements Type {
    protected final List<Type> parameterTypes;
    protected final Type returnType;

    public FunctionType(List<Type> parameterTypes, Type returnType) {
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    public List<Type> getParameterTypes() {
        return parameterTypes;
    }

    public Type getReturnType() {
        return returnType;
    }

    @Override
    public String getExpressionType() {
        String argsStr = parameterTypes.stream().map(e -> String.format("%s", e)).collect(Collectors.joining(", "));
        return String.format("function<%s> -> %s", argsStr, returnType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionType that = (FunctionType) o;
        return Objects.equals(parameterTypes, that.parameterTypes) && Objects.equals(returnType, that.returnType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameterTypes, returnType);
    }

    @Override
    public String toString() {
        String argsStr = parameterTypes.stream().map(e -> String.format("%s", e)).collect(Collectors.joining(", "));
        return String.format("function<%s> -> %s", argsStr, returnType);
    }
}
