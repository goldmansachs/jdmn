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
package com.gs.dmn.runtime.external;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultExternalFunctionExecutorTest {
    private final ExternalFunctionExecutor executor = new DefaultExternalFunctionExecutor();

    private final String testClass = "com.gs.dmn.runtime.external.TestClass";
    private final String instanceMethod = "instanceMethod";
    private final String staticMethod = "staticMethod";
    private final List<String> parameterTypes = Arrays.asList("int", "double");
    private final List<Object> args = Arrays.asList(
            1, 3.0
    );

    @Test
    void testExecuteInstanceMethod() {
        JavaFunctionInfo info = new JavaFunctionInfo(testClass, instanceMethod, parameterTypes);
        Object result = executor.execute(info, args);
        assertEquals(4.0, result);
    }

    @Test
    void testExecuteStaticMethod() {
        JavaFunctionInfo info = new JavaFunctionInfo(testClass, staticMethod, parameterTypes);
        Object result = executor.execute(info, args);
        assertEquals(-2.0, result);
    }

    @Test
    void testExecuteWhenIncorrectClassName() {
        JavaFunctionInfo info = new JavaFunctionInfo("MissingClass", instanceMethod, parameterTypes);
        Object result = executor.execute(info, args);
        assertNull(result);
    }

    @Test
    void testExecuteWhenIncorrectMethodName() {
        JavaFunctionInfo info = new JavaFunctionInfo(testClass, "otherMethod", parameterTypes);
        Object result = executor.execute(info, args);
        assertNull(result);
    }

    @Test
    void testExecuteWhenIncorrectNumberOfParameters() {
        JavaFunctionInfo info = new JavaFunctionInfo(testClass, instanceMethod, Arrays.asList("int", "String", "String"));
        Object result = executor.execute(info, args);
        assertNull(result);
    }

    @Test
    void testExecuteWhenIncorrectParameters() {
        JavaFunctionInfo info = new JavaFunctionInfo(testClass, instanceMethod, Arrays.asList("int", "String"));
        Object result = executor.execute(info, args);
        assertNull(result);
    }

    @Test
    void testExecuteWhenIncorrectNumberOfArguments() {
        JavaFunctionInfo info = new JavaFunctionInfo(testClass, instanceMethod, parameterTypes);
        Object result = executor.execute(info, Arrays.asList(1, 3, 5));
        assertNull(result);
    }

    @Test
    void testExecuteWhenIncorrectArguments() {
        JavaFunctionInfo info = new JavaFunctionInfo(testClass, instanceMethod, parameterTypes);
        Object result = executor.execute(info, Arrays.asList(1, "abc"));
        assertNull(result);
    }
}

class TestClass {
    static double staticMethod(int a, double b) {
        return a - b;
    }

    double instanceMethod(int a, double b) {
        return a + b;
    }
}