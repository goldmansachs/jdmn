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
package com.gs.dmn.feel.lib.type.logic;

import com.gs.dmn.feel.lib.type.BaseType;
import com.gs.dmn.feel.lib.type.BooleanType;
import org.slf4j.Logger;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class DefaultBooleanType extends BaseType implements BooleanType {
    public DefaultBooleanType(Logger logger) {
        super(logger);
    }

    @Override
    public Boolean booleanNot(Object operand) {
        if (operand instanceof Boolean) {
            return ! (Boolean) operand;
        } else {
            return null;
        }
    }

    @Override
    public Boolean booleanOr(List<Object> operands) {
        if (operands.size() < 2) {
            return null;
        } else {
            Object result = operands.get(0);
            for (int i = 1; i < operands.size(); i++) {
                result = binaryBooleanOr(result, operands.get(i));
            }
            return (Boolean) result;
        }
    }

    @Override
    public Boolean booleanOr(Object... operands) {
        if (operands.length < 2) {
            return null;
        } else {
            Object result = operands[0];
            for (int i = 1; i < operands.length; i++) {
                result = binaryBooleanOr(result, operands[i]);
            }
            return (Boolean) result;
        }
    }

    @Override
    public Boolean binaryBooleanOr(Object first, Object second) {
        if (isBooleanTrue(first) || isBooleanTrue(second)) {
            return TRUE;
        } else if (isBooleanFalse(first) && isBooleanFalse(second)) {
            return FALSE;
        } else {
            return null;
        }
    }

    @Override
    public Boolean booleanAnd(List<Object> operands) {
        if (operands.size() < 2) {
            return null;
        } else {
            Object result = operands.get(0);
            for (int i = 1; i < operands.size(); i++) {
                result = binaryBooleanAnd(result, operands.get(i));
            }
            return (Boolean) result;
        }
    }

    @Override
    public Boolean booleanAnd(Object... operands) {
        if (operands.length < 2) {
            return null;
        } else {
            Object result = operands[0];
            for (int i = 1; i < operands.length; i++) {
                result = binaryBooleanAnd(result, operands[i]);
            }
            return (Boolean) result;
        }
    }

    @Override
    public Boolean binaryBooleanAnd(Object first, Object second) {
        if (isBooleanFalse(first) || isBooleanFalse(second)) {
            return FALSE;
        } else if (isBooleanTrue(first) && isBooleanTrue(second)) {
            return TRUE;
        } else {
            return null;
        }
    }

    @Override
    public Boolean booleanEqual(Boolean first, Boolean second) {
        if (first == null && second == null) {
            return TRUE;
        } else if (first == null) {
            return FALSE;
        } else if (second == null) {
            return FALSE;
        } else {
            return first.equals(second);
        }
    }

    @Override
    public Boolean booleanNotEqual(Boolean first, Boolean second) {
        return booleanNot(booleanEqual(first, second));
    }

    private boolean isBooleanTrue(Object obj) {
        return obj instanceof Boolean && (Boolean )obj;
    }

    private boolean isBooleanFalse(Object obj) {
        return obj instanceof Boolean && ! (Boolean )obj;
    }
}
