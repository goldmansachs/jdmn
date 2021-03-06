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

import com.gs.dmn.feel.lib.type.bool.BooleanLib;
import com.gs.dmn.feel.lib.type.bool.BooleanType;
import com.gs.dmn.feel.lib.type.bool.DefaultBooleanLib;
import com.gs.dmn.feel.lib.type.bool.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.context.ContextType;
import com.gs.dmn.feel.lib.type.context.DefaultContextType;
import com.gs.dmn.feel.lib.type.function.DefaultFunctionType;
import com.gs.dmn.feel.lib.type.function.FunctionType;
import com.gs.dmn.feel.lib.type.list.DefaultListLib;
import com.gs.dmn.feel.lib.type.list.DefaultListType;
import com.gs.dmn.feel.lib.type.list.ListLib;
import com.gs.dmn.feel.lib.type.list.ListType;
import com.gs.dmn.feel.lib.type.numeric.NumericLib;
import com.gs.dmn.feel.lib.type.numeric.NumericType;
import com.gs.dmn.feel.lib.type.range.DefaultRangeLib;
import com.gs.dmn.feel.lib.type.range.DefaultRangeType;
import com.gs.dmn.feel.lib.type.range.RangeLib;
import com.gs.dmn.feel.lib.type.range.RangeType;
import com.gs.dmn.feel.lib.type.string.DefaultStringLib;
import com.gs.dmn.feel.lib.type.string.DefaultStringType;
import com.gs.dmn.feel.lib.type.string.StringLib;
import com.gs.dmn.feel.lib.type.string.StringType;
import com.gs.dmn.feel.lib.type.time.*;
import com.gs.dmn.feel.lib.type.time.mixed.*;

import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

public abstract class BaseMixedJavaTimeFEELLib<NUMBER> extends BaseStandardFEELLib<NUMBER, LocalDate, OffsetTime, ZonedDateTime, Duration> implements StandardFEELLib<NUMBER, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    private static final BooleanType BOOLEAN_TYPE = new DefaultBooleanType();
    private static final StringType STRING_TYPE = new DefaultStringType();
    private static final DateType<LocalDate, Duration> DATE_TYPE = new LocalDateType();
    private static final TimeType<OffsetTime, Duration> TIME_TYPE = new OffsetTimeType();
    private static final DateTimeType<ZonedDateTime, Duration> DATE_TIME_TYPE = new ZonedDateTimeType();
    private static final ListType LIST_TYPE = new DefaultListType();
    private static final ContextType CONTEXT_TYPE = new DefaultContextType();
    private static final RangeType RANGE_TYPE = new DefaultRangeType();
    private static final FunctionType FUNCTION_TYPE = new DefaultFunctionType();

    private static final StringLib STRING_LIB = new DefaultStringLib();
    private static final BooleanLib BOOLEAN_LIB = new DefaultBooleanLib();
    private static final DateTimeLib DATE_TIME_LIB = new MixedDateTimeLib();
    private static final DurationLib<LocalDate, Duration> DURATION_LIB = new MixedDurationLib();
    private static final ListLib LIST_LIB = new DefaultListLib();
    private static final RangeLib RANGE_LIB = new DefaultRangeLib();

    protected BaseMixedJavaTimeFEELLib(NumericType<NUMBER> numericType, DurationType<Duration, NUMBER> durationType, NumericLib<NUMBER> numericLib) {
        this(numericType,
                BOOLEAN_TYPE,
                STRING_TYPE,
                DATE_TYPE,
                TIME_TYPE,
                DATE_TIME_TYPE,
                durationType,
                LIST_TYPE,
                CONTEXT_TYPE,
                RANGE_TYPE,
                FUNCTION_TYPE,
                numericLib,
                STRING_LIB,
                BOOLEAN_LIB,
                DATE_TIME_LIB,
                DURATION_LIB,
                LIST_LIB,
                RANGE_LIB
        );
    }

    protected BaseMixedJavaTimeFEELLib(
            NumericType<NUMBER> numericType,
            BooleanType booleanType,
            StringType stringType,
            DateType<LocalDate, Duration> dateType,
            TimeType<OffsetTime, Duration> timeType,
            DateTimeType<ZonedDateTime, Duration> dateTimeType,
            DurationType<Duration, NUMBER> durationType,
            ListType listType,
            ContextType contextType,
            RangeType rangeType,
            FunctionType functionType,
            NumericLib<NUMBER> numericLib,
            StringLib stringLib,
            BooleanLib booleanLib,
            DateTimeLib<NUMBER, LocalDate, OffsetTime, ZonedDateTime, Duration> dateTimeLib,
            DurationLib<LocalDate, Duration> durationLib,
            ListLib listLib,
            RangeLib rangeLib) {
        super(numericType, booleanType, stringType,
                dateType, timeType, dateTimeType, durationType,
                listType, contextType, rangeType, functionType,
                numericLib, stringLib, booleanLib, dateTimeLib, durationLib, listLib, rangeLib
        );
    }

    //
    // Conversion functions
    //
    public LocalDate date(ZonedDateTime from) {
        try {
            return this.dateTimeLib.date(toDate(from));
        } catch (Exception e) {
            String message = String.format("date(%s)", from);
            logError(message, e);
            return null;
        }
    }

    public OffsetTime time(ZonedDateTime from) {
        try {
            return this.dateTimeLib.time(toTime(from));
        } catch (Exception e) {
            String message = String.format("time(%s)", from);
            logError(message, e);
            return null;
        }
    }
    public OffsetTime time(LocalDate from) {
        try {
            return this.dateTimeLib.time(toTime(from));
        } catch (Exception e) {
            String message = String.format("time(%s)", from);
            logError(message, e);
            return null;
        }
    }

    public ZonedDateTime dateAndTime(Object date, OffsetTime time) {
        try {
            return this.dateTimeLib.dateAndTime(toDate(date), time);
        } catch (Exception e) {
            String message = String.format("dateAndTime(%s, %s)", date, time);
            logError(message, e);
            return null;
        }
    }

    public Duration yearsAndMonthsDuration(ZonedDateTime from, ZonedDateTime to) {
        try {
            return this.durationLib.yearsAndMonthsDuration(toDate(from), toDate(to));
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }
    public Duration yearsAndMonthsDuration(ZonedDateTime from, LocalDate to) {
        try {
            return this.durationLib.yearsAndMonthsDuration(toDate(from), to);
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }
    public Duration yearsAndMonthsDuration(LocalDate from, ZonedDateTime to) {
        try {
            return this.durationLib.yearsAndMonthsDuration(from, toDate(to));
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    //
    // Date properties
    //
    public NUMBER year(ZonedDateTime dateTime) {
        try {
            return valueOf(this.dateTimeLib.yearDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("year(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public NUMBER month(ZonedDateTime dateTime) {
        try {
            return valueOf(this.dateTimeLib.monthDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("month(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public NUMBER day(ZonedDateTime dateTime) {
        try {
            return valueOf(this.dateTimeLib.dayDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("day(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public NUMBER weekday(ZonedDateTime dateTime) {
        try {
            return valueOf(this.dateTimeLib.weekdayDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("weekday(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    //
    // Time properties
    //
    public NUMBER hour(ZonedDateTime dateTime) {
        try {
            return valueOf(this.dateTimeLib.hourDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("hour(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public NUMBER minute(ZonedDateTime dateTime) {
        try {
            return valueOf(this.dateTimeLib.minuteDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("minute(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public NUMBER second(ZonedDateTime dateTime) {
        try {
            return valueOf(this.dateTimeLib.secondDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("second(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public Duration timeOffset(ZonedDateTime dateTime) {
        try {
            return this.dateTimeLib.timeOffsetDateTime(dateTime);
        } catch (Exception e) {
            String message = String.format("timeOffset(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public String timezone(ZonedDateTime dateTime) {
        try {
            return this.dateTimeLib.timezoneDateTime(dateTime);
        } catch (Exception e) {
            String message = String.format("timezone(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    //
    // Temporal functions
    //
    public NUMBER dayOfYear(ZonedDateTime dateTime) {
        try {
            return valueOf(this.dateTimeLib.dayOfYearDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("dayOfYear(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public String dayOfWeek(ZonedDateTime dateTime) {
        try {
            return this.dateTimeLib.dayOfWeekDateTime(dateTime);
        } catch (Exception e) {
            String message = String.format("dayOfWeek(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public NUMBER weekOfYear(ZonedDateTime dateTime) {
        try {
            return valueOf(this.dateTimeLib.weekOfYearDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("weekday(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public String monthOfYear(ZonedDateTime dateTime) {
        try {
            return this.dateTimeLib.monthOfYearDateTime(dateTime);
        } catch (Exception e) {
            String message = String.format("weekday(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }
}
