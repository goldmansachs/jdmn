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
package com.gs.dmn.transformation.basic;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.DRGElementFilter;
import com.gs.dmn.DRGElementReference;
import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.*;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.DMNContextKind;
import com.gs.dmn.context.environment.Environment;
import com.gs.dmn.context.environment.EnvironmentFactory;
import com.gs.dmn.context.environment.RuntimeEnvironment;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.AnyType;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.el.synthesis.ELTranslator;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.textual.FilterExpression;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.annotation.DRGElementKind;
import com.gs.dmn.runtime.annotation.ExpressionKind;
import com.gs.dmn.runtime.annotation.HitPolicy;
import com.gs.dmn.transformation.native_.NativeFactory;
import com.gs.dmn.transformation.native_.statement.Statement;
import com.gs.dmn.transformation.proto.MessageType;
import com.gs.dmn.transformation.proto.ProtoBufferFactory;
import com.gs.dmn.transformation.proto.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface BasicDMNToNativeTransformer<T, C> {
    DMNDialectDefinition<?, ?, ?, ?, ?, ?> getDialect();

    DMNModelRepository getDMNModelRepository();

    EnvironmentFactory getEnvironmentFactory();

    DMNEnvironmentFactory getDMNEnvironmentFactory();

    ELTranslator<T, C> getFEELTranslator();

    NativeTypeFactory getNativeTypeFactory();

    NativeFactory getNativeFactory();

    DRGElementFilter getDrgElementFilter();

    ProtoBufferFactory getProtoFactory();

    //
    // Configuration
    //
    boolean isOnePackage();

    boolean isSingletonInputData();

    boolean isStrongTyping();

    //
    // TItemDefinition related functions
    //
    boolean isComplexType(TItemDefinition itemDefinition);

    String itemDefinitionNativeSimpleInterfaceName(TItemDefinition itemDefinition);

    String itemDefinitionNativeQualifiedInterfaceName(TItemDefinition itemDefinition);

    String itemDefinitionNativeSimpleInterfaceName(String className);

    String itemDefinitionNativeClassName(String interfaceName);

    String itemDefinitionSignature(TItemDefinition itemDefinition);

    List<String> itemDefinitionComplexComponents(TItemDefinition itemDefinition);

    String getter(TItemDefinition itemDefinition);

    String setter(TItemDefinition itemDefinition, String args);

    String protoGetter(TItemDefinition itemDefinition);

    String protoGetter(TDRGElement drgElement);

    String protoSetter(TItemDefinition itemDefinition, String args);

    String protoSetter(TDRGElement drgElement, String args);

    //
    // Native factory methods
    //
    String defaultConstructor(String className);

    String constructor(String className, String arguments);

    //
    // DRGElement related functions
    //
    boolean hasListType(TDRGElement element);

    String drgElementClassName(DRGElementReference<? extends TDRGElement> reference);

    String drgElementClassName(TDRGElement element);

    String drgElementReferenceVariableName(DRGElementReference<? extends TDRGElement> reference);

    String drgElementOutputType(DRGElementReference<? extends TDRGElement> reference);

    String drgElementOutputType(TDRGElement element);

    String drgElementOutputInterfaceName(TDRGElement element);

    String drgElementOutputClassName(TDRGElement element);

    Type drgElementOutputFEELType(TDRGElement element);

    Type drgElementOutputFEELType(TDRGElement element, DMNContext context);

    Type drgElementVariableFEELType(TDRGElement element);

    Type drgElementVariableFEELType(TDRGElement element, DMNContext context);

    String annotation(TDRGElement element, String description);

    List<Pair<String, Type>> drgElementTypeSignature(TDRGElement element, Function<Object, String> nameProducer);

    List<Pair<String, Type>> drgElementTypeSignature(DRGElementReference<? extends TDRGElement> reference, Function<Object, String> nameProducer);

    List<Pair<String, Type>> drgElementTypeSignature(TDRGElement element);

    boolean canGenerateApplyWithMap(TDRGElement element);

    String drgElementSignatureWithMap(TDRGElement element);

    String drgElementSignature(TDRGElement element);

    String drgElementSignature(DRGElementReference<? extends TDRGElement> reference);

    List<Pair<String, String>> drgElementSignatureParameters(TDRGElement element);

    List<Pair<String, String>> drgElementSignatureParameters(DRGElementReference<? extends TDRGElement> reference);

    List<String> drgElementComplexInputClassNames(TDRGElement element);

    String drgElementArgumentListWithMap(TDRGElement element);

    String drgElementArgumentListWithMap(DRGElementReference<? extends TDRGElement> reference);

    String drgElementArgumentList(TDRGElement element);

    String drgElementArgumentList(DRGElementReference<? extends TDRGElement> reference);

    String drgElementConvertedArgumentList(TDRGElement element);

    String drgElementConvertedArgumentList(DRGElementReference<? extends TDRGElement> reference);

    List<String> drgElementArgumentNameList(TDRGElement element);

    List<String> drgElementArgumentDisplayNameList(TDRGElement element);

    List<String> drgElementArgumentNameList(DRGElementReference<? extends TDRGElement> reference);

    List<String> drgElementArgumentDisplayNameList(DRGElementReference<? extends TDRGElement> reference);

    boolean shouldGenerateApplyWithConversionFromString(TDRGElement element);

    String drgElementSignatureWithConversionFromString(TDRGElement element);

    String drgElementArgumentListWithConversionFromString(TDRGElement element);

    boolean hasComplexInputDatas(TDRGElement element);

    boolean hasDirectSubDecisions(TDRGElement element);

    boolean hasDirectSubInvocables(TDRGElement element);

    String drgElementConstructorSignature(TDRGElement element);

    String drgElementConstructorNewArgumentList(TDRGElement element);

    boolean isSingletonDecision();

    String singletonDecisionConstructor(String javaClassName, TDecision decision);

    String singletonDecisionInstance(String decisionQName);

    //
    // NamedElement functions
    //
    String namedElementVariableName(TNamedElement element);

    String elementName(Object obj);

    String displayName(Object obj);

    String nativeName(Object obj);

    String lambdaApplySignature();

    String lambdaArgsVariableName();

    //
    // Comment related functions
    //
    String startElementCommentText(TDRGElement element);

    String endElementCommentText(TDRGElement element);

    String evaluateElementCommentText(TDRGElement element);

    QualifiedName drgElementOutputTypeRef(TDRGElement element);

    //
    // InputData related functions
    //
    Type toFEELType(TInputData inputData);

    //
    // Invocable  related functions
    //
    List<FormalParameter<Type>> invocableFEELParameters(TDRGElement invocable);

    List<String> invocableFEELParameterNames(TDRGElement invocable);

    String singletonInvocableInstance(TInvocable invocable);

    //
    // BKM related functions
    //
    List<FormalParameter<Type>> bkmFEELParameters(TBusinessKnowledgeModel bkm);

    List<String> bkmFEELParameterNames(TBusinessKnowledgeModel bkm);

    //
    // Decision Service related functions
    //
    List<FormalParameter<Type>> dsFEELParameters(TDecisionService service);

    List<String> dsFEELParameterNames(TDecisionService service);

    Statement serviceToNative(TDecisionService element);

    String convertMethodName(TItemDefinition itemDefinition);

    String augmentSignature(String signature);

    List<Pair<String, String>> augmentSignatureParameters(List<Pair<String, String>> signature);

    String augmentArgumentList(String arguments);

    List<String> extractExtraParametersFromExecutionContext();

    List<DRGElementReference<TInputData>> inputDataClosure(DRGElementReference<TDecision> reference);

    List<Pair<String, Type>> inputDataParametersClosure(DRGElementReference<TDecision> reference);

    String drgReferenceQualifiedName(DRGElementReference<? extends TDRGElement> reference);

    String bindingName(DRGElementReference<? extends TDRGElement> reference);

    String registryId(TDRGElement element);

    String parameterNativeType(TDefinitions model, TInformationItem element);

    String parameterNativeType(TDRGElement element);

    String extractParameterFromArgs(Pair<String, String> parameter, int index);

    boolean isLazyEvaluated(DRGElementReference<? extends TDRGElement> reference);

    boolean isLazyEvaluated(TDRGElement element);

    boolean isLazyEvaluated(String name);

    String lazyEvaluationType(TDRGElement input, String inputJavaType);

    String lazyEvaluation(String elementName, String nativeName);

    String pairClassName();

    String pairComparatorClassName();

    String argumentsClassName();

    String argumentsVariableName(TDRGElement element);

    String dmnTypeClassName();

    String dmnRuntimeExceptionClassName();

    String lazyEvalClassName();

    String contextClassName();

    String rangeClassName();

    String executorClassName();

    String registryClassName();

    String executionContextClassName();

    String executionContextVariableName();

    String annotationSetClassName();

    String annotationSetVariableName();

    String eventListenerClassName();

    String eventListenerVariableName();

    String defaultEventListenerClassName();

    String loggingEventListenerClassName();

    String treeTraceEventListenerClassName();

    String externalExecutorClassName();

    String externalExecutorVariableName();

    String defaultExternalExecutorClassName();

    String cacheInterfaceName();

    String cacheVariableName();

    String defaultCacheClassName();

    boolean isCaching();

    boolean isCached(String elementName);

    boolean isParallelStream();

    String getStream();

    String drgElementAnnotationClassName();

    String elementKindAnnotationClassName();

    String expressionKindAnnotationClassName();

    String drgElementMetadataClassName();

    String drgElementMetadataFieldName();

    String drgRuleMetadataClassName();

    String drgRuleMetadataFieldName();

    String assertClassName();

    String javaExternalFunctionClassName();

    String lambdaExpressionClassName();

    String javaFunctionInfoClassName();

    //
    // Decision Table related functions
    //
    String defaultValue(TDRGElement element);

    String defaultValue(TDRGElement element, TOutputClause output);

    String condition(TDRGElement element, TDecisionRule rule, int ruleIndex);

    String outputEntryToNative(TDRGElement element, TLiteralExpression outputEntryExpression, int outputIndex);

    String outputClauseName(TDRGElement element, TOutputClause output);

    String outputClauseClassName(TDRGElement element, TOutputClause outputClause, int index);

    String outputClauseVariableName(TDRGElement element, TOutputClause outputClause);

    String outputClausePriorityVariableName(TDRGElement element, TOutputClause outputClause);

    String outputClauseGetter(TDRGElement element, TOutputClause output);

    String drgElementOutputGetter(TDRGElement element, TOutputClause output);

    String outputClauseSetter(TDRGElement element, TOutputClause output, String args);

    String drgElementOutputSetter(TDRGElement element, TOutputClause output, String args);

    Integer outputClausePriority(TDRGElement element, TLiteralExpression literalExpression, int outputIndex);

    String outputClausePriorityGetter(TDRGElement element, TOutputClause output);

    String outputClausePrioritySetter(TDRGElement element, TOutputClause output, String args);

    HitPolicy hitPolicy(TDRGElement element);

    String aggregation(TDecisionTable decisionTable);

    String aggregator(TDRGElement element, TDecisionTable decisionTable, TOutputClause outputClause, String ruleOutputListVariable);

    String annotation(TDRGElement element, TDecisionRule rule);

    String annotationEscapedText(TDecisionRule rule);

    String escapeInString(String text);

    //
    // Rule related functions
    //
    String ruleOutputClassName(TDRGElement element);

    String qualifiedRuleOutputClassName(TDRGElement element);

    String ruleId(List<TDecisionRule> rules, TDecisionRule rule);

    String abstractRuleOutputClassName();

    String ruleOutputListClassName();

    String ruleSignature(TDecision decision);

    String ruleArgumentList(TDecision decision);

    String ruleSignature(TBusinessKnowledgeModel bkm);

    String ruleArgumentList(TBusinessKnowledgeModel bkm);

    String hitPolicyAnnotationClassName();

    String ruleAnnotationClassName();

    //
    // Expression related functions
    //
    Type expressionType(TDRGElement element, TExpression expression, DMNContext context);

    Type convertType(Type type, boolean convertToContext);

    Statement expressionToNative(TDRGElement element);

    Statement expressionToNative(TDRGElement element, TExpression expression, DMNContext context);

    String literalExpressionToNative(TDRGElement element, String expressionText);

    String functionDefinitionToNative(TDRGElement element, FunctionDefinition<Type> functionDefinition, boolean convertTypeToContext, String body);

    boolean isCompoundStatement(Statement stm);

    //
    // Type related functions
    //
    boolean isComplexType(Type type);

    boolean isDateTimeType(Type type);

    Type toFEELType(TDefinitions model, String typeName);

    Type toFEELType(TDefinitions model, QualifiedName typeRef);

    Type toFEELType(TItemDefinition itemDefinition);

    //
    // Common functions
    //
    String toNativeType(TDecision decision);

    String toStringNativeType(Type type);

    String toNativeType(Type type);

    String makeListType(String listType, String elementType);

    String makeListType(String listType);

    String nullableType(String type);

    String jdmnRootPackage();

    String qualifiedName(String pkg, String name);

    String qualifiedName(DRGElementReference<? extends TDRGElement> reference);

    String qualifiedName(TDRGElement element);

    String qualifiedName(Class<?> cls);

    String qualifiedModuleName(DRGElementReference<? extends TDRGElement> reference);

    String qualifiedModuleName(TDRGElement element);

    String qualifiedModuleName(String pkg, String moduleName);

    String getterName(String name);

    String getter(String name);

    String setter(String name, String args);

    String contextGetter(String name);

    String contextSetter(String name);

    String nativeFriendlyVariableName(String name);

    String nativeFriendlyName(String name);

    String upperCaseFirst(String name);

    String lowerCaseFirst(String name);

    String nativeModelPackageName(String modelName);

    String nativeRootPackageName();

    String nativeTypePackageName(String modelName);

    DRGElementKind elementKind(TDRGElement element);

    ExpressionKind expressionKind(TDRGElement element);

    String asEmptyList(TDRGElement element);

    String asList(Type elementType, String exp);

    //
    // Environment related functions
    //
    Environment makeEnvironment(TDRGElement element);

    Environment makeFunctionDefinitionEnvironment(TNamedElement element, TFunctionDefinition functionDefinition);

    Pair<DMNContext, Map<TContextEntry, Expression<Type>>> makeContextEnvironment(TDRGElement element, TContext context, DMNContext parentContext);

    Environment makeRelationEnvironment(TNamedElement element, TRelation relation);

    boolean isFEELFunction(TFunctionKind kind);

    boolean isJavaFunction(TFunctionKind kind);

    //
    // .proto related functions
    //
    boolean isGenerateProto();

    boolean isGenerateProtoMessages();

    boolean isGenerateProtoServices();

    String getProtoVersion();

    String protoPackage(String javaPackageName);

    Pair<Pair<List<MessageType>, List<MessageType>>, List<Service>> dmnToProto(TDefinitions definitions);

    String drgElementSignatureProto(TDRGElement element);

    String drgElementArgumentListProto(TDRGElement element);

    String convertProtoMember(String source, TItemDefinition parent, TItemDefinition child, boolean staticContext);

    String convertMemberToProto(String source, String sourceType, TItemDefinition child, boolean staticContext);

    String qualifiedProtoMessageName(TItemDefinition itemDefinition);

    String qualifiedRequestMessageName(TDRGElement element);

    String qualifiedResponseMessageName(TDRGElement element);

    String requestVariableName(TDRGElement element);

    String responseVariableName(TDRGElement element);

    String namedElementVariableNameProto(TNamedElement element);

    String drgElementOutputTypeProto(TDRGElement element);

    String qualifiedNativeProtoType(TItemDefinition itemDefinition);

    boolean isProtoReference(TItemDefinition itemDefinition);

    boolean isProtoReference(Type type);

    String protoFieldName(TNamedElement element);

    String extractParameterFromRequestMessage(TDRGElement element, Pair<String, Type> parameter, boolean staticContext);

    String convertValueToProtoNativeType(String value, Type type, boolean staticContext);

    String extractMemberFromProtoValue(String protoValue, Type type, boolean staticContext);

    default DMNContext makeBuiltInContext() {
        return getEnvironmentFactory().getBuiltInContext();
    }

    default DMNContext makeGlobalContext(TDRGElement element) {
        return DMNContext.of(
                this.makeBuiltInContext(),
                DMNContextKind.GLOBAL,
                element,
                makeEnvironment(element),
                RuntimeEnvironment.of()
        );
    }

    default DMNContext makeGlobalContext(TDRGElement element, boolean isRecursive) {
        return DMNContext.of(
                makeBuiltInContext(),
                DMNContextKind.GLOBAL,
                element,
                getDMNEnvironmentFactory().makeEnvironment(element, isRecursive),
                RuntimeEnvironment.of()
        );
    }

    default DMNContext makeGlobalContext(TDRGElement element, DMNContext parentContext) {
        return DMNContext.of(
                parentContext,
                DMNContextKind.GLOBAL,
                element,
                makeEnvironment(element),
                RuntimeEnvironment.of()
        );
    }

    default DMNContext makeUnaryTestContext(Expression<Type> inputExpression, DMNContext parentContext) {
        return DMNContext.of(
                parentContext,
                DMNContextKind.UNARY_TEST_CONTEXT,
                parentContext.getElement(),
                getDMNEnvironmentFactory().makeUnaryTestEnvironment((TDRGElement) parentContext.getElement(), inputExpression),
                RuntimeEnvironment.of());
    }

    default DMNContext makeLocalContext(DMNContext parentContext) {
        return DMNContext.of(
                parentContext,
                DMNContextKind.LOCAL,
                parentContext.getElement(),
                getEnvironmentFactory().emptyEnvironment(),
                RuntimeEnvironment.of()
        );
    }

    default DMNContext makeLocalContext(TDRGElement element, DMNContext parentContext) {
        return DMNContext.of(
                parentContext,
                DMNContextKind.LOCAL,
                element,
                getEnvironmentFactory().emptyEnvironment(),
                RuntimeEnvironment.of()
        );
    }

    default DMNContext makeFunctionContext(TDRGElement element, TFunctionDefinition expression, DMNContext parentContext) {
        return DMNContext.of(
                parentContext,
                DMNContextKind.FUNCTION,
                element,
                makeFunctionDefinitionEnvironment(element, expression),
                RuntimeEnvironment.of()
        );
    }

    default DMNContext makeFunctionContext(TFunctionDefinition functionDefinition, DMNContext parentContext) {
        DMNContext functionContext = DMNContext.of(
                parentContext,
                DMNContextKind.FUNCTION,
                parentContext.getElement(),
                getEnvironmentFactory().emptyEnvironment(),
                RuntimeEnvironment.of());
        List<TInformationItem> formalParameterList = functionDefinition.getFormalParameter();
        TDefinitions model = getDMNModelRepository().getModel(parentContext.getElement());
        for (TInformationItem param : formalParameterList) {
            String name = param.getName();
            Type type = toFEELType(null, QualifiedName.toQualifiedName(model, param.getTypeRef()));
            functionContext.addDeclaration(getEnvironmentFactory().makeVariableDeclaration(name, type));
        }
        return functionContext;
    }

    default DMNContext makeFunctionContext(FunctionDefinition<Type> functionDefinition, DMNContext parentContext) {
        DMNContext functionContext = DMNContext.of(
                parentContext,
                DMNContextKind.FUNCTION,
                parentContext.getElement(),
                getEnvironmentFactory().emptyEnvironment(),
                RuntimeEnvironment.of());
        List<FormalParameter<Type>> formalParameterList = functionDefinition.getFormalParameters();
        for (FormalParameter<Type> param : formalParameterList) {
            String name = param.getName();
            Type type = param.getType();
            functionContext.addDeclaration(getEnvironmentFactory().makeVariableDeclaration(name, type));
        }
        return functionContext;
    }

    default DMNContext makeForContext(DMNContext parentContext) {
        return DMNContext.of(
                parentContext,
                DMNContextKind.FOR,
                parentContext.getElement(),
                this.getEnvironmentFactory().emptyEnvironment(),
                RuntimeEnvironment.of()
        );
    }

    default DMNContext makeIteratorContext(DMNContext context) {
        return DMNContext.of(
                context,
                DMNContextKind.ITERATOR,
                context.getElement(),
                this.getEnvironmentFactory().emptyEnvironment(),
                RuntimeEnvironment.of()
        );
    }

    default DMNContext makeFilterContext(FilterExpression<Type> filterExpression, String filterParameterName, DMNContext parentContext) {
        Expression<Type> source = filterExpression.getSource();
        Type sourceType = source.getType();
        return makeFilterContext(sourceType, filterParameterName, parentContext);
    }

    default DMNContext makeFilterContext(Type sourceType, String filterParameterName, DMNContext parentContext) {
        Type itemType = AnyType.ANY;
        if (sourceType instanceof ListType) {
            itemType = ((ListType) sourceType).getElementType();
        }
        DMNContext filterContext = DMNContext.of(
                parentContext,
                DMNContextKind.FILTER,
                parentContext.getElement(),
                getEnvironmentFactory().emptyEnvironment(),
                RuntimeEnvironment.of()
        );
        filterContext.addDeclaration(this.getEnvironmentFactory().makeVariableDeclaration(filterParameterName, itemType));
        return filterContext;
    }

    default DMNContext makeRelationContext(TDRGElement element, TRelation relation, DMNContext parentContext) {
        return DMNContext.of(
                parentContext,
                DMNContextKind.RELATION,
                element,
                makeRelationEnvironment(element, relation),
                RuntimeEnvironment.of()
        );
    }

    default DMNContext makeLocalContext(TDRGElement element, TContext context, DMNContext parentContext) {
        return DMNContext.of(
                parentContext,
                DMNContextKind.LOCAL,
                element,
                this.getEnvironmentFactory().emptyEnvironment(),
                RuntimeEnvironment.of()
        );
    }

    //
    // Mock testing related methods
    //
    boolean isMockTesting();

    boolean isGenerateExtra();

    String getNativeNumberType();

    String getNativeDateType();

    String getNativeTimeType();

    String getNativeDateAndTimeType();

    String getNativeDurationType();

    String getDefaultIntegerValue();

    String getDefaultDecimalValue();

    String getDefaultStringValue();

    String getDefaultBooleanValue();

    String getDefaultDateValue();

    String getDefaultTimeValue();

    String getDefaultDateAndTimeValue();

    String getDefaultDurationValue();

    boolean isInteger(TItemDefinition element);

    String makeIntegerForInput(String text);

    String makeDecimalForInput(String text);

    String makeDecimalForDecision(String text);

    String makeDate(String text);

    String makeTime(String text);

    String makeDateTime(String text);

    String makeDuration(String text);

    String getDefaultValue(Type memberType, TItemDefinition memberItemDefinition);
}
