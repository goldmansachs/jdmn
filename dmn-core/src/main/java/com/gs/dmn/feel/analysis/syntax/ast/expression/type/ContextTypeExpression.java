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
package com.gs.dmn.feel.analysis.syntax.ast.expression.type;

import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.runtime.Pair;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ContextTypeExpression<T, C> extends TypeExpression<T, C> {
    private final List<Pair<String, TypeExpression<T, C>>> members;

    public ContextTypeExpression(List<Pair<String, TypeExpression<T, C>>> contextType) {
        this.members = contextType;
    }

    public List<Pair<String, TypeExpression<T, C>>> getMembers() {
        return this.members;
    }

    @Override
    public Object accept(Visitor<T, C> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContextTypeExpression<?, ?> that = (ContextTypeExpression<?, ?>) o;
        return Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(members);
    }

    @Override
    public String toString() {
        String membersStr = this.members.stream().map(e -> String.format("%s: %s", e.getLeft(), e.getRight().toString())).collect(Collectors.joining(", "));
        return String.format("%s(%s)", getClass().getSimpleName(), membersStr);
    }
}
