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
package com.gs.dmn.feel.lib.stub;

import com.gs.dmn.feel.lib.type.function.FunctionType;
import com.gs.dmn.runtime.DMNRuntimeException;

public class FunctionTypeStub implements FunctionType {
    @Override
    public boolean isFunction(Object value) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Object functionValue(Object value) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean functionIs(Object function1, Object function2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean functionEqual(Object function1, Object function2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean functionNotEqual(Object function1, Object function2) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
