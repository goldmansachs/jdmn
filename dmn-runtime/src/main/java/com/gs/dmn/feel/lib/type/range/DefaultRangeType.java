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

import com.gs.dmn.feel.lib.type.BaseType;
import com.gs.dmn.feel.lib.type.BooleanType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.runtime.Range;
import org.slf4j.Logger;

public class DefaultRangeType extends BaseType implements RangeType {
    private final BooleanType booleanType;

    public DefaultRangeType(Logger logger) {
        super(logger);
        this.booleanType = new DefaultBooleanType(logger);
    }

    @Override
    public Boolean rangeIs(Range range1, Range range2) {
        return rangeEqual(range1, range2);
    }

    @Override
    public Boolean rangeEqual(Range range1, Range range2) {
        return range1 != null && range1.equals(range2);
    }

    @Override
    public Boolean rangeNotEqual(Range range1, Range range2) {
        return this.booleanType.booleanNot(rangeEqual(range1, range2));
    }
}