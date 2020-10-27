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

import com.gs.dmn.feel.lib.DefaultFEELLib;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.feel.lib.stub.*;
import com.gs.dmn.feel.lib.type.*;
import com.gs.dmn.signavio.feel.lib.stub.SignavioDateTimeLibStub;
import com.gs.dmn.signavio.feel.lib.stub.SignavioListLibStub;
import com.gs.dmn.signavio.feel.lib.stub.SignavioNumberLibStub;
import com.gs.dmn.signavio.feel.lib.stub.SignavioStringLibStub;
import com.gs.dmn.signavio.feel.lib.type.list.SignavioListLib;
import com.gs.dmn.signavio.feel.lib.type.numeric.SignavioNumberLib;
import com.gs.dmn.signavio.feel.lib.type.string.SignavioStringLib;
import com.gs.dmn.signavio.feel.lib.type.time.SignavioDateTimeLib;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

public class DefaultSignavioLibExceptionsTest extends BaseSignavioLibExceptionsTest<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    @Override
    protected DefaultSignavioLib getLib() {
        NumericType<BigDecimal> numericType = new NumericTypeStub<>();
        BooleanType booleanType = new BooleanTypeStub();
        StringType stringType = new StringTypeStub();
        DateType<XMLGregorianCalendar, Duration> dateType = new DateTypeStub<>();
        TimeType<XMLGregorianCalendar, Duration> timeType = new TimeTypeStub<>();
        DateTimeType<XMLGregorianCalendar, Duration> dateTimeType = new DateTimeTypeStub<>();
        DurationType<Duration, BigDecimal> durationType = new DurationTypeStub<>();
        ListType listType = new ListTypeStub();
        ContextType contextType = new ContextTypeStub();
        StandardFEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> feelLib = new DefaultFEELLib();
        SignavioNumberLib<BigDecimal> numberLib = new SignavioNumberLibStub<>();
        SignavioStringLib stringLib = new SignavioStringLibStub();
        SignavioDateTimeLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar> dateTimeLib = new SignavioDateTimeLibStub<>();
        SignavioListLib listLib = new SignavioListLibStub();
        return new DefaultSignavioLib(
                numericType, booleanType, stringType,
                dateType, timeType, dateTimeType, durationType,
                listType, contextType,
                feelLib, numberLib, stringLib, dateTimeLib, listLib
        );
    }
}