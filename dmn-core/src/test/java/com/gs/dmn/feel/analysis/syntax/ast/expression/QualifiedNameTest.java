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
package com.gs.dmn.feel.analysis.syntax.ast.expression;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QualifiedNameTest {
    @Test
    public void testPropertiesWhenNull() {
        QualifiedName qualifiedName = new QualifiedName(null);
        assertEquals("", qualifiedName.getName());
        assertEquals(Collections.emptyList(), qualifiedName.getNames());
        assertEquals("", qualifiedName.getQualifiedName());

        assertEquals("QualifiedName(, 0)", qualifiedName.toString());
    }

    @Test
    public void testProperties() {
        List<String> names = Arrays.asList("a", "b", "c");
        QualifiedName qualifiedName = new QualifiedName(names);
        assertEquals("a.b.c", qualifiedName.getName());
        assertEquals(names, qualifiedName.getNames());
        assertEquals("a.b.c", qualifiedName.getQualifiedName());

        assertEquals("QualifiedName(a.b.c, 3)", qualifiedName.toString());
    }
}