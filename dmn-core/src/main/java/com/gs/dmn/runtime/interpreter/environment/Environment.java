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
package com.gs.dmn.runtime.interpreter.environment;

import java.util.LinkedHashMap;
import java.util.Map;

public class Environment<K, V> {
    private final Map<K, V> bindings = new LinkedHashMap<>();

    private final Environment<K, V> parent;

    Environment(Environment<K, V> parent) {
        this.parent = parent;
    }

    public Environment<K, V> getParent() {
        return parent;
    }

    public void bind(K key, V value) {
        bindings.put(normalize(key), value);
    }

    public V lookupBinding(K key) {
        if (isLocalBound(key)) {
            return lookupLocalBinding(key);
        } else {
            if (parent != null) {
                return parent.lookupBinding(key);
            } else {
                return null;
            }
        }
    }

    public boolean isBound(K key) {
        if (isLocalBound(key)) {
            return true;
        } else {
            if (parent != null) {
                return parent.isBound(key);
            } else {
                return false;
            }
        }
    }

    private V lookupLocalBinding(K key) {
        return bindings.get(normalize(key));
    }

    private boolean isLocalBound(K key) {
        return bindings.containsKey(normalize(key));
    }

    private K normalize(K key) {
        return key;
    }
}
