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

import com.gs.dmn.error.SemanticError;
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
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn-with-duplicates', modelId = 'definitions'): The name of a DRGElement must be unique. Found duplicates for 'CIP Assessments, Input'.",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn-with-duplicates', modelId = 'definitions'): The name of a ItemDefinition must be unique. Found duplicates for 'itemDefinition'.",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn-with-duplicates', modelId = 'definitions', elementName = 'CIP Assessments', elementId = 'cip-assessments'): Missing variable",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn-with-duplicates', modelId = 'definitions', elementName = 'CIP Assessments', elementId = 'cip-assessments1'): Missing variable",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn-with-duplicates', modelId = 'definitions', elementName = 'CIP Assessments', elementId = 'id-input-1'): DRGElement name and variable name should be the same. Found 'CIP Assessments' and 'string'"
        );
        validate(validator, resource("dmn/input/1.1/test-dmn-with-duplicates.dmn"), expectedErrors);
    }

    @Test
    public void testValidateDefinitionsWithError() {
        List<String> expectedErrors = Arrays.asList(
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions'): The name of a DRGElement must be unique. Found duplicates for 'dec1'.",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementName = 'dec1'): Missing importType of import",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementName = 'dec1'): Missing namespace of import",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementName = 'test-empty-container'): Incorrect definition of type",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions'): Missing id for element TDecision",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions'): Missing name for element TDecision",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions'): Missing variable",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions'): Missing expression",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementId = 'dec'): Missing name for element TDecision",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementId = 'dec'): Missing variable",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementId = 'dec'): Missing expression",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions'): Missing id for element TBusinessKnowledgeModel",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions'): Missing name for element TBusinessKnowledgeModel",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions'): Missing variable",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions'): Missing expression",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementId = 'bkm'): Missing name for element TBusinessKnowledgeModel",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementId = 'bkm'): Missing variable",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementId = 'bkm'): Missing expression",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementId = 'bkm1'): Missing name for element TBusinessKnowledgeModel",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementId = 'bkm1'): Missing variable name",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions'): The href of a TDMNElementReference must be unique. Found duplicates for '125'.",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementId = 'bkm1'): Missing expression",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions'): Missing id for element TDecisionService",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions'): Missing name for element TDecisionService",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions'): Missing variable",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementId = 'ds'): Missing name for element TDecisionService",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementId = 'ds'): Missing variable",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementId = 'ds1'): Missing name for element TDecisionService",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementId = 'ds1'): Missing variable name",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementName = 'CIP Assessments', elementId = 'cip-assessments'): Missing variable",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementName = 'dec1', elementId = 'dec1'): Missing variable name",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementName = 'dec1', elementId = 'dec1'): Missing typRef of variable",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions', elementName = 'dec1', elementId = 'dec1'): Missing text of literal expression",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn', modelId = 'definitions'): The variable type 'string' must be the same as the type of the contained expression 'number'"
        );
        validate(validator, resource("dmn/input/1.5/test-dmn.dmn"), expectedErrors);
    }

    @Test
    public void testValidateDefinitionsWhenNull() {
        List<SemanticError> actualErrors = validator.validate(null);
        assertTrue(actualErrors.isEmpty());
    }
}