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
package com.gs.dmn.serialization;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TCKVersion {
    protected static final LinkedHashMap<String, String> TCK_1_OTHER_NAMESPACES = new LinkedHashMap<>();
    public static final TCKVersion TCK_1 = new TCKVersion("1", "tck/testCases.xsd",
            null, "",
            TCK_1_OTHER_NAMESPACES,
            "org.omg.dmn.tck.marshaller._20160719"
    );

    public static final TCKVersion LATEST = TCK_1;

    protected static final List<TCKVersion> VALUES = Arrays.asList(TCK_1);

    public static TCKVersion fromVersion(String key) {
        for (TCKVersion version: VALUES) {
            if (version.version.equals(key)) {
                return  version;
            }
        }
        throw new IllegalArgumentException(String.format("Cannot find TCK version '%s'", key));
    }

    private final String version;
    private final String schemaLocation;
    private final String prefix;
    private final String namespace;
    private final Map<String, String> otherNamespaces;
    private final String javaPackage;
    private final LinkedHashMap<String, String> namespaceMap;

    TCKVersion(String version, String schemaLocation, String prefix, String namespace, Map<String, String> otherNamespaces, String javaPackage) {
        this.version = version;
        this.schemaLocation = schemaLocation;
        this.prefix = prefix;
        this.namespace = namespace;
        this.otherNamespaces = otherNamespaces;
        this.javaPackage = javaPackage;

        this.namespaceMap = new LinkedHashMap<>();
        addMap(namespace, prefix);
        this.namespaceMap.putAll(otherNamespaces);
    }

    public String getVersion() {
        return version;
    }

    public String getSchemaLocation() {
        return schemaLocation;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getNamespace() {
        return namespace;
    }

    public Map<String, String> getNamespaceMap() {
        return this.namespaceMap;
    }

    public String getJavaPackage() {
        return javaPackage;
    }

    private void addMap(String namespace, String prefix) {
        if (!StringUtils.isEmpty(prefix)) {
            this.namespaceMap.put(namespace, prefix);
        }
    }
}
