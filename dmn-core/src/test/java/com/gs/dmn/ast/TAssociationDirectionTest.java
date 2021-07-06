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

public class TAssociationDirectionTest {
    @Test
    public void testFromValue() {
        assertEquals(TAssociationDirection.NONE, TAssociationDirection.fromValue("None"));
        assertEquals(TAssociationDirection.ONE, TAssociationDirection.fromValue("One"));
        assertEquals(TAssociationDirection.BOTH, TAssociationDirection.fromValue("Both"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromValueWhenIncorrectName() {
        TAssociationDirection.fromValue("asd");
    }
}
