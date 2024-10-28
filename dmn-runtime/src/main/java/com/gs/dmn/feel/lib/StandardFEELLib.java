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

import com.gs.dmn.feel.lib.type.range.RangeLib;
import com.gs.dmn.runtime.LambdaExpression;

import java.util.List;

public interface StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION>, RangeLib {
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

    DATE date(Object from);

    @Override
    TIME time(String from);

    TIME time(NUMBER hour, NUMBER minute, NUMBER second, DURATION offset);

    TIME time(Object time);

    @Override
    DATE_TIME dateAndTime(String from);

    DATE_TIME dateAndTime(Object date, Object time);

    @Override
    DURATION duration(String literal);

    DURATION yearsAndMonthsDuration(Object from, Object to);

    //
    // Numeric functions
    //
    NUMBER decimal(NUMBER n, NUMBER scale);

    // Extension to DMN 1.3
    NUMBER round(NUMBER n, NUMBER scale, String mode);
    NUMBER roundUp(NUMBER n, NUMBER scale);
    NUMBER roundDown(NUMBER n, NUMBER scale);
    NUMBER roundHalfUp(NUMBER n, NUMBER scale);
    NUMBER roundHalfDown(NUMBER n, NUMBER scale);

    NUMBER floor(NUMBER n);
    NUMBER floor(NUMBER n, NUMBER scale);

    NUMBER ceiling(NUMBER n);
    NUMBER ceiling(NUMBER n, NUMBER scale);

    <T> T abs(T n);

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

    List<String> split(String string, String delimiter);

    String stringJoin(List<String> list);

    String stringJoin(List<String> list, String delimiter);

    //
    // Boolean functions
    //
    Boolean not(Boolean operand);

    //
    // Date properties
    //
    NUMBER year(Object date);

    NUMBER month(Object date);

    NUMBER day(Object date);

    NUMBER weekday(Object date);

    //
    // Time properties
    //
    NUMBER hour(Object time);

    NUMBER minute(Object time);

    NUMBER second(Object time);

    DURATION timeOffset(Object time);

    String timezone(Object time);

    //
    // Duration properties
    //
    NUMBER years(DURATION duration);

    NUMBER months(DURATION duration);

    NUMBER days(DURATION duration);

    NUMBER hours(DURATION duration);

    NUMBER minutes(DURATION duration);

    NUMBER seconds(DURATION duration);

    //
    // Temporal functions
    //
    NUMBER dayOfYear(Object date);

    String dayOfWeek(Object date);

    NUMBER weekOfYear(Object date);

    String monthOfYear(Object date);

    //
    // Date and time functions
    //
    Boolean is(Object value1, Object value2);

    DATE_TIME now();

    DATE today();

    //
    // List functions
    //
    List append(List<?> list, Object... items);

    @Override
    NUMBER count(List<?> list);

    @Override
    <T> T min(List<T> list);
    <T> T min(Object... args);

    @Override
    <T> T max(List<T> list);
    <T> T max(Object... args);

    @Override
    NUMBER sum(List<?> list);
    NUMBER sum(Object... numbers);

    NUMBER mean(List<?> list);
    NUMBER mean(Object... numbers);

    // Use all instead
    @Override
    @Deprecated
    Boolean and(List<?> list);
    // Use all instead
    @Deprecated
    Boolean and(Object... args);

    // Use any instead
    @Override
    @Deprecated
    Boolean or(List<?> list);
    // Use any instead
    @Deprecated
    Boolean or(Object... args);

    Boolean all(List<?> list);
    Boolean all(Object... args);

    Boolean any(List<?> list);
    Boolean any(Object... args);

    <T> List<T> sublist(List<T> list, NUMBER position);

    <T> List<T> sublist(List<T> list, NUMBER position, NUMBER length);

    <T> List<T> concatenate(List<T>... lists);

    <T> List<T> insertBefore(List<T> list, NUMBER position, T newItem);

    <T> List<T> remove(List<T> list, Object position);

    <T> List<T> listReplace(List<T> list, Object position, T newItem);

    <T> List<T> reverse(List<T> list);

    <T> List<NUMBER> indexOf(List<T> list, Object match);

    <T> List<T> union(List<T>... lists);

    <T> List<T> distinctValues(List<T> list1);

    List flatten(List<?> list1);

    NUMBER product(List<?> list);
    NUMBER product(Object... numbers);

    NUMBER median(List<?> list);
    NUMBER median(Object... numbers);

    NUMBER stddev(List<?> list);
    NUMBER stddev(Object... numbers);

    List mode(List<?> list);
    List mode(Object... numbers);

    void collect(List<?> result, List<?> list);

    //
    // Temporal built-in functions
    //
    <T> List<T> sort(List<T> list, LambdaExpression<Boolean> comparator);
    Boolean isInstanceOf(Object value, String type);
}
