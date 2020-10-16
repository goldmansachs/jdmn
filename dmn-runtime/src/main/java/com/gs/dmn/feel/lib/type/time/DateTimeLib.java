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

public interface DateTimeLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    DATE date(String literal);

    DATE date(NUMBER year, NUMBER month, NUMBER day);

    DATE date(DATE from);

    TIME time(String literal);

    TIME time(NUMBER hour, NUMBER minute, NUMBER second, DURATION offset);

    TIME time(TIME from);

    DATE_TIME dateAndTime(String literal);

    DATE_TIME dateAndTime(DATE date, TIME time);

    Integer year(DATE date);
    Integer yearDateTime(DATE_TIME dateTime);

    Integer month(DATE date);
    Integer monthDateTime(DATE_TIME dateTime);

    Integer day(DATE date);
    Integer dayDateTime(DATE_TIME dateTime);

    Integer weekday(DATE date);
    Integer weekdayDateTime(DATE_TIME dateTime);

    Integer hour(TIME date);
    Integer hourDateTime(DATE_TIME dateTime);

    Integer minute(TIME date);
    Integer minuteDateTime(DATE_TIME dateTime);

    Integer second(TIME date);
    Integer secondDateTime(DATE_TIME dateTime);

    DURATION timeOffset(TIME date);
    DURATION timeOffsetDateTime(DATE_TIME dateTime);

    String timezone(TIME date);
    String timezoneDateTime(DATE_TIME dateTime);

    DATE toDate(Object object);

    TIME toTime(Object object);
}
