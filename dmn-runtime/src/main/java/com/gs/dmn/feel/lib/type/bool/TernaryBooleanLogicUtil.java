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
package com.gs.dmn.feel.lib.type.bool;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class TernaryBooleanLogicUtil {
    private static TernaryBooleanLogicUtil INSTANCE;

    public static TernaryBooleanLogicUtil getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TernaryBooleanLogicUtil();
        }

        return INSTANCE;
    }

    private TernaryBooleanLogicUtil() {
    }

    public Boolean not(Object operand) {
        if (operand instanceof Boolean boolean1) {
            return !boolean1;
        } else {
            return null;
        }
    }

    public Boolean and(Object first, Object second) {
        if (isBooleanFalse(first) || isBooleanFalse(second)) {
            return FALSE;
        } else if (isBooleanTrue(first) && isBooleanTrue(second)) {
            return TRUE;
        } else {
            return null;
        }
    }

    public Boolean or(Object first, Object second) {
        if (isBooleanTrue(first) || isBooleanTrue(second)) {
            return TRUE;
        } else if (isBooleanFalse(first) && isBooleanFalse(second)) {
            return FALSE;
        } else {
            return null;
        }
    }

    private boolean isBooleanTrue(Object obj) {
        return obj instanceof Boolean b && b;
    }

    private boolean isBooleanFalse(Object obj) {
        return obj instanceof Boolean b && ! b;
    }
}
