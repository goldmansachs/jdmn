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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PositiveUnaryTests<T, C> extends UnaryTests<T, C> {
    private List<PositiveUnaryTest<T, C>> positiveUnaryTests = new ArrayList<>();

    public PositiveUnaryTests(List<PositiveUnaryTest<T, C>> positiveUnaryTests) {
        if (positiveUnaryTests != null) {
            this.positiveUnaryTests = positiveUnaryTests;
        }
    }

    public List<PositiveUnaryTest<T, C>> getPositiveUnaryTests() {
        return this.positiveUnaryTests;
    }

    @Override
    public Object accept(Visitor<T, C> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositiveUnaryTests<?, ?> that = (PositiveUnaryTests<?, ?>) o;
        return Objects.equals(positiveUnaryTests, that.positiveUnaryTests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positiveUnaryTests);
    }

    @Override
    public String toString() {
        String tests = this.positiveUnaryTests.stream().map(Object::toString).collect(Collectors.joining(","));
        return String.format("%s(%s)", getClass().getSimpleName(), tests);
    }
}
