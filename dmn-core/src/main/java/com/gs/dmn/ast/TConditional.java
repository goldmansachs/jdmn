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
package com.gs.dmn.ast;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "ifExp",
        "thenExp",
        "elseExp"
})
public class TConditional extends TExpression implements Visitable {
    private TChildExpression ifExp;
    private TChildExpression thenExp;
    private TChildExpression elseExp;

    public TChildExpression getIf() {
        return ifExp;
    }

    public void setIf(TChildExpression ifExp) {
        this.ifExp = ifExp;
    }

    public TChildExpression getThen() {
        return thenExp;
    }

    public void setThen(TChildExpression thenExp) {
        this.thenExp = thenExp;
    }

    public TChildExpression getElse() {
        return elseExp;
    }

    public void setElse(TChildExpression elseExp) {
        this.elseExp = elseExp;
    }

    @Override
    public <C, R> R accept(Visitor<C, R> visitor, C context) {
        return visitor.visit(this, context);
    }
}
