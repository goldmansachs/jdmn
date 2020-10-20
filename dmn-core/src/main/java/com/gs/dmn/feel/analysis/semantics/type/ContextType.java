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

public class ContextType extends Type implements CompositeDataType {
    public static final Type ANY_CONTEXT = new ContextType();

    private final Map<String, Type> members = new LinkedHashMap<>();

    public ContextType() {
        this(new LinkedHashMap<>());
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
        return Collections.emptyList();
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

        ContextType that = (ContextType) o;

        return members.equals(that.members);
    }

    @Override
    public int hashCode() {
        return members.hashCode();
    }

    @Override
    public String toString() {
        String types = members.entrySet().stream().map(e -> String.format("%s = %s", e.getKey(), e.getValue())).collect(Collectors.joining(", "));
        return String.format("ContextType(%s)", types);
    }
}
