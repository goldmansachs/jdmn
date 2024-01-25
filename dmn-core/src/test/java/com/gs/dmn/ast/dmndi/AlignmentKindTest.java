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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlignmentKindTest {
    @Test
    public void testFromValue() {
        assertEquals(AlignmentKind.START, AlignmentKind.fromValue("start"));
        assertEquals(AlignmentKind.END, AlignmentKind.fromValue("end"));
        assertEquals(AlignmentKind.CENTER, AlignmentKind.fromValue("center"));
    }

    @Test
    public void testFromValueWhenIncorrectName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            AlignmentKind.fromValue("asd");
        });
    }
}
