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
package com.gs.dmn.runtime.listener;

import java.util.LinkedHashMap;

public class Arguments extends LinkedHashMap<String, Object> {
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (String key : this.keySet()) {
            Object value = this.get(key);
            if (first) {
                first = false;
            } else {
                result.append(", ");
            }
            result.append(String.format("%s='%s'", key, value));
        }
        return result.toString();
    }
}
