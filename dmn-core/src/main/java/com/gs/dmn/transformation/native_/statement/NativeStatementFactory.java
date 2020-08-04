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
package com.gs.dmn.transformation.native_.statement;

import com.gs.dmn.feel.analysis.semantics.type.Type;

public class NativeStatementFactory {
    public NativeStatementFactory() {
    }

    public CompoundStatement makeCompoundStatement() {
        return new CompoundStatement();
    }

    public Statement makeNopStatement() {
        return new ExpressionStatement("", null);
    }

    public ExpressionStatement makeExpressionStatement(String text, Type type) {
        return new ExpressionStatement(text, type);
    }

    public CommentStatement makeCommentStatement(String message) {
        return new CommentStatement(message);
    }

    public ExpressionStatement makeAssignmentStatement(String nativeType, String variableName, String expression, Type type) {
        return new AssignmentStatement(nativeType, variableName, expression, type);
    }

    public Statement makeReturnStatement(String expression, Type type) {
        return new ExpressionStatement(String.format("return %s;", expression), type);
    }
}
