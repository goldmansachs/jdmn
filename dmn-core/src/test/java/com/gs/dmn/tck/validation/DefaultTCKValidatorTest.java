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
package com.gs.dmn.tck.validation;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultTCKValidatorTest extends AbstractValidatorTest {
    private final TCKValidator validator = new DefaultTCKValidator();

    @Test
    public void testValidateWhenTestCasesNameIsMissing() {
        List<String> expectedErrors = Arrays.asList(
                "[ERROR] Missing testCasesName for element TestCases"
        );
        validate(validator, tckResource("tck/1.2/cl3/0020-vacation-days/0020-vacation-days-test-01.xml"), expectedErrors);
    }

    @Test
    public void testValidateDefinitionsWithDuplicatesError() {
        List<String> expectedErrors = Arrays.asList(
                "[ERROR] (testCasesName = '0004-lending-test-01.xml'): The id of a TestCase must be unique. Found duplicates for '001'.",
                "[ERROR] (testCasesName = '0004-lending-test-01.xml', testCase = '001'): The name of a component must be unique. Found duplicates for 'Age'.",
                "[ERROR] (testCasesName = '0004-lending-test-01.xml', testCase = '001'): The name of a component must be unique. Found duplicates for 'Income'.",
                "[ERROR] (testCasesName = '0004-lending-test-01.xml', testCase = '001'): The name of a component must be unique. Found duplicates for 'CreditScore'."
        );
        validate(validator, resource("dmn/input/1.1/test-tck-with-duplicates.xml"), expectedErrors);
    }

    @Test
    public void testValidateDefinitionsWithError() {
        List<String> expectedErrors = Arrays.asList(
                "[ERROR] (testCasesName = '0004-lending-test-01.xml', testCase = '001'): Missing name of inputNode in testCase with id '001'",
                "[ERROR] (testCasesName = '0004-lending-test-01.xml', testCase = '001'): Missing value of inputNode '' in testCase with id '001'",
                "[ERROR] (testCasesName = '0004-lending-test-01.xml', testCase = '001'): Missing value of inputNode 'ApplicantData' in testCase with id '001'",
                "[ERROR] (testCasesName = '0004-lending-test-01.xml', testCase = '001'): Missing name of resultNode in testCase with id '001'",
                "[ERROR] (testCasesName = '0004-lending-test-01.xml', testCase = '003'): Missing invocableName of testCase with id '003'",
                "[ERROR] (testCasesName = '0004-lending-test-01.xml', testCase = '004'): Invalid type of testCase with id '004'. Expected type is bkm or decisionService when invocableName is provided.",
                "[ERROR] (testCasesName = '0004-lending-test-01.xml'): The name of a InputNode must be unique. Found duplicates for 'Routing'.",
                "[ERROR] (testCasesName = '0004-lending-test-01.xml'): The name of a ResultNode must be unique. Found duplicates for 'Routing'."
        );
        validate(validator, resource("dmn/input/1.1/test-tck.xml"), expectedErrors);
    }

    @Test
    public void testValidateDefinitionsWhenNull() {
        List<ValidationError> actualErrors = validator.validate(null);
        assertTrue(actualErrors.isEmpty());
    }
}