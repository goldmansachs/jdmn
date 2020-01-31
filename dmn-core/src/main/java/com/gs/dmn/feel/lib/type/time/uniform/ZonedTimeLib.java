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
package com.gs.dmn.feel.lib.type.time.uniform;

import com.gs.dmn.feel.lib.type.time.BaseDateTimeLib;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.*;

import static com.gs.dmn.feel.lib.type.time.xml.DefaultDateTimeLib.UTC;

public class ZonedTimeLib extends BaseDateTimeLib {
    private final DatatypeFactory dataTypeFactory;

    public ZonedTimeLib(DatatypeFactory dataTypeFactory) {
        this.dataTypeFactory = dataTypeFactory;
    }

    public ZonedDateTime time(String literal) {
        if (literal == null) {
            return null;
        }
        OffsetTime offsetTime = this.makeOffsetTime(literal);
        ZoneOffset offset = offsetTime.getOffset();

        // Make ZonedDateTime
        return ZonedDateTime.of(LocalDateTime.of(LocalDate.MIN, offsetTime.toLocalTime()), offset);
    }

    public ZonedDateTime time(BigDecimal hour, BigDecimal minute, BigDecimal second, Duration offset) {
        if (hour == null || minute == null || second == null) {
            return null;
        }

        if (offset != null) {
            // Make offset
            String sign = offset.getSign() < 0 ? "-" : "+";
            String offsetString = String.format("%s%02d:%02d", sign, offset.getHours(), offset.getMinutes());

            // Make literal for ZonedDateTime
            int year = LocalDate.MIN.getYear();
            int month = LocalDate.MIN.getMonthValue();
            int day = LocalDate.MIN.getDayOfMonth();
            BigDecimal secondFraction = second.subtract(BigDecimal.valueOf(second.intValue()));
            int millis = (int) (secondFraction.doubleValue() * 1000);
            String format = dateTimeISOFormat(year, month, day, hour.intValue(), minute.intValue(), second.intValue(), millis, offsetString);

            // Make ZonedDateTime
            return makeZonedDateTime(format);
        } else {
            // Make OffsetTime and add nanos
            OffsetTime offsetTime = OffsetTime.of(hour.intValue(), minute.intValue(), second.intValue(), 0, ZoneOffset.UTC);
            BigDecimal secondFraction = second.subtract(BigDecimal.valueOf(second.intValue()));
            double nanos = secondFraction.doubleValue() * 1E9;
            offsetTime = offsetTime.plusNanos((long) nanos);

            // Make ZonedDateTime
            return ZonedDateTime.of(LocalDateTime.of(LocalDate.MIN, offsetTime.toLocalTime()), UTC);
        }
    }

    public ZonedDateTime time(ZonedDateTime from) {
        if (!isTime(from)) {
            return null;
        }

        OffsetTime offsetTime = from.toOffsetDateTime().toOffsetTime();
        return offsetTime.atDate(LocalDate.MIN).toZonedDateTime();
    }

    public ZonedDateTime toTime(Object object) {
        if (!(object instanceof ZonedDateTime)) {
            return null;
        }
        return time((ZonedDateTime) object);
    }

    public BigDecimal hour(ZonedDateTime date) {
        return BigDecimal.valueOf(date.getHour());
    }

    public BigDecimal minute(ZonedDateTime date) {
        return BigDecimal.valueOf(date.getMinute());
    }

    public BigDecimal second(ZonedDateTime date) {
        return BigDecimal.valueOf(date.getSecond());
    }

    public Duration timeOffset(ZonedDateTime date) {
        // timezone offset in seconds
        int secondsOffset = date.getOffset().getTotalSeconds();
        return this.dataTypeFactory.newDuration((long) secondsOffset * 1000);
    }

    public String timezone(ZonedDateTime date) {
        return date.getZone().getId();
    }

    @Override
    protected ZonedDateTime makeZonedDateTime(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        return ZonedDateTime.parse(literal, FEEL_DATE_TIME_FORMAT);
    }

    private String dateTimeISOFormat(int year, int month, int day, int hours, int minutes, int seconds, int millis, String offset) {
        return String.format("%04d-%02d-%02dT%02d:%02d:%02d.%d%s", year, month, day, hours, minutes, seconds, millis, offset);
    }

    private boolean isTime(ZonedDateTime from) {
        return from != null && from.getHour() >=0;
    }
}
