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

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.VariableDeclaration;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.AbstractAnalysisVisitor;
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
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.test.*;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import org.omg.spec.dmn._20191111.model.TDefinitions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FEELSemanticVisitor extends AbstractAnalysisVisitor {
    public FEELSemanticVisitor(BasicDMNToNativeTransformer dmnTransformer) {
        super(dmnTransformer);
    }

    //
    // Tests
    //
    @Override
    public Object visit(PositiveUnaryTests element, FEELContext context) {
        element.getPositiveUnaryTests().forEach(ut -> ut.accept(this, context));
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(NegatedPositiveUnaryTests element, FEELContext context) {
        element.getPositiveUnaryTests().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(SimplePositiveUnaryTests element, FEELContext context) {
        element.getSimplePositiveUnaryTests().forEach(sput -> sput.accept(this, context));
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(NegatedSimplePositiveUnaryTests element, FEELContext context) {
        element.getSimplePositiveUnaryTests().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(Any element, FEELContext context) {
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(NullTest element, FEELContext context) {
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(ExpressionTest element, FEELContext context) {
        element.getExpression().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(OperatorTest element, FEELContext context) {
        element.getEndpoint().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(RangeTest element, FEELContext context) {
        element.getStart().accept(this, context);
        element.getEnd().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(ListTest element, FEELContext context) {
        element.getListLiteral().accept(this, context);
        element.deriveType(context);
        return element;
    }

    //
    // Textual expressions
    //
    @Override
    public Object visit(FunctionDefinition element, FEELContext context) {
        // Analyze parameters
        element.getFormalParameters().forEach(p -> p.accept(this, context));

        // Analyze return type
        Type returnType = null;
        TypeExpression returnTypeExpression = element.getReturnTypeExpression();
        if (returnTypeExpression != null) {
            returnTypeExpression.accept(this, context);
            returnType = returnTypeExpression.getType();
        }

        // Make body environment
        Environment environment = context.getEnvironment();
        Environment bodyEnvironment = this.environmentFactory.makeEnvironment(environment);
        element.getFormalParameters().forEach(
                p -> bodyEnvironment.addDeclaration(this.environmentFactory.makeVariableDeclaration(p.getName(), p.getType()))
        );

        // Analyze body
        FEELContext bodyContext = FEELContext.makeContext(context.getElement(), bodyEnvironment);
        Expression body = element.getBody();
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
    public Object visit(FormalParameter element, FEELContext context) {
        if (element.getType() == null) {
            TypeExpression typeExpression = element.getTypeExpression();
            if (typeExpression != null) {
                typeExpression.accept(this, context);
                element.setType(typeExpression.getType());
            }
        }
        return element;
    }

    @Override
    public Object visit(Context element, FEELContext context) {
        Environment environment = context.getEnvironment();
        Environment entryEnvironment = this.environmentFactory.makeEnvironment(environment);
        FEELContext entryContext = FEELContext.makeContext(context.getElement(), entryEnvironment);
        element.getEntries().forEach(ce -> ce.accept(this, entryContext));
        element.deriveType(entryContext);
        return element;
    }

    @Override
    public Object visit(ContextEntry element, FEELContext context) {
        ContextEntryKey key = (ContextEntryKey) element.getKey().accept(this, context);
        Expression expression = (Expression) element.getExpression().accept(this, context);
        Environment environment = context.getEnvironment();
        environment.addDeclaration(this.environmentFactory.makeVariableDeclaration(key.getKey(), expression.getType()));
        return element;
    }

    @Override
    public Object visit(ContextEntryKey element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(ForExpression element, FEELContext context) {
        List<Iterator> iterators = element.getIterators();
        FEELContext qParamsContext = visitIterators(element, context, iterators);
        qParamsContext.getEnvironment().addDeclaration(this.environmentFactory.makeVariableDeclaration(ForExpression.PARTIAL_PARAMETER_NAME, new ListType(NullType.NULL)));
        element.getBody().accept(this, qParamsContext);
        element.deriveType(qParamsContext);
        qParamsContext.getEnvironment().updateVariableDeclaration(ForExpression.PARTIAL_PARAMETER_NAME, element.getType());
        element.getBody().accept(new UpdatePartialVisitor(element.getType()), qParamsContext);
        return element;
    }

    @Override
    public Object visit(Iterator element, FEELContext context) {
        element.getDomain().accept(this, context);
        return element;
    }

    @Override
    public Object visit(ExpressionIteratorDomain element, FEELContext context) {
        element.getExpression().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(RangeIteratorDomain element, FEELContext context) {
        element.getStart().accept(this, context);
        element.getEnd().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(IfExpression element, FEELContext context) {
        element.getCondition().accept(this, context);
        element.getThenExpression().accept(this, context);
        element.getElseExpression().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(QuantifiedExpression element, FEELContext context) {
        List<Iterator> iterators = element.getIterators();
        FEELContext qParamsContext = visitIterators(element, context, iterators);
        element.getBody().accept(this, qParamsContext);
        element.deriveType(qParamsContext);
        return element;
    }

    @Override
    public Object visit(FilterExpression element, FEELContext context) {
        // analyse source
        Expression source = element.getSource();
        source.accept(this, context);
        // transform filter (add missing 'item' in front of members)
        Expression filter = transformFilter(source, element.getFilter(), FilterExpression.FILTER_PARAMETER_NAME, context);
        element.setFilter(filter);
        // analyse filter
        FEELContext filterContext = makeFilterContext(context, source, FilterExpression.FILTER_PARAMETER_NAME);
        element.getFilter().accept(this, filterContext);
        // derive type
        element.deriveType(filterContext);
        return element;
    }

    private Expression transformFilter(Expression source, Expression filter, String filterVariableName, FEELContext context) {
        Type elementType = source.getType();
        if (elementType instanceof ListType) {
            elementType = ((ListType) elementType).getElementType();
        }
        return (Expression)filter.accept(new AddItemFilterVisitor(filterVariableName, elementType), context);
    }

    @Override
    public Object visit(InstanceOfExpression element, FEELContext context) {
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    //
    // Expressions
    //
    @Override
    public Object visit(ExpressionList element, FEELContext context) {
        element.getExpressionList().forEach(e -> e.accept(this, context));
        element.deriveType(context);
        return element;
    }

    //
    // Logic expressions
    //
    @Override
    public Object visit(Conjunction element, FEELContext context) {
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(Disjunction element, FEELContext context) {
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(LogicNegation element, FEELContext context) {
        element.getLeftOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    //
    // Comparison expressions
    //
    @Override
    public Object visit(Relational element, FEELContext context) {
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(BetweenExpression element, FEELContext context) {
        element.getValue().accept(this, context);
        element.getLeftEndpoint().accept(this, context);
        element.getRightEndpoint().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(InExpression element, FEELContext context) {
        Expression value = element.getValue();
        value.accept(this, context);

        // Visit tests with value type injected in scope
        Environment environment = context.getEnvironment();
        Environment testEnvironment = this.environmentFactory.makeEnvironment(environment, value);
        FEELContext testContext = FEELContext.makeContext(context.getElement(), testEnvironment);
        element.getTests().forEach(t -> t.accept(this, testContext));

        element.deriveType(context);
        return element;
    }

    //
    // Arithmetic expressions
    //
    @Override
    public Object visit(Addition element, FEELContext context) {
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(Multiplication element, FEELContext context) {
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(Exponentiation element, FEELContext context) {
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(ArithmeticNegation element, FEELContext context) {
        element.getLeftOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    //
    // Postfix expressions
    //
    @Override
    public Object visit(PathExpression element, FEELContext context) {
        element.getSource().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(FunctionInvocation element, FEELContext context) {
        Expression function = element.getFunction();
        Parameters parameters = element.getParameters();
        if (function instanceof Name && "sort".equals(((Name) function).getName())) {
            boolean success = false;
            if (parameters.getSignature().size() == 2) {
                Expression listExpression;
                Expression lambdaExpression;
                if (parameters instanceof PositionalParameters) {
                    listExpression = ((PositionalParameters) parameters).getParameters().get(0);
                    lambdaExpression = ((PositionalParameters) parameters).getParameters().get(1);
                } else {
                    listExpression = ((NamedParameters) parameters).getParameters().get("list");
                    lambdaExpression = ((NamedParameters) parameters).getParameters().get("function");
                }
                listExpression.accept(this, context);
                Type listType = listExpression.getType();
                if (listType instanceof ListType && lambdaExpression instanceof FunctionDefinition) {
                    List<FormalParameter> formalParameters = ((FunctionDefinition) lambdaExpression).getFormalParameters();
                    formalParameters.forEach(p -> p.setType(((ListType) listType).getElementType()));
                    success = true;
                }
                lambdaExpression.accept(this, context);
            }
            if (!success) {
                throw new DMNRuntimeException(String.format("Cannot infer parameter type for lambda in sort call '%s'", element));
            }
        } else {
            parameters.accept(this, context);
            function.accept(this, context);
        }

        inferMissingTypesInFunction(element.getFunction(), element.getParameters(), context);

        element.deriveType(context);
        return element;
    }

    private void inferMissingTypesInFunction(Expression function, Parameters arguments, FEELContext context) {
        Type functionType = function.getType();
        if (functionType instanceof FEELFunctionType) {
            FEELFunctionType feelFunctionType = (FEELFunctionType) functionType;
            if (!feelFunctionType.isStaticTyped()) {
                // Bind names to types in function type
                bindNameToTypes(feelFunctionType.getParameters(), arguments);

                // Process function definition
                FunctionDefinition functionDefinition = feelFunctionType.getFunctionDefinition();
                if (functionDefinition != null) {
                    // Bind names to types in function type
                    bindNameToTypes(functionDefinition.getFormalParameters(), arguments);

                    // Set return type
                    functionDefinition.accept(this, context);
                    Type returnType = feelFunctionType.getReturnType();
                    if (returnType == null || returnType == AnyType.ANY) {
                        Type newReturnType = functionDefinition.getReturnType();
                        feelFunctionType.setReturnType(newReturnType);
                    }
                }
            }
        }
    }

    private void bindNameToTypes(List<FormalParameter> parameters, Parameters arguments) {
        if (arguments instanceof NamedParameters) {
            for(FormalParameter p: parameters) {
                Type type = p.getType();
                if (type == null || type == AnyType.ANY) {
                    Type newType = ((NamedParameters) arguments).getParameters().get(p.getName()).getType();
                    p.setType(newType);
                }
            }
        } else if (arguments instanceof PositionalParameters) {
            for(int i=0; i < parameters.size(); i++) {
                FormalParameter p = parameters.get(i);
                Type type = p.getType();
                if (type == null || type == AnyType.ANY) {
                    Type newType = ((PositionalParameters) arguments).getParameters().get(i).getType();
                    p.setType(newType);
                }
            }
        }

    }


    @Override
    public Object visit(NamedParameters element, FEELContext context) {
        element.getParameters().values().forEach(p -> p.accept(this, context));
        return element;
    }

    @Override
    public Object visit(PositionalParameters element, FEELContext context) {
        element.getParameters().forEach(p -> p.accept(this, context));
        return element;
    }

    //
    // Primary expressions
    //
    @Override
    public Object visit(BooleanLiteral element, FEELContext context) {
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(DateTimeLiteral element, FEELContext context) {
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(NullLiteral element, FEELContext context) {
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(NumericLiteral element, FEELContext context) {
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(StringLiteral element, FEELContext context) {
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(ListLiteral element, FEELContext context) {
        element.getExpressionList().forEach(e -> e.accept(this, context));
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(QualifiedName element, FEELContext context) {
        Environment environment = context.getEnvironment();
        List<String> names = element.getNames();
        if (names ==  null || names.isEmpty()) {
            throw new SemanticError(element, "Illegal qualified name.");
        } else if (names.size() == 1) {
            element.deriveType(context);
            return element;
        } else {
            VariableDeclaration source = (VariableDeclaration) environment.lookupVariableDeclaration(names.get(0));
            Type sourceType = source.getType();
            for (int i = 1; i < names.size(); i++) {
                String member = names.get(i);
                sourceType = element.navigationType(sourceType, member);
            }
            element.setType(sourceType);
        }
        return element;
    }

    @Override
    public Object visit(Name element, FEELContext context) {
        element.deriveType(context);
        return element;
    }

    //
    // Type expressions
    //

    @Override
    public Object visit(NamedTypeExpression element, FEELContext context) {
        TDefinitions model = this.dmnModelRepository.getModel(context.getElement());
        com.gs.dmn.transformation.basic.QualifiedName typeRef = com.gs.dmn.transformation.basic.QualifiedName.toQualifiedName(model, element.getQualifiedName());
        element.setType(this.dmnTransformer.toFEELType(null, typeRef));
        return element;
    }

    @Override
    public Object visit(ListTypeExpression element, FEELContext context) {
        element.getElementTypeExpression().accept(this, context);
        element.setType(new ListType(element.getElementTypeExpression().getType()));
        return element;
    }

    @Override
    public Object visit(ContextTypeExpression element, FEELContext context) {
        ContextType contextType = new ContextType();
        for (Pair<String, TypeExpression> member: element.getMembers()) {
            member.getRight().accept(this, context);
            contextType.addMember(member.getLeft(), new ArrayList<>(), member.getRight().getType());
        }
        element.setType(contextType);
        return element;
    }

    @Override
    public Object visit(FunctionTypeExpression element, FEELContext context) {
        element.getParameters().forEach(e -> e.accept(this, context));
        element.getReturnType().accept(this, context);

        List<FormalParameter> parameters = element.getParameters().stream().map(e -> new FormalParameter(null, e.getType())).collect(Collectors.toList());
        Type returnType = element.getReturnType().getType();
        FunctionType functionType = new FEELFunctionType(parameters, returnType, false);
        element.setType(functionType);
        return element;
    }

    private FEELContext visitIterators(final Expression element, FEELContext context, List<Iterator> iterators) {
        FEELSemanticVisitor visitor = this;
        Environment environment = context.getEnvironment();
        Environment qEnvironment = this.environmentFactory.makeEnvironment(environment);
        FEELContext qParams = FEELContext.makeContext(context.getElement(), qEnvironment);
        iterators.forEach(it -> {
            it.accept(visitor, qParams);
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
            qEnvironment.addDeclaration(this.environmentFactory.makeVariableDeclaration(itName, itType));
        });
        return qParams;
    }
}
