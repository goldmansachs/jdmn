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
package com.gs.dmn.runtime.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(alphabetic=true)
public class QName {
    @JsonProperty("namespace")
    private String namespace;

    @JsonProperty("localName")
    private String localName;

    // Required by ObjectMapper
    QName() {
    }

    public QName(String namespace, String localName) {
        this.namespace = namespace;
        this.localName = localName;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getLocalName() {
        return localName;
    }

    @Override
    public String toString() {
        return "{%s}%s".formatted(namespace, localName);
    }
}
