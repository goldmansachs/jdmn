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
package com.gs.dmn.validation.table;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BoundTest {
    @Test
    public void testCompareTo() {
        List<Bound> bounds = Arrays.asList(
            null,
            null,
            new Bound(null, true, false, 4),
            new Bound(null, false, false, 4),
            new Bound(null, true, false, 5),
            new Bound(null, true, true, 3),
            new Bound(null, false, true, 3),
            new Bound(null, true, false, 6),
            new Bound(null, true, true, 4)
        );
        bounds.sort(Bound.COMPARATOR);
        assertEquals("[[3, 3], 4), [4, (4, (5, (6, null, null]", bounds.toString());
    }
}