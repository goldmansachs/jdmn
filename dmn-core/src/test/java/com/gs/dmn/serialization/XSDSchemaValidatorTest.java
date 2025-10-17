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
package com.gs.dmn.serialization;

import com.gs.dmn.validation.AbstractValidatorTest;
import org.junit.jupiter.api.Test;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class XSDSchemaValidatorTest extends AbstractValidatorTest {
    @Test
    public void testSchemaValidationFor11() {
        File dmnFile = new File(resource("dmn/input/1.1/test-dmn.dmn").getPath());
        List<String> actualErrors = XSDSchemaValidator.validateXSDSchema(new StreamSource(dmnFile), DMNVersion.DMN_11);
        assertTrue(actualErrors.isEmpty());
    }

    @Test
    public void testSchemaValidationFor15() {
        File dmnFile = new File(resource("dmn/input/1.5/test-dmn.dmn").getPath());
        List<String> actualErrors = XSDSchemaValidator.validateXSDSchema(new StreamSource(dmnFile), DMNVersion.DMN_15);
        List<String> expectedErrors = Arrays.asList(
                "Line 80, Column 15: cvc-complex-type.4: Attribute 'name' must appear on element 'decision'.",
                "Line 83, Column 24: cvc-complex-type.4: Attribute 'name' must appear on element 'decision'.",
                "Line 87, Column 20: cvc-complex-type.4: Attribute 'name' must appear on element 'variable'.",
                "Line 98, Column 29: cvc-complex-type.4: Attribute 'name' must appear on element 'businessKnowledgeModel'.",
                "Line 101, Column 38: cvc-complex-type.4: Attribute 'name' must appear on element 'businessKnowledgeModel'.",
                "Line 104, Column 39: cvc-complex-type.4: Attribute 'name' must appear on element 'businessKnowledgeModel'.",
                "Line 105, Column 20: cvc-complex-type.4: Attribute 'name' must appear on element 'variable'.",
                "Line 112, Column 29: cvc-complex-type.2.4.a: Invalid content was found starting with element '{\"https://www.omg.org/spec/DMN/20230324/MODEL/\":literalExpression}'. One of '{\"https://www.omg.org/spec/DMN/20230324/MODEL/\":knowledgeRequirement, \"https://www.omg.org/spec/DMN/20230324/MODEL/\":authorityRequirement}' is expected.",
                "Line 115, Column 22: cvc-complex-type.4: Attribute 'name' must appear on element 'decisionService'.",
                "Line 118, Column 30: cvc-complex-type.4: Attribute 'name' must appear on element 'decisionService'.",
                "Line 121, Column 31: cvc-complex-type.4: Attribute 'name' must appear on element 'decisionService'.",
                "Line 122, Column 20: cvc-complex-type.4: Attribute 'name' must appear on element 'variable'.",
                "Line 123, Column 21: cvc-complex-type.4: Attribute 'href' must appear on element 'inputData'.",
                "Line 124, Column 25: cvc-complex-type.2.4.a: Invalid content was found starting with element '{\"https://www.omg.org/spec/DMN/20230324/MODEL/\":inputDecision}'. One of '{\"https://www.omg.org/spec/DMN/20230324/MODEL/\":inputData}' is expected.",
                "Line 124, Column 25: cvc-complex-type.4: Attribute 'href' must appear on element 'inputDecision'.",
                "Line 125, Column 26: cvc-complex-type.4: Attribute 'href' must appear on element 'outputDecision'."
        );
        checkErrors(expectedErrors, actualErrors);
    }
}