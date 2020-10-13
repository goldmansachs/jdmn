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
package com.gs.dmn.feel.lib.type.time.xml;

import com.gs.dmn.feel.lib.DefaultFEELLib;
import com.gs.dmn.feel.lib.type.DurationType;
import com.gs.dmn.feel.lib.type.RelationalComparator;
import org.slf4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;

public class DoubleDurationType extends BaseDefaultDurationType implements DurationType<Duration, Double> {
    @Deprecated
    public DoubleDurationType(Logger logger) {
        this(logger, DefaultFEELLib.DATA_TYPE_FACTORY, new DefaultDurationComparator());
    }

    @Deprecated
    public DoubleDurationType(Logger logger, DatatypeFactory dataTypeFactory) {
        this(logger, dataTypeFactory, new DefaultDurationComparator());
    }

    public DoubleDurationType(Logger logger, DatatypeFactory dataTypeFactory, RelationalComparator<Duration> durationComparator) {
        super(logger, dataTypeFactory, durationComparator);
    }

    //
    // Duration operators
    //
    @Override
    public Duration durationMultiply(Duration first, Double second) {
        return this.durationMultiply(first, (Number) second);
    }

    @Override
    public Duration durationDivide(Duration first, Double second) {
        return this.durationDivide(first, (Number) second);
    }
}
