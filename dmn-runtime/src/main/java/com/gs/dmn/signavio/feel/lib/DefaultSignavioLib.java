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
import com.gs.dmn.feel.lib.type.*;
import com.gs.dmn.feel.lib.type.context.DefaultContextType;
import com.gs.dmn.feel.lib.type.list.DefaultListType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.signavio.feel.lib.type.list.SignavioListLib;
import com.gs.dmn.signavio.feel.lib.type.numeric.DefaultSignavioNumberLib;
import com.gs.dmn.signavio.feel.lib.type.numeric.DefaultSignavioNumericType;
import com.gs.dmn.signavio.feel.lib.type.numeric.SignavioNumberLib;
import com.gs.dmn.signavio.feel.lib.type.string.DefaultSignavioStringLib;
import com.gs.dmn.signavio.feel.lib.type.string.DefaultSignavioStringType;
import com.gs.dmn.signavio.feel.lib.type.string.SignavioStringLib;
import com.gs.dmn.signavio.feel.lib.type.time.SignavioDateTimeLib;
import com.gs.dmn.signavio.feel.lib.type.time.xml.*;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

import static com.gs.dmn.feel.lib.DefaultFEELLib.DATA_TYPE_FACTORY;

public class DefaultSignavioLib extends BaseSignavioLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    private static final NumericType<BigDecimal> NUMERIC_TYPE = new DefaultSignavioNumericType(LOGGER);
    private static final BooleanType BOOLEAN_TYPE = new DefaultBooleanType(LOGGER);
    private static final StringType STRING_TYPE = new DefaultSignavioStringType(LOGGER);
    private static final DateType<XMLGregorianCalendar, Duration> DATE_TYPE = new DefaultSignavioDateType(LOGGER, DATA_TYPE_FACTORY);
    private static final TimeType<XMLGregorianCalendar, Duration> TIME_TYPE = new DefaultSignavioTimeType(LOGGER, DATA_TYPE_FACTORY);
    private static final DateTimeType<XMLGregorianCalendar, Duration> DATE_TIME_TYPE = new DefaultSignavioDateTimeType(LOGGER, DATA_TYPE_FACTORY);
    private static final DurationType<Duration, BigDecimal> DURATION_TYPE = new DefaultSignavioDurationType(LOGGER, DATA_TYPE_FACTORY);
    private static final ListType LIST_TYPE = new DefaultListType(LOGGER);
    private static final ContextType CONTEXT_TYPE = new DefaultContextType(LOGGER);

    private static final DefaultFEELLib FEEL_LIB = new DefaultFEELLib();
    private static final SignavioNumberLib<BigDecimal> NUMBER_LIB = new DefaultSignavioNumberLib();
    private static final SignavioStringLib STRING_LIB = new DefaultSignavioStringLib();
    private static final SignavioDateTimeLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar> DATE_TIME_LIB = new DefaultSignavioDateTimeLib();
    private static final SignavioListLib LIST_LIB = new SignavioListLib();

    public static final DefaultSignavioLib INSTANCE = new DefaultSignavioLib();

    public DefaultSignavioLib() {
        this(NUMERIC_TYPE,
                BOOLEAN_TYPE,
                STRING_TYPE,
                DATE_TYPE,
                TIME_TYPE,
                DATE_TIME_TYPE,
                DURATION_TYPE,
                LIST_TYPE,
                CONTEXT_TYPE,
                FEEL_LIB,
                NUMBER_LIB,
                STRING_LIB,
                DATE_TIME_LIB,
                LIST_LIB
        );
    }

    protected DefaultSignavioLib(
            NumericType<BigDecimal> numericType, BooleanType booleanType, StringType stringType,
            DateType<XMLGregorianCalendar, Duration> dateType, TimeType<XMLGregorianCalendar, Duration> timeType, DateTimeType<XMLGregorianCalendar, Duration> dateTimeType, DurationType<Duration, BigDecimal> durationType,
            ListType listType, ContextType contextType,
            StandardFEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> feelLib,
            SignavioNumberLib<BigDecimal> numberLib,
            SignavioStringLib stringLib,
            SignavioDateTimeLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar> dateTimeLib,
            SignavioListLib listLib) {
        super(numericType, booleanType, stringType,
                dateType, timeType, dateTimeType, durationType,
                listType, contextType,
                feelLib, numberLib, stringLib, dateTimeLib, listLib);
    }
}
