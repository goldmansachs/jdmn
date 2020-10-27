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

import com.gs.dmn.feel.lib.type.*;
import com.gs.dmn.feel.lib.type.context.DefaultContextType;
import com.gs.dmn.runtime.LazyEval;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.listener.EventListener;
import com.gs.dmn.runtime.listener.Rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Deprecated
    // Backwards compatibility with 5.3.0
    protected BaseFEELLib(NumericType<NUMBER> numericType, BooleanType booleanType, StringType stringType, DateType<DATE, DURATION> dateType, TimeType<TIME, DURATION> timeType, DateTimeType<DATE_TIME, DURATION> dateTimeType, DurationType<DURATION, NUMBER> durationType, ListType listType) {
        this(numericType, booleanType, stringType, dateType, timeType, dateTimeType, durationType, listType, new DefaultContextType(LOGGER));
    }

    protected BaseFEELLib(NumericType<NUMBER> numericType, BooleanType booleanType, StringType stringType, DateType<DATE, DURATION> dateType, TimeType<TIME, DURATION> timeType, DateTimeType<DATE_TIME, DURATION> dateTimeType, DurationType<DURATION, NUMBER> durationType, ListType listType, ContextType contextType) {
        this.numericType = numericType;
        this.booleanType = booleanType;
        this.stringType = stringType;
        this.dateType = dateType;
        this.timeType = timeType;
        this.dateTimeType = dateTimeType;
        this.durationType = durationType;
        this.listType = listType;
        this.contextType = contextType;
    }

    //
    // Numeric operators
    //
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

    //
    // Boolean operators
    //
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
    public String stringAdd(String first, String second) {
        try {
            return stringType.stringAdd(first, second);
        } catch (Exception e) {
            String message = String.format("stringAdd(%s, %s)", first, second);
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

    //
    // Date operators
    //

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
    public DURATION dateSubtract(DATE first, DATE second) {
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
    public DURATION dateTimeSubtract(DATE_TIME first, DATE_TIME second) {
        try {
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
    public DURATION durationMultiply(DURATION first, NUMBER second) {
        try {
            return durationType.durationMultiply(first, second);
        } catch (Exception e) {
            String message = String.format("durationMultiply(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION durationDivide(DURATION first, NUMBER second) {
        try {
            return durationType.durationDivide(first, second);
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
    public Boolean contextEqual(Object c1, Object c2) {
        try {
            return contextType.contextEqual(c1, c2);
        } catch (Exception e) {
            String message = String.format("contextEqual(%s, %s)", c1, c2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean contextNotEqual(Object c1, Object c2) {
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
    public List getEntries(Object m) {
        try {
            return contextType.getEntries(m);
        } catch (Exception e) {
            String message = String.format("getEntries(%s)", m);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Object getValue(Object m, Object key) {
        try {
            return contextType.getValue(m, key);
        } catch (Exception e) {
            String message = String.format("getValue(%s, %s)", m, key);
            logError(message, e);
            return null;
        }
    }

    //
    // Extra functions
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

    @Override
    public List<NUMBER> rangeToList(boolean isOpenStart, NUMBER start, boolean isOpenEnd, NUMBER end) {
        Pair<Integer, Integer> intRange = intRange(isOpenStart, (Number) start, isOpenEnd, (Number) end);
        return numericRangeToList(intRange);
    }

    @Override
    public List<NUMBER> rangeToList(NUMBER start, NUMBER end) {
        Pair<Integer, Integer> intRange = intRange((Number) start, (Number) end);
        return numericRangeToList(intRange);
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

    private List<NUMBER> numericRangeToList(Pair<Integer, Integer> intRange) {
        List<NUMBER> result = new ArrayList<>();
        if (intRange == null) {
            return result;
        }

        int startValue = intRange.getLeft();
        int endValue = intRange.getRight();
        if (startValue <= endValue) {
            for (int i = startValue; i <= endValue; i++) {
                result.add(valueOf(i));
            }
        } else {
            for (int i = startValue; i >= endValue; i--) {
                result.add(valueOf(i));
            }
        }
        return result;
    }

    @Override
    public List flattenFirstLevel(List list) {
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

    @Override
    public Object elementAt(List list, NUMBER index) {
        return elementAt(list, intValue(index));
    }

    private Object elementAt(List list, int index) {
        if (list == null) {
            return null;
        }
        int listSize = list.size();
        if (1 <= index && index <= listSize) {
            return list.get(index - 1);
        } else if (-listSize <= index && index <= -1) {
            return list.get(listSize + index);
        } else {
            return null;
        }
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
