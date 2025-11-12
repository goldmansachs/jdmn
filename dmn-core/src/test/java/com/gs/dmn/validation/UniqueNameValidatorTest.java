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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UniqueNameValidatorTest extends AbstractValidatorTest {
    private final UniqueNameValidator validator = new UniqueNameValidator();

    @Test
    public void testValidateWhenCorrect() {
        validate(validator, tckResource("tck/1.2/cl3/0020-vacation-days/0020-vacation-days.dmn"), new ArrayList<>());
    }

    @Test
    public void testValidateDefinitionsWhenNotUniqueNames() {
        List<String> expectedErrors = Arrays.asList(
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn-with-duplicates', modelId = 'definitions'): The 'name' of a 'DRGElement' must be unique. Found 3 duplicates for 'CIP Assessments'.",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn-with-duplicates', modelId = 'definitions'): The 'name' of a 'DRGElement' must be unique. Found 2 duplicates for 'Input'.",
                "[ERROR] (namespace = 'http://camunda.org/schema/1.0/dmn', modelName = 'test-dmn-with-duplicates', modelId = 'definitions'): The 'name' of a 'ItemDefinition' must be unique. Found 2 duplicates for 'itemDefinition'."
        );
        validate(validator, resource("dmn/input/1.1/test-dmn-with-duplicates.dmn"), expectedErrors);
    }

    @Test
    public void testValidateDefinitionsWhenNull() {
        List<SemanticError> actualErrors = validator.validate(null);
        assertTrue(actualErrors.isEmpty());
    }
}