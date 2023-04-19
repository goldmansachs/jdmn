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
        "in",
        "match"
})
public class TFilter extends TExpression implements Visitable {
    private TChildExpression in;
    private TChildExpression match;

    public TChildExpression getIn() {
        return in;
    }

    public void setIn(TChildExpression in) {
        this.in = in;
    }

    public TChildExpression getMatch() {
        return match;
    }

    public void setMatch(TChildExpression match) {
        this.match = match;
    }

    @Override
    public <C> Object accept(Visitor visitor, C context) {
        return visitor.visit(this, context);
    }
}
