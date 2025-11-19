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
package com.gs.dmn.feel.analysis.syntax.ast.library;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class LibraryMemoizer {
    protected static final Logger LOGGER = LoggerFactory.getLogger(LibraryMemoizer.class);

    private final Set<String> configs = new LinkedHashSet<>();
    private final Map<String, ELLib> namespaceToLib = new LinkedHashMap<>();

    public void addConfig(String configPath) {
        this.configs.add(configPath);
    }

    public boolean containsConfig(String configPath) {
        return configs.contains(configPath);
    }

    public void addLib(ELLib lib) {
        if (lib != null && !StringUtils.isBlank(lib.getNamespace())) {
            this.namespaceToLib.putIfAbsent(lib.getNamespace(), lib);
        }
    }

    public ELLib getLib(String namespace) {
        return this.namespaceToLib.get(namespace);
    }

    public Collection<ELLib> getLibraries() {
        return this.namespaceToLib.values();
    }
}
