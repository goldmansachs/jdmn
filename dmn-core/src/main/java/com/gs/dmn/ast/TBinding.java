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
        "parameter",
        "expression"
})
public class TBinding<C> extends DMNBaseElement implements Visitable<C> {
    private TInformationItem<C> parameter;
    private TExpression<C> expression;

    public TInformationItem<C> getParameter() {
        return parameter;
    }

    public void setParameter(TInformationItem<C> value) {
        this.parameter = value;
    }

    public TExpression<C> getExpression() {
        return expression;
    }

    public void setExpression(TExpression<C> value) {
        this.expression = value;
    }

    @Override
    public Object accept(Visitor<C> visitor, C context) {
        return visitor.visit(this, context);
    }
}
