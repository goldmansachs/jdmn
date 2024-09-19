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
package com.gs.dmn.serialization.xstream.v1_5;

import com.gs.dmn.serialization.AbstractXStreamUnmarshalMarshalTest;
import com.gs.dmn.serialization.diff.XMLDifferenceEvaluator;
import org.junit.jupiter.api.Test;
import org.xmlunit.diff.DifferenceEvaluator;
import org.xmlunit.diff.DifferenceEvaluators;

import javax.xml.transform.stream.StreamSource;

public class UnmarshalMarshalTest extends AbstractXStreamUnmarshalMarshalTest {
    @Test
    public void testSimple() throws Exception {
        testRoundTrip("xstream/v1_5/simple.dmn");
    }

    @Test
    public void testChapter11Example() throws Exception {
        testRoundTrip("xstream/v1_5/Chapter 11 Example.dmn", getMarshaller());
    }

    @Test
    public void testFinancial() throws Exception {
        testRoundTrip("xstream/v1_5/Financial.dmn");
    }

    @Test
    public void testLoanInfo() throws Exception {
        testRoundTrip("xstream/v1_5/Loan info.dmn");
    }

    @Test
    public void testRecommendedLoanProducts() throws Exception {
        testRoundTrip("xstream/v1_5/Recommended Loan Products.dmn");
    }

    @Test
    public void testGroup() throws Exception {
        testRoundTrip("xstream/v1_5/group.dmn");
    }

    @Test
    public void testDMNeEdge() throws Exception {
        testRoundTrip("xstream/v1_5/dmnedge.dmn");
    }

    @Test
    public void testFunctionItem() throws Exception {
        testRoundTrip("xstream/v1_5/functionItem.dmn");
    }

    @Test
    public void testDecisionList() throws Exception {
        testRoundTrip("xstream/v1_5/decision-list.dmn");
    }

    @Test
    public void test0034DrgScopes() throws Exception {
        testRoundTrip("xstream/v1_5/0034-drg-scopes.dmn");
    }

    @Test
    public void testSampleConditional() throws Exception {
        testRoundTrip("xstream/v1_5/sampleConditional.dmn");
    }

    @Test
    public void testSampleFilter() throws Exception {
        testRoundTrip("xstream/v1_5/sampleFilter.dmn");
    }

    @Test
    public void testSampleFor() throws Exception {
        testRoundTrip("xstream/v1_5/sampleFor.dmn");
    }

    @Test
    public void testSampleQuantified() throws Exception {
        testRoundTrip("xstream/v1_5/sampleQuantified.dmn");
    }

    @Override
    protected StreamSource getSchemaSource() {
        return new StreamSource(this.getClass().getResource("/dmn/1.5/DMN15.xsd").getFile());
    }

    @Override
    protected DifferenceEvaluator makeDMNDifferenceEvaluator() {
        return DifferenceEvaluators.chain(DifferenceEvaluators.Default, XMLDifferenceEvaluator.dmn15DiffEvaluator());
    }
}
