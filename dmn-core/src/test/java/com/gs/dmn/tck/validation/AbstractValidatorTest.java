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

import com.gs.dmn.AbstractTest;
import com.gs.dmn.tck.TCKSerializer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.serialization.xstream.XMLTCKSerializer;

import java.io.File;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractValidatorTest extends AbstractTest {
    protected final TCKSerializer serializer = new XMLTCKSerializer(LOGGER, this.inputParameters);

    protected void validate(String ruleName, TCKValidator validator, URI fileURI, List<String> expectedErrors) {
        TestCases repository = readTestCases(fileURI);
        List<ValidationError> actualErrors = validator.validate(repository);

        checkErrors(ruleName, expectedErrors, actualErrors);
    }

    protected void validate(TCKValidator validator, URI fileURI, List<String> expectedErrors) {
        validate(validator.ruleName(), validator, fileURI, expectedErrors);
    }

    protected TestCases readTestCases(URI fileURI) {
        File input = new File(fileURI);
        return this.serializer.read(input);
    }

    protected void checkErrors(String ruleName, List<String> expectedErrors, List<ValidationError> actualErrors) {
        assertEquals(expectedErrors.size(), actualErrors.size());
        for (int i = 0; i < actualErrors.size(); i++) {
            String expected = String.format("[%s] %s", ruleName, expectedErrors.get(i));
            String actual = actualErrors.get(i).toText();
            assertEquals(expected, actual, "Failed at index " + i);
        }
    }

    protected void checkXSDErrors(List<String> expectedErrors, List<String> actualErrors) {
        assertEquals(expectedErrors.size(), actualErrors.size());
        for (int i = 0; i < actualErrors.size(); i++) {
            assertEquals(expectedErrors.get(i), actualErrors.get(i), "Failed at index " + i);
        }
    }
}