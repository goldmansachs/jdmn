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
package com.gs.dmn.feel.lib.type.context;

import com.gs.dmn.feel.lib.type.BaseType;
import com.gs.dmn.feel.lib.type.bool.BooleanType;
import com.gs.dmn.feel.lib.type.bool.DefaultBooleanType;
import com.gs.dmn.runtime.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DefaultContextType extends BaseType implements ContextType {
    private final BooleanType booleanType;

    public DefaultContextType() {
        this.booleanType = new DefaultBooleanType();
    }

    @Override
    public boolean isContext(Object value) {
        return value instanceof Context;
    }

    @Override
    public Context contextValue(Context value) {
        return value;
    }

    @Override
    public Boolean contextIs(Context c1, Context c2) {
        return contextEqual(c1, c2);
    }

    @Override
    public Boolean contextEqual(Context c1, Context c2) {
        if (c1 == null && c2 == null) {
            return true;
        } else if (c1 == null) {
            return false;
        } else if (c2 == null) {
            return false;
        } else {
            return c1.equals(c2);
        }
    }

    @Override
    public Boolean contextNotEqual(Context c1, Context c2) {
        return booleanType.booleanNot(contextEqual(c1, c2));
    }

    @Override
    public List getEntries(Context m) {
        if (m != null) {
            List result = new ArrayList<>();
            Set keys = m.getBindings().keySet();
            for (Object key: keys) {
                Context c = new Context().add("key", key).add("value", m.get(key));
                result.add(c);
            }
            return result;
        } else {
            return null;
        }
    }

    @Override
    public Object getValue(Context context, Object key) {
        if (context != null) {
            return context.get(key);
        } else {
            return null;
        }
    }
}
