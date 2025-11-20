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
package com.gs.dmn.runtime.discovery;

import com.gs.dmn.runtime.DMNRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ModelElementRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelElementRegistry.class);

    private final Map<String, String> map = new LinkedHashMap<>();

    public void register(String qName, String className) {
        Objects.requireNonNull(qName, "Missing qName");
        Objects.requireNonNull(className, "Missing class name");

        String value = map.get(qName);
        if (value == null) {
            map.put(qName, className);
        } else if (!value.equals(className)) {
            throw new DMNRuntimeException(String.format("Name '%s' is not unique", qName));
        } else {
            LOGGER.warn("Name {} and value {} were already registered", qName, className);
        }
    }

    public String discover(String qName) {
        return this.map.get(qName);
    }

    public Set<String> keys() {
        return this.map.keySet();
    }
}

