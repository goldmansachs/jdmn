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

import com.gs.dmn.runtime.LambdaExpression;

import java.util.List;

public interface StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    //
    // Conversion functions
    //
    @Override
    NUMBER number(String from);

    NUMBER number(String from, String groupingSeparator, String decimalSeparator);

    @Override
    String string(Object from);

    @Override
    DATE date(String from);

    DATE date(NUMBER year, NUMBER month, NUMBER day);

    @Override
    DATE date(DATE from);

    @Override
    TIME time(String from);

    @Override
    TIME time(NUMBER hour, NUMBER minute, NUMBER second, DURATION offset);

    @Override
    TIME time(TIME time);

    @Override
    DATE_TIME dateAndTime(String from);

    @Override
    DATE_TIME dateAndTime(DATE date, TIME time);

    @Override
    DURATION duration(String literal);

    DURATION yearsAndMonthsDuration(DATE from, DATE to);

    //
    // Numeric functions
    //
    NUMBER decimal(NUMBER n, NUMBER scale);

    // Extension to DMN 1.3
    NUMBER round(NUMBER n, NUMBER scale, String mode);

    NUMBER floor(NUMBER number);

    NUMBER ceiling(NUMBER number);

    NUMBER abs(NUMBER number);

    NUMBER modulo(NUMBER dividend, NUMBER divisor);

    // Backwards compatibility due to changes in DMN 1.3-125
    NUMBER intModulo(NUMBER dividend, NUMBER divisor);

    NUMBER sqrt(NUMBER number);

    NUMBER log(NUMBER number);

    NUMBER exp(NUMBER number);

    Boolean odd(NUMBER number);

    Boolean even(NUMBER number);

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

    List split(String string, String delimiter);

    //
    // Boolean functions
    //
    Boolean not(Boolean operand);

    //
    // Date functions
    //
    @Override
    NUMBER year(DATE date);

    @Override
    NUMBER month(DATE date);

    @Override
    NUMBER day(DATE date);

    @Override
    NUMBER weekday(DATE date);

    //
    // Time functions
    //
    @Override
    NUMBER hour(TIME time);

    @Override
    NUMBER minute(TIME time);

    @Override
    NUMBER second(TIME time);

    @Override
    DURATION timeOffset(TIME time);

    @Override
    String timezone(TIME time);

    //
    // Duration functions
    //
    @Override
    NUMBER years(DURATION duration);

    @Override
    NUMBER months(DURATION duration);

    @Override
    NUMBER days(DURATION duration);

    @Override
    NUMBER hours(DURATION duration);

    @Override
    NUMBER minutes(DURATION duration);

    @Override
    NUMBER seconds(DURATION duration);

    //
    // List functions
    //
    @Override
    Boolean listContains(List list, Object element);

    List append(List list, Object... items);

    @Override
    NUMBER count(List list);

    @Override
    NUMBER min(List list);
    NUMBER min(Object... numbers);

    @Override
    NUMBER max(List list);
    NUMBER max(Object... numbers);

    @Override
    NUMBER sum(List list);
    NUMBER sum(Object... numbers);

    NUMBER mean(List list);
    NUMBER mean(Object... numbers);

    // Use all instead
    @Override
    @Deprecated
    Boolean and(List list);
    // Use all instead
    @Deprecated
    Boolean and(Object... args);

    // Use any instead
    @Override
    @Deprecated
    Boolean or(List list);
    // Use any instead
    @Deprecated
    Boolean or(Object... args);

    Boolean all(List list);
    Boolean all(Object... args);

    Boolean any(List list);
    Boolean any(Object... args);

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

    NUMBER product(List list);
    NUMBER product(Object... numbers);

    NUMBER median(List list);
    NUMBER median(Object... numbers);

    NUMBER stddev(List list);
    NUMBER stddev(Object... numbers);

    List mode(List list);
    List mode(Object... numbers);

    void collect(List result, List list);

    <T> List<T> sort(List<T> list, LambdaExpression<Boolean> comparator);
}
