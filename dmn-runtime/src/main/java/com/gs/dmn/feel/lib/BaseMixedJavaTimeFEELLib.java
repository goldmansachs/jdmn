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
import com.gs.dmn.feel.lib.type.numeric.NumericLib;
import com.gs.dmn.feel.lib.type.string.DefaultStringLib;
import com.gs.dmn.feel.lib.type.string.DefaultStringType;
import com.gs.dmn.feel.lib.type.string.StringLib;
import com.gs.dmn.feel.lib.type.time.DateTimeLib;
import com.gs.dmn.feel.lib.type.time.DurationLib;
import com.gs.dmn.feel.lib.type.time.mixed.*;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

public abstract class BaseMixedJavaTimeFEELLib<NUMBER> extends BaseStandardFEELLib<NUMBER, LocalDate, OffsetTime, ZonedDateTime, Duration> implements StandardFEELLib<NUMBER, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    protected static final DatatypeFactory DATA_TYPE_FACTORY = XMLDatataypeFactory.newInstance();

    private static final BooleanType BOOLEAN_TYPE = new DefaultBooleanType(LOGGER);
    private static final StringType STRING_TYPE = new DefaultStringType(LOGGER);
    private static final DateType<LocalDate, Duration> DATE_TYPE = new LocalDateType(LOGGER, DATA_TYPE_FACTORY);
    private static final TimeType<OffsetTime, Duration> TIME_TYPE = new OffsetTimeType(LOGGER, DATA_TYPE_FACTORY);
    private static final DateTimeType<ZonedDateTime, Duration> DATE_TIME_TYPE = new ZonedDateTimeType(LOGGER, DATA_TYPE_FACTORY);
    private static final ListType LIST_TYPE = new DefaultListType(LOGGER);
    private static final ContextType CONTEXT_TYPE = new DefaultContextType(LOGGER);

    private static final StringLib STRING_LIB = new DefaultStringLib();
    private static final BooleanLib BOOLEAN_LIB = new DefaultBooleanLib();
    private static final DateTimeLib DATE_TIME_LIB = new MixedDateTimeLib(DATA_TYPE_FACTORY);
    private static final DurationLib<LocalDate, Duration> DURATION_LIB = new MixedDurationLib(DATA_TYPE_FACTORY);

    private static final DefaultListLib LIST_LIB = new DefaultListLib();

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
                numericLib,
                STRING_LIB,
                BOOLEAN_LIB,
                DATE_TIME_LIB,
                DURATION_LIB,
                LIST_LIB
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
            NumericLib<NUMBER> numericLib,
            StringLib stringLib,
            BooleanLib booleanLib,
            DateTimeLib<NUMBER, LocalDate, OffsetTime, ZonedDateTime, Duration> dateTimeLib,
            DurationLib<LocalDate, Duration> durationLib,
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
    // Date functions
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
    // Time functions
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
}
