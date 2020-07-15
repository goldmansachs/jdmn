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

import com.gs.dmn.AbstractTest;
import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import org.omg.spec.dmn._20180521.model.TDefinitions;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractValidatorTest extends AbstractTest {
    private final DMNReader reader = new DMNReader(LOGGER, false);

    protected void validate(DMNValidator validator, String path, List<String> expectedErrors) {
        DMNModelRepository repository = makeRepository(path);
        List<String> actualErrors = validator.validate(repository);

        assertEquals(expectedErrors, actualErrors);
    }

    private DMNModelRepository makeRepository(String path) {
        File input = new File(resource(path));
        Pair<TDefinitions, PrefixNamespaceMappings> pair = reader.read(input);
        return new DMNModelRepository(pair);
    }
}