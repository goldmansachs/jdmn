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

import java.util.List;

public class CompositeType extends Type {
    @JsonProperty("types")
    private List<Type> types;

    // Required by ObjectMapper
    CompositeType() {
        super();
    }

    public CompositeType(String namespace, String id, String name, String label, boolean isCollection, List<Type> subTypes) {
        super(namespace, id, name, label, isCollection);
        this.types = subTypes;
    }

    public List<Type> getTypes() {
        return types;
    }
}
