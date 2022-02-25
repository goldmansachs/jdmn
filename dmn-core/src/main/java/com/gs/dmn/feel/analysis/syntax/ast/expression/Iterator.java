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
package com.gs.dmn.feel.analysis.syntax.ast.expression;

import com.gs.dmn.feel.analysis.syntax.ast.Element;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;

public class Iterator<C> extends Element<C> {
    private final String name;
    private final IteratorDomain<C> domain;

    public Iterator(String name, IteratorDomain<C> domain) {
        this.name = name;
        this.domain = domain;
    }

    public String getName() {
        return this.name;
    }

    public IteratorDomain<C> getDomain() {
        return this.domain;
    }

    @Override
    public Object accept(Visitor<C> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return String.format("%s(%s in %s)", getClass().getSimpleName(), this.name, this.domain.toString());
    }
}
