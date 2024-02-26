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
package com.gs.dmn.runtime.function;

import com.gs.dmn.ast.TFunctionDefinition;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;

public class DMNFunction extends Function {
    public static Function of(TFunctionDefinition functionDefinition, Type type, DMNContext definitionContext) {
        return new DMNFunction(functionDefinition, type, definitionContext);
    }

    private final TFunctionDefinition functionDefinition;
    private final Type type;
    private final DMNContext definitionContext;

    private DMNFunction(TFunctionDefinition functionDefinition, Type type, DMNContext definitionContext) {
        this.functionDefinition = functionDefinition;
        this.type = type;
        this.definitionContext = definitionContext;
    }

    public TFunctionDefinition getFunctionDefinition() {
        return functionDefinition;
    }

    @Override
    public Type getType() {
        return type;
    }

    public DMNContext getDefinitionContext() {
        return definitionContext;
    }

    @Override
    public String toString() {
        return "%s(functionDefinition='%s' type='%s')".formatted(getClass().getSimpleName(), functionDefinition, type);
    }
}
