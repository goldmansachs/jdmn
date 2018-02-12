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
package com.gs.dmn.feel.analysis.syntax.ast.expression.comparison;

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.BooleanType;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

public class BetweenExpression extends Comparison {
    private final Expression value;
    private final Expression leftEndpoint;
    private final Expression rightEndpoint;

    public BetweenExpression(Expression value, Expression leftEndpoint, Expression rightEndpoint) {
        this.value = value;
        this.leftEndpoint = leftEndpoint;
        this.rightEndpoint = rightEndpoint;
    }

    public Expression getValue() {
        return value;
    }

    public Expression getLeftEndpoint() {
        return leftEndpoint;
    }

    public Expression getRightEndpoint() {
        return rightEndpoint;
    }

    @Override
    public void deriveType(Environment environment) {
        setType(BooleanType.BOOLEAN);
        checkType(">=", value.getType(), leftEndpoint.getType());
        checkType("<=", value.getType(), rightEndpoint.getType());
    }

    @Override
    public Object accept(Visitor visitor, FEELContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        return String.format("BetweenExpression(%s, %s, %s)", value.toString(), leftEndpoint.toString(), rightEndpoint.toString());
    }

}
