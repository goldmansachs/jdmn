/**
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

import java.math.BigDecimal;
import java.math.BigInteger;

public class FEELXMLParser {
    private final FEELXMLGregorianCalendar calendar;
    private final String format;
    private final String value;

    private final int formatLength;
    private final int valueLength;

    private int formatIndex;
    private int valueIndex;

    FEELXMLParser(FEELXMLGregorianCalendar calendar, String format, String value) {
        this.calendar = calendar;
        this.format = format;
        this.value = value;
        this.formatLength = format.length();
        this.valueLength = value.length();
    }

    public void parse() throws IllegalArgumentException {
        while (formatIndex < formatLength) {
            char formatCh = format.charAt(formatIndex++);

            if (formatCh != '%') { // not a meta character
                skip(formatCh);
                continue;
            }

            // seen meta character. we don't do error check against the format
            switch (format.charAt(formatIndex++)) {
                case 'Y':
                    parseAndSetYear(4);
                    break;
                case 'M':
                    calendar.setMonth(parseInt(2, 2));
                    break;
                case 'D':
                    calendar.setDay(parseInt(2, 2));
                    break;
                case 'h':
                    calendar.setHour(parseInt(2, 2), false);
                    break;
                case 'm':
                    calendar.setMinute(parseInt(2, 2));
                    break;
                case 's':
                    calendar.setSecond(parseInt(2, 2));
                    if (peek() == '.') {
                        calendar.setFractionalSecond(parseBigDecimal());
                    }
                    break;
                case 'z': // time zone. missing, 'Z', or [+-]nn:nn
                    char valueCh = peek();
                    if (valueCh == 'Z') {
                        valueIndex++;
                        calendar.setTimezone(0);
                    } else if (valueCh == '+' || valueCh == '-') {
                        valueIndex++;
                        int h = parseInt(2, 2);
                        skip(':');
                        int m = parseInt(2, 2);
                        int s = 0;
                        if (peek() == ':') {
                            skip(':');
                            s = parseInt(2, 2);
                        }
                        calendar.setTimezone((((h * 60 + m) * 60) + s) * (valueCh == '+' ? 1 : -1));
                    }
                    break;
                default:
                    // illegal meta character. impossible.
                    throw new InternalError();
            }
        }

        if (valueIndex != valueLength) {
            // some tokens are left in the input
            throw new IllegalArgumentException(value);
        }
        calendar.fixHour();
    }

    private char peek() throws IllegalArgumentException {
        if (valueIndex == valueLength) {
            return (char) -1;
        }
        return value.charAt(valueIndex);
    }

    private char read() throws IllegalArgumentException {
        if (valueIndex == valueLength) {
            throw new IllegalArgumentException(value);
        }
        return value.charAt(valueIndex++);
    }

    private void skip(char ch) throws IllegalArgumentException {
        if (read() != ch) {
            throw new IllegalArgumentException(value);
        }
    }

    private int parseInt(int minDigits, int maxDigits) throws IllegalArgumentException {
        int n = 0;
        char ch;
        int vstart = valueIndex;
        while (calendar.isDigit(ch = peek()) && (valueIndex - vstart) <= maxDigits) {
            valueIndex++;
            n = n * 10 + ch - '0';
        }
        if ((valueIndex - vstart) < minDigits) {
            // we are expecting more digits
            throw new IllegalArgumentException(value);
        }

        return n;
    }

    private void parseAndSetYear(int minDigits) throws IllegalArgumentException {
        int valueStart = valueIndex;
        int n = 0;
        boolean neg = false;

        // skip leading negative, if it exists
        if (peek() == '-') {
            valueIndex++;
            neg = true;
        }
        while (true) {
            char ch = peek();
            if (!calendar.isDigit(ch)) {
                break;
            }
            valueIndex++;
            n = n * 10 + ch - '0';
        }

        if ((valueIndex - valueStart) < minDigits) {
            // we are expecting more digits
            throw new IllegalArgumentException(value);
        }

        if (valueIndex - valueStart < 7) {
            // definitely int only. I don't know the exact # of digits that can be in int,
            // but as long as we can catch (0-9999) range, that should be enough.
            if (neg) {
                n = -n;
            }
            calendar.setYear(n);
            calendar.setEon(null);
        } else {
            calendar.setYear(new BigInteger(value.substring(valueStart, valueIndex)));
        }
    }

    private BigDecimal parseBigDecimal() throws IllegalArgumentException {
        int valueStart = valueIndex;
        if (peek() == '.') {
            valueIndex++;
        } else {
            throw new IllegalArgumentException(value);
        }
        while (calendar.isDigit(peek())) {
            valueIndex++;
        }
        return new BigDecimal(value.substring(valueStart, valueIndex));
    }
}

