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

import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.Objects;

public class OperatorRange<C> extends Range<C> {
    private final String operator;
    private final Expression<C> endpoint;
    private final EndpointsRange<C> endpointsRange;

    public OperatorRange(String operator, Expression<C> endpoint) {
        this.operator = operator;
        this.endpoint = endpoint;
        if (operator == null || "=".equals(operator)) {
            this.endpointsRange = new EndpointsRange<>(false, endpoint, false, endpoint);
        } else if ("<".equals(operator)) {
            this.endpointsRange = new EndpointsRange<>(true, null, true, endpoint);
        } else if ("<=".equals(operator)) {
            this.endpointsRange = new EndpointsRange<>(true, null, false, endpoint);
        } else if (">".equals(operator)) {
            this.endpointsRange = new EndpointsRange<>(true, endpoint, true, null);
        } else if (">=".equals(operator)) {
            this.endpointsRange = new EndpointsRange<>(false, endpoint, true, null);
        } else {
            throw new DMNRuntimeException(String.format("Unexpected operator '%s'", operator));
        }
    }

    public Expression<C> getEndpoint() {
        return this.endpoint;
    }

    public String getOperator() {
        return this.operator;
    }

    public EndpointsRange<C> getEndpointsRange() {
        return this.endpointsRange;
    }

    @Override
    public Object accept(Visitor<C> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperatorRange<?> that = (OperatorRange<?>) o;
        return Objects.equals(operator, that.operator) && Objects.equals(endpoint, that.endpoint) && Objects.equals(endpointsRange, that.endpointsRange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, endpoint, endpointsRange);
    }

    @Override
    public String toString() {
        return String.format("%s(%s,%s)", getClass().getSimpleName(), this.operator, this.endpoint.toString());
    }
}
