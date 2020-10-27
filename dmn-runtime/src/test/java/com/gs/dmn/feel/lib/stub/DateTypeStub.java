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

import com.gs.dmn.feel.lib.type.DateType;
import com.gs.dmn.runtime.DMNRuntimeException;


public class DateTypeStub<DATE, DURATION> implements DateType<DATE, DURATION> {
    @Override
    public Boolean dateEqual(DATE first, DATE second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean dateNotEqual(DATE first, DATE second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean dateLessThan(DATE first, DATE second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean dateGreaterThan(DATE first, DATE second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean dateLessEqualThan(DATE first, DATE second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean dateGreaterEqualThan(DATE first, DATE second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DURATION dateSubtract(DATE first, DATE second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE dateAddDuration(DATE DATE, DURATION DURATION) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DATE dateSubtractDuration(DATE DATE, DURATION DURATION) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
