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
package com.gs.dmn.runtime.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultCache implements Cache {
    private static Logger LOGGER = LoggerFactory.getLogger(DefaultCache.class);

    private final Map<String, Object> bindings = new LinkedHashMap<>();

    @Override
    public boolean contains(String key) {
        return bindings.keySet().contains(key);
    }

    @Override
    public void bind(String key, Object value) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Bind '%s' to '%s'", key, value));
        }

        bindings.put(key, value);
    }

    @Override
    public Object lookup(String key) {
        Object value = bindings.get(key);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Retrieve '%s' = '%s'", key, value));
        }

        return value;
    }

    @Override
    public void clear() {
        bindings.clear();
    }
}
