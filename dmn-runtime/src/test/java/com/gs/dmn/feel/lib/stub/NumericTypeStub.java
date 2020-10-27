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

import com.gs.dmn.feel.lib.type.NumericType;
import com.gs.dmn.runtime.DMNRuntimeException;

public class NumericTypeStub<NUMBER> implements NumericType<NUMBER> {
    @Override
    public NUMBER numericAdd(NUMBER first, NUMBER second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER numericSubtract(NUMBER first, NUMBER second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER numericMultiply(NUMBER first, NUMBER second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER numericDivide(NUMBER first, NUMBER second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER numericUnaryMinus(NUMBER first) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER numericExponentiation(NUMBER first, NUMBER second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean numericEqual(NUMBER first, NUMBER second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean numericNotEqual(NUMBER first, NUMBER second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean numericLessThan(NUMBER first, NUMBER second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean numericGreaterThan(NUMBER first, NUMBER second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean numericLessEqualThan(NUMBER first, NUMBER second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean numericGreaterEqualThan(NUMBER first, NUMBER second) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
