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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Stream;

public interface FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends
        NumericType<NUMBER>, StringType, BooleanType, ListType, ContextType, RangeType, FunctionType,
        DateType<DATE, DURATION>, TimeType<TIME, DURATION>, DateTimeType<DATE_TIME, DURATION>, DurationType<DURATION, NUMBER> {
    Logger LOGGER = LoggerFactory.getLogger(FEELLib.class);

    default void logError(String message) {
        LOGGER.error(message);
    }

    default void logError(String message, Throwable e) {
        LOGGER.error(message, e);
    }

    //
    // Conversions from string
    //
    NUMBER number(String literal);

    DATE date(String literal);

    TIME time(String literal);

    DATE_TIME dateAndTime(String literal);

    DURATION duration(String literal);

    //
    // Conversion to string
    //
    String string(Object from);

    //
    // Implicit conversion functions
    //
    <T> List<T> asList(T ...objects);
    <T> T asElement(List<T> list);

    //
    // Error conversions
    //
    default <T> T toNull(Object obj) {
        return null;
    }

    //
    // Extra conversion functions
    //
    List<NUMBER> rangeToList(boolean isOpenStart, NUMBER start, boolean isOpenEnd, NUMBER end);
    List<NUMBER> rangeToList(NUMBER start, NUMBER end);
    Stream<NUMBER> rangeToStream(boolean isOpenStart, NUMBER start, boolean isOpenEnd, NUMBER end);
    Stream<NUMBER> rangeToStream(NUMBER start, NUMBER endValue);

    DATE toDate(Object from);
    TIME toTime(Object from);
    DATE_TIME toDateTime(Object from);

    //
    // Boolean functions
    //
    Boolean and(List<?> list);
    Boolean or(List<?> list);

    //
    // List functions
    //
    <T> T elementAt(List<?> list, NUMBER index);
    Boolean listContains(List<?> list, Object element);
    List flattenFirstLevel(List<?> list);
    // Used in HIT policies
    NUMBER count(List<?> numbers);
    NUMBER min(List<?> numbers);
    NUMBER max(List<?> numbers);
    NUMBER sum(List<?> numbers);
}
