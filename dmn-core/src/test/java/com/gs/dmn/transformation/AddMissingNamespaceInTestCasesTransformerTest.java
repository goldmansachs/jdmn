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
package com.gs.dmn.transformation;

import com.gs.dmn.runtime.Pair;
import com.gs.dmn.tck.ast.TestCases;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static com.gs.dmn.serialization.DMNConstants.XSI_NS;
import static com.gs.dmn.serialization.DMNConstants.XSI_PREFIX;

public class AddMissingNamespaceInTestCasesTransformerTest extends SimpleDMNTransformerTest {
    @Test
    public void testTransform() throws Exception {
        doTest("1.1", Arrays.asList("0004-lending.dmn"),
                "0004-lending-test-01.xml", new LinkedHashMap<String, Pair<String, String>>() {{
                    put("0004-lending.dmn", new Pair<>("http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b", "tns"));
                    put("0004-lending-test-01.xml", new Pair<>(XSI_NS, XSI_PREFIX));
                }}
        );
    }

    @Override
    protected DMNTransformer<TestCases> getTransformer() {
        return new AddMissingNamespaceInTestCasesTransformer();
    }

    @Override
    protected String getInputPath() {
        return "dmn/input/";
    }

    @Override
    protected  String getTargetPath() {
        return "target/missing-namespace/";
    }

    @Override
    protected  String getExpectedPath() {
        return "dmn/expected/missing-namespace/";
    }
}