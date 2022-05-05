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
package com.gs.dmn.serialization.xstream.dom;

import javax.xml.stream.Location;
import java.util.LinkedHashMap;
import java.util.Map;

public class ElementInfo {
    private final Location location;
    private final Map<String, String> nsContext;
    private final String prefix;
    private final String namespaceURI;

    public ElementInfo() {
        this(null, null, null, new LinkedHashMap<>());
    }

    public ElementInfo(Location location, String prefix, String namespaceURI, Map<String, String> nsContext) {
        this.location = location;
        this.prefix = prefix;
        this.namespaceURI = namespaceURI;
        this.nsContext = nsContext;
    }

    public Location getLocation() {
        return location;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getNamespaceURI() {
        return namespaceURI;
    }

    public Map<String, String> getNsContext() {
        return nsContext;
    }
}
