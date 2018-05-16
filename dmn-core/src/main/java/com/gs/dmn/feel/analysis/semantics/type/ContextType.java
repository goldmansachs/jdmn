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

public class ContextType extends Type implements CompositeDataType {
    private final Map<String, Type> members = new LinkedHashMap();

    public ContextType() {
        this(new LinkedHashMap());
    }

    public ContextType(Map<String, Type> namedTypes) {
        this.members.putAll(namedTypes);
    }

    @Override
    public ContextType addMember(String name, List<String> aliases, Type type) {
        this.members.put(name, type);
        return this;
    }

    @Override
    public Set<String> getMembers() {
        return members.keySet();
    }

    @Override
    public Type getMemberType(String key) {
        return members.get(key);
    }

    @Override
    public List<String> getAliases(String name) {
        return Arrays.asList();
    }

    @Override
    public boolean equivalentTo(Type other) {
        if (other instanceof ContextType) {
            Set<String> thisNames = this.getMembers();
            Set<String> otherNames = ((ContextType) other).getMembers();
            if (!thisNames.equals(otherNames)) {
                return false;
            }
            for (String name : thisNames) {
                Type thisType = this.getMemberType(name);
                Type otherType = ((ContextType) other).getMemberType(name);
                if (!thisType.equivalentTo(otherType)) {
                    return false;
                }
            }
            return true;
        } else if (other instanceof ItemDefinitionType) {
            Set<String> thisNames = this.getMembers();
            Set<String> otherNames = ((ItemDefinitionType) other).getMembers();
            if (!thisNames.equals(otherNames)) {
                return false;
            }
            for (String name : thisNames) {
                Type thisType = this.getMemberType(name);
                Type otherType = ((ItemDefinitionType) other).getMemberType(name);
                if (!thisType.equivalentTo(otherType)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean conformsTo(Type other) {
        if (other == ANY) {
            return true;
        }
        if (other instanceof ContextType) {
            Set<String> thisNames = this.getMembers();
            Set<String> otherNames = ((ContextType) other).getMembers();
            if (!thisNames.containsAll(otherNames)) {
                return false;
            }
            for (String name : otherNames) {
                Type thisType = this.getMemberType(name);
                Type otherType = ((ContextType) other).getMemberType(name);
                if (!thisType.conformsTo(otherType)) {
                    return false;
                }
            }
            return true;
        } else if (other instanceof ItemDefinitionType) {
            Set<String> thisNames = this.getMembers();
            Set<String> otherNames = ((ItemDefinitionType) other).getMembers();
            if (!thisNames.containsAll(otherNames)) {
                return false;
            }
            for (String name : otherNames) {
                Type thisType = this.getMemberType(name);
                Type otherType = ((ItemDefinitionType) other).getMemberType(name);
                if (!thisType.conformsTo(otherType)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
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

        ContextType that = (ContextType) o;

        return members.equals(that.members);
    }

    @Override
    public int hashCode() {
        return members.hashCode();
    }

    @Override
    public String toString() {
        String types = members.keySet().stream().map(k -> String.format("%s = %s", k, members.get(k))).collect(Collectors.joining(", "));
        return String.format("ContextType(%s)", types);
    }
}
