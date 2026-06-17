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
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;

import javax.xml.datatype.Duration;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

public class Assert {
    public static final BigDecimal NUMBER_COMPARISON_PRECISION = new BigDecimal("0.00000001");

    public static void assertEquals(Object expected, Object actual) {
        assertEquals("", expected, actual);
    }

    public static void assertEquals(String message, Object expected, Object actual) {
        if (expected == null) {
            Assertions.assertEquals(expected, actual, message);
        } else if (isNumber(expected)) {
            if (actual == null) {
                Assertions.assertEquals(expected, actual, message);
            } else {
                BigDecimal expectedBD = (BigDecimal) normalizeNumber(expected);
                BigDecimal actualBD = (BigDecimal) normalizeNumber(actual);
                Assertions.assertEquals(expectedBD.doubleValue(), actualBD.doubleValue(), NUMBER_COMPARISON_PRECISION.doubleValue(), message);
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
                int expectedSize = ((List) expected).size();
                int actualSize = ((List) actual).size();
                String sizeErrorMessage = String.format("Size of %s vs %s", expected, actual);
                Assertions.assertEquals(expectedSize, actualSize, appendMessage(message, sizeErrorMessage));
                for (int i = 0; i < expectedSize; i++) {
                    String elementErrorMessage = String.format("%s vs %s at index %s", expected, actual, i + 1);
                    assertEquals(appendMessage(message, elementErrorMessage), ((List) expected).get(i), ((List) actual).get(i));
                }
            }
        } else if (expected instanceof Context) {
            if (actual == null) {
                actual = new Context();
            }
            for(Object key: ((Context) expected).keySet()) {
                Object expectedMember = ((Context) expected).get(key);
                Object actualMember = ((Context) actual).get(key);
                String errorMessage = String.format("%s vs %s at member %s", expected, actual, key);
                assertEquals(appendMessage(message, errorMessage), expectedMember, actualMember);
            }
        } else if (isComplex(expected)) {
            if (actual == null) {
                List<Method> expectedGetters = getters(expected.getClass());
                for (Method expectedGetter : expectedGetters) {
                    try {
                        Object expectedProperty = getProperty(expected, expectedGetter);
                        assertEquals(message, expectedProperty, null);
                    } catch (Exception e) {
                        throw new DMNRuntimeException(String.format("Error in '%s.%s()' ", expected.getClass().getSimpleName(), expectedGetter.getName()), e);
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
                        throw new DMNRuntimeException(String.format("Error in '%s.%s()' ", expected.getClass().getSimpleName(), expectedGetter.getName()), e);
                    }
                }
            }
        } else {
            Assertions.assertEquals(expected, actual, message);
        }
    }

    private static String appendMessage(String message1, String message2) {
        if (StringUtils.isBlank(message1)) {
            return message2;
        } else if (StringUtils.isBlank(message2)) {
            return message1;
        } else {
            return message1 + " " + message2;
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
        return object instanceof TemporalAccessor
                || object instanceof Duration
                || object instanceof java.time.Duration
                ;
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
        if (object instanceof OffsetTime) {
            return ((OffsetTime) object).withOffsetSameInstant(ZoneOffset.UTC);
        } else if (object instanceof ZonedDateTime) {
            return ((ZonedDateTime) object).withZoneSameInstant(BaseType.UTC);
        } else if (object instanceof Duration) {
            return BaseDefaultDurationType.normalize((Duration) object);
        } else if (object instanceof java.time.Period) {
            return ((Period) object).normalized();
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
