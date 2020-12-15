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

import com.gs.dmn.validation.DMNValidator;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SignavioDMNValidatorTest extends AbstractSignavioValidatorTest {
    private final DMNValidator validator = new SignavioDMNValidator();

    @Test
    public void testValidate() {
        String path = "dmn/complex/";
        String diagramName = "Linked Decision Test.dmn";

        List<String> expectedErrors = Arrays.asList(
                "(model='Linked Decision Test', label='Assess applicant age', name='assessApplicantAge', id='id-98f1b72e74edaaae8d7fd9043f7e1bc4'): error: Decision name and variable name should be the same. Found 'assessApplicantAge' and 'Assess applicant age'",
                "(model='Linked Decision Test', label='Assess applicant age', name='assessApplicantAge2', id='id-06eb38446e6385a69e74fcd503660971'): error: Decision name and variable name should be the same. Found 'assessApplicantAge2' and 'Assess applicant age'",
                "(model='Linked Decision Test', label='Assess issue', name='assessIssue', id='id-0f2f9823e96f0599d2739fda4c5b3c79'): error: Decision name and variable name should be the same. Found 'assessIssue' and 'Assess issue'",
                "(model='Linked Decision Test', label='Assess issue', name='assessIssue', id='id-0f2f9823e96f0599d2739fda4c5b3c79'): error: Not supported expression language 'http://www.omg.org/spec/FEEL/20140401'",
                "(model='Linked Decision Test', label='Assess issue risk', name='assessIssueRisk', id='id-90d13f677a4e3f0f8230a12f15301f00'): error: Decision name and variable name should be the same. Found 'assessIssueRisk' and 'Assess issue risk'",
                "(model='Linked Decision Test', label='Compare against lending threshold', name='compareAgainstLendingThreshold', id='id-8369770df890b566296308a9deebec47'): error: Decision name and variable name should be the same. Found 'compareAgainstLendingThreshold' and 'Compare against lending threshold'",
                "(model='Linked Decision Test', label='Generate output data', name='generateOutputData', id='id-f3dfdd3ac42c255265e190eaf50dd65d'): error: Decision name and variable name should be the same. Found 'generateOutputData' and 'Generate output data'",
                "(model='Linked Decision Test', label='Generate output data', name='generateOutputData', id='id-f3dfdd3ac42c255265e190eaf50dd65d'): error: Not supported expression language 'http://www.omg.org/spec/FEEL/20140401'",
                "(model='Linked Decision Test', label='Make credit decision', name='makeCreditDecision', id='id-5b83918d6fc820d73123e7ca0e6d3ca6'): error: Decision name and variable name should be the same. Found 'makeCreditDecision' and 'Make credit decision'",
                "(model='Linked Decision Test', label='Make credit decision', name='makeCreditDecision2', id='id-53305251d2d6fb14173b439b019adeda'): error: Decision name and variable name should be the same. Found 'makeCreditDecision2' and 'Make credit decision'",
                "(model='Linked Decision Test', label='Make credit decision', name='makeCreditDecision3', id='id-75d5270913befc4881b90708206b1e9e'): error: Decision name and variable name should be the same. Found 'makeCreditDecision3' and 'Make credit decision'",
                "(model='Linked Decision Test', label='Process prior issues', name='processPriorIssues', id='id-bdfc5bfa4ce80fd221463ee66b277220'): error: Decision name and variable name should be the same. Found 'processPriorIssues' and 'Process prior issues'",
                "(model='Linked Decision Test', label='Root', name='root', id='id-dd34e15633241b301d7c512a35c9493a'): error: Decision name and variable name should be the same. Found 'root' and 'Root'"
        );
        validate(validator, signavioResource(path + diagramName), expectedErrors);
    }
}