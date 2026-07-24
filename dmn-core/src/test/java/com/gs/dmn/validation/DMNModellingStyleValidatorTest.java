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

public class DMNModellingStyleValidatorTest extends AbstractValidatorTest {
    private final DMNModellingStyleValidator validator = new DMNModellingStyleValidator();

    @Test
    public void testValidateWhenCorrect() {
        validate(validator, tckResource("tck/1.2/cl3/0020-vacation-days/0020-vacation-days.dmn"), new ArrayList<>());
    }

    @Test
    public void testValidateWhenIncorrect() {
        List<String> expectedErrors = List.of(
                "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style', modelName = 'test-dmn-modelling-style', modelId = 'test-dmn-modelling-style', elementName = 'person', elementId = 'person'): Complex type 'person' contains nested complex type 'address' which is not allowed. Complex types should be modelled separately.",
                "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style', modelName = 'test-dmn-modelling-style', modelId = 'test-dmn-modelling-style'): All context entries should be literal expressions. Context entry 'address' is a not a literal expression"
        );
        validate(validator, resource("dmn/input/1.5/test-dmn-modelling-style.dmn"), expectedErrors);
    }

    @Test
    public void testValidateDefinitionsWhenNull() {
        List<ValidationError> actualErrors = validator.validate(null);
        assertTrue(actualErrors.isEmpty());
    }
}
