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

    private static final long SECONDS_IN_A_MINUTE = 60;
    private static final long SECONDS_IN_AN_HOUR = 60 * SECONDS_IN_A_MINUTE;
    private static final long SECONDS_IN_A_DAY = 24 * SECONDS_IN_AN_HOUR;
    private static final long NANOSECONDS_PER_SECOND = 1000000000;

    public static String formatNumber(Number from) {
        if (from == null) {
            return null;
        }

        if (from instanceof Double) {
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
        } else if (from instanceof TemporalAccessor) {
            // Its time with zone ID
            return BaseDateTimeLib.FEEL_TIME.format((TemporalAccessor) from);
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
        long totalMonths = period.toTotalMonths();
        if (totalMonths == 0) {
            return "P0M";
        }

        StringBuilder builder = new StringBuilder();
        if (totalMonths < 0) {
            builder.append("-P");
        } else {
            builder.append('P');
        }
        long years = Math.abs(totalMonths / 12);
        if (years !=0) {
            builder.append(years).append('Y');
        }
        long months = Math.abs(totalMonths % 12);
        builder.append(months).append('M');
        return builder.toString();
    }

    private static String formatDuration(Duration duration) {
        if (duration == null) {
            return null;
        }

        if (Duration.ZERO.equals(duration)) {
            return "PT0S";
        }
        long days = duration.getSeconds() / SECONDS_IN_A_DAY;
        long hours = (duration.getSeconds() % SECONDS_IN_A_DAY) / SECONDS_IN_AN_HOUR;
        long minutes = (duration.getSeconds() % SECONDS_IN_AN_HOUR) / SECONDS_IN_A_MINUTE;
        long seconds = duration.getSeconds() % SECONDS_IN_A_MINUTE;

        StringBuilder builder = new StringBuilder();
        if (duration.isNegative()) {
            builder.append("-");
        }
        builder.append("P");
        if (days != 0) {
            builder.append(Math.abs(days)).append("D");
        }
        if (hours != 0 || minutes != 0 || seconds != 0 || duration.getNano() != 0) {
            builder.append("T");
            if (hours != 0) {
                builder.append(Math.abs(hours)).append("H");
            }
            if (minutes != 0) {
                builder.append(Math.abs(minutes)).append("M");
            }
            if (seconds != 0 || duration.getNano() != 0) {
                appendSecondsFieldToDuration(builder, seconds, duration.getNano());
            }
        }
        return builder.toString();
    }

    private static void appendSecondsFieldToDuration(StringBuilder builder, long seconds, long nanoseconds) {
        if (seconds < 0 && nanoseconds > 0) {
            if (seconds == -1) {
                builder.append("0");
            } else {
                builder.append(Math.abs(seconds + 1));
            }
        } else {
            builder.append(Math.abs(seconds));
        }
        if (nanoseconds > 0) {
            final int pos = builder.length();
            if (seconds < 0) {
                builder.append(2 * NANOSECONDS_PER_SECOND - nanoseconds);
            } else {
                builder.append(nanoseconds + NANOSECONDS_PER_SECOND);
            }
            // eliminates trailing zeros in the nanoseconds
            while (builder.charAt(builder.length() - 1) == '0') {
                builder.setLength(builder.length() - 1);
            }
            builder.setCharAt(pos, '.');
        }
        builder.append('S');
    }
}
