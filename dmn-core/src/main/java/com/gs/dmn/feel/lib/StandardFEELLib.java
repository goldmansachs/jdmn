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

import com.gs.dmn.runtime.LambdaExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public interface StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    static final Logger LOGGER = LoggerFactory.getLogger(StandardFEELLib.class);

    default void logError(String message) {
        LOGGER.error(message);
    }

    default void logError(String message, Throwable e) {
        LOGGER.error(message, e);
    }

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

    //
    // Numeric functions
    //
    NUMBER decimal(NUMBER n, NUMBER scale);

    NUMBER floor(NUMBER number);

    NUMBER ceiling(NUMBER number);

    NUMBER abs(NUMBER number);

    NUMBER modulo(NUMBER divident, NUMBER divisor);

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
    NUMBER min(Object... numbers);

    NUMBER max(List list);
    NUMBER max(Object... numbers);

    NUMBER sum(List list);
    NUMBER sum(Object... numbers);

    NUMBER mean(List list);
    NUMBER mean(Object... numbers);

    // Use all instead
    @Deprecated
    Boolean and(List list);
    // Use all instead
    @Deprecated
    Boolean and(Object... args);

    // Use any instead
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
