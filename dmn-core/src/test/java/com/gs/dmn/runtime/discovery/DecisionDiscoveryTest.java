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
package com.gs.dmn.runtime.discovery;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class DecisionDiscoveryTest {
    private final DecisionDiscovery decisionDiscovery = new DecisionDiscovery();

    @Test
    public void testDiscovery() {
        Set<Class<?>> decisions = decisionDiscovery.discover("com.gs");
        assertEquals(1, decisions.size());
        Class<?> first = decisions.iterator().next();
        assertEquals("com.gs.dmn.runtime.discovery.NopDecision", first.getName());
    }
}