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
package com.gs.dmn.feel.lib.type.range;

import com.gs.dmn.runtime.Range;

public interface RangeLib {
    Boolean before(Object point1, Object point2);
    Boolean before(Object point, Range range);
    Boolean before(Range range, Object point);
    Boolean before(Range range1, Range range2);

    Boolean after(Object point1, Object point2);
    Boolean after(Object point, Range range);
    Boolean after(Range range, Object point);
    Boolean after(Range range1, Range range2);

    Boolean meets(Range range1, Range range2);

    Boolean metBy(Range range1, Range range2);

    Boolean overlaps(Range range1, Range range2);

    Boolean overlapsBefore(Range range1, Range range2);

    Boolean overlapsAfter(Range range1, Range range2);

    Boolean finishes(Object point, Range range);
    Boolean finishes(Range range1, Range range2);

    Boolean finishedBy(Range range, Object point);
    Boolean finishedBy(Range range1, Range range2);

    Boolean includes(Range range, Object point);
    Boolean includes(Range range1, Range range2);

    Boolean during(Object point, Range range);
    Boolean during(Range range1, Range range2);

    Boolean starts(Object point, Range range);
    Boolean starts(Range range1, Range range2);

    Boolean startedBy(Range range, Object point);
    Boolean startedBy(Range range1, Range range2);

    Boolean coincides(Object point1, Object point2);
    Boolean coincides(Range range1, Range range2);
}
