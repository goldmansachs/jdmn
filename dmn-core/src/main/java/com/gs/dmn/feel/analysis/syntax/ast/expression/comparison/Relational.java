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
package com.gs.dmn.feel.analysis.syntax.ast.expression.comparison;

import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.runtime.DMNContext;

import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;

public class Relational extends Comparison {
    private final String operator;
    private final Expression leftOperand;
    private final Expression rightOperand;

    public Relational(String operator, Expression leftOperand, Expression rightOperand) {
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
        setType(BOOLEAN);
        checkType(this.operator, this.leftOperand.getType(), this.rightOperand.getType());
    }

    @Override
    public Object accept(Visitor visitor, DMNContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        return String.format("%s(%s,%s,%s)", getClass().getSimpleName(), getOperator(), getLeftOperand(), getRightOperand());
    }

}
 
