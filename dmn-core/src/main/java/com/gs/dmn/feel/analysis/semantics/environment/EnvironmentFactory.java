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
package com.gs.dmn.feel.analysis.semantics.environment;

import com.gs.dmn.feel.analysis.semantics.type.FunctionType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

public abstract class EnvironmentFactory {
    public Environment emptyEnvironment() {
        return new Environment();
    }

    public Environment makeEnvironment() {
        return makeEnvironment(getRootEnvironment(), null);
    }

    public Environment makeEnvironment(Environment rootEnvironment) {
        return makeEnvironment(rootEnvironment, null);
    }

    public Environment makeEnvironment(Expression inputExpression) {
        return new Environment(getRootEnvironment(), inputExpression);
    }

    public Environment makeEnvironment(Environment environment, Expression valueExp) {
        return new Environment(environment, valueExp);
    }

    public VariableDeclaration makeVariableDeclaration(String text, Type variableType) {
        return new VariableDeclaration(text, variableType);
    }

    public FunctionDeclaration makeFunctionDeclaration(String name, FunctionType type) {
        return new FunctionDeclaration(name, type);
    }

    public BusinessKnowledgeModelDeclaration makeBusinessKnowledgeModelDeclaration(String name, FunctionType type) {
        return new BusinessKnowledgeModelDeclaration(name, type);
    }

    public MemberDeclaration makeMemberDeclaration(String text, Type variableType) {
        return new MemberDeclaration(text, variableType);
    }

    public abstract Environment getRootEnvironment();
}
