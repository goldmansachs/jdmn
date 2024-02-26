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
package com.gs.dmn.feel.analysis.semantics;

import com.gs.dmn.el.analysis.semantics.type.AnyType;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.feel.analysis.semantics.type.ContextType;
import com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.feel.analysis.syntax.ast.ASTFactory;
import com.gs.dmn.feel.analysis.syntax.ast.visitor.CloneVisitor;
import com.gs.dmn.feel.analysis.syntax.ast.Element;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Name;
import com.gs.dmn.feel.analysis.syntax.ast.expression.PathExpression;

public class AddItemFilterVisitor<T, C> extends CloneVisitor<T, C> {
    private final ASTFactory<T, C> astFactory = new ASTFactory<>();

    private final String lambdaParameterName;
    private final Type lambdaParameterType;

    public AddItemFilterVisitor(String lambdaParameterName, Type lambdaParameterType, ErrorHandler errorHandler) {
        super(errorHandler);
        this.lambdaParameterName = lambdaParameterName;
        this.lambdaParameterType = lambdaParameterType;
    }

    //
    // Postfix expressions
    //
    @Override
    public Element<T> visit(PathExpression<T> element, C context) {
        Expression<T> source = element.getSource();
        if (source instanceof Name) {
            String name = ((Name<T>) source).getName();
            if (isMember(name, this.lambdaParameterType)) {
                Expression<T> newSource = this.astFactory.toPathExpression(this.astFactory.toName(this.lambdaParameterName), name);
                return this.astFactory.toPathExpression(newSource, element.getMember());
            }
        }
        return element;
    }

    //
    // Primary expressions
    //
    @Override
    public Element<T> visit(Name<T> element, C context) {
        if (element == null) {
            return null;
        }

        String name = element.getName();
        if (isMember(name, this.lambdaParameterType)) {
            Expression<T> source = this.astFactory.toName(this.lambdaParameterName);
            return this.astFactory.toPathExpression(source, element.getName());
        } else {
            return element;
        }
    }

    private boolean isMember(String name, Type type) {
        if (type instanceof ContextType contextType) {
            return contextType.getMemberType(name) != null;
        } else if (type instanceof ItemDefinitionType definitionType) {
            return definitionType.getMemberType(name) != null;
        } else {
            return type instanceof AnyType;
        }
    }

}
