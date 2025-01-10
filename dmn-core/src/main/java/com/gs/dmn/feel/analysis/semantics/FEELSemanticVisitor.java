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

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.context.environment.VariableDeclaration;
import com.gs.dmn.el.analysis.semantics.type.AnyType;
import com.gs.dmn.el.analysis.semantics.type.NullType;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.error.LogAndThrowErrorHandler;
import com.gs.dmn.feel.FEELConstants;
import com.gs.dmn.feel.OperatorDecisionTable;
import com.gs.dmn.feel.analysis.AbstractAnalysisVisitor;
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
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.syntax.ast.expression.textual.ForExpression.PARTIAL_PARAMETER_NAME;

public class FEELSemanticVisitor extends AbstractAnalysisVisitor<Type, DMNContext, Element<Type>> {
    public FEELSemanticVisitor(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        super(dmnTransformer, new LogAndThrowErrorHandler(LOGGER));
    }

    //
    // Tests
    //
    @Override
    public Element<Type> visit(PositiveUnaryTests<Type> element, DMNContext context) {
        // Visit children
        List<PositiveUnaryTest<Type>> positiveUnaryTests = element.getPositiveUnaryTests();
        positiveUnaryTests.forEach(ut -> ut.accept(this, context));

        // Derive type
        List<Type> types = positiveUnaryTests.stream().map(Expression::getType).collect(Collectors.toList());
        element.setType(new TupleType(types));

        return element;
    }

    @Override
    public Element<Type> visit(NegatedPositiveUnaryTests<Type> element, DMNContext context) {
        // Visit children
        PositiveUnaryTests<Type> positiveUnaryTests = element.getPositiveUnaryTests();
        positiveUnaryTests.accept(this, context);

        // Derive type
        Type type = positiveUnaryTests.getType();
        element.setType(type);
        if (type instanceof TupleType) {
            for (Type child : ((TupleType) type).getTypes()) {
                if (child == BooleanType.BOOLEAN || child instanceof RangeType) {
                } else {
                    handleError(context, element, String.format("Operator '%s' cannot be applied to '%s'", "not", child));
                    return null;
                }
            }
        }

        return element;
    }

    @Override
    public Element<Type> visit(Any<Type> element, DMNContext context) {
        // Derive type
        element.setType(BooleanType.BOOLEAN);

        return element;
    }

    @Override
    public Element<Type> visit(NullTest<Type> element, DMNContext context) {
        // Derive type
        element.setType(BooleanType.BOOLEAN);

        return element;
    }

    @Override
    public Element<Type> visit(ExpressionTest<Type> element, DMNContext context) {
        // Visit children
        Expression<Type> expression = element.getExpression();
        expression.accept(this, context);

        // Derive type
        element.setType(BooleanType.BOOLEAN);

        return element;
    }

    @Override
    public Element<Type> visit(OperatorRange<Type> element, DMNContext context) {
        // Visit children
        Expression<Type> endpoint = element.getEndpoint();
        String operator = element.getOperator();
        endpoint.accept(this, context);

        // Derive type
        Type endpointType = endpoint.getType();
        if (context.isExpressionContext()) {
            element.setType(new RangeType(endpointType));
        } else {
            Type inputExpressionType = context.getInputExpressionType();
            element.setType(new RangeType(endpointType));
            // Check according to 7.3.2 UnaryTests Metamodel
            if (operator == null) {
                if (endpointType instanceof ListType && Type.sameSemanticDomain(inputExpressionType, ((ListType) endpointType).getElementType())) {
                    // Endpoint is a list - check contains
                    checkType(element, "=", inputExpressionType, ((ListType) endpointType).getElementType(), context);
                } else if (endpointType instanceof RangeType && Type.sameSemanticDomain(inputExpressionType, ((RangeType) endpointType).getRangeType())) {
                    // Endpoint is a range - check contains
                    checkType(element, "=", inputExpressionType, ((RangeType) endpointType).getRangeType(), context);
                } else {
                    // Endpoint is a value - check equality
                    checkType(element, "=", inputExpressionType, endpointType, context);
                }
            } else {
                // Endpoint has operator, unary test
                checkType(element, operator, inputExpressionType, endpointType, context);
            }
        }

        return element;
    }

    @Override
    public Element<Type> visit(EndpointsRange<Type> element, DMNContext context) {
        Expression<Type> start = element.getStart();
        Expression<Type> end = element.getEnd();
        if (start == null && end == null) {
            handleError(String.format("Illegal range, both endpoints are null in context of element '%s'", context.getElementName()));
            return null;
        }

        // Visit children
        if (start != null) {
            start.accept(this, context);
        }
        if (end != null) {
            end.accept(this, context);
        }

        // Derive type
        if (context.isExpressionContext()) {
            // Evaluate as expression
            Type endpointType = element.getEndpointType();
            element.setType(new RangeType(endpointType));
        } else {
            // Evaluate as test
            Type inputExpressionType = context.getInputExpressionType();
            Type endpointType = element.getEndpointType();
            element.setType(new RangeType(endpointType));
            // Involves relational operations
            checkType(element, "<", inputExpressionType, endpointType, context);
        }

        return element;
    }

    @Override
    public Element<Type> visit(ListTest<Type> element, DMNContext context) {
        // Visit children
        ListLiteral<Type> listLiteral = element.getListLiteral();

        // Calculate optimization
        if (listLiteral.allTestsAreEqualityTest()) {
            List<Expression<Type>> expressionList = listLiteral.getExpressionList();
            List<Expression<Type>> newListElements = expressionList.stream().map(e -> e instanceof OperatorRange ? ((OperatorRange<Type>) e).getEndpoint() : e).collect(Collectors.toList());
            ListLiteral<Type> newListLiteral = new ListLiteral<>(newListElements);
            newListLiteral.accept(this, context);

            element.setOptimizedListLiteral(newListLiteral);
        } else {
            listLiteral.accept(this, context);
        }

        // Derive type
        element.setType(BOOLEAN);
        Type inputExpressionType = context.getInputExpressionType();
        ListLiteral<Type> optimizedListLiteral = element.getOptimizedListLiteral();
        if (optimizedListLiteral != null) {
            // Optimisation
            Type optimizedListType = optimizedListLiteral.getType();
            Type optimizedListElementType = ((ListType) optimizedListType).getElementType();
            if (com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(inputExpressionType, optimizedListType)) {
                // both are compatible lists
            } else if (com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(inputExpressionType, optimizedListElementType)) {
                // input conforms to element in the list
            } else {
                handleError(context, element, String.format("Cannot compare '%s', '%s'", inputExpressionType, optimizedListType));
                return null;
            }
        } else {
            // test is list of ranges compatible with input
        }

        return element;
    }

    //
    // Textual expressions
    //
    @Override
    public Element<Type> visit(FunctionDefinition<Type> element, DMNContext context) {
        // Analyze parameters
        element.getFormalParameters().forEach(p -> p.accept(this, context));

        // Analyze return type
        Type returnType = null;
        TypeExpression<Type> returnTypeExpression = element.getReturnTypeExpression();
        if (returnTypeExpression != null) {
            returnTypeExpression.accept(this, context);
            returnType = returnTypeExpression.getType();
        }

        // Make body environment
        DMNContext bodyContext = this.dmnTransformer.makeFunctionContext(element, context);

        // Analyze body
        Expression<Type> body = element.getBody();
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
    public Element<Type> visit(FormalParameter<Type> element, DMNContext context) {
        if (element.getType() == null) {
            TypeExpression<Type> typeExpression = element.getTypeExpression();
            if (typeExpression != null) {
                typeExpression.accept(this, context);
                element.setType(typeExpression.getType());
            }
        }
        return element;
    }

    @Override
    public Element<Type> visit(Context<Type> element, DMNContext context) {
        // Visit children
        DMNContext localContext = this.dmnTransformer.makeLocalContext(context);
        List<ContextEntry<Type>> entries = element.getEntries();
        entries.forEach(ce -> ce.accept(this, localContext));

        // Derive type
        ContextType type = new ContextType();
        entries.forEach(e -> type.addMember(e.getKey().getKey(), Collections.emptyList(), e.getExpression().getType()));
        element.setType(type);

        return element;
    }

    @Override
    public Element<Type> visit(ContextEntry<Type> element, DMNContext context) {
        // Visit children
        ContextEntryKey<Type> key = (ContextEntryKey<Type>) element.getKey().accept(this, context);
        Expression<Type> expression = (Expression<Type>) element.getExpression().accept(this, context);

        // Add declaration
        context.addDeclaration(this.environmentFactory.makeVariableDeclaration(key.getKey(), expression.getType()));

        return element;
    }

    @Override
    public Element<Type> visit(ContextEntryKey<Type> element, DMNContext context) {
        return element;
    }

    @Override
    public Element<Type> visit(ForExpression<Type> element, DMNContext context) {
        // Visit children
        List<Iterator<Type>> iterators = element.getIterators();
        DMNContext qParamsContext = visitIterators(element, context, iterators);
        Type partialType = ListType.ANY_LIST;
        qParamsContext.addDeclaration(this.environmentFactory.makeVariableDeclaration(PARTIAL_PARAMETER_NAME, partialType));
        Expression<Type> body = element.getBody();
        body.accept(this, qParamsContext);

        // Derive type
        element.setType(new ListType(body.getType()));

        // Visit body with new context
        qParamsContext.updateVariableDeclaration(PARTIAL_PARAMETER_NAME, element.getType());
        body.accept(new UpdatePartialVisitor<>(element.getType(), this.errorHandler), qParamsContext);

        return element;
    }

    @Override
    public Element<Type> visit(Iterator<Type> element, DMNContext context) {
        // Visit children
        element.getDomain().accept(this, context);

        return element;
    }

    @Override
    public Element<Type> visit(ExpressionIteratorDomain<Type> element, DMNContext context) {
        // Visit children
        Expression<Type> expression = element.getExpression();
        expression.accept(this, context);

        // Derive type
        element.setType(expression.getType());

        return element;
    }

    @Override
    public Element<Type> visit(RangeIteratorDomain<Type> element, DMNContext context) {
        // Visit children
        Expression<Type> start = element.getStart();
        start.accept(this, context);
        element.getEnd().accept(this, context);

        // Derive type
        element.setType(new RangeType(start.getType()));

        return element;
    }

    @Override
    public Element<Type> visit(IfExpression<Type> element, DMNContext context) {
        // Visit children
        Expression<Type> condition = element.getCondition();
        condition.accept(this, context);
        Expression<Type> thenExpression = element.getThenExpression();
        thenExpression.accept(this, context);
        Expression<Type> elseExpression = element.getElseExpression();
        elseExpression.accept(this, context);

        // Derive type
        Type conditionType = condition.getType();
        Type thenType = thenExpression.getType();
        Type elseType = elseExpression.getType();
        if (conditionType != BOOLEAN) {
            handleError(context, element, String.format("Condition type must be boolean. Found '%s' instead.", conditionType));
            return null;
        } else if (com.gs.dmn.el.analysis.semantics.type.Type.isNullType(thenType) && com.gs.dmn.el.analysis.semantics.type.Type.isNullType(elseType)) {
            handleError(context, element, String.format("Types of then and else branches are incompatible. Found '%s' and '%s'.", thenType, elseType));
            return null;
        } else if (com.gs.dmn.el.analysis.semantics.type.Type.isNullType(thenType)) {
            element.setType(elseType);
        } else if (com.gs.dmn.el.analysis.semantics.type.Type.isNullType(elseType)) {
            element.setType(thenType);
        } else {
            if (com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(thenType, elseType)) {
                element.setType(elseType);
            } else if (com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(elseType, thenType)) {
                element.setType(thenType);
            } else {
                handleError(context, element, String.format("Types of then and else branches are incompatible. Found '%s' and '%s'.", thenType, elseType));
                return null;
            }
        }

        return element;
    }

    @Override
    public Element<Type> visit(QuantifiedExpression<Type> element, DMNContext context) {
        // Visit children
        List<Iterator<Type>> iterators = element.getIterators();
        DMNContext qParamsContext = visitIterators(element, context, iterators);
        Expression<Type> body = element.getBody();
        body.accept(this, qParamsContext);

        // Derive type
        element.setType(body.getType());

        return element;
    }

    @Override
    public Element<Type> visit(FilterExpression<Type> element, DMNContext context) {
        // analyse source
        Expression<Type> source = element.getSource();
        source.accept(this, context);

        // transform filter (add missing 'item' in front of members)
        Expression<Type> filter = transformFilter(source, element.getFilter(), FilterExpression.FILTER_PARAMETER_NAME, context);
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
                handleError(context, element, String.format("Cannot resolve type for '%s'", element));
            }
        } else {
            if (filterType == NUMBER) {
                element.setType(sourceType);
            } else if (filterType == BOOLEAN) {
                element.setType(new ListType(sourceType));
            } else {
                handleError(context, element, String.format("Cannot resolve type for '%s'", element));
            }
        }

        return element;
    }

    private Expression<Type> transformFilter(Expression<Type> source, Expression<Type> filter, String filterVariableName, DMNContext context) {
        Type elementType = source.getType();
        if (elementType instanceof ListType) {
            elementType = ((ListType) elementType).getElementType();
        }
        return (Expression<Type>)filter.accept(new AddItemFilterVisitor<>(filterVariableName, elementType, this.errorHandler), context);
    }

    @Override
    public Element<Type> visit(InstanceOfExpression<Type> element, DMNContext context) {
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
    public Element<Type> visit(ExpressionList<Type> element, DMNContext context) {
        // Visit children
        List<Expression<Type>> expressionList = element.getExpressionList();
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
    public Element<Type> visit(Conjunction<Type> element, DMNContext context) {
        // Visit children
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);

        // Derive type
        // Not need to check the operand types. or, and, not are total functions
        element.setType(BOOLEAN);

        return element;
    }

    @Override
    public Element<Type> visit(Disjunction<Type> element, DMNContext context) {
        // Visit children
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);

        // Derive type
        // Not need to check the operand types. or, and, not are total functions
        element.setType(BOOLEAN);

        return element;
    }

    @Override
    public Element<Type> visit(LogicNegation<Type> element, DMNContext context) {
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
    public Element<Type> visit(Relational<Type> element, DMNContext context) {
        // Visit children
        Expression<Type> leftOperand = element.getLeftOperand();
        leftOperand.accept(this, context);
        Expression<Type> rightOperand = element.getRightOperand();
        rightOperand.accept(this, context);
        String operator = element.getOperator();

        // Derive type
        element.setType(BOOLEAN);
        checkType(element, operator, leftOperand.getType(), rightOperand.getType(), context);

        return element;
    }

    @Override
    public Element<Type> visit(BetweenExpression<Type> element, DMNContext context) {
        // Visit children
        Expression<Type> value = element.getValue();
        value.accept(this, context);
        Expression<Type> leftEndpoint = element.getLeftEndpoint();
        leftEndpoint.accept(this, context);
        Expression<Type> rightEndpoint = element.getRightEndpoint();
        rightEndpoint.accept(this, context);

        // Derive type
        element.setType(BOOLEAN);
        checkType(element, ">=", value.getType(), leftEndpoint.getType(), context);
        checkType(element, "<=", value.getType(), rightEndpoint.getType(), context);

        return element;
    }

    @Override
    public Element<Type> visit(InExpression<Type> element, DMNContext parentContext) {
        // Visit children
        Expression<Type> value = element.getValue();
        value.accept(this, parentContext);

        // Visit tests with value type injected in scope
        DMNContext testContext = parentContext.isTestContext() ? parentContext : this.dmnTransformer.makeUnaryTestContext(value, parentContext);
        element.getTests().forEach(t -> t.accept(this, testContext));

        // Derive type
        element.setType(BOOLEAN);

        return element;
    }

    //
    // Arithmetic expressions
    //
    @Override
    public Element<Type> visit(Addition<Type> element, DMNContext context) {
        // Visit children
        Expression<Type> leftOperand = element.getLeftOperand();
        leftOperand.accept(this, context);
        Expression<Type> rightOperand = element.getRightOperand();
        rightOperand.accept(this, context);

        // Derive type
        element.setType(NUMBER);
        checkType(element, element.getOperator(), leftOperand.getType(), rightOperand.getType(), context);

        return element;
    }

    @Override
    public Element<Type> visit(Multiplication<Type> element, DMNContext context) {
        // Visit children
        Expression<Type> leftOperand = element.getLeftOperand();
        leftOperand.accept(this, context);
        Expression<Type> rightOperand = element.getRightOperand();
        rightOperand.accept(this, context);

        // Derive type
        element.setType(NUMBER);
        checkType(element, element.getOperator(), leftOperand.getType(), rightOperand.getType(), context);

        return element;
    }

    @Override
    public Element<Type> visit(Exponentiation<Type> element, DMNContext context) {
        // Visit children
        Expression<Type> leftOperand = element.getLeftOperand();
        leftOperand.accept(this, context);
        Expression<Type> rightOperand = element.getRightOperand();
        rightOperand.accept(this, context);

        // Derive type
        element.setType(NUMBER);
        checkType(element, element.getOperator(), leftOperand.getType(), rightOperand.getType(), context);

        return element;
    }

    @Override
    public Element<Type> visit(ArithmeticNegation<Type> element, DMNContext context) {
        // Visit children
        element.getLeftOperand().accept(this, context);

        // Derive type
        Type type = element.getLeftOperand().getType();
        if (type != NUMBER && !(type instanceof DurationType)) {
            handleError(context, element, String.format("Operator '%s' cannot be applied to '%s'", element.getOperator(), type));
        }

        // Derive type
        element.setType(type);
        return element;
    }

    //
    // Postfix expressions
    //
    @Override
    public Element<Type> visit(PathExpression<Type> element, DMNContext context) {
        // Visit children
        Expression<Type> source = element.getSource();
        source.accept(this, context);

        // Derive type
        Type sourceType = source.getType();
        Type type = navigationType(sourceType, element.getMember());
        element.setType(type);

        return element;
    }

    @Override
    public Element<Type> visit(FunctionInvocation<Type> element, DMNContext context) {
        // Visit children
        Expression<Type> function = element.getFunction();
        Parameters<Type> parameters = element.getParameters();
        if (function instanceof Name && "sort".equals(((Name<Type>) function).getName())) {
            visitSortParameters(element, context, parameters);
        } else if (function instanceof Name && "list replace".equals(((Name<Type>) function).getName())) {
            visitListReplaceParameters(element, context, parameters);
        } else {
            parameters.accept(this, context);
        }
        function.accept(this, context);

        // Derive type
        inferMissingTypesInFEELFunction(element.getFunction(), element.getParameters(), context);
        if (function instanceof Name) {
            String functionName = ((Name<Type>) function).getName();
            FunctionInvocationUtils.deriveType(element, context, functionName);
        } else if (function instanceof QualifiedName && ((QualifiedName<Type>) function).getNames().size() == 1) {
            String functionName = ((QualifiedName<Type>) function).getQualifiedName();
            FunctionInvocationUtils.deriveType(element, context, functionName);
        } else {
            FunctionType functionType = (FunctionType) function.getType();

            FunctionInvocationUtils.setInvocationType(element, functionType);
            if (parameters instanceof NamedParameters) {
                ParameterConversions<Type> parameterConversions = new NamedParameterConversions<>(functionType.getParameters());
                parameters.setParameterConversions(parameterConversions);
            } else {
                ParameterConversions<Type> parameterConversions = new PositionalParameterConversions<>(functionType.getParameterTypes());
                parameters.setParameterConversions(parameterConversions);
            }
            parameters.setConvertedParameterTypes(parameters.getSignature());
        }

        return element;
    }

    private void visitSortParameters(FunctionInvocation<Type> element, DMNContext context, Parameters<Type> parameters) {
        boolean success = false;
        if (parameters.getSignature().size() == 2) {
            Expression<Type> listExpression;
            Expression<Type> lambdaExpression;
            if (parameters instanceof PositionalParameters) {
                listExpression = ((PositionalParameters<Type>) parameters).getParameters().get(0);
                lambdaExpression = ((PositionalParameters<Type>) parameters).getParameters().get(1);
            } else {
                listExpression = ((NamedParameters<Type>) parameters).getParameters().get("list");
                lambdaExpression = ((NamedParameters<Type>) parameters).getParameters().get("function");
            }
            listExpression.accept(this, context);
            Type listType = listExpression.getType();
            if (listType instanceof ListType) {
                Type elementType = ((ListType) listType).getElementType();
                if (lambdaExpression instanceof FunctionDefinition) {
                    List<FormalParameter<Type>> formalParameters = ((FunctionDefinition<Type>) lambdaExpression).getFormalParameters();
                    formalParameters.forEach(p -> p.setType(elementType));
                    success = true;
                } else if (lambdaExpression instanceof Name) {
                    Declaration declaration = context.lookupVariableDeclaration(((Name<Type>) lambdaExpression).getName());
                    Type type = declaration.getType();
                    if (type instanceof FunctionType && !(type instanceof BuiltinFunctionType)) {
                        List<FormalParameter<Type>> formalParameters = ((FunctionType) type).getParameters();
                        formalParameters.forEach(p -> p.setType(elementType));
                        success = true;
                    }
                }
            }
            lambdaExpression.accept(this, context);
        }
        if (!success) {
            handleError(context, element, String.format("Cannot infer parameter type for lambda in sort call '%s'", element));
        }
    }

    private void visitListReplaceParameters(FunctionInvocation<Type> element, DMNContext context, Parameters<Type> parameters) {
        ParameterTypes<Type> signature = parameters.getSignature();
        if (signature.size() == 3) {
            Expression<Type> listExpression;
            Expression<Type> secondParameter;
            boolean hasPosition = false;
            Expression<Type> newItemExpression;
            if (parameters instanceof PositionalParameters) {
                listExpression = ((PositionalParameters<Type>) parameters).getParameters().get(0);
                secondParameter = ((PositionalParameters<Type>) parameters).getParameters().get(1);
                newItemExpression = ((PositionalParameters<Type>) parameters).getParameters().get(2);
            } else {
                listExpression = ((NamedParameters<Type>) parameters).getParameters().get("list");
                secondParameter = ((NamedParameters<Type>) parameters).getParameters().get("position");
                if (secondParameter == null) {
                    hasPosition = true;
                    secondParameter = ((NamedParameters<Type>) parameters).getParameters().get("match");
                }
                newItemExpression = ((NamedParameters<Type>) parameters).getParameters().get("newItem");
            }
            listExpression.accept(this, context);
            Type listType = listExpression.getType();
            if (listType instanceof ListType) {
                Type elementType = ((ListType) listType).getElementType();
                if (secondParameter instanceof FunctionDefinition) {
                    List<FormalParameter<Type>> formalParameters = ((FunctionDefinition<Type>) secondParameter).getFormalParameters();
                    formalParameters.forEach(p -> p.setType(elementType));
                } else if (secondParameter instanceof Name) {
                    Declaration declaration = context.lookupVariableDeclaration(((Name<Type>) secondParameter).getName());
                    Type type = declaration.getType();
                    if (type instanceof FunctionType && !(type instanceof BuiltinFunctionType)) {
                        List<FormalParameter<Type>> formalParameters = ((FunctionType) type).getParameters();
                        formalParameters.forEach(p -> p.setType(elementType));
                    }
                }
            }
            secondParameter.accept(this, context);
            // Check type for named parameters invocation
            Type secondParameterType = secondParameter.getType();
            if (hasPosition && secondParameterType == NUMBER) {
                handleError(context, element, String.format("Parameter 'position' must be a number, found '%s'", secondParameterType));
            }
            // Check cardinality and return type of 'match' parameter
            if (secondParameterType instanceof FunctionType) {
                List<FormalParameter<Type>> matchSignature = ((FunctionType) secondParameterType).getParameters();
                if (matchSignature.size() != 2) {
                    handleError(context, element, String.format("'match' parameter should have 2 parameters, found '%s'", matchSignature.size()));
                }
                Type returnType = ((FunctionType) secondParameterType).getReturnType();
                if (returnType != BOOLEAN) {
                    handleError(context, element, String.format("'match' parameter should return boolean, found '%s'", returnType));
                }
            }
            newItemExpression.accept(this, context);
        } else {
            handleError(context, element, String.format("Expecting 3 parameters found '%s'", signature.size()));
        }
    }

    private void inferMissingTypesInFEELFunction(Expression<Type> function, Parameters<Type> arguments, DMNContext context) {
        Type functionType = function.getType();
        if (functionType instanceof FEELFunctionType) {
            FEELFunctionType feelFunctionType = (FEELFunctionType) functionType;
            if (!feelFunctionType.isFullySpecified()) {
                // Bind names to types in function type
                bindNameToTypes(feelFunctionType.getParameters(), arguments);

                // Process function definition
                FunctionDefinition<Type> functionDefinition = feelFunctionType.getFunctionDefinition();
                if (functionDefinition != null) {
                    // Bind names to types in function definition
                    bindNameToTypes(functionDefinition.getFormalParameters(), arguments);

                    // Set return type
                    functionDefinition.accept(this, context);
                    Type returnType = feelFunctionType.getReturnType();
                    if (com.gs.dmn.el.analysis.semantics.type.Type.isNullOrAny(returnType)) {
                        Type newReturnType = functionDefinition.getReturnType();
                        feelFunctionType.setReturnType(newReturnType);
                    }
                }
            }
        }
    }

    private void bindNameToTypes(List<FormalParameter<Type>> parameters, Parameters<Type> arguments) {
        if (arguments instanceof NamedParameters) {
            for(FormalParameter<Type> p: parameters) {
                Type type = p.getType();
                if (com.gs.dmn.el.analysis.semantics.type.Type.isNullOrAny(type)) {
                    Type newType = ((NamedParameters<Type>) arguments).getParameters().get(p.getName()).getType();
                    p.setType(newType);
                }
            }
        } else if (arguments instanceof PositionalParameters) {
            for(int i=0; i < parameters.size(); i++) {
                FormalParameter<Type> p = parameters.get(i);
                Type type = p.getType();
                if (com.gs.dmn.el.analysis.semantics.type.Type.isNullOrAny(type)) {
                    Type newType = ((PositionalParameters<Type>) arguments).getParameters().get(i).getType();
                    p.setType(newType);
                }
            }
        }
    }

    @Override
    public Element<Type> visit(NamedParameters<Type> element, DMNContext context) {
        // Visit children
        element.getParameters().values().forEach(p -> p.accept(this, context));

        return element;
    }

    @Override
    public Element<Type> visit(PositionalParameters<Type> element, DMNContext context) {
        // Visit children
        element.getParameters().forEach(p -> p.accept(this, context));

        return element;
    }

    //
    // Primary expressions
    //
    @Override
    public Element<Type> visit(BooleanLiteral<Type> element, DMNContext context) {
        // Derive type
        element.setType(BOOLEAN);

        return element;
    }

    @Override
    public Element<Type> visit(DateTimeLiteral<Type> element, DMNContext context) {
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
                element.setType(YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION);
            } else if (element.isDaysAndTimeDuration(element.getLexeme())) {
                element.setType(DaysAndTimeDurationType.DAYS_AND_TIME_DURATION);
            } else {
                handleError(context, element, String.format("Date time literal '%s(%s) is not supported", conversionFunction, element.getLexeme()));
                return null;
            }
        } else {
            handleError(context, element, String.format("Date time literal '%s(%s)' is not supported", conversionFunction, element.getLexeme()));
            return null;
        }

        return element;
    }

    @Override
    public Element<Type> visit(NullLiteral<Type> element, DMNContext context) {
        // Derive type
        element.setType(NullType.NULL);

        return element;
    }

    @Override
    public Element<Type> visit(NumericLiteral<Type> element, DMNContext context) {
        // Derive type
        element.setType(NUMBER);

        return element;
    }

    @Override
    public Element<Type> visit(StringLiteral<Type> element, DMNContext context) {
        // Derive type
        element.setType(StringType.STRING);

        return element;
    }

    @Override
    public Element<Type> visit(ListLiteral<Type> element, DMNContext context) {
        // Visit children
        List<Expression<Type>> expressionList = element.getExpressionList();
        expressionList.forEach(e -> e.accept(this, context));

        // Derive type
        if (expressionList.isEmpty()) {
            if (context.isExpressionContext()) {
                // conforms to any other list
                element.setType(new ListType(NullType.NULL));
            } else {
                element.setType(ListType.ANY_LIST);
            }
        } else {
            checkListElementTypes(element);
        }

        return element;
    }

    private void checkListElementTypes(ListLiteral<Type> element) {
        List<Type> types = element.getExpressionList().stream().map(Expression::getType).collect(Collectors.toList());
        // Find root type if possible
        if (types.size() == 0) {
            element.setType(ListType.ANY_LIST);
        } else {
            Type rooType = types.get(0);
            for (int i=1; i<types.size(); i++) {
                Type type = types.get(i);
                if (com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(type, rooType)) {
                } else if (com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(rooType, type)) {
                    rooType = type;
                } else {
                    rooType = null;
                    break;
                }
            }
            if (rooType == null) {
                element.setType(ListType.ANY_LIST);
            } else {
                element.setType(new ListType(rooType));
            }
        }
    }

    @Override
    public Element<Type> visit(QualifiedName<Type> element, DMNContext context) {
        // Derive type
        List<String> names = element.getNames();
        if (names ==  null || names.isEmpty()) {
            handleError(context, element, "Illegal qualified name.");
            return null;
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

    private void deriveType(QualifiedName<Type> element, DMNContext context) {
        deriveType(element, context, element.getName());
    }

    @Override
    public Element<Type> visit(Name<Type> element, DMNContext context) {
        deriveType(element, context, element.getName());
        return element;
    }

    private void deriveType(NamedExpression<Type> element, DMNContext context, String name) {
        Type type;
        // Lookup for variables
        Declaration declaration = context.lookupVariableDeclaration(name);
        if (declaration instanceof VariableDeclaration) {
            type = declaration.getType();
            element.setType(type);
        } else {
            // Lookup for functions
            List<Declaration> declarations = context.lookupFunctionDeclaration(name);
            if (declarations != null && !declarations.isEmpty()) {
                if (declarations.size() == 1) {
                    declaration = declarations.get(0);
                    type = declaration.getType();
                } else {
                    type = new BuiltinOverloadedFunctionType(declarations);
                }
                element.setType(type);
            }
        }
    }

    //
    // Type expressions
    //

    @Override
    public Element<Type> visit(NamedTypeExpression<Type> element, DMNContext context) {
        // Derive type
        TDefinitions model = this.dmnModelRepository.getModel(context.getElement());
        com.gs.dmn.QualifiedName typeRef = com.gs.dmn.QualifiedName.toQualifiedName(model, element.getQualifiedName());
        element.setType(this.dmnTransformer.toFEELType(null, typeRef));
        return element;
    }

    @Override
    public Element<Type> visit(ContextTypeExpression<Type> element, DMNContext context) {
        // Derive type
        ContextType contextType = new ContextType();
        for (Pair<String, TypeExpression<Type>> member: element.getMembers()) {
            member.getRight().accept(this, context);
            contextType.addMember(member.getLeft(), new ArrayList<>(), member.getRight().getType());
        }
        element.setType(contextType);
        return element;
    }

    @Override
    public Element<Type> visit(RangeTypeExpression<Type> element, DMNContext context) {
        // Visit children
        element.getElementTypeExpression().accept(this, context);

        // Derive type
        element.setType(new RangeType(element.getElementTypeExpression().getType()));

        return element;
    }

    @Override
    public Element<Type> visit(FunctionTypeExpression<Type> element, DMNContext context) {
        // Visit children
        element.getParameters().forEach(e -> e.accept(this, context));
        element.getReturnType().accept(this, context);

        // Derive type
        List<FormalParameter<Type>> parameters = element.getParameters().stream().map(e -> new FormalParameter<>(null, e.getType())).collect(Collectors.toList());
        Type returnType = element.getReturnType().getType();
        FunctionType functionType = new FEELFunctionType(parameters, returnType, false);
        element.setType(functionType);
        return element;
    }

    @Override
    public Element<Type> visit(ListTypeExpression<Type> element, DMNContext context) {
        // Visit children
        element.getElementTypeExpression().accept(this, context);

        // Derive type
        element.setType(new ListType(element.getElementTypeExpression().getType()));

        return element;
    }

    private DMNContext visitIterators(final Expression<Type> element, DMNContext context, List<Iterator<Type>> iterators) {
        FEELSemanticVisitor visitor = this;
        DMNContext qContext = this.dmnTransformer.makeIteratorContext(context);
        iterators.forEach(it -> {
            it.accept(visitor, qContext);
            String itName = it.getName();
            Type domainType = it.getDomain().getType();
            Type itType = null;
            if (domainType instanceof ListType) {
                itType = ((ListType) domainType).getElementType();
            } else if (domainType instanceof RangeType) {
                itType = ((RangeType) domainType).getRangeType();
            } else {
                handleError(context, element, String.format("Cannot resolve iterator type for '%s'", domainType));
            }
            qContext.addDeclaration(this.environmentFactory.makeVariableDeclaration(itName, itType));
        });
        return qContext;
    }

    protected void checkType(Expression<Type> element, String operator, Type leftOperandType, Type rightOperandType, DMNContext context) {
        try {
            Type resultType = OperatorDecisionTable.resultType(operator, normalize(leftOperandType), normalize(rightOperandType));
            if (resultType != null) {
                element.setType(resultType);
            } else {
                handleError(context, element, String.format("Operator '%s' cannot be applied to '%s', '%s'", operator, leftOperandType, rightOperandType));
            }
        } catch (Exception e) {
            handleError(context, element, String.format("Operator '%s' cannot be applied to '%s', '%s'", operator, leftOperandType, rightOperandType), e);
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
