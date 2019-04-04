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

import com.gs.dmn.feel.lib.DoubleUtil;
import com.gs.dmn.feel.lib.type.BaseType;
import com.gs.dmn.feel.lib.type.BooleanType;
import com.gs.dmn.feel.lib.type.NumericType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import org.slf4j.Logger;

public class DoubleNumericType extends BaseType implements NumericType<Double> {
    private final BooleanType booleanType;

    public DoubleNumericType(Logger logger) {
        super(logger);
        this.booleanType = new DefaultBooleanType(logger);
    }

    @Override
    public Double numericAdd(Double first, Double second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return first + second;
        } catch (Throwable e) {
            String message = String.format("numericAdd(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double numericSubtract(Double first, Double second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return first - second;
        } catch (Throwable e) {
            String message = String.format("numericSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double numericMultiply(Double first, Double second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return first * second;
        } catch (Throwable e) {
            String message = String.format("numericMultiply(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double numericDivide(Double first, Double second) {
        try {
            return DoubleUtil.numericDivide(first, second);
        } catch (Throwable e) {
            String message = String.format("numericDivide(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double numericUnaryMinus(Double first) {
        if (first == null) {
            return null;
        }

        try {
            return - first;
        } catch (Throwable e) {
            String message = String.format("numericUnaryMinus(%s)", first);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double numericExponentiation(Double first, Double second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return Math.pow(first, second);
        } catch (Throwable e) {
            String message = String.format("numericExponentiation(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean numericEqual(Double first, Double second) {
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
    public Boolean numericNotEqual(Double first, Double second) {
        return booleanType.booleanNot(numericEqual(first, second));
    }

    @Override
    public Boolean numericLessThan(Double first, Double second) {
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
    public Boolean numericGreaterThan(Double first, Double second) {
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
    public Boolean numericLessEqualThan(Double first, Double second) {
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
    public Boolean numericGreaterEqualThan(Double first, Double second) {
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
