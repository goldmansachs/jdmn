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
    public static final LinkedHashMap<String, String> DMN_11_OTHER_NAMESPACES = new LinkedHashMap<>();
    public static final DMNVersion DMN_11 = new DMNVersion("1.1", "dmn/1.1/dmn.xsd",
            null, "http://www.omg.org/spec/DMN/20151101/dmn.xsd",
            "feel", "http://www.omg.org/spec/FEEL/20140401",
            DMN_11_OTHER_NAMESPACES,
            "org.omg.spec.dmn._20151101.model"
    );

    public static final LinkedHashMap<String, String> DMN_12_OTHER_NAMESPACES = new LinkedHashMap<>();
    static {
        DMN_12_OTHER_NAMESPACES.put("http://www.omg.org/spec/DMN/20180521/DC/", "dc");
        DMN_12_OTHER_NAMESPACES.put("http://www.omg.org/spec/DMN/20180521/DMNDI/", "dmndi");
        DMN_12_OTHER_NAMESPACES.put("http://www.omg.org/spec/DMN/20180521/DI/", "di");
    }
    public static final DMNVersion DMN_12 = new DMNVersion("1.2", "dmn/1.2/dmn.xsd",
            null, "http://www.omg.org/spec/DMN/20180521/MODEL/",
            "feel", "http://www.omg.org/spec/DMN/20180521/FEEL/",
            DMN_12_OTHER_NAMESPACES,
            "org.omg.spec.dmn._20180521.model"
    );

    public static final DMNVersion LATEST = DMN_12;

    public static final List<DMNVersion> VALUES = Arrays.asList(DMN_11, DMN_12);

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
    private final Map<String, String> otherNamespaces;
    private final String javaPackage;
    private final LinkedHashMap<String, String> namespaceMap;

    DMNVersion(String version, String schemaLocation, String prefix, String namespace, String feelPrefix, String feelNamespace, Map<String, String> otherNamespaces, String javaPackage) {
        this.version = version;
        this.schemaLocation = schemaLocation;
        this.prefix = prefix;
        this.namespace = namespace;
        this.feelPrefix = feelPrefix;
        this.feelNamespace = feelNamespace;
        this.otherNamespaces = otherNamespaces;
        this.javaPackage = javaPackage;

        this.namespaceMap = new LinkedHashMap<>();
        addMap(namespace, prefix);
        this.namespaceMap.putAll(otherNamespaces);
        addMap(feelNamespace, feelPrefix);
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
