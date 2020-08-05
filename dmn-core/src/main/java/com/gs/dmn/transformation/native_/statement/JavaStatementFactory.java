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

public class JavaStatementFactory implements NativeStatementFactory {
    public JavaStatementFactory() {
    }

    //
    // Simple statements
    //
    @Override
    public NopStatement makeNopStatement() {
        return new NopStatement();
    }

    @Override
    public ExpressionStatement makeExpressionStatement(String text, Type type) {
        return new ExpressionStatement(text, type);
    }

    @Override
    public CommentStatement makeCommentStatement(String message) {
        return new CommentStatement("// " + message);
    }

    @Override
    public ExpressionStatement makeAssignmentStatement(String nativeType, String variableName, String expression, Type type) {
        return new AssignmentStatement(String.format("%s %s = %s;", nativeType, variableName, expression), type);
    }

    @Override
    public Statement makeReturnStatement(String expression, Type type) {
        return new ReturnStatement(String.format("return %s;", expression), type);
    }

    //
    // Compound statement
    //
    @Override
    public CompoundStatement makeCompoundStatement() {
        return new CompoundStatement();
    }
}
