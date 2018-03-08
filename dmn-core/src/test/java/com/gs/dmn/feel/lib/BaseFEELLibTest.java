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
package com.gs.dmn.feel.lib;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public abstract class BaseFEELLibTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends CommonLibFunctionsTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    //
    // Conversion functions
    //
    @Test
    public void testNumberWithSeparators() {
        assertNull(getLib().number(null, null, null));
        assertNull(getLib().number(null, ".", ","));
        assertNull(getLib().number("123", null, ","));
        assertNull(getLib().number("123", ".", null));

        assertNull(getLib().number("12,356,00", ".", ","));

        assertEquals(makeNumber("12356"), getLib().number("12.356,00", ".", ","));
        assertEquals(makeNumber("12356"), getLib().number("12.356", ".", ","));
        assertEquals("12356.01", getLib().number("12356,01", ".", ",").toString());

        assertEquals(makeNumber("12356"), getLib().number("12,356.00", ",", "."));
        assertEquals(makeNumber("12356"), getLib().number("12,356", ",", "."));
        assertEquals("12356.01", getLib().number("12356.01", ",", ".").toString());
    }

    @Test
    public void testDuration() {
        assertEquals("P1Y8M", getLib().duration("P1Y8M").toString());
        assertEquals("P2DT20H", getLib().duration("P2DT20H").toString());
        assertEquals("-PT2H", getLib().duration("-PT2H").toString());
    }

    @Test
    public void testYearsAndMonthsDuration() throws Exception {
        assertEquals("P0Y0M", getLib().yearsAndMonthsDuration(makeDateAndTime("2015-12-24T12:15:00.000+01:00"), makeDateAndTime("2015-12-24T12:15:00.000+01:00")).toString());
        assertEquals("P1Y2M", getLib().yearsAndMonthsDuration(makeDateAndTime("2016-09-30T23:25:00"), makeDateAndTime("2017-12-28T12:12:12")).toString());
        assertEquals("P7Y6M", getLib().yearsAndMonthsDuration(makeDateAndTime("2010-05-30T03:55:58"), makeDateAndTime("2017-12-15T00:59:59")).toString());
        assertEquals("-P4033Y2M", getLib().yearsAndMonthsDuration(makeDateAndTime("2014-12-31T23:59:59"), makeDateAndTime("-2019-10-01T12:32:59")).toString());
        assertEquals("-P4035Y11M", getLib().yearsAndMonthsDuration(makeDateAndTime("2017-09-05T10:20:00-01:00"), makeDateAndTime("-2019-10-01T12:32:59+02:00")).toString());
    }

    //
    // Boolean built-in functions for Booleans
    //
    @Test
    public void testNot() {
        assertTrue(getLib().not(Boolean.FALSE));
        assertFalse(getLib().not(Boolean.TRUE));
        assertNull(getLib().not(null));
    }

    //
    // String built-in functions for Strings
    //
    @Test
    public void testSubstring() {
        assertEquals("obar", getLib().substring("foobar", makeNumber("3")));
        assertEquals("oba", getLib().substring("foobar", makeNumber("3"), makeNumber("3")));
        assertEquals("a", getLib().substring("foobar", makeNumber("-2"), makeNumber("1")));
    }

    @Test
    public void testStringLength() {
        assertEqualsNumber(makeNumber(3), getLib().stringLength("foo"));
    }

    @Test
    public void testUpperCase() {
        assertEquals("ABC4", getLib().upperCase("aBc4"));
        assertEquals("abc4", getLib().lowerCase("aBc4"));
    }

    @Test
    public void testLowerCase() {
        assertEquals("abc4", getLib().lowerCase("aBc4"));
    }

    @Test
    public void testSubstringBefore() {
        assertEquals("foo", getLib().substringBefore("foobar", "bar"));
        assertEquals("", getLib().substringBefore("foobar", "xyz"));
    }

    @Test
    public void testSubstringAfter() {
        assertEquals("ar", getLib().substringAfter("foobar", "ob"));
    }

    @Test
    public void testReplace() {
        assertNull(getLib().replace("", "", ""));
        assertNull(getLib().replace("", "", null));

        assertNull(getLib().replace("abc", "[a-z]*", "#"));
        assertEquals("#", getLib().replace("abc", "[a-z]+", "#"));

        assertNull(getLib().replace(null, "(ab)|(a)", "[1=$1][2=$2]"));
        assertNull(getLib().replace("abcd", null, "[1=$1][2=$2]"));
        assertNull(getLib().replace("abcd", "(ab)|(a)", null));

        assertEquals("[1=ab][2=]cd", getLib().replace("abcd", "(ab)|(a)", "[1=$1][2=$2]"));

        assertEquals("a*cada*", getLib().replace("abracadabra", "bra", "*"));
        assertEquals("*", getLib().replace("abracadabra", "a.*a", "*"));
        assertEquals("*c*bra", getLib().replace("abracadabra", "a.*?a", "*"));
        assertEquals("brcdbr", getLib().replace("abracadabra", "a", ""));
        assertEquals("abbraccaddabbra", getLib().replace("abracadabra", "a(.)", "a$1$1"));
        assertEquals(null, getLib().replace("abracadabra", ".*?", "$1"));
        assertEquals("b", getLib().replace("AAAA", "A+", "b"));
        assertEquals("bbbb", getLib().replace("AAAA", "A+?", "b"));
        assertEquals("carted", getLib().replace("darted", "^(.*?)d(.*)$", "$1c$2"));
    }

    @Test
    public void testMatches() {
        assertTrue(getLib().matches("", "", ""));
        assertTrue(getLib().matches("", "", null));

        assertTrue(getLib().matches("", "[a-z]*", ""));
        assertTrue(getLib().matches("abc", "[a-z]+", ""));

        assertTrue(getLib().matches("abracadabra", "bra"));
        assertTrue(getLib().matches("abracadabra", "^a.*a$"));
        assertFalse(getLib().matches("abracadabra", "^bra"));

        String input = "Kaum hat dies der Hahn gesehen,\n" +
                "Fangt er auch schon an zu krahen:\n" +
                "Kikeriki! Kikikerikih!!\n" +
                "Tak, tak, tak! - da kommen sie.\n";

        assertFalse(getLib().matches(input, "Kaum.*krahen"));
        assertTrue(getLib().matches(input, "Kaum.*krahen", "s"));
        assertTrue(getLib().matches(input, "^Kaum.*gesehen,$", "m"));
        assertFalse(getLib().matches(input, "^Kaum.*gesehen,$"));
        assertTrue(getLib().matches(input, "kiki", "i"));
    }

    //
    // String built-in functions for Lists
    //
    @Test
    public void testListContains() {
        assertEquals(true, getLib().listContains(makeNumberList(1, 2, 3), makeNumber(2)));
    }

    //
    // Statistic operations
    //
    @Override
    @Test
    public void testMin() throws Exception {
        super.testMin();
        assertEqualsNumber(makeNumber("1"), getLib().min(makeNumber(1), makeNumber(2), makeNumber(3)));
        assertNull(getLib().min(1, null, 3));
    }

    @Override
    @Test
    public void testMax() throws Exception {
        super.testMax();
        assertEqualsNumber(makeNumber("3"), getLib().max(makeNumber(1), makeNumber(2), makeNumber(3)));
        assertNull(getLib().max(1, null, 3));
    }

    @Override
    @Test
    public void testSum() throws Exception {
        super.testSum();
        assertEqualsNumber(makeNumber("6"), getLib().sum(makeNumber(1), makeNumber(2), makeNumber(3)));
        assertNull(getLib().sum(1, null, 3));
    }

    @Test
    public void testMean() {
        assertNull(getLib().mean((List) null));

        assertEqualsNumber(2.0, getLib().mean(makeNumberList(1, 2, 3)), 0.001);

        assertEqualsNumber(2.0, getLib().mean(makeNumber(1), makeNumber(2), makeNumber(3)), 0.001);
    }

    @Test
    public void testAnd() {
        assertNull(getLib().and((List) null));
        assertTrue(getLib().and(Arrays.asList(true, true)));
        assertFalse(getLib().and(Arrays.asList(true, true, false)));
        assertFalse(getLib().and(Arrays.asList(null, false)));

        assertNull(getLib().and(null, null));
        assertTrue(getLib().and(true, true));
        assertFalse(getLib().and(true, true, false));
    }

    @Test
    public void testOr() {
        assertNull(getLib().or((List) null));
        assertTrue(getLib().or(Arrays.asList(true, true)));
        assertFalse(getLib().or(Arrays.asList(false, false, false)));
        assertNull(getLib().or(Arrays.asList(null, false)));

        assertNull(getLib().or(null, null));
        assertTrue(getLib().or(true, true));
        assertTrue(getLib().or(true, true, false));
    }

    @Test
    public void testSublist() {
        assertEquals(makeNumberList("1", "2", "3"), getLib().sublist(makeNumberList(1, 2, 3), makeNumber("1")));
        assertEquals(makeNumberList("1", "2"), getLib().sublist(makeNumberList(1, 2, 3), makeNumber("1"), makeNumber("2")));
        assertEquals(makeNumberList("2"), getLib().sublist(makeNumberList(1, 2, 3), makeNumber("2"), makeNumber("1")));
    }

    @Test
    public void testConcatenate() {
        assertEquals(makeNumberList("1", "2", "3", "4", "5", "6"), getLib().concatenate(makeNumberList(1, 2, 3), makeNumberList(4, 5, 6)));
    }

    @Test
    public void testInsertBefore() {
        assertEquals(makeNumberList("2", "1", "3"), getLib().insertBefore(makeNumberList(1, 3), makeNumber("1"), makeNumber("2")));
    }

    @Test
    public void testRemove() {
        assertEquals(makeNumberList("1", "3"), getLib().remove(makeNumberList(1, 2, 3), makeNumber("2")));
    }

    @Test
    public void testReverse() {
        assertEquals(makeNumberList("3", "2", "1"), getLib().reverse(makeNumberList(1, 2, 3)));
    }

    @Test
    public void testIndexOf() {
        assertEquals(makeNumberList("2", "4"), getLib().indexOf(Arrays.asList("1", "2", "3", "2"), "2"));
    }

    @Test
    public void testUnion() {
        assertEquals(makeNumberList("1", "2", "3"), getLib().union(makeNumberList(1, 2), makeNumberList(2, 3)));
    }

    @Test
    public void testDistinctValues() {
        assertEquals(makeNumberList("1", "2", "3"), getLib().distinctValues(makeNumberList(1, 2, 3, 2, 1)));
    }

    @Test
    public void testFlatten() {
        assertEquals("[1, 2, 3, 4]", getLib().flatten(Arrays.asList(
                Arrays.asList(Arrays.asList("1", "2")),
                Arrays.asList(Arrays.asList("3")),
                "4"
        )).toString());
    }

    @Test
    public void testRangeToList() {
        assertEquals(makeNumberList("2"), getLib().rangeToList(true, makeNumber("1"), true, makeNumber("3")));
        assertEquals(makeNumberList("1", "2"), getLib().rangeToList(false, makeNumber("1"), true, makeNumber("3")));
        assertEquals(makeNumberList("2", "3"), getLib().rangeToList(true, makeNumber("1"), false, makeNumber("3")));
        assertEquals(makeNumberList("1", "2", "3"), getLib().rangeToList(false, makeNumber("1"), false, makeNumber("3")));

        assertEquals(makeNumberList(), getLib().rangeToList(false, null, false, makeNumber("3")));
        assertEquals(makeNumberList(), getLib().rangeToList(false, makeNumber("1"), false, null));
        assertEquals(makeNumberList(), getLib().rangeToList(false, null, false, null));
    }

    @Test
    public void testFlattenFirstLevel() {
        assertEquals("[]", getLib().flattenFirstLevel(Arrays.asList()).toString());
        assertEquals("[l11, l12, l13]", getLib().flattenFirstLevel(Arrays.asList("l11", "l12", "l13")).toString());
        assertEquals("[l11, l21, l22, l13]", getLib().flattenFirstLevel(Arrays.asList("l11", Arrays.asList("l21", "l22"), "l13")).toString());
        assertEquals("[l11, l21, [l31, l32], l13]", getLib().flattenFirstLevel(Arrays.asList("l11", Arrays.asList("l21", Arrays.asList("l31", "l32")), "l13")).toString());

        assertEquals(null, getLib().flattenFirstLevel(null));
        assertEquals("[l11, null, [null, l32], l13]", getLib().flattenFirstLevel(Arrays.asList("l11", Arrays.asList(null, Arrays.asList(null, "l32")), "l13")).toString());
    }

    @Test
    public void testElementAt() {
        assertEquals("1", getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("1")));
        assertEquals("2", getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("2")));
        assertEquals("3", getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("3")));
        assertEquals("3", getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("-1")));
        assertEquals("2", getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("-2")));
        assertEquals("1", getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("-3")));

        assertEquals(null, getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("4")));
        assertEquals(null, getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("-4")));
        assertEquals(null, getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("0")));

        assertEquals(null, getLib().elementAt(null, makeNumber("1")));
    }

    //
    // String built-in functions for Number
    //
    @Test
    public void testDecimal() {
        assertNull(getLib().decimal(null, null));
        assertNull(getLib().decimal(null, makeNumber("128")));
        assertNull(getLib().decimal(makeNumber("10"), null));

        assertEqualsNumber(makeNumber("0.33"), getLib().decimal(makeNumber("0.333"), makeNumber("2")));
        assertEqualsNumber(makeNumber("2"), getLib().decimal(makeNumber("1.5"), makeNumber("0")));
        assertEqualsNumber(makeNumber("2"), getLib().decimal(makeNumber("2.5"), makeNumber("0")));
        assertEqualsNumber(makeNumber("10.00"), getLib().decimal(makeNumber("10.001"), makeNumber("2")));
    }
}
