/**
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
package com.gs.dmn.serialization;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import org.junit.Test;
import org.omg.spec.dmn._20151101.dmn.TDefinitions;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.junit.Assert.*;

public class DMNValidatorTest {
    private static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(DMNValidatorTest.class));

    private DMNDialectDefinition dialectDefinition = new StandardDMNDialectDefinition();
    private DMNValidator validator = dialectDefinition.createValidator(true);
    private DMNReader reader = new DMNReader(LOGGER, false);

    @Test
    public void testValidateDefinitions() {
        validate("tck/cl3/input/0020-vacation-days.dmn");
    }

    @Test
    public void testValidateDefinitionsWhenNotUniqueNames() {
        try {
            File input = new File(DMNValidatorTest.class.getClassLoader().getResource("dmn/input/test-dmn-with-duplicates.dmn").getFile());
            TDefinitions definitions = reader.read(input);
            validator.validateDefinitions(new DMNModelRepository(definitions));
            fail("Should throw IllegalArgument");
        } catch (IllegalArgumentException e) {
            assertEquals("The 'name' of a 'DRGElement' must be unique. Found duplicates for 'CIP Assessments'.", e.getMessage());
        }
    }

    @Test
    public void testValidateDefinitionsWithError() {
        try {
            File input = new File(DMNValidatorTest.class.getClassLoader().getResource("dmn/input/test-dmn.dmn").getFile());
            TDefinitions definitions = reader.read(input);
            validator.validateDefinitions(new DMNModelRepository(definitions));
            fail("Should throw IllegalArgument");
        } catch (IllegalArgumentException e) {
            assertEquals("Missing variable for 'CIP Assessments'", e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateDefinitionsWhenNull() {
        validator.validateDefinitions(null);
    }

    private void validate(String path) {
        try {
            File input = new File(DMNValidatorTest.class.getClassLoader().getResource(path).getFile());
            TDefinitions definitions = reader.read(input);
            validator.validateDefinitions(new DMNModelRepository(definitions));
            assertTrue(true);
        } catch (IllegalArgumentException e) {
            fail("Unexpected exception, diagram is correct");
        }
    }
}