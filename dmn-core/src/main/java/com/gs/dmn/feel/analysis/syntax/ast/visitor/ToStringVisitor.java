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
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ToStringVisitor<T, C> extends AbstractVisitor<T, C, String> {
    private static boolean lowerPrecedence(Expression<?> childNode, Expression<?> parentNode) {
        String prec1 = PRECDEDENCE.get(childNode.getClass());
        String prec2 = PRECDEDENCE.get(parentNode.getClass());
        return (prec1 != null && prec2 != null) ? prec1.compareTo(prec2) < 0 : false;
    }

    private static boolean sameOperator(Class<?> exp1, Class<?> exp2) {
        return exp1 == exp2;
    }

    // Precedence is the rule + alternative in grammar
    private static final Map<Class<?>, String> PRECDEDENCE = new LinkedHashMap<>();
    static {
        // Textual expressions
        PRECDEDENCE.put(ForExpression.class, "2a");
        PRECDEDENCE.put(IfExpression.class, "2a");
        PRECDEDENCE.put(QuantifiedExpression.class, "2a");

        // Logic
        PRECDEDENCE.put(Disjunction.class, "2b");
        PRECDEDENCE.put(Conjunction.class, "2c");

        // Comparison
        PRECDEDENCE.put(Relational.class, "2d49a");
        PRECDEDENCE.put(BetweenExpression.class, "2d49b");
        PRECDEDENCE.put(InExpression.class, "2d49c");

        // Arithmetic expressions
        PRECDEDENCE.put(Addition.class, "2e4a");
        PRECDEDENCE.put(Multiplication.class, "2e4b");
        PRECDEDENCE.put(Exponentiation.class, "2e4c");
        PRECDEDENCE.put(ArithmeticNegation.class, "2e4d");

        // Instance of
        PRECDEDENCE.put(InstanceOfExpression.class, "2f");

        // Postfix expression
        PRECDEDENCE.put(PathExpression.class, "2g");
        PRECDEDENCE.put(FilterExpression.class, "2g");
        PRECDEDENCE.put(FunctionInvocation.class, "2g");

        // Literals expressions
        PRECDEDENCE.put(NumericLiteral.class, "2h");
        PRECDEDENCE.put(StringLiteral.class, "2h");
        PRECDEDENCE.put(BooleanLiteral.class, "2h");
        PRECDEDENCE.put(DateTimeLiteral.class, "2h");
        PRECDEDENCE.put(NullLiteral.class, "2h");
        PRECDEDENCE.put(ListLiteral.class, "2h");

        // Boxed expressions
        PRECDEDENCE.put(FunctionDefinition.class, "2h");
        PRECDEDENCE.put(Context.class, "2h");
        PRECDEDENCE.put(ListTest.class, "2h");
        PRECDEDENCE.put(EndpointsRange.class, "2h");
        // Force parenthesis when is inner node
        PRECDEDENCE.put(OperatorRange.class, "1");
    }

    public ToStringVisitor(ErrorHandler errorHandler) {
        super(errorHandler);
    }

    //
    // Libraries
    //
    @Override
    public String visit(Library<T> element, C context) {
        if (element == null) {
            return null;
        }

        element.getFunctions().forEach(function -> function.accept(this, context));
        throw new DMNRuntimeException("Not supported");
    }

    @Override
    public String visit(FunctionDeclaration<T> element, C context) {
        if (element == null) {
            return null;
        }

        element.getFormalParameters().forEach(p -> p.accept(this, context));
        if (element.getReturnTypeExpression() != null) {
            element.getReturnTypeExpression().accept(this, context);
        }
        throw new DMNRuntimeException("Not supported");
    }

    //
    // Tests
    //
    @Override
    public String visit(PositiveUnaryTests<T> element, C context) {
        if (element == null) {
            return null;
        }
        
        return element.getPositiveUnaryTests().stream().map(ut -> ut.accept(this, context)).collect(Collectors.joining(", "));
    }

    @Override
    public String visit(NegatedPositiveUnaryTests<T> element, C context) {
        if (element == null) {
            return null;
        }

        String tests = "";
        PositiveUnaryTests<T> positiveUnaryTests = element.getPositiveUnaryTests();
        if (positiveUnaryTests != null) {
            tests = positiveUnaryTests.accept(this, context);
        }
        return makeNegatedPositiveUnaryTests(tests);
    }

    @Override
    public String visit(Any<T> element, C context) {
        return "-";
    }

    @Override
    public String visit(ExpressionTest<T> element, C context) {
        if (element == null) {
            return null;
        }

        String expressionText = "";
        Expression<T> expression = element.getExpression();
        if (expression != null) {
            expressionText = expression.accept(this, context);
        }
        return expressionText;
    }

    @Override
    public String visit(OperatorRange<T> element, C context) {
        if (element == null) {
            return null;
        }

        String endpointText = "";
        Expression<T> endpoint = element.getEndpoint();
        if (endpoint != null) {
            endpointText = endpoint.accept(this, context);
        }
        return makeOperatorRangeTest(element.getOperator(), endpointText);
    }

    @Override
    public String visit(EndpointsRange<T> element, C context) {
        if (element == null) {
            return null;
        }

        String startEndpoint = "";
        String endEndpoint = "";
        Expression<T> start = element.getStart();
        if (start != null) {
            startEndpoint = start.accept(this, context);
        }
        Expression<T> end = element.getEnd();
        if (end != null) {
            endEndpoint = end.accept(this, context);
        }
        return makeRangeTest(element.isOpenStart(), startEndpoint, element.isOpenEnd(), endEndpoint);
    }

    @Override
    public String visit(ListTest<T> element, C context) {
        if (element == null) {
            return null;
        }

        String listText = "";
        ListLiteral<T> listLiteral = element.getListLiteral();
        if (listLiteral != null) {
            listText = listLiteral.accept(this, context);
        }
        return makeListTest(listText);
    }

    //
    // Textual expressions
    //
    @Override
    public String visit(FunctionDefinition<T> element, C context) {
        if (element == null) {
            return null;
        }

        String bodyText = "";
        Expression<T> body = element.getBody();
        if (body != null) {
            bodyText = body.accept(this, context);
        }
        List<String> parameters = element.getFormalParameters().stream().map(p -> p.accept(this, context)).collect(Collectors.toList());
        String returnText = "";
        TypeExpression<T> returnTypeExpression = element.getReturnTypeExpression();
        if (returnTypeExpression != null) {
            returnText = returnTypeExpression.accept(this, context);
        }
        return makeFunctionDefinition(parameters, bodyText, returnText, element.isExternal());
    }

    @Override
    public String visit(FormalParameter<T> element, C context) {
        if (element == null) {
            return null;
        }

        String typeText = "";
        TypeExpression<T> typeExpression = element.getTypeExpression();
        if (typeExpression != null) {
            typeText = typeExpression.accept(this, context);
        }
        return makeFormalParameter(element.getName(), typeText);
    }

    @Override
    public String visit(Context<T> element, C context) {
        if (element == null) {
            return null;
        }

        List<String> entries = element.getEntries().stream().map(ce -> ce.accept(this, context)).collect(Collectors.toList());
        return makeContext(entries);
    }

    @Override
    public String visit(ContextEntry<T> element, C context) {
        if (element == null) {
            return null;
        }

        String keyText = "";
        ContextEntryKey<T> key = element.getKey();
        if (key != null) {
            keyText = key.accept(this, context);
        }
        String valueText = "";
        Expression<T> expression = element.getExpression();
        if (expression != null) {
            valueText = expression.accept(this, context);
        }
        return makeContextEntry(keyText, valueText);
    }

    @Override
    public String visit(ContextEntryKey<T> element, C context) {
        if (element == null) {
            return null;
        }

        return element.getKey();
    }

    @Override
    public String visit(ForExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        List<String> iterators = element.getIterators().stream().map(it -> it.accept(this, context)).collect(Collectors.toList());
        String bodyText = "";
        Expression<T> body = element.getBody();
        if (body != null) {
            bodyText = body.accept(this, context);
        }
        return makeForExpression(iterators, bodyText);
    }

    @Override
    public String visit(Iterator<T> element, C context) {
        if (element == null) {
            return null;
        }

        String domainText = "";
        IteratorDomain<T> domain = element.getDomain();
        if (domain != null) {
            domainText = domain.accept(this, context);
        }
        return makeIterator(element.getName(), domainText);
    }

    @Override
    public String visit(ExpressionIteratorDomain<T> element, C context) {
        if (element == null) {
            return null;
        }

        String expText = "";
        Expression<T> expression = element.getExpression();
        if (expression != null) {
            expText = expression.accept(this, context);
        }
        return expText;
    }

    @Override
    public String visit(RangeIteratorDomain<T> element, C context) {
        if (element == null) {
            return null;
        }

        String startText = "";
        Expression<T> start = element.getStart();
        if (start != null) {
            startText = start.accept(this, context);
        }
        String endText = "";
        Expression<T> end = element.getEnd();
        if (end != null) {
            endText = end.accept(this, context);
        }
        return makeRangeDomain(startText, endText);
    }

    @Override
    public String visit(IfExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        String conditionText = "";
        Expression<T> condition = element.getCondition();
        if (condition != null) {
            conditionText = condition.accept(this, context);
        }
        String thenText = "";
        Expression<T> thenExpression = element.getThenExpression();
        if (thenExpression != null) {
            thenText = thenExpression.accept(this, context);
        }
        String elseText = "";
        Expression<T> elseExpression = element.getElseExpression();
        if (elseExpression != null) {
            elseText = elseExpression.accept(this, context);
        }
        return makeIfExpression(conditionText, thenText, elseText);
    }

    @Override
    public String visit(QuantifiedExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        List<String> iterators = element.getIterators().stream().map(it -> it.accept(this, context)).collect(Collectors.toList());
        String bodyText = "";
        Expression<T> body = element.getBody();
        if (body != null) {
            bodyText = body.accept(this, context);
        }
        return makeQuantifiedExpression(element.getPredicate(), iterators, bodyText);
    }

    @Override
    public String visit(FilterExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        String sourceText = "";
        Expression<T> source = element.getSource();
        if (source != null) {
            sourceText = source.accept(this, context);
        }
        String filterText = "";
        Expression<T> filter = element.getFilter();
        if (filter != null) {
            filterText = filter.accept(this, context);
        }
        return makeFilter(sourceText, filterText);
    }

    @Override
    public String visit(InstanceOfExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        String leftOperandText = "";
        Expression<T> leftOperand = element.getLeftOperand();
        if (leftOperand != null) {
            leftOperandText = leftOperand.accept(this, context);
        }
        String rightOperandText = "";
        TypeExpression<T> rightOperand = element.getRightOperand();
        if (rightOperand != null) {
            rightOperandText = rightOperand.accept(this, context);
        }
        return makeInstanceOf(leftOperandText, rightOperandText);
    }

    //
    // Expressions
    //
    @Override
    public String visit(ExpressionList<T> element, C context) {
        if (element == null) {
            return null;
        }

        return element.getExpressionList().stream().map(e -> e.accept(this, context)).collect(Collectors.joining(", "));
    }

    //
    // Logic expressions
    //
    @Override
    public String visit(Conjunction<T> element, C context) {
        if (element == null) {
            return null;
        }

        return makeBinaryExpression("and", element, element.getLeftOperand(), element.getRightOperand(), context);
    }

    @Override
    public String visit(Disjunction<T> element, C context) {
        if (element == null) {
            return null;
        }

        return makeBinaryExpression("or", element, element.getLeftOperand(), element.getRightOperand(), context);
    }

    @Override
    public String visit(LogicNegation<T> element, C context) {
        if (element == null) {
            return null;
        }

        return makeUnaryExpression(element.getOperator(), element, element.getLeftOperand(), context);
    }

    //
    // Comparison expressions
    //
    @Override
    public String visit(Relational<T> element, C context) {
        if (element == null) {
            return null;
        }

        return makeBinaryExpression(element.getOperator(), element, element.getLeftOperand(), element.getRightOperand(), context);
    }

    @Override
    public String visit(BetweenExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        String valueText = "";
        Expression<T> value = element.getValue();
        if (value != null) {
            valueText = value.accept(this, context);
        }
        String leftEndpointText = "";
        Expression<T> leftEndpoint = element.getLeftEndpoint();
        if (leftEndpoint != null) {
            leftEndpointText = leftEndpoint.accept(this, context);
        }
        String rightEndpointText = "";
        Expression<T> rightEndpoint = element.getRightEndpoint();
        if (rightEndpoint != null) {
            rightEndpointText = rightEndpoint.accept(this, context);
        }
        return makeBetweenExpression(valueText, leftEndpointText, rightEndpointText);
    }


    @Override
    public String visit(InExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        String valueText = "";
        Expression<T> value = element.getValue();
        if (value != null) {
            valueText = value.accept(this, context);
        }
        List<String> tests = element.getTests().stream().map(t -> t.accept(this, context)).collect(Collectors.toList());
        return makeInExpression(valueText, tests);
    }

    //
    // Arithmetic expressions
    //
    @Override
    public String visit(Addition<T> element, C context) {
        if (element == null) {
            return null;
        }

        return makeBinaryExpression(element.getOperator(), element, element.getLeftOperand(), element.getRightOperand(), context);
    }

    @Override
    public String visit(Multiplication<T> element, C context) {
        if (element == null) {
            return null;
        }

        return makeBinaryExpression(element.getOperator(), element, element.getLeftOperand(), element.getRightOperand(), context);
    }

    @Override
    public String visit(Exponentiation<T> element, C context) {
        if (element == null) {
            return null;
        }

        return makeBinaryExpression(element.getOperator(), element, element.getLeftOperand(), element.getRightOperand(), context);
    }

    @Override
    public String visit(ArithmeticNegation<T> element, C context) {
        if (element == null) {
            return null;
        }

        return makeUnaryExpression("-", element, element.getLeftOperand(), context);
    }

    //
    // Postfix expressions
    //
    @Override
    public String visit(PathExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        String sourceText = "";
        Expression<T> source = element.getSource();
        boolean requiresParenthsisis = false;
        if (source != null) {
            sourceText = source.accept(this, context);
            requiresParenthsisis = lowerPrecedence(source, element);
        }
        return makePathExpression(sourceText, element.getMember(), requiresParenthsisis);
    }

    @Override
    public String visit(FunctionInvocation<T> element, C context) {
        if (element == null) {
            return null;
        }

        String functionText = "";
        Expression<T> function = element.getFunction();
        if (function != null) {
            functionText = function.accept(this, context);
        }
        String argsText = "";
        Parameters<T> parameters = element.getParameters();
        if (parameters != null) {
            argsText = parameters.accept(this, context);
        }
        return makeFunctionCall(functionText, argsText);
    }

    @Override
    public String visit(NamedParameters<T> element, C context) {
        if (element == null) {
            return null;
        }

        return element.getParameters().entrySet().stream().map(e -> makeNamedParameter(e, context)).collect(Collectors.joining(", "));
    }

    @Override
    public String visit(PositionalParameters<T> element, C context) {
        if (element == null) {
            return "";
        }

        return element.getParameters().stream().map(p -> p.accept(this, context)).collect(Collectors.joining(", "));
    }

    //
    // Primary expressions
    //
    @Override
    public String visit(NumericLiteral<T> element, C context) {
        return makeSimpleLiteral(element);
    }

    @Override
    public String visit(BooleanLiteral<T> element, C context) {
        return makeSimpleLiteral(element);
    }

    @Override
    public String visit(StringLiteral<T> element, C context) {
        return makeSimpleLiteral(element);
    }

    @Override
    public String visit(DateTimeLiteral<T> element, C context) {
        return makeFunctionCall(element.getConversionFunction(), element.getLexeme());
    }

    @Override
    public String visit(NullLiteral<T> element, C context) {
        return "null";
    }

    @Override
    public String visit(ListLiteral<T> element, C context) {
        if (element == null) {
            return null;
        }

        List<String> members = element.getExpressionList().stream().map(e -> e.accept(this, context)).collect(Collectors.toList());
        return makeListLiteral(members);
    }

    @Override
    public String visit(Name<T> element, C context) {
        if (element == null) {
            return null;
        }

        return String.format("%s", element.getName());
    }

    @Override
    public String visit(QualifiedName<T> element, C context) {
        if (element == null) {
            return null;
        }

        throw new DMNRuntimeException("Not supported");
    }

    @Override
    public String visit(NamedTypeExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        return makeNamedTypeExpression(element.getQualifiedName());
    }

    @Override
    public String visit(ListTypeExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        TypeExpression<T> elementTypeExpression = element.getElementTypeExpression();
        String elementType = "";
        if (elementTypeExpression != null) {
            elementType = elementTypeExpression.accept(this, context);
        }
        return makeListTypeExpression(elementType);
    }

    @Override
    public String visit(RangeTypeExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        TypeExpression<T> elementTypeExpression = element.getElementTypeExpression();
        String elementType = "";
        if (elementTypeExpression != null) {
            elementType = elementTypeExpression.accept(this, context);
        }
        return makeRangeTypeExpression(elementType);
    }

    @Override
    public String visit(ContextTypeExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        List<String> members = element.getMembers().stream().map(m -> makeMemberTypeExpression(m, context)).collect(Collectors.toList());
        return makeContextTypeExpression(members);
    }

    @Override
    public String visit(FunctionTypeExpression<T> element, C context) {
        if (element == null) {
            return null;
        }

        List<String> parameters = element.getParameters().stream().map(p -> makeFunctionTypeExpressionParameter(p, context)).collect(Collectors.toList());
        TypeExpression<T> returnTypeExpression = element.getReturnType();
        String returnType = "";
        if (returnTypeExpression != null) {
            returnType = returnTypeExpression.accept(this, context);
        }
        return makeFunctionTypeExpression(parameters, returnType);
    }

    //
    // Serialization method
    //
    //
    // Tests
    //
    private String makeNegatedPositiveUnaryTests(String tests) {
        return String.format("not(%s)", tests);
    }

    private String makeOperatorRangeTest(String operator, String endpoint) {
        if (StringUtils.isBlank(operator)) {
            return endpoint;
        } else {
            return String.format("%s %s", operator, endpoint);
        }
    }

    private String makeRangeTest(boolean openStart, String startEndpoint, boolean openEnd, String endEndpoint) {
        String startPara = openStart ? "(" : "[";
        String endPara = openEnd ? ")" : "]";
        return String.format("%s%s .. %s%s", startPara, startEndpoint, endEndpoint, endPara);
    }

    private String makeListTest(String listText) {
        return listText;
    }

    //
    // Expressions
    //
    private String makeBinaryExpression(String operator, Expression<T> element, Expression<T> leftOperand, Expression<T> rightOperand, C context) {
        String leftOpdText = "";
        Class<?> elementClass = element.getClass();
        if (leftOperand != null) {
            leftOpdText = leftOperand.accept(this, context);
            if (lowerPrecedence(leftOperand, element)) {
                leftOpdText = String.format("(%s)", leftOpdText);
            }
        }
        String rightOpdText = "";
        if (rightOperand != null) {
            rightOpdText = rightOperand.accept(this, context);
            Class<?> rightClass = rightOperand.getClass();
            if (lowerPrecedence(rightOperand, element)) {
                rightOpdText = String.format("(%s)", rightOpdText);
            } else if (sameOperator(rightClass, elementClass)) {
                rightOpdText = String.format("(%s)", rightOpdText);
            }
        }

        return String.format("%s %s %s", leftOpdText, operator, rightOpdText);
    }

    private String makeUnaryExpression(String operator, Expression<T> element, Expression<T> operand, C context) {
        String opd = "";
        if (operand != null) {
            opd = operand.accept(this, context);
            if (lowerPrecedence(operand, element)) {
                opd = String.format("(%s)", opd);
            }
        }
        if (operator.equals("-")) {
            return String.format("%s%s", operator, opd);
        } else {
            return String.format("%s %s", operator, opd);
        }
    }

    private String makeForExpression(List<String> iterators, String body) {
        return String.format(String.format("for %s return %s", String.join(", ", iterators), body));
    }

    private String makeIterator(String name, String domain) {
        return String.format("%s in %s", name, domain);
    }

    private String makeRangeDomain(String start, String end) {
        return String.format("%s .. %s", start, end);
    }

    private String makeIfExpression(String condition, String then, String else_) {
        return String.format("if %s then %s else %s", condition, then, else_);
    }

    private String makeQuantifiedExpression(String predicate, List<String> iterators, String body) {
        return String.format("%s %s satisfies %s", predicate, String.join(" ", iterators), body);
    }

    // Comparison
    private String makeBetweenExpression(String value, String leftEndpoint, String rightEndpoint) {
        return String.format("%s between %s and %s", value, leftEndpoint, rightEndpoint);
    }

    private String makeInExpression(String value, List<String> tests) {
        String testsText = String.join(", ", tests);
        if (tests.size() > 1) {
            return String.format("%s in (%s)", value, testsText);
        } else {
            return String.format("%s in %s", value, testsText);
        }
    }

    private String makeInstanceOf(String leftOperand, String rightOperand) {
        return String.format("%s instance of %s", leftOperand, rightOperand);
    }

    // Postfix
    private String makeFunctionCall(String functionName, String args) {
        return String.format("%s(%s)", functionName, args);
    }

    private String makeNamedParameter(Map.Entry<String, Expression<T>> entry, C context) {
        String name = entry.getKey();
        if (name.contains(" ")) {
            name = String.format("'%s'", name);
        }
        return String.format("%s: %s", name, entry.getValue() == null ? "null" : entry.getValue().accept(this, context));
    }

    private String makeFilter(String source, String filter) {
        return String.format("%s[%s]", source, filter);
    }

    private String makePathExpression(String source, String member, boolean requiresParenthesis) {
        if (requiresParenthesis) {
            return String.format("(%s).%s", source, member);
        } else {
            return String.format("%s.%s", source, member);
        }
    }

    // Primary
    private String makeSimpleLiteral(SimpleLiteral<T> element) {
        return String.format("%s", element.getLexeme());
    }

    private String makeListLiteral(List<String> members) {
        return String.format("[%s]", String.join(", ", members));
    }

    private String makeContext(List<String> entries) {
        return String.format("{%s}", String.join(", ", entries));
    }

    private String makeContextEntry(String key, String value) {
        return String.format("\"%s\": %s", key, value);
    }

    private String makeFunctionDefinition(List<String> parameters, String body, String returnType, boolean isExternal) {
        String returnTypePart = "";
        if (!StringUtils.isBlank(returnType)) {
            returnTypePart = String.format(" : %s", returnType);
        }
        String externalPart = "";
        if (isExternal) {
            externalPart = " external";
        }
        String bodyPart = "";
        if (!StringUtils.isBlank(body)) {
            bodyPart = " " + body;
        }
        return String.format("(function(%s)%s%s%s)", String.join(", ", parameters), returnTypePart, externalPart, bodyPart);
    }

    private String makeFormalParameter(String name, String type) {
        if (StringUtils.isBlank(type)) {
            return name;
        } else {
            return String.format("%s: %s", name, type);
        }
    }

    // Type expressions
    private String makeNamedTypeExpression(String qualifiedName) {
        return qualifiedName;
    }

    private String makeMemberTypeExpression(Pair<String, TypeExpression<T>> member, C context) {
        return String.format("%s: %s", member.getLeft(), member.getRight().accept(this, context));
    }

    private String makeContextTypeExpression(List<String> members) {
        return String.format("context<%s>", String.join(", ", members));
    }

    private String makeRangeTypeExpression(String elementType) {
        return String.format("range<%s>", elementType);
    }

    private String makeListTypeExpression(String elementType) {
        return String.format("list<%s>", elementType);
    }

    private String makeFunctionTypeExpression(List<String> parameters, String returnType) {
        return String.format("function<%s> -> %s", String.join(", ", parameters), returnType);
    }

    private String makeFunctionTypeExpressionParameter(TypeExpression<T> parameter, C context) {
        return String.format("%s", parameter.accept(this, context));
    }
}
