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

import java.util.Arrays;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class DefaultBooleanLib implements BooleanLib {
    @Override
    public Boolean and(List<Object> list) {
        return all(list);
    }

    @Override
    public Boolean and(Object... args) {
        return all(args);
    }

    @Override
    public Boolean all(List<Object> list) {
        if (list == null) {
            return null;
        }

        if (list.stream().anyMatch(b -> b == Boolean.FALSE)) {
            return FALSE;
        } else if (list.stream().allMatch(b -> b == Boolean.TRUE)) {
            return TRUE;
        } else {
            return null;
        }
    }

    @Override
    public Boolean all(Object... args) {
        if (args == null) {
            return null;
        }

        return all(Arrays.asList(args));
    }

    @Override
    public Boolean or(List<Object> list) {
        return any(list);
    }

    @Override
    public Boolean or(Object... args) {
        return any(args);
    }

    @Override
    public Boolean any(List<Object> list) {
        if (list == null) {
            return null;
        }

        if (list.stream().anyMatch(b -> b == Boolean.TRUE)) {
            return TRUE;
        } else if (list.stream().allMatch(b -> b == Boolean.FALSE)) {
            return FALSE;
        } else {
            return null;
        }
    }

    @Override
    public Boolean any(Object... args) {
        if (args == null) {
            return null;
        }

        return any(Arrays.asList(args));
    }
}
