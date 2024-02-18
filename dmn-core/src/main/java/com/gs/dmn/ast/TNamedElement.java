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

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "@kind")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "definitions", value = TDefinitions.class),
        @JsonSubTypes.Type(name = "elementCollection", value = TElementCollection.class),
        @JsonSubTypes.Type(name = "businessContextElement", value = TBusinessContextElement.class),
        @JsonSubTypes.Type(name = "itemDefinition", value = TItemDefinition.class),
        @JsonSubTypes.Type(name = "informationItem", value = TInformationItem.class),
        @JsonSubTypes.Type(name = "drgElement", value = TDRGElement.class),
        @JsonSubTypes.Type(name = "import", value = TImport.class)
})
public abstract class TNamedElement extends TDMNElement {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Override
    public String toString() {
        return "%s(%s)".formatted(this.getClass().getSimpleName(), name);
    }
}
