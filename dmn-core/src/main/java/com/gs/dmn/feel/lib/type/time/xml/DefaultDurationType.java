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
package com.gs.dmn.feel.lib.type.time.xml;

import com.gs.dmn.feel.lib.type.BaseType;
import com.gs.dmn.feel.lib.type.BooleanType;
import com.gs.dmn.feel.lib.type.DurationType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import org.slf4j.Logger;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;

public class DefaultDurationType extends BaseType implements DurationType<Duration, BigDecimal> {
    private final BooleanType booleanType;

    public DefaultDurationType(Logger logger) {
        super(logger);
        this.booleanType = new DefaultBooleanType(logger);
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
            return first.equals(second);
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
            return first.compare(second) < 0;
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
            return first.compare(second) > 0;
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
            return first.compare(second) <= 0;
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
            return first.compare(second) >= 0;
        }
    }

    @Override
    public Duration durationAdd(Duration first, Duration second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return first.add(second);
        } catch (Throwable e) {
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
        } catch (Throwable e) {
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
        } catch (Throwable e) {
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
            return first.multiply(BigDecimal.ONE.divide(second));
        } catch (Throwable e) {
            String message = String.format("durationDivide(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }
}
