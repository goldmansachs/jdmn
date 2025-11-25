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

class JavaExternalFunctionTest {
    private final ExternalFunctionExecutor executor = new DefaultExternalFunctionExecutor();

    private final String testClass = "com.gs.dmn.runtime.external.ExternalClass";
    private final String instanceMethod = "instanceMethod";
    private final String staticMethod = "staticMethod";
    private final List<String> instanceParameterTypes = Arrays.asList("byte", "short", "int", "float", "double", "char", "java.lang.String");
    private final List<String> staticParameterTypes = Arrays.asList("int", "double");

    @Test
    void testApplyForInstanceMethod() {
        JavaFunctionInfo info = new JavaFunctionInfo(testClass, instanceMethod, instanceParameterTypes);
        JavaExternalFunction<String> function = new JavaExternalFunction<>(info, executor, String.class);

        String result = function.apply(1, 2, 3, 4.0, 5.0, 'a', "str");
        assertEquals("15.0-a-str", result);
    }

    @Test
    void testApplyForStaticMethod() {
        JavaFunctionInfo info = new JavaFunctionInfo(testClass, staticMethod, staticParameterTypes);
        JavaExternalFunction<Number> function = new JavaExternalFunction<>(info, executor, Number.class);

        Number result = function.apply(1, 3.0);
        assertEquals(-2.0, result);
    }
}

class ExternalClass {
    static double staticMethod(int a, double b) {
        return a - b;
    }

    String instanceMethod(byte a, short b, int c, float d, double e, char ch, String str) {
        return String.format("%s-%s-%s", a + b + c + d + e, ch, str);
    }
}