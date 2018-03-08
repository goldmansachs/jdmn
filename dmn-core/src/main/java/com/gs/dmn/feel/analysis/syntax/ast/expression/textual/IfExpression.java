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
package com.gs.dmn.feel.analysis.syntax.ast.expression.textual;

import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.NullType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;

public class IfExpression extends Expression {
    private final Expression condition;
    private final Expression thenExpression;
    private final Expression elseExpression;

    public IfExpression(Expression condition, Expression thenExpression, Expression elseExpression) {
        this.condition = condition;
        this.thenExpression = thenExpression;
        this.elseExpression = elseExpression;
    }

    public Expression getCondition() {
        return condition;
    }

    public Expression getThenExpression() {
        return thenExpression;
    }

    public Expression getElseExpression() {
        return elseExpression;
    }

    @Override
    public void deriveType(Environment environment) {
        Type conditionType = condition.getType();
        Type thenType = thenExpression.getType();
        Type elseType = elseExpression.getType();
        if (conditionType != BOOLEAN) {
            throw new SemanticError(this, String.format("Condition type must be boolean. Found '%s' instead.", conditionType));
        }
        if (thenType == NullType.NULL && elseType == NullType.NULL) {
            throw new SemanticError(this, String.format("Types of then and else branches are incompatible. Found '%s' and '%s'.", thenType, elseType));
        } else if (thenType == NullType.NULL) {
            setType(elseType);
        } else if (elseType == NullType.NULL) {
            setType(thenType);
        } else {
            if (thenType.conformsTo(elseType)) {
                setType(elseType);
            } else if (elseType.conformsTo(thenType)) {
                setType(thenType);
            } else {
                throw new SemanticError(this, String.format("Types of then and else branches are incompatible. Found '%s' and '%s'.", thenType, elseType));
            }
        }
    }

    @Override
    public Object accept(Visitor visitor, FEELContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        return String.format("IfExpression(%s, %s, %s)", condition.toString(), thenExpression.toString(), elseExpression.toString());
    }
}
