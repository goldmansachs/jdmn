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
package com.gs.dmn.signavio.transformation;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ExportedLiteralDMNToJavaTransformerTest extends AbstractSignavioDMNToJavaTest {
    @Override
    protected String getInputPath() {
        return "dmn/literal";
    }

    @Override
    protected String getExpectedPath() {
        return "dmn/dmn2java/expected/literal/dmn";
    }

    @Test
    public void testAll() throws Exception {
        List<String> diagrams = Arrays.asList(
                "simple-decision-feel-boolean-literal-expression",
                "simple-decision-feel-complex-literal-expression",
                "simple-decision-feel-date-literal-expression",
                "simple-decision-feel-enumeration-literal-expression",
                "simple-decision-feel-numeric-literal-expression",
                "simple-decision-feel-string-literal-expression"
        );
        for (String diagram : diagrams) {
            doSingleModelTest(diagram);
        }
    }

    @Test
    public void testFreeText() throws Exception {
        List<String> diagrams = Arrays.asList(
                "simple-decision-free-text-boolean-literal-expression",
                "simple-decision-free-text-complex-literal-expression",
                "simple-decision-free-text-date-literal-expression",
                "simple-decision-free-text-enumeration-literal-expression",
                "simple-decision-free-text-numeric-literal-expression",
                "simple-decision-free-text-string-literal-expression"
        );
        for (String diagram : diagrams) {
            doSingleModelTest(diagram);
        }
    }
}
