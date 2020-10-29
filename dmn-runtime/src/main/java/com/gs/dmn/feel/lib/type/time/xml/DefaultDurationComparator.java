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
import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.util.function.Supplier;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class DefaultDurationComparator implements RelationalComparator<Duration> {
    @Override
    public Integer compare(Duration first, Duration second) {
        BigDecimal firstValue = BaseDefaultDurationType.normalize(first);
        BigDecimal secondValue = BaseDefaultDurationType.normalize(second);
        return compare(firstValue, secondValue);
    }

    @Override
    public Boolean equalTo(Duration first, Duration second) {
        return applyOperator(first, second, new Supplier[] {
                () -> TRUE,
                () -> FALSE,
                () -> FALSE,
                () -> compare(first, second) == DatatypeConstants.EQUAL
        });
    }

    @Override
    public Boolean lessThan(Duration first, Duration second) {
        return applyOperator(first, second, new Supplier[] {
                () -> FALSE,
                () -> null,
                () -> null,
                () -> compare(first, second) == DatatypeConstants.LESSER
        });
    }

    @Override
    public Boolean greaterThan(Duration first, Duration second) {
        return applyOperator(first, second, new Supplier[] {
                () -> FALSE,
                () -> null,
                () -> null,
                () -> compare(first, second) == DatatypeConstants.GREATER
        });
    }

    @Override
    public Boolean lessEqualThan(Duration first, Duration second) {
        return applyOperator(first, second, new Supplier[] {
                () -> FALSE,
                () -> null,
                () -> null,
                () -> { Integer result = compare(first, second); return result == DatatypeConstants.LESSER || result == DatatypeConstants.EQUAL; }
        });
    }

    @Override
    public Boolean greaterEqualThan(Duration first, Duration second) {
        return applyOperator(first, second, new Supplier[] {
                () -> FALSE,
                () -> null,
                () -> null,
                () -> { Integer result = compare(first, second); return result == DatatypeConstants.GREATER || result == DatatypeConstants.EQUAL; }
        });
    }

    private Integer compare(BigDecimal first, BigDecimal second) {
        int diff = first.subtract(second).intValue();
        if (diff == 0) {
            return DatatypeConstants.EQUAL;
        } else if (diff < 0) {
            return DatatypeConstants.LESSER;
        } else {
            return DatatypeConstants.GREATER;
        }
    }
}
