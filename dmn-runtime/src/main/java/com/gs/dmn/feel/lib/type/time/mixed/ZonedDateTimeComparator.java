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
package com.gs.dmn.feel.lib.type.time.mixed;

import com.gs.dmn.feel.lib.type.RelationalComparator;
import com.gs.dmn.feel.lib.type.time.xml.DefaultDateTimeLib;

import java.time.ZonedDateTime;
import java.util.function.Supplier;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ZonedDateTimeComparator implements RelationalComparator<ZonedDateTime> {
    @Override
    public Integer compare(ZonedDateTime first, ZonedDateTime second) {
        return first.withZoneSameInstant(DefaultDateTimeLib.UTC).compareTo(second.withZoneSameInstant(DefaultDateTimeLib.UTC));
    }

    @Override
    public Boolean equalTo(ZonedDateTime first, ZonedDateTime second) {
        return applyOperator(first, second, new Supplier[] {
                () -> TRUE,
                () -> FALSE,
                () -> FALSE,
                () -> first.compareTo(second) == 0
        });
    }

    @Override
    public Boolean lessThan(ZonedDateTime first, ZonedDateTime second) {
        if (first == null && second == null) {
            return false;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = compare(first, second);
            return result < 0;
        }
    }

    @Override
    public Boolean greaterThan(ZonedDateTime first, ZonedDateTime second) {
        if (first == null && second == null) {
            return false;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = compare(first, second);
            return result > 0;
        }
    }

    @Override
    public Boolean lessEqualThan(ZonedDateTime first, ZonedDateTime second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = compare(first, second);
            return result <= 0;
        }
    }

    @Override
    public Boolean greaterEqualThan(ZonedDateTime first, ZonedDateTime second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = compare(first, second);
            return result >= 0;
        }
    }
}
