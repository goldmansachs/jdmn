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
package com.gs.dmn.serialization.xstream.v1_3;

import com.gs.dmn.serialization.AbstractUnmarshalMarshalTest;
import com.gs.dmn.serialization.DMNVersion;
import org.junit.Test;
import org.w3c.dom.Node;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UnmarshalMarshalTest extends AbstractUnmarshalMarshalTest {
    @Test
    public void testV13_simple() throws Exception {
        testRoundTrip("xstream/v1_3/", "simple.dmn");
    }

    @Test
    public void testV13_ch11example_asFromOMG() throws Exception {
        testRoundTrip("xstream/v1_3/", "Chapter 11 Example.dmn", getMarshaller());
    }

    @Test
    public void testV13_financial() throws Exception {
        testRoundTrip("xstream/v1_3/", "Financial.dmn");
    }

    @Test
    public void testV13_loan_info() throws Exception {
        testRoundTrip("xstream/v1_3/", "Loan info.dmn");
    }

    @Test
    public void testV13_recommended_loan_product() throws Exception {
        testRoundTrip("xstream/v1_3/", "Recommended Loan Products.dmn");
    }

    @Test
    public void testV13_group() throws Exception {
        testRoundTrip("xstream/v1_3/", "group.dmn");
    }

    @Test
    public void testV13_dmnedge() throws Exception {
        testRoundTrip("xstream/v1_3/", "dmnedge.dmn");
    }

    @Test
    public void testV13_functionItem() throws Exception {
        testRoundTrip("xstream/v1_3/", "functionItem.dmn");
    }

    @Test
    public void testV13_decision_list() throws Exception {
        testRoundTrip("xstream/v1_3/", "decision-list.dmn");
    }

    public void testRoundTrip(String subdir, String xmlfile) throws Exception {
        testRoundTrip(subdir, xmlfile, getMarshaller());
    }

    @Override
    protected StreamSource getSchemaSource() {
        return new StreamSource(com.gs.dmn.serialization.xstream.v1_2.UnmarshalMarshalTest.class.getResource("/dmn/1.3/DMN13.xsd").getFile());
    }

    @Override
    protected Set<QName> getAttributesWithDefaultValues() {
        Set<QName> attrWhichCanDefault = new HashSet<QName>();
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
        nodeHavingDefaultableAttr.addAll(Arrays.asList("definitions",
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
        if (DMNVersion.DMN_13.getNamespace().equals(target.getNamespaceURI()) ||
                DMNVersion.DMN_13.getPrefixToNamespaceMap().get("dmndi").equals(target.getNamespaceURI())) {
            return target.getLocalName();
        }
        return null;
    }
}
