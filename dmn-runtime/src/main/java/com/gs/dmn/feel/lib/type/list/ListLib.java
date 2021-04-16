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
    <T> Boolean listContains(List<T> list, T element);

    <T> List<T> append(List<T> list, T... items);

    <T> List<T> sublist(List<T> list, int position);

    <T> List<T> sublist(List<T> list, int position, int length);

    <T> List<T> concatenate(List<T>... lists);

    <T> List<T> insertBefore(List<T> list, int position, T newItem);

    <T> List<T> remove(List<T> list, int position);

    <T> List<T> reverse(List<T> list);

    <T> List<T> union(List<T>... lists);

    <T> List<T> distinctValues(List<T> list1);

    List flatten(List list1);

    void collect(List result, List list);

    <T> List<T> sort(List<T> list, LambdaExpression<Boolean> comparator);
}
