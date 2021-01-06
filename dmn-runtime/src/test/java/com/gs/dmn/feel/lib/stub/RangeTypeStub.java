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

import com.gs.dmn.feel.lib.type.range.RangeType;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Range;

public class RangeTypeStub implements RangeType {
    @Override
    public Boolean rangeIs(Range range1, Range range2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean rangeEqual(Range range1, Range range2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean rangeNotEqual(Range range1, Range range2) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
