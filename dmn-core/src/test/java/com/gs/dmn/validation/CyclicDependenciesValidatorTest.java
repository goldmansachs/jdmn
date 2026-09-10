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

import java.util.Collections;
import java.util.List;

class CyclicDependenciesValidatorTest extends AbstractValidatorTest {
    private final CyclicDependenciesValidator validator = new CyclicDependenciesValidator();

    @Test
    public void testWhenNoCycles() {
        List<String> expectedErrors = Collections.emptyList();
        validateCompositeValidator(validator, tckResource("tck/1.2/cl3/0020-vacation-days/0020-vacation-days.dmn"), expectedErrors);
    }

    @Test
    public void testWhenImportCycles() {
        List<String> expectedErrors = List.of(
                "[cyclic-imports-validator] [ERROR] Cyclic import detected: model-a --> model-b --> model-a",
                "[cyclic-item-definitions-validator] [ERROR] Cyclic item definition detected: nsa#t1 -> nsa#t2 -> nsb#other -> nsa#t1"
        );
        validateCompositeValidator(validator, tckResource("other/1.5/cycles-no-prefix/translator/"), expectedErrors);
    }

    @Test
    public void testWhenItemDefinitionsCycle() {
        List<String> expectedErrors = List.of(
                "[cyclic-imports-validator] [ERROR] Cyclic import detected: item-definition-cycle-model-a --> item-definition-cycle-model-b --> item-definition-cycle-model-a",
                "[cyclic-item-definitions-validator] [ERROR] Cyclic item definition detected: http://www.example.com/definitions/item-definition-cycle#TypeA1 -> http://www.example.com/definitions/item-definition-cycle#TypeB1 -> http://www.example.com/definitions/item-definition-cycle#TypeC1 -> http://www.example.com/definitions/item-definition-cycle#TypeA1",
                "[cyclic-item-definitions-validator] [ERROR] Cyclic item definition detected: http://www.example.com/definitions/item-definition-cycle#boolean -> http://www.example.com/definitions/item-definition-cycle#string -> http://www.example.com/definitions/item-definition-cycle#number -> http://www.example.com/definitions/item-definition-cycle#boolean",
                "[cyclic-item-definitions-validator] [ERROR] Cyclic item definition detected: http://www.example.com/definitions/item-definition-cycle-model-a#TypeA -> http://www.example.com/definitions/item-definition-cycle-model-b#TypeB -> http://www.example.com/definitions/item-definition-cycle-model-b#TypeC -> http://www.example.com/definitions/item-definition-cycle-model-a#TypeA"
        );
        validateCompositeValidator(validator, tckResource("other/1.5/cycles-item-definitions/translator"), expectedErrors);
    }

    @Test
    public void testWhenInformationRequirementsCycles() {
        List<String> expectedErrors = List.of(
                "[cyclic-imports-validator] [ERROR] Cyclic import detected: decision-a --> decision-b --> decision-a",
                "[cyclic-information-requirements-validator] [ERROR] Cyclic information requirement detected: decision-a#DecisionA --> decision-b#DecisionB --> decision-b#DecisionC --> decision-a#DecisionA"
        );
        validateCompositeValidator(validator, tckResource("other/1.5/cyclic-decisions-requirements/"), expectedErrors);
    }

    @Test
    public void testWhenKnowledgeRequirementCycles() {
        List<String> expectedErrors = List.of(
                "[cyclic-imports-validator] [ERROR] Cyclic import detected: bkm-a --> bkm-b --> bkm-a",
                "[cyclic-knowledge-requirements-validator] [ERROR] Cyclic knowledge requirement detected: bkm-a#BKMA --> bkm-b#BKMB --> bkm-b#BKMC --> bkm-a#BKMA"
        );
        validateCompositeValidator(validator, tckResource("other/1.5/cyclic-knowledge-requirements/"), expectedErrors);
    }

}