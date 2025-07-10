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

import com.gs.dmn.DMNModelRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class CompositeDMNValidatorTest extends AbstractValidatorTest {
    private final DMNValidator validator = new CompositeDMNValidator(
            Arrays.asList(new DefaultDMNValidator(), new NopDMNValidator())
    );

    @Test
    public void testValidateWhenCorrect() {
        List<String> expectedErrors = Collections.emptyList();
        validate(validator, tckResource("tck/1.2/cl3/0020-vacation-days/0020-vacation-days.dmn"), expectedErrors);
    }

    @Test
    public void testValidateEmptyRepo() {
        List<String> expectedErrors = Collections.emptyList();
        List<String> actualErrors = validator.validate(null);
        checkErrors(expectedErrors, actualErrors);

        actualErrors = validator.validate(new DMNModelRepository(new ArrayList<>()));
        checkErrors(expectedErrors, actualErrors);
    }
}