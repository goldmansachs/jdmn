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
package com.gs.dmn.feel.synthesis;

import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TInvocable;
import com.gs.dmn.ast.TNamedElement;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.OperatorDecisionTable;
import com.gs.dmn.feel.analysis.semantics.ReplaceItemFilterVisitor;
import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.type.*;
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
import com.gs.dmn.feel.analysis.syntax.ast.test.*;
import com.gs.dmn.feel.lib.StringEscapeUtil;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FEELToNativeVisitor extends AbstractFEELToJavaVisitor {
    private static final int INITIAL_VALUE = -1;
    private int filterCount = INITIAL_VALUE;

    public FEELToNativeVisitor(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        super(dmnTransformer);
    }

    public void init() {
        this.filterCount = INITIAL_VALUE;
    }

    //
    // Tests
    //
    @Override
    public Object visit(PositiveUnaryTests<Type, DMNContext> element, DMNContext context) {
        List<PositiveUnaryTest<Type, DMNContext>> simplePositiveUnaryTests = element.getPositiveUnaryTests();
        List<String> operands = simplePositiveUnaryTests.stream().map(t -> String.format("(%s)", t.accept(this, context))).collect(Collectors.toList());
        return toBooleanOr(operands);
    }

    @Override
    public Object visit(NegatedPositiveUnaryTests<Type, DMNContext> element, DMNContext context) {
        PositiveUnaryTests<Type, DMNContext> simplePositiveUnaryTests = element.getPositiveUnaryTests();
        String condition = (String) simplePositiveUnaryTests.accept(this, context);
        condition = this.nativeFactory.makeBuiltinFunctionInvocation("booleanNot", condition);
        return condition;
    }

    @Override
    public Object visit(Any<Type, DMNContext> element, DMNContext context) {
        return this.nativeFactory.trueConstant();
    }

    @Override
    public Object visit(NullTest<Type, DMNContext> element, DMNContext context) {
        return this.nativeFactory.isNull(inputExpressionToJava(context));
    }

    @Override
    public Object visit(ExpressionTest<Type, DMNContext> element, DMNContext context) {
        Expression<Type, DMNContext> expression = element.getExpression();
        return expression.accept(this, context);
    }

    @Override
    public Object visit(OperatorRange<Type, DMNContext> element, DMNContext context) {
        if (context.isExpressionContext()) {
            // Evaluate as range
            return element.getEndpointsRange().accept(this, context);
        } else {
            // Evaluate as test
            String operator = element.getOperator();
            Expression<Type, DMNContext> endpoint = element.getEndpoint();
            String condition;
            if (operator == null) {
                condition = makeListTestCondition("=", inputExpressionToJava(context), endpoint, context);
            } else {
                condition = makeListTestCondition(operator, inputExpressionToJava(context), endpoint, context);
            }
            return condition;
        }
    }

    @Override
    public Object visit(EndpointsRange<Type, DMNContext> element, DMNContext context) {
        Expression<Type, DMNContext> startEndpoint = element.getStart();
        Expression<Type, DMNContext> endEndpoint = element.getEnd();
        if (context.isExpressionContext()) {
            // Evaluate as range
            String startIncluded = this.nativeFactory.booleanValueLiteral("" + !element.isOpenStart());
            String endIncluded = this.nativeFactory.booleanValueLiteral("" + !element.isOpenEnd());
            String start = startEndpoint == null ? this.nativeFactory.nullLiteral() : (String) startEndpoint.accept(this, context);
            String end = endEndpoint == null ? this.nativeFactory.nullLiteral() : (String) endEndpoint.accept(this, context);

            String clsName = this.dmnTransformer.rangeClassName();
            String args = String.format("%s, %s, %s, %s", startIncluded, start, endIncluded, end);
            return this.dmnTransformer.constructor(clsName, args);
        } else {
            // Evaluate as test
            String leftCondition = makeListTestCondition(element.isOpenStart() ? ">" : ">=", inputExpressionToJava(context), startEndpoint, context);
            String rightCondition = makeListTestCondition(element.isOpenEnd() ? "<" : "<=", inputExpressionToJava(context), endEndpoint, context);
            String args = String.format("%s, %s", leftCondition, rightCondition);
            return this.nativeFactory.makeBuiltinFunctionInvocation("booleanAnd", args);
        }
    }

    @Override
    public Object visit(ListTest<Type, DMNContext> element, DMNContext context) {
        Type inputExpressionType = context.getInputExpressionType();

        ListLiteral<Type, DMNContext> optimizedListLiteral = element.getOptimizedListLiteral();
        if (optimizedListLiteral != null) {
            // Optimisation
            Type optimizedListType = optimizedListLiteral.getType();
            Type optimizedListElementType = ((ListType) optimizedListType).getElementType();
            if (com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(inputExpressionType, optimizedListType)) {
                // both are compatible lists
                String javaList = (String) optimizedListLiteral.accept(this, context);
                String args = String.format("%s, %s", inputExpressionToJava(context), javaList);
                return this.nativeFactory.makeBuiltinFunctionInvocation("listEqual", args);
            } else if (com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(inputExpressionType, optimizedListElementType)) {
                // input conforms to element in the list
                String javaList = (String) optimizedListLiteral.accept(this, context);
                String args = String.format("%s, %s", javaList, inputExpressionToJava(context));
                return this.nativeFactory.makeBuiltinFunctionInvocation("listContains", args);
            } else {
                throw new SemanticError(element, String.format("Cannot compare '%s', '%s'", inputExpressionType, optimizedListType));
            }
        } else {
            // test is list of ranges compatible with input
            ListLiteral<Type, DMNContext> listLiteral = element.getListLiteral();
            String javaList = (String) listLiteral.accept(this, context);
            String args = String.format("%s, %s", javaList, "true");
            return this.nativeFactory.makeBuiltinFunctionInvocation("listContains", args);
        }
    }

    //
    // Textual expressions
    //
    @Override
    public Object visit(FunctionDefinition<Type, DMNContext> element, DMNContext context) {
        if (element.isStaticTyped()) {
            String body = (String)element.getBody().accept(this, context);
            return this.dmnTransformer.functionDefinitionToNative((TDRGElement) context.getElement(), element, false, body);
        } else {
            throw new DMNRuntimeException("Dynamic typing for FEEL functions not supported yet");
        }
    }

    @Override
    public Object visit(FormalParameter<Type, DMNContext> element, DMNContext context) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    @Override
    public Object visit(Context<Type, DMNContext> element, DMNContext context) {
        String addMethods = element.getEntries().stream().map(e -> (String) e.accept(this, context)).collect(Collectors.joining(""));
        return this.nativeFactory.fluentConstructor(this.dmnTransformer.contextClassName(), addMethods);
    }

    @Override
    public Object visit(NamedParameters<Type, DMNContext> element, DMNContext context) {
        Map<String, Object> arguments = new LinkedHashMap<>();
        Map<String, Expression<Type, DMNContext>> parameters = element.getParameters();

        for (Map.Entry<String, Expression<Type, DMNContext>> entry : parameters.entrySet()) {
            String key = entry.getKey();
            Expression<Type, DMNContext> expression = entry.getValue();
            Object value = expression.accept(this, context);
            arguments.put(key, value);
        }
        element.setOriginalArguments(new NamedArguments<>(arguments));
        return element;
    }

    @Override
    public Object visit(PositionalParameters<Type, DMNContext> element, DMNContext context) {
        List<Object> arguments = new ArrayList<>();
        element.getParameters().forEach(p -> arguments.add(p.accept(this, context)));
        element.setOriginalArguments(new PositionalArguments<>(arguments));
        return element;
    }

    @Override
    public Object visit(ContextEntry<Type, DMNContext> element, DMNContext context) {
        String key = (String) element.getKey().accept(this, context);
        String value = (String) element.getExpression().accept(this, context);
        return String.format(".add(%s, %s)", key, value);
    }

    @Override
    public Object visit(ContextEntryKey<Type, DMNContext> element, DMNContext context) {
        return String.format("\"%s\"", element.getKey());
    }

    @Override
    public Object visit(ForExpression<Type, DMNContext> element, DMNContext context) {
        DMNContext forContext = this.dmnTransformer.makeForContext(element, context);

        List<Iterator<Type, DMNContext>> iterators = element.getIterators();
        List<Pair<String, String>> domainIterators = new ArrayList<>();
        for (Iterator<Type, DMNContext> it : iterators) {
            IteratorDomain<Type, DMNContext> expressionDomain = it.getDomain();
            String domain = (String) expressionDomain.accept(this, forContext);
            domainIterators.add(new Pair<>(domain, it.getName()));
        }
        String body = (String) element.getBody().accept(this, forContext);
        return this.nativeFactory.makeForExpression(domainIterators, body);
    }

    @Override
    public Object visit(Iterator<Type, DMNContext> element, DMNContext context) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    @Override
    public Object visit(ExpressionIteratorDomain<Type, DMNContext> element, DMNContext context) {
        Expression<Type, DMNContext>expressionDomain = element.getExpression();
        String domain;
        if (expressionDomain instanceof Name) {
            String name = ((Name<Type, DMNContext>) expressionDomain).getName();
            domain = this.dmnTransformer.nativeFriendlyVariableName(name);
        } else if (expressionDomain instanceof EndpointsRange) {
            EndpointsRange<Type, DMNContext> test = (EndpointsRange<Type, DMNContext>) expressionDomain;
            String start = (String) test.getStart().accept(this, context);
            String end = (String) test.getEnd().accept(this, context);
            String args = String.format("%s, %s, %s, %s", test.isOpenStart(), start, test.isOpenEnd(), end);
            domain = this.nativeFactory.makeBuiltinFunctionInvocation("rangeToList", args);
        } else if (expressionDomain instanceof ListTest) {
            ListTest<Type, DMNContext> test = (ListTest<Type, DMNContext>) expressionDomain;
            domain = (String) test.getListLiteral().accept(this, context);
        } else if (expressionDomain instanceof ListLiteral) {
            domain = (String) expressionDomain.accept(this, context);
        } else if (expressionDomain instanceof FunctionInvocation) {
            domain = (String) expressionDomain.accept(this, context);
        } else {
            throw new UnsupportedOperationException(String.format("FEEL '%s' is not supported yet with domain '%s'",
                    element.getClass().getSimpleName(), expressionDomain.getClass().getSimpleName()));
        }
        return domain;
    }

    @Override
    // TODO optimize generated code
    public Object visit(RangeIteratorDomain<Type, DMNContext> element, DMNContext context) {
        String start = (String) element.getStart().accept(this, context);
        String end = (String) element.getEnd().accept(this, context);
        String args = String.format("%s, %s", start, end);
        return this.nativeFactory.makeBuiltinFunctionInvocation("rangeToList", args);
    }

    @Override
    public Object visit(IfExpression<Type, DMNContext> element, DMNContext context) {
        String condition = (String) element.getCondition().accept(this, context);
        String thenExp = (String) element.getThenExpression().accept(this, context);
        String elseExp = (String) element.getElseExpression().accept(this, context);
        return this.nativeFactory.makeIfExpression(condition, thenExp, elseExp);
    }

    @Override
    public Object visit(QuantifiedExpression<Type, DMNContext> element, DMNContext context) {
        ForExpression<Type, DMNContext> forExpression = element.toForExpression();
        String forList = (String) forExpression.accept(this, context);
        // Add boolean predicate
        String predicate = element.getPredicate();
        if ("some".equals(predicate)) {
            return this.nativeFactory.makeSomeExpression(forList);
        } else if ("every".equals(predicate)) {
            return this.nativeFactory.makeEveryExpression(forList);
        } else {
            throw new UnsupportedOperationException("Predicate '" + predicate + "' is not supported yet");
        }
    }

    @Override
    public Object visit(FilterExpression<Type, DMNContext> element, DMNContext context) {
        // Generate source
        Type sourceType = element.getSource().getType();
        Type filterType = element.getFilter().getType();
        String source = (String) element.getSource().accept(this, context);

        // Replace 'item' with 'item_xx' to be able to handle multiple filters
        String olderParameterName = FilterExpression.FILTER_PARAMETER_NAME;
        String newParameterName = this.dmnTransformer.lowerCaseFirst(newParameterName(olderParameterName));
        element.accept(new ReplaceItemFilterVisitor<>(olderParameterName, newParameterName, this.errorHandler), context);

        // Generate filter
        DMNContext feelContext = this.dmnTransformer.makeFilterContext(element, newParameterName, context);
        String filter = (String) element.getFilter().accept(this, feelContext);

        // Convert source to list
        if (!(sourceType instanceof ListType)) {
            source = this.dmnTransformer.asList(sourceType, source);
        }

        // Filter
        if (filterType == BooleanType.BOOLEAN) {
            return this.nativeFactory.makeCollectionLogicFilter(source, newParameterName, filter);
        } else if (filterType == NumberType.NUMBER) {
            // Compute element type
            Type elementType;
            if (sourceType instanceof ListType) {
                elementType = ((ListType) sourceType).getElementType();
            } else {
                elementType = sourceType;
            }
            String javaElementType = this.dmnTransformer.toNativeType(elementType);

            return this.nativeFactory.makeCollectionNumericFilter(javaElementType, source, filter);
        } else {
            throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
        }
    }

    private String newParameterName(String olderParameterName) {
        if (this.filterCount == INITIAL_VALUE) {
            this.filterCount++;
            return olderParameterName;
        } else {
            return String.format("%s_%d_", olderParameterName, ++this.filterCount);
        }
    }

    @Override
    public Object visit(InstanceOfExpression<Type, DMNContext> element, DMNContext context) {
        String leftOperand = (String) element.getLeftOperand().accept(this, context);
        Type rightOperandType = element.getRightOperand().getType();
        String javaType = this.nativeTypeFactory.toNativeType(rightOperandType.toString());
        return this.nativeFactory.makeInstanceOf(leftOperand, javaType);
    }

    //
    // Expressions
    //
    @Override
    public Object visit(ExpressionList<Type, DMNContext> element, DMNContext context) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    //
    // Logic expressions
    //
    @Override
    public Object visit(Disjunction<Type, DMNContext> element, DMNContext context) {
        Expression<Type, DMNContext> leftOperand = element.getLeftOperand();
        Expression<Type, DMNContext> rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public Object visit(Conjunction<Type, DMNContext> element, DMNContext context) {
        Expression<Type, DMNContext> leftOperand = element.getLeftOperand();
        Expression<Type, DMNContext> rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public Object visit(LogicNegation<Type, DMNContext> element, DMNContext context) {
        Expression<Type, DMNContext> leftOperand = element.getLeftOperand();
        String leftOpd = (String) leftOperand.accept(this, context);
        return this.nativeFactory.makeBuiltinFunctionInvocation("booleanNot", leftOpd);
    }

    //
    // Comparison expressions
    //
    @Override
    public Object visit(Relational<Type, DMNContext> element, DMNContext context) {
        Expression<Type, DMNContext> leftOperand = element.getLeftOperand();
        Expression<Type, DMNContext> rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public Object visit(BetweenExpression<Type, DMNContext> element, DMNContext context) {
        String value = (String) element.getValue().accept(this, context);
        Expression<Type, DMNContext> leftEndpoint = element.getLeftEndpoint();
        Expression<Type, DMNContext> rightEndpoint = element.getRightEndpoint();
        String leftOpd = (String) leftEndpoint.accept(this, context);
        String rightOpd = (String) rightEndpoint.accept(this, context);
        String feelOperator = "<=";
        NativeOperator javaOperator = OperatorDecisionTable.javaOperator(feelOperator, leftEndpoint.getType(), rightEndpoint.getType());
        String c1 = makeCondition(feelOperator, leftOpd, value, javaOperator);
        String c2 = makeCondition(feelOperator, value, rightOpd, javaOperator);
        String args = String.format("%s, %s", c1, c2);
        return this.nativeFactory.makeBuiltinFunctionInvocation("booleanAnd", args);
    }

    @Override
    public Object visit(InExpression<Type, DMNContext> element, DMNContext context) {
        Expression<Type, DMNContext> valueExp = element.getValue();
        List<PositiveUnaryTest<Type, DMNContext>> positiveUnaryTests = element.getTests();

        DMNContext inContext = this.dmnTransformer.makeUnaryTestContext(valueExp, context);
        List<String> result = new ArrayList<>();
        for (PositiveUnaryTest<Type, DMNContext> positiveUnaryTest: positiveUnaryTests) {
            String test = (String) positiveUnaryTest.accept(this, inContext);
            result.add(test);
        }
        if (result.size() == 1) {
            return String.format("(%s)", result.get(0));
        } else {
            return this.nativeFactory.makeBuiltinFunctionInvocation("booleanOr", String.join(", ", result));
        }
    }

    //
    // Arithmetic expressions
    //
    @Override
    public Object visit(Addition<Type, DMNContext> element, DMNContext context) {
        Expression<Type, DMNContext> leftOperand = element.getLeftOperand();
        Expression<Type, DMNContext> rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public Object visit(Multiplication<Type, DMNContext> element, DMNContext context) {
        Expression<Type, DMNContext> leftOperand = element.getLeftOperand();
        Expression<Type, DMNContext> rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public Object visit(Exponentiation<Type, DMNContext> element, DMNContext context) {
        String leftOpd = (String) element.getLeftOperand().accept(this, context);
        String rightOpd = (String) element.getRightOperand().accept(this, context);
        String args = String.format("%s, %s", leftOpd, rightOpd);
        return this.nativeFactory.makeBuiltinFunctionInvocation("numericExponentiation", args);
    }

    @Override
    public Object visit(ArithmeticNegation<Type, DMNContext> element, DMNContext context) {
        Expression<Type, DMNContext> leftOperand = element.getLeftOperand();
        String leftOpd = (String) leftOperand.accept(this, context);
        return this.nativeFactory.makeBuiltinFunctionInvocation("numericUnaryMinus", leftOpd);
    }

    //
    // Postfix expressions
    //
    @Override
    public Object visit(PathExpression<Type, DMNContext> element, DMNContext context) {
        Expression<Type, DMNContext> sourceExpression = element.getSource();
        Type sourceType = sourceExpression.getType();
        String source = (String) sourceExpression.accept(this, context);
        String member = element.getMember();

        return makeNavigation(element, sourceType, source, member, nativeFriendlyVariableName(member));
    }

    @Override
    public Object visit(FunctionInvocation<Type, DMNContext> element, DMNContext context) {
        // Generate code for actual parameters
        Parameters<Type, DMNContext> parameters = element.getParameters();
        parameters.accept(this, context);
        Arguments<Type, DMNContext> arguments = parameters.convertArguments(this::convertArgument);
        Expression<Type, DMNContext> function = element.getFunction();
        FunctionType functionType = (FunctionType) function.getType();
        List<FormalParameter<Type, DMNContext>> formalParameters = functionType.getParameters();
        List<Object> argList = arguments.argumentList(formalParameters);

        // Generate code for function
        String javaFunctionCode = (String) function.accept(this, context);
        if (functionType instanceof BuiltinFunctionType) {
            String argumentsText = argList.stream().map(Object::toString).collect(Collectors.joining(", "));
            return this.nativeFactory.makeBuiltinFunctionInvocation(javaFunctionCode, argumentsText);
        } else if (functionType instanceof DMNFunctionType) {
            if (!dmnTransformer.isJavaFunction(((DMNFunctionType) functionType).getKind())) {
                addExtraArguments(argList);
            }
            TNamedElement invocable = ((DMNFunctionType) functionType).getDRGElement();
            if (invocable instanceof TInvocable) {
                String argumentsText = argList.stream().map(Object::toString).collect(Collectors.joining(", "));
                if (function instanceof Name) {
                    if (((Name<Type, DMNContext>) function).getName().equals(invocable.getName())) {
                        String javaQualifiedName = this.dmnTransformer.singletonInvocableInstance((TInvocable) invocable);
                        return this.nativeFactory.makeApplyInvocation(javaQualifiedName, argumentsText);
                    } else {
                        return this.nativeFactory.makeApplyInvocation(javaFunctionCode, argumentsText);
                    }
                } else {
                    return this.nativeFactory.makeApplyInvocation(javaFunctionCode, argumentsText);
                }
            } else {
                String argumentsText = argList.stream().map(Object::toString).collect(Collectors.joining(", "));
                return this.nativeFactory.makeApplyInvocation(javaFunctionCode, argumentsText);
            }
        } else if (functionType instanceof FEELFunctionType) {
            if (!((FEELFunctionType) functionType).isExternal()) {
                addExtraArguments(argList);
            }
            String argumentsText = argList.stream().map(Object::toString).collect(Collectors.joining(", "));
            return this.nativeFactory.makeApplyInvocation(javaFunctionCode, argumentsText);
        } else {
            throw new DMNRuntimeException(String.format("Not supported function type '%s' in '%s'", functionType, context.getElementName()));
        }
    }

    private void addExtraArguments(List<Object> argList) {
        argList.add(String.format("%s", dmnTransformer.executionContextVariableName()));
    }

    protected Object convertArgument(Object param, Conversion<Type> conversion) {
        String conversionFunction = this.nativeFactory.conversionFunction(conversion, this.dmnTransformer.toNativeType(conversion.getTargetType()));
        if (conversionFunction != null) {
            param = this.nativeFactory.makeBuiltinFunctionInvocation(conversionFunction, param == null ? this.nativeFactory.nullLiteral() : param.toString());
        }
        return param;
    }

    //
    // Primary expressions
    //
    @Override
    public Object visit(NumericLiteral<Type, DMNContext> element, DMNContext context) {
        return this.nativeFactory.numericLiteral(element.getLexeme());
    }

    @Override
    public Object visit(StringLiteral<Type, DMNContext> element, DMNContext context) {
        String lexeme = element.getLexeme();
        String value = StringEscapeUtil.unescapeFEEL(lexeme);
        value = StringEscapeUtil.escapeFEEL(value);
        value = String.format("\"%s\"", value);
        return value;
    }

    @Override
    public Object visit(BooleanLiteral<Type, DMNContext> element, DMNContext context) {
        String value = element.getLexeme();
        return this.nativeFactory.booleanLiteral(value);
    }

    @Override
    public Object visit(DateTimeLiteral<Type, DMNContext> element, DMNContext context) {
        return this.nativeFactory.dateTimeLiteral(element);
    }

    @Override
    public Object visit(NullLiteral<Type, DMNContext> element, DMNContext context) {
        return this.nativeFactory.nullLiteral();
    }

    @Override
    public Object visit(ListLiteral<Type, DMNContext> element, DMNContext context) {
        List<Expression<Type, DMNContext>> expressionList = element.getExpressionList();
        String elements = expressionList.stream().map(e -> (String) e.accept(this, context)).collect(Collectors.joining(", "));
        Type elementType = ((ListType) element.getType()).getElementType();
        return this.dmnTransformer.asList(elementType, elements);
    }

    @Override
    public Object visit(QualifiedName<Type, DMNContext> element, DMNContext context) {
        if (element.getNames().size() == 1) {
            return nameToJava(element.getNames().get(0), context);
        } else {
            return handleNotSupportedElement(element);
        }
    }

    @Override
    public Object visit(Name<Type, DMNContext> element, DMNContext context) {
        String name = element.getName();
        return nameToJava(name, context);
    }

    //
    // Type expressions
    //
    @Override
    public Object visit(NamedTypeExpression<Type, DMNContext> element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(ContextTypeExpression<Type, DMNContext> element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(RangeTypeExpression<Type, DMNContext> element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(FunctionTypeExpression<Type, DMNContext> element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(ListTypeExpression<Type, DMNContext> element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    protected Object nameToJava(String name, DMNContext context) {
        if (name.equals(DMNContext.INPUT_ENTRY_PLACE_HOLDER)) {
            return inputExpressionToJava(context);
        } else {
            // Return lambda when DMN Invocable
            Declaration declaration = context.lookupVariableDeclaration(name);
            if (declaration != null) {
                Type type = declaration.getType();
                if (type instanceof DMNFunctionType) {
                    // DRG Element names
                    TDRGElement drgElement = ((DMNFunctionType) type).getDRGElement();
                    if (drgElement instanceof TInvocable) {
                        String drgElementName = drgElement.getName();
                        if (drgElementName.equals(name)) {
                            String javaQualifiedName = this.dmnTransformer.qualifiedName(drgElement);
                            return String.format("%s.instance().lambda", javaQualifiedName);
                        }
                    }
                }
            }
            // Other names
            String javaName = nativeFriendlyVariableName(name);
            return this.dmnTransformer.lazyEvaluation(name, javaName);
        }
    }

    private String inputExpressionToJava(DMNContext context) {
        if (context.isExpressionContext()) {
            throw new DMNRuntimeException(String.format("Missing inputExpression in context of element '%s'", context.getElementName()));
        } else {
            // Evaluate as test
            Expression<Type, DMNContext> inputExpression = (Expression) context.getInputExpression();
            return (String) inputExpression.accept(this, context);
        }
    }

    private Object toBooleanOr(List<String> operands) {
        if (operands.size() == 1) {
            return operands.get(0);
        } else {
            return this.nativeFactory.makeBuiltinFunctionInvocation("booleanOr", String.join(", ", operands));
        }
    }

    private String makeListTestCondition(String feelOperator, String inputExpressionText, Expression<Type, DMNContext> rightOperand, DMNContext context) {
        String rightOpd = (String) rightOperand.accept(this, context);
        String condition;
        Expression<Type, DMNContext> inputExpression = (Expression) context.getInputExpression();
        String javaOperator = listTestOperator(feelOperator, inputExpression, rightOperand);
        if (StringUtils.isEmpty(javaOperator)) {
            condition = infixExpression(javaOperator, inputExpressionText, rightOpd);
        } else {
            condition = functionalExpression(javaOperator, inputExpressionText, rightOpd);
        }
        return condition;
    }

    protected Object handleNotSupportedElement(Element<Type, DMNContext> element) {
        throw new UnsupportedOperationException("FEEL '" + (element == null ? null : element.getClass().getSimpleName()) + "' is not supported in this context");
    }
}
