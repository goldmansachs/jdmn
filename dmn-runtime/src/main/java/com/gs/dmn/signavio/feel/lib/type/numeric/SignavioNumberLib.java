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
package com.gs.dmn.signavio.feel.lib.type.numeric;

import java.util.List;

public interface SignavioNumberLib<NUMBER> {
    //
    // Conversion
    //
    NUMBER number(String text);
    NUMBER number(String text, NUMBER defaultValue);

    NUMBER abs(NUMBER number);

    NUMBER count(List<?> list);

    NUMBER round(NUMBER number, NUMBER digits);

    NUMBER ceiling(NUMBER number);

    NUMBER floor(NUMBER number);

    NUMBER integer(NUMBER number);

    NUMBER modulo(NUMBER dividend, NUMBER divisor);

    NUMBER percent(NUMBER base);

    NUMBER power(NUMBER base, NUMBER exponent);

    NUMBER product(List<?> factors);

    NUMBER roundDown(NUMBER number, NUMBER digits);

    NUMBER roundUp(NUMBER number, NUMBER digits);

    NUMBER sum(List<?> numbers);

    NUMBER avg(List<?> list);

    NUMBER max(List<?> numbers);

    NUMBER median(List<?> numbers);

    NUMBER min(List<?> numbers);

    Object signavioMode(List<?> numbers);

    NUMBER valueOf(long number);

    int intValue(NUMBER number);

    Number toNumber(NUMBER number);
}

