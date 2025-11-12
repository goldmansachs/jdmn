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
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SignavioDMNValidatorTest extends AbstractSignavioValidatorTest {
    private final DMNValidator validator = new SignavioDMNValidator();

    @Test
    public void testValidate() {
        String path = "dmn/complex/";
        String diagramName = "Linked Decision Test.dmn";

        List<String> expectedErrors = Arrays.asList(
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'assessApplicantAge', elementId = 'id-98f1b72e74edaaae8d7fd9043f7e1bc4'): DRGElement name and variable name should be the same. Found 'assessApplicantAge' and 'Assess applicant age'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'assessApplicantAge2', elementId = 'id-06eb38446e6385a69e74fcd503660971'): DRGElement name and variable name should be the same. Found 'assessApplicantAge2' and 'Assess applicant age'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'assessIssue', elementId = 'id-0f2f9823e96f0599d2739fda4c5b3c79'): DRGElement name and variable name should be the same. Found 'assessIssue' and 'Assess issue'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'assessIssueRisk', elementId = 'id-90d13f677a4e3f0f8230a12f15301f00'): DRGElement name and variable name should be the same. Found 'assessIssueRisk' and 'Assess issue risk'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'assessIssueRisk', elementId = 'id-90d13f677a4e3f0f8230a12f15301f00'): Missing expression",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'compareAgainstLendingThreshold', elementId = 'id-8369770df890b566296308a9deebec47'): DRGElement name and variable name should be the same. Found 'compareAgainstLendingThreshold' and 'Compare against lending threshold'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'generateOutputData', elementId = 'id-f3dfdd3ac42c255265e190eaf50dd65d'): DRGElement name and variable name should be the same. Found 'generateOutputData' and 'Generate output data'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'makeCreditDecision', elementId = 'id-5b83918d6fc820d73123e7ca0e6d3ca6'): DRGElement name and variable name should be the same. Found 'makeCreditDecision' and 'Make credit decision'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'makeCreditDecision2', elementId = 'id-53305251d2d6fb14173b439b019adeda'): DRGElement name and variable name should be the same. Found 'makeCreditDecision2' and 'Make credit decision'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'makeCreditDecision3', elementId = 'id-75d5270913befc4881b90708206b1e9e'): DRGElement name and variable name should be the same. Found 'makeCreditDecision3' and 'Make credit decision'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'processPriorIssues', elementId = 'id-bdfc5bfa4ce80fd221463ee66b277220'): DRGElement name and variable name should be the same. Found 'processPriorIssues' and 'Process prior issues'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'root', elementId = 'id-dd34e15633241b301d7c512a35c9493a'): DRGElement name and variable name should be the same. Found 'root' and 'Root'"
        );
        validate(validator, signavioResource(path + diagramName), expectedErrors);
    }
}