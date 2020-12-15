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
package com.gs.dmn.signavio.validation;

import com.gs.dmn.DMNModelRepository;
import org.junit.Test;
import org.omg.spec.dmn._20191111.model.TDecision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RuleDescriptionValidatorTest extends AbstractSignavioValidatorTest {
    private final DMNModelRepository repository = new DMNModelRepository();
    private final RuleDescriptionValidator validator = new RuleDescriptionValidator();

    @Test
    public void testValidate() {
        String path = "dmn/complex/";
        String diagramName = "Linked Decision Test.dmn";

        List<String> expectedErrors = Arrays.asList(
                "(model='Linked Decision Test', label='Assess applicant age', name='assessApplicantAge', id='id-98f1b72e74edaaae8d7fd9043f7e1bc4'): error: Description of rule 0 in decision 'assessApplicantAge' contains illegal sequence 'string(-)'",
                "(model='Linked Decision Test', label='Assess applicant age', name='assessApplicantAge', id='id-98f1b72e74edaaae8d7fd9043f7e1bc4'): error: Description of rule 1 in decision 'assessApplicantAge' contains illegal sequence 'string(-)'",
                "(model='Linked Decision Test', label='Assess applicant age', name='assessApplicantAge', id='id-98f1b72e74edaaae8d7fd9043f7e1bc4'): error: Description of rule 2 in decision 'assessApplicantAge' contains illegal sequence 'string(-)'",
                "(model='Linked Decision Test', label='Make credit decision', name='makeCreditDecision', id='id-5b83918d6fc820d73123e7ca0e6d3ca6'): error: Description of rule 0 in decision 'makeCreditDecision' contains illegal sequence 'string(-)'",
                "(model='Linked Decision Test', label='Make credit decision', name='makeCreditDecision', id='id-5b83918d6fc820d73123e7ca0e6d3ca6'): error: Description of rule 1 in decision 'makeCreditDecision' contains illegal sequence 'string(-)'",
                "(model='Linked Decision Test', label='Make credit decision', name='makeCreditDecision', id='id-5b83918d6fc820d73123e7ca0e6d3ca6'): error: Description of rule 2 in decision 'makeCreditDecision' contains illegal sequence 'string(-)'",
                "(model='Linked Decision Test', label='Process prior issues', name='processPriorIssues', id='id-bdfc5bfa4ce80fd221463ee66b277220'): error: Description of rule 0 in decision 'processPriorIssues' contains illegal sequence 'string(-)'",
                "(model='Linked Decision Test', label='Process prior issues', name='processPriorIssues', id='id-bdfc5bfa4ce80fd221463ee66b277220'): error: Description of rule 1 in decision 'processPriorIssues' contains illegal sequence 'string(-)'",
                "(model='Linked Decision Test', label='Process prior issues', name='processPriorIssues', id='id-bdfc5bfa4ce80fd221463ee66b277220'): error: Description of rule 2 in decision 'processPriorIssues' contains illegal sequence 'string(-)'",
                "(model='Linked Decision Test', label='Process prior issues', name='processPriorIssues', id='id-bdfc5bfa4ce80fd221463ee66b277220'): error: Description of rule 3 in decision 'processPriorIssues' contains illegal sequence 'string(-)'",
                "(model='Linked Decision Test', label='Process prior issues', name='processPriorIssues', id='id-bdfc5bfa4ce80fd221463ee66b277220'): error: Description of rule 4 in decision 'processPriorIssues' contains illegal sequence 'string(-)'",
                "(model='Linked Decision Test', label='Root', name='root', id='id-dd34e15633241b301d7c512a35c9493a'): error: Description of rule 0 in decision 'root' contains illegal sequence 'string(-)'",
                "(model='Linked Decision Test', label='Root', name='root', id='id-dd34e15633241b301d7c512a35c9493a'): error: Description of rule 1 in decision 'root' contains illegal sequence 'string(-)'",
                "(model='Linked Decision Test', label='Root', name='root', id='id-dd34e15633241b301d7c512a35c9493a'): error: Description of rule 2 in decision 'root' contains illegal sequence 'string(-)'",
                "(model='Linked Decision Test', label='Root', name='root', id='id-dd34e15633241b301d7c512a35c9493a'): error: Description of rule 3 in decision 'root' contains illegal sequence 'string(-)'"
        );
        validate(validator, signavioResource(path + diagramName), expectedErrors);
    }

    @Test
    public void testValidateIncorrectList() {
        String description = "[ , string(\"abc\" ,  , string(\"abc\") , ]";
        List<String> actualErrors = new ArrayList<>();
        validator.validate(repository, null, makeDecision(), 0, description, actualErrors);

        List<String> expectedErrors = Arrays.asList(
                "(name='Test'): error: Description of rule 0 in decision 'Test' contains illegal sequence '[ ,'",
                "(name='Test'): error: Description of rule 0 in decision 'Test' contains illegal sequence ',  ,'",
                "(name='Test'): error: Description of rule 0 in decision 'Test' contains illegal sequence ', ]'"
        );
        assertEquals(expectedErrors, actualErrors);
    }

    @Test
    public void testValidateIncorrectStrings() {
        String description = "[ string(-) ]";
        List<String> actualErrors = new ArrayList<>();
        validator.validate(repository, null, makeDecision(), 0, description, actualErrors);

        List<String> expectedErrors = Arrays.asList(
                "(name='Test'): error: Description of rule 0 in decision 'Test' contains illegal sequence 'string(-)'"
        );
        assertEquals(expectedErrors, actualErrors);
    }

    @Test
    public void testValidateIncorrectCharacters() {
        String description = "[ string(\"\") , string(\"abc \u00A0 123\") , string(\"\") ]";
        List<String> actualErrors = new ArrayList<>();
        validator.validate(repository, null, makeDecision(), 0, description, actualErrors);

        List<String> expectedErrors = Arrays.asList(
                "(name='Test'): error: Description of rule 0 in decision 'Test' contains illegal sequence 'NO-BREAK SPACE'"
        );
        assertEquals(expectedErrors, actualErrors);
    }

    private TDecision makeDecision() {
        TDecision decision = new TDecision();
        decision.setName("Test");
        return decision;
    }
}