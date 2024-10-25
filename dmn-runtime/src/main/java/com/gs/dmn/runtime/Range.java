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
package com.gs.dmn.runtime;

import java.util.Objects;

public class Range {
    private final boolean startIncluded;
    private final Object start;
    private final boolean endIncluded;
    private final Object end;
    private final String operator;

    public Range() {
        this(false, null, false, null, null);
    }

    public Range(boolean startIncluded, Object start, boolean endIncluded, Object end) {
        this.startIncluded = startIncluded;
        this.start = start;
        this.endIncluded = endIncluded;
        this.end = end;
        this.operator = null;
    }

    public Range(boolean startIncluded, Object start, boolean endIncluded, Object end, String operator) {
        this.startIncluded = startIncluded;
        this.start = start;
        this.endIncluded = endIncluded;
        this.end = end;
        this.operator = operator;
    }

    public boolean isStartIncluded() {
        return startIncluded;
    }

    public Object getStart() {
        return start;
    }

    public boolean isEndIncluded() {
        return endIncluded;
    }

    public Object getEnd() {
        return end;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range range = (Range) o;
        return isStartIncluded() == range.isStartIncluded() &&
                isEndIncluded() == range.isEndIncluded() &&
                Objects.equals(getStart(), range.getStart()) &&
                Objects.equals(getEnd(), range.getEnd()) &&
                Objects.equals(getOperator(), range.getOperator());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isStartIncluded(), getStart(), isEndIncluded(), getEnd(), getOperator());
    }

    @Override
    public String toString() {
        return String.format("Range(%s,%s,%s,%s,%s)", this.operator, this.startIncluded, this.start, this.endIncluded, this.end);
    }
}
