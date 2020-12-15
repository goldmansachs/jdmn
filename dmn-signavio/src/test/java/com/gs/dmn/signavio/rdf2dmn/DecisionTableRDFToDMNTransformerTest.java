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
package com.gs.dmn.signavio.rdf2dmn;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DecisionTableRDFToDMNTransformerTest extends AbstractRDFToDMNTransformerTest {
    @Override
    protected String getTestFolder() {
        return "decision-table";
    }

    @Test
    public void testOneDiagram() throws Exception {
        doTest("compound-decision-primitive-type-inputs-sfeel-input-entries-compound-output-first-hit-policy");
    }

    @Test
    public void testAllDiagrams() throws Exception {
        List<String> diagrams = Arrays.asList(
                "compound-decision-primitive-type-inputs-sfeel-input-entries-compound-output-first-hit-policy",
                "simple-decision-complex-type-inputs-sfeel-input-entries-single-output-collect-hit-policy",
                "simple-decision-primitive-type-inputs-compound-output-output-order-hit-policy",
                "simple-decision-primitive-type-inputs-compound-output-priority-hit-policy",
                "simple-decision-primitive-type-inputs-feel-input-entries-single-output-first-hit-policy",
                "simple-decision-primitive-type-inputs-feel-input-entries-single-output-unique-hit-policy",
                "simple-decision-primitive-type-inputs-sfeel-input-entries-compound-output-first-hit-policy",
                "simple-decision-primitive-type-inputs-sfeel-input-entries-single-output-first-hit-policy",
                "simple-decision-primitive-type-inputs-single-output-any-hit-policy",
                "simple-decision-primitive-type-inputs-single-output-collect-count-hit-policy",
                "simple-decision-primitive-type-inputs-single-output-collect-hit-policy",
                "simple-decision-primitive-type-inputs-single-output-collect-min-hit-policy",
                "simple-decision-primitive-type-inputs-single-output-collect-sum-hit-policy",
                "simple-decision-primitive-type-inputs-single-output-first-hit-policy",
                "simple-decision-primitive-type-inputs-single-output-output-order-hit-policy",
                "simple-decision-primitive-type-inputs-single-output-priority-hit-policy",
                "simple-decision-primitive-type-inputs-single-output-rule-order-hit-policy",
                "simple-decision-primitive-type-inputs-single-output-unique-hit-policy",
                "simple-decision-primitive-type-list-inputs-feel-input-entries-single-output-unique-hit-policy"
        );
        for(String diagram : diagrams) {
            doTest(diagram);
        }
    }
}
