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

import com.gs.dmn.feel.lib.type.range.RangeLib;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Range;

public class RangeLibStub implements RangeLib {
    @Override
    public Range<?> range(String from) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean rangeContains(Range<?> range, Object point) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean before(Object point1, Object point2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean before(Object point, Range<?> range) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean before(Range<?> range, Object point) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean before(Range<?> range1, Range<?> range2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean after(Object point1, Object point2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean after(Object point, Range<?> range) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean after(Range<?> range, Object point) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean after(Range<?> range1, Range<?> range2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean meets(Range<?> range1, Range<?> range2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean metBy(Range<?> range1, Range<?> range2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean overlaps(Range<?> range1, Range<?> range2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean overlapsBefore(Range<?> range1, Range<?> range2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean overlapsAfter(Range<?> range1, Range<?> range2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean finishes(Object point, Range<?> range) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean finishes(Range<?> range1, Range<?> range2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean finishedBy(Range<?> range, Object point) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean finishedBy(Range<?> range1, Range<?> range2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean includes(Range<?> range, Object point) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean includes(Range<?> range1, Range<?> range2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean during(Object point, Range<?> range) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean during(Range<?> range1, Range<?> range2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean starts(Object point, Range<?> range) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean starts(Range<?> range1, Range<?> range2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean startedBy(Range<?> range, Object point) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean startedBy(Range<?> range1, Range<?> range2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean coincides(Object point1, Object point2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean coincides(Range<?> range1, Range<?> range2) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
