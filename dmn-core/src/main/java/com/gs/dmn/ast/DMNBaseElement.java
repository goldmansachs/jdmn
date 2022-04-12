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

import javax.xml.stream.Location;
import java.util.*;

public abstract class DMNBaseElement {
    private Location location;
    private Map<String, String> nsContext;
    private DMNBaseElement parent;
    private final List<DMNBaseElement> children = new ArrayList<>();

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Map<String, String> getNsContext() {
        if (nsContext == null) {
            nsContext = new HashMap<>();
        }
        return nsContext;
    }

    public String getNamespaceURI(String prefix) {
        if (this.nsContext != null && this.nsContext.containsKey(prefix)) {
            return this.nsContext.get( prefix );
        }
        if (this.parent != null) {
            return parent.getNamespaceURI(prefix);
        }
        return null;
    }

    public Optional<String> getPrefixForNamespaceURI(String namespaceURI ) {
        if (this.nsContext != null && this.nsContext.containsValue(namespaceURI)) {
            return this.nsContext.entrySet().stream().filter(kv -> kv.getValue().equals(namespaceURI)).findFirst().map(Map.Entry::getKey);
        }
        if (this.parent != null) {
            return parent.getPrefixForNamespaceURI( namespaceURI );
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
}
