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
package com.gs.dmn;

import com.gs.dmn.runtime.ExecutionContext;
import com.gs.dmn.runtime.annotation.AnnotationSet;
import com.gs.dmn.runtime.cache.Cache;
import com.gs.dmn.runtime.cache.DefaultCache;
import com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor;
import com.gs.dmn.runtime.external.ExternalFunctionExecutor;
import com.gs.dmn.runtime.listener.EventListener;
import com.gs.dmn.runtime.listener.NopEventListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

public abstract class AbstractHandwrittenDecisionTest {
    protected AnnotationSet annotationSet;
    protected EventListener eventListener;
    protected ExternalFunctionExecutor externalFunctionExecutor;
    protected Cache cache;
    protected ExecutionContext context;

    protected abstract void applyDecision();

    @Test
    public void testPerformance() {
        long before = System.currentTimeMillis();
        applyDecision();
        long after = System.currentTimeMillis();
        assertTrue("Takes longer than 500ms", after - before < 500);
    }

    @BeforeEach
    public void setUp() {
        this.annotationSet = new AnnotationSet();
        this.eventListener = new NopEventListener();
        this.externalFunctionExecutor = new DefaultExternalFunctionExecutor();
        this.cache = new DefaultCache();
        this.context = new ExecutionContext(annotationSet, eventListener, externalFunctionExecutor, cache);
    }
}
