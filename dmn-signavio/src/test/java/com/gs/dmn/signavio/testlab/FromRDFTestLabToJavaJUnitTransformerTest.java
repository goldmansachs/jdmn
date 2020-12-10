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
package com.gs.dmn.signavio.testlab;

import org.junit.Test;

import java.net.URI;

public class FromRDFTestLabToJavaJUnitTransformerTest extends AbstractTestLabToJavaJUnitTransformerTest {
    @Override
    protected String getInputPath() {
        return "rdf/rdf2java/expected/dmn/decision-table";
    }

    @Override
    protected String getExpectedPath() {
        return "rdf/rdf2java/expected/java/decision-table/test-lab";
    }

    @Test
    public void testAll() throws Exception {
        doTest("compound-decision-primitive-type-inputs-sfeel-input-entries-compound-output-first-hit-policy");
        doTest("simple-decision-complex-type-inputs-sfeel-input-entries-single-output-collect-hit-policy");
        doTest("simple-decision-primitive-type-inputs-feel-input-entries-single-output-first-hit-policy");
    }
}