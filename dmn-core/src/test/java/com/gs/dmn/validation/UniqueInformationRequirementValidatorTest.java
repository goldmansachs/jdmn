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

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class UniqueInformationRequirementValidatorTest extends AbstractValidatorTest {
    private final UniqueInformationRequirementValidator validator = new UniqueInformationRequirementValidator();

    @Test
    public void testValidateWhenCorrect() {
        validate(validator, tckResource("tck/1.2/cl3/0020-vacation-days/0020-vacation-days.dmn"), new ArrayList<>());
    }

    @Test
    public void testValidateDefinitionsWhenNotUniqueNames() {
        List<String> expectedErrors = Arrays.asList(
                "(model='test-dmn', name='CIP Assessments', id='cip-assessments'): error: Duplicated informationRequirement.requiredInput 'input-1'",
                "(model='test-dmn', name='CIP Assessments', id='cip-assessments'): error: Duplicated informationRequirement.requiredDecision 'decision-1'"
        );
        validate(validator, resource("dmn/input/1.1/test-dmn-with-duplicated-information-requirements.dmn"), expectedErrors);
    }

    @Test
    public void testValidateDefinitionsWhenNull() {
        List<String> actualErrors = validator.validate(null);
        assertTrue(actualErrors.isEmpty());
    }
}