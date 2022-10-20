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

import com.gs.dmn.runtime.Context;

import java.util.List;

public interface ContextType {
    boolean isContext(Object value);

    Context contextValue(Context value);

    Boolean contextIs(Context c1, Context c2);

    Boolean contextEqual(Context c1, Context c2);

    Boolean contextNotEqual(Context c1, Context c2);

    List getEntries(Context context);

    Object getValue(Context context, Object key);
}
