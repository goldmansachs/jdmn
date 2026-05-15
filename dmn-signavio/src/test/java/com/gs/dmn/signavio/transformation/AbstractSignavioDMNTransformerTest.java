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
import com.gs.dmn.signavio.AbstractSignavioFileTransformerTest;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.SignavioTestConstants;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.DMNTransformer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public abstract class AbstractSignavioDMNTransformerTest extends AbstractSignavioFileTransformerTest {
    @Test
    public void testTransformationWhenEmptyRepo() {
        DMNTransformer<TestLab> transformer = getTransformer();
        // Test model transformation
        assertNull(transformer.transform(null));
        // Test model and test cases transformation
        Pair<DMNModelRepository, List<TestLab>> res = transformer.transform(null, null);
        assertNull(res.getLeft());
        assertNull(res.getRight());
    }

    @Test
    public void testTransformationWhenEmptyConfig() {
        DMNTransformer<TestLab> transformer = getTransformer();
        // Test model transformation
        DMNModelRepository repository = new DMNModelRepository();
        assertEquals(repository, transformer.transform(repository));
        // Test model and test cases transformation
        List<TestLab> testCasesList = null;
        Pair<DMNModelRepository, List<TestLab>> res = transformer.transform(repository, testCasesList);
        assertEquals(repository, res.getLeft());
        assertEquals(testCasesList, res.getRight());
    }

    protected DMNModelRepository executeDMNTransformation(DMNTransformer<TestLab> transformer, URI dmnFileURI) {
        DMNModelRepository repository = readModel(dmnFileURI);
        return transformer.transform(repository);
    }

    protected DMNModelRepository readModel(URI dmnFileURI) {
        File dmnFile = new File(dmnFileURI);
        return new SignavioDMNModelRepository(this.dmnSerializer.readModel(dmnFile), SignavioTestConstants.SIG_EXT_NAMESPACE);
    }

    protected abstract DMNTransformer<TestLab> getTransformer();
}
