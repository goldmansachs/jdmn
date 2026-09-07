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

import com.gs.dmn.error.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DictionaryValidatorTest extends AbstractValidatorTest {
    private final DictionaryValidator validator = new DictionaryValidator();

    @Test
    public void testValidateWhenCorrect() {
        validateCompositeValidator(validator, resource("dmn/input/1.5/test-dictionary-correct.dmn"), new ArrayList<>());
    }

    @Test
    public void testValidateWhenIncorrect() {
        List<String> expectedErrors = List.of(
                "[dictionary-validator] [ERROR] (namespace = 'http://test.example.org/dmn/dictionary-incorrect', modelName = 'test-dictionary-incorrect', modelId = 'definitions'): Dictionaries must not contain DRG elements.",
                "[default-dmn-validator] [ERROR] (namespace = 'http://test.example.org/dmn/dictionary-incorrect', modelName = 'test-dictionary-incorrect', modelId = 'definitions', elementId = 'it2'): Missing name for element TItemDefinition",
                "[dmn-modelling-style-validator] [WARNING] (namespace = 'http://test.example.org/dmn/dictionary-incorrect', modelName = 'test-dictionary-incorrect', modelId = 'definitions', elementName = 'Incorrect name #', elementId = 'it3'): Name 'Incorrect name #' contains invalid characters. Names should contain only alphanumeric characters, underscores, dashes and spaces, and should start with an alphanumeric character.",
                "[type-ref-validator] [ERROR] (namespace = 'http://test.example.org/dmn/dictionary-incorrect', modelName = 'test-dictionary-incorrect', modelId = 'definitions', elementName = 'Missing typeRef definition', elementId = 'it4'): Cannot find definition of typeRef 'abc'"
        );
        validateCompositeValidator(validator, resource("dmn/input/1.5/test-dictionary-incorrect.dmn"), expectedErrors);
    }

    @Test
    public void testValidateDefinitionsWhenNull() {
        List<ValidationError> actualErrors = validator.validate(null);
        assertTrue(actualErrors.isEmpty());
    }
}
