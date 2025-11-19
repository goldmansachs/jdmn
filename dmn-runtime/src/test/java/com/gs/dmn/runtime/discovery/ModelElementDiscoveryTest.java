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
package com.gs.dmn.runtime.discovery;

import com.gs.dmn.runtime.JavaTimeDMNBaseDecision;
import com.gs.dmn.runtime.annotation.DRGElement;
import com.gs.dmn.runtime.annotation.Rule;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ModelElementDiscoveryTest {
    private final ModelElementDiscovery modelElementDiscovery = new ModelElementDiscovery();

    @Test
    public void testDiscovery() {
        Set<Class<?>> decisions = modelElementDiscovery.discover("com.gs");
        assertEquals(2, decisions.size());
        assertTrue(decisions.stream().map(Class::getName).collect(Collectors.toList()).contains("com.gs.dmn.runtime.discovery.NopDecision"));
    }

    @Test
    public void testGetDRGElementAnnotation() {
        DRGElement drgElementAnnotation = modelElementDiscovery.getDRGElementAnnotation(JavaTimeDMNBaseDecision.class);
        assertNull(drgElementAnnotation);
    }

    @Test
    public void testGetRuleAnnotation() {
        Rule ruleAnnotation = this.modelElementDiscovery.getRuleAnnotation(JavaTimeDMNBaseDecision.class, 0);
        assertNull(ruleAnnotation);
    }

}