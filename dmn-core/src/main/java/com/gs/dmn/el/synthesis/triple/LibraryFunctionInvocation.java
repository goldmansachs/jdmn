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
package com.gs.dmn.el.synthesis.triple;

import java.util.List;

public class LibraryFunctionInvocation extends BuiltinFunctionInvocation {
    private final Triple class_;
    private final boolean staticAccess;

    LibraryFunctionInvocation(Triple class_, boolean staticAccess, Triple conversionFunction, List<Triple> operands) {
        super(conversionFunction, operands);
        this.class_ = class_;
        this.staticAccess = staticAccess;
    }

    public Triple getClass_() {
        return class_;
    }

    public boolean isStaticAccess() {
        return staticAccess;
    }

    @Override
    public <C, R> R accept(Visitor<C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return String.format("LibraryFunctionInvocation(%s, %s, %s)", class_, conversionFunction, operands);
    }
}
