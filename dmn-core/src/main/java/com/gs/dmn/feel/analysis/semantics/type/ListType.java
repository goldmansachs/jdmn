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
package com.gs.dmn.feel.analysis.semantics.type;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;

public class ListType extends Type {
    public static final Type ANY_LIST = new ListType(ANY);
    public static final Type NUMBER_LIST = new ListType(NumberType.NUMBER);
    public static final Type STRING_LIST = new ListType(StringType.STRING);
    public static final Type BOOLEAN_LIST = new ListType(BooleanType.BOOLEAN);
    public static final Type DATE_LIST = new ListType(DateType.DATE);
    public static final Type TIME_LIST = new ListType(TimeType.TIME);
    public static final Type DATE_AND_TIME_LIST = new ListType(DateTimeType.DATE_AND_TIME);

    private final Type elementType;

    public ListType() {
        this(ANY);
    }

    public ListType(Type elementType) {
        if (elementType == null) {
            elementType = ANY;
        }
        this.elementType = elementType;
    }

    public Type getElementType() {
        return elementType;
    }

    @Override
    public boolean equivalentTo(Type other) {
        return other instanceof ListType
                && this.elementType.equivalentTo(((ListType) other).elementType);
    }

    @Override
    public boolean conformsTo(Type other) {
        return other instanceof ListType
                && this.elementType.conformsTo(((ListType) other).elementType)
                || other == ANY;
    }

    @Override
    public boolean isValid() {
        if (elementType == null) {
            return false;
        }
        return elementType.isValid();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListType listType = (ListType) o;

        return elementType != null ? elementType.equals(listType.elementType) : listType.elementType == null;

    }

    @Override
    public int hashCode() {
        return elementType != null ? elementType.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.format("ListType(%s)", elementType);
    }
}
