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

class CyclicItemDefinitionsValidatorTest extends AbstractValidatorTest {
    private final CyclicItemDefinitionsValidator validator = new CyclicItemDefinitionsValidator();

    @Test
    public void testWhenNoCycles() {
        List<String> expectedErrors = Collections.emptyList();
        validateSimpleValidator(validator, resource("dmn/input/1.3/0004-lending.dmn"), expectedErrors);
    }

    @Test
    public void testWhenCycle() {
        List<String> expectedErrors = List.of(
                "[ERROR] Cyclic item definition detected: http://www.example.com/definitions/item-definition-cycle#TypeA1 -> http://www.example.com/definitions/item-definition-cycle#TypeB1 -> http://www.example.com/definitions/item-definition-cycle#TypeC1 -> http://www.example.com/definitions/item-definition-cycle#TypeA1",
                "[ERROR] Cyclic item definition detected: http://www.example.com/definitions/item-definition-cycle#boolean -> http://www.example.com/definitions/item-definition-cycle#string -> http://www.example.com/definitions/item-definition-cycle#number -> http://www.example.com/definitions/item-definition-cycle#boolean",
                "[ERROR] Cyclic item definition detected: http://www.example.com/definitions/item-definition-cycle-model-a#TypeA -> http://www.example.com/definitions/item-definition-cycle-model-b#TypeB -> http://www.example.com/definitions/item-definition-cycle-model-b#TypeC -> http://www.example.com/definitions/item-definition-cycle-model-a#TypeA"
        );
        validateSimpleValidator(validator, tckResource("other/1.5/cycles-item-definitions/translator"), expectedErrors);
    }
}
