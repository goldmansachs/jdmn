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
package com.gs.dmn.feel.lib.type.function;

import com.gs.dmn.feel.lib.type.BaseType;
import com.gs.dmn.feel.lib.type.bool.BooleanType;
import com.gs.dmn.feel.lib.type.bool.DefaultBooleanType;
import com.gs.dmn.runtime.DMNRuntimeException;

public class DefaultFunctionType extends BaseType implements FunctionType {
    private final BooleanType booleanType;

    public DefaultFunctionType() {
        this.booleanType = new DefaultBooleanType();
    }

    @Override
    public boolean isFunction(Object value) {
        if (value == null) {
            return true;
        }

        throw new DMNRuntimeException("isFunction is not supported yet");
    }

    @Override
    public Object functionValue(Object value) {
        if (isFunction(value)) {
            return value;
        } else {
            return null;
        }
    }

    @Override
    public Boolean functionIs(Object function1, Object function2) {
        return functionEqual(function1, function2);
    }

    @Override
    public Boolean functionEqual(Object function1, Object function2) {
        if (isFunction(function1) && isFunction(function2)) {
            return function1 == function2;
        } else {
            return null;
        }
    }

    @Override
    public Boolean functionNotEqual(Object function1, Object function2) {
        return this.booleanType.booleanNot(functionEqual(function1, function2));
    }
}
