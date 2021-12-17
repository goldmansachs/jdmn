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
package com.gs.dmn.transformation.native_.statement;

import com.gs.dmn.feel.analysis.semantics.type.Type;

public class AssignmentStatement extends Statement {
    private final String lhsNativeType;
    private final String lhs;
    private final String rhs;
    private final Type lhsType;
    private final String text;

    public AssignmentStatement(String lhsNativeType, String lhs, String rhs, Type lshType, String text) {
        this.lhsNativeType = lhsNativeType;
        this.lhs = lhs;
        this.rhs = rhs;
        this.lhsType = lshType;
        this.text = text;
    }

    public String getLhs() {
        return lhs;
    }

    public String getRhs() {
        return rhs;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
