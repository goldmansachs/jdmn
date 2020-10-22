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

public class CloneVisitor extends AbstractVisitor {
    private final ASTFactory astFactory = new ASTFactory();

    //
    // Tests
    //
    @Override
    public Object visit(PositiveUnaryTests element, FEELContext context) {
        if (element == null) {
            return null;
        }
        
        List<Expression> putList = element.getPositiveUnaryTests().stream().map(ut -> (Expression) ut.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toPositiveUnaryTests(putList);
    }

    @Override
    public Object visit(NegatedPositiveUnaryTests element, FEELContext context) {
        if (element == null) {
            return null;
        }

        PositiveUnaryTests puts = (PositiveUnaryTests) element.getPositiveUnaryTests().accept(this, context);
        return this.astFactory.toNegatedUnaryTests(puts);
    }

    @Override
    public Object visit(SimplePositiveUnaryTests element, FEELContext context) {
        if (element == null) {
            return null;
        }

        List<Expression> expressionList = element.getSimplePositiveUnaryTests().stream().map(sput -> (Expression) sput.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toSimplePositiveUnaryTests(expressionList);
    }

    @Override
    public Object visit(NegatedSimplePositiveUnaryTests element, FEELContext context) {
        if (element == null) {
            return null;
        }

        SimplePositiveUnaryTests sputs = (SimplePositiveUnaryTests) element.getSimplePositiveUnaryTests().accept(this, context);
        return this.astFactory.toNegatedSimpleUnaryTests(sputs);
    }

    @Override
    public Object visit(Any element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(NullTest element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(ExpressionTest element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression expression = (Expression) element.getExpression().accept(this, context);
        return this.astFactory.toExpressionTest(expression);
    }

    @Override
    public Object visit(OperatorTest element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression endpoint = (Expression) element.getEndpoint().accept(this, context);
        return this.astFactory.toOperatorTest(element.getOperator(), endpoint);
    }

    @Override
    public Object visit(RangeTest element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression start = (Expression) element.getStart().accept(this, context);
        Expression end = (Expression) element.getEnd().accept(this, context);
        return this.astFactory.toIntervalTest(par(element.isOpenStart()), start, par(element.isOpenEnd()), end);
    }

    private String par(boolean isOpen) {
        return isOpen ? "(" : "]";
    }

    @Override
    public Object visit(ListTest element, FEELContext context) {
        if (element == null) {
            return null;
        }

        ListLiteral listLiteral = (ListLiteral) element.getListLiteral().accept(this, context);
        return this.astFactory.toListTest(listLiteral.getExpressionList());
    }

    //
    // Textual expressions
    //
    @Override
    public Object visit(FunctionDefinition element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(FormalParameter element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(Context element, FEELContext context) {
        if (element == null) {
            return null;
        }

        List<ContextEntry> entries = element.getEntries().stream().map(ce -> (ContextEntry) ce.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toContext(entries);
    }

    @Override
    public Object visit(ContextEntry element, FEELContext context) {
        if (element == null) {
            return null;
        }

        ContextEntryKey key = (ContextEntryKey) element.getKey().accept(this, context);
        Expression expression = (Expression) element.getExpression().accept(this, context);
        return this.astFactory.toContextEntry(key, expression);
    }

    @Override
    public Object visit(ContextEntryKey element, FEELContext context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toContextEntryKey(element.getKey());
    }

    @Override
    public Object visit(ForExpression element, FEELContext context) {
        if (element == null) {
            return null;
        }

        List<Iterator> iterators = element.getIterators().stream().map(it -> (Iterator) it.accept(this, context)).collect(Collectors.toList());
        Expression body = (Expression) element.getBody().accept(this, context);
        return this.astFactory.toForExpression(iterators, body);
    }

    @Override
    public Object visit(Iterator element, FEELContext context) {
        if (element == null) {
            return null;
        }

        IteratorDomain domain = (IteratorDomain) element.getDomain().accept(this, context);
        return this.astFactory.toIterator(element.getName(), domain);
    }

    @Override
    public Object visit(ExpressionIteratorDomain element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression expression = (Expression) element.getExpression().accept(this, context);
        return this.astFactory.toIteratorDomain(expression, null);
    }

    @Override
    public Object visit(RangeIteratorDomain element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression start = (Expression) element.getStart().accept(this, context);
        Expression end = (Expression) element.getEnd().accept(this, context);
        return this.astFactory.toIteratorDomain(start, end);
    }

    @Override
    public Object visit(IfExpression element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression condition = (Expression) element.getCondition().accept(this, context);
        Expression thenExpression = (Expression) element.getThenExpression().accept(this, context);
        Expression elseExpression = (Expression) element.getElseExpression().accept(this, context);
        return this.astFactory.toIfExpression(condition, thenExpression, elseExpression);
    }

    @Override
    public Object visit(QuantifiedExpression element, FEELContext context) {
        if (element == null) {
            return null;
        }

        List<Iterator> iterators = element.getIterators().stream().map(it -> (Iterator) it.accept(this, context)).collect(Collectors.toList());
        Expression body = (Expression) element.getBody().accept(this, context);
        return this.astFactory.toQuantifiedExpression(element.getPredicate(), iterators, body);
    }

    @Override
    public Object visit(FilterExpression element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression source = (Expression) element.getSource().accept(this, context);
        Expression filter = (Expression) element.getFilter().accept(this, context);
        return this.astFactory.toFilterExpression(source, filter);
    }

    @Override
    public Object visit(InstanceOfExpression element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression leftOperand = (Expression) element.getLeftOperand().accept(this, context);
        TypeExpression rightOperand = (TypeExpression) element.getRightOperand().accept(this, context);
        return this.astFactory.toInstanceOf(leftOperand, rightOperand);
    }

    //
    // Expressions
    //
    @Override
    public Object visit(ExpressionList element, FEELContext context) {
        if (element == null) {
            return null;
        }

        List<Expression> expressionList = element.getExpressionList().stream().map(e -> (Expression) e.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toExpressionList(expressionList);
    }

    //
    // Logic expressions
    //
    @Override
    public Object visit(Conjunction element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression left = (Expression) element.getLeftOperand().accept(this, context);
        Expression right = (Expression) element.getRightOperand().accept(this, context);
        return this.astFactory.toConjunction(left, right);
    }

    @Override
    public Object visit(Disjunction element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression left = (Expression) element.getLeftOperand().accept(this, context);
        Expression right = (Expression) element.getRightOperand().accept(this, context);
        return this.astFactory.toDisjunction(left, right);
    }

    @Override
    public Object visit(LogicNegation element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression left = (Expression) element.getLeftOperand().accept(this, context);
        return this.astFactory.toNegation("not", left);
    }

    //
    // Comparison expressions
    //
    @Override
    public Object visit(Relational element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression leftOpd = (Expression) element.getLeftOperand().accept(this, context);
        Expression rightOpd = (Expression) element.getRightOperand().accept(this, context);
        return this.astFactory.toComparison(element.getOperator(), leftOpd, rightOpd);
    }

    @Override
    public Object visit(BetweenExpression element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression value = (Expression) element.getValue().accept(this, context);
        Expression left = (Expression) element.getLeftEndpoint().accept(this, context);
        Expression right = (Expression) element.getRightEndpoint().accept(this, context);
        return this.astFactory.toBetweenExpression(value, left, right);
    }

    @Override
    public Object visit(InExpression element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression value = (Expression) element.getValue().accept(this, context);
        List<Expression> putList = element.getTests().stream().map(t -> (Expression) t.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toInExpression(value, this.astFactory.toPositiveUnaryTests(putList));
    }

    //
    // Arithmetic expressions
    //
    @Override
    public Object visit(Addition element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression left = (Expression) element.getLeftOperand().accept(this, context);
        Expression right = (Expression) element.getRightOperand().accept(this, context);
        return this.astFactory.toAddition(element.getOperator(), left, right);
    }

    @Override
    public Object visit(Multiplication element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression left = (Expression) element.getLeftOperand().accept(this, context);
        Expression right = (Expression) element.getRightOperand().accept(this, context);
        return this.astFactory.toMultiplication(element.getOperator(), left, right);
    }

    @Override
    public Object visit(Exponentiation element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression left = (Expression) element.getLeftOperand().accept(this, context);
        Expression right = (Expression) element.getRightOperand().accept(this, context);
        return this.astFactory.toExponentiation(left, right);
    }

    @Override
    public Object visit(ArithmeticNegation element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression left = (Expression) element.getLeftOperand().accept(this, context);
        return this.astFactory.toNegation("-", left);
    }

    //
    // Postfix expressions
    //
    @Override
    public Object visit(PathExpression element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression source = (Expression) element.getSource().accept(this, context);
        return this.astFactory.toPathExpression(source, element.getMember());
    }

    @Override
    public Object visit(FunctionInvocation element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Expression function = (Expression) element.getFunction().accept(this, context);
        Parameters parameters = (Parameters) element.getParameters().accept(this, context);
        return this.astFactory.toFunctionInvocation(function, parameters);
    }

    @Override
    public Object visit(NamedParameters element, FEELContext context) {
        if (element == null) {
            return null;
        }

        Map<String, Expression> newParameters = new LinkedHashMap<>();
        Map<String, Expression> parameters = element.getParameters();
        parameters.forEach((key, value1) -> {
            Expression value = (Expression) value1.accept(this, context);
            newParameters.put(key, value);
        });
        return this.astFactory.toNamedParameters(newParameters);
    }

    @Override
    public Object visit(PositionalParameters element, FEELContext context) {
        if (element == null) {
            return null;
        }

        List<Expression> expressionList = element.getParameters().stream().map(p -> (Expression) p.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toPositionalParameters(expressionList);
    }

    //
    // Primary expressions
    //
    @Override
    public Object visit(BooleanLiteral element, FEELContext context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toBooleanLiteral(element.getLexeme());
    }

    @Override
    public Object visit(DateTimeLiteral element, FEELContext context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toDateTimeLiteral(element.getConversionFunction(), element.getLexeme());
    }

    @Override
    public Object visit(NullLiteral element, FEELContext context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toNullLiteral();
    }

    @Override
    public Object visit(NumericLiteral element, FEELContext context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toNumericLiteral(element.getLexeme());
    }

    @Override
    public Object visit(StringLiteral element, FEELContext context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toStringLiteral(element.getLexeme());
    }

    @Override
    public Object visit(ListLiteral element, FEELContext context) {
        if (element == null) {
            return null;
        }

        List<Expression> expressionList = element.getExpressionList().stream().map(e -> (Expression) e.accept(this, context)).collect(Collectors.toList());
        return this.astFactory.toListLiteral(expressionList);
    }

    @Override
    public Object visit(QualifiedName element, FEELContext context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toQualifiedName(element.getNames());
    }

    @Override
    public Object visit(Name element, FEELContext context) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toName(element.getName());
    }

    @Override
    public Object visit(NamedTypeExpression element, FEELContext params) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toNamedTypeExpression(element.getQualifiedName());
    }

    @Override
    public Object visit(ListTypeExpression element, FEELContext params) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toListTypeExpression(element.getElementTypeExpression());
    }

    @Override
    public Object visit(ContextTypeExpression element, FEELContext params) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toContextTypeExpression(element.getMembers());
    }

    @Override
    public Object visit(FunctionTypeExpression element, FEELContext params) {
        if (element == null) {
            return null;
        }

        return this.astFactory.toFunctionTypeExpression(element.getParameters(), element.getReturnType());
    }
}
