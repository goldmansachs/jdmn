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

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.error.NopErrorHandler;
import com.gs.dmn.feel.analysis.semantics.type.ContextType;
import com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.feel.analysis.semantics.type.StringType;
import com.gs.dmn.feel.analysis.syntax.ast.Element;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Name;
import com.gs.dmn.feel.analysis.syntax.ast.expression.PathExpression;
import com.gs.dmn.feel.analysis.syntax.ast.visitor.BaseVisitorTest;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddItemFilterVisitorTest extends BaseVisitorTest {
    private final String parameterName = "parameterName";
    private final Type stringType = StringType.STRING;
    private final ContextType contextType = new ContextType().addMember(this.parameterName, Collections.emptyList(), this.stringType);
    private final ItemDefinitionType itemDefinitionType = new ItemDefinitionType("personType").addMember(this.parameterName, Collections.emptyList(), this.stringType);

    private final ErrorHandler errorHandler = NopErrorHandler.INSTANCE;
    private final AddItemFilterVisitor<Type, DMNContext> contextTypeVisitor = new AddItemFilterVisitor<>(this.parameterName, this.contextType, this.errorHandler);
    private final AddItemFilterVisitor<Type, DMNContext> itemDefinitionTypeVisitor = new AddItemFilterVisitor<>(this.parameterName, this.itemDefinitionType, this.errorHandler);

    @Override
    protected Visitor<Type, DMNContext, Element<Type>> getVisitor() {
        return new AddItemFilterVisitor<>(this.parameterName, this.stringType, this.errorHandler);
    }

    @Override
    @Test
    public void testVisitName() {
        super.testVisitName();

        Name<Type> matchingElement = new Name<>(this.parameterName);
        assertEquals("Name(parameterName)", getVisitor().visit(matchingElement, null).toString());
        assertEquals("PathExpression(Name(parameterName), parameterName)", this.contextTypeVisitor.visit(matchingElement, null).toString());
        assertEquals("PathExpression(Name(parameterName), parameterName)", this.itemDefinitionTypeVisitor.visit(matchingElement, null).toString());

        Name<Type> notMatchingElement = new Name<>("otherName");
        assertEquals("Name(otherName)", getVisitor().visit(notMatchingElement, null).toString());
        assertEquals("Name(otherName)", this.contextTypeVisitor.visit(notMatchingElement, null).toString());
        assertEquals("Name(otherName)", this.itemDefinitionTypeVisitor.visit(notMatchingElement, null).toString());
    }

    @Override
    @Test
    public void testVisitPathExpression() {
        super.testVisitName();

        PathExpression<Type> matchingElement = new PathExpression<>(new Name<>("parameterName"), "member");
        assertEquals("PathExpression(Name(parameterName), member)", getVisitor().visit(matchingElement, null).toString());
        assertEquals("PathExpression(PathExpression(Name(parameterName), parameterName), member)", this.contextTypeVisitor.visit(matchingElement, null).toString());
        assertEquals("PathExpression(PathExpression(Name(parameterName), parameterName), member)", this.itemDefinitionTypeVisitor.visit(matchingElement, null).toString());

        PathExpression<Type> notMatchingElement = new PathExpression<>(new Name<>("otherName"), "member");
        assertEquals("PathExpression(Name(otherName), member)", getVisitor().visit(notMatchingElement, null).toString());
        assertEquals("PathExpression(Name(otherName), member)", this.contextTypeVisitor.visit(notMatchingElement, null).toString());
        assertEquals("PathExpression(Name(otherName), member)", this.itemDefinitionTypeVisitor.visit(notMatchingElement, null).toString());
    }
}