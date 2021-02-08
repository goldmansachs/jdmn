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

import com.gs.dmn.feel.lib.type.RelationalComparator;

import javax.xml.datatype.DatatypeConstants;
import java.util.function.Supplier;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public abstract class XMLDateTimeComparator<T> extends XMLCalendarType implements RelationalComparator<T> {
    @Override
    public Integer compare(T first, T second) {
        if (first == null || second == null) {
            return null;
        } else {
            return compareTo(first, second);
        }
    }

    @Override
    public Boolean equalTo(T first, T second) {
        return applyOperator(first, second, new Supplier[] {
                () -> TRUE,
                () -> FALSE,
                () -> FALSE,
                () -> compareTo(first, second) == DatatypeConstants.EQUAL
        });
    }

    @Override
    public Boolean lessThan(T first, T second) {
        return applyOperator(first, second, new Supplier[] {
                () -> null,
                () -> null,
                () -> null,
                () -> compareTo(first, second) == DatatypeConstants.LESSER
        });
    }

    @Override
    public Boolean greaterThan(T first, T second) {
        return applyOperator(first, second, new Supplier[] {
                () -> null,
                () -> null,
                () -> null,
                () -> compareTo(first, second) == DatatypeConstants.GREATER
        });
    }

    @Override
    public Boolean lessEqualThan(T first, T second) {
        return applyOperator(first, second, new Supplier[] {
                () -> TRUE,
                () -> null,
                () -> null,
                () -> { Integer result = compareTo(first, second); return result == DatatypeConstants.LESSER || result == DatatypeConstants.EQUAL; }
        });
    }

    @Override
    public Boolean greaterEqualThan(T first, T second) {
        return applyOperator(first, second, new Supplier[] {
                () -> TRUE,
                () -> null,
                () -> null,
                () -> { Integer result = compareTo(first, second); return result == DatatypeConstants.GREATER || result == DatatypeConstants.EQUAL; }
        });
    }

    protected abstract Integer compareTo(T first, T second);
}
