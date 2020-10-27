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

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public abstract class BaseSignavioLibExceptionsTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    @Test
    public void testIsDefined() {
        assertFalse(getLib().isDefined(null));
    }

    @Test
    public void testIsUndefined() {
        assertTrue(getLib().isUndefined(null));
    }

    @Test
    public void testIsValid() {
        assertFalse(getLib().isValid(null));
    }

    @Test
    public void testIsInvalid() {
        assertTrue(getLib().isInvalid(null));
    }

    @Test
    public void testAbs() {
        assertNull(getLib().abs(null));
    }

    @Test
    public void testCount() {
        assertNull(getLib().count(null));
    }

    @Test
    public void testRound() {
        assertNull(getLib().round(null, null));
    }

    @Test
    public void testCeiling() {
        assertNull(getLib().ceiling(null));
    }

    @Test
    public void testFloor() {
        assertNull(getLib().floor(null));
    }

    @Test
    public void testInteger() {
        assertNull(getLib().integer(null));
    }

    @Test
    public void testModulo() {
        assertNull(getLib().modulo(null, null));
    }

    @Test
    public void testPercent() {
        assertNull(getLib().percent(null));
    }

    @Test
    public void testPower() {
        assertNull(getLib().power(null, null));
    }

    @Test
    public void testProduct() {
        assertNull(getLib().product(null));
    }

    @Test
    public void testRoundDown() {
        assertNull(getLib().roundDown(null, null));
    }

    @Test
    public void testRoundUp() {
        assertNull(getLib().roundUp(null, null));
    }

    @Test
    public void testSum() {
        assertNull(getLib().sum(null));
    }

    @Test
    public void testDay() {
        assertNull(getLib().day(null));
    }

    @Test
    public void testDayAdd() {
        assertNull(getLib().dayAdd(null, null));
    }

    @Test
    public void testDayDiff() {
        assertNull(getLib().dayDiff(null, null));
    }

    @Test
    public void testDate() {
        assertNull(getLib().date((String) null));
        assertNull(getLib().date((DATE) null));
        assertNull(getLib().date(null, null, null));
        assertNull(getLib().date(makeNumber(), makeNumber(), makeNumber()));
    }

    @Test
    public void testDateTime() {
        assertNull(getLib().dateTime(null, null, null, null, null, null));
        assertNull(getLib().dateTime(makeNumber(), makeNumber(), makeNumber(), makeNumber(), makeNumber(), makeNumber()));
        assertNull(getLib().dateTime(null, null, null, null, null, null, null));
        assertNull(getLib().dateTime(makeNumber(), makeNumber(), makeNumber(), makeNumber(), makeNumber(), makeNumber(), makeNumber()));
    }

    @Test
    public void testHour() {
        assertNull(getLib().hour(null));
    }

    @Test
    public void testHourDiff() {
        assertNull(getLib().hourDiff(null, null));
    }

    @Test
    public void testMinute() {
        assertNull(getLib().minute(null));
    }

    @Test
    public void testSecond() {
        assertNull(getLib().second(null));
    }

    @Test
    public void testTimeOffset() {
        assertNull(getLib().timeOffset(null));
    }

    @Test
    public void testTimezone() {
        assertNull(getLib().timezone(null));
    }

    @Test
    public void testYears() {
        assertNull(getLib().years(null));
    }

    @Test
    public void testMonths() {
        assertNull(getLib().months(null));
    }

    @Test
    public void testDays() {
        assertNull(getLib().days(null));
    }

    @Test
    public void testHours() {
        assertNull(getLib().hours(null));
    }

    @Test
    public void testMinutes() {
        assertNull(getLib().minutes(null));
    }

    @Test
    public void testSeconds() {
        assertNull(getLib().seconds(null));
    }

    @Test
    public void testString() {
        assertEquals("null", getLib().string(null));
    }

    @Test
    public void testMinutesDiff() {
        assertNull(getLib().minutesDiff(null, null));
    }

    @Test
    public void testMonth() {
        assertNull(getLib().month(null));
    }

    @Test
    public void testMonthAdd() {
        assertNull(getLib().monthAdd(null, null));
    }

    @Test
    public void testMonthDiff() {
        assertNull(getLib().monthDiff(null, null));
    }

    @Test
    public void testNow() {
        assertNull(getLib().now());
    }

    @Test
    public void testToday() {
        assertNull(getLib().today());
    }

    @Test
    public void testWeekday() {
        assertNull(getLib().weekday(null));
    }

    @Test
    public void testYear() {
        assertNull(getLib().year(null));
    }

    @Test
    public void testYearAdd() {
        assertNull(getLib().yearAdd(null, null));
    }

    @Test
    public void testYearDiff() {
        assertNull(getLib().yearDiff(null, null));
    }

    @Test
    public void testAppend() {
        List nullSingleton = new ArrayList();
        nullSingleton.add(null);
        assertEquals(nullSingleton, getLib().append(null, null));
    }

    @Test
    public void testNumber() {
        assertNull(getLib().number(null));
        assertNull(getLib().number(null, null));
    }

    @Test
    public void testMid() {
        assertNull(getLib().mid(null, null, null));
    }

    @Test
    public void testLeft() {
        assertNull(getLib().left(null, null));
    }

    @Test
    public void testRight() {
        assertNull(getLib().right(null, null));
    }

    @Test
    public void testText() {
        assertNull(getLib().text(null, null));
    }

    @Test
    public void testTextOccurrences() {
        assertNull(getLib().textOccurrences(null, null));
    }

    @Test
    public void testContains() {
        assertNull(getLib().contains(null, null));
    }

    @Test
    public void testStartsWith() {
        assertNull(getLib().startsWith(null, null));
    }

    @Test
    public void testEndsWith() {
        assertNull(getLib().endsWith(null, null));
    }

    @Test
    public void testNot() {
        assertNull(getLib().not(null));
    }

    @Test
    public void testTime() {
        assertNull(getLib().time((String) null));
        assertNull(getLib().time((TIME) null));
        assertNull(getLib().time(null, null, null, null));
    }

    @Test
    public void testDateAndTime() {
        assertNull(getLib().dateAndTime(null));
        assertNull(getLib().dateAndTime(null, null));
    }

    @Test
    public void testDuration() {
        assertNull(getLib().duration(null));
    }

    @Test
    public void testAppendAll() {
        assertNull(getLib().appendAll(null, null));
    }

    @Test
    public void testRemove() {
        assertNull(getLib().remove(null, null));
    }

    @Test
    public void testRemoveAll() {
        assertNull(getLib().removeAll(null, null));
    }

    @Test
    public void testNotContainsAny() {
        assertNull(getLib().notContainsAny(null, null));
    }

    @Test
    public void testContainsOnly() {
        assertNull(getLib().containsOnly(null, null));
    }

    @Test
    public void testAreElementsOf() {
        assertNull(getLib().areElementsOf(null, null));
    }

    @Test
    public void testElementOf() {
        assertNull(getLib().elementOf(null, null));
    }

    @Test
    public void testZip() {
        assertNull(getLib().zip(null, null));
    }

    @Test
    public void testAvg() {
        assertNull(getLib().avg(null));
    }

    @Test
    public void testMax() {
        assertNull(getLib().max(null));
    }

    @Test
    public void testMedian() {
        assertNull(getLib().median(null));
    }

    @Test
    public void testMin() {
        assertNull(getLib().min(null));
    }

    @Test
    public void testMode() {
        assertNull(getLib().mode(null));
    }

    @Test
    public void testStringAdd() {
        assertNull(getLib().stringAdd(null, null));
    }

    @Test
    public void testConcat() {
        assertNull(getLib().concat(null));
    }

    @Test
    public void testIsAlpha() {
        assertNull(getLib().isAlpha(null));
    }

    @Test
    public void testIsAlphanumeric() {
        assertNull(getLib().isAlphanumeric(null));
    }

    @Test
    public void testIsNumeric() {
        assertNull(getLib().isNumeric(null));
    }

    @Test
    public void testIsSpaces() {
        assertNull(getLib().isSpaces(null));
    }

    @Test
    public void testLower() {
        assertNull(getLib().lower(null));
    }

    @Test
    public void testTrim() {
        assertNull(getLib().trim(null));
    }

    @Test
    public void testUpper() {
        assertNull(getLib().upper(null));
    }

    @Test
    public void testAnd() {
        assertNull(getLib().and(null));
    }

    @Test
    public void testOr() {
        assertNull(getLib().or(null));
    }

    @Test
    public void testListContains() {
        assertNull(getLib().listContains(null, null));
    }

    @Test
    public void testToDate() {
        assertNull(getLib().toDate(null));
    }

    @Test
    public void testToTime() {
        assertNull(getLib().toTime(null));
    }

    @Test
    public void testLen() {
        assertNull(getLib().len(null));
    }

    protected NUMBER makeNumber() {
        return getLib().number("1");
    }

    protected abstract BaseSignavioLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> getLib();
}