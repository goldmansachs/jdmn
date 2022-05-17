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
import com.gs.dmn.serialization.xstream.DMNMarshallerFactory;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

import java.io.*;

public abstract class AbstractXStreamUnmarshalMarshalTest extends AbstractUnmarshalMarshalTest<TDefinitions, DMNMarshaller> {
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
    protected Diff makeDMNDiff(File expectedOutputFile, File actualOutputFile) {
        return DiffBuilder
                .compare(Input.fromFile(expectedOutputFile))
                .withTest(Input.fromFile(actualOutputFile))
                .withDifferenceEvaluator(makeDMNDifferenceEvaluator())
                .checkForSimilar()
                .ignoreWhitespace()
                .ignoreComments()
                .build();
    }
}
