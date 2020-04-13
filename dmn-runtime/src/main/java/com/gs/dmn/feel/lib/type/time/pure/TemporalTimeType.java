/*
 * Copyright 2016 Goldman Sachs.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * <p>
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.feel.lib.type.time.pure;

import com.gs.dmn.feel.lib.type.BooleanType;
import com.gs.dmn.feel.lib.type.TimeType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.time.JavaTimeType;
import org.slf4j.Logger;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;

public class TemporalTimeType extends JavaTimeType implements TimeType<Temporal, TemporalAmount> {
    private final BooleanType booleanType;

    public TemporalTimeType(Logger logger) {
        super(logger);
        this.booleanType = new DefaultBooleanType(logger);
    }

    //
    // Time operators
    //

    @Override
    public Boolean timeEqual(Temporal first, Temporal second) {
        return temporalTimeEqual(first, second);
    }

    @Override
    public Boolean timeNotEqual(Temporal first, Temporal second) {
        return booleanType.booleanNot(timeEqual(first, second));
    }

    @Override
    public Boolean timeLessThan(Temporal first, Temporal second) {
        return temporalTimeLessThan(first, second);
    }

    @Override
    public Boolean timeGreaterThan(Temporal first, Temporal second) {
        return temporalTimeGreaterThan(first, second);
    }

    @Override
    public Boolean timeLessEqualThan(Temporal first, Temporal second) {
        return temporalTimeLessEqualThan(first, second);
    }

    @Override
    public Boolean timeGreaterEqualThan(Temporal first, Temporal second) {
        return temporalTimeGreaterEqualThan(first, second);
    }

    @Override
    public TemporalAmount timeSubtract(Temporal first, Temporal second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return java.time.Duration.between(second, first);
        } catch (Exception e) {
            String message = String.format("timeSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Temporal timeAddDuration(Temporal time, TemporalAmount duration) {
        if (time == null || duration == null) {
            return null;
        }

        try {
            return time.plus(duration);
        } catch (Exception e) {
            String message = String.format("timeAddDuration(%s, %s)", time, duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Temporal timeSubtractDuration(Temporal time, TemporalAmount duration) {
        if (time == null || duration == null) {
            return null;
        }

        try {
            return time.minus(duration);
        } catch (Exception e) {
            String message = String.format("timeSubtractDuration(%s, %s)", time, duration);
            logError(message, e);
            return null;
        }
    }

    protected Boolean temporalTimeEqual(Temporal first, Temporal second) {
        try {
            if (first == null && second == null) {
                return true;
            } else if (first == null) {
                return false;
            } else if (second == null) {
                return false;
            } else {
                Integer result = compare(first, second);
                return result != null && result == 0;
            }
        } catch (Exception e) {
            String message = String.format("=(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    protected Boolean temporalTimeLessThan(Temporal first, Temporal second) {
        try {
            if (first == null && second == null) {
                return false;
            } else if (first == null) {
                return null;
            } else if (second == null) {
                return null;
            } else {
                Integer result = compare(first, second);
                return result != null && result < 0;
            }
        } catch (Exception e) {
            String message = String.format("<(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    protected Boolean temporalTimeGreaterThan(Temporal first, Temporal second) {
        try {
            if (first == null && second == null) {
                return false;
            } else if (first == null) {
                return null;
            } else if (second == null) {
                return null;
            } else {
                Integer result = compare(first, second);
                return result != null && result > 0;
            }
        } catch (Exception e) {
            String message = String.format(">(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    protected Boolean temporalTimeLessEqualThan(Temporal first, Temporal second) {
        try {
            if (first == null && second == null) {
                return true;
            } else if (first == null) {
                return null;
            } else if (second == null) {
                return null;
            } else {
                Integer result = compare(first, second);
                return result != null && result <= 0;
            }
        } catch (Exception e) {
            String message = String.format("<=(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    protected Boolean temporalTimeGreaterEqualThan(Temporal first, Temporal second) {
        try {
            if (first == null && second == null) {
                return true;
            } else if (first == null) {
                return null;
            } else if (second == null) {
                return null;
            } else {
                Integer result = compare(first, second);
                return result != null && result >= 0;
            }
        } catch (Exception e) {
            String message = String.format(">=(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    protected Integer compare(Temporal first, Temporal second) {
        if (first instanceof LocalTime && second instanceof LocalTime) {
            return ((LocalTime) first).compareTo((LocalTime) second);
        } else if (first instanceof OffsetTime && second instanceof OffsetTime) {
            return ((OffsetTime) first).compareTo((OffsetTime) second);
        }
        return  null;
    }
}
