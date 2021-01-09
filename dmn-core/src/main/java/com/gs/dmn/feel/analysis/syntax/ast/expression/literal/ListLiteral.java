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
package com.gs.dmn.feel.analysis.syntax.ast.expression.literal;

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.AnyType;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.runtime.DMNContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListLiteral extends Expression {
    private final List<Expression> expressionList = new ArrayList<>();

    public ListLiteral(List<Expression> expressionList) {
        if (expressionList != null) {
            this.expressionList.addAll(expressionList);
        }
    }

    public List<Expression> getExpressionList() {
        return this.expressionList;
    }

    public void add(Expression ast) {
        this.expressionList.add(ast);
    }

    @Override
    public void deriveType(DMNContext context) {
        Environment environment = context.getEnvironment();
        if (this.expressionList.isEmpty()) {
            if (context.getInputExpressionType() == null) {
                setType(new ListType(AnyType.ANY));
            } else {
                setType(context.getInputExpressionType());
            }
        } else {
            checkListElementTypes();
        }
    }

    private void checkListElementTypes() {
        List<Type> types = this.expressionList.stream().map(Expression::getType).collect(Collectors.toList());
        boolean sameType = true;
        for (int i = 0; i < types.size() - 1; i++) {
            Type type1 = types.get(i);
            for (int j = i + 1; j < types.size(); j++) {
                Type type2 = types.get(j);
                if (!Type.conformsTo(type1, type2)) {
                    sameType = false;
                    break;
                }
            }
            if (!sameType) {
                break;
            }
        }
        if (sameType) {
            setType(new ListType(types.get(0)));
        } else {
            setType(ListType.ANY_LIST);
        }
    }

    @Override
    public Object accept(Visitor visitor, DMNContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        String expressions = this.expressionList.stream().map(Object::toString).collect(Collectors.joining(","));
        return String.format("ListLiteral(%s)", expressions);
    }
}
