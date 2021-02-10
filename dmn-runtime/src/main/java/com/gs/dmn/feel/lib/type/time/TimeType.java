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

public interface TimeType<TIME, DURATION> {
    boolean isTime(Object value);

    Long timeValue(TIME time);

    Boolean timeIs(TIME first, TIME second);

    Boolean timeEqual(TIME first, TIME second);

    Boolean timeNotEqual(TIME first, TIME second);

    Boolean timeLessThan(TIME first, TIME second);

    Boolean timeGreaterThan(TIME first, TIME second);

    Boolean timeLessEqualThan(TIME first, TIME second);

    Boolean timeGreaterEqualThan(TIME first, TIME second);

    DURATION timeSubtract(TIME first, TIME second);

    TIME timeAddDuration(TIME time, DURATION duration);

    TIME timeSubtractDuration(TIME time, DURATION duration);
}
