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

import java.util.*;

public class TCKVersion {
    protected static final LinkedHashMap<String, String> TCK_1_OTHER_NAMESPACES = new LinkedHashMap<>();
    static {
        TCK_1_OTHER_NAMESPACES.put(DMNConstants.XSD_NS, DMNConstants.XSD_PREFIX);
        TCK_1_OTHER_NAMESPACES.put(DMNConstants.XSI_NS, DMNConstants.XSI_PREFIX);
    }

    public static final TCKVersion TCK_1 = new TCKVersion("1", "tck/testCases.xsd",
            "", "http://www.omg.org/spec/DMN/20160719/testcase",
            TCK_1_OTHER_NAMESPACES,
            "org.omg.dmn.tck.marshaller._20160719"
    );

    public static final TCKVersion LATEST = TCK_1;

    protected static final List<TCKVersion> VALUES = Collections.singletonList(TCK_1);

    public static TCKVersion fromVersion(String key) {
        for (TCKVersion version : VALUES) {
            if (version.version.equals(key)) {
                return version;
            }
        }
        throw new IllegalArgumentException(String.format("Cannot find TCK version '%s'", key));
    }

    private final String version;
    private final String schemaLocation;
    private final String prefix;
    private final String namespace;
    private final Map<String, String> namespaceToPrefixMap;
    private final Map<String, String> prefixToNamespaceMap;
    private final String javaPackage;

    TCKVersion(String version, String schemaLocation, String prefix, String namespace, Map<String, String> otherNamespaces, String javaPackage) {
        this.version = version;
        this.schemaLocation = schemaLocation;
        this.prefix = prefix;
        this.namespace = namespace;
        this.javaPackage = javaPackage;

        this.namespaceToPrefixMap = new LinkedHashMap<>();
        this.prefixToNamespaceMap = new LinkedHashMap<>();
        addMap(namespace, prefix);
        for (Map.Entry<String, String> entry : otherNamespaces.entrySet()) {
            String otherNamespace = entry.getKey();
            String otherPrefix = entry.getValue();
            addMap(otherNamespace, otherPrefix);
        }
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

    public Map<String, String> getNamespaceToPrefixMap() {
        return this.namespaceToPrefixMap;
    }

    public Map<String, String> getPrefixToNamespaceMap() {
        return prefixToNamespaceMap;
    }

    public String getJavaPackage() {
        return javaPackage;
    }

    private void addMap(String namespace, String prefix) {
        if (StringUtils.isEmpty(namespace)) {
            throw new IllegalArgumentException("Namespace cannot be null or empty");
        }
        if (prefix == null) {
            prefix = "";
        }
        this.namespaceToPrefixMap.put(namespace, prefix);
        this.prefixToNamespaceMap.put(prefix, namespace);
    }
}
