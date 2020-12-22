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

import com.gs.dmn.feel.lib.type.*;
import com.gs.dmn.feel.lib.type.bool.BooleanLib;
import com.gs.dmn.feel.lib.type.bool.DefaultBooleanLib;
import com.gs.dmn.feel.lib.type.context.DefaultContextType;
import com.gs.dmn.feel.lib.type.list.DefaultListLib;
import com.gs.dmn.feel.lib.type.list.DefaultListType;
import com.gs.dmn.feel.lib.type.list.ListLib;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.numeric.DefaultNumericLib;
import com.gs.dmn.feel.lib.type.numeric.DefaultNumericType;
import com.gs.dmn.feel.lib.type.numeric.NumericLib;
import com.gs.dmn.feel.lib.type.string.DefaultStringLib;
import com.gs.dmn.feel.lib.type.string.DefaultStringType;
import com.gs.dmn.feel.lib.type.string.StringLib;
import com.gs.dmn.feel.lib.type.time.DateTimeLib;
import com.gs.dmn.feel.lib.type.time.DurationLib;
import com.gs.dmn.feel.lib.type.time.xml.*;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

public class DefaultFEELLib extends BaseStandardFEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> implements StandardFEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    public static final DatatypeFactory DATA_TYPE_FACTORY = XMLDatataypeFactory.newInstance();

    private static final NumericType<BigDecimal> NUMERIC_TYPE = new DefaultNumericType(LOGGER);
    private static final BooleanType BOOLEAN_TYPE = new DefaultBooleanType(LOGGER);
    private static final StringType STRING_TYPE = new DefaultStringType(LOGGER);
    private static final DateType<XMLGregorianCalendar, Duration> DATE_TYPE = new DefaultDateType(LOGGER, DATA_TYPE_FACTORY);
    private static final TimeType<XMLGregorianCalendar, Duration> TIME_TYPE = new DefaultTimeType(LOGGER, DATA_TYPE_FACTORY);
    private static final DateTimeType<XMLGregorianCalendar, Duration> DATE_TIME_TYPE = new DefaultDateTimeType(LOGGER, DATA_TYPE_FACTORY);
    private static final DurationType<Duration, BigDecimal> DURATION_TYPE = new DefaultDurationType(LOGGER, DATA_TYPE_FACTORY);
    private static final ListType LIST_TYPE = new DefaultListType(LOGGER);
    private static final ContextType CONTEXT_TYPE = new DefaultContextType(LOGGER);

    private static final NumericLib<BigDecimal> NUMERIC_LIB = new DefaultNumericLib();
    private static final StringLib STRING_LIB = new DefaultStringLib();
    private static final BooleanLib BOOLEAN_LIB = new DefaultBooleanLib();
    private static final DateTimeLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> DATE_TIME_LIB = new DefaultDateTimeLib(DATA_TYPE_FACTORY);
    private static final DurationLib<XMLGregorianCalendar, Duration> DURATION_LIB = new DefaultDurationLib(DATA_TYPE_FACTORY);
    private static final ListLib LIST_LIB = new DefaultListLib();

    public static final DefaultFEELLib INSTANCE = new DefaultFEELLib();

    public DefaultFEELLib() {
        this(NUMERIC_TYPE,
                BOOLEAN_TYPE,
                STRING_TYPE,
                DATE_TYPE,
                TIME_TYPE,
                DATE_TIME_TYPE,
                DURATION_TYPE,
                LIST_TYPE,
                CONTEXT_TYPE,
                NUMERIC_LIB,
                STRING_LIB,
                BOOLEAN_LIB,
                DATE_TIME_LIB,
                DURATION_LIB,
                LIST_LIB
        );
    }

    protected DefaultFEELLib(
            NumericType<BigDecimal> numericType,
            BooleanType booleanType,
            StringType stringType,
            DateType<XMLGregorianCalendar, Duration> dateType,
            TimeType<XMLGregorianCalendar, Duration> timeType,
            DateTimeType<XMLGregorianCalendar, Duration> dateTimeType,
            DurationType<Duration, BigDecimal> durationType,
            ListType listType,
            ContextType contextType,
            NumericLib<BigDecimal> numericLib,
            StringLib stringLib,
            BooleanLib booleanLib,
            DateTimeLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> dateTimeLib,
            DurationLib<XMLGregorianCalendar, Duration> durationLib,
            ListLib listLib) {
        super(numericType, booleanType, stringType,
                dateType, timeType, dateTimeType, durationType,
                listType, contextType,
                numericLib, stringLib, booleanLib, dateTimeLib, durationLib, listLib
        );
    }

    //
    // Constructors
    //
    @Override
    protected BigDecimal valueOf(long number) {
        return BigDecimal.valueOf(number);
    }

    @Override
    protected int intValue(BigDecimal number) {
        return number.intValue();
    }
}
