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
package com.gs.dmn.ast;

import com.gs.dmn.serialization.DMNVersion;

import javax.xml.namespace.QName;
import java.util.Map;

public class DMNVersionTransformerVisitor<C> extends TraversalVisitor<C> {
    private final DMNVersion sourceVersion;
    private final DMNVersion targetVersion;
    private TDefinitions definitions;

    public DMNVersionTransformerVisitor(DMNVersion sourceVersion, DMNVersion targetVersion) {
        this.sourceVersion = sourceVersion;
        this.targetVersion = targetVersion;
    }

    @Override
    public DMNBaseElement visit(TDefinitions element, C context) {
        definitions = element;
        // Update expression language
        if (this.sourceVersion.getFeelNamespace().equals(element.getTypeLanguage())) {
            element.setTypeLanguage(this.targetVersion.getFeelNamespace());
        }
        if (this.sourceVersion.getFeelNamespace().equals(element.getExpressionLanguage())) {
            element.setExpressionLanguage(this.targetVersion.getFeelNamespace());
        }
        // Update XML namespaces
        Map<String, String> nsContext = element.getElementInfo().getNsContext();
        for (Map.Entry<String, String> entry : nsContext.entrySet()) {
            // DMN namespace
            if (this.sourceVersion.getNamespace().equals(entry.getValue())) {
                entry.setValue(this.targetVersion.getNamespace());
            // FEEL namespace
            } else if (this.sourceVersion.getFeelNamespace().equals(entry.getValue())) {
                entry.setValue(this.targetVersion.getFeelNamespace());
            }
        }
        // Add missing DMNDI namespaces
        if (this.sourceVersion == DMNVersion.DMN_11) {
            for (Map.Entry<String, String> entry : this.targetVersion.getPrefixToNamespaceMap().entrySet()) {
                String key = entry.getKey();
                if ("dmndi".equals(key) || "di".equals(key) || "dc".equals(key)) {
                    nsContext.put(key, entry.getValue());
                }
            }
        }
        super.visit(element, context);
        return element;
    }

    @Override
    protected QName visitTypeRef(QName typeRef, C context) {
        if (this.sourceVersion == DMNVersion.DMN_11) {
            if (typeRef != null) {
                String namespaceURI = typeRef.getNamespaceURI();
                String prefix = typeRef.getPrefix();
                String nsForPrefix = definitions.getNamespaceURI(prefix);
                String localPart = typeRef.getLocalPart();
                if (this.sourceVersion.getFeelNamespace().equals(namespaceURI) || this.targetVersion.getFeelNamespace().equals(nsForPrefix)) {
                    return new QName(String.format("%s.%s", this.targetVersion.getFeelPrefix(), localPart));
                } else {
                    return new QName(typeRef.getLocalPart());
                }
            }
        }
        return typeRef;
    }
}
