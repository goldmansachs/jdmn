/**
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

import org.junit.Test;

import javax.xml.namespace.QName;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class QualifiedNameTest {
    @Test
    public void testConstructorWithNamespace() {
        QualifiedName qualifiedName = new QualifiedName("abc");
        assertEquals(null, qualifiedName.getNamespace());
        assertEquals("abc", qualifiedName.getLocalPart());
    }

    @Test
    public void testConstructorWhenMissingNamespace() {
        QualifiedName qualifiedName = new QualifiedName("abc");
        assertEquals(null, qualifiedName.getNamespace());
        assertEquals("abc", qualifiedName.getLocalPart());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullString() {
        new QualifiedName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithEmpty() {
        new QualifiedName("");
    }

    @Test
    public void testFactoryWithNullQName() {
        assertNull(QualifiedName.toQualifiedName((QName) null));
        assertNull(QualifiedName.toQualifiedName((String) null));
    }
}