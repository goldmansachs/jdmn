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
package com.gs.dmn.runtime.compiler;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryJUnitRunnerTest {
    @Test
    public void demoRun() throws Exception {
        String className = "com.example.demo.Demo";
        String classSource = """
            package com.example.demo;
            import org.junit.jupiter.api.Test;
            import static org.junit.jupiter.api.Assertions.*;
            public class Demo {
              int calculate(int x) { return x; }
            }
        """;
        String testClassName = "com.example.demo.DemoTest";
        String testClassSource = """
            package com.example.demo;
            import org.junit.jupiter.api.Test;
            import static org.junit.jupiter.api.Assertions.*;
            public class DemoTest {
              Demo demo = new Demo();
              @Test void succeeds() { assertTrue(1 == demo.calculate(1)); }
              @Test void fails() { assertEquals(2, demo.calculate(1)); }
            }
        """;

        Map<String, String> sources = new HashMap<>();
        sources.put(className, classSource);
        sources.put(testClassName, testClassSource);

        InMemoryJavaCompiler compiler = new InMemoryJavaCompiler();
        Map<String, byte[]> classBytes = compiler.compile(sources);
        InMemoryJUnitRunner runner = new InMemoryJUnitRunner();
        TestRunResult result = runner.run(sources.keySet(), classBytes);

        // Expect 2 tests, 1 success, 1 failure
        assertEquals(2, result.getTestsFound(), "testsFound");
        assertEquals(1, result.getTestsSucceeded(), "testsSucceeded");
        assertEquals(1, result.getTestsFailed(), "testsFailed");
        TestFailure actualFailure = result.getFailures().get(0);
        assertEquals("com.example.demo.DemoTest", actualFailure.getClassName());
        assertEquals("fails()", actualFailure.getMethodName());
        assertEquals("expected: <2> but was: <1>", actualFailure.getMessage());
    }
}

