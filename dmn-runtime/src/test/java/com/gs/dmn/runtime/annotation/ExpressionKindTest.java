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

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpressionKindTest {

    @Test
    public void testKindByClass() {
        List<Pair<String, ExpressionKind>> tests = Arrays.asList(
                new MutablePair<>("TContext", ExpressionKind.CONTEXT),
                new MutablePair<>("TDecisionTable", ExpressionKind.DECISION_TABLE),
                new MutablePair<>("TFunctionDefinition", ExpressionKind.FUNCTION_DEFINITION),
                new MutablePair<>("TInvocation", ExpressionKind.INVOCATION),
                new MutablePair<>("TList", ExpressionKind.LIST),
                new MutablePair<>("TLiteralExpression", ExpressionKind.LITERAL_EXPRESSION),
                new MutablePair<>("TRelation", ExpressionKind.RELATION),
                new MutablePair<>("TUnaryTests", ExpressionKind.UNARY_TESTS),
                new MutablePair<>("TConditional", ExpressionKind.CONDITIONAL),
                new MutablePair<>("TFor", ExpressionKind.FOR),
                new MutablePair<>("TFilter", ExpressionKind.FILTER),
                new MutablePair<>("TSome", ExpressionKind.SOME),
                new MutablePair<>("TEvery", ExpressionKind.EVERY),
                new MutablePair<>("X", ExpressionKind.OTHER)
        );

        for(Pair<String, ExpressionKind> test: tests) {
            assertEquals(test.getRight(), ExpressionKind.kindByName(test.getLeft()));
        }
    }
}