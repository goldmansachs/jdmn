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
package com.gs.dmn.feel.analysis.semantics.type;

import com.gs.dmn.el.analysis.semantics.type.Type;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;

public class ListType implements com.gs.dmn.el.analysis.semantics.type.ListType, FEELType {
    public static final Type EMPTY_LIST = new ListType();
    public static final Type ANY_LIST = new ListType(ANY);
    public static final Type NUMBER_LIST = new ListType(NumberType.NUMBER);
    public static final Type STRING_LIST = new ListType(StringType.STRING);
    public static final Type BOOLEAN_LIST = new ListType(BooleanType.BOOLEAN);
    public static final Type DATE_LIST = new ListType(DateType.DATE);
    public static final Type TIME_LIST = new ListType(TimeType.TIME);
    public static final Type DATE_AND_TIME_LIST = new ListType(DateTimeType.DATE_AND_TIME);
    public static final Type DAYS_AND_TIME_DURATION_LIST = new ListType(DaysAndTimeDurationType.DAYS_AND_TIME_DURATION);
    public static final Type YEARS_AND_MONTHS_DURATION_LIST = new ListType(YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION);
    public static final Type CONTEXT_LIST = new ListType(ContextType.CONTEXT);
    public static final Type COMPARABLE_LIST = new ListType(ComparableDataType.COMPARABLE);

    private final Type elementType;

    public ListType() {
        this(null);
    }

    public ListType(Type elementType) {
        this.elementType = elementType;
    }

    @Override
    public Type getElementType() {
        return elementType;
    }

    @Override
    public boolean equivalentTo(Type other) {
        return other instanceof ListType
                && Type.equivalentTo(this.elementType, ((ListType) other).elementType);
    }

    @Override
    public boolean conformsTo(Type other) {
        if (other == ANY || equivalentTo(other)) {
            return true;
        }

        if (other instanceof ListType) {
            return Type.equivalentTo(this, ListType.EMPTY_LIST) || Type.conformsTo(this.elementType, ((ListType) other).elementType);
        } else {
            return false;
        }
    }

    @Override
    public boolean isFullySpecified() {
        return !com.gs.dmn.el.analysis.semantics.type.Type.isNullOrAny(elementType);
    }

    @Override
    public String typeExpression() {
        return String.format("list<%s>", elementType.typeExpression());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListType listType = (ListType) o;

        return elementType != null ? elementType.equals(listType.elementType) : Type.isNull(listType.elementType);
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
