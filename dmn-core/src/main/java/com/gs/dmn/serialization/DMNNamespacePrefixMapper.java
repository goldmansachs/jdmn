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

import static com.gs.dmn.serialization.DMNConstants.*;

public class DMNNamespacePrefixMapper extends NamespacePrefixMapper {
    private final String userNamespace;
    private final String userPrefix;

    private final Map<String, String> namespaceMap = new HashMap<>();

    public DMNNamespacePrefixMapper() {
        this(null, null);
    }

    public DMNNamespacePrefixMapper(String namespace, String prefix) {
        this.userNamespace = namespace;
        this.userPrefix = prefix;

        this.namespaceMap.put(XSD_NS, XSD_PREFIX);
        this.namespaceMap.put(FEEL_11_NS, FEEL_11_PREFIX);
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
