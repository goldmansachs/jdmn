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
package com.gs.dmn.runtime.interpreter;

import com.gs.dmn.el.analysis.semantics.type.Type;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private List<Error> errors = new ArrayList();

    public static final Result of(Object value, Type type) {
        return new Result(value, type);
    }

    public static Object value(Result result) {
        return result == null ? null : result.getValue();
    }

    public static Type type(Result result) {
        return result == null ? null : result.getType();
    }

    private final Object value;
    private final Type type;

    private Result(Object value, Type type) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public void addError(String error, Exception e) {
        this.errors.add(new Error(error, e));
    }

    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("Result(%s, %s, %s)", value, type, errors);
    }
}
