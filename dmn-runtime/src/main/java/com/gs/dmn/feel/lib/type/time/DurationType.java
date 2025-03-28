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
package com.gs.dmn.feel.lib.type.time;

public interface DurationType<DURATION,  NUMBER> {
    default boolean isDuration(Object value) {
        return isYearsAndMonthsDuration(value) || isDaysAndTimeDuration(value);
    }

    boolean isYearsAndMonthsDuration(Object value);

    boolean isDaysAndTimeDuration(Object value);

    Long durationValue(DURATION duration);

    Boolean durationIs(DURATION first, DURATION second);

    Boolean durationEqual(DURATION first, DURATION second);

    Boolean durationNotEqual(DURATION first, DURATION second);

    Boolean durationLessThan(DURATION first, DURATION second);

    Boolean durationGreaterThan(DURATION first, DURATION second);

    Boolean durationLessEqualThan(DURATION first, DURATION second);

    Boolean durationGreaterEqualThan(DURATION first, DURATION second);

    DURATION durationAdd(DURATION first, DURATION second);

    DURATION durationSubtract(DURATION first, DURATION second);

    NUMBER durationDivide(DURATION first, DURATION second);

    DURATION durationMultiplyNumber(DURATION first, NUMBER second);

    DURATION durationDivideNumber(DURATION first, NUMBER second);

    DURATION durationUnaryMinus(DURATION first);
}
