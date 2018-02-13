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
package com.gs.dmn.feel;

import com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.feel.analysis.semantics.type.NumberType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.interpreter.SFEELInterpreterImpl;
import com.gs.dmn.feel.synthesis.SFEELTranslatorImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SFEELProcessorTest extends AbstractFEELProcessorTest {
    @Before
    public void setUp() {
        this.feelTranslator = new SFEELTranslatorImpl(dmnTransformer);
        this.feelInterpreter = new SFEELInterpreterImpl(dmnInterpreter);
    }

    @Test
    public void testQualifiedName() {
        Type bType = new ItemDefinitionType("b").addMember("c", Arrays.asList("C"), NumberType.NUMBER);
        Type aType = new ItemDefinitionType("a").addMember("b", Arrays.asList("C"), bType);
        List<EnvironmentEntry> pairs = Arrays.asList(new EnvironmentEntry("a", aType, null));

        doExpressionTest(pairs, "", "a.b.c",
                "PathExpression(PathExpression(Name(a), b), c)",
                "number",
                null,
                null,
                null);
    }
}