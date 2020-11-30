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

public class DMNVersion {
    protected static final LinkedHashMap<String, String> DMN_11_OTHER_NAMESPACES = new LinkedHashMap<>();
    public static final DMNVersion DMN_11 = new DMNVersion("1.1", "dmn/1.1/dmn.xsd",
            null, "http://www.omg.org/spec/DMN/20151101/dmn.xsd",
            "feel", "http://www.omg.org/spec/FEEL/20140401",
            DMN_11_OTHER_NAMESPACES,
            "org.omg.spec.dmn._20151101.model"
    );

    protected static final LinkedHashMap<String, String> DMN_12_OTHER_NAMESPACES = new LinkedHashMap<>();
    static {
        DMN_12_OTHER_NAMESPACES.put("http://www.omg.org/spec/DMN/20180521/DC/", "dc");
        DMN_12_OTHER_NAMESPACES.put("http://www.omg.org/spec/DMN/20180521/DMNDI/", "dmndi");
        DMN_12_OTHER_NAMESPACES.put("http://www.omg.org/spec/DMN/20180521/DI/", "di");
    }
    public static final DMNVersion DMN_12 = new DMNVersion("1.2", "dmn/1.2/DMN12.xsd",
            null, "http://www.omg.org/spec/DMN/20180521/MODEL/",
            "feel", "http://www.omg.org/spec/DMN/20180521/FEEL/",
            DMN_12_OTHER_NAMESPACES,
            "org.omg.spec.dmn._20180521.model"
    );

    protected static final LinkedHashMap<String, String> DMN_13_OTHER_NAMESPACES = new LinkedHashMap<>();
    static {
        DMN_13_OTHER_NAMESPACES.put("http://www.omg.org/spec/DMN/20180521/DC/", "dc");
        DMN_13_OTHER_NAMESPACES.put("https://www.omg.org/spec/DMN/20191111/DMNDI/", "dmndi");
        DMN_13_OTHER_NAMESPACES.put("http://www.omg.org/spec/DMN/20180521/DI/", "di");
    }
    public static final DMNVersion DMN_13 = new DMNVersion("1.3", "dmn/1.3/DMN13.xsd",
            null, "https://www.omg.org/spec/DMN/20191111/MODEL/",
            "feel", "https://www.omg.org/spec/DMN/20191111/FEEL/",
            DMN_13_OTHER_NAMESPACES,
            "org.omg.spec.dmn._20191111.model"
    );

    public static final DMNVersion LATEST = DMN_13;

    protected static final List<DMNVersion> VALUES = Arrays.asList(DMN_11, DMN_12, DMN_13);

    public static DMNVersion fromVersion(String key) {
        for (DMNVersion version: VALUES) {
            if (version.version.equals(key)) {
                return  version;
            }
        }
        throw new IllegalArgumentException(String.format("Cannot find DMN version '%s'", key));
    }

    private final String version;
    private final String schemaLocation;
    private final String prefix;
    private final String namespace;
    private final String feelPrefix;
    private final String feelNamespace;
    private final String javaPackage;
    private final LinkedHashMap<String, String> namespaceToPrefixMap;
    private final LinkedHashMap<String, String> prefixToNamespaceMap;

    DMNVersion(String version, String schemaLocation, String prefix, String namespace, String feelPrefix, String feelNamespace, Map<String, String> otherNamespaces, String javaPackage) {
        this.version = version;
        this.schemaLocation = schemaLocation;
        this.prefix = prefix;
        this.namespace = namespace;
        this.feelPrefix = feelPrefix;
        this.feelNamespace = feelNamespace;
        this.javaPackage = javaPackage;

        this.namespaceToPrefixMap = new LinkedHashMap<>();
        this.prefixToNamespaceMap = new LinkedHashMap<>();
        addMap(namespace, prefix);
        addMap(feelNamespace, feelPrefix);
        for (Map.Entry<String, String> entry: otherNamespaces.entrySet()) {
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

    public String getFeelPrefix() {
        return feelPrefix;
    }

    public String getFeelNamespace() {
        return feelNamespace;
    }

    public Map<String, String> getNamespaceToPrefixMap() {
        return this.namespaceToPrefixMap;
    }

    public LinkedHashMap<String, String> getPrefixToNamespaceMap() {
        return prefixToNamespaceMap;
    }

    public String getJavaPackage() {
        return javaPackage;
    }

    private void addMap(String namespace, String prefix) {
        if (!StringUtils.isEmpty(namespace) && !StringUtils.isEmpty(prefix)) {
            this.namespaceToPrefixMap.put(namespace, prefix);
        }
        if (prefix == null) {
            prefix = "";
        }
        this.prefixToNamespaceMap.put(prefix, namespace);
    }
}
