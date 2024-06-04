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

class TCKVersionTest {
    @Test
    void testFromVersion() {
        // Used in client code
        assertEquals(TCKVersion.TCK_1, TCKVersion.fromVersion("1"));
    }

    @Test
    void testFromIncorrectVersion() {
        // Used in client code
        assertThrows(IllegalArgumentException.class,
                () -> {
                    TCKVersion.fromVersion("1.4");
                });
    }

    @Test
    void testNamespaceToPrefixMap() {
        // Used in client code
        assertNotNull(TCKVersion.LATEST.getNamespaceToPrefixMap());
        assertFalse(TCKVersion.LATEST.getNamespaceToPrefixMap().isEmpty());
    }

    @Test
    void testPrefixToNamespaceMap() {
        // Used in client code
        assertNotNull(TCKVersion.LATEST.getPrefixToNamespaceMap());
        assertFalse(TCKVersion.LATEST.getPrefixToNamespaceMap().isEmpty());
    }
}