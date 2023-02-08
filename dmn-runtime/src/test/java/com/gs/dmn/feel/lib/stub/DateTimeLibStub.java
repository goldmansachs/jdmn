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

import java.util.List;

public class DateTimeLibStub<NUMBER, DATE, TIME, DATE_TIME, DURATION> implements DateTimeLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    //
    // Conversion functions
    //
    @Override
    public DATE date(String literal) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE date(NUMBER year, NUMBER month, NUMBER day) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE date(Object from) {
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
    public TIME time(Object from) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE_TIME dateAndTime(String literal) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE_TIME dateAndTime(Object date, Object time) {
        throw new DMNRuntimeException("Not supported yet");
    }

    //
    // Date properties
    //
    @Override
    public Integer year(Object date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer month(Object date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer day(Object date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer weekday(Object date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    //
    // Time properties
    //
    @Override
    public Integer hour(Object time) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer minute(Object time) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer second(Object time) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DURATION timeOffset(Object time) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String timezone(Object time) {
        throw new DMNRuntimeException("Not supported yet");
    }

    //
    // Temporal functions
    //
    @Override
    public Integer dayOfYear(Object date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String dayOfWeek(Object date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer weekOfYear(Object date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String monthOfYear(Object date) {
        throw new DMNRuntimeException("Not supported yet");
    }

    //
    // Extra conversion functions
    //
    @Override
    public DATE toDate(Object from) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public TIME toTime(Object from) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE_TIME toDateTime(Object from) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public <T> T min(List<T> list) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public <T> T max(List<T> list) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
