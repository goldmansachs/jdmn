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

import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.Conversion;
import com.gs.dmn.runtime.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Triples {
    private final List<Triple> triples = new ArrayList<>();

    public void init() {
        this.triples.clear();
    }

    public Triple getTriple(int index) {
        return this.triples.get(index);
    }

    // Literals
    public Triple numericLiteral(String lexeme) {
        Triple triple = new NumberLiteral(lexeme);
        return addTriple(triple);
    }

    public Triple stringLiteral(String value) {
        Triple triple = new StringLiteral(value);
        return addTriple(triple);
    }

    public Triple booleanLiteral(String lexeme) {
        Triple triple = new BooleanLiteral(lexeme, false);
        return addTriple(triple);
    }

    public Triple booleanValueLiteral(boolean value) {
        Triple triple = new BooleanLiteral("" + value, true);
        return addTriple(triple);
    }

    public Triple booleanValueLiteral(String lexeme) {
        Triple triple = new BooleanLiteral(lexeme, true);
        return addTriple(triple);
    }

    public Triple booleanValueConstant(String lexeme) {
        Triple triple = new BooleanLiteral(lexeme, true);
        return addTriple(triple);
    }

    public Triple trueConstant() {
        Triple triple = new BooleanLiteral("true", false);
        return addTriple(triple);
    }

    public Triple dateTimeLiteral(com.gs.dmn.feel.analysis.syntax.ast.expression.literal.DateTimeLiteral<Type> element) {
        Triple triple = new DateTimeLiteral(element);
        return addTriple(triple);
    }

    public Triple nullLiteral() {
        Triple triple = new NullLiteral();
        return addTriple(triple);
    }

    public Triple name(String name) {
        Triple triple = new NameTriple(name);
        return addTriple(triple);
    }

    public Triple makeLambdaAccessor(String exp) {
        Triple triple = new TextTriple(exp);
        return addTriple(triple);
    }

    public Triple makeContextEntry(String key, Triple value) {
        Triple triple = new ContextEntryTriple(key, value);
        return addTriple(triple);
    }

    //
    // Path
    //
    public Triple makeItemDefinitionAccessor(String javaType, Triple source, String memberName) {
        Triple triple = new ItemDefinitionAccessor(javaType, source, memberName);
        return addTriple(triple);
    }

    public Triple makeContextAccessor(String javaType, Triple source, String memberName) {
        Triple triple = new ContextAccessor(javaType, source, memberName);
        return addTriple(triple);
    }

    public Triple makeContextSelectExpression(String contextClassName, Triple source, String memberName) {
        Triple triple = new ContextSelect(contextClassName, source, memberName);
        return addTriple(triple);
    }

    public Triple makeRangeAccessor(Triple source, String rangeGetter) {
        Triple triple = new RangeAccessor(source, rangeGetter);
        return addTriple(triple);
    }

    public Triple makeCollectionMap(Triple source, Triple filter) {
        Triple triple = new CollectionMap(source, filter);
        return addTriple(triple);
    }

    //
    // Filter
    //
    public Triple makeCollectionLogicFilter(Triple source, String newParameterName, Triple filter) {
        Triple triple = new CollectionLogicFilter(source, newParameterName, filter);
        return addTriple(triple);
    }

    public Triple makeCollectionNumericFilter(String javaElementType, Triple source, Triple filter) {
        Triple triple = new CollectionNumericFilter(javaElementType, source, filter);
        return addTriple(triple);
    }

    //
    // Function
    //
    public Triple makeFunctionDefinition(TDRGElement element, com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition<Type> functionDefinition, boolean convertTypeToContext, Triple body) {
        Triple triple = new FunctionDefinitionTriple(element, functionDefinition, convertTypeToContext, body);
        return addTriple(triple);
    }

    public Triple makeBuiltinFunctionInvocation(String functionName, Triple... operands) {
        return makeBuiltinFunctionInvocation(name(functionName), Arrays.asList(operands));
    }

    public Triple makeBuiltinFunctionInvocation(Triple function, List<Triple> operands) {
        Triple triple = new BuiltinFunctionInvocation(function, operands);
        return addTriple(triple);
    }

    public Triple makeApplyInvocation(String javaFunctionName, List<Triple> arguments) {
        return makeApplyInvocation(this.name(javaFunctionName), arguments);
    }

    public Triple makeApplyInvocation(Triple javaFunctionCode, List<Triple> arguments) {
        Triple triple = new ApplyInvocation(javaFunctionCode, arguments);
        return addTriple(triple);
    }

    public Triple singletonInvocableInstance(String exp) {
        Triple triple = new SingletonInvocableInstance(exp);
        return addTriple(triple);
    }

    public Triple constructor(String clsName, List<Triple> args, boolean isGeneric) {
        Triple triple = new Constructor(clsName, args, isGeneric);
        return addTriple(triple);
    }

    public Triple fluentConstructor(String contextClassName, List<Triple> addMethods) {
        Triple triple = new FluentConstructor(contextClassName, addMethods);
        return addTriple(triple);
    }

    //
    // Conversion
    //
    public Triple asList(Type sourceType, List<Triple> source) {
        Triple triple = new AsList(sourceType, source);
        return addTriple(triple);
    }

    public Triple conversionFunction(Conversion<Type> conversion, String toNativeType) {
        Triple triple = new ConversionFunction(conversion, toNativeType);
        return addTriple(triple);
    }

    public Triple makeConvertArgument(Triple param, Conversion<Type> conversion) {
        Triple triple = new ConvertArgument(param, conversion);
        return addTriple(triple);
    }

    //
    // Expression
    //
    public Triple makeInfixExpression(String nativeOperator, Triple leftOpd, Triple rightOpd) {
        Triple triple;
        if ("==".equals(nativeOperator)) {
            if (isNullLiteral(leftOpd)) {
                return makeIsNull(rightOpd);
            } else if (isNullLiteral(rightOpd)) {
                return makeIsNull(leftOpd);
            }
        }
        if ("!=".equals(nativeOperator)) {
            if (isNullLiteral(leftOpd)) {
                return makeIsNotNull(rightOpd);
            } else if (isNullLiteral(rightOpd)) {
                return makeIsNotNull(leftOpd);
            }
        }
        triple = new InfixTriple(nativeOperator, leftOpd, rightOpd);
        return addTriple(triple);
    }

    private boolean isNullLiteral(Triple operand) {
        if (operand instanceof TripleReference) {
            operand = this.triples.get(((TripleReference) operand).getIndex());
        }
        return operand instanceof NullLiteral;
    }

    public Triple makeIfExpression(Triple condition, Triple thenExp, Triple elseExp) {
        Triple triple = new IfTriple(condition, thenExp, elseExp);
        return addTriple(triple);
    }

    public Triple makeIsNull(Triple inputExpressionToJava) {
        Triple triple = new IsNullTriple(inputExpressionToJava);
        return addTriple(triple);
    }

    public Triple makeIsNotNull(Triple inputExpressionToJava) {
        Triple triple = new IsNotNullTriple(inputExpressionToJava);
        return addTriple(triple);
    }

    public Triple makeForExpression(List<Pair<Triple, String>> domainIterators, Triple body) {
        Triple triple = new ForTriple(domainIterators, body);
        return addTriple(triple);
    }

    public Triple makeSomeExpression(Triple forList) {
        Triple triple = new SomeTriple(forList);
        return addTriple(triple);
    }

    public Triple makeEveryExpression(Triple forList) {
        Triple triple = new EveryTriple(forList);
        return addTriple(triple);
    }

    public Triple makeInstanceOf(Triple leftOperand, Type rightOperandType) {
        Triple triple = new InstanceOfTriple(leftOperand, rightOperandType);
        return addTriple(triple);
    }

    public Triple lazyEvaluation(String name, String nativeName) {
        Triple triple = new LazyEvaluationTriple(name, nativeName);
        return addTriple(triple);
    }

    public Triple text(String text) {
        Triple triple = new TextTriple(String.format("\"%s\"", text));
        return addTriple(triple);
    }

    @Override
    public String toString() {
        return triples.toString();
    }

    private Triple addTriple(Triple triple) {
        int lastIndex = triples.size();
        triples.add(triple);
        return new TripleReference(lastIndex);
    }
}
