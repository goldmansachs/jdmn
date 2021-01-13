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
package com.gs.dmn.feel.analysis.syntax.ast.expression.type;

import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.runtime.DMNContext;

public class NamedTypeExpression extends TypeExpression {
    private final String qualifiedName;

    public NamedTypeExpression(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public String getQualifiedName() {
        return this.qualifiedName;
    }

    @Override
    public Object accept(Visitor visitor, DMNContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), this.qualifiedName);
    }
}
