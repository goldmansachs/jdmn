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

import com.gs.dmn.feel.analysis.syntax.ast.expression.Iterator;
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
import com.gs.dmn.feel.analysis.syntax.ast.test.*;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.gs.dmn.feel.FEELConstants.DATE_TIME_LITERAL_NAMES;

public class ASTFactory<T, C> {
    //
    // Expressions
    //
    public Expression<T, C> toExpressionList(List<Expression<T, C>> expressionList) {
        if (expressionList.size() == 1) {
            return expressionList.get(0);
        } else {
            return new ExpressionList<>(expressionList);
        }
    }

    public Expression<T, C> toComparison(String operator, Expression<T, C> leftOperand, Expression<T, C> rightOperand) {
        return new Relational<>(operator, leftOperand, rightOperand);
    }

    public Expression<T, C> toMultiplication(String operator, Expression<T, C> leftOperand, Expression<T, C> rightOperand) {
        return new Multiplication<>(operator, leftOperand, rightOperand);
    }

    public Expression<T, C> toAddition(String operator, Expression<T, C> leftOperand, Expression<T, C> rightOperand) {
        return new Addition<>(operator, leftOperand, rightOperand);
    }

    public Expression<T, C> toExponentiation(Expression<T, C> leftOperand, Expression<T, C> rightOperand) {
        return new Exponentiation<>(leftOperand, rightOperand);
    }

    public Expression<T, C> toNegation(List<String> kindList, Expression<T, C> operand) {
        kindList = optimizeDoubleNegation(kindList);
        Expression<T, C> result = operand;
        for(int i = kindList.size() - 1; i >= 0; i--) {
            String kind = kindList.get(i);
            result = toNegation(kind, result);
        }
        return result;
    }

    private List<String> optimizeDoubleNegation(List<String> kindList) {
        if (kindList.size() >= 2) {
            String op = kindList.get(0);
            if (sameOperator(op, kindList)) {
                int size = kindList.size();
                if (size % 2 == 0) {
                    return new ArrayList<>();
                } else {
                    List<String> arrayList = new ArrayList<>();
                    arrayList.add(op);
                    return arrayList;
                }
            }
            return kindList;
        } else {
            return kindList;
        }
    }

    private boolean sameOperator(String op, List<String> kindList) {
        for(String kind: kindList) {
            if (!op.equals(kind)) {
                return false;
            }
        }
        return true;
    }

    public Expression<T, C> toNegation(String kind, Expression<T, C> operand) {
        if ("-".equals(kind)) {
            return new ArithmeticNegation<>(operand);
        } else if ("not".equals(kind)) {
            return new LogicNegation<>(operand);
        } else {
            throw new DMNRuntimeException(String.format("Unknown unary operator '%s'", kind));
        }
    }

    public NumericLiteral<T, C> toNumericLiteral(String lexeme) {
        return new NumericLiteral<>(lexeme);
    }

    public StringLiteral<T, C> toStringLiteral(String lexeme) {
        return new StringLiteral<>(lexeme);
    }

    public BooleanLiteral<T, C> toBooleanLiteral(String lexeme) {
        return new BooleanLiteral<>(lexeme);
    }

    public Expression<T, C> toDateTimeLiteral(String lexeme) {
        String stringLiteral = temporalStringLiteral(lexeme);
        String kind = temporalLiteralKind(stringLiteral);
        return this.toDateTimeLiteral(kind, stringLiteral);
    }

    private String temporalStringLiteral(String lexeme) {
        if (StringUtils.isEmpty(lexeme)) {
            return lexeme;
        } else {
            return lexeme.substring(1).trim();
        }
    }

    private String temporalLiteralKind(String stringLiteral) {
        String kind;
        if (StringUtils.isEmpty(stringLiteral) || "\"\"".equals(stringLiteral)) {
            kind = "date and time";
        } else if (stringLiteral.contains("-") && stringLiteral.contains("T") && stringLiteral.contains(":")) {
            kind = "date and time";
        } else if (stringLiteral.contains(":")) {
            kind = "time";
        } else if (stringLiteral.startsWith("\"P") || stringLiteral.startsWith("\"-P") || stringLiteral.startsWith("\"+P")) {
            kind = "duration";
        } else {
            kind = "date";
        }
        return kind;
    }

    public Expression<T, C> toDateTimeLiteral(String kind, Expression<T, C> stringLiteral) {
        return toFunctionInvocation(toName(kind), new PositionalParameters<>(Collections.singletonList(stringLiteral)));
    }

    public Expression<T, C> toDateTimeLiteral(String kind, String lexeme) {
        return new DateTimeLiteral<>(kind, lexeme);
    }

    public Expression<T, C> toName(String name) {
        return new Name<>(name);
    }

    public Expression<T, C> toQualifiedName(String... names) {
        return toQualifiedName(Arrays.asList(names));
    }

    public Expression<T, C> toQualifiedName(List<String> names) {
        if (names.size() > 0) {
            return toPathExpression(names);
        } else {
            throw new IllegalArgumentException(String.format("Illegal qualified name '%s'", names));
        }
    }

    //
    // Tests
    //
    public UnaryTests<T, C> toAny() {
        return new Any<>();
    }

    public PositiveUnaryTest<T, C> toPositiveUnaryTest(Expression<T, C> expression) {
        if (expression instanceof SimplePositiveUnaryTest) {
            return (PositiveUnaryTest<T, C>) expression;
        } else if (expression instanceof NullTest) {
            return (PositiveUnaryTest<T, C>) expression;
        } else if (expression instanceof ExpressionTest) {
            return (PositiveUnaryTest<T, C>) expression;
        } else if (expression instanceof NullLiteral) {
            return toNullPositiveUnaryTest();
        } else if (expression instanceof ListLiteral) {
            // Shallow conversion of list elements to Positive Unary Test
            List<Expression<T, C>> puts = new ArrayList<>();
            for (Expression listElement: ((ListLiteral<T, C>) expression).getExpressionList()) {
                if (listElement instanceof ListLiteral) {
                    puts.add(listElement);
                } else {
                    PositiveUnaryTest put = toPositiveUnaryTest(listElement);
                    puts.add(put);
                }
            }
            return toListTest((ListLiteral<T, C>) toListLiteral(puts));
        } else {
            if (containsQuestionMark(expression)) {
                return toExpressionTest(expression);
            } else {
                return toOperatorRange(null, expression);
            }
        }
    }

    private boolean containsQuestionMark(Expression<T, C> expression) {
        ContainsNameVisitor<T, C> visitor = new ContainsNameVisitor<>();
        expression.accept(visitor, null);
        return visitor.isFound();
    }

    public NullTest<T, C> toNullPositiveUnaryTest() {
        return new NullTest<>();
    }

    public ExpressionTest<T, C> toExpressionTest(Expression<T, C> expression) {
        return new ExpressionTest<>(expression);
    }

    public OperatorRange<T, C> toOperatorRange(String operator, Expression<T, C> endpoint) {
        return new OperatorRange<>(operator, endpoint);
    }

    public EndpointsRange<T, C> toEndpointsRange(String leftPar, Expression<T, C> start, String rightPar, Expression<T, C> end) {
        return new EndpointsRange<>(!"[".equals(leftPar), start, !"]".equals(rightPar), end);
    }

    public ListTest<T, C> toListTest(ListLiteral<T, C> expression) {
        return new ListTest<>(expression);
    }

    public ListTest<T, C> toListTest(List<Expression<T, C>> expressions) {
        return new ListTest<>((ListLiteral<T, C>) toListLiteral(expressions));
    }

    public UnaryTests<T, C> toNegatedUnaryTests(PositiveUnaryTests<T, C> ast) {
        return new NegatedPositiveUnaryTests<>(ast);
    }

    public PositiveUnaryTests<T, C> toPositiveUnaryTests(List<Expression<T, C>> expressions) {
        List<PositiveUnaryTest<T, C>> positiveUnaryTests = new ArrayList<>();
        for (Expression<T, C> e : expressions) {
            if (e instanceof PositiveUnaryTest) {
                positiveUnaryTests.add((PositiveUnaryTest<T, C>) e);
            } else {
                positiveUnaryTests.add(toPositiveUnaryTest(e));
            }
        }
        return new PositiveUnaryTests<>(positiveUnaryTests);
    }

    public FormalParameter<T, C> toFormalParameter(String parameterName, TypeExpression<T, C> typeExpression) {
        return new FormalParameter<>(parameterName, typeExpression);
    }

    public Expression<T, C> toFunctionDefinition(List<FormalParameter<T, C>> formalParameters, TypeExpression<T, C> returnTypeExpression, Expression<T, C> body, boolean external) {
        return new FunctionDefinition<>(formalParameters, returnTypeExpression, body, external);
    }

    public Iterator<T, C> toIterator(String name, Expression<T, C> domain) {
        return new Iterator<>(name, toIteratorDomain(domain, null));
    }

    public Iterator<T, C> toIterator(String name, IteratorDomain<T, C> domain) {
        return new Iterator<>(name, domain);
    }

    public IteratorDomain<T, C> toIteratorDomain(Expression<T, C> start, Expression<T, C> end) {
        if (end == null) {
            return new ExpressionIteratorDomain<>(start);
        } else {
            return new RangeIteratorDomain<>(start, end);
        }
    }

    public Expression<T, C> toContext(List<ContextEntry<T, C>> entries) {
        return new Context<>(entries);
    }

    public ContextEntryKey<T, C> toContextEntryKey(String text) {
        return new ContextEntryKey<>(text);
    }

    public ContextEntry<T, C> toContextEntry(ContextEntryKey<T, C> key, Expression<T, C> expression) {
        return new ContextEntry<>(key, expression);
    }

    public Expression<T, C> toListLiteral(List<Expression<T, C>> expressions) {
        return new ListLiteral<>(expressions);
    }

    public Expression<T, C> toNullLiteral() {
        return new NullLiteral<>();
    }

    public Expression<T, C> toForExpression(List<Iterator<T, C>> iterators, Expression<T, C> body) {
        return new ForExpression<>(iterators, body);
    }

    public IfExpression<T, C> toIfExpression(Expression<T, C> condition, Expression<T, C> thenExpression, Expression<T, C> elseExpression) {
        return new IfExpression<>(condition, thenExpression, elseExpression);
    }

    public Expression<T, C> toQuantifiedExpression(String predicate, List<Iterator<T, C>> iterators, Expression<T, C> body) {
        return new QuantifiedExpression<>(predicate, iterators, body);
    }

    public Expression<T, C> toDisjunction(Expression<T, C> left, Expression<T, C> right) {
        return new Disjunction<>(left, right);
    }

    public Expression<T, C> toConjunction(Expression<T, C> left, Expression<T, C> right) {
        return new Conjunction<>(left, right);
    }

    public Expression<T, C> toBetweenExpression(Expression<T, C> value, Expression<T, C> leftEndpoint, Expression<T, C> rightEndpoint) {
        return new BetweenExpression<>(value, leftEndpoint, rightEndpoint);
    }

    public Expression<T, C> toInExpression(Expression<T, C> value, Expression<T, C> expression) {
        if (expression instanceof PositiveUnaryTest) {
            return new InExpression<>(value, (PositiveUnaryTest<T, C>) expression);
        } else if (expression instanceof PositiveUnaryTests) {
            return new InExpression<>(value, (PositiveUnaryTests<T, C>) expression);
        } else {
            return new InExpression<>(value, toOperatorRange(null, expression));
        }
    }

    public Expression<T, C> toInstanceOf(Expression<T, C> expression, TypeExpression<T, C> typeExpresion) {
        return new InstanceOfExpression<>(expression, typeExpresion);
    }

    public Expression<T, C> toPathExpression(Expression<T, C> source, String member) {
        return new PathExpression<>(source, member);
    }

    public Expression<T, C> toPathExpression(List<String> names) {
        Expression<T, C> source = toName(names.get(0));
        for(int i = 1; i < names.size(); i++) {
            source = toPathExpression(source, names.get(i));
        }
        return source;
    }

    public Expression<T, C> toFilterExpression(Expression<T, C> value, Expression<T, C> filter) {
        return new FilterExpression<>(value, filter);
    }

    public Expression<T, C> toFunctionInvocation(Expression<T, C> function, Parameters<T, C> parameters) {
        if (isDateTimeLiteral(function, parameters)) {
            String functionName;
            if (function instanceof Name) {
                functionName = ((Name<T, C>) function).getName();
            } else {
                functionName = ((QualifiedName<T, C>) function).getQualifiedName();
            }
            StringLiteral<T, C> stringLiteral = (StringLiteral<T, C>) ((PositionalParameters<T, C>) parameters).getParameters().get(0);
            return new DateTimeLiteral<>(functionName, stringLiteral.getLexeme());
        } else {
            return new FunctionInvocation<>(function, parameters);
        }
    }

    private boolean isDateTimeLiteral(Expression<T, C> function, Parameters<T, C> parameters) {
        return (
                function instanceof Name && DATE_TIME_LITERAL_NAMES.contains(((Name<T, C>) function).getName())
                ||
                function instanceof QualifiedName && DATE_TIME_LITERAL_NAMES.contains(((QualifiedName<T, C>) function).getQualifiedName())
               )
               && parameters instanceof PositionalParameters
               && ((PositionalParameters<T, C>) parameters).getParameters().size() == 1
               && ((PositionalParameters<T, C>) parameters).getParameters().get(0) instanceof StringLiteral;
    }

    public NamedParameters<T, C> toNamedParameters(Map<String, Expression<T, C>> params) {
        return new NamedParameters<>(params);
    }

    public PositionalParameters<T, C> toPositionalParameters(List<Expression<T, C>> params) {
        return new PositionalParameters<>(params);
    }

    public TypeExpression<T, C> toNamedTypeExpression(String qualifiedName) {
        return new NamedTypeExpression<>(qualifiedName);
    }

    public TypeExpression<T, C> toNamedTypeExpression(Expression<T, C> exp) {
        if (exp instanceof Name) {
            return toNamedTypeExpression(((Name<T, C>) exp).getName());
        } else if (exp instanceof QualifiedName) {
            return toNamedTypeExpression(((QualifiedName<T, C>) exp).getQualifiedName());
        } else if (exp instanceof PathExpression) {
            return toNamedTypeExpression(((PathExpression<T, C>) exp).getPath());
        } else {
            throw new UnsupportedOperationException("Not supported" + exp.toString());
        }
    }

    public TypeExpression<T, C> toListTypeExpression(TypeExpression<T, C> elementType) {
        return new ListTypeExpression<>(elementType);
    }

    public TypeExpression<T, C> toContextTypeExpression(List<Pair<String, TypeExpression<T, C>>> members) {
        return new ContextTypeExpression<>(members);
    }

    public TypeExpression<T, C> toFunctionTypeExpression(List<TypeExpression<T, C>> parameters, TypeExpression<T, C> returnType) {
        return new FunctionTypeExpression<>(parameters, returnType);
    }
}
