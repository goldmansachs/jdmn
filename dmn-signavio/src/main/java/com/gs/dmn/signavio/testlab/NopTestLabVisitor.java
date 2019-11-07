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
package com.gs.dmn.signavio.testlab;

import com.gs.dmn.signavio.testlab.expression.*;

public class NopTestLabVisitor implements TestLabVisitor {
    @Override
    public Object visit(TestLab element, Object... params) {
        return element;
    }

    @Override
    public Object visit(InputParameterDefinition element, Object... params) {
        return element;
    }

    @Override
    public Object visit(OutputParameterDefinition element, Object... params) {
        return element;
    }

    @Override
    public Object visit(TestCase element, Object... params) {
        return element;
    }

    @Override
    public Object visit(NumberLiteral element, Object... params) {
        return element;
    }

    @Override
    public Object visit(StringLiteral element, Object... params) {
        return element;
    }

    @Override
    public Object visit(BooleanLiteral element, Object... params) {
        return element;
    }

    @Override
    public Object visit(DateLiteral element, Object... params) {
        return element;
    }

    @Override
    public Object visit(TimeLiteral element, Object... params) {
        return element;
    }

    @Override
    public Object visit(DatetimeLiteral element, Object... params) {
        return element;
    }

    @Override
    public Object visit(EnumerationLiteral element, Object... params) {
        return element;
    }

    @Override
    public Object visit(ListExpression element, Object... params) {
        return element;
    }

    @Override
    public Object visit(ComplexExpression element, Object... params) {
        return element;
    }

    @Override
    public Object visit(Slot element, Object... params) {
        return element;
    }
}
