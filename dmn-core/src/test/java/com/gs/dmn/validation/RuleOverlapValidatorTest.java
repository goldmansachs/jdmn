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
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class RuleOverlapValidatorTest extends AbstractValidatorTest {
    private final RuleOverlapValidator validator = new RuleOverlapValidator(new StandardDMNDialectDefinition());

    @Test
    public void testValidateWhenCorrect() {
        List<String> expectedErrors = Arrays.asList();
        validate(validator, tckResource("tck/1.2/cl3/0020-vacation-days/0020-vacation-days.dmn"), expectedErrors);
    }

    @Test
    public void testValidateWhenRepositoryIsEmpty() {
        List<String> expectedErrors = Arrays.asList();
        assertEquals(Arrays.asList(), validator.validate(null));
        assertEquals(Arrays.asList(), validator.validate(new DMNModelRepository()));
    }

    @Test
    public void testValidateWhenIntervals() {
        List<String> expectedErrors = Arrays.asList(
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[1, 3]' overlap in decision 'Loan Grade'"
        );
        validate(validator, resource("dmn/input/1.3/loan-grade-with-intervals.dmn"), expectedErrors);
    }

    @Test
    public void testValidateWhenRelationalOperators() {
        List<String> expectedErrors = Arrays.asList(
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[1, 3]' overlap in decision 'Loan Grade'",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[4, 3]' overlap in decision 'Loan Grade'"
        );
        validate(validator, resource("dmn/input/1.3/loan-grade-with-relational-operators.dmn"), expectedErrors);
    }

    @Test
    public void testValidateWhenAny() {
        List<String> expectedErrors = Arrays.asList(
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[1, 3]' overlap in decision 'Loan Grade'",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[1, 4, 3]' overlap in decision 'Loan Grade'",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[4, 3]' overlap in decision 'Loan Grade'"
        );
        validate(validator, resource("dmn/input/1.3/loan-grade-with-any.dmn"), expectedErrors);
    }
}