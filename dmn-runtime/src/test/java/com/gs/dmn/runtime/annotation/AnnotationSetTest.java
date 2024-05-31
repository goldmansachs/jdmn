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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnnotationSetTest {
    @Test
    public void testThreshold() {
        AnnotationSet set = new AnnotationSet(2);
        set.addAnnotation("Name", 0, "text1");
        set.addAnnotation("Name", 0, "text2");

        assertEquals(2, set.size());

        set.addAnnotation("Name", 0, "text3");

        assertEquals(3, set.size());
        assertEquals("text1", set.get(0).getAnnotation());
        assertEquals("text2", set.get(1).getAnnotation());
        assertEquals("Too many annotations, maximum number is 2", set.get(2).getAnnotation());
    }

    @Test
    public void testIsSet() {
        AnnotationSet set = new AnnotationSet();
        set.addAnnotation("Name", 0, "text");
        set.addAnnotation("Name", 0, "text");

        assertEquals(2, set.size());
        assertEquals(1, set.toSet().size());
    }

}