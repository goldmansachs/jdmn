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
package com.gs.dmn.validation;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultDMNValidatorTest extends AbstractValidatorTest {
    private final DMNValidator validator = new DefaultDMNValidator();

    @Test
    public void testValidateWhenCorrect() {
        List<String> expectedErrors = Collections.emptyList();
        validate(validator, tckResource("tck/1.2/cl3/0020-vacation-days/0020-vacation-days.dmn"), expectedErrors);
    }

    @Test
    public void testValidateDefinitionsWhenNotUniqueNames() {
        List<String> expectedErrors = Arrays.asList(
                "(model='test-dmn-with-duplicates'): The name of a DRGElement must be unique. Found duplicates for 'CIP Assessments, Input'.",
                "(model='test-dmn-with-duplicates'): The name of a ItemDefinition must be unique. Found duplicates for 'itemDefinition'.",
                "(model='test-dmn-with-duplicates', name='CIP Assessments', id='cip-assessments'): Missing variable",
                "(model='test-dmn-with-duplicates', name='CIP Assessments', id='cip-assessments1'): Missing variable",
                "(model='test-dmn-with-duplicates', label='String', name='CIP Assessments', id='id-input-1'): DRGElement name and variable name should be the same. Found 'CIP Assessments' and 'string'"
        );
        validate(validator, resource("dmn/input/1.1/test-dmn-with-duplicates.dmn"), expectedErrors);
    }

    @Test
    public void testValidateDefinitionsWithError() {
        List<String> expectedErrors = Arrays.asList(
                "(model='test-dmn'): The name of a DRGElement must be unique. Found duplicates for 'dec1'.",
                "(model='test-dmn', name='dec1'): Missing importType of import",
                "(model='test-dmn', name='dec1'): Missing namespace of import",
                "(model='test-dmn', name='test-empty-container'): Incorrect definition of type",
                "(model='test-dmn'): Missing id for element TDecision",
                "(model='test-dmn'): Missing name for element TDecision",
                "(model='test-dmn'): Missing variable",
                "(model='test-dmn'): Missing expression",
                "(model='test-dmn', id='dec'): Missing name for element TDecision",
                "(model='test-dmn', id='dec'): Missing variable",
                "(model='test-dmn', id='dec'): Missing expression",
                "(model='test-dmn'): Missing id for element TBusinessKnowledgeModel",
                "(model='test-dmn'): Missing name for element TBusinessKnowledgeModel",
                "(model='test-dmn'): Missing variable",
                "(model='test-dmn'): Missing expression",
                "(model='test-dmn', id='bkm'): Missing name for element TBusinessKnowledgeModel",
                "(model='test-dmn', id='bkm'): Missing variable",
                "(model='test-dmn', id='bkm'): Missing expression",
                "(model='test-dmn', id='bkm1'): Missing name for element TBusinessKnowledgeModel",
                "(model='test-dmn', id='bkm1'): Missing variable name",
                "(model='test-dmn'): The href of a TDMNElementReference must be unique. Found duplicates for '125'.",
                "(model='test-dmn', id='bkm1'): Missing expression",
                "(model='test-dmn'): Missing id for element TDecisionService",
                "(model='test-dmn'): Missing name for element TDecisionService",
                "(model='test-dmn'): Missing variable",
                "(model='test-dmn', id='ds'): Missing name for element TDecisionService",
                "(model='test-dmn', id='ds'): Missing variable",
                "(model='test-dmn', id='ds1'): Missing name for element TDecisionService",
                "(model='test-dmn', id='ds1'): Missing variable name",
                "(model='test-dmn', name='CIP Assessments', id='cip-assessments'): Missing variable",
                "(model='test-dmn', name='dec1', id='dec1'): Missing variable name",
                "(model='test-dmn', name='dec1', id='dec1'): Missing typRef of variable",
                "(model='test-dmn', name='dec1', id='dec1'): Missing text of literal expression",
                "(model='test-dmn'): The variable type 'string' must be the same as the type of the contained expression 'number'"
        );
        validate(validator, resource("dmn/input/1.5/test-dmn.dmn"), expectedErrors);
    }

    @Test
    public void testValidateDefinitionsWhenNull() {
        List<String> actualErrors = validator.validate(null);
        assertTrue(actualErrors.isEmpty());
    }
}