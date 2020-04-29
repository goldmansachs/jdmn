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

import com.gs.dmn.feel.lib.DefaultFEELLib;
import com.gs.dmn.feel.lib.type.BooleanType;
import com.gs.dmn.feel.lib.type.DurationType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.runtime.DMNRuntimeException;
import org.slf4j.Logger;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.math.BigDecimal;

public class DefaultDurationType extends BaseDefaultDurationType implements DurationType<Duration, BigDecimal> {
    private final BooleanType booleanType;
    private final DatatypeFactory dataTypeFactory;

    @Deprecated
    public DefaultDurationType(Logger logger) {
        super(logger);
        this.booleanType = new DefaultBooleanType(logger);
        this.dataTypeFactory = DefaultFEELLib.DATA_TYPE_FACTORY;
    }

    public DefaultDurationType(Logger logger, DatatypeFactory dataTypeFactory) {
        super(logger);
        this.booleanType = new DefaultBooleanType(logger);
        this.dataTypeFactory = dataTypeFactory;
    }

    //
    // Duration operators
    //

    @Override
    public Boolean durationEqual(Duration first, Duration second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return false;
        } else if (second == null) {
            return false;
        } else {
            return compare(first, second) == DatatypeConstants.EQUAL;
        }
    }

    @Override
    public Boolean durationNotEqual(Duration first, Duration second) {
        return booleanType.booleanNot(durationEqual(first, second));
    }

    @Override
    public Boolean durationLessThan(Duration first, Duration second) {
        if (first == null && second == null) {
            return false;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            return compare(first, second) == DatatypeConstants.LESSER;
        }
    }

    @Override
    public Boolean durationGreaterThan(Duration first, Duration second) {
        if (first == null && second == null) {
            return false;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            return compare(first, second) == DatatypeConstants.GREATER;
        }
    }

    @Override
    public Boolean durationLessEqualThan(Duration first, Duration second) {
        if (first == null && second == null) {
            return false;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int compare = compare(first, second);
            return compare == DatatypeConstants.LESSER || compare == DatatypeConstants.EQUAL;
        }
    }

    @Override
    public Boolean durationGreaterEqualThan(Duration first, Duration second) {
        if (first == null && second == null) {
            return false;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int compare = compare(first, second);
            return compare == DatatypeConstants.GREATER || compare == DatatypeConstants.EQUAL;
        }
    }

    @Override
    public Duration durationAdd(Duration first, Duration second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return first.add(second);
        } catch (Exception e) {
            String message = String.format("durationAdd(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Duration durationSubtract(Duration first, Duration second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return first.subtract(second);
        } catch (Exception e) {
            String message = String.format("durationSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Duration durationMultiply(Duration first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return first.multiply(second);
        } catch (Exception e) {
            String message = String.format("durationMultiply(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Duration durationDivide(Duration first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            if (isYearsAndMonths(first)) {
                long months = (first.getYears() * 12 + first.getMonths()) / second.intValue();
                return this.dataTypeFactory.newDurationYearMonth(String.format("P%dM", months));
            } else if (isDaysAndTime(first)) {
                long hours = 24L * first.getDays() + first.getHours();
                long minutes = 60L * hours + first.getMinutes();
                long seconds = 60L * minutes + first.getSeconds();
                seconds = seconds / second.intValue();
                return this.dataTypeFactory.newDurationDayTime(seconds * 1000L);
            } else {
                throw new DMNRuntimeException(String.format("Cannot divide '%s' by '%s'", first, second));
            }
        } catch (Exception e) {
            String message = String.format("durationDivide(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }
}
