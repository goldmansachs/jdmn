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
package com.gs.dmn.feel.lib.reference;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemDefinitionType extends CompositeDataType {
    private final String modelName;
    private final String name;

    public ItemDefinitionType(String modelName, String name) {
        this(modelName, name, new LinkedHashMap<>());
    }

    public ItemDefinitionType(String modelName, String name, Map<String, Type> namedTypes) {
        super(namedTypes);
        this.modelName = modelName;
        this.name = name;
    }

    @Override
    public String getExpressionType() {
        String membersStr = members.entrySet().stream().map(e -> String.format("%s: %s", e.getKey(), e.getValue())).collect(Collectors.joining(", "));
        return String.format("'%s':'%s'<%s>", modelName, name, membersStr);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDefinitionType that = (ItemDefinitionType) o;
        return Objects.equals(modelName, that.modelName) && Objects.equals(name, that.name) && Objects.equals(getMembers(), that.getMembers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(modelName, name, getMembers());
    }
}
