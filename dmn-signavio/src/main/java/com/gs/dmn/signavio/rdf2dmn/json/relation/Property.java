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
package com.gs.dmn.signavio.rdf2dmn.json.relation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(name="boolean", value = BooleanProperty.class),
        @JsonSubTypes.Type(name="date", value = DateProperty.class),
        @JsonSubTypes.Type(name="datetime", value = DateTimeProperty.class),
        @JsonSubTypes.Type(name="enumeration", value = EnumerationProperty.class),
        @JsonSubTypes.Type(name="number", value = NumberProperty.class),
        @JsonSubTypes.Type(name="string", value = StringProperty.class),
        @JsonSubTypes.Type(name="time", value = TimeProperty.class)
})
public abstract class Property {
    private List<Integer> pathElements;
    private String name;
    private String type;
    private boolean isList;

    public List<Integer> getPathElements() {
        return pathElements;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isList() {
        return isList;
    }
}
