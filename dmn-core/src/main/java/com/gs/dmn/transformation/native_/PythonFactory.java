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
package com.gs.dmn.transformation.native_;

import com.gs.dmn.DRGElementReference;
import com.gs.dmn.ast.TDecision;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class PythonFactory extends JavaFactory implements NativeFactory {
    private static final Set<String> PYTHON_KEYWORDS = new LinkedHashSet<>(
            Arrays.asList(
                    "False", "None", "True",
                    "and", "as", "assert", "async", "await",
                    "class", "continue", "def", "del", "elif",
                    "else", "except", "finally", "for", "from",
                    "global", "if", "import", "in", "is",
                    "lambda", "nonlocal", "not", "or", "pass",
                    "raisen", "return", "try", "while", "with",
                    "yieldbreak"
            )
    );

    public PythonFactory(BasicDMNToNativeTransformer<Type, DMNContext> transformer) {
        super(transformer);
    }

    //
    // Constructor
    //
    @Override
    public String constructor(String className, String arguments) {
        className = removeOptionalParts(className);
        return "%s(%s)".formatted(className, arguments);
    }

    protected String removeOptionalParts(String name) {
        while (name.startsWith("typing.Optional[")) {
            int first = name.indexOf("[");
            int last = name.lastIndexOf("]");
            name = name.substring(first + 1, last);
        }
        return name;
    }

    @Override
    public String fluentConstructor(String className, String addMethods) {
        className = removeOptionalParts(className);
        return "%s()%s".formatted(className, addMethods);
    }

    @Override
    public String functionalInterfaceConstructor(String functionalInterface, String returnType, String applyMethod) {
        functionalInterface = removeOptionalParts(functionalInterface);
        return "%s(lambda *%s: (%s))".formatted(functionalInterface, this.transformer.lambdaArgsVariableName(), applyMethod);
    }

    //
    // Selection
    //
    @Override
    public String makeItemDefinitionAccessor(String javaType, String source, String memberName) {
        memberName = this.transformer.lowerCaseFirst(memberName);
        return "%s if (%s) else (%s.%s)".formatted(this.nullLiteral(), isNull(source), source, memberName);

    }

    @Override
    public String makeItemDefinitionSelectExpression(String source, String memberName, String memberType) {
        return "%s.%s".formatted(source, memberName);
    }

    @Override
    public String makeContextAccessor(String javaType, String source, String memberName) {
        return "%s.%s".formatted(source, this.transformer.contextGetter(memberName));
    }

    @Override
    public String makeCollectionMap(String source, String filter) {
        return "list(map(lambda x: %s, %s))".formatted(filter, source);
    }

    @Override
    public String makeContextSelectExpression(String contextClassName, String source, String memberName) {
        return "(%s).get(\"%s\", self.asList())".formatted(source, memberName);
    }

    @Override
    public String prefixWithSelf(String text) {
        return "self.%s".formatted(text);
    }

    //
    // Expressions
    //
    @Override
    public String makeCollectionLogicFilter(String source, String parameterName, String filter) {
        return "list(filter(lambda %s: %s, %s))".formatted(parameterName, filter, source);
    }

    @Override
    public String makeCollectionNumericFilter(String javaElementType, String source, String filter) {
        String args = "%s, %s".formatted(source, filter);
        String call = this.makeBuiltinFunctionInvocation("elementAt", args);
        return "%s".formatted(call);
    }

    @Override
    public String makeIfExpression(String condition, String thenExp, String elseExp) {
        String args = "%s, %s".formatted(condition, trueConstant());
        String call = this.makeBuiltinFunctionInvocation("booleanEqual", args);
        return "(%s if %s else %s)".formatted(thenExp, call, elseExp);
    }

    @Override
    public String makeNullCheck(String exp, String type) {
        return "None if %s is None else %s".formatted(exp, exp);
    }

    @Override
    public String makeForExpression(List<Pair<String, String>> domainIterators, String body) {
        // Add code for each map
        StringBuilder nestedLists = new StringBuilder();
        for (Pair<String, String> pair: domainIterators) {
            nestedLists.append("list(map(lambda %s: ".formatted(pair.getRight()));
        }
        // Add body
        nestedLists.append(body);
        // Add source lists and close parenthesis
        for (int i = domainIterators.size() - 1; i >= 0; i--) {
            Pair<String, String> domain = domainIterators.get(i);
            nestedLists.append(", %s))".formatted(domain.getLeft()));
        }
        // Flatten nested lists
        StringBuilder finalResult = new StringBuilder();
        for (int i = 0; i < domainIterators.size() - 1; i++) {
            finalResult.append("list(self.flattenFirstLevel(");
        }
        // Append nested lists
        finalResult.append(nestedLists);
        // Close parenthesis
        for (int i = 0; i < domainIterators.size() - 1; i++) {
            finalResult.append("))");
        }
        return finalResult.toString();
    }

    @Override
    public String makeSomeExpression(String list) {
        return makeBuiltinFunctionInvocation("booleanOr", list);
    }

    @Override
    public String makeEveryExpression(String list) {
        return makeBuiltinFunctionInvocation("booleanAnd", list);
    }

    @Override
    public String makeInstanceOf(String value, Type type) {
        String nativeType = this.typeFactory.toNativeType(type.toString());
        return "isinstance(%s, %s)".formatted(value, nativeType);
    }

    //
    // Decision Table aggregators
    //
    @Override
    public String makeMinAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return "self.min(list(map(lambda o: o.%s, %s)))".formatted(
            outputClauseVariableName, ruleOutputListVariableName
        );
    }

    @Override
    public String makeMaxAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return "self.max(list(map(lambda o: o.%s, %s)))".formatted(
            outputClauseVariableName, ruleOutputListVariableName
        );
    }

    @Override
    public String makeCountAggregator(String ruleOutputListVariableName) {
        return "self.number(String.format(\"%d\", len(" + ruleOutputListVariableName + ")))";
    }

    @Override
    public String makeSumAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return "self.sum(list(map(lambda o: o.%s, %s)))".formatted(
            outputClauseVariableName, ruleOutputListVariableName
        );
    }

    //
    // Return
    //
    @Override
    public String makeReturn(String expression) {
        return "return %s".formatted(expression);
    }

    //
    // Assignment
    //
    @Override
    public String makeVariableAssignment(String type, String name, String expression) {
        String nullableType = this.typeFactory.nullableType(type);
        return "%s: %s = %s".formatted(name, nullableType, expression);
    }

    @Override
    public String makeMemberAssignment(String complexTypeVariable, String memberName, String value) {
        return "%s.%s = %s".formatted(complexTypeVariable, memberName, value);
    }

    @Override
    public String makeContextMemberAssignment(String complexTypeVariable, String memberName, String value) {
        return "%s.%s %s)".formatted(complexTypeVariable, this.transformer.contextSetter(memberName), value);
    }

    //
    // Equality
    //
    @Override
    public String isNull(String exp) {
        return "%s is %s".formatted(exp, this.nullLiteral());
    }

    //
    // Functions
    //
    @Override
    public String makeBuiltinFunctionInvocation(String javaFunctionCode, String argumentsText) {
        // Python keywords
        if (PYTHON_KEYWORDS.contains(javaFunctionCode)) {
            javaFunctionCode += "_";
        }
        return "self.%s(%s)".formatted(javaFunctionCode, argumentsText);
    }

    @Override
    protected String applyMethod(String returnType, String signature, String parametersAssignment, String body) {
        if (StringUtils.isEmpty(parametersAssignment)) {
            return body;
        } else {
            return "%s %s".formatted(parametersAssignment, body);
        }
    }

    @Override
    protected String makeLambdaParameterAssignment(String type, String name, int i) {
        return "%s := %s[%s],".formatted(name, transformer.lambdaArgsVariableName(), i);
    }

    @Override
    public String makeExternalExecutorCall(String externalExecutorVariableName, String className, String methodName, String arguments, String returnNativeType) {
        return "%s.execute(\"%s\", \"%s\", arrayOf(%s))".formatted(externalExecutorVariableName, className, methodName, arguments);
    }

    //
    // Parameters
    //
    @Override
    public String nullableParameterType(String parameterType) {
        return "%s".formatted(this.typeFactory.nullableType(parameterType));
    }

    @Override
    public String nullableParameter(String parameterType, String parameterName) {
        return "%s: %s".formatted(parameterName, this.typeFactory.nullableType(parameterType));
    }

    @Override
    public String parameterType(String parameterType) {
        return "%s".formatted(parameterType);
    }

    @Override
    public String parameter(String parameterType, String parameterName) {
        return "%s: %s".formatted(parameterName, parameterType);
    }

    @Override
    public String decisionConstructorParameter(DRGElementReference<TDecision> d) {
        String defaultValue = "None";
        return "%s: %s = %s".formatted(this.transformer.drgElementReferenceVariableName(d), this.transformer.qualifiedName(d), defaultValue);
    }

    //
    // Literal
    //
    @Override
    public String numericLiteral(String lexeme) {
        return "self.number(\"%s\")".formatted(lexeme);
    }

    @Override
    public String trueConstant() {
        return "True";
    }

    @Override
    public String falseConstant() {
        return "False";
    }

    @Override
    public String trueValueConstant() {
        return trueConstant();
    }

    @Override
    public String falseValueConstant() {
        return falseConstant();
    }

    @Override
    public String nullLiteral() {
        return "None";
    }

    //
    // Conversions
    //
    @Override
    public String makeListConversion(String javaExpression, ItemDefinitionType expectedElementType) {
        String elementConversion = convertToItemDefinitionType("x", expectedElementType);
        return "list(map(lambda x: %s, %s))".formatted(elementConversion, javaExpression);
    }

    @Override
    public String convertToItemDefinitionType(String expression, ItemDefinitionType type) {
        String convertMethodName = convertMethodName(type);
        String interfaceName = transformer.toNativeType(type);
        return "%s().%s(%s)".formatted(interfaceName, convertMethodName, expression);
    }

    @Override
    protected String itemDefinitionConversionLambda(String qNativeType, String convertFunction) {
        return "lambda e: %s.%s(e)".formatted(qNativeType, convertFunction);
    }
}
