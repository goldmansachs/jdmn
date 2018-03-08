/**
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

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.TupleType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PositiveUnaryTests extends UnaryTests {
    private List<PositiveUnaryTest> positiveUnaryTests = new ArrayList<>();

    public PositiveUnaryTests(List<PositiveUnaryTest> positiveUnaryTests) {
        if (positiveUnaryTests != null) {
            this.positiveUnaryTests = positiveUnaryTests;
        }
    }

    public List<PositiveUnaryTest> getPositiveUnaryTests() {
        return positiveUnaryTests;
    }

    @Override
    public void deriveType(Environment environment) {
        List<Type> types = getPositiveUnaryTests().stream().map(Expression::getType).collect(Collectors.toList());
        setType(new TupleType(types));
    }

    @Override
    public Object accept(Visitor visitor, FEELContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        String tests = positiveUnaryTests.stream().map(Object::toString).collect(Collectors.joining(","));
        return String.format("PositiveUnaryTests(%s)", tests);
    }
}
