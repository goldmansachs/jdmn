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
package com.gs.dmn.signavio.feel.lib.stub;

import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.signavio.feel.lib.type.list.SignavioListLib;

import java.util.List;

public class SignavioListLibStub extends SignavioListLib {
    @Override
    public List appendAll(List list1, List list2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public List remove(List list, Object element) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public List removeAll(List list1, List list2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean notContainsAny(List list1, List list2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean containsOnly(List list1, List list2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean areElementsOf(List list1, List list2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public List<?> zip(List attributes, List values) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Object mode(List numbers) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
