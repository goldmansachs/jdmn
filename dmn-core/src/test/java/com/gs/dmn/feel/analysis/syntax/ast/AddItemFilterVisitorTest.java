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
package com.gs.dmn.feel.analysis.syntax.ast;

import com.gs.dmn.feel.analysis.semantics.AddItemFilterVisitor;
import com.gs.dmn.feel.analysis.semantics.type.ContextType;
import com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.feel.analysis.semantics.type.StringType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Name;
import com.gs.dmn.feel.analysis.syntax.ast.expression.PathExpression;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class AddItemFilterVisitorTest extends BaseVisitorTest {
    private final String parameterName = "parameterName";
    private final Type stringType = StringType.STRING;
    private final ContextType contextType = new ContextType().addMember(parameterName, Arrays.asList(), stringType);
    private final ItemDefinitionType itemDefinitionType = new ItemDefinitionType("personType").addMember(parameterName, Arrays.asList(), stringType);

    private final AddItemFilterVisitor contextTypeVisitor = new AddItemFilterVisitor(this.parameterName, contextType);
    private final AddItemFilterVisitor itemDefinitionTypeVisitor = new AddItemFilterVisitor(this.parameterName, itemDefinitionType);

    @Override
    protected Visitor getVisitor() {
        return new AddItemFilterVisitor(this.parameterName, this.stringType);
    }

    @Test
    public void testVisitName() {
        super.testVisitName();

        Name matchingElement = new Name(this.parameterName);
        assertEquals("Name(parameterName)", getVisitor().visit(matchingElement, null).toString());
        assertEquals("PathExpression(Name(parameterName), parameterName)", this.contextTypeVisitor.visit(matchingElement, null).toString());
        assertEquals("PathExpression(Name(parameterName), parameterName)", this.itemDefinitionTypeVisitor.visit(matchingElement, null).toString());

        Name notMatchingElement = new Name("otherName");
        assertEquals("Name(otherName)", getVisitor().visit(notMatchingElement, null).toString());
        assertEquals("Name(otherName)", this.contextTypeVisitor.visit(notMatchingElement, null).toString());
        assertEquals("Name(otherName)", this.itemDefinitionTypeVisitor.visit(notMatchingElement, null).toString());
    }

    @Test
    public void testVisitPathExpression() {
        super.testVisitName();

        PathExpression matchingElement = new PathExpression(new Name("parameterName"), "member");
        assertEquals("PathExpression(Name(parameterName), member)", getVisitor().visit(matchingElement, null).toString());
        assertEquals("PathExpression(PathExpression(Name(parameterName), parameterName), member)", this.contextTypeVisitor.visit(matchingElement, null).toString());
        assertEquals("PathExpression(PathExpression(Name(parameterName), parameterName), member)", this.itemDefinitionTypeVisitor.visit(matchingElement, null).toString());

        PathExpression notMatchingElement = new PathExpression(new Name("otherName"), "member");
        assertEquals("PathExpression(Name(otherName), member)", getVisitor().visit(notMatchingElement, null).toString());
        assertEquals("PathExpression(Name(otherName), member)", this.contextTypeVisitor.visit(notMatchingElement, null).toString());
        assertEquals("PathExpression(Name(otherName), member)", this.itemDefinitionTypeVisitor.visit(notMatchingElement, null).toString());
    }
}