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

public class DRGElementKindTest {
    @Test
    public void testKindByName() {
        List<Pair<String, DRGElementKind>> tests = Arrays.asList(
                new MutablePair<>("TBusinessKnowledgeModel", DRGElementKind.BUSINESS_KNOWLEDGE_MODEL),
                new MutablePair<>("TDecision", DRGElementKind.DECISION),
                new MutablePair<>("TDecisionService", DRGElementKind.DECISION_SERVICE),
                new MutablePair<>("TInputData", DRGElementKind.INPUT_DATA),
                new MutablePair<>("TKnowledgeSource", DRGElementKind.KNOWLEDGE_SOURCE),
                new MutablePair<>("X", DRGElementKind.OTHER)
        );

        for(Pair<String, DRGElementKind> test: tests) {
            assertEquals(test.getRight(), DRGElementKind.kindByName(test.getLeft()));
        }
    }
}