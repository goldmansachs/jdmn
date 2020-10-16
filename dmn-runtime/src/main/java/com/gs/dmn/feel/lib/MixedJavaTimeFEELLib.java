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

import com.gs.dmn.feel.lib.type.bool.DefaultBooleanLib;
import com.gs.dmn.feel.lib.type.context.DefaultContextType;
import com.gs.dmn.feel.lib.type.list.DefaultListLib;
import com.gs.dmn.feel.lib.type.list.DefaultListType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.numeric.DefaultNumericLib;
import com.gs.dmn.feel.lib.type.numeric.DefaultNumericType;
import com.gs.dmn.feel.lib.type.string.DefaultStringLib;
import com.gs.dmn.feel.lib.type.string.DefaultStringType;
import com.gs.dmn.feel.lib.type.time.DateTimeLib;
import com.gs.dmn.feel.lib.type.time.mixed.*;
import com.gs.dmn.feel.lib.type.time.xml.DefaultDurationType;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

public class MixedJavaTimeFEELLib extends BaseStandardFEELLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> implements StandardFEELLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    private static final DatatypeFactory DATA_TYPE_FACTORY = XMLDatataypeFactory.newInstance();
    public static MixedJavaTimeFEELLib INSTANCE = new MixedJavaTimeFEELLib();

    public MixedJavaTimeFEELLib() {
        super(new DefaultNumericType(LOGGER),
                new DefaultBooleanType(LOGGER),
                new DefaultStringType(LOGGER),
                new LocalDateType(LOGGER, DATA_TYPE_FACTORY),
                new OffsetTimeType(LOGGER, DATA_TYPE_FACTORY),
                new ZonedDateTimeType(LOGGER, DATA_TYPE_FACTORY),
                new DefaultDurationType(LOGGER, DATA_TYPE_FACTORY),
                new DefaultListType(LOGGER),
                new DefaultContextType(LOGGER),
                new DefaultNumericLib(),
                new DefaultStringLib(),
                new DefaultBooleanLib(),
                (DateTimeLib) new MixedDateTimeLib(DATA_TYPE_FACTORY),
                new MixedDurationLib(DATA_TYPE_FACTORY),
                new DefaultListLib()
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
    public BigDecimal year(ZonedDateTime dateTime) {
        try {
            return valueOf(this.dateTimeLib.yearDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("year(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public BigDecimal month(ZonedDateTime dateTime) {
        try {
            return valueOf(this.dateTimeLib.monthDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("month(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public BigDecimal day(ZonedDateTime dateTime) {
        try {
            return valueOf(this.dateTimeLib.dayDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("day(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public BigDecimal weekday(ZonedDateTime dateTime) {
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
    public BigDecimal hour(ZonedDateTime dateTime) {
        try {
            return valueOf(this.dateTimeLib.hourDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("hour(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public BigDecimal minute(ZonedDateTime dateTime) {
        try {
            return valueOf(this.dateTimeLib.minuteDateTime(dateTime));
        } catch (Exception e) {
            String message = String.format("minute(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public BigDecimal second(ZonedDateTime dateTime) {
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

    @Override
    protected BigDecimal valueOf(long number) {
        return BigDecimal.valueOf(number);
    }

    @Override
    protected int intValue(BigDecimal number) {
        return number.intValue();
    }
}
