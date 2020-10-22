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
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.LazyEval;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.listener.EventListener;
import com.gs.dmn.runtime.listener.Rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
        return numericType.numericAdd(first, second);
    }

    @Override
    public NUMBER numericSubtract(NUMBER first, NUMBER second) {
        return numericType.numericSubtract(first, second);
    }

    @Override
    public NUMBER numericMultiply(NUMBER first, NUMBER second) {
        return numericType.numericMultiply(first, second);
    }

    @Override
    public NUMBER numericDivide(NUMBER first, NUMBER second) {
        return numericType.numericDivide(first, second);
    }

    @Override
    public NUMBER numericUnaryMinus(NUMBER first) {
        return numericType.numericUnaryMinus(first);
    }

    @Override
    public NUMBER numericExponentiation(NUMBER first, NUMBER second) {
        return numericType.numericExponentiation(first, second);
    }

    @Override
    public Boolean numericEqual(NUMBER first, NUMBER second) {
        return numericType.numericEqual(first, second);
    }

    @Override
    public Boolean numericNotEqual(NUMBER first, NUMBER second) {
        return numericType.numericNotEqual(first, second);
    }

    @Override
    public Boolean numericLessThan(NUMBER first, NUMBER second) {
        return numericType.numericLessThan(first, second);
    }

    @Override
    public Boolean numericGreaterThan(NUMBER first, NUMBER second) {
        return numericType.numericGreaterThan(first, second);
    }

    @Override
    public Boolean numericLessEqualThan(NUMBER first, NUMBER second) {
        return numericType.numericLessEqualThan(first, second);
    }

    @Override
    public Boolean numericGreaterEqualThan(NUMBER first, NUMBER second) {
        return numericType.numericGreaterEqualThan(first, second);
    }

    //
    // Boolean operators
    //
    @Override
    public Boolean booleanNot(Object operand) {
        return booleanType.booleanNot(operand);
    }

    @Override
    public Boolean booleanOr(List<Object> operands) {
        return booleanType.booleanOr(operands);
    }

    @Override
    public Boolean booleanOr(Object... operands) {
        return booleanType.booleanOr(operands);
    }

    @Override
    public Boolean binaryBooleanOr(Object first, Object second) {
        return booleanType.binaryBooleanOr(first, second);
    }

    @Override
    public Boolean booleanAnd(List<Object> operands) {
        return booleanType.booleanAnd(operands);
    }

    @Override
    public Boolean booleanAnd(Object... operands) {
        return booleanType.booleanAnd(operands);
    }

    @Override
    public Boolean binaryBooleanAnd(Object first, Object second) {
        return booleanType.binaryBooleanAnd(first, second);
    }

    @Override
    public Boolean booleanEqual(Boolean first, Boolean second) {
        return booleanType.booleanEqual(first, second);
    }

    @Override
    public Boolean booleanNotEqual(Boolean first, Boolean second) {
        return booleanType.booleanNotEqual(first, second);
    }

    @Deprecated
    public Boolean booleanNot(Boolean operand) {
        return booleanType.booleanNot(operand);
    }

    @Deprecated
    public Boolean booleanOr(Boolean... operands) {
        return booleanType.booleanOr(operands);
    }

    @Deprecated
    public Boolean binaryBooleanOr(Boolean first, Boolean second) {
        return booleanType.binaryBooleanOr(first, second);
    }

    @Deprecated
    public Boolean booleanAnd(Boolean... operands) {
        return booleanType.booleanAnd(operands);
    }

    @Deprecated
    public Boolean binaryBooleanAnd(Boolean first, Boolean second) {
        return booleanType.binaryBooleanAnd(first, second);
    }

    //
    // String operators
    //
    @Override
    public Boolean stringEqual(String first, String second) {
        return stringType.stringEqual(first, second);
    }

    @Override
    public Boolean stringNotEqual(String first, String second) {
        return stringType.stringNotEqual(first, second);
    }

    @Override
    public String stringAdd(String first, String second) {
        return stringType.stringAdd(first, second);
    }

    @Override
    public Boolean stringLessThan(String first, String second) {
        return stringType.stringLessThan(first, second);
    }

    @Override
    public Boolean stringGreaterThan(String first, String second) {
        return stringType.stringGreaterThan(first, second);
    }

    @Override
    public Boolean stringLessEqualThan(String first, String second) {
        return stringType.stringLessEqualThan(first, second);
    }

    @Override
    public Boolean stringGreaterEqualThan(String first, String second) {
        return stringType.stringGreaterEqualThan(first, second);
    }

    //
    // Date operators
    //

    @Override
    public Boolean dateEqual(DATE first, DATE second) {
        return dateType.dateEqual(first, second);
    }

    @Override
    public Boolean dateNotEqual(DATE first, DATE second) {
        return dateType.dateNotEqual(first, second);
    }

    @Override
    public Boolean dateLessThan(DATE first, DATE second) {
        return dateType.dateLessThan(first, second);
    }

    @Override
    public Boolean dateGreaterThan(DATE first, DATE second) {
        return dateType.dateGreaterThan(first, second);
    }

    @Override
    public Boolean dateLessEqualThan(DATE first, DATE second) {
        return dateType.dateLessEqualThan(first, second);
    }

    @Override
    public Boolean dateGreaterEqualThan(DATE first, DATE second) {
        return dateType.dateGreaterEqualThan(first, second);
    }

    @Override
    public DURATION dateSubtract(DATE first, DATE second) {
        return dateType.dateSubtract(first, second);
    }

    @Override
    public DATE dateAddDuration(DATE date, DURATION duration) {
        return dateType.dateAddDuration(date, duration);
    }

    @Override
    public DATE dateSubtractDuration(DATE date, DURATION duration) {
        return dateType.dateSubtractDuration(date, duration);
    }

    //
    // Time operators
    //
    @Override
    public Boolean timeEqual(TIME first, TIME second) {
        return timeType.timeEqual(first, second);
    }

    @Override
    public Boolean timeNotEqual(TIME first, TIME second) {
        return timeType.timeNotEqual(first, second);
    }

    @Override
    public Boolean timeLessThan(TIME first, TIME second) {
        return timeType.timeLessThan(first, second);
    }

    @Override
    public Boolean timeGreaterThan(TIME first, TIME second) {
        return timeType.timeGreaterThan(first, second);
    }

    @Override
    public Boolean timeLessEqualThan(TIME first, TIME second) {
        return timeType.timeLessEqualThan(first, second);
    }

    @Override
    public Boolean timeGreaterEqualThan(TIME first, TIME second) {
        return timeType.timeGreaterEqualThan(first, second);
    }

    @Override
    public DURATION timeSubtract(TIME first, TIME second) {
        return timeType.timeSubtract(first, second);
    }

    @Override
    public TIME timeAddDuration(TIME time, DURATION duration) {
        return timeType.timeAddDuration(time, duration);
    }

    @Override
    public TIME timeSubtractDuration(TIME time, DURATION duration) {
        return timeType.timeSubtractDuration(time, duration);
    }

    //
    // Date and Time operators
    //
    @Override
    public Boolean dateTimeEqual(DATE_TIME first, DATE_TIME second) {
        return dateTimeType.dateTimeEqual(first, second);
    }

    @Override
    public Boolean dateTimeNotEqual(DATE_TIME first, DATE_TIME second) {
        return dateTimeType.dateTimeNotEqual(first, second);
    }

    @Override
    public Boolean dateTimeLessThan(DATE_TIME first, DATE_TIME second) {
        return dateTimeType.dateTimeLessThan(first, second);
    }

    @Override
    public Boolean dateTimeGreaterThan(DATE_TIME first, DATE_TIME second) {
        return dateTimeType.dateTimeGreaterThan(first, second);
    }

    @Override
    public Boolean dateTimeLessEqualThan(DATE_TIME first, DATE_TIME second) {
        return dateTimeType.dateTimeLessEqualThan(first, second);
    }

    @Override
    public Boolean dateTimeGreaterEqualThan(DATE_TIME first, DATE_TIME second) {
        return dateTimeType.dateTimeGreaterEqualThan(first, second);
    }

    @Override
    public DURATION dateTimeSubtract(DATE_TIME first, DATE_TIME second) {
        return dateTimeType.dateTimeSubtract(first, second);
    }

    @Override
    public DATE_TIME dateTimeAddDuration(DATE_TIME date_time, DURATION duration) {
        return dateTimeType.dateTimeAddDuration(date_time, duration);
    }

    @Override
    public DATE_TIME dateTimeSubtractDuration(DATE_TIME date_time, DURATION duration) {
        return dateTimeType.dateTimeSubtractDuration(date_time, duration);
    }

    //
    // Duration operators
    //
    @Override
    public Boolean durationEqual(DURATION first, DURATION second) {
        return durationType.durationEqual(first, second);
    }

    @Override
    public Boolean durationNotEqual(DURATION first, DURATION second) {
        return durationType.durationNotEqual(first, second);
    }

    @Override
    public Boolean durationLessThan(DURATION first, DURATION second) {
        return durationType.durationLessThan(first, second);
    }

    @Override
    public Boolean durationGreaterThan(DURATION first, DURATION second) {
        return durationType.durationGreaterThan(first, second);
    }

    @Override
    public Boolean durationLessEqualThan(DURATION first, DURATION second) {
        return durationType.durationLessEqualThan(first, second);
    }

    @Override
    public Boolean durationGreaterEqualThan(DURATION first, DURATION second) {
        return durationType.durationGreaterEqualThan(first, second);
    }

    @Override
    public DURATION durationAdd(DURATION first, DURATION second) {
        return durationType.durationAdd(first, second);
    }

    @Override
    public DURATION durationSubtract(DURATION first, DURATION second) {
        return durationType.durationSubtract(first, second);
    }

    @Override
    public DURATION durationMultiply(DURATION first, NUMBER second) {
        return durationType.durationMultiply(first, second);
    }

    @Override
    public DURATION durationDivide(DURATION first, NUMBER second) {
        return durationType.durationDivide(first, second);
    }

    //
    // List operators
    //
    @Override
    public Boolean listEqual(List list1, List list2) {
        return listType.listEqual(list1, list2);
    }

    @Override
    public Boolean listNotEqual(List list1, List list2) {
        return listType.listNotEqual(list1, list2);
    }

    //
    // Context operators
    //
    @Override
    public Boolean contextEqual(Object c1, Object c2) {
        return contextType.contextEqual(c1, c2);
    }

    @Override
    public Boolean contextNotEqual(Object c1, Object c2) {
        return contextType.contextNotEqual(c1, c2);
    }

    //
    // Context functions
    //
    @Override
    public List getEntries(Object m) {
        if (m instanceof Context) {
            List result = new ArrayList<>();
            Context context = (Context) m;
            Set keys = context.getBindings().keySet();
            for (Object key: keys) {
                Context c = new Context().add("key", key).add("value", context.get(key));
                result.add(c);
            }
            return result;
        } else {
            return null;
        }
    }

    @Override
    public Object getValue(Object m, Object key) {
        if (m instanceof Context) {
            return ((Context) m).get(key);
        } else {
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

                // Column index starts from 1
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
