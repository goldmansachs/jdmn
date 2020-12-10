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
package com.gs.dmn.signavio.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNNamespacePrefixMapper;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.DMNWriter;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.AbstractFileTransformerTest;
import com.gs.dmn.transformation.DMNTransformer;
import org.junit.Test;
import org.omg.spec.dmn._20191111.model.TDefinitions;

import java.io.File;

public class SimplifyTypesForMIDTransformerTest extends AbstractFileTransformerTest {
    private final DMNTransformer<TestLab> transformer = new SimplifyTypesForMIDTransformer(LOGGER);
    private final DMNReader dmnReader = new DMNReader(LOGGER, false);
    private final DMNWriter dmnWriter = new DMNWriter(LOGGER);

    @Test
    public void testTransform() throws Exception {
        String path = "dmn/dmn2java/expected/complex/";

        // Transform DMN
        File dmnFile = new File(signavioResource(path + "IteratorExampleReturningMultiple.dmn"));
        Pair<TDefinitions, PrefixNamespaceMappings> pair = dmnReader.read(dmnFile);
        DMNModelRepository repository = new SignavioDMNModelRepository(pair, "http://www.provider.com/schema/dmn/1.1/");
        DMNModelRepository actualRepository = transformer.transform(repository);

        // Check output
        checkDefinitions(actualRepository, "IteratorExampleReturningMultiple.dmn");
    }

    private void checkDefinitions(DMNModelRepository repository, String fileName) throws Exception {
        File actualDMNFile = new File("target/" + fileName);
        TDefinitions actualDefinitions = repository.getRootDefinitions();
        dmnWriter.write(actualDefinitions, actualDMNFile, new DMNNamespacePrefixMapper(actualDefinitions.getNamespace(), "sig"));

        String path = "dmn/dmn2java/expected/complex/";
        File expectedDMNFile = new File(signavioResource(path + fileName));

        compareFile(expectedDMNFile, actualDMNFile);
    }
}