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
import com.gs.dmn.feel.lib.type.context.ContextType;
import com.gs.dmn.feel.lib.type.function.FunctionType;
import com.gs.dmn.feel.lib.type.list.ListLib;
import com.gs.dmn.feel.lib.type.list.ListType;
import com.gs.dmn.feel.lib.type.numeric.NumericLib;
import com.gs.dmn.feel.lib.type.numeric.NumericRoundingMode;
import com.gs.dmn.feel.lib.type.numeric.NumericType;
import com.gs.dmn.feel.lib.type.range.RangeLib;
import com.gs.dmn.feel.lib.type.range.RangeType;
import com.gs.dmn.feel.lib.type.string.StringLib;
import com.gs.dmn.feel.lib.type.string.StringType;
import com.gs.dmn.feel.lib.type.time.*;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.LambdaExpression;
import com.gs.dmn.runtime.Range;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseStandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends BaseFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> implements StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    private final NumericLib<NUMBER> numberLib;
    private final StringLib stringLib;
    private final BooleanLib booleanLib;
    protected final DateTimeLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> dateTimeLib;
    protected final DurationLib<DATE, DURATION> durationLib;
    private final ListLib listLib;
    private final RangeLib rangeLib;

    protected BaseStandardFEELLib(
            NumericType<NUMBER> numericType, BooleanType booleanType, StringType stringType,
            DateType<DATE, DURATION> dateType, TimeType<TIME, DURATION> timeType, DateTimeType<DATE_TIME, DURATION> dateTimeType, DurationType<DURATION, NUMBER> durationType,
            ListType listType, ContextType contextType, RangeType rangeType, FunctionType functionType,
            NumericLib<NUMBER> numberLib,
            StringLib stringLib,
            BooleanLib booleanLib,
            DateTimeLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> dateTimeLib,
            DurationLib<DATE, DURATION> durationLib,
            ListLib listLib,
            RangeLib rangeLib) {
        super(numericType, booleanType, stringType, dateType, timeType, dateTimeType, durationType, listType, contextType, rangeType, functionType);
        this.numberLib = numberLib;
        this.stringLib = stringLib;
        this.booleanLib = booleanLib;
        this.dateTimeLib = dateTimeLib;
        this.durationLib = durationLib;
        this.listLib = listLib;
        this.rangeLib = rangeLib;
    }

    //
    // Conversion functions
    //

    @Override
    public NUMBER number(String literal) {
        try {
            return this.numberLib.number(literal);
        } catch (Exception e) {
            String message = "number(%s)".formatted(literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER number(String from, String groupingSeparator, String decimalSeparator) {
        try {
            return this.numberLib.number(from, groupingSeparator, decimalSeparator);
        } catch (Exception e) {
            String message = "number(%s, %s, %s)".formatted(from, groupingSeparator, decimalSeparator);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String string(Object from) {
        try {
            return this.stringLib.string(from);
        } catch (Exception e) {
            String message = "string(%s)".formatted(from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE date(String literal) {
        try {
            return this.dateTimeLib.date(literal);
        } catch (Exception e) {
            String message = "date(%s)".formatted(literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE date(NUMBER year, NUMBER month, NUMBER day) {
        try {
            return this.dateTimeLib.date(year, month, day);
        } catch (Exception e) {
            String message = "date(%s, %s, %s)".formatted(year, month, day);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE date(Object from) {
        try {
            return this.dateTimeLib.date(from);
        } catch (Exception e) {
            String message = "date(%s)".formatted(from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TIME time(String literal) {
        try {
            return this.dateTimeLib.time(literal);
        } catch (Exception e) {
            String message = "time(%s)".formatted(literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TIME time(NUMBER hour, NUMBER minute, NUMBER second, DURATION offset) {
        try {
            return this.dateTimeLib.time(hour, minute, second, offset);
        } catch (Exception e) {
            String message = "time(%s, %s, %s, %s)".formatted(hour, minute, second, offset);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TIME time(Object from) {
        try {
            return this.dateTimeLib.time(from);
        } catch (Exception e) {
            String message = "time(%s)".formatted(from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE_TIME dateAndTime(String from) {
        try {
            return this.dateTimeLib.dateAndTime(from);
        } catch (Exception e) {
            String message = "dateAndTime(%s)".formatted(from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE_TIME dateAndTime(Object date, Object time) {
        try {
            return this.dateTimeLib.dateAndTime(date, time);
        } catch (Exception e) {
            String message = "dateAndTime(%s, %s)".formatted(date, time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION duration(String from) {
        try {
            return this.durationLib.duration(from);
        } catch (Exception e) {
            String message = "duration(%s)".formatted(from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION yearsAndMonthsDuration(Object from, Object to) {
        try {
            return this.durationLib.yearsAndMonthsDuration(from, to);
        } catch (Exception e) {
            String message = "yearsAndMonthsDURATION(%s, %s)".formatted(from, to);
            logError(message, e);
            return null;
        }
    }

    //
    // Extra conversion functions
    //
    @Override
    public DATE toDate(Object from) {
        try {
            return this.dateTimeLib.toDate(from);
        } catch (Exception e) {
            String message = "toDate(%s)".formatted(from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TIME toTime(Object from) {
        try {
            return this.dateTimeLib.toTime(from);
        } catch (Exception e) {
            String message = "toTime(%s)".formatted(from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE_TIME toDateTime(Object from) {
        try {
            return this.dateTimeLib.toDateTime(from);
        } catch (Exception e) {
            String message = "toTime(%s)".formatted(from);
            logError(message, e);
            return null;
        }
    }

    //
    // Numeric functions
    //
    @Override
    public NUMBER decimal(NUMBER n, NUMBER scale) {
        try {
            return this.numberLib.decimal(n, scale);
        } catch (Exception e) {
            String message = "decimal(%s, %s)".formatted(n, scale);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER round(NUMBER n, NUMBER scale, String mode) {
        try {
            RoundingMode roundingMode = NumericRoundingMode.fromValue(mode);
            if (roundingMode == null) {
                throw new DMNRuntimeException("Unknown rounding mode '%s'. Expected one of '%s'".formatted(mode, NumericRoundingMode.ALLOWED_VALUES));
            } else {
                return this.numberLib.round(n, scale, roundingMode);
            }
        } catch (Exception e) {
            String message = "round(%s, %s, %s)".formatted(n, scale, mode);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER roundUp(NUMBER n, NUMBER scale) {
        try {
            return this.numberLib.round(n, scale, RoundingMode.UP);
        } catch (Exception e) {
            String message = "roundUp(%s, %s)".formatted(n, scale);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER roundDown(NUMBER n, NUMBER scale) {
        try {
            return this.numberLib.round(n, scale, RoundingMode.DOWN);
        } catch (Exception e) {
            String message = "roundDown(%s, %s)".formatted(n, scale);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER roundHalfUp(NUMBER n, NUMBER scale) {
        try {
            return this.numberLib.round(n, scale, RoundingMode.HALF_UP);
        } catch (Exception e) {
            String message = "roundHalfUp(%s, %s)".formatted(n, scale);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER roundHalfDown(NUMBER n, NUMBER scale) {
        try {
            return this.numberLib.round(n, scale, RoundingMode.HALF_DOWN);
        } catch (Exception e) {
            String message = "roundHalfDown(%s, %s)".formatted(n, scale);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER floor(NUMBER n) {
        try {
            return this.numberLib.floor(n, valueOf(0));
        } catch (Exception e) {
            String message = "floor(%s)".formatted(n);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER floor(NUMBER n, NUMBER scale) {
        try {
            return this.numberLib.floor(n, scale);
        } catch (Exception e) {
            String message = "floor(%s, %s)".formatted(n, scale);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER ceiling(NUMBER n) {
        try {
            return this.numberLib.ceiling(n, valueOf(0));
        } catch (Exception e) {
            String message = "ceiling(%s)".formatted(n);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER ceiling(NUMBER number, NUMBER scale) {
        try {
            return this.numberLib.ceiling(number, scale);
        } catch (Exception e) {
            String message = "ceiling(%s, %s)".formatted(number, scale);
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> T abs(T n) {
        try {
            if (n instanceof Number) {
                return (T) this.numberLib.abs((NUMBER) n);
            } else {
                return (T) this.durationLib.abs((DURATION) n);
            }
        } catch (Exception e) {
            String message = "abs(%s)".formatted(n);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER intModulo(NUMBER dividend, NUMBER divisor) {
        try {
            return this.numberLib.intModulo(dividend, divisor);
        } catch (Exception e) {
            String message = "modulo(%s, %s)".formatted(dividend, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER modulo(NUMBER dividend, NUMBER divisor) {
        try {
            return this.numberLib.modulo(dividend, divisor);
        } catch (Exception e) {
            String message = "modulo(%s, %s)".formatted(dividend, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER sqrt(NUMBER number) {
        try {
            return this.numberLib.sqrt(number);
        } catch (Exception e) {
            String message = "sqrt(%s)".formatted(number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER log(NUMBER number) {
        try {
            return this.numberLib.log(number);
        } catch (Exception e) {
            String message = "log(%s)".formatted(number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER exp(NUMBER number) {
        try {
            return this.numberLib.exp(number);
        } catch (Exception e) {
            String message = "exp(%s)".formatted(number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean odd(NUMBER number) {
        try {
            return this.numberLib.odd(number);
        } catch (Exception e) {
            String message = "odd(%s)".formatted(number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean even(NUMBER number) {
        try {
            return this.numberLib.even(number);
        } catch (Exception e) {
            String message = "even(%s)".formatted(number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER mean(List<?> list) {
        try {
            return this.numberLib.mean(list);
        } catch (Exception e) {
            String message = "mean(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER mean(Object... args) {
        try {
            return this.numberLib.mean(args);
        } catch (Exception e) {
            String message = "mean(%s)".formatted(Arrays.toString(args));
            logError(message, e);
            return null;
        }
    }

    //
    // String functions
    //
    @Override
    public Boolean contains(String string, String match) {
        try {
            return this.stringLib.contains(string, match);
        } catch (Exception e) {
            String message = "contains(%s, %s)".formatted(string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean startsWith(String string, String match) {
        try {
            return this.stringLib.startsWith(string, match);
        } catch (Exception e) {
            String message = "startsWith(%s, %s)".formatted(string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean endsWith(String string, String match) {
        try {
            return this.stringLib.endsWith(string, match);
        } catch (Exception e) {
            String message = "endsWith(%s, %s)".formatted(string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER stringLength(String string) {
        try {
            return string == null ? null : valueOf(this.stringLib.stringLength(string));
        } catch (Exception e) {
            String message = "stringLength(%s)".formatted(string);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String substring(String string, NUMBER startPosition) {
        try {
            return this.stringLib.substring(string, this.numberLib.toNumber(startPosition));
        } catch (Exception e) {
            String message = "substring(%s, %s)".formatted(string, startPosition);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String substring(String string, NUMBER startPosition, NUMBER length) {
        try {
            return this.stringLib.substring(string, this.numberLib.toNumber(startPosition), this.numberLib.toNumber(length));
        } catch (Exception e) {
            String message = "substring(%s, %s, %s)".formatted(string, startPosition, length);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String upperCase(String string) {
        try {
            return this.stringLib.upperCase(string);
        } catch (Exception e) {
            String message = "upperCase(%s)".formatted(string);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String lowerCase(String string) {
        try {
            return this.stringLib.lowerCase(string);
        } catch (Exception e) {
            String message = "lowerCase(%s)".formatted(string);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String substringBefore(String string, String match) {
        try {
            return this.stringLib.substringBefore(string, match);
        } catch (Exception e) {
            String message = "substringBefore(%s, %s)".formatted(string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String substringAfter(String string, String match) {
        try {
            return this.stringLib.substringAfter(string, match);
        } catch (Exception e) {
            String message = "substringAfter(%s, %s)".formatted(string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String replace(String input, String pattern, String replacement) {
        return replace(input, pattern, replacement, "");
    }

    @Override
    public String replace(String input, String pattern, String replacement, String flags) {
        try {
            return this.stringLib.replace(input, pattern, replacement, flags);
        } catch (Exception e) {
            String message = "replace(%s, %s, %s, %s)".formatted(input, pattern, replacement, flags);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean matches(String input, String pattern) {
        return matches(input, pattern, "");
    }

    @Override
    public Boolean matches(String input, String pattern, String flags) {
        try {
            return this.stringLib.matches(input, pattern, flags);
        } catch (Exception e) {
            String message = "matches(%s, %s, %s)".formatted(input, pattern, flags);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List<String> split(String string, String delimiter) {
        try {
            return this.stringLib.split(string, delimiter);
        } catch (Exception e) {
            String message = "split(%s, %s)".formatted(string, delimiter);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String stringJoin(List<String> list) {
        try {
            return this.stringLib.stringJoin(list);
        } catch (Exception e) {
            String message = "stringJoin(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String stringJoin(List<String> list, String delimiter) {
        try {
            return this.stringLib.stringJoin(list, delimiter);
        } catch (Exception e) {
            String message = "stringJoin(%s, %s)".formatted(list, delimiter);
            logError(message, e);
            return null;
        }
    }

    //
    // Boolean functions
    //
    @Override
    public Boolean and(List<?> list) {
        try {
            return this.booleanLib.and(list);
        } catch (Exception e) {
            String message = "and(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean and(Object... args) {
        try {
            return this.booleanLib.and(args);
        } catch (Exception e) {
            String message = "and(%s)".formatted(Arrays.toString(args));
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean all(List<?> list) {
        try {
            return this.booleanLib.all(list);
        } catch (Exception e) {
            String message = "all(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean all(Object... args) {
        try {
            return this.booleanLib.all(args);
        } catch (Exception e) {
            String message = "all(%s)".formatted(Arrays.toString(args));
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean or(List<?> list) {
        try {
            return this.booleanLib.or(list);
        } catch (Exception e) {
            String message = "or(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean or(Object... args) {
        try {
            return this.booleanLib.or(args);
        } catch (Exception e) {
            String message = "or(%s)".formatted(Arrays.toString(args));
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean any(List<?> list) {
        try {
            return this.booleanLib.any(list);
        } catch (Exception e) {
            String message = "any(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean any(Object... args) {
        try {
            return this.booleanLib.any(args);
        } catch (Exception e) {
            String message = "any(%s)".formatted(Arrays.toString(args));
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean not(Boolean operand) {
        try {
            return this.booleanType.booleanNot(operand);
        } catch (Exception e) {
            String message = "not(%s)".formatted(operand);
            logError(message, e);
            return null;
        }
    }

    //
    // Date properties
    //
    @Override
    public NUMBER year(Object date) {
        try {
            return valueOf(this.dateTimeLib.year(date));
        } catch (Exception e) {
            String message = "year(%s)".formatted(date);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER month(Object date) {
        try {
            return valueOf(this.dateTimeLib.month(date));
        } catch (Exception e) {
            String message = "month(%s)".formatted(date);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER day(Object date) {
        try {
            return valueOf(this.dateTimeLib.day(date));
        } catch (Exception e) {
            String message = "day(%s)".formatted(date);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER weekday(Object date) {
        try {
            return valueOf(this.dateTimeLib.weekday(date));
        } catch (Exception e) {
            String message = "weekday(%s)".formatted(date);
            logError(message, e);
            return null;
        }
    }

    //
    // Time properties
    //
    @Override
    public NUMBER hour(Object time) {
        try {
            return valueOf(this.dateTimeLib.hour(time));
        } catch (Exception e) {
            String message = "hour(%s)".formatted(time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER minute(Object time) {
        try {
            return valueOf(this.dateTimeLib.minute(time));
        } catch (Exception e) {
            String message = "minute(%s)".formatted(time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER second(Object time) {
        try {
            return valueOf(this.dateTimeLib.second(time));
        } catch (Exception e) {
            String message = "second(%s)".formatted(time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION timeOffset(Object time) {
        try {
            return this.dateTimeLib.timeOffset(time);
        } catch (Exception e) {
            String message = "timeOffset(%s)".formatted(time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String timezone(Object time) {
        try {
            return this.dateTimeLib.timezone(time);
        } catch (Exception e) {
            String message = "timezone(%s)".formatted(time);
            logError(message, e);
            return null;
        }
    }

    //
    // Duration properties
    //
    @Override
    public NUMBER years(DURATION duration) {
        try {
            return valueOf(this.durationLib.years(duration));
        } catch (Exception e) {
            String message = "years(%s)".formatted(duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER months(DURATION duration) {
        try {
            return valueOf(this.durationLib.months(duration));
        } catch (Exception e) {
            String message = "months(%s)".formatted(duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER days(DURATION duration) {
        try {
            return valueOf(this.durationLib.days(duration));
        } catch (Exception e) {
            String message = "days(%s)".formatted(duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER hours(DURATION duration) {
        try {
            return valueOf(this.durationLib.hours(duration));
        } catch (Exception e) {
            String message = "hours(%s)".formatted(duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER minutes(DURATION duration) {
        try {
            return valueOf(this.durationLib.minutes(duration));
        } catch (Exception e) {
            String message = "minutes(%s)".formatted(duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER seconds(DURATION duration) {
        try {
            return valueOf(this.durationLib.seconds(duration));
        } catch (Exception e) {
            String message = "seconds(%s)".formatted(duration);
            logError(message, e);
            return null;
        }
    }

    //
    // Date and time functions
    //
    @Override
    public Boolean is(Object value1, Object value2) {
        try {
            if (value1 == null || value2 == null) {
                return value1 == value2;
            } else if (value1.getClass() != value2.getClass()) {
                // Different kind
                return false;
            } else if (value1 instanceof Number) {
                return this.numericType.numericIs((NUMBER) value1, (NUMBER) value2);
            } else if (value1 instanceof Boolean boolean1) {
                return this.booleanType.booleanIs(boolean1, (Boolean) value2);
            } else if (value1 instanceof String string) {
                return this.stringType.stringIs(string, (String) value2);
            } else if (isDate(value1)) {
                return this.dateType.dateIs((DATE) value1, (DATE) value2);
            } else if (isTime(value1)) {
                return this.timeType.timeIs((TIME) value1, (TIME) value2);
            } else if (isDateTime(value1)) {
                return this.dateTimeType.dateTimeIs((DATE_TIME) value1, (DATE_TIME) value2);
            } else if (isDuration(value1)) {
                return this.durationType.durationIs((DURATION) value1, (DURATION) value2);
            } else if (value1 instanceof List list) {
                return this.listType.listIs(list, (List) value2);
            } else if (value1 instanceof Range range) {
                return this.rangeType.rangeIs(range, (Range) value2);
            } else if (value1 instanceof Context context) {
                return this.contextType.contextIs(context, (Context) value2);
            } else {
                logError("'%s' is not supported yet".formatted(value1.getClass().getSimpleName()));
                return false;
            }
        } catch (Exception e) {
            String message = "is(%s, %s)".formatted(value1, value2);
            logError(message, e);
            return false;
        }
    }

    @Override
    public DATE_TIME now() {
        try {
            return this.dateTimeLib.now();
        } catch (Exception e) {
            String message = "now()";
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE today() {
        try {
            return this.dateTimeLib.today();
        } catch (Exception e) {
            String message = "today()";
            logError(message, e);
            return null;
        }
    }

    //
    // Temporal functions
    //
    @Override
    public NUMBER dayOfYear(Object date) {
        try {
            return valueOf(this.dateTimeLib.dayOfYear(date));
        } catch (Exception e) {
            String message = "dayOfYear(%s)".formatted(date);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String dayOfWeek(Object date) {
        try {
            return this.dateTimeLib.dayOfWeek(date);
        } catch (Exception e) {
            String message = "dayOfWeek(%s)".formatted(date);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER weekOfYear(Object date) {
        try {
            return valueOf(this.dateTimeLib.weekOfYear(date));
        } catch (Exception e) {
            String message = "weekOfYear(%s)".formatted(date);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String monthOfYear(Object date) {
        try {
            return this.dateTimeLib.monthOfYear(date);
        } catch (Exception e) {
            String message = "weekOfYear(%s)".formatted(date);
            logError(message, e);
            return null;
        }
    }

    //
    // List functions
    //
    @Override
    public List append(List list, Object... items) {
        try {
            return this.listLib.append(list, items);
        } catch (Exception e) {
            String message = "append(%s, %s)".formatted(list, Arrays.toString(items));
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER count(List<?> list) {
        try {
            return this.numberLib.count(list);
        } catch (Exception e) {
            String message = "count(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> T min(List<T> list) {
        try {
            if (list == null || list.isEmpty()) {
                return null;
            }

            Object first = list.get(0);
            if (first instanceof Number) {
                return (T) this.numberLib.min(list);
            } else if (first instanceof String) {
                return (T) this.stringLib.min(list);
            } else if (isDate(first)) {
                return (T) this.dateTimeLib.min(list);
            } else if (isTime(first)) {
                return (T) this.dateTimeLib.min(list);
            } else if (isDateTime(first)) {
                return (T) this.dateTimeLib.min(list);
            } else if (isDuration(first)) {
                return (T) this.dateTimeLib.min(list);
            } else {
                throw new DMNRuntimeException("Not supported yet for '%s'".formatted(first));
            }
        } catch (Exception e) {
            String message = "min(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> T min(Object... args) {
        try {
            if (args == null || args.length < 1) {
                return null;
            }

            return (T) min(Arrays.asList(args));
        } catch (Exception e) {
            String message = "min(%s)".formatted(Arrays.toString(args));
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> T max(List<T> list) {
        try {
            if (list == null || list.isEmpty()) {
                return null;
            }

            Object first = list.get(0);
            if (first instanceof Number) {
                return (T) this.numberLib.max(list);
            } else if (first instanceof String) {
                return (T) this.stringLib.max(list);
            } else if (isDate(first)) {
                return (T) this.dateTimeLib.max(list);
            } else if (isTime(first)) {
                return (T) this.dateTimeLib.max(list);
            } else if (isDateTime(first)) {
                return (T) this.dateTimeLib.max(list);
            } else if (isDuration(first)) {
                return (T) this.dateTimeLib.max(list);
            } else {
                throw new DMNRuntimeException("Not supported yet for '%s'".formatted(first));
            }
        } catch (Exception e) {
            String message = "min(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> T max(Object... args) {
        try {
            if (args == null || args.length < 1) {
                return null;
            }

            return (T) max(Arrays.asList(args));
        } catch (Exception e) {
            String message = "max(%s)".formatted(Arrays.toString(args));
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER sum(List<?> list) {
        try {
            return this.numberLib.sum(list);
        } catch (Exception e) {
            String message = "sum(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER sum(Object... args) {
        try {
            return this.numberLib.sum(args);
        } catch (Exception e) {
            String message = "sum(%s)".formatted(Arrays.toString(args));
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> List<T> sublist(List<T> list, NUMBER startPosition) {
        try {
            return this.listLib.sublist(list, intValue(startPosition));
        } catch (Exception e) {
            String message = "sublist(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> List<T> sublist(List<T> list, NUMBER startPosition, NUMBER length) {
        try {
            return this.listLib.sublist(list, intValue(startPosition), intValue(length));
        } catch (Exception e) {
            String message = "sublist(%s, %s, %s)".formatted(list, startPosition, length);
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> List<T> concatenate(List<T>... lists) {
        try {
            return this.listLib.concatenate(lists);
        } catch (Exception e) {
            String message = "concatenate(%s)".formatted(Arrays.toString(lists));
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> List<T> insertBefore(List<T> list, NUMBER position, T newItem) {
        try {
            return this.listLib.insertBefore(list, intValue(position), newItem);
        } catch (Exception e) {
            String message = "insertBefore(%s, %s, %s)".formatted(list, position, newItem);
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> List<T> remove(List<T> list, Object position) {
        try {
            return this.listLib.remove(list, ((Number)position).intValue());
        } catch (Exception e) {
            String message = "remove(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> List<T> reverse(List<T> list) {
        try {
            return this.listLib.reverse(list);
        } catch (Exception e) {
            String message = "reverse(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> List<NUMBER> indexOf(List<T> list, Object match) {
        List<NUMBER> result = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Object o = list.get(i);
                if (o == null && match == null || o!= null && o.equals(match)) {
                    result.add(valueOf((long) i + 1));
                }
            }
        }
        return result;
    }

    @Override
    public <T> List<T> union(List<T>... lists) {
        try {
            return this.listLib.union(lists);
        } catch (Exception e) {
            String message = "union(%s)".formatted(Arrays.toString(lists));
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> List<T> distinctValues(List<T> list) {
        try {
            return this.listLib.distinctValues(list);
        } catch (Exception e) {
            String message = "distinctValues(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List flatten(List<?> list) {
        try {
            return this.listLib.flatten(list);
        } catch (Exception e) {
            String message = "flatten(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER product(List<?> list) {
        try {
            return this.numberLib.product(list);
        } catch (Exception e) {
            String message = "product(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER product(Object... args) {
        try {
            return this.numberLib.product(args);
        } catch (Exception e) {
            String message = "product(%s)".formatted(Arrays.toString(args));
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER median(List<?> list) {
        try {
            return this.numberLib.median(list);
        } catch (Exception e){
            String message = "median(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER median(Object... args) {
        try {
            return this.numberLib.median(args);
        } catch (Exception e) {
            String message = "median(%s)".formatted(Arrays.toString(args));
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER stddev(List<?> list) {
        try {
            return this.numberLib.stddev(list);
        } catch (Exception e) {
            String message = "stddev(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER stddev(Object... args) {
        try {
            return this.numberLib.stddev(args);
        } catch (Exception e) {
            String message = "stddev(%s)".formatted(Arrays.toString(args));
            logError(message, e);
            return null;
        }
    }

    @Override
    public List mode(List<?> list) {
        try {
            return this.numberLib.mode(list);
        } catch (Exception e) {
            String message = "mode(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List mode(Object... args) {
        try {
            return this.numberLib.mode(args);
        } catch (Exception e) {
            String message = "mode(%s)".formatted(Arrays.toString(args));
            logError(message, e);
            return null;
        }
    }

    @Override
    public void collect(List<?> result, List<?> list) {
        try {
            this.listLib.collect(result, list);
        } catch (Exception e) {
            String message = "collect(%s, %s)".formatted(result, list);
            logError(message, e);
        }
    }

    @Override
    public <T> List<T> sort(List<T> list, LambdaExpression<Boolean> comparator) {
        try {
            return this.listLib.sort(list, comparator);
        } catch (Exception e) {
            String message = "sort(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    //
    // Range functions
    //
    @Override
    public Boolean before(Object point1, Object point2) {
        try {
            return this.rangeLib.before(point1, point2);
        } catch (Exception e) {
            String message = "before(%s, %s)".formatted(point1, point2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean before(Object point, Range range) {
        try {
            return this.rangeLib.before(point, range);
        } catch (Exception e) {
            String message = "before(%s, %s)".formatted(point, range);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean before(Range range, Object point) {
        try {
            return this.rangeLib.before(range, point);
        } catch (Exception e) {
            String message = "before(%s, %s)".formatted(range, point);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean before(Range range1, Range range2) {
        try {
            return this.rangeLib.before(range1, range2);
        } catch (Exception e) {
            String message = "before(%s, %s)".formatted(range1, range2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean after(Object point1, Object point2) {
        try {
            return this.rangeLib.after(point1, point2);
        } catch (Exception e) {
            String message = "after(%s, %s)".formatted(point1, point2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean after(Object point, Range range) {
        try {
            return this.rangeLib.after(point, range);
        } catch (Exception e) {
            String message = "after(%s, %s)".formatted(point, range);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean after(Range range, Object point) {
        try {
            return this.rangeLib.after(range, point);
        } catch (Exception e) {
            String message = "after(%s, %s)".formatted(range, point);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean after(Range range1, Range range2) {
        try {
            return this.rangeLib.after(range1, range2);
        } catch (Exception e) {
            String message = "after(%s, %s)".formatted(range1, range2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean meets(Range range1, Range range2) {
        try {
            return this.rangeLib.meets(range1, range2);
        } catch (Exception e) {
            String message = "meets(%s, %s)".formatted(range1, range2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean metBy(Range range1, Range range2) {
        try {
            return this.rangeLib.metBy(range1, range2);
        } catch (Exception e) {
            String message = "metBy(%s, %s)".formatted(range1, range2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean overlaps(Range range1, Range range2) {
        try {
            return this.rangeLib.overlaps(range1, range2);
        } catch (Exception e) {
            String message = "overlaps(%s, %s)".formatted(range1, range2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean overlapsBefore(Range range1, Range range2) {
        try {
            return this.rangeLib.overlapsBefore(range1, range2);
        } catch (Exception e) {
            String message = "overlapsBefore(%s, %s)".formatted(range1, range2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean overlapsAfter(Range range1, Range range2) {
        try {
            return this.rangeLib.overlapsAfter(range1, range2);
        } catch (Exception e) {
            String message = "overlapsAfter(%s, %s)".formatted(range1, range2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean finishes(Object point, Range range) {
        try {
            return this.rangeLib.finishes(point, range);
        } catch (Exception e) {
            String message = "finishes(%s, %s)".formatted(point, range);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean finishes(Range range1, Range range2) {
        try {
            return this.rangeLib.finishes(range1, range2);
        } catch (Exception e) {
            String message = "finishes(%s, %s)".formatted(range1, range2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean finishedBy(Range range, Object point) {
        try {
            return this.rangeLib.finishedBy(range, point);
        } catch (Exception e) {
            String message = "finishedBy(%s, %s)".formatted(range, point);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean finishedBy(Range range1, Range range2) {
        try {
            return this.rangeLib.finishedBy(range1, range2);
        } catch (Exception e) {
            String message = "finishedBy(%s, %s)".formatted(range1, range2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean includes(Range range, Object point) {
        try {
            return this.rangeLib.includes(range, point);
        } catch (Exception e) {
            String message = "includes(%s, %s)".formatted(range, point);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean includes(Range range1, Range range2) {
        try {
            return this.rangeLib.includes(range1, range2);
        } catch (Exception e) {
            String message = "includes(%s, %s)".formatted(range1, range2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean during(Object point, Range range) {
        try {
            return this.rangeLib.during(point, range);
        } catch (Exception e) {
            String message = "during(%s, %s)".formatted(point, range);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean during(Range range1, Range range2) {
        try {
            return this.rangeLib.during(range1, range2);
        } catch (Exception e) {
            String message = "during(%s, %s)".formatted(range1, range2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean starts(Object point, Range range) {
        try {
            return this.rangeLib.starts(point, range);
        } catch (Exception e) {
            String message = "starts(%s, %s)".formatted(point, range);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean starts(Range range1, Range range2) {
        try {
            return this.rangeLib.starts(range1, range2);
        } catch (Exception e) {
            String message = "starts(%s, %s)".formatted(range1, range2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean startedBy(Range range, Object point) {
        try {
            return this.rangeLib.startedBy(range, point);
        } catch (Exception e) {
            String message = "startedBy(%s, %s)".formatted(range, point);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean startedBy(Range range1, Range range2) {
        try {
            return this.rangeLib.startedBy(range1, range2);
        } catch (Exception e) {
            String message = "startedBy(%s, %s)".formatted(range1, range2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean coincides(Object point1, Object point2) {
        try {
            return this.rangeLib.coincides(point1, point2);
        } catch (Exception e) {
            String message = "coincides(%s, %s)".formatted(point1, point2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean coincides(Range range1, Range range2) {
        try {
            return this.rangeLib.coincides(range1, range2);
        } catch (Exception e) {
            String message = "coincides(%s, %s)".formatted(range1, range2);
            logError(message, e);
            return null;
        }
    }
    @Override
    public Boolean isInstanceOf(Object value, String type) {
        try {
            if (value == null) {
                return "Null".equals(type);
            } else {
                return conformsTo(value, type);
            }
        } catch (Exception e) {
            String message = "instance of(%s, %s)".formatted(value, type);
            logError(message, e);
            return null;
        }
    }

    private Boolean conformsTo(Object value, String type) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case "Null": return value == null;
            case "Any": return value != null;
            case "number": return this.numericType.isNumber(value);
            case "string": return this.stringType.isString(value);
            case "boolean": return this.booleanType.isBoolean(value);
            case "date": return this.dateType.isDate(value);
            case "time": return this.timeType.isTime(value);
            case "date and time": return this.dateTimeType.isDateTime(value);
            case "years and months duration": return this.durationType.isYearsAndMonthsDuration(value);
            case "days and time duration": return this.durationType.isDaysAndTimeDuration(value);
            default: throw new DMNRuntimeException("instance of (%s, %s) is not supported yet".formatted(value, type));
        }
    }
}
