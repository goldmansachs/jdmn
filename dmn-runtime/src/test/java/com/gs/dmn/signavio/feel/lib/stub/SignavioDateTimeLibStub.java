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
package com.gs.dmn.signavio.feel.lib.stub;

import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.signavio.feel.lib.type.time.SignavioDateTimeLib;

public class SignavioDateTimeLibStub<NUMBER, DATE, TIME, DATE_TIME> implements SignavioDateTimeLib<NUMBER, DATE, TIME, DATE_TIME> {
    @Override
    public DATE date(String literal) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public TIME time(String literal) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE_TIME dateAndTime(String literal) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE yearAdd(DATE date, NUMBER yearsToAdd) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE_TIME yearAddDateTime(DATE_TIME date_time, NUMBER yearsToAdd) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Long yearDiff(DATE date1, DATE date2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Long yearDiffDateTime(DATE_TIME dateTime1, DATE_TIME dateTime2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE monthAdd(DATE date, NUMBER monthsToAdd) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE_TIME monthAddDateTime(DATE_TIME date_time, NUMBER monthsToAdd) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Long monthDiff(DATE date1, DATE date2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Long monthDiffDateTime(DATE_TIME dateTime1, DATE_TIME dateTime2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE dayAdd(DATE date, NUMBER daysToAdd) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE_TIME dayAddDateTime(DATE_TIME dateTime, NUMBER daysToAdd) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Long dayDiff(DATE date1, DATE date2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Long dayDiffDateTime(DATE_TIME dateTime1, DATE_TIME dateTime2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Long hourDiff(TIME time1, TIME time2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Long hourDiffDateTime(DATE_TIME dateTime1, DATE_TIME dateTime2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Long minutesDiff(TIME time1, TIME time2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Long minutesDiffDateTime(DATE_TIME dateTime1, DATE_TIME dateTime2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer year(DATE date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer yearDateTime(DATE_TIME date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer month(DATE date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer monthDateTime(DATE_TIME date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer day(DATE date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer dayDateTime(DATE_TIME date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer weekday(DATE date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer weekdayDateTime(DATE_TIME date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer hour(TIME time) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer hourDateTime(DATE_TIME date_time) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer minute(TIME time) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer minuteDateTime(DATE_TIME date_time) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE today() {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE_TIME now() {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE toDate(Object object) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public TIME toTime(Object object) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE_TIME toDateTime(Object object) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
