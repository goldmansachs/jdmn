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

import com.gs.dmn.feel.lib.type.DurationType;
import com.gs.dmn.runtime.DMNRuntimeException;

public class DurationTypeStub<DURATION, NUMBER> implements DurationType<DURATION, NUMBER> {
    @Override
    public Boolean durationIs(DURATION first, DURATION second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean durationEqual(DURATION first, DURATION second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean durationNotEqual(DURATION first, DURATION second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean durationLessThan(DURATION first, DURATION second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean durationGreaterThan(DURATION first, DURATION second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean durationLessEqualThan(DURATION first, DURATION second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean durationGreaterEqualThan(DURATION first, DURATION second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DURATION durationAdd(DURATION first, DURATION second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DURATION durationSubtract(DURATION first, DURATION second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public NUMBER durationDivide(DURATION first, DURATION second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DURATION durationMultiplyNumber(DURATION first, NUMBER second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DURATION durationDivideNumber(DURATION first, NUMBER second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public boolean isDuration(Object value) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public boolean isYearsAndMonthsDuration(Object value) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public boolean isDaysAndTimeDuration(Object value) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
