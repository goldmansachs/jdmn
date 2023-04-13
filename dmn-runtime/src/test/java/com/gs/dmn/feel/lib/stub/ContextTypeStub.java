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

import com.gs.dmn.feel.lib.type.context.ContextType;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.List;

public class ContextTypeStub implements ContextType {
    @Override
    public boolean isContext(Object value) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Context contextValue(Context value) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean contextIs(Context c1, Context c2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean contextEqual(Context c1, Context c2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean contextNotEqual(Context c1, Context c2) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public List getEntries(Context context) {
        throw new DMNRuntimeException("Not supported yet");    }

    @Override
    public Object getValue(Context context, Object key) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Context contextPut(Context context, String key, Object value) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Context contextPut(Context context, List<String> key, Object value) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Context contextMerge(List<?> contexts) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
