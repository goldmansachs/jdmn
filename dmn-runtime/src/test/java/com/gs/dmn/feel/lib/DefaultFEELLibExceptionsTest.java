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

import com.gs.dmn.feel.lib.stub.*;
import com.gs.dmn.feel.lib.type.*;
import com.gs.dmn.feel.lib.type.bool.BooleanLib;
import com.gs.dmn.feel.lib.type.list.ListLib;
import com.gs.dmn.feel.lib.type.numeric.NumericLib;
import com.gs.dmn.feel.lib.type.string.StringLib;
import com.gs.dmn.feel.lib.type.time.DateTimeLib;
import com.gs.dmn.feel.lib.type.time.DurationLib;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

public class DefaultFEELLibExceptionsTest extends BaseStandardFEELLibExceptionsTest<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    @Override
    protected DefaultFEELLib getLib() {
        NumericType<BigDecimal> numericType = new NumericTypeStub<>();
        BooleanType booleanType = new BooleanTypeStub();
        StringType stringType = new StringTypeStub();
        DateType<XMLGregorianCalendar, Duration> dateType = new DateTypeStub<>();
        TimeType<XMLGregorianCalendar, Duration> timeType = new TimeTypeStub<>();
        DateTimeType<XMLGregorianCalendar, Duration> dateTimeType = new DateTimeTypeStub<>();
        DurationType<Duration, BigDecimal> durationType = new DurationTypeStub<>();
        ListType listType = new ListTypeStub();
        ContextType contextType = new ContextTypeStub();
        NumericLib<BigDecimal> numericLib = new NumericLibStub<>();
        StringLib stringLib = new StringLibStub();
        BooleanLib booleanLib = new BooleanLibStub();
        DateTimeLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> dateTimeLib = new DateTimeLibStub<>();
        DurationLib<XMLGregorianCalendar, Duration> durationLib = new DurationLibStub<>();
        ListLib listLib = new ListLibStub();
        return new DefaultFEELLib(numericType, booleanType, stringType,
                dateType, timeType, dateTimeType, durationType,
                listType, contextType,
                numericLib, stringLib, booleanLib, dateTimeLib, durationLib, listLib);
    }
}