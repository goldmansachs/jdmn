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
package com.gs.dmn.feel.lib.type.bool;

import java.time.Duration;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAmount;
import java.util.Arrays;
import java.util.List;

public class DefaultBooleanLib {
    public Boolean and(List list) {
        return all(list);
    }

    public Boolean and(Object... args) {
        return all(args);
    }

    public Boolean all(List list) {
        if (list == null) {
            return null;
        }

        if (list.stream().anyMatch(b -> b == Boolean.FALSE)) {
            return false;
        } else if (list.stream().allMatch(b -> b == Boolean.TRUE)) {
            return true;
        } else {
            return null;
        }
    }

    public Boolean all(Object... args) {
        if (args == null) {
            return null;
        }

        return all(Arrays.asList(args));
    }

    public Boolean or(List list) {
        return any(list);
    }

    public Boolean or(Object... args) {
        return any(args);
    }

    public Boolean any(List list) {
        if (list == null) {
            return null;
        }

        if (list.stream().anyMatch(b -> b == Boolean.TRUE)) {
            return true;
        } else if (list.stream().allMatch(b -> b == Boolean.FALSE)) {
            return false;
        } else {
            return null;
        }
    }

    public Boolean any(Object... args) {
        if (args == null) {
            return null;
        }

        return any(Arrays.asList(args));
    }
}
