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

import com.gs.dmn.runtime.*;
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
        // register
        ModelElementRegistry registry = new ModelElementRegistry();
        registry.register(qName, className);

        // check keys
        Set<String> expectKeys = new LinkedHashSet<>(Collections.singletonList(qName));
        assertEquals(expectKeys, registry.keys());
    }

    @Test
    void testRegistrationForNulls() {
        // register
        ModelElementRegistry registry = new ModelElementRegistry();

        // check null arguments
        Assertions.assertThrows(DMNRuntimeException.class, () -> registry.register(null, className));
        Assertions.assertThrows(DMNRuntimeException.class, () -> registry.register(qName, null));
    }

    @Test
    void testRegistrationForExistingWithDifferentValue() {
        // register
        ModelElementRegistry registry = new ModelElementRegistry();
        registry.register(qName, "otherName");

        // check different class name
        Assertions.assertThrows(DMNRuntimeException.class, () -> registry.register(qName, className));
    }

    @Test
    void testRegistrationForExistingWithSameValue() {
        // register
        ModelElementRegistry registry = new ModelElementRegistry();
        registry.register(qName, className);

        // check if cached
        assertEquals(1, registry.keys().size());
        registry.register(qName, className);
        assertEquals(1, registry.keys().size());
    }

    @Test
    void testDiscoveryForDecision() {
        // register decision
        ModelElementRegistry registry = new ModelElementRegistry();
        registry.register("elementName", NopDecision.class.getName());

        // check discover decision
        ExecutableDRGElement<Object> element1 = registry.discover("elementName", Object.class);
        assertInstanceOf(NopDecision.class, element1);

        // check if it is cached
        ExecutableDRGElement<Object> element2 = registry.discover("elementName", Object.class);
        assertSame(element1, element2);
    }

    @Test
    void testDiscoveryForBKM() {
        // register bkm
        ModelElementRegistry registry = new ModelElementRegistry();
        registry.register("elementName", NopBKM.class.getName());

        // discover BKM
        ExecutableDRGElement<Object> element1 = registry.discover("elementName", Object.class);
        assertInstanceOf(NopBKM.class, element1);

        // check it is cached
        ExecutableDRGElement<Object> element2 = registry.discover("elementName", Object.class);
        assertSame(element1, element2);
    }

    @Test
    void testDiscoveryForDS() {
        // register DS
        ModelElementRegistry registry = new ModelElementRegistry();
        registry.register("elementName", NopDS.class.getName());

        // discover DS
        ExecutableDRGElement<Object> element1 = registry.discover("elementName", Object.class);
        assertInstanceOf(NopDS.class, element1);

        // check it is cached
        ExecutableDRGElement<Object> element2 = registry.discover("elementName", Object.class);
        assertSame(element1, element2);
    }

    @Test
    void testDiscoverSingletonForDecision() {
        // register decision
        ModelElementRegistry registry = new ModelElementRegistry();
        registry.register("elementName", NopDecision.class.getName());

        // check when singleton not generated
        DMNRuntimeException exception = assertThrows(DMNRuntimeException.class, () -> registry.discoverSingleton("elementName", Object.class));
        assertEquals("Cannot instantiate class '" + NopDecision.class.getName() + "' for name 'elementName'", exception.getMessage());
    }

    @Test
    void testDiscoverSingletonForBKM() {
        // register BKM
        ModelElementRegistry registry = new ModelElementRegistry();
        registry.register("elementName", NopBKM.class.getName());

        // check discovery
        ExecutableDRGElement<Object> element1 = registry.discoverSingleton("elementName", Object.class);
        assertInstanceOf(NopBKM.class, element1);

        // check it is cached
        ExecutableDRGElement<Object> element2 = registry.discoverSingleton("elementName", Object.class);
        assertSame(element1, element2);
    }

    @Test
    void testDiscoverSingletonForDS() {
        // register DS
        ModelElementRegistry registry = new ModelElementRegistry();
        registry.register("elementName", NopDS.class.getName());

        // discover DS
        ExecutableDRGElement<Object> element1 = registry.discoverSingleton("elementName", Object.class);
        assertInstanceOf(NopDS.class, element1);

        // check it is cached
        ExecutableDRGElement<Object> element2 = registry.discoverSingleton("elementName", Object.class);
        assertSame(element1, element2);
    }

    @Test
    void testDiscoverWhenNotRegistered() {
        // register
        ModelElementRegistry registry = new ModelElementRegistry();

        // check discover for not registered elements
        DMNRuntimeException exception = assertThrows(DMNRuntimeException.class, () -> registry.discover("elementName", Object.class));
        assertEquals("Element 'elementName' is not registered. Registered elements are []", exception.getMessage());
        exception = assertThrows(DMNRuntimeException.class, () -> registry.discoverSingleton("elementName", Object.class));
        assertEquals("Element 'elementName' is not registered. Registered elements are []", exception.getMessage());
    }

    @Test
    void testDiscoverWhenRegisteredClassDoesNotExist() {
        // register
        ModelElementRegistry registry = new ModelElementRegistry();
        registry.register("elementName", "com.gs.dmn.runtime.MissingExecutableElement");

        // check discover
        DMNRuntimeException exception = assertThrows(DMNRuntimeException.class, () -> registry.discover("elementName", Object.class));
        assertEquals("Cannot instantiate class 'com.gs.dmn.runtime.MissingExecutableElement' for name 'elementName'", exception.getMessage());
        exception = assertThrows(DMNRuntimeException.class, () -> registry.discoverSingleton("elementName", Object.class));
        assertEquals("Cannot instantiate class 'com.gs.dmn.runtime.MissingExecutableElement' for name 'elementName'", exception.getMessage());
    }

    @Test
    void testDiscoverWhenClassIsNotDMNElement() {
        // register
        ModelElementRegistry registry = new ModelElementRegistry();
        registry.register("elementName", "com.gs.dmn.runtime.discovery.ExecutableElement");

        // check discover
        DMNRuntimeException exception = assertThrows(DMNRuntimeException.class, () -> registry.discover("elementName", Object.class));
        assertEquals("Cannot instantiate class 'com.gs.dmn.runtime.discovery.ExecutableElement' for name 'elementName'", exception.getMessage());
        exception = assertThrows(DMNRuntimeException.class, () -> registry.discoverSingleton("elementName", Object.class));
        assertEquals("Cannot instantiate class 'com.gs.dmn.runtime.discovery.ExecutableElement' for name 'elementName'", exception.getMessage());
    }

}

class ExecutableElement implements ExecutableDRGElement<Object> {
    public ExecutableElement() {
    }

    @Override
    public Object applyMap(Map<String, String> input_, ExecutionContext context_) {
        return "123";
    }

    @Override
    public Object applyPojo(ExecutableDRGElementInput input_, ExecutionContext context_) {
        return null;
    }

    @Override
    public Object applyContext(Context input_, ExecutionContext context_) {
        return null;
    }
}
