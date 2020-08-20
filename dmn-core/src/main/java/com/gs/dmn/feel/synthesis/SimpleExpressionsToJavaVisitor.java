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
package com.gs.dmn.feel.synthesis;

import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.environment.Declaration;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.VariableDeclaration;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.Element;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Addition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.ArithmeticNegation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Exponentiation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Multiplication;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.BetweenExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.InExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.Relational;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.Conjunction;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.Disjunction;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.LogicNegation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.textual.*;
import com.gs.dmn.feel.analysis.syntax.ast.test.*;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

import java.util.List;
import java.util.stream.Collectors;

class SimpleExpressionsToJavaVisitor extends FEELToJavaVisitor {
    public SimpleExpressionsToJavaVisitor(BasicDMNToNativeTransformer dmnTransformer) {
        super(dmnTransformer);
    }

    //
    // Tests
    //
    @Override
    public Object visit(PositiveUnaryTests element, FEELContext context) {
        List<PositiveUnaryTest> positiveUnaryTests = element.getPositiveUnaryTests();
        return positiveUnaryTests.stream().map(put -> (String) put.accept(this, context)).collect(Collectors.joining(", "));
    }

    @Override
    public Object visit(NegatedPositiveUnaryTests element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(SimplePositiveUnaryTests element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(NegatedSimplePositiveUnaryTests element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(Any element, FEELContext context) {
        return "-";
    }

    @Override
    public Object visit(NullTest element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(ExpressionTest element, FEELContext context) {
        return element.getExpression().accept(this, context);
    }

    @Override
    public Object visit(OperatorTest element, FEELContext context) {
        return element.getEndpoint().accept(this, context);
    }

    @Override
    public Object visit(RangeTest element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(ListTest element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    //
    // Textual expressions
    //
    @Override
    public Object visit(FunctionDefinition element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(FormalParameter element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(ForExpression element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(Iterator element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(ExpressionIteratorDomain element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(RangeIteratorDomain element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(IfExpression element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(QuantifiedExpression element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(FilterExpression element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(InstanceOfExpression element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    //
    // Expressions
    //
    @Override
    public Object visit(ExpressionList element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    //
    // Logic functions
    //
    @Override
    public Object visit(Conjunction element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(Disjunction element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(LogicNegation element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    //
    // Comparison expressions
    //
    @Override
    public Object visit(Relational element, FEELContext context) {
        return super.visit(element, context);
    }

    @Override
    public Object visit(BetweenExpression element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(InExpression element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    //
    // Primary expressions
    //
    @Override
    public Object visit(QualifiedName element, FEELContext context) {
        List<String> names = element.getNames();
        if (names.size() == 1) {
            return javaFriendlyVariableName(names.get(0));
        } else if (names.size() == 2) {
            String sourceName = names.get(0);
            String memberName = names.get(1);
            return makeNavigationPath(element, sourceName, memberName, context);
        }
        throw new SemanticError(element, String.format("Cannot compute navigation path '%s'", element.toString()));
    }

    @Override
    public Object visit(Name element, FEELContext context) {
        String name = element.getName();
        String javaName = javaFriendlyVariableName(name);
        return this.dmnTransformer.lazyEvaluation(name, javaName);
    }

    private String makeNavigationPath(Expression element, String sourceName, String memberName, FEELContext params) {
        Environment environment = params.getEnvironment();

        // Look up source declaration
        Declaration sourceDeclaration = environment.lookupVariableDeclaration(sourceName);
        if (sourceDeclaration instanceof VariableDeclaration) {
            com.gs.dmn.feel.analysis.semantics.type.Type sourceType = sourceDeclaration.getType();
            String sourceVariableName = javaFriendlyVariableName(sourceName);
            String memberVariableName = javaFriendlyVariableName(memberName);
            return makeNavigation(element, sourceType, sourceVariableName, memberName, memberVariableName);
        }
        throw new SemanticError(element, String.format("Cannot generate navigation path '%s'", element.toString()));
    }
}
