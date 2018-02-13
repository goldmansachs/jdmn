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

import java.util.List;

public interface BooleanType {
    Boolean booleanNot(Boolean operand);

    Boolean booleanOr(List<Boolean> operands);

    Boolean booleanOr(Boolean... operands);

    Boolean binaryBooleanOr(Boolean first, Boolean second);

    Boolean booleanAnd(List<Boolean> operands);

    Boolean booleanAnd(Boolean... operands);

    Boolean binaryBooleanAnd(Boolean first, Boolean second);

    Boolean booleanEqual(Boolean first, Boolean second);

    Boolean booleanNotEqual(Boolean first, Boolean second);
}
