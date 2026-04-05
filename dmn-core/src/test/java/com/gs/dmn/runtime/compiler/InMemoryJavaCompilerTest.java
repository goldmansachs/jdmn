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

import com.gs.dmn.runtime.DMNRuntimeException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryJavaCompilerTest {
    @Test
    public void testCompile() throws Exception {
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
        assertNotNull(classBytes);
        assertEquals(2, classBytes.size(), "Expected 2 compiled classes");
        assertTrue(classBytes.containsKey(className), "Expected compiled class for " + className);
        assertTrue(classBytes.containsKey(testClassName), "Expected compiled class for " + testClassName);
    }

    @Test
    public void testCompileWithErrors() {
        String className = "com.example.demo.Demo";
        String classSource = """
            package com.example.demo;
            import org.junit.jupiter.api.Test;
            import static org.junit.jupiter.api.Assertions.*;
            public class Demo {
              int calcul ate(int x) { return x; }
            }
        """;
        Map<String, String> sources = new HashMap<>();
        sources.put(className, classSource);

        assertThrows(DMNRuntimeException.class, () -> {
            InMemoryJavaCompiler compiler = new InMemoryJavaCompiler();
            compiler.compile(sources);
        });
    }
}

