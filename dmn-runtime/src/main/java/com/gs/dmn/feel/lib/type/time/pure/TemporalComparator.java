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
package com.gs.dmn.feel.lib.type.time.pure;

import com.gs.dmn.feel.lib.type.RelationalComparator;
import org.slf4j.Logger;

import java.time.*;
import java.time.temporal.Temporal;
import java.util.function.Supplier;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class TemporalComparator implements RelationalComparator<Temporal> {
    private final Logger logger;

    public TemporalComparator(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Integer compare(Temporal first, Temporal second) {
        // Time
        if (first instanceof LocalTime && second instanceof LocalTime) {
            return ((LocalTime) first).compareTo((LocalTime) second);
        } else if (first instanceof OffsetTime && second instanceof OffsetTime) {
            return ((OffsetTime) first).compareTo((OffsetTime) second);

        // Date time
        } else if (first instanceof LocalDateTime && second instanceof LocalDateTime) {
            return ((LocalDateTime) first).compareTo((LocalDateTime) second);
        } else if (first instanceof OffsetDateTime && second instanceof OffsetDateTime) {
            return ((OffsetDateTime) first).compareTo((OffsetDateTime) second);
        } else if (first instanceof ZonedDateTime && second instanceof ZonedDateTime) {
            return ((ZonedDateTime) first).compareTo((ZonedDateTime) second);
        }
        return  null;
    }

    @Override
    public Boolean equalTo(Temporal first, Temporal second) {
        try {
            return applyOperator(first, second, new Supplier[] {
                    () -> TRUE,
                    () -> FALSE,
                    () -> FALSE,
                    () -> { Integer result = compare(first, second); return result != null && result == 0; }
            });
        } catch (Exception e) {
            String message = String.format("=(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean lessThan(Temporal first, Temporal second) {
        try {
            return applyOperator(first, second, new Supplier[] {
                    () -> FALSE,
                    () -> null,
                    () -> null,
                    () -> { Integer result = compare(first, second); return result != null && result < 0; }
            });
        } catch (Exception e) {
            String message = String.format("<(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean greaterThan(Temporal first, Temporal second) {
        try {
            return applyOperator(first, second, new Supplier[] {
                    () -> FALSE,
                    () -> null,
                    () -> null,
                    () -> { Integer result = compare(first, second); return result != null && result > 0; }
            });
        } catch (Exception e) {
            String message = String.format(">(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean lessEqualThan(Temporal first, Temporal second) {
        try {
            return applyOperator(first, second, new Supplier[] {
                    () -> TRUE,
                    () -> null,
                    () -> null,
                    () -> { Integer result = compare(first, second); return result != null && result <= 0; }
            });
        } catch (Exception e) {
            String message = String.format("<=(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean greaterEqualThan(Temporal first, Temporal second) {
        try {
            return applyOperator(first, second, new Supplier[] {
                    () -> TRUE,
                    () -> null,
                    () -> null,
                    () -> { Integer result = compare(first, second); return result != null && result >= 0; }
            });
        } catch (Exception e) {
            String message = String.format(">=(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    protected void logError(String message, Throwable e) {
        this.logger.error(message, e);
    }
}
