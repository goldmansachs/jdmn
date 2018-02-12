/**
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
package com.gs.dmn.feel.analysis.syntax.ast.expression.textual;

import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.NumericLiteral;

import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;

public class FilterExpression extends Expression {
    public static final String FILTER_PARAMETER_NAME = "item";

    private final Expression source;
    private Expression filter;

    public FilterExpression(Expression source, Expression filter) {
        this.source = source;
        this.filter = filter;
    }

    public Expression getSource() {
        return source;
    }

    public Expression getFilter() {
        return filter;
    }

    public void setFilter(Expression filter) {
        this.filter = filter;
    }

    @Override
    public void deriveType(Environment environment) {
        Type sourceType = source.getType();
        Type filterType = filter.getType();
        if (sourceType instanceof ListType) {
            if (filterType == NUMBER) {
                setType(((ListType) sourceType).getElementType());
            } else if (filterType == BOOLEAN) {
                setType(sourceType);
            } else {
                throw new SemanticError(this, String.format("Cannot resolve type for '%s'", this.toString()));
            }
        } else {
            if (filter instanceof NumericLiteral && "1".equals(((NumericLiteral) filter).getValue())) {
                setType(sourceType);
            } else if (filterType == BOOLEAN) {
                setType(new ListType(sourceType));
            } else {
                throw new SemanticError(this, String.format("Cannot resolve type for '%s'", this.toString()));
            }
        }
    }

    @Override
    public Object accept(Visitor visitor, FEELContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        return String.format("FilterExpression(%s, %s)", source.toString(), filter.toString());
    }
}
