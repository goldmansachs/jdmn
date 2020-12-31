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
package com.gs.dmn.feel;

import com.gs.dmn.feel.analysis.semantics.type.Type;

import java.util.Objects;

class OperatorTableInputEntry {
    private final String operator;
    private final Type leftOperandType;
    private final Type rightOperandType;

    OperatorTableInputEntry(String operator, Type leftOperandType, Type rightOperandType) {
        this.operator = operator;
        this.leftOperandType = leftOperandType;
        this.rightOperandType = rightOperandType;
    }

    public String getOperator() {
        return operator;
    }

    public Type getLeftOperandType() {
        return leftOperandType;
    }

    public Type getRightOperandType() {
        return rightOperandType;
    }

    public boolean equivalentTo(OperatorTableInputEntry other) {
        return this.operator.equals(other.operator)
                && Type.equivalentTo(this.leftOperandType, other.leftOperandType)
                && Type.equivalentTo(this.rightOperandType, other.rightOperandType);
    }

    public boolean conformsTo(OperatorTableInputEntry other) {
        return this.operator.equals(other.operator)
                && Type.conformsTo(this.leftOperandType, other.leftOperandType)
                && Type.conformsTo(this.rightOperandType, other.rightOperandType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OperatorTableInputEntry that = (OperatorTableInputEntry) o;

        if (!Objects.equals(operator, that.operator)) {
            return false;
        }
        if (leftOperandType != null ? !Type.conformsTo(leftOperandType, that.leftOperandType) : that.leftOperandType != null) {
            return false;
        }
        return rightOperandType != null ? !Type.conformsTo(rightOperandType, that.rightOperandType) : that.rightOperandType == null;
    }

    @Override
    public int hashCode() {
        int result = operator != null ? operator.hashCode() : 0;
        result = 31 * result + (leftOperandType != null ? leftOperandType.hashCode() : 0);
        result = 31 * result + (rightOperandType != null ? rightOperandType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", operator, leftOperandType, rightOperandType);
    }
}
