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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gs.dmn.serialization.xstream.dom.ElementInfo;

import java.util.*;

@JsonPropertyOrder({
        "elementInfo"
})
public abstract class DMNBaseElement {
    private ElementInfo elementInfo;

    @JsonIgnore
    private DMNBaseElement parent;
    @JsonIgnore
    private final List<DMNBaseElement> children = new ArrayList<>();

    public ElementInfo getElementInfo() {
        if (elementInfo == null) {
            this.elementInfo = new ElementInfo();
        }
        return elementInfo;
    }

    public void setElementInfo(ElementInfo elementInfo) {
        this.elementInfo = elementInfo;
    }

    public String getNamespaceURI(String prefix) {
        Map<String, String> nsContext = elementInfo.getNsContext();
        if (nsContext != null && nsContext.containsKey(prefix)) {
            return nsContext.get(prefix);
        }
        if (this.parent != null) {
            return parent.getNamespaceURI(prefix);
        }
        return null;
    }

    public Optional<String> getPrefixForNamespaceURI(String namespaceURI) {
        Map<String, String> nsContext = elementInfo.getNsContext();
        if (nsContext != null && nsContext.containsValue(namespaceURI)) {
            return nsContext.entrySet().stream().filter(kv -> kv.getValue().equals(namespaceURI)).findFirst().map(Map.Entry::getKey);
        }
        if (this.parent != null) {
            return parent.getPrefixForNamespaceURI(namespaceURI);
        }
        return Optional.empty();
    }

    public DMNBaseElement getParent() {
        return parent;
    }

    public void setParent(DMNBaseElement parent) {
        this.parent = parent;
    }

    public java.util.List<DMNBaseElement> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public void addChildren(DMNBaseElement child) {
        this.children.add(child);
    }

    @Override
    public final boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public final int hashCode() {
        return super.hashCode();
    }
}
