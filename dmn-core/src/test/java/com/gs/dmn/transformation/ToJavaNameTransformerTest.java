/**
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
package com.gs.dmn.transformation;

import com.gs.dmn.runtime.Pair;
import org.junit.Test;
import org.omg.dmn.tck.marshaller._20160719.TestCases;
import org.omg.spec.dmn._20151101.dmn.*;

import java.util.Arrays;

import static com.gs.dmn.runtime.Assert.assertEquals;

public class ToJavaNameTransformerTest extends NameTransformerTest {
    @Test
    public void testDMNTransform() throws Exception {
        doTest("0004-lending.dmn", new Pair<>("http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b", "tns"),
                "0004-lending-test-01.xml", new Pair<>("http://www.w3.org/2001/XMLSchema-instance", "xsi"));
    }

    @Test
    public void testQuotedNames() {
        ToJavaNameTransformer transformer = (ToJavaNameTransformer) getTransformer();
        TDRGElement tNamedElement = makeElement("abc ? x");
        NameMappings nameMappings = new NameMappings();

        // Transform first name
        transformer.addNameMapping(tNamedElement, nameMappings);
        assertEquals(Arrays.asList("abcX"), nameMappings.values());

        // Transform second name
        transformer.addNameMapping(makeElement("abc?x"), nameMappings);
        assertEquals(Arrays.asList("abcX", "abcX_1"), nameMappings.values());

    }

    private TDRGElement makeElement(String name) {
        TInputData inputData = new TInputData();
        inputData.setName(name);
        return inputData;
    }

    @Override
    protected DMNTransformer<TestCases> getTransformer() {
        return new ToJavaNameTransformer(LOGGER);
    }

    @Override
    protected String getInputPath() {
        return "dmn/input/";
    }

    @Override
    protected  String getExpectedPath() {
        return "dmn/expected/java/";
    }
}