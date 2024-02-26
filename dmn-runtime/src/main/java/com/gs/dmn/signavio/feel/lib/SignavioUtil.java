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
package com.gs.dmn.signavio.feel.lib;

import com.gs.dmn.runtime.DMNRuntimeException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

public class SignavioUtil {
    public static boolean areNullSafe(Object... inputs) {
        for (Object o: inputs) {
            if (o == null) {
                return false;
            }
        }

        return true;
    }

    public static <T> boolean verifyValidityOfListArgument(List<T> list) {
        return areNullSafe(list) && !list.isEmpty() && !list.contains(null);
    }

    public static boolean matchesAnyOf(Object obj, List<?> list) {
        for (Object e: list) {
            if (matches(obj, e)) {
                return true;
            }
        }

        return false;
    }

    public static boolean matches(Object o1, Object o2) {
        return nullSafeEval(equals(o1, o2));
    }

    public static boolean nullSafeEval(Boolean input) {
        return input != null && input;
    }

    public static Boolean equals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        } else if (o1 != null && o2 != null) {
            if (o1 instanceof Number && o2 instanceof Number) {
                return equalNumbers(o1, o2);
            } else if (o1 instanceof Temporal && o2 instanceof Temporal) {
                return equalTemporal(o1, o2);
            } else if (o1 instanceof List && o2 instanceof List) {
                return equalLists(o1, o2);
            } else {
                return o1.equals(o2);
            }
        }
        return false;
    }

    private static Boolean equalNumbers(Object o1, Object o2) {
        return ((BigDecimal) o1).compareTo((BigDecimal) o2) == 0;
    }

    private static Boolean equalLists(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        } else if (o1 == null || o2 == null) {
            return false;
        } else {
            List<?> l1 = (List<?>) o1;
            List<?> l2 = (List<?>) o2;
            if (l1.size() != l2.size()) {
                return false;
            }
            for (int i=0; i<l1.size(); i++) {
                if (!matches(l1.get(i), l2.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    private static Boolean equalTemporal(Object o1, Object o2) {
        if (o1 instanceof LocalDate firstDate && o2 instanceof LocalDate secondDate) {
            return firstDate.isEqual(secondDate);
        } else if (o1 instanceof OffsetTime firstOffsetTime && o2 instanceof OffsetTime secondOffsetTime) {
            return firstOffsetTime.isEqual(secondOffsetTime);
        } else if (o1 instanceof OffsetDateTime firstOffsetDateTime && o2 instanceof OffsetDateTime secondOffsetDateTime) {
            return firstOffsetDateTime.isEqual(secondOffsetDateTime);
        }
        return false;
    }

    public static List<BigDecimal> asBigDecimals(List<?> objects) {
        List<BigDecimal> result = new ArrayList<>();
        for (Object o: objects) {
            result.add(asBigDecimal(o));
        }
        return result;
    }

    public static BigDecimal asBigDecimal(Object e) {
        if (e instanceof BigDecimal decimal) {
            return decimal;
        } else {
            throw new DMNRuntimeException("Expected number found '%s'".formatted(e));
        }
    }

    public static List<Double> asDoubles(List<?> objects) {
        List<Double> result = new ArrayList<>();
        for (Object o: objects) {
            result.add(asDouble(o));
        }
        return result;
    }

    public static Double asDouble(Object e) {
        if (e instanceof Double double1) {
            return double1;
        } else {
            throw new DMNRuntimeException("Expected number found '%s'".formatted(e));
        }
    }

    public static boolean validFieldNamesAndValueList(List<?> attributes, List<?> values) {
        if (attributes.size() != values.size()) {
            return false;
        } else {
            for (int i=0; i<attributes.size(); i++) {
                Object attribute = attributes.get(i);
                if (!(attribute instanceof String)) {
                    return false;
                }
                Object value = values.get(i);
                if (!(value instanceof List)) {
                    return false;
                }
            }
            return true;
        }
    }
}
