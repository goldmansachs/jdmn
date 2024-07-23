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

import com.gs.dmn.DRGElementReference;
import com.gs.dmn.NameUtils;
import com.gs.dmn.ast.TBusinessKnowledgeModel;
import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TInvocable;
import com.gs.dmn.ast.TNamedElement;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.el.analysis.semantics.type.AnyType;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.OperatorDecisionTable;
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
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.basic.ImportContextType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FEELToNativeVisitor extends AbstractFEELToJavaVisitor<Object> {
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
    public String visit(PositiveUnaryTests<Type> element, DMNContext context) {
        List<PositiveUnaryTest<Type>> simplePositiveUnaryTests = element.getPositiveUnaryTests();
        List<String> operands = simplePositiveUnaryTests.stream().map(t -> String.format("%s", t.accept(this, context))).collect(Collectors.toList());
        return toBooleanOr(operands);
    }

    @Override
    public String visit(NegatedPositiveUnaryTests<Type> element, DMNContext context) {
        PositiveUnaryTests<Type> simplePositiveUnaryTests = element.getPositiveUnaryTests();
        String condition = (String) simplePositiveUnaryTests.accept(this, context);
        condition = this.nativeFactory.makeBuiltinFunctionInvocation("booleanNot", condition);
        return condition;
    }

    @Override
    public String visit(Any<Type> element, DMNContext context) {
        return this.nativeFactory.trueConstant();
    }

    @Override
    public String visit(NullTest<Type> element, DMNContext context) {
        return this.nativeFactory.isNull(inputExpressionToJava(context));
    }

    @Override
    public String visit(ExpressionTest<Type> element, DMNContext context) {
        Expression<Type> expression = element.getExpression();
        return (String) expression.accept(this, context);
    }

    @Override
    public String visit(OperatorRange<Type> element, DMNContext context) {
        if (context.isExpressionContext()) {
            // Evaluate as range
            return (String) element.getEndpointsRange().accept(this, context);
        } else {
            // Evaluate as test
            String operator = element.getOperator();
            Expression<Type> endpoint = element.getEndpoint();
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
    public String visit(EndpointsRange<Type> element, DMNContext context) {
        Expression<Type> startEndpoint = element.getStart();
        Expression<Type> endEndpoint = element.getEnd();
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
    public String visit(ListTest<Type> element, DMNContext context) {
        Type inputExpressionType = context.getInputExpressionType();

        ListLiteral<Type> optimizedListLiteral = element.getOptimizedListLiteral();
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
                handleError(context, element, String.format("Cannot compare '%s', '%s'", inputExpressionType, optimizedListType));
                return null;
            }
        } else {
            // test is list of ranges compatible with input
            ListLiteral<Type> listLiteral = element.getListLiteral();
            String javaList = (String) listLiteral.accept(this, context);
            String args = String.format("%s, %s", javaList, "true");
            return this.nativeFactory.makeBuiltinFunctionInvocation("listContains", args);
        }
    }

    //
    // Textual expressions
    //
    @Override
    public String visit(FunctionDefinition<Type> element, DMNContext context) {
        if (element.isStaticTyped()) {
            String body = (String)element.getBody().accept(this, context);
            return this.dmnTransformer.functionDefinitionToNative((TDRGElement) context.getElement(), element, false, body);
        } else {
            handleError(context, element, "Dynamic typing for FEEL functions not supported yet");
            return null;
        }
    }

    @Override
    public String visit(FormalParameter<Type> element, DMNContext context) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    @Override
    public String visit(Context<Type> element, DMNContext context) {
        String addMethods = element.getEntries().stream().map(e -> (String) e.accept(this, context)).collect(Collectors.joining(""));
        return this.nativeFactory.fluentConstructor(this.dmnTransformer.contextClassName(), addMethods);
    }

    @Override
    public NamedParameters<Type> visit(NamedParameters<Type> element, DMNContext context) {
        Map<String, Object> arguments = new LinkedHashMap<>();
        Map<String, Expression<Type>> parameters = element.getParameters();

        for (Map.Entry<String, Expression<Type>> entry : parameters.entrySet()) {
            String key = entry.getKey();
            Expression<Type> expression = entry.getValue();
            Object value = expression.accept(this, context);
            arguments.put(key, value);
        }
        element.setOriginalArguments(new NamedArguments<>(arguments));
        return element;
    }

    @Override
    public PositionalParameters<Type> visit(PositionalParameters<Type> element, DMNContext context) {
        List<Object> arguments = new ArrayList<>();
        element.getParameters().forEach(p -> arguments.add(p.accept(this, context)));
        element.setOriginalArguments(new PositionalArguments<>(arguments));
        return element;
    }

    @Override
    public String visit(ContextEntry<Type> element, DMNContext context) {
        String key = (String) element.getKey().accept(this, context);
        String value = (String) element.getExpression().accept(this, context);
        return String.format(".add(%s, %s)", key, value);
    }

    @Override
    public String visit(ContextEntryKey<Type> element, DMNContext context) {
        return String.format("\"%s\"", element.getKey());
    }

    @Override
    public String visit(ForExpression<Type> element, DMNContext context) {
        DMNContext forContext = this.dmnTransformer.makeForContext(context);

        List<Iterator<Type>> iterators = element.getIterators();
        List<Pair<String, String>> domainIterators = new ArrayList<>();
        for (Iterator<Type> it : iterators) {
            IteratorDomain<Type> expressionDomain = it.getDomain();
            String domain = (String) expressionDomain.accept(this, forContext);
            domainIterators.add(new Pair<>(domain, it.getName()));
        }
        String body = (String) element.getBody().accept(this, forContext);
        return this.nativeFactory.makeForExpression(domainIterators, body);
    }

    @Override
    public String visit(Iterator<Type> element, DMNContext context) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    @Override
    public String visit(ExpressionIteratorDomain<Type> element, DMNContext context) {
        Expression<Type>expressionDomain = element.getExpression();
        String domain;
        if (expressionDomain instanceof Name) {
            String name = ((Name<Type>) expressionDomain).getName();
            domain = this.dmnTransformer.nativeFriendlyVariableName(name);
        } else if (expressionDomain instanceof EndpointsRange) {
            EndpointsRange<Type> test = (EndpointsRange<Type>) expressionDomain;
            String start = (String) test.getStart().accept(this, context);
            String end = (String) test.getEnd().accept(this, context);
            String args = String.format("%s, %s, %s, %s", test.isOpenStart(), start, test.isOpenEnd(), end);
            domain = this.nativeFactory.makeBuiltinFunctionInvocation("rangeToList", args);
        } else if (expressionDomain instanceof ListTest) {
            ListTest<Type> test = (ListTest<Type>) expressionDomain;
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
    public String visit(RangeIteratorDomain<Type> element, DMNContext context) {
        String start = (String) element.getStart().accept(this, context);
        String end = (String) element.getEnd().accept(this, context);
        String args = String.format("%s, %s", start, end);
        return this.nativeFactory.makeBuiltinFunctionInvocation("rangeToList", args);
    }

    @Override
    public String visit(IfExpression<Type> element, DMNContext context) {
        String condition = (String) element.getCondition().accept(this, context);
        String thenExp = (String) element.getThenExpression().accept(this, context);
        String elseExp = (String) element.getElseExpression().accept(this, context);
        return this.nativeFactory.makeIfExpression(condition, thenExp, elseExp);
    }

    @Override
    public String visit(QuantifiedExpression<Type> element, DMNContext context) {
        ForExpression<Type> forExpression = element.toForExpression();
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
    public String visit(FilterExpression<Type> element, DMNContext context) {
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
    public String visit(InstanceOfExpression<Type> element, DMNContext context) {
        try {
            String leftOperand = (String) element.getLeftOperand().accept(this, context);
            Type rightOperandType = element.getRightOperand().getType();
            return this.nativeFactory.makeInstanceOf(leftOperand, rightOperandType);
        } catch (Exception e) {
            throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
        }
    }

    //
    // Expressions
    //
    @Override
    public String visit(ExpressionList<Type> element, DMNContext context) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    //
    // Logic expressions
    //
    @Override
    public String visit(Disjunction<Type> element, DMNContext context) {
        Expression<Type> leftOperand = element.getLeftOperand();
        Expression<Type> rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public String visit(Conjunction<Type> element, DMNContext context) {
        Expression<Type> leftOperand = element.getLeftOperand();
        Expression<Type> rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public String visit(LogicNegation<Type> element, DMNContext context) {
        Expression<Type> leftOperand = element.getLeftOperand();
        String leftOpd = (String) leftOperand.accept(this, context);
        return this.nativeFactory.makeBuiltinFunctionInvocation("booleanNot", leftOpd);
    }

    //
    // Comparison expressions
    //
    @Override
    public String visit(Relational<Type> element, DMNContext context) {
        Expression<Type> leftOperand = element.getLeftOperand();
        Expression<Type> rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public String visit(BetweenExpression<Type> element, DMNContext context) {
        String value = (String) element.getValue().accept(this, context);
        Expression<Type> leftEndpoint = element.getLeftEndpoint();
        Expression<Type> rightEndpoint = element.getRightEndpoint();
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
    public String visit(InExpression<Type> element, DMNContext context) {
        Expression<Type> valueExp = element.getValue();
        List<PositiveUnaryTest<Type>> positiveUnaryTests = element.getTests();

        DMNContext inContext = this.dmnTransformer.makeUnaryTestContext(valueExp, context);
        List<String> result = new ArrayList<>();
        for (PositiveUnaryTest<Type> positiveUnaryTest: positiveUnaryTests) {
            String test = (String) positiveUnaryTest.accept(this, inContext);
            result.add(test);
        }
        if (result.size() == 1) {
            return String.format("%s", result.get(0));
        } else {
            return this.nativeFactory.makeBuiltinFunctionInvocation("booleanOr", String.join(", ", result));
        }
    }

    //
    // Arithmetic expressions
    //
    @Override
    public String visit(Addition<Type> element, DMNContext context) {
        Expression<Type> leftOperand = element.getLeftOperand();
        Expression<Type> rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public String visit(Multiplication<Type> element, DMNContext context) {
        Expression<Type> leftOperand = element.getLeftOperand();
        Expression<Type> rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public String visit(Exponentiation<Type> element, DMNContext context) {
        String leftOpd = (String) element.getLeftOperand().accept(this, context);
        String rightOpd = (String) element.getRightOperand().accept(this, context);
        String args = String.format("%s, %s", leftOpd, rightOpd);
        return this.nativeFactory.makeBuiltinFunctionInvocation("numericExponentiation", args);
    }

    @Override
    public String visit(ArithmeticNegation<Type> element, DMNContext context) {
        Expression<Type> leftOperand = element.getLeftOperand();
        String leftOpd = (String) leftOperand.accept(this, context);
        return this.nativeFactory.makeBuiltinFunctionInvocation("numericUnaryMinus", leftOpd);
    }

    //
    // Postfix expressions
    //
    @Override
    public String visit(PathExpression<Type> element, DMNContext context) {
        Expression<Type> sourceExpression = element.getSource();
        Type sourceType = sourceExpression.getType();
        String source = (String) sourceExpression.accept(this, context);
        String member = element.getMember();

        return makeNavigation(element, sourceType, source, member, nativeFriendlyVariableName(member), context);
    }

    @Override
    public String visit(FunctionInvocation<Type> element, DMNContext context) {
        // Generate code for actual parameters
        Parameters<Type> parameters = element.getParameters();
        parameters.accept(this, context);
        Arguments<Type> arguments = parameters.convertArguments(this::convertArgument);
        Expression<Type> function = element.getFunction();
        FunctionType functionType = (FunctionType) function.getType();
        List<FormalParameter<Type>> formalParameters = functionType.getParameters();
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
                    if (((Name<Type>) function).getName().equals(invocable.getName())) {
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
            handleError(context, element, String.format("Not supported function type '%s' in '%s'", functionType, context.getElementName()));
            return null;
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
    public String visit(NumericLiteral<Type> element, DMNContext context) {
        return this.nativeFactory.numericLiteral(element.getLexeme());
    }

    @Override
    public String visit(StringLiteral<Type> element, DMNContext context) {
        String lexeme = element.getLexeme();
        String value = StringEscapeUtil.unescapeFEEL(lexeme);
        value = StringEscapeUtil.escapeFEEL(value);
        value = String.format("\"%s\"", value);
        return value;
    }

    @Override
    public String visit(BooleanLiteral<Type> element, DMNContext context) {
        String value = element.getLexeme();
        return this.nativeFactory.booleanLiteral(value);
    }

    @Override
    public String visit(DateTimeLiteral<Type> element, DMNContext context) {
        return this.nativeFactory.dateTimeLiteral(element);
    }

    @Override
    public String visit(NullLiteral<Type> element, DMNContext context) {
        return this.nativeFactory.nullLiteral();
    }

    @Override
    public String visit(ListLiteral<Type> element, DMNContext context) {
        List<Expression<Type>> expressionList = element.getExpressionList();
        String elements = expressionList.stream().map(e -> (String) e.accept(this, context)).collect(Collectors.joining(", "));
        Type elementType = ((ListType) element.getType()).getElementType();
        return this.dmnTransformer.asList(elementType, elements);
    }

    @Override
    public String visit(QualifiedName<Type> element, DMNContext context) {
        if (element.getNames().size() == 1) {
            return nameToJava(element.getNames().get(0), context);
        } else {
            return handleNotSupportedElement(element);
        }
    }

    @Override
    public String visit(Name<Type> element, DMNContext context) {
        String name = element.getName();
        return nameToJava(name, context);
    }

    //
    // Type expressions
    //
    @Override
    public String visit(NamedTypeExpression<Type> element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public String visit(ContextTypeExpression<Type> element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public String visit(RangeTypeExpression<Type> element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public String visit(FunctionTypeExpression<Type> element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public String visit(ListTypeExpression<Type> element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    protected String nameToJava(String name, DMNContext context) {
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
            handleError(context, null, String.format("Missing inputExpression in context of element '%s'", context.getElementName()));
            return null;
        } else {
            // Evaluate as test
            Expression<Type> inputExpression = (Expression) context.getInputExpression();
            return (String) inputExpression.accept(this, context);
        }
    }

    private String toBooleanOr(List<String> operands) {
        if (operands.size() == 1) {
            return operands.get(0);
        } else {
            return this.nativeFactory.makeBuiltinFunctionInvocation("booleanOr", String.join(", ", operands));
        }
    }

    private String makeListTestCondition(String feelOperator, String inputExpressionText, Expression<Type> rightOperand, DMNContext context) {
        String rightOpd = (String) rightOperand.accept(this, context);
        String condition;
        Expression<Type> inputExpression = (Expression) context.getInputExpression();
        String javaOperator = listTestOperator(feelOperator, inputExpression, rightOperand);
        if (StringUtils.isEmpty(javaOperator)) {
            condition = infixExpression(javaOperator, inputExpressionText, rightOpd);
        } else {
            condition = functionalExpression(javaOperator, inputExpressionText, rightOpd);
        }
        return condition;
    }

    protected String handleNotSupportedElement(Element<Type> element) {
        throw new UnsupportedOperationException("FEEL '" + (element == null ? null : element.getClass().getSimpleName()) + "' is not supported in this context");
    }

    protected String makeNavigation(Expression<Type> element, Type sourceType, String source, String memberName, String memberVariableName, DMNContext context) {
        if (sourceType instanceof ImportContextType) {
            ImportContextType importContextType = (ImportContextType) sourceType;
            DRGElementReference<? extends TDRGElement> memberReference = importContextType.getMemberReference(memberName);
            if (memberReference == null) {
                handleError(context, element, String.format("Cannot find reference for '%s'", memberName));
                return null;
            }
            TDRGElement drgElement = memberReference.getElement();
            if (drgElement instanceof TBusinessKnowledgeModel) {
                return this.dmnTransformer.singletonInvocableInstance((TBusinessKnowledgeModel) drgElement);
            } else {
                String javaName = this.dmnTransformer.drgReferenceQualifiedName(memberReference);
                return this.dmnTransformer.lazyEvaluation(memberReference.getElementName(), javaName);
            }
        } else if (sourceType instanceof ItemDefinitionType) {
            Type memberType = ((ItemDefinitionType) sourceType).getMemberType(memberName);
            String javaType = this.dmnTransformer.toNativeType(memberType);
            return this.nativeFactory.makeItemDefinitionAccessor(javaType, source, memberName);
        } else if (sourceType instanceof ContextType) {
            Type memberType = ((ContextType) sourceType).getMemberType(memberName);
            String javaType = this.dmnTransformer.toNativeType(memberType);
            return this.nativeFactory.makeContextAccessor(javaType, source, memberName);
        } else if (sourceType instanceof ListType) {
            String filter = makeNavigation(element, ((ListType) sourceType).getElementType(), "x", memberName, memberVariableName, context);
            return this.nativeFactory.makeCollectionMap(source, filter);
        } else if (sourceType instanceof DateType) {
            return this.nativeFactory.makeBuiltinFunctionInvocation(propertyFunctionName(memberName), source);
        } else if (sourceType instanceof TimeType) {
            return this.nativeFactory.makeBuiltinFunctionInvocation(propertyFunctionName(memberName), source);
        } else if (sourceType instanceof DateTimeType) {
            return this.nativeFactory.makeBuiltinFunctionInvocation(propertyFunctionName(memberName), source);
        } else if (sourceType instanceof DurationType) {
            return this.nativeFactory.makeBuiltinFunctionInvocation(propertyFunctionName(memberName), source);
        } else if (sourceType instanceof RangeType) {
            return String.format("%s.%s", source, rangeGetter(memberName));
        } else if (sourceType instanceof AnyType) {
            // source is Context
            return this.nativeFactory.makeContextSelectExpression(this.dmnTransformer.contextClassName(), source, memberName);
        } else {
            handleError(context, element, String.format("Cannot generate navigation path '%s'", element));
            return null;
        }
    }

    protected String makeCondition(String feelOperator, Expression<Type> leftOperand, Expression<Type> rightOperand, DMNContext context) {
        String leftOpd = (String) leftOperand.accept(this, context);
        String rightOpd = (String) rightOperand.accept(this, context);
        NativeOperator javaOperator = OperatorDecisionTable.javaOperator(feelOperator, leftOperand.getType(), rightOperand.getType());
        return makeCondition(feelOperator, leftOpd, rightOpd, javaOperator);
    }

    protected String makeCondition(String feelOperator, String leftOpd, String rightOpd, NativeOperator javaOperator) {
        if (javaOperator == null) {
            handleError(makeOperatorErrorMessage(feelOperator, leftOpd, rightOpd));
            return null;
        } else {
            if (javaOperator.getCardinality() == 2) {
                if (javaOperator.getNotation() == NativeOperator.Notation.FUNCTIONAL) {
                    if (javaOperator.getAssociativity() == NativeOperator.Associativity.LEFT_RIGHT) {
                        return functionalExpression(javaOperator.getName(), leftOpd, rightOpd);
                    } else {
                        return functionalExpression(javaOperator.getName(), rightOpd, leftOpd);
                    }
                } else {
                    if (javaOperator.getAssociativity() == NativeOperator.Associativity.LEFT_RIGHT) {
                        return infixExpression(javaOperator.getName(), leftOpd, rightOpd);
                    } else {
                        return infixExpression(javaOperator.getName(), rightOpd, leftOpd);
                    }
                }
            } else {
                handleError(makeOperatorErrorMessage(feelOperator, leftOpd, rightOpd));
                return null;
            }
        }
    }

    protected String listTestOperator(String feelOperatorName, Expression<Type> leftOperand, Expression<Type> rightOperand) {
        NativeOperator javaOperator = OperatorDecisionTable.javaOperator(feelOperatorName, rightOperand.getType(), rightOperand.getType());
        if (javaOperator != null) {
            return javaOperator.getName();
        } else {
            handleError(makeOperatorErrorMessage(feelOperatorName, leftOperand, rightOperand));
            return null;
        }
    }

    protected String functionalExpression(String javaOperator, String leftOpd, String rightOpd) {
        String args = String.format("%s, %s", leftOpd, rightOpd);
        return this.nativeFactory.makeBuiltinFunctionInvocation(javaOperator, args);
    }

    protected String infixExpression(String javaOperator, String leftOpd, String rightOpd) {
        return String.format("(%s) %s (%s)", leftOpd, javaOperator, rightOpd);
    }

    protected String rangeGetter(String memberName) {
        memberName = NameUtils.removeSingleQuotes(memberName);
        if ("start included".equalsIgnoreCase(memberName)) {
            return "isStartIncluded()";
        }  else if ("end included".equalsIgnoreCase(memberName)) {
            return "isEndIncluded()";
        } else {
            return this.dmnTransformer.getter(memberName);
        }
    }

    private static String makeOperatorErrorMessage(String feelOperator, Object leftOpd, Object rightOpd) {
        return String.format("Operator '%s' cannot be applied to '%s' and '%s'", feelOperator, leftOpd, rightOpd);
    }
}
