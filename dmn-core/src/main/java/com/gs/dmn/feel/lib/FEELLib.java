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
import com.gs.dmn.runtime.LambdaExpression;

import java.util.List;

public interface FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends
        NumericType<NUMBER>, StringType, BooleanType, ListType,
        DateType<DATE, DURATION>, TimeType<TIME, DURATION>, DateTimeType<DATE_TIME, DURATION>, DurationType<DURATION, NUMBER> {
    //
    // Conversion functions
    //
    NUMBER number(String from);

    NUMBER number(String from, String groupingSeparator, String decimalSeparator);

    String string(Object from);

    DATE date(String from);

    DATE date(NUMBER year, NUMBER month, NUMBER day);

    DATE date(DATE_TIME from);

    TIME time(String from);

    TIME time(NUMBER hour, NUMBER minute, NUMBER second, DURATION offset);

    TIME time(DATE_TIME time);

    DATE_TIME dateAndTime(String from);

    DATE_TIME dateAndTime(DATE date, TIME time);

    DURATION duration(String literal);

    DURATION yearsAndMonthsDuration(DATE_TIME from, DATE_TIME to);

    DATE toDate(Object object);

    TIME toTime(Object object);

    //
    // Numeric functions
    //
    NUMBER decimal(NUMBER n, NUMBER scale);

    NUMBER floor(NUMBER number);

    NUMBER ceiling(NUMBER number);

    //
    // String functions
    //
    Boolean contains(String string, String match);

    Boolean startsWith(String string, String match);

    Boolean endsWith(String string, String match);

    String substring(String string, NUMBER startPosition);

    String substring(String string, NUMBER startPosition, NUMBER length);

    NUMBER stringLength(String string);

    String upperCase(String string);

    String lowerCase(String string);

    String substringBefore(String string, String match);

    String substringAfter(String string, String match);

    String replace(String input, String pattern, String replacement);

    String replace(String input, String pattern, String replacement, String flags);

    Boolean matches(String input, String pattern);

    Boolean matches(String input, String pattern, String flags);

    //
    // Boolean functions
    //
    Boolean and(List list);

    Boolean or(List list);

    Boolean not(Boolean operand);

    //
    // Date functions
    //
    NUMBER year(DATE date);

    NUMBER month(DATE date);

    NUMBER day(DATE date);

    //
    // Time functions
    //
    NUMBER hour(TIME time);

    NUMBER minute(TIME time);

    NUMBER second(TIME time);

    DURATION timeOffset(TIME time);

    DURATION timezone(TIME time);

    //
    // Duration functions
    //
    NUMBER years(DURATION duration);

    NUMBER months(DURATION duration);

    NUMBER days(DURATION duration);

    NUMBER hours(DURATION duration);

    NUMBER minutes(DURATION duration);

    NUMBER seconds(DURATION duration);

    //
    // List functions
    //
    Boolean listContains(List list, Object element);

    List append(List list, Object... items);

    NUMBER count(List list);

    NUMBER min(List list);

    NUMBER max(List list);

    NUMBER sum(List list);

    NUMBER min(Object... numbers);

    NUMBER max(Object... numbers);

    NUMBER sum(Object... numbers);

    NUMBER mean(List list);

    NUMBER mean(Object... numbers);

    Boolean and(Object... args);

    Boolean or(Object... args);

    List sublist(List list, NUMBER position);

    List sublist(List list, NUMBER position, NUMBER length);

    List concatenate(Object... lists);

    List insertBefore(List list, NUMBER position, Object newItem);

    List remove(List list, Object position);

    List reverse(List list);

    List indexOf(List list, Object match);

    List union(Object... lists);

    List distinctValues(List list1);

    List flatten(List list1);

    void collect(List result, List list);

    <T> List<T> sort(List<T> list, LambdaExpression<Boolean> comparator);

    //
    // Extra functions
    //
    <T> List<T> asList(T ...objects);

    <T> T asElement(List<T> list);

    List<NUMBER> rangeToList(boolean isOpenStart, NUMBER start, boolean isOpenEnd, NUMBER end);

    List<NUMBER> rangeToList(NUMBER start, NUMBER end);

    List flattenFirstLevel(List list);

    Object elementAt(List list, NUMBER index);
}
