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
import com.gs.dmn.runtime.ExecutableDRGElement;
import com.gs.dmn.runtime.ExecutionContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ModelElementRegistryTest {
    private final String qName = "qName";
    private final String className = "className";

    @Test
    void testRegistration() {
        ModelElementRegistry registry = new ModelElementRegistry();
        registry.register(qName, className);

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

    @Test
    void testDiscovery() {
        ModelElementRegistry registry = new ModelElementRegistry();
        registry.register("elementName", "com.gs.dmn.runtime.discovery.ExecutableElement");

        ExecutableDRGElement element = registry.discover("elementName");
        assertNotNull(element);
    }

    @Test
    void testDiscoverWhenNotRegistered() {
        ModelElementRegistry registry = new ModelElementRegistry();

        DMNRuntimeException exception = assertThrows(DMNRuntimeException.class, () -> registry.discover("elementName"));
        assertEquals("Element 'elementName' is not registered. Registered elements are []", exception.getMessage());
    }

    @Test
    void testDiscoverWhenRegisteredClassDoesNotExist() {
        ModelElementRegistry registry = new ModelElementRegistry();
        registry.register("elementName", "com.gs.dmn.runtime.MissingExecutableElement");

        DMNRuntimeException exception = assertThrows(DMNRuntimeException.class, () -> registry.discover("elementName"));
        assertEquals("Cannot instantiate class 'com.gs.dmn.runtime.MissingExecutableElement' for name 'elementName'", exception.getMessage());
    }
}

class ExecutableElement implements ExecutableDRGElement {
    public ExecutableElement() {
    }

    @Override
    public Object applyMap(Map<String, String> input_, ExecutionContext context_) {
        return "123";
    }
}
