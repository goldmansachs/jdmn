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
package com.gs.dmn.el.analysis.semantics.type;

public class AnyType implements NamedType {
    public static final AnyType ANY = new AnyType();

    private final String name;

    protected AnyType() {
        this.name = "Any";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equivalentTo(Type other) {
        return Type.isAny(other);
    }

    @Override
    public boolean conformsTo(Type other) {
        return Type.isAny(other);
    }

    @Override
    public boolean isFullySpecified() {
        return true;
    }

    @Override
    public String typeExpression() {
        return getName();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
