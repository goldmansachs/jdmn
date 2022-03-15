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
package com.gs.dmn.runtime.interpreter;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

public class InputClausePair {
    private final Expression<Type, DMNContext> expression;
    private final Object value;

    public InputClausePair(Expression<Type, DMNContext> expression, Object value) {
        this.expression = expression;
        this.value = value;
    }

    public Expression<Type, DMNContext> getExpression() {
        return expression;
    }

    public Object getValue() {
        return value;
    }
}
