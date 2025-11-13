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
package com.gs.dmn.signavio.testlab;

import com.gs.dmn.error.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLabValidatorTest {
    public static List<String> toText(List<ValidationError> errors) {
        return errors.stream().map(ValidationError::toText).collect(Collectors.toList());
    }

    private final TestLabValidator validator = new TestLabValidator();

    @Test
    public void testValidateWhenNull() {
        List<ValidationError> errors = validator.validate(null);
        assertEquals(List.of("[test-lab-validator] [ERROR] Missing or empty TestLab"), toText(errors));
    }

    @Test
    public void testValidateWhenEmpty() {
        List<ValidationError> errors = validator.validate(new TestLab());
        assertEquals(List.of("[test-lab-validator] [ERROR] Missing or empty OutputParameterDefinitions for TestLab 'null'"), toText(errors));
    }
}