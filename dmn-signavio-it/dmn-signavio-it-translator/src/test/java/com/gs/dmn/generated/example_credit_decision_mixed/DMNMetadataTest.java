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

import com.gs.dmn.runtime.metadata.*;
import com.gs.dmn.serialization.JsonSerializer;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DMNMetadataTest {
    @Test
    public void testMetadata() throws Exception {
        File metadataFile = new File(getClass().getClassLoader().getResource("com/gs/dmn/generated/example_credit_decision_mixed/DMNMetadata.json").getFile());
        DMNMetadata dmnMetadata = JsonSerializer.OBJECT_MAPPER.readValue(metadataFile, DMNMetadata.class);
        assertEquals("1.3", dmnMetadata.getDmnVersion());
        assertEquals("1.0", dmnMetadata.getModelVersion());
        assertNotNull(dmnMetadata.getPlatformVersion());

        List<String> expectedTypes = Arrays.asList("applicant", "assessApplicantAge", "assessIssue", "assessIssueRisk",
                "compareAgainstLendingThreshold", "creditIssueType", "currentRiskAppetite",
                "generateOutputData", "lendingThreshold", "makeCreditDecision",
                "priorIssue", "processPriorIssues");
        assertEquals(expectedTypes, dmnMetadata.getTypes().stream().map(NamedElement::getName).sorted().collect(Collectors.toList()));

        List<String> expectedElements = Arrays.asList("applicant", "assessApplicantAge", "assessIssue", "assessIssueRisk",
                "compareAgainstLendingThreshold", "currentRiskAppetite",
                "generateOutputData", "lendingThreshold", "makeCreditDecision",
                "priorIssue_iterator", "processPriorIssues");
        assertEquals(expectedElements, dmnMetadata.getElements().stream().map(NamedElement::getName).sorted().collect(Collectors.toList()));

        List<String> expectedInputDatas = Arrays.asList("applicant", "currentRiskAppetite", "lendingThreshold", "priorIssue_iterator");
        assertEquals(expectedInputDatas, dmnMetadata.getInputDatas().stream().map(NamedElement::getName).sorted().collect(Collectors.toList()));

        List<String> expectedDecisions = Arrays.asList("assessApplicantAge", "assessIssue", "assessIssueRisk",
                "compareAgainstLendingThreshold", "generateOutputData", "makeCreditDecision", "processPriorIssues");
        assertEquals(expectedDecisions, dmnMetadata.getDecisions().stream().map(NamedElement::getName).sorted().collect(Collectors.toList()));

        // Check applicant input data
        InputData applicant = (InputData) dmnMetadata.findElementByName("applicant");
        assertEquals("id-d2376567fde3c9400ee327ecec21e36d", applicant.getId());
        assertEquals("applicant", applicant.getName());
        assertEquals("Applicant", applicant.getLabel());
        assertEquals("{http://www.provider.com/dmn/1.1/diagram/9acf44f2b05343d79fc35140c493c1e0.xml}applicant", applicant.getTypeRef().toString());

        assertNotNull(dmnMetadata.findElementById("id-d2376567fde3c9400ee327ecec21e36d"));

        // Check top decision
        Decision generateOutputData = (Decision) dmnMetadata.findElementByName("generateOutputData");
        assertEquals("id-f3dfdd3ac42c255265e190eaf50dd65d", generateOutputData.getId());
        assertEquals("generateOutputData", generateOutputData.getName());
        assertEquals("Generate output data", generateOutputData.getLabel());
        assertEquals("{http://www.provider.com/dmn/1.1/diagram/9acf44f2b05343d79fc35140c493c1e0.xml}generateOutputData", generateOutputData.getTypeRef().toString());

        // Check applicantType
        CompositeType applicantType = (CompositeType) dmnMetadata.findTypeByName("applicant");
        assertEquals("id-5bf135bcd0f2d1d6dabfb49f463ee763", applicantType.getId());
        assertEquals("applicant", applicantType.getName());
        assertEquals("Applicant", applicantType.getLabel());
        List<String> expectedSubTypes = Arrays.asList("age", "creditScore", "name", "priorIssues");
        assertEquals(expectedSubTypes, applicantType.getTypes().stream().map(NamedElement::getName).collect(Collectors.toList()));
    }
}
