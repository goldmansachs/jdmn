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
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SweepMissingRuleValidatorWithMergeTest extends AbstractValidatorTest {
    private final SweepMissingRuleValidator validator = new SweepMissingRuleValidator(new StandardDMNDialectDefinition(), true);

    @Test
    public void testValidateWhenCorrect() {
        List<String> expectedErrors = Collections.emptyList();
        validate(validator, tckResource("tck/1.2/cl3/0020-vacation-days/0020-vacation-days.dmn"), expectedErrors);
    }

    @Test
    public void testValidateWhenRepositoryIsEmpty() {
        List<String> expectedErrors = Collections.emptyList();
        assertEquals(expectedErrors, validator.validate(null));
        assertEquals(expectedErrors, validator.validate(new DMNModelRepository()));
    }

    @Test
    public void testValidateWhenIntervals1() {
        List<String> expectedErrors = Arrays.asList(
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(0, 250), (1000, 5000]]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(250, 500), (1000, 4000)]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(500, 750), (3000, 4000)]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(750, 1000), (3000, 5000]]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(1000, 1500), [0, 500)]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(1000, 1500), (3000, 5000]]' in 'Loan Grade' table",
//                "[(1500, 2000), (0, 5000)]",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(2000, 2500), (2000, 5000]]' in 'Loan Grade' table"
        );
        validate(validator, resource("dmn/input/1.3/loan-grade-with-intervals-1.dmn"), expectedErrors);
    }

    @Test
    public void testValidateWhenIntervals2() {
        List<String> expectedErrors = Arrays.asList(
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(0, 250), (750, 1500]]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(250, 500), (750, 1200)]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(500, 750), (1000, 1200)]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(750, 1000), (1000, 1500]]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(1000, 1500), [0, 250)]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(1000, 1500), (1000, 1500]]' in 'Loan Grade' table",
//                "[(1500, 1600), (0, 1500)]",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(1600, 2000), (850, 1500]]' in 'Loan Grade' table"
        );
        validate(validator, resource("dmn/input/1.3/loan-grade-with-intervals-2.dmn"), expectedErrors);
    }

    @Test
    public void testValidateWhenRelationalOperators() {
        List<String> expectedErrors = Arrays.asList(
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(-Infinity, 200), (1000, 4000)]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(200, 500], (1000, 4000)]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[[500, 750), (3000, 4000)]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(750, 2000), (3000, 5000]]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[[1000, 2000), [0, 500)]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(2000, +Infinity), (3000, 5000]]' in 'Loan Grade' table"
       );
        validate(validator, resource("dmn/input/1.3/loan-grade-with-relational-operators.dmn"), expectedErrors);
    }

    @Test
    public void testValidateWhenAny() {
        List<String> expectedErrors = Arrays.asList(
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(-Infinity, 250), (3000, 5000]]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(250, 750), (3000, 4000)]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(750, 2000), (3000, 5000]]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(2000, 2500), (3000, 5000]]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[(2500, +Infinity), (3000, 5000]]' in 'Loan Grade' table"
        );
        validate(validator, resource("dmn/input/1.3/loan-grade-with-any.dmn"), expectedErrors);
    }

    @Test
    public void testValidateWhenBoolean() {
        List<String> expectedErrors = Collections.emptyList();
        validate(validator, resource("dmn/input/1.3/loan-grade-with-boolean.dmn"), expectedErrors);
    }

    @Test
    public void testValidateWhenEnumeration() {
        List<String> expectedErrors = Collections.singletonList(
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[{\"E11\"}, {\"E22\", \"E23\"}, {\"E32\", \"E33\"}]' in 'Loan Grade' table"
        );
        validate(validator, resource("dmn/input/1.3/loan-grade-with-enumeration.dmn"), expectedErrors);
    }

    @Test
    public void testValidateWhenEnumerationMissing() {
        List<String> expectedErrors = Arrays.asList(
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[{\"E11\", \"E12\", \"E13\"}, {\"E21\"}, {\"E31\"}]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[{\"E11\"}, {\"E22\"}, {\"E32\", \"E33\"}]' in 'Loan Grade' table",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Found missing rule '[{\"E12\"}, {\"E22\"}, {\"E31\", \"E32\"}]' in 'Loan Grade' table"
        );
        validate(validator, resource("dmn/input/1.3/loan-grade-with-enumeration-missing.dmn"), expectedErrors);
    }
}