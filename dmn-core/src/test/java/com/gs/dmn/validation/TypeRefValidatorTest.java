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

import com.gs.dmn.DMNModelRepository;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TypeRefValidatorTest extends AbstractValidatorTest {
    private final TypeRefValidator validator = new TypeRefValidator();

    @Test
    public void validate() {
        File input = new File(DefaultDMNValidatorTest.class.getClassLoader().getResource("dmn/input/test-dmn-with-missing-type-ref.dmn").getFile());
        DMNModelRepository repository = makeRepository(input);
        List<String> actualErrors = validator.validate(repository);

        List<String> expectedErrors = Arrays.asList(
           "Cannot find type 'QualifiedName(sig, applicant)' for DRGElement 'applicant'"
        );
        assertEquals(expectedErrors, actualErrors);
    }
}