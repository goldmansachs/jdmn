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
package com.gs.dmn.example_credit_decision_mixed;

import com.gs.dmn.example_credit_decision_mixed.type.Applicant;
import com.gs.dmn.example_credit_decision_mixed.type.ApplicantImpl;
import com.gs.dmn.feel.lib.type.time.xml.DefaultDateTimeLib;
import com.gs.dmn.serialization.JsonSerializer;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonSerializerTest {
    private final GenerateOutputData decision = new GenerateOutputData();
    private final String applicantText = "{\"Age\":38,\"Credit score\":100,\"Name\":\"Amy\",\"Prior issues\":[\"Late payment\"]}";
    private final String personText = "{\"firstName\":\"Amy\",\"lastName\":\"Smith\",\"id\":38,\"gender\":\"female\",\"dateOfBirth\":\"2017-01-03\",\"timeOfBirth\":\"11:20:30Z\",\"dateTimeOfBirth\":\"2016-08-01T12:00:00Z\",\"list\":[\"Late payment\"],\"married\":true,\"dateTimeList\":[\"2016-08-01T12:00:00Z\"],\"yearsAndMonthsDuration\":\"P1Y1M\",\"daysAndTimeDuration\":\"P2DT20H\"}";

    @Test
    public void testApplicantSerialization() throws IOException {
        ApplicantImpl applicant = new ApplicantImpl();
        applicant.setName("Amy");
        applicant.setAge(decision.number("38"));
        applicant.setCreditScore(decision.number("100"));
        applicant.setPriorIssues(Arrays.asList("Late payment"));

        Writer writer = new StringWriter();
        JsonSerializer.OBJECT_MAPPER.writeValue(writer, applicant);
        writer.close();

        assertEquals(applicantText, writer.toString());
    }

    @Test
    public void testApplicantDeserialization() throws IOException {
        Applicant applicant = JsonSerializer.OBJECT_MAPPER.readValue(applicantText, ApplicantImpl.class);

        assertTrue(decision.stringEqual("Amy", applicant.getName()));
        assertTrue(decision.numericEqual(decision.number("38"), applicant.getAge()));
        assertTrue(decision.numericEqual(decision.number("100"), applicant.getCreditScore()));
        assertEquals(Arrays.asList("Late payment"), applicant.getPriorIssues());
    }

    @Test
    public void testPersonSerialization() throws IOException {
        Person person = new Person();
        person.setFirstName("Amy");
        person.setLastName("Smith");
        person.setId(decision.number("38"));
        person.setGender("female");
        person.setList(Arrays.asList("Late payment"));
        person.setMarried(true);
        person.setDateOfBirth(decision.date("2017-01-03"));
        person.setTimeOfBirth(decision.time("11:20:30Z"));
        person.setDateTimeOfBirth(decision.dateAndTime("2016-08-01T12:00:00Z"));
        person.setDateTimeList(Arrays.asList(decision.dateAndTime("2016-08-01T12:00:00Z")));
        person.setYearsAndMonthsDuration(decision.duration("P1Y1M"));
        person.setDaysAndTimeDuration(decision.duration("P2DT20H"));

        Writer writer = new StringWriter();
        JsonSerializer.OBJECT_MAPPER.writeValue(writer, person);
        writer.close();

        assertEquals(personText, writer.toString());
    }

    @Test
    public void testPersonDeserialization() throws IOException {
        Person person = JsonSerializer.OBJECT_MAPPER.readValue(personText, Person.class);

        assertTrue(decision.stringEqual("Amy", person.getFirstName()));
        assertTrue(decision.stringEqual("Smith", person.getLastName()));
        assertTrue(decision.numericEqual(decision.number("38"), person.getId()));
        assertTrue(decision.stringEqual("female", person.getGender()));
        assertTrue(decision.listEqual(Arrays.asList("Late payment"), person.getList()));
        assertTrue(decision.booleanEqual(true, person.getMarried()));
        assertTrue(decision.dateEqual(decision.date("2017-01-03"), person.getDateOfBirth()));
        assertTrue(decision.timeEqual(decision.time("11:20:30Z"), person.getTimeOfBirth()));
        assertTrue(decision.dateTimeEqual(decision.dateAndTime("2016-08-01T12:00:00Z"), person.getDateTimeOfBirth()));
        assertEquals(Arrays.asList(decision.dateAndTime("2016-08-01T12:00:00Z").withZoneSameInstant(DefaultDateTimeLib.UTC)), person.getDateTimeList());
        assertTrue(decision.durationEqual(decision.duration("P1Y1M"), person.getYearsAndMonthsDuration()));
        assertTrue(decision.durationEqual(decision.duration("P2DT20H"), person.getDaysAndTimeDuration()));
    }
}
