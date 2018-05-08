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
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.DefaultDMNValidator;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class DMNValidatorTest {
    private static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(DMNValidatorTest.class));

    private final DMNValidator validator = new DefaultDMNValidator();
    private final DMNReader reader = new DMNReader(LOGGER, false);

    @Test
    public void testValidateDefinitions() {
        validate("tck/cl3/input/0020-vacation-days.dmn");
    }

    @Test
    public void testValidateDefinitionsWhenNotUniqueNames() {
        File input = new File(DMNValidatorTest.class.getClassLoader().getResource("dmn/input/test-dmn-with-duplicates.dmn").getFile());
        DMNModelRepository repository = reader.read(input);
        List<String> errors = validator.validate(repository);
        assertTrue(!errors.isEmpty());
    }

    @Test
    public void testValidateDefinitionsWithError() {
        File input = new File(DMNValidatorTest.class.getClassLoader().getResource("dmn/input/test-dmn.dmn").getFile());
        DMNModelRepository repository = reader.read(input);
        List<String> errors = validator.validate(repository);
        assertTrue(!errors.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateDefinitionsWhenNull() {
        validator.validate(null);
    }

    private void validate(String path) {
        File input = new File(DMNValidatorTest.class.getClassLoader().getResource(path).getFile());
        DMNModelRepository repository = reader.read(input);
        List<String> erros = validator.validate(repository);
        assertTrue(erros.isEmpty());
    }
}