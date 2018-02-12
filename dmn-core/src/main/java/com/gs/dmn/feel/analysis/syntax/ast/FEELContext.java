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
package com.gs.dmn.feel.analysis.syntax.ast;

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;

public class FEELContext {
    private final Environment environment;
    private final RuntimeEnvironment runtimeEnvironment;

    public static FEELContext makeContext(Environment environment) {
        return new FEELContext(environment, null);
    }

    public static FEELContext makeContext(Environment environment, RuntimeEnvironment runtimeEnvironment) {
        return new FEELContext(environment, runtimeEnvironment);
    }

    private FEELContext(Environment environment, RuntimeEnvironment runtimeEnvironment) {
        this.environment = environment;
        this.runtimeEnvironment = runtimeEnvironment;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Object lookupRuntimeBinding(String variableName) {
        if (runtimeEnvironment == null) {
            throw new DMNRuntimeException("Missing runtime environment");
        }
        return runtimeEnvironment.lookupBinding(variableName);
    }

    public void runtimeBind(String variableName, Object value) {
        if (runtimeEnvironment == null) {
            throw new DMNRuntimeException("Missing runtime environment");
        }
        runtimeEnvironment.bind(variableName, value);
    }

    public RuntimeEnvironment getRuntimeEnvironment() {
        return runtimeEnvironment;
    }
}
