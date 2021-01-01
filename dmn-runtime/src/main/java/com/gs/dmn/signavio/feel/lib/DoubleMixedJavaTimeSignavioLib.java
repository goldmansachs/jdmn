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

import com.gs.dmn.feel.lib.DoubleMixedJavaTimeFEELLib;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.feel.lib.type.*;
import com.gs.dmn.feel.lib.type.context.DefaultContextType;
import com.gs.dmn.feel.lib.type.list.DefaultListType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
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

import static com.gs.dmn.feel.lib.DefaultFEELLib.DATA_TYPE_FACTORY;

public class DoubleMixedJavaTimeSignavioLib extends BaseSignavioLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    private static final NumericType<Double> NUMERIC_TYPE = new DoubleSignavioNumericType(LOGGER);
    private static final BooleanType BOOLEAN_TYPE = new DefaultBooleanType(LOGGER);
    private static final StringType STRING_TYPE = new DefaultSignavioStringType(LOGGER);
    private static final DateType<LocalDate, Duration> DATE_TYPE = new SignavioLocalDateType(LOGGER, DATA_TYPE_FACTORY);
    private static final TimeType<OffsetTime, Duration> TIME_TYPE = new SignavioOffsetTimeType(LOGGER, DATA_TYPE_FACTORY);
    private static final DateTimeType<ZonedDateTime, Duration> DATE_TIME_TYPE = new SignavioZonedDateTimeType(LOGGER, DATA_TYPE_FACTORY);
    private static final DurationType<Duration, Double> DURATION_TYPE = new DoubleSignavioDurationType(LOGGER, DATA_TYPE_FACTORY);
    private static final ListType LIST_TYPE = new DefaultListType(LOGGER);
    private static final ContextType CONTEXT_TYPE = new DefaultContextType(LOGGER);

    private static final DoubleMixedJavaTimeFEELLib FEEL_LIB = new DoubleMixedJavaTimeFEELLib();
    private static final SignavioNumberLib<Double> NUMBER_LIB = new DoubleSignavioNumberLib();
    private static final SignavioStringLib STRING_LIB = new DefaultSignavioStringLib();
    private static final SignavioDateTimeLib DATE_TIME_LIB = new MixedSignavioDateTimeLib();
    private static final SignavioListLib LIST_LIB = new SignavioListLib();

    public static final DoubleMixedJavaTimeSignavioLib INSTANCE = new DoubleMixedJavaTimeSignavioLib();

    private final DoubleMixedJavaTimeFEELLib mixedFeelLib;

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
                FEEL_LIB,
                NUMBER_LIB,
                STRING_LIB,
                DATE_TIME_LIB,
                LIST_LIB
        );
    }

    protected DoubleMixedJavaTimeSignavioLib(
            NumericType<Double> numericType, BooleanType booleanType, StringType stringType,
            DateType<LocalDate, Duration> dateType, TimeType<OffsetTime, Duration> timeType, DateTimeType<ZonedDateTime, Duration> dateTimeType, DurationType<Duration, Double> durationType,
            ListType listType, ContextType contextType,
            StandardFEELLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> feelLib,
            SignavioNumberLib<Double> numberLib,
            SignavioStringLib stringLib,
            SignavioDateTimeLib<Double, LocalDate, OffsetTime, ZonedDateTime> dateTimeLib,
            SignavioListLib listLib) {
        super(numericType, booleanType, stringType,
                dateType, timeType, dateTimeType, durationType,
                listType, contextType,
                feelLib, numberLib, stringLib, dateTimeLib, listLib);
        this.mixedFeelLib = (DoubleMixedJavaTimeFEELLib) this.feelLib;
    }

    //
    // Date and time operations
    //
    public Double day(ZonedDateTime date) {
        return this.mixedFeelLib.day(date);
    }

    public LocalDate dayAdd(ZonedDateTime dateTime, Double daysToAdd) {
        try {
            return this.dateTimeLib.dayAddDateTime(dateTime, daysToAdd);
        } catch (Exception e) {
            String message = String.format("dayAdd(%s, %s)", dateTime, daysToAdd);
            logError(message, e);
            return null;
        }
    }

    public Double dayDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        try {
            return Double.valueOf(this.dateTimeLib.dayDiffDateTime(dateTime1, dateTime2));
        } catch (Exception e) {
            String message = String.format("dayDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }

    public Double hour(ZonedDateTime dateTime) {
        return this.mixedFeelLib.hour(dateTime);
    }

    public Double hourDiff(ZonedDateTime time1, ZonedDateTime time2) {
        try {
            return Double.valueOf(this.dateTimeLib.hourDiffDateTime(time1, time2));
        } catch (Exception e) {
            String message = String.format("hourDiff(%s, %s)", time1, time2);
            logError(message, e);
            return null;
        }
    }

    public Double minute(ZonedDateTime dateTime) {
        return this.mixedFeelLib.minute(dateTime);
    }

    public Double second(ZonedDateTime time) {
        return this.mixedFeelLib.second(time);
    }

    public Duration timeOffset(ZonedDateTime time) {
        return this.mixedFeelLib.timeOffset(time);
    }

    public String timezone(ZonedDateTime time) {
        return this.mixedFeelLib.timezone(time);
    }

    public Double minutesDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        try {
            return Double.valueOf(this.dateTimeLib.minutesDiffDateTime(dateTime1, dateTime2));
        } catch (Exception e) {
            String message = String.format("minutesDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }

    public Double month(ZonedDateTime dateTime) {
        return this.mixedFeelLib.month(dateTime);
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

    public Double monthDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        try {
            return Double.valueOf(this.dateTimeLib.monthDiffDateTime(dateTime1, dateTime2));
        } catch (Exception e) {
            String message = String.format("monthDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }

    public Double weekday(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(this.dateTimeLib.weekdayDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("weekday(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public Double year(ZonedDateTime dateTime) {
        return this.mixedFeelLib.year(dateTime);
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

    public Double yearDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        try {
            return Double.valueOf(this.dateTimeLib.yearDiffDateTime(dateTime1, dateTime2));
        } catch (Exception e) {
            String message = String.format("yearDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }

    public LocalDate date(LocalDate date) {
        return this.mixedFeelLib.date(date);
    }

    public OffsetTime time(OffsetTime time) {
        return this.mixedFeelLib.time(time);
    }
}
