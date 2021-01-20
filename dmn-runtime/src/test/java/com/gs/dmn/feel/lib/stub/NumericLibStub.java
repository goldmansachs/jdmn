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

import com.gs.dmn.feel.lib.type.numeric.NumericLib;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.List;

public class NumericLibStub<NUMBER> implements NumericLib<NUMBER> {
    @Override
    public NUMBER number(String literal) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER number(String from, String groupingSeparator, String decimalSeparator) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER decimal(NUMBER n, NUMBER scale) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER round(NUMBER n, NUMBER scale, String mode) {
        throw new DMNRuntimeException("Not supported yet");    }

    @Override
    public NUMBER floor(NUMBER NUMBER) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER ceiling(NUMBER NUMBER) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER abs(NUMBER NUMBER) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER intModulo(NUMBER dividend, NUMBER divisor) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER modulo(NUMBER dividend, NUMBER divisor) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER sqrt(NUMBER NUMBER) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER log(NUMBER NUMBER) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER exp(NUMBER NUMBER) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean odd(NUMBER NUMBER) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean even(NUMBER NUMBER) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER count(List list) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER min(List list) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER min(Object... args) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER max(List list) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER max(Object... args) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER sum(List list) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER sum(Object... args) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER mean(List list) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER mean(Object... args) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER product(List list) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER product(Object... NUMBERs) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER median(List list) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER median(Object... NUMBERs) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER stddev(List list) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER stddev(Object... NUMBERs) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public List mode(List list) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public List mode(Object... NUMBERs) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Number toNumber(NUMBER bigDecimal) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
