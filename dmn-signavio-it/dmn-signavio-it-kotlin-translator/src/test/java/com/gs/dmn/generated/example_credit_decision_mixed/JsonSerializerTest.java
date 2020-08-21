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
package com.gs.dmn.generated.example_credit_decision_mixed;

import com.gs.dmn.generated.example_credit_decision_mixed.type.Applicant;
import com.gs.dmn.generated.example_credit_decision_mixed.type.ApplicantImpl;
import com.gs.dmn.serialization.JsonSerializer;
import org.junit.Test;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonSerializerTest {
    private final GenerateOutputData decision = new GenerateOutputData();
    private final String applicantText = "{\"Age\":38,\"Credit score\":100,\"Name\":\"Amy\",\"Prior issues\":[\"Late payment\"]}";

    @Test
    public void testApplicantSerialization() throws Exception {
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
    public void testApplicantDeserialization() throws Exception {
        Applicant applicant = JsonSerializer.OBJECT_MAPPER.readValue(applicantText, ApplicantImpl.class);

        assertTrue(decision.stringEqual("Amy", applicant.getName()));
        assertTrue(decision.numericEqual(decision.number("38"), applicant.getAge()));
        assertTrue(decision.numericEqual(decision.number("100"), applicant.getCreditScore()));
        assertEquals(Arrays.asList("Late payment"), applicant.getPriorIssues());
    }
}
