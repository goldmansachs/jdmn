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
import com.gs.dmn.ast.TDecision;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.error.NopErrorHandler;
import com.gs.dmn.log.NopBuildLogger;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.DMNTransformer;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CorrectLabelTransformerTest extends AbstractSignavioDMNTransformerTest {
    private final CorrectLabelVisitor visitor = new CorrectLabelVisitor(new NopBuildLogger(), new NopErrorHandler());
    private final CorrectLabelTransformer transformer = new CorrectLabelTransformer(LOGGER);

    @Test
    public void testTransformWithOneAnnotation() throws Exception {
        String path = "dmn/input/1.1/";

        // Transform DMN
        URI dmnFileURI = resource(path + "Null Safe Tests.dmn");
        DMNModelRepository actualRepository = executeDMNTransformation(transformer, dmnFileURI);

        // Check output
        TDefinitions definitions = getDefinitions(actualRepository);
        checkDefinitions(definitions, "null-safe-tests-labels.dmn");
    }

    @Test
    public void testTransformForLabelIsEmpty() {
        assertNull(visitor.transformLabel(null));
        assertEquals("abc", visitor.transformLabel(" abc \n\r\t\f "));
    }

    @Test
    public void testTransformForIllegalString() {
        TDecision element = makeDecision(" abc \n ");
        visitor.visit(element, null);

        assertEquals("abc", element.getLabel());
    }

    @Test
    public void testStrip() {
        CorrectLabelVisitor visitor = new CorrectLabelVisitor(new NopBuildLogger(), new NopErrorHandler());
        assertNull(visitor.transformLabel(null));
        assertEquals("", visitor.transformLabel("  \n \t"));
        assertEquals("abc", visitor.transformLabel(" abc"));
        assertEquals("_abc", visitor.transformLabel(" _abc"));
        assertEquals("-abc", visitor.transformLabel(" -abc"));
        assertEquals("9abc-", visitor.transformLabel("9abc- "));
        assertEquals("hello world !", visitor.transformLabel("hello world !"));
        assertEquals("café Straße", visitor.transformLabel("café Straße"));
    }

    @Test
    public void testCamelCase() {
        CorrectLabelVisitor visitor = new CorrectLabelVisitor(CorrectLabelTransformer.TransformationKind.CAMEL_CASE, new NopBuildLogger(), new NopErrorHandler());
        assertNull(visitor.transformLabel(null));
        assertEquals("", visitor.transformLabel("  \n \t"));
        assertEquals("Abc", visitor.transformLabel(" abc"));
        assertEquals("_abc", visitor.transformLabel(" _abc"));
        assertEquals("Abc", visitor.transformLabel(" -abc"));
        assertEquals("_9abc", visitor.transformLabel("9abc- "));
        assertEquals("HelloWorld", visitor.transformLabel("hello world !"));
        assertEquals("CaféStraße", visitor.transformLabel("café Straße"));
    }

    @Test
    public void testRemoveCharacters() {
        CorrectLabelVisitor visitor = new CorrectLabelVisitor(CorrectLabelTransformer.TransformationKind.REMOVE_ILLEGAL_CHARACTERS, new NopBuildLogger(), new NopErrorHandler());
        assertNull(visitor.transformLabel(null));
        assertEquals("", visitor.transformLabel("  \n \t"));
        assertEquals("abc", visitor.transformLabel(" abc"));
        assertEquals("_abc", visitor.transformLabel(" _abc"));
        assertEquals("-abc", visitor.transformLabel(" -abc"));
        assertEquals("9abc-", visitor.transformLabel("9abc- "));
        assertEquals("hello world", visitor.transformLabel("hello world !"));
        assertEquals("café Straße", visitor.transformLabel("café Straße"));
    }

    private TDecision makeDecision(String label) {
        TDecision element = new TDecision();
        element.setLabel(label);
        return element;
    }

    @Override
    protected DMNTransformer<TestLab> getTransformer() {
        return transformer;
    }
}