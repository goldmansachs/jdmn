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
package com.gs.dmn.serialization;

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.serialization.jackson.DMNMarshallerFactory;

import javax.xml.transform.stream.StreamSource;
import java.io.*;

public abstract class AbstractJacksonUnmarshalMarshalTest extends AbstractUnmarshalMarshalTest<TDefinitions, DMNMarshaller> {
    @Override
    protected DMNMarshaller getMarshaller() {
        return DMNMarshallerFactory.newDefaultMarshaller();
    }

    @Override
    protected TDefinitions readModel(DMNMarshaller marshaller, FileInputStream fis) {
        return marshaller.unmarshal(new InputStreamReader(fis), true);
    }

    @Override
    protected void writeModel(DMNMarshaller marshaller, TDefinitions definitions, File outputDMNFile) throws IOException {
        LOGGER.debug(marshaller.marshal(definitions));

        try (FileWriter targetFos = new FileWriter(outputDMNFile)) {
            marshaller.marshal(definitions, targetFos);
        }
    }

    @Override
    protected StreamSource getSchemaSource() {
        return null;
    }

    @Override
    protected void validateXSDSchema(File inputDMNFile) {
    }
}
