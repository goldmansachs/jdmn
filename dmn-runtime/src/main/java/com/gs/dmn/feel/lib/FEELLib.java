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
import com.gs.dmn.feel.lib.type.bool.BooleanType;
import com.gs.dmn.feel.lib.type.range.RangeType;
import com.gs.dmn.runtime.DMNRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public interface FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends
        NumericType<NUMBER>, StringType, BooleanType, ListType, ContextType, RangeType,
        DateType<DATE, DURATION>, TimeType<TIME, DURATION>, DateTimeType<DATE_TIME, DURATION>, DurationType<DURATION, NUMBER> {
    Logger LOGGER = LoggerFactory.getLogger(FEELLib.class);

    default void logError(String message) {
        LOGGER.error(message);
    }

    default void logError(String message, Throwable e) {
        LOGGER.error(message, e);
    }

    // Check if value is a function object
    default boolean isFunction(Object value) {
        throw new DMNRuntimeException("Function objects are not supported yet");
    }

    //
    // Conversion functions
    //
    NUMBER number(String literal);

    DATE date(String literal);
    DATE date(DATE dateTime);

    TIME time(String literal);
    TIME time(TIME dateTime);
    TIME time(NUMBER hour, NUMBER minute, NUMBER second, DURATION offset);

    DATE_TIME dateAndTime(String literal);
    DATE_TIME dateAndTime(DATE date, TIME time);

    DURATION duration(String literal);

    String string(Object object);

    //
    // Implicit conversion functions
    //
    <T> List<T> asList(T ...objects);
    <T> T asElement(List<T> list);

    //
    // Extra conversion functions
    //
    List<NUMBER> rangeToList(boolean isOpenStart, NUMBER start, boolean isOpenEnd, NUMBER end);
    List<NUMBER> rangeToList(NUMBER start, NUMBER end);

    DATE toDate(Object from);
    TIME toTime(Object from);
    DATE_TIME toDateTime(Object from);

    //
    // Boolean functions
    //
    Boolean and(List list);
    Boolean or(List list);

    //
    // List functions
    //
    Object elementAt(List list, NUMBER index);
    Boolean listContains(List list, Object value);
    List flattenFirstLevel(List list);
    NUMBER count(List numbers);
    NUMBER min(List<NUMBER> numbers);
    NUMBER max(List<NUMBER> numbers);
    NUMBER sum(List<NUMBER> numbers);

    //
    // Context functions
    //
    @Override
    List getEntries(Object m);
    @Override
    Object getValue(Object m, Object key);

    //
    // Date time properties
    //
    NUMBER year(DATE date);
    NUMBER month(DATE date);
    NUMBER day(DATE date);
    NUMBER weekday(DATE date);
    NUMBER hour(TIME time);
    NUMBER minute(TIME time);
    NUMBER second(TIME time);
    DURATION timeOffset(TIME time);
    String timezone(TIME time);

    //
    // Duration properties
    //
    NUMBER years(DURATION duration);
    NUMBER months(DURATION duration);
    NUMBER days(DURATION duration);
    NUMBER hours(DURATION duration);
    NUMBER minutes(DURATION duration);
    NUMBER seconds(DURATION duration);
}
