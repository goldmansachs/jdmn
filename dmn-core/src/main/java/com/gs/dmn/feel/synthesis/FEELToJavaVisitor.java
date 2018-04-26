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
package com.gs.dmn.feel.synthesis;

import com.gs.dmn.feel.analysis.semantics.ReplaceItemFilterVisitor;
import com.gs.dmn.feel.analysis.semantics.environment.BusinessKnowledgeModelDeclaration;
import com.gs.dmn.feel.analysis.semantics.environment.Conversion;
import com.gs.dmn.feel.analysis.semantics.environment.Declaration;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
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
import com.gs.dmn.runtime.interpreter.Arguments;
import com.gs.dmn.runtime.interpreter.NamedArguments;
import com.gs.dmn.runtime.interpreter.PositionalArguments;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FEELToJavaVisitor extends AbstractFEELToJavaVisitor {
    private static final int INITIAL_VALUE = -1;
    private int filterCount = INITIAL_VALUE;

    public FEELToJavaVisitor(BasicDMN2JavaTransformer dmnTransformer) {
        super(dmnTransformer);
    }

    public void init() {
        this.filterCount = INITIAL_VALUE;
    }

    //
    // Tests
    //
    @Override
    public Object visit(PositiveUnaryTests element, FEELContext context) {
        List<PositiveUnaryTest> simplePositiveUnaryTests = element.getPositiveUnaryTests();
        List<String> operands = simplePositiveUnaryTests.stream().map(t -> String.format("(%s)", (String) t.accept(this, context))).collect(Collectors.toList());
        return toBooleanOr(operands);
    }

    @Override
    public Object visit(NegatedPositiveUnaryTests element, FEELContext context) {
        PositiveUnaryTests simplePositiveUnaryTests = element.getPositiveUnaryTests();
        String condition = (String) simplePositiveUnaryTests.accept(this, context);
        condition = String.format("booleanNot(%s)", condition);
        return condition;
    }


    @Override
    public Object visit(NegatedSimplePositiveUnaryTests element, FEELContext context) {
        SimplePositiveUnaryTests simplePositiveUnaryTests = element.getSimplePositiveUnaryTests();
        String condition = (String) simplePositiveUnaryTests.accept(this, context);
        condition = String.format("booleanNot(%s)", condition);
        return condition;
    }

    @Override
    public Object visit(SimplePositiveUnaryTests element, FEELContext context) {
        List<SimplePositiveUnaryTest> simplePositiveUnaryTests = element.getSimplePositiveUnaryTests();
        List<String> operands = simplePositiveUnaryTests.stream().map(t -> String.format("(%s)", (String) t.accept(this, context))).collect(Collectors.toList());
        return toBooleanOr(operands);
    }

    @Override
    public Object visit(Any element, FEELContext context) {
        return "true";
    }

    @Override
    public Object visit(NullTest element, FEELContext context) {
        return String.format("%s == null", inputExpressionToJava(context));
    }

    @Override
    public Object visit(ExpressionTest element, FEELContext context) {
        Expression expression = element.getExpression();
        String condition = (String) expression.accept(this, context);
        return condition;
    }

    @Override
    public Object visit(OperatorTest element, FEELContext context) {
        String operator = element.getOperator();
        Expression endpoint = element.getEndpoint();
        String condition;
        if (endpoint instanceof FunctionInvocation) {
            condition = (String) endpoint.accept(this, context);
        } else {
            if (operator == null) {
                condition = makeListTestCondition("=", inputExpressionToJava(context), endpoint, context);
            } else {
                condition = makeListTestCondition(operator, inputExpressionToJava(context), endpoint, context);
            }
        }
        return condition;
    }

    @Override
    public Object visit(RangeTest element, FEELContext context) {
        String leftCondition = makeListTestCondition(element.isOpenStart() ? ">" : ">=", inputExpressionToJava(context), element.getStart(), context);
        String rightCondition = makeListTestCondition(element.isOpenEnd() ? "<" : "<=", inputExpressionToJava(context), element.getEnd(), context);
        return String.format("booleanAnd(%s, %s)", leftCondition, rightCondition);
    }

    @Override
    public Object visit(ListTest element, FEELContext context) {
        String operator = "=";
        Expression listLiteral = element.getListLiteral();
        String condition;
        if (listLiteral instanceof FunctionInvocation) {
            condition = (String) listLiteral.accept(this, context);
        } else {
            condition = makeListTestCondition(operator, inputExpressionToJava(context), listLiteral, context);
        }
        return condition;
    }

    //
    // Textual expressions
    //
    @Override
    public Object visit(FunctionDefinition element, FEELContext context) {
        if (element.isStaticTyped()) {
            String body = (String)element.getBody().accept(this, context);
            return dmnTransformer.functionDefinitionToJava(element, false, body);
        } else {
            throw new DMNRuntimeException("Dynamic typing for FEEL functions not supported yet");
        }
    }

    @Override
    public Object visit(FormalParameter element, FEELContext context) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    @Override
    public Object visit(Context element, FEELContext context) {
        String addMethods = element.getEntries().stream().map(e -> (String) e.accept(this, context)).collect(Collectors.joining(""));
        return dmnTransformer.fluentConstructor(DMNToJavaTransformer.CONTEXT_CLASS_NAME, addMethods);
    }

    @Override
    public Object visit(NamedParameters element, FEELContext context) {
        Map<String, Object> arguments = new LinkedHashMap<>();
        Map<String, Expression> parameters = element.getParameters();

        for (Map.Entry<String, Expression> entry : parameters.entrySet()) {
            String key = entry.getKey();
            Expression expression = entry.getValue();
            Object value = expression.accept(this, context);
            arguments.put(key, value);
        }
        return new NamedArguments(arguments);
    }

    @Override
    public Object visit(PositionalParameters element, FEELContext context) {
        List<Object> arguments = new ArrayList<>();
        element.getParameters().forEach(p -> arguments.add(p.accept(this, context)));
        return new PositionalArguments(arguments);
    }

    @Override
    public Object visit(ContextEntry element, FEELContext context) {
        String key = (String) element.getKey().accept(this, context);
        String value = (String) element.getExpression().accept(this, context);
        return String.format(".add(%s, %s)", key, value);
    }

    @Override
    public Object visit(ContextEntryKey element, FEELContext context) {
        return String.format("\"%s\"", element.getKey());
    }

    @Override
    public Object visit(ForExpression element, FEELContext context) {
        // Add code for each iterator
        StringBuilder result = new StringBuilder();
        List<Iterator> iterators = element.getIterators();
        for (Iterator it : iterators) {
            IteratorDomain expressionDomain = it.getDomain();
            String domain = (String) expressionDomain.accept(this, context);
            result.append(String.format("%s.stream().map(%s -> ", domain, it.getName()));
        }
        // Add body
        String body = (String) element.getBody().accept(this, context);
        result.append(body);
        // Close parenthesis
        for (int i = 0; i < iterators.size(); i++) {
            result.append(")");
        }
        // Flatten nested streams
        for (int i = 0; i < iterators.size() - 1; i++) {
            result.append(".flatMap(x -> x)");
        }
        // Collect result
        result.append(".collect(Collectors.toList())");
        return result.toString();
    }

    @Override
    public Object visit(Iterator element, FEELContext context) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    @Override
    public Object visit(ExpressionIteratorDomain element, FEELContext context) {
        Expression expressionDomain = element.getExpression();
        String domain = null;
        if (expressionDomain instanceof Name) {
            String name = ((Name) expressionDomain).getName();
            domain = dmnTransformer.javaFriendlyVariableName(name);
        } else if (expressionDomain instanceof RangeTest) {
            RangeTest test = (RangeTest) expressionDomain;
            String start = (String) test.getStart().accept(this, context);
            String end = (String) test.getEnd().accept(this, context);
            domain = String.format("rangeToList(%s, %s, %s, %s)", test.isOpenStart(), start, test.isOpenEnd(), end);
        } else if (expressionDomain instanceof ListTest) {
            ListTest test = (ListTest) expressionDomain;
            domain = (String) test.getListLiteral().accept(this, context);
        } else if (expressionDomain instanceof ListLiteral) {
            domain = (String) expressionDomain.accept(this, context);
        } else {
            throw new UnsupportedOperationException(String.format("FEEL '%s' is not supported yet with domain '%s'",
                    element.getClass().getSimpleName(), expressionDomain.getClass().getSimpleName()));
        }
        return domain;
    }

    @Override
    // TODO optimize generated code
    public Object visit(RangeIteratorDomain element, FEELContext context) {
        String start = (String) element.getStart().accept(this, context);
        String end = (String) element.getEnd().accept(this, context);
        return String.format("rangeToList(%s, %s)", start, end);
    }

    @Override
    public Object visit(IfExpression element, FEELContext context) {
        String condition = (String) element.getCondition().accept(this, context);
        String thenExp = (String) element.getThenExpression().accept(this, context);
        String elseExp = (String) element.getElseExpression().accept(this, context);
        return String.format("(booleanEqual(%s, Boolean.TRUE)) ? %s : %s", condition, thenExp, elseExp);
    }

    @Override
    public Object visit(QuantifiedExpression element, FEELContext context) {
        ForExpression forExpression = element.toForExpression();
        String forList = (String) forExpression.accept(this, context);
        // Add boolean predicate
        String predicate = element.getPredicate();
        if ("some".equals(predicate)) {
            return String.format("booleanOr((List)%s)", forList);
        } else if ("every".equals(predicate)) {
            return String.format("booleanAnd((List)%s)", forList);
        } else {
            throw new UnsupportedOperationException("Predicate '" + predicate + "' is not supported yet");
        }
    }

    @Override
    public Object visit(FilterExpression element, FEELContext context) {
        // Generate source
        Type sourceType = element.getSource().getType();
        Type filterType = element.getFilter().getType();
        String source = (String) element.getSource().accept(this, context);

        // Replace 'item' with 'item_xx' to be able to handle multiple filters
        String olderParameterName = FilterExpression.FILTER_PARAMETER_NAME;
        String newParameterName = newParameterName(olderParameterName);
        element.accept(new ReplaceItemFilterVisitor(olderParameterName, newParameterName), context);

        // Generate filter
        FEELContext feelContext = makeFilterContext(context, element.getSource(), newParameterName);
        String filter = (String) element.getFilter().accept(this, feelContext);

        // Convert source to list
        if (!(sourceType instanceof ListType)) {
            source = dmnTransformer.asList(source);
        }

        // Filter
        if (filterType == BooleanType.BOOLEAN) {
            return String.format("%s.stream().filter(%s -> %s).collect(Collectors.toList())", source, newParameterName, filter);
        } else if (filterType == NumberType.NUMBER) {
            // Compute element type
            Type elementType = null;
            if (sourceType instanceof ListType) {
                elementType = ((ListType) sourceType).getElementType();
            } else {
                elementType = sourceType;
            }
            String javaElementType = dmnTransformer.toJavaType(elementType);

            return String.format("(%s)(elementAt(%s, %s))", javaElementType, source, filter);
        } else {
            throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
        }
    }

    private String newParameterName(String olderParameterName) {
        if (filterCount == INITIAL_VALUE) {
            filterCount++;
            return olderParameterName;
        } else {
            return String.format("%s_%d_", olderParameterName, ++filterCount);
        }
    }

    @Override
    public Object visit(InstanceOfExpression element, FEELContext context) {
        String opd = (String) element.getValue().accept(this, context);
        String qTypeName = element.getQTypeName().getQualifiedName();
        String javaType = feelTypeTranslator.toJavaType(qTypeName);
        return String.format("%s instanceof %s", opd, javaType);
    }

    //
    // Expressions
    //
    @Override
    public Object visit(ExpressionList element, FEELContext context) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    //
    // Logic expressions
    //
    @Override
    public Object visit(Disjunction element, FEELContext context) {
        Expression leftOperand = element.getLeftOperand();
        Expression rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public Object visit(Conjunction element, FEELContext context) {
        Expression leftOperand = element.getLeftOperand();
        Expression rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public Object visit(LogicNegation element, FEELContext context) {
        Expression leftOperand = element.getLeftOperand();
        String leftOpd = (String) leftOperand.accept(this, context);
        return String.format("booleanNot(%s)", leftOpd);
    }

    //
    // Comparison expressions
    //
    @Override
    public Object visit(Relational element, FEELContext context) {
        Expression leftOperand = element.getLeftOperand();
        Expression rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public Object visit(BetweenExpression element, FEELContext context) {
        String value = (String) element.getValue().accept(this, context);
        String leftEndpoint = (String) element.getLeftEndpoint().accept(this, context);
        String rightEndpoint = (String) element.getRightEndpoint().accept(this, context);
        return String.format("booleanAnd(numericLessEqualThan(%s, %s), numericLessEqualThan(%s, %s))", leftEndpoint, value, value, rightEndpoint);
    }

    @Override
    public Object visit(InExpression element, FEELContext context) {
        Expression valueExp = element.getValue();
        List<PositiveUnaryTest> positiveUnaryTests = element.getTests();

        Environment inEnvironment = environmentFactory.makeEnvironment(context.getEnvironment(), valueExp);
        FEELContext inParams = FEELContext.makeContext(inEnvironment);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < positiveUnaryTests.size(); i++) {
            if (i != 0) {
                result.append(" || ");
            }
            PositiveUnaryTest positiveUnaryTest = positiveUnaryTests.get(i);
            String test = null;
            if (positiveUnaryTest instanceof ListTest) {
                ListLiteral listLiteral = ((ListTest) positiveUnaryTest).getListLiteral();
                String javaList = (String) listLiteral.accept(this, inParams);
                test = String.format("listContains(%s, %s)", javaList, inputExpressionToJava(inParams));
            } else {
                test = (String) positiveUnaryTest.accept(this, inParams);
            }
            result.append(String.format("(%s)", test));
        }
        return result.toString();
    }

    //
    // Arithmetic expressions
    //
    @Override
    public Object visit(Addition element, FEELContext context) {
        Expression leftOperand = element.getLeftOperand();
        Expression rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public Object visit(Multiplication element, FEELContext context) {
        Expression leftOperand = element.getLeftOperand();
        Expression rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public Object visit(Exponentiation element, FEELContext context) {
        String leftOpd = (String) element.getLeftOperand().accept(this, context);
        String rightOpd = (String) element.getRightOperand().accept(this, context);
        return String.format("numericExponentiation(%s, %s)", leftOpd, rightOpd);
    }

    @Override
    public Object visit(ArithmeticNegation element, FEELContext context) {
        Expression leftOperand = element.getLeftOperand();
        String leftOpd = (String) leftOperand.accept(this, context);
        return String.format("numericUnaryMinus(%s)", leftOpd);
    }

    //
    // Postfix expressions
    //
    @Override
    public Object visit(PathExpression element, FEELContext context) {
        Expression sourceExpression = element.getSource();
        Type sourceType = sourceExpression.getType();
        String source = (String) sourceExpression.accept(this, context);
        String member = element.getMember();

        return makeNavigation(element, sourceType, source, member, javaFriendlyVariableName(member));
    }

    @Override
    public Object visit(FunctionInvocation element, FEELContext context) {
        Arguments arguments = (Arguments) element.getParameters().accept(this, context);
        Expression function = element.getFunction();
        FunctionType functionType = (FunctionType) function.getType();
        List<FormalParameter> formalParameters = functionType.getParameters();
        List<Object> argList = arguments.argumentList(formalParameters);
        if (!argList.isEmpty()) {
            argList = convertArguments(argList, element.getParameterConversions());
        }
        String argumentsText = argList.stream().map(Object::toString).collect(Collectors.joining(", "));
        if (function instanceof Name || function instanceof QualifiedName && ((QualifiedName) function).getNames().size() == 1) {
            String feelFunctionName = functionName(function);
            Signature signature = element.getParameters().getSignature();
            Declaration declaration = context.getEnvironment().lookupFunctionDeclaration(feelFunctionName, signature);
            if (declaration instanceof BusinessKnowledgeModelDeclaration) {
                argumentsText = dmnTransformer.drgElementArgumentsExtra(dmnTransformer.augmentArgumentList(argumentsText));
                String javaFunctionName = dmnTransformer.bkmFunctionName(feelFunctionName);
                return String.format("%s(%s)", javaFunctionName, argumentsText);
            } else {
                String javaFunctionName = javaFunctionName(feelFunctionName);
                if (functionType instanceof FEELFunctionType || functionType instanceof DMNFunctionType) {
                    return String.format("%s.apply(%s)", javaFunctionName, argumentsText);
                } else {
                    return String.format("%s(%s)", javaFunctionName, argumentsText);
                }
            }
        } else {
            String functionCode = (String) function.accept(this, context);
            return String.format("%s.apply(%s)", functionCode, argumentsText);
        }
    }

    @Override
    public Object convertArgument(Object param, Conversion conversion) {
        String conversionFunction = conversion.conversionFunction(conversion);
        if (conversionFunction != null) {
            param = String.format("%s(%s)", conversionFunction, param);
        }
        return param;
    }

    //
    // Primary expressions
    //
    @Override
    public Object visit(NumericLiteral element, FEELContext context) {
        return String.format("number(\"%s\")", element.getValue());
    }

    @Override
    public Object visit(StringLiteral element, FEELContext context) {
        return String.format("%s", dmnTransformer.escapeInString(element.getValue()));
    }

    @Override
    public Object visit(BooleanLiteral element, FEELContext context) {
        String value = element.getValue();
        return "true".equals(value) ? "Boolean.TRUE" : "Boolean.FALSE";
    }

    @Override
    public Object visit(DateTimeLiteral element, FEELContext context) {
        return dateTimeLiteralToJava(element);
    }

    @Override
    public Object visit(NullLiteral element, FEELContext context) {
        return "null";
    }

    @Override
    public Object visit(ListLiteral element, FEELContext context) {
        List<Expression> expressionList = element.getExpressionList();
        String elements = expressionList.stream().map(e -> (String) e.accept(this, context)).collect(Collectors.joining(", "));
        return dmnTransformer.asList(elements);
    }

    @Override
    public Object visit(QualifiedName element, FEELContext context) {
        if (element.getNames().size() == 1) {
            return nameToJava(element.getNames().get(0), context);
        } else {
            throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
        }
    }

    @Override
    public Object visit(Name element, FEELContext context) {
        String name = element.getName();
        return nameToJava(name, context);
    }

    protected Object nameToJava(String name, FEELContext context) {
        if (name.equals(DMNToJavaTransformer.INPUT_ENTRY_PLACE_HOLDER)) {
            return inputExpressionToJava(context);
        } else {
            String javaName = javaFriendlyVariableName(name);
            return dmnTransformer.isLazyEvaluated(name) ? javaName + ".getOrCompute()" : javaName;
        }
    }

    private String inputExpressionToJava(FEELContext context) {
        Environment environment = context.getEnvironment();

        Expression inputExpression = environment.getInputExpression();
        if (inputExpression == null) {
            throw new DMNRuntimeException("Missing inputExpression");
        } else {
            SimpleExpressionsToJavaVisitor visitor = new SimpleExpressionsToJavaVisitor(dmnTransformer);
            visitor.init();
            return (String) inputExpression.accept(visitor, context);
        }
    }

    private Object toBooleanOr(List<String> operands) {
        if (operands.size() == 1) {
            return operands.get(0);
        } else {
            return String.format("booleanOr(%s)", operands.stream().collect(Collectors.joining(", ")));
        }
    }

    private String makeListTestCondition(String feelOperator, String inputExpression, Expression rightOperand, FEELContext params) {
        String rightOpd = (String) rightOperand.accept(this, params);
        String condition = null;
        String javaOperator = listTestOperator(feelOperator, params.getEnvironment().getInputExpression(), rightOperand);
        if (StringUtils.isEmpty(javaOperator)) {
            condition = infixExpression(javaOperator, inputExpression, rightOpd);
        } else {
            condition = functionalExpression(javaOperator, inputExpression, rightOpd);
        }
        return condition;
    }
}
