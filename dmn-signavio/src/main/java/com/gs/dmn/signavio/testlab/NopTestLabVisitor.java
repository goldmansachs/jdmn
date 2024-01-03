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

public class NopTestLabVisitor<C> implements Visitor<TestLabElement, C> {
    @Override
    public TestLabElement visit(TestLab element, C context) {
       return element;
    }

    @Override
    public TestLabElement visit(InputParameterDefinition element, C context) {
       return element;
    }

    @Override
    public TestLabElement visit(OutputParameterDefinition element, C context) {
       return element;
    }

    @Override
    public TestLabElement visit(TestCase element, C context) {
       return element;
    }

    @Override
    public TestLabElement visit(NumberLiteral element, C context) {
       return element;
    }

    @Override
    public TestLabElement visit(StringLiteral element, C context) {
       return element;
    }

    @Override
    public TestLabElement visit(BooleanLiteral element, C context) {
       return element;
    }

    @Override
    public TestLabElement visit(DateLiteral element, C context) {
       return element;
    }

    @Override
    public TestLabElement visit(TimeLiteral element, C context) {
       return element;
    }

    @Override
    public TestLabElement visit(DatetimeLiteral element, C context) {
       return element;
    }

    @Override
    public TestLabElement visit(EnumerationLiteral element, C context) {
       return element;
    }

    @Override
    public TestLabElement visit(ListExpression element, C context) {
       return element;
    }

    @Override
    public TestLabElement visit(ComplexExpression element, C context) {
       return element;
    }

    @Override
    public TestLabElement visit(Slot element, C context) {
       return element;
    }
}
