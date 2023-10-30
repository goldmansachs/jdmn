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

import java.util.List;

public class BuiltinFunctionInvocation extends FunctionInvocationTriple {
    private final Triple conversionFunction;
    private final List<Triple> operands;

    BuiltinFunctionInvocation(Triple conversionFunction, List<Triple> operands) {
        this.conversionFunction = conversionFunction;
        this.operands = operands;
    }

    Triple getConversionFunction() {
        return conversionFunction;
    }

    List<Triple> getOperands() {
        return operands;
    }

    @Override
    public <C, R> R accept(Visitor<C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return String.format("BuiltinFunctionInvocation(%s, %s)", conversionFunction, operands);
    }
}
