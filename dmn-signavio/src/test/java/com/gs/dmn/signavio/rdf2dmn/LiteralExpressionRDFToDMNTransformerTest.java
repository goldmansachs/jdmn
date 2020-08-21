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

public class LiteralExpressionRDFToDMNTransformerTest extends AbstractRDFToDMNTransformerTest {
    @Override
    protected String getTestFolder() {
        return "literal";
    }

    @Test
    public void testOneDiagram() throws Exception {
        doTest("simple-decision-free-text-boolean-literal-expression");
    }

    @Test
    public void testAllDiagrams() throws Exception {
        List<String> diagrams = Arrays.asList(
                "simple-decision-feel-boolean-literal-expression",
                "simple-decision-feel-complex-literal-expression",
                "simple-decision-feel-date-literal-expression",
                "simple-decision-feel-enumeration-literal-expression",
                "simple-decision-feel-numeric-literal-expression",
                "simple-decision-feel-string-literal-expression",
                "simple-decision-free-text-boolean-literal-expression",
                "simple-decision-free-text-complex-literal-expression",
                "simple-decision-free-text-date-literal-expression",
                "simple-decision-free-text-enumeration-literal-expression",
                "simple-decision-free-text-numeric-literal-expression",
                "simple-decision-free-text-string-literal-expression"
        );
        for (String diagram : diagrams) {
            doTest(diagram);
        }
    }
}
