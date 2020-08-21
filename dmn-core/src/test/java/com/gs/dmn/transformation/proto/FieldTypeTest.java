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
package com.gs.dmn.transformation.proto;

import com.gs.dmn.runtime.DMNRuntimeException;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldTypeTest {
    @Test(expected = DMNRuntimeException.class)
    public void testConstructorWhenModifierIsNull() {
        new FieldType(null, "ab");
    }

    @Test(expected = DMNRuntimeException.class)
    public void testConstructorWhenTypeIsNull() {
        new FieldType("optional", null);
    }

    @Test
    public void testEquals() {
        FieldType ft1 = new FieldType("optional", "string");
        FieldType ft2 = new FieldType("optional", "string");
        assertEquals(ft1, ft2);
    }

    @Test
    public void testHasCode() {
        FieldType ft1 = new FieldType("optional", "string");
        assertEquals(953450673, ft1.hashCode());
    }
}