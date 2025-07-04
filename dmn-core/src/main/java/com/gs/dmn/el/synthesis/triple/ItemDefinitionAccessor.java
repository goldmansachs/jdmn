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

public class ItemDefinitionAccessor extends PathTriple {
    private final String nativeType;
    private final Triple source;
    private final String memberName;

    ItemDefinitionAccessor(String nativeType, Triple source, String memberName) {
        this.nativeType = nativeType;
        this.source = source;
        this.memberName = memberName;
    }

    String getNativeType() {
        return nativeType;
    }

    Triple getSource() {
        return source;
    }

    String getMemberName() {
        return memberName;
    }

    @Override
    public <C, R> R accept(Visitor<C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return String.format("ItemDefinitionAccessor(%s, %s, %s)", nativeType, source, memberName);
    }
}
