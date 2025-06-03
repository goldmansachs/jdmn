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
package com.gs.dmn.feel.analysis.semantics;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

public class SemanticError extends RuntimeException {
    public SemanticError(String errorMessage) {
        super(errorMessage);
    }

    public SemanticError(DMNContext context, Expression<Type> expression, String errorMessage) {
        super(String.format("%s: %s", makeLocation(context, expression), errorMessage));
    }

    public SemanticError(DMNContext context, Expression<Type> expression, String errorMessage, Exception e) {
        super(String.format("%s: %s", makeLocation(context, expression), errorMessage), e);
    }

    private static String makeLocation(DMNContext context, Expression<Type> expression) {
        String location = null;
        if (context != null && context.getElement() != null) {
            location = context.getElementName();
        }
        if (location == null) {
            location = expression.getClass().getSimpleName();
        } else {
            location += ":" + expression.getClass().getSimpleName();
        }
        return location;
    }
}
