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
package com.gs.dmn.feel.lib.type.time.xml;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.TemporalAccessor;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoField.*;

public class DefaultDateLib {
    public static final Pattern BEGIN_YEAR = Pattern.compile("^-?(([1-9]\\d\\d\\d+)|(0\\d\\d\\d))-"); // FEEL spec, "specified by XML Schema Part 2 Datatypes", hence: yearFrag ::= '-'? (([1-9] digit digit digit+)) | ('0' digit digit digit))
    public static final DateTimeFormatter FEEL_DATE;

    static {
        FEEL_DATE = new DateTimeFormatterBuilder().appendValue(YEAR, 4, 9, SignStyle.NORMAL)
                .appendLiteral('-')
                .appendValue(MONTH_OF_YEAR, 2)
                .appendLiteral('-')
                .appendValue(DAY_OF_MONTH, 2)
                .toFormatter();
    }

    public TemporalAccessor date(String literal) {
        if (literal == null) {
            throw new IllegalArgumentException("Date literal cannot be null");
        }
        if (!BEGIN_YEAR.matcher(literal).find()) {
            throw new IllegalArgumentException("Year not compliant with XML Schema Part 2 Datatypes");
        }

        try {
            return LocalDate.from(FEEL_DATE.parse(literal));
        } catch (DateTimeException e) {
            throw new RuntimeException("Parsing exception in date literal", e);
        }
    }
}
