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
package com.gs.dmn.feel.lib.type.string;

import com.gs.dmn.feel.lib.type.BaseType;
import com.gs.dmn.feel.lib.type.ComparableComparator;
import com.gs.dmn.feel.lib.type.StringType;
import org.slf4j.Logger;

public class DefaultStringType extends BaseType implements StringType {
    private final ComparableComparator<String> comparator;

    public DefaultStringType(Logger logger) {
        super(logger);
        this.comparator = new ComparableComparator<>();
    }

    @Override
    public String stringAdd(String first, String second) {
        if (first == null || second == null) {
            return null;
        } else {
            return first + second;
        }
    }

    @Override
    public Boolean stringEqual(String first, String second) {
        return this.comparator.equalTo(first, second);
    }

    @Override
    public Boolean stringNotEqual(String first, String second) {
        return this.comparator.notEqualTo(first, second);
    }

    @Override
    public Boolean stringLessThan(String first, String second) {
        return this.comparator.lessThan(first, second);
    }

    @Override
    public Boolean stringGreaterThan(String first, String second) {
        return this.comparator.greaterThan(first, second);
    }

    @Override
    public Boolean stringLessEqualThan(String first, String second) {
        return this.comparator.lessEqualThan(first, second);
    }

    @Override
    public Boolean stringGreaterEqualThan(String first, String second) {
        return this.comparator.greaterEqualThan(first, second);
    }
}
