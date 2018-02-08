/**
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

/**
 * Created by Octavian Patrascoiu on 16/09/2016.
 */
public class ItemDefinitionType extends NamedType implements CompositeDataType {
    private Map<String, Type> members = new LinkedHashMap<>();
    private Map<String, List<String>> aliases = new LinkedHashMap<>();

    public ItemDefinitionType(String name) {
        super(name);
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
        return other instanceof ItemDefinitionType && getName().equals(((ItemDefinitionType) other).getName());
    }

    public ContextType toContextType() {
        ContextType contextType = new ContextType();
        members.keySet().forEach(
                key -> contextType.addMember(key, aliases.get(key), members.get(key))
        );
        return contextType;
    }

    @Override
    public boolean conformsTo(Type other) {
        return other instanceof ItemDefinitionType && this.equivalentTo(other)
                || other == ANY;
    }

    @Override
    public boolean isValid() {
        if (members == null || members.isEmpty()) {
            return false;
        }
        return members.values().stream().allMatch(t -> t.isValid() && t != AnyType.ANY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemDefinitionType that = (ItemDefinitionType) o;

        if (members != null ? !members.equals(that.members) : that.members != null) return false;
        return aliases != null ? aliases.equals(that.aliases) : that.aliases == null;

    }

    @Override
    public int hashCode() {
        int result = members != null ? members.hashCode() : 0;
        result = 31 * result + (aliases != null ? aliases.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String members = this.members.keySet().stream().map(k -> String.format("%s = %s", toList(k, this.aliases.get(k)), this.members.get(k))).collect(Collectors.joining(", "));
        return String.format("ItemDefinitionType(%s)", members);
    }

    private String toList(String name, List<String> aliases) {
        if (aliases == null || aliases.isEmpty()) {
            return name;
        }
        return name + ", " + aliases.stream().collect(Collectors.joining(", "));
    }
}
