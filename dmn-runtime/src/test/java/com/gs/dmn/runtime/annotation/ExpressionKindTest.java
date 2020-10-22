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
package com.gs.dmn.runtime.annotation;

import com.gs.dmn.runtime.Pair;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExpressionKindTest {

    @Test
    public void testKindByClass() {
        List<Pair<String, ExpressionKind>> tests = Arrays.asList(
                new Pair<>("TContext", ExpressionKind.CONTEXT),
                new Pair<>("TDecisionTable", ExpressionKind.DECISION_TABLE),
                new Pair<>("TFunctionDefinition", ExpressionKind.FUNCTION_DEFINITION),
                new Pair<>("TInvocation", ExpressionKind.INVOCATION),
                new Pair<>("TList", ExpressionKind.LIST),
                new Pair<>("TLiteralExpression", ExpressionKind.LITERAL_EXPRESSION),
                new Pair<>("TRelation", ExpressionKind.RELATION),
                new Pair<>("X", ExpressionKind.OTHER)
        );

        for(Pair<String, ExpressionKind> test: tests) {
            assertEquals(test.getRight(), ExpressionKind.kindByName(test.getLeft()));
        }
    }
}