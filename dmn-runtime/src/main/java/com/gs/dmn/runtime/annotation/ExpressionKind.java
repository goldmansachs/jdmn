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
package com.gs.dmn.runtime.annotation;

public enum ExpressionKind {
    FUNCTION_DEFINITION,
    DECISION_TABLE,
    RELATION,
    LIST,
    CONTEXT,
    INVOCATION,
    LITERAL_EXPRESSION,
    OTHER;

    public static ExpressionKind kindByClass(Class expClass) {
        String expClassName = expClass.getSimpleName();
        return kindByName(expClassName);
    }

    static ExpressionKind kindByName(String expClassName) {
        if ("TFunctionDefinition".equals(expClassName)) {
            return FUNCTION_DEFINITION;
        } else if ("TDecisionTable".equals(expClassName)) {
            return DECISION_TABLE;
        } else if ("TRelation".equals(expClassName)) {
            return RELATION;
        } else if ("TList".equals(expClassName)) {
            return LIST;
        } else if ("TContext".equals(expClassName)) {
            return CONTEXT;
        } else if ("TInvocation".equals(expClassName)) {
            return INVOCATION;
        } else if ("TLiteralExpression".equals(expClassName)) {
            return LITERAL_EXPRESSION;
        }
        return OTHER;
    }
}
