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
package com.gs.dmn.runtime.annotation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnnotationTest {
    private final Annotation annotation = new Annotation("Decision Name", 0, "text");

    @Test
    public void testProperties() {
        assertEquals("Decision Name", annotation.getDecisionName());
        assertEquals(0, annotation.getRuleIndex());
        assertEquals("text", annotation.getAnnotation());
    }

    @Test
    public void testSerialization() {
        assertEquals("Annotation('Decision Name', 0, 'text')", annotation.toString());
    }

    @Test
    public void testEquals() {
        assertTrue(annotation.equals(annotation));
        assertTrue(annotation.equals(new Annotation("Decision Name", 0, "text")));
    }
}