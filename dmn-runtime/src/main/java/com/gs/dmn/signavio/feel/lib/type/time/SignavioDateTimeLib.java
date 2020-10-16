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
package com.gs.dmn.signavio.feel.lib.type.time;

public interface SignavioDateTimeLib<NUMBER, DATE, TIME, DATE_TIME> {
    DATE yearAdd(DATE date, NUMBER yearsToAdd);
    DATE_TIME yearAddDateTime(DATE_TIME dateTime, NUMBER yearsToAdd);

    Long yearDiff(DATE date1, DATE date2);
    Long yearDiffDateTime(DATE_TIME dateTime1, DATE_TIME dateTime2);

    DATE monthAdd(DATE date, NUMBER monthsToAdd);
    DATE_TIME monthAddDateTime(DATE_TIME dateTime, NUMBER monthsToAdd);

    Long monthDiff(DATE date1, DATE date2);
    Long monthDiffDateTime(DATE_TIME dateTime1, DATE_TIME dateTime2);

    DATE dayAdd(DATE date, NUMBER daysToAdd);
    DATE dayAddDateTime(DATE_TIME dateTime, NUMBER daysToAdd);

    Long dayDiff(DATE date1, DATE date2);
    Long dayDiffDateTime(DATE_TIME dateTime1, DATE_TIME dateTime2);

    Long hourDiff(TIME time1, TIME time2);
    Long hourDiffDateTime(DATE_TIME dateTime1, DATE_TIME dateTime2);

    Long minutesDiff(TIME time1, TIME time2);
    Long minutesDiffDateTime(DATE_TIME dateTime1, DATE_TIME dateTime2);

    Integer weekday(DATE date);
    Integer weekdayDateTime(DATE_TIME dateTime);

    DATE today();

    DATE_TIME now();
}
