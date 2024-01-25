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
package com.gs.dmn.ast.dmndi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class KnownColorTest {

    @Test
    public void testFromValue() {
        assertEquals(KnownColor.MAROON, KnownColor.fromValue("maroon"));
        assertEquals(KnownColor.RED, KnownColor.fromValue("red"));
        assertEquals(KnownColor.ORANGE, KnownColor.fromValue("orange"));
        assertEquals(KnownColor.YELLOW, KnownColor.fromValue("yellow"));
        assertEquals(KnownColor.OLIVE, KnownColor.fromValue("olive"));
        assertEquals(KnownColor.PURPLE, KnownColor.fromValue("purple"));
        assertEquals(KnownColor.FUCHSIA, KnownColor.fromValue("fuchsia"));
        assertEquals(KnownColor.WHITE, KnownColor.fromValue("white"));
        assertEquals(KnownColor.LIME, KnownColor.fromValue("lime"));
        assertEquals(KnownColor.GREEN, KnownColor.fromValue("green"));
        assertEquals(KnownColor.NAVY, KnownColor.fromValue("navy"));
        assertEquals(KnownColor.BLUE, KnownColor.fromValue("blue"));
        assertEquals(KnownColor.AQUA, KnownColor.fromValue("aqua"));
        assertEquals(KnownColor.TEAL, KnownColor.fromValue("teal"));
        assertEquals(KnownColor.BLACK, KnownColor.fromValue("black"));
        assertEquals(KnownColor.SILVER, KnownColor.fromValue("silver"));
        assertEquals(KnownColor.GRAY, KnownColor.fromValue("gray"));
    }

    @Test
    public void testFromValueWhenIncorrectName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            KnownColor.fromValue("asd");
        });
    }
}
