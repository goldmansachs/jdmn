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
package com.gs.dmn.runtime.cache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultCacheTest {
    private final String key = "key";
    private final String value = "value";

    @Test
    void testContains() {
        Cache cache = new DefaultCache();
        cache.bind(key, value);

        assertTrue(cache.contains(key));
        assertFalse(cache.contains("otherKey"));
    }

    @Test
    void testBind() {
        Cache cache = new DefaultCache();

        assertFalse(cache.contains(key));
        cache.bind(key, value);
        assertTrue(cache.contains(key));
    }

    @Test
    void testLookup() {
        Cache cache = new DefaultCache();

        assertNull(cache.lookup(key));
        cache.bind(key, value);
        assertEquals(value, cache.lookup(key));
    }

    @Test
    void testClear() {
        Cache cache = new DefaultCache();
        cache.bind(key, value);

        assertEquals(value, cache.lookup(key));
        cache.clear();
        assertNull(cache.lookup(key));
    }
}