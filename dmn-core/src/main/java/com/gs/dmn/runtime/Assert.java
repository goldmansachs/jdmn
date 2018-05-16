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
package com.gs.dmn.runtime;

import com.gs.dmn.feel.lib.DateTimeUtil;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Assert {
    private static final int ASSERT_SCALE = 8;

    public static void assertEquals(Object expected, Object actual) {
        assertEquals(null, expected, actual);
    }

    public static void assertEquals(String message, Object expected, Object actual) {
        if (expected == null) {
            org.junit.Assert.assertEquals(message, expected, actual);
        } else if (isNumber(expected)) {
            org.junit.Assert.assertEquals(message, convertNumber(expected), convertNumber(actual));
        } else if (isBoolean(expected)) {
            org.junit.Assert.assertEquals(message, expected, actual);
        } else if (isString(expected)) {
            org.junit.Assert.assertEquals(message, expected, actual);
        } else if (isDateTime(expected)) {
            org.junit.Assert.assertEquals(message, convertDateTime(expected), convertDateTime(actual));
        } else if (isList(expected)) {
            if (actual == null) {
                actual = new ArrayList<>();
            }
            org.junit.Assert.assertEquals((message == null ? "" : message)  + expected.toString() + " vs " + actual.toString(), ((List) expected).size(), ((List) actual).size());
            for (int i = 0; i < ((List) expected).size(); i++) {
                assertEquals(message, ((List) expected).get(i), ((List) actual).get(i));
            }
        } else if (expected instanceof Context) {
            if (actual == null) {
                actual = new Context();
            }
            for(Object key: ((Context) expected).getBindings().keySet()) {
                Object expectedMember = ((Context) expected).get(key);
                Object actualMember = ((Context) actual).get(key);
                assertEquals(message, expectedMember, actualMember);
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
            org.junit.Assert.assertEquals(message, expected, actual);
        }
    }

    private static boolean isNumber(Object object) {
        return object instanceof BigDecimal
                || object instanceof Double
                ;
    }

    private static boolean isString(Object object) {
        return object instanceof String;
    }

    private static boolean isBoolean(Object object) {
        return object instanceof Boolean ;
    }

    private static boolean isDateTime(Object object) {
        return object instanceof XMLGregorianCalendar
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
        Method[] declaredMethods = cls.getDeclaredMethods();
        List<Method> getters = new ArrayList<>();
        for (Method m : declaredMethods) {
            if (m.getName().startsWith("get")) {
                getters.add(m);
            }
        }
        return getters;
    }

    private static Object getProperty(Object expected, Method expectedGetter) throws Exception {
        return expected == null ? null : expectedGetter.invoke(expected);
    }

    private static Object convertDateTime(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof ZonedDateTime) {
            return ((ZonedDateTime) object).withZoneSameInstant(DateTimeUtil.UTC);
        } else if (object instanceof OffsetTime) {
            return ((OffsetTime) object).withOffsetSameInstant(ZoneOffset.UTC);
        }
        return object;
    }

    private static Object convertNumber(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof BigDecimal) {
            return roundUp((BigDecimal)object);
        } else if (object instanceof Double) {
            return roundUp(BigDecimal.valueOf((Double)object));
        }
        return object;
    }

    public static Object roundUp(BigDecimal bigDecimal) {
        return bigDecimal.setScale(ASSERT_SCALE, java.math.BigDecimal.ROUND_UP).stripTrailingZeros();
    }
}
