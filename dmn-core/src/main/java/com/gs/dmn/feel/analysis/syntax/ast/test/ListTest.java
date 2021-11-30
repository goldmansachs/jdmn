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
package com.gs.dmn.feel.analysis.syntax.ast.test;

import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.RangeType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.ListLiteral;
import com.gs.dmn.runtime.DMNContext;

import java.util.List;
import java.util.Objects;

import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;

public class ListTest extends SimplePositiveUnaryTest {
    private final ListLiteral listLiteral;

    public ListTest(ListLiteral listLiteral) {
        this.listLiteral = listLiteral;
    }

    public ListLiteral getListLiteral() {
        return this.listLiteral;
    }

    @Override
    public void deriveType(DMNContext context) {
        setType(BOOLEAN);
        List<Expression> expressionList = this.listLiteral.getExpressionList();
        if (!expressionList.isEmpty()) {
            Type listType = this.listLiteral.getType();
            Type listElementType = ((ListType) listType).getElementType();
            Type inputExpressionType = context.getInputExpressionType();
            if (Type.conformsTo(inputExpressionType, listType)) {
            } else if (Type.conformsTo(inputExpressionType, listElementType)) {
            } else if (listElementType instanceof RangeType &&Type.conformsTo(inputExpressionType, ((RangeType) listElementType).getRangeType())) {
            } else {
                throw new SemanticError(this, String.format("Cannot compare '%s', '%s'", inputExpressionType, listType));
            }
        }

    }

    @Override
    public Object accept(Visitor visitor, DMNContext context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListTest listTest = (ListTest) o;
        return Objects.equals(listLiteral, listTest.listLiteral);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listLiteral);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), this.listLiteral);
    }
}
