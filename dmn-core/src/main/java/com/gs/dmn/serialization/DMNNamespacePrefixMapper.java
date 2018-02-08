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
package com.gs.dmn.serialization;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Octavian Patrascoiu on 12/08/2016.
 */
public class DMNNamespacePrefixMapper extends NamespacePrefixMapper {
    public static final String DMN_NS = "http://www.omg.org/spec/DMN/20151101/dmn.xsd";
    public static final String TCK_NS = "http://www.omg.org/spec/DMN/20160719/testcase";
    public static final String XSD_NS = "http://www.w3.org/2001/XMLSchema";
    public static final String FEEL_NS = "http://www.omg.org/spec/FEEL/20140401";

    private final String userNamespace;
    private final String userPrefix;

    private final Map<String, String> namespaceMap = new HashMap<>();

    public DMNNamespacePrefixMapper() {
        this.userNamespace = null;
        this.userPrefix = null;

        this.namespaceMap.put(XSD_NS, "xsd");
        this.namespaceMap.put(FEEL_NS, "feel");
        if (!StringUtils.isEmpty(userNamespace) && !StringUtils.isEmpty(userPrefix)) {
            this.namespaceMap.put(userNamespace, userPrefix);
        }
    }

    public DMNNamespacePrefixMapper(String namespace, String prefix) {
        this.userNamespace = namespace;
        this.userPrefix = prefix;

        this.namespaceMap.put(XSD_NS, "xsd");
        this.namespaceMap.put(FEEL_NS, "feel");
        if (!StringUtils.isEmpty(userNamespace) && !StringUtils.isEmpty(userPrefix)) {
            this.namespaceMap.put(userNamespace, userPrefix);
        }
    }

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        return namespaceMap.getOrDefault(namespaceUri, suggestion);
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return namespaceMap.keySet().toArray(new String[]{});
    }

    public String getUserNamespace() {
        return userNamespace;
    }

    public String getUserPrefix() {
        return userPrefix;
    }
}
