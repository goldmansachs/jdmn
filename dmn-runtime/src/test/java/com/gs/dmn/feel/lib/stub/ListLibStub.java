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
package com.gs.dmn.feel.lib.stub;

import com.gs.dmn.feel.lib.type.list.ListLib;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.LambdaExpression;

import java.util.List;

public class ListLibStub implements ListLib {
    @Override
    public Boolean listContains(List list, Object element) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public List append(List list, Object... items) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public List sublist(List list, int position) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public List sublist(List list, int position, int length) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public List concatenate(Object... lists) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public List insertBefore(List list, int position, Object newItem) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public List remove(List list, int position) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public List reverse(List list) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public List union(Object... lists) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public List distinctValues(List list1) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public List flatten(List list1) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public void collect(List result, List list) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public <T> List<T> sort(List<T> list, LambdaExpression<Boolean> comparator) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
