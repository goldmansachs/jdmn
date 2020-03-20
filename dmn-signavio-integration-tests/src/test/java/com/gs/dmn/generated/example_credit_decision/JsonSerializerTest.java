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
package com.gs.dmn.generated.example_credit_decision;

import com.gs.dmn.serialization.JsonSerializer;
import com.gs.dmn.signavio.feel.lib.DefaultSignavioLib;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonSerializerTest {
    private final DefaultSignavioLib lib = new DefaultSignavioLib();
    private final String personText = "{\"514 AT\":\"at\",\"AT\":\"AT\",\"Date and Time List\":[\"2016-08-01T12:00:00Z\"],\"Date and Time of Birth\":\"2016-08-01T12:00:00Z\",\"Date of Birth\":\"2017-01-03\",\"Days and Time Duration\":\"P2DT20H\",\"First Name\":\"Amy\",\"Gender\":\"female\",\"ID\":38,\"Last Name\":\"Smith\",\"List\":[\"Late payment\"],\"Married\":true,\"Time of Birth\":\"11:20:30Z\",\"Years and Months Duration\":\"P1Y1M\"}";

    @Test
    public void testPersonSerialization() throws IOException {
        PersonImpl person = new PersonImpl();
        person.setId(lib.number("38"));
        person.setFirstName("Amy");
        person.setLastName("Smith");
        person.setDateOfBirth(lib.date("2017-01-03"));
        person.setTimeOfBirth(lib.time("11:20:30Z"));
        person.setDateTimeOfBirth(lib.dateAndTime("2016-08-01T12:00:00Z"));
        person.setGender("female");
        person.setMarried(true);
        person.setList(Collections.singletonList("Late payment"));
        person.setDateTimeList(Collections.singletonList(lib.dateAndTime("2016-08-01T12:00:00Z")));
        person.setYearsAndMonthsDuration(lib.duration("P1Y1M"));
        person.setDaysAndTimeDuration(lib.duration("P2DT20H"));
        person.setAT("AT");
        person.setAt("at");

        Writer writer = new StringWriter();
        JsonSerializer.OBJECT_MAPPER.writeValue(writer, person);
        writer.close();

        assertEquals(personText, writer.toString());
    }

    @Test
    public void testPersonDeserialization() throws IOException {
        PersonImpl person = JsonSerializer.OBJECT_MAPPER.readValue(personText, PersonImpl.class);

        assertTrue(lib.stringEqual("Amy", person.getFirstName()));
        assertTrue(lib.stringEqual("Smith", person.getLastName()));
        assertTrue(lib.numericEqual(lib.number("38"), person.getId()));
        assertTrue(lib.stringEqual("female", person.getGender()));
        assertTrue(lib.listEqual(Collections.singletonList("Late payment"), person.getList()));
        assertTrue(lib.booleanEqual(true, person.getMarried()));
        assertTrue(lib.dateEqual(lib.date("2017-01-03"), person.getDateOfBirth()));
        assertTrue(lib.timeEqual(lib.time("11:20:30Z"), person.getTimeOfBirth()));
        assertTrue(lib.dateTimeEqual(lib.dateAndTime("2016-08-01T12:00:00Z"), person.getDateTimeOfBirth()));
        assertEquals(Collections.singletonList(lib.dateAndTime("2016-08-01T12:00:00Z")), person.getDateTimeList());
        assertTrue(lib.durationEqual(lib.duration("P1Y1M"), person.getYearsAndMonthsDuration()));
        assertTrue(lib.durationEqual(lib.duration("P2DT20H"), person.getDaysAndTimeDuration()));
        assertTrue(lib.stringEqual("AT", person.getAT()));
        assertTrue(lib.stringEqual("at", person.getAt()));
    }
}
