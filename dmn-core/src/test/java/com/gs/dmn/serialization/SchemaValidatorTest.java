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

import com.gs.dmn.tck.TestCasesReader;
import org.junit.Test;

import java.io.File;

import static com.gs.dmn.serialization.DMNConstants.DMN_11_PACKAGE;

public class SchemaValidatorTest {

    @Test
    public void testValidateForDMNFiles() {
        File schemaLocation = new File(SchemaValidatorTest.class.getClassLoader().getResource("dmn/dmn.xsd").getFile());

        SchemaValidator validator = new SchemaValidator(schemaLocation, DMN_11_PACKAGE);
        File dmnFileFolder = new File(SchemaValidatorTest.class.getClassLoader().getResource("tck").getFile());
        validate(dmnFileFolder, validator, DMNConstants.DMN_FILE_EXTENSION);
    }

    @Test
    public void testValidateForTCKFiles() {
        File schemaLocation = new File(SchemaValidatorTest.class.getClassLoader().getResource("tck/testCases.xsd").getFile());

        SchemaValidator validator = new SchemaValidator(schemaLocation, "org.omg.dmn.tck.marshaller._20160719");
        File dmnFileFolder = new File(SchemaValidatorTest.class.getClassLoader().getResource("tck").getFile());
        validate(dmnFileFolder, validator, TestCasesReader.TEST_FILE_EXTENSION);
    }

    private void validate(File file, SchemaValidator validator, String extension) {
        if (file.isDirectory()) {
            for(File child: file.listFiles()) {
                validate(child, validator, extension);
            }
        } else {
            validator.validateFile(file, extension);
        }
    }

}