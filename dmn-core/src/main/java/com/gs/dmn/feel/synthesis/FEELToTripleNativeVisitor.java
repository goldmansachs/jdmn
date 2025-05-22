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
import com.gs.dmn.el.synthesis.triple.Triple;
import com.gs.dmn.el.synthesis.triple.Triples;
import com.gs.dmn.feel.OperatorDecisionTable;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.Element;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Iterator;
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
import com.gs.dmn.transformation.basic.ImportContextType;

import java.util.*;
import java.util.stream.Collectors;

import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.transformation.native_.NativeFactory.MAP_ITERATOR;

public class FEELToTripleNativeVisitor extends AbstractFEELToJavaVisitor<Object> {
    private static final int INITIAL_VALUE = -1;
    private int filterCount = INITIAL_VALUE;

    private final Triples triples;

    public FEELToTripleNativeVisitor(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        super(dmnTransformer);
        this.triples = new Triples();
    }

    public void init() {
        this.filterCount = INITIAL_VALUE;
        this.triples.init();
    }

    public Triples getTriples() {
        return this.triples;
    }

    //
    // Tests
    //
    @Override
    public Triple visit(PositiveUnaryTests<Type> element, DMNContext context) {
        List<PositiveUnaryTest<Type>> simplePositiveUnaryTests = element.getPositiveUnaryTests();
        List<Triple> operands = simplePositiveUnaryTests.stream().map(t -> (Triple) t.accept(this, context)).collect(Collectors.toList());
        return toBooleanOr(operands);
    }

    @Override
    public Triple visit(NegatedPositiveUnaryTests<Type> element, DMNContext context) {
        PositiveUnaryTests<Type> simplePositiveUnaryTests = element.getPositiveUnaryTests();
        Triple condition = (Triple) simplePositiveUnaryTests.accept(this, context);
        condition = this.triples.makeBuiltinFunctionInvocation("booleanNot", condition);
        return condition;
    }

    @Override
    public Triple visit(Any<Type> element, DMNContext context) {
        return this.triples.trueConstant();
    }

    @Override
    public Triple visit(ExpressionTest<Type> element, DMNContext context) {
        Expression<Type> expression = element.getExpression();
        return (Triple) expression.accept(this, context);
    }

    @Override
    public Triple visit(OperatorRange<Type> element, DMNContext context) {
        String operator = normalizeOperator(element.getOperator());
        Expression<Type> endpoint = element.getEndpoint();
        if (context.isExpressionContext()) {
            // Evaluate as range
            Triple endpointValue = (Triple) endpoint.accept(this, context);
            Triple operatorValue = this.triples.text(operator);
            List<Triple> arguments;
            if (isValidRangeOperator(operator)) {
                arguments = Arrays.asList(operatorValue, endpointValue);
            } else {
                throw new DMNRuntimeException(String.format("Unknown operator '%s'", operator));
            }
            String clsName = this.dmnTransformer.rangeClassName();
            return this.triples.constructor(clsName, arguments, true);
        } else {
            // Evaluate as test according to 7.3.2 UnaryTests Metamodel
            Type inputExpressionType = context.getInputExpressionType();
            Type endpointType = endpoint.getType();
            if (Type.sameSemanticDomain(endpointType, inputExpressionType)) {
                // input and endpoint are comparable
                return makeRangeCondition(operator, (Expression) context.getInputExpression(), endpoint, context);
            } else if (endpointType instanceof ListType) {
                Type endpointElementType = ((ListType) endpointType).getElementType();
                if (Type.sameSemanticDomain(endpointElementType, inputExpressionType)) {
                    // input and list elements are comparable
                    Triple javaList = (Triple) endpoint.accept(this, context);
                    return this.triples.makeBuiltinFunctionInvocation("listContains", javaList, inputExpressionToJava(context));
                }
            } else if (endpointType instanceof RangeType) {
                Type endpointElementType = ((RangeType) endpointType).getRangeType();
                if (Type.sameSemanticDomain(endpointElementType, inputExpressionType)) {
                    // input and list elements are comparable
                    Triple javaList = (Triple) endpoint.accept(this, context);
                    return this.triples.makeBuiltinFunctionInvocation("rangeContains", javaList, inputExpressionToJava(context));
                }
            }

            // Cannot compare
            handleError(context, element, String.format("Cannot compare '%s', '%s'", inputExpressionType, endpointType));
            return null;
        }
    }

    @Override
    public Triple visit(EndpointsRange<Type> element, DMNContext context) {
        Expression<Type> startEndpoint = element.getStart();
        Expression<Type> endEndpoint = element.getEnd();
        if (context.isExpressionContext()) {
            // Evaluate as range
            Triple startIncluded = this.triples.booleanValueConstant("" + !element.isOpenStart());
            Triple endIncluded = this.triples.booleanValueConstant("" + !element.isOpenEnd());
            Triple start = startEndpoint == null ? this.triples.nullLiteral() : (Triple) startEndpoint.accept(this, context);
            Triple end = endEndpoint == null ? this.triples.nullLiteral() : (Triple) endEndpoint.accept(this, context);

            String clsName = this.dmnTransformer.rangeClassName();
            return this.triples.constructor(clsName, Arrays.asList(startIncluded, start, endIncluded, end), true);
        } else {
            // Evaluate as test
            Triple leftCondition = makeRangeCondition(element.isOpenStart() ? ">" : ">=", (Expression) context.getInputExpression(), startEndpoint, context);
            Triple rightCondition = makeRangeCondition(element.isOpenEnd() ? "<" : "<=", (Expression) context.getInputExpression(), endEndpoint, context);
            return this.triples.makeBuiltinFunctionInvocation("booleanAnd", leftCondition, rightCondition);
        }
    }

    @Override
    public Triple visit(ListTest<Type> element, DMNContext context) {
        Type inputExpressionType = context.getInputExpressionType();

        ListLiteral<Type> optimizedListLiteral = element.getOptimizedListLiteral();
        if (optimizedListLiteral != null) {
            // Optimisation
            Type optimizedListType = optimizedListLiteral.getType();
            Type optimizedListElementType = ((ListType) optimizedListType).getElementType();
            if (com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(inputExpressionType, optimizedListType)) {
                // both are compatible lists
                Triple javaList = (Triple) optimizedListLiteral.accept(this, context);
                return this.triples.makeBuiltinFunctionInvocation("listEqual", inputExpressionToJava(context), javaList);
            } else if (com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(inputExpressionType, optimizedListElementType)) {
                // input conforms to element in the list
                Triple javaList = (Triple) optimizedListLiteral.accept(this, context);
                return this.triples.makeBuiltinFunctionInvocation("listContains", javaList, inputExpressionToJava(context));
            } else {
                handleError(context, element, String.format("Cannot compare '%s', '%s'", inputExpressionType, optimizedListType));
                return null;
            }
        } else {
            // test is list of ranges compatible with input
            ListLiteral<Type> listLiteral = element.getListLiteral();
            Triple javaList = (Triple) listLiteral.accept(this, context);
            return this.triples.makeBuiltinFunctionInvocation("listContains", javaList, this.triples.booleanValueLiteral("true"));
        }
    }

    //
    // Textual expressions
    //
    @Override
    public Triple visit(FunctionDefinition<Type> element, DMNContext context) {
        if (element.isStaticTyped()) {
            Triple body = (Triple) element.getBody().accept(this, context);
            return this.triples.makeFunctionDefinition((TDRGElement) context.getElement(), element, false, body);
        } else {
            handleError("Dynamic typing for FEEL functions not supported yet");
            return null;
        }
    }

    @Override
    public Triple visit(FormalParameter<Type> element, DMNContext context) {
        throw new DMNRuntimeException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    @Override
    public Triple visit(Context<Type> element, DMNContext context) {
        List<Triple> addMethods = element.getEntries().stream().map(e -> (Triple) e.accept(this, context)).collect(Collectors.toList());
        return this.triples.fluentConstructor(this.dmnTransformer.contextClassName(), addMethods);
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
    public Triple visit(ContextEntry<Type> element, DMNContext context) {
        String key = (String) element.getKey().accept(this, context);
        Triple value = (Triple) element.getExpression().accept(this, context);
        return this.triples.makeContextEntry(key, value);
    }

    @Override
    public String visit(ContextEntryKey<Type> element, DMNContext context) {
        return String.format("\"%s\"", element.getKey());
    }

    @Override
    public Triple visit(ForExpression<Type> element, DMNContext context) {
        DMNContext forContext = this.dmnTransformer.makeForContext(context);

        // Translate iterators
        List<Iterator<Type>> iterators = element.getIterators();
        List<Pair<Triple, String>> domainIterators = new ArrayList<>();
        for (Iterator<Type> it : iterators) {
            IteratorDomain<Type> expressionDomain = it.getDomain();
            Triple domain = (Triple) expressionDomain.accept(this, forContext);
            domainIterators.add(new Pair<>(domain, it.getName()));
        }

        // Translate body
        Triple body = (Triple) element.getBody().accept(this, forContext);

        return this.triples.makeForExpression(domainIterators, body);
    }

    @Override
    public Triple visit(Iterator<Type> element, DMNContext context) {
        throw new DMNRuntimeException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    @Override
    public Triple visit(ExpressionIteratorDomain<Type> element, DMNContext context) {
        Expression<Type> expressionDomain = element.getExpression();
        Triple domain;
        if (element.getType() instanceof ListType) {
            domain = (Triple) expressionDomain.accept(this, context);
        } else if (element.getType() instanceof RangeType && expressionDomain instanceof EndpointsRange) {
            Triple rangeTriple = (Triple) expressionDomain.accept(this, context);
            domain = this.triples.makeBuiltinFunctionInvocation("rangeToList", rangeTriple);
        } else {
            throw new DMNRuntimeException(String.format("FEEL '%s' is not supported yet with domain '%s'",
                    element.getClass().getSimpleName(), expressionDomain.getClass().getSimpleName()));
        }
        return domain;
    }

    @Override
    // TODO optimize generated code
    public Triple visit(RangeIteratorDomain<Type> element, DMNContext context) {
        Triple start = (Triple) element.getStart().accept(this, context);
        Triple end = (Triple) element.getEnd().accept(this, context);
        return this.triples.makeBuiltinFunctionInvocation("rangeToList", start, end);
    }

    @Override
    public Triple visit(IfExpression<Type> element, DMNContext context) {
        Triple condition = (Triple) element.getCondition().accept(this, context);
        Triple thenExp = (Triple) element.getThenExpression().accept(this, context);
        Triple elseExp = (Triple) element.getElseExpression().accept(this, context);
        return this.triples.makeIfExpression(condition, thenExp, elseExp);
    }

    @Override
    public Triple visit(QuantifiedExpression<Type> element, DMNContext context) {
        // Transform into equivalent nested for expressions
        ForExpression<Type> forExpression = element.toForExpression();

        // Translate the for expression
        Triple forList = (Triple) forExpression.accept(this, context);

        // Add boolean predicate
        String predicate = element.getPredicate();
        if ("some".equals(predicate)) {
            return this.triples.makeSomeExpression(forList);
        } else if ("every".equals(predicate)) {
            return this.triples.makeEveryExpression(forList);
        } else {
            throw new DMNRuntimeException("Predicate '" + predicate + "' is not supported yet");
        }
    }

    @Override
    public Triple visit(FilterExpression<Type> element, DMNContext context) {
        // Generate source
        Type sourceType = element.getSource().getType();
        Type filterType = element.getFilter().getType();
        Triple source = (Triple) element.getSource().accept(this, context);

        // Replace 'item' with 'item_xx' to be able to handle multiple filters
        String olderParameterName = FilterExpression.FILTER_PARAMETER_NAME;
        String newParameterName = this.dmnTransformer.lowerCaseFirst(newParameterName(olderParameterName));
        element.accept(new ReplaceItemFilterVisitor<>(olderParameterName, newParameterName, this.errorHandler), context);

        // Generate filter
        DMNContext feelContext = this.dmnTransformer.makeFilterContext(element, newParameterName, context);
        Triple filter = (Triple) element.getFilter().accept(this, feelContext);

        // Convert source to list
        if (!(sourceType instanceof ListType)) {
            source = this.triples.asList(sourceType, Collections.singletonList(source));
        }

        // Filter
        if (filterType == BooleanType.BOOLEAN) {
            return this.triples.makeCollectionLogicFilter(source, newParameterName, filter);
        } else if (filterType == NUMBER) {
            // Compute element type
            Type elementType;
            if (sourceType instanceof ListType) {
                elementType = ((ListType) sourceType).getElementType();
            } else {
                elementType = sourceType;
            }
            String javaElementType = this.dmnTransformer.toNativeType(elementType);

            return this.triples.makeCollectionNumericFilter(javaElementType, source, filter);
        } else {
            throw new DMNRuntimeException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
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
    public Triple visit(InstanceOfExpression<Type> element, DMNContext context) {
        try {
            Triple leftOperand = (Triple) element.getLeftOperand().accept(this, context);
            Type rightOperandType = element.getRightOperand().getType();
            return this.triples.makeInstanceOf(leftOperand, rightOperandType);
        } catch (Exception e) {
            throw new DMNRuntimeException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
        }
    }

    //
    // Expressions
    //
    @Override
    public Triple visit(ExpressionList<Type> element, DMNContext context) {
        throw new DMNRuntimeException("FEEL '" + element.getClass().getSimpleName() + "' is not supported yet");
    }

    //
    // Logic expressions
    //
    @Override
    public Triple visit(Disjunction<Type> element, DMNContext context) {
        Expression<Type> leftOperand = element.getLeftOperand();
        Expression<Type> rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public Triple visit(Conjunction<Type> element, DMNContext context) {
        Expression<Type> leftOperand = element.getLeftOperand();
        Expression<Type> rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public Triple visit(LogicNegation<Type> element, DMNContext context) {
        Expression<Type> leftOperand = element.getLeftOperand();
        Triple leftOpd = (Triple) leftOperand.accept(this, context);
        return this.triples.makeBuiltinFunctionInvocation("booleanNot", leftOpd);
    }

    //
    // Comparison expressions
    //
    @Override
    public Triple visit(Relational<Type> element, DMNContext context) {
        Expression<Type> leftOperand = element.getLeftOperand();
        Expression<Type> rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public Triple visit(BetweenExpression<Type> element, DMNContext context) {
        Triple value = (Triple) element.getValue().accept(this, context);
        Expression<Type> leftEndpoint = element.getLeftEndpoint();
        Expression<Type> rightEndpoint = element.getRightEndpoint();
        Triple leftOpd = (Triple) leftEndpoint.accept(this, context);
        Triple rightOpd = (Triple) rightEndpoint.accept(this, context);
        String feelOperator = "<=";
        NativeOperator javaOperator = OperatorDecisionTable.javaOperator(feelOperator, leftEndpoint.getType(), rightEndpoint.getType());
        Triple c1 = makeCondition(feelOperator, leftOpd, value, javaOperator);
        Triple c2 = makeCondition(feelOperator, value, rightOpd, javaOperator);
        return this.triples.makeBuiltinFunctionInvocation("booleanAnd", c1, c2);
    }

    @Override
    public Triple visit(InExpression<Type> element, DMNContext context) {
        Expression<Type> valueExp = element.getValue();
        List<PositiveUnaryTest<Type>> positiveUnaryTests = element.getTests();

        DMNContext testContext = context.isTestContext() ? context : this.dmnTransformer.makeUnaryTestContext(valueExp, context);
        List<Triple> result = new ArrayList<>();
        for (PositiveUnaryTest<Type> positiveUnaryTest : positiveUnaryTests) {
            Triple condition;
            if (positiveUnaryTest instanceof ExpressionTest) {
                condition =  makeRangeCondition("=", valueExp, ((ExpressionTest<Type>) positiveUnaryTest).getExpression(), context);
            } else {
                condition = (Triple) positiveUnaryTest.accept(this, testContext);
            }
            result.add(condition);
        }
        if (result.size() == 1) {
            return result.get(0);
        } else {
            return this.triples.makeBuiltinFunctionInvocation("booleanOr", result.toArray(new Triple[0]));
        }
    }

    //
    // Arithmetic expressions
    //
    @Override
    public Triple visit(Addition<Type> element, DMNContext context) {
        Expression<Type> leftOperand = element.getLeftOperand();
        Expression<Type> rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public Triple visit(Multiplication<Type> element, DMNContext context) {
        Expression<Type> leftOperand = element.getLeftOperand();
        Expression<Type> rightOperand = element.getRightOperand();
        String feelOperator = element.getOperator();
        return makeCondition(feelOperator, leftOperand, rightOperand, context);
    }

    @Override
    public Triple visit(Exponentiation<Type> element, DMNContext context) {
        Triple leftOpd = (Triple) element.getLeftOperand().accept(this, context);
        Triple rightOpd = (Triple) element.getRightOperand().accept(this, context);
        return this.triples.makeBuiltinFunctionInvocation("numericExponentiation", leftOpd, rightOpd);
    }

    @Override
    public Triple visit(ArithmeticNegation<Type> element, DMNContext context) {
        Expression<Type> leftOperand = element.getLeftOperand();
        Triple leftOpd = (Triple) leftOperand.accept(this, context);
        Type type = element.getType();
        if (type == NUMBER) {
            return this.triples.makeBuiltinFunctionInvocation("numericUnaryMinus", leftOpd);
        } else {
            return this.triples.makeBuiltinFunctionInvocation("durationUnaryMinus", leftOpd);
        }
    }

    //
    // Postfix expressions
    //
    @Override
    public Triple visit(PathExpression<Type> element, DMNContext context) {
        Expression<Type> sourceExpression = element.getSource();
        Type sourceType = sourceExpression.getType();
        Triple source = (Triple) sourceExpression.accept(this, context);
        String member = element.getMember();

        return makeNavigation(element, sourceType, source, member, nativeFriendlyVariableName(member), context);
    }

    @Override
    public Triple visit(FunctionInvocation<Type> element, DMNContext context) {
        // Generate code for actual parameters
        Parameters<Type> parameters = element.getParameters();
        parameters.accept(this, context);

        // Generate code for conversion
        Arguments<Type> arguments = parameters.convertArguments(this::checkBindingArgument);

        // Generate code for function invocation
        Expression<Type> function = element.getFunction();
        FunctionType functionType = (FunctionType) function.getType();
        List<FormalParameter<Type>> formalParameters = functionType.getParameters();
        List<Object> argList = arguments.argumentList(formalParameters);
        Triple javaFunctionCode = (Triple) function.accept(this, context);
        if (functionType instanceof BuiltinFunctionType) {
            List<Triple> operands = visitArgList(argList);
            return this.triples.makeBuiltinFunctionInvocation(javaFunctionCode, operands);
        } else if (functionType instanceof DMNFunctionType) {
            if (!this.dmnModelRepository.isJavaFunction(((DMNFunctionType) functionType).getKind())) {
                addExtraArguments(argList);
            }
            TNamedElement invocable = ((DMNFunctionType) functionType).getDRGElement();
            if (invocable instanceof TInvocable) {
                List<Triple> operands = visitArgList(argList);
                if (function instanceof Name) {
                    if (((Name<Type>) function).getName().equals(invocable.getName())) {
                        String javaQualifiedName = this.dmnTransformer.singletonInvocableInstance((TInvocable) invocable);
                        return this.triples.makeApplyInvocation(javaQualifiedName, operands);
                    } else {
                        return this.triples.makeApplyInvocation(javaFunctionCode, operands);
                    }
                } else {
                    return this.triples.makeApplyInvocation(javaFunctionCode, operands);
                }
            } else {
                List<Triple> operands = visitArgList(argList);
                return this.triples.makeApplyInvocation(javaFunctionCode, operands);
            }
        } else if (functionType instanceof FEELFunctionType) {
            if (!((FEELFunctionType) functionType).isExternal()) {
                addExtraArguments(argList);
            }
            List<Triple> operands = visitArgList(argList);
            return this.triples.makeApplyInvocation(javaFunctionCode, operands);
        } else {
            handleError(String.format("Not supported function type '%s' in '%s'", functionType, context.getElementName()));
            return null;
        }
    }

    private List<Triple> visitArgList(List<Object> argList) {
        List<Triple> operands = new ArrayList<>();
        for (Object arg : argList) {
            if (arg instanceof Triple) {
                operands.add((Triple) arg);
            } else {
                handleError("Illegal arg, should be Operand");
                return null;
            }
        }
        return operands;
    }

    private void addExtraArguments(List<Object> argList) {
        argList.add(this.triples.name(dmnTransformer.executionContextVariableName()));
    }

    protected Triple checkBindingArgument(Object param, Conversion<Type> conversion) {
        // TODO check constraints
        if (param instanceof Triple) {
            return this.triples.makeConvertArgument((Triple) param, conversion);
        } else {
            handleError(String.format("Expected operand, found '%s'", param));
            return null;
        }
    }

    //
    // Primary expressions
    //
    @Override
    public Triple visit(NumericLiteral<Type> element, DMNContext context) {
        return this.triples.numericLiteral(element.getLexeme());
    }

    @Override
    public Triple visit(StringLiteral<Type> element, DMNContext context) {
        String lexeme = element.getLexeme();
        return this.triples.stringLiteral(lexeme);
    }

    @Override
    public Triple visit(BooleanLiteral<Type> element, DMNContext context) {
        String value = element.getLexeme();
        return this.triples.booleanLiteral(value);
    }

    @Override
    public Triple visit(DateTimeLiteral<Type> element, DMNContext context) {
        return this.triples.dateTimeLiteral(element);
    }

    @Override
    public Triple visit(NullLiteral<Type> element, DMNContext context) {
        return this.triples.nullLiteral();
    }

    @Override
    public Triple visit(ListLiteral<Type> element, DMNContext context) {
        List<Expression<Type>> expressionList = element.getExpressionList();
        List<Triple> elements = expressionList.stream().map(e -> (Triple) e.accept(this, context)).collect(Collectors.toList());
        Type elementType = ((ListType) element.getType()).getElementType();
        return this.triples.asList(elementType, elements);
    }

    @Override
    public Triple visit(QualifiedName<Type> element, DMNContext context) {
        if (element.getNames().size() == 1) {
            return nameToJava(element.getNames().get(0), context);
        } else {
            return handleNotSupportedElement(element);
        }
    }

    @Override
    public Triple visit(Name<Type> element, DMNContext context) {
        String name = element.getName();
        return nameToJava(name, context);
    }

    //
    // Type expressions
    //
    @Override
    public Triple visit(NamedTypeExpression<Type> element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Triple visit(ContextTypeExpression<Type> element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Triple visit(RangeTypeExpression<Type> element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Triple visit(FunctionTypeExpression<Type> element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Triple visit(ListTypeExpression<Type> element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    protected Triple nameToJava(String name, DMNContext context) {
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
                            return this.triples.makeLambdaAccessor(String.format("%s.instance().lambda", javaQualifiedName));
                        }
                    }
                }
            }
            // Other names
            String javaName = nativeFriendlyVariableName(name);
            return nameToTriple(name, javaName);
        }
    }

    private Triple inputExpressionToJava(DMNContext context) {
        if (context.isExpressionContext()) {
            handleError(String.format("Missing inputExpression in context of element '%s'", context.getElementName()));
            return null;
        } else {
            // Evaluate as test
            Expression<Type> inputExpression = (Expression) context.getInputExpression();
            return (Triple) inputExpression.accept(this, context);
        }
    }

    private Triple toBooleanOr(List<Triple> operands) {
        if (operands.size() == 1) {
            return operands.get(0);
        } else {
            return this.triples.makeBuiltinFunctionInvocation("booleanOr", operands.toArray(new Triple[0]));
        }
    }

    private Triple makeRangeCondition(String feelOperator, Expression<Type> leftOperand, Expression<Type> rightOperand, DMNContext context) {
        Triple leftOpd = (Triple) leftOperand.accept(this, context);
        Triple rightOpd = (Triple) rightOperand.accept(this, context);
        Triple condition;
        NativeOperator nativeOperator = rangeOperator(feelOperator, leftOperand, rightOperand);
        if (nativeOperator.getNotation() == NativeOperator.Notation.INFIX) {
            condition = infixExpression(nativeOperator.getName(), leftOpd, rightOpd);
        } else {
            condition = functionalExpression(nativeOperator.getName(), leftOpd, rightOpd);
        }
        return condition;
    }

    protected Triple handleNotSupportedElement(Element<Type> element) {
        throw new DMNRuntimeException("FEEL '" + (element == null ? null : element.getClass().getSimpleName()) + "' is not supported in this context");
    }

    protected Triple makeNavigation(Expression<Type> element, Type sourceType, Triple source, String memberName, String memberVariableName, DMNContext context) {
        if (sourceType instanceof ImportContextType) {
            ImportContextType importContextType = (ImportContextType) sourceType;
            DRGElementReference<? extends TDRGElement> memberReference = importContextType.getMemberReference(memberName);
            if (memberReference == null) {
                handleError(context, element, String.format("Cannot find reference for '%s'", memberName));
                return null;
            }
            TDRGElement drgElement = memberReference.getElement();
            if (drgElement instanceof TInvocable) {
                return this.triples.singletonInvocableInstance(this.dmnTransformer.singletonInvocableInstance((TBusinessKnowledgeModel) drgElement));
            } else {
                String javaName = this.dmnTransformer.drgReferenceQualifiedName(memberReference);
                return nameToTriple(memberReference.getElementName(), javaName);
            }
        } else if (sourceType instanceof ItemDefinitionType) {
            Type memberType = ((ItemDefinitionType) sourceType).getMemberType(memberName);
            String javaType = this.dmnTransformer.toNativeType(memberType);
            return this.triples.makeItemDefinitionAccessor(javaType, source, memberName);
        } else if (sourceType instanceof ContextType) {
            Type memberType = ((ContextType) sourceType).getMemberType(memberName);
            String javaType = this.dmnTransformer.toNativeType(memberType);
            return this.triples.makeContextAccessor(javaType, source, memberName);
        } else if (sourceType instanceof ListType) {
            Triple filter = makeNavigation(element, ((ListType) sourceType).getElementType(), triples.name(MAP_ITERATOR), memberName, memberVariableName, context);
            return this.triples.makeCollectionMap(source, filter);
        } else if (sourceType instanceof DateType) {
            return this.triples.makeBuiltinFunctionInvocation(propertyFunctionName(memberName), source);
        } else if (sourceType instanceof TimeType) {
            return this.triples.makeBuiltinFunctionInvocation(propertyFunctionName(memberName), source);
        } else if (sourceType instanceof DateTimeType) {
            return this.triples.makeBuiltinFunctionInvocation(propertyFunctionName(memberName), source);
        } else if (sourceType instanceof DurationType) {
            return this.triples.makeBuiltinFunctionInvocation(propertyFunctionName(memberName), source);
        } else if (sourceType instanceof RangeType) {
            return this.triples.makeRangeAccessor(source, rangeGetter(memberName));
        } else if (sourceType instanceof AnyType) {
            // source is Context
            return this.triples.makeContextSelectExpression(this.dmnTransformer.contextClassName(), source, memberName);
        } else {
            handleError(context, element, String.format("Cannot generate navigation path '%s'", element));
            return null;
        }
    }

    protected Triple makeCondition(String feelOperator, Expression<Type> leftOperand, Expression<Type> rightOperand, DMNContext context) {
        Triple leftOpd = (Triple) leftOperand.accept(this, context);
        Triple rightOpd = (Triple) rightOperand.accept(this, context);
        NativeOperator javaOperator = OperatorDecisionTable.javaOperator(feelOperator, leftOperand.getType(), rightOperand.getType());
        return makeCondition(feelOperator, leftOpd, rightOpd, javaOperator);
    }

    protected Triple makeCondition(String feelOperator, Triple leftOpd, Triple rightOpd, NativeOperator javaOperator) {
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

    protected NativeOperator rangeOperator(String feelOperatorName, Expression<Type> leftOperand, Expression<Type> rightOperand) {
        NativeOperator nativeOperator = OperatorDecisionTable.javaOperator(feelOperatorName, rightOperand.getType(), rightOperand.getType());
        if (nativeOperator != null) {
            return nativeOperator;
        } else {
            handleError(makeOperatorErrorMessage(feelOperatorName, leftOperand, rightOperand));
            return null;
        }
    }

    protected Triple functionalExpression(String nativeOperator, Triple leftOpd, Triple rightOpd) {
        return triples.makeBuiltinFunctionInvocation(nativeOperator, leftOpd, rightOpd);
    }

    protected Triple infixExpression(String nativeOperator, Triple leftOpd, Triple rightOpd) {
        return triples.makeInfixExpression(nativeOperator, leftOpd, rightOpd);
    }

    protected String rangeGetter(String memberName) {
        memberName = NameUtils.removeSingleQuotes(memberName);
        if ("start included".equalsIgnoreCase(memberName)) {
            return "isStartIncluded()";
        } else if ("end included".equalsIgnoreCase(memberName)) {
            return "isEndIncluded()";
        } else {
            return this.dmnTransformer.getter(memberName);
        }
    }

    private Triple nameToTriple(String name, String javaName) {
        if (this.dmnTransformer.isLazyEvaluated(name)) {
            return this.triples.lazyEvaluation(name, javaName);
        } else {
            return this.triples.name(javaName);
        }
    }

    private static String makeOperatorErrorMessage(String feelOperator, Object leftOpd, Object rightOpd) {
        return String.format("Operator '%s' cannot be applied to '%s' and '%s'", feelOperator, leftOpd, rightOpd);
    }
}