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
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TypeRefValidatorTest extends AbstractFileTransformerTest {

    private final DMNReader dmnReader = new DMNReader(LOGGER, false);

    @Test
    public void testValidation() {
        List<String> expectedErrors = Arrays.asList(
                "(model='Example credit decision', label='Assess applicant age', name='assessApplicantAge', id='id-1454118466a747091e601b188b2b5c7b'): error: Cannot find typeRef 'QualifiedName(null, assessApplicantAge)'. The inferred type is 'number'",
                "(model='Example credit decision', label='Assess issue', name='assessIssue', id='id-f2562ba74063028327a13930c969145c'): error: Cannot find typeRef 'QualifiedName(null, assessIssue)'. The inferred type is 'number'",
                "(model='Example credit decision', label='Assess issue risk', name='assessIssueRisk', id='id-11e61e8750fa5f9973ad4928a3841475'): error: Cannot find typeRef 'QualifiedName(null, assessIssueRisk)'",
                "(model='Example credit decision', label='Make credit decision', name='makeCreditDecision', id='id-99379862982a9a0a4ba92985d1eea607'): error: Cannot find typeRef 'QualifiedName(null, makeCreditDecision)'. The inferred type is 'string'",
                "(model='Example credit decision', label='Process prior issues', name='processPriorIssues', id='id-b7fa3f2fe2a2f47a77bfd440c827a301'): error: Cannot find typeRef 'QualifiedName(null, processPriorIssues)'. The inferred type is 'ListType(number)'"
        );
        List<String> actualErrors = executeValidation(signavioResource("dmn/complex/credit-decision-missing-some-definitions.dmn"));

        assertEquals(expectedErrors, actualErrors);
    }

    private List<String> executeValidation(URI dmnFileURI) {
        DMNValidator validator = new TypeRefValidator(LOGGER);

        File dmnFile = new File(dmnFileURI);
        DMNModelRepository repository = new SignavioDMNModelRepository(dmnReader.read(dmnFile));
        return validator.validate(repository);
    }
}
