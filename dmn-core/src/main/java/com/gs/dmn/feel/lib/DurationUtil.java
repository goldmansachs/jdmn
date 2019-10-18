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

import java.time.Duration;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAmount;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

public class DurationUtil {
    private static GregorianCalendar GREGORIAN = new GregorianCalendar(
            1970,
            1,
            1,
            0,
            0,
            0);

    public static int compare(javax.xml.datatype.Duration duration1, javax.xml.datatype.Duration duration2) {
        javax.xml.datatype.Duration lhs = normalize(duration1);
        javax.xml.datatype.Duration rhs = normalize(duration2);
        return lhs.compare(rhs);
    }

    public static javax.xml.datatype.Duration normalize(javax.xml.datatype.Duration duration) {
        return duration.normalizeWith(DurationUtil.GREGORIAN);
    }

    public static TemporalAmount duration(String literal) {
        if (literal == null) {
            throw new IllegalArgumentException("Duration literal cannot be null");
        }

        if (literal.indexOf("-") > 0) {
            throw new IllegalArgumentException("Negative values for units are not allowed.");
        }

        try {
            return Duration.parse(literal);
        } catch (DateTimeParseException e1) {
            try {
                return Period.parse(literal).normalized();
            } catch (DateTimeParseException e2) {
                throw new RuntimeException("Parsing exception in duration literal",
                        new RuntimeException(new Throwable() {
                            public final List<Throwable> causes = Arrays.asList(new Throwable[]{e1, e2});
                        }));
            }
        }
    }

    public static TemporalAmount duration(TemporalAmount val) {
        if (val == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        return val;
    }
}
