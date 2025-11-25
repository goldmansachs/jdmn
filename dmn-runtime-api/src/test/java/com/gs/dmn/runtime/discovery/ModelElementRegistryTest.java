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
package com.gs.dmn.runtime.discovery;

import com.gs.dmn.runtime.DMNRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ModelElementRegistryTest {
    private final String qName = "qName";
    private final String className = "className";

    @Test
    void testRegistration() {
        ModelElementRegistry registry = new ModelElementRegistry();
        registry.register(qName, className);

        assertEquals(className, registry.discover(qName));
        Set<String> expectKeys = new LinkedHashSet<>(Collections.singletonList(qName));
        assertEquals(expectKeys, registry.keys());
    }

    @Test
    void testRegistrationForNulls() {
        ModelElementRegistry registry = new ModelElementRegistry();

        Assertions.assertThrows(NullPointerException.class, () -> registry.register(null, className));

        Assertions.assertThrows(NullPointerException.class, () -> registry.register(qName, null));
    }

    @Test
    void testRegistrationForExistingWithDifferentValue() {
        ModelElementRegistry registry = new ModelElementRegistry();

        registry.register(qName, "otherName");
        Assertions.assertThrows(DMNRuntimeException.class, () -> registry.register(qName, className));

    }

    @Test
    void testRegistrationForExistingWithSameValue() {
        ModelElementRegistry registry = new ModelElementRegistry();

        registry.register(qName, className);
        assertEquals(1, registry.keys().size());
        registry.register(qName, className);
        assertEquals(1, registry.keys().size());
    }
}