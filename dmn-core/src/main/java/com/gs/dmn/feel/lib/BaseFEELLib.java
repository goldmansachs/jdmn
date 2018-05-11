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
package com.gs.dmn.feel.lib;

import com.gs.dmn.feel.lib.type.*;

import java.math.BigDecimal;
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

    public BaseFEELLib(NumericType<NUMBER> numericType, BooleanType booleanType, StringType stringType, DateType<DATE, DURATION> dateType, TimeType<TIME, DURATION> timeType, DateTimeType<DATE_TIME, DURATION> dateTimeType, DurationType<DURATION, NUMBER> durationType, ListType listType) {
        this.numericType = numericType;
        this.booleanType = booleanType;
        this.stringType = stringType;
        this.dateType = dateType;
        this.timeType = timeType;
        this.dateTimeType = dateTimeType;
        this.durationType = durationType;
        this.listType = listType;
    }

    //
    // Numeric operators
    //
    public NUMBER numericAdd(NUMBER first, NUMBER second) {
        return numericType.numericAdd(first, second);
    }

    public NUMBER numericSubtract(NUMBER first, NUMBER second) {
        return numericType.numericSubtract(first, second);
    }

    public NUMBER numericMultiply(NUMBER first, NUMBER second) {
        return numericType.numericMultiply(first, second);
    }

    public NUMBER numericDivide(NUMBER first, NUMBER second) {
        return numericType.numericDivide(first, second);
    }

    public NUMBER numericUnaryMinus(NUMBER first) {
        return numericType.numericUnaryMinus(first);
    }

    public NUMBER numericExponentiation(NUMBER first, NUMBER second) {
        return numericType.numericExponentiation(first, second);
    }

    public Boolean numericEqual(NUMBER first, NUMBER second) {
        return numericType.numericEqual(first, second);
    }

    public Boolean numericNotEqual(NUMBER first, NUMBER second) {
        return numericType.numericNotEqual(first, second);
    }

    public Boolean numericLessThan(NUMBER first, NUMBER second) {
        return numericType.numericLessThan(first, second);
    }

    public Boolean numericGreaterThan(NUMBER first, NUMBER second) {
        return numericType.numericGreaterThan(first, second);
    }

    public Boolean numericLessEqualThan(NUMBER first, NUMBER second) {
        return numericType.numericLessEqualThan(first, second);
    }

    public Boolean numericGreaterEqualThan(NUMBER first, NUMBER second) {
        return numericType.numericGreaterEqualThan(first, second);
    }

    //
    // Boolean operators
    //
    public Boolean booleanNot(Boolean operand) {
        return booleanType.booleanNot(operand);
    }

    public Boolean booleanOr(List<Boolean> operands) {
        return booleanType.booleanOr(operands);
    }

    public Boolean booleanOr(Boolean... operands) {
        return booleanType.booleanOr(operands);
    }

    public Boolean binaryBooleanOr(Boolean first, Boolean second) {
        return booleanType.binaryBooleanOr(first, second);
    }

    public Boolean booleanAnd(List<Boolean> operands) {
        return booleanType.booleanAnd(operands);
    }

    public Boolean booleanAnd(Boolean... operands) {
        return booleanType.booleanAnd(operands);
    }

    public Boolean binaryBooleanAnd(Boolean first, Boolean second) {
        return booleanType.binaryBooleanAnd(first, second);
    }

    public Boolean booleanEqual(Boolean first, Boolean second) {
        return booleanType.booleanEqual(first, second);
    }

    public Boolean booleanNotEqual(Boolean first, Boolean second) {
        return booleanType.booleanNotEqual(first, second);
    }

    //
    // String operators
    //
    public Boolean stringEqual(String first, String second) {
        return stringType.stringEqual(first, second);
    }

    public Boolean stringNotEqual(String first, String second) {
        return stringType.stringNotEqual(first, second);
    }

    public String stringAdd(String first, String second) {
        return stringType.stringAdd(first, second);
    }

    public Boolean stringLessThan(String first, String second) {
        return stringType.stringLessThan(first, second);
    }

    public Boolean stringGreaterThan(String first, String second) {
        return stringType.stringGreaterThan(first, second);
    }

    public Boolean stringLessEqualThan(String first, String second) {
        return stringType.stringLessEqualThan(first, second);
    }

    public Boolean stringGreaterEqualThan(String first, String second) {
        return stringType.stringGreaterEqualThan(first, second);
    }

    //
    // Date operators
    //

    public Boolean dateEqual(DATE first, DATE second) {
        return dateType.dateEqual(first, second);
    }

    public Boolean dateNotEqual(DATE first, DATE second) {
        return dateType.dateNotEqual(first, second);
    }

    public Boolean dateLessThan(DATE first, DATE second) {
        return dateType.dateLessThan(first, second);
    }

    public Boolean dateGreaterThan(DATE first, DATE second) {
        return dateType.dateGreaterThan(first, second);
    }

    public Boolean dateLessEqualThan(DATE first, DATE second) {
        return dateType.dateLessEqualThan(first, second);
    }

    public Boolean dateGreaterEqualThan(DATE first, DATE second) {
        return dateType.dateGreaterEqualThan(first, second);
    }

    public DURATION dateSubtract(DATE first, DATE second) {
        return dateType.dateSubtract(first, second);
    }

    public DATE dateAddDuration(DATE date, DURATION duration) {
        return dateType.dateAddDuration(date, duration);
    }

    public DATE dateSubtractDuration(DATE date, DURATION duration) {
        return dateType.dateSubtractDuration(date, duration);
    }

    //
    // Time operators
    //
    public Boolean timeEqual(TIME first, TIME second) {
        return timeType.timeEqual(first, second);
    }

    public Boolean timeNotEqual(TIME first, TIME second) {
        return timeType.timeNotEqual(first, second);
    }

    public Boolean timeLessThan(TIME first, TIME second) {
        return timeType.timeLessThan(first, second);
    }

    public Boolean timeGreaterThan(TIME first, TIME second) {
        return timeType.timeGreaterThan(first, second);
    }

    public Boolean timeLessEqualThan(TIME first, TIME second) {
        return timeType.timeLessEqualThan(first, second);
    }

    public Boolean timeGreaterEqualThan(TIME first, TIME second) {
        return timeType.timeGreaterEqualThan(first, second);
    }

    public DURATION timeSubtract(TIME first, TIME second) {
        return timeType.timeSubtract(first, second);
    }

    public TIME timeAddDuration(TIME time, DURATION duration) {
        return timeType.timeAddDuration(time, duration);
    }

    public TIME timeSubtractDuration(TIME time, DURATION duration) {
        return timeType.timeSubtractDuration(time, duration);
    }

    //
    // Date and Time operators
    //
    public Boolean dateTimeEqual(DATE_TIME first, DATE_TIME second) {
        return dateTimeType.dateTimeEqual(first, second);
    }

    public Boolean dateTimeNotEqual(DATE_TIME first, DATE_TIME second) {
        return dateTimeType.dateTimeNotEqual(first, second);
    }

    public Boolean dateTimeLessThan(DATE_TIME first, DATE_TIME second) {
        return dateTimeType.dateTimeLessThan(first, second);
    }

    public Boolean dateTimeGreaterThan(DATE_TIME first, DATE_TIME second) {
        return dateTimeType.dateTimeGreaterThan(first, second);
    }

    public Boolean dateTimeLessEqualThan(DATE_TIME first, DATE_TIME second) {
        return dateTimeType.dateTimeLessEqualThan(first, second);
    }

    public Boolean dateTimeGreaterEqualThan(DATE_TIME first, DATE_TIME second) {
        return dateTimeType.dateTimeGreaterEqualThan(first, second);
    }

    public DURATION dateTimeSubtract(DATE_TIME first, DATE_TIME second) {
        return dateTimeType.dateTimeSubtract(first, second);
    }

    public DATE_TIME dateTimeAddDuration(DATE_TIME date_time, DURATION duration) {
        return dateTimeType.dateTimeAddDuration(date_time, duration);
    }

    public DATE_TIME dateTimeSubtractDuration(DATE_TIME date_time, DURATION duration) {
        return dateTimeType.dateTimeSubtractDuration(date_time, duration);
    }

    //
    // Duration operators
    //
    public Boolean durationEqual(DURATION first, DURATION second) {
        return durationType.durationEqual(first, second);
    }

    public Boolean durationNotEqual(DURATION first, DURATION second) {
        return durationType.durationNotEqual(first, second);
    }

    public Boolean durationLessThan(DURATION first, DURATION second) {
        return durationType.durationLessThan(first, second);
    }

    public Boolean durationGreaterThan(DURATION first, DURATION second) {
        return durationType.durationGreaterThan(first, second);
    }

    public Boolean durationLessEqualThan(DURATION first, DURATION second) {
        return durationType.durationLessEqualThan(first, second);
    }

    public Boolean durationGreaterEqualThan(DURATION first, DURATION second) {
        return durationType.durationGreaterEqualThan(first, second);
    }

    public DURATION durationAdd(DURATION first, DURATION second) {
        return durationType.durationAdd(first, second);
    }

    public DURATION durationSubtract(DURATION first, DURATION second) {
        return durationType.durationSubtract(first, second);
    }

    public DURATION durationMultiply(DURATION first, NUMBER second) {
        return durationType.durationMultiply(first, second);
    }

    public DURATION durationDivide(DURATION first, NUMBER second) {
        return durationType.durationDivide(first, second);
    }

    //
    // List operators
    //
    public Boolean listEqual(List list1, List list2) {
        return listType.listEqual(list1, list2);
    }

    public Boolean listNotEqual(List list1, List list2) {
        return listType.listNotEqual(list1, list2);
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

    public List<BigDecimal> rangeToList(boolean isOpenStart, BigDecimal start, boolean isOpenEnd, BigDecimal end) {
        List<BigDecimal> result = new ArrayList<>();
        if (start == null || end == null) {
            return result;
        }
        int startValue = isOpenStart ? start.intValue() + 1 : start.intValue();
        int endValue = isOpenEnd ? end.intValue() - 1 : end.intValue();
        for (int i = startValue; i <= endValue; i++) {
            result.add(BigDecimal.valueOf(i));
        }
        return result;
    }
    public List<Double> rangeToList(boolean isOpenStart, Double start, boolean isOpenEnd, Double end) {
        List<Double> result = new ArrayList<>();
        if (start == null || end == null) {
            return result;
        }
        int startValue = isOpenStart ? start.intValue() + 1 : start.intValue();
        int endValue = isOpenEnd ? end.intValue() - 1 : end.intValue();
        for (int i = startValue; i <= endValue; i++) {
            result.add(Double.valueOf(i));
        }
        return result;
    }

    public List<BigDecimal> rangeToList(BigDecimal start, BigDecimal end) {
        List<BigDecimal> result = new ArrayList<>();
        if (start == null || end == null) {
            return result;
        }
        int startValue = start.intValue();
        int endValue = end.intValue();
        if (startValue <= endValue) {
            for (int i = startValue; i <= endValue; i++) {
                result.add(BigDecimal.valueOf(i));
            }
        } else {
            for (int i = startValue; i <= endValue; i--) {
                result.add(BigDecimal.valueOf(i));
            }
        }
        return result;
    }
    public List<Double> rangeToList(Double start, Double end) {
        List<Double> result = new ArrayList<>();
        if (start == null || end == null) {
            return result;
        }
        int startValue = start.intValue();
        int endValue = end.intValue();
        if (startValue <= endValue) {
            for (int i = startValue; i <= endValue; i++) {
                result.add(Double.valueOf(i));
            }
        } else {
            for (int i = startValue; i <= endValue; i--) {
                result.add(Double.valueOf(i));
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

    public Object elementAt(List list, BigDecimal index) {
        return elementAt(list, index.intValue());
    }

    public Object elementAt(List list, Double index) {
        return elementAt(list, index.intValue());
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
}
