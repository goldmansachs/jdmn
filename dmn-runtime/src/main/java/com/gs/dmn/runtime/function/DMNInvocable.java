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

import com.gs.dmn.runtime.Function;

public class DMNInvocable extends Function {
    public static Function of(Object invocable, Object type, Object definitionContext) {
        return new DMNInvocable(invocable, type, definitionContext);
    }

    private final Object invocable;
    private final Object type;
    private final Object definitionContext;

    private DMNInvocable(Object invocable, Object type, Object definitionContext) {
        this.invocable = invocable;
        this.type = type;
        this.definitionContext = definitionContext;
    }

    public Object getInvocable() {
        return invocable;
    }

    public Object getType() {
        return type;
    }

    public Object getDefinitionContext() {
        return definitionContext;
    }

    @Override
    public String toString() {
        return String.format("%s(invocable='%s' type='%s')", getClass().getSimpleName(), invocable, type);
    }
}
