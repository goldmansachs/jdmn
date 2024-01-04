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

public class EndpointsRange<T> extends Range<T> {
    private final boolean openStart;
    private final Expression<T> start;
    private final boolean openEnd;
    private final Expression<T> end;

    public EndpointsRange(boolean isOpenStart, Expression<T> start, boolean isOpenEnd, Expression<T> end) {
        this.openStart = isOpenStart;
        this.start = start;
        this.openEnd = isOpenEnd;
        this.end = end;
    }

    public Expression<T> getStart() {
        return this.start;
    }

    public Expression<T> getEnd() {
        return this.end;
    }

    public boolean isOpenStart() {
        return this.openStart;
    }

    public boolean isOpenEnd() {
        return this.openEnd;
    }

    @Override
    public T getEndpointType() {
        T endpointType;
        if (start != null) {
            endpointType = start.getType();
        } else {
            endpointType = end.getType();
        }
        return endpointType;
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndpointsRange<?> that = (EndpointsRange<?>) o;
        return openStart == that.openStart && openEnd == that.openEnd && Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(openStart, start, openEnd, end);
    }

    @Override
    public String toString() {
        return String.format("%s(%s,%s,%s,%s)", getClass().getSimpleName(), this.openStart, this.start.toString(), this.openEnd, this.end.toString());
    }
}
