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

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.Pair;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.Map;

public abstract class SimpleDMNDialectTransformer<S, T> {
    protected final BuildLogger logger;
    protected final PrefixNamespaceMappings prefixNamespaceMappings;
    protected final DMNVersion sourceVersion;
    protected final DMNVersion targetVersion;

    public SimpleDMNDialectTransformer(BuildLogger logger, DMNVersion sourceVersion, DMNVersion targetVersion) {
        this.logger = logger;
        this.prefixNamespaceMappings = new PrefixNamespaceMappings();
        this.sourceVersion = sourceVersion;
        this.targetVersion = targetVersion;
    }

    public abstract Pair<T, PrefixNamespaceMappings> transformDefinitions(S sourceDefinitions);

    protected String transformImportType(String importType) {
        if (this.sourceVersion.getNamespace().equals(importType)) {
            importType = targetVersion.getNamespace();
        }
        return importType;
    }

    protected Object transform(Element extension) {
        Map<String, String> sourceMap = this.sourceVersion.getPrefixToNamespaceMap();
        Map<String, String> targetMap = this.targetVersion.getPrefixToNamespaceMap();

        Node clone = extension.cloneNode(true);
        NamedNodeMap attributes = clone.getAttributes();
        for (int i=0; i<attributes.getLength(); i++) {
            Node item = attributes.item(i);
            for (Map.Entry<String, String> sourceEntry: sourceMap.entrySet()) {
                String prefix = sourceEntry.getKey();
                String sourceNamespace = sourceEntry.getValue();
                if (sourceNamespace.equals(item.getNodeValue())) {
                    item.setNodeValue(targetMap.get(prefix));
                    break;
                }
            }
        }
        return clone;
    }
}
