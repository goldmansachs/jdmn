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
package com.gs.dmn.signavio.feel.lib;

import com.gs.dmn.runtime.Context;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public abstract class BaseSignavioLibTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends CommonLibFunctionsTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    @Override
    protected abstract SignavioLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> getLib();

    //
    // Arithmetic operations
    //
    @Test
    public void testNumberWithDefault() {
        assertNull(getLib().number(null, null));
        assertEqualsNumber(123.56, getLib().number(null, "123.56"), 0.001);
        assertEqualsNumber(123.56, getLib().number("123.56", null), 0.001);
        assertEqualsNumber(123.56, getLib().number("123.56", "134"), 0.001);
    }

    @Test
    public void testAbs() {
        assertNull(getLib().abs(null));

        assertEqualsNumber(makeNumber("123"), getLib().abs(makeNumber("123")));
        assertEqualsNumber(123.45, getLib().abs(makeNumber("123.45")), 0.001);
        assertEqualsNumber(makeNumber("123"), getLib().abs(makeNumber("-123")));
        assertEqualsNumber(123.45, getLib().abs(makeNumber("-123.45")), 0.001);
    }

    @Test
    public void testRound() {
        assertNull(getLib().round(null, null));
        assertNull(getLib().round(makeNumber("3.44"), null));
        assertNull(getLib().round(null, makeNumber("3.44")));

        assertEqualsNumber(3.4, getLib().round(makeNumber("3.44"), makeNumber("1")), 0.001);

        assertEqualsNumber(2.23, getLib().round(makeNumber("2.234"), makeNumber("2")), 0.0001);
        assertEqualsNumber(2.24, getLib().round(makeNumber("2.235"), makeNumber("2")), 0.0001);
        assertEqualsNumber(2.24, getLib().round(makeNumber("2.236"), makeNumber("2")), 0.0001);
    }

    @Test
    public void testInteger() {
        assertNull(getLib().integer(null));

        assertEqualsNumber(makeNumber("123"), getLib().integer(makeNumber("123.45")));
        assertEqualsNumber(makeNumber("123"), getLib().integer(makeNumber("123.56")));
    }

    @Test
    public void testModulo() {
        assertNull(getLib().modulo(null, null));
        assertNull(getLib().modulo(null, makeNumber("3.23")));
        assertNull(getLib().modulo(makeNumber("3.23"), null));

        assertEqualsNumber(makeNumber("1"), getLib().modulo(makeNumber("4"), makeNumber("3")));
        assertEqualsNumber(makeNumber("1"), getLib().modulo(makeNumber("4.125"), makeNumber("3.23")));
    }

    @Test
    public void testPercent() {
        assertNull(getLib().percent(null));

        assertEqualsNumber(0.1, getLib().percent(makeNumber("10")), 0.01);
    }

    @Test
    public void testPower() {
        assertNull(getLib().power(null, null));
        assertNull(getLib().power(null, makeNumber("3")));
        assertNull(getLib().power(makeNumber("3"), null));

        assertEqualsNumber(makeNumber("1"), getLib().numericExponentiation(makeNumber("3"), makeNumber("0")));

        assertEqualsNumber(makeNumber("8"), getLib().power(makeNumber("2"), makeNumber("3")));
        assertEqualsNumber(makeNumber("8"), getLib().power(makeNumber("2"), makeNumber("3.5")));
        assertEqualsNumber(makeNumber("9"), getLib().power(makeNumber("3"), makeNumber("2.25")));
        assertEqualsNumber(makeNumber("9"), getLib().power(makeNumber("3"), makeNumber("2.5")));
        assertEqualsNumber(makeNumber("9"), getLib().power(makeNumber("3"), makeNumber("2.75")));
        assertEqualsNumber(makeNumber("1"), getLib().power(makeNumber("3"), makeNumber("0.5")));
        assertEqualsNumber(makeNumber("0.1111111111111111"), getLib().power(makeNumber("3"), makeNumber("-2")));
        assertEqualsNumber(makeNumber("0.1111111111111111"), getLib().power(makeNumber("3"), makeNumber("-2.75")));
    }

    @Test
    public void testProduct() {
        assertNull(getLib().product(null));

        assertEqualsNumber(makeNumber("6"), getLib().product(Arrays.asList(makeNumber("2"), makeNumber("3"))));
        assertNull(getLib().product(Arrays.asList(null, makeNumber("3"))));
    }

    @Test
    public void testRoundDown() {
        assertNull(getLib().roundDown(null, null));
        assertNull(getLib().roundDown(null, makeNumber("2")));
        assertNull(getLib().roundDown(makeNumber("2"), null));

        assertEqualsNumber(1.34, getLib().roundDown(makeNumber("1.344"), makeNumber("2")), 0.001);
    }

    @Test
    public void testRoundUp() {
        assertNull(getLib().roundUp(null, null));
        assertNull(getLib().roundUp(null, makeNumber("2")));
        assertNull(getLib().roundUp(makeNumber("2"), null));

        assertEqualsNumber(1.35, getLib().roundUp(makeNumber("1.344"), makeNumber("2")), 0.001);
    }

    //
    // List operations
    //
    @Override
    @Test
    public void testAppend() {
        assertEquals("[null]", getLib().append(null, null).toString());
        assertEquals("[1, 2, null]", getLib().append(Arrays.asList("1", "2"), null).toString());
        assertEquals("[1, 2, 3]", getLib().append(Arrays.asList("1", "2"), 3).toString());
    }

    @Test
    public void testAppendAll() {
        assertEquals("[1, 2, 1, 2]", getLib().appendAll(Arrays.asList("1", "2"), Arrays.asList("1", "2")).toString());
        assertEquals("[1, 2]", getLib().appendAll(null, Arrays.asList("1", "2")).toString());
        assertEquals("[1, 2]", getLib().appendAll(Arrays.asList("1", "2"), null).toString());
        assertEquals("[]", getLib().appendAll(null, null).toString());
    }

    @Test
    public void testZip() {
        assertNull(getLib().zip(null, null));
        assertNull(getLib().zip(null, Arrays.asList(Arrays.asList("v1"), Arrays.asList("v2"))));
        assertNull(getLib().zip(Arrays.asList("k1", "k2"), null));
        assertNull(getLib().zip(Arrays.asList("k1", "k2"), Arrays.asList(4)));
        assertNull(getLib().zip(Arrays.asList("k1", "k2"), Arrays.asList(Arrays.asList(3))));

        List keys = Arrays.asList("k1", "k2");
        List values = Arrays.asList(Arrays.asList("v1"), Arrays.asList("v2"));
        List zip = getLib().zip(keys, values);
        assertEquals(1, zip.size());
        Assert.assertEquals(new Context().add("k1", "v1").add("k2", "v2"), zip.get(0));
    }

    @Test
    public void testRemove() {
        assertEquals(null, getLib().remove(null, null));
        assertEquals("[1, 2]", getLib().remove(Arrays.asList("1", "2"), null).toString());
        assertEquals("[1]", getLib().remove(Arrays.asList("1", "2"), "2").toString());
        assertEquals("[1]", getLib().remove(Arrays.asList("2", "1", "2", "2"), "2").toString());
    }

    @Test
    public void testRemoveAll() {
        assertEquals(null, getLib().removeAll(null, Arrays.asList("1", "2")));
        assertEquals(null, getLib().removeAll(null, null));
        assertEquals("[]", getLib().removeAll(Arrays.asList("1", "2"), Arrays.asList("1", "2")).toString());
        assertEquals("[1, 2]", getLib().removeAll(Arrays.asList("1", "2"), null).toString());
        assertEquals("[1, 2]", getLib().removeAll(Arrays.asList("1", "2"), Arrays.asList("3")).toString());
        assertEquals("[1]", getLib().removeAll(Arrays.asList("1", "2", "3", "2"), Arrays.asList("3", "2")).toString());
    }

    @Test
    public void testNotContainsAny() {
        assertNull(getLib().notContainsAny(null, null));
        assertNull(getLib().notContainsAny(Arrays.asList("1", "2"), null));
        assertNull(getLib().notContainsAny(null, Arrays.asList("1", "2")));

        assertFalse(getLib().notContainsAny(Arrays.asList("1", "2"), Arrays.asList("1", "4")));
        assertTrue(getLib().notContainsAny(Arrays.asList("1", "2"), Arrays.asList("3", "4")));
    }

    @Test
    public void testContainsOnly() {
        assertNull(getLib().containsOnly(null, null));
        assertNull(getLib().containsOnly(Arrays.asList(), null));
        assertNull(getLib().containsOnly(Arrays.asList("1", "2"), null));
        assertNull(getLib().containsOnly(null, Arrays.asList()));
        assertNull(getLib().containsOnly(null, Arrays.asList("1", "2")));

        assertFalse(getLib().containsOnly(Arrays.asList("1", "2"), Arrays.asList("1", "4")));
        assertTrue(getLib().containsOnly(Arrays.asList("1", "2"), Arrays.asList("1", "2", "3")));

        assertTrue(getLib().containsOnly(Arrays.asList(), Arrays.asList()));
        assertFalse(getLib().containsOnly(Arrays.asList(), Arrays.asList("1", "4")));
    }

    @Test
    public void testAreElementsOf() {
        assertNull(getLib().areElementsOf(null, null));
        assertNull(getLib().areElementsOf(Arrays.asList("1", "2"), null));
        assertNull(getLib().areElementsOf(null, Arrays.asList("1", "2")));

        assertFalse(getLib().areElementsOf(Arrays.asList("1", "2"), Arrays.asList("1", "4")));
        assertTrue(getLib().areElementsOf(Arrays.asList("1", "2"), Arrays.asList("1", "2", "3")));
    }

    @Test
    public void testElementOf() {
        assertNull(getLib().elementOf(null, null));
        assertNull(getLib().elementOf(Arrays.asList("1", "2"), null));
        assertNull(getLib().elementOf(null, Arrays.asList("1", "2")));

        assertFalse(getLib().elementOf(Arrays.asList("1", "2"), Arrays.asList("1", "4")));
        assertTrue(getLib().elementOf(Arrays.asList("1", "2"), Arrays.asList("1", "2", "3")));
    }

    //
    // Statistical operations
    //
    @Test
    public void testAvg() {
        assertNull(getLib().avg(null));

        assertEqualsNumber(makeNumber("2"), getLib().avg(Arrays.asList(makeNumber("1"), makeNumber("3"))));
        assertNull(getLib().avg(Arrays.asList(makeNumber("1"), null)));
    }

    @Test
    public void testMedian() {
        assertNull(getLib().median(null));

        assertNull(getLib().median(Arrays.asList()));
        assertEqualsNumber(makeNumber("2"), getLib().median(Arrays.asList(makeNumber("1"), makeNumber("3"))));
        assertEqualsNumber(makeNumber("3"), getLib().median(Arrays.asList(makeNumber("1"), makeNumber("5"), makeNumber("3"))));
        assertNull(getLib().median(Arrays.asList(makeNumber("1"), null)));
    }

    @Test
    public void testMode() {
        assertNull(getLib().mode(null));

        assertNull(getLib().mode(Arrays.asList()));
        assertEqualsNumber(makeNumber("1"), getLib().mode(Arrays.asList(makeNumber("1"), makeNumber("3"))));
        assertEqualsNumber(makeNumber("3"), getLib().mode(Arrays.asList(makeNumber("1"), makeNumber("3"), makeNumber("3"))));
        assertEqualsNumber(makeNumber("1"), getLib().mode(Arrays.asList(makeNumber("1"), null)));
    }

    //
    // Text operations
    //
    @Test
    public void testStringAdd() {
        assertEquals("", getLib().stringAdd(null, null));
        assertEquals("a", getLib().stringAdd("a", null));
        assertEquals("b", getLib().stringAdd(null, "b"));
        assertEquals("ab", getLib().stringAdd("a", "b"));
        assertEquals("ba", getLib().stringAdd("b", "a"));
    }

    @Test
    public void testConcat() {
        assertNull(getLib().concat(null));

        assertEquals("123", getLib().concat(Arrays.asList("1", "2", "3")));
        assertEquals("13", getLib().concat(Arrays.asList("1", null, "3")));
    }

    @Test
    public void testIsAlpha() {
        assertFalse(getLib().isAlpha(null));

        assertTrue(getLib().isAlpha("abc"));
        assertFalse(getLib().isAlpha("abc1"));
        assertFalse(getLib().isAlpha("+-"));
    }

    @Test
    public void testIsAlphaNumeric() {
        assertFalse(getLib().isAlphanumeric(null));

        assertTrue(getLib().isAlphanumeric("abc"));
        assertTrue(getLib().isAlphanumeric("abc1"));
        assertTrue(getLib().isAlphanumeric("123"));
        assertFalse(getLib().isAlphanumeric("+-"));
    }

    @Test
    public void testIsNumeric() {
        assertFalse(getLib().isNumeric(null));

        assertFalse(getLib().isNumeric("abc"));
        assertFalse(getLib().isNumeric("abc1"));
        assertTrue(getLib().isNumeric("123"));
        assertFalse(getLib().isNumeric("+-"));
    }

    @Test
    public void testIsSpaces() {
        assertFalse(getLib().isNumeric(null));

        assertTrue(getLib().isSpaces("  \t\n\f"));
        assertFalse(getLib().isSpaces("abc1"));
        assertTrue(getLib().isNumeric("123"));
        assertFalse(getLib().isNumeric("+-"));
    }

    @Test
    public void testLen() {
        assertNull(getLib().len(null));

        assertEqualsNumber(makeNumber("0"), getLib().len(""));
        assertEqualsNumber(makeNumber("4"), getLib().len("abc1"));
    }

    @Test
    public void testLower() {
        assertNull(getLib().lower(null));

        assertEquals("", getLib().lower(""));
        assertEquals("abc1", getLib().lower("aBc1"));
    }

    @Test
    public void testTrim() {
        assertNull(getLib().trim(null));

        assertEquals("abc", getLib().trim(" abc "));
        assertEquals("ab c1", getLib().trim("ab c1"));
        assertNull(getLib().trim(null));
        assertEquals("", getLib().trim(""));
    }

    @Test
    public void testUpper() {
        assertNull(getLib().upper(null));

        assertEquals("", getLib().upper(""));
        assertEquals("ABC1", getLib().upper("aBc1"));
    }

    @Test
    public void testMid() {
        assertNull(getLib().mid(null, makeNumber("6"), makeNumber("5")));
        assertNull(getLib().mid(null, makeNumber("6"), null));
        assertNull(getLib().mid(null, null, makeNumber("5")));

        assertEquals("World", getLib().mid("Hello World!", makeNumber("6"), makeNumber("5")));
        assertEquals("b", getLib().mid("bb", makeNumber("1"), makeNumber("2")));
        assertNull(getLib().mid("Hello World!", makeNumber("100"), makeNumber("5")));
    }

    @Test
    public void testLeft() {
        assertNull(getLib().left(null, null));
        assertNull(getLib().left(null, makeNumber("5")));

        assertEquals("Hello", getLib().left("Hello World!", makeNumber("5")));
        assertNull(getLib().left("Hello World!", makeNumber("100")));
    }

    @Test
    public void testRight() {
        assertNull(getLib().right(null, null));
        assertNull(getLib().right(null, makeNumber("100")));
        assertNull(getLib().right("Hello World!", null));

        assertEquals("World!", getLib().right("Hello World!", makeNumber("6")));
        assertNull(getLib().right("Hello World!", makeNumber("100")));
    }

    @Test
    public void testText() {
        assertNull(getLib().text(null, null));
        assertNull(getLib().text(null, "#.000"));
        assertNull(getLib().text(makeNumber("1"), null));

        assertEquals("1.000", getLib().text(makeNumber("1"), "#.000"));
        assertEquals("X1", getLib().text(makeNumber("1"), "X"));
        assertNull(getLib().text(makeNumber("1"), null));
    }

    @Test
    public void testTextOccurrences() {
        assertNull(getLib().textOccurrences(null, null));
        assertNull(getLib().textOccurrences(null, ""));
        assertNull(getLib().textOccurrences("", null));

        assertEqualsNumber(makeNumber("0"), getLib().textOccurrences("can", "abc "));
        assertEqualsNumber(makeNumber("6"), getLib().textOccurrences("can", "Can you can a can as a canner can can a can?"));
    }

    //
    // Signavio isXXX functions
    //
    @Test
    public void testIsPredicates() {
        assertFalse(getLib().isDefined(null));
        assertTrue(getLib().isUndefined(null));

        assertFalse(getLib().isValid(null));
        assertTrue(getLib().isInvalid(null));
    }
}
