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
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;

public class RangeType extends Type {
    public static final Type NUMBER_RANGE_TYPE = new RangeType(NUMBER);

    private final Type type;

    public RangeType() {
        this(NUMBER);
    }

    public RangeType(Type type) {
        if (type == null) {
            type = NUMBER;
        }
        this.type = type;
    }

    public Type getRangeType() {
        return type;
    }

    @Override
    public boolean equivalentTo(Type other) {
        return other instanceof RangeType
                && this.type.equivalentTo(((RangeType) other).type);
    }

    @Override
    public boolean conformsTo(Type other) {
        return other instanceof RangeType && this.type.conformsTo(((RangeType) other).type)
                || other == ANY;
    }

    @Override
    public boolean isValid() {
        return type != null && type.isValid();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RangeType rangeType = (RangeType) o;

        return type.equals(rangeType.type);

    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public String toString() {
        return String.format("RangeType(%s)", type.toString());
    }
}
