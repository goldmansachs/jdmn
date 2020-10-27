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

import com.gs.dmn.feel.lib.type.time.DurationLib;
import com.gs.dmn.runtime.DMNRuntimeException;

public class DurationLibStub<DATE, DURATION> implements DurationLib<DATE, DURATION> {
    @Override
    public DURATION duration(String from) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public DURATION yearsAndMonthsDuration(DATE from, DATE to) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Long years(DURATION duration) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Long months(DURATION duration) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Long days(DURATION duration) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Long hours(DURATION duration) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Long minutes(DURATION duration) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Long seconds(DURATION duration) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
