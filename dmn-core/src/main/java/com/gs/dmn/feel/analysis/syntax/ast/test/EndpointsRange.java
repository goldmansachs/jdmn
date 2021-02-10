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

import com.gs.dmn.feel.analysis.semantics.type.RangeType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.runtime.DMNContext;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.Objects;

public class EndpointsRange extends Range {
    private final boolean openStart;
    private final Expression start;
    private final boolean openEnd;
    private final Expression end;

    public EndpointsRange(boolean isOpenStart, Expression start, boolean isOpenEnd, Expression end) {
        this.openStart = isOpenStart;
        this.start = start;
        this.openEnd = isOpenEnd;
        this.end = end;
    }

    public Expression getStart() {
        return this.start;
    }

    public Expression getEnd() {
        return this.end;
    }

    public boolean isOpenStart() {
        return this.openStart;
    }

    public boolean isOpenEnd() {
        return this.openEnd;
    }

    @Override
    public void deriveType(DMNContext context) {
        if (this.start == null && this.end == null) {
            throw new DMNRuntimeException(String.format("Illegal range, both endpoints are null in context of element '%s'", context.getElementName()));
        } else if (this.start != null) {
            Type startType = this.start.getType();
            setType(new RangeType(startType));
        } else {
            Type endType = this.end.getType();
            setType(new RangeType(endType));
        }
    }

    @Override
    public Object accept(Visitor visitor, DMNContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndpointsRange that = (EndpointsRange) o;
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
