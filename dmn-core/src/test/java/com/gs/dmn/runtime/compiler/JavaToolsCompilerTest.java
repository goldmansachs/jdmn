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

import org.junit.Test;

import static org.junit.Assert.*;

public class JavaToolsCompilerTest extends AbstractCompilerTest {
    @Test
    public void testMakeClassData() {
        ClassData classData = makeClassData();

        assertEquals(JavaxToolsClassData.class.getName(), classData.getClass().getName());
        assertEquals("com.gs.dmn.runtime", classData.getPackageName());
        String className = classData.getClassName();
        assertTrue(className.startsWith("LambdaExpression"));
        assertTrue(className.endsWith("Impl"));
        assertNotNull("", ((JavaxToolsClassData)classData).getClassText());
    }

    @Override
    @Test
    public void testCompile() throws Exception {
        Class<?> cls = getCompiler().compile(makeClassData());
        assertNotNull(cls);
    }

    @Override
    protected JavaCompiler getCompiler() {
        return new JavaxToolsCompiler();
    }
}