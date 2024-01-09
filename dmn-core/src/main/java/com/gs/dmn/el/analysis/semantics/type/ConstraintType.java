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
package com.gs.dmn.el.analysis.semantics.type;

import com.gs.dmn.ast.TUnaryTests;

import java.util.Objects;

public class ConstraintType implements Type {
    private final Type type;
    private final TUnaryTests allowedValues;
    private final String unaryTests;

    public ConstraintType(Type type, TUnaryTests allowedValues) {
        this.type = type;
        this.allowedValues = allowedValues;
        this.unaryTests = allowedValues.getText();
    }

    public Type getType() {
        return this.type;
    }

    public String getUnaryTests() {
        return this.unaryTests;
    }

    @Override
    public boolean equivalentTo(Type other) {
        return this.type.equivalentTo(other);
    }

    @Override
    public boolean conformsTo(Type other) {
        if (other instanceof ConstraintType) {
            return this.type.conformsTo(((ConstraintType) other).getType());
        } else {
            return this.type.conformsTo(other);
        }
    }

    @Override
    public boolean isFullySpecified() {
        return this.type.isFullySpecified();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstraintType that = (ConstraintType) o;

        if (!Objects.equals(this.type, that.type)) return false;
        return Objects.equals(this.allowedValues, that.allowedValues);
    }

    public String serialize() {
        return type.serialize();
    }

    @Override
    public int hashCode() {
        int result = this.type != null ? this.type.hashCode() : 0;
        result = 31 * result + (this.allowedValues != null ? this.allowedValues.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("ConstraintType(%s, %s)", this.type, getUnaryTests());
    }
}
