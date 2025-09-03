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
package com.gs.dmn.transformation.proto;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.JavaTimeDMNDialectDefinition;
import com.gs.dmn.feel.analysis.semantics.type.FEELType;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProtoBufferJavaFactoryTest {
    private final JavaTimeDMNDialectDefinition dialect = new JavaTimeDMNDialectDefinition();
    private final ProtoBufferFactory factory = new ProtoBufferJavaFactory(new BasicDMNToJavaTransformer(dialect, new DMNModelRepository(), new NopLazyEvaluationDetector(), new InputParameters()));

    @Test
    public void testProtoType() {
        assertEquals("double", factory.toProtoType("number"));
        assertEquals("string", factory.toProtoType("string"));
        assertEquals("bool", factory.toProtoType("boolean"));
        assertEquals("string", factory.toProtoType("date"));
        assertEquals("string", factory.toProtoType("time"));
        assertEquals("string", factory.toProtoType("date and time"));
        assertEquals("string", factory.toProtoType("datetime"));
        assertEquals("string", factory.toProtoType("dateTime"));
        assertEquals("string", factory.toProtoType("years and months duration"));
        assertEquals("string", factory.toProtoType("yearMonthDuration"));
        assertEquals("string", factory.toProtoType("days and time duration"));
        assertEquals("string", factory.toProtoType("dayTimeDuration"));
        assertEquals("string", factory.toProtoType("enumeration"));

        assertNull(factory.toProtoType("Any"));
        assertNull(factory.toProtoType("Null"));
        assertEquals("string", factory.toProtoType("temporal"));
        assertEquals("string", factory.toProtoType("duration"));
        assertEquals("string", factory.toProtoType("comparable"));
        assertNull(factory.toProtoType("context"));
    }

    @Test
    public void testToNativeType() {
        assertEquals("Double", factory.toNativeProtoType("number"));
        assertEquals("String", factory.toNativeProtoType("string"));
        assertEquals("Boolean", factory.toNativeProtoType("boolean"));
        assertEquals("String", factory.toNativeProtoType("date"));
        assertEquals("String", factory.toNativeProtoType("time"));
        assertEquals("String", factory.toNativeProtoType("date and time"));
        assertEquals("String", factory.toNativeProtoType("datetime"));
        assertEquals("String", factory.toNativeProtoType("dateTime"));
        assertEquals("String", factory.toNativeProtoType("years and months duration"));
        assertEquals("String", factory.toNativeProtoType("yearMonthDuration"));
        assertEquals("String", factory.toNativeProtoType("days and time duration"));
        assertEquals("String", factory.toNativeProtoType("dayTimeDuration"));
        assertEquals("String", factory.toNativeProtoType("enumeration"));

        assertEquals("Object", factory.toNativeProtoType("Any"));
        assertEquals("Object", factory.toNativeProtoType("Null"));
        assertEquals("String", factory.toNativeProtoType("temporal"));
        assertEquals("String", factory.toNativeProtoType("duration"));
        assertEquals("Object", factory.toNativeProtoType("comparable"));
        assertEquals("Object", factory.toNativeProtoType("context"));
    }

    @Test
    public void testMappings() {
        assertEquals(ProtoBufferJavaFactory.FEEL_TYPE_TO_PROTO_TYPE.keySet().size(), FEELType.FEEL_PRIMITIVE_TYPES.size());
        assertEquals(ProtoBufferJavaFactory.FEEL_TYPE_TO_NATIVE_PROTO_TYPE.keySet().size(), FEELType.FEEL_PRIMITIVE_TYPES.size());
    }
}