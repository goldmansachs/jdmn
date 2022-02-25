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

import static com.gs.dmn.feel.analysis.semantics.type.FEELTypes.FEEL_LITERAL_DATE_TIME_NAMES;

public class ASTFactory<C> {
    //
    // Expressions
    //
    public Expression<C> toExpressionList(List<Expression<C>> expressionList) {
        if (expressionList.size() == 1) {
            return expressionList.get(0);
        } else {
            return new ExpressionList<>(expressionList);
        }
    }

    public Expression<C> toComparison(String operator, Expression<C> leftOperand, Expression<C> rightOperand) {
        return new Relational<>(operator, leftOperand, rightOperand);
    }

    public Expression<C> toMultiplication(String operator, Expression<C> leftOperand, Expression<C> rightOperand) {
        return new Multiplication<>(operator, leftOperand, rightOperand);
    }

    public Expression<C> toAddition(String operator, Expression<C> leftOperand, Expression<C> rightOperand) {
        return new Addition<>(operator, leftOperand, rightOperand);
    }

    public Expression<C> toExponentiation(Expression<C> leftOperand, Expression<C> rightOperand) {
        return new Exponentiation<>(leftOperand, rightOperand);
    }

    public Expression<C> toNegation(List<String> kindList, Expression<C> operand) {
        kindList = optimizeDoubleNegation(kindList);
        Expression<C> result = operand;
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

    public Expression<C> toNegation(String kind, Expression<C> operand) {
        if ("-".equals(kind)) {
            return new ArithmeticNegation<>(operand);
        } else if ("not".equals(kind)) {
            return new LogicNegation<>(operand);
        } else {
            throw new DMNRuntimeException(String.format("Unknown unary operator '%s'", kind));
        }
    }

    public NumericLiteral<C> toNumericLiteral(String lexeme) {
        return new NumericLiteral<>(lexeme);
    }

    public StringLiteral<C> toStringLiteral(String lexeme) {
        return new StringLiteral<>(lexeme);
    }

    public BooleanLiteral<C> toBooleanLiteral(String lexeme) {
        return new BooleanLiteral<>(lexeme);
    }

    public Expression<C> toDateTimeLiteral(String lexeme) {
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

    public Expression<C> toDateTimeLiteral(String kind, Expression<C> stringLiteral) {
        return toFunctionInvocation(toName(kind), new PositionalParameters<>(Collections.singletonList(stringLiteral)));
    }

    public Expression<C> toDateTimeLiteral(String kind, String lexeme) {
        return new DateTimeLiteral<>(kind, lexeme);
    }

    public Expression<C> toName(String name) {
        return new Name<>(name);
    }

    public Expression<C> toQualifiedName(String... names) {
        return toQualifiedName(Arrays.asList(names));
    }

    public Expression<C> toQualifiedName(List<String> names) {
        if (names.size() > 0) {
            return toPathExpression(names);
        } else {
            throw new IllegalArgumentException(String.format("Illegal qualified name '%s'", names));
        }
    }

    //
    // Tests
    //
    public UnaryTests<C> toAny() {
        return new Any<>();
    }

    public PositiveUnaryTest<C> toPositiveUnaryTest(Expression<C> expression) {
        if (expression instanceof SimplePositiveUnaryTest) {
            return (PositiveUnaryTest<C>) expression;
        } else if (expression instanceof NullLiteral) {
            return toNullPositiveUnaryTest();
        } else if (expression instanceof SimpleLiteral) {
            return toOperatorRange(null, expression);
        } else if (expression instanceof ArithmeticNegation && ((ArithmeticNegation<C>) expression).getLeftOperand() instanceof NumericLiteral) {
            return toOperatorRange(null, expression);
        } else if (expression instanceof NamedExpression || expression instanceof PathExpression) {
            return toOperatorRange(null, expression);
        } else if (expression instanceof Context) {
            return toOperatorRange(null, expression);
        } else if (expression instanceof FunctionInvocation) {
            // TODO refactor to use ExpressionTest
            return toOperatorRange(null, expression);
        } else if (expression instanceof ListLiteral) {
            return toListTest((ListLiteral<C>) expression);
        } else {
            return toExpressionTest(expression);
        }
    }

    public NullTest<C> toNullPositiveUnaryTest() {
        return new NullTest<>();
    }

    public ExpressionTest<C> toExpressionTest(Expression<C> expression) {
        return new ExpressionTest<>(expression);
    }

    public OperatorRange<C> toOperatorRange(String operator, Expression<C> endpoint) {
        return new OperatorRange<>(operator, endpoint);
    }

    public EndpointsRange<C> toEndpointsRange(String leftPar, Expression<C> start, String rightPar, Expression<C> end) {
        return new EndpointsRange<>(!"[".equals(leftPar), start, !"]".equals(rightPar), end);
    }

    public ListTest<C> toListTest(ListLiteral<C> expression) {
        return new ListTest<>(expression);
    }

    public ListTest<C> toListTest(List<Expression<C>> expressions) {
        return new ListTest<>((ListLiteral<C>) toListLiteral(expressions));
    }

    public UnaryTests<C> toNegatedUnaryTests(PositiveUnaryTests<C> ast) {
        return new NegatedPositiveUnaryTests<>(ast);
    }

    public PositiveUnaryTests<C> toPositiveUnaryTests(List<Expression<C>> expressions) {
        List<PositiveUnaryTest<C>> positiveUnaryTests = new ArrayList<>();
        for (Expression<C> e : expressions) {
            if (e instanceof PositiveUnaryTest) {
                positiveUnaryTests.add((PositiveUnaryTest<C>) e);
            } else {
                positiveUnaryTests.add(toPositiveUnaryTest(e));
            }
        }
        return new PositiveUnaryTests<>(positiveUnaryTests);
    }

    public FormalParameter<C> toFormalParameter(String parameterName, TypeExpression<C> typeExpression) {
        return new FormalParameter<>(parameterName, typeExpression);
    }

    public Expression<C> toFunctionDefinition(List<FormalParameter<C>> formalParameters, TypeExpression<C> returnTypeExpression, Expression<C> body, boolean external) {
        return new FunctionDefinition<>(formalParameters, returnTypeExpression, body, external);
    }

    public Iterator<C> toIterator(String name, Expression<C> domain) {
        return new Iterator<>(name, toIteratorDomain(domain, null));
    }

    public Iterator<C> toIterator(String name, IteratorDomain<C> domain) {
        return new Iterator<>(name, domain);
    }

    public IteratorDomain<C> toIteratorDomain(Expression<C> start, Expression<C> end) {
        if (end == null) {
            return new ExpressionIteratorDomain<>(start);
        } else {
            return new RangeIteratorDomain<>(start, end);
        }
    }

    public Expression<C> toContext(List<ContextEntry<C>> entries) {
        return new Context<>(entries);
    }

    public ContextEntryKey<C> toContextEntryKey(String text) {
        return new ContextEntryKey<>(text);
    }

    public ContextEntry<C> toContextEntry(ContextEntryKey<C> key, Expression<C> expression) {
        return new ContextEntry<>(key, expression);
    }

    public Expression<C> toListLiteral(List<Expression<C>> expressions) {
        return new ListLiteral<>(expressions);
    }

    public Expression<C> toNullLiteral() {
        return new NullLiteral<>();
    }

    public Expression<C> toForExpression(List<Iterator<C>> iterators, Expression<C> body) {
        return new ForExpression<>(iterators, body);
    }

    public IfExpression<C> toIfExpression(Expression<C> condition, Expression<C> thenExpression, Expression<C> elseExpression) {
        return new IfExpression<>(condition, thenExpression, elseExpression);
    }

    public Expression<C> toQuantifiedExpression(String predicate, List<Iterator<C>> iterators, Expression<C> body) {
        return new QuantifiedExpression<>(predicate, iterators, body);
    }

    public Expression<C> toDisjunction(Expression<C> left, Expression<C> right) {
        return new Disjunction<>(left, right);
    }

    public Expression<C> toConjunction(Expression<C> left, Expression<C> right) {
        return new Conjunction<>(left, right);
    }

    public Expression<C> toBetweenExpression(Expression<C> value, Expression<C> leftEndpoint, Expression<C> rightEndpoint) {
        return new BetweenExpression<>(value, leftEndpoint, rightEndpoint);
    }

    public Expression<C> toInExpression(Expression<C> value, Expression<C> expression) {
        if (expression instanceof PositiveUnaryTest) {
            return new InExpression<>(value, (PositiveUnaryTest<C>) expression);
        } else if (expression instanceof PositiveUnaryTests) {
            return new InExpression<>(value, (PositiveUnaryTests<C>) expression);
        } else {
            return new InExpression<>(value, toOperatorRange(null, expression));
        }
    }

    public Expression<C> toInstanceOf(Expression<C> expression, TypeExpression<C> typeExpresion) {
        return new InstanceOfExpression<>(expression, typeExpresion);
    }

    public Expression<C> toPathExpression(Expression<C> source, String member) {
        return new PathExpression<>(source, member);
    }

    public Expression<C> toPathExpression(List<String> names) {
        Expression<C> source = toName(names.get(0));
        for(int i = 1; i < names.size(); i++) {
            source = toPathExpression(source, names.get(i));
        }
        return source;
    }

    public Expression<C> toFilterExpression(Expression<C> value, Expression<C> filter) {
        return new FilterExpression<>(value, filter);
    }

    public Expression<C> toFunctionInvocation(Expression<C> function, Parameters<C> parameters) {
        if (isDateTimeLiteral(function, parameters)) {
            String functionName;
            if (function instanceof Name) {
                functionName = ((Name<C>) function).getName();
            } else {
                functionName = ((QualifiedName<C>) function).getQualifiedName();
            }
            StringLiteral<C> stringLiteral = (StringLiteral<C>) ((PositionalParameters<C>) parameters).getParameters().get(0);
            return new DateTimeLiteral<>(functionName, stringLiteral.getLexeme());
        } else {
            return new FunctionInvocation<>(function, parameters);
        }
    }

    private boolean isDateTimeLiteral(Expression<C> function, Parameters<C> parameters) {
        return (
                function instanceof Name && FEEL_LITERAL_DATE_TIME_NAMES.contains(((Name<C>) function).getName())
                        ||
                        function instanceof QualifiedName && FEEL_LITERAL_DATE_TIME_NAMES.contains(((QualifiedName<C>) function).getQualifiedName())
        )
                && parameters instanceof PositionalParameters
                && ((PositionalParameters<C>) parameters).getParameters().size() == 1
                && ((PositionalParameters<C>) parameters).getParameters().get(0) instanceof StringLiteral;
    }

    public NamedParameters<C> toNamedParameters(Map<String, Expression<C>> params) {
        return new NamedParameters<>(params);
    }

    public PositionalParameters<C> toPositionalParameters(List<Expression<C>> params) {
        return new PositionalParameters<>(params);
    }

    public TypeExpression<C> toNamedTypeExpression(String qualifiedName) {
        return new NamedTypeExpression<>(qualifiedName);
    }

    public TypeExpression<C> toNamedTypeExpression(Expression<C> exp) {
        if (exp instanceof Name) {
            return toNamedTypeExpression(((Name<C>) exp).getName());
        } else if (exp instanceof QualifiedName) {
            return toNamedTypeExpression(((QualifiedName<C>) exp).getQualifiedName());
        } else if (exp instanceof PathExpression) {
            return toNamedTypeExpression(((PathExpression<C>) exp).getPath());
        } else {
            throw new UnsupportedOperationException("Not supported" + exp.toString());
        }
    }

    public TypeExpression<C> toListTypeExpression(TypeExpression<C> elementType) {
        return new ListTypeExpression<>(elementType);
    }

    public TypeExpression<C> toContextTypeExpression(List<Pair<String, TypeExpression<C>>> members) {
        return new ContextTypeExpression<>(members);
    }

    public TypeExpression<C> toFunctionTypeExpression(List<TypeExpression<C>> parameters, TypeExpression<C> returnType) {
        return new FunctionTypeExpression<>(parameters, returnType);
    }
}
