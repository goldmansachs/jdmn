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
package com.gs.dmn.feel.analysis.syntax.ast.visitor;

import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.feel.analysis.syntax.ast.ASTFactory;
import com.gs.dmn.feel.analysis.syntax.ast.Element;
import com.gs.dmn.feel.analysis.syntax.ast.expression.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Addition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.ArithmeticNegation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Exponentiation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Multiplication;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.BetweenExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.InExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.Relational;
import com.gs.dmn.feel.analysis.syntax.ast.expression.context.Context;
import com.gs.dmn.feel.analysis.syntax.ast.expression.context.ContextEntry;
import com.gs.dmn.feel.analysis.syntax.ast.expression.context.ContextEntryKey;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.Conjunction;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.Disjunction;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.LogicNegation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.textual.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.library.FunctionDeclaration;
import com.gs.dmn.feel.analysis.syntax.ast.library.Library;
import com.gs.dmn.feel.analysis.syntax.ast.test.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CloneVisitor<T, C> extends AbstractVisitor<T, C, Element<T>> {
    private final ASTFactory<T, C> astFactory;

    public CloneVisitor(ErrorHandler errorHandler) {
        super(errorHandler);
        this.astFactory = new ASTFactory<>();
    }

    //
    // Libraries
    //
    @Override
    public Element<T> visit(Library<T> element, C context) {
        if (element == null) {
            return null;
        }

        List<FunctionDeclaration<T>> functions = element.getFunctions().stream().map(f -> (FunctionDeclaration<T>) f.accept(this, context)).collect(Collectors.toList());
        return new Library<>(element.getNamespace(), element.getName(), functions);
    }

    @Override
    public Element<T> visit(FunctionDeclaration<T> element, C context) {
        if (element == null) {
            return null;
        }

        List<FormalParameter<T>> parameters = element.getFormalParameters().stream().map(p -> (FormalParameter<T>) p.accept(this, context)).collect(Collectors.toList());
        TypeExpression<T> returnType = element.getReturnTypeExpression() == null ? null : (TypeExpression<T>) element.getReturnTypeExpression().accept(this, context);
        return this.astFactory.toFunctionDeclaration(element.getName(), parameters, returnType);
    }

    //
    // Tests
    //
    @Override
    public Element<T> visit(PositiveUnaryTests<T> element, C context) {
        if (element == null) {
            return null;
        }

        List<Expression<T>> putList = element.getPositiveUnaryTests().stream().map(ut -> (Expression<T>) ut.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toPositiveUnaryTests(putList);
    }

    @Override
    public Element<T> visit(NegatedPositiveUnaryTests<T> element, C context) {
        if (element == null) {
            return null;
        }

        PositiveUnaryTests<T> puts = element.getPositiveUnaryTests() == null ? null : (PositiveUnaryTests<T>) element.getPositiveUnaryTests().accept(this, context);
        return this.astFactory.toNegatedUnaryTests(puts);
    }

    @Override
    public Element<T> visit(Any<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(ExpressionTest<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> expression = element.getExpression() == null ? null : (Expression<T>) element.getExpression().accept(this, context);
        return this.astFactory.toExpressionTest(expression);
    }

    @Override
    public Element<T> visit(OperatorRange<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> endpoint = element.getEndpoint() == null ? null : (Expression<T>) element.getEndpoint().accept(this, context);
        return this.astFactory.toOperatorRange(element.getOperator(), endpoint);
    }

    @Override
    public Element<T> visit(EndpointsRange<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> start = element.getStart() == null ? null : (Expression<T>) element.getStart().accept(this, context);
        Expression<T> end = element.getEnd() == null ? null : (Expression<T>) element.getEnd().accept(this, context);
        return this.astFactory.toEndpointsRange(par(element.isOpenStart()), start, par(element.isOpenEnd()), end);
    }

    private String par(boolean isOpen) {
        return isOpen ? "(" : "]";
    }

    @Override
    public Element<T> visit(ListTest<T> element, C context) {
        if (element == null) {
            return null;
        }

        ListLiteral<T> listLiteral = element.getListLiteral() == null ? null : (ListLiteral<T>) element.getListLiteral().accept(this, context);
        return this.astFactory.toListTest(listLiteral == null ? null : listLiteral.getExpressionList());
    }

    //
    // Textual expressions
    //
    @Override
    public Element<T> visit(FunctionDefinition<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(FormalParameter<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(Context<T> element, C context) {
        if (element == null) {
            return null;
        }

        List<ContextEntry<T>> entries = element.getEntries().stream().map(ce -> (ContextEntry<T>) ce.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toContext(entries);
    }

    @Override
    public Element<T> visit(ContextEntry<T> element, C context) {
        if (element == null) {
            return null;
        }

        ContextEntryKey<T> key = element.getKey() == null ? null : (ContextEntryKey<T>) element.getKey().accept(this, context);
        Expression<T> expression = element.getExpression() == null ? null : (Expression<T>) element.getExpression().accept(this, context);
        return this.astFactory.toContextEntry(key, expression);
    }

    @Override
    public Element<T> visit(ContextEntryKey<T> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toContextEntryKey(element.getKey());
    }

    @Override
    public Element<T> visit(ForExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        List<Iterator<T>> iterators = element.getIterators().stream().map(it -> (Iterator<T>) it.accept(this, context)).collect(Collectors.toList());
        Expression<T> body = element.getBody() == null ? null : (Expression<T>) element.getBody().accept(this, context);
        return this.astFactory.toForExpression(iterators, body);
    }

    @Override
    public Element<T> visit(Iterator<T> element, C context) {
        if (element == null) {
            return null;
        }

        IteratorDomain<T> domain = element.getDomain() == null ? null : (IteratorDomain<T>) element.getDomain().accept(this, context);
        return this.astFactory.toIterator(element.getName(), domain);
    }

    @Override
    public Element<T> visit(ExpressionIteratorDomain<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> expression = element.getExpression() == null ? null : (Expression<T>) element.getExpression().accept(this, context);
        return this.astFactory.toIteratorDomain(expression, null);
    }

    @Override
    public Element<T> visit(RangeIteratorDomain<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> start = element.getStart() == null ? null : (Expression<T>) element.getStart().accept(this, context);
        Expression<T> end = (Expression<T>) element.getEnd().accept(this, context);
        return this.astFactory.toIteratorDomain(start, end);
    }

    @Override
    public Element<T> visit(IfExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> condition = element.getCondition() == null ? null : (Expression<T>) element.getCondition().accept(this, context);
        Expression<T> thenExpression = element.getThenExpression() == null ? null : (Expression<T>) element.getThenExpression().accept(this, context);
        Expression<T> elseExpression = element.getElseExpression() == null ? null : (Expression<T>) element.getElseExpression().accept(this, context);
        return this.astFactory.toIfExpression(condition, thenExpression, elseExpression);
    }

    @Override
    public Element<T> visit(QuantifiedExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        List<Iterator<T>> iterators = element.getIterators().stream().map(it -> (Iterator<T>) it.accept(this, context)).collect(Collectors.toList());
        Expression<T> body = element.getBody() == null ? null : (Expression<T>) element.getBody().accept(this, context);
        return this.astFactory.toQuantifiedExpression(element.getPredicate(), iterators, body);
    }

    @Override
    public Element<T> visit(FilterExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> source = element.getSource() == null ? null : (Expression<T>) element.getSource().accept(this, context);
        Expression<T> filter = element.getFilter() == null ? null : (Expression<T>) element.getFilter().accept(this, context);
        return this.astFactory.toFilterExpression(source, filter);
    }

    @Override
    public Element<T> visit(InstanceOfExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> leftOperand = element.getLeftOperand() == null ? null : (Expression<T>) element.getLeftOperand().accept(this, context);
        TypeExpression<T> rightOperand = element.getRightOperand() == null ? null : (TypeExpression<T>) element.getRightOperand().accept(this, context);
        return this.astFactory.toInstanceOf(leftOperand, rightOperand);
    }

    //
    // Expressions
    //
    @Override
    public Element<T> visit(ExpressionList<T> element, C context) {
        if (element == null) {
            return null;
        }

        List<Expression<T>> expressionList = element.getExpressionList().stream().map(e -> (Expression<T>) e.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toExpressionList(expressionList);
    }

    //
    // Logic expressions
    //
    @Override
    public Element<T> visit(Conjunction<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> left = element.getLeftOperand() == null ? null : (Expression<T>) element.getLeftOperand().accept(this, context);
        Expression<T> right = element.getRightOperand() == null ? null : (Expression<T>) element.getRightOperand().accept(this, context);
        return this.astFactory.toConjunction(left, right);
    }

    @Override
    public Element<T> visit(Disjunction<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> left = element.getLeftOperand() == null ? null : (Expression<T>) element.getLeftOperand().accept(this, context);
        Expression<T> right = element.getRightOperand() == null ? null : (Expression<T>) element.getRightOperand().accept(this, context);
        return this.astFactory.toDisjunction(left, right);
    }

    @Override
    public Element<T> visit(LogicNegation<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> left = element.getLeftOperand() == null ? null : (Expression<T>) element.getLeftOperand().accept(this, context);
        return this.astFactory.toNegation("not", left);
    }

    //
    // Comparison expressions
    //
    @Override
    public Element<T> visit(Relational<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> leftOpd = element.getLeftOperand() == null ? null : (Expression<T>) element.getLeftOperand().accept(this, context);
        Expression<T> rightOpd = element.getRightOperand() == null ? null : (Expression<T>) element.getRightOperand().accept(this, context);
        return this.astFactory.toComparison(element.getOperator(), leftOpd, rightOpd);
    }

    @Override
    public Element<T> visit(BetweenExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> value = element.getValue() == null ? null : (Expression<T>) element.getValue().accept(this, context);
        Expression<T> left = element.getLeftEndpoint() == null ? null : (Expression<T>) element.getLeftEndpoint().accept(this, context);
        Expression<T> right = element.getRightEndpoint() == null ? null : (Expression<T>) element.getRightEndpoint().accept(this, context);
        return this.astFactory.toBetweenExpression(value, left, right);
    }

    @Override
    public Element<T> visit(InExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> value = element.getValue() == null ? null : (Expression<T>) element.getValue().accept(this, context);
        List<Expression<T>> putList = element.getTests().stream().map(t -> (Expression<T>) t.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toInExpression(value, this.astFactory.toPositiveUnaryTests(putList));
    }

    //
    // Arithmetic expressions
    //
    @Override
    public Element<T> visit(Addition<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> left = element.getLeftOperand() == null ? null : (Expression<T>) element.getLeftOperand().accept(this, context);
        Expression<T> right = element.getRightOperand() == null ? null : (Expression<T>) element.getRightOperand().accept(this, context);
        return this.astFactory.toAddition(element.getOperator(), left, right);
    }

    @Override
    public Element<T> visit(Multiplication<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> left = element.getLeftOperand() == null ? null : (Expression<T>) element.getLeftOperand().accept(this, context);
        Expression<T> right = element.getRightOperand() == null ? null : (Expression<T>) element.getRightOperand().accept(this, context);
        return this.astFactory.toMultiplication(element.getOperator(), left, right);
    }

    @Override
    public Element<T> visit(Exponentiation<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> left = element.getLeftOperand() == null ? null : (Expression<T>) element.getLeftOperand().accept(this, context);
        Expression<T> right = element.getRightOperand() == null ? null : (Expression<T>) element.getRightOperand().accept(this, context);
        return this.astFactory.toExponentiation(left, right);
    }

    @Override
    public Element<T> visit(ArithmeticNegation<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> left = element.getLeftOperand() == null ? null : (Expression<T>) element.getLeftOperand().accept(this, context);
        return this.astFactory.toNegation("-", left);
    }

    //
    // Postfix expressions
    //
    @Override
    public Element<T> visit(PathExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> source = element.getSource() == null ? null : (Expression<T>) element.getSource().accept(this, context);
        return this.astFactory.toPathExpression(source, element.getMember());
    }

    @Override
    public Element<T> visit(FunctionInvocation<T> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T> function = element.getFunction() == null ? null : (Expression<T>) element.getFunction().accept(this, context);
        Parameters<T> parameters = element.getParameters() == null ? null : (Parameters<T>) element.getParameters().accept(this, context);
        return this.astFactory.toFunctionInvocation(function, parameters);
    }

    @Override
    public Element<T> visit(NamedParameters<T> element, C context) {
        if (element == null) {
            return null;
        }

        Map<String, Expression<T>> newParameters = new LinkedHashMap<>();
        Map<String, Expression<T>> parameters = element.getParameters();
        parameters.forEach((key, value1) -> {
            Expression<T> value = (Expression<T>) value1.accept(this, context);
            newParameters.put(key, value);
        });
        return this.astFactory.toNamedParameters(newParameters);
    }

    @Override
    public Element<T> visit(PositionalParameters<T> element, C context) {
        if (element == null) {
            return null;
        }

        List<Expression<T>> expressionList = element.getParameters().stream().map(p -> (Expression<T>) p.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toPositionalParameters(expressionList);
    }

    //
    // Primary expressions
    //
    @Override
    public Element<T> visit(BooleanLiteral<T> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toBooleanLiteral(element.getLexeme());
    }

    @Override
    public Element<T> visit(DateTimeLiteral<T> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toDateTimeLiteral(element.getConversionFunction(), element.getLexeme());
    }

    @Override
    public Element<T> visit(NullLiteral<T> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toNullLiteral();
    }

    @Override
    public Element<T> visit(NumericLiteral<T> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toNumericLiteral(element.getLexeme());
    }

    @Override
    public Element<T> visit(StringLiteral<T> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toStringLiteral(element.getLexeme());
    }

    @Override
    public Element<T> visit(ListLiteral<T> element, C context) {
        if (element == null) {
            return null;
        }

        List<Expression<T>> expressionList = element.getExpressionList().stream().map(e -> (Expression<T>) e.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toListLiteral(expressionList);
    }

    @Override
    public Element<T> visit(QualifiedName<T> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toQualifiedName(element.getNames());
    }

    @Override
    public Element<T> visit(Name<T> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toName(element.getName());
    }

    @Override
    public Element<T> visit(NamedTypeExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toNamedTypeExpression(element.getQualifiedName());
    }

    @Override
    public Element<T> visit(ContextTypeExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toContextTypeExpression(element.getMembers());
    }

    @Override
    public Element<T> visit(RangeTypeExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toRangeTypeExpression(element.getElementTypeExpression());
    }

    @Override
    public Element<T> visit(FunctionTypeExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toFunctionTypeExpression(element.getParameters(), element.getReturnType());
    }

    @Override
    public Element<T> visit(ListTypeExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toListTypeExpression(element.getElementTypeExpression());
    }
}
