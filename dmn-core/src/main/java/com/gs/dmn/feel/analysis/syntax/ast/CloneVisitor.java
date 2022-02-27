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

public class CloneVisitor<T, C> extends AbstractVisitor<T, C> {
    private final ASTFactory<T, C> astFactory;

    public CloneVisitor(ErrorHandler errorHandler) {
        super(errorHandler);
        this.astFactory = new ASTFactory<>();
    }

    //
    // Tests
    //
    @Override
    public Object visit(PositiveUnaryTests<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        List<Expression<T, C>> putList = element.getPositiveUnaryTests().stream().map(ut -> (Expression<T, C>) ut.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toPositiveUnaryTests(putList);
    }

    @Override
    public Object visit(NegatedPositiveUnaryTests<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        PositiveUnaryTests<T, C> puts = (PositiveUnaryTests<T, C>) element.getPositiveUnaryTests().accept(this, context);
        return this.astFactory.toNegatedUnaryTests(puts);
    }

    @Override
    public Object visit(Any<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(NullTest<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(ExpressionTest<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> expression = (Expression<T, C>) element.getExpression().accept(this, context);
        return this.astFactory.toExpressionTest(expression);
    }

    @Override
    public Object visit(OperatorRange<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> endpoint = (Expression<T, C>) element.getEndpoint().accept(this, context);
        return this.astFactory.toOperatorRange(element.getOperator(), endpoint);
    }

    @Override
    public Object visit(EndpointsRange<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> start = (Expression<T, C>) element.getStart().accept(this, context);
        Expression<T, C> end = (Expression<T, C>) element.getEnd().accept(this, context);
        return this.astFactory.toEndpointsRange(par(element.isOpenStart()), start, par(element.isOpenEnd()), end);
    }

    private String par(boolean isOpen) {
        return isOpen ? "(" : "]";
    }

    @Override
    public Object visit(ListTest<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        ListLiteral<T, C> listLiteral = (ListLiteral<T, C>) element.getListLiteral().accept(this, context);
        return this.astFactory.toListTest(listLiteral.getExpressionList());
    }

    //
    // Textual expressions
    //
    @Override
    public Object visit(FunctionDefinition<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(FormalParameter<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(Context<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        List<ContextEntry<T, C>> entries = element.getEntries().stream().map(ce -> (ContextEntry<T, C>) ce.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toContext(entries);
    }

    @Override
    public Object visit(ContextEntry<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        ContextEntryKey<T, C> key = (ContextEntryKey<T, C>) element.getKey().accept(this, context);
        Expression<T, C> expression = (Expression<T, C>) element.getExpression().accept(this, context);
        return this.astFactory.toContextEntry(key, expression);
    }

    @Override
    public Object visit(ContextEntryKey<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toContextEntryKey(element.getKey());
    }

    @Override
    public Object visit(ForExpression<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        List<Iterator<T, C>> iterators = element.getIterators().stream().map(it -> (Iterator<T, C>) it.accept(this, context)).collect(Collectors.toList());
        Expression<T, C> body = (Expression<T, C>) element.getBody().accept(this, context);
        return this.astFactory.toForExpression(iterators, body);
    }

    @Override
    public Object visit(Iterator<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        IteratorDomain<T, C> domain = (IteratorDomain<T, C>) element.getDomain().accept(this, context);
        return this.astFactory.toIterator(element.getName(), domain);
    }

    @Override
    public Object visit(ExpressionIteratorDomain<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> expression = (Expression<T, C>) element.getExpression().accept(this, context);
        return this.astFactory.toIteratorDomain(expression, null);
    }

    @Override
    public Object visit(RangeIteratorDomain<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> start = (Expression<T, C>) element.getStart().accept(this, context);
        Expression<T, C> end = (Expression<T, C>) element.getEnd().accept(this, context);
        return this.astFactory.toIteratorDomain(start, end);
    }

    @Override
    public Object visit(IfExpression<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> condition = (Expression<T, C>) element.getCondition().accept(this, context);
        Expression<T, C> thenExpression = (Expression<T, C>) element.getThenExpression().accept(this, context);
        Expression<T, C> elseExpression = (Expression<T, C>) element.getElseExpression().accept(this, context);
        return this.astFactory.toIfExpression(condition, thenExpression, elseExpression);
    }

    @Override
    public Object visit(QuantifiedExpression<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        List<Iterator<T, C>> iterators = element.getIterators().stream().map(it -> (Iterator<T, C>) it.accept(this, context)).collect(Collectors.toList());
        Expression<T, C> body = (Expression<T, C>) element.getBody().accept(this, context);
        return this.astFactory.toQuantifiedExpression(element.getPredicate(), iterators, body);
    }

    @Override
    public Object visit(FilterExpression<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> source = (Expression<T, C>) element.getSource().accept(this, context);
        Expression<T, C> filter = (Expression<T, C>) element.getFilter().accept(this, context);
        return this.astFactory.toFilterExpression(source, filter);
    }

    @Override
    public Object visit(InstanceOfExpression<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> leftOperand = (Expression<T, C>) element.getLeftOperand().accept(this, context);
        TypeExpression<T, C> rightOperand = (TypeExpression<T, C>) element.getRightOperand().accept(this, context);
        return this.astFactory.toInstanceOf(leftOperand, rightOperand);
    }

    //
    // Expressions
    //
    @Override
    public Object visit(ExpressionList<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        List<Expression<T, C>> expressionList = element.getExpressionList().stream().map(e -> (Expression<T, C>) e.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toExpressionList(expressionList);
    }

    //
    // Logic expressions
    //
    @Override
    public Object visit(Conjunction<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> left = (Expression<T, C>) element.getLeftOperand().accept(this, context);
        Expression<T, C> right = (Expression<T, C>) element.getRightOperand().accept(this, context);
        return this.astFactory.toConjunction(left, right);
    }

    @Override
    public Object visit(Disjunction<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> left = (Expression<T, C>) element.getLeftOperand().accept(this, context);
        Expression<T, C> right = (Expression<T, C>) element.getRightOperand().accept(this, context);
        return this.astFactory.toDisjunction(left, right);
    }

    @Override
    public Object visit(LogicNegation<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> left = (Expression<T, C>) element.getLeftOperand().accept(this, context);
        return this.astFactory.toNegation("not", left);
    }

    //
    // Comparison expressions
    //
    @Override
    public Object visit(Relational<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> leftOpd = (Expression<T, C>) element.getLeftOperand().accept(this, context);
        Expression<T, C> rightOpd = (Expression<T, C>) element.getRightOperand().accept(this, context);
        return this.astFactory.toComparison(element.getOperator(), leftOpd, rightOpd);
    }

    @Override
    public Object visit(BetweenExpression<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> value = (Expression<T, C>) element.getValue().accept(this, context);
        Expression<T, C> left = (Expression<T, C>) element.getLeftEndpoint().accept(this, context);
        Expression<T, C> right = (Expression<T, C>) element.getRightEndpoint().accept(this, context);
        return this.astFactory.toBetweenExpression(value, left, right);
    }

    @Override
    public Object visit(InExpression<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> value = (Expression<T, C>) element.getValue().accept(this, context);
        List<Expression<T, C>> putList = element.getTests().stream().map(t -> (Expression<T, C>) t.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toInExpression(value, this.astFactory.toPositiveUnaryTests(putList));
    }

    //
    // Arithmetic expressions
    //
    @Override
    public Object visit(Addition<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> left = (Expression<T, C>) element.getLeftOperand().accept(this, context);
        Expression<T, C> right = (Expression<T, C>) element.getRightOperand().accept(this, context);
        return this.astFactory.toAddition(element.getOperator(), left, right);
    }

    @Override
    public Object visit(Multiplication<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> left = (Expression<T, C>) element.getLeftOperand().accept(this, context);
        Expression<T, C> right = (Expression<T, C>) element.getRightOperand().accept(this, context);
        return this.astFactory.toMultiplication(element.getOperator(), left, right);
    }

    @Override
    public Object visit(Exponentiation<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> left = (Expression<T, C>) element.getLeftOperand().accept(this, context);
        Expression<T, C> right = (Expression<T, C>) element.getRightOperand().accept(this, context);
        return this.astFactory.toExponentiation(left, right);
    }

    @Override
    public Object visit(ArithmeticNegation<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> left = (Expression<T, C>) element.getLeftOperand().accept(this, context);
        return this.astFactory.toNegation("-", left);
    }

    //
    // Postfix expressions
    //
    @Override
    public Object visit(PathExpression<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> source = (Expression<T, C>) element.getSource().accept(this, context);
        return this.astFactory.toPathExpression(source, element.getMember());
    }

    @Override
    public Object visit(FunctionInvocation<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Expression<T, C> function = (Expression<T, C>) element.getFunction().accept(this, context);
        Parameters<T, C> parameters = (Parameters<T, C>) element.getParameters().accept(this, context);
        return this.astFactory.toFunctionInvocation(function, parameters);
    }

    @Override
    public Object visit(NamedParameters<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        Map<String, Expression<T, C>> newParameters = new LinkedHashMap<>();
        Map<String, Expression<T, C>> parameters = element.getParameters();
        parameters.forEach((key, value1) -> {
            Expression<T, C> value = (Expression<T, C>) value1.accept(this, context);
            newParameters.put(key, value);
        });
        return this.astFactory.toNamedParameters(newParameters);
    }

    @Override
    public Object visit(PositionalParameters<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        List<Expression<T, C>> expressionList = element.getParameters().stream().map(p -> (Expression<T, C>) p.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toPositionalParameters(expressionList);
    }

    //
    // Primary expressions
    //
    @Override
    public Object visit(BooleanLiteral<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toBooleanLiteral(element.getLexeme());
    }

    @Override
    public Object visit(DateTimeLiteral<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toDateTimeLiteral(element.getConversionFunction(), element.getLexeme());
    }

    @Override
    public Object visit(NullLiteral<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toNullLiteral();
    }

    @Override
    public Object visit(NumericLiteral<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toNumericLiteral(element.getLexeme());
    }

    @Override
    public Object visit(StringLiteral<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toStringLiteral(element.getLexeme());
    }

    @Override
    public Object visit(ListLiteral<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        List<Expression<T, C>> expressionList = element.getExpressionList().stream().map(e -> (Expression<T, C>) e.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toListLiteral(expressionList);
    }

    @Override
    public Object visit(QualifiedName<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toQualifiedName(element.getNames());
    }

    @Override
    public Object visit(Name<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toName(element.getName());
    }

    @Override
    public Object visit(NamedTypeExpression<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toNamedTypeExpression(element.getQualifiedName());
    }

    @Override
    public Object visit(ListTypeExpression<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toListTypeExpression(element.getElementTypeExpression());
    }

    @Override
    public Object visit(ContextTypeExpression<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toContextTypeExpression(element.getMembers());
    }

    @Override
    public Object visit(FunctionTypeExpression<T, C> element, C context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toFunctionTypeExpression(element.getParameters(), element.getReturnType());
    }
}
