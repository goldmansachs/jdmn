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
package com.gs.dmn.feel.interpreter;

import com.gs.dmn.feel.OperatorDecisionTable;
import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Iterator;
import com.gs.dmn.feel.analysis.syntax.ast.expression.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Addition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.ArithmeticNegation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Exponentiation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Multiplication;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.BetweenExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.InExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.Relational;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.Context;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.Conjunction;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.Disjunction;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.LogicNegation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.textual.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.ContextTypeExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.FunctionTypeExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.ListTypeExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.NamedTypeExpression;
import com.gs.dmn.feel.analysis.syntax.ast.test.*;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.lib.StringEscapeUtil;
import com.gs.dmn.feel.synthesis.AbstractFEELToJavaVisitor;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.feel.synthesis.FEELTranslatorForInterpreter;
import com.gs.dmn.feel.synthesis.NativeOperator;
import com.gs.dmn.runtime.Range;
import com.gs.dmn.runtime.*;
import com.gs.dmn.runtime.compiler.ClassData;
import com.gs.dmn.runtime.compiler.JavaCompiler;
import com.gs.dmn.runtime.compiler.JavaxToolsCompiler;
import com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor;
import com.gs.dmn.runtime.external.JavaFunctionInfo;
import com.gs.dmn.runtime.interpreter.*;
import com.gs.dmn.transformation.basic.ImportContextType;
import org.omg.spec.dmn._20191111.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.gs.dmn.feel.analysis.syntax.ast.expression.textual.ForExpression.PARTIAL_PARAMETER_NAME;
import static com.gs.dmn.transformation.AbstractDMNToNativeTransformer.INPUT_ENTRY_PLACE_HOLDER;

class FEELInterpreterVisitor<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractFEELToJavaVisitor {
    private static final Logger LOGGER = LoggerFactory.getLogger(FEELInterpreterVisitor.class);

    // private static final JavaCompiler JAVA_COMPILER = new JavaAssistCompiler();
    private static final JavaCompiler JAVA_COMPILER = new JavaxToolsCompiler();

    private final DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> dmnInterpreter;
    private final FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> lib;
    private final FEELTranslator feelTranslator;
    private final DefaultExternalFunctionExecutor externalFunctionExecutor;
    private final TypeConverter typeConverter;

    FEELInterpreterVisitor(DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> dmnInterpreter) {
        super(dmnInterpreter.getBasicDMNTransformer());
        this.dmnInterpreter = dmnInterpreter;
        this.typeConverter = dmnInterpreter.getTypeConverter();
        this.feelTranslator = new FEELTranslatorForInterpreter(dmnInterpreter.getBasicDMNTransformer());
        this.lib = dmnInterpreter.getFeelLib();
        this.externalFunctionExecutor = new DefaultExternalFunctionExecutor();
    }

    @Override
    public Object visit(PositiveUnaryTests element, DMNContext context) {
        List<Boolean> positiveUnaryTests = element.getPositiveUnaryTests().stream().map(put -> (Boolean) put.accept(this, context)).collect(Collectors.toList());
        if (positiveUnaryTests.size() == 1) {
            return positiveUnaryTests.get(0);
        } else {
            return this.lib.booleanOr((List) positiveUnaryTests);
        }
    }

    @Override
    public Object visit(NegatedPositiveUnaryTests element, DMNContext context) {
        Boolean positiveUnaryTests = (Boolean) element.getPositiveUnaryTests().accept(this, context);
        return this.lib.booleanNot(positiveUnaryTests);
    }

    @Override
    public Object visit(Any element, DMNContext context) {
        return Boolean.TRUE;
    }

    @Override
    public Object visit(NullTest element, DMNContext context) {
        Object self = context.lookupBinding(INPUT_ENTRY_PLACE_HOLDER);
        return self == null;
    }

    @Override
    public Object visit(ExpressionTest element, DMNContext context) {
        return element.getExpression().accept(this, context);
    }

    @Override
    public Object visit(OperatorRange element, DMNContext context) {
        String operator = element.getOperator();
        Type inputExpressionType = context.getInputExpressionType();
        Expression endpoint = element.getEndpoint();
        Type endpointType = endpoint.getType();

        try {
            if (endpoint instanceof FunctionInvocation) {
                return endpoint.accept(this, context);
            } else {
                Object self = context.lookupBinding(INPUT_ENTRY_PLACE_HOLDER);
                if (operator == null) {
                    if (Type.equivalentTo(inputExpressionType, endpointType)) {
                        return evaluateOperatorRange(element, "=", self, endpoint, context);
                    } else if (endpointType instanceof ListType && Type.equivalentTo(inputExpressionType, ((ListType) endpointType).getElementType())) {
                        List endpointValueList = (List)endpoint.accept(this, context);
                        List results = new ArrayList();
                        for(Object endpointValue: endpointValueList) {
                            results.add(evaluateOperatorRange(element, "=", self, inputExpressionType, ((ListType) endpointType).getElementType(), endpointValue));
                        }
                        return this.lib.or(results);
                    }
                    throw new DMNRuntimeException(String.format("Cannot evaluate test '%s'", element));
                } else {
                    return evaluateOperatorRange(element, operator, self, endpoint, context);
                }
            }
        } catch (Exception e) {
            this.errorHandler.reportError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    private Object evaluateOperatorRange(Expression element, String operator, Object self, Expression endpointExpression, DMNContext context) throws Exception {
        Object endpointValue = endpointExpression.accept(this, context);
        if (context.isExpressionContext()) {
            if (operator == null || "=".equals(operator)) {
                return new Range(true, endpointValue, true, endpointValue);
            } else if ("<".equals(operator)) {
                return new Range(false, null, false, endpointValue);
            } else if ("<=".equals(operator)) {
                return new Range(false, null, true, endpointValue);
            } else if (">".equals(operator)) {
                return new Range(false, endpointValue, false, null);
            } else if (">=".equals(operator)) {
                return new Range(true, endpointValue, false, null);
            } else {
                throw new DMNRuntimeException(String.format("Unknown operator '%s'", operator));
            }
        } else {
            return evaluateOperatorRange(element, operator, self, context.getInputExpressionType(), endpointExpression.getType(), endpointValue);
        }
    }

    private Object evaluateOperatorRange(Expression element, String operator, Object self, Type inputExpressionType, Type endpointType, Object endpointValue) throws IllegalAccessException, InvocationTargetException {
        NativeOperator javaOperator = javaOperator(operator, inputExpressionType, endpointType);
        if (javaOperator == null) {
            this.errorHandler.reportError(String.format("Cannot find method for '%s' '%s'", operator, element));
            return null;
        } else {
            String methodName = javaOperator.getName();
            if (javaOperator.getAssociativity() == NativeOperator.Associativity.LEFT_RIGHT) {
                Class[] argumentTypes = {getClass(self), getClass(endpointValue)};
                Method method = MethodUtils.resolveMethod(methodName, this.lib.getClass(), argumentTypes);
                if (method == null) {
                    throw new DMNRuntimeException(String.format("Cannot find method '%s' for arguments '%s' and '%s'", methodName, self, endpointValue));
                }
                return method.invoke(this.lib, self, endpointValue);
            } else {
                Class[] argumentTypes = {getClass(endpointValue), getClass(null)};
                Method method = MethodUtils.resolveMethod(methodName, this.lib.getClass(), argumentTypes);
                if (method == null) {
                    throw new DMNRuntimeException(String.format("Cannot find method '%s' for arguments '%s' and '%s'", methodName, self, endpointValue));
                }
                return method.invoke(this.lib, endpointValue, self);
            }
        }
    }

    private Object evaluateBinaryOperator(Expression element, String operator, Expression leftOperand, Expression rightOperand, DMNContext context) throws Exception {
        NativeOperator javaOperator = javaOperator(operator, leftOperand, rightOperand);
        if (javaOperator == null) {
            this.errorHandler.reportError(String.format("Cannot find method for '%s' '%s'", operator, element));
            return null;
        } else {
            if (javaOperator.getCardinality() == 2) {
                Object leftValue = leftOperand.accept(this, context);
                Object rightValue = rightOperand.accept(this, context);
                if (javaOperator.getNotation() == NativeOperator.Notation.FUNCTIONAL) {
                    if (javaOperator.getAssociativity() == NativeOperator.Associativity.LEFT_RIGHT) {
                        Method method = MethodUtils.resolveMethod(javaOperator.getName(), this.lib.getClass(), new Class[]{getClass(leftValue), getClass(rightValue)});
                        return method.invoke(this.lib, leftValue, rightValue);
                    } else {
                        Method method = MethodUtils.resolveMethod(javaOperator.getName(), this.lib.getClass(), new Class[]{getClass(rightValue), getClass(leftValue)});
                        return method.invoke(this.lib, rightValue, leftValue);
                    }
                } else {
                    // Infix
                    if (javaOperator.getName().equals("==")) {
                        return leftValue == rightValue;
                    } else if (javaOperator.getName().equals("!=")) {
                        return leftValue != rightValue;
                    } else {
                        this.errorHandler.reportError(String.format("Cannot evaluate '%s' '%s'", operator, element));
                        return null;
                    }
                }
            } else {
                this.errorHandler.reportError(String.format("Cannot evaluate '%s' '%s'", operator, element));
                return null;
            }
        }
    }

    protected NativeOperator javaOperator(String feelOperator, Expression leftOperand, Expression rightOperand) {
        Type leftOperandType = leftOperand.getType();
        Type rightOperandType = rightOperand.getType();
        return javaOperator(feelOperator, leftOperandType, rightOperandType);
    }

    private NativeOperator javaOperator(String feelOperator, Type leftOperandType, Type rightOperandType) {
        return OperatorDecisionTable.javaOperator(feelOperator, leftOperandType, rightOperandType);
    }

    private Class<?> getClass(Object leftValue) {
        return leftValue == null ? null : leftValue.getClass();
    }

    @Override
    public Object visit(EndpointsRange element, DMNContext context) {
        Expression startExpression = element.getStart();
        Expression endExpression = element.getEnd();

        try {
            Object self = context.lookupBinding(INPUT_ENTRY_PLACE_HOLDER);
            String leftOperator = element.isOpenStart() ? ">" : ">=";
            String rightOperator = element.isOpenEnd() ? "<" : "<=";

            if (self == null) {
                // Evaluate as range
                Object startValue = element.getStart().accept(this, context);
                Object endValue = element.getEnd().accept(this, context);
                return new Range(!element.isOpenStart(), startValue, !element.isOpenEnd(), endValue);
            } else {
                // Evaluate as test
                Object leftCondition = evaluateOperatorRange(element, leftOperator, self, startExpression, context);
                Object rightCondition = evaluateOperatorRange(element, rightOperator, self, endExpression, context);

                return this.lib.booleanAnd(leftCondition, rightCondition);
            }
        } catch (Exception e) {
            this.errorHandler.reportError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(ListTest element, DMNContext context) {
        try {
            ListLiteral listLiteral = element.getListLiteral();
            Type listType = listLiteral.getType();
            Type listElementType = ((ListType) listType).getElementType();
            Type inputExpressionType = context.getInputExpressionType();
            Object self = context.lookupBinding(INPUT_ENTRY_PLACE_HOLDER);

            Object result;
            if (Type.conformsTo(inputExpressionType, listType)) {
                String operator = "=";
                return evaluateOperatorRange(element, operator, self, listLiteral, context);
            } else if (Type.conformsTo(inputExpressionType, listElementType)) {
                List list = (List) listLiteral.accept(this, context);
                result = this.lib.listContains(list, self);
            } else if (listElementType instanceof RangeType && Type.conformsTo(inputExpressionType, ((RangeType) listElementType).getRangeType())) {
                List list = (List) listLiteral.accept(this, context);
                result = this.lib.listContains(list, true);
            } else {
                throw new SemanticError(element, String.format("Cannot compare '%s', '%s'", inputExpressionType, listType));
            }

            return result;
        } catch (Exception e) {
            this.errorHandler.reportError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(FunctionDefinition element, DMNContext context) {
        return element;
    }

    private Object makeLambdaExpression(FunctionDefinition element, DMNContext context) {
        try {
            // Compile
            ClassData classData = JAVA_COMPILER.makeClassData(element, context, this.dmnTransformer, this.feelTranslator, this.lib.getClass().getName());
            Class<?> cls = JAVA_COMPILER.compile(classData);

            // Create instance
            return cls.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Execution error for FunctionDefinition %s", element.toString()), e);
        }
    }

    @Override
    public Object visit(FormalParameter element, DMNContext context) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    @Override
    public Object visit(Context element, DMNContext parentContext) {
        DMNContext entryContext = this.dmnTransformer.makeLocalContext(parentContext);
        List<Pair> entries = element.getEntries().stream().map(e -> (Pair) e.accept(this, entryContext)).collect(Collectors.toList());
        com.gs.dmn.runtime.Context runtimeContext = new com.gs.dmn.runtime.Context();
        for (Pair p : entries) {
            runtimeContext.put(p.getLeft(), p.getRight());
        }
        return runtimeContext;
    }

    @Override
    public Object visit(ContextEntry element, DMNContext context) {
        Object key = element.getKey().accept(this, context);
        Object value = element.getExpression().accept(this, context);
        context.addDeclaration(this.dmnTransformer.getEnvironmentFactory().makeVariableDeclaration((String) key, element.getExpression().getType()));
        context.bind((String) key, value);
        return new Pair<>(key, value);
    }

    @Override
    public Object visit(ContextEntryKey element, DMNContext context) {
        return element.getKey();
    }

    @Override
    public Object visit(ForExpression element, DMNContext context) {
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
        DMNContext forContext = this.dmnTransformer.makeForContext(element, context);
        List result = new ArrayList<>();
        forContext.bind(PARTIAL_PARAMETER_NAME, result);
        if (expressionDomain instanceof ExpressionIteratorDomain) {
            for (Object value : (List) domain) {
                forContext.bind(iterator.getName(), value);
                result.add(element.getBody().accept(this, forContext));
            }
        } else {
            int start = toNumber(((Pair) domain).getLeft());
            int end = toNumber(((Pair) domain).getRight());
            if (start <= end) {
                for(int value = start; value <= end; value++) {
                    forContext.bind(iterator.getName(), BigDecimal.valueOf(value));
                    result.add(element.getBody().accept(this, forContext));
                }
            } else {
                for(int value = start; value >= end; value--) {
                    forContext.bind(iterator.getName(), BigDecimal.valueOf(value));
                    result.add(element.getBody().accept(this, forContext));
                }
            }
        }
        for (int i = 1; i <= iteratorNo - 1; i++) {
            result = this.lib.flattenFirstLevel(result);
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
    public Object visit(Iterator element, DMNContext context) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    @Override
    public Object visit(ExpressionIteratorDomain element, DMNContext context) {
        Expression expressionDomain = element.getExpression();
        List domain;
        if (expressionDomain instanceof Name) {
            String name = ((Name) expressionDomain).getName();
            domain = (List) context.lookupBinding(name);
        } else if (expressionDomain instanceof EndpointsRange) {
            EndpointsRange test = (EndpointsRange) expressionDomain;
            if (test.getType() instanceof RangeType && Type.conformsTo(((RangeType) test.getType()).getRangeType(), NumberType.NUMBER)) {
                Object start = test.getStart().accept(this, context);
                Object end = test.getEnd().accept(this, context);
                domain = this.lib.rangeToList(test.isOpenStart(), (NUMBER) start, test.isOpenEnd(), (NUMBER) end);
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
    public Object visit(RangeIteratorDomain element, DMNContext context) {
        Object start = element.getStart().accept(this, context);
        Object end = element.getEnd().accept(this, context);
        return new Pair<>(start, end);
    }

    @Override
    public Object visit(IfExpression element, DMNContext context) {
        Object condition = element.getCondition().accept(this, context);
        if (condition == Boolean.TRUE) {
            return element.getThenExpression().accept(this, context);
        } else {
            return element.getElseExpression().accept(this, context);
        }
    }

    @Override
    public Object visit(QuantifiedExpression element, DMNContext context) {
        // Transform into nested for expressions
        ForExpression equivalentForExpression = element.toForExpression();
        // Evaluate
        List result = (List) equivalentForExpression.accept(this, context);
        // Apply predicate
        String predicate = element.getPredicate();
        if ("some".equals(predicate)) {
            return this.lib.or(result);
        } else if ("every".equals(predicate)) {
            return this.lib.and(result);
        } else {
            throw new UnsupportedOperationException("Predicate '" + predicate + "' is not supported yet");
        }
    }

    @Override
    public Object visit(FilterExpression element, DMNContext context) {
        Type sourceType = element.getSource().getType();
        Type filterType = element.getFilter().getType();
        Object source = element.getSource().accept(this, context);
        if (!(sourceType instanceof ListType)) {
            source = Arrays.asList(source);
        }
        if (filterType == BooleanType.BOOLEAN) {
            List<Object> result = new ArrayList<>();
            for (Object item : (List) source) {
                DMNContext filterContext = this.dmnTransformer.makeFilterContext(element, FilterExpression.FILTER_PARAMETER_NAME, context);
                filterContext.bind(FilterExpression.FILTER_PARAMETER_NAME, item);

                Boolean filterValue = (Boolean) element.getFilter().accept(this, filterContext);
                if (filterValue != null && filterValue) {
                    result.add(item);
                }
            }
            return result;
        } else if (filterType == NumberType.NUMBER) {
            Object filterValue = element.getFilter().accept(this, context);
            return this.lib.elementAt((List) source, (NUMBER) filterValue);
        } else {
            throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
        }
    }

    @Override
    public Object visit(InstanceOfExpression element, DMNContext context) {
        try {
            Object e1 = element.getLeftOperand().accept(this, context);
            Type e2 = element.getRightOperand().getType();
            if (e1 == null) {
                return e2 == NullType.NULL;
            } else {
                return Type.conformsTo(element.getLeftOperand().getType(), e2);
            }
        } catch (Exception e) {
            this.errorHandler.reportError("Cannot evaluate instanceof", e);
            return null;
        }
    }

    @Override
    public Object visit(ExpressionList element, DMNContext context) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    @Override
    public Object visit(Disjunction element, DMNContext context) {
        try {
            Object leftOperand = element.getLeftOperand().accept(this, context);
            Object rightOperand = element.getRightOperand().accept(this, context);
            return this.lib.or(Arrays.asList(leftOperand, rightOperand));
        } catch (Exception e) {
            this.errorHandler.reportError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(Conjunction element, DMNContext context) {
        try {
            Object leftOperand = element.getLeftOperand().accept(this, context);
            Object rightOperand = element.getRightOperand().accept(this, context);
            return this.lib.and(Arrays.asList(leftOperand, rightOperand));
        } catch (Exception e) {
            this.errorHandler.reportError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(LogicNegation element, DMNContext context) {
        Object leftOperand = element.getLeftOperand().accept(this, context);
        return this.lib.booleanNot(leftOperand);
    }

    @Override
    public Object visit(Relational element, DMNContext context) {
        try {
            return evaluateBinaryOperator(element, element.getOperator(), element.getLeftOperand(), element.getRightOperand(), context);
        } catch (Exception e) {
            this.errorHandler.reportError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(BetweenExpression element, DMNContext context) {
        try {
            Object value = element.getValue().accept(this, context);
            Expression leftEndpoint = element.getLeftEndpoint();
            Expression rightEndpoint = element.getRightEndpoint();
            Object leftOpd = leftEndpoint.accept(this, context);
            Object rightOpd = rightEndpoint.accept(this, context);
            if (leftEndpoint.getType() == NumberType.NUMBER) {
                return this.lib.booleanAnd(this.lib.numericLessEqualThan((NUMBER) leftOpd, (NUMBER) value), this.lib.numericLessEqualThan((NUMBER) value, (NUMBER) rightOpd));
            } else if (leftEndpoint.getType() == StringType.STRING) {
                return this.lib.booleanAnd(this.lib.stringLessEqualThan((String) leftOpd, (String) value), this.lib.stringLessEqualThan((String) value, (String) rightOpd));
            } else if (leftEndpoint.getType() == DateType.DATE) {
                return this.lib.booleanAnd(this.lib.dateLessEqualThan((DATE) leftOpd, (DATE) value), this.lib.dateLessEqualThan((DATE) value, (DATE) rightOpd));
            } else if (leftEndpoint.getType() == TimeType.TIME) {
                return this.lib.booleanAnd(this.lib.timeLessEqualThan((TIME) leftOpd, (TIME) value), this.lib.timeLessEqualThan((TIME) value, (TIME) rightOpd));
            } else if (leftEndpoint.getType() == DateTimeType.DATE_AND_TIME) {
                return this.lib.booleanAnd(this.lib.dateTimeLessEqualThan((DATE_TIME) leftOpd, (DATE_TIME) value), this.lib.dateTimeLessEqualThan((DATE_TIME) value, (DATE_TIME) rightOpd));
            } else if (leftEndpoint.getType() == DurationType.YEARS_AND_MONTHS_DURATION) {
                return this.lib.booleanAnd(this.lib.durationLessEqualThan((DURATION) leftOpd, (DURATION) value), this.lib.durationLessEqualThan((DURATION) value, (DURATION) rightOpd));
            } else if (leftEndpoint.getType() == DurationType.DAYS_AND_TIME_DURATION) {
                return this.lib.booleanAnd(this.lib.durationLessEqualThan((DURATION) leftOpd, (DURATION) value), this.lib.durationLessEqualThan((DURATION) value, (DURATION) rightOpd));
            } else{
                throw new DMNRuntimeException(String.format("Type '%s' is not supported yet", leftEndpoint.getType()));
            }
        } catch (Exception e) {
            this.errorHandler.reportError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(InExpression element, DMNContext context) {
        Expression valueExp = element.getValue();
        Object value = valueExp.accept(this, context);

        DMNContext inParams = this.dmnTransformer.makeUnaryTestContext(valueExp, context);
        inParams.bind(INPUT_ENTRY_PLACE_HOLDER, value);

        List<Object> result = new ArrayList<>();
        List<PositiveUnaryTest> positiveUnaryTests = element.getTests();
        for (PositiveUnaryTest positiveUnaryTest : positiveUnaryTests) {
            Object test = positiveUnaryTest.accept(this, inParams);
            result.add(test);
        }
        if (result.size() == 1) {
            return result.get(0);
        } else {
            return this.lib.booleanOr(result);
        }
    }

    @Override
    public Object visit(Addition element, DMNContext context) {
        try {
            return evaluateBinaryOperator(element, element.getOperator(), element.getLeftOperand(), element.getRightOperand(), context);
        } catch (Exception e) {
            this.errorHandler.reportError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(Multiplication element, DMNContext context) {
        try {
            return evaluateBinaryOperator(element, element.getOperator(), element.getLeftOperand(), element.getRightOperand(), context);
        } catch (Exception e) {
            this.errorHandler.reportError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(Exponentiation element, DMNContext context) {
        try {
            return evaluateBinaryOperator(element, element.getOperator(), element.getLeftOperand(), element.getRightOperand(), context);
        } catch (Exception e) {
            this.errorHandler.reportError(String.format("Cannot evaluate '%s'", element), e);
            return null;
        }
    }

    @Override
    public Object visit(ArithmeticNegation element, DMNContext context) {
        Object leftOperand = element.getLeftOperand().accept(this, context);
        return this.lib.numericUnaryMinus((NUMBER) leftOperand);
    }

    @Override
    public Object visit(FunctionInvocation element, DMNContext context) {
        // Evaluate and convert actual parameters
        Parameters parameters = element.getParameters();
        parameters.accept(this, context);
        Arguments arguments = parameters.convertArguments((value, conversion) -> this.typeConverter.convertValue(value, conversion, this.lib));

        Expression function = element.getFunction();
        FunctionType functionType = (FunctionType) element.getFunction().getType();
        List<FormalParameter> formalParameters = functionType.getParameters();
        List<Object> argList = arguments.argumentList(formalParameters);
        if (isSimpleName(function)) {
            String functionName = functionName(function);
            return evaluateFunction(functionName, functionType, argList, context);
        } else {
            Object functionDefinition = function.accept(this, context);
            return evaluateFunction(functionDefinition, functionType, argList, context);
        }
    }

    private Object evaluateFunction(String functionName, FunctionType functionType, List<Object> argList, DMNContext context) {
        Object functionDefinition = context.lookupBinding(functionName);
        if (isFunctionDefinition(functionDefinition)) {
            return evaluateFunction(functionDefinition, functionType, argList, context);
        } else {
            String javaFunctionName = javaFunctionName(functionName);
            if ("sort".equals(javaFunctionName)) {
                FunctionDefinition comparatorDefinition = (FunctionDefinition) argList.get(1);
                Object lambdaExpression = makeLambdaExpression(comparatorDefinition, context);
                argList.set(1, lambdaExpression);
                Object result = evaluateBuiltInFunction(this.lib, javaFunctionName, argList);
                JAVA_COMPILER.deleteLambdaClass(lambdaExpression);
                return result;
            } else {
                return evaluateBuiltInFunction(this.lib, javaFunctionName, argList);
            }
        }
    }

    private Object evaluateFunction(Object functionDefinition, FunctionType functionType, List<Object> argList, DMNContext context) {
        if (functionDefinition == null) {
            throw new DMNRuntimeException(String.format("Missing function definition, expecting value of type for '%s'", functionType));
        } else if (functionDefinition instanceof TBusinessKnowledgeModel) {
            Result result = this.dmnInterpreter.evaluate((TInvocable) functionDefinition, argList, context);
            return Result.value(result);
        } else if (functionDefinition instanceof TDecisionService) {
            Result result = this.dmnInterpreter.evaluate((TInvocable) functionDefinition, argList, context);
            return Result.value(result);
        } else if (functionDefinition instanceof TFunctionDefinition) {
            return evaluateFunctionDefinition((TFunctionDefinition) functionDefinition, argList, context);
        } else if (functionDefinition instanceof FunctionDefinition) {
            return evaluateFunction((FunctionDefinition) functionDefinition, functionType, argList, context);
        } else if (functionDefinition instanceof LambdaExpression) {
            return evaluateLambdaExpression((LambdaExpression) functionDefinition, argList, context);
        } else {
            throw new DMNRuntimeException(String.format("Not supported yet %s", functionDefinition.getClass().getSimpleName()));
        }
    }

    private Object evaluateFunction(FunctionDefinition functionDefinition, FunctionType functionType, List<Object> argList, DMNContext context) {
        if (functionDefinition.isExternal()) {
            if (isJavaFunction(((FunctionDefinition) functionDefinition).getBody())) {
                return evaluateExternalJavaFunction((FunctionDefinition) functionDefinition, argList, context);
            } else {
                throw new DMNRuntimeException(String.format("Not supported external function '%s'", functionDefinition));
            }
        } else {
            if (functionType instanceof FEELFunctionType) {
                // Use the one with inferred types
                functionDefinition = ((FEELFunctionType) functionType).getFunctionDefinition();
            }
            return evaluateFunctionDefinition(functionDefinition, argList, context);
        }
    }

    private Object evaluateFunctionDefinition(TFunctionDefinition binding, List<Object> argList, DMNContext context) {
        TFunctionKind kind = binding.getKind();
        if (this.dmnTransformer.isFEELFunction(kind)) {
            Result result = this.dmnInterpreter.evaluate(binding, argList, context);
            return Result.value(result);
        } else if (this.dmnTransformer.isJavaFunction(kind)) {
            return evaluateExternalJavaFunction(binding, argList, context);
        } else {
            throw new DMNRuntimeException(String.format("Kind '%s' is not supported yet", kind.value()));
        }
    }

    private Object evaluateExternalJavaFunction(TFunctionDefinition functionDefinition, List<Object> argList, DMNContext context) {
        JavaFunctionInfo info = this.expressionToNativeTransformer.extractJavaFunctionInfo((TDRGElement) context.getElement(), functionDefinition);
        // Use reflection to evaluate
        return evaluateExternalJavaFunction(info, argList);
    }

    private Object evaluateExternalJavaFunction(FunctionDefinition functionDefinition, List<Object> argList, DMNContext context) {
        JavaFunctionInfo info = this.expressionToNativeTransformer.extractJavaFunctionInfo((TDRGElement) context.getElement(), functionDefinition);
        // Use reflection to evaluate
        return evaluateExternalJavaFunction(info, argList);
    }

    public Object evaluateFunctionDefinition(FunctionDefinition functionDefinition, List<Object> argList, DMNContext parentContext) {
        // Create new environments and bind parameters
        DMNContext functionContext = this.dmnTransformer.makeFunctionContext(functionDefinition, parentContext);
        List<FormalParameter> formalParameterList = functionDefinition.getFormalParameters();
        for (int i = 0; i < formalParameterList.size(); i++) {
            FormalParameter param = formalParameterList.get(i);
            String name = param.getName();
            Object value = argList.get(i);
            functionContext.bind(name, value);
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

    private Object evaluateLambdaExpression(LambdaExpression binding, List<Object> argList, DMNContext context) {
        String functionName = "apply";
        return evaluateMethod(binding, binding.getClass(), functionName, argList);
    }

    private Object evaluateBuiltInFunction(FEELLib lib, String functionName, List<Object> argList) {
        return evaluateMethod(lib, lib.getClass(), functionName, argList);
    }

    private Object evaluateExternalJavaFunction(JavaFunctionInfo info, List<Object> argList) {
        return this.externalFunctionExecutor.execute(info, argList);
    }

    private Object evaluateMethod(Object object, Class<?> cls, String functionName, List<Object> argList) {
        try {
            Class[] argTypes = new Class[argList.size()];
            for (int i = 0; i < argList.size(); i++) {
                argTypes[i] = getClass(argList.get(i));
            }
            Method declaredMethod = MethodUtils.resolveMethod(functionName, cls, argTypes);
            if (declaredMethod == null) {
                throw new DMNRuntimeException(String.format("Cannot resolve '%s.%s(%s)", cls.getName(), functionName, argList));
            }
            Object[] args = JavaFunctionInfo.makeArgs(declaredMethod, argList);
            return declaredMethod.invoke(object, args);
        } catch (Exception e) {
            this.errorHandler.reportError(String.format("Cannot invoke function '%s.%s(%s)'", cls.getName(), functionName, argList), e);
            return null;
        }
    }

    private boolean isSimpleName(Expression function) {
        return function instanceof Name || function instanceof QualifiedName && ((QualifiedName) function).getNames().size() == 1;
    }
    private boolean isFunctionDefinition(Object binding) {
        return binding instanceof TBusinessKnowledgeModel
                || binding instanceof TDecisionService
                || binding instanceof TFunctionDefinition
                || binding instanceof FunctionDefinition
                || binding instanceof LambdaExpression;
    }

    private boolean isJavaFunction(Expression body) {
        return  body instanceof Context &&
                ((Context) body).getEntries().size() == 1 &&
                "java".equals(((Context) body).getEntries().get(0).getKey().getKey());
    }

    @Override
    public Object visit(NamedParameters element, DMNContext context) {
        Map<String, Object> arguments = new LinkedHashMap<>();
        Map<String, Expression> parameters = element.getParameters();

        for (Map.Entry<String, Expression> entry : parameters.entrySet()) {
            String key = entry.getKey();
            Expression expression = entry.getValue();
            Object value = expression.accept(this, context);
            arguments.put(key, value);
        }
        element.setOriginalArguments(new NamedArguments(arguments));
        return element;
    }

    @Override
    public Object visit(PositionalParameters element, DMNContext context) {
        List<Object> arguments = new ArrayList<>();
        element.getParameters().forEach(p -> arguments.add(p.accept(this, context)));
        element.setOriginalArguments(new PositionalArguments(arguments));
        return element;
    }

    @Override
    public Object visit(PathExpression element, DMNContext context) {
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
            if (sourceType instanceof ImportContextType) {
                List<String> aliases = ((ImportContextType) sourceType).getAliases(member);
                return ((com.gs.dmn.runtime.Context) source).get(member, aliases.toArray());
            } else if (sourceType instanceof ItemDefinitionType) {
                List<String> aliases = ((ItemDefinitionType) sourceType).getAliases(member);
                if (source instanceof com.gs.dmn.runtime.Context) {
                    return ((com.gs.dmn.runtime.Context) source).get(member, aliases.toArray());
                } else {
                    String getterName = this.dmnTransformer.getterName(member);
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
            } else if (sourceType instanceof RangeType) {
                return evaluateRangeMember(source, member);
            } else if (sourceType instanceof AnyType) {
                // source is Context
                List<String> aliases = Arrays.asList();
                return ((com.gs.dmn.runtime.Context) source).get(member, aliases.toArray());
            } else {
                this.errorHandler.reportError(String.format("Cannot evaluate '%s'.", element));
                return null;
            }
        } catch (Exception e) {
            this.errorHandler.reportError(String.format("Cannot evaluate '%s'.", element), e);
            return null;
        }
    }

    private Object evaluateDateTimeMember(Object source, String member) {
        if ("year".equals(member)) {
            return this.lib.year(this.lib.toDate(source));
        } else if ("month".equals(member)) {
            return this.lib.month(this.lib.toDate(source));
        } else if ("day".equals(member)) {
            return this.lib.day(this.lib.toDate(source));
        } else if ("weekday".equals(member)) {
            return this.lib.weekday(this.lib.toDate(source));
        } else if ("hour".equals(member)) {
            return this.lib.hour(this.lib.toTime(source));
        } else if ("minute".equals(member)) {
            return this.lib.minute(this.lib.toTime(source));
        } else if ("second".equals(member)) {
            return this.lib.second(this.lib.toTime(source));
        } else if ("time offset".equals(member)) {
            return this.lib.timeOffset(this.lib.toTime(source));
        } else if ("timezone".equals(member)) {
            return this.lib.timezone(this.lib.toTime(source));

        } else if ("years".equals(member)) {
            return this.lib.years((DURATION) source);
        } else if ("months".equals(member)) {
            return this.lib.months((DURATION) source);
        } else if ("days".equals(member)) {
            return this.lib.days((DURATION) source);
        } else if ("hours".equals(member)) {
            return this.lib.hours((DURATION) source);
        } else if ("minutes".equals(member)) {
            return this.lib.minutes((DURATION) source);
        } else if ("seconds".equals(member)) {
            return this.lib.seconds((DURATION) source);
        } else {
            throw new DMNRuntimeException(String.format("Cannot resolve method '%s' for date time", member));
        }
    }

    private Object evaluateRangeMember(Object source, String member) {
        if ("start".equals(member)) {
            return ((Range) source).getStart();
        } else if ("end".equals(member)) {
            return ((Range) source).getEnd();
        } else if ("start included".equals(member)) {
            return ((Range) source).isStartIncluded();
        } else if ("end included".equals(member)) {
            return ((Range) source).isEndIncluded();
        } else {
            throw new DMNRuntimeException(String.format("Cannot resolve method '%s' for date time", member));
        }
    }

    @Override
    public Object visit(BooleanLiteral element, DMNContext context) {
        return Boolean.parseBoolean(element.getLexeme());
    }

    @Override
    public Object visit(DateTimeLiteral element, DMNContext context) {
        Type type = element.getType();
        String literal = StringEscapeUtil.stripQuotes(element.getLexeme());
        if (type == DateType.DATE) {
            return this.lib.date(literal);
        } else if (type == TimeType.TIME) {
            return this.lib.time(literal);
        } else if (type == DateTimeType.DATE_AND_TIME) {
            return this.lib.dateAndTime(literal);
        } else if (type == DurationType.DAYS_AND_TIME_DURATION || type == DurationType.YEARS_AND_MONTHS_DURATION) {
            return this.lib.duration(literal);
        } else {
            this.errorHandler.reportError(String.format("Illegal date time literal '%s'", element));
            return null;
        }
    }

    @Override
    public Object visit(NullLiteral element, DMNContext context) {
        return null;
    }

    @Override
    public Object visit(NumericLiteral element, DMNContext context) {
        return this.lib.number(element.getLexeme());
    }

    @Override
    public Object visit(StringLiteral element, DMNContext context) {
        String lexeme = element.getLexeme();
        String value = StringEscapeUtil.unescapeFEEL(lexeme);
        return value;
    }

    @Override
    public Object visit(ListLiteral element, DMNContext context) {
        List<Expression> expressionList = element.getExpressionList();
        List result = new ArrayList();
        for (Expression exp : expressionList) {
            Object value = exp.accept(this, context);
            result.add(value);
        }
        return result;
    }

    @Override
    public Object visit(QualifiedName element, DMNContext context) {
        if (element.getNames().size() == 1) {
            String name = element.getNames().get(0);
            if (name.equals(INPUT_ENTRY_PLACE_HOLDER)) {
                return context.getInputExpression().accept(this, context);
            } else {
                return context.lookupBinding(name);
            }
        } else {
            throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
        }
    }

    @Override
    public Object visit(Name element, DMNContext context) {
        String variableName = element.getName();
        return context.lookupBinding(variableName);
    }

    //
    // Type expressions
    //
    @Override
    public Object visit(NamedTypeExpression element, DMNContext params) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    @Override
    public Object visit(ListTypeExpression element, DMNContext params) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    @Override
    public Object visit(ContextTypeExpression element, DMNContext params) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    @Override
    public Object visit(FunctionTypeExpression element, DMNContext params) {
        throw new UnsupportedOperationException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }
}
