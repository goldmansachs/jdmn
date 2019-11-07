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

import com.gs.dmn.runtime.Context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SignavioListUtil {
    public static List appendAll(List list1, List list2) {
        List result = new ArrayList();
        if (list1 != null) {
            result.addAll(list1);
        }
        if (list2 != null) {
            result.addAll(list2);
        }
        return result;
    }

    public static List remove(List list, Object element) {
        if (list == null) {
            return null;
        }
        List result = new ArrayList();
        for(Object obj: list) {
            if (element == null) {
                if (element != obj) {
                    result.add(obj);
                }
            } else {
                if (!element.equals(obj)) {
                    result.add(obj);
                }
            }
        }
        result.remove(element);
        return result;
    }

    public static List removeAll(List list1, List list2) {
        if (list1 == null) {
            return null;
        }
        List result = new ArrayList(list1);
        if (list2 != null) {
            result.removeAll(list2);
        }
        return result;
    }

    public static Boolean notContainsAny(List list1, List list2) {
        if (list1 == null || list2 == null) {
            return null;
        }
        for(Object obj2: list2) {
            if (list1.contains(obj2)) {
                return false;
            }
        }
        return true;
    }

    public static Boolean containsOnly(List list1, List list2) {
        if (list1 == null || list2 == null) {
            return null;
        }

        // If subject list is empty, only pass if the criteria list is also empty
        if (list1.isEmpty()) {
            return (list2.isEmpty());
        }

        for(Object obj1: list1) {
            if (!list2.contains(obj1)) {
                return false;
            }
        }
        return true;
    }

    public static Boolean areElementsOf(List list1, List list2) {
        if (list1 == null || list2 == null) {
            return null;
        }
        for(Object obj1: list1) {
            if (!list2.contains(obj1)) {
                return false;
            }
        }
        return true;
    }

    public static List<?> zip(List attributes, List values) {
        int card = cardinal(values);
        List result = new ArrayList<>();
        for (int i = 0; i < card; i++) {
            Context context = new Context();
            for (int j = 0; j < attributes.size(); j++) {
                Object key = attributes.get(j);
                Object value = values.get(j);
                List list = (List) value;
                if (i < list.size()) {
                    context.add(key, list.get(i));
                } else {
                    context.add(key, null);
                }
            }
            result.add(context);
        }
        return result;
    }

    private static int cardinal(List values) {
        int card = 0;
        for (Object value : values) {
            if (value instanceof List) {
                List list = (List) value;
                if (card < list.size()) {
                    card = list.size();
                }
            } else {
                if (card < 1) {
                    card = 1;
                }
            }
        }
        return card;
    }

    public static Object mode(List numbers) {
        Map<Object, Integer> map = new LinkedHashMap<>();
        for (Object n : numbers) {
            Integer counter = map.get(n);
            if (counter == null) {
                counter = 1;
            } else {
                counter++;
            }
            map.put(n, counter);
        }
        Object resultKey = null;
        Integer resultCounter = null;
        for (Object key : map.keySet()) {
            Integer counter = map.get(key);
            if (resultCounter == null || counter > resultCounter) {
                resultKey = key;
                resultCounter = counter;
            }
        }
        return resultKey;
    }
}
