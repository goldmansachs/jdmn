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
package com.gs.dmn.serialization.xstream.v1_2;

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.dmndi.DMNDiagram;
import com.gs.dmn.ast.dmndi.DMNShape;
import com.gs.dmn.ast.dmndi.DMNStyle;
import com.gs.dmn.serialization.AbstractXStreamUnmarshalMarshalTest;
import com.gs.dmn.serialization.DMNMarshaller;
import com.gs.dmn.serialization.diff.XMLDifferenceEvaluator;
import com.gs.dmn.serialization.xstream.DMNMarshallerFactory;
import com.gs.dmn.serialization.xstream.extensions.MyTestRegister;
import org.junit.jupiter.api.Test;
import org.xmlunit.diff.DifferenceEvaluator;
import org.xmlunit.diff.DifferenceEvaluators;

import javax.xml.transform.stream.StreamSource;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UnmarshalMarshalTest extends AbstractXStreamUnmarshalMarshalTest {
    @Test
    public void testSimple() throws Exception {
        testRoundTrip("xstream/v1_2/simple.dmn");
    }

    @Test
    public void testCh11Example() throws Exception {
        testRoundTrip("xstream/v1_2/ch11example.dmn");
    }

    @Test
    public void testImportName() throws Exception {
        testRoundTrip("xstream/v1_2/ImportName.dmn");
    }

    @Test
    public void testDecisionService20180911v12() throws Exception {
        testRoundTrip("xstream/v1_2/DecisionService20180911v12.dmn");
    }

    @Test
    public void testDiamondWithColors() throws Exception {
        testRoundTrip("xstream/v1_2/diamondWithColors.dmn");
    }

    @Test
    public void testDMNDIDiagramElementExtension() throws Exception {
        testRoundTrip("xstream/v1_2/DMNDIDiagramElementExtension.dmn");
    }

    @Test
    public void testDMNDIDiagramElementExtensionWithContent() throws Exception {
        DMNMarshaller marshaller = DMNMarshallerFactory.newMarshallerWithExtensions(Arrays.asList(new MyTestRegister()));
        testRoundTrip("xstream/v1_2/DMNDIDiagramElementExtension_withContent.dmn", marshaller);
    }

    @Test
    public void testHardcodedJavaMaxCall() throws Exception {
        testRoundTrip("xstream/v1_2/hardcoded-java-max-call.dmn");
    }

    @Test
    public void testFontSizeSharedStyle() throws Exception {
        testRoundTrip("xstream/v1_2/test-FontSize-sharedStyle.dmn");
        TDefinitions definitions = getMarshaller().unmarshal(new InputStreamReader(this.getClass().getResourceAsStream("/xstream/v1_2/test-FontSize-sharedStyle.dmn")), true);
        List<DMNDiagram> dmnDiagram = definitions.getDMNDI().getDMNDiagram();
        DMNShape shape0 = (DMNShape) dmnDiagram.get(0).getDMNDiagramElement().get(0);
        DMNStyle shape0sharedStyle = (DMNStyle) shape0.getDMNLabel().getSharedStyle();
        assertEquals("LS_4d396200-362f-4939-830d-32d2b4c87042_0", shape0sharedStyle.getId());
        assertEquals(21d, shape0sharedStyle.getFontSize(), 0.0d);
    }

    @Test
    public void testDMNLabelText() throws Exception {
        testRoundTrip("xstream/v1_2/DMNLabel-Text.dmn");
    }

    @Test
    public void testDecisionList() throws Exception {
        testRoundTrip("xstream/v1_2/decision-list.dmn");
    }

    @Override
    protected StreamSource getSchemaSource() {
        return new StreamSource(this.getClass().getResource("/dmn/1.2/DMN12.xsd").getFile());
    }


    @Override
    protected DifferenceEvaluator makeDMNDifferenceEvaluator() {
        return DifferenceEvaluators.chain(DifferenceEvaluators.Default, XMLDifferenceEvaluator.dmn12DiffEvaluator());
    }
}
