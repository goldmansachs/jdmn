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

import com.gs.dmn.feel.lib.type.StringType;
import com.gs.dmn.runtime.DMNRuntimeException;

public class StringTypeStub implements StringType {
    @Override
    public Boolean stringEqual(String first, String second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean stringNotEqual(String first, String second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String stringAdd(String first, String second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean stringLessThan(String first, String second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean stringGreaterThan(String first, String second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean stringLessEqualThan(String first, String second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean stringGreaterEqualThan(String first, String second) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
