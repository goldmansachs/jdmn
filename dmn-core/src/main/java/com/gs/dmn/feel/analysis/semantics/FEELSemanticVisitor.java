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

import com.gs.dmn.error.LogAndThrowErrorHandler;
import com.gs.dmn.feel.analysis.semantics.environment.Declaration;
import com.gs.dmn.feel.analysis.semantics.environment.VariableDeclaration;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.AbstractAnalysisVisitor;
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
import com.gs.dmn.runtime.DMNContext;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import org.omg.spec.dmn._20191111.model.TDefinitions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.gs.dmn.feel.analysis.syntax.ast.expression.textual.ForExpression.PARTIAL_PARAMETER_NAME;

public class FEELSemanticVisitor extends AbstractAnalysisVisitor {
    public FEELSemanticVisitor(BasicDMNToNativeTransformer dmnTransformer) {
        super(dmnTransformer, new LogAndThrowErrorHandler(LOGGER));
    }

    //
    // Tests
    //
    @Override
    public Object visit(PositiveUnaryTests element, DMNContext context) {
        element.getPositiveUnaryTests().forEach(ut -> ut.accept(this, context));
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(NegatedPositiveUnaryTests element, DMNContext context) {
        element.getPositiveUnaryTests().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(Any element, DMNContext context) {
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(NullTest element, DMNContext context) {
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(ExpressionTest element, DMNContext context) {
        element.getExpression().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(OperatorRange element, DMNContext context) {
        element.getEndpoint().accept(this, context);
        element.getEndpointsRange().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(EndpointsRange element, DMNContext context) {
        if (element.getStart() != null) {
            element.getStart().accept(this, context);
        }
        if (element.getEnd() != null) {
            element.getEnd().accept(this, context);
        }
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(ListTest element, DMNContext context) {
        element.getListLiteral().accept(this, context);
        element.deriveType(context);
        return element;
    }

    //
    // Textual expressions
    //
    @Override
    public Object visit(FunctionDefinition element, DMNContext context) {
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
        DMNContext bodyContext = this.dmnTransformer.makeFunctionContext(element, context);

        // Analyze body
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
    public Object visit(FormalParameter element, DMNContext context) {
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
    public Object visit(Context element, DMNContext context) {
        DMNContext localContext = this.dmnTransformer.makeLocalContext(context);
        element.getEntries().forEach(ce -> ce.accept(this, localContext));
        element.deriveType(localContext);
        return element;
    }

    @Override
    public Object visit(ContextEntry element, DMNContext context) {
        ContextEntryKey key = (ContextEntryKey) element.getKey().accept(this, context);
        Expression expression = (Expression) element.getExpression().accept(this, context);
        context.addDeclaration(this.environmentFactory.makeVariableDeclaration(key.getKey(), expression.getType()));
        return element;
    }

    @Override
    public Object visit(ContextEntryKey element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(ForExpression element, DMNContext context) {
        List<Iterator> iterators = element.getIterators();
        DMNContext qParamsContext = visitIterators(element, context, iterators);
        qParamsContext.addDeclaration(this.environmentFactory.makeVariableDeclaration(PARTIAL_PARAMETER_NAME, new ListType(NullType.NULL)));
        element.getBody().accept(this, qParamsContext);
        element.deriveType(qParamsContext);
        qParamsContext.updateVariableDeclaration(PARTIAL_PARAMETER_NAME, element.getType());
        element.getBody().accept(new UpdatePartialVisitor(element.getType(), this.errorHandler), qParamsContext);
        return element;
    }

    @Override
    public Object visit(Iterator element, DMNContext context) {
        element.getDomain().accept(this, context);
        return element;
    }

    @Override
    public Object visit(ExpressionIteratorDomain element, DMNContext context) {
        element.getExpression().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(RangeIteratorDomain element, DMNContext context) {
        element.getStart().accept(this, context);
        element.getEnd().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(IfExpression element, DMNContext context) {
        element.getCondition().accept(this, context);
        element.getThenExpression().accept(this, context);
        element.getElseExpression().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(QuantifiedExpression element, DMNContext context) {
        List<Iterator> iterators = element.getIterators();
        DMNContext qParamsContext = visitIterators(element, context, iterators);
        element.getBody().accept(this, qParamsContext);
        element.deriveType(qParamsContext);
        return element;
    }

    @Override
    public Object visit(FilterExpression element, DMNContext context) {
        // analyse source
        Expression source = element.getSource();
        source.accept(this, context);
        // transform filter (add missing 'item' in front of members)
        Expression filter = transformFilter(source, element.getFilter(), FilterExpression.FILTER_PARAMETER_NAME, context);
        element.setFilter(filter);
        // analyse filter
        DMNContext filterContext = this.dmnTransformer.makeFilterContext(element, FilterExpression.FILTER_PARAMETER_NAME, context);
        element.getFilter().accept(this, filterContext);
        // derive type
        element.deriveType(filterContext);
        return element;
    }

    private Expression transformFilter(Expression source, Expression filter, String filterVariableName, DMNContext context) {
        Type elementType = source.getType();
        if (elementType instanceof ListType) {
            elementType = ((ListType) elementType).getElementType();
        }
        return (Expression)filter.accept(new AddItemFilterVisitor(filterVariableName, elementType, this.errorHandler), context);
    }

    @Override
    public Object visit(InstanceOfExpression element, DMNContext context) {
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    //
    // Expressions
    //
    @Override
    public Object visit(ExpressionList element, DMNContext context) {
        element.getExpressionList().forEach(e -> e.accept(this, context));
        element.deriveType(context);
        return element;
    }

    //
    // Logic expressions
    //
    @Override
    public Object visit(Conjunction element, DMNContext context) {
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(Disjunction element, DMNContext context) {
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(LogicNegation element, DMNContext context) {
        element.getLeftOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    //
    // Comparison expressions
    //
    @Override
    public Object visit(Relational element, DMNContext context) {
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(BetweenExpression element, DMNContext context) {
        element.getValue().accept(this, context);
        element.getLeftEndpoint().accept(this, context);
        element.getRightEndpoint().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(InExpression element, DMNContext parentContext) {
        Expression value = element.getValue();
        value.accept(this, parentContext);

        // Visit tests with value type injected in scope
        DMNContext testContext = this.dmnTransformer.makeUnaryTestContext(value, parentContext);
        element.getTests().forEach(t -> t.accept(this, testContext));

        element.deriveType(parentContext);
        return element;
    }

    //
    // Arithmetic expressions
    //
    @Override
    public Object visit(Addition element, DMNContext context) {
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(Multiplication element, DMNContext context) {
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(Exponentiation element, DMNContext context) {
        element.getLeftOperand().accept(this, context);
        element.getRightOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(ArithmeticNegation element, DMNContext context) {
        element.getLeftOperand().accept(this, context);
        element.deriveType(context);
        return element;
    }

    //
    // Postfix expressions
    //
    @Override
    public Object visit(PathExpression element, DMNContext context) {
        element.getSource().accept(this, context);
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(FunctionInvocation element, DMNContext context) {
        Expression function = element.getFunction();
        Parameters parameters = element.getParameters();
        if (function instanceof Name && "sort".equals(((Name) function).getName())) {
            visitSortParameters(element, context, parameters);
        } else {
            parameters.accept(this, context);
        }
        function.accept(this, context);

        inferMissingTypesInFEELFunction(element.getFunction(), element.getParameters(), context);
        element.deriveType(context);

        return element;
    }

    private void visitSortParameters(FunctionInvocation element, DMNContext context, Parameters parameters) {
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
            if (listType instanceof ListType) {
                Type elementType = ((ListType) listType).getElementType();
                if (lambdaExpression instanceof FunctionDefinition) {
                    List<FormalParameter> formalParameters = ((FunctionDefinition) lambdaExpression).getFormalParameters();
                    formalParameters.forEach(p -> p.setType(elementType));
                    success = true;
                } else if (lambdaExpression instanceof Name) {
                    Declaration declaration = context.lookupVariableDeclaration(((Name) lambdaExpression).getName());
                    Type type = declaration.getType();
                    if (type instanceof FunctionType && !(type instanceof BuiltinFunctionType)) {
                        List<FormalParameter> formalParameters = ((FunctionType) type).getParameters();
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

    private void inferMissingTypesInFEELFunction(Expression function, Parameters arguments, DMNContext context) {
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
                    if (Type.isNullOrAny(returnType)) {
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
                if (Type.isNullOrAny(type)) {
                    Type newType = ((NamedParameters) arguments).getParameters().get(p.getName()).getType();
                    p.setType(newType);
                }
            }
        } else if (arguments instanceof PositionalParameters) {
            for(int i=0; i < parameters.size(); i++) {
                FormalParameter p = parameters.get(i);
                Type type = p.getType();
                if (Type.isNullOrAny(type)) {
                    Type newType = ((PositionalParameters) arguments).getParameters().get(i).getType();
                    p.setType(newType);
                }
            }
        }
    }

    @Override
    public Object visit(NamedParameters element, DMNContext context) {
        element.getParameters().values().forEach(p -> p.accept(this, context));
        return element;
    }

    @Override
    public Object visit(PositionalParameters element, DMNContext context) {
        element.getParameters().forEach(p -> p.accept(this, context));
        return element;
    }

    //
    // Primary expressions
    //
    @Override
    public Object visit(BooleanLiteral element, DMNContext context) {
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(DateTimeLiteral element, DMNContext context) {
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(NullLiteral element, DMNContext context) {
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(NumericLiteral element, DMNContext context) {
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(StringLiteral element, DMNContext context) {
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(ListLiteral element, DMNContext context) {
        element.getExpressionList().forEach(e -> e.accept(this, context));
        element.deriveType(context);
        return element;
    }

    @Override
    public Object visit(QualifiedName element, DMNContext context) {
        List<String> names = element.getNames();
        if (names ==  null || names.isEmpty()) {
            throw new SemanticError(element, "Illegal qualified name.");
        } else if (names.size() == 1) {
            element.deriveType(context);
            return element;
        } else {
            VariableDeclaration source = (VariableDeclaration) context.lookupVariableDeclaration(names.get(0));
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
    public Object visit(Name element, DMNContext context) {
        element.deriveType(context);
        return element;
    }

    //
    // Type expressions
    //

    @Override
    public Object visit(NamedTypeExpression element, DMNContext context) {
        TDefinitions model = this.dmnModelRepository.getModel(context.getElement());
        com.gs.dmn.transformation.basic.QualifiedName typeRef = com.gs.dmn.transformation.basic.QualifiedName.toQualifiedName(model, element.getQualifiedName());
        element.setType(this.dmnTransformer.toFEELType(null, typeRef));
        return element;
    }

    @Override
    public Object visit(ListTypeExpression element, DMNContext context) {
        element.getElementTypeExpression().accept(this, context);
        element.setType(new ListType(element.getElementTypeExpression().getType()));
        return element;
    }

    @Override
    public Object visit(ContextTypeExpression element, DMNContext context) {
        ContextType contextType = new ContextType();
        for (Pair<String, TypeExpression> member: element.getMembers()) {
            member.getRight().accept(this, context);
            contextType.addMember(member.getLeft(), new ArrayList<>(), member.getRight().getType());
        }
        element.setType(contextType);
        return element;
    }

    @Override
    public Object visit(FunctionTypeExpression element, DMNContext context) {
        element.getParameters().forEach(e -> e.accept(this, context));
        element.getReturnType().accept(this, context);

        List<FormalParameter> parameters = element.getParameters().stream().map(e -> new FormalParameter(null, e.getType())).collect(Collectors.toList());
        Type returnType = element.getReturnType().getType();
        FunctionType functionType = new FEELFunctionType(parameters, returnType, false);
        element.setType(functionType);
        return element;
    }

    private DMNContext visitIterators(final Expression element, DMNContext context, List<Iterator> iterators) {
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
}
