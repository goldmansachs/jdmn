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
        String path = "dmn2java/exported/complex/input/";
        String diagramName = "Linked Decision Test.dmn";

        List<String> expectedErrors = Arrays.asList(
                "Decision name and variable name should be the same. Found 'assessApplicantAge' and 'Assess applicant age'",
                "Decision name and variable name should be the same. Found 'assessApplicantAge2' and 'Assess applicant age'",
                "Decision name and variable name should be the same. Found 'assessIssue' and 'Assess issue'",
                "Not supported expression language 'http://www.omg.org/spec/FEEL/20140401' in decision 'assessIssue'",
                "Decision name and variable name should be the same. Found 'assessIssueRisk' and 'Assess issue risk'",
                "Decision name and variable name should be the same. Found 'compareAgainstLendingThreshold' and 'Compare against lending threshold'",
                "Decision name and variable name should be the same. Found 'generateOutputData' and 'Generate output data'",
                "Not supported expression language 'http://www.omg.org/spec/FEEL/20140401' in decision 'generateOutputData'",
                "Decision name and variable name should be the same. Found 'makeCreditDecision' and 'Make credit decision'",
                "Decision name and variable name should be the same. Found 'makeCreditDecision2' and 'Make credit decision'",
                "Decision name and variable name should be the same. Found 'makeCreditDecision3' and 'Make credit decision'",
                "Decision name and variable name should be the same. Found 'processPriorIssues' and 'Process prior issues'",
                "Decision name and variable name should be the same. Found 'root' and 'Root'"
        );
        validate(validator, path + diagramName, expectedErrors);
    }
}