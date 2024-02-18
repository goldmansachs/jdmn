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

public class Range {
    private final boolean startIncluded;
    private final Object start;
    private final boolean endIncluded;
    private final Object end;

    public Range() {
        this(false, null, false, null);
    }

    public Range(boolean startIncluded, Object start, boolean endIncluded, Object end) {
        this.startIncluded = startIncluded;
        this.start = start;
        this.endIncluded = endIncluded;
        this.end = end;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Range range = (Range) o;

        if (this.startIncluded != range.startIncluded) return false;
        if (this.endIncluded != range.endIncluded) return false;
        if (this.start != null ? !this.start.equals(range.start) : range.start != null) return false;
        return this.end != null ? this.end.equals(range.end) : range.end == null;
    }

    @Override
    public int hashCode() {
        int result = (this.startIncluded ? 1 : 0);
        result = 31 * result + (this.start != null ? this.start.hashCode() : 0);
        result = 31 * result + (this.endIncluded ? 1 : 0);
        result = 31 * result + (this.end != null ? this.end.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Range(%s,%s,%s,%s)".formatted(this.startIncluded, this.start.toString(), this.end.toString(), this.endIncluded);
    }
}
