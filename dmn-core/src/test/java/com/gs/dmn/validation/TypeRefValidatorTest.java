/*
 Copyright 2016.
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
   http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
*/
package com.gs.dmn.validation;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TypeRefValidatorTest extends AbstractValidatorTest {
    private final TypeRefValidator validator = new TypeRefValidator();

    @Test
    public void testValidateWhenCorrect() {
        List<String> expectedErrors = Arrays.asList();
        validate(validator, "tck/cl3/input/0020-vacation-days.dmn", expectedErrors);
    }

    @Test
    public void validate() {
        List<String> expectedErrors = Arrays.asList(
           "Error:(label='Applicant', name='applicant', id='id-d2376567fde3c9400ee327ecec21e36d') Cannot find typeRef 'QualifiedName(null, applicant)'"
        );
        validate(validator, "dmn/input/test-dmn-with-missing-type-ref.dmn", expectedErrors);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateDefinitionsWhenNull() {
        validator.validate(null);
    }
}