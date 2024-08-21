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
package com.gs.dmn.serialization.jackson;

import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class QNameDeserializerTest {
    @Test
    void testFromKey() {
        assertNull(QNameDeserializer.fromKey(null));
        
        assertEquals(new QName("namespace", "local", "prefix"), QNameDeserializer.fromKey("namespace|local|prefix"));
        assertEquals(new QName("", "local", "prefix"), QNameDeserializer.fromKey("|local|prefix"));
        assertEquals(new QName("namespace", "local", ""), QNameDeserializer.fromKey("namespace|local|"));
        assertEquals(new QName("", "local", ""), QNameDeserializer.fromKey("local"));

        assertEquals(new QName("namespace", "local"), QNameDeserializer.fromKey("namespace|local|"));

        assertEquals(new QName("local"), QNameDeserializer.fromKey("local"));
    }
}