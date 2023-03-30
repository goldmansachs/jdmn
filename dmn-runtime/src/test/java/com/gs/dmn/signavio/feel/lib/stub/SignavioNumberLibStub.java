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
package com.gs.dmn.signavio.feel.lib.stub;

import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.signavio.feel.lib.type.numeric.SignavioNumberLib;

import java.util.List;

public class SignavioNumberLibStub<NUMBER> implements SignavioNumberLib<NUMBER> {
    @Override
    public NUMBER number(String text) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER number(String text, NUMBER defaultValue) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER abs(NUMBER number) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER count(List<?> list) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER round(NUMBER number, NUMBER digits) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER ceiling(NUMBER number) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER floor(NUMBER number) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER integer(NUMBER number) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER modulo(NUMBER dividend, NUMBER divisor) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER percent(NUMBER base) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER power(NUMBER base, NUMBER exponent) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER product(List<?> numbers) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER roundDown(NUMBER number, NUMBER digits) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER roundUp(NUMBER number, NUMBER digits) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER sum(List<?> numbers) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER avg(List<?> numbers) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER max(List<?> numbers) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER median(List<?> numbers) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER min(List<?> numbers) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER mode(List<?> numbers) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER valueOf(long number) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public int intValue(NUMBER number) {
        return 0;
    }

    @Override
    public Number toNumber(NUMBER number) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
