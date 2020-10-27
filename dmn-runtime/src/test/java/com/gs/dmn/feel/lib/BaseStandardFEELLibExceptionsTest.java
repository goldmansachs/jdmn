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

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public abstract class BaseStandardFEELLibExceptionsTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends BaseFEELLibExceptionsTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    @Test
    public void testNumber() {
        assertNull(getLib().number(null));
        assertNull(getLib().number(null, null, null));
    }

    @Test
    public void testString() {
        assertNull(getLib().string(null));
    }

    @Test
    public void testDate() {
        assertNull(getLib().date((String) null));
        assertNull(getLib().date((DATE) null));
        assertNull(getLib().date(null, null, null));
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
    public void testYearsAndMonthsDuration() {
        assertNull(getLib().yearsAndMonthsDuration(null, null));
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
    public void testDecimal() {
        assertNull(getLib().decimal(null, null));
    }

    @Test
    public void testFloor() {
        assertNull(getLib().floor(null));
    }

    @Test
    public void testCeiling() {
        assertNull(getLib().ceiling(null));
    }

    @Test
    public void testAbs() {
        assertNull(getLib().abs(null));
    }

    @Test
    public void testIntModulo() {
        assertNull(getLib().intModulo(null, null));
    }

    @Test
    public void testModulo() {
        assertNull(getLib().modulo(null, null));
    }

    @Test
    public void testSqrt() {
        assertNull(getLib().sqrt(null));
    }

    @Test
    public void testLog() {
        assertNull(getLib().log(null));
    }

    @Test
    public void testExp() {
        assertNull(getLib().exp(null));
    }

    @Test
    public void testOdd() {
        assertNull(getLib().odd(null));
    }

    @Test
    public void testEven() {
        assertNull(getLib().even(null));
    }

    @Test
    public void testMean() {
        assertNull(getLib().mean((List) null));
        assertNull(getLib().mean());
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
    public void testStringLength() {
        assertNull(getLib().stringLength(null));
    }

    @Test
    public void testSubstring() {
        assertNull(getLib().substring(null, null));
        assertNull(getLib().substring(null, null, null));
    }

    @Test
    public void testUpperCase() {
        assertNull(getLib().upperCase(null));
    }

    @Test
    public void testLowerCase() {
        assertNull(getLib().lowerCase(null));
    }

    @Test
    public void testSubstringBefore() {
        assertNull(getLib().substringBefore(null, null));
    }

    @Test
    public void testSubstringAfter() {
        assertNull(getLib().substringAfter(null, null));
    }

    @Test
    public void testReplace() {
        assertNull(getLib().replace(null, null, null));
        assertNull(getLib().replace(null, null, null, null));
    }

    @Test
    public void testMatches() {
        assertNull(getLib().matches(null, null));
        assertNull(getLib().matches(null, null, null));
    }

    @Test
    public void testSplit() {
        assertNull(getLib().split(null, null));
    }

    @Test
    public void testAnd() {
        assertNull(getLib().and((List) null));
        assertNull(getLib().and());

        assertNull(getLib().booleanAnd((List) null));
        assertNull(getLib().booleanAnd());

        assertNull(getLib().binaryBooleanAnd(null, null));
    }

    @Test
    public void testAll() {
        assertNull(getLib().all((List) null));
        assertNull(getLib().all());
    }

    @Test
    public void testOr() {
        assertNull(getLib().or((List) null));
        assertNull(getLib().or());

        assertNull(getLib().booleanOr((List) null));
        assertNull(getLib().booleanOr());

        assertNull(getLib().binaryBooleanOr(null, null));
    }

    @Test
    public void testAny() {
        assertNull(getLib().any((List) null));
        assertNull(getLib().any());
    }

    @Test
    public void testNot() {
        assertNull(getLib().not(null));

        assertNull(getLib().booleanNot(null));
    }

    @Test
    public void testYear() {
        assertNull(getLib().year(null));
    }

    @Test
    public void testMonth() {
        assertNull(getLib().month(null));
    }

    @Test
    public void testDay() {
        assertNull(getLib().day(null));
    }

    @Test
    public void testWeekday() {
        assertNull(getLib().weekday(null));
    }

    @Test
    public void testHour() {
        assertNull(getLib().hour(null));
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
    public void testListContains() {
        assertNull(getLib().listContains(null, null));
    }

    @Test
    public void testAppend() {
        assertNull(getLib().append(null));
    }

    @Test
    public void testCount() {
        assertNull(getLib().count(null));
    }

    @Test
    public void testMin() {
        assertNull(getLib().min((List) null));
        assertNull(getLib().min());
    }

    @Test
    public void testMax() {
        assertNull(getLib().max((List) null));
        assertNull(getLib().max());
    }

    @Test
    public void testSum() {
        assertNull(getLib().sum((List) null));
        assertNull(getLib().sum());
    }

    @Test
    public void testSublist() {
        assertNull(getLib().sublist(null, null));
        assertNull(getLib().sublist(null, null, null));
    }

    @Test
    public void testConcatenate() {
        assertNull(getLib().concatenate((List) null));
        assertNull(getLib().concatenate((Object) null));
    }

    @Test
    public void testInsertBefore() {
        assertNull(getLib().insertBefore(null, null, null));
    }

    @Test
    public void testRemove() {
        assertNull(getLib().remove(null, null));
    }

    @Test
    public void testReverse() {
        assertNull(getLib().reverse(null));
    }

    @Test
    public void testIndexOf() {
        assertEquals(Arrays.asList(), getLib().indexOf(null, null));
    }

    @Test
    public void testUnion() {
        assertNull(getLib().union((List) null));
        assertNull(getLib().union((Object) null));
    }

    @Test
    public void testDistinctValues() {
        assertNull(getLib().distinctValues(null));
    }

    @Test
    public void testFlatten() {
        assertNull(getLib().flatten(null));
    }

    @Test
    public void testProduct() {
        assertNull(getLib().product((List) null));
        assertNull(getLib().product());
    }

    @Test
    public void testMedian() {
        assertNull(getLib().median((List) null));
        assertNull(getLib().median());
    }

    @Test
    public void testStddev() {
        assertNull(getLib().stddev((List) null));
        assertNull(getLib().stddev());
    }

    @Test
    public void testMode() {
        assertNull(getLib().mode((List) null));
        assertNull(getLib().mode());
    }

    @Test
    public void testCollect() {
        getLib().collect(null, null);
        assertNull(null);
    }

    @Test
    public void testSort() {
        assertNull(getLib().sort(null, null));
    }
}