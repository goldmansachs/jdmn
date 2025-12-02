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
import com.gs.dmn.runtime.external.ExternalFunctionExecutor;
import com.gs.dmn.runtime.listener.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutionContext {
    static final Logger LOGGER = LoggerFactory.getLogger(ExecutionContext.class);

    private final AnnotationSet annotations;
    private final EventListener eventListener;
    private final ExternalFunctionExecutor externalFunctionExecutor;
    private final Cache cache;

    ExecutionContext(AnnotationSet annotations, EventListener eventListener, ExternalFunctionExecutor externalFunctionExecutor, Cache cache) {
        this.annotations = annotations;
        this.eventListener = eventListener;
        this.externalFunctionExecutor = externalFunctionExecutor;
        this.cache = cache;
    }

    public AnnotationSet getAnnotations() {
        return annotations;
    }

    public EventListener getEventListener() {
        return eventListener;
    }

    public ExternalFunctionExecutor getExternalFunctionExecutor() {
        return externalFunctionExecutor;
    }

    public Cache getCache() {
        return cache;
    }
}
