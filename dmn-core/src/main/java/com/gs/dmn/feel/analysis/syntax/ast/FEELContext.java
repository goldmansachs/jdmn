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
package com.gs.dmn.feel.analysis.syntax.ast;

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import org.omg.spec.dmn._20191111.model.TNamedElement;

public class FEELContext {
    private final Environment environment;
    private final RuntimeEnvironment runtimeEnvironment;
    private final TNamedElement element;

    public static FEELContext makeContext(TNamedElement element, Environment environment) {
        return new FEELContext(element, environment, null);
    }

    public static FEELContext makeContext(TNamedElement element, Environment environment, RuntimeEnvironment runtimeEnvironment) {
        return new FEELContext(element, environment, runtimeEnvironment);
    }

    private FEELContext(TNamedElement element, Environment environment, RuntimeEnvironment runtimeEnvironment) {
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

    public Object lookupRuntimeBinding(String variableName) {
        if (this.runtimeEnvironment == null) {
            throw new DMNRuntimeException("Missing runtime environment");
        }
        return this.runtimeEnvironment.lookupBinding(variableName);
    }

    public void runtimeBind(String variableName, Object value) {
        if (this.runtimeEnvironment == null) {
            throw new DMNRuntimeException("Missing runtime environment");
        }
        this.runtimeEnvironment.bind(variableName, value);
    }

    public RuntimeEnvironment getRuntimeEnvironment() {
        return this.runtimeEnvironment;
    }
}
