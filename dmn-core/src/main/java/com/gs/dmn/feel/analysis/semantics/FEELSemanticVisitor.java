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
package com.gs.dmn.feel.analysis.semantics;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.context.environment.VariableDeclaration;
import com.gs.dmn.error.LogAndThrowErrorHandler;
import com.gs.dmn.feel.FEELConstants;
import com.gs.dmn.feel.OperatorDecisionTable;
import com.gs.dmn.feel.analysis.AbstractAnalysisVisitor;
import com.gs.dmn.feel.analysis.semantics.type.*;
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
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import org.omg.spec.dmn._20191111.model.TDefinitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.syntax.ast.expression.textual.ForExpression.PARTIAL_PARAMETER_NAME;

public class FEELSemanticVisitor extends AbstractAnalysisVisitor<Type, DMNContext> {
    public FEELSemanticVisitor(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        super(dmnTransformer, new LogAndThrowErrorHandler(LOGGER));
    }

    //
    // Tests
    //
    @Override
    public Object visit(PositiveUnaryTests<Type, DMNContext> element, DMNContext context) {
        // Visit children
        List<PositiveUnaryTest<Type, DMNContext>> positiveUnaryTests = element.getPositiveUnaryTests();
        positiveUnaryTests.forEach(ut -> ut.accept(this, context));

        // Derive type
        List<Type> types = positiveUnaryTests.stream().map(Expression::getType).collect(Collectors.toList());
        element.setType(new TupleType(types));

        return element;
    }

    @Override
    public Object visit(NegatedPositiveUnaryTests<Type, DMNContext> element, DMNContext context) {
        // Visit children
        PositiveUnaryTests<Type, DMNContext> positiveUnaryTests = element.getPositiveUnaryTests();
        positiveUnaryTests.accept(this, context);

        // Derive type
        Type type = positiveUnaryTests.getType();
        element.setType(type);
        if (type instanceof TupleType) {
            for (Type child : ((TupleType) type).getTypes()) {
                if (child == BooleanType.BOOLEAN || child instanceof RangeType) {
                } else {
                    throw new SemanticError(element, String.format("Operator '%s' cannot be applied to '%s'", "not", child));
                }
            }
        }

        return element;
    }

    @Override
    public Object visit(Any<Type, DMNContext> element, DMNContext context) {
        // Derive type
        element.setType(BooleanType.BOOLEAN);

        return element;
    }

    @Override
    public Object visit(NullTest<Type, DMNContext> element, DMNContext context) {
        // Derive type
        element.setType(BooleanType.BOOLEAN);

        return element;
    }

    @Override
    public Object visit(ExpressionTest<Type, DMNContext> element, DMNContext context) {
        // Visit children
        Expression<Type, DMNContext> expression = element.getExpression();
        expression.accept(this, context);

        // Derive type
        element.setType(BooleanType.BOOLEAN);
        if (expression.getType() != BooleanType.BOOLEAN) {
            throw new DMNRuntimeException(String.format("Illegal type of positive unary test '%s'. Expected boolean found '%s'", expression, expression.getType()));
        }

        return element;
    }

    @Override
    public Object visit(OperatorRange<Type, DMNContext> element, DMNContext context) {
        // Visit children
        Expression<Type, DMNContext> endpoint = element.getEndpoint();
        String operator = element.getOperator();
        endpoint.accept(this, context);
        element.getEndpointsRange().accept(this, context);

        // Derive type
        if (context.isExpressionContext()) {
            element.setType(new RangeType(endpoint.getType()));
        } else {
            Type inputExpressionType = context.getInputExpressionType();
            element.setType(new RangeType(endpoint.getType()));
            if (endpoint instanceof FunctionInvocation) {
            } else if (endpoint instanceof NamedExpression) {
            } else {
                if (operator == null) {
                    checkType(element, "=", inputExpressionType, endpoint.getType(), context);
                } else {
                    checkType(element, operator, inputExpressionType, endpoint.getType(), context);
                }
            }
        }

        return element;
    }

    @Override
    public Object visit(EndpointsRange<Type, DMNContext> element, DMNContext context) {
        // Visit children
        Expression<Type, DMNContext> start = element.getStart();
        if (start != null) {
            start.accept(this, context);
        }
        Expression<Type, DMNContext> end = element.getEnd();
        if (end != null) {
            end.accept(this, context);
        }

        // Derive type
        if (start == null && end == null) {
            throw new DMNRuntimeException(String.format("Illegal range, both endpoints are null in context of element '%s'", context.getElementName()));
        } else if (start != null) {
            Type startType = start.getType();
            element.setType(new RangeType(startType));
        } else {
            Type endType = end.getType();
            element.setType(new RangeType(endType));
        }

        return element;
    }

    @Override
    public Object visit(ListTest<Type, DMNContext> element, DMNContext context) {
        // Visit children
        ListLiteral<Type, DMNContext> listLiteral = element.getListLiteral();
        listLiteral.accept(this, context);

        // Derive type
        element.setType(BOOLEAN);
        List<Expression<Type, DMNContext>> expressionList = listLiteral.getExpressionList();
        if (!expressionList.isEmpty()) {
            Type listType = listLiteral.getType();
            Type listElementType = ((ListType) listType).getElementType();
            Type inputExpressionType = context.getInputExpressionType();
            if (Type.conformsTo(inputExpressionType, listType)) {
            } else if (Type.conformsTo(inputExpressionType, listElementType)) {
            } else if (listElementType instanceof RangeType &&Type.conformsTo(inputExpressionType, ((RangeType) listElementType).getRangeType())) {
            } else {
                throw new SemanticError(element, String.format("Cannot compare '%s', '%s'", inputExpressionType, listType));
            }
        }

        return element;
    }

    //
    // Textual expressions
    //
    @Override
    public Object visit(FunctionDefinition<Type, DMNContext> element, DMNContext context) {
        // Analyze parameters
        element.getFormalParameters().forEach(p -> p.accept(this, context));

        // Analyze return type
        Type returnType = null;
        TypeExpression<Type, DMNContext> returnTypeExpression = element.getReturnTypeExpression();
        if (returnTypeExpression != null) {
            returnTypeExpression.accept(this, context);
            returnType = returnTypeExpression.getType();
        }

        // Make body environment
        DMNContext bodyContext = this.dmnTransformer.makeFunctionContext(element, context);

        // Analyze body
        Expression<Type, DMNContext> body = element.getBody();
        if (element.isStaticTyped()) {
            // Analyze body
            body.accept(this, bodyContext);
        }

        // Derive element type
        if (returnType == null) {
            if (element.isExternal()) {
                returnType = AnyType.ANY;
            } else {
                returnType = body.getType();
            }
        }
        FEELFunctionType type = new FEELFunctionType(element.getFormalParameters(), returnType, element.isExternal(), element);
        element.setType(type);

        return element;
    }

    @Override
    public Object visit(FormalParameter<Type, DMNContext> element, DMNContext context) {
        if (element.getType() == null) {
            TypeExpression<Type, DMNContext> typeExpression = element.getTypeExpression();
            if (typeExpression != null) {
                typeExpression.accept(this, context);
                element.setType(typeExpression.getType());
            }
        }
        return element;
    }

    @Override
    public Object visit(Context<Type, DMNContext> element, DMNContext context) {
        // Visit children
        DMNContext localContext = this.dmnTransformer.makeLocalContext(context);
        List<ContextEntry<Type, DMNContext>> entries = element.getEntries();
        entries.forEach(ce -> ce.accept(this, localContext));

        // Derive type
        ContextType type = new ContextType();
        entries.forEach(e -> type.addMember(e.getKey().getKey(), Arrays.asList(), e.getExpression().getType()));
        element.setType(type);

        return element;
    }

    @Override
    public Object visit(ContextEntry<Type, DMNContext> element, DMNContext context) {
        // Visit children
        ContextEntryKey<Type, DMNContext> key = (ContextEntryKey<Type, DMNContext>) element.getKey().accept(this, context);
        Expression<Type, DMNContext> expression = (Expression<Type, DMNContext>) element.getExpression().accept(this, context);

        // Add declaration
        context.addDeclaration(this.environmentFactory.makeVariableDeclaration(key.getKey(), expression.getType()));

        return element;
    }

    @Override
    public Object visit(ContextEntryKey<Type, DMNContext> element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(ForExpression<Type, DMNContext> element, DMNContext context) {
        // Visit children
        List<Iterator<Type, DMNContext>> iterators = element.getIterators();
        DMNContext qParamsContext = visitIterators(element, context, iterators);
        qParamsContext.addDeclaration(this.environmentFactory.makeVariableDeclaration(PARTIAL_PARAMETER_NAME, new ListType(NullType.NULL)));
        Expression<Type, DMNContext> body = element.getBody();
        body.accept(this, qParamsContext);

        // Derive type
        element.setType(new ListType(body.getType()));

        // Visit body with new context
        qParamsContext.updateVariableDeclaration(PARTIAL_PARAMETER_NAME, element.getType());
        body.accept(new UpdatePartialVisitor<>(element.getType(), this.errorHandler), qParamsContext);

        return element;
    }

    @Override
    public Object visit(Iterator<Type, DMNContext> element, DMNContext context) {
        // Visit children
        element.getDomain().accept(this, context);

        return element;
    }

    @Override
    public Object visit(ExpressionIteratorDomain<Type, DMNContext> element, DMNContext context) {
        // Visit children
        Expression<Type, DMNContext> expression = element.getExpression();
        expression.accept(this, context);

        // Derive type
        element.setType(expression.getType());

        return element;
    }

    @Override
    public Object visit(RangeIteratorDomain<Type, DMNContext> element, DMNContext context) {
        // Visit children
        Expression<Type, DMNContext> start = element.getStart();
        start.accept(this, context);
        element.getEnd().accept(this, context);

        // Derive type
        element.setType(new RangeType(start.getType()));

        return element;
    }

    @Override
    public Object visit(IfExpression<Type, DMNContext> element, DMNContext context) {
        // Visit children
        Expression<Type, DMNContext> condition = element.getCondition();
        condition.accept(this, context);
        Expression<Type, DMNContext> thenExpression = element.getThenExpression();
        thenExpression.accept(this, context);
        Expression<Type, DMNContext> elseExpression = element.getElseExpression();
        elseExpression.accept(this, context);

        // Derive type
        Type conditionType = condition.getType();
        Type thenType = thenExpression.getType();
        Type elseType = elseExpression.getType();
        if (conditionType != BOOLEAN) {
            throw new SemanticError(element, String.format("Condition type must be boolean. Found '%s' instead.", conditionType));
        }
        if (Type.isNullType(thenType) && Type.isNullType(elseType)) {
            throw new SemanticError(element, String.format("Types of then and else branches are incompatible. Found '%s' and '%s'.", thenType, elseType));
        } else if (Type.isNullType(thenType)) {
            element.setType(elseType);
        } else if (Type.isNullType(elseType)) {
            element.setType(thenType);
        } else {
            if (Type.conformsTo(thenType, elseType)) {
                element.setType(elseType);
            } else if (Type.conformsTo(elseType, thenType)) {
                element.setType(thenType);
            } else {
                throw new SemanticError(element, String.format("Types of then and else branches are incompatible. Found '%s' and '%s'.", thenType, elseType));
            }
        }

        return element;
    }

    @Override
    public Object visit(QuantifiedExpression<Type, DMNContext> element, DMNContext context) {
        // Visit children
        List<Iterator<Type, DMNContext>> iterators = element.getIterators();
        DMNContext qParamsContext = visitIterators(element, context, iterators);
        Expression<Type, DMNContext> body = element.getBody();
        body.accept(this, qParamsContext);

        // Derive type
        element.setType(body.getType());

        return element;
    }

    @Override
    public Object visit(FilterExpression<Type, DMNContext> element, DMNContext context) {
        // analyse source
        Expression<Type, DMNContext> source = element.getSource();
        source.accept(this, context);

        // transform filter (add missing 'item' in front of members)
        Expression<Type, DMNContext> filter = transformFilter(source, element.getFilter(), FilterExpression.FILTER_PARAMETER_NAME, context);
        element.setFilter(filter);

        // analyse filter
        DMNContext filterContext = this.dmnTransformer.makeFilterContext(element, FilterExpression.FILTER_PARAMETER_NAME, context);
        element.getFilter().accept(this, filterContext);

        // derive type
        Type sourceType = source.getType();
        Type filterType = filter.getType();
        if (sourceType instanceof ListType) {
            if (filterType == NUMBER) {
                element.setType(((ListType) sourceType).getElementType());
            } else if (filterType == BOOLEAN) {
                element.setType(sourceType);
            } else {
                throw new SemanticError(element, String.format("Cannot resolve type for '%s'", element));
            }
        } else {
            if (filterType == NUMBER) {
                element.setType(sourceType);
            } else if (filterType == BOOLEAN) {
                element.setType(new ListType(sourceType));
            } else {
                throw new SemanticError(element, String.format("Cannot resolve type for '%s'", element));
            }
        }

        return element;
    }

    private Expression<Type, DMNContext> transformFilter(Expression<Type, DMNContext> source, Expression<Type, DMNContext> filter, String filterVariableName, DMNContext context) {
        Type elementType = source.getType();
        if (elementType instanceof ListType) {
            elementType = ((ListType) elementType).getElementType();
        }
        return (Expression<Type, DMNContext>)filter.accept(new AddItemFilterVisitor<>(filterVariableName, elementType, this.errorHandler), context);
    }

    @Override
    public Object visit(InstanceOfExpression<Type, DMNContext> element, DMNContext context) {
        // Visit children
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);

        // Derive type
        element.setType(BOOLEAN);

        return element;
    }

    //
    // Expressions
    //
    @Override
    public Object visit(ExpressionList<Type, DMNContext> element, DMNContext context) {
        // Visit children
        List<Expression<Type, DMNContext>> expressionList = element.getExpressionList();
        expressionList.forEach(e -> e.accept(this, context));

        // Derive type
        List<Type> types = expressionList.stream().map(Expression::getType).collect(Collectors.toList());
        element.setType(new TupleType(types));

        return element;
    }

    //
    // Logic expressions
    //
    @Override
    public Object visit(Conjunction<Type, DMNContext> element, DMNContext context) {
        // Visit children
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);

        // Derive type
        // Not need to check the operand types. or, and, not are total functions
        element.setType(BOOLEAN);

        return element;
    }

    @Override
    public Object visit(Disjunction<Type, DMNContext> element, DMNContext context) {
        // Visit children
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);

        // Derive type
        // Not need to check the operand types. or, and, not are total functions
        element.setType(BOOLEAN);

        return element;
    }

    @Override
    public Object visit(LogicNegation<Type, DMNContext> element, DMNContext context) {
        // Visit children
        element.getLeftOperand().accept(this, context);

        // Derive type
        // Not need to check the operand types. or, and, not are total functions
        element.setType(BOOLEAN);

        return element;
    }

    //
    // Comparison expressions
    //
    @Override
    public Object visit(Relational<Type, DMNContext> element, DMNContext context) {
        // Visit children
        Expression<Type, DMNContext> leftOperand = element.getLeftOperand();
        leftOperand.accept(this, context);
        Expression<Type, DMNContext> rightOperand = element.getRightOperand();
        rightOperand.accept(this, context);
        String operator = element.getOperator();

        // Derive type
        element.setType(BOOLEAN);
        checkType(element, operator, leftOperand.getType(), rightOperand.getType(), context);

        return element;
    }

    @Override
    public Object visit(BetweenExpression<Type, DMNContext> element, DMNContext context) {
        // Visit children
        Expression<Type, DMNContext> value = element.getValue();
        value.accept(this, context);
        Expression<Type, DMNContext> leftEndpoint = element.getLeftEndpoint();
        leftEndpoint.accept(this, context);
        Expression<Type, DMNContext> rightEndpoint = element.getRightEndpoint();
        rightEndpoint.accept(this, context);

        // Derive type
        element.setType(BOOLEAN);
        checkType(element, ">=", value.getType(), leftEndpoint.getType(), context);
        checkType(element, "<=", value.getType(), rightEndpoint.getType(), context);

        return element;
    }

    @Override
    public Object visit(InExpression<Type, DMNContext> element, DMNContext parentContext) {
        // Visit children
        Expression<Type, DMNContext> value = element.getValue();
        value.accept(this, parentContext);

        // Visit tests with value type injected in scope
        DMNContext testContext = this.dmnTransformer.makeUnaryTestContext(value, parentContext);
        element.getTests().forEach(t -> t.accept(this, testContext));

        // Derive type
        element.setType(BOOLEAN);

        return element;
    }

    //
    // Arithmetic expressions
    //
    @Override
    public Object visit(Addition<Type, DMNContext> element, DMNContext context) {
        // Visit children
        Expression<Type, DMNContext> leftOperand = element.getLeftOperand();
        leftOperand.accept(this, context);
        Expression<Type, DMNContext> rightOperand = element.getRightOperand();
        rightOperand.accept(this, context);

        // Derive type
        element.setType(NUMBER);
        checkType(element, element.getOperator(), leftOperand.getType(), rightOperand.getType(), context);

        return element;
    }

    @Override
    public Object visit(Multiplication<Type, DMNContext> element, DMNContext context) {
        // Visit children
        Expression<Type, DMNContext> leftOperand = element.getLeftOperand();
        leftOperand.accept(this, context);
        Expression<Type, DMNContext> rightOperand = element.getRightOperand();
        rightOperand.accept(this, context);

        // Derive type
        element.setType(NUMBER);
        checkType(element, element.getOperator(), leftOperand.getType(), rightOperand.getType(), context);

        return element;
    }

    @Override
    public Object visit(Exponentiation<Type, DMNContext> element, DMNContext context) {
        // Visit children
        Expression<Type, DMNContext> leftOperand = element.getLeftOperand();
        leftOperand.accept(this, context);
        Expression<Type, DMNContext> rightOperand = element.getRightOperand();
        rightOperand.accept(this, context);

        // Derive type
        element.setType(NUMBER);
        checkType(element, element.getOperator(), leftOperand.getType(), rightOperand.getType(), context);

        return element;
    }

    @Override
    public Object visit(ArithmeticNegation<Type, DMNContext> element, DMNContext context) {
        // Visit children
        element.getLeftOperand().accept(this, context);

        // Derive type
        Type type = element.getLeftOperand().getType();
        element.setType(NUMBER);
        if (type != NUMBER) {
            throw new SemanticError(element, String.format("Operator '%s' cannot be applied to '%s'", element.getOperator(), type));
        }

        // Derive type
        return element;
    }

    //
    // Postfix expressions
    //
    @Override
    public Object visit(PathExpression<Type, DMNContext> element, DMNContext context) {
        // Visit children
        Expression<Type, DMNContext> source = element.getSource();
        source.accept(this, context);

        // Derive type
        Type sourceType = source.getType();
        Type type = navigationType(sourceType, element.getMember());
        element.setType(type);

        return element;
    }

    @Override
    public Object visit(FunctionInvocation<Type, DMNContext> element, DMNContext context) {
        // Visit children
        Expression<Type, DMNContext> function = element.getFunction();
        Parameters<Type, DMNContext> parameters = element.getParameters();
        if (function instanceof Name && "sort".equals(((Name<Type, DMNContext>) function).getName())) {
            visitSortParameters(element, context, parameters);
        } else {
            parameters.accept(this, context);
        }
        function.accept(this, context);

        // Derive type
        inferMissingTypesInFEELFunction(element.getFunction(), element.getParameters(), context);
        if (function instanceof Name) {
            String functionName = ((Name<Type, DMNContext>) function).getName();
            FunctionInvocationUtils.deriveType(element, context, functionName);
        } else if (function instanceof QualifiedName && ((QualifiedName<Type, DMNContext>) function).getNames().size() == 1) {
            String functionName = ((QualifiedName<Type, DMNContext>) function).getQualifiedName();
            FunctionInvocationUtils.deriveType(element, context, functionName);
        } else {
            FunctionType functionType = (FunctionType) function.getType();

            FunctionInvocationUtils.setInvocationType(element, functionType);
            if (parameters instanceof NamedParameters) {
                ParameterConversions<Type, DMNContext> parameterConversions = new NamedParameterConversions<>(functionType.getParameters());
                parameters.setParameterConversions(parameterConversions);
            } else {
                ParameterConversions<Type, DMNContext> parameterConversions = new PositionalParameterConversions<>(functionType.getParameterTypes());
                parameters.setParameterConversions(parameterConversions);
            }
            parameters.setConvertedParameterTypes(parameters.getSignature());
        }

        return element;
    }

    private void visitSortParameters(FunctionInvocation<Type, DMNContext> element, DMNContext context, Parameters<Type, DMNContext> parameters) {
        boolean success = false;
        if (parameters.getSignature().size() == 2) {
            Expression<Type, DMNContext> listExpression;
            Expression<Type, DMNContext> lambdaExpression;
            if (parameters instanceof PositionalParameters) {
                listExpression = ((PositionalParameters<Type, DMNContext>) parameters).getParameters().get(0);
                lambdaExpression = ((PositionalParameters<Type, DMNContext>) parameters).getParameters().get(1);
            } else {
                listExpression = ((NamedParameters<Type, DMNContext>) parameters).getParameters().get("list");
                lambdaExpression = ((NamedParameters<Type, DMNContext>) parameters).getParameters().get("function");
            }
            listExpression.accept(this, context);
            Type listType = listExpression.getType();
            if (listType instanceof ListType) {
                Type elementType = ((ListType) listType).getElementType();
                if (lambdaExpression instanceof FunctionDefinition) {
                    List<FormalParameter<Type, DMNContext>> formalParameters = ((FunctionDefinition<Type, DMNContext>) lambdaExpression).getFormalParameters();
                    formalParameters.forEach(p -> p.setType(elementType));
                    success = true;
                } else if (lambdaExpression instanceof Name) {
                    Declaration declaration = context.lookupVariableDeclaration(((Name<Type, DMNContext>) lambdaExpression).getName());
                    Type type = declaration.getType();
                    if (type instanceof FunctionType && !(type instanceof BuiltinFunctionType)) {
                        List<FormalParameter<Type, DMNContext>> formalParameters = ((FunctionType) type).getParameters();
                        formalParameters.forEach(p -> p.setType(elementType));
                        success = true;
                    }
                }
            }
            lambdaExpression.accept(this, context);
        }
        if (!success) {
            throw new SemanticError(element, String.format("Cannot infer parameter type for lambda in sort call '%s'", element));
        }
    }

    private void inferMissingTypesInFEELFunction(Expression<Type, DMNContext> function, Parameters<Type, DMNContext> arguments, DMNContext context) {
        Type functionType = function.getType();
        if (functionType instanceof FEELFunctionType) {
            FEELFunctionType feelFunctionType = (FEELFunctionType) functionType;
            if (!feelFunctionType.isFullySpecified()) {
                // Bind names to types in function type
                bindNameToTypes(feelFunctionType.getParameters(), arguments);

                // Process function definition
                FunctionDefinition<Type, DMNContext> functionDefinition = feelFunctionType.getFunctionDefinition();
                if (functionDefinition != null) {
                    // Bind names to types in function definition
                    bindNameToTypes(functionDefinition.getFormalParameters(), arguments);

                    // Set return type
                    functionDefinition.accept(this, context);
                    Type returnType = feelFunctionType.getReturnType();
                    if (Type.isNullOrAny(returnType)) {
                        Type newReturnType = functionDefinition.getReturnType();
                        feelFunctionType.setReturnType(newReturnType);
                    }
                }
            }
        }
    }

    private void bindNameToTypes(List<FormalParameter<Type, DMNContext>> parameters, Parameters<Type, DMNContext> arguments) {
        if (arguments instanceof NamedParameters) {
            for(FormalParameter<Type, DMNContext> p: parameters) {
                Type type = p.getType();
                if (Type.isNullOrAny(type)) {
                    Type newType = ((NamedParameters<Type, DMNContext>) arguments).getParameters().get(p.getName()).getType();
                    p.setType(newType);
                }
            }
        } else if (arguments instanceof PositionalParameters) {
            for(int i=0; i < parameters.size(); i++) {
                FormalParameter<Type, DMNContext> p = parameters.get(i);
                Type type = p.getType();
                if (Type.isNullOrAny(type)) {
                    Type newType = ((PositionalParameters<Type, DMNContext>) arguments).getParameters().get(i).getType();
                    p.setType(newType);
                }
            }
        }
    }

    @Override
    public Object visit(NamedParameters<Type, DMNContext> element, DMNContext context) {
        // Visit children
        element.getParameters().values().forEach(p -> p.accept(this, context));

        return element;
    }

    @Override
    public Object visit(PositionalParameters<Type, DMNContext> element, DMNContext context) {
        // Visit children
        element.getParameters().forEach(p -> p.accept(this, context));

        return element;
    }

    //
    // Primary expressions
    //
    @Override
    public Object visit(BooleanLiteral<Type, DMNContext> element, DMNContext context) {
        // Derive type
        element.setType(BOOLEAN);

        return element;
    }

    @Override
    public Object visit(DateTimeLiteral<Type, DMNContext> element, DMNContext context) {
        String conversionFunction = element.getConversionFunction();
        // Derive type
        if (DateType.DATE.hasConversionFunction(conversionFunction)) {
            element.setType(DateType.DATE);
        } else if (TimeType.TIME.hasConversionFunction(conversionFunction)) {
            element.setType(TimeType.TIME);
        } else if (DateTimeType.DATE_AND_TIME.hasConversionFunction(conversionFunction)) {
            element.setType(DateTimeType.DATE_AND_TIME);
        } else if (FEELConstants.DURATION_LITERAL_FUNCTION_NAME.equals(conversionFunction)) {
            if (element.isYearsAndMonthsDuration(element.getLexeme())) {
                element.setType(DurationType.YEARS_AND_MONTHS_DURATION);
            } else if (element.isDaysAndTimeDuration(element.getLexeme())) {
                element.setType(DurationType.DAYS_AND_TIME_DURATION);
            } else {
                throw new SemanticError(element, String.format("Date time literal '%s(%s) is not supported", conversionFunction, element.getLexeme()));
            }
        } else {
            throw new SemanticError(element, String.format("Date time literal '%s(%s)' is not supported", conversionFunction, element.getLexeme()));
        }

        return element;
    }

    @Override
    public Object visit(NullLiteral<Type, DMNContext> element, DMNContext context) {
        // Derive type
        element.setType(NullType.NULL);

        return element;
    }

    @Override
    public Object visit(NumericLiteral<Type, DMNContext> element, DMNContext context) {
        // Derive type
        element.setType(NUMBER);

        return element;
    }

    @Override
    public Object visit(StringLiteral<Type, DMNContext> element, DMNContext context) {
        // Derive type
        element.setType(StringType.STRING);

        return element;
    }

    @Override
    public Object visit(ListLiteral<Type, DMNContext> element, DMNContext context) {
        // Visit children
        List<Expression<Type, DMNContext>> expressionList = element.getExpressionList();
        expressionList.forEach(e -> e.accept(this, context));

        // Derive type
        if (expressionList.isEmpty()) {
            if (context.getInputExpressionType() == null) {
                // conforms to any other list
                element.setType(new ListType(NullType.NULL));
            } else {
                element.setType(context.getInputExpressionType());
            }
        } else {
            checkListElementTypes(element);
        }

        return element;
    }

    private void checkListElementTypes(ListLiteral<Type, DMNContext> element) {
        List<Type> types = element.getExpressionList().stream().map(Expression::getType).collect(Collectors.toList());
        boolean sameType = true;
        for (int i = 0; i < types.size() - 1; i++) {
            Type type1 = types.get(i);
            for (int j = i + 1; j < types.size(); j++) {
                Type type2 = types.get(j);
                if (!Type.conformsTo(type1, type2)) {
                    sameType = false;
                    break;
                }
            }
            if (!sameType) {
                break;
            }
        }
        if (sameType) {
            element.setType(new ListType(types.get(0)));
        } else {
            element.setType(ListType.ANY_LIST);
        }
    }

    @Override
    public Object visit(QualifiedName<Type, DMNContext> element, DMNContext context) {
        // Derive type
        List<String> names = element.getNames();
        if (names ==  null || names.isEmpty()) {
            throw new SemanticError(element, "Illegal qualified name.");
        } else if (names.size() == 1) {
            deriveType(element, context);
            return element;
        } else {
            VariableDeclaration source = (VariableDeclaration) context.lookupVariableDeclaration(names.get(0));
            Type sourceType = source.getType();
            for (int i = 1; i < names.size(); i++) {
                String member = names.get(i);
                sourceType = navigationType(sourceType, member);
            }
            element.setType(sourceType);
        }

        return element;
    }

    private void deriveType(QualifiedName<Type, DMNContext> element, DMNContext context) {
        Type type;
        String name = element.getName();
        // Lookup for variables
        Declaration declaration = context.lookupVariableDeclaration(name);
        if (declaration instanceof VariableDeclaration) {
            type = declaration.getType();
            element.setType(type);
            return;
        }
        // Lookup for functions
        List<Declaration> declarations = context.lookupFunctionDeclaration(name);
        if (declarations != null && declarations.size() == 1) {
            declaration = declarations.get(0);
            type = declaration.getType();
        } else {
            type = new BuiltinOverloadedFunctionType(declarations);
        }
        element.setType(type);
    }

    @Override
    public Object visit(Name<Type, DMNContext> element, DMNContext context) {
        // Derive type
        Type type;
        String name = element.getName();
        // Lookup for variables
        Declaration declaration = context.lookupVariableDeclaration(name);
        if (declaration instanceof VariableDeclaration) {
            type = declaration.getType();
            element.setType(type);
        } else {// Lookup for functions
            List<Declaration> declarations = context.lookupFunctionDeclaration(name);
            if (declarations != null && declarations.size() == 1) {
                declaration = declarations.get(0);
                type = declaration.getType();
            } else {
                type = new BuiltinOverloadedFunctionType(declarations);
            }
            element.setType(type);
        }

        return element;
    }

    //
    // Type expressions
    //

    @Override
    public Object visit(NamedTypeExpression<Type, DMNContext> element, DMNContext context) {
        // Derive type
        TDefinitions model = this.dmnModelRepository.getModel(context.getElement());
        com.gs.dmn.QualifiedName typeRef = com.gs.dmn.QualifiedName.toQualifiedName(model, element.getQualifiedName());
        element.setType(this.dmnTransformer.toFEELType(null, typeRef));
        return element;
    }

    @Override
    public Object visit(ListTypeExpression<Type, DMNContext> element, DMNContext context) {
        // Visit children
        element.getElementTypeExpression().accept(this, context);

        // Derive type
        element.setType(new ListType(element.getElementTypeExpression().getType()));

        return element;
    }

    @Override
    public Object visit(ContextTypeExpression<Type, DMNContext> element, DMNContext context) {
        // Derive type
        ContextType contextType = new ContextType();
        for (Pair<String, TypeExpression<Type, DMNContext>> member: element.getMembers()) {
            member.getRight().accept(this, context);
            contextType.addMember(member.getLeft(), new ArrayList<>(), member.getRight().getType());
        }
        element.setType(contextType);
        return element;
    }

    @Override
    public Object visit(FunctionTypeExpression<Type, DMNContext> element, DMNContext context) {
        // Visit children
        element.getParameters().forEach(e -> e.accept(this, context));
        element.getReturnType().accept(this, context);

        // Derive type
        List<FormalParameter<Type, DMNContext>> parameters = element.getParameters().stream().map(e -> new FormalParameter<Type, DMNContext>(null, e.getType())).collect(Collectors.toList());
        Type returnType = element.getReturnType().getType();
        FunctionType functionType = new FEELFunctionType(parameters, returnType, false);
        element.setType(functionType);
        return element;
    }

    private DMNContext visitIterators(final Expression<Type, DMNContext> element, DMNContext context, List<Iterator<Type, DMNContext>> iterators) {
        FEELSemanticVisitor visitor = this;
        DMNContext qContext = this.dmnTransformer.makeIteratorContext(context);
        iterators.forEach(it -> {
            it.accept(visitor, qContext);
            String itName = it.getName();
            Type domainType = it.getDomain().getType();
            Type itType;
            if (domainType instanceof ListType) {
                itType = ((ListType) domainType).getElementType();
            } else if (domainType instanceof RangeType) {
                itType = ((RangeType) domainType).getRangeType();
            } else {
                throw new SemanticError(element, String.format("Cannot resolve iterator type for '%s'", domainType));
            }
            qContext.addDeclaration(this.environmentFactory.makeVariableDeclaration(itName, itType));
        });
        return qContext;
    }

    protected void checkType(Expression<Type, DMNContext> element, String operator, Type leftOperandType, Type rightOperandType, DMNContext context) {
        try {
            Type resultType = OperatorDecisionTable.resultType(operator, normalize(leftOperandType), normalize(rightOperandType));
            if (resultType != null) {
                element.setType(resultType);
            } else {
                throw new SemanticError(element, String.format("Operator '%s' cannot be applied to '%s', '%s'", operator, leftOperandType, rightOperandType));
            }
        } catch (Exception e) {
            throw new SemanticError(element, String.format("Operator '%s' cannot be applied to '%s', '%s' in element '%s'", operator, leftOperandType, rightOperandType, context.getElementName()), e);
        }
    }

    private Type normalize(Type type) {
        return type;
    }

    public Type memberType(Type sourceType, String member) {
        Type memberType = AnyType.ANY;
        if (sourceType instanceof ItemDefinitionType) {
            memberType = ((ItemDefinitionType) sourceType).getMemberType(member);
        } else if (sourceType instanceof ContextType) {
            memberType = ((ContextType) sourceType).getMemberType(member);
        } else if (sourceType instanceof DateType) {
            memberType = DateType.getMemberType(member);
        } else if (sourceType instanceof TimeType) {
            memberType = TimeType.getMemberType(member);
        } else if (sourceType instanceof DateTimeType) {
            memberType = DateTimeType.getMemberType(member);
        } else if (sourceType instanceof DurationType) {
            memberType = DurationType.getMemberType(sourceType, member);
        } else if (sourceType instanceof RangeType) {
            memberType = ((RangeType) sourceType).getMemberType(member);
        }
        return memberType;
    }

    public Type navigationType(Type sourceType, String member) {
        Type type;
        if (sourceType instanceof ListType) {
            Type memberType = memberType(((ListType) sourceType).getElementType(), member);
            type = new ListType(memberType);
        } else {
            type = memberType(sourceType, member);
        }
        return type;
    }
}
