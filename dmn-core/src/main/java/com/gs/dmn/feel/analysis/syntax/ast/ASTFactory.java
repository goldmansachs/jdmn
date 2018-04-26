/**
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
import com.gs.dmn.feel.analysis.syntax.ast.test.*;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.gs.dmn.feel.analysis.semantics.type.FEELTypes.FEEL_LITERAL_DATE_TIME_NAMES;

public class ASTFactory {
    public ASTFactory() {
    }

    //
    // Expressions
    //
    public Expression toExpressionList(List<Expression> expressionList) {
        if (expressionList.size() == 1) {
            return expressionList.get(0);
        } else {
            return new ExpressionList(expressionList);
        }
    }

    public Expression toComparison(String operator, Expression leftOperand, Expression rightOperand) {
        return new Relational(operator, leftOperand, rightOperand);
    }

    public Expression toMultiplication(String operator, Expression leftOperand, Expression rightOperand) {
        return new Multiplication(operator, leftOperand, rightOperand);
    }

    public Expression toAddition(String operator, Expression leftOperand, Expression rightOperand) {
        return new Addition(operator, leftOperand, rightOperand);
    }

    public Expression toExponentiation(Expression leftOperand, Expression rightOperand) {
        return new Exponentiation(leftOperand, rightOperand);
    }

    public Expression toNegation(List<String> kindList, Expression operand) {
        kindList = optimizeDoubleNegation(kindList);
        Expression result = operand;
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
                    ArrayList arrayList = new ArrayList();
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

    public Expression toNegation(String kind, Expression operand) {
        if ("-".equals(kind)) {
            return new ArithmeticNegation(operand);
        } else if ("not".equals(kind)) {
            return new LogicNegation(operand);
        } else {
            throw new DMNRuntimeException(String.format("Unknown unary operator '%s'", kind));
        }
    }

    public NumericLiteral toNumericLiteral(String lexeme) {
        return new NumericLiteral(lexeme);
    }

    public StringLiteral toStringLiteral(String lexeme) {
        return new StringLiteral(lexeme);
    }

    public BooleanLiteral toBooleanLiteral(String lexeme) {
        return new BooleanLiteral(lexeme);
    }

    public Expression toDateTimeLiteral(String kind, Expression stringLiteral) {
        return toFunctionInvocation(toName(kind), new PositionalParameters(Arrays.asList(stringLiteral)));
    }

    public Expression toDateTimeLiteral(String kind, String lexeme) {
        return new DateTimeLiteral(kind, lexeme);
    }

    public Expression toName(String name) {
        return new Name(name);
    }

    public Expression toQualifiedName(String... names) {
        return toQualifiedName(Arrays.asList(names));
    }

    public Expression toQualifiedName(List<String> names) {
        if (names.size() > 0) {
            return toPathExpression(names);
        } else {
            throw new IllegalArgumentException(String.format("Illegal qualified name '%s'", names));
        }
    }

    //
    // Tests
    //
    public SimpleUnaryTests toAny() {
        return new Any();
    }


    public SimpleUnaryTests toNegatedSimpleUnaryTests(SimplePositiveUnaryTests ast) {
        return new NegatedSimplePositiveUnaryTests(ast);
    }

    public SimplePositiveUnaryTests toSimplePositiveUnaryTests(List<Expression> expressions) {
        List<SimplePositiveUnaryTest> tests = new ArrayList<>();
        expressions.forEach(expression -> {
            if (expression instanceof SimplePositiveUnaryTest) {
                tests.add((SimplePositiveUnaryTest) expression);
            } else {
                tests.add(toOperatorTest(null, expression));
            }
        });
        return new SimplePositiveUnaryTests(tests);
    }

    public PositiveUnaryTest toPositiveUnaryTest(Expression expression) {
        if (expression instanceof SimplePositiveUnaryTest) {
            return (PositiveUnaryTest) expression;
        } else if (expression instanceof NullLiteral) {
            return toNullPositiveUnaryTest();
        } else if (expression instanceof SimpleLiteral) {
            return toOperatorTest(null, expression);
        } else if (expression instanceof ArithmeticNegation && ((ArithmeticNegation) expression).getLeftOperand() instanceof NumericLiteral) {
            return toOperatorTest(null, expression);
        } else if (expression instanceof NamedExpression || expression instanceof PathExpression) {
            return toOperatorTest(null, expression);
        } else if (expression instanceof FunctionInvocation) {
            // TODO refactor to use ExpressionTest
            return toOperatorTest(null, expression);
        } else if (expression instanceof ListLiteral) {
            return toListTest((ListLiteral) expression);
        } else {
            return toExpressionTest(expression);
        }
    }

    public NullTest toNullPositiveUnaryTest() {
        return new NullTest();
    }

    public ExpressionTest toExpressionTest(Expression expression) {
        return new ExpressionTest(expression);
    }

    public OperatorTest toOperatorTest(String operator, Expression endpoint) {
        return new OperatorTest(operator, endpoint);
    }

    public RangeTest toIntervalTest(String leftPar, Expression start, String rightPar, Expression end) {
        return new RangeTest(!"[".equals(leftPar), start, !"]".equals(rightPar), end);
    }

    public ListTest toListTest(ListLiteral expression) {
        return new ListTest(expression);
    }

    public ListTest toListTest(List<Expression> expressions) {
        return new ListTest((ListLiteral) toListLiteral(expressions));
    }

    public UnaryTests toNegatedUnaryTests(PositiveUnaryTests ast) {
        return new NegatedPositiveUnaryTests(ast);
    }

    public PositiveUnaryTests toPositiveUnaryTests(List<Expression> expressions) {
        List<PositiveUnaryTest> positiveUnaryTests = new ArrayList<>();
        for (Expression e : expressions) {
            if (e instanceof PositiveUnaryTest) {
                positiveUnaryTests.add((PositiveUnaryTest) e);
            } else {
                positiveUnaryTests.add(toPositiveUnaryTest(e));
            }
        }
        return new PositiveUnaryTests(positiveUnaryTests);
    }

    public FormalParameter toFormalParameter(String parameterName, String typeName) {
        return new FormalParameter(parameterName, typeName);
    }

    public Expression toFunctionDefinition(List<FormalParameter> formalParameters, Expression body, boolean external) {
        return new FunctionDefinition(formalParameters, body, external);
    }

    public Iterator toIterator(String name, Expression domain) {
        return new Iterator(name, toIteratorDomain(domain, null));
    }

    public Iterator toIterator(String name, IteratorDomain domain) {
        return new Iterator(name, domain);
    }

    public IteratorDomain toIteratorDomain(Expression start, Expression end) {
        if (end == null) {
            return new ExpressionIteratorDomain(start);
        } else {
            return new RangeIteratorDomain(start, end);
        }
    }

    public Expression toContext(List<ContextEntry> entries) {
        return new Context(entries);
    }

    public ContextEntryKey toContextEntryKey(String text) {
        return new ContextEntryKey(text);
    }

    public ContextEntry toContextEntry(ContextEntryKey key, Expression expression) {
        return new ContextEntry(key, expression);
    }

    public Expression toListLiteral(List<Expression> expressions) {
        return new ListLiteral(expressions);
    }

    public Expression toNullLiteral() {
        return new NullLiteral();
    }

    public Expression toForExpression(List<Iterator> iterators, Expression body) {
        return new ForExpression(iterators, body);
    }

    public IfExpression toIfExpression(Expression condition, Expression thenExpression, Expression elseExpression) {
        return new IfExpression(condition, thenExpression, elseExpression);
    }

    public Expression toQuantifiedExpression(String predicate, List<Iterator> iterators, Expression body) {
        return new QuantifiedExpression(predicate, iterators, body);
    }

    public Expression toDisjunction(Expression left, Expression right) {
        return new Disjunction(left, right);
    }

    public Expression toConjunction(Expression left, Expression right) {
        return new Conjunction(left, right);
    }

    public Expression toBetweenExpression(Expression value, Expression leftEndpoint, Expression rightEndpoint) {
        return new BetweenExpression(value, leftEndpoint, rightEndpoint);
    }

    public Expression toInExpression(Expression value, Expression expression) {
        if (expression instanceof PositiveUnaryTest) {
            return new InExpression(value, (PositiveUnaryTest) expression);
        } else if (expression instanceof PositiveUnaryTests) {
            return new InExpression(value, (PositiveUnaryTests) expression);
        } else {
            return new InExpression(value, toOperatorTest(null, expression));
        }
    }

    public Expression toInstanceOf(Expression value, Expression qName) {
        if (qName instanceof QualifiedName) {
            return new InstanceOfExpression(value, (QualifiedName) qName);
        } else if (qName instanceof Name) {
            String name = ((Name) qName).getName();
            return new InstanceOfExpression(value, new QualifiedName(Arrays.asList(name)));
        } else {
            throw new DMNRuntimeException(String.format("Not supported qName '%s'", qName));
        }
    }

    public Expression toPathExpression(Expression source, String member) {
        return new PathExpression(source, member);
    }

    public Expression toPathExpression(List<String> names) {
        Expression source = toName(names.get(0));
        for(int i = 1; i<names.size(); i++) {
            source = toPathExpression(source, names.get(i));
        }
        return source;
    }

    public Expression toFilterExpression(Expression value, Expression filter) {
        return new FilterExpression(value, filter);
    }

    public Expression toFunctionInvocation(Expression function, Parameters parameters) {
        if (isDateTimeLiteral(function, parameters)) {
            String functionName = null;
            if (function instanceof Name) {
                functionName = ((Name) function).getName();
            } else {
                functionName = ((QualifiedName) function).getQualifiedName();
            }
            StringLiteral stringLiteral = (StringLiteral) ((PositionalParameters) parameters).getParameters().get(0);
            return new DateTimeLiteral(functionName, stringLiteral.getValue());
        } else {
            return new FunctionInvocation(function, parameters);
        }
    }

    private boolean isDateTimeLiteral(Expression function, Parameters parameters) {
        return (
                function instanceof Name && FEEL_LITERAL_DATE_TIME_NAMES.contains(((Name) function).getName())
                        ||
                        function instanceof QualifiedName && FEEL_LITERAL_DATE_TIME_NAMES.contains(((QualifiedName) function).getQualifiedName())
        )
                && parameters instanceof PositionalParameters
                && ((PositionalParameters) parameters).getParameters().size() == 1
                && ((PositionalParameters) parameters).getParameters().get(0) instanceof StringLiteral;
    }

    public NamedParameters toNamedParameters(Map<String, Expression> params) {
        return new NamedParameters(params);
    }

    public PositionalParameters toPositionalParameters(List<Expression> params) {
        return new PositionalParameters(params);
    }
}
