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

import java.util.List;

import static org.junit.Assert.assertNull;

public abstract class BaseFEELLibExceptionsTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    @Test
    public void testNumericAdd() {
        assertNull(getLib().numericAdd(null, null));
    }

    @Test
    public void testNumericSubtract() {
        assertNull(getLib().numericSubtract(null, null));
    }

    @Test
    public void testNumericMultiply() {
        assertNull(getLib().numericMultiply(null, null));
    }

    @Test
    public void testNumericDivide() {
        assertNull(getLib().numericDivide(null, null));
    }

    @Test
    public void testNumericUnaryMinus() {
        assertNull(getLib().numericUnaryMinus(null));
    }

    @Test
    public void testNumericExponentiation() {
        assertNull(getLib().numericExponentiation(null, null));
    }

    @Test
    public void testNumericEqual() {
        assertNull(getLib().numericEqual(null, null));
    }

    @Test
    public void testNumericNotEqual() {
        assertNull(getLib().numericNotEqual(null, null));
    }

    @Test
    public void testNumericLessThan() {
        assertNull(getLib().numericLessThan(null, null));
    }

    @Test
    public void testNumericGreaterThan() {
        assertNull(getLib().numericGreaterThan(null, null));
    }

    @Test
    public void testNumericLessEqualThan() {
        assertNull(getLib().numericLessEqualThan(null, null));
    }

    @Test
    public void testNumericGreaterEqualThan() {
        assertNull(getLib().numericGreaterEqualThan(null, null));
    }

    @Test
    public void testBooleanNot() {
        assertNull(getLib().booleanNot((Object) null));
        assertNull(getBaseFEELLib().booleanNot(null));
    }

    @Test
    public void testBooleanOr() {
        assertNull(getLib().booleanOr((List) null));
        assertNull(getLib().booleanOr((Object) null));
        assertNull(getBaseFEELLib().booleanOr());
        assertNull(getBaseFEELLib().booleanOr((Boolean) null));
    }

    @Test
    public void testBinaryBooleanOr() {
        assertNull(getLib().binaryBooleanOr((Object) null, null));
        assertNull(getBaseFEELLib().binaryBooleanOr(null, null));
    }

    @Test
    public void testBooleanAnd() {
        assertNull(getLib().booleanAnd((List) null));
        assertNull(getLib().booleanAnd((Object) null));
        assertNull(getBaseFEELLib().booleanAnd());
        assertNull(getBaseFEELLib().booleanAnd((Boolean) null));
    }

    @Test
    public void testBinaryBooleanAnd() {
        assertNull(getLib().binaryBooleanAnd((Object) null, null));
        assertNull(getBaseFEELLib().binaryBooleanAnd(null, null));
    }

    @Test
    public void testBooleanEqual() {
        assertNull(getLib().booleanEqual(null, null));
    }

    @Test
    public void testBooleanNotEqual() {
        assertNull(getLib().booleanNotEqual(null, null));
    }

    @Test
    public void testStringEqual() {
        assertNull(getLib().stringEqual(null, null));
    }

    @Test
    public void testStringNotEqual() {
        assertNull(getLib().stringNotEqual(null, null));
    }

    @Test
    public void testStringAdd() {
        assertNull(getLib().stringAdd(null, null));
    }

    @Test
    public void testStringLessThan() {
        assertNull(getLib().stringLessThan(null, null));
    }

    @Test
    public void testStringGreaterThan() {
        assertNull(getLib().stringGreaterThan(null, null));
    }

    @Test
    public void testStringLessEqualThan() {
        assertNull(getLib().stringLessEqualThan(null, null));
    }

    @Test
    public void testStringGreaterEqualThan() {
        assertNull(getLib().stringGreaterEqualThan(null, null));
    }

    @Test
    public void testDateEqual() {
        assertNull(getLib().dateEqual(null, null));
    }

    @Test
    public void testDateNotEqual() {
        assertNull(getLib().dateNotEqual(null, null));
    }

    @Test
    public void testDateLessThan() {
        assertNull(getLib().dateLessThan(null, null));
    }

    @Test
    public void testDateGreaterThan() {
        assertNull(getLib().dateGreaterThan(null, null));
    }

    @Test
    public void testDateLessEqualThan() {
        assertNull(getLib().dateLessEqualThan(null, null));
    }

    @Test
    public void testDateGreaterEqualThan() {
        assertNull(getLib().dateGreaterEqualThan(null, null));
    }

    @Test
    public void testDateSubtract() {
        assertNull(getLib().dateSubtract(null, null));
    }

    @Test
    public void testDateAddDuration() {
        assertNull(getLib().dateAddDuration(null, null));
    }

    @Test
    public void testDateSubtractDuration() {
        assertNull(getLib().dateSubtractDuration(null, null));
    }

    @Test
    public void testTimeEqual() {
        assertNull(getLib().timeEqual(null, null));
    }

    @Test
    public void testTimeNotEqual() {
        assertNull(getLib().timeNotEqual(null, null));
    }

    @Test
    public void testTimeLessThan() {
        assertNull(getLib().timeLessThan(null, null));
    }

    @Test
    public void testTimeGreaterThan() {
        assertNull(getLib().timeGreaterThan(null, null));
    }

    @Test
    public void testTimeLessEqualThan() {
        assertNull(getLib().timeLessEqualThan(null, null));
    }

    @Test
    public void testTimeGreaterEqualThan() {
        assertNull(getLib().timeGreaterEqualThan(null, null));
    }

    @Test
    public void testTimeSubtract() {
        assertNull(getLib().timeSubtract(null, null));
    }

    @Test
    public void testTimeAddDuration() {
        assertNull(getLib().timeAddDuration(null, null));
    }

    @Test
    public void testTimeSubtractDuration() {
        assertNull(getLib().timeSubtractDuration(null, null));
    }

    @Test
    public void testDateTimeEqual() {
        assertNull(getLib().dateTimeEqual(null, null));
    }

    @Test
    public void testDateTimeNotEqual() {
        assertNull(getLib().dateTimeNotEqual(null, null));
    }

    @Test
    public void testDateTimeLessThan() {
        assertNull(getLib().dateTimeLessThan(null, null));
    }

    @Test
    public void testDateTimeGreaterThan() {
        assertNull(getLib().dateTimeGreaterThan(null, null));
    }

    @Test
    public void testDateTimeLessEqualThan() {
        assertNull(getLib().dateTimeLessEqualThan(null, null));
    }

    @Test
    public void testDateTimeGreaterEqualThan() {
        assertNull(getLib().dateTimeGreaterEqualThan(null, null));
    }

    @Test
    public void testDateTimeSubtract() {
        assertNull(getLib().dateTimeSubtract(null, null));
    }

    @Test
    public void testDateTimeAddDuration() {
        assertNull(getLib().dateTimeAddDuration(null, null));
    }

    @Test
    public void testDateTimeSubtractDuration() {
        assertNull(getLib().dateTimeSubtractDuration(null, null));
    }

    @Test
    public void testDurationEqual() {
        assertNull(getLib().durationEqual(null, null));
    }

    @Test
    public void testDurationNotEqual() {
        assertNull(getLib().durationNotEqual(null, null));
    }

    @Test
    public void testDurationLessThan() {
        assertNull(getLib().durationLessThan(null, null));
    }

    @Test
    public void testDurationGreaterThan() {
        assertNull(getLib().durationGreaterThan(null, null));
    }

    @Test
    public void testDurationLessEqualThan() {
        assertNull(getLib().durationLessEqualThan(null, null));
    }

    @Test
    public void testDurationGreaterEqualThan() {
        assertNull(getLib().durationGreaterEqualThan(null, null));
    }

    @Test
    public void testDurationAdd() {
        assertNull(getLib().durationAdd(null, null));
    }

    @Test
    public void testDurationSubtract() {
        assertNull(getLib().durationSubtract(null, null));
    }

    @Test
    public void testDurationMultiply() {
        assertNull(getLib().durationMultiply(null, null));
    }

    @Test
    public void testDurationDivide() {
        assertNull(getLib().durationDivide(null, null));
    }

    @Test
    public void testListEqual() {
        assertNull(getLib().listEqual(null, null));
    }

    @Test
    public void testListNotEqual() {
        assertNull(getLib().listNotEqual(null, null));
    }

    @Test
    public void testContextEqual() {
        assertNull(getLib().contextEqual(null, null));
    }

    @Test
    public void testContextNotEqual() {
        assertNull(getLib().contextNotEqual(null, null));
    }

    @Test
    public void testGetEntries() {
        assertNull(getLib().getEntries(null));
    }

    @Test
    public void testGetValue() {
        assertNull(getLib().getValue(null, null));
    }

    protected abstract StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> getLib();

    private BaseFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> getBaseFEELLib() {
        return (BaseFEELLib) getLib();
    }
}