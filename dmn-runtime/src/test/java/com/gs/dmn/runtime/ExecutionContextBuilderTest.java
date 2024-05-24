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
package com.gs.dmn.runtime;

import com.gs.dmn.runtime.annotation.AnnotationSet;
import com.gs.dmn.runtime.cache.Cache;
import com.gs.dmn.runtime.cache.DefaultCache;
import com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor;
import com.gs.dmn.runtime.external.ExternalFunctionExecutor;
import com.gs.dmn.runtime.listener.EventListener;
import com.gs.dmn.runtime.listener.NopEventListener;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExecutionContextBuilderTest {
    @Test
    void testBuild() {
        AnnotationSet annotations = new AnnotationSet();
        EventListener eventListener = new NopEventListener();
        ExternalFunctionExecutor externalFunctionExecutor = new DefaultExternalFunctionExecutor();
        Cache cache = new DefaultCache();
        ExecutionContext context = ExecutionContextBuilder.executionContext().
                withAnnotations(annotations).
                withEventListener(eventListener).
                withExternalFunctionExecutor(externalFunctionExecutor).
                withCache(cache).
                build();

        assertEquals(annotations, context.getAnnotations());
        assertEquals(eventListener, context.getEventListener());
        assertEquals(externalFunctionExecutor, context.getExternalFunctionExecutor());
        assertEquals(cache, context.getCache());
    }
}