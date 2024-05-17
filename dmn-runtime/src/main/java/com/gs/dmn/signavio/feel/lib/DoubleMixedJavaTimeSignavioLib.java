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
import com.gs.dmn.signavio.feel.lib.type.numeric.DoubleSignavioNumberLib;
import com.gs.dmn.signavio.feel.lib.type.numeric.DoubleSignavioNumericType;
import com.gs.dmn.signavio.feel.lib.type.numeric.SignavioNumberLib;
import com.gs.dmn.signavio.feel.lib.type.string.DefaultSignavioStringLib;
import com.gs.dmn.signavio.feel.lib.type.string.DefaultSignavioStringType;
import com.gs.dmn.signavio.feel.lib.type.string.SignavioStringLib;
import com.gs.dmn.signavio.feel.lib.type.time.SignavioDateTimeLib;
import com.gs.dmn.signavio.feel.lib.type.time.mixed.MixedSignavioDateTimeLib;
import com.gs.dmn.signavio.feel.lib.type.time.mixed.SignavioLocalDateType;
import com.gs.dmn.signavio.feel.lib.type.time.mixed.SignavioOffsetTimeType;
import com.gs.dmn.signavio.feel.lib.type.time.mixed.SignavioZonedDateTimeType;
import com.gs.dmn.signavio.feel.lib.type.time.xml.DoubleSignavioDurationType;

import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

/**
 * @deprecated  As of release 8.3.0, replaced by {@link PureJavaTimeSignavioLib}
 */
@Deprecated
public class DoubleMixedJavaTimeSignavioLib extends BaseSignavioLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    private static final NumericType<Double> NUMERIC_TYPE = new DoubleSignavioNumericType();
    private static final BooleanType BOOLEAN_TYPE = new DefaultBooleanType();
    private static final StringType STRING_TYPE = new DefaultSignavioStringType();
    private static final DateType<LocalDate, Duration> DATE_TYPE = new SignavioLocalDateType();
    private static final TimeType<OffsetTime, Duration> TIME_TYPE = new SignavioOffsetTimeType();
    private static final DateTimeType<ZonedDateTime, Duration> DATE_TIME_TYPE = new SignavioZonedDateTimeType();
    private static final DurationType<Duration, Double> DURATION_TYPE = new DoubleSignavioDurationType();
    private static final ListType LIST_TYPE = new DefaultListType();
    private static final ContextType CONTEXT_TYPE = new DefaultContextType();
    private static final RangeType RANGE_TYPE = new DefaultRangeType();
    private static final FunctionType FUNCTION_TYPE = new DefaultFunctionType();

    private static final SignavioNumberLib<Double> NUMBER_LIB = new DoubleSignavioNumberLib();
    private static final SignavioStringLib STRING_LIB = new DefaultSignavioStringLib();
    private static final BooleanLib BOOLEAN_LIB = new DefaultBooleanLib();
    private static final SignavioDateTimeLib DATE_TIME_LIB = new MixedSignavioDateTimeLib();
    private static final DurationLib<LocalDate, Duration> DURATION_LIB = new MixedDurationLib();
    private static final SignavioListLib LIST_LIB = new DefaultSignavioListLib();

    public static final DoubleMixedJavaTimeSignavioLib INSTANCE = new DoubleMixedJavaTimeSignavioLib();

    public DoubleMixedJavaTimeSignavioLib() {
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

    protected DoubleMixedJavaTimeSignavioLib(
            NumericType<Double> numericType, BooleanType booleanType, StringType stringType,
            DateType<LocalDate, Duration> dateType, TimeType<OffsetTime, Duration> timeType, DateTimeType<ZonedDateTime, Duration> dateTimeType, DurationType<Duration, Double> durationType,
            ListType listType, ContextType contextType, RangeType rangeType, FunctionType functionType,
            SignavioNumberLib<Double> numberLib,
            SignavioStringLib stringLib,
            BooleanLib booleanLib,
            SignavioDateTimeLib<Double, LocalDate, OffsetTime, ZonedDateTime> dateTimeLib,
            DurationLib<LocalDate, Duration> durationLib,
            SignavioListLib listLib) {
        super(numericType, booleanType, stringType,
                dateType, timeType, dateTimeType, durationType,
                listType, contextType, rangeType, functionType,
                numberLib, stringLib, booleanLib, dateTimeLib, durationLib, listLib);
    }

    //
    // Date and time operations
    //
    public ZonedDateTime dayAdd(ZonedDateTime dateTime, Double daysToAdd) {
        try {
            return this.dateTimeLib.dayAddDateTime(dateTime, daysToAdd);
        } catch (Exception e) {
            String message = String.format("dayAdd(%s, %s)", dateTime, daysToAdd);
            logError(message, e);
            return null;
        }
    }

    public ZonedDateTime monthAdd(ZonedDateTime dateTime, Double monthsToAdd) {
        try {
            return this.dateTimeLib.monthAddDateTime(dateTime, monthsToAdd);
        } catch (Exception e) {
            String message = String.format("monthAdd(%s, %s)", dateTime, monthsToAdd);
            logError(message, e);
            return null;
        }
    }

    public ZonedDateTime yearAdd(ZonedDateTime dateTime, Double yearsToAdd) {
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
    protected Double valueOf(long number) {
        return Double.valueOf(number);
    }

    @Override
    protected int intValue(Double number) {
        return number.intValue();
    }
}
