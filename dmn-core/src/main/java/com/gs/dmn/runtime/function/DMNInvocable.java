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

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import org.omg.spec.dmn._20191111.model.TInvocable;

public class DMNInvocable extends Function {
    public static Function of(TInvocable invocable, Type type, DMNContext definitionContext) {
        return new DMNInvocable(invocable, type, definitionContext);
    }

    private final TInvocable invocable;
    private final Type type;
    private final DMNContext definitionContext;

    private DMNInvocable(TInvocable invocable, Type type, DMNContext definitionContext) {
        this.invocable = invocable;
        this.type = type;
        this.definitionContext = definitionContext;
    }

    public TInvocable getInvocable() {
        return invocable;
    }

    public Type getType() {
        return type;
    }

    public DMNContext getDefinitionContext() {
        return definitionContext;
    }

    @Override
    public String toString() {
        return String.format("%s(invocable='%s' type='%s')", getClass().getSimpleName(), invocable, type);
    }
}
