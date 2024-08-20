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

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RuleGroupTest {
    @Test
    public void testIsEmpty() {
        assertTrue(new RuleGroup().isEmpty());
        assertFalse(new RuleGroup(Arrays.asList(1, 2)).isEmpty());
    }

    @Test
    public void testSerialize() {
        assertEquals("[]", new RuleGroup().serialize());
        // Indexes start from 1 - user-friendly
        assertEquals("[2, 3]", new RuleGroup(Arrays.asList(1, 2)).serialize());
    }

    @Test
    public void testSort() {
        List<RuleGroup> groups = Arrays.asList(
                null,
                null,
                new RuleGroup(),
                new RuleGroup(),
                new RuleGroup(Arrays.asList(3, 1)),
                new RuleGroup(Arrays.asList(3, 7)),
                new RuleGroup(Arrays.asList(3, 2))
        );
        groups.sort(RuleGroup.COMPARATOR);
        assertEquals("[[], [], [3, 1], [3, 2], [3, 7], null, null]", groups.toString());
    }

    @Test
    public void testMinusNode() {
        assertEquals(new RuleGroup(Arrays.asList(1, 2)), new RuleGroup(Arrays.asList(1, 2, 3)).minus(3));
        assertEquals(new RuleGroup(Arrays.asList(1, 2, 3)), new RuleGroup(Arrays.asList(1, 2, 3)).minus(4));

        assertEquals(new RuleGroup(), new RuleGroup(Collections.emptyList()).minus(3));
        assertEquals(new RuleGroup(), new RuleGroup(Collections.emptyList()).minus(4));
    }

    @Test
    public void testMinusList() {
        assertEquals(new RuleGroup(Arrays.asList(1, 2)), new RuleGroup(Arrays.asList(1, 2, 3)).minus(Collections.singletonList(3)));
        assertEquals(new RuleGroup(Arrays.asList(1, 2, 3)), new RuleGroup(Arrays.asList(1, 2, 3)).minus(Collections.singletonList(4)));

        assertEquals(new RuleGroup(), new RuleGroup(Collections.emptyList()).minus(Collections.singletonList(3)));
        assertEquals(new RuleGroup(), new RuleGroup(Collections.emptyList()).minus(Collections.singletonList(4)));
    }

    @Test
    public void union() {
        assertEquals(new RuleGroup(Arrays.asList(1, 2, 3)), new RuleGroup(Arrays.asList(1, 2, 3)).union(3));
        assertEquals(new RuleGroup(Arrays.asList(1, 2, 3, 4)), new RuleGroup(Arrays.asList(1, 2, 3)).union(4));

        assertEquals(new RuleGroup(Collections.singletonList(3)), new RuleGroup(Collections.emptyList()).union(3));
        assertEquals(new RuleGroup(Collections.singletonList(4)), new RuleGroup(Collections.emptyList()).union(4));
    }

    @Test
    public void intersect() {
        assertEquals(new RuleGroup(Collections.singletonList(3)), new RuleGroup(Arrays.asList(1, 2, 3)).intersect(Collections.singletonList(3)));
        assertEquals(new RuleGroup(), new RuleGroup(Arrays.asList(1, 2, 3)).intersect(Collections.singletonList(4)));

        assertEquals(new RuleGroup(), new RuleGroup(Collections.emptyList()).intersect(Collections.singletonList(3)));
        assertEquals(new RuleGroup(), new RuleGroup(Collections.emptyList()).intersect(Collections.singletonList(4)));
    }
}