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
package com.gs.dmn.runtime.annotation;

import org.omg.spec.dmn._20180521.model.*;

public enum ExpressionKind {
    FUNCTION_DEFINITION,
    DECISION_TABLE,
    RELATION,
    LIST,
    CONTEXT,
    INVOCATION,
    LITERAL_EXPRESSION,
    OTHER;

    public static ExpressionKind kindByClass(TExpression exp) {
        if (exp instanceof TFunctionDefinition) {
            return FUNCTION_DEFINITION;
        } else if (exp instanceof TDecisionTable) {
            return DECISION_TABLE;
        } else if (exp instanceof TRelation) {
            return RELATION;
        } else if (exp instanceof TList) {
            return LIST;
        } else if (exp instanceof TContext) {
            return CONTEXT;
        } else if (exp instanceof TInvocation) {
            return INVOCATION;
        } else if (exp instanceof TLiteralExpression) {
            return LITERAL_EXPRESSION;
        }
        return OTHER;
    }
}
