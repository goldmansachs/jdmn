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

import com.gs.dmn.feel.lib.type.time.BaseDateTimeLib;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;

public class FormatUtils {
    public static final ThreadLocal<DecimalFormat> DECIMAL_FORMAT = ThreadLocal.withInitial(() -> new DecimalFormat("0.########"));

    public static String formatNumber(Number from) {
        if (from == null) {
            return "null";
        } else if (from instanceof Double) {
            return DECIMAL_FORMAT.get().format(from);
        } else if (from instanceof BigDecimal) {
            return ((BigDecimal) from).toPlainString();
        } else {
            return from.toString();
        }
    }

    public static String formatTemporal(Object from) {
        if (from == null) {
            return "null";
        } else if (from instanceof LocalDate) {
            return ((LocalDate) from).format(BaseDateTimeLib.FEEL_DATE);
        } else if (from instanceof LocalTime) {
            return ((LocalTime) from).format(BaseDateTimeLib.FEEL_TIME);
        } else if (from instanceof OffsetTime) {
            return ((OffsetTime) from).format(BaseDateTimeLib.FEEL_TIME);
        } else if (from instanceof LocalDateTime) {
            return ((LocalDateTime) from).format(BaseDateTimeLib.FEEL_DATE_TIME);
        } else if (from instanceof OffsetDateTime) {
            return ((OffsetDateTime) from).format(BaseDateTimeLib.FEEL_DATE_TIME);
        } else if (from instanceof ZonedDateTime) {
            TemporalAccessor accessor = (TemporalAccessor) from;
            ZoneId zone = accessor.query(TemporalQueries.zone());
            if (!(zone instanceof ZoneOffset)) {
                // it is a ZoneRegion
                return BaseDateTimeLib.REGION_DATETIME_FORMATTER.format(accessor);
            } else {
                return BaseDateTimeLib.FEEL_DATE_TIME.format(accessor);
            }
        } else if (from instanceof XMLGregorianCalendar) {
            return from.toString();
        } else if (from instanceof javax.xml.datatype.Duration) {
            return from.toString();
        } else if (from instanceof java.time.Period) {
            return formatPeriod((Period) from);
        } else if (from instanceof java.time.Duration) {
            return formatDuration((Duration) from);
        } else {
            return from.toString();
        }
    }

    private static String formatPeriod(Period period) {
        return period.toString();
    }

    private static String formatDuration(Duration duration) {
        return duration.toString();
    }
}
