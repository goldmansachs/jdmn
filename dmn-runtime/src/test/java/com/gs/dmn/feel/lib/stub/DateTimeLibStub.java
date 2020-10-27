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
package com.gs.dmn.feel.lib.stub;

import com.gs.dmn.feel.lib.type.time.DateTimeLib;
import com.gs.dmn.runtime.DMNRuntimeException;

public class DateTimeLibStub<NUMBER, DATE, TIME, DATE_TIME, DURATION> implements DateTimeLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    @Override
    public DATE date(String literal) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE date(NUMBER year, NUMBER month, NUMBER day) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE date(DATE from) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public TIME time(String literal) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public TIME time(NUMBER hour, NUMBER minute, NUMBER second, DURATION offset) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public TIME time(TIME from) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE_TIME dateAndTime(String literal) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE_TIME dateAndTime(DATE date, TIME time) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer year(DATE date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer yearDateTime(DATE_TIME dateTime) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer month(DATE date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer monthDateTime(DATE_TIME dateTime) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer day(DATE date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer dayDateTime(DATE_TIME dateTime) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer weekday(DATE date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer weekdayDateTime(DATE_TIME dateTime) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer hour(TIME time) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer hourDateTime(DATE_TIME dateTime) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer minute(TIME time) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer minuteDateTime(DATE_TIME dateTime) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer second(TIME time) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer secondDateTime(DATE_TIME dateTime) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DURATION timeOffset(TIME time) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DURATION timeOffsetDateTime(DATE_TIME dateTime) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String timezone(TIME time) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String timezoneDateTime(DATE_TIME dateTime) {
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
}
