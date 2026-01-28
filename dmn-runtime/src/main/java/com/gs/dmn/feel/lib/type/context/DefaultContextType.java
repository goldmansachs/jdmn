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
import com.gs.dmn.runtime.DMNRuntimeException;

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
            Set keys = m.keySet();
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

    @Override
    public Context context(List entries) {
        if (entries == null) {
            return null;
        }

        Context result = new Context();
        for (Object entry : entries) {
            if (validEntry(entry)) {
                String key = (String) ((Context) entry).get("key");
                Object value = ((Context) entry).get("value");
                if (!result.keySet().contains(key)) {
                    result.add(key, value);
                } else {
                    throw new DMNRuntimeException(String.format("Duplicated key '%s' in context()", key));
                }
            } else {
                throw new DMNRuntimeException(String.format("Illegal entry '%s' in context()", entry));
            }
        }
        return result;
    }

    private boolean validEntry(Object entry) {
        if (!(entry instanceof Context)) {
            return false;
        }
        Set keys = ((Context) entry).keySet();
        if (!(keys.contains("key") && keys.contains("value"))) {
            return false;
        }
        Object key = ((Context) entry).get("key");
        return key instanceof String;
    }

    @Override
    public Context contextPut(Context context, String key, Object value) {
        if (context == null || key == null) {
            return null;
        }

        Context clone = Context.clone(context);
        clone.add(key, value);
        return clone;
    }

    @Override
    public Context contextPut(Context context, List<String> keys, Object value) {
        if (context == null || keys == null || keys.isEmpty()) {
            return null;
        }

        if (keys.size() == 1) {
            return contextPut(context, keys.get(0), value);
        }
        Context rootContext = Context.clone(context);
        Context currentContext = rootContext;
        for (int i=0; i<keys.size(); i++) {
            String key = keys.get(i);
            if (key == null) {
                throw new DMNRuntimeException(String.format("Incorrect key '%s' in context '%s'", keys, context));
            }
            if (i == keys.size() -1) {
                // last key from path
                if (currentContext != null) {
                    currentContext.add(key, value);
                } else {
                    throw new DMNRuntimeException(String.format("Incorrect path '%s' in context '%s'", keys, context));
                }
            } else {
                // extract context from path
                Object o = currentContext.get(key);
                if (o instanceof Context) {
                   currentContext = (Context) o;
                } else {
                    throw new DMNRuntimeException(String.format("Incorrect path '%s' in context '%s'", keys, context));
                }
            }
        }
        return rootContext;
    }

    @Override
    public Context contextMerge(List<?> contexts) {
        if (contexts == null) {
            return null;
        }

        Context context = new Context();
        for (Object o: contexts) {
            if (o instanceof Context) {
                Context c = (Context) o;
                for (Object key : c.keySet()) {
                    context.add(key, c.get(key));
                }
            } else {
                throw new DMNRuntimeException(String.format("Expected Context found '%s'", o));
            }
        }
        return context;
    }
}
