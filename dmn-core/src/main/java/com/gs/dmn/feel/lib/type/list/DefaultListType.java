/**
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

import com.gs.dmn.feel.lib.type.BaseType;
import com.gs.dmn.feel.lib.type.BooleanType;
import com.gs.dmn.feel.lib.type.ListType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import org.slf4j.Logger;

import java.util.List;

public class DefaultListType extends BaseType implements ListType {
    private final BooleanType booleanType;

    public DefaultListType(Logger logger) {
        super(logger);
        this.booleanType = new DefaultBooleanType(logger);
    }

    @Override
    public Boolean listEqual(List list1, List list2) {
        if (list1 == null && list2 == null) {
            return true;
        } else if (list1 == null) {
            return false;
        } else if (list2 == null) {
            return false;
        } else {
            return list1.equals(list2);
        }
    }

    @Override
    public Boolean listNotEqual(List list1, List list2) {
        return booleanType.booleanNot(listEqual(list1, list2));
    }
}
