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

public final class ExecutionContextBuilder {
    private AnnotationSet annotations = new AnnotationSet();
    private EventListener eventListener = new NopEventListener();
    private ExternalFunctionExecutor externalFunctionExecutor = new DefaultExternalFunctionExecutor();
    private Cache cache = new DefaultCache();

    private ExecutionContextBuilder() {
    }

    public static ExecutionContextBuilder executionContext() {
        return new ExecutionContextBuilder();
    }

    public ExecutionContextBuilder withAnnotations(AnnotationSet annotations) {
        this.annotations = annotations;
        return this;
    }

    public ExecutionContextBuilder withEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
        return this;
    }

    public ExecutionContextBuilder withExternalFunctionExecutor(ExternalFunctionExecutor externalFunctionExecutor) {
        this.externalFunctionExecutor = externalFunctionExecutor;
        return this;
    }

    public ExecutionContextBuilder withCache(Cache cache) {
        this.cache = cache;
        return this;
    }

    public ExecutionContext build() {
        return new ExecutionContext(annotations, eventListener, externalFunctionExecutor, cache);
    }
}
