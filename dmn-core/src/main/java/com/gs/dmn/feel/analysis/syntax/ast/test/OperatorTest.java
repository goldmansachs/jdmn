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
package com.gs.dmn.feel.analysis.syntax.ast.test;

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.BooleanType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.NamedExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionInvocation;
import com.gs.dmn.runtime.DMNRuntimeException;

public class OperatorTest extends SimplePositiveUnaryTest {
    private final String operator;
    private final Expression endpoint;

    public OperatorTest(String operator, Expression endpoint) {
        this.operator = operator;
        this.endpoint = endpoint;
    }

    public Expression getEndpoint() {
        return endpoint;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    // TODO refactor types for Tests
    public void deriveType(Environment environment) {
        setType(BooleanType.BOOLEAN);
        Type inputExpressionType = environment.getInputExpressionType();
        if (inputExpressionType == null) {
            throw new DMNRuntimeException(String.format("Missing input expression type when evaluating '%s'", endpoint));
        }
        if (inputExpressionType.conformsTo(endpoint.getType())) {
            return;
        }
        if (endpoint instanceof FunctionInvocation) {
        } else if (endpoint instanceof NamedExpression) {
        } else {
            if (operator == null) {
                checkType("=", inputExpressionType, endpoint.getType());
            } else {
                checkType(operator, inputExpressionType, endpoint.getType());
            }
        }
    }

    @Override
    public Object accept(Visitor visitor, FEELContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        return String.format("OperatorTest(%s,%s)", operator, endpoint.toString());
    }
}
