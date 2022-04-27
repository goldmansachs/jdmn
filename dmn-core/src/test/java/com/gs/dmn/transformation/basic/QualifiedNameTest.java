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
package com.gs.dmn.transformation.basic;

import com.gs.dmn.QualifiedName;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class QualifiedNameTest {
    @Test
    public void testConstructorWithNullString() {
        QualifiedName qualifiedName = QualifiedName.toQualifiedName(null, (String) null);
        assertNull(qualifiedName);
    }

    @Test
    public void testConstructorWithEmptyString() {
        QualifiedName qualifiedName = QualifiedName.toQualifiedName(null, "");
        assertNull(qualifiedName);
    }

    @Test
    public void testConstructorForFEELTypes() {
        QualifiedName qualifiedName = QualifiedName.toQualifiedName(null,"feel.string");
        assertEquals("feel", qualifiedName.getNamespace());
        assertEquals("string", qualifiedName.getLocalPart());
    }

    @Test
    public void testConstructorWhenMissingNamespace() {
        QualifiedName qualifiedName = QualifiedName.toQualifiedName(null,"abc");
        assertNull(qualifiedName.getNamespace());
        assertEquals("abc", qualifiedName.getLocalPart());
    }

    @Test
    public void testConstructorWhenDotInName() {
        QualifiedName qualifiedName = QualifiedName.toQualifiedName(null,"test.abc");
        assertNull(qualifiedName.getNamespace());
        assertEquals("test.abc", qualifiedName.getLocalPart());
    }
}