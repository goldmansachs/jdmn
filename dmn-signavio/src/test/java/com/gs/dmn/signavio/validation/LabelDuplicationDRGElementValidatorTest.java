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

public class LabelDuplicationDRGElementValidatorTest extends AbstractSignavioValidatorTest {
    private final DMNValidator validator = new LabelDuplicationDRGElementValidator();

    @Test
    public void testValidate() {
        String path = "dmn/complex/";
        String diagramName = "Linked Decision Test.dmn";

        List<String> expectedErrors = Arrays.asList(
                "Found 2 Decision with duplicated label 'Assess applicant age'",
                "Label = 'Assess applicant age' Id = 'id-98f1b72e74edaaae8d7fd9043f7e1bc4' kind = 'TDecision'",
                "Label = 'Assess applicant age' Id = 'id-06eb38446e6385a69e74fcd503660971' kind = 'TDecision'",
                "Found 3 Decision with duplicated label 'Make credit decision'",
                "Label = 'Make credit decision' Id = 'id-5b83918d6fc820d73123e7ca0e6d3ca6' kind = 'TDecision'",
                "Label = 'Make credit decision' Id = 'id-53305251d2d6fb14173b439b019adeda' kind = 'TDecision'",
                "Label = 'Make credit decision' Id = 'id-75d5270913befc4881b90708206b1e9e' kind = 'TDecision'"
        );
        validate(validator, signavioResource(path + diagramName), expectedErrors);
    }
}