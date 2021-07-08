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
package com.gs.dmn.ast;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TDecisionTableOrientationTest {
    @Test
    public void testFromValue() {
        assertEquals(TDecisionTableOrientation.RULE_AS_ROW, TDecisionTableOrientation.fromValue("Rule-as-Row"));
        assertEquals(TDecisionTableOrientation.RULE_AS_COLUMN, TDecisionTableOrientation.fromValue("Rule-as-Column"));
        assertEquals(TDecisionTableOrientation.CROSS_TABLE, TDecisionTableOrientation.fromValue("CrossTable"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromValueWhenIncorrectName() {
        TDecisionTableOrientation.fromValue("asd");
    }
}
