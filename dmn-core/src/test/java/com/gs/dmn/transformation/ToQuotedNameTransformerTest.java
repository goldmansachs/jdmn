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

public class ToQuotedNameTransformerTest extends NameTransformerTest {
    @Test
    public void testTransform() throws Exception {
        doTest("0004-lending.dmn", new Pair<>("http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b", "tns"),
                "0004-lending-test-01.xml", new Pair<>("http://www.w3.org/2001/XMLSchema-instance", "xsi"));
        doTest("0007-date-time.dmn", new Pair<>("http://www.trisotech.com/definitions/_69430b3e-17b8-430d-b760-c505bf6469f9", "tns"),
                "0007-date-time-test-01.xml", new Pair<>("http://www.w3.org/2001/XMLSchema-instance", "xsi"));
        doTest("0034-drg-scopes.dmn", new Pair<>("http://www.actico.com/spec/DMN/0.1.0/0034-drg-scopes", "tns"),
                "0034-drg-scopes-test-01.xml", new Pair<>("http://www.w3.org/2001/XMLSchema-instance", "xsi"));
    }

    @Override
    protected DMNTransformer<TestCases> getTransformer() {
        return new ToQuotedNameTransformer(LOGGER);
    }

    @Override
    protected String getInputPath() {
        return "dmn/input/";
    }

    @Override
    protected  String getTargetPath() {
        return "target/quoted/";
    }

    @Override
    protected  String getExpectedPath() {
        return "dmn/expected/quoted/";
    }
}