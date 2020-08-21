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
package com.gs.dmn.runtime.listener;

import org.junit.Test;

import static org.junit.Assert.*;

public class CompositeListenerTest {
    private EventListener listener = new CompositeListener();

    @Test
    public void testStartDRGElement() {
        listener.startDRGElement(null, null);
        assertTrue(true);
    }

    @Test
    public void testEndDRGElement() {
        listener.endDRGElement(null, null, null, 0);
        assertTrue(true);
    }

    @Test
    public void testStartRule() {
        listener.startRule(null, null);
        assertTrue(true);
    }

    @Test
    public void testMatchRule() {
        listener.matchRule(null, null);
        assertTrue(true);
    }

    @Test
    public void testEndRule() {
        listener.endRule(null, null, null);
        assertTrue(true);
    }

    @Test
    public void testMatchColumn() {
        listener.matchColumn(null, 0, null);
        assertTrue(true);
    }
}