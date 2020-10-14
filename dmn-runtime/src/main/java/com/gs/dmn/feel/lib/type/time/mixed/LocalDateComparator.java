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

import java.time.LocalDate;
import java.util.function.Supplier;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class LocalDateComparator implements RelationalComparator<LocalDate> {
    @Override
    public Integer compare(LocalDate first, LocalDate second) {
        return first.compareTo(second);
    }

    @Override
    public Boolean equalTo(LocalDate first, LocalDate second) {
        return applyOperator(first, second, new Supplier[] {
                () -> TRUE,
                () -> FALSE,
                () -> FALSE,
                () -> first.compareTo(second) == 0
        });
    }

    @Override
    public  Boolean lessThan(LocalDate first, LocalDate second) {
        return applyOperator(first, second, new Supplier[] {
                () -> FALSE,
                () -> null,
                () -> null,
                () -> first.compareTo(second) < 0
        });
    }

    @Override
    public  Boolean greaterThan(LocalDate first, LocalDate second) {
        return applyOperator(first, second, new Supplier[] {
                () -> FALSE,
                () -> null,
                () -> null,
                () -> first.compareTo(second) > 0
        });
    }

    @Override
    public  Boolean lessEqualThan(LocalDate first, LocalDate second) {
        return applyOperator(first, second, new Supplier[] {
                () -> TRUE,
                () -> null,
                () -> null,
                () -> first.compareTo(second) <= 0
        });
    }

    @Override
    public  Boolean greaterEqualThan(LocalDate first, LocalDate second) {
        return applyOperator(first, second, new Supplier[] {
                () -> TRUE,
                () -> null,
                () -> null,
                () -> first.compareTo(second) >= 0
        });
    }
}
