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

import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.SimpleLiteral;
import com.gs.dmn.feel.lib.StringEscapeUtil;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.*;
import java.util.stream.Collectors;

public class Context<T, C> extends Expression<T, C> {
    private final List<ContextEntry<T, C>> entries = new ArrayList<>();

    public Context(List<ContextEntry<T, C>> entries) {
        if (entries != null) {
            this.entries.addAll(entries);
        }
    }

    public List<ContextEntry<T, C>> getEntries() {
        return this.entries;
    }

    public ContextEntry<T, C> entry(String name) {
        List<ContextEntry<T, C>> result = this.entries.stream().filter(e -> name.equals(e.getKey().getKey())).collect(Collectors.toList());
        return result.size() == 1 ? result.get(0) : null;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new LinkedHashMap<>();
        for(ContextEntry<T, C> entry: this.entries) {
            String key = entry.getKey().getKey();
            Expression<T, C> expression = entry.getExpression();
            Object value;
            if (expression instanceof Context) {
                value = ((Context<T, C>) expression).toMap();
            } else if (expression instanceof SimpleLiteral) {
                String lexeme = ((SimpleLiteral<T, C>) expression).getLexeme();
                value = StringEscapeUtil.stripQuotes(lexeme);
            } else {
                throw new DMNRuntimeException(String.format("'%s' is not supported", expression.getClass().getSimpleName()));
            }
            result.put(key, value);
        }
        return result;
    }

    @Override
    public Object accept(Visitor<T, C> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Context<?, ?> context = (Context<?, ?>) o;
        return Objects.equals(entries, context.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entries);
    }

    @Override
    public String toString() {
        String expressions = this.entries.stream().map(ContextEntry::toString).collect(Collectors.joining(","));
        return String.format("%s(%s)", getClass().getSimpleName(), expressions);
    }
}
