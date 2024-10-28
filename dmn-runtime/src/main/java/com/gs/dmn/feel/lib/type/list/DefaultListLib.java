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
package com.gs.dmn.feel.lib.type.list;

import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.LambdaExpression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DefaultListLib implements ListLib {
    @Override
    public <T> List<T> append(List<T> list, T... items) {
        List<T> result = new ArrayList<>();
        if (list != null) {
            result.addAll(list);
        }
        if (items != null) {
            Collections.addAll(result, items);
        } else {
            result.add(null);
        }
        return result;
    }

    @Override
    public <T> List<T> sublist(List<T> list, int position) {
        List<T> result = new ArrayList<>();
        if (list == null || isOutOfBounds(list, position)) {
            return result;
        }
        int javaStartPosition;
        // up to, not included
        int javaEndPosition = list.size();
        if (position < 0) {
            javaStartPosition = list.size() + position;
        } else {
            javaStartPosition = position - 1;
        }
        for (int i = javaStartPosition; i < javaEndPosition; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    @Override
    public <T> List<T> sublist(List<T> list, int position, int length) {
        List<T> result = new ArrayList<>();
        if (list == null || isOutOfBounds(list, position)) {
            return result;
        }
        int javaStartPosition;
        int javaEndPosition;
        if (position < 0) {
            javaStartPosition = list.size() + position;
            javaEndPosition = javaStartPosition + length;
        } else {
            javaStartPosition = position - 1;
            javaEndPosition = javaStartPosition + length;
        }
        for (int i = javaStartPosition; i < javaEndPosition; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    private boolean isOutOfBounds(List list, int position) {
        int length = list.size();
        if (position < 0) {
            return !(-length <= position);
        } else {
            return !(1 <= position && position <= length);
        }
    }

    @Override
    public <T> List<T> concatenate(List<T>... lists) {
        List<T> result = new ArrayList<>();
        if (lists != null) {
            for (List<T> list : lists) {
                result.addAll(list);
            }
        }
        return result;
    }

    @Override
    public <T> List<T> insertBefore(List<T> list, int position, T newItem) {
        List<T> result = new ArrayList<>();
        if (list != null) {
            result.addAll(list);
        }
        if (isOutOfBounds(result, position)) {
            return result;
        }
        if (position < 0) {
            position = result.size() + position;
        } else {
            position = position - 1;
        }
        result.add(position, newItem);
        return result;
    }

    @Override
    public <T> List<T> remove(List<T> list, int position) {
        List result = new ArrayList<>();
        if (list != null) {
            result.addAll(list);
        }
        result.remove(position - 1);
        return result;
    }

    @Override
    public <T> List<T> listReplace(List<T> list, Object position, T newItem) {
        if (list == null || position == null) {
            return  null;
        }

        if (position instanceof Number) {
            return listReplaceInt(list, (Number) position, newItem);
        } else if (position instanceof LambdaExpression) {
            return listReplaceMatch(list, (LambdaExpression) position, newItem);
        } else {
            throw new DMNRuntimeException(String.format("Illegal argument '%s'. Expected number or predicate", position.getClass().getName()));
        }
    }

    private <T> List<T> listReplaceInt(List<T> list, Number position, T newItem) {
        List result = new ArrayList<>();
        result.addAll(list);

        int index = position.intValue();
        if (index < 0) {
            index = list.size() + index;
        } else {
            --index;
        }
        result.set(index, newItem);
        return result;
    }

    private <T> List<T> listReplaceMatch(List<T> list, LambdaExpression<Boolean> match, T newItem) {
        List result = new ArrayList<>();
        result.addAll(list);

        for (int i=0; i<list.size(); i++) {
            if (match.apply(list.get(i), newItem)) {
                result.set(i, newItem);
            }
        }

        return result;
    }

    @Override
    public <T> List<T> reverse(List<T> list) {
        List result = new ArrayList<>();
        if (list != null) {
            for (int i = list.size() - 1; i >= 0; i--) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    @Override
    public <T> List<T> union(List<T>... lists) {
        List<T> result = new ArrayList<>();
        if (lists != null) {
            for (List<T> list : lists) {
                result.addAll(list);
            }
        }
        return distinctValues(result);
    }

    @Override
    public <T> List<T> distinctValues(List<T> list1) {
        List<T> result = new ArrayList<>();
        if (list1 != null) {
            for (T element : list1) {
                if (!result.contains(element)) {
                    result.add(element);
                }
            }
        }
        return result;
    }

    @Override
    public List flatten(List list1) {
        if (list1 == null) {
            return null;
        }
        List result = new ArrayList<>();
        collect(result, list1);
        return result;
    }

    @Override
    public void collect(List result, List list) {
        if (list != null) {
            for (Object object : list) {
                if (object instanceof List) {
                    collect(result, (List) object);
                } else {
                    result.add(object);
                }
            }
        }
    }

    @Override
    public <T> List<T> sort(List<T> list, LambdaExpression<Boolean> precedes) {
        List<T> clone = new ArrayList<>(list);
        Comparator<? super T> comp = (Comparator<T>) (o1, o2) -> {
            if (precedes.apply(o1, o2)) {
                return -1;
            } else if (o1 != null && o1.equals(o2)) {
                return 0;
            } else {
                return 1;
            }
        };
        clone.sort(comp);
        return clone;
    }
}
