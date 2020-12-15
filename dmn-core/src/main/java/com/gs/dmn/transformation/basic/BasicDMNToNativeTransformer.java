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
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.synthesis.FEELTranslator;
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
import org.omg.spec.dmn._20191111.model.*;

import javax.xml.bind.JAXBElement;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface BasicDMNToNativeTransformer {
    DMNDialectDefinition<?, ?, ?, ?, ?, ?> getDialect();

    DMNModelRepository getDMNModelRepository();

    EnvironmentFactory getEnvironmentFactory();

    DMNEnvironmentFactory getDMNEnvironmentFactory();

    FEELTranslator getFEELTranslator();

    NativeTypeFactory getNativeTypeFactory();

    NativeFactory getNativeFactory();

    DMNExpressionToNativeTransformer getExpressionToNativeTransformer();

    DRGElementFilter getDrgElementFilter();

    ProtoBufferFactory getProtoFactory();

    //
    // Configuration
    //
    boolean isOnePackage();

    boolean isSingletonInputData();

    //
    // TItemDefinition related functions
    //
    boolean isComplexType(TItemDefinition itemDefinition);

    String itemDefinitionNativeSimpleInterfaceName(TItemDefinition itemDefinition);

    String itemDefinitionNativeSimpleInterfaceName(String className);

    String itemDefinitionNativeClassName(String interfaceName);

    String itemDefinitionNativeQualifiedInterfaceName(TItemDefinition itemDefinition);

    String itemDefinitionSignature(TItemDefinition itemDefinition);

    String getter(TItemDefinition itemDefinition);

    String setter(TItemDefinition itemDefinition);

    String protoGetter(TItemDefinition itemDefinition);

    String protoGetter(TDRGElement drgElement);

    String protoSetter(TItemDefinition itemDefinition);

    String protoSetter(TDRGElement drgElement);

    //
    // TInformationItem related functions
    //
    String defaultConstructor(String className);

    String constructor(String className, String arguments);

    //
    // DRGElement related functions
    //
    boolean hasListType(TDRGElement element);

    String drgElementClassName(TDRGElement element);

    String drgElementReferenceVariableName(DRGElementReference<? extends TDRGElement> reference);

    String drgElementOutputType(DRGElementReference<? extends TDRGElement> reference);

    String drgElementOutputType(TDRGElement element);

    String drgElementOutputClassName(TDRGElement element);

    Type drgElementOutputFEELType(TDRGElement element);

    Type drgElementOutputFEELType(TDRGElement element, Environment environment);

    Type drgElementVariableFEELType(TDRGElement element);

    Type drgElementVariableFEELType(TDRGElement element, Environment environment);

    String annotation(TDRGElement element, String description);

    List<Pair<String, Type>> drgElementTypeSignature(TDRGElement element, Function<Object, String> nameProducer);

    List<Pair<String, Type>> drgElementTypeSignature(DRGElementReference<? extends TDRGElement> reference, Function<Object, String> nameProducer);

    List<Pair<String, Type>> drgElementTypeSignature(TDRGElement element);

    String drgElementSignature(TDRGElement element);

    String drgElementSignature(DRGElementReference<? extends TDRGElement> reference);

    String drgElementArgumentList(TDRGElement element);

    String drgElementArgumentList(DRGElementReference<? extends TDRGElement> reference);

    String drgElementConvertedArgumentList(TDRGElement element);

    String drgElementConvertedArgumentList(DRGElementReference<? extends TDRGElement> reference);

    List<String> drgElementArgumentNameList(TDRGElement element);

    List<String> drgElementArgumentDisplayNameList(TDRGElement element);

    List<String> drgElementArgumentNameList(DRGElementReference<? extends TDRGElement> reference);

    List<String> drgElementArgumentDisplayNameList(DRGElementReference<? extends TDRGElement> reference);

    boolean shouldGenerateApplyWithConversionFromString(TDRGElement element);

    String drgElementSignatureExtraCacheWithConversionFromString(TDRGElement element);

    String drgElementSignatureExtraWithConversionFromString(TDRGElement element);

    String drgElementSignatureWithConversionFromString(TDRGElement element);

    String drgElementArgumentListExtraCacheWithConversionFromString(TDRGElement element);

    String drgElementArgumentListExtraCacheWithConvertedArgumentList(TDRGElement element);

    String drgElementDefaultArgumentListExtraCacheWithConversionFromString(TDRGElement element);

    String drgElementDefaultArgumentListExtraCache(TDRGElement element);

    String drgElementArgumentListWithConversionFromString(TDRGElement element);

    String decisionConstructorSignature(TDecision decision);

    String decisionConstructorNewArgumentList(TDecision decision);

    boolean hasDirectSubDecisions(TDecision decision);

    //
    // NamedElement functions
    //
    String namedElementVariableName(TNamedElement element);

    String elementName(Object obj);

    String displayName(Object obj);

    String nativeName(Object obj);

    //
    // Evaluate method related functions
    //
    String drgElementEvaluateSignature(TDRGElement element);

    String drgElementEvaluateArgumentList(TDRGElement element);

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
    List<FormalParameter> invocableFEELParameters(TDRGElement invocable);

    //
    // BKM related functions
    //
    List<FormalParameter> bkmFEELParameters(TBusinessKnowledgeModel bkm);

    String bkmFunctionName(DRGElementReference<? extends TDRGElement> reference);

    String bkmFunctionName(TBusinessKnowledgeModel bkm);

    String bkmFunctionName(String name);

    String bkmQualifiedFunctionName(TBusinessKnowledgeModel bkm);

    List<String> bkmFEELParameterNames(TBusinessKnowledgeModel bkm);

    //
    // Decision Service related functions
    //
    List<FormalParameter> dsFEELParameters(TDecisionService service);

    String dsFunctionName(TDecisionService service);

    String dsFunctionName(String name);

    List<String> dsFEELParameterNames(TDecisionService service);

    String convertMethodName(TItemDefinition itemDefinition);

    String augmentSignature(String signature);

    String augmentArgumentList(String arguments);

    List<DRGElementReference<TInputData>> inputDataClosure(DRGElementReference<TDecision> reference);

    List<Pair<String, Type>> inputDataParametersClosure(DRGElementReference<TDecision> reference);

    String drgReferenceQualifiedName(DRGElementReference<? extends TDRGElement> reference);

    String bindingName(DRGElementReference<? extends TDRGElement> reference);

    String parameterNativeType(TDefinitions model, TInformationItem element);

    String parameterNativeType(TDRGElement element);

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

    String annotationSetClassName();

    String annotationSetVariableName();

    String eventListenerClassName();

    String eventListenerVariableName();

    String defaultEventListenerClassName();

    String loggingEventListenerClassName();

    String externalExecutorClassName();

    String externalExecutorVariableName();

    String defaultExternalExecutorClassName();

    String cacheInterfaceName();

    String cacheVariableName();

    String defaultCacheClassName();

    String drgElementSignatureExtra(DRGElementReference<? extends TDRGElement> reference);

    String drgElementSignatureExtra(TDRGElement element);

    String drgElementSignatureExtra(String signature);

    String drgElementArgumentListExtra(DRGElementReference<? extends TDRGElement> reference);

    String drgElementArgumentListExtra(TDRGElement element);

    String drgElementArgumentListExtra(String arguments);

    String drgElementDefaultArgumentListExtra(String arguments);

    boolean isCaching();

    boolean isCached(String elementName);

    boolean isParallelStream();

    String getStream();

    String drgElementSignatureExtraCache(DRGElementReference<? extends TDRGElement> reference);

    String drgElementSignatureExtraCache(TDRGElement element);

    String drgElementSignatureExtraCache(String signature);

    String drgElementArgumentListExtraCache(DRGElementReference<? extends TDRGElement> reference);

    String drgElementArgumentListExtraCache(TDRGElement element);

    String drgElementArgumentListExtraCache(String arguments);

    String drgElementDefaultArgumentListExtraCache(String arguments);

    String drgElementAnnotationClassName();

    String elementKindAnnotationClassName();

    String expressionKindAnnotationClassName();

    String drgElementMetadataClassName();

    String drgElementMetadataFieldName();

    String drgRuleMetadataClassName();

    String drgRuleMetadataFieldName();

    String assertClassName();

    //
    // Decision Table related functions
    //
    String defaultValue(TDRGElement element);

    String defaultValue(TDRGElement element, TOutputClause output);

    String condition(TDRGElement element, TDecisionRule rule);

    String outputEntryToNative(TDRGElement element, TLiteralExpression outputEntryExpression, int outputIndex);

    String outputClauseName(TDRGElement element, TOutputClause output);

    String outputClauseClassName(TDRGElement element, TOutputClause outputClause, int index);

    String outputClauseVariableName(TDRGElement element, TOutputClause outputClause);

    String outputClausePriorityVariableName(TDRGElement element, TOutputClause outputClause);

    String getter(TDRGElement element, TOutputClause output);

    String setter(TDRGElement element, TOutputClause output);

    Integer priority(TDRGElement element, TLiteralExpression literalExpression, int outputIndex);

    String priorityGetter(TDRGElement element, TOutputClause output);

    String prioritySetter(TDRGElement element, TOutputClause output);

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
    Type expressionType(TDRGElement element, JAXBElement<? extends TExpression> jElement, Environment environment);

    Type expressionType(TDRGElement element, TExpression expression, Environment environment);

    Type convertType(Type type, boolean convertToContext);

    Statement expressionToNative(TDRGElement element);

    Statement expressionToNative(TDRGElement element, TExpression expression, Environment environment);

    String literalExpressionToNative(TDRGElement element, String expressionText);

    String functionDefinitionToNative(TDRGElement element, FunctionDefinition functionDefinition, boolean convertTypeToContext, String body);

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

    String qualifiedName(String pkg, String name);

    String qualifiedName(DRGElementReference<? extends TDRGElement> reference);

    String qualifiedName(TDRGElement element);

    String getterName(String name);

    String getter(String name);

    String setter(String name);

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

    Environment makeEnvironment(TDRGElement element, Environment parentEnvironment);

    Environment makeFunctionDefinitionEnvironment(TNamedElement element, TFunctionDefinition functionDefinition, Environment parentEnvironment);

    Environment makeInputEntryEnvironment(TDRGElement element, Expression inputExpression);

    Environment makeOutputEntryEnvironment(TDRGElement element, EnvironmentFactory environmentFactory);

    Pair<Environment, Map<TContextEntry, Expression>> makeContextEnvironment(TDRGElement element, TContext context, Environment parentEnvironment);

    Environment makeRelationEnvironment(TNamedElement element, TRelation relation, Environment environment);

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

    String drgElementSignatureExtraCacheProto(TDRGElement element);

    String drgElementArgumentListExtraCacheProto(TDRGElement element);

    String drgElementArgumentListProto(TDRGElement element);

    String drgElementDefaultArgumentListExtraCacheProto(TDRGElement element);

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
}
