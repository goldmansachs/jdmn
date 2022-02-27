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

import java.util.Objects;

public class Conversion<T> {
    private final ConversionKind kind;
    private final T targetType;

    public Conversion(ConversionKind kind, T elementType) {
        this.kind = kind;
        this.targetType = elementType;
    }

    public ConversionKind getKind() {
        return this.kind;
    }

    public T getTargetType() {
        return this.targetType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversion<?> that = (Conversion<?>) o;
        return this.kind == that.kind &&
                Objects.equals(this.targetType, that.targetType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.kind, this.targetType);
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", getClass().getSimpleName(), this.kind, this.targetType);
    }
}
