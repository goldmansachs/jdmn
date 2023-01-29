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
            String message = String.format("isNumber(%s)", value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public NUMBER numericValue(NUMBER value) {
        try {
            return numericType.numericValue(value);
        } catch (Exception e) {
            String message = String.format("numericValue(%s)", value);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean numericIs(NUMBER first, NUMBER second) {
        try {
            return numericType.numericIs(first, second);
        } catch (Exception e) {
            String message = String.format("numericIs(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean numericEqual(NUMBER first, NUMBER second) {
        try {
            return numericType.numericEqual(first, second);
        } catch (Exception e) {
            String message = String.format("numericEqual(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean numericNotEqual(NUMBER first, NUMBER second) {
        try {
            return numericType.numericNotEqual(first, second);
        } catch (Exception e) {
            String message = String.format("numericNotEqual(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean numericLessThan(NUMBER first, NUMBER second) {
        try {
            return numericType.numericLessThan(first, second);
        } catch (Exception e) {
            String message = String.format("numericLessThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean numericGreaterThan(NUMBER first, NUMBER second) {
        try {
            return numericType.numericGreaterThan(first, second);
        } catch (Exception e) {
            String message = String.format("numericGreaterThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean numericLessEqualThan(NUMBER first, NUMBER second) {
        try {
            return numericType.numericLessEqualThan(first, second);
        } catch (Exception e) {
            String message = String.format("numericLessEqualThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean numericGreaterEqualThan(NUMBER first, NUMBER second) {
        try {
            return numericType.numericGreaterEqualThan(first, second);
        } catch (Exception e) {
            String message = String.format("numericGreaterEqualThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER numericAdd(NUMBER first, NUMBER second) {
        try {
            return numericType.numericAdd(first, second);
        } catch (Exception e) {
            String message = String.format("numericAdd(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER numericSubtract(NUMBER first, NUMBER second) {
        try {
            return numericType.numericSubtract(first, second);
        } catch (Exception e) {
            String message = String.format("numericSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER numericMultiply(NUMBER first, NUMBER second) {
        try {
            return numericType.numericMultiply(first, second);
        } catch (Exception e) {
            String message = String.format("numericMultiply(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER numericDivide(NUMBER first, NUMBER second) {
        try {
            return numericType.numericDivide(first, second);
        } catch (Exception e) {
            String message = String.format("numericDivide(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER numericUnaryMinus(NUMBER first) {
        try {
            return numericType.numericUnaryMinus(first);
        } catch (Exception e) {
            String message = String.format("numericUnaryMinus(%s", first);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER numericExponentiation(NUMBER first, NUMBER second) {
        try {
            return numericType.numericExponentiation(first, second);
        } catch (Exception e) {
            String message = String.format("numericExponentiation(%s, %s)", first, second);
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
            String message = String.format("isBoolean(%s)", value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public Boolean booleanValue(Boolean value) {
        try {
            return booleanType.booleanValue(value);
        } catch (Exception e) {
            String message = String.format("booleanValue(%s)", value);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean booleanIs(Boolean first, Boolean second) {
        try {
            return booleanType.booleanIs(first, second);
        } catch (Exception e) {
            String message = String.format("booleanIs(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean booleanEqual(Boolean first, Boolean second) {
        try {
            return booleanType.booleanEqual(first, second);
        } catch (Exception e) {
            String message = String.format("booleanEqual(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean booleanNotEqual(Boolean first, Boolean second) {
        try {
            return booleanType.booleanNotEqual(first, second);
        } catch (Exception e) {
            String message = String.format("booleanNotEqual(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean booleanNot(Object operand) {
        try {
            return booleanType.booleanNot(operand);
        } catch (Exception e) {
            String message = String.format("booleanNot(%s)", operand);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean booleanOr(List<Object> operands) {
        try {
            return booleanType.booleanOr(operands);
        } catch (Exception e) {
            String message = String.format("booleanOr(%s)", operands);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean booleanOr(Object... operands) {
        try {
            return booleanType.booleanOr(operands);
        } catch (Exception e) {
            String message = String.format("booleanOr(%s)", Arrays.toString(operands));
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean binaryBooleanOr(Object first, Object second) {
        try {
            return booleanType.binaryBooleanOr(first, second);
        } catch (Exception e) {
            String message = String.format("binaryBooleanOr(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean booleanAnd(List<Object> operands) {
        try {
            return booleanType.booleanAnd(operands);
        } catch (Exception e) {
            String message = String.format("booleanAnd(%s)", operands);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean booleanAnd(Object... operands) {
        try {
            return booleanType.booleanAnd(operands);
        } catch (Exception e) {
            String message = String.format("booleanAnd(%s)", Arrays.toString(operands));
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean binaryBooleanAnd(Object first, Object second) {
        try {
            return booleanType.binaryBooleanAnd(first, second);
        } catch (Exception e) {
            String message = String.format("binaryBooleanAnd(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Deprecated
    public Boolean booleanNot(Boolean operand) {
        try {
            return booleanType.booleanNot(operand);
        } catch (Exception e) {
            String message = String.format("booleanNot(%s)", operand);
            logError(message, e);
            return null;
        }
    }

    @Deprecated
    public Boolean booleanOr(Boolean... operands) {
        try {
            return booleanType.booleanOr(operands);
        } catch (Exception e) {
            String message = String.format("booleanOr(%s)", Arrays.toString(operands));
            logError(message, e);
            return null;
        }
    }

    @Deprecated
    public Boolean binaryBooleanOr(Boolean first, Boolean second) {
        try {
            return booleanType.binaryBooleanOr(first, second);
        } catch (Exception e) {
            String message = String.format("binaryBooleanOr(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Deprecated
    public Boolean booleanAnd(Boolean... operands) {
        try {
            return booleanType.booleanAnd(operands);
        } catch (Exception e) {
            String message = String.format("booleanAnd(%s)", Arrays.toString(operands));
            logError(message, e);
            return null;
        }
    }

    @Deprecated
    public Boolean binaryBooleanAnd(Boolean first, Boolean second) {
        try {
            return booleanType.binaryBooleanAnd(first, second);
        } catch (Exception e) {
            String message = String.format("binaryBooleanAnd(%s, %s)", first, second);
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
            String message = String.format("isString(%s)", value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public String stringValue(String value) {
        try {
            return stringType.stringValue(value);
        } catch (Exception e) {
            String message = String.format("stringValue(%s)", value);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean stringIs(String first, String second) {
        try {
            return stringType.stringIs(first, second);
        } catch (Exception e) {
            String message = String.format("stringIs(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean stringEqual(String first, String second) {
        try {
            return stringType.stringEqual(first, second);
        } catch (Exception e) {
            String message = String.format("stringEqual(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean stringNotEqual(String first, String second) {
        try {
            return stringType.stringNotEqual(first, second);
        } catch (Exception e) {
            String message = String.format("stringNotEqual(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean stringLessThan(String first, String second) {
        try {
            return stringType.stringLessThan(first, second);
        } catch (Exception e) {
            String message = String.format("stringLessThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean stringGreaterThan(String first, String second) {
        try {
            return stringType.stringGreaterThan(first, second);
        } catch (Exception e) {
            String message = String.format("stringGreaterThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean stringLessEqualThan(String first, String second) {
        try {
            return stringType.stringLessEqualThan(first, second);
        } catch (Exception e) {
            String message = String.format("stringLessEqualThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean stringGreaterEqualThan(String first, String second) {
        try {
            return stringType.stringGreaterEqualThan(first, second);
        } catch (Exception e) {
            String message = String.format("(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String stringAdd(String first, String second) {
        try {
            return stringType.stringAdd(first, second);
        } catch (Exception e) {
            String message = String.format("stringAdd(%s, %s)", first, second);
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
            String message = String.format("isDate(%s)", value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public Long dateValue(DATE date) {
        try {
            return this.dateType.dateValue(date);
        } catch (Exception e) {
            String message = String.format("dateValue(%s)", date);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateIs(DATE first, DATE second) {
        try {
            return dateType.dateIs(first, second);
        } catch (Exception e) {
            String message = String.format("dateIs(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateEqual(DATE first, DATE second) {
        try {
            return dateType.dateEqual(first, second);
        } catch (Exception e) {
            String message = String.format("dateEqual(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateNotEqual(DATE first, DATE second) {
        try {
            return dateType.dateNotEqual(first, second);
        } catch (Exception e) {
            String message = String.format("dateNotEqual(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateLessThan(DATE first, DATE second) {
        try {
            return dateType.dateLessThan(first, second);
        } catch (Exception e) {
            String message = String.format("dateLessThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateGreaterThan(DATE first, DATE second) {
        try {
            return dateType.dateGreaterThan(first, second);
        } catch (Exception e) {
            String message = String.format("dateGreaterThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateLessEqualThan(DATE first, DATE second) {
        try {
            return dateType.dateLessEqualThan(first, second);
        } catch (Exception e) {
            String message = String.format("dateLessEqualThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateGreaterEqualThan(DATE first, DATE second) {
        try {
            return dateType.dateGreaterEqualThan(first, second);
        } catch (Exception e) {
            String message = String.format("dateGreaterEqualThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION dateSubtract(DATE first, Object second) {
        try {
            return dateType.dateSubtract(first, second);
        } catch (Exception e) {
            String message = String.format("dateSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE dateAddDuration(DATE date, DURATION duration) {
        try {
            return dateType.dateAddDuration(date, duration);
        } catch (Exception e) {
            String message = String.format("dateAddDuration(%s, %s)", date, duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE dateSubtractDuration(DATE date, DURATION duration) {
        try {
            return dateType.dateSubtractDuration(date, duration);
        } catch (Exception e) {
            String message = String.format("dateSubtractDuration(%s, %s)", date, duration);
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
            String message = String.format("isTime(%s)", value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public Long timeValue(TIME time) {
        try {
            return this.timeType.timeValue(time);
        } catch (Exception e) {
            String message = String.format("timeValue(%s)", time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean timeIs(TIME first, TIME second) {
        try {
            return timeType.timeIs(first, second);
        } catch (Exception e) {
            String message = String.format("timeIs(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean timeEqual(TIME first, TIME second) {
        try {
            return timeType.timeEqual(first, second);
        } catch (Exception e) {
            String message = String.format("timeEqual(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean timeNotEqual(TIME first, TIME second) {
        try {
            return timeType.timeNotEqual(first, second);
        } catch (Exception e) {
            String message = String.format("timeNotEqual(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean timeLessThan(TIME first, TIME second) {
        try {
            return timeType.timeLessThan(first, second);
        } catch (Exception e) {
            String message = String.format("timeLessThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean timeGreaterThan(TIME first, TIME second) {
        try {
            return timeType.timeGreaterThan(first, second);
        } catch (Exception e) {
            String message = String.format("timeGreaterThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean timeLessEqualThan(TIME first, TIME second) {
        try {
            return timeType.timeLessEqualThan(first, second);
        } catch (Exception e) {
            String message = String.format("timeLessEqualThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean timeGreaterEqualThan(TIME first, TIME second) {
        try {
            return timeType.timeGreaterEqualThan(first, second);
        } catch (Exception e) {
            String message = String.format("timeGreaterEqualThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION timeSubtract(TIME first, TIME second) {
        try {
            return timeType.timeSubtract(first, second);
        } catch (Exception e) {
            String message = String.format("timeSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TIME timeAddDuration(TIME time, DURATION duration) {
        try {
            return timeType.timeAddDuration(time, duration);
        } catch (Exception e) {
            String message = String.format("timeAddDuration(%s, %s)", time, duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TIME timeSubtractDuration(TIME time, DURATION duration) {
        try {
            return timeType.timeSubtractDuration(time, duration);
        } catch (Exception e) {
            String message = String.format("timeSubtractDuration(%s, %s)", time, duration);
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
            String message = String.format("isDateTime(%s)", value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public Long dateTimeValue(DATE_TIME dateTime) {
        try {
            return this.dateTimeType.dateTimeValue(dateTime);
        } catch (Exception e) {
            String message = String.format("dateTimeValue(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateTimeIs(DATE_TIME first, DATE_TIME second) {
        try {
            return dateTimeType.dateTimeIs(first, second);
        } catch (Exception e) {
            String message = String.format("dateTimeIs(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateTimeEqual(DATE_TIME first, DATE_TIME second) {
        try {
            return dateTimeType.dateTimeEqual(first, second);
        } catch (Exception e) {
            String message = String.format("dateTimeEqual(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateTimeNotEqual(DATE_TIME first, DATE_TIME second) {
        try {
            return dateTimeType.dateTimeNotEqual(first, second);
        } catch (Exception e) {
            String message = String.format("dateTimeNotEqual(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateTimeLessThan(DATE_TIME first, DATE_TIME second) {
        try {
            return dateTimeType.dateTimeLessThan(first, second);
        } catch (Exception e) {
            String message = String.format("dateTimeLessThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateTimeGreaterThan(DATE_TIME first, DATE_TIME second) {
        try {
            return dateTimeType.dateTimeGreaterThan(first, second);
        } catch (Exception e) {
            String message = String.format("dateTimeGreaterThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateTimeLessEqualThan(DATE_TIME first, DATE_TIME second) {
        try {
            return dateTimeType.dateTimeLessEqualThan(first, second);
        } catch (Exception e) {
            String message = String.format("dateTimeLessEqualThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean dateTimeGreaterEqualThan(DATE_TIME first, DATE_TIME second) {
        try {
            return dateTimeType.dateTimeGreaterEqualThan(first, second);
        } catch (Exception e) {
            String message = String.format("dateTimeGreaterEqualThan(%s, %s)", first, second);
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
            String message = String.format("dateTimeSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE_TIME dateTimeAddDuration(DATE_TIME dateTime, DURATION duration) {
        try {
            return dateTimeType.dateTimeAddDuration(dateTime, duration);
        } catch (Exception e) {
            String message = String.format("dateTimeAddDuration(%s, %s)", dateTime, duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE_TIME dateTimeSubtractDuration(DATE_TIME dateTime, DURATION duration) {
        try {
            return dateTimeType.dateTimeSubtractDuration(dateTime, duration);
        } catch (Exception e) {
            String message = String.format("dateTimeSubtractDuration(%s, %s)", dateTime, duration);
            logError(message, e);
            return null;
        }
    }

    //
    // Duration operators
    //
    @Override
    public boolean isDuration(Object value) {
        try {
            return this.durationType.isDuration(value);
        } catch (Exception e) {
            String message = String.format("isDuration(%s)", value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public boolean isYearsAndMonthsDuration(Object value) {
        try {
            return this.durationType.isYearsAndMonthsDuration(value);
        } catch (Exception e) {
            String message = String.format("isYearsAndMonthsDuration(%s)", value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public boolean isDaysAndTimeDuration(Object value) {
        try {
            return this.durationType.isDaysAndTimeDuration(value);
        } catch (Exception e) {
            String message = String.format("isDaysAndTimeDuration(%s)", value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public Boolean durationIs(DURATION first, DURATION second) {
        try {
            return durationType.durationIs(first, second);
        } catch (Exception e) {
            String message = String.format("durationIs(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Long durationValue(DURATION duration) {
        try {
            return this.durationType.durationValue(duration);
        } catch (Exception e) {
            String message = String.format("durationValue(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean durationEqual(DURATION first, DURATION second) {
        try {
            return durationType.durationEqual(first, second);
        } catch (Exception e) {
            String message = String.format("durationEqual(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean durationNotEqual(DURATION first, DURATION second) {
        try {
            return durationType.durationNotEqual(first, second);
        } catch (Exception e) {
            String message = String.format("durationNotEqual(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean durationLessThan(DURATION first, DURATION second) {
        try {
            return durationType.durationLessThan(first, second);
        } catch (Exception e) {
            String message = String.format("durationLessThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean durationGreaterThan(DURATION first, DURATION second) {
        try {
            return durationType.durationGreaterThan(first, second);
        } catch (Exception e) {
            String message = String.format("durationGreaterThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean durationLessEqualThan(DURATION first, DURATION second) {
        try {
            return durationType.durationLessEqualThan(first, second);
        } catch (Exception e) {
            String message = String.format("durationLessEqualThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean durationGreaterEqualThan(DURATION first, DURATION second) {
        try {
            return durationType.durationGreaterEqualThan(first, second);
        } catch (Exception e) {
            String message = String.format("durationGreaterEqualThan(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION durationAdd(DURATION first, DURATION second) {
        try {
            return durationType.durationAdd(first, second);
        } catch (Exception e) {
            String message = String.format("durationAdd(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION durationSubtract(DURATION first, DURATION second) {
        try {
            return durationType.durationSubtract(first, second);
        } catch (Exception e) {
            String message = String.format("durationSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER durationDivide(DURATION first, DURATION second) {
        try {
            return durationType.durationDivide(first, second);
        } catch (Exception e) {
            String message = String.format("durationMultiply(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION durationMultiplyNumber(DURATION first, NUMBER second) {
        try {
            return durationType.durationMultiplyNumber(first, second);
        } catch (Exception e) {
            String message = String.format("durationMultiply(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION durationDivideNumber(DURATION first, NUMBER second) {
        try {
            return durationType.durationDivideNumber(first, second);
        } catch (Exception e) {
            String message = String.format("durationDivide(%s, %s)", first, second);
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
            String message = String.format("isList(%s)", value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public List listValue(List value) {
        try {
            return this.listType.listValue(value);
        } catch (Exception e) {
            String message = String.format("listValue(%s)", value);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean listIs(List first, List second) {
        try {
            return listType.listIs(first, second);
        } catch (Exception e) {
            String message = String.format("listIs(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean listEqual(List list1, List list2) {
        try {
            return listType.listEqual(list1, list2);
        } catch (Exception e) {
            String message = String.format("listEqual(%s, %s)", list1, list2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean listNotEqual(List list1, List list2) {
        try {
            return listType.listNotEqual(list1, list2);
        } catch (Exception e) {
            String message = String.format("listNotEqual(%s, %s)", list1, list2);
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
            String message = String.format("isContext(%s)", value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public Context contextValue(Context value) {
        try {
            return contextType.contextValue(value);
        } catch (Exception e) {
            String message = String.format("contextValue(%s)", value);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean contextIs(Context c1, Context c2) {
        try {
            return contextType.contextIs(c1, c2);
        } catch (Exception e) {
            String message = String.format("contextIs(%s, %s)", c1, c2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean contextEqual(Context c1, Context c2) {
        try {
            return contextType.contextEqual(c1, c2);
        } catch (Exception e) {
            String message = String.format("contextEqual(%s, %s)", c1, c2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean contextNotEqual(Context c1, Context c2) {
        try {
            return contextType.contextNotEqual(c1, c2);
        } catch (Exception e) {
            String message = String.format("contextNotEqual(%s, %s)", c1, c2);
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
            String message = String.format("getEntries(%s)", m);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Object getValue(Context m, Object key) {
        try {
            return contextType.getValue(m, key);
        } catch (Exception e) {
            String message = String.format("getValue(%s, %s)", m, key);
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
            String message = String.format("isRange(%s)", value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public Range rangeValue(Range value) {
        try {
            return rangeType.rangeValue(value);
        } catch (Exception e) {
            String message = String.format("rangeValue(%s)", value);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean rangeIs(Range range1, Range range2) {
        try {
            return rangeType.rangeIs(range1, range2);
        } catch (Exception e) {
            String message = String.format("rangeIs(%s, %s)", range1, range2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean rangeEqual(Range range1, Range range2) {
        try {
            return rangeType.rangeEqual(range1, range2);
        } catch (Exception e) {
            String message = String.format("rangeEqual(%s, %s)", range1, range2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean rangeNotEqual(Range range1, Range range2) {
        try {
            return rangeType.rangeNotEqual(range1, range2);
        } catch (Exception e) {
            String message = String.format("rangeNotEqual(%s, %s)", range1, range2);
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
            String message = String.format("isFunction(%s)", value);
            logError(message, e);
            return false;
        }
    }

    @Override
    public Object functionValue(Object value) {
        try {
            return functionType.functionValue(value);
        } catch (Exception e) {
            String message = String.format("functionValue(%s)", value);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean functionIs(Object function1, Object function2) {
        try {
            return functionType.functionIs(function1, function2);
        } catch (Exception e) {
            String message = String.format("functionIs(%s, %s)", function1, function2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean functionEqual(Object function1, Object function2) {
        try {
            return functionType.functionEqual(function1, function2);
        } catch (Exception e) {
            String message = String.format("functionEqual(%s, %s)", function1, function2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean functionNotEqual(Object function1, Object function2) {
        try {
            return functionType.functionNotEqual(function1, function2);
        } catch (Exception e) {
            String message = String.format("functionNotEqual(%s, %s)", function1, function2);
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
            if (object instanceof List) {
                result.addAll((List) object);
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
                if (operand instanceof LazyEval) {
                    operand = ((LazyEval) operand).getOrCompute();
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
