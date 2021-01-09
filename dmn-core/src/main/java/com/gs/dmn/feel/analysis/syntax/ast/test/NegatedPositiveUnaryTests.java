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

import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.type.BooleanType;
import com.gs.dmn.feel.analysis.semantics.type.RangeType;
import com.gs.dmn.feel.analysis.semantics.type.TupleType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.runtime.DMNContext;

public class NegatedPositiveUnaryTests extends UnaryTests {
    private final PositiveUnaryTests positiveUnaryTests;

    public NegatedPositiveUnaryTests(PositiveUnaryTests positiveUnaryTests) {
        this.positiveUnaryTests = positiveUnaryTests;
    }

    public PositiveUnaryTests getPositiveUnaryTests() {
        return positiveUnaryTests;
    }

    @Override
    public void deriveType(DMNContext context) {
        Type type = positiveUnaryTests.getType();
        setType(type);
        if (type instanceof TupleType) {
            for (Type child : ((TupleType) type).getTypes()) {
                if (child == BooleanType.BOOLEAN || child instanceof RangeType) {
                } else {
                    throw new SemanticError(this, String.format("Operator '%s' cannot be applied to '%s'", "not", child));
                }
            }
        }
    }

    @Override
    public Object accept(Visitor visitor, DMNContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        return String.format("NegatedUnaryTests(%s)", positiveUnaryTests.toString());
    }
}
