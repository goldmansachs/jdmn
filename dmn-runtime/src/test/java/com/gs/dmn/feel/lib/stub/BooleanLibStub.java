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

import com.gs.dmn.feel.lib.type.bool.BooleanLib;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.List;

public class BooleanLibStub implements BooleanLib {
    @Override
    public Boolean and(List<Object> list) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean and(Object... args) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean all(List<Object> list) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean all(Object... args) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean or(List<Object> list) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean or(Object... args) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean any(List<Object> list) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean any(Object... args) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
