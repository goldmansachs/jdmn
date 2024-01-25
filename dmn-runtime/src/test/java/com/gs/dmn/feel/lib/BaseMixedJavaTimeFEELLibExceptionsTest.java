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

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertNull;

public abstract class BaseMixedJavaTimeFEELLibExceptionsTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends BaseStandardFEELLibExceptionsTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    @Override
    @Test
    public void testDate() {
        super.testDate();

        assertNull(getBaseMixedJavaTimeLib().date((ZonedDateTime) null));
    }

    @Override
    @Test
    public void testTime() {
        super.testTime();

        assertNull(getBaseMixedJavaTimeLib().time((ZonedDateTime) null));
        assertNull(getBaseMixedJavaTimeLib().time((LocalDate) null));
    }

    @Override
    @Test
    public void testYearsAndMonthsDuration() {
        super.testYearsAndMonthsDuration();

        assertNull(getBaseMixedJavaTimeLib().yearsAndMonthsDuration((ZonedDateTime) null, (ZonedDateTime) null));
        assertNull(getBaseMixedJavaTimeLib().yearsAndMonthsDuration(null, (LocalDate) null));
        assertNull(getBaseMixedJavaTimeLib().yearsAndMonthsDuration((LocalDate) null, null));
    }

    private BaseMixedJavaTimeFEELLib<NUMBER> getBaseMixedJavaTimeLib() {
        return (BaseMixedJavaTimeFEELLib<NUMBER>) getLib();
    }
}