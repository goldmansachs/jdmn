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
package com.gs.dmn.runtime;

import com.gs.dmn.feel.analysis.semantics.environment.Declaration;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterTypes;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import org.omg.spec.dmn._20191111.model.TNamedElement;

import java.util.List;

public class DMNContext {
    private final TNamedElement element;
    private final Environment environment;
    private final RuntimeEnvironment runtimeEnvironment;

    public static DMNContext of(TNamedElement element, Environment environment) {
        return new DMNContext(element, environment, null);
    }

    public static DMNContext of(TNamedElement element, Environment environment, RuntimeEnvironment runtimeEnvironment) {
        return new DMNContext(element, environment, runtimeEnvironment);
    }

    private DMNContext(TNamedElement element, Environment environment, RuntimeEnvironment runtimeEnvironment) {
        this.element = element;
        this.environment = environment;
        this.runtimeEnvironment = runtimeEnvironment;
    }

    public TNamedElement getElement() {
        return this.element;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public Expression getInputExpression() {
        return environment.getInputExpression();
    }

    public Type getInputExpressionType() {
        return environment.getInputExpressionType();
    }

    public void addDeclaration(Declaration declaration) {
        environment.addDeclaration(declaration);
    }

    public Declaration lookupVariableDeclaration(String name) {
        return environment.lookupVariableDeclaration(name);
    }

    public List<Declaration> lookupFunctionDeclaration(String name) {
        return environment.lookupFunctionDeclaration(name);
    }

    public void updateVariableDeclaration(String name, Type type) {
        environment.updateVariableDeclaration(name, type);
    }

    public RuntimeEnvironment getRuntimeEnvironment() {
        return this.runtimeEnvironment;
    }

    public boolean isBound(String key) {
        return runtimeEnvironment.isBound(key);
    }

    public Object lookupBinding(String variableName) {
        if (this.runtimeEnvironment == null) {
            throw new DMNRuntimeException("Missing runtime environment");
        }
        return this.runtimeEnvironment.lookupBinding(variableName);
    }

    public void bind(String variableName, Object value) {
        if (this.runtimeEnvironment == null) {
            throw new DMNRuntimeException("Missing runtime environment");
        }
        this.runtimeEnvironment.bind(variableName, value);
    }
}
