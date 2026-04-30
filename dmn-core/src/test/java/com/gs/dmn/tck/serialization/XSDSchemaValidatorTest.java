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
package com.gs.dmn.tck.serialization;

import com.gs.dmn.error.ValidationError;
import com.gs.dmn.serialization.TCKVersion;
import com.gs.dmn.validation.AbstractValidatorTest;
import org.junit.jupiter.api.Test;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class XSDSchemaValidatorTest extends AbstractValidatorTest {
    private final XSDSchemaValidator validator = new XSDSchemaValidator();

    @Test
    public void testSchemaValidationForEmpty() {
        File dmnFile = new File(resource("dmn/input/1.1/test-empty-tck.xml").getPath());
        List<ValidationError> actualErrors = validator.validateXSDSchema(new StreamSource(dmnFile), TCKVersion.LATEST);
        List<String> expectedErrors = Arrays.asList(
                "[ERROR] (testCasesName = 'test-empty-tck.xml'): Line 5, Column 13: cvc-complex-type.2.4.b: The content of element 'testCases' is not complete. One of '{\"http://www.omg.org/spec/DMN/20160719/testcase\":labels, \"http://www.omg.org/spec/DMN/20160719/testcase\":testCase}' is expected.");
        checkErrors(XSDSchemaValidator.RULE_NAME, expectedErrors, actualErrors);
    }

    @Test
    public void testSchemaValidationForComplex() {
        File dmnFile = new File(resource("dmn/input/1.1/test-tck.xml").getPath());
        List<ValidationError> actualErrors = validator.validateXSDSchema(new StreamSource(dmnFile), TCKVersion.LATEST);
        List<String> expectedErrors = Arrays.asList(
                "[ERROR] (testCasesName = 'test-tck.xml'): Line 10, Column 21: cvc-complex-type.4: Attribute 'name' must appear on element 'resultNode'.");
        checkErrors(XSDSchemaValidator.RULE_NAME, expectedErrors, actualErrors);
    }
}