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
package com.gs.dmn.feel.analysis.syntax.ast.expression.logic;

import com.gs.dmn.feel.analysis.semantics.type.BooleanType;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.runtime.DMNContext;

public abstract class LogicExpression extends Expression {
    private final String operator;
    private final Expression leftOperand;
    private final Expression rightOperand;

    protected LogicExpression(String operator, Expression leftOperand, Expression rightOperand) {
        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public String getOperator() {
        return this.operator;
    }

    public Expression getLeftOperand() {
        return this.leftOperand;
    }

    public Expression getRightOperand() {
        return this.rightOperand;
    }

    @Override
    public void deriveType(DMNContext context) {
        // Not need to check the operand types. or, and, not are total functions
        setType(BooleanType.BOOLEAN);
    }
    @Override
    public String toString() {
        return String.format("%s(%s,%s)", getClass().getSimpleName(), this.leftOperand, this.rightOperand);
    }
}
