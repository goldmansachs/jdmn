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

public interface Visitor<R, C> {
    // Tests
    R visit(TestLab element, C context);
    R visit(InputParameterDefinition element, C context);
    R visit(OutputParameterDefinition element, C context);
    R visit(TestCase element, C context);

    // Expressions
    R visit(NumberLiteral element, C context);
    R visit(StringLiteral element, C context);
    R visit(BooleanLiteral element, C context);
    R visit(DateLiteral element, C context);
    R visit(TimeLiteral element, C context);
    R visit(DatetimeLiteral element, C context);
    R visit(EnumerationLiteral element, C context);
    R visit(ListExpression element, C context);
    R visit(ComplexExpression element, C context);

    // Other
    R visit(Slot element, C context);
}
