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
package com.gs.dmn.feel.interpreter;

import com.gs.dmn.feel.OperatorDecisionTable;
import com.gs.dmn.feel.analysis.semantics.environment.Conversion;
import com.gs.dmn.feel.analysis.semantics.environment.ConversionKind;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Iterator;
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
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.synthesis.AbstractFEELToJavaVisitor;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.feel.synthesis.FEELTranslatorForInterpreter;
import com.gs.dmn.feel.synthesis.JavaOperator;
import com.gs.dmn.feel.synthesis.type.FEELTypeTranslator;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.LambdaExpression;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.compiler.ClassData;
import com.gs.dmn.runtime.compiler.JavaCompiler;
import com.gs.dmn.runtime.compiler.JavaxToolsCompiler;
import com.gs.dmn.runtime.interpreter.Arguments;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.NamedArguments;
import com.gs.dmn.runtime.interpreter.PositionalArguments;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironmentFactory;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import org.omg.spec.dmn._20180521.model.TBusinessKnowledgeModel;
import org.omg.spec.dmn._20180521.model.TFunctionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

class FEELInterpreterVisitor extends AbstractFEELToJavaVisitor {
    private static final RuntimeEnvironmentFactory runtimeEnvironmentFactory = RuntimeEnvironmentFactory.instance();
    private static final Logger LOGGER = LoggerFactory.getLogger(FEELInterpreterVisitor.class);

    private final DMNInterpreter dmnInterpreter;
    private final FEELLib lib;
    private final FEELTypeTranslator typeTranslator;
    private final FEELTranslator feelTranslator;

//    private final static JavaCompiler JAVA_COMPILER = new JavaAssistCompiler();
    private final static JavaCompiler JAVA_COMPILER = new JavaxToolsCompiler(new File("."));

    FEELInterpreterVisitor(DMNInterpreter dmnInterpreter) {
        super(dmnInterpreter.getBasicDMNTransformer());
        this.dmnInterpreter = dmnInterpreter;
        this.feelTranslator = new FEELTranslatorForInterpreter(dmnInterpreter.getBasicDMNTransformer());
        this.lib = dmnInterpreter.getFeelLib();
        this.typeTranslator = dmnInterpreter.getBasicDMNTransformer().getFEELTypeTranslator();
    }

    @Override
    public Object visit(PositiveUnaryTests element, FEELContext context) {
        List<Boolean> positiveUnaryTests = element.getPositiveUnaryTests().stream().map(put -> (Boolean) put.accept(this, context)).collect(Collectors.toList());
        if (positiveUnaryTests.size() == 1) {
            return positiveUnaryTests.get(0);
        } else {
            return lib.booleanOr(positiveUnaryTests);
        }
    }

    @Override
    public Object visit(NegatedPositiveUnaryTests element, FEELContext context) {
        Boolean positiveUnaryTests = (Boolean) element.getPositiveUnaryTests().accept(this, context);
        return lib.booleanNot(positiveUnaryTests);
    }

    @Override
    public Object visit(SimplePositiveUnaryTests element, FEELContext context) {
        List<Boolean> simplePositiveUnaryTests = element.getSimplePositiveUnaryTests().stream().map(sput -> (Boolean) sput.accept(this, context)).collect(Collectors.toList());
        if (simplePositiveUnaryTests.size() == 1) {
            return simplePositiveUnaryTests.get(0);
        } else {
            return lib.booleanOr(simplePositiveUnaryTests);
        }
    }

    @Override
    public Object visit(NegatedSimplePositiveUnaryTests element, FEELContext context) {
        Boolean simplePositiveUnaryTests = (Boolean) element.getSimplePositiveUnaryTests().accept(this, context);
        return lib.booleanNot(simplePositiveUnaryTests);
    }

    @Override
    public Object visit(Any element, FEELContext context) {
        return Boolean.TRUE;
    }

    @Override
    public Object visit(NullTest element, FEELContext context) {
        Object self = context.lookupRuntimeBinding(DMNToJavaTransformer.INPUT_ENTRY_PLACE_HOLDER);
        return self == null;
    }

    @Override
    public Object visit(ExpressionTest element, FEELContext context) {
        return element.getExpression().accept(this, context);
    }

    @Override
    public Object visit(OperatorTest element, FEELContext context) {
        String operator = element.getOperator();
        Type inputExpressionType = context.getEnvironment().getInputExpressionType();
        Expression endpoint = element.getEndpoint();
        Type endpointType = endpoint.getType();

        try {
            if (endpoint instanceof FunctionInvocation) {
                return endpoint.accept(this, context);
            } else {
                Object self = context.lookupRuntimeBinding(DMNToJavaTransformer.INPUT_ENTRY_PLACE_HOLDER);
                if (operator == null) {
                    if (inputExpressionType.equivalentTo(endpointType)) {
                        return evaluateOperatorTest(element, "=", self, endpoint, context);
                    } else if (endpointType instanceof ListType && inputExpressionType.equivalentTo(((ListType) endpointType).getElementType())) {
                        List endpointValueList = (List)endpoint.accept(this, context);
                        List results = new ArrayList();
                        for(Object endpointValue: endpointValueList) {
                            results.add(evaluateOperatorTest(element, "=", self, inputExpressionType, ((ListType) endpointType).getElementType(), endpointValue));
                        }
                        return lib.or(results);
                    }
                    throw new DMNRuntimeException(String.format("Cannot evaluate test '%s'", element));
                } else {
                    return evaluateOperatorTest(element, operator, self, endpoint, context);
                }
            }
        } catch (Exception e) {
            handleError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    private Object evaluateOperatorTest(Expression element, String operator, Object self, Expression endpointExpression, FEELContext context) throws Exception {
        Object endpointValue = endpointExpression.accept(this, context);
        return evaluateOperatorTest(element, operator, self, context.getEnvironment().getInputExpressionType(), endpointExpression.getType(), endpointValue);
    }

    private Object evaluateOperatorTest(Expression element, String operator, Object self, Type inputExpressionType, Type endpointType, Object endpointValue) throws IllegalAccessException, InvocationTargetException {
        JavaOperator javaOperator = javaOperator(operator, inputExpressionType, endpointType);
        if (javaOperator == null) {
            handleError(String.format("Cannot find method for '%s' '%s'", operator, element));
            return null;
        } else {
            String methodName = javaOperator.getName();
            if (javaOperator.getAssociativity() == JavaOperator.Associativity.LEFT_RIGHT) {
                Class[] argumentTypes = {getClass(self), getClass(endpointValue)};
                Method method = MethodUtils.resolveMethod(methodName, lib.getClass(), argumentTypes);
                if (method == null) {
                    throw new DMNRuntimeException(String.format("Cannot find method '%s' for arguments '%s' and '%s'", methodName, self, endpointValue));
                }
                return method.invoke(lib, self, endpointValue);
            } else {
                Class[] argumentTypes = {getClass(endpointValue), getClass(null)};
                Method method = MethodUtils.resolveMethod(methodName, lib.getClass(), argumentTypes);
                if (method == null) {
                    throw new DMNRuntimeException(String.format("Cannot find method '%s' for arguments '%s' and '%s'", methodName, self, endpointValue));
                }
                return method.invoke(lib, endpointValue, self);
            }
        }
    }

    private Object evaluateBinaryOperator(Expression element, String operator, Expression leftOperand, Expression rightOperand, FEELContext context) throws Exception {
        JavaOperator javaOperator = javaOperator(operator, leftOperand, rightOperand);
        if (javaOperator == null) {
            handleError(String.format("Cannot find method for '%s' '%s'", operator, element));
            return null;
        } else {
            if (javaOperator.getCardinality() == 2) {
                Object leftValue = leftOperand.accept(this, context);
                Object rightValue = rightOperand.accept(this, context);
                if (javaOperator.getNotation() == JavaOperator.Notation.FUNCTIONAL) {
                    if (javaOperator.getAssociativity() == JavaOperator.Associativity.LEFT_RIGHT) {
                        Method method = MethodUtils.resolveMethod(javaOperator.getName(), lib.getClass(), new Class[]{getClass(leftValue), getClass(rightValue)});
                        return method.invoke(lib, leftValue, rightValue);
                    } else {
                        Method method = MethodUtils.resolveMethod(javaOperator.getName(), lib.getClass(), new Class[]{getClass(rightValue), getClass(leftValue)});
                        return method.invoke(lib, rightValue, leftValue);
                    }
                } else {
                    // Infix
                    if (javaOperator.getName().equals("==")) {
                        return leftValue == rightValue;
                    } else if (javaOperator.getName().equals("!=")) {
                        return leftValue != rightValue;
                    } else {
                        handleError(String.format("Cannot evaluate '%s' '%s'", operator, element));
                        return null;
                    }
                }
            } else {
                handleError(String.format("Cannot evaluate '%s' '%s'", operator, element));
                return null;
            }
        }
    }

    protected JavaOperator javaOperator(String feelOperator, Expression leftOperand, Expression rightOperand) {
        Type leftOperandType = leftOperand.getType();
        Type rightOperandType = rightOperand.getType();
        return javaOperator(feelOperator, leftOperandType, rightOperandType);
    }

    private JavaOperator javaOperator(String feelOperator, Type leftOperandType, Type rightOperandType) {
        return OperatorDecisionTable.javaOperator(feelOperator, leftOperandType, rightOperandType);
    }

    private Class<?> getClass(Object leftValue) {
        return leftValue == null ? null : leftValue.getClass();
    }

    @Override
    public Object visit(RangeTest element, FEELContext context) {
        Expression startExpression = element.getStart();
        Expression endExpression = element.getEnd();

        try {
            Object self = context.lookupRuntimeBinding(DMNToJavaTransformer.INPUT_ENTRY_PLACE_HOLDER);
            String leftOperator = element.isOpenStart() ? ">" : ">=";
            String rightOperator = element.isOpenEnd() ? "<" : "<=";

            Object leftCondition = evaluateOperatorTest(element, leftOperator, self, startExpression, context);
            Object rightCondition = evaluateOperatorTest(element, rightOperator, self, endExpression, context);

            return lib.booleanAnd((Boolean) leftCondition, (Boolean) rightCondition);
        } catch (Exception e) {
            handleError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(ListTest element, FEELContext context) {
        try {
            String operator = "=";
            Expression listLiteral = element.getListLiteral();
            if (listLiteral instanceof FunctionInvocation) {
                return listLiteral.accept(this, context);
            } else {
                Object self = context.lookupRuntimeBinding(DMNToJavaTransformer.INPUT_ENTRY_PLACE_HOLDER);
                return evaluateOperatorTest(element, operator, self, listLiteral, context);
            }
        } catch (Exception e) {
            handleError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(FunctionDefinition element, FEELContext context) {
        return element;
    }

    private Object makeLambdaExpression(FunctionDefinition element, FEELContext context) {
        try {
            // Compile
            ClassData classData = JAVA_COMPILER.makeClassData(element, context, dmnTransformer, feelTranslator, lib.getClass().getName());
            Class<?> cls = JAVA_COMPILER.compile(classData);

            // Create instance
            return cls.newInstance();
        } catch (Exception e) {
            throw new DMNRuntimeException("Execution error", e);
        }
    }

    @Override
    public Object visit(FormalParameter element, FEELContext context) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    @Override
    public Object visit(Context element, FEELContext context) {
        List<Pair> entries = element.getEntries().stream().map(e -> (Pair) e.accept(this, context)).collect(Collectors.toList());
        com.gs.dmn.runtime.Context runtimeContext = new com.gs.dmn.runtime.Context();
        for (Pair p : entries) {
            runtimeContext.put(p.getLeft(), p.getRight());
        }
        return runtimeContext;
    }

    @Override
    public Object visit(ContextEntry element, FEELContext context) {
        Object key = element.getKey().accept(this, context);
        Object value = element.getExpression().accept(this, context);
        return new Pair(key, value);
    }

    @Override
    public Object visit(ContextEntryKey element, FEELContext context) {
        return element.getKey();
    }

    @Override
    public Object visit(ForExpression element, FEELContext context) {
        // Transform into equivalent nested for expressions
        int iteratorNo = element.getIterators().size();
        if (iteratorNo > 1) {
            element = element.toNestedForExpression();
        }

        // Evaluate domain
        Iterator iterator = element.getIterators().get(0);
        IteratorDomain expressionDomain = iterator.getDomain();
        Object domain = expressionDomain.accept(this, context);

        // Loop over domain and evaluate body
        FEELContext forContext = FEELContext.makeContext(context.getEnvironment(), runtimeEnvironmentFactory.makeEnvironment(context.getRuntimeEnvironment()));
        List result = new ArrayList<>();
        if (expressionDomain instanceof ExpressionIteratorDomain) {
            for (Object value : (List) domain) {
                forContext.runtimeBind(iterator.getName(), value);
                result.add(element.getBody().accept(this, forContext));
            }
        } else {
            int start = toNumber(((Pair) domain).getLeft());
            int end = toNumber(((Pair) domain).getRight());
            if (start <= end) {
                for(int value = start; value <= end; value++) {
                    forContext.runtimeBind(iterator.getName(), BigDecimal.valueOf(value));
                    result.add(element.getBody().accept(this, forContext));
                }
            } else {
                for(int value = start; value <= end; value--) {
                    forContext.runtimeBind(iterator.getName(), BigDecimal.valueOf(value));
                    result.add(element.getBody().accept(this, forContext));
                }
            }
        }
        for (int i = 1; i <= iteratorNo - 1; i++) {
            result = lib.flattenFirstLevel(result);
        }
        return result;
    }

    private int toNumber(Object number) {
        if (number instanceof BigDecimal) {
            return ((BigDecimal) number).intValue();
        }
        throw new DMNRuntimeException(String.format("Cannot convert '%s' to integer", number));
    }

    @Override
    public Object visit(Iterator element, FEELContext context) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    @Override
    public Object visit(ExpressionIteratorDomain element, FEELContext context) {
        Expression expressionDomain = element.getExpression();
        List domain = null;
        if (expressionDomain instanceof Name) {
            String name = ((Name) expressionDomain).getName();
            domain = (List) context.getRuntimeEnvironment().lookupBinding(name);
        } else if (expressionDomain instanceof RangeTest) {
            RangeTest test = (RangeTest) expressionDomain;
            if (test.getType() instanceof RangeType && ((RangeType) test.getType()).getRangeType().conformsTo(NumberType.NUMBER)) {
                Object start = test.getStart().accept(this, context);
                Object end = test.getEnd().accept(this, context);
                domain = lib.rangeToList(test.isOpenStart(), start, test.isOpenEnd(), end);
            } else {
                throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
            }
        } else if (expressionDomain instanceof ListTest) {
            ListTest test = (ListTest) expressionDomain;
            domain = (List) test.getListLiteral().accept(this, context);
        } else if (expressionDomain instanceof ListLiteral) {
            domain = (List) expressionDomain.accept(this, context);
        } else {
            throw new UnsupportedOperationException(String.format("FEEL '%s' is not supported yet with domain '%s'",
                    element.getClass().getSimpleName(), expressionDomain.getClass().getSimpleName()));
        }
        return domain;
    }

    @Override
    public Object visit(RangeIteratorDomain element, FEELContext context) {
        Object start = element.getStart().accept(this, context);
        Object end = element.getEnd().accept(this, context);
        return new Pair(start, end);
    }

    @Override
    public Object visit(IfExpression element, FEELContext context) {
        Object condition = element.getCondition().accept(this, context);
        if (condition == Boolean.TRUE) {
            return element.getThenExpression().accept(this, context);
        } else {
            return element.getElseExpression().accept(this, context);
        }
    }

    @Override
    public Object visit(QuantifiedExpression element, FEELContext context) {
        // Transform into nested for expressions
        ForExpression equivalentForExpression = element.toForExpression();
        // Evaluate
        List result = (List) equivalentForExpression.accept(this, context);
        // Apply predicate
        String predicate = element.getPredicate();
        if ("some".equals(predicate)) {
            return lib.or(result);
        } else if ("every".equals(predicate)) {
            return lib.and(result);
        } else {
            throw new UnsupportedOperationException("Predicate '" + predicate + "' is not supported yet");
        }
    }

    @Override
    public Object visit(FilterExpression element, FEELContext context) {
        Type sourceType = element.getSource().getType();
        Type filterType = element.getFilter().getType();
        Object source = element.getSource().accept(this, context);
        if (!(sourceType instanceof ListType)) {
            source = Arrays.asList(source);
        }
        if (filterType == BooleanType.BOOLEAN) {
            List<Object> result = new ArrayList<>();
            for (Object item : (List) source) {
                FEELContext filterContext = makeFilterContext(context, item, FilterExpression.FILTER_PARAMETER_NAME);
                Boolean filterValue = (Boolean) element.getFilter().accept(this, filterContext);
                if (filterValue != null && filterValue) {
                    result.add(item);
                }
            }
            return result;
        } else if (filterType == NumberType.NUMBER) {
            Object filterValue = element.getFilter().accept(this, context);
            return lib.elementAt((List) source, filterValue);
        } else {
            throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
        }
    }

    private FEELContext makeFilterContext(FEELContext context, Object item, String filterParameterName) {
        RuntimeEnvironment runtimeEnvironment = runtimeEnvironmentFactory.makeEnvironment(context.getRuntimeEnvironment());
        runtimeEnvironment.bind(filterParameterName, item);
        return FEELContext.makeContext(context.getEnvironment(), runtimeEnvironment);
    }

    @Override
    public Object visit(InstanceOfExpression element, FEELContext context) {
        try {
            Object opd = element.getValue().accept(this, context);
            if (opd != null) {
                Class opdClass = opd.getClass();
                String qTypeName = element.getQTypeName().getQualifiedName();
                String javaType = typeTranslator.toQualifiedJavaType(qTypeName);
                Class cls = Class.forName(javaType);
                return cls.isAssignableFrom(opdClass);
            } else {
                return true;
            }
        } catch (Exception e) {
            handleError("Cannot evaluate instanceof", e);
            return null;
        }
    }

    @Override
    public Object visit(ExpressionList element, FEELContext context) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    @Override
    public Object visit(Disjunction element, FEELContext context) {
        try {
            Object leftOperand = element.getLeftOperand().accept(this, context);
            Object rightOperand = element.getRightOperand().accept(this, context);
            return lib.or(Arrays.asList((Boolean) leftOperand, (Boolean) rightOperand));
        } catch (Exception e) {
            handleError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(Conjunction element, FEELContext context) {
        try {
            Object leftOperand = element.getLeftOperand().accept(this, context);
            Object rightOperand = element.getRightOperand().accept(this, context);
            return lib.and(Arrays.asList((Boolean) leftOperand, (Boolean) rightOperand));
        } catch (Exception e) {
            handleError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(LogicNegation element, FEELContext context) {
        Object leftOperand = element.getLeftOperand().accept(this, context);
        if (leftOperand instanceof Boolean || leftOperand == null) {
            return lib.booleanNot((Boolean) leftOperand);
        } else {
            handleError(String.format("Expected boolean operand, found '%s'", leftOperand.getClass().getName()));
            return null;
        }
    }

    @Override
    public Object visit(Relational element, FEELContext context) {
        try {
            return evaluateBinaryOperator(element, element.getOperator(), element.getLeftOperand(), element.getRightOperand(), context);
        } catch (Exception e) {
            handleError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(BetweenExpression element, FEELContext context) {
        try {
            Object value = element.getValue().accept(this, context);
            Object leftEndpoint = element.getLeftEndpoint().accept(this, context);
            Object rightEndpoint = element.getRightEndpoint().accept(this, context);
            return lib.booleanAnd(lib.numericLessEqualThan(leftEndpoint, value), lib.numericLessEqualThan(value, rightEndpoint));
        } catch (Exception e) {
            handleError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(InExpression element, FEELContext context) {
        Expression valueExp = element.getValue();
        Object value = valueExp.accept(this, context);

        Environment inEnvironment = environmentFactory.makeEnvironment(context.getEnvironment(), valueExp);
        RuntimeEnvironment inRuntimeEnvironment = runtimeEnvironmentFactory.makeEnvironment(context.getRuntimeEnvironment());
        FEELContext inParams = FEELContext.makeContext(inEnvironment, inRuntimeEnvironment);
        inParams.runtimeBind(DMNToJavaTransformer.INPUT_ENTRY_PLACE_HOLDER, value);

        Boolean result = false;
        List<PositiveUnaryTest> positiveUnaryTests = element.getTests();
        for (PositiveUnaryTest positiveUnaryTest : positiveUnaryTests) {
            Boolean test = false;
            if (positiveUnaryTest instanceof ListTest) {
                List list = (List) ((ListTest) positiveUnaryTest).getListLiteral().accept(this, inParams);
                return lib.listContains(list, value);
            } else {
                test = (Boolean) positiveUnaryTest.accept(this, inParams);
            }
            result = lib.booleanOr(result, test);
        }
        return result;
    }

    @Override
    public Object visit(Addition element, FEELContext context) {
        try {
            return evaluateBinaryOperator(element, element.getOperator(), element.getLeftOperand(), element.getRightOperand(), context);
        } catch (Exception e) {
            handleError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(Multiplication element, FEELContext context) {
        try {
            return evaluateBinaryOperator(element, element.getOperator(), element.getLeftOperand(), element.getRightOperand(), context);
        } catch (Exception e) {
            handleError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(Exponentiation element, FEELContext context) {
        try {
            return evaluateBinaryOperator(element, element.getOperator(), element.getLeftOperand(), element.getRightOperand(), context);
        } catch (Exception e) {
            handleError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(ArithmeticNegation element, FEELContext context) {
        Object leftOperand = element.getLeftOperand().accept(this, context);
        return lib.numericUnaryMinus(leftOperand);
    }

    @Override
    public Object visit(FunctionInvocation element, FEELContext context) {
        Arguments arguments = (Arguments) element.getParameters().accept(this, context);
        Expression function = element.getFunction();
        FunctionType functionType = (FunctionType) element.getFunction().getType();
        List<FormalParameter> formalParameters = functionType.getParameters();
        List<Object> argList = arguments.argumentList(formalParameters);
        if (!argList.isEmpty()) {
            argList = convertArguments(argList, element.getParameterConversions());
        }
        if (function instanceof Name || function instanceof QualifiedName && ((QualifiedName) function).getNames().size() == 1) {
            String feelFunctionName = functionName(function);
            Object binding = context.lookupRuntimeBinding(feelFunctionName);
            if (binding instanceof TBusinessKnowledgeModel) {
                return dmnInterpreter.evaluateBKM((TBusinessKnowledgeModel) binding, argList, context);
            } else if (binding instanceof TFunctionDefinition) {
                return dmnInterpreter.evaluateFunctionDefinition((TFunctionDefinition) binding, argList, context);
            } else if (binding instanceof FunctionDefinition) {
                FunctionDefinition functionDefinitionBinding = (FunctionDefinition) binding;
                if (functionType instanceof FEELFunctionType) {
                    // Use the one with inferred types
                    functionDefinitionBinding = ((FEELFunctionType) functionType).getFunctionDefinition();
                }
                return evaluateFunctionDefinition(functionDefinitionBinding, argList, context);
            } else if (binding instanceof LambdaExpression) {
                return evaluateLambdaExpression((LambdaExpression) binding, argList, context);
            } else {
                String javaFunctionName = javaFunctionName(feelFunctionName);
                if ("sort".equals(javaFunctionName)) {
                    FunctionDefinition functionDefinition = (FunctionDefinition) argList.get(1);
                    Object lambdaExpression = makeLambdaExpression(functionDefinition, context);
                    argList.set(1, lambdaExpression);
                }
                return evaluateBuiltInFunction(lib, javaFunctionName, argList);
            }
        } else {
            Object binding = function.accept(this, context);
            if (binding instanceof TBusinessKnowledgeModel) {
                return dmnInterpreter.evaluateBKM((TBusinessKnowledgeModel) binding, argList, context);
            } else if (binding instanceof TFunctionDefinition) {
                return dmnInterpreter.evaluateFunctionDefinition((TFunctionDefinition) binding, argList, context);
            } else if (binding instanceof FunctionDefinition) {
                FunctionDefinition functionDefinitionBinding = (FunctionDefinition) binding;
                if (functionType instanceof FEELFunctionType) {
                    // Use the one with inferred types
                    functionDefinitionBinding = ((FEELFunctionType) functionType).getFunctionDefinition();
                }
                return evaluateFunctionDefinition(functionDefinitionBinding, argList, context);
            } else if (binding instanceof LambdaExpression) {
                return evaluateLambdaExpression((LambdaExpression) binding, argList, context);
            } else {
                throw new DMNRuntimeException(String.format("Not supported yet %s", binding.getClass().getSimpleName()));
            }
        }
    }

    public Object evaluateFunctionDefinition(FunctionDefinition functionDefinition, List<Object> argList, FEELContext context) {
        // Create new environments and bind parameters
        Environment functionEnvironment = environmentFactory.makeEnvironment(context.getEnvironment());
        RuntimeEnvironment functionRuntimeEnvironment = runtimeEnvironmentFactory.makeEnvironment(context.getRuntimeEnvironment());
        FEELContext functionContext = FEELContext.makeContext(functionEnvironment, functionRuntimeEnvironment);
        List<FormalParameter> formalParameterList = functionDefinition.getFormalParameters();
        for (int i = 0; i < formalParameterList.size(); i++) {
            FormalParameter param = formalParameterList.get(i);
            String name = param.getName();
            Type type = param.getType();
            Object value = argList.get(i);
            functionEnvironment.addDeclaration(environmentFactory.makeVariableDeclaration(name, type));
            functionRuntimeEnvironment.bind(name, value);
        }

        // Execute function body
        Expression body = functionDefinition.getBody();
        Object output;
        if (body == null) {
            output = null;
        } else {
            output = body.accept(this, functionContext);
        }

        return output;
    }

    @Override
    protected Object convertArgument(Object value, Conversion conversion) {
        ConversionKind kind = conversion.getKind();
        if (kind == ConversionKind.NONE) {
            return value;
        } else if (kind == ConversionKind.ELEMENT_TO_LIST) {
            return lib.asList(value);
        } else if (kind == ConversionKind.LIST_TO_ELEMENT) {
            return lib.asElement((List)value);
        }
        return value;
    }

    private Object evaluateLambdaExpression(LambdaExpression binding, List<Object> argList, FEELContext context) {
        String functionName = "apply";
        return evaluateMethod(binding, binding.getClass(), functionName, argList);
    }

    private Object evaluateBuiltInFunction(FEELLib lib, String functionName, List<Object> argList) {
        return evaluateMethod(lib, lib.getClass(), functionName, argList);
    }

    private Object evaluateMethod(Object object, Class<?> cls, String functionName, List<Object> argList) {
        try {
            Class[] argTypes = new Class[argList.size()];
            for (int i = 0; i < argList.size(); i++) {
                argTypes[i] = getClass(argList.get(i));
            }
            Method declaredMethod = MethodUtils.resolveMethod(functionName, cls, argTypes);
            Object[] args = makeArgs(declaredMethod, argList);
            return declaredMethod.invoke(object, args);
        } catch (Exception e) {
            handleError(String.format("Cannot evaluate function '%s'", functionName), e);
            return null;
        }
    }

    private Object[] makeArgs(Method declaredMethod, List<Object> argList) {
        if (declaredMethod.isVarArgs()) {
            int parameterCount = declaredMethod.getParameterCount();
            int mandatoryParameterCount = parameterCount - 1;
            Object[] args = new Object[parameterCount];
            for (int i = 0; i < mandatoryParameterCount; i++) {
                args[i] = argList.get(i);
            }
            args[parameterCount - 1] = argList.subList(mandatoryParameterCount, argList.size()).toArray();
            return args;
        } else {
            return argList.toArray();
        }
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
    public Object visit(PathExpression element, FEELContext context) {
        Expression source = element.getSource();
        Type sourceType = source.getType();
        Object sourceValue = source.accept(this, context);
        String member = element.getMember();

        if (sourceType instanceof ListType) {
            List result = new ArrayList();
            if (sourceValue == null) {
                return null;
            }
            for (Object obj : (List) sourceValue) {
                result.add(navigate(element, ((ListType) sourceType).getElementType(), obj, member));
            }
            return result;
        } else {
            return navigate(element, sourceType, sourceValue, member);
        }
    }

    private Object navigate(PathExpression element, Type sourceType, Object source, String member) {
        try {
            if (sourceType instanceof ItemDefinitionType) {
                List<String> aliases = ((ItemDefinitionType) sourceType).getAliases(member);
                if (source instanceof com.gs.dmn.runtime.Context) {
                    return ((com.gs.dmn.runtime.Context) source).get(member, aliases.toArray());
                } else {
                    String getterName = dmnTransformer.getterName(member);
                    Method method = MethodUtils.resolveMethod(getterName, source.getClass(), new Class[]{});
                    return method.invoke(source);
                }
            } else if (sourceType instanceof ContextType) {
                List<String> aliases = ((ContextType) sourceType).getAliases(member);
                return ((com.gs.dmn.runtime.Context) source).get(member, aliases.toArray());
            } else if (sourceType instanceof DateType) {
                return evaluateDateTimeMember(source, member);
            } else if (sourceType instanceof TimeType) {
                return evaluateDateTimeMember(source, member);
            } else if (sourceType instanceof DateTimeType) {
                return evaluateDateTimeMember(source, member);
            } else if (sourceType instanceof DurationType) {
                return evaluateDateTimeMember(source, member);
            } else {
                handleError(String.format("Cannot evaluate '%s'.", element));
                return null;
            }
        } catch (Exception e) {
            handleError(String.format("Cannot evaluate '%s'.", element), e);
            return null;
        }
    }

    private Object evaluateDateTimeMember(Object source, String member) {
        if ("year".equals(member)) {
            return lib.year(lib.toDate(source));
        } else if ("month".equals(member)) {
            return lib.month(lib.toDate(source));
        } else if ("day".equals(member)) {
            return lib.day(lib.toDate(source));
        } else if ("hour".equals(member)) {
            return lib.hour(lib.toTime(source));
        } else if ("minute".equals(member)) {
            return lib.minute(lib.toTime(source));
        } else if ("second".equals(member)) {
            return lib.second(lib.toTime(source));
        } else if ("time offset".equals(member)) {
            return lib.timeOffset(lib.toTime(source));
        } else if ("timezone".equals(member)) {
            return lib.timezone(lib.toTime(source));

        } else if ("years".equals(member)) {
            return lib.years(source);
        } else if ("months".equals(member)) {
            return lib.months(source);
        } else if ("days".equals(member)) {
            return lib.days(source);
        } else if ("hours".equals(member)) {
            return lib.hours(source);
        } else if ("minutes".equals(member)) {
            return lib.minutes(source);
        } else if ("seconds".equals(member)) {
            return lib.seconds(source);
        } else {
            throw new DMNRuntimeException(String.format("Cannot resolve method '%s' for date time", member));
        }
    }

    @Override
    public Object visit(BooleanLiteral element, FEELContext context) {
        return Boolean.parseBoolean(element.getValue());
    }

    @Override
    public Object visit(DateTimeLiteral element, FEELContext context) {
        Type type = element.getType();
        String literal = stripQuotes(element.getValue());
        if (type == DateType.DATE) {
            return lib.date(literal);
        } else if (type == TimeType.TIME) {
            return lib.time(literal);
        } else if (type == DateTimeType.DATE_AND_TIME) {
            return lib.dateAndTime(literal);
        } else if (type == DurationType.DAYS_AND_TIME_DURATION || type == DurationType.YEARS_AND_MONTHS_DURATION) {
            return lib.duration(literal);
        } else {
            handleError(String.format("Illegal date time literal '%s'", element));
            return null;
        }
    }

    @Override
    public Object visit(NullLiteral element, FEELContext context) {
        return null;
    }

    @Override
    public Object visit(NumericLiteral element, FEELContext context) {
        return lib.number(element.getValue());
    }

    @Override
    public Object visit(StringLiteral element, FEELContext context) {
        String value = element.getValue();
        return stripQuotes(value);
    }

    @Override
    public Object visit(ListLiteral element, FEELContext context) {
        List<Expression> expressionList = element.getExpressionList();
        List result = new ArrayList();
        for (Expression exp : expressionList) {
            Object value = exp.accept(this, context);
            result.add(value);
        }
        return result;
    }

    @Override
    public Object visit(QualifiedName element, FEELContext context) {
        if (element.getNames().size() == 1) {
            String name = element.getNames().get(0);
            if (name.equals(DMNToJavaTransformer.INPUT_ENTRY_PLACE_HOLDER)) {
                return context.getEnvironment().getInputExpression().accept(this, context);
            } else {
                return context.lookupRuntimeBinding(name);
            }
        } else {
            throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
        }
    }

    @Override
    public Object visit(Name element, FEELContext context) {
        String variableName = element.getName();
        return context.lookupRuntimeBinding(variableName);
    }

    private String stripQuotes(String value) {
        return value.substring(1, value.length() - 1);
    }

    private void handleError(String message) {
        LOGGER.error(message);
        throw new DMNRuntimeException(message);
    }

    private void handleError(String message, Exception e) {
        LOGGER.error(message, e);
        throw new DMNRuntimeException(message, e);
    }

}
