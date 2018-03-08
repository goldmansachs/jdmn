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
package com.gs.dmn.feel.analysis.syntax.ast.test;

import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.ListLiteral;

import java.util.List;

import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;

public class ListTest extends SimplePositiveUnaryTest {
    private final ListLiteral listLiteral;

    public ListTest(ListLiteral listLiteral) {
        this.listLiteral = listLiteral;
    }

    public ListLiteral getListLiteral() {
        return listLiteral;
    }

    @Override
    public void deriveType(Environment environment) {
        setType(BOOLEAN);
        List<Expression> expressionList = listLiteral.getExpressionList();
        if (!expressionList.isEmpty()) {
            Type listElementType = ((ListType) listLiteral.getType()).getElementType();
            Type inputExpressionType = environment.getInputExpressionType();
            if (inputExpressionType instanceof ListType) {
                if (!((ListType) inputExpressionType).getElementType().conformsTo(listElementType)) {
                    throw new SemanticError(this, String.format("Operator '=' cannot be applied to '%s', '%s'", inputExpressionType, this.toString()));
                }
            } else if (inputExpressionType.conformsTo(listElementType)) {
            } else {
                throw new SemanticError(this, String.format("Operator '=' cannot be applied to '%s', '%s'", inputExpressionType, listElementType));
            }
        }

    }

    @Override
    public Object accept(Visitor visitor, FEELContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        return String.format("ListTest(%s)", listLiteral);
    }
}
