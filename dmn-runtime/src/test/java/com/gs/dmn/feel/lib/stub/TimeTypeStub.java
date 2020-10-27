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

import com.gs.dmn.feel.lib.type.TimeType;
import com.gs.dmn.runtime.DMNRuntimeException;

public class TimeTypeStub<TIME, DURATION> implements TimeType<TIME, DURATION> {
    @Override
    public Boolean timeEqual(TIME first, TIME second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean timeNotEqual(TIME first, TIME second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean timeLessThan(TIME first, TIME second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean timeGreaterThan(TIME first, TIME second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean timeLessEqualThan(TIME first, TIME second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean timeGreaterEqualThan(TIME first, TIME second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DURATION timeSubtract(TIME first, TIME second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public TIME timeAddDuration(TIME TIME, DURATION duration) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public TIME timeSubtractDuration(TIME TIME, DURATION duration) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
