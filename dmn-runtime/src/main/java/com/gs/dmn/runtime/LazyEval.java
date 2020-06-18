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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * Lazily evaluate the supplied operations to reduce unnecessary computation
 *
 * @param <T> the type of value stored
 *
 */
public final class LazyEval<T> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(LazyEval.class);

    private T value;
    private boolean isValueSet = false;
    private final Supplier<T> supplier;

    public LazyEval(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T getOrCompute() {
        return isValueSet? value : compute();
    }

    private T compute() {
        LOGGER.info("Trigger lazy evaluation");

        isValueSet = true;
        return value = supplier.get();
    }
}