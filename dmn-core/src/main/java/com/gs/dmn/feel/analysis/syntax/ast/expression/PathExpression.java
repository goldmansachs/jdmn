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

import com.gs.dmn.feel.analysis.syntax.ast.Visitor;

import java.util.Objects;

public class PathExpression<T> extends Expression<T> {
    private final Expression<T> source;
    private final String member;

    public PathExpression(Expression<T> source, String member) {
        this.source = source;
        this.member = member;
    }

    public String getMember() {
        return this.member;
    }

    public Expression<T> getSource() {
        return this.source;
    }

    public String getPath() {
        return getPath(this);
    }

    private String getPath(Expression<T> exp) {
        if (exp instanceof Name) {
            return ((Name<T>) exp).getName();
        } else if (exp instanceof QualifiedName) {
            return ((QualifiedName<T>) exp).getQualifiedName();
        } else if (exp instanceof PathExpression) {
            PathExpression<T> expPath = (PathExpression<T>) exp;
            return String.format("%s.%s", getPath(expPath.source), expPath.member);
        }
        return String.format("%s.%s", getPath(this.source), this.member);
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathExpression<?> that = (PathExpression<?>) o;
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
