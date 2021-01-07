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
package com.gs.dmn.feel.analysis.syntax.ast.expression.function;

import com.gs.dmn.feel.analysis.semantics.type.Type;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class NamedParameterTypes extends ParameterTypes {
    private Map<String, Type> namedTypes = new LinkedHashMap<>();

    public NamedParameterTypes(Map<String, Type> namedTypes) {
        if (namedTypes != null) {
            this.namedTypes = namedTypes;
        }
    }

    @Override
    public int size() {
        return this.namedTypes == null ? 0 : this.namedTypes.size();
    }

    @Override
    public boolean compatible(List<FormalParameter> parameters) {
        if (size() != parameters.size()) {
            return false;
        }
        for (FormalParameter formalParameter : parameters) {
            Type argumentType = this.namedTypes.get(formalParameter.getName());
            Type parameterType = formalParameter.getType();
            if (!Type.conformsTo(argumentType, parameterType)) {
                return false;
            }
        }
        return true;
    }

    public Type getType(String name) {
        return this.namedTypes.get(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedParameterTypes that = (NamedParameterTypes) o;
        return Objects.equals(this.namedTypes, that.namedTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.namedTypes);
    }

    @Override
    public String toString() {
        String opd = this.namedTypes.entrySet().stream().map(e -> String.format("%s : %s", e.getKey(), e.getValue().toString())).collect(Collectors.joining(", "));
        return String.format("NamedParameterTypes(%s)", opd);
    }
}
