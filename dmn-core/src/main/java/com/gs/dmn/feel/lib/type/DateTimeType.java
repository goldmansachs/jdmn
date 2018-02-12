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

public interface DateTimeType<DATE_TIME, DURATION> {
    Boolean dateTimeEqual(DATE_TIME first, DATE_TIME second);

    Boolean dateTimeNotEqual(DATE_TIME first, DATE_TIME second);

    Boolean dateTimeLessThan(DATE_TIME first, DATE_TIME second);

    Boolean dateTimeGreaterThan(DATE_TIME first, DATE_TIME second);

    Boolean dateTimeLessEqualThan(DATE_TIME first, DATE_TIME second);

    Boolean dateTimeGreaterEqualThan(DATE_TIME first, DATE_TIME second);

    DURATION dateTimeSubtract(DATE_TIME first, DATE_TIME second);

    DATE_TIME dateTimeAddDuration(DATE_TIME dateTime, DURATION duration);

    DATE_TIME dateTimeSubtractDuration(DATE_TIME dateTime, DURATION duration);
}
