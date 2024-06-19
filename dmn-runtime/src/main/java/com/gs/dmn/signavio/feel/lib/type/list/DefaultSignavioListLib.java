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
import com.gs.dmn.signavio.feel.lib.SignavioUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultSignavioListLib implements SignavioListLib {
    @Override
    public <T> List<T> append(List<T> list, T element) {
        if (!SignavioUtil.areNullSafe(list)) {
            return null;
        }

        List<T> result = new ArrayList<>(list);
        result.add(element);
        return result;
    }

    @Override
    public <T> List<T> remove(List<T> list, T element) {
        if (!SignavioUtil.areNullSafe(list)) {
            return null;
        }

        List<T> result = new ArrayList<>(list);
        result.removeIf(e -> Objects.equals(element, e));
        return result;
    }

    @Override
    public <T> List<T> appendAll(List<T> list1, List<T> list2) {
        if (!SignavioUtil.areNullSafe(list1, list2)) {
            return null;
        }

        List<T> result = new ArrayList<>(list1);
        result.addAll(list2);
        return result;
    }

    @Override
    public <T> List<T> removeAll(List<T> list1, List<T> list2) {
        if (!SignavioUtil.areNullSafe(list1, list2)) {
            return null;
        }

        List<T> result = new ArrayList<>(list1);
        for (T element: list2) {
            result.removeIf(e -> Objects.equals(e, element));
        }
        return result;
    }

    @Override
    public Boolean notContainsAny(List<?> list1, List<?> list2) {
        if (!SignavioUtil.areNullSafe(list1, list2)) {
            return null;
        }

        for (Object obj1: list1) {
            if (SignavioUtil.matchesAnyOf(obj1, list2)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean containsOnly(List<?> list1, List<?> list2) {
        if (!SignavioUtil.areNullSafe(list1, list2)) {
            return null;
        }

        for(Object obj1: list1) {
            if (!SignavioUtil.matchesAnyOf(obj1, list2)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean areElementsOf(List<?> list1, List<?> list2) {
        if (!SignavioUtil.areNullSafe(list1, list2)) {
            return null;
        }

        for(Object obj1: list1) {
            if (!SignavioUtil.matchesAnyOf(obj1, list2)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean elementOf(List<?> list1, List<?> list2) {
        return this.areElementsOf(list1, list2);
    }

    @Override
    public List<?> zip(List<?> attributes, List<?> values) {
        if (!SignavioUtil.areNullSafe(attributes, values)) {
            return null;
        } else if (!SignavioUtil.validFieldNamesAndValueList(attributes, values)) {
            return null;
        }

        List<Context> result = new ArrayList<>();
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
            int size = ((List<?>) value).size();
            if (card < size) {
                card = size;
            }
        }
        return card;
    }
}
