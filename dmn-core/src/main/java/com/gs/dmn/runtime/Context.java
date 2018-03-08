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
package com.gs.dmn.runtime;

import java.util.*;

public class Context {
    private String name;
    private final Map map = new LinkedHashMap<>();

    public Context() {
    }

    public Context(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map getBindings() {
        return map;
    }

    public Object get(Object name) {
        return map.get(name);
    }

    public Object get(Object name, Object... aliases) {
        Object o = map.get(name);
        if (o != null) {
            return o;
        }
        if (aliases != null) {
            for(Object key: aliases) {
                o = map.get(key);
                if (o != null) {
                    return o;
                }
            }
        }
        return null;
    }

    public Object put(Object key, Object value) {
        return map.put(key, value);
    }

    public Context add(Object key, Object value) {
        this.put(key, value);
        return this;
    }

    public boolean isEquivalent(Context other) {
        return other != null && this.keySet().equals(other.keySet());
    }

    @Override
    public String toString() {
        Set set = this.map.keySet();
        if (set != null && !set.isEmpty() && set.iterator().next() instanceof String) {
            ArrayList orderedKeys = new ArrayList<>(set);
            Collections.sort(orderedKeys);
            StringBuilder result = new StringBuilder("{");
            for(int i=0; i<orderedKeys.size(); i++) {
                Object key = orderedKeys.get(i);
                Object member = this.get(key);
                result.append(String.format("%s%s=%s", (i != 0 ? ", " : ""), key, member));
            }
            result.append("}");
            return result.toString();
        } else {
            return super.toString();
        }
    }

    public Iterable<?> keySet() {
        return map.keySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Context context = (Context) o;

        return map.equals(context.map);

    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }
}
