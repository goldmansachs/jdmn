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
    void testRun() throws Exception {
        String className = "com.example.demo.Demo";
        String classSource = """
            package com.example.demo;
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
        TestRunResult result = runTests(className, classSource, testClassName, testClassSource);

        // Expect 2 tests, 1 success, 1 failure
        assertEquals(2, result.getTestsFound(), "testsFound");
        assertEquals(1, result.getTestsSucceeded(), "testsSucceeded");
        assertEquals(1, result.getTestsFailed(), "testsFailed");
        TestFailure actualFailure = result.getFailures().get(0);
        assertEquals("com.example.demo.DemoTest", actualFailure.getClassName());
        assertEquals("fails", actualFailure.getMethodName());
        assertEquals("expected: <2> but was: <1>", actualFailure.getMessage());
        assertEquals("", actualFailure.getTestCasesName());
        assertEquals("", actualFailure.getTestCaseId());
    }

    @Test
    void testRunWithAnnotations() throws Exception {
        String className = "com.example.demo.Demo";
        String classSource = """
            package com.example.demo;
            public class Demo {
              int calculate(int x) { return x; }
            }
        """;
        String testClassName = "com.example.demo.DemoTest";
        String testClassSource = """
            package com.example.demo;
            import org.junit.jupiter.api.Test;
            import static org.junit.jupiter.api.Assertions.*;
            @com.gs.dmn.runtime.annotation.TestCases(
               testCasesName = "0004-lending-test-01",
               modelName = "0004-lending.dmn"
            )
            public class DemoTest {
              Demo demo = new Demo();
              @com.gs.dmn.runtime.annotation.TestCase(id = "001", resultNode = "node1")
              @Test void succeeds() { assertTrue(1 == demo.calculate(1)); }
              @com.gs.dmn.runtime.annotation.TestCase(id = "002", resultNode = "node2")
              @Test void fails() { assertEquals(2, demo.calculate(1)); }
            }
        """;
        TestRunResult result = runTests(className, classSource, testClassName, testClassSource);

        // Expect 2 tests, 1 success, 1 failure
        assertEquals(2, result.getTestsFound(), "testsFound");
        assertEquals(1, result.getTestsSucceeded(), "testsSucceeded");
        assertEquals(1, result.getTestsFailed(), "testsFailed");
        TestFailure actualFailure = result.getFailures().get(0);
        assertEquals("com.example.demo.DemoTest", actualFailure.getClassName());
        assertEquals("fails", actualFailure.getMethodName());
        assertEquals("expected: <2> but was: <1>", actualFailure.getMessage());
        assertEquals("0004-lending-test-01", actualFailure.getTestCasesName());
        assertEquals("002", actualFailure.getTestCaseId());
    }

    private TestRunResult runTests(String className, String classSource, String testClassName, String testClassSource) throws Exception {
        Map<String, String> sources = makeSources(className, classSource, testClassName, testClassSource);

        InMemoryJavaCompiler compiler = new InMemoryJavaCompiler();
        Map<String, byte[]> classBytes = compiler.compile(sources);
        InMemoryJUnitRunner runner = new InMemoryJUnitRunner();
        return runner.run(sources.keySet(), classBytes);
    }

    private Map<String, String> makeSources(String className, String classSource, String testClassName, String testClassSource) {
        Map<String, String> sources = new HashMap<>();
        sources.put(className, classSource);
        sources.put(testClassName, testClassSource);
        return sources;
    }
}

