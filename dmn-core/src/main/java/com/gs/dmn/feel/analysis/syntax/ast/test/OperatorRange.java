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
package com.gs.dmn.feel.analysis.syntax.ast.test;

import com.gs.dmn.feel.analysis.semantics.type.BooleanType;
import com.gs.dmn.feel.analysis.semantics.type.RangeType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.NamedExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionInvocation;
import com.gs.dmn.runtime.DMNContext;
import com.gs.dmn.runtime.DMNRuntimeException;

public class OperatorRange extends Range {
    private final String operator;
    private final Expression endpoint;
    private final EndpointsRange endpointsRange;

    public OperatorRange(String operator, Expression endpoint) {
        this.operator = operator;
        this.endpoint = endpoint;
        if (operator == null || "=".equals(operator)) {
            this.endpointsRange = new EndpointsRange(false, endpoint, false, endpoint);
        } else if ("<".equals(operator)) {
            this.endpointsRange = new EndpointsRange(true, null, true, endpoint);
        } else if ("<=".equals(operator)) {
            this.endpointsRange = new EndpointsRange(true, null, false, endpoint);
        } else if (">".equals(operator)) {
            this.endpointsRange = new EndpointsRange(true, endpoint, true, null);
        } else if (">=".equals(operator)) {
            this.endpointsRange = new EndpointsRange(false, endpoint, true, null);
        } else {
            throw new DMNRuntimeException(String.format("Unexpected operator '%s'", operator));
        }
    }

    public Expression getEndpoint() {
        return this.endpoint;
    }

    public String getOperator() {
        return this.operator;
    }

    public EndpointsRange getEndpointsRange() {
        return this.endpointsRange;
    }

    @Override
    public void deriveType(DMNContext context) {
        if (context.isExpressionContext()) {
            this.setType(new RangeType(this.endpoint.getType()));
        } else {
            Type inputExpressionType = context.getInputExpressionType();
            this.setType(new RangeType(this.endpoint.getType()));
            if (this.endpoint instanceof FunctionInvocation) {
            } else if (this.endpoint instanceof NamedExpression) {
            } else {
                if (this.operator == null) {
                    checkType("=", inputExpressionType, this.endpoint.getType());
                } else {
                    checkType(this.operator, inputExpressionType, this.endpoint.getType());
                }
            }
        }
    }

    @Override
    public Object accept(Visitor visitor, DMNContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        return String.format("%s(%s,%s)", getClass().getSimpleName(), this.operator, this.endpoint.toString());
    }
}
