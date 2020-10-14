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

import java.util.List;

public interface ListLib {
    Boolean listContains(List list, Object element);

    List append(List list, Object... items);

    List sublist(List list, int position);

    List sublist(List list, int position, int length);

    List concatenate(Object... lists);

    List insertBefore(List list, int position, Object newItem);

    List remove(List list, int position);

    List reverse(List list);

    List union(Object... lists);

    List distinctValues(List list1);

    List flatten(List list1);

    void collect(List result, List list);

    <T> List<T> sort(List<T> list, LambdaExpression<Boolean> comparator);
}
