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

import com.gs.dmn.DMNModelRepository;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UniqueNameValidatorTest extends AbstractValidatorTest {
    private final UniqueNameValidator validator = new UniqueNameValidator();

    @Test
    public void testValidateDefinitions() {
        File input = new File(UniqueNameValidatorTest.class.getClassLoader().getResource("tck/cl3/input/0020-vacation-days.dmn").getFile());
        DMNModelRepository repository = makeRepository(input);
        List<String> erros = validator.validate(repository);
        assertTrue(erros.isEmpty());
    }

    @Test
    public void testValidateDefinitionsWhenNotUniqueNames() {
        File input = new File(UniqueNameValidatorTest.class.getClassLoader().getResource("dmn/input/test-dmn-with-duplicates.dmn").getFile());
        DMNModelRepository repository = makeRepository(input);
        List<String> actualErrors = validator.validate(repository);

        List<String> expectedErrors = Arrays.asList(
                "The 'name' of a 'DRGElement' must be unique. Found 3 duplicates for 'CIP Assessments'.",
                "The 'name' of a 'DRGElement' must be unique. Found 2 duplicates for 'Input'.",
                "The 'name' of a 'ItemDefinition' must be unique. Found 2 duplicates for 'itemDefinition'."
        );
        assertEquals(expectedErrors, actualErrors);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateDefinitionsWhenNull() {
        validator.validate(null);
    }

}