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
import java.util.Set;

public abstract class CompositeDataType implements Type {
    protected final Map<String, Type> members = new LinkedHashMap<>();

    public CompositeDataType() {
        this(new LinkedHashMap<>());
    }

    public CompositeDataType(Map<String, Type> namedTypes) {
        this.members.putAll(namedTypes);
    }

    public CompositeDataType addMember(String name, Type type) {
        this.members.put(name, type);
        return this;
    }

    public Set<String> getMembers() {
        return members.keySet();
    }

    public Type getMemberType(String key) {
        return members.get(key);
    }

    @Override
    public String toString() {
        return getExpressionType();
    }
}
