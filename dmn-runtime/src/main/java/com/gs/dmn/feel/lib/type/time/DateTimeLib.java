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
package com.gs.dmn.feel.lib.type.time;

public interface DateTimeLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends TemporalLib<DATE, DATE_TIME> {
    //
    // Conversion functions
    //
    DATE date(String literal);

    DATE date(NUMBER year, NUMBER month, NUMBER day);

    DATE date(Object from);

    TIME time(String literal);

    TIME time(NUMBER hour, NUMBER minute, NUMBER second, DURATION offset);

    TIME time(Object from);

    DATE_TIME dateAndTime(String literal);

    DATE_TIME dateAndTime(Object date, Object time);

    //
    // Date properties
    //
    Integer year(Object date);

    Integer month(Object date);

    Integer day(Object date);

    Integer weekday(Object date);

    //
    // Time properties
    //
    Integer hour(Object date);

    Integer minute(Object date);

    Integer second(Object date);

    DURATION timeOffset(Object date);

    String timezone(Object date);

    //
    // Extra conversion functions
    //
    DATE toDate(Object from);

    TIME toTime(Object from);

    DATE_TIME toDateTime(Object from);
}
