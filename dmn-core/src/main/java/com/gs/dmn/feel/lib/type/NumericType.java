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

public interface NumericType<NUMBER> {
    NUMBER numericAdd(NUMBER first, NUMBER second);

    NUMBER numericSubtract(NUMBER first, NUMBER second);

    NUMBER numericMultiply(NUMBER first, NUMBER second);

    NUMBER numericDivide(NUMBER first, NUMBER second);

    NUMBER numericUnaryMinus(NUMBER first);

    NUMBER numericExponentiation(NUMBER first, NUMBER second);

    Boolean numericEqual(NUMBER first, NUMBER second);

    Boolean numericNotEqual(NUMBER first, NUMBER second);

    Boolean numericLessThan(NUMBER first, NUMBER second);

    Boolean numericGreaterThan(NUMBER first, NUMBER second);

    Boolean numericLessEqualThan(NUMBER first, NUMBER second);

    Boolean numericGreaterEqualThan(NUMBER first, NUMBER second);
}
