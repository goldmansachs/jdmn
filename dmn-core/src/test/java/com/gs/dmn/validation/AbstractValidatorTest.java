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

import com.gs.dmn.AbstractTest;
import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.serialization.xstream.XMLDMNSerializer;

import java.io.File;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractValidatorTest extends AbstractTest {
    protected final DMNSerializer serializer = new XMLDMNSerializer(LOGGER, this.inputParameters);

    protected void validate(DMNValidator validator, URI fileURI, List<String> expectedErrors) {
        DMNModelRepository repository = makeRepository(fileURI);
        List<SemanticError> actualErrors = validator.validate(repository);

        checkErrors(expectedErrors, actualErrors);
    }

    protected DMNModelRepository makeRepository(URI fileURI) {
        File input = new File(fileURI);
        List<TDefinitions> definitionsList = this.serializer.readModels(input);
        return new DMNModelRepository(definitionsList);
    }

    protected void checkErrors(List<String> expectedErrors, List<SemanticError> actualErrors) {
        assertEquals(expectedErrors.size(), actualErrors.size());
        for (int i = 0; i < actualErrors.size(); i++) {
            assertEquals(expectedErrors.get(i), actualErrors.get(i).toText(), "Failed at index " + i);
        }
    }

    protected void checkXSDErrors(List<String> expectedErrors, List<String> actualErrors) {
        assertEquals(expectedErrors.size(), actualErrors.size());
        for (int i = 0; i < actualErrors.size(); i++) {
            assertEquals(expectedErrors.get(i), actualErrors.get(i), "Failed at index " + i);
        }
    }
}