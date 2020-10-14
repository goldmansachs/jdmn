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

import com.gs.dmn.runtime.LambdaExpression;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DefaultListLib implements ListLib {
    @Override
    public Boolean listContains(List list, Object element) {
        return list == null ? null : list.contains(element);
    }

    @Override
    public List append(List list, Object... items) {
        List result = new ArrayList<>();
        if (list != null) {
            result.addAll(list);
        }
        if (items != null) {
            for (Object item : items) {
                result.add(item);
            }
        } else {
            result.add(null);
        }
        return result;
    }

    @Override
    public List sublist(List list, int position) {
        List result = new ArrayList<>();
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
    public List sublist(List list, int position, int length) {
        List result = new ArrayList<>();
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
    public List concatenate(Object... lists) {
        List result = new ArrayList<>();
        if (lists != null) {
            for (Object list : lists) {
                result.addAll((List) list);
            }
        }
        return result;
    }

    @Override
    public List insertBefore(List list, int position, Object newItem) {
        List result = new ArrayList<>();
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
    public List remove(List list, int position) {
        List result = new ArrayList<>();
        if (list != null) {
            result.addAll(list);
        }
        result.remove(position - 1);
        return result;
    }

    @Override
    public List reverse(List list) {
        List result = new ArrayList<>();
        if (list != null) {
            for (int i = list.size() - 1; i >= 0; i--) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    @Override
    public List union(Object... lists) {
        List result = new ArrayList<>();
        if (lists != null) {
            for (Object list : lists) {
                result.addAll((List) list);
            }
        }
        return distinctValues(result);
    }

    @Override
    public List distinctValues(List list1) {
        List result = new ArrayList<>();
        if (list1 != null) {
            for (Object element : list1) {
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
    public <T> List<T> sort(List<T> list, LambdaExpression<Boolean> comparator) {
        List<T> clone = new ArrayList<>(list);
        Comparator<? super T> comp = (Comparator<T>) (o1, o2) -> {
            if (comparator.apply(o1, o2)) {
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
