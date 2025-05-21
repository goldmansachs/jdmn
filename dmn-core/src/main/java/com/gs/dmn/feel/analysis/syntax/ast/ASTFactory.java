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

import com.gs.dmn.feel.analysis.semantics.SemanticError;
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
import com.gs.dmn.feel.analysis.syntax.ast.visitor.ContainsNameVisitor;
import com.gs.dmn.runtime.Pair;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.gs.dmn.feel.FEELConstants.DATE_TIME_LITERAL_NAMES;

public class ASTFactory<T, C> {
    //
    // Expressions
    //
    public Expression<T> toExpressionList(List<Expression<T>> expressionList) {
        if (expressionList.size() == 1) {
            return expressionList.get(0);
        } else {
            return new ExpressionList<>(expressionList);
        }
    }

    public Expression<T> toComparison(String operator, Expression<T> leftOperand, Expression<T> rightOperand) {
        return new Relational<>(operator, leftOperand, rightOperand);
    }

    public Expression<T> toMultiplication(String operator, Expression<T> leftOperand, Expression<T> rightOperand) {
        return new Multiplication<>(operator, leftOperand, rightOperand);
    }

    public Expression<T> toAddition(String operator, Expression<T> leftOperand, Expression<T> rightOperand) {
        return new Addition<>(operator, leftOperand, rightOperand);
    }

    public Expression<T> toExponentiation(Expression<T> leftOperand, Expression<T> rightOperand) {
        return new Exponentiation<>(leftOperand, rightOperand);
    }

    public Expression<T> toNegation(List<String> kindList, Expression<T> operand) {
        kindList = optimizeDoubleNegation(kindList);
        Expression<T> result = operand;
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

    public Expression<T> toNegation(String kind, Expression<T> operand) {
        if ("-".equals(kind)) {
            return new ArithmeticNegation<>(operand);
        } else if ("not".equals(kind)) {
            return new LogicNegation<>(operand);
        } else {
            throw new SemanticError(String.format("Unknown unary operator '%s'", kind));
        }
    }

    public NumericLiteral<T> toNumericLiteral(String lexeme) {
        return new NumericLiteral<>(lexeme);
    }

    public StringLiteral<T> toStringLiteral(String lexeme) {
        return new StringLiteral<>(lexeme);
    }

    public BooleanLiteral<T> toBooleanLiteral(String lexeme) {
        return new BooleanLiteral<>(lexeme);
    }

    public Expression<T> toDateTimeLiteral(String lexeme) {
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

    public Expression<T> toDateTimeLiteral(String kind, Expression<T> stringLiteral) {
        return toFunctionInvocation(toName(kind), new PositionalParameters<>(Collections.singletonList(stringLiteral)));
    }

    public Expression<T> toDateTimeLiteral(String kind, String lexeme) {
        return new DateTimeLiteral<>(kind, lexeme);
    }

    public Expression<T> toName(String name) {
        return new Name<>(name);
    }

    public Expression<T> toQualifiedName(String... names) {
        return toQualifiedName(Arrays.asList(names));
    }

    public Expression<T> toQualifiedName(List<String> names) {
        if (names.size() > 0) {
            return toPathExpression(names);
        } else {
            throw new SemanticError(String.format("Illegal qualified name '%s'", names));
        }
    }

    //
    // Tests
    //
    public UnaryTests<T> toAny() {
        return new Any<>();
    }

    public PositiveUnaryTest<T> toPositiveUnaryTest(Expression<T> expression) {
        if (expression instanceof PositiveUnaryTest) {
            // since DMN 1.1
            return (PositiveUnaryTest<T>) expression;
        } else if (expression instanceof NullLiteral) {
            // since DMN 1.1
            return toOperatorRange(null, expression);
        } else if (expression instanceof SimpleLiteral) {
            // since DMN 1.1
            return toOperatorRange(null, expression);
        } else if (expression instanceof ListLiteral) {
            // Since DMN 1.1, semantics in DMN 1.2
            // Shallow conversion of list elements to Positive Unary Test
            List<Expression<T>> puts = new ArrayList<>();
            for (Expression<T> listElement: ((ListLiteral<T>) expression).getExpressionList()) {
                if (listElement instanceof ListLiteral) {
                    puts.add(listElement);
                } else {
                    PositiveUnaryTest<T> put = toPositiveUnaryTest(listElement);
                    puts.add(put);
                }
            }
            return toListTest((ListLiteral<T>) toListLiteral(puts));
        } else {
            // Since DMN 1.2
            if (containsQuestionMark(expression)) {
                return toExpressionTest(expression);
            } else {
                return toOperatorRange(null, expression);
            }
        }
    }

    private boolean containsQuestionMark(Expression<T> expression) {
        ContainsNameVisitor<T, C> visitor = new ContainsNameVisitor<>();
        expression.accept(visitor, null);
        return visitor.isFound();
    }

    public ExpressionTest<T> toExpressionTest(Expression<T> expression) {
        return new ExpressionTest<>(expression);
    }

    public OperatorRange<T> toOperatorRange(String operator, Expression<T> endpoint) {
        return new OperatorRange<>(operator, endpoint);
    }

    public EndpointsRange<T> toEndpointsRange(String leftPar, Expression<T> start, String rightPar, Expression<T> end) {
        return new EndpointsRange<>(!"[".equals(leftPar), start, !"]".equals(rightPar), end);
    }

    public ListTest<T> toListTest(ListLiteral<T> expression) {
        return new ListTest<>(expression);
    }

    public ListTest<T> toListTest(List<Expression<T>> expressions) {
        return new ListTest<>((ListLiteral<T>) toListLiteral(expressions));
    }

    public UnaryTests<T> toNegatedUnaryTests(PositiveUnaryTests<T> ast) {
        return new NegatedPositiveUnaryTests<>(ast);
    }

    public PositiveUnaryTests<T> toPositiveUnaryTests(List<Expression<T>> expressions) {
        List<PositiveUnaryTest<T>> positiveUnaryTests = new ArrayList<>();
        for (Expression<T> e : expressions) {
            if (e instanceof PositiveUnaryTest) {
                positiveUnaryTests.add((PositiveUnaryTest<T>) e);
            } else {
                positiveUnaryTests.add(toPositiveUnaryTest(e));
            }
        }
        return new PositiveUnaryTests<>(positiveUnaryTests);
    }

    public FormalParameter<T> toFormalParameter(String parameterName, TypeExpression<T> typeExpression) {
        return new FormalParameter<>(parameterName, typeExpression);
    }

    public Expression<T> toFunctionDefinition(List<FormalParameter<T>> formalParameters, TypeExpression<T> returnTypeExpression, Expression<T> body, boolean external) {
        return new FunctionDefinition<>(formalParameters, returnTypeExpression, body, external);
    }

    public Iterator<T> toIterator(String name, Expression<T> domain) {
        return new Iterator<>(name, toIteratorDomain(domain, null));
    }

    public Iterator<T> toIterator(String name, IteratorDomain<T> domain) {
        return new Iterator<>(name, domain);
    }

    public IteratorDomain<T> toIteratorDomain(Expression<T> start, Expression<T> end) {
        if (end == null) {
            return new ExpressionIteratorDomain<>(start);
        } else {
            return new RangeIteratorDomain<>(start, end);
        }
    }

    public Expression<T> toContext(List<ContextEntry<T>> entries) {
        return new Context<>(entries);
    }

    public ContextEntryKey<T> toContextEntryKey(String text) {
        return new ContextEntryKey<>(text);
    }

    public ContextEntry<T> toContextEntry(ContextEntryKey<T> key, Expression<T> expression) {
        return new ContextEntry<>(key, expression);
    }

    public Expression<T> toListLiteral(List<Expression<T>> expressions) {
        return new ListLiteral<>(expressions);
    }

    public Expression<T> toNullLiteral() {
        return new NullLiteral<>();
    }

    public Expression<T> toForExpression(List<Iterator<T>> iterators, Expression<T> body) {
        return new ForExpression<>(iterators, body);
    }

    public IfExpression<T> toIfExpression(Expression<T> condition, Expression<T> thenExpression, Expression<T> elseExpression) {
        return new IfExpression<>(condition, thenExpression, elseExpression);
    }

    public Expression<T> toQuantifiedExpression(String predicate, List<Iterator<T>> iterators, Expression<T> body) {
        return new QuantifiedExpression<>(predicate, iterators, body);
    }

    public Expression<T> toDisjunction(Expression<T> left, Expression<T> right) {
        return new Disjunction<>(left, right);
    }

    public Expression<T> toConjunction(Expression<T> left, Expression<T> right) {
        return new Conjunction<>(left, right);
    }

    public Expression<T> toBetweenExpression(Expression<T> value, Expression<T> leftEndpoint, Expression<T> rightEndpoint) {
        return new BetweenExpression<>(value, leftEndpoint, rightEndpoint);
    }

    public Expression<T> toInExpression(Expression<T> value, Expression<T> expression) {
        if (expression instanceof PositiveUnaryTest) {
            return new InExpression<>(value, (PositiveUnaryTest<T>) expression);
        } else if (expression instanceof PositiveUnaryTests) {
            return new InExpression<>(value, (PositiveUnaryTests<T>) expression);
        } else {
            return new InExpression<>(value, toOperatorRange(null, expression));
        }
    }

    public Expression<T> toInstanceOf(Expression<T> expression, TypeExpression<T> typeExpresion) {
        return new InstanceOfExpression<>(expression, typeExpresion);
    }

    public Expression<T> toPathExpression(Expression<T> source, String member) {
        return new PathExpression<>(source, member);
    }

    public Expression<T> toPathExpression(List<String> names) {
        Expression<T> source = toName(names.get(0));
        for(int i = 1; i < names.size(); i++) {
            source = toPathExpression(source, names.get(i));
        }
        return source;
    }

    public Expression<T> toFilterExpression(Expression<T> value, Expression<T> filter) {
        return new FilterExpression<>(value, filter);
    }

    public Expression<T> toFunctionInvocation(Expression<T> function, Parameters<T> parameters) {
        if (isDateTimeLiteral(function, parameters)) {
            String functionName;
            if (function instanceof Name) {
                functionName = ((Name<T>) function).getName();
            } else {
                functionName = ((QualifiedName<T>) function).getQualifiedName();
            }
            StringLiteral<T> stringLiteral = (StringLiteral<T>) ((PositionalParameters<T>) parameters).getParameters().get(0);
            return new DateTimeLiteral<>(functionName, stringLiteral.getLexeme());
        } else {
            return new FunctionInvocation<>(function, parameters);
        }
    }

    private boolean isDateTimeLiteral(Expression<T> function, Parameters<T> parameters) {
        return (
                function instanceof Name && DATE_TIME_LITERAL_NAMES.contains(((Name<T>) function).getName())
                ||
                function instanceof QualifiedName && DATE_TIME_LITERAL_NAMES.contains(((QualifiedName<T>) function).getQualifiedName())
               )
               && parameters instanceof PositionalParameters
               && ((PositionalParameters<T>) parameters).getParameters().size() == 1
               && ((PositionalParameters<T>) parameters).getParameters().get(0) instanceof StringLiteral;
    }

    public NamedParameters<T> toNamedParameters(Map<String, Expression<T>> params) {
        return new NamedParameters<>(params);
    }

    public PositionalParameters<T> toPositionalParameters(List<Expression<T>> params) {
        return new PositionalParameters<>(params);
    }

    public TypeExpression<T> toNamedTypeExpression(String qualifiedName) {
        return new NamedTypeExpression<>(qualifiedName);
    }

    public TypeExpression<T> toNamedTypeExpression(Expression<T> exp) {
        if (exp instanceof Name) {
            return toNamedTypeExpression(((Name<T>) exp).getName());
        } else if (exp instanceof QualifiedName) {
            return toNamedTypeExpression(((QualifiedName<T>) exp).getQualifiedName());
        } else if (exp instanceof PathExpression) {
            return toNamedTypeExpression(((PathExpression<T>) exp).getPath());
        } else {
            throw new SemanticError("Not supported" + exp.toString());
        }
    }

    public TypeExpression<T> toContextTypeExpression(List<Pair<String, TypeExpression<T>>> members) {
        return new ContextTypeExpression<>(members);
    }

    public TypeExpression<T> toRangeTypeExpression(TypeExpression<T> elementType) {
        return new RangeTypeExpression<>(elementType);
    }

    public TypeExpression<T> toFunctionTypeExpression(List<TypeExpression<T>> parameters, TypeExpression<T> returnType) {
        return new FunctionTypeExpression<>(parameters, returnType);
    }

    public TypeExpression<T> toListTypeExpression(TypeExpression<T> elementType) {
        return new ListTypeExpression<>(elementType);
    }

    public TypeExpression<T> toTypeExpression(String typeName, TypeExpression<T> elementType) {
        if ("range".equals(typeName)) {
            return new RangeTypeExpression<>(elementType);
        } else if ("list".equals(typeName)) {
            return new ListTypeExpression<>(elementType);
        } else {
            throw new SemanticError(String.format("Not supported type '%s'", typeName));
        }
    }

}
