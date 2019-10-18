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
import com.gs.dmn.feel.lib.type.DateTimeType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.time.JavaTimeType;
import org.slf4j.Logger;

import java.time.*;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;

public class TemporalDateTimeType extends JavaTimeType implements DateTimeType<Temporal, TemporalAmount> {
    private final BooleanType booleanType;

    public TemporalDateTimeType(Logger logger) {
        super(logger);
        this.booleanType = new DefaultBooleanType(logger);
    }

    //
    // Date and time operators
    //

    @Override
    public Boolean dateTimeEqual(Temporal first, Temporal second) {
        return temporalDateTimeEqual(first, second);
    }

    @Override
    public Boolean dateTimeNotEqual(Temporal first, Temporal second) {
        return booleanType.booleanNot(dateTimeEqual(first, second));
    }

    @Override
    public Boolean dateTimeLessThan(Temporal first, Temporal second) {
        return temporalDateTimeLessThan(first, second);
    }

    @Override
    public Boolean dateTimeGreaterThan(Temporal first, Temporal second) {
        return temporalDateTimeGreaterThan(first, second);
    }

    @Override
    public Boolean dateTimeLessEqualThan(Temporal first, Temporal second) {
        return temporalDateTimeLessEqualThan(first, second);
    }

    @Override
    public Boolean dateTimeGreaterEqualThan(Temporal first, Temporal second) {
        return temporalDateTimeGreaterEqualThan(first, second);
    }

    @Override
    public TemporalAmount dateTimeSubtract(Temporal first, Temporal second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return Duration.between(first, second);
        } catch (Exception e) {
            String message = String.format("dateTimeSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Temporal dateTimeAddDuration(Temporal dateTime, TemporalAmount duration) {
        if (dateTime == null || duration == null) {
            return null;
        }

        try {
            return dateTime.plus(duration);
        } catch (Exception e) {
            String message = String.format("dateTimeAddDuration(%s, %s)", dateTime, duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Temporal dateTimeSubtractDuration(Temporal dateTime, TemporalAmount duration) {
        if (dateTime == null || duration == null) {
            return null;
        }

        try {
            return dateTime.minus(duration);
        } catch (Exception e) {
            String message = String.format("dateTimeSubtractDuration(%s, %s)", dateTime, duration);
            logError(message, e);
            return null;
        }
    }

    protected Boolean temporalDateTimeEqual(Temporal first, Temporal second) {
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

    protected Boolean temporalDateTimeLessThan(Temporal first, Temporal second) {
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

    protected Boolean temporalDateTimeGreaterThan(Temporal first, Temporal second) {
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

    protected Boolean temporalDateTimeLessEqualThan(Temporal first, Temporal second) {
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

    protected Boolean temporalDateTimeGreaterEqualThan(Temporal first, Temporal second) {
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

    private Integer compare(Temporal first, Temporal second) {
        if (first instanceof LocalDateTime && second instanceof LocalDateTime) {
            return ((LocalDateTime) first).compareTo((LocalDateTime) second);
        } else if (first instanceof OffsetDateTime && second instanceof OffsetDateTime) {
            return ((OffsetDateTime) first).compareTo((OffsetDateTime) second);
        } else if (first instanceof ZonedDateTime && second instanceof ZonedDateTime) {
            return ((ZonedDateTime) first).compareTo((ZonedDateTime) second);
        }
        return null;
    }
}
