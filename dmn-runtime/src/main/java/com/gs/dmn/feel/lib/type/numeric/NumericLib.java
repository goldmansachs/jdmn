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
package com.gs.dmn.feel.lib.type.numeric;

import java.util.List;

public interface NumericLib<NUMBER> {
    NUMBER number(String literal);

    NUMBER number(String from, String groupingSeparator, String decimalSeparator);

    NUMBER decimal(NUMBER n, NUMBER scale);

    // Extension to DMN 1.3
    NUMBER round(NUMBER n, NUMBER scale, String mode);

    NUMBER floor(NUMBER n);

    NUMBER ceiling(NUMBER n);

    NUMBER abs(NUMBER number);

    NUMBER intModulo(NUMBER dividend, NUMBER divisor);

    NUMBER modulo(NUMBER dividend, NUMBER divisor);

    NUMBER sqrt(NUMBER NUMBER);

    NUMBER log(NUMBER NUMBER);

    NUMBER exp(NUMBER NUMBER);

    Boolean odd(NUMBER NUMBER);

    Boolean even(NUMBER NUMBER);

    //
    // List functions
    //
    NUMBER count(List list);

    NUMBER min(List list);

    NUMBER min(Object... args);

    NUMBER max(List list);

    NUMBER max(Object... args);

    NUMBER sum(List list);

    NUMBER sum(Object... args);

    NUMBER mean(List list);

    NUMBER mean(Object... args);

    NUMBER product(List list);

    NUMBER product(Object... NUMBERs);

    NUMBER median(List list);

    NUMBER median(Object... NUMBERs);

    NUMBER stddev(List list);

    NUMBER stddev(Object... NUMBERs);

    List mode(List list);

    List mode(Object... NUMBERs);

    Number toNumber(NUMBER number);
}
