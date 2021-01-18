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
package com.gs.dmn.runtime.interpreter.environment;

import java.util.LinkedHashMap;
import java.util.Map;

public class RuntimeEnvironment {
    public static RuntimeEnvironment of() {
        return of(null);
    }

    public static RuntimeEnvironment of(RuntimeEnvironment parent) {
        return new RuntimeEnvironment(parent);
    }

    private final RuntimeEnvironment parent;

    private final Map<String, Object> bindings = new LinkedHashMap<>();

    RuntimeEnvironment(RuntimeEnvironment parent) {
        this.parent = parent;
    }

    public void bind(String key, Object value) {
        bindings.put(key, value);
    }

    public Object lookupBinding(String key) {
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

    public boolean isBound(String key) {
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

    private Object lookupLocalBinding(String key) {
        return bindings.get(key);
    }

    private boolean isLocalBound(String key) {
        return bindings.containsKey(key);
    }
}
