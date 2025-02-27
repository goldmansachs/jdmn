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
package com.gs.dmn.generated.proto.date_time_proto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;

public class DateTimeProtoHandwrittenTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    private final Date dateDecision = new Date();
    private final Time timeDecision = new Time();
    private final DateTime dateTimeDecision = new DateTime();

    @Test
    public void testCase1() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        // Initialize input data
        LocalDate inputDate = date("2020-09-10");

        // Check Date
        checkValues(date("2020-09-10"), dateDecision.apply(inputDate, context_));

        // Check Date with proto request
        com.gs.dmn.generated.proto.date_time_proto.proto.DateRequest.Builder dateBuilder_ = com.gs.dmn.generated.proto.date_time_proto.proto.DateRequest.newBuilder();
        String inputDateProto0 = string(inputDate);
        dateBuilder_.setInputDate(inputDateProto0);
        com.gs.dmn.generated.proto.date_time_proto.proto.DateRequest dateRequest_ = dateBuilder_.build();
        checkValues("2020-09-10", dateDecision.applyProto(dateRequest_, context_).getDate());
    }

    @Test
    public void testCase2() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        // Initialize input data
        TemporalAccessor inputTime = time("12:10:10");

        // Check Time
        checkValues(time("12:10:10"), timeDecision.apply(inputTime, context_));

        // Check Time with proto request
        com.gs.dmn.generated.proto.date_time_proto.proto.TimeRequest.Builder timeBuilder_ = com.gs.dmn.generated.proto.date_time_proto.proto.TimeRequest.newBuilder();
        String inputTimeProto0 = string(inputTime);
        timeBuilder_.setInputTime(inputTimeProto0);
        com.gs.dmn.generated.proto.date_time_proto.proto.TimeRequest timeRequest_ = timeBuilder_.build();
        checkValues("12:10:10", timeDecision.applyProto(timeRequest_, context_).getTime());
    }

    @Test
    public void testCase3() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        // Initialize input data
        TemporalAccessor inputDateTime = dateAndTime("2020-09-19T12:10:10");

        // Check DateTime
        checkValues(dateAndTime("2020-09-19T12:10:10"), dateTimeDecision.apply(inputDateTime, context_));

        // Check DateTime with proto request
        com.gs.dmn.generated.proto.date_time_proto.proto.DateTimeRequest.Builder dateTimeBuilder_ = com.gs.dmn.generated.proto.date_time_proto.proto.DateTimeRequest.newBuilder();
        String inputDateTimeProto0 = string(inputDateTime);
        dateTimeBuilder_.setInputDateTime(inputDateTimeProto0);
        com.gs.dmn.generated.proto.date_time_proto.proto.DateTimeRequest dateTimeRequest_ = dateTimeBuilder_.build();
        checkValues("2020-09-19T12:10:10", dateTimeDecision.applyProto(dateTimeRequest_, context_).getDateTime());
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
