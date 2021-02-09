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

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;

public class DefaultDurationComparator extends XMLDateTimeComparator<Duration> {
    @Override
    protected Integer compareTo(Duration first, Duration second) {
        Long firstValue = durationValue(first);
        Long secondValue = durationValue(second);
        long diff = firstValue - secondValue;
        if (diff == 0) {
            return DatatypeConstants.EQUAL;
        } else if (diff < 0) {
            return DatatypeConstants.LESSER;
        } else {
            return DatatypeConstants.GREATER;
        }
    }
}
