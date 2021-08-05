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
import com.gs.dmn.validation.table.RuleGroup;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

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
        assertEquals(expectedErrors, validator.validate(null));
        assertEquals(expectedErrors, validator.validate(new DMNModelRepository()));
    }

    @Test
    public void testValidateWhenIntervals1() {
        List<String> expectedErrors = Arrays.asList(
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[1, 3]' overlap in decision 'Loan Grade'"
        );
        validate(validator, resource("dmn/input/1.3/loan-grade-with-intervals-1.dmn"), expectedErrors);
    }

    @Test
    public void testValidateWhenIntervals2() {
        List<String> expectedErrors = Arrays.asList(
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[1, 3]' overlap in decision 'Loan Grade'"
        );
        validate(validator, resource("dmn/input/1.3/loan-grade-with-intervals-2.dmn"), expectedErrors);
    }

    @Test
    public void testValidateWhenIntervals3() {
        List<String> expectedErrors = Arrays.asList(
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[1, 3, 5]' overlap in decision 'Loan Grade'",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[3, 6]' overlap in decision 'Loan Grade'"
        );
        validate(validator, resource("dmn/input/1.3/loan-grade-with-intervals-3.dmn"), expectedErrors);
    }

    @Test
    public void testValidateWhenRelationalOperators() {
        List<String> expectedErrors = Arrays.asList(
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[1, 3]' overlap in decision 'Loan Grade'",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[3, 4]' overlap in decision 'Loan Grade'"
        );
        validate(validator, resource("dmn/input/1.3/loan-grade-with-relational-operators.dmn"), expectedErrors);
    }

    @Test
    public void testValidateWhenAny() {
        List<String> expectedErrors = Arrays.asList(
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[1, 3, 4]' overlap in decision 'Loan Grade'"
        );
        validate(validator, resource("dmn/input/1.3/loan-grade-with-any.dmn"), expectedErrors);
    }

    @Test
    public void testValidateWhenBoolean() {
        List<String> expectedErrors = Arrays.asList(
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[1, 3]' overlap in decision 'Loan Grade'",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[2, 4]' overlap in decision 'Loan Grade'",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[3, 4]' overlap in decision 'Loan Grade'"
        );
        validate(validator, resource("dmn/input/1.3/loan-grade-with-boolean.dmn"), expectedErrors);
    }

    @Test
    public void testValidateWhenEnumeration() {
        List<String> expectedErrors = Arrays.asList(
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[1, 3]' overlap in decision 'Loan Grade'",
                "(model='loan-grade', name='Loan Grade', id='_FAF682B2-D00A-469A-8B7D-932154DA95E0'): error: Decision table rules '[2, 3, 4]' overlap in decision 'Loan Grade'"
        );
        validate(validator, resource("dmn/input/1.3/loan-grade-with-enumeration.dmn"), expectedErrors);
    }

    @Test
    public void testValidateWhenEnumerationMissing() {
        List<String> expectedErrors = Arrays.asList(
        );
        validate(validator, resource("dmn/input/1.3/loan-grade-with-enumeration-missing.dmn"), expectedErrors);
    }

    @Test
    public void testMaxCliquesBronKerbosch() {
        // https://en.wikipedia.org/wiki/Bron%E2%80%93Kerbosch_algorithm
        RuleGroup nodes = new RuleGroup(Arrays.asList(1, 2, 3, 4, 5, 6));
        Map<Integer, Set<Integer>> neighbor = new LinkedHashMap<>();
        neighbor.put(1, new HashSet<>(Arrays.asList(2, 5)));
        neighbor.put(2, new HashSet<>(Arrays.asList(1, 3, 5)));
        neighbor.put(3, new HashSet<>(Arrays.asList(2, 4)));
        neighbor.put(4, new HashSet<>(Arrays.asList(3, 5, 6)));
        neighbor.put(5, new HashSet<>(Arrays.asList(1, 2, 4)));
        neighbor.put(6, new HashSet<>(Arrays.asList(4)));
        List<RuleGroup> maxCliques = new ArrayList<>();
        this.validator.maxCliquesBronKerbosch(new RuleGroup(), nodes, new RuleGroup(), neighbor, maxCliques);

        List<RuleGroup> expectedGroups = new ArrayList<>();
        expectedGroups.add(new RuleGroup(Arrays.asList(2, 3)));
        expectedGroups.add(new RuleGroup(Arrays.asList(1, 2 ,5)));
        expectedGroups.add(new RuleGroup(Arrays.asList(3, 4)));
        expectedGroups.add(new RuleGroup(Arrays.asList(4, 5)));
        expectedGroups.add(new RuleGroup(Arrays.asList(4, 6)));
        assertEquals(expectedGroups, maxCliques);
    }
}