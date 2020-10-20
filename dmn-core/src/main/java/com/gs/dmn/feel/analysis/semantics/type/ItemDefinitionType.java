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
package com.gs.dmn.feel.analysis.semantics.type;

import java.util.*;
import java.util.stream.Collectors;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;

public class ItemDefinitionType extends NamedType implements CompositeDataType {
    public static final Type ANY_ITEM_DEFINITION = new ItemDefinitionType("");

    private final String modelName;
    private final Map<String, Type> members = new LinkedHashMap<>();
    private final Map<String, List<String>> aliases = new LinkedHashMap<>();

    public ItemDefinitionType(String name) {
        this(name, null);
    }

    public ItemDefinitionType(String name, String modelName) {
        super(name);
        this.modelName = modelName;
    }

    public String getModelName() {
        return this.modelName;
    }

    public ContextType toContextType() {
        ContextType contextType = new ContextType();
        members.forEach((key, value) -> contextType.addMember(key, aliases.get(key), value));
        return contextType;
    }

    @Override
    public ItemDefinitionType addMember(String name, List<String> aliases, Type type) {
        this.members.put(name, type);
        this.aliases.put(name, aliases);
        return this;
    }

    @Override
    public Set<String> getMembers() {
        return members.keySet();
    }

    @Override
    public Type getMemberType(String member) {
        return members.get(member);
    }

    @Override
    public List<String> getAliases(String name) {
        List<String> aliases = this.aliases.get(name);
        return aliases == null ? new ArrayList<>() : aliases;
    }

    @Override
    public boolean equivalentTo(Type other) {
        return CompositeDataType.equivalentTo(this, other);
    }

    @Override
    public boolean conformsTo(Type other) {
        return CompositeDataType.conformsTo(this, other);
    }

    @Override
    public boolean isValid() {
        if (members.isEmpty()) {
            return false;
        }
        return members.values().stream().allMatch(t -> t.isValid() && t != AnyType.ANY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemDefinitionType that = (ItemDefinitionType) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (modelName != null ? !modelName.equals(that.modelName) : that.modelName != null) return false;
        if (!members.equals(that.members)) return false;
        return aliases.equals(that.aliases);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (modelName != null ? modelName.hashCode() : 0);
        result = 31 * result + members.hashCode();
        result = 31 * result + aliases.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String members = this.members.entrySet().stream().map(e -> String.format("%s = %s", toList(e.getKey(), this.aliases.get(e.getKey())), e.getValue())).collect(Collectors.joining(", "));
        return String.format("ItemDefinitionType(%s, %s)", name, members);
    }

    private String toList(String name, List<String> aliases) {
        if (aliases == null || aliases.isEmpty()) {
            return name;
        }
        return name + ", " + String.join(", ", aliases);
    }
}
