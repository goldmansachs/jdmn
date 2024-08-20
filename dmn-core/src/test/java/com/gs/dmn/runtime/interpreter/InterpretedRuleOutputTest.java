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
package com.gs.dmn.runtime.interpreter;

import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class InterpretedRuleOutputTest {
    @Test
    public void testEquals() {
        assertEquals(new InterpretedRuleOutput(true, "123"), new InterpretedRuleOutput(true, "123"));
        assertNotEquals(new InterpretedRuleOutput(true, "1234"), new InterpretedRuleOutput(true, "123"));

        Context c1 = new Context();
        c1.put("Rate", new Pair<>("Best", null));
        c1.put("Status", new Pair<>("Approved", null));
        Context c2 = new Context();
        c2.put("Rate", new Pair<>("Best", null));
        c2.put("Status", new Pair<>("Approved", null));
        assertEquals(new InterpretedRuleOutput(true, c1), new InterpretedRuleOutput(true, c2));
    }
}