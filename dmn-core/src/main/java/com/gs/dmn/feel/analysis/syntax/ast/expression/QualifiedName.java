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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QualifiedName<T> extends NamedExpression<T> {
    private List<String> names = new ArrayList<>();

    public QualifiedName(List<String> names) {
        if (names != null) {
            this.names = names;
        }
    }

    public List<String> getNames() {
        return this.names;
    }

    public String getQualifiedName() {
        return String.join(".", this.names);
    }

    @Override
    public String getName() {
        return getQualifiedName();
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QualifiedName<?> that = (QualifiedName<?>) o;
        return Objects.equals(names, that.names);
    }

    @Override
    public int hashCode() {
        return Objects.hash(names);
    }

    @Override
    public String toString() {
        return "%s(%s, %d)".formatted(getClass().getSimpleName(), String.join(".", this.names), this.names.size());
    }
}
