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
package com.gs.dmn.feel.analysis.syntax.ast.expression.function;

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.ContextType;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.SimpleLiteral;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.*;
import java.util.stream.Collectors;

public class Context extends Expression {
    private final List<ContextEntry> entries = new ArrayList<>();

    public Context(List<ContextEntry> entries) {
        if (entries != null) {
            this.entries.addAll(entries);
        }
    }

    public List<ContextEntry> getEntries() {
        return entries;
    }

    public ContextEntry entry(String name) {
        List<ContextEntry> result = entries.stream().filter(e -> name.equals(e.getKey().getKey())).collect(Collectors.toList());
        return result.size() == 1 ? result.get(0) : null;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new LinkedHashMap<>();
        for(ContextEntry entry: entries) {
            String key = entry.getKey().getKey();
            Expression expression = entry.getExpression();
            Object value;
            if (expression instanceof Context) {
                value = ((Context) expression).toMap();
            } else if (expression instanceof SimpleLiteral) {
                value = ((SimpleLiteral) expression).getValue().replace("\"", "");
            } else {
                throw new DMNRuntimeException(String.format("'%s' is not supported", expression.getClass().getSimpleName()));
            }
            result.put(key, value);
        }
        return result;
    }

    @Override
    public void deriveType(Environment environment) {
        ContextType type = new ContextType();
        entries.forEach(e -> type.addMember(e.getKey().getKey(), Arrays.asList(), e.getExpression().getType()));
        setType(type);
    }

    @Override
    public Object accept(Visitor visitor, FEELContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        String expressions = entries.stream().map(ContextEntry::toString).collect(Collectors.joining(","));
        return String.format("Context(%s)", expressions);
    }
}
