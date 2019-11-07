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

public interface TestLabVisitor {
    // Tests
    Object visit(TestLab element, Object... params);
    Object visit(InputParameterDefinition element, Object... params);
    Object visit(OutputParameterDefinition element, Object... params);
    Object visit(TestCase element, Object... params);

    // Expressions
    Object visit(NumberLiteral element, Object... params);
    Object visit(StringLiteral element, Object... params);
    Object visit(BooleanLiteral element, Object... params);
    Object visit(DateLiteral element, Object... params);
    Object visit(TimeLiteral element, Object... params);
    Object visit(DatetimeLiteral element, Object... params);
    Object visit(EnumerationLiteral element, Object... params);
    Object visit(ListExpression element, Object... params);
    Object visit(ComplexExpression element, Object... params);

    // Other
    Object visit(Slot element, Object... params);
}
