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

import com.gs.dmn.validation.AbstractValidatorTest;
import org.junit.Test;
import org.omg.spec.dmn._20180521.model.TDecision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RuleDescriptionValidatorTest extends AbstractValidatorTest {
    private final RuleDescriptionValidator validator = new RuleDescriptionValidator();

    @Test
    public void testValidateIncorrectList() {
        String description = "[ , string(\"abc\" ,  , string(\"abc\") , ]";
        List<String> actualErrors = new ArrayList<>();
        validator.validate(description, 0, makeDecision(), actualErrors);

        List<String> expectedErrors = Arrays.asList(
                "Description of rule 0 in decision 'Test' contains illegal sequence '[ ,'",
                "Description of rule 0 in decision 'Test' contains illegal sequence ',  ,'",
                "Description of rule 0 in decision 'Test' contains illegal sequence ', ]'"
        );
        assertEquals(expectedErrors, actualErrors);
    }

    @Test
    public void testValidateIncorrectStrings() {
        String description = "[ string(-) ]";
        List<String> actualErrors = new ArrayList<>();
        validator.validate(description, 0, makeDecision(), actualErrors);

        List<String> expectedErrors = Arrays.asList(
                "Description of rule 0 in decision 'Test' contains illegal sequence 'string(-)'"
        );
        assertEquals(expectedErrors, actualErrors);
    }

    @Test
    public void testValidateIncorrectCharacters() {
        String description = "[ string(\"\") , string(\"abc \u00A0 123\") , string(\"\") ]";
        List<String> actualErrors = new ArrayList<>();
        validator.validate(description, 0, makeDecision(), actualErrors);

        List<String> expectedErrors = Arrays.asList(
                "Description of rule 0 in decision 'Test' contains illegal sequence 'NO-BREAK SPACE'"
        );
        assertEquals(expectedErrors, actualErrors);
    }

    private TDecision makeDecision() {
        TDecision decision = new TDecision();
        decision.setName("Test");
        return decision;
    }
}