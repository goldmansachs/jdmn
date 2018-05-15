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
package com.gs.dmn.feel.lib.type.numeric;

import com.gs.dmn.feel.lib.BigDecimalUtil;
import com.gs.dmn.feel.lib.type.BaseType;
import com.gs.dmn.feel.lib.type.BooleanType;
import com.gs.dmn.feel.lib.type.NumericType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.math.MathContext;

public class DefaultNumericType extends BaseType implements NumericType<BigDecimal> {
    public static final MathContext MATH_CONTEXT = MathContext.DECIMAL128;

    private final BooleanType booleanType;

    public DefaultNumericType(Logger logger) {
        super(logger);
        this.booleanType = new DefaultBooleanType(logger);
    }

    @Override
    public BigDecimal numericAdd(BigDecimal first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return first.add(second, MATH_CONTEXT);
        } catch (Throwable e) {
            String message = String.format("numericAdd(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal numericSubtract(BigDecimal first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return first.subtract(second, MATH_CONTEXT);
        } catch (Throwable e) {
            String message = String.format("numericSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal numericMultiply(BigDecimal first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return first.multiply(second, MATH_CONTEXT);
        } catch (Throwable e) {
            String message = String.format("numericMultiply(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal numericDivide(BigDecimal first, BigDecimal second) {
        try {
            return BigDecimalUtil.numericDivide(first, second);
        } catch (Throwable e) {
            String message = String.format("numericDivide(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal numericUnaryMinus(BigDecimal first) {
        if (first == null) {
            return null;
        }

        try {
            return first.negate(MATH_CONTEXT);
        } catch (Throwable e) {
            String message = String.format("numericUnaryMinus(%s)", first);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal numericExponentiation(BigDecimal first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return numericExponentiation(first, second.intValue());
        } catch (Throwable e) {
            String message = String.format("numericExponentiation(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    private BigDecimal numericExponentiation(BigDecimal first, int second) {
        if (first == null) {
            return null;
        }

        try {
            if (second < 0) {
                return first.pow(second, MATH_CONTEXT);
            } else if (second == 0) {
                return BigDecimal.ONE;
            } else {
                BigDecimal temp = first.pow(-second, MATH_CONTEXT);
                return BigDecimal.ONE.divide(temp, MATH_CONTEXT);
            }
        } catch (Throwable e) {
            String message = String.format("numericExponentiation(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean numericEqual(BigDecimal first, BigDecimal second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return false;
        } else if (second == null) {
            return false;
        } else {
            int result = first.compareTo(second);
            return result == 0;
        }
    }

    @Override
    public Boolean numericNotEqual(BigDecimal first, BigDecimal second) {
        return booleanType.booleanNot(numericEqual(first, second));
    }

    @Override
    public Boolean numericLessThan(BigDecimal first, BigDecimal second) {
        if (first == null && second == null) {
            return null;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = first.compareTo(second);
            return result < 0;
        }
    }

    @Override
    public Boolean numericGreaterThan(BigDecimal first, BigDecimal second) {
        if (first == null && second == null) {
            return null;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = first.compareTo(second);
            return result > 0;
        }
    }

    @Override
    public Boolean numericLessEqualThan(BigDecimal first, BigDecimal second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = first.compareTo(second);
            return result <= 0;
        }
    }

    @Override
    public Boolean numericGreaterEqualThan(BigDecimal first, BigDecimal second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = first.compareTo(second);
            return result >= 0;
        }
    }
}
