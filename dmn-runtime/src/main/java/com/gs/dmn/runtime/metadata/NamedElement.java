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
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "@kind", visible = false)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "inputData", value = InputData.class),
        @JsonSubTypes.Type(name = "bkm", value = BKM.class),
        @JsonSubTypes.Type(name = "decision", value = Decision.class),
        @JsonSubTypes.Type(name = "referenceType", value = TypeReference.class),
        @JsonSubTypes.Type(name = "compositeType", value = CompositeType.class),
})
@JsonPropertyOrder(alphabetic=true)
public class NamedElement {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("label")
    private String label;

    // Required by ObjectMapper
    NamedElement() {
    }

    public NamedElement(String id, String name, String label) {
        this.id = id;
        this.name = name;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }
}
