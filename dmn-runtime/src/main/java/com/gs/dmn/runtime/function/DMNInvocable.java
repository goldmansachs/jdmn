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
    public static Function of(Object invocable, Object declaration) {
        return new DMNInvocable(invocable, declaration);
    }

    private final Object invocable;
    private final Object declaration;

    private DMNInvocable(Object invocable, Object declaration) {
        this.invocable = invocable;
        this.declaration = declaration;
    }

    public Object getInvocable() {
        return invocable;
    }

    public Object getDeclaration() {
        return declaration;
    }
}
