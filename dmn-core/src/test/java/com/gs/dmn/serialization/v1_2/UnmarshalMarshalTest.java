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
package com.gs.dmn.serialization.v1_2;

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.dmndi.DMNDiagram;
import com.gs.dmn.ast.dmndi.DMNShape;
import com.gs.dmn.ast.dmndi.DMNStyle;
import com.gs.dmn.serialization.AbstractUnmarshalMarshalTest;
import com.gs.dmn.serialization.DMNMarshaller;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.extensions.MyTestRegister;
import com.gs.dmn.serialization.xstream.DMNMarshallerFactory;
import org.junit.Test;
import org.w3c.dom.Node;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class UnmarshalMarshalTest extends AbstractUnmarshalMarshalTest {
    @Test
    public void testV12_simple() throws Exception {
        testRoundTrip("xstream/v1_2/", "simple.dmn");
    }

    @Test
    public void testV12_ch11example() throws Exception {
        testRoundTrip("xstream/v1_2/", "ch11example.dmn");
    }

    @Test
    public void testV12_ImportName() throws Exception {
        testRoundTrip("xstream/v1_2/", "ImportName.dmn");
    }

    @Test
    public void testV12_DecisionService20180911v12() throws Exception {
        testRoundTrip("xstream/v1_2/", "DecisionService20180911v12.dmn");
    }

    @Test
    public void testV12_DiamondWithColors() throws Exception {
        testRoundTrip("xstream/v1_2/", "diamondWithColors.dmn");
    }

    @Test
    public void testV12_DMNDIDiagramElementExtension() throws Exception {
        testRoundTrip("xstream/v1_2/", "DMNDIDiagramElementExtension.dmn");
    }

    @Test
    public void testV12_DMNDIDiagramElementExtension_withContent() throws Exception {
        DMNMarshaller marshaller = DMNMarshallerFactory.newMarshallerWithExtensions(Arrays.asList(new MyTestRegister()));
        testRoundTrip("xstream/v1_2/", "DMNDIDiagramElementExtension_withContent.dmn", marshaller);
    }

    @Test
    public void test_hardcoded_java_max_call() throws Exception {
        testRoundTrip("xstream/v1_2/", "hardcoded-java-max-call.dmn");
    }

    @Test
    public void test_FontSize_sharedStyle() throws Exception {
        testRoundTrip("xstream/v1_2/", "test-FontSize-sharedStyle.dmn");
        TDefinitions definitions = getMarshaller().unmarshal(new InputStreamReader(this.getClass().getResourceAsStream("/xstream/v1_2/test-FontSize-sharedStyle.dmn")), true);
        List<DMNDiagram> dmnDiagram = definitions.getDMNDI().getDMNDiagram();
        DMNShape shape0 = (DMNShape) dmnDiagram.get(0).getDMNDiagramElement().get(0);
        DMNStyle shape0sharedStyle = (DMNStyle) shape0.getDMNLabel().getSharedStyle();
        assertEquals("LS_4d396200-362f-4939-830d-32d2b4c87042_0", shape0sharedStyle.getId());
        assertEquals(21d, shape0sharedStyle.getFontSize(), 0.0d);
    }

    @Test
    public void test_DMNLabel_Text() throws Exception {
        testRoundTrip("xstream/v1_2/", "DMNLabel-Text.dmn");
    }

    @Test
    public void testV12_decision_list() throws Exception {
        testRoundTrip("xstream/v1_2/", "decision-list.dmn");
    }

    @Override
    protected StreamSource getSchemaSource() {
        return new StreamSource(UnmarshalMarshalTest.class.getResource("/dmn/1.2/DMN12.xsd").getFile());
    }

    @Override
    protected Set<QName> getAttributesWithDefaultValues() {
        Set<QName> attrWhichCanDefault = new HashSet<>();
        attrWhichCanDefault.addAll(Arrays.asList(
                new QName("expressionLanguage"),
                new QName("typeLanguage"),
                new QName("isCollection"),
                new QName("hitPolicy"),
                new QName("preferredOrientation"),
                new QName("kind"),
                new QName("textFormat"),
                new QName("associationDirection"),
                new QName("isCollapsed")));
        return attrWhichCanDefault;
    }

    @Override
    protected Set<String> getNodesHavingAttributesWithDefaultValues() {
        Set<String> nodeHavingDefaultableAttr = new HashSet<>();
        nodeHavingDefaultableAttr.addAll(Arrays.asList(
                "definitions",
                "decisionTable",
                "itemDefinition",
                "itemComponent",
                "encapsulatedLogic",
                "textAnnotation",
                "association",
                "DMNShape"));
        return nodeHavingDefaultableAttr;
    }

    @Override
    protected String safeStripDMNPrefix(Node target) {
        if (DMNVersion.DMN_12.getNamespace().equals(target.getNamespaceURI()) ||
                DMNVersion.DMN_12.getPrefixToNamespaceMap().get("dmndi").equals(target.getNamespaceURI())) {
            return target.getLocalName();
        }
        return null;
    }
}
