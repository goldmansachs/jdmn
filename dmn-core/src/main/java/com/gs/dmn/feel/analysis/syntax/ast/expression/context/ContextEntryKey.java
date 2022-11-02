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
package com.gs.dmn.feel.analysis.syntax.ast.expression.context;

import com.gs.dmn.NameUtils;
import com.gs.dmn.feel.analysis.syntax.ast.Element;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;

public class ContextEntryKey<T, C> extends Element<T, C> {
    private final String key;

    public ContextEntryKey(String key) {
        // Remove quotes from key if key is string
        key = NameUtils.removeDoubleQuotes(key);
        // Remove quotes from key if key is a name
        key = NameUtils.removeSingleQuotes(key);
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public Object accept(Visitor<T, C> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), this.key);
    }
}
