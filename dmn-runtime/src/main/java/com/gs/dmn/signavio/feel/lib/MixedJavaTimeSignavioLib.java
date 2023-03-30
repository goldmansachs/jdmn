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

import com.gs.dmn.feel.lib.type.bool.BooleanLib;
import com.gs.dmn.feel.lib.type.bool.BooleanType;
import com.gs.dmn.feel.lib.type.bool.DefaultBooleanLib;
import com.gs.dmn.feel.lib.type.bool.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.context.ContextType;
import com.gs.dmn.feel.lib.type.context.DefaultContextType;
import com.gs.dmn.feel.lib.type.function.DefaultFunctionType;
import com.gs.dmn.feel.lib.type.function.FunctionType;
import com.gs.dmn.feel.lib.type.list.DefaultListType;
import com.gs.dmn.feel.lib.type.list.ListType;
import com.gs.dmn.feel.lib.type.numeric.NumericType;
import com.gs.dmn.feel.lib.type.range.DefaultRangeType;
import com.gs.dmn.feel.lib.type.range.RangeType;
import com.gs.dmn.feel.lib.type.string.StringType;
import com.gs.dmn.feel.lib.type.time.*;
import com.gs.dmn.feel.lib.type.time.mixed.MixedDurationLib;
import com.gs.dmn.signavio.feel.lib.type.list.DefaultSignavioListLib;
import com.gs.dmn.signavio.feel.lib.type.list.SignavioListLib;
import com.gs.dmn.signavio.feel.lib.type.numeric.DefaultSignavioNumberLib;
import com.gs.dmn.signavio.feel.lib.type.numeric.DefaultSignavioNumericType;
import com.gs.dmn.signavio.feel.lib.type.numeric.SignavioNumberLib;
import com.gs.dmn.signavio.feel.lib.type.string.DefaultSignavioStringLib;
import com.gs.dmn.signavio.feel.lib.type.string.DefaultSignavioStringType;
import com.gs.dmn.signavio.feel.lib.type.string.SignavioStringLib;
import com.gs.dmn.signavio.feel.lib.type.time.SignavioDateTimeLib;
import com.gs.dmn.signavio.feel.lib.type.time.mixed.MixedSignavioDateTimeLib;
import com.gs.dmn.signavio.feel.lib.type.time.mixed.SignavioLocalDateType;
import com.gs.dmn.signavio.feel.lib.type.time.mixed.SignavioOffsetTimeType;
import com.gs.dmn.signavio.feel.lib.type.time.mixed.SignavioZonedDateTimeType;
import com.gs.dmn.signavio.feel.lib.type.time.xml.DefaultSignavioDurationType;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

public class MixedJavaTimeSignavioLib extends BaseSignavioLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    private static final NumericType<BigDecimal> NUMERIC_TYPE = new DefaultSignavioNumericType();
    private static final BooleanType BOOLEAN_TYPE = new DefaultBooleanType();
    private static final StringType STRING_TYPE = new DefaultSignavioStringType();
    private static final DateType<LocalDate, Duration> DATE_TYPE = new SignavioLocalDateType();
    private static final TimeType<OffsetTime, Duration> TIME_TYPE = new SignavioOffsetTimeType();
    private static final DateTimeType<ZonedDateTime, Duration> DATE_TIME_TYPE = new SignavioZonedDateTimeType();
    private static final DurationType<Duration, BigDecimal> DURATION_TYPE = new DefaultSignavioDurationType();
    private static final ListType LIST_TYPE = new DefaultListType();
    private static final ContextType CONTEXT_TYPE = new DefaultContextType();
    private static final RangeType RANGE_TYPE = new DefaultRangeType();
    private static final FunctionType FUNCTION_TYPE = new DefaultFunctionType();

    private static final SignavioNumberLib<BigDecimal> NUMBER_LIB = new DefaultSignavioNumberLib();
    private static final SignavioStringLib STRING_LIB = new DefaultSignavioStringLib();
    private static final BooleanLib BOOLEAN_LIB = new DefaultBooleanLib();
    private static final SignavioDateTimeLib DATE_TIME_LIB = new MixedSignavioDateTimeLib();
    private static final DurationLib<LocalDate, Duration> DURATION_LIB = new MixedDurationLib();
    private static final SignavioListLib<BigDecimal> LIST_LIB = new DefaultSignavioListLib();

    public static MixedJavaTimeSignavioLib INSTANCE = new MixedJavaTimeSignavioLib();

    public MixedJavaTimeSignavioLib() {
        this(NUMERIC_TYPE,
                BOOLEAN_TYPE,
                STRING_TYPE,
                DATE_TYPE,
                TIME_TYPE,
                DATE_TIME_TYPE,
                DURATION_TYPE,
                LIST_TYPE,
                CONTEXT_TYPE,
                RANGE_TYPE,
                FUNCTION_TYPE,
                NUMBER_LIB,
                STRING_LIB,
                BOOLEAN_LIB,
                DATE_TIME_LIB,
                DURATION_LIB,
                LIST_LIB
        );
    }

    protected MixedJavaTimeSignavioLib(
            NumericType<BigDecimal> numericType, BooleanType booleanType, StringType stringType,
            DateType<LocalDate, Duration> dateType, TimeType<OffsetTime, Duration> timeType, DateTimeType<ZonedDateTime, Duration> dateTimeType, DurationType<Duration, BigDecimal> durationType,
            ListType listType, ContextType contextType, RangeType rangeType, FunctionType functionType,
            SignavioNumberLib<BigDecimal> numberLib,
            SignavioStringLib stringLib,
            BooleanLib booleanLib,
            SignavioDateTimeLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime> dateTimeLib,
            DurationLib<LocalDate, Duration> durationLib,
            SignavioListLib<BigDecimal> listLib) {
        super(numericType, booleanType, stringType,
                dateType, timeType, dateTimeType, durationType,
                listType, contextType, rangeType, functionType,
                numberLib, stringLib, booleanLib, dateTimeLib, durationLib, listLib);
    }

    //
    // Date and time operations
    //
    public ZonedDateTime dayAdd(ZonedDateTime dateTime, BigDecimal daysToAdd) {
        try {
            return this.dateTimeLib.dayAddDateTime(dateTime, daysToAdd);
        } catch (Exception e) {
            String message = String.format("dayAdd(%s, %s)", dateTime, daysToAdd);
            logError(message, e);
            return null;
        }
    }

    public ZonedDateTime monthAdd(ZonedDateTime dateTime, BigDecimal monthsToAdd) {
        try {
            return this.dateTimeLib.monthAddDateTime(dateTime, monthsToAdd);
        } catch (Exception e) {
            String message = String.format("monthAdd(%s, %s)", dateTime, monthsToAdd);
            logError(message, e);
            return null;
        }
    }

    public ZonedDateTime yearAdd(ZonedDateTime dateTime, BigDecimal yearsToAdd) {
        try {
            return this.dateTimeLib.yearAddDateTime(dateTime, yearsToAdd);
        } catch (Exception e) {
            String message = String.format("yearAdd(%s, %s)", dateTime, yearsToAdd);
            logError(message, e);
            return null;
        }
    }

    //
    // Extra conversion functions
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
