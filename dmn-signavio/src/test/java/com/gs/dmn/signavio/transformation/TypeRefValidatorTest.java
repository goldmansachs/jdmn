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
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.TypeRefValidator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static com.gs.dmn.signavio.testlab.TestLabValidatorTest.toText;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeRefValidatorTest extends AbstractSignavioFileTransformerTest {
    @Test
    public void testValidation() {
        List<String> expectedErrors = Arrays.asList(
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/ae3c0e4e8dab4f8fb28dd36f96c934a1.xml', modelName = 'Example credit decision', modelId = 'id-25c57a4efcbd4d2ead65fcbaa5ec9b32', elementName = 'name', elementId = 'id-3338f5be7599eb351bf9ed99c553198c-relation-10'): Cannot find definition of typeRef 'missingNameComponentTypeRef'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/ae3c0e4e8dab4f8fb28dd36f96c934a1.xml', modelName = 'Example credit decision', modelId = 'id-25c57a4efcbd4d2ead65fcbaa5ec9b32', elementName = 'lendingThresholdMissing', elementId = 'id-4cb80be6fb604151f1e9edf9c3cbe2e7-1'): Cannot find definition of typeRef 'lendingThresholdMissingTypeRef'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/ae3c0e4e8dab4f8fb28dd36f96c934a1.xml', modelName = 'Example credit decision', modelId = 'id-25c57a4efcbd4d2ead65fcbaa5ec9b32', elementName = 'assessApplicantAge', elementId = 'id-1454118466a747091e601b188b2b5c7b'): Cannot find definition of typeRef 'assessApplicantAge'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/ae3c0e4e8dab4f8fb28dd36f96c934a1.xml', modelName = 'Example credit decision', modelId = 'id-25c57a4efcbd4d2ead65fcbaa5ec9b32', elementName = 'assessIssue', elementId = 'id-f2562ba74063028327a13930c969145c'): Cannot find definition of typeRef 'assessIssue'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/ae3c0e4e8dab4f8fb28dd36f96c934a1.xml', modelName = 'Example credit decision', modelId = 'id-25c57a4efcbd4d2ead65fcbaa5ec9b32', elementName = 'assessIssueRisk', elementId = 'id-11e61e8750fa5f9973ad4928a3841475'): Cannot find definition of typeRef 'assessIssueRisk'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/ae3c0e4e8dab4f8fb28dd36f96c934a1.xml', modelName = 'Example credit decision', modelId = 'id-25c57a4efcbd4d2ead65fcbaa5ec9b32', elementName = 'compareAgainstLendingThreshold', elementId = 'id-8d177a9bb52aa7c82782a45a04074801'): Cannot find definition of typeRef 'missingDTOutputTypeRef'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/ae3c0e4e8dab4f8fb28dd36f96c934a1.xml', modelName = 'Example credit decision', modelId = 'id-25c57a4efcbd4d2ead65fcbaa5ec9b32', elementName = 'makeCreditDecision', elementId = 'id-99379862982a9a0a4ba92985d1eea607'): Cannot find definition of typeRef 'makeCreditDecision'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/ae3c0e4e8dab4f8fb28dd36f96c934a1.xml', modelName = 'Example credit decision', modelId = 'id-25c57a4efcbd4d2ead65fcbaa5ec9b32', elementName = 'processPriorIssues', elementId = 'id-b7fa3f2fe2a2f47a77bfd440c827a301'): Cannot find definition of typeRef 'processPriorIssues'"
        );
        List<SemanticError> actualErrors = executeValidation(signavioResource("dmn/complex/credit-decision-missing-some-definitions.dmn"));

        assertEquals(expectedErrors, toText(actualErrors));
    }

    private List<SemanticError> executeValidation(URI dmnFileURI) {
        DMNValidator validator = new TypeRefValidator(LOGGER);

        File dmnFile = new File(dmnFileURI);
        DMNModelRepository repository = new SignavioDMNModelRepository(this.dmnSerializer.readModel(dmnFile));
        return validator.validate(repository);
    }
}
