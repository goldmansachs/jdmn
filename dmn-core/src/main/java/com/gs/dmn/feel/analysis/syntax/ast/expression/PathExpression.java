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
package com.gs.dmn.feel.analysis.syntax.ast.expression;

import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.runtime.DMNContext;

import java.util.Objects;

public class PathExpression extends Expression {
    private final Expression source;
    private final String member;

    public PathExpression(Expression source, String member) {
        this.source = source;
        this.member = member;
    }

    public String getMember() {
        return this.member;
    }

    public Expression getSource() {
        return this.source;
    }

    public String getPath() {
        return getPath(this);
    }

    private String getPath(Expression exp) {
        if (exp instanceof Name) {
            return ((Name) exp).getName();
        } else if (exp instanceof QualifiedName) {
            return ((QualifiedName) exp).getQualifiedName();
        } else if (exp instanceof PathExpression) {
            return String.format("%s.%s", getPath(this.source), this.member);
        }
        return String.format("%s.%s", getPath(this.source), this.member);
    }

    @Override
    public void deriveType(DMNContext context) {
        Type sourceType = this.source.getType();
        Type type = navigationType(sourceType, this.member);
        setType(type);
    }

    @Override
    public Object accept(Visitor visitor, DMNContext context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathExpression that = (PathExpression) o;
        return Objects.equals(source, that.source) && Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, member);
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", getClass().getSimpleName(), this.source.toString(), this.member);
    }
}
