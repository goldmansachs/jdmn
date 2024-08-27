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
package com.gs.dmn.serialization.xstream.v1_1;

import com.gs.dmn.serialization.AbstractXStreamUnmarshalMarshalTest;
import com.gs.dmn.serialization.DMNMarshaller;
import com.gs.dmn.serialization.diff.XMLDifferenceEvaluator;
import com.gs.dmn.serialization.xstream.DMNMarshallerFactory;
import com.gs.dmn.serialization.xstream.extensions.kie.KieTestRegister;
import com.gs.dmn.serialization.xstream.extensions.test.TestRegister;
import org.junit.jupiter.api.Test;
import org.xmlunit.diff.DifferenceEvaluator;
import org.xmlunit.diff.DifferenceEvaluators;

import javax.xml.transform.stream.StreamSource;
import java.util.Collections;

public class UnmarshalMarshalTest extends AbstractXStreamUnmarshalMarshalTest {
    @Test
    public void test0001InputDataString() throws Exception {
        testRoundTrip("xstream/v1_1/0001-input-data-string.dmn");
    }

    @Test
    public void test0002InputDataNumber() throws Exception {
        testRoundTrip("xstream/v1_1/0002-input-data-number.dmn");
    }

    @Test
    public void test0003InputDataStringAllowedValues() throws Exception {
        testRoundTrip("xstream/v1_1/0003-input-data-string-allowed-values.dmn");
    }

    @Test
    public void test0005DecisionList() throws Exception {
        testRoundTrip("xstream/v1_1/0005-decision-list.dmn");
    }

    @Test
    public void testHardcodedJavaMaxCall() throws Exception {
        testRoundTrip("xstream/v1_1/hardcoded-java-max-call.dmn");
    }

    @Test
    public void testDishDecision() throws Exception {
        testRoundTrip("xstream/v1_1/dish-decision.dmn");
    }

    @Test
    public void testDummyDefinitions() throws Exception {
        testRoundTrip("xstream/v1_1/dummy-definitions.dmn");
    }

    @Test
    public void testDummyRelation() throws Exception {
        testRoundTrip("xstream/v1_1/dummy-relation.dmn");
    }

    @Test
    public void testCh11Example() throws Exception {
        testRoundTrip("xstream/v1_1/ch11example.dmn");
    }

    @Test
    public void testHelloWorldSemanticNamespace() throws Exception {
        testRoundTrip("xstream/v1_1/Hello_World_semantic_namespace.dmn");
    }

    @Test
    public void testHelloWorldSemanticNamespaceWithExtensions() throws Exception {
        DMNMarshaller marshaller = DMNMarshallerFactory.newMarshallerWithExtensions(Collections.singletonList(new KieTestRegister()));
        testRoundTrip("xstream/v1_1/Hello_World_semantic_namespace_with_extensions.dmn", marshaller);
    }

    @Test
    public void testHelloWorldSemanticNamespaceWithExtensionsOtherNsLocation() throws Exception {
        DMNMarshaller marshaller = DMNMarshallerFactory.newMarshallerWithExtensions(Collections.singletonList(new KieTestRegister()));
        testRoundTrip("xstream/v1_1/Hello_World_semantic_namespace_with_extensions_other_ns_location.dmn", marshaller);
    }

    @Test
    public void testSemanticNamespace() throws Exception {
        testRoundTrip("xstream/v1_1/semantic-namespace.dmn");
    }

    @Test
    public void test20161014() throws Exception {
        DMNMarshaller marshaller = DMNMarshallerFactory.newMarshallerWithExtensions(Collections.singletonList(new TestRegister()));
        testRoundTrip("xstream/v1_1/test20161014.dmn", marshaller);
    }

    @Test
    public void testHardcodedFunctionDefinition() throws Exception {
        testRoundTrip("xstream/v1_1/hardcoded_function_definition.dmn");
    }

    @Override
    protected StreamSource getSchemaSource() {
        return new StreamSource(this.getClass().getResource("/dmn/1.1/dmn.xsd").getFile());
    }

    @Override
    protected DifferenceEvaluator makeDMNDifferenceEvaluator() {
        return DifferenceEvaluators.chain(DifferenceEvaluators.Default, XMLDifferenceEvaluator.dmn11DiffEvaluator());
    }
}
