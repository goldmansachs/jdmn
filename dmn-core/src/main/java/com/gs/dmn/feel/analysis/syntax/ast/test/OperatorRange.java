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

import java.util.Objects;

public class OperatorRange<T> extends Range<T> {
    private final String operator;
    private final Expression<T> endpoint;

    public OperatorRange(String operator, Expression<T> endpoint) {
        this.operator = operator;
        this.endpoint = endpoint;
    }

    public Expression<T> getEndpoint() {
        return this.endpoint;
    }

    @Override
    public T getEndpointType() {
        return this.endpoint.getType();
    }

    public String getOperator() {
        return this.operator;
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperatorRange<?> that = (OperatorRange<?>) o;
        return Objects.equals(operator, that.operator) && Objects.equals(endpoint, that.endpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, endpoint);
    }

    @Override
    public String toString() {
        return String.format("%s(%s,%s)", getClass().getSimpleName(), this.operator, this.endpoint.toString());
    }
}
