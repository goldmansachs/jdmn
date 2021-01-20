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

import com.gs.dmn.runtime.LambdaExpression;
import com.gs.dmn.runtime.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public abstract class BaseStandardFEELLibTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends BaseFEELLibTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    //
    // Constructors
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

        List<Pair<String, String>> pairs = Arrays.asList(
//                new Pair<>("P83333333Y3M", "P999999999M"),
//                new Pair<>("-P83333333Y3M","-P999999999M"),
                new Pair<>("P367DT6H58M59S","P1Y0M2DT6H58M59.000S")
// Overflow in duration(from)
//                new Pair<>("P99999999Y", "P11999999988M"),
//                new Pair<>("-P99999999Y", "P2129706043D")
        );
        for (Pair<String, String> pair: pairs) {
            DURATION duration = makeDuration(pair.getLeft());
            DURATION normalizedDuration = makeDuration(pair.getRight());
            assertTrue(String.format("Error for '%s'", pair.toString()), getLib().durationEqual(duration, normalizedDuration));
        }

    }

    @Test
    public void testYearsAndMonthsDuration() {
        assertNull(getLib().yearsAndMonthsDuration(null, null));

        assertEquals("P0Y0M", getLib().yearsAndMonthsDuration(makeDate("2015-12-24"), makeDate("2015-12-24")).toString());
        assertEquals("P1Y2M", getLib().yearsAndMonthsDuration(makeDate("2016-09-30"), makeDate("2017-12-28")).toString());
        assertEquals("P7Y6M", getLib().yearsAndMonthsDuration(makeDate("2010-05-30"), makeDate("2017-12-15")).toString());
        assertEquals("-P4033Y2M", getLib().yearsAndMonthsDuration(makeDate("2014-12-31"), makeDate("-2019-10-01")).toString());
        assertEquals("-P4035Y11M", getLib().yearsAndMonthsDuration(makeDate("2017-09-05"), makeDate("-2019-10-01")).toString());
    }

    //
    // Numeric functions
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
    public void testRoundUp() {
        assertNull(getLib().round(null, null, null));
        assertNull(getLib().round(null, makeNumber("128"), null));
        assertNull(getLib().round(makeNumber("10"), makeNumber("2"), null));
        assertNull(getLib().round(makeNumber("10"), makeNumber("2"), "abc"));

        //
        // Test round up
        //
        assertEqualsNumber(makeNumber("6"), getLib().round(makeNumber("5.5"), makeNumber("0"), "up"));
        assertEqualsNumber(makeNumber("3"), getLib().round(makeNumber("2.5"), makeNumber("0"), "up"));
        assertEqualsNumber(makeNumber("2"), getLib().round(makeNumber("1.6"), makeNumber("0"), "up"));
        assertEqualsNumber(makeNumber("2"), getLib().round(makeNumber("1.1"), makeNumber("0"), "up"));
        assertEqualsNumber(makeNumber("1"), getLib().round(makeNumber("1.0"), makeNumber("0"), "up"));
        assertEqualsNumber(makeNumber("-1"), getLib().round(makeNumber("-1.0"), makeNumber("0"), "up"));
        assertEqualsNumber(makeNumber("-2"), getLib().round(makeNumber("-1.1"), makeNumber("0"), "up"));
        assertEqualsNumber(makeNumber("-2"), getLib().round(makeNumber("-1.6"), makeNumber("0"), "up"));
        assertEqualsNumber(makeNumber("-3"), getLib().round(makeNumber("-2.5"), makeNumber("0"), "up"));
        assertEqualsNumber(makeNumber("-6"), getLib().round(makeNumber("-5.5"), makeNumber("0"), "up"));

        assertEqualsNumber(makeNumber("5.13"), getLib().round(makeNumber("5.125"), makeNumber("2"), "up"));
        assertEqualsNumber(makeNumber("2.13"), getLib().round(makeNumber("2.125"), makeNumber("2"), "up"));
        assertEqualsNumber(makeNumber("1.13"), getLib().round(makeNumber("1.126"), makeNumber("2"), "up"));
        assertEqualsNumber(makeNumber("1.13"), getLib().round(makeNumber("1.121"), makeNumber("2"), "up"));
        assertEqualsNumber(makeNumber("1.12"), getLib().round(makeNumber("1.120"), makeNumber("2"), "up"));
        assertEqualsNumber(makeNumber("-1.12"), getLib().round(makeNumber("-1.120"), makeNumber("2"), "up"));
        assertEqualsNumber(makeNumber("-1.13"), getLib().round(makeNumber("-1.121"), makeNumber("2"), "up"));
        assertEqualsNumber(makeNumber("-1.13"), getLib().round(makeNumber("-1.126"), makeNumber("2"), "up"));
        assertEqualsNumber(makeNumber("-2.13"), getLib().round(makeNumber("-2.125"), makeNumber("2"), "up"));
        assertEqualsNumber(makeNumber("-5.13"), getLib().round(makeNumber("-5.125"), makeNumber("2"), "up"));
    }

    @Test
    public void testRoundDown() {
        assertNull(getLib().round(null, null, "down"));
        assertNull(getLib().round(null, makeNumber("128"), "down"));
        assertNull(getLib().round(makeNumber("10"), null, "down"));

        assertEqualsNumber(makeNumber("5"), getLib().round(makeNumber("5.5"), makeNumber("0"), "down"));
        assertEqualsNumber(makeNumber("2"), getLib().round(makeNumber("2.5"), makeNumber("0"), "down"));
        assertEqualsNumber(makeNumber("1"), getLib().round(makeNumber("1.6"), makeNumber("0"), "down"));
        assertEqualsNumber(makeNumber("1"), getLib().round(makeNumber("1.1"), makeNumber("0"), "down"));
        assertEqualsNumber(makeNumber("1"), getLib().round(makeNumber("1.0"), makeNumber("0"), "down"));
        assertEqualsNumber(makeNumber("-1"), getLib().round(makeNumber("-1.0"), makeNumber("0"), "down"));
        assertEqualsNumber(makeNumber("-1"), getLib().round(makeNumber("-1.1"), makeNumber("0"), "down"));
        assertEqualsNumber(makeNumber("-1"), getLib().round(makeNumber("-1.6"), makeNumber("0"), "down"));
        assertEqualsNumber(makeNumber("-2"), getLib().round(makeNumber("-2.5"), makeNumber("0"), "down"));
        assertEqualsNumber(makeNumber("-5"), getLib().round(makeNumber("-5.5"), makeNumber("0"), "down"));

        assertEqualsNumber(makeNumber("5.12"), getLib().round(makeNumber("5.125"), makeNumber("2"), "down"));
        assertEqualsNumber(makeNumber("2.12"), getLib().round(makeNumber("2.125"), makeNumber("2"), "down"));
        assertEqualsNumber(makeNumber("1.12"), getLib().round(makeNumber("1.126"), makeNumber("2"), "down"));
        assertEqualsNumber(makeNumber("1.12"), getLib().round(makeNumber("1.121"), makeNumber("2"), "down"));
        assertEqualsNumber(makeNumber("1.12"), getLib().round(makeNumber("1.120"), makeNumber("2"), "down"));
        assertEqualsNumber(makeNumber("-1.12"), getLib().round(makeNumber("-1.120"), makeNumber("2"), "down"));
        assertEqualsNumber(makeNumber("-1.12"), getLib().round(makeNumber("-1.121"), makeNumber("2"), "down"));
        assertEqualsNumber(makeNumber("-1.12"), getLib().round(makeNumber("-1.126"), makeNumber("2"), "down"));
        assertEqualsNumber(makeNumber("-2.12"), getLib().round(makeNumber("-2.125"), makeNumber("2"), "down"));
        assertEqualsNumber(makeNumber("-5.12"), getLib().round(makeNumber("-5.125"), makeNumber("2"), "down"));
    }

    @Test
    public void testRoundHalfUp() {
        assertNull(getLib().round(null, null, "half up"));
        assertNull(getLib().round(null, makeNumber("128"), "half up"));
        assertNull(getLib().round(makeNumber("10"), null, "half up"));

        assertEqualsNumber(makeNumber("6"), getLib().round(makeNumber("5.5"), makeNumber("0"), "half up"));
        assertEqualsNumber(makeNumber("3"), getLib().round(makeNumber("2.5"), makeNumber("0"), "half up"));
        assertEqualsNumber(makeNumber("2"), getLib().round(makeNumber("1.6"), makeNumber("0"), "half up"));
        assertEqualsNumber(makeNumber("1"), getLib().round(makeNumber("1.1"), makeNumber("0"), "half up"));
        assertEqualsNumber(makeNumber("1"), getLib().round(makeNumber("1.0"), makeNumber("0"), "half up"));
        assertEqualsNumber(makeNumber("-1"), getLib().round(makeNumber("-1.0"), makeNumber("0"), "half up"));
        assertEqualsNumber(makeNumber("-1"), getLib().round(makeNumber("-1.1"), makeNumber("0"), "half up"));
        assertEqualsNumber(makeNumber("-2"), getLib().round(makeNumber("-1.6"), makeNumber("0"), "half up"));
        assertEqualsNumber(makeNumber("-3"), getLib().round(makeNumber("-2.5"), makeNumber("0"), "half up"));
        assertEqualsNumber(makeNumber("-6"), getLib().round(makeNumber("-5.5"), makeNumber("0"), "half up"));

        assertEqualsNumber(makeNumber("5.13"), getLib().round(makeNumber("5.125"), makeNumber("2"), "half up"));
        assertEqualsNumber(makeNumber("2.13"), getLib().round(makeNumber("2.125"), makeNumber("2"), "half up"));
        assertEqualsNumber(makeNumber("1.13"), getLib().round(makeNumber("1.126"), makeNumber("2"), "half up"));
        assertEqualsNumber(makeNumber("1.12"), getLib().round(makeNumber("1.121"), makeNumber("2"), "half up"));
        assertEqualsNumber(makeNumber("1.12"), getLib().round(makeNumber("1.120"), makeNumber("2"), "half up"));
        assertEqualsNumber(makeNumber("-1.12"), getLib().round(makeNumber("-1.120"), makeNumber("2"), "half up"));
        assertEqualsNumber(makeNumber("-1.12"), getLib().round(makeNumber("-1.121"), makeNumber("2"), "half up"));
        assertEqualsNumber(makeNumber("-1.13"), getLib().round(makeNumber("-1.126"), makeNumber("2"), "half up"));
        assertEqualsNumber(makeNumber("-2.13"), getLib().round(makeNumber("-2.125"), makeNumber("2"), "half up"));
        assertEqualsNumber(makeNumber("-5.13"), getLib().round(makeNumber("-5.125"), makeNumber("2"), "half up"));
    }

    @Test
    public void testRoundHalfDown() {
        assertNull(getLib().round(null, null, "half down"));
        assertNull(getLib().round(null, makeNumber("128"), "half down"));
        assertNull(getLib().round(makeNumber("10"), null, "half down"));

        assertEqualsNumber(makeNumber("5"), getLib().round(makeNumber("5.5"), makeNumber("0"), "half down"));
        assertEqualsNumber(makeNumber("2"), getLib().round(makeNumber("2.5"), makeNumber("0"), "half down"));
        assertEqualsNumber(makeNumber("2"), getLib().round(makeNumber("1.6"), makeNumber("0"), "half down"));
        assertEqualsNumber(makeNumber("1"), getLib().round(makeNumber("1.1"), makeNumber("0"), "half down"));
        assertEqualsNumber(makeNumber("1"), getLib().round(makeNumber("1.0"), makeNumber("0"), "half down"));
        assertEqualsNumber(makeNumber("-1"), getLib().round(makeNumber("-1.0"), makeNumber("0"), "half down"));
        assertEqualsNumber(makeNumber("-1"), getLib().round(makeNumber("-1.1"), makeNumber("0"), "half down"));
        assertEqualsNumber(makeNumber("-2"), getLib().round(makeNumber("-1.6"), makeNumber("0"), "half down"));
        assertEqualsNumber(makeNumber("-2"), getLib().round(makeNumber("-2.5"), makeNumber("0"), "half down"));
        assertEqualsNumber(makeNumber("-5"), getLib().round(makeNumber("-5.5"), makeNumber("0"), "half down"));

        assertEqualsNumber(makeNumber("5.12"), getLib().round(makeNumber("5.125"), makeNumber("2"), "half down"));
        assertEqualsNumber(makeNumber("2.12"), getLib().round(makeNumber("2.125"), makeNumber("2"), "half down"));
        assertEqualsNumber(makeNumber("1.13"), getLib().round(makeNumber("1.126"), makeNumber("2"), "half down"));
        assertEqualsNumber(makeNumber("1.12"), getLib().round(makeNumber("1.121"), makeNumber("2"), "half down"));
        assertEqualsNumber(makeNumber("1.12"), getLib().round(makeNumber("1.120"), makeNumber("2"), "half down"));
        assertEqualsNumber(makeNumber("-1.12"), getLib().round(makeNumber("-1.120"), makeNumber("2"), "half down"));
        assertEqualsNumber(makeNumber("-1.12"), getLib().round(makeNumber("-1.121"), makeNumber("2"), "half down"));
        assertEqualsNumber(makeNumber("-1.13"), getLib().round(makeNumber("-1.126"), makeNumber("2"), "half down"));
        assertEqualsNumber(makeNumber("-2.12"), getLib().round(makeNumber("-2.125"), makeNumber("2"), "half down"));
        assertEqualsNumber(makeNumber("-5.12"), getLib().round(makeNumber("-5.125"), makeNumber("2"), "half down"));
    }

    @Test
    public void testFloor() {
        assertNull(getLib().floor(null));

        assertEqualsNumber(makeNumber("1"), getLib().floor(makeNumber(1)));
        assertEqualsNumber(makeNumber("1"), getLib().floor(makeNumber(1.23)));
        assertEqualsNumber(makeNumber("1"), getLib().floor(makeNumber(1.5)));
        assertEqualsNumber(makeNumber("1"), getLib().floor(makeNumber(1.56)));
        assertEqualsNumber(makeNumber("-2"), getLib().floor(makeNumber(-1.56)));
    }

    @Test
    public void testCeiling() {
        assertNull(getLib().ceiling(null));

        assertEqualsNumber(makeNumber("1"), getLib().ceiling(makeNumber(1)));
        assertEqualsNumber(makeNumber("2"), getLib().ceiling(makeNumber(1.23)));
        assertEqualsNumber(makeNumber("2"), getLib().ceiling(makeNumber(1.5)));
        assertEqualsNumber(makeNumber("2"), getLib().ceiling(makeNumber(1.56)));
        assertEqualsNumber(makeNumber("-1"), getLib().ceiling(makeNumber(-1.5)));
    }

    @Test
    public void testAbs() {
        assertNull(getLib().abs(null));

        assertEqualsNumber(makeNumber("10"), getLib().abs(makeNumber(10)));
        assertEqualsNumber(makeNumber("10"), getLib().abs(makeNumber(-10)));
    }

    @Test
    public void testModulo() {
        assertNull(getLib().modulo(null, null));
        assertNull(getLib().modulo(makeNumber(10), makeNumber(0)));

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
    public void testIntModulo() {
        assertNull(getLib().modulo(null, null));
        assertNull(getLib().intModulo(makeNumber(10), makeNumber(0)));

        assertEqualsNumber(makeNumber("2"), getLib().intModulo(makeNumber(10), makeNumber(4)));
        assertEqualsNumber(makeNumber("2"), getLib().intModulo(makeNumber(10), makeNumber(-4)));
        assertEqualsNumber(makeNumber("-2"), getLib().intModulo(makeNumber(-10), makeNumber(4)));
        assertEqualsNumber(makeNumber("-2"), getLib().intModulo(makeNumber(-10), makeNumber(-4)));
    }

    @Test
    public void testSqrt() {
        assertNull(getLib().sqrt(null));
        assertNull(getLib().sqrt(makeNumber(-3)));

        assertEqualsNumber(makeNumber("4"), getLib().sqrt(makeNumber(16)));
    }

    @Test
    public void testLog() {
        assertNull(getLib().log(null));
        assertNull(getLib().log(makeNumber(-3)));

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
    // String functions
    //
    @Test
    public void testContains() {
        assertNull(getLib().contains(null, null));
        assertNull(getLib().contains(null, "bcg"));
        assertNull(getLib().contains("bcg", null));

        assertTrue(getLib().contains("abc", "a"));
        assertFalse(getLib().contains("aBc1", "bcg"));

        assertEquals(false, getLib().contains("foobar", "of"));
        assertEquals(true, getLib().startsWith("foobar", "fo"));
        assertEquals(true, getLib().endsWith("foobar", "r"));
    }

    @Test
    public void testStartsWith() {
        assertNull(getLib().startsWith(null, null));
        assertNull(getLib().startsWith(null, "bcg"));
        assertNull(getLib().startsWith("bcg", null));

        assertTrue(getLib().startsWith("abc", "a"));
        assertFalse(getLib().startsWith("aBc1", "bcg"));

        assertEquals(true, getLib().startsWith("foobar", "fo"));
    }

    @Test
    public void testEndsWith() {
        assertNull(getLib().endsWith(null, null));
        assertNull(getLib().endsWith(null, "bcg"));
        assertNull(getLib().endsWith("bcg", null));

        assertTrue(getLib().endsWith("abc", "c"));
        assertFalse(getLib().endsWith("aBc1", "bcg"));

        assertEquals(true, getLib().endsWith("foobar", "r"));
    }

    @Test
    public void testSubstring() {
        assertNull(getLib().substring(null, null));
        assertNull(getLib().substring(null, makeNumber("0")));
        assertNull(getLib().substring("", null));

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
        assertNull(getLib().stringLength(null));

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
        assertNull(getLib().upperCase(null));

        assertEquals("ABC4", getLib().upperCase("aBc4"));
        assertEquals("abc4", getLib().lowerCase("aBc4"));
    }

    @Test
    public void testLowerCase() {
        assertNull(getLib().lowerCase(null));

        assertEquals("abc4", getLib().lowerCase("aBc4"));
    }

    @Test
    public void testSubstringBefore() {
        assertNull(getLib().substringBefore(null, null));
        assertNull(getLib().substringBefore(null, "bar"));
        assertNull(getLib().substringBefore("foobar", null));

        assertEquals("foo", getLib().substringBefore("foobar", "bar"));
        assertEquals("", getLib().substringBefore("foobar", "xyz"));
    }

    @Test
    public void testSubstringAfter() {
        assertNull(getLib().substringAfter(null, null));
        assertNull( getLib().substringAfter(null, "ob"));
        assertNull(getLib().substringAfter("foobar", null));

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
    // Boolean functions
    //
    @Test
    public void testNot() {
        assertTrue(getLib().not(Boolean.FALSE));
        assertFalse(getLib().not(Boolean.TRUE));
        assertNull(getLib().not(null));
    }

    //
    // Date properties
    //
    @Test
    public void testDateProperties() {
        assertNull(getLib().year(null));
        assertEqualsNumber(makeNumber("2018"), getLib().year(getLib().date("2018-12-10")));

        assertNull(getLib().month(null));
        assertEqualsNumber(makeNumber("12"), getLib().month(getLib().date("2018-12-10")));

        assertNull(getLib().day(null));
        assertEqualsNumber(makeNumber("10"), getLib().day(getLib().date("2018-12-10")));

        assertNull(getLib().weekday(null));
        assertEqualsNumber(makeNumber("1"), getLib().weekday(getLib().date("2018-12-10")));
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
        assertEqualsNumber(makeNumber("1"), getLib().years(makeDuration("P1Y2M")));
        assertEqualsNumber(makeNumber("2"), getLib().months(makeDuration("P1Y2M")));

        assertEqualsNumber(makeNumber("3"), getLib().days(makeDuration("P3DT4H5M6.700S")));
        assertEqualsNumber(makeNumber("4"), getLib().hours(makeDuration("P3DT4H5M6.700S")));
        assertEqualsNumber(makeNumber("5"), getLib().minutes(makeDuration("P3DT4H5M6.700S")));
        assertEqualsNumber(makeNumber("6"), getLib().seconds(makeDuration("P3DT4H5M6.700S")));
    }

    //
    // List functions
    //
    @Test
    public void testListContains() {
        assertNull(getLib().listContains(null, makeNumber(2)));
        assertNull(getLib().listContains(null, makeNumber(2)));

        assertEquals(false, getLib().listContains(makeNumberList(1, 2, 3), null));
        assertEquals(true, getLib().listContains(makeNumberList(1, 2, 3), makeNumber(2)));
    }

    @Test
    public void testAppend() {
        assertEquals("[null]", getLib().append(null, null).toString());
        assertEquals(Arrays.asList("3"), getLib().append(null, "3"));
        assertEquals(makeNumberList("1", null, "3", null, "3"), getLib().append(makeNumberList(1, null, 3), null, makeNumber(3)));

        assertEquals(makeNumberList("1", "2", "3", "4"), getLib().append(makeNumberList(1, 2, 3), makeNumber(4)));
        assertEquals(makeNumberList("1", null, "3", "4"), getLib().append(makeNumberList(1, null, 3), makeNumber(4)));
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
        assertNull(getLib().mean());
        assertNull(getLib().mean((Object) null));
        assertNull(getLib().mean(new Object[] {}));
        assertNull(getLib().mean((List) null));
        assertNull(getLib().mean(makeNumberList()));

        assertNull(getLib().mean(makeNumber(1), null, makeNumber(3)));
        assertNull(getLib().mean(makeNumberList(1, null, 3)));

        assertEqualsNumber(2.0, getLib().mean(makeNumber(1), makeNumber(2), makeNumber(3)), 0.001);
        assertEqualsNumber(2.0, getLib().mean(makeNumberList(1, 2, 3)), 0.001);
    }

    @Test
    public void testAll() {
        assertFalse(getLib().all(true, true, false));
        assertFalse(getLib().all(null, false));
        assertFalse(getLib().all(Arrays.asList(true, true, false)));
        assertFalse(getLib().all(Arrays.asList(null, false)));

        assertTrue(getLib().all());
        assertTrue(getLib().all(new Object[] {}));
        assertTrue(getLib().all(Arrays.asList()));
        assertTrue(getLib().all(true, true));
        assertTrue(getLib().all(Arrays.asList(true, true)));

        assertNull(getLib().all((Object) null));
        assertNull(getLib().all(null, null));
        assertNull(getLib().all(null, true));
        assertNull(getLib().all((List) null));
        assertNull(getLib().all(Arrays.asList(null, null)));
        assertNull(getLib().all(Arrays.asList(null, true)));
    }

    @Test
    public void testAny() {
        assertTrue(getLib().any(true, true));
        assertTrue(getLib().any(false, true));
        assertTrue(getLib().any(null, true));
        assertTrue(getLib().any(Arrays.asList(true, true)));
        assertTrue(getLib().any(Arrays.asList(false, true)));
        assertTrue(getLib().any(Arrays.asList(null, true)));

        assertFalse(getLib().any());
        assertFalse(getLib().any(new Object[] {}));
        assertFalse(getLib().any(Arrays.asList()));
        assertFalse(getLib().any(false, false, false));
        assertFalse(getLib().any(Arrays.asList(false, false, false)));

        assertNull(getLib().any((Object) null));
        assertNull(getLib().any(null, null));
        assertNull(getLib().any(null, false));
        assertNull(getLib().any((List) null));
        assertNull(getLib().any(Arrays.asList(null, null)));
        assertNull(getLib().any(Arrays.asList(null, false)));
    }

    @Test
    public void testSublist() {
        assertNull(getLib().sublist(null, null));
        assertNull(getLib().sublist(null, null, null));

        assertEquals(makeNumberList("1", "2", "3"), getLib().sublist(makeNumberList(1, 2, 3), makeNumber("1")));
        assertEquals(makeNumberList("1", "2"), getLib().sublist(makeNumberList(1, 2, 3), makeNumber("1"), makeNumber("2")));
        assertEquals(makeNumberList("2"), getLib().sublist(makeNumberList(1, 2, 3), makeNumber("2"), makeNumber("1")));
    }

    @Test
    public void testConcatenate() {
        assertNull(getLib().concatenate(null, null));

        assertEquals(makeNumberList("1", "2", "3", "4", "5", "6"), getLib().concatenate(makeNumberList(1, 2, 3), makeNumberList(4, 5, 6)));
    }

    @Test
    public void testInsertBefore() {
        assertNull(getLib().insertBefore(null, null, null));

        assertEquals(makeNumberList("2", "1", "3"), getLib().insertBefore(makeNumberList(1, 3), makeNumber("1"), makeNumber("2")));
        assertEquals(makeNumberList("1", "2", "4", "3"), getLib().insertBefore(makeNumberList(1, 2, 3), makeNumber("-1"), makeNumber("4")));
        // Out of bounds
        assertEquals(makeNumberList("1", "2", "3"), getLib().insertBefore(makeNumberList(1, 2, 3), makeNumber("5"), makeNumber("4")));
        assertEquals(makeNumberList("1", "2", "3"), getLib().insertBefore(makeNumberList(1, 2, 3), makeNumber("-5"), makeNumber("4")));
    }

    @Test
    public void testRemove() {
        assertNull(getLib().remove(null, null));

        assertEquals(makeNumberList("1", "3"), getLib().remove(makeNumberList(1, 2, 3), makeNumber("2")));
    }

    @Test
    public void testReverse() {
        assertEquals(Arrays.asList(), getLib().reverse(null));

        assertEquals(makeNumberList("3", "2", "1"), getLib().reverse(makeNumberList(1, 2, 3)));
    }

    @Test
    public void testIndexOf() {
        assertEquals(Arrays.asList(), getLib().indexOf(null, null));

        assertEquals(makeNumberList("2", "4"), getLib().indexOf(Arrays.asList("1", "2", "3", "2"), "2"));
    }

    @Test
    public void testUnion() {
        assertNull(getLib().union(null, null));

        assertEquals(makeNumberList("1", "2", "3"), getLib().union(makeNumberList(1, 2), makeNumberList(2, 3)));
    }

    @Test
    public void testDistinctValues() {
        assertEquals(Arrays.asList(), getLib().distinctValues(null));

        assertEquals(makeNumberList("1", "2", "3"), getLib().distinctValues(makeNumberList(1, 2, 3, 2, 1)));
    }

    @Test
    public void testFlatten() {
        assertNull(getLib().flatten(null));

        assertEquals("[1, 2, 3, 4]", getLib().flatten(Arrays.asList(
                Arrays.asList(Arrays.asList("1", "2")),
                Arrays.asList(Arrays.asList("3")),
                "4"
        )).toString());
    }

    @Test
    public void testProduct() {
        assertNull(getLib().product());
        assertNull(getLib().product((Object) null));
        assertNull(getLib().product(new Object[] {}));
        assertNull(getLib().product((List) null));
        assertNull(getLib().product((makeNumberList())));

        assertNull(getLib().product(makeNumber(2), null, makeNumber(4)));
        assertNull(getLib().product(makeNumberList(2, null, 4)));

        assertEqualsNumber(makeNumber(24), getLib().product(makeNumber(2), makeNumber(3), makeNumber(4)));
        assertEqualsNumber(makeNumber(24), getLib().product(makeNumberList(2, 3, 4)));
    }

    @Test
    public void testMedian() {
        assertNull(getLib().median());
        assertNull(getLib().median((Object) null));
        assertNull(getLib().median(new Object[] {}));
        assertNull(getLib().median((List) null));
        assertNull(getLib().median(makeNumberList()));

        assertNull(getLib().median(makeNumber(8), null, makeNumber(5)));
        assertNull(getLib().median(makeNumberList(8, null, 5)));

        assertEqualsNumber(makeNumber(4), getLib().median(makeNumber(8), makeNumber(2), makeNumber(5), makeNumber(3), makeNumber(4)));
        assertEqualsNumber(makeNumber(4), getLib().median(makeNumberList(8, 2, 5, 3, 4)));
        assertEqualsNumber(makeNumber(2.5), getLib().median(makeNumber(6), makeNumber(1), makeNumber(2), makeNumber(3)));
        assertEqualsNumber(makeNumber(2.5), getLib().median(makeNumberList(6, 1, 2, 3)));
    }

    @Test
    public void testStddev() {
        assertNull(getLib().stddev());
        assertNull(getLib().stddev((Object) null));
        assertNull(getLib().stddev(new Object[] {}));
        assertNull(getLib().stddev((List) null));
        assertNull(getLib().stddev(makeNumberList()));

        assertNull(getLib().stddev(makeNumber(2), makeNumber(4), null, makeNumber(5)));
        assertNull(getLib().stddev(makeNumberList(2, 4, null, 5)));

        assertEqualsNumber(makeNumber("2.0816659994661"), getLib().stddev(makeNumber(2), makeNumber(4), makeNumber(7), makeNumber(5)));
        assertEqualsNumber(makeNumber("2.0816659994661"), getLib().stddev(makeNumberList(2, 4, 7, 5)));
    }

    @Test
    public void testMode() {
        assertNull(getLib().mode((List) null));
        assertNull(getLib().mode((Object)null));

        assertEquals(makeNumberList(), getLib().mode());
        assertEquals(makeNumberList(), getLib().mode(new Object[] {}));
        assertEquals(makeNumberList(), getLib().mode(makeNumberList()));

        assertNull(getLib().mode(makeNumber(1), null));
        assertNull(getLib().mode(makeNumberList(1, null)));

        assertEquals(makeNumberList(6), getLib().mode(makeNumber(6), makeNumber(3), makeNumber(9), makeNumber(6), makeNumber(6)));
        assertEquals(makeNumberList(6), getLib().mode(makeNumberList(6, 3, 9, 6, 6)));
        assertEquals(makeNumberList(1, 6), getLib().mode(makeNumber(6), makeNumber(1), makeNumber(9), makeNumber(6), makeNumber(1)));
        assertEquals(makeNumberList(1, 6), getLib().mode(makeNumberList(6, 1, 9, 6, 1)));
    }

    @Test
    public void testCollect() {
        getLib().collect(null, null);
        getLib().collect(Arrays.asList(), null);

        List<String> result = new ArrayList<>();
        getLib().collect(result, Arrays.asList(Arrays.asList("1", "2"), "3"));
        assertEquals(Arrays.asList("1", "2", "3"), result);
    }

    @Test
    public void testSort() {
        LambdaExpression<Boolean> comparator = new LambdaExpression<Boolean>() {
            @Override
            public Boolean apply(Object... args) {
                String s1 = (String) args[0];
                String s2 = (String) args[1];
                return s1.compareTo(s2) < 0;
            }
        };

        assertNull(getLib().sort(null, null));
        assertNull(getLib().sort(null, comparator));
        assertEquals(Arrays.asList(), getLib().sort(Arrays.asList(), null));

        assertEquals(Arrays.asList("1", "2", "3"), getLib().sort(Arrays.asList("3", "1", "2"), comparator));
    }
}
