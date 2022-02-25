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

import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.feel.analysis.syntax.ast.expression.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Addition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.ArithmeticNegation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Exponentiation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Multiplication;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.BetweenExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.InExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.Relational;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.Conjunction;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.Disjunction;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.LogicNegation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.textual.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.test.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CloneVisitor<C> extends AbstractVisitor<C> {
    private final ASTFactory<C> astFactory;

    public CloneVisitor(ErrorHandler errorHandler) {
        super(errorHandler);
        this.astFactory = new ASTFactory<>();
    }

    //
    // Tests
    //
    @Override
    public Object visit(PositiveUnaryTests<C> element, C context) {
        if (element == null) {
            return null;
        }

        List<Expression<C>> putList = element.getPositiveUnaryTests().stream().map(ut -> (Expression<C>) ut.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toPositiveUnaryTests(putList);
    }

    @Override
    public Object visit(NegatedPositiveUnaryTests<C> element, C context) {
        if (element == null) {
            return null;
        }

        PositiveUnaryTests<C> puts = (PositiveUnaryTests<C>) element.getPositiveUnaryTests().accept(this, context);
        return this.astFactory.toNegatedUnaryTests(puts);
    }

    @Override
    public Object visit(Any<C> element, C context) {
        return element;
    }

    @Override
    public Object visit(NullTest<C> element, C context) {
        return element;
    }

    @Override
    public Object visit(ExpressionTest<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> expression = (Expression<C>) element.getExpression().accept(this, context);
        return this.astFactory.toExpressionTest(expression);
    }

    @Override
    public Object visit(OperatorRange<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> endpoint = (Expression<C>) element.getEndpoint().accept(this, context);
        return this.astFactory.toOperatorRange(element.getOperator(), endpoint);
    }

    @Override
    public Object visit(EndpointsRange<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> start = (Expression<C>) element.getStart().accept(this, context);
        Expression<C> end = (Expression<C>) element.getEnd().accept(this, context);
        return this.astFactory.toEndpointsRange(par(element.isOpenStart()), start, par(element.isOpenEnd()), end);
    }

    private String par(boolean isOpen) {
        return isOpen ? "(" : "]";
    }

    @Override
    public Object visit(ListTest<C> element, C context) {
        if (element == null) {
            return null;
        }

        ListLiteral<C> listLiteral = (ListLiteral<C>) element.getListLiteral().accept(this, context);
        return this.astFactory.toListTest(listLiteral.getExpressionList());
    }

    //
    // Textual expressions
    //
    @Override
    public Object visit(FunctionDefinition<C> element, C context) {
        return element;
    }

    @Override
    public Object visit(FormalParameter<C> element, C context) {
        return element;
    }

    @Override
    public Object visit(Context<C> element, C context) {
        if (element == null) {
            return null;
        }

        List<ContextEntry<C>> entries = element.getEntries().stream().map(ce -> (ContextEntry<C>) ce.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toContext(entries);
    }

    @Override
    public Object visit(ContextEntry<C> element, C context) {
        if (element == null) {
            return null;
        }

        ContextEntryKey<C> key = (ContextEntryKey<C>) element.getKey().accept(this, context);
        Expression<C> expression = (Expression<C>) element.getExpression().accept(this, context);
        return this.astFactory.toContextEntry(key, expression);
    }

    @Override
    public Object visit(ContextEntryKey<C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toContextEntryKey(element.getKey());
    }

    @Override
    public Object visit(ForExpression<C> element, C context) {
        if (element == null) {
            return null;
        }

        List<Iterator<C>> iterators = element.getIterators().stream().map(it -> (Iterator<C>) it.accept(this, context)).collect(Collectors.toList());
        Expression<C> body = (Expression<C>) element.getBody().accept(this, context);
        return this.astFactory.toForExpression(iterators, body);
    }

    @Override
    public Object visit(Iterator<C> element, C context) {
        if (element == null) {
            return null;
        }

        IteratorDomain<C> domain = (IteratorDomain<C>) element.getDomain().accept(this, context);
        return this.astFactory.toIterator(element.getName(), domain);
    }

    @Override
    public Object visit(ExpressionIteratorDomain<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> expression = (Expression<C>) element.getExpression().accept(this, context);
        return this.astFactory.toIteratorDomain(expression, null);
    }

    @Override
    public Object visit(RangeIteratorDomain<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> start = (Expression<C>) element.getStart().accept(this, context);
        Expression<C> end = (Expression<C>) element.getEnd().accept(this, context);
        return this.astFactory.toIteratorDomain(start, end);
    }

    @Override
    public Object visit(IfExpression<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> condition = (Expression<C>) element.getCondition().accept(this, context);
        Expression<C> thenExpression = (Expression<C>) element.getThenExpression().accept(this, context);
        Expression<C> elseExpression = (Expression<C>) element.getElseExpression().accept(this, context);
        return this.astFactory.toIfExpression(condition, thenExpression, elseExpression);
    }

    @Override
    public Object visit(QuantifiedExpression<C> element, C context) {
        if (element == null) {
            return null;
        }

        List<Iterator<C>> iterators = element.getIterators().stream().map(it -> (Iterator<C>) it.accept(this, context)).collect(Collectors.toList());
        Expression<C> body = (Expression<C>) element.getBody().accept(this, context);
        return this.astFactory.toQuantifiedExpression(element.getPredicate(), iterators, body);
    }

    @Override
    public Object visit(FilterExpression<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> source = (Expression<C>) element.getSource().accept(this, context);
        Expression<C> filter = (Expression<C>) element.getFilter().accept(this, context);
        return this.astFactory.toFilterExpression(source, filter);
    }

    @Override
    public Object visit(InstanceOfExpression<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> leftOperand = (Expression<C>) element.getLeftOperand().accept(this, context);
        TypeExpression<C> rightOperand = (TypeExpression<C>) element.getRightOperand().accept(this, context);
        return this.astFactory.toInstanceOf(leftOperand, rightOperand);
    }

    //
    // Expressions
    //
    @Override
    public Object visit(ExpressionList<C> element, C context) {
        if (element == null) {
            return null;
        }

        List<Expression<C>> expressionList = element.getExpressionList().stream().map(e -> (Expression<C>) e.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toExpressionList(expressionList);
    }

    //
    // Logic expressions
    //
    @Override
    public Object visit(Conjunction<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> left = (Expression<C>) element.getLeftOperand().accept(this, context);
        Expression<C> right = (Expression<C>) element.getRightOperand().accept(this, context);
        return this.astFactory.toConjunction(left, right);
    }

    @Override
    public Object visit(Disjunction<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> left = (Expression<C>) element.getLeftOperand().accept(this, context);
        Expression<C> right = (Expression<C>) element.getRightOperand().accept(this, context);
        return this.astFactory.toDisjunction(left, right);
    }

    @Override
    public Object visit(LogicNegation<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> left = (Expression<C>) element.getLeftOperand().accept(this, context);
        return this.astFactory.toNegation("not", left);
    }

    //
    // Comparison expressions
    //
    @Override
    public Object visit(Relational<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> leftOpd = (Expression<C>) element.getLeftOperand().accept(this, context);
        Expression<C> rightOpd = (Expression<C>) element.getRightOperand().accept(this, context);
        return this.astFactory.toComparison(element.getOperator(), leftOpd, rightOpd);
    }

    @Override
    public Object visit(BetweenExpression<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> value = (Expression<C>) element.getValue().accept(this, context);
        Expression<C> left = (Expression<C>) element.getLeftEndpoint().accept(this, context);
        Expression<C> right = (Expression<C>) element.getRightEndpoint().accept(this, context);
        return this.astFactory.toBetweenExpression(value, left, right);
    }

    @Override
    public Object visit(InExpression<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> value = (Expression<C>) element.getValue().accept(this, context);
        List<Expression<C>> putList = element.getTests().stream().map(t -> (Expression<C>) t.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toInExpression(value, this.astFactory.toPositiveUnaryTests(putList));
    }

    //
    // Arithmetic expressions
    //
    @Override
    public Object visit(Addition<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> left = (Expression<C>) element.getLeftOperand().accept(this, context);
        Expression<C> right = (Expression<C>) element.getRightOperand().accept(this, context);
        return this.astFactory.toAddition(element.getOperator(), left, right);
    }

    @Override
    public Object visit(Multiplication<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> left = (Expression<C>) element.getLeftOperand().accept(this, context);
        Expression<C> right = (Expression<C>) element.getRightOperand().accept(this, context);
        return this.astFactory.toMultiplication(element.getOperator(), left, right);
    }

    @Override
    public Object visit(Exponentiation<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> left = (Expression<C>) element.getLeftOperand().accept(this, context);
        Expression<C> right = (Expression<C>) element.getRightOperand().accept(this, context);
        return this.astFactory.toExponentiation(left, right);
    }

    @Override
    public Object visit(ArithmeticNegation<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> left = (Expression<C>) element.getLeftOperand().accept(this, context);
        return this.astFactory.toNegation("-", left);
    }

    //
    // Postfix expressions
    //
    @Override
    public Object visit(PathExpression<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> source = (Expression<C>) element.getSource().accept(this, context);
        return this.astFactory.toPathExpression(source, element.getMember());
    }

    @Override
    public Object visit(FunctionInvocation<C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<C> function = (Expression<C>) element.getFunction().accept(this, context);
        Parameters<C> parameters = (Parameters<C>) element.getParameters().accept(this, context);
        return this.astFactory.toFunctionInvocation(function, parameters);
    }

    @Override
    public Object visit(NamedParameters<C> element, C context) {
        if (element == null) {
            return null;
        }

        Map<String, Expression<C>> newParameters = new LinkedHashMap<>();
        Map<String, Expression<C>> parameters = element.getParameters();
        parameters.forEach((key, value1) -> {
            Expression<C> value = (Expression<C>) value1.accept(this, context);
            newParameters.put(key, value);
        });
        return this.astFactory.toNamedParameters(newParameters);
    }

    @Override
    public Object visit(PositionalParameters<C> element, C context) {
        if (element == null) {
            return null;
        }

        List<Expression<C>> expressionList = element.getParameters().stream().map(p -> (Expression<C>) p.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toPositionalParameters(expressionList);
    }

    //
    // Primary expressions
    //
    @Override
    public Object visit(BooleanLiteral<C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toBooleanLiteral(element.getLexeme());
    }

    @Override
    public Object visit(DateTimeLiteral<C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toDateTimeLiteral(element.getConversionFunction(), element.getLexeme());
    }

    @Override
    public Object visit(NullLiteral<C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toNullLiteral();
    }

    @Override
    public Object visit(NumericLiteral<C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toNumericLiteral(element.getLexeme());
    }

    @Override
    public Object visit(StringLiteral<C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toStringLiteral(element.getLexeme());
    }

    @Override
    public Object visit(ListLiteral<C> element, C context) {
        if (element == null) {
            return null;
        }

        List<Expression<C>> expressionList = element.getExpressionList().stream().map(e -> (Expression<C>) e.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toListLiteral(expressionList);
    }

    @Override
    public Object visit(QualifiedName<C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toQualifiedName(element.getNames());
    }

    @Override
    public Object visit(Name<C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toName(element.getName());
    }

    @Override
    public Object visit(NamedTypeExpression<C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toNamedTypeExpression(element.getQualifiedName());
    }

    @Override
    public Object visit(ListTypeExpression<C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toListTypeExpression(element.getElementTypeExpression());
    }

    @Override
    public Object visit(ContextTypeExpression<C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toContextTypeExpression(element.getMembers());
    }

    @Override
    public Object visit(FunctionTypeExpression<C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toFunctionTypeExpression(element.getParameters(), element.getReturnType());
    }
}
