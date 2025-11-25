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
package com.gs.dmn.runtime;

import com.gs.dmn.runtime.discovery.ModelElementRegistry;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExecutorTest {
    @Test
    void testExecute() {
        ModelElementRegistry registry = new ModelElementRegistry();
        registry.register("className", "com.gs.dmn.runtime.ExecutableElement");

        Executor executor = new Executor(registry);
        Object result = executor.execute("className", null, null);
        assertEquals("123", result);
    }

    @Test
    void testExecuteWhenNotRegistered() {
        ModelElementRegistry registry = new ModelElementRegistry();

        Executor executor = new Executor(registry);
        DMNRuntimeException exception = assertThrows(DMNRuntimeException.class, () -> executor.execute("className", null, null));
        assertEquals("Element 'className' is not registered. Registered elements are []", exception.getMessage());
    }

    @Test
    void testExecuteWhenRegisteredClassDoesNotExist() {
        ModelElementRegistry registry = new ModelElementRegistry();
        registry.register("className", "com.gs.dmn.runtime.MissingExecutableElement");

        Executor executor = new Executor(registry);
        DMNRuntimeException exception = assertThrows(DMNRuntimeException.class, () -> executor.execute("className", null, null));
        assertEquals("Cannot instantiate class 'com.gs.dmn.runtime.MissingExecutableElement' for name 'className'", exception.getMessage());
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