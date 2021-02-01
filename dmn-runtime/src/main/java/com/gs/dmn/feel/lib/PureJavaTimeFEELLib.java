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
import com.gs.dmn.feel.lib.type.range.DefaultRangeLib;
import com.gs.dmn.feel.lib.type.range.DefaultRangeType;
import com.gs.dmn.feel.lib.type.range.RangeLib;
import com.gs.dmn.feel.lib.type.range.RangeType;
import com.gs.dmn.feel.lib.type.string.DefaultStringLib;
import com.gs.dmn.feel.lib.type.string.DefaultStringType;
import com.gs.dmn.feel.lib.type.string.StringLib;
import com.gs.dmn.feel.lib.type.time.DateTimeLib;
import com.gs.dmn.feel.lib.type.time.DurationLib;
import com.gs.dmn.feel.lib.type.time.pure.*;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;

public class PureJavaTimeFEELLib extends BaseStandardFEELLib<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount> implements StandardFEELLib<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount> {
    private static final NumericType<BigDecimal> NUMERIC_TYPE = new DefaultNumericType();
    private static final BooleanType BOOLEAN_TYPE = new DefaultBooleanType();
    private static final StringType STRING_TYPE = new DefaultStringType();
    private static final DateType<LocalDate, TemporalAmount> DATE_TYPE = new LocalDateType();
    private static final TimeType<Temporal, TemporalAmount> TIME_TYPE = new TemporalTimeType();
    private static final DateTimeType<Temporal, TemporalAmount> DATE_TIME_TYPE = new TemporalDateTimeType();
    private static final DurationType<TemporalAmount, BigDecimal> DURATION_TYPE = new TemporalAmountDurationType();
    private static final ListType LIST_TYPE = new DefaultListType();
    private static final ContextType CONTEXT_TYPE = new DefaultContextType();
    private static final RangeType RANGE_TYPE = new DefaultRangeType();

    private static final DefaultNumericLib NUMERIC_LIB = new DefaultNumericLib();
    private static final DefaultStringLib STRING_LIB = new DefaultStringLib();
    private static final DefaultBooleanLib BOOLEAN_LIB = new DefaultBooleanLib();
    private static final DateTimeLib DATE_TIME_LIB = new TemporalDateTimeLib(DefaultFEELLib.DATA_TYPE_FACTORY);
    private static final TemporalAmountDurationLib DURATION_LIB = new TemporalAmountDurationLib();
    private static final ListLib LIST_LIB = new DefaultListLib();
    private static final RangeLib RANGE_LIB = new DefaultRangeLib();

    public static final PureJavaTimeFEELLib INSTANCE = new PureJavaTimeFEELLib();

    public PureJavaTimeFEELLib() {
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
                NUMERIC_LIB,
                STRING_LIB,
                BOOLEAN_LIB,
                DATE_TIME_LIB,
                DURATION_LIB,
                LIST_LIB,
                RANGE_LIB
        );
    }

    protected PureJavaTimeFEELLib(
            NumericType<BigDecimal> numericType,
            BooleanType booleanType,
            StringType stringType,
            DateType<LocalDate, TemporalAmount> dateType,
            TimeType<Temporal, TemporalAmount> timeType,
            DateTimeType<Temporal, TemporalAmount> dateTimeType,
            DurationType<TemporalAmount, BigDecimal> durationType,
            ListType listType, ContextType contextType, RangeType rangeType,
            NumericLib<BigDecimal> numericLib,
            StringLib stringLib,
            BooleanLib booleanLib,
            DateTimeLib<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount> dateTimeLib,
            DurationLib<LocalDate, TemporalAmount> durationLib,
            ListLib listLib,
            RangeLib rangeLib) {
        super(numericType, booleanType, stringType,
                dateType, timeType, dateTimeType, durationType,
                listType, contextType, rangeType,
                numericLib, stringLib, booleanLib, dateTimeLib, durationLib, listLib, rangeLib
        );
    }

    //
    // Conversion functions
    //
    public TemporalAmount yearsAndMonthsDuration(Temporal from, Temporal to) {
        try {
            return this.durationLib.yearsAndMonthsDuration(toDate(from), toDate(to));
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    //
    // Date properties
    //
    public BigDecimal year(Temporal dateTime) {
        try {
            return valueOf(this.dateTimeLib.yearDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("year(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public BigDecimal month(Temporal dateTime) {
        try {
            return valueOf(this.dateTimeLib.monthDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("month(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public BigDecimal day(Temporal dateTime) {
        try {
            return valueOf(this.dateTimeLib.dayDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("day(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public BigDecimal weekday(Temporal dateTime) {
        try {
            return valueOf(this.dateTimeLib.weekdayDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("weekday(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    //
    // Temporal functions
    //
    public BigDecimal dayOfYear(Temporal dateTime) {
        try {
            return valueOf(this.dateTimeLib.dayOfYearDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("dayOfYear(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public String dayOfWeek(Temporal dateTime) {
        try {
            return this.dateTimeLib.dayOfWeekDateTime(dateTime);
        } catch (Exception e) {
            String message = String.format("dayOfWeek(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public BigDecimal weekOfYear(Temporal dateTime) {
        try {
            return valueOf(this.dateTimeLib.weekOfYearDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("weekday(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public String monthOfYear(Temporal dateTime) {
        try {
            return this.dateTimeLib.monthOfYearDateTime(dateTime);
        } catch (Exception e) {
            String message = String.format("weekday(%s)", dateTime);
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

    @Override
    protected boolean isDate(Object value) {
        return value instanceof LocalDate;
    }

    @Override
    protected boolean isTime(Object value) {
        return value instanceof OffsetTime
        || value instanceof LocalTime;
    }

    @Override
    protected boolean isDateTime(Object value) {
        return value instanceof LocalDateTime
                || value instanceof OffsetDateTime
                || value instanceof ZonedDateTime;
    }

    @Override
    protected boolean isDuration(Object value) {
        return value instanceof TemporalAmount;
    }
}
