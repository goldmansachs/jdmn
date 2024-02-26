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

import com.gs.dmn.feel.lib.type.bool.BooleanType;
import com.gs.dmn.feel.lib.type.context.ContextType;
import com.gs.dmn.feel.lib.type.function.FunctionType;
import com.gs.dmn.feel.lib.type.list.ListType;
import com.gs.dmn.feel.lib.type.numeric.NumericType;
import com.gs.dmn.feel.lib.type.range.RangeType;
import com.gs.dmn.feel.lib.type.string.StringType;
import com.gs.dmn.feel.lib.type.time.DateTimeType;
import com.gs.dmn.feel.lib.type.time.DateType;
import com.gs.dmn.feel.lib.type.time.DurationType;
import com.gs.dmn.feel.lib.type.time.TimeType;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.LazyEval;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.Range;
import com.gs.dmn.runtime.listener.EventListener;
import com.gs.dmn.runtime.listener.Rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BaseFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> implements FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    protected final NumericType<NUMBER> numericType;
    protected final BooleanType booleanType;
    protected final StringType stringType;
    protected final DateType<DATE, DURATION> dateType;
    protected final TimeType<TIME, DURATION> timeType;
    protected final DateTimeType<DATE_TIME, DURATION> dateTimeType;
    protected final DurationType<DURATION, NUMBER> durationType;
    protected final ListType listType;
    protected final ContextType contextType;
    protected final RangeType rangeType;
    protected final FunctionType functionType;

    protected BaseFEELLib(NumericType<NUMBER> numericType, BooleanType booleanType, StringType stringType, DateType<DATE, DURATION> dateType, TimeType<TIME, DURATION> timeType, DateTimeType<DATE_TIME, DURATION> dateTimeType, DurationType<DURATION, NUMBER> durationType, ListType listType, ContextType contextType, RangeType rangeType, FunctionType functionType) {
        this.numericType = numericType;
        this.booleanType = booleanType;
        this.stringType = stringType;
        this.dateType = dateType;
        this.timeType = timeType;
        this.dateTimeType = dateTimeType;
        this.durationType = durationType;
        this.listType = listType;
        this.contextType = contextType;
        this.rangeType = rangeType;
        this.functionType = functionType;
    }

    //
    // Numeric operators
    //
    @Override
    public boolean isNumber(Object value) {
        try {
            return numericType.isNumber(value);
        } catch (Exception e) {
            String message = "isNumber(%s)".formatted(value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public NUMBER numericValue(NUMBER value) {
        try {
            return numericType.numericValue(value);
        } catch (Exception e) {
            String message = "numericValue(%s)".formatted(value);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean numericIs(NUMBER first, NUMBER second) {
        try {
            return numericType.numericIs(first, second);
        } catch (Exception e) {
            String message = "numericIs(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean numericEqual(NUMBER first, NUMBER second) {
        try {
            return numericType.numericEqual(first, second);
        } catch (Exception e) {
            String message = "numericEqual(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean numericNotEqual(NUMBER first, NUMBER second) {
        try {
            return numericType.numericNotEqual(first, second);
        } catch (Exception e) {
            String message = "numericNotEqual(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean numericLessThan(NUMBER first, NUMBER second) {
        try {
            return numericType.numericLessThan(first, second);
        } catch (Exception e) {
            String message = "numericLessThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean numericGreaterThan(NUMBER first, NUMBER second) {
        try {
            return numericType.numericGreaterThan(first, second);
        } catch (Exception e) {
            String message = "numericGreaterThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean numericLessEqualThan(NUMBER first, NUMBER second) {
        try {
            return numericType.numericLessEqualThan(first, second);
        } catch (Exception e) {
            String message = "numericLessEqualThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean numericGreaterEqualThan(NUMBER first, NUMBER second) {
        try {
            return numericType.numericGreaterEqualThan(first, second);
        } catch (Exception e) {
            String message = "numericGreaterEqualThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER numericAdd(NUMBER first, NUMBER second) {
        try {
            return numericType.numericAdd(first, second);
        } catch (Exception e) {
            String message = "numericAdd(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER numericSubtract(NUMBER first, NUMBER second) {
        try {
            return numericType.numericSubtract(first, second);
        } catch (Exception e) {
            String message = "numericSubtract(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER numericMultiply(NUMBER first, NUMBER second) {
        try {
            return numericType.numericMultiply(first, second);
        } catch (Exception e) {
            String message = "numericMultiply(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER numericDivide(NUMBER first, NUMBER second) {
        try {
            return numericType.numericDivide(first, second);
        } catch (Exception e) {
            String message = "numericDivide(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER numericUnaryMinus(NUMBER first) {
        try {
            return numericType.numericUnaryMinus(first);
        } catch (Exception e) {
            String message = "numericUnaryMinus(%s".formatted(first);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER numericExponentiation(NUMBER first, NUMBER second) {
        try {
            return numericType.numericExponentiation(first, second);
        } catch (Exception e) {
            String message = "numericExponentiation(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    //
    // Boolean operators
    //
    @Override
    public boolean isBoolean(Object value) {
        try {
            return this.booleanType.isBoolean(value);
        } catch (Exception e) {
            String message = "isBoolean(%s)".formatted(value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public Boolean booleanValue(Boolean value) {
        try {
            return booleanType.booleanValue(value);
        } catch (Exception e) {
            String message = "booleanValue(%s)".formatted(value);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean booleanIs(Boolean first, Boolean second) {
        try {
            return booleanType.booleanIs(first, second);
        } catch (Exception e) {
            String message = "booleanIs(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean booleanEqual(Boolean first, Boolean second) {
        try {
            return booleanType.booleanEqual(first, second);
        } catch (Exception e) {
            String message = "booleanEqual(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean booleanNotEqual(Boolean first, Boolean second) {
        try {
            return booleanType.booleanNotEqual(first, second);
        } catch (Exception e) {
            String message = "booleanNotEqual(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean booleanNot(Object operand) {
        try {
            return booleanType.booleanNot(operand);
        } catch (Exception e) {
            String message = "booleanNot(%s)".formatted(operand);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean booleanOr(List<Object> operands) {
        try {
            return booleanType.booleanOr(operands);
        } catch (Exception e) {
            String message = "booleanOr(%s)".formatted(operands);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean booleanOr(Object... operands) {
        try {
            return booleanType.booleanOr(operands);
        } catch (Exception e) {
            String message = "booleanOr(%s)".formatted(Arrays.toString(operands));
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean binaryBooleanOr(Object first, Object second) {
        try {
            return booleanType.binaryBooleanOr(first, second);
        } catch (Exception e) {
            String message = "binaryBooleanOr(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean booleanAnd(List<Object> operands) {
        try {
            return booleanType.booleanAnd(operands);
        } catch (Exception e) {
            String message = "booleanAnd(%s)".formatted(operands);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean booleanAnd(Object... operands) {
        try {
            return booleanType.booleanAnd(operands);
        } catch (Exception e) {
            String message = "booleanAnd(%s)".formatted(Arrays.toString(operands));
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean binaryBooleanAnd(Object first, Object second) {
        try {
            return booleanType.binaryBooleanAnd(first, second);
        } catch (Exception e) {
            String message = "binaryBooleanAnd(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Deprecated
    public Boolean booleanNot(Boolean operand) {
        try {
            return booleanType.booleanNot(operand);
        } catch (Exception e) {
            String message = "booleanNot(%s)".formatted(operand);
            logError(message, e);
            return null;
        }
    }

    @Deprecated
    public Boolean booleanOr(Boolean... operands) {
        try {
            return booleanType.booleanOr(operands);
        } catch (Exception e) {
            String message = "booleanOr(%s)".formatted(Arrays.toString(operands));
            logError(message, e);
            return null;
        }
    }

    @Deprecated
    public Boolean binaryBooleanOr(Boolean first, Boolean second) {
        try {
            return booleanType.binaryBooleanOr(first, second);
        } catch (Exception e) {
            String message = "binaryBooleanOr(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Deprecated
    public Boolean booleanAnd(Boolean... operands) {
        try {
            return booleanType.booleanAnd(operands);
        } catch (Exception e) {
            String message = "booleanAnd(%s)".formatted(Arrays.toString(operands));
            logError(message, e);
            return null;
        }
    }

    @Deprecated
    public Boolean binaryBooleanAnd(Boolean first, Boolean second) {
        try {
            return booleanType.binaryBooleanAnd(first, second);
        } catch (Exception e) {
            String message = "binaryBooleanAnd(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    //
    // String operators
    //
    @Override
    public boolean isString(Object value) {
        try {
            return stringType.isString(value);
        } catch (Exception e) {
            String message = "isString(%s)".formatted(value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public String stringValue(String value) {
        try {
            return stringType.stringValue(value);
        } catch (Exception e) {
            String message = "stringValue(%s)".formatted(value);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean stringIs(String first, String second) {
        try {
            return stringType.stringIs(first, second);
        } catch (Exception e) {
            String message = "stringIs(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean stringEqual(String first, String second) {
        try {
            return stringType.stringEqual(first, second);
        } catch (Exception e) {
            String message = "stringEqual(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean stringNotEqual(String first, String second) {
        try {
            return stringType.stringNotEqual(first, second);
        } catch (Exception e) {
            String message = "stringNotEqual(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean stringLessThan(String first, String second) {
        try {
            return stringType.stringLessThan(first, second);
        } catch (Exception e) {
            String message = "stringLessThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean stringGreaterThan(String first, String second) {
        try {
            return stringType.stringGreaterThan(first, second);
        } catch (Exception e) {
            String message = "stringGreaterThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean stringLessEqualThan(String first, String second) {
        try {
            return stringType.stringLessEqualThan(first, second);
        } catch (Exception e) {
            String message = "stringLessEqualThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean stringGreaterEqualThan(String first, String second) {
        try {
            return stringType.stringGreaterEqualThan(first, second);
        } catch (Exception e) {
            String message = "(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String stringAdd(String first, String second) {
        try {
            return stringType.stringAdd(first, second);
        } catch (Exception e) {
            String message = "stringAdd(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    //
    // Date operators
    //
    @Override
    public boolean isDate(Object value) {
        try {
            return this.dateType.isDate(value);
        } catch (Exception e) {
            String message = "isDate(%s)".formatted(value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public Long dateValue(DATE date) {
        try {
            return this.dateType.dateValue(date);
        } catch (Exception e) {
            String message = "dateValue(%s)".formatted(date);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateIs(DATE first, DATE second) {
        try {
            return dateType.dateIs(first, second);
        } catch (Exception e) {
            String message = "dateIs(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateEqual(DATE first, DATE second) {
        try {
            return dateType.dateEqual(first, second);
        } catch (Exception e) {
            String message = "dateEqual(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateNotEqual(DATE first, DATE second) {
        try {
            return dateType.dateNotEqual(first, second);
        } catch (Exception e) {
            String message = "dateNotEqual(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateLessThan(DATE first, DATE second) {
        try {
            return dateType.dateLessThan(first, second);
        } catch (Exception e) {
            String message = "dateLessThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateGreaterThan(DATE first, DATE second) {
        try {
            return dateType.dateGreaterThan(first, second);
        } catch (Exception e) {
            String message = "dateGreaterThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateLessEqualThan(DATE first, DATE second) {
        try {
            return dateType.dateLessEqualThan(first, second);
        } catch (Exception e) {
            String message = "dateLessEqualThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateGreaterEqualThan(DATE first, DATE second) {
        try {
            return dateType.dateGreaterEqualThan(first, second);
        } catch (Exception e) {
            String message = "dateGreaterEqualThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION dateSubtract(DATE first, Object second) {
        try {
            return dateType.dateSubtract(first, second);
        } catch (Exception e) {
            String message = "dateSubtract(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE dateAddDuration(DATE date, DURATION duration) {
        try {
            return dateType.dateAddDuration(date, duration);
        } catch (Exception e) {
            String message = "dateAddDuration(%s, %s)".formatted(date, duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE dateSubtractDuration(DATE date, DURATION duration) {
        try {
            return dateType.dateSubtractDuration(date, duration);
        } catch (Exception e) {
            String message = "dateSubtractDuration(%s, %s)".formatted(date, duration);
            logError(message, e);
            return null;
        }
    }

    //
    // Time operators
    //
    @Override
    public boolean isTime(Object value) {
        try {
            return this.timeType.isTime(value);
        } catch (Exception e) {
            String message = "isTime(%s)".formatted(value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public Long timeValue(TIME time) {
        try {
            return this.timeType.timeValue(time);
        } catch (Exception e) {
            String message = "timeValue(%s)".formatted(time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean timeIs(TIME first, TIME second) {
        try {
            return timeType.timeIs(first, second);
        } catch (Exception e) {
            String message = "timeIs(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean timeEqual(TIME first, TIME second) {
        try {
            return timeType.timeEqual(first, second);
        } catch (Exception e) {
            String message = "timeEqual(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean timeNotEqual(TIME first, TIME second) {
        try {
            return timeType.timeNotEqual(first, second);
        } catch (Exception e) {
            String message = "timeNotEqual(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean timeLessThan(TIME first, TIME second) {
        try {
            return timeType.timeLessThan(first, second);
        } catch (Exception e) {
            String message = "timeLessThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean timeGreaterThan(TIME first, TIME second) {
        try {
            return timeType.timeGreaterThan(first, second);
        } catch (Exception e) {
            String message = "timeGreaterThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean timeLessEqualThan(TIME first, TIME second) {
        try {
            return timeType.timeLessEqualThan(first, second);
        } catch (Exception e) {
            String message = "timeLessEqualThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean timeGreaterEqualThan(TIME first, TIME second) {
        try {
            return timeType.timeGreaterEqualThan(first, second);
        } catch (Exception e) {
            String message = "timeGreaterEqualThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION timeSubtract(TIME first, TIME second) {
        try {
            return timeType.timeSubtract(first, second);
        } catch (Exception e) {
            String message = "timeSubtract(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TIME timeAddDuration(TIME time, DURATION duration) {
        try {
            return timeType.timeAddDuration(time, duration);
        } catch (Exception e) {
            String message = "timeAddDuration(%s, %s)".formatted(time, duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TIME timeSubtractDuration(TIME time, DURATION duration) {
        try {
            return timeType.timeSubtractDuration(time, duration);
        } catch (Exception e) {
            String message = "timeSubtractDuration(%s, %s)".formatted(time, duration);
            logError(message, e);
            return null;
        }
    }

    //
    // Date and Time operators
    //
    @Override
    public boolean isDateTime(Object value) {
        try {
            return this.dateTimeType.isDateTime(value);
        } catch (Exception e) {
            String message = "isDateTime(%s)".formatted(value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public Long dateTimeValue(DATE_TIME dateTime) {
        try {
            return this.dateTimeType.dateTimeValue(dateTime);
        } catch (Exception e) {
            String message = "dateTimeValue(%s)".formatted(dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateTimeIs(DATE_TIME first, DATE_TIME second) {
        try {
            return dateTimeType.dateTimeIs(first, second);
        } catch (Exception e) {
            String message = "dateTimeIs(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateTimeEqual(DATE_TIME first, DATE_TIME second) {
        try {
            return dateTimeType.dateTimeEqual(first, second);
        } catch (Exception e) {
            String message = "dateTimeEqual(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateTimeNotEqual(DATE_TIME first, DATE_TIME second) {
        try {
            return dateTimeType.dateTimeNotEqual(first, second);
        } catch (Exception e) {
            String message = "dateTimeNotEqual(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateTimeLessThan(DATE_TIME first, DATE_TIME second) {
        try {
            return dateTimeType.dateTimeLessThan(first, second);
        } catch (Exception e) {
            String message = "dateTimeLessThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateTimeGreaterThan(DATE_TIME first, DATE_TIME second) {
        try {
            return dateTimeType.dateTimeGreaterThan(first, second);
        } catch (Exception e) {
            String message = "dateTimeGreaterThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateTimeLessEqualThan(DATE_TIME first, DATE_TIME second) {
        try {
            return dateTimeType.dateTimeLessEqualThan(first, second);
        } catch (Exception e) {
            String message = "dateTimeLessEqualThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateTimeGreaterEqualThan(DATE_TIME first, DATE_TIME second) {
        try {
            return dateTimeType.dateTimeGreaterEqualThan(first, second);
        } catch (Exception e) {
            String message = "dateTimeGreaterEqualThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION dateTimeSubtract(DATE_TIME first, Object second) {
        try {
            if (isDate(first)) {
                first = toDateTime(first);
            }
            if (isDate(second)) {
                second = toDateTime(second);
            }
            return dateTimeType.dateTimeSubtract(first, second);
        } catch (Exception e) {
            String message = "dateTimeSubtract(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE_TIME dateTimeAddDuration(DATE_TIME dateTime, DURATION duration) {
        try {
            return dateTimeType.dateTimeAddDuration(dateTime, duration);
        } catch (Exception e) {
            String message = "dateTimeAddDuration(%s, %s)".formatted(dateTime, duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE_TIME dateTimeSubtractDuration(DATE_TIME dateTime, DURATION duration) {
        try {
            return dateTimeType.dateTimeSubtractDuration(dateTime, duration);
        } catch (Exception e) {
            String message = "dateTimeSubtractDuration(%s, %s)".formatted(dateTime, duration);
            logError(message, e);
            return null;
        }
    }

    //
    // Duration operators
    //
    @Override
    public boolean isYearsAndMonthsDuration(Object value) {
        try {
            return this.durationType.isYearsAndMonthsDuration(value);
        } catch (Exception e) {
            String message = "isYearsAndMonthsDuration(%s)".formatted(value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public boolean isDaysAndTimeDuration(Object value) {
        try {
            return this.durationType.isDaysAndTimeDuration(value);
        } catch (Exception e) {
            String message = "isDaysAndTimeDuration(%s)".formatted(value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public Boolean durationIs(DURATION first, DURATION second) {
        try {
            return durationType.durationIs(first, second);
        } catch (Exception e) {
            String message = "durationIs(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Long durationValue(DURATION duration) {
        try {
            return this.durationType.durationValue(duration);
        } catch (Exception e) {
            String message = "durationValue(%s)".formatted(duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean durationEqual(DURATION first, DURATION second) {
        try {
            return durationType.durationEqual(first, second);
        } catch (Exception e) {
            String message = "durationEqual(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean durationNotEqual(DURATION first, DURATION second) {
        try {
            return durationType.durationNotEqual(first, second);
        } catch (Exception e) {
            String message = "durationNotEqual(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean durationLessThan(DURATION first, DURATION second) {
        try {
            return durationType.durationLessThan(first, second);
        } catch (Exception e) {
            String message = "durationLessThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean durationGreaterThan(DURATION first, DURATION second) {
        try {
            return durationType.durationGreaterThan(first, second);
        } catch (Exception e) {
            String message = "durationGreaterThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean durationLessEqualThan(DURATION first, DURATION second) {
        try {
            return durationType.durationLessEqualThan(first, second);
        } catch (Exception e) {
            String message = "durationLessEqualThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean durationGreaterEqualThan(DURATION first, DURATION second) {
        try {
            return durationType.durationGreaterEqualThan(first, second);
        } catch (Exception e) {
            String message = "durationGreaterEqualThan(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION durationAdd(DURATION first, DURATION second) {
        try {
            return durationType.durationAdd(first, second);
        } catch (Exception e) {
            String message = "durationAdd(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION durationSubtract(DURATION first, DURATION second) {
        try {
            return durationType.durationSubtract(first, second);
        } catch (Exception e) {
            String message = "durationSubtract(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER durationDivide(DURATION first, DURATION second) {
        try {
            return durationType.durationDivide(first, second);
        } catch (Exception e) {
            String message = "durationMultiply(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION durationMultiplyNumber(DURATION first, NUMBER second) {
        try {
            return durationType.durationMultiplyNumber(first, second);
        } catch (Exception e) {
            String message = "durationMultiply(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION durationDivideNumber(DURATION first, NUMBER second) {
        try {
            return durationType.durationDivideNumber(first, second);
        } catch (Exception e) {
            String message = "durationDivide(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    //
    // List operators
    //
    @Override
    public boolean isList(Object value) {
        try {
            return listType.isList(value);
        } catch (Exception e) {
            String message = "isList(%s)".formatted(value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public List listValue(List value) {
        try {
            return this.listType.listValue(value);
        } catch (Exception e) {
            String message = "listValue(%s)".formatted(value);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean listIs(List first, List second) {
        try {
            return listType.listIs(first, second);
        } catch (Exception e) {
            String message = "listIs(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean listEqual(List list1, List list2) {
        try {
            return listType.listEqual(list1, list2);
        } catch (Exception e) {
            String message = "listEqual(%s, %s)".formatted(list1, list2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean listNotEqual(List list1, List list2) {
        try {
            return listType.listNotEqual(list1, list2);
        } catch (Exception e) {
            String message = "listNotEqual(%s, %s)".formatted(list1, list2);
            logError(message, e);
            return null;
        }
    }

    //
    // Context operators
    //
    @Override
    public boolean isContext(Object value) {
        try {
            return contextType.isContext(value);
        } catch (Exception e) {
            String message = "isContext(%s)".formatted(value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public Context contextValue(Context value) {
        try {
            return contextType.contextValue(value);
        } catch (Exception e) {
            String message = "contextValue(%s)".formatted(value);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean contextIs(Context c1, Context c2) {
        try {
            return contextType.contextIs(c1, c2);
        } catch (Exception e) {
            String message = "contextIs(%s, %s)".formatted(c1, c2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean contextEqual(Context c1, Context c2) {
        try {
            return contextType.contextEqual(c1, c2);
        } catch (Exception e) {
            String message = "contextEqual(%s, %s)".formatted(c1, c2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean contextNotEqual(Context c1, Context c2) {
        try {
            return contextType.contextNotEqual(c1, c2);
        } catch (Exception e) {
            String message = "contextNotEqual(%s, %s)".formatted(c1, c2);
            logError(message, e);
            return null;
        }
    }

    //
    // Context functions
    //
    @Override
    public List getEntries(Context m) {
        try {
            return contextType.getEntries(m);
        } catch (Exception e) {
            String message = "getEntries(%s)".formatted(m);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Object getValue(Context m, Object key) {
        try {
            return contextType.getValue(m, key);
        } catch (Exception e) {
            String message = "getValue(%s, %s)".formatted(m, key);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Context context(List entries) {
        try {
            return contextType.context(entries);
        } catch (Exception e) {
            String message = "context(%s)".formatted(entries);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Context contextPut(Context context, String key, Object value) {
        try {
            return contextType.contextPut(context, key, value);
        } catch (Exception e) {
            String message = "contextPut(%s, %s, %s)".formatted(context, key, value);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Context contextPut(Context context, List<String> keys, Object value) {
        try {
            return contextType.contextPut(context, keys, value);
        } catch (Exception e) {
            String message = "contextPut(%s, %s, %s)".formatted(context, keys, value);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Context contextMerge(List<?> contexts) {
        try {
            return contextType.contextMerge(contexts);
        } catch (Exception e) {
            String message = "contextMerge(%s)".formatted(contexts);
            logError(message, e);
            return null;
        }
    }

    //
    // Range operators
    //
    @Override
    public boolean isRange(Object value) {
        try {
            return rangeType.isRange(value);
        } catch (Exception e) {
            String message = "isRange(%s)".formatted(value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public Range rangeValue(Range value) {
        try {
            return rangeType.rangeValue(value);
        } catch (Exception e) {
            String message = "rangeValue(%s)".formatted(value);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean rangeIs(Range range1, Range range2) {
        try {
            return rangeType.rangeIs(range1, range2);
        } catch (Exception e) {
            String message = "rangeIs(%s, %s)".formatted(range1, range2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean rangeEqual(Range range1, Range range2) {
        try {
            return rangeType.rangeEqual(range1, range2);
        } catch (Exception e) {
            String message = "rangeEqual(%s, %s)".formatted(range1, range2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean rangeNotEqual(Range range1, Range range2) {
        try {
            return rangeType.rangeNotEqual(range1, range2);
        } catch (Exception e) {
            String message = "rangeNotEqual(%s, %s)".formatted(range1, range2);
            logError(message, e);
            return null;
        }
    }

    //
    // Function operators
    //
    @Override
    public boolean isFunction(Object value) {
        try {
            return functionType.isFunction(value);
        } catch (Exception e) {
            String message = "isFunction(%s)".formatted(value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public Object functionValue(Object value) {
        try {
            return functionType.functionValue(value);
        } catch (Exception e) {
            String message = "functionValue(%s)".formatted(value);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean functionIs(Object function1, Object function2) {
        try {
            return functionType.functionIs(function1, function2);
        } catch (Exception e) {
            String message = "functionIs(%s, %s)".formatted(function1, function2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean functionEqual(Object function1, Object function2) {
        try {
            return functionType.functionEqual(function1, function2);
        } catch (Exception e) {
            String message = "functionEqual(%s, %s)".formatted(function1, function2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean functionNotEqual(Object function1, Object function2) {
        try {
            return functionType.functionNotEqual(function1, function2);
        } catch (Exception e) {
            String message = "functionNotEqual(%s, %s)".formatted(function1, function2);
            logError(message, e);
            return null;
        }
    }

    //
    // Implicit conversion functions
    //
    @Override
    public<T> List<T> asList(T ...objects) {
        if (objects == null) {
            List<T> result = new ArrayList<>();
            result.add(null);
            return result;
        } else {
            return Arrays.asList(objects);
        }
    }

    @Override
    public<T> T asElement(List<T> list) {
        if (list == null) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            return null;
        }
    }

    //
    // Extra conversion functions
    //
    @Override
    public List<NUMBER> rangeToList(boolean isOpenStart, NUMBER start, boolean isOpenEnd, NUMBER end) {
        return rangeToStream(isOpenStart, start, isOpenEnd, end).collect(Collectors.toList());
    }

    @Override
    public List<NUMBER> rangeToList(NUMBER start, NUMBER end) {
        return rangeToStream(start, end).collect(Collectors.toList());
    }

    @Override
    public Stream<NUMBER> rangeToStream(boolean isOpenStart, NUMBER start, boolean isOpenEnd, NUMBER end) {
        Pair<Integer, Integer> intRange = intRange(isOpenStart, (Number) start, isOpenEnd, (Number) end);
        if (intRange == null) {
            return Stream.of();
        }

        return intRangeToStream(intRange.getLeft(), intRange.getRight());
    }

    @Override
    public Stream<NUMBER> rangeToStream(NUMBER start, NUMBER end) {
        Pair<Integer, Integer> intRange = intRange((Number) start, (Number) end);
        if (intRange == null) {
            return Stream.of();
        }

        return intRangeToStream(intRange.getLeft(), intRange.getRight());
    }

    private Pair<Integer, Integer> intRange(boolean isOpenStart, Number start, boolean isOpenEnd, Number end) {
        if (start == null || end == null) {
            return null;
        }
        int startValue = isOpenStart ? start.intValue() + 1 : start.intValue();
        int endValue = isOpenEnd ? end.intValue() - 1 : end.intValue();
        return new Pair<>(startValue, endValue);
    }

    private Pair<Integer, Integer> intRange(Number start, Number end) {
        if (start == null || end == null) {
            return null;
        }
        int startValue = start.intValue();
        int endValue = end.intValue();
        return new Pair<>(startValue, endValue);
    }

    private Stream<NUMBER> intRangeToStream(int startValue, int endValue) {
        if (startValue <= endValue) {
            return Stream.iterate(valueOf(startValue), (i) -> numericAdd(i, valueOf(1))).limit((long) endValue - startValue + 1).sequential();
        } else {
            return Stream.iterate(valueOf(startValue), (i) -> numericSubtract(i, valueOf(1))).limit((long) startValue - endValue + 1).sequential();
        }
    }

    @Override
    public <T> T elementAt(List<?> list, NUMBER index) {
        return elementAt(list, intValue(index));
    }

    private <T> T elementAt(List<?> list, int index) {
        if (list == null) {
            return null;
        }
        int listSize = list.size();
        if (1 <= index && index <= listSize) {
            return (T) list.get(index - 1);
        } else if (-listSize <= index && index <= -1) {
            return (T) list.get(listSize + index);
        } else {
            return null;
        }
    }

    @Override
    public Boolean listContains(List<?> list, Object element) {
        return list == null ? null : list.contains(element);
    }

    @Override
    public List flattenFirstLevel(List<?> list) {
        if (list == null) {
            return null;
        }
        List result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof List list1) {
                result.addAll(list1);
            } else {
                result.add(object);
            }
        }
        return result;
    }

    public boolean ruleMatches(EventListener eventListener, Rule rule, Object... operands) {
        if (operands == null || operands.length == 0) {
            return false;
        } else {
            for (int i=0; i<operands.length; i++) {
                Object operand = operands[i];
                if (operand instanceof LazyEval eval) {
                    operand = eval.getOrCompute();
                }

                // Column index starts first 1
                eventListener.matchColumn(rule, i + 1, operand);

                if (operand == null || operand == Boolean.FALSE) {
                    return false;
                }
            }
            return true;
        }
    }

    protected abstract NUMBER valueOf(long number);
    protected abstract int intValue(NUMBER number);
}
