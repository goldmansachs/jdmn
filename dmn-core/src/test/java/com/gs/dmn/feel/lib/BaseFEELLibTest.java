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
package com.gs.dmn.feel.lib;

import com.gs.dmn.runtime.Context;
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
        assertNull(getLib().number("1.235.00", ".", "."));
        assertNull(getLib().number("12,356,00", ".", ","));

        assertEqualsNumber(makeNumber("12356"), getLib().number("12.356,00", ".", ","));
        assertEqualsNumber(makeNumber("12356"), getLib().number("12.356", ".", ","));
        assertEqualsNumber(makeNumber("12356.01"), getLib().number("12356,01", ".", ","));

        assertEqualsNumber(makeNumber("12356"), getLib().number("12,356.00", ",", "."));
        assertEqualsNumber(makeNumber("12356"), getLib().number("12,356", ",", "."));
        assertEqualsNumber(makeNumber("12356.01"), getLib().number("12356.01", ",", "."));

        assertEqualsNumber(makeNumber("1000000.01"), getLib().number("1000000.01", null, "."));
        assertEqualsNumber(makeNumber("1000000.00"), getLib().number("1,000,000", ",", null));
        assertEqualsNumber(makeNumber("1000000.00"), getLib().number("1,000,000.00", ",", null));
        assertEqualsNumber(makeNumber("1000000.01"), getLib().number("1.000.000,01", ".", ","));
    }

    @Override
    @Test
    public void testDuration()  {
        assertEquals("P1Y8M", getLib().duration("P1Y8M").toString());
        assertEquals("P2DT20H", getLib().duration("P2DT20H").toString());
        assertEquals("-PT2H", getLib().duration("-PT2H").toString());
    }

    @Test
    public void testYearsAndMonthsDuration() {
        assertNull(getLib().yearsAndMonthsDuration(null, null));

        assertEquals("P0Y0M", getLib().yearsAndMonthsDuration(makeDateAndTime("2015-12-24T12:15:00.000+01:00"), makeDateAndTime("2015-12-24T12:15:00.000+01:00")).toString());
        assertEquals("P1Y2M", getLib().yearsAndMonthsDuration(makeDateAndTime("2016-09-30T23:25:00"), makeDateAndTime("2017-12-28T12:12:12")).toString());
        assertEquals("P7Y6M", getLib().yearsAndMonthsDuration(makeDateAndTime("2010-05-30T03:55:58"), makeDateAndTime("2017-12-15T00:59:59")).toString());
        assertEquals("-P4033Y2M", getLib().yearsAndMonthsDuration(makeDateAndTime("2014-12-31T23:59:59"), makeDateAndTime("-2019-10-01T12:32:59")).toString());
        assertEquals("-P4035Y11M", getLib().yearsAndMonthsDuration(makeDateAndTime("2017-09-05T10:20:00-01:00"), makeDateAndTime("-2019-10-01T12:32:59+02:00")).toString());
    }

    //
    // Boolean functions
    //
    @Test
    public void testNot() {
        assertTrue(getLib().not(Boolean.FALSE));
        assertFalse(getLib().not(Boolean.TRUE));
        assertNull(getLib().not(null));
    }

    //
    // String functions
    //
    @Test
    public void testSubstring() {
        assertEquals("obar", getLib().substring("foobar", makeNumber("3")));
        assertEquals("oba", getLib().substring("foobar", makeNumber("3"), makeNumber("3")));
        assertEquals("a", getLib().substring("foobar", makeNumber("-2"), makeNumber("1")));

        // horse + grinning face emoji
        assertEquals("\uD83D\uDE00", getLib().substring("foo\ud83d\udc0ebar\uD83D\uDE00", makeNumber("8")));
        assertEquals("\uD83D\uDC0E", getLib().substring("foo\ud83d\udc0ebar\uD83D\uDE00", makeNumber("4"), makeNumber("1")));
        assertEquals("\uD83D\uDC0Ebar", getLib().substring("foo\ud83d\udc0ebar\uD83D\uDE00", makeNumber("4"), makeNumber("4")));
    }

    @Test
    public void testStringLength() {
        assertEqualsNumber(makeNumber(3), getLib().stringLength("foo"));
        assertEqualsNumber(makeNumber(1), getLib().stringLength("\n"));
        assertEqualsNumber(makeNumber(1), getLib().stringLength("\r"));
        assertEqualsNumber(makeNumber(1), getLib().stringLength("\t"));
        assertEqualsNumber(makeNumber(1), getLib().stringLength("\""));
        assertEqualsNumber(makeNumber(1), getLib().stringLength("\'"));
        assertEqualsNumber(makeNumber(1), getLib().stringLength("\\"));
        assertEqualsNumber(makeNumber(1), getLib().stringLength("\u0009"));
        assertEqualsNumber(makeNumber(6), getLib().stringLength("\\u0009"));
        // horse emoji
        assertEqualsNumber(makeNumber(2), getLib().stringLength("\uD83D\uDCA9"));
        // horse emoji lowercase
        assertEqualsNumber(makeNumber(2), getLib().stringLength("\ud83d\udca9"));
        // horse + grinning face emoji
        assertEqualsNumber(makeNumber(4), getLib().stringLength("\ud83d\udc0e\uD83D\uDE00"));
        // horse + grinning face emoji
        assertEqualsNumber(makeNumber(4), getLib().stringLength("🐎😀"));
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

    @Test
    public void testSplit() {
        assertNull("", getLib().split(null, null));
        assertNull("", getLib().split("", ""));

        assertEquals(getLib().asList("John", "Doe"), getLib().split("John Doe", "\\s"));
        assertEquals(getLib().asList("a", "b", "c", "", ""), getLib().split("a;b;c;;", ";"));
    }

    //
    // List functions
    //
    @Test
    public void testListContains() {
        assertEquals(true, getLib().listContains(makeNumberList(1, 2, 3), makeNumber(2)));
    }

    @Override
    @Test
    public void testMin() {
        super.testMin();
        assertEqualsNumber(makeNumber("1"), getLib().min(makeNumber(1), makeNumber(2), makeNumber(3)));
        assertNull(getLib().min(1, null, 3));
    }

    @Override
    @Test
    public void testMax() {
        super.testMax();
        assertEqualsNumber(makeNumber("3"), getLib().max(makeNumber(1), makeNumber(2), makeNumber(3)));
        assertNull(getLib().max(1, null, 3));
    }

    @Override
    @Test
    public void testSum() {
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
    public void testAll() {
        assertNull(getLib().all((List) null));
        assertTrue(getLib().all(Arrays.asList(true, true)));
        assertFalse(getLib().all(Arrays.asList(true, true, false)));
        assertFalse(getLib().all(Arrays.asList(null, false)));

        assertNull(getLib().all(null, null));
        assertTrue(getLib().all(true, true));
        assertFalse(getLib().all(true, true, false));
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
    public void testAny() {
        assertNull(getLib().any((List) null));
        assertTrue(getLib().any(Arrays.asList(true, true)));
        assertFalse(getLib().any(Arrays.asList(false, false, false)));
        assertNull(getLib().any(Arrays.asList(null, false)));

        assertNull(getLib().any(null, null));
        assertTrue(getLib().any(true, true));
        assertTrue(getLib().any(true, true, false));
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
    public void testProduct() {
        assertNull(getLib().product((List) null));
        assertNull(getLib().product((makeNumberList())));

        assertEqualsNumber(makeNumber(24), getLib().product(makeNumberList(2, 3, 4)));

        assertEqualsNumber(makeNumber(24), getLib().product(makeNumber(2), makeNumber(3), makeNumber(4)));
    }

    @Test
    public void testMedian() {
        assertNull(getLib().median((List) null));
        assertNull(getLib().median(makeNumberList()));

        assertEqualsNumber(makeNumber(4), getLib().median(makeNumberList(8, 2, 5, 3, 4)));
        assertEqualsNumber(makeNumber(2.5), getLib().median(makeNumberList(6, 1, 2, 3)));

        assertEqualsNumber(makeNumber(4), getLib().median(makeNumber(8), makeNumber(2), makeNumber(5), makeNumber(3), makeNumber(4)));
        assertEqualsNumber(makeNumber(2.5), getLib().median(makeNumber(6), makeNumber(1), makeNumber(2), makeNumber(3)));
    }

    @Test
    public void testStddev() {
        assertNull(getLib().stddev((List) null));
        assertNull(getLib().stddev(makeNumberList()));

        assertEqualsNumber(makeNumber("2.0816659994661"), getLib().stddev(makeNumberList(2, 4, 7, 5)));

        assertEqualsNumber(makeNumber("2.0816659994661"), getLib().stddev(makeNumber(2), makeNumber(4), makeNumber(7), makeNumber(5)));
    }

    @Test
    public void testMode() {
        assertNull(getLib().mode((List) null));
        assertNull(getLib().mode((Object)null));
        assertNull(getLib().mode(getLib().asList(getLib().number("1"), null)));

        assertEquals(makeNumberList(), getLib().mode());
        assertEquals(makeNumberList(), getLib().mode(makeNumberList()));

        assertEquals(makeNumberList(6), getLib().mode(makeNumberList(6, 3, 9, 6, 6)));
        assertEquals(makeNumberList(1, 6), getLib().mode(makeNumberList(6, 1, 9, 6, 1)));

        assertEquals(makeNumberList(6), getLib().mode(makeNumber(6), makeNumber(3), makeNumber(9), makeNumber(6), makeNumber(6)));
        assertEquals(makeNumberList(1, 6), getLib().mode(makeNumber(6), makeNumber(1), makeNumber(9), makeNumber(6), makeNumber(1)));
    }

    //
    // Context functions
    //
    @Test
    public void testGetEntries() {
        assertNull(getLib().getEntries(null));
        assertNull(getLib().getEntries(makeNumber("1")));

        assertEquals(Arrays.asList(), getLib().getEntries(new Context()));
        assertEquals(Arrays.asList(new Context().add("key", "a").add("value", makeNumber("1"))), getLib().getEntries(new Context().add("a", makeNumber("1"))));
    }

    @Test
    public void testGetValue() {
        assertNull(getLib().getValue(null, null));
        assertNull(getLib().getValue(new Context(), null));
        assertNull(getLib().getValue(new Context(), "a"));

        assertEquals(makeNumber("1"), getLib().getValue(new Context().add("a", makeNumber("1")), "a"));
    }

    //
    // String built-in functions for Number
    //
    @Test
    public void testDecimal() {
        assertNull(getLib().decimal(null, null));
        assertNull(getLib().decimal(null, makeNumber("128")));
        assertNull(getLib().decimal(makeNumber("10"), null));

//        assertEqualsNumber(makeNumber("-10"), getLib().decimal(makeNumber("-10"), makeNumber(Long.MAX_VALUE)));

        assertEqualsNumber(makeNumber("0.33"), getLib().decimal(makeNumber("0.333"), makeNumber("2")));
        assertEqualsNumber(makeNumber("2"), getLib().decimal(makeNumber("1.5"), makeNumber("0")));
        assertEqualsNumber(makeNumber("2"), getLib().decimal(makeNumber("2.5"), makeNumber("0")));
        assertEqualsNumber(makeNumber("10.00"), getLib().decimal(makeNumber("10.001"), makeNumber("2")));
    }

    @Test
    public void testAbs() {
        assertNull(getLib().abs(null));

        assertEqualsNumber(makeNumber("10"), getLib().abs(makeNumber(10)));
        assertEqualsNumber(makeNumber("10"), getLib().abs(makeNumber(-10)));
    }

    @Test
    public void testIntModulo() {
        assertNull(getLib().modulo(null, null));

        assertEqualsNumber(makeNumber("2"), getLib().intModulo(makeNumber(10), makeNumber(4)));
        assertEqualsNumber(makeNumber("2"), getLib().intModulo(makeNumber(10), makeNumber(-4)));
        assertEqualsNumber(makeNumber("-2"), getLib().intModulo(makeNumber(-10), makeNumber(4)));
        assertEqualsNumber(makeNumber("-2"), getLib().intModulo(makeNumber(-10), makeNumber(-4)));
    }

    @Test
    public void testModulo() {
        assertNull(getLib().modulo(null, null));

        assertEqualsNumber(makeNumber("2"), getLib().modulo(makeNumber(10), makeNumber(4)));
        assertEqualsNumber(makeNumber("-2"), getLib().modulo(makeNumber(10), makeNumber(-4)));
        assertEqualsNumber(makeNumber("2"), getLib().modulo(makeNumber(-10), makeNumber(4)));
        assertEqualsNumber(makeNumber("-2"), getLib().modulo(makeNumber(-10), makeNumber(-4)));

        assertEqualsNumber(makeNumber("1.1"), getLib().modulo(makeNumber(10.1), makeNumber(4.5)));
        assertEqualsNumber(makeNumber("3.4"), getLib().modulo(makeNumber(-10.1), makeNumber(4.5)));
        assertEqualsNumber(makeNumber("-3.4"), getLib().modulo(makeNumber(10.1), makeNumber(-4.5)));
        assertEqualsNumber(makeNumber("-1.1"), getLib().modulo(makeNumber(-10.1), makeNumber(-4.5)));
    }

    @Test
    public void testSqrt() {
        assertNull(getLib().sqrt(null));

        assertEqualsNumber(makeNumber("4"), getLib().sqrt(makeNumber(16)));
    }

    @Test
    public void testLog() {
        assertNull(getLib().log(null));

        assertEqualsNumber(makeNumber("2.30258509299404590109361379290930926799774169921875"), getLib().log(makeNumber(10)));
    }

    @Test
    public void testExp() {
        assertNull(getLib().exp(null));

        assertEqualsNumber(makeNumber("148.413159102576599934764089994132518768310546875"), getLib().exp(makeNumber(5)));
    }

    @Test
    public void testOdd() {
        assertNull(getLib().odd(null));

        assertFalse(getLib().odd(makeNumber("0.00")));
        assertTrue(getLib().odd(makeNumber("1.00")));
        assertTrue(getLib().odd(makeNumber(5)));
        assertFalse(getLib().odd(makeNumber(2)));
    }

    @Test
    public void testEven() {
        assertNull(getLib().even(null));

        assertTrue(getLib().even(makeNumber("0.00")));
        assertFalse(getLib().even(makeNumber("1.00")));
        assertFalse(getLib().even(makeNumber(5)));
        assertTrue(getLib().even(makeNumber(2)));
    }

    //
    // Date properties
    //
    @Test
    public void testDateProperties() {
        assertEqualsNumber(getLib().number("2018"), getLib().year(getLib().date("2018-12-10")));
        assertEqualsNumber(getLib().number("12"), getLib().month(getLib().date("2018-12-10")));
        assertEqualsNumber(getLib().number("10"), getLib().day(getLib().date("2018-12-10")));
        assertEqualsNumber(getLib().number("1"), getLib().weekday(getLib().date("2018-12-10")));
    }

    //
    // Time properties
    //
    @Test
    public void testTimeProperties() {
        // See each implementation
    }

    //
    // Date and time properties
    //
    @Test
    public void testDateAndTimeProperties() {
        // See each implementation
    }

    //
    // Duration properties
    //
    @Test
    public void testDurationProperties() {
        assertEqualsNumber(getLib().number("1"), getLib().years(getLib().duration("P1Y2M")));
        assertEqualsNumber(getLib().number("2"), getLib().months(getLib().duration("P1Y2M")));

        assertEqualsNumber(getLib().number("3"), getLib().days(getLib().duration("P1Y2M3DT4H5M6.700S")));
        assertEqualsNumber(getLib().number("4"), getLib().hours(getLib().duration("P1Y2M3DT4H5M6.700S")));
        assertEqualsNumber(getLib().number("5"), getLib().minutes(getLib().duration("P1Y2M3DT4H5M6.700S")));
        assertEqualsNumber(getLib().number("6"), getLib().seconds(getLib().duration("P1Y2M3DT4H5M6.700S")));
    }
}
