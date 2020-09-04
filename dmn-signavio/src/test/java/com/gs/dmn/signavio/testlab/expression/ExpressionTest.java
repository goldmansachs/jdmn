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
package com.gs.dmn.signavio.testlab.expression;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExpressionTest {
    @Test
    public void testToFEELExpression() {
        assertEquals("null", new NumberLiteral().toFEELExpression());
        assertEquals("null", new BooleanLiteral().toFEELExpression());
        assertEquals("null", new StringLiteral().toFEELExpression());
        assertEquals("null", new DateLiteral().toFEELExpression());
        assertEquals("null", new TimeLiteral().toFEELExpression());
        assertEquals("null", new DatetimeLiteral().toFEELExpression());
        assertEquals("null", new EnumerationLiteral().toFEELExpression());
        assertEquals("null", ((Expression) new ComplexExpression()).toFEELExpression());
        assertEquals("null", ((Expression) new ListExpression()).toFEELExpression());

        assertEquals("null", ((Expression) new ComplexExpression()).toFEELExpression());
        assertEquals("null", ((Expression) new ListExpression()).toFEELExpression());

        assertEquals("123", new NumberLiteral("123").toFEELExpression());
        assertEquals("true", new BooleanLiteral(true).toFEELExpression());
        assertEquals("\"abc\"", new StringLiteral("abc").toFEELExpression());
        assertEquals("date(\"2002-01-02\")", new DateLiteral("2002-01-02").toFEELExpression());
        assertEquals("time(\"13:03\")", new TimeLiteral("13:03").toFEELExpression());
        assertEquals("date and time(\"2002-01-02T13:03\")", new DatetimeLiteral("2002-01-02T13:03").toFEELExpression());
        assertEquals("\"e1\"", new EnumerationLiteral("e1", "1").toFEELExpression());

        assertEquals("{null : null, null : null}", ((Expression) new ComplexExpression(Arrays.asList(new Slot(), new Slot()))).toFEELExpression());
        assertEquals("[null]", ((Expression) new ListExpression(Arrays.asList(new NumberLiteral()))).toFEELExpression());
    }

    @Test
    public void testEquals() {
        List<Expression> expressionList1 = makeExpressions();
        List<Expression> expressionList2 = makeExpressions();

        assertEquals(expressionList1, expressionList2);
        assertEquals(new ComplexExpression(), new ComplexExpression());
        assertEquals(new ListExpression(), new ListExpression());
        assertEquals(new Slot(), new Slot());
        assertEquals(new Slot(null, null, null, null), new Slot());
    }

    @Test
    public void testToString() {
        assertEquals("NumberLiteral(null)", new NumberLiteral().toString());
        assertEquals("BooleanLiteral(null)", new BooleanLiteral().toString());
        assertEquals("StringLiteral(null)", new StringLiteral().toString());
        assertEquals("DateLiteral(null)", new DateLiteral().toString());
        assertEquals("TimeLiteral(null)", new TimeLiteral().toString());
        assertEquals("DatetimeLiteral(null)", new DatetimeLiteral().toString());
        assertEquals("EnumerationLiteral(null, null)", new EnumerationLiteral().toString());

        assertEquals("ComplexExpression(null)", ((Expression) new ComplexExpression()).toString());
        assertEquals("ListExpression(null)", ((Expression) new ListExpression()).toString());

        assertEquals("NumberLiteral(123)", new NumberLiteral("123").toString());
        assertEquals("BooleanLiteral(true)", new BooleanLiteral(true).toString());
        assertEquals("StringLiteral(abc)", new StringLiteral("abc").toString());
        assertEquals("DateLiteral(2002-01-02)", new DateLiteral("2002-01-02").toString());
        assertEquals("TimeLiteral(13:03)", new TimeLiteral("13:03").toString());
        assertEquals("DatetimeLiteral(2002-01-02T13:03)", new DatetimeLiteral("2002-01-02T13:03").toString());
        assertEquals("EnumerationLiteral(e1, 1)", new EnumerationLiteral("e1", "1").toString());

        assertEquals("ComplexExpression([Slot(null, null, null, null), Slot(null, null, null, null)])", ((Expression) new ComplexExpression(Arrays.asList(new Slot(), new Slot()))).toString());
        assertEquals("ListExpression([NumberLiteral(null)])", ((Expression) new ListExpression(Arrays.asList(new NumberLiteral()))).toString());
    }

    private List<Expression> makeExpressions() {
        List<Expression> expressions = new ArrayList<>();
        expressions.add(new NumberLiteral());
        expressions.add(new BooleanLiteral());
        expressions.add(new StringLiteral());
        expressions.add(new DateLiteral());
        expressions.add(new TimeLiteral());
        expressions.add(new DatetimeLiteral());
        expressions.add(new EnumerationLiteral());

        expressions.add(new ComplexExpression());
        expressions.add(new ListExpression());
        return expressions;
    }

}