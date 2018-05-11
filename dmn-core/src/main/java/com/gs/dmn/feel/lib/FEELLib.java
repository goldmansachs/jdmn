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

import java.util.List;

public interface FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends
        NumericType<NUMBER>, StringType, BooleanType, ListType,
        DateType<DATE, DURATION>, TimeType<TIME, DURATION>, DateTimeType<DATE_TIME, DURATION>, DurationType<DURATION, NUMBER> {

    //
    // Constructors
    //
    NUMBER number(String literal);

    TIME time(String literal);
    TIME time(DATE_TIME dateTime);
    TIME time(NUMBER hour, NUMBER minute, NUMBER second, DURATION offset);

    DATE date(String literal);
    DATE date(DATE_TIME dateTime);

    DATE_TIME dateAndTime(String literal);
    DATE_TIME dateAndTime(DATE date, TIME time);

    DURATION duration(String literal);

    String string(Object object);

    //
    // Conversion functions
    //
    <T> List<T> asList(T ...objects);
    <T> T asElement(List<T> list);

    List<NUMBER> rangeToList(boolean isOpenStart, NUMBER start, boolean isOpenEnd, NUMBER end);
    List<NUMBER> rangeToList(NUMBER start, NUMBER end);

    DATE toDate(Object object);
    TIME toTime(Object object);

    //
    // Boolean
    //
    Boolean and(List list);
    Boolean or(List list);

    //
    // List functions
    //
    Object elementAt(List list, NUMBER index);
    Boolean listContains(List list, Object value);
    List flattenFirstLevel(List list);
    NUMBER min(List<NUMBER> numbers);
    NUMBER max(List<NUMBER> numbers);
    NUMBER sum(List<NUMBER> numbers);
    NUMBER count(List<NUMBER> numbers);

    //
    // Date time functions
    //
    NUMBER year(DATE date);
    NUMBER month(DATE date);
    NUMBER day(DATE date);
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
}
