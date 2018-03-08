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
package com.gs.dmn.feel.analysis.semantics;

import com.gs.dmn.feel.analysis.semantics.type.ContextType;
import com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.ASTFactory;
import com.gs.dmn.feel.analysis.syntax.ast.CloneVisitor;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Name;
import com.gs.dmn.feel.analysis.syntax.ast.expression.PathExpression;

public class AddItemFilterVisitor extends CloneVisitor {
    private final ASTFactory astFactory = new ASTFactory();

    private final String lambdaParameterName;
    private final Type lambdaParameterType;

    public AddItemFilterVisitor(String lambdaParameterName, Type lambdaParameterType) {
        this.lambdaParameterName = lambdaParameterName;
        this.lambdaParameterType = lambdaParameterType;
    }

    //
    // Postfix expressions
    //
    @Override
    public Object visit(PathExpression element, FEELContext context) {
        Expression source = element.getSource();
        if (source instanceof Name) {
            String name = ((Name) source).getName();
            if (isMember(name, lambdaParameterType)) {
                Expression newSource = astFactory.toPathExpression(astFactory.toName(lambdaParameterName), name);
                return astFactory.toPathExpression(newSource, element.getMember());
            }
        }
        return element;
    }

    //
    // Primary expressions
    //
    @Override
    public Object visit(Name element, FEELContext context) {
        String name = element.getName();
        if (isMember(name, lambdaParameterType)) {
            Expression source = astFactory.toName(lambdaParameterName);
            return astFactory.toPathExpression(source, element.getName());
        } else {
            return element;
        }
    }

    private boolean isMember(String name, Type type) {
        if (type instanceof ContextType) {
            return ((ContextType) type).getMemberType(name) != null;
        } else if (type instanceof ItemDefinitionType) {
            return ((ItemDefinitionType) type).getMemberType(name) != null;
        } else {
            return false;
        }
    }

}
