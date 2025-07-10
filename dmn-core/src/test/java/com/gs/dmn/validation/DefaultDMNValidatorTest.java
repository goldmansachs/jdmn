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

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultDMNValidatorTest extends AbstractValidatorTest {
    private final DMNValidator validator = new DefaultDMNValidator();

    @Test
    public void testValidateWhenCorrect() {
        List<String> expectedErrors = Collections.emptyList();
        validate(validator, tckResource("tck/1.2/cl3/0020-vacation-days/0020-vacation-days.dmn"), expectedErrors);
    }

    @Test
    public void testValidateDefinitionsWhenNotUniqueNames() {
        List<String> expectedErrors = Arrays.asList(
                "(model='test-dmn-with-duplicates'): error: The name of a DRGElement must be unique. Found duplicates for 'CIP Assessments, Input'.",
                "(model='test-dmn-with-duplicates'): error: The name of a ItemDefinition must be unique. Found duplicates for 'itemDefinition'.",
                "(model='test-dmn-with-duplicates', name='CIP Assessments', id='cip-assessments'): error: Missing variable",
                "(model='test-dmn-with-duplicates', name='CIP Assessments', id='cip-assessments1'): error: Missing variable",
                "(model='test-dmn-with-duplicates', label='String', name='CIP Assessments', id='id-input-1'): error: DRGElement name and variable name should be the same. Found 'CIP Assessments' and 'string'"
        );
        validate(validator, resource("dmn/input/1.1/test-dmn-with-duplicates.dmn"), expectedErrors);
    }

    @Test
    public void testValidateDefinitionsWithError() {
        List<String> expectedErrors = Arrays.asList(
                "(model='test-dmn'): error: The name of a DRGElement must be unique. Found duplicates for 'dec1'.",
                "(model='test-dmn', name='dec1'): error: Missing id",
                "(model='test-dmn', name='dec1'): error: Missing importType of import",
                "(model='test-dmn', name='dec1'): error: Missing namespace of import",
                "(model='test-dmn'): error: Missing id",
                "(model='test-dmn'): error: Missing name",
                "(model='test-dmn'): error: Missing variable",
                "(model='test-dmn'): error: Missing expression",
                "(model='test-dmn', id='dec'): error: Missing name",
                "(model='test-dmn', id='dec'): error: Missing variable",
                "(model='test-dmn', id='dec'): error: Missing expression",
                "(model='test-dmn'): error: Missing id",
                "(model='test-dmn'): error: Missing name",
                "(model='test-dmn'): error: Missing variable",
                "(model='test-dmn'): error: Missing expression",
                "(model='test-dmn', id='bkm'): error: Missing name",
                "(model='test-dmn', id='bkm'): error: Missing variable",
                "(model='test-dmn', id='bkm'): error: Missing expression",
                "(model='test-dmn', id='bkm1'): error: Missing name",
                "(model='test-dmn', id='bkm1'): error: Missing variable name",
                "(model='test-dmn'): error: The href of a TDMNElementReference must be unique. Found duplicates for '125'.",
                "(model='test-dmn', id='bkm1'): error: Missing expression",
                "(model='test-dmn'): error: Missing id",
                "(model='test-dmn'): error: Missing name",
                "(model='test-dmn'): error: Missing variable",
                "(model='test-dmn', id='ds'): error: Missing name",
                "(model='test-dmn', id='ds'): error: Missing variable",
                "(model='test-dmn', id='ds1'): error: Missing name",
                "(model='test-dmn', id='ds1'): error: Missing variable name",
                "(model='test-dmn', name='CIP Assessments', id='cip-assessments'): error: Missing variable",
                "(model='test-dmn', name='dec1', id='dec1'): error: Missing variable name",
                "(model='test-dmn', name='dec1', id='dec1'): error: Missing typRef of variable",
                "(model='test-dmn', name='dec1', id='dec1'): error: Missing text of literal expression",
                "(model='test-dmn'): error: The variable type 'string' must be the same as the type of the contained expression 'number'"
        );
        validate(validator, resource("dmn/input/1.5/test-dmn.dmn"), expectedErrors);
    }

    @Test
    public void testValidateDefinitionsWhenNull() {
        List<String> actualErrors = validator.validate(null);
        assertTrue(actualErrors.isEmpty());
    }
}