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
package com.gs.dmn.feel.analysis.syntax.ast.expression.literal;

import com.gs.dmn.feel.analysis.semantics.type.StringType;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.runtime.DMNContext;

public class StringLiteral extends SimpleLiteral {
    public StringLiteral(String value) {
        super(value);
    }

    @Override
    public void deriveType(DMNContext context) {
        this.setType(StringType.STRING);
    }

    @Override
    public Object accept(Visitor visitor, DMNContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        return String.format("StringLiteral(%s)", lexeme);
    }
}
 
