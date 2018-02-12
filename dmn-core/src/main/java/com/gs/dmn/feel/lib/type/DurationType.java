/**
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
package com.gs.dmn.feel.lib.type;

public interface DurationType<DURATION,  NUMBER> {
    Boolean durationEqual(DURATION first, DURATION second);

    Boolean durationNotEqual(DURATION first, DURATION second);

    Boolean durationLessThan(DURATION first, DURATION second);

    Boolean durationGreaterThan(DURATION first, DURATION second);

    Boolean durationLessEqualThan(DURATION first, DURATION second);

    Boolean durationGreaterEqualThan(DURATION first, DURATION second);

    DURATION durationAdd(DURATION first, DURATION second);

    DURATION durationSubtract(DURATION first, DURATION second);

    DURATION durationMultiply(DURATION first, NUMBER second);

    DURATION durationDivide(DURATION first, NUMBER second);
}
