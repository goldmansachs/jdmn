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
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.ListLiteral;

import java.util.Objects;

public class ListTest<T> extends SimplePositiveUnaryTest<T> {
    private final ListLiteral<T> listLiteral;
    private ListLiteral<T> optimizedListLiteral;

    public ListTest(ListLiteral<T> listLiteral) {
        this.listLiteral = listLiteral;
    }

    public ListLiteral<T> getListLiteral() {
        return this.listLiteral;
    }

    public void setOptimizedListLiteral(ListLiteral<T> optimizedListLiteral) {
        this.optimizedListLiteral = optimizedListLiteral;
    }

    public ListLiteral<T> getOptimizedListLiteral() {
        return optimizedListLiteral;
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListTest<?> listTest = (ListTest<?>) o;
        return Objects.equals(listLiteral, listTest.listLiteral);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listLiteral);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), this.listLiteral);
    }


}
