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
package com.gs.dmn.runtime;

import com.gs.dmn.feel.lib.type.BaseType;
import com.gs.dmn.feel.lib.type.time.xml.BaseDefaultDurationType;
import org.junit.jupiter.api.Assertions;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class Assert {
    public static final BigDecimal NUMBER_COMPARISON_PRECISION = new BigDecimal("0.00000001");

    public static void assertEquals(Object expected, Object actual) {
        assertEquals(null, expected, actual);
    }

    public static void assertEquals(String message, Object expected, Object actual) {
        if (expected == null) {
            Assertions.assertEquals(expected, actual, message);
        } else if (isNumber(expected)) {
            BigDecimal expectedBD = (BigDecimal) normalizeNumber(expected);
            BigDecimal actualBD = (BigDecimal) normalizeNumber(actual);
            if (actual == null) {
                Assertions.assertEquals(expected, actual, message);
            } else {
                boolean condition = expectedBD.subtract(actualBD).abs().compareTo(NUMBER_COMPARISON_PRECISION) < 0;
                Assertions.assertTrue(condition, (message + ". Expected '%s' found '%s'").formatted(expectedBD, actualBD));
            }
        } else if (isBoolean(expected)) {
            Assertions.assertEquals(expected, actual, message);
        } else if (isString(expected)) {
            Assertions.assertEquals(expected, actual, message);
        } else if (isDateTime(expected)) {
            Assertions.assertEquals(normalizeDateTime(expected), normalizeDateTime(actual), message);
        } else if (isList(expected)) {
            if (actual == null) {
                Assertions.assertEquals(expected, actual, message);
            } else {
                Assertions.assertEquals(((List) expected).size(), ((List) actual).size(), (message == null ? "" : message)  + expected + " vs " + actual);
                for (int i = 0; i < ((List) expected).size(); i++) {
                    assertEquals(message, ((List) expected).get(i), ((List) actual).get(i));
                }
            }
        } else if (expected instanceof Context context) {
            if (actual == null) {
                actual = new Context();
            }
            for(Object key: context.getBindings().keySet()) {
                Object expectedMember = context.get(key);
                Object actualMember = ((Context) actual).get(key);
                assertEquals(message + " for member '%s'".formatted(key), expectedMember, actualMember);
            }
        } else if (isComplex(expected)) {
            if (actual == null) {
                List<Method> expectedGetters = getters(expected.getClass());
                for (Method expectedGetter : expectedGetters) {
                    try {
                        Object expectedProperty = getProperty(expected, expectedGetter);
                        assertEquals(message, expectedProperty, null);
                    } catch (Exception e) {
                        throw new DMNRuntimeException("Error in '%s.%s()' ".formatted(expected.getClass().getSimpleName(), expectedGetter.getName()), e);
                    }
                }
            } else {
                List<Method> expectedGetters = getters(expected.getClass());
                for (Method expectedGetter : expectedGetters) {
                    try {
                        Object expectedProperty = getProperty(expected, expectedGetter);
                        Method actualGetter = actual.getClass().getDeclaredMethod(expectedGetter.getName());
                        Object actualProperty = getProperty(actual, actualGetter);
                        assertEquals(message, expectedProperty, actualProperty);
                    } catch (Exception e) {
                        throw new DMNRuntimeException("Error in '%s.%s()' ".formatted(expected.getClass().getSimpleName(), expectedGetter.getName()), e);
                    }
                }
            }
        } else {
            Assertions.assertEquals(expected, actual, message);
        }
    }

    private static boolean isNumber(Object object) {
        return object instanceof Number;
    }

    private static boolean isString(Object object) {
        return object instanceof String;
    }

    private static boolean isBoolean(Object object) {
        return object instanceof Boolean ;
    }

    private static boolean isDateTime(Object object) {
        return object instanceof XMLGregorianCalendar
                || object instanceof java.time.Duration
                || object instanceof Duration
                || object instanceof ZonedDateTime
                || object instanceof LocalDate
                || object instanceof OffsetTime;
    }

    private static boolean isList(Object actual) {
        return actual instanceof List;
    }

    private static boolean isComplex(Object expected) {
        List<Method> getters = getters(expected.getClass());
        return !getters.isEmpty();
    }

    private static List<Method> getters(Class<?> cls) {
        List<Method> getters = new ArrayList<>();
        if (com.gs.dmn.runtime.DMNType.class.isAssignableFrom(cls)) {
            Method[] declaredMethods = cls.getDeclaredMethods();
            for (Method m : declaredMethods) {
                if (m.getName().startsWith("get")) {
                    getters.add(m);
                }
            }
        }
        return getters;
    }

    private static Object getProperty(Object expected, Method expectedGetter) throws Exception {
        return expected == null ? null : expectedGetter.invoke(expected);
    }

    private static Object normalizeDateTime(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof OffsetTime time) {
            return time.withOffsetSameInstant(ZoneOffset.UTC);
        } else if (object instanceof ZonedDateTime time) {
            return time.withZoneSameInstant(BaseType.UTC);
        } else if (object instanceof Duration duration) {
            return BaseDefaultDurationType.normalize(duration);
        } else if (object instanceof java.time.Period period) {
            return period.normalized();
        }
        return object;
    }

    private static Object normalizeNumber(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Number) {
            return new BigDecimal(object.toString());
        }
        return object;
    }
}
