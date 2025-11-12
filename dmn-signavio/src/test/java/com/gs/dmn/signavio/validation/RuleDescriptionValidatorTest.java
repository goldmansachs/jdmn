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

import com.gs.dmn.ast.TDecision;
import com.gs.dmn.error.SemanticError;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RuleDescriptionValidatorTest extends AbstractSignavioValidatorTest {
    private final RuleDescriptionValidator validator = new RuleDescriptionValidator();

    @Test
    public void testValidate() {
        String path = "dmn/complex/";
        String diagramName = "Linked Decision Test.dmn";

        List<String> expectedErrors = Arrays.asList(
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'assessApplicantAge', elementId = 'id-98f1b72e74edaaae8d7fd9043f7e1bc4'): Description of rule 0 in decision 'assessApplicantAge' contains illegal sequence 'string(-)'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'assessApplicantAge', elementId = 'id-98f1b72e74edaaae8d7fd9043f7e1bc4'): Description of rule 1 in decision 'assessApplicantAge' contains illegal sequence 'string(-)'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'assessApplicantAge', elementId = 'id-98f1b72e74edaaae8d7fd9043f7e1bc4'): Description of rule 2 in decision 'assessApplicantAge' contains illegal sequence 'string(-)'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'makeCreditDecision', elementId = 'id-5b83918d6fc820d73123e7ca0e6d3ca6'): Description of rule 0 in decision 'makeCreditDecision' contains illegal sequence 'string(-)'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'makeCreditDecision', elementId = 'id-5b83918d6fc820d73123e7ca0e6d3ca6'): Description of rule 1 in decision 'makeCreditDecision' contains illegal sequence 'string(-)'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'makeCreditDecision', elementId = 'id-5b83918d6fc820d73123e7ca0e6d3ca6'): Description of rule 2 in decision 'makeCreditDecision' contains illegal sequence 'string(-)'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'processPriorIssues', elementId = 'id-bdfc5bfa4ce80fd221463ee66b277220'): Description of rule 0 in decision 'processPriorIssues' contains illegal sequence 'string(-)'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'processPriorIssues', elementId = 'id-bdfc5bfa4ce80fd221463ee66b277220'): Description of rule 1 in decision 'processPriorIssues' contains illegal sequence 'string(-)'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'processPriorIssues', elementId = 'id-bdfc5bfa4ce80fd221463ee66b277220'): Description of rule 2 in decision 'processPriorIssues' contains illegal sequence 'string(-)'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'processPriorIssues', elementId = 'id-bdfc5bfa4ce80fd221463ee66b277220'): Description of rule 3 in decision 'processPriorIssues' contains illegal sequence 'string(-)'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'processPriorIssues', elementId = 'id-bdfc5bfa4ce80fd221463ee66b277220'): Description of rule 4 in decision 'processPriorIssues' contains illegal sequence 'string(-)'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'root', elementId = 'id-dd34e15633241b301d7c512a35c9493a'): Description of rule 0 in decision 'root' contains illegal sequence 'string(-)'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'root', elementId = 'id-dd34e15633241b301d7c512a35c9493a'): Description of rule 1 in decision 'root' contains illegal sequence 'string(-)'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'root', elementId = 'id-dd34e15633241b301d7c512a35c9493a'): Description of rule 2 in decision 'root' contains illegal sequence 'string(-)'",
                "[ERROR] (namespace = 'http://www.provider.com/dmn/1.1/diagram/b4fc99dd0b044cf1b31b6e60d01c50fa.xml', modelName = 'Linked Decision Test', modelId = 'id-d944b069f6a24d0788b33965ec1c2318', elementName = 'root', elementId = 'id-dd34e15633241b301d7c512a35c9493a'): Description of rule 3 in decision 'root' contains illegal sequence 'string(-)'"
        );
        validate(validator, signavioResource(path + diagramName), expectedErrors);
    }

    @Test
    public void testValidateIncorrectList() {
        String description = "[ , string(\"abc\" ,  , string(\"abc\") , ]";
        List<SemanticError> actualErrors = new ArrayList<>();
        validator.validate(null, makeDecision(), 0, description, actualErrors);

        List<String> expectedErrors = Arrays.asList(
                "[ERROR] (elementName = 'Test'): Description of rule 0 in decision 'Test' contains illegal sequence '[ ,'",
                "[ERROR] (elementName = 'Test'): Description of rule 0 in decision 'Test' contains illegal sequence ',  ,'",
                "[ERROR] (elementName = 'Test'): Description of rule 0 in decision 'Test' contains illegal sequence ', ]'"
        );
        checkErrors(expectedErrors, actualErrors);
    }

    @Test
    public void testValidateIncorrectStrings() {
        String description = "[ string(-) ]";
        List<SemanticError> actualErrors = new ArrayList<>();
        validator.validate(null, makeDecision(), 0, description, actualErrors);

        List<String> expectedErrors = Collections.singletonList(
                "[ERROR] (elementName = 'Test'): Description of rule 0 in decision 'Test' contains illegal sequence 'string(-)'"
        );
        checkErrors(expectedErrors, actualErrors);
    }

    @Test
    public void testValidateIncorrectCharacters() {
        String description = "[ string(\"\") , string(\"abc \u00A0 123\") , string(\"\") ]";
        List<SemanticError> actualErrors = new ArrayList<>();
        validator.validate(null, makeDecision(), 0, description, actualErrors);

        List<String> expectedErrors = Collections.singletonList(
                "[ERROR] (elementName = 'Test'): Description of rule 0 in decision 'Test' contains illegal sequence 'NO-BREAK SPACE'"
        );
        checkErrors(expectedErrors, actualErrors);
    }

    private TDecision makeDecision() {
        TDecision decision = new TDecision();
        decision.setName("Test");
        return decision;
    }
}