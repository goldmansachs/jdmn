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
package com.gs.dmn.el.synthesis.triple;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.Conversion;
import com.gs.dmn.feel.lib.StringEscapeUtil;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.native_.NativeFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TripleSerializerToString implements Visitor<Triples, String> {
    private final BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer;
    private final NativeFactory nativeFactory;

    public TripleSerializerToString(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        this.nativeFactory = dmnTransformer.getNativeFactory();
        this.dmnTransformer = dmnTransformer;
    }

    //
    // Literals
    //
    @Override
    public String visit(NumberLiteral triple, Triples context) {
        return this.nativeFactory.numericLiteral(triple.getLexeme());
    }

    @Override
    public String visit(StringLiteral triple, Triples context) {
        String lexeme = triple.getLexeme();
        String value = StringEscapeUtil.unescapeFEEL(lexeme);
        value = StringEscapeUtil.escapeFEEL(value);
        value = String.format("\"%s\"", value);
        return value;
    }

    @Override
    public String visit(BooleanLiteral triple, Triples context) {
        String lexeme = triple.getLexeme();
        return triple.isValue() ? nativeFactory.booleanValueLiteral(lexeme) : nativeFactory.booleanLiteral(lexeme);
    }

    @Override
    public String visit(DateTimeLiteral triple, Triples context) {
        return this.nativeFactory.dateTimeLiteral(triple.getElement());
    }

    @Override
    public String visit(NullLiteral triple, Triples context) {
        return this.nativeFactory.nullLiteral();
    }

    @Override
    public String visit(NameTriple triple, Triples context) {
        return triple.getName();
    }

    @Override
    public String visit(ContextEntryTriple triple, Triples context) {
        String key = triple.getKey();
        String value = triple.getValue().accept(this, context);
        return String.format(".add(%s, %s)", key, value);
    }

    //
    // Path
    //
    @Override
    public String visit(ItemDefinitionAccessor triple, Triples context) {
        String sourceText = triple.getSource().accept(this, context);
        return this.nativeFactory.makeItemDefinitionAccessor(triple.getJavaType(), sourceText, triple.getMemberName());
    }

    @Override
    public String visit(ContextAccessor triple, Triples context) {
        String sourceText = triple.getSource().accept(this, context);
        return this.nativeFactory.makeContextAccessor(triple.getJavaType(), sourceText, triple.getMemberName());
    }

    @Override
    public String visit(ContextSelect triple, Triples context) {
        String sourceText = triple.getSource().accept(this, context);
        return this.nativeFactory.makeContextSelectExpression(triple.getContextClassName(), sourceText, triple.getMemberName());
    }

    @Override
    public String visit(RangeAccessor triple, Triples context) {
        String sourceText = triple.getSource().accept(this, context);
        return String.format("%s.%s", sourceText, triple.getRangeGetter());
    }

    @Override
    public String visit(CollectionMap triple, Triples context) {
        String sourceText = triple.getSource().accept(this, context);
        String filterText = triple.getFilter().accept(this, context);
        return this.nativeFactory.makeCollectionMap(sourceText, filterText);
    }

    //
    // Filter
    //
    @Override
    public String visit(CollectionLogicFilter triple, Triples context) {
        String sourceText = triple.getSource().accept(this, context);
        String filterText = triple.getFilter().accept(this, context);
        return this.nativeFactory.makeCollectionLogicFilter(sourceText, triple.getNewParameterName(), filterText);
    }

    @Override
    public String visit(CollectionNumericFilter triple, Triples context) {
        String sourceText = triple.getSource().accept(this, context);
        String filterText = triple.getFilter().accept(this, context);
        return this.nativeFactory.makeCollectionNumericFilter(triple.getJavaElementType(), sourceText, filterText);
    }

    //
    // Function
    //
    @Override
    public String visit(FunctionDefinitionTriple triple, Triples context) {
        String body = triple.getBody().accept(this, context);
        return this.dmnTransformer.functionDefinitionToNative(triple.getElement(), triple.getFunctionDefinition(), triple.isConvertTypeToContext(), body);
    }

    @Override
    public String visit(BuiltinFunctionInvocation triple, Triples context) {
        String function = triple.getConversionFunction().accept(this, context);
        String argumentsText = triple.getOperands().stream().map(o -> o.accept(this, context)).collect(Collectors.joining(", "));
        return this.nativeFactory.makeBuiltinFunctionInvocation(function, argumentsText);
    }

    @Override
    public String visit(ApplyInvocation triple, Triples context) {
        String function = triple.getNameOperand().accept(this, context);
        String argumentsText = triple.getArguments().stream().map(a -> a.accept(this, context)).collect(Collectors.joining(", "));
        return this.nativeFactory.makeApplyInvocation(function, argumentsText);
    }

    @Override
    public String visit(SingletonInvocableInstance triple, Triples context) {
        return triple.getExp();
    }

    @Override
    public String visit(Constructor triple, Triples context) {
        String argsText = triple.getArgs().stream().map(e -> e.accept(this, context)).collect(Collectors.joining(", "));
        return this.nativeFactory.constructor(triple.getClsName(), argsText);
    }

    @Override
    public String visit(FluentConstructor triple, Triples context) {
        String addMethods = triple.getAddMethods().stream().map(o -> o.accept(this, context)).collect(Collectors.joining(""));
        return this.nativeFactory.fluentConstructor(triple.getContextClassName(), addMethods);
    }

    //
    // Conversion
    //
    @Override
    public String visit(AsList triple, Triples context) {
        String elements = triple.getSource().stream().map(s -> s.accept(this, context)).collect(Collectors.joining(", "));
        Type elementType = triple.getSourceType();
        return this.dmnTransformer.asList(elementType, elements);
    }

    @Override
    public String visit(ConversionFunction triple, Triples context) {
        return this.nativeFactory.conversionFunction(triple.getConversion(), triple.getToNativeType());
    }

    @Override
    public String visit(ConvertArgument convertArgument, Triples context) {
        Triple param = convertArgument.getParam();
        String paramText;
        if (param == null) {
            paramText = this.nativeFactory.nullLiteral();
        } else {
            paramText = param.accept(this, context);
        }
        Conversion<Type> conversion = convertArgument.getConversion();
        String conversionFunction = this.nativeFactory.conversionFunction(conversion, this.dmnTransformer.toNativeType(conversion.getTargetType()));
        if (conversionFunction != null) {
            paramText = this.nativeFactory.makeBuiltinFunctionInvocation(conversionFunction, paramText);
        }
        return paramText;
    }

    //
    // Expressions
    //
    @Override
    public String visit(InfixTriple infixExpression, Triples context) {
        String nativeOperator = infixExpression.getNativeOperator();
        String leftOpd = infixExpression.getLeftOpd().accept(this, context);
        String rightOpd = infixExpression.getRightOpd().accept(this, context);
        return String.format("(%s) %s (%s)", leftOpd, nativeOperator, rightOpd);
    }

    @Override
    public String visit(IsNullTriple triple, Triples context) {
        String text = triple.getInputExpressionToJava().accept(this, context);
        return this.nativeFactory.isNull(text);
    }

    @Override
    public String visit(IfTriple triple, Triples context) {
        String condition = triple.getCondition().accept(this, context);
        String thenExp = triple.getThenExp().accept(this, context);
        String elseExp = triple.getElseExp().accept(this, context);
        return this.nativeFactory.makeIfExpression(condition, thenExp, elseExp);
    }

    @Override
    public String visit(ForTriple triple, Triples context) {
        String body = triple.getBody().accept(this, context);
        List<Pair<String, String>> domainIterators = new ArrayList<>();
        for (Pair<Triple, String> itOpd : triple.getDomainIterators()) {
            String it = itOpd.getLeft().accept(this, context);
            String name = itOpd.getRight();
            domainIterators.add(new Pair<>(it, name));
        }
        return this.nativeFactory.makeForExpression(domainIterators, body);
    }

    @Override
    public String visit(EveryTriple triple, Triples context) {
        String forList = triple.getForList().accept(this, context);
        return this.nativeFactory.makeEveryExpression(forList);
    }

    @Override
    public String visit(SomeTriple triple, Triples context) {
        String forList = triple.getForList().accept(this, context);
        return this.nativeFactory.makeSomeExpression(forList);
    }

    @Override
    public String visit(InstanceOfTriple triple, Triples context) {
        String operandText = triple.getLeftOperand().accept(this, context);
        return this.nativeFactory.makeInstanceOf(operandText, triple.getRightOperandType());
    }

    @Override
    public String visit(LazyEvaluationTriple triple, Triples context) {
        return this.dmnTransformer.lazyEvaluation(triple.getName(), triple.getNativeName());
    }

    @Override
    public String visit(TripleReference triple, Triples context) {
        Triple refTriple = context.getTriple(triple.getIndex());
        return refTriple.accept(this, context);
    }

    @Override
    public String visit(TextTriple triple, Triples context) {
        return triple.getText();
    }
}
