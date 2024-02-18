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
package com.gs.dmn.el.synthesis.triple;

public class RangeAccessor extends PathTriple {
    private final Triple source;
    private final String rangeGetter;

    RangeAccessor(Triple source, String rangeGetter) {
        this.source = source;
        this.rangeGetter = rangeGetter;
    }

    Triple getSource() {
        return source;
    }

    String getRangeGetter() {
        return rangeGetter;
    }

    @Override
    public <C, R> R accept(Visitor<C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return "RangeAccessor(%s, %s)".formatted(source, rangeGetter);
    }
}
