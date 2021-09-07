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
package com.gs.dmn.validation.table;

import java.util.ArrayList;
import java.util.List;

public class Input {
    public static boolean isNumberType(String typeRef) {
        return "number".equals(typeRef);
    }

    public static boolean isBooleanType(String typeRef) {
        return "boolean".equals(typeRef);
    }

    public static boolean isStringType(String typeRef) {
        return "string".equals(typeRef);
    }

    private final String typeRef;
    private final List<String> allowedValues = new ArrayList<>();

    public Input(String typeRef) {
        this.typeRef = typeRef;
    }

    public Input(String typeRef, List<String> allowedValues) {
        this.typeRef = typeRef;
        if (allowedValues != null) {
            this.allowedValues.addAll(allowedValues);
        }
    }

    public boolean isNumberType() {
        return Input.isNumberType(typeRef);
    }

    public boolean isBooleanType() {
        return Input.isBooleanType(typeRef);
    }

    public boolean isStringType() {
        return Input.isStringType(typeRef);
    }

    public List<String> getAllowedValues() {
        return allowedValues;
    }

    @Override
    public String toString() {
        if (isStringType() || isBooleanType()) {
            return String.format("%s %s", typeRef, allowedValues);
        } else {
            return typeRef;
        }
    }
}
