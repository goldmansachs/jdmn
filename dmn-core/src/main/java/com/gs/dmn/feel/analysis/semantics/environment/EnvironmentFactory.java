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
package com.gs.dmn.feel.analysis.semantics.environment;

import com.gs.dmn.feel.analysis.semantics.type.FunctionType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

public interface EnvironmentFactory {
    default Environment emptyEnvironment() {
        return new Environment();
    }

    default Environment makeEnvironment() {
        return makeEnvironment(getRootEnvironment(), null);
    }

    default Environment makeEnvironment(Environment rootEnvironment) {
        return makeEnvironment(rootEnvironment, null);
    }

    default Environment makeEnvironment(Expression inputExpression) {
        return new Environment(getRootEnvironment(), inputExpression);
    }

    default Environment makeEnvironment(Environment environment, Expression valueExp) {
        return new Environment(environment, valueExp);
    }

    default Declaration makeVariableDeclaration(String name, Type type) {
        if (type instanceof FunctionType) {
            return new FunctionDeclaration(name, (FunctionType) type);
        } else {
            return new VariableDeclaration(name, type);
        }
    }

    default Declaration makeFunctionDeclaration(String name, FunctionType type) {
        return new FunctionDeclaration(name, type);
    }

    default BusinessKnowledgeModelDeclaration makeBusinessKnowledgeModelDeclaration(String name, FunctionType type) {
        return new BusinessKnowledgeModelDeclaration(name, type);
    }

    default DecisionServiceDeclaration makeDecisionServiceDeclaration(String name, FunctionType type) {
        return new DecisionServiceDeclaration(name, type);
    }

    Environment getRootEnvironment();
}
