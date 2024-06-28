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
package com.gs.dmn.transformation;

import com.gs.dmn.ast.ObjectFactory;
import com.gs.dmn.ast.TDefinitions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DMNToManifestTransformerTest {
    private static TDefinitions DEFINITIONS;

    @BeforeAll
    public static void setUp() {
        DEFINITIONS = new ObjectFactory().createTDefinitions();
        DEFINITIONS.setNamespace("namespace");
    }
    @Test
    void testSingleModel() {
        assertEquals("id-123", DMNToManifestTransformer.uniqueId("id-123", DEFINITIONS, false));
        // implicit namespace
        assertEquals("id-123", DMNToManifestTransformer.uniqueHref("#id-123", DEFINITIONS, false));
        // explicit namespace
        assertEquals("id-123", DMNToManifestTransformer.uniqueHref("namespace#id-123", DEFINITIONS, false));
    }

    @Test
    void testMultipleModels() {
        assertEquals("namespace#id-123", DMNToManifestTransformer.uniqueId("id-123", DEFINITIONS, true));
        // implicit namespace
        assertEquals("namespace#id-123", DMNToManifestTransformer.uniqueHref("#id-123", DEFINITIONS, true));
        // same explicit namespace
        assertEquals("namespace#id-123", DMNToManifestTransformer.uniqueHref("namespace#id-123", DEFINITIONS, true));
        // different explicit namespace
        assertEquals("namespace1#id-123", DMNToManifestTransformer.uniqueHref("namespace1#id-123", DEFINITIONS, true));
    }
}