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
package com.gs.dmn.serialization;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DMNVersionTest {
    @Test
    void testFromVersion() {
        // Used in client code
        assertEquals(DMNVersion.DMN_14, DMNVersion.fromVersion("1.4"));
    }

    @Test
    void testFromIncorrectVersion() {
        // Used in client code
        assertThrows(IllegalArgumentException.class,
                () -> DMNVersion.fromVersion("xxx"));
    }

    @Test
    void testNamespaceToPrefixMap() {
        // Used in client code
        assertNotNull(DMNVersion.LATEST.getNamespaceToPrefixMap());
        assertFalse(DMNVersion.LATEST.getNamespaceToPrefixMap().isEmpty());
    }

    @Test
    void testPrefixToNamespaceMap() {
        // Used in client code
        assertNotNull(DMNVersion.LATEST.getPrefixToNamespaceMap());
        assertFalse(DMNVersion.LATEST.getPrefixToNamespaceMap().isEmpty());
    }

    @Test
    void testFEELPrefix() {
        for (DMNVersion version : DMNVersion.VERSIONS) {
            assertEquals("feel", version.getFeelPrefix());
        }
    }
}