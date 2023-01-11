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
package com.gs.dmn.signavio.feel.lib.type.list;

import com.gs.dmn.runtime.Context;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DefaultSignavioListLib implements SignavioListLib<BigDecimal> {
    @Override
    public <T> List<T> append(List<T> list, T item) {
        List<T> result = new ArrayList<>();
        if (list != null) {
            result.addAll(list);
        }
        result.add(item);
        return result;
    }

    @Override
    public <T> List<T> appendAll(List<T> list1, List<T> list2) {
        List<T> result = new ArrayList<>();
        if (list1 != null) {
            result.addAll(list1);
        }
        if (list2 != null) {
            result.addAll(list2);
        }
        return result;
    }

    @Override
    public <T> List<T> remove(List<T> list, T element) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<T> result = new ArrayList<>();
        for(T obj: list) {
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

    @Override
    public <T> List<T> removeAll(List<T> list1, List<T> list2) {
        if (list1 == null || list2 == null) {
            return new ArrayList<>();
        }
        List<T> result = new ArrayList<>(list1);
        result.removeAll(list2);
        return result;
    }

    @Override
    public Boolean notContainsAny(List<?> list1, List<?> list2) {
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

    @Override
    public Boolean containsOnly(List<?> list1, List<?> list2) {
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

    @Override
    public Boolean areElementsOf(List<?> list1, List<?> list2) {
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

    @Override
    public Boolean elementOf(List<?> list1, List<?> list2) {
        return areElementsOf(list1, list2);
    }

    @Override
    public List<?> zip(List<?> attributes, List<?> values) {
        List<Context> result = new ArrayList<>();
        if (attributes == null || values == null) {
            return result;
        }
        for (Object dimension: values) {
            if (dimension == null) {
                return result;
            }
        }

        int card = cardinal(values);
        for (int i = 0; i < card; i++) {
            Context context = new Context();
            for (int j = 0; j < attributes.size(); j++) {
                Object key = attributes.get(j);
                Object value = values.get(j);
                List<?> list = (List<?>) value;
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

    private int cardinal(List<?> values) {
        int card = 0;
        for (Object value : values) {
            if (value instanceof List) {
                List<?> list = (List<?>) value;
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
}
