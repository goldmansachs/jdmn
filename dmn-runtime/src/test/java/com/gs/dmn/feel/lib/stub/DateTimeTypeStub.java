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

import com.gs.dmn.feel.lib.type.DateTimeType;
import com.gs.dmn.runtime.DMNRuntimeException;

public class DateTimeTypeStub<DATE_TIME, DURATION> implements DateTimeType<DATE_TIME, DURATION> {
    @Override
    public Boolean dateTimeIs(DATE_TIME first, DATE_TIME second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean dateTimeEqual(DATE_TIME first, DATE_TIME second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean dateTimeNotEqual(DATE_TIME first, DATE_TIME second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean dateTimeLessThan(DATE_TIME first, DATE_TIME second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean dateTimeGreaterThan(DATE_TIME first, DATE_TIME second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean dateTimeLessEqualThan(DATE_TIME first, DATE_TIME second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean dateTimeGreaterEqualThan(DATE_TIME first, DATE_TIME second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DURATION dateTimeSubtract(DATE_TIME first, DATE_TIME second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE_TIME dateTimeAddDuration(DATE_TIME DATE_TIME, DURATION duration) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE_TIME dateTimeSubtractDuration(DATE_TIME DATE_TIME, DURATION duration) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
