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
package com.gs.dmn.signavio.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.transformation.AbstractFileTransformerTest;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.TypeRefValidator;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class TypeRefValidatorTest extends AbstractFileTransformerTest {
    private static final String BASE_PATH = "dmn2java/exported/complex";

    private final DMNReader dmnReader = new DMNReader(LOGGER, false);

    @Test
    public void testValidation() {
        List<String> expectedErrors = Arrays.asList(
                "Error:(label='Assess applicant age', name='assessApplicantAge', id='id-1454118466a747091e601b188b2b5c7b') Cannot find typeRef 'QualifiedName(null, assessApplicantAge)'. The inferred type is 'number'",
                "Error:(label='Assess issue', name='assessIssue', id='id-f2562ba74063028327a13930c969145c') Cannot find typeRef 'QualifiedName(null, assessIssue)'. The inferred type is 'number'",
                "Error:(label='Assess issue risk', name='assessIssueRisk', id='id-11e61e8750fa5f9973ad4928a3841475') Cannot find typeRef 'QualifiedName(null, assessIssueRisk)'",
                "Error:(label='Make credit decision', name='makeCreditDecision', id='id-99379862982a9a0a4ba92985d1eea607') Cannot find typeRef 'QualifiedName(null, makeCreditDecision)'. The inferred type is 'string'",
                "Error:(label='Prior issue', name='priorIssue_iterator', id='id-0effee0f01e97b6e75c76627f8ebebe6') Cannot find typeRef 'QualifiedName(null, priorIssue)'",
                "Error:(label='Process prior issues', name='processPriorIssues', id='id-b7fa3f2fe2a2f47a77bfd440c827a301') Cannot find typeRef 'QualifiedName(null, processPriorIssues)'. The inferred type is 'ListType(number)'"
        );
        List<String> actualErrors = executeValidation(
                resourcePath("input/credit-decision-missing-some-definitions.dmn"));

        assertExpectedErrors(expectedErrors, actualErrors);
    }

    private List<String> executeValidation(String dmnFilePath) {
        DMNValidator validator = new TypeRefValidator(LOGGER);

        File dmnFile = new File(resource(dmnFilePath));
        DMNModelRepository repository = new SignavioDMNModelRepository(dmnReader.read(dmnFile));
        return validator.validate(repository);
    }

    private String resourcePath(String relativePath) {
        return String.format("%s/%s", BASE_PATH, relativePath);
    }

    private void assertExpectedErrors(List<String> expectedErrors, List<String> actualErrors) {
        assertEquals("Same size", expectedErrors.size(), actualErrors.size());
        for (int i=0; i<expectedErrors.size(); i++) {
            assertEquals(expectedErrors.get(i), actualErrors.get(i));
        }
    }
}
