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

import com.gs.dmn.runtime.DMNContext;

import java.util.ArrayList;
import java.util.List;

public class TFunctionDefinition extends TExpression implements Visitable {
    private List<TInformationItem> formalParameter;
    private TExpression expression;
    private TFunctionKind kind;

    public List<TInformationItem> getFormalParameter() {
        if (formalParameter == null) {
            formalParameter = new ArrayList<>();
        }
        return this.formalParameter;
    }

    public TExpression getExpression() {
        return expression;
    }

    public void setExpression(TExpression value) {
        this.expression = value;
    }

    public TFunctionKind getKind() {
        if (kind == null) {
            return TFunctionKind.FEEL;
        } else {
            return kind;
        }
    }

    public void setKind(TFunctionKind value) {
        this.kind = value;
    }

    @Override
    public Object accept(Visitor visitor, DMNContext context) {
        return visitor.visit(this, context);
    }
}
