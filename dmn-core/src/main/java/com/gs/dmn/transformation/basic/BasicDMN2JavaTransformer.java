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
import com.gs.dmn.feel.analysis.semantics.environment.*;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.lib.StringEscapeUtil;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.feel.synthesis.FEELTranslatorImpl;
import com.gs.dmn.feel.synthesis.expression.JavaExpressionFactory;
import com.gs.dmn.feel.synthesis.expression.NativeExpressionFactory;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.runtime.*;
import com.gs.dmn.runtime.annotation.AnnotationSet;
import com.gs.dmn.runtime.annotation.DRGElementKind;
import com.gs.dmn.runtime.annotation.ExpressionKind;
import com.gs.dmn.runtime.annotation.HitPolicy;
import com.gs.dmn.runtime.cache.Cache;
import com.gs.dmn.runtime.cache.DefaultCache;
import com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor;
import com.gs.dmn.runtime.external.ExternalFunctionExecutor;
import com.gs.dmn.runtime.interpreter.ImportPath;
import com.gs.dmn.runtime.listener.Arguments;
import com.gs.dmn.runtime.listener.EventListener;
import com.gs.dmn.runtime.listener.LoggingEventListener;
import com.gs.dmn.runtime.listener.NopEventListener;
import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.InputParamUtil;
import com.gs.dmn.transformation.java.CompoundStatement;
import com.gs.dmn.transformation.java.ExpressionStatement;
import com.gs.dmn.transformation.java.Statement;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.LazyEvaluationOptimisation;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import java.util.*;
import java.util.stream.Collectors;

public class BasicDMN2JavaTransformer {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BasicDMN2JavaTransformer.class);

    protected final DMNModelRepository dmnModelRepository;
    protected final EnvironmentFactory environmentFactory;
    protected final NativeTypeFactory typeFactory;
    protected NativeExpressionFactory expressionFactory;

    protected final FEELTranslator feelTranslator;
    private final ContextToJavaTransformer contextToJavaTransformer;
    private final DecisionTableToJavaTransformer decisionTableToJavaTransformer;
    private final FunctionDefinitionToJavaTransformer functionDefinitionToJavaTransformer;
    private final LiteralExpressionToJavaTransformer literalExpressionToJavaTransformer;
    private final InvocationToJavaTransformer invocationToJavaTransformer;
    private final RelationToJavaTransformer relationToJavaTransformer;

    private final String javaRootPackage;
    private final boolean onePackage;
    private final boolean caching;
    private final Integer cachingThreshold;
    private final boolean singletonInputData;

    private final LazyEvaluationOptimisation lazyEvaluationOptimisation;
    private final Set<String> cachedElements;
    protected final DRGElementFilter drgElementFilter;

    protected final FEELTypeMemoizer feelTypeMemoizer;
    protected final JavaTypeMemoizer javaTypeMemoizer;
    protected final EnvironmentMemoizer environmentMemoizer;

    public BasicDMN2JavaTransformer(DMNModelRepository dmnModelRepository, EnvironmentFactory environmentFactory, NativeTypeFactory feelTypeTranslator, LazyEvaluationDetector lazyEvaluationDetector, Map<String, String> inputParameters) {
        this.dmnModelRepository = dmnModelRepository;
        this.environmentFactory = environmentFactory;
        this.typeFactory = feelTypeTranslator;
        setExpressionFactory();

        this.feelTranslator = new FEELTranslatorImpl(this);
        this.contextToJavaTransformer = new ContextToJavaTransformer(this);
        this.decisionTableToJavaTransformer = new DecisionTableToJavaTransformer(this);
        this.functionDefinitionToJavaTransformer = new FunctionDefinitionToJavaTransformer(this);
        this.invocationToJavaTransformer = new InvocationToJavaTransformer(this);
        this.literalExpressionToJavaTransformer = new LiteralExpressionToJavaTransformer(this);
        this.relationToJavaTransformer = new RelationToJavaTransformer(this);

        this.javaRootPackage = InputParamUtil.getOptionalParam(inputParameters, "javaRootPackage");
        boolean onePackageDefault = dmnModelRepository.getAllDefinitions().size() == 1;
        this.onePackage = InputParamUtil.getOptionalBooleanParam(inputParameters, "onePackage", "" + onePackageDefault);
        this.caching = InputParamUtil.getOptionalBooleanParam(inputParameters, "caching");
        String cachingThresholdParam = InputParamUtil.getOptionalParam(inputParameters, "cachingThreshold", "1");
        this.cachingThreshold = Integer.parseInt(cachingThresholdParam);
        this.singletonInputData = InputParamUtil.getOptionalBooleanParam(inputParameters, "singletonInputData", "true");

        this.lazyEvaluationOptimisation = lazyEvaluationDetector.detect(this.dmnModelRepository);
        this.cachedElements = this.dmnModelRepository.computeCachedElements(this.caching, this.cachingThreshold);
        this.drgElementFilter = new DRGElementFilter(this.singletonInputData);

        this.feelTypeMemoizer = new FEELTypeMemoizer();
        this.javaTypeMemoizer = new JavaTypeMemoizer();
        this.environmentMemoizer = new EnvironmentMemoizer();
    }

    protected void setExpressionFactory() {
        this.expressionFactory = new JavaExpressionFactory(this);
    }

    public DMNModelRepository getDMNModelRepository() {
        return this.dmnModelRepository;
    }

    public EnvironmentFactory getEnvironmentFactory() {
        return this.environmentFactory;
    }

    public NativeTypeFactory getFEELTypeTranslator() {
        return this.typeFactory;
    }

    public FEELTranslator getFEELTranslator() {
        return this.feelTranslator;
    }

    public NativeTypeFactory getTypeFactory() {
        return this.typeFactory;
    }

    public NativeExpressionFactory getExpressionFactory() {
        return this.expressionFactory;
    }

    public boolean isList(TDRGElement element) {
        Type feelType = drgElementOutputFEELType(element);
        return feelType instanceof ListType;
    }

    public boolean isOnePackage() {
        return this.onePackage;
    }

    public boolean isSingletonInputData() {
        return this.singletonInputData;
    }

    //
    // TItemDefinition related functions
    //
    public boolean isComplexType(TItemDefinition itemDefinition) {
        Type type = toFEELType(itemDefinition);
        return type instanceof ItemDefinitionType
                || type instanceof ListType && ((ListType) type).getElementType() instanceof ItemDefinitionType;
    }

    public String itemDefinitionJavaSimpleInterfaceName(TItemDefinition itemDefinition) {
        Type type = toFEELType(itemDefinition);
        // ItemDefinition can be a complex type with isCollection = true
        if (type instanceof ListType && ((ListType) type).getElementType() instanceof ItemDefinitionType) {
            type = ((ListType) type).getElementType();
        }
        if (type instanceof ItemDefinitionType) {
            return upperCaseFirst(((ItemDefinitionType) type).getName());
        } else {
            throw new IllegalArgumentException(String.format("Unexpected type '%s' for ItemDefinition '%s'", type, itemDefinition.getName()));
        }
    }

    public String itemDefinitionJavaSimpleInterfaceName(String className) {
        return className.substring(0, className.length() - "Impl".length());
    }

    public String itemDefinitionJavaClassName(String interfaceName) {
        return interfaceName + "Impl";
    }

    public String itemDefinitionJavaQualifiedInterfaceName(TItemDefinition itemDefinition) {
        Type type = toFEELType(itemDefinition);
        return toJavaType(type);
    }

    public String itemDefinitionVariableName(TItemDefinition itemDefinition) {
        String name = itemDefinition.getName();
        if (StringUtils.isBlank(name)) {
            throw new DMNRuntimeException(String.format("Variable name cannot be null. ItemDefinition id '%s'", itemDefinition.getId()));
        }
        return lowerCaseFirst(name);
    }

    public String itemDefinitionSignature(TItemDefinition itemDefinition) {
        List<Pair<String, String>> parameters = new ArrayList<>();
        List<TItemDefinition> itemComponents = itemDefinition.getItemComponent();
        this.dmnModelRepository.sortNamedElements(itemComponents);
        for (TItemDefinition child : itemComponents) {
            parameters.add(new Pair<>(itemDefinitionVariableName(child), itemDefinitionJavaQualifiedInterfaceName(child)));
        }
        return parameters.stream().map(p -> this.expressionFactory.nullableParameter(p.getRight(), p.getLeft())).collect(Collectors.joining(", "));
    }

    public String getter(TItemDefinition itemDefinition) {
        return getter(itemDefinitionVariableName(itemDefinition));
    }

    public String setter(TItemDefinition itemDefinition) {
        return setter(itemDefinitionVariableName(itemDefinition));
    }

    //
    // TInformationItem related functions
    //
    public String informationItemTypeName(TBusinessKnowledgeModel bkm, TInformationItem element) {
        TDefinitions model = this.dmnModelRepository.getModel(bkm);
        Type type = toFEELType(model, QualifiedName.toQualifiedName(model, element.getTypeRef()));
        return toJavaType(type);
    }

    public String informationItemVariableName(TInformationItem element) {
        String name = element.getName();
        return lowerCaseFirst(name);
    }

    public String parameterVariableName(TInformationItem element) {
        String name = element.getName();
        if (name == null) {
            throw new DMNRuntimeException(String.format("Parameter name cannot be null. Parameter id '%s'", element.getId()));
        }
        return lowerCaseFirst(name);
    }

    public String defaultConstructor(String className) {
        return this.expressionFactory.constructor(className, "");
    }

    public String constructor(String className, String arguments) {
        return this.expressionFactory.constructor(className, arguments);
    }

    //
    // DRGElement related functions
    //
    public String drgElementClassName(TDRGElement element) {
        return upperCaseFirst(element.getName());
    }

    public String drgElementVariableName(DRGElementReference<? extends TDRGElement> reference) {
        String name = reference.getElementName();
        if (name == null) {
            throw new DMNRuntimeException(String.format("Variable name cannot be null. Decision id '%s'", reference.getElement().getId()));
        }
        return drgReferenceQualifiedName(reference);
    }

    public String drgElementVariableName(TDRGElement element) {
        String name = element.getName();
        if (name == null) {
            throw new DMNRuntimeException(String.format("Variable name cannot be null. Decision id '%s'", element.getId()));
        }
        return lowerCaseFirst(name);
    }

    public String drgElementOutputType(DRGElementReference<? extends TDRGElement> reference) {
        return drgElementOutputType(reference.getElement());
    }

    public String drgElementOutputType(TDRGElement element) {
        Type type = drgElementOutputFEELType(element);
        return toJavaType(type);
    }

    public String drgElementOutputClassName(TDRGElement element) {
        Type type = drgElementOutputFEELType(element);
        if (type instanceof ListType) {
            type = ((ListType) type).getElementType();
        }
        return toJavaType(type);
    }

    public Type drgElementOutputFEELType(TDRGElement element) {
        return drgElementOutputFEELType(element, makeEnvironment(element));
    }

    public Type drgElementOutputFEELType(TDRGElement element, Environment environment) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        QualifiedName typeRef = this.dmnModelRepository.typeRef(model, element);
        if (typeRef != null) {
            return toFEELType(model, typeRef);
        } else {
            // Infer type from body
            return inferDRGElementOutputFEELType(element, environment);
        }
    }

    public Type drgElementVariableFEELType(TDRGElement element) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        QualifiedName typeRef = this.dmnModelRepository.typeRef(model, element);
        Type type = typeRef == null ? null : toFEELType(model, typeRef);
        if (type == null || !type.isValid()) {
            // Infer type from body
            Environment environment = makeEnvironment(element);
            return inferDRGElementVariableFEELType(element, environment);
        }
        return type;
    }

    public Type drgElementVariableFEELType(TDRGElement element, Environment environment) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        QualifiedName typeRef = this.dmnModelRepository.typeRef(model, element);
        Type type = typeRef == null ? null : toFEELType(model, typeRef);
        if (type == null || !type.isValid()) {
            // Infer type from body
            return inferDRGElementVariableFEELType(element, environment);
        }
        return type;
    }

    private Type inferDRGElementOutputFEELType(TDRGElement element, Environment environment) {
        if (element instanceof TDecision) {
            return expressionType(element, ((TDecision) element).getExpression(), environment);
        } else if (element instanceof TBusinessKnowledgeModel) {
            Type type = expressionType(element, ((TBusinessKnowledgeModel) element).getEncapsulatedLogic(), environment);
            return ((FunctionType)type).getReturnType();
        } else if (element instanceof TDecisionService) {
            return makeDSOutputType((TDecisionService) element);
        }
        throw new DMNRuntimeException(String.format("Cannot infer the output type of '%s'", element.getName()));
    }

    private Type inferDRGElementVariableFEELType(TDRGElement element, Environment environment) {
        if (element instanceof TDecision) {
            return expressionType(element, ((TDecision) element).getExpression(), environment);
        } else if (element instanceof TBusinessKnowledgeModel) {
            return expressionType(element, ((TBusinessKnowledgeModel) element).getEncapsulatedLogic(), environment);
        } else if (element instanceof TDecisionService) {
            return makeDSType((TDecisionService) element);
        }
        throw new DMNRuntimeException(String.format("Cannot infer the output type of '%s'", element.getName()));
    }

    public String annotation(TDRGElement element, String description) {
        if (StringUtils.isBlank(description)) {
            return "\"\"";
        }
        try {
            Environment environment = makeEnvironment(element);
            Statement statement = this.literalExpressionToJavaTransformer.literalExpressionToJava(element, description, environment);
            return ((ExpressionStatement)statement).getExpression();
        } catch (Exception e) {
            LOGGER.warn(String.format("Cannot process description '%s' for element '%s'", description, element == null ? "" : element.getName()));
            return String.format("\"%s\"", description.replaceAll("\"", "\\\\\""));
        }
    }

    public String drgElementSignature(TDRGElement element) {
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return drgElementSignature(reference);
    }

    public String drgElementSignature(DRGElementReference<? extends TDRGElement> reference) {
        TDRGElement element = reference.getElement();
        if (element instanceof TBusinessKnowledgeModel) {
            List<Pair<String, String>> parameters = bkmParameters((DRGElementReference<TBusinessKnowledgeModel>) reference);
            String signature = parameters.stream().map(p -> this.expressionFactory.nullableParameter(p.getRight(), p.getLeft())).collect(Collectors.joining(", "));
            return augmentSignature(signature);
        } else if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure((DRGElementReference<TDecision>) reference);
            String decisionSignature = parameters.stream().map(p -> this.expressionFactory.nullableParameter(toJavaType(p.getRight()), p.getLeft())).collect(Collectors.joining(", "));
            return augmentSignature(decisionSignature);
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    public String drgElementArgumentList(TDRGElement element) {
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return drgElementArgumentList(reference);
    }

    public String drgElementArgumentList(DRGElementReference<? extends TDRGElement> reference) {
        TDRGElement element = reference.getElement();
        if (element instanceof TBusinessKnowledgeModel) {
            List<Pair<String, String>> parameters = bkmParameters((DRGElementReference<TBusinessKnowledgeModel>) reference);
            String arguments = parameters.stream().map(p -> String.format("%s", p.getLeft())).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure((DRGElementReference<TDecision>) reference);
            String arguments = parameters.stream().map(p -> String.format("%s", p.getLeft())).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    public String drgElementConvertedArgumentList(TDRGElement element) {
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return drgElementConvertedArgumentList(reference);
    }

    public String drgElementConvertedArgumentList(DRGElementReference<? extends TDRGElement> reference) {
        TDRGElement element = reference.getElement();
        if (element instanceof TBusinessKnowledgeModel) {
            List<Pair<String, String>> parameters = bkmParameters((DRGElementReference<TBusinessKnowledgeModel>) reference);
            String arguments = parameters.stream().map(p -> String.format("%s", p.getLeft())).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure((DRGElementReference<TDecision>) reference);
            String arguments = parameters.stream().map(p -> String.format("%s", convertDecisionArgument(p.getLeft(), p.getRight()))).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    public List<String> drgElementArgumentNameList(TDRGElement element) {
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return drgElementArgumentNameList(reference);
    }

    public List<String> drgElementArgumentNameList(DRGElementReference<? extends TDRGElement> reference) {
        return drgElementArgumentNameList(reference, true);
    }

    public List<String> drgElementArgumentNameList(DRGElementReference<? extends TDRGElement> reference, boolean javaFriendlyName) {
        TDRGElement element = reference.getElement();
        if (element instanceof TBusinessKnowledgeModel) {
            List<Pair<String, String>> parameters = bkmParameters((DRGElementReference<TBusinessKnowledgeModel>) reference, javaFriendlyName);
            return parameters.stream().map(Pair::getLeft).collect(Collectors.toList());
        } else if (element instanceof TDecisionService) {
            List<Pair<String, Type>> parameters = dsParameters((DRGElementReference<TDecisionService>) reference, javaFriendlyName);
            return parameters.stream().map(Pair::getLeft).collect(Collectors.toList());
        } else if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure((DRGElementReference<TDecision>) reference, javaFriendlyName);
            return parameters.stream().map(Pair::getLeft).collect(Collectors.toList());
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    public boolean shouldGenerateApplyWithConversionFromString(TDRGElement element) {
        if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure(this.dmnModelRepository.makeDRGElementReference((TDecision) element));
            return parameters.stream().anyMatch(p -> p.getRight() != StringType.STRING);
        } else if (element instanceof TBusinessKnowledgeModel) {
            return false;
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    public String drgElementSignatureExtraCacheWithConversionFromString(TDRGElement element) {
        return drgElementSignatureExtraCache(drgElementSignatureExtraWithConversionFromString(element));
    }

    public String drgElementSignatureExtraWithConversionFromString(TDRGElement element) {
        return drgElementSignatureExtra(drgElementSignatureWithConversionFromString(element));
    }

    public String drgElementSignatureWithConversionFromString(TDRGElement element) {
        if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure(this.dmnModelRepository.makeDRGElementReference((TDecision) element));
            String decisionSignature = parameters.stream().map(p -> this.expressionFactory.nullableParameter(toStringJavaType(p.getRight()), p.getLeft())).collect(Collectors.joining(", "));
            return augmentSignature(decisionSignature);
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    public String drgElementArgumentsExtraCacheWithConversionFromString(TDRGElement element) {
        String arguments = drgElementArgumentsExtraWithConversionFromString(element);
        return drgElementArgumentsExtraCache(arguments);
    }

    private String drgElementArgumentsExtraWithConversionFromString(TDRGElement element) {
        String arguments = drgElementArgumentListWithConversionFromString(element);
        return drgElementArgumentsExtra(arguments);
    }

    public String drgElementDefaultArgumentsExtraCacheWithConversionFromString(TDRGElement element) {
        String arguments = drgElementDefaultArgumentsExtraWithConversionFromString(element);
        return drgElementDefaultArgumentsExtraCache(arguments);
    }

    private String drgElementDefaultArgumentsExtraWithConversionFromString(TDRGElement element) {
        String arguments = drgElementArgumentListWithConversionFromString(element);
        return drgElementDefaultArgumentsExtra(arguments);
    }

    public String drgElementArgumentListWithConversionFromString(TDRGElement element) {
        if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure(this.dmnModelRepository.makeDRGElementReference((TDecision) element));
            String arguments = parameters.stream().map(p -> String.format("%s", this.expressionFactory.convertDecisionArgumentFromString(p.getLeft(), p.getRight()))).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    public String decisionConstructorSignature(TDecision decision) {
        List<DRGElementReference<TDecision>> subDecisionReferences = this.dmnModelRepository.directSubDecisions(decision);
        this.dmnModelRepository.sortNamedElementReferences(subDecisionReferences);
        return subDecisionReferences.stream().map(d -> this.expressionFactory.decisionConstructorParameter(d)).collect(Collectors.joining(", "));
    }

    public String decisionConstructorNewArgumentList(TDecision decision) {
        List<DRGElementReference<TDecision>> subDecisionReferences = this.dmnModelRepository.directSubDecisions(decision);
        this.dmnModelRepository.sortNamedElementReferences(subDecisionReferences);
        return subDecisionReferences
                .stream()
                .map(d -> String.format("%s", defaultConstructor(qualifiedName(d))))
                .collect(Collectors.joining(", "));
    }

    public boolean hasDirectSubDecisions(TDecision decision) {
        return !this.dmnModelRepository.directSubDecisions(decision).isEmpty();
    }

    //
    // Evaluate method related functions
    //
    public String drgElementEvaluateSignature(TDRGElement element) {
        return drgElementSignatureExtra(drgElementDirectSignature(element));
    }

    public String drgElementEvaluateArgumentList(TDRGElement element) {
        return drgElementArgumentsExtra(drgElementDirectArgumentList(element));
    }

    protected String drgElementDirectSignature(TDRGElement element) {
        if (element instanceof TDecision) {
            List<Pair<String, String>> parameters = directInformationRequirementParameters(element);
            String javaParameters = parameters.stream().map(p -> this.expressionFactory.nullableParameter(p.getRight(), p.getLeft())).collect(Collectors.joining(", "));
            return augmentSignature(javaParameters);
        } else if (element instanceof TBusinessKnowledgeModel) {
            return drgElementSignature(this.dmnModelRepository.makeDRGElementReference(element));
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    protected String drgElementDirectArgumentList(TDRGElement element) {
        if (element instanceof TDecision) {
            List<Pair<String, String>> parameters = directInformationRequirementParameters(element);
            String argumentList = parameters.stream().map(p -> String.format("%s", p.getLeft())).collect(Collectors.joining(", "));
            return augmentArgumentList(argumentList);
        } else if (element instanceof TBusinessKnowledgeModel) {
            return drgElementArgumentList(this.dmnModelRepository.makeDRGElementReference(element));
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    private List<Pair<String, String>> directInformationRequirementParameters(TDRGElement element) {
        List<DRGElementReference<? extends TDRGElement>> inputs = directInformationRequirements(element);
        this.dmnModelRepository.sortNamedElementReferences(inputs);

        List<Pair<String, String>> parameters = new ArrayList<>();
        for (DRGElementReference<? extends TDRGElement> reference : inputs) {
            TDRGElement input = reference.getElement();
            if (input instanceof TInputData) {
                TInputData inputData = (TInputData) input;
                String parameterName = inputDataVariableName(reference);
                String parameterJavaType = inputDataType(inputData);
                parameters.add(new Pair<>(parameterName, parameterJavaType));
            } else if (input instanceof TDecision) {
                TDecision subDecision = (TDecision) input;
                String parameterName = drgElementVariableName(reference);
                String parameterJavaType = drgElementOutputType(subDecision);
                parameters.add(new Pair<>(parameterName, lazyEvaluationType(input, parameterJavaType)));
            } else {
                throw new UnsupportedOperationException(String.format("'%s' is not supported yet", input.getClass().getSimpleName()));
            }
        }
        return parameters;
    }

    protected List<DRGElementReference<? extends TDRGElement>> directInformationRequirements(TDRGElement element) {
        List<DRGElementReference<TInputData>> directInputReferences = this.dmnModelRepository.directInputDatas(element);
        List<DRGElementReference<TDecision>> directSubDecisionsReferences = this.dmnModelRepository.directSubDecisions(element);

        List<DRGElementReference<? extends TDRGElement>> inputs = new ArrayList<>();
        inputs.addAll(directInputReferences);
        inputs.addAll(directSubDecisionsReferences);
        return inputs;
    }

    //
    // Comment related functions
    //
    public String startElementCommentText(TDRGElement element) {
        if (element instanceof TDecision) {
            return String.format("Start decision '%s'", element.getName());
        } else if (element instanceof TBusinessKnowledgeModel) {
            return String.format("Start BKM '%s'", element.getName());
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    public String endElementCommentText(TDRGElement element) {
        if (element instanceof TDecision) {
            return String.format("End decision '%s'", element.getName());
        } else if (element instanceof TBusinessKnowledgeModel) {
            return String.format("End BKM '%s'", element.getName());
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    public String evaluateElementCommentText(TDRGElement element) {
        if (element instanceof TDecision) {
            return String.format("Evaluate decision '%s'", element.getName());
        } else if (element instanceof TBusinessKnowledgeModel) {
            return String.format("Evaluate BKM '%s'", element.getName());
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    public QualifiedName drgElementOutputTypeRef(TDRGElement element) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        if (element instanceof TBusinessKnowledgeModel) {
            QualifiedName typeRef = this.dmnModelRepository.typeRef(model, element);
            if (typeRef == null) {
                throw new DMNRuntimeException(String.format("Cannot infer return type for BKM '%s'", element.getName()));
            }
            return typeRef;
        } else {
            TInformationItem variable = this.dmnModelRepository.variable(element);
            if (variable != null) {
                return QualifiedName.toQualifiedName(model, variable.getTypeRef());
            } else {
                throw new DMNRuntimeException(String.format("Missing variable in element '%s'", element.getClass().getSimpleName()));
            }
        }
    }

    //
    // InputData related functions
    //
    public String inputDataVariableName(DRGElementReference<? extends TDRGElement> reference) {
        String name = reference.getElementName();
        if (name == null) {
            throw new DMNRuntimeException(String.format("Variable name cannot be null. InputData id '%s'", reference.getElement().getId()));
        }
        return drgReferenceQualifiedName(reference);
    }

    public String inputDataVariableName(TInputData inputData) {
        String name = inputData.getName();
        if (name == null) {
            throw new DMNRuntimeException(String.format("Variable name cannot be null. InputData id '%s'", inputData.getId()));
        }
        String modelName = this.dmnModelRepository.getModelName(inputData);
        return drgReferenceQualifiedName(new ImportPath(), modelName, name);
    }

    public Type toFEELType(TInputData inputData) {
        TDefinitions model = this.dmnModelRepository.getModel(inputData);
        String typeRefString = inputData.getVariable().getTypeRef();
        QualifiedName typeRef = QualifiedName.toQualifiedName(model, typeRefString);
        return toFEELType(model, typeRef);
    }

    private String inputDataType(TInputData inputData) {
        Type type = toFEELType(inputData);
        return toJavaType(type);
    }

    //
    // Invocable  related functions
    //
    public List<FormalParameter> invFEELParameters(TDRGElement invocable) {
        if (invocable instanceof TBusinessKnowledgeModel) {
            return bkmFEELParameters((TBusinessKnowledgeModel) invocable);
        } else if (invocable instanceof TDecisionService) {
            return dsFEELParameters((TDecisionService) invocable);
        } else {
            throw new DMNRuntimeException(String.format("Illegal invocable '%s'", invocable.getClass().getSimpleName()));
        }
    }

    //
    // BKM related functions
    //
    public String bkmFunctionName(DRGElementReference<? extends TDRGElement> reference) {
        return bkmFunctionName((TBusinessKnowledgeModel) reference.getElement());
    }

    public String bkmFunctionName(TBusinessKnowledgeModel bkm) {
        String name = bkm.getName();
        return bkmFunctionName(name);
    }

    public String bkmFunctionName(String name) {
        return javaFriendlyName(name);
    }

    public String bkmQualifiedFunctionName(TBusinessKnowledgeModel bkm) {
        String javaPackageName = qualifiedName(bkm);
        String javaFunctionName = bkmFunctionName(bkm);
        return qualifiedName(javaPackageName, javaFunctionName);
    }

    protected List<FormalParameter> bkmFEELParameters(TBusinessKnowledgeModel bkm) {
        TDefinitions model = this.dmnModelRepository.getModel(bkm);
        List<FormalParameter> parameters = new ArrayList<>();
        bkm.getEncapsulatedLogic().getFormalParameter().forEach(p -> parameters.add(new FormalParameter(p.getName(), toFEELType(model, QualifiedName.toQualifiedName(model, p.getTypeRef())))));
        return parameters;
    }

    public List<String> bkmFEELParameterNames(TBusinessKnowledgeModel bkm) {
        return bkmFEELParameters(bkm).stream().map(FormalParameter::getName).collect(Collectors.toList());
    }

    protected List<Pair<String, String>> bkmParameters(DRGElementReference<TBusinessKnowledgeModel> reference) {
        return bkmParameters(reference, true);
    }

    protected List<Pair<String, String>> bkmParameters(DRGElementReference<TBusinessKnowledgeModel> reference, boolean javaFriendlyName) {
        List<Pair<String, String>> parameters = new ArrayList<>();
        TBusinessKnowledgeModel bkm = reference.getElement();
        TFunctionDefinition encapsulatedLogic = bkm.getEncapsulatedLogic();
        List<TInformationItem> formalParameters = encapsulatedLogic.getFormalParameter();
        for (TInformationItem parameter : formalParameters) {
            String parameterName = javaFriendlyName ? informationItemVariableName(parameter) : parameter.getName();
            String parameterType = informationItemTypeName(bkm, parameter);
            parameters.add(new Pair<>(parameterName, parameterType));
        }
        return parameters;
    }

    //
    // Decision Service related functions
    //
    public String dsFunctionName(TDecisionService service) {
        String name = service.getName();
        return dsFunctionName(name);
    }

    public String dsFunctionName(String name) {
        return javaFriendlyName(name);
    }

    public List<FormalParameter> dsFEELParameters(TDecisionService service) {
        List<FormalParameter> parameters = new ArrayList<>();
        for (TDMNElementReference er: service.getInputData()) {
            TInputData inputData = getDMNModelRepository().findInputDataByRef(service, er.getHref());
            parameters.add(new FormalParameter(inputData.getName(), toFEELType(inputData)));
        }
        for (TDMNElementReference er: service.getInputDecision()) {
            TDecision decision = getDMNModelRepository().findDecisionByRef(service, er.getHref());
            parameters.add(new FormalParameter(decision.getName(), drgElementOutputFEELType(decision)));
        }
        return parameters;
    }

    public List<String> dsFEELParameterNames(TDecisionService service) {
        return dsFEELParameters(service).stream().map(FormalParameter::getName).collect(Collectors.toList());
    }

    private List<Pair<String, Type>> dsParameters(DRGElementReference<TDecisionService> reference) {
        return dsParameters(reference, true);
    }

    private List<Pair<String, Type>> dsParameters(DRGElementReference<TDecisionService> reference, boolean javaFriendlyName) {
        TDecisionService service = reference.getElement();
        List<Pair<String, Type>> parameters = new ArrayList<>();
        for (TDMNElementReference er: service.getInputData()) {
            TInputData inputData = this.dmnModelRepository.findInputDataByRef(service, er.getHref());
            String importName = this.dmnModelRepository.findImportName(service, er);
            String parameterName = javaFriendlyName ? drgElementVariableName(this.dmnModelRepository.makeDRGElementReference(importName, inputData)) : inputData.getName();
            Type parameterType = toFEELType(inputData);
            parameters.add(new Pair<>(parameterName, parameterType));
        }
        for (TDMNElementReference er: service.getInputDecision()) {
            TDecision decision = this.dmnModelRepository.findDecisionByRef(service, er.getHref());
            String importName = this.dmnModelRepository.findImportName(service, er);
            String parameterName = javaFriendlyName ? drgElementVariableName(this.dmnModelRepository.makeDRGElementReference(importName, decision)) : decision.getName();
            Type parameterType = drgElementOutputFEELType(decision);
            parameters.add(new Pair<>(parameterName, parameterType));
        }
        return parameters;
    }

    //
    // Decision related functions
    //
    protected String convertDecisionArgument(String paramName, Type type) {
        if (type instanceof ItemDefinitionType) {
            return this.expressionFactory.convertToItemDefinitionType(paramName, (ItemDefinitionType) type);
        } else {
            return paramName;
        }
    }

    protected Statement convertExpression(Statement statement, Type expectedType) {
        if (!(statement instanceof ExpressionStatement)) {
            return statement;
        }
        String javaExpression = ((ExpressionStatement)statement).getExpression();
        Type expressionType = ((ExpressionStatement)statement).getExpressionType();
        if ("null".equals(javaExpression)) {
            return new ExpressionStatement(javaExpression, expectedType);
        }
        if (expectedType instanceof ListType && expressionType instanceof ListType) {
            Type expectedElementType = ((ListType) expectedType).getElementType();
            Type expressionElementType = ((ListType) expressionType).getElementType();
            if (expectedElementType instanceof ItemDefinitionType) {
                if (expressionElementType.conformsTo(expectedElementType) || expressionElementType == AnyType.ANY || expressionElementType instanceof ContextType) {
                    String conversionText = this.expressionFactory.makeListConversion(javaExpression, (ItemDefinitionType) expectedElementType);
                    return new ExpressionStatement(conversionText, expectedType);
                }
            }
        } else if (expectedType instanceof ListType) {
            return new ExpressionStatement(this.expressionFactory.convertElementToList(javaExpression, expectedType), expectedType);
        } else if (expressionType instanceof ListType) {
            return new ExpressionStatement(this.expressionFactory.convertListToElement(javaExpression, expectedType), expectedType);
        } else if (expectedType instanceof ItemDefinitionType) {
            if (expressionType.conformsTo(expectedType) || expressionType == AnyType.ANY || expressionType instanceof ContextType) {
                return new ExpressionStatement(this.expressionFactory.convertToItemDefinitionType(javaExpression, (ItemDefinitionType) expectedType), expectedType);
            }
        }
        return statement;
    }

    public String convertMethodName(TItemDefinition itemDefinition) {
        return this.expressionFactory.convertMethodName(itemDefinition);
    }

    protected String augmentSignature(String signature) {
        String annotationParameter = this.expressionFactory.parameter(annotationSetClassName(), annotationSetVariableName());
        if (StringUtils.isBlank(signature)) {
            return annotationParameter;
        } else {
            return String.format("%s, %s", signature, annotationParameter);
        }
    }

    public String augmentArgumentList(String arguments) {
        String extra = annotationSetVariableName();
        if (StringUtils.isBlank(arguments)) {
            return extra;
        } else {
            return String.format("%s, %s", arguments, extra);
        }
    }

    public List<Pair<String, Type>> inputDataParametersClosure(DRGElementReference<TDecision> reference) {
        return inputDataParametersClosure(reference, true);
    }

    public List<Pair<String, Type>> inputDataParametersClosure(DRGElementReference<TDecision> reference, boolean javaFriendlyName) {
        List<DRGElementReference<TInputData>> allInputDataReferences = this.dmnModelRepository.allInputDatas(reference, this.drgElementFilter);
        this.dmnModelRepository.sortNamedElementReferences(allInputDataReferences);

        List<Pair<String, Type>> parameters = new ArrayList<>();
        for (DRGElementReference<TInputData> inputData : allInputDataReferences) {
            String parameterName = javaFriendlyName ? inputDataVariableName(inputData) : inputData.getElementName();
            Type parameterType = toFEELType(inputData.getElement());
            parameters.add(new Pair<>(parameterName, parameterType));
        }
        return parameters;
    }

    public String drgReferenceQualifiedName(DRGElementReference<? extends TDRGElement> reference) {
        return drgReferenceQualifiedName(reference.getImportPath(), reference.getModelName(), reference.getElementName());
    }

    private String drgReferenceQualifiedName(ImportPath importPath, String modelName, String elementName) {
        Pair<List<String>, String> qName = qualifiedName(importPath, modelName, elementName);

        String javaPrefix = qName.getLeft().stream().map(this::javaModelName).collect(Collectors.joining("_"));
        String javaName = lowerCaseFirst(qName.getRight());
        if (StringUtils.isBlank(javaPrefix)) {
            return javaName;
        } else {
            return String.format("%s_%s", javaPrefix, javaName);
        }
    }

    public String bindingName(DRGElementReference<? extends TDRGElement> reference) {
        Pair<List<String>, String> qName = qualifiedName(reference.getImportPath(), reference.getModelName(), reference.getElementName());

        String prefix = String.join(".", qName.getLeft());
        if (StringUtils.isBlank(prefix)) {
            return qName.getRight();
        } else {
            return String.format("%s.%s", prefix, qName.getRight());
        }
    }

    private Pair<List<String>, String> qualifiedName(ImportPath importPath, String modelName, String elementName) {
        if (this.onePackage) {
            return new Pair<>(Collections.emptyList(), elementName);
        } else if (this.singletonInputData) {
            if (ImportPath.isEmpty(importPath)) {
                modelName = "";
            }
            return new Pair<>(Collections.singletonList(modelName), elementName);
        } else {
            return new Pair<>(importPath.getPathElements(), elementName);
        }
    }

    public String parameterJavaType(TDefinitions model, TInformationItem element) {
        return parameterType(model, element);
    }

    public String parameterJavaType(TDRGElement element) {
        String parameterJavaType;
        if (element instanceof TInputData) {
            parameterJavaType = inputDataType((TInputData) element);
        } else if (element instanceof TDecision) {
            parameterJavaType = drgElementOutputType(element);
        } else {
            throw new UnsupportedOperationException(String.format("'%s' is not supported", element.getClass().getSimpleName()));
        }
        return parameterJavaType;
    }

    private String parameterType(TDefinitions model, TInformationItem element) {
        QualifiedName typeRef = this.dmnModelRepository.typeRef(model, element);
        if (typeRef == null) {
            throw new IllegalArgumentException(String.format("Cannot resolve typeRef for element '%s'", element.getName()));
        }
        Type type = toFEELType(model, typeRef);
        return toJavaType(type);
    }

    public boolean isLazyEvaluated(DRGElementReference<? extends TDRGElement> reference) {
        return this.isLazyEvaluated(reference.getElement());
    }

    public boolean isLazyEvaluated(TDRGElement element) {
        return this.isLazyEvaluated(element.getName());
    }

    public boolean isLazyEvaluated(String name) {
        return this.lazyEvaluationOptimisation.isLazyEvaluated(name);
    }

    protected String lazyEvaluationType(TDRGElement input, String inputJavaType) {
        return isLazyEvaluated(input) ? String.format("%s<%s>", lazyEvalClassName(), inputJavaType) : inputJavaType;
    }

    public String lazyEvaluation(String elementName, String javaName) {
        return isLazyEvaluated(elementName) ? String.format("%s.getOrCompute()", javaName) : javaName;
    }

    public String pairClassName() {
        return Pair.class.getName();
    }

    public String pairComparatorClassName() {
        return PairComparator.class.getName();
    }

    public String argumentsClassName() {
        return Arguments.class.getName();
    }

    public String dmnTypeClassName() {
        return DMNType.class.getName();
    }

    public String dmnRuntimeExceptionClassName() {
        return DMNRuntimeException.class.getName();
    }

    public String lazyEvalClassName() {
        return LazyEval.class.getName();
    }

    public String contextClassName() {
        return Context.class.getName();
    }

    public String annotationSetClassName() {
        return AnnotationSet.class.getName();
    }

    public String annotationSetVariableName() {
        return "annotationSet_";
    }

    public String eventListenerClassName() {
        return EventListener.class.getName();
    }

    public String eventListenerVariableName() {
        return "eventListener_";
    }

    public String defaultEventListenerClassName() {
        return NopEventListener.class.getName();
    }

    public String loggingEventListenerClassName() {
        return LoggingEventListener.class.getName();
    }

    public String externalExecutorClassName() {
        return ExternalFunctionExecutor.class.getName();
    }

    public String externalExecutorVariableName() {
        return "externalExecutor_";
    }

    public String defaultExternalExecutorClassName() {
        return DefaultExternalFunctionExecutor.class.getName();
    }

    public String cacheInterfaceName() {
        return Cache.class.getName();
    }

    public String cacheVariableName() {
        return "cache_";
    }

    public String defaultCacheClassName() {
        return DefaultCache.class.getName();
    }

    public String drgElementSignatureExtra(DRGElementReference<? extends TDRGElement> reference) {
        String signature = drgElementSignature(reference);
        return drgElementSignatureExtra(signature);
    }

    public String drgElementSignatureExtra(TDRGElement element) {
        String signature = drgElementSignature(element);
        return drgElementSignatureExtra(signature);
    }

    public String drgElementSignatureExtra(String signature) {
        String listenerParameter = this.expressionFactory.parameter(eventListenerClassName(), eventListenerVariableName());
        String executorParameter = this.expressionFactory.parameter(externalExecutorClassName(), externalExecutorVariableName());
        if (StringUtils.isBlank(signature)) {
            return String.format("%s, %s", listenerParameter, executorParameter);
        } else {
            return String.format("%s, %s, %s", signature, listenerParameter, executorParameter);
        }
    }

    public String drgElementArgumentsExtra(DRGElementReference<? extends TDRGElement> reference) {
        String arguments = drgElementArgumentList(reference);
        return drgElementArgumentsExtra(arguments);
    }

    public String drgElementArgumentsExtra(TDRGElement element) {
        String arguments = drgElementArgumentList(element);
        return drgElementArgumentsExtra(arguments);
    }

    public String drgElementArgumentsExtra(String arguments) {
        if (StringUtils.isBlank(arguments)) {
            return String.format("%s, %s", eventListenerVariableName(), externalExecutorVariableName());
        } else {
            return String.format("%s, %s, %s", arguments, eventListenerVariableName(), externalExecutorVariableName());
        }
    }

    public String drgElementDefaultArgumentsExtra(String arguments) {
        String loggerArgument = constructor(loggingEventListenerClassName(), "LOGGER");
        String executorArgument = defaultConstructor(defaultExternalExecutorClassName());
        if (StringUtils.isBlank(arguments)) {
            return String.format("%s, %s", loggerArgument, executorArgument);
        } else {
            return String.format("%s, %s, %s", arguments, loggerArgument, executorArgument);
        }
    }

    public boolean isCaching() {
        return this.caching;
    }

    public boolean isCached(String elementName) {
        if (!this.caching) {
            return false;
        }
        return this.cachedElements.contains(elementName);
    }

    public String drgElementSignatureExtraCache(DRGElementReference<? extends TDRGElement> reference) {
        String signature = drgElementSignatureExtra(reference);
        return drgElementSignatureExtraCache(signature);
    }

    public String drgElementSignatureExtraCache(TDRGElement element) {
        String signature = drgElementSignatureExtra(element);
        return drgElementSignatureExtraCache(signature);
    }

    public String drgElementSignatureExtraCache(String signature) {
        if (!this.caching) {
            return signature;
        }

        String cacheParameter = this.expressionFactory.parameter(cacheInterfaceName(), cacheVariableName());
        if (StringUtils.isBlank(signature)) {
            return cacheParameter;
        } else {
            return String.format("%s, %s", signature, cacheParameter);
        }
    }

    public String drgElementArgumentsExtraCache(DRGElementReference<? extends TDRGElement> reference) {
        String arguments = drgElementArgumentsExtra(reference);
        return drgElementArgumentsExtraCache(arguments);
    }

    public String drgElementArgumentsExtraCache(TDRGElement element) {
        String arguments = drgElementArgumentsExtra(element);
        return drgElementArgumentsExtraCache(arguments);
    }

    public String drgElementArgumentsExtraCache(String arguments) {
        if (!this.caching) {
            return arguments;
        }

        if (StringUtils.isBlank(arguments)) {
            return String.format("%s", cacheVariableName());
        } else {
            return String.format("%s, %s", arguments, cacheVariableName());
        }
    }

    public String drgElementDefaultArgumentsExtraCache(String arguments) {
        if (!this.caching) {
            return arguments;
        }

        String defaultCacheArgument = defaultConstructor(defaultCacheClassName());
        if (StringUtils.isBlank(arguments)) {
            return defaultCacheArgument;
        } else {
            return String.format("%s, %s", arguments, defaultCacheArgument);
        }
    }

    public String drgElementAnnotationClassName() {
        return com.gs.dmn.runtime.annotation.DRGElement.class.getName();
    }

    public String elementKindAnnotationClassName() {
        return DRGElementKind.class.getName();
    }

    public String expressionKindAnnotationClassName() {
        return ExpressionKind.class.getName();
    }

    public String drgElementMetadataClassName() {
        return com.gs.dmn.runtime.listener.DRGElement.class.getName();
    }

    public String drgElementMetadataFieldName() {
        return "DRG_ELEMENT_METADATA";
    }

    public String drgRuleMetadataClassName() {
        return com.gs.dmn.runtime.listener.Rule.class.getName();
    }

    public String drgRuleMetadataFieldName() {
        return "drgRuleMetadata";
    }

    public String assertClassName() {
        return Assert.class.getName();
    }

    //
    // Decision Table related functions
    //
    public String defaultValue(TDRGElement element) {
        return this.decisionTableToJavaTransformer.defaultValue(element);
    }

    public String defaultValue(TDRGElement element, TOutputClause output) {
        return this.decisionTableToJavaTransformer.defaultValue(element, output);
    }

    public String condition(TDRGElement element, TDecisionRule rule) {
        return this.decisionTableToJavaTransformer.condition(element, rule);
    }

    public String outputEntryToJava(TDRGElement element, TLiteralExpression outputEntryExpression, int outputIndex) {
        return this.decisionTableToJavaTransformer.outputEntryToJava(element, outputEntryExpression, outputIndex);
    }

    public String outputClauseClassName(TDRGElement element, TOutputClause outputClause) {
        return this.decisionTableToJavaTransformer.outputClauseClassName(element, outputClause);
    }

    public String outputClauseVariableName(TDRGElement element, TOutputClause outputClause) {
        return this.decisionTableToJavaTransformer.outputClauseVariableName(element, outputClause);
    }

    public String outputClausePriorityVariableName(TDRGElement element, TOutputClause outputClause) {
        return this.decisionTableToJavaTransformer.outputClausePriorityVariableName(element, outputClause);
    }

    public String getter(TDRGElement element, TOutputClause output) {
        return this.decisionTableToJavaTransformer.getter(element, output);
    }

    public String setter(TDRGElement element, TOutputClause output) {
        return this.decisionTableToJavaTransformer.setter(element, output);
    }

    public Integer priority(TDRGElement element, TLiteralExpression literalExpression, int outputIndex) {
        return this.decisionTableToJavaTransformer.priority(element, literalExpression, outputIndex);
    }

    public String priorityGetter(TDRGElement element, TOutputClause output) {
        return this.decisionTableToJavaTransformer.priorityGetter(element, output);
    }

    public String prioritySetter(TDRGElement element, TOutputClause output) {
        return this.decisionTableToJavaTransformer.prioritySetter(element, output);
    }

    public HitPolicy hitPolicy(TDRGElement element) {
        return this.decisionTableToJavaTransformer.hitPolicy(element);
    }

    public String aggregation(TDecisionTable decisionTable) {
        return this.decisionTableToJavaTransformer.aggregation(decisionTable);
    }

    public String aggregator(TDRGElement element, TDecisionTable decisionTable, TOutputClause outputClause, String ruleOutputListVariable) {
        return this.decisionTableToJavaTransformer.aggregator(element, decisionTable, outputClause, ruleOutputListVariable);
    }

    public String annotation(TDRGElement element, TDecisionRule rule) {
        return this.decisionTableToJavaTransformer.annotation(element, rule);
    }

    public String annotationEscapedText(TDecisionRule rule) {
        return this.decisionTableToJavaTransformer.annotationEscapedText(rule);
    }

    public String escapeInString(String text) {
        return StringEscapeUtil.escapeInString(text);
    }

    private int nextChar(String text, int i) {
        if (i == text.length()) {
            return -1;
        } else {
            return text.charAt(i + 1);
        }
    }

    //
    // Rule related functions
    //
    public String ruleOutputClassName(TDRGElement element) {
        return this.decisionTableToJavaTransformer.ruleOutputClassName(element);
    }

    public String ruleId(List<TDecisionRule> rules, TDecisionRule rule) {
        return this.decisionTableToJavaTransformer.ruleId(rules, rule);
    }

    public String abstractRuleOutputClassName() {
        return this.decisionTableToJavaTransformer.abstractRuleOutputClassName();
    }

    public String ruleOutputListClassName() {
        return this.decisionTableToJavaTransformer.ruleOutputListClassName();
    }

    public String ruleSignature(TDecision decision) {
        return this.decisionTableToJavaTransformer.ruleSignature(decision);
    }

    public String ruleArgumentList(TDecision decision) {
        return this.decisionTableToJavaTransformer.ruleArgumentList(decision);
    }

    public String ruleSignature(TBusinessKnowledgeModel bkm) {
        return this.decisionTableToJavaTransformer.ruleSignature(bkm);
    }

    public String ruleArgumentList(TBusinessKnowledgeModel bkm) {
        return this.decisionTableToJavaTransformer.ruleArgumentList(bkm);
    }

    public String hitPolicyAnnotationClassName() {
        return this.decisionTableToJavaTransformer.hitPolicyAnnotationClassName();
    }

    public String ruleAnnotationClassName() {
        return this.decisionTableToJavaTransformer.ruleAnnotationClassName();
    }

    //
    // Expression related functions
    //
    public boolean isCompoundStatement(Statement stm) {
        return stm instanceof CompoundStatement;
    }

    public Statement expressionToJava(TDRGElement element) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        TExpression expression = this.dmnModelRepository.expression(element);
        if (expression instanceof TContext) {
            return this.contextToJavaTransformer.expressionToJava(element, (TContext) expression);
        } else if (expression instanceof TLiteralExpression) {
            Statement statement = this.literalExpressionToJavaTransformer.expressionToJava(((TLiteralExpression) expression).getText(), element);
            Type expectedType = toFEELType(model, drgElementOutputTypeRef(element));
            return convertExpression(statement, expectedType);
        } else if (expression instanceof TInvocation) {
            return this.invocationToJavaTransformer.expressionToJava(element, (TInvocation) expression);
        } else if (expression instanceof TRelation) {
            return this.relationToJavaTransformer.expressionToJava(element, (TRelation) expression);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s'", expression.getClass().getSimpleName()));
        }
    }

    Statement expressionToJava(TDRGElement element, TExpression expression, Environment environment) {
        if (expression instanceof TContext) {
            return this.contextToJavaTransformer.contextExpressionToJava(element, (TContext) expression, environment);
        } else if (expression instanceof TFunctionDefinition) {
            return this.functionDefinitionToJavaTransformer.functionDefinitionToJava(element, (TFunctionDefinition) expression, environment);
        } else if (expression instanceof TLiteralExpression) {
            return this.literalExpressionToJavaTransformer.literalExpressionToJava(element, ((TLiteralExpression) expression).getText(), environment);
        } else if (expression instanceof TInvocation) {
            return this.invocationToJavaTransformer.invocationExpressionToJava(element, (TInvocation) expression, environment);
        } else if (expression instanceof TRelation) {
            return this.relationToJavaTransformer.relationExpressionToJava(element, (TRelation) expression, environment);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s'", expression.getClass().getSimpleName()));
        }
    }

    public String literalExpressionToJava(TDRGElement element, String expressionText) {
        Statement statement = this.literalExpressionToJavaTransformer.expressionToJava(expressionText, element);
        return ((ExpressionStatement)statement).getExpression();
    }

    public String functionDefinitionToJava(FunctionDefinition element, boolean convertTypeToContext, String body) {
        return this.functionDefinitionToJavaTransformer.functionDefinitionToJava(element, body, convertTypeToContext);
    }

    public Type convertType(Type type, boolean convertToContext) {
        if (convertToContext) {
            if (type instanceof ItemDefinitionType) {
                type = ((ItemDefinitionType) type).toContextType();
            }
        }
        return type;
    }

    //
    // Type related functions
    //
    public boolean isComplexType(Type type) {
        return type instanceof ItemDefinitionType
                || type instanceof ListType && ((ListType) type).getElementType() instanceof ItemDefinitionType;
    }

    public Type toFEELType(TDefinitions model, String typeName) {
        Type type = this.feelTypeMemoizer.get(model, typeName);
        if (type == null) {
            type = toFEELTypeNoCache(model, typeName);
            this.feelTypeMemoizer.put(model, typeName, type);
        }
        return type;
    }

    private Type toFEELTypeNoCache(TDefinitions model, String typeName) {
        if (StringUtils.isBlank(typeName)) {
            return null;
        }
        // Lookup primitive types
        QualifiedName qName = QualifiedName.toQualifiedName(model, typeName);
        Type primitiveType = lookupPrimitiveType(qName);
        if (primitiveType != null) {
            return primitiveType;
        }
        // Lookup item definitions
        TItemDefinition itemDefinition = this.dmnModelRepository.lookupItemDefinition(model, qName);
        if (itemDefinition != null) {
            return toFEELType(itemDefinition);
        }
        throw new DMNRuntimeException(String.format("Cannot map type '%s' to FEEL", qName.toString()));
    }

    public Type toFEELType(TDefinitions model, QualifiedName typeRef) {
        Type type = this.feelTypeMemoizer.get(model, typeRef);
        if (type == null) {
            type = toFEELTypeNoCache(model, typeRef);
            this.feelTypeMemoizer.put(model, typeRef, type);
        }
        return type;
    }

    private Type toFEELTypeNoCache(TDefinitions model, QualifiedName typeRef) {
        // Lookup primitive types
        Type primitiveType = lookupPrimitiveType(typeRef);
        if (primitiveType != null) {
            return primitiveType;
        }
        // Lookup item definitions
        TItemDefinition itemDefinition = this.dmnModelRepository.lookupItemDefinition(model, typeRef);
        if (itemDefinition != null) {
            return toFEELType(itemDefinition);
        }
        throw new DMNRuntimeException(String.format("Cannot map type '%s' to FEEL", typeRef));
    }

    Type toFEELType(TItemDefinition itemDefinition) {
        Type type = this.feelTypeMemoizer.get(itemDefinition);
        if (type == null) {
            type = toFEELTypeNoCache(itemDefinition);
            this.feelTypeMemoizer.put(itemDefinition, type);
        }
        return type;
    }

    private Type toFEELTypeNoCache(TItemDefinition itemDefinition) {
        itemDefinition = this.dmnModelRepository.normalize(itemDefinition);
        TDefinitions model = this.dmnModelRepository.getModel(itemDefinition);
        QualifiedName typeRef = QualifiedName.toQualifiedName(model, itemDefinition.getTypeRef());
        List<TItemDefinition> itemComponent = itemDefinition.getItemComponent();
        if (typeRef == null && (itemComponent == null || itemComponent.isEmpty())) {
            return AnyType.ANY;
        }
        Type type;
        if (typeRef != null) {
            type = toFEELType(model, typeRef);
        } else {
            TDefinitions definitions = this.dmnModelRepository.getModel(itemDefinition);
            String modelName = definitions == null ? null : definitions.getName();
            type = new ItemDefinitionType(itemDefinition.getName(), modelName);
            for(TItemDefinition item: itemComponent) {
                ((ItemDefinitionType)type).addMember(item.getName(), Arrays.asList(item.getLabel()), toFEELType(item));
            }
        }
        if (itemDefinition.isIsCollection()) {
            return new ListType(type);
        } else {
            return type;
        }
    }

    Type lookupPrimitiveType(QualifiedName typeRef) {
        if (typeRef == null) {
            return null;
        }
        String importName = typeRef.getNamespace();
        if (DMNVersion.LATEST.getFeelPrefix().equals(importName)) {
            String typeName = typeRef.getLocalPart();
            return FEELTypes.FEEL_NAME_TO_FEEL_TYPE.get(typeName);
        } else if (StringUtils.isBlank(importName)) {
            String typeName = typeRef.getLocalPart();
            return FEELTypes.FEEL_NAME_TO_FEEL_TYPE.get(typeName);
        } else {
            return null;
        }
    }

    //
    // Common functions
    //
    public String toJavaType(TDecision decision) {
        String javaType = this.javaTypeMemoizer.get(decision);
        if (javaType == null) {
            javaType = toJavaTypeNoCache(decision);
            this.javaTypeMemoizer.put(decision, javaType);
        }
        return javaType;
    }

    private String toJavaTypeNoCache(TDecision decision) {
        Type type = this.drgElementOutputFEELType(decision);
        return toJavaType(type);
    }

    public String toStringJavaType(Type type) {
        return toJavaType(StringType.STRING);
    }

    public String toJavaType(Type type) {
        String javaType = this.javaTypeMemoizer.get(type);
        if (javaType == null) {
            javaType = toJavaTypeNoCache(type);
            this.javaTypeMemoizer.put(type, javaType);
        }
        return javaType;
    }

    private String toJavaTypeNoCache(Type type) {
        if (type instanceof NamedType) {
            String typeName = ((NamedType) type).getName();
            String primitiveType = this.typeFactory.toJavaType(typeName);
            if (!StringUtils.isBlank(primitiveType)) {
                return primitiveType;
            } else {
                if (type instanceof ItemDefinitionType) {
                    String modelName = ((ItemDefinitionType) type).getModelName();
                    return qualifiedName(javaTypePackageName(modelName), upperCaseFirst(typeName));
                } else {
                    throw new DMNRuntimeException(String.format("Cannot infer platform type for '%s'", type));
                }
            }
        } else if (type instanceof ContextType) {
            return contextClassName();
        } else if (type instanceof ListType) {
            if (((ListType) type).getElementType() instanceof AnyType) {
                return makeListType(DMNToJavaTransformer.LIST_TYPE);
            } else {
                String elementType = toJavaType(((ListType) type).getElementType());
                return makeListType(DMNToJavaTransformer.LIST_TYPE, elementType);
            }
        } else if (type instanceof FunctionType) {
            String returnType = toJavaType(((FunctionType) type).getReturnType());
            return makeFunctionType(LambdaExpression.class.getName(), returnType);
        }
        throw new IllegalArgumentException(String.format("Cannot map type '%s' to Java", type));
    }

    protected String makeListType(String listType, String elementType) {
        return String.format("%s<%s>", listType, elementType);
    }

    protected String makeListType(String listType) {
        return String.format("%s<? extends Object>", listType);
    }

    protected String makeFunctionType(String name, String returnType) {
        return String.format("%s<%s>", name, returnType);
    }

    public String qualifiedName(String pkg, String name) {
        if (StringUtils.isBlank(pkg)) {
            return name;
        } else {
            return pkg + "." + name;
        }
    }

    public String qualifiedName(DRGElementReference<? extends TDRGElement> reference) {
        return qualifiedName(reference.getElement());
    }

    public String qualifiedName(TDRGElement element) {
        TDefinitions definitions = this.dmnModelRepository.getModel(element);
        String pkg = this.javaModelPackageName(definitions.getName());
        String name = drgElementClassName(element);
        if (StringUtils.isBlank(pkg)) {
            return name;
        } else {
            return pkg + "." + name;
        }
    }

    public String getterName(String name) {
        return String.format("get%s", StringUtils.capitalize(name));
    }

    public String getter(String name) {
        return String.format("%s()", getterName(name));
    }

    public String setter(String name) {
        return String.format("set%s", StringUtils.capitalize(name));
    }

    public String contextGetter(String name) {
        return String.format("get(\"%s\")", name);
    }

    public String contextSetter(String name) {
        return String.format("put(\"%s\", ", name);
    }

    public String javaFriendlyVariableName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new DMNRuntimeException("Cannot build variable name from empty string");
        }
        name = this.dmnModelRepository.removeSingleQuotes(name);
        String[] parts = name.split("\\.");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            String firstChar = Character.toString(Character.toLowerCase(part.charAt(0)));
            String partName = javaFriendlyName(part.length() == 1 ? firstChar : firstChar + part.substring(1));
            if (i != 0) {
                result.append(".");
            }
            result.append(partName);
        }
        return result.toString();
    }

    public String javaFriendlyName(String name) {
        name = this.dmnModelRepository.removeSingleQuotes(name);
        StringBuilder result = new StringBuilder();
        boolean skippedPrevious = false;
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (Character.isAlphabetic(ch) || Character.isDigit(ch) || ch == '_') {
                if (skippedPrevious) {
                    ch = Character.toUpperCase(ch);
                }
                result.append(ch);
                skippedPrevious = false;
            } else {
                skippedPrevious = true;
            }
        }
        return result.toString();
    }

    public String upperCaseFirst(String name) {
        name = this.dmnModelRepository.removeSingleQuotes(name);
        String firstChar = Character.toString(Character.toUpperCase(name.charAt(0)));
        return javaFriendlyName(name.length() == 1 ? firstChar : firstChar + name.substring(1));
    }

    public String lowerCaseFirst(String name) {
        name = this.dmnModelRepository.removeSingleQuotes(name);
        String firstChar = Character.toString(Character.toLowerCase(name.charAt(0)));
        return javaFriendlyName(name.length() == 1 ? firstChar : firstChar + name.substring(1));
    }

    public String javaModelPackageName(String modelName) {
        if (modelName != null && modelName.endsWith(DMNConstants.DMN_FILE_EXTENSION)) {
            modelName = modelName.substring(0, modelName.length() - 4);
        }
        if (this.onePackage) {
            modelName = "";
        }

        String modelPackageName = javaModelName(modelName);
        String elementPackageName = this.javaRootPackage;
        if (StringUtils.isBlank(elementPackageName)) {
            return modelPackageName;
        } else if (!StringUtils.isBlank(modelPackageName)) {
            return elementPackageName + "." + modelPackageName;
        } else {
            return elementPackageName;
        }
    }

    protected String javaModelName(String name) {
        if (StringUtils.isEmpty(name)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        boolean skippedPrevious = false;
        boolean first = true;
        for (int ch: name.codePoints().toArray()) {
            if (Character.isJavaIdentifierPart(ch)) {
                if (skippedPrevious && !first) {
                    result.append('_');
                }
                result.append((char)ch);
                skippedPrevious = false;
                first = false;
            } else {
                skippedPrevious = true;
            }
        }
        String newName = result.toString().toLowerCase();
        if (newName.isEmpty()) {
            return newName;
        } else {
            if (!Character.isJavaIdentifierStart(newName.codePointAt(0))) {
                return "p_" + newName;
            } else {
                return newName;
            }
        }
    }

    public String javaRootPackageName() {
        if (this.javaRootPackage == null) {
            return "";
        } else {
            return this.javaRootPackage;
        }
    }

    public String javaTypePackageName(String modelName) {
        String modelPackageName = javaModelPackageName(modelName);
        if (StringUtils.isBlank(modelPackageName)) {
            return DMNToJavaTransformer.DATA_PACKAGE;
        } else {
            return modelPackageName + "." + DMNToJavaTransformer.DATA_PACKAGE;
        }
    }

    public DRGElementKind elementKind(TDRGElement element) {
        return DRGElementKind.kindByClass(element.getClass());
    }

    public ExpressionKind expressionKind(TDRGElement element) {
        TExpression expression = this.dmnModelRepository.expression(element);
        if (expression != null) {
            return ExpressionKind.kindByClass(expression.getClass());
        }
        return ExpressionKind.OTHER;
    }

    public String asList(String exp) {
        return this.expressionFactory.asList(exp);
    }

    //
    // Environment related functions
    //
    public Environment makeEnvironment(TDRGElement element) {
        Environment environment = environmentMemoizer.get(element);
        if (environment == null) {
            environment = makeEnvironmentNoCache(element);
            this.environmentMemoizer.put(element, environment);
        }
        return environment;
    }

    private Environment makeEnvironmentNoCache(TDRGElement element) {
        Environment parentEnvironment = this.environmentFactory.getRootEnvironment();
        return makeEnvironment(element, parentEnvironment);
    }

    public Environment makeEnvironment(TDRGElement element, Environment parentEnvironment) {
        Environment elementEnvironment = this.environmentFactory.makeEnvironment(parentEnvironment);

        // Add declaration for each direct child
        List<DRGElementReference<? extends TDRGElement>> directReferences = getDMNModelRepository().directDRGElements(element);
        for (DRGElementReference<? extends TDRGElement> reference: directReferences) {
            // Create child environment to infer type if needed
            TDRGElement child = reference.getElement();
            Declaration declaration = makeDeclaration(element, elementEnvironment, child);
            addDeclaration(elementEnvironment, declaration, element, child);
        }

        // Add it to cache to avoid infinite loops
        this.environmentMemoizer.put(element, elementEnvironment);
        // Add declaration of element to support recursion
        Declaration declaration = makeDeclaration(element, elementEnvironment, element);
        addDeclaration(elementEnvironment, declaration, element, element);

        // Add declaration for parameters
        if (element instanceof  TBusinessKnowledgeModel) {
            Environment bkmEnvironment = this.environmentFactory.makeEnvironment(elementEnvironment);
            TDefinitions definitions = this.dmnModelRepository.getModel(element);
            TFunctionDefinition functionDefinition = ((TBusinessKnowledgeModel) element).getEncapsulatedLogic();
            if (functionDefinition != null) {
                functionDefinition.getFormalParameter().forEach(
                        p -> {
                            bkmEnvironment.addDeclaration(this.environmentFactory.makeVariableDeclaration(p.getName(), toFEELType(definitions, QualifiedName.toQualifiedName(definitions, p.getTypeRef()))));
                        });
                elementEnvironment = bkmEnvironment;
            }
        }

        return elementEnvironment;
    }

    protected Declaration makeDeclaration(TDRGElement parent, Environment parentEnvironment, TDRGElement child) {
        if (parent == null || child == null) {
            throw new IllegalArgumentException("Cannot add declaration for null DRG element");
        }

        Declaration declaration;
        if (child instanceof TInputData) {
            declaration = makeVariableDeclaration(child, ((TInputData) child).getVariable());
        } else if (child instanceof TBusinessKnowledgeModel) {
            declaration = makeInvocableDeclaration((TBusinessKnowledgeModel) child);
        } else if (child instanceof TDecision) {
            declaration = makeVariableDeclaration(child, ((TDecision) child).getVariable());
        } else if (child instanceof TDecisionService) {
            declaration = makeInvocableDeclaration((TDecisionService) child);
        } else {
            throw new UnsupportedOperationException(String.format("'%s' is not supported yet", child.getClass().getSimpleName()));
        }
        return declaration;
    }

    protected void addDeclaration(Environment parentEnvironment, Declaration declaration, TDRGElement parent, TDRGElement child) {
        Type type = declaration.getType();
        String importName = this.dmnModelRepository.findChildImportName(parent, child);
        if (ImportPath.isEmpty(importName)) {
            parentEnvironment.addDeclaration(declaration);
        } else {
            Declaration importDeclaration = parentEnvironment.lookupVariableDeclaration(importName);
            if (importDeclaration == null) {
                ImportContextType contextType = new ImportContextType(importName);
                contextType.addMember(declaration.getName(), new ArrayList<>(), type);
                contextType.addMemberReference(declaration.getName(), this.dmnModelRepository.makeDRGElementReference(importName, child));
                importDeclaration = this.environmentFactory.makeVariableDeclaration(importName, contextType);
                parentEnvironment.addDeclaration(importDeclaration);
            } else if (importDeclaration instanceof VariableDeclaration) {
                Type importType = ((VariableDeclaration) importDeclaration).getType();
                if (importType instanceof ImportContextType) {
                    ((ImportContextType) importType).addMember(declaration.getName(), new ArrayList<>(), type);
                    ((ImportContextType) importType).addMemberReference(declaration.getName(), this.dmnModelRepository.makeDRGElementReference(importName, child));
                } else {
                    throw new DMNRuntimeException(String.format("Cannot process declaration for '%s.%s'", importName, declaration.getName()));
                }
            } else {
                throw new DMNRuntimeException(String.format("Cannot process declaration for '%s.%s'", importName, declaration.getName()));
            }
        }
    }

    public Environment makeFunctionDefinitionEnvironment(TNamedElement element, TFunctionDefinition functionDefinition, Environment parentEnvironment) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        Environment environment = this.environmentFactory.makeEnvironment(parentEnvironment);
        functionDefinition.getFormalParameter().forEach(
                p -> environment.addDeclaration(this.environmentFactory.makeVariableDeclaration(p.getName(), toFEELType(model, QualifiedName.toQualifiedName(model, p.getTypeRef())))));
        return environment;
    }

    private Type makeDSOutputType(TDecisionService decisionService) {
        TDefinitions model = this.dmnModelRepository.getModel(decisionService);
        // Derive from variable
        TInformationItem variable = decisionService.getVariable();
        if (variable != null && variable.getTypeRef() != null) {
            return toFEELType(model, variable.getTypeRef());
        }
        // Derive from decisions
        Environment environment = makeEnvironment(decisionService);
        List<TDMNElementReference> outputDecisions = decisionService.getOutputDecision();
        if (outputDecisions.size() == 1) {
            TDecision decision = getDMNModelRepository().findDecisionByRef(decisionService, outputDecisions.get(0).getHref());
            String decisionName = decision.getName();
            VariableDeclaration declaration = (VariableDeclaration) environment.lookupVariableDeclaration(decisionName);
            return declaration.getType();
        } else {
            ContextType type = new ContextType();
            for (TDMNElementReference er: outputDecisions) {
                TDecision decision = getDMNModelRepository().findDecisionByRef(decisionService, er.getHref());
                String decisionName = decision.getName();
                VariableDeclaration declaration = (VariableDeclaration) environment.lookupVariableDeclaration(decisionName);
                type.addMember(decisionName, Arrays.asList(), declaration.getType());
            }
            return type;
        }
    }

    private FunctionType makeDSType(TDecisionService decisionService) {
        List<FormalParameter> parameters = dsFEELParameters(decisionService);
        FunctionType type = new DMNFunctionType(parameters, makeDSOutputType(decisionService), decisionService);
        return type;
    }

    public Environment makeInputEntryEnvironment(TDRGElement element, Expression inputExpression) {
        Environment environment = this.environmentFactory.makeEnvironment(makeEnvironment(element), inputExpression);
        environment.addDeclaration(DMNToJavaTransformer.INPUT_ENTRY_PLACE_HOLDER, this.environmentFactory.makeVariableDeclaration(DMNToJavaTransformer.INPUT_ENTRY_PLACE_HOLDER, inputExpression.getType()));
        return environment;
    }

    Environment makeOutputEntryEnvironment(TDRGElement element, EnvironmentFactory environmentFactory) {
        return environmentFactory.makeEnvironment(makeEnvironment(element));
    }

    public Pair<Environment, Map<TContextEntry, Expression>> makeContextEnvironment(TDRGElement element, TContext context, Environment parentEnvironment) {
        Environment contextEnvironment = this.makeEnvironment((TDRGElement) element, parentEnvironment);
        Map<TContextEntry, Expression> literalExpressionMap = new LinkedHashMap<>();
        for(TContextEntry entry: context.getContextEntry()) {
            TInformationItem variable = entry.getVariable();
            JAXBElement<? extends TExpression> jElement = entry.getExpression();
            TExpression expression = jElement == null ? null : jElement.getValue();
            Expression feelExpression = null;
            if (expression instanceof TLiteralExpression) {
                feelExpression = this.feelTranslator.analyzeExpression(((TLiteralExpression) expression).getText(), FEELContext.makeContext(element, contextEnvironment));
                literalExpressionMap.put(entry, feelExpression);
            }
            if (variable != null) {
                String name = variable.getName();
                Type entryType;
                if (expression instanceof TLiteralExpression) {
                    entryType = entryType(element, entry, expression, feelExpression);
                } else {
                    entryType = entryType(element, entry, contextEnvironment);
                }
                addContextEntryDeclaration(contextEnvironment, name, entryType);
            }
        }
        return new Pair<>(contextEnvironment, literalExpressionMap);
    }

    Type entryType(TNamedElement element, TContextEntry entry, TExpression expression, Expression feelExpression) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        TInformationItem variable = entry.getVariable();
        Type entryType = variableType(element, variable);
        if (entryType != null) {
            return entryType;
        }
        QualifiedName typeRef = expression == null ? null : QualifiedName.toQualifiedName(model, expression.getTypeRef());
        if (typeRef != null) {
            entryType = toFEELType(model, typeRef);
        }
        if (entryType == null) {
            entryType = feelExpression.getType();
        }
        return entryType;
    }

    private void addContextEntryDeclaration(Environment contextEnvironment, String name, Type entryType) {
        if (entryType instanceof FunctionType) {
            contextEnvironment.addDeclaration(this.environmentFactory.makeFunctionDeclaration(name, (FunctionType) entryType));
        } else {
            contextEnvironment.addDeclaration(this.environmentFactory.makeVariableDeclaration(name, entryType));
        }
    }

    Type entryType(TDRGElement element, TContextEntry entry, Environment contextEnvironment) {
        TInformationItem variable = entry.getVariable();
        Type feelType = variableType(element, variable);
        if (feelType != null) {
            return feelType;
        }
        feelType = expressionType(element, entry.getExpression(), contextEnvironment);
        return feelType == null ? AnyType.ANY : feelType;
    }

    Type variableType(TNamedElement element, TInformationItem variable) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        if (variable != null) {
            QualifiedName typeRef = QualifiedName.toQualifiedName(model, variable.getTypeRef());
            if (typeRef != null) {
                return toFEELType(model, typeRef);
            }
        }
        return null;
    }

    private Type expressionType(TDRGElement element, JAXBElement<? extends TExpression> jElement, Environment environment) {
        return jElement == null ? null : expressionType(element, jElement.getValue(), environment);
    }

    Type expressionType(TDRGElement element, TExpression expression, Environment environment) {
        if (expression == null) {
            return null;
        }
        TDefinitions model = this.dmnModelRepository.getModel(element);
        QualifiedName typeRef = QualifiedName.toQualifiedName(model, expression.getTypeRef());
        if (typeRef != null) {
            return toFEELType(model, typeRef);
        }
        if (expression instanceof TContext) {
            List<TContextEntry> contextEntryList = ((TContext) expression).getContextEntry();
            // Collect members & return type
            List<Pair<String, Type>> members = new ArrayList<>();
            Type returnType = null;
            Environment contextEnvironment = this.environmentFactory.makeEnvironment(environment);
            for(TContextEntry entry: contextEntryList) {
                TInformationItem variable = entry.getVariable();
                if (variable != null) {
                    String name = variable.getName();
                    Type entryType = entryType(element, entry, contextEnvironment);
                    contextEnvironment.addDeclaration(this.environmentFactory.makeVariableDeclaration(name, entryType));
                    members.add(new Pair<>(name, entryType));
                } else {
                    returnType = entryType(element, entry, contextEnvironment);
                }
            }
            // Infer return type
            if (returnType == null) {
                ItemDefinitionType contextType = new ItemDefinitionType("");
                for (Pair<String, Type> member: members) {
                    contextType.addMember(member.getLeft(), new ArrayList<>(), member.getRight());
                }
                return contextType;
            } else {
                return returnType;
            }
        } else if (expression instanceof TFunctionDefinition) {
            Type type = functionDefinitionType(element, (TFunctionDefinition) expression, environment);
            return type;
        } else if (expression instanceof TLiteralExpression) {
            Type type = literalExpressionType(element, (TLiteralExpression) expression, environment);
            return type;
        } else if (expression instanceof TInvocation) {
            TExpression body = ((TInvocation) expression).getExpression().getValue();
            if (body instanceof TLiteralExpression) {
                String bkmName = ((TLiteralExpression) body).getText();
                TBusinessKnowledgeModel bkm = this.dmnModelRepository.findKnowledgeModelByName(bkmName);
                if (bkm == null) {
                    throw new DMNRuntimeException(String.format("Cannot find BKM for '%s'", bkmName));
                }
                Type expressionType = drgElementOutputFEELType(bkm);
                return expressionType;
            } else {
                throw new DMNRuntimeException(String.format("Not supported '%s'", body.getClass().getSimpleName()));
            }
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", expression.getClass().getSimpleName()));
        }
    }

    private Type functionDefinitionType(TDRGElement element, TFunctionDefinition functionDefinition, Environment environment) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        JAXBElement<? extends TExpression> expressionElement = functionDefinition.getExpression();
        if (expressionElement != null) {
            TExpression body = expressionElement.getValue();
            QualifiedName typeRef = QualifiedName.toQualifiedName(model, body.getTypeRef());
            if (typeRef != null) {
                return toFEELType(model, typeRef);
            } else {
                Environment functionDefinitionEnvironment = makeFunctionDefinitionEnvironment(element, functionDefinition, environment);
                TFunctionKind kind = functionDefinition.getKind();
                Type bodyType = null;
                if (isFEELFunction(kind)) {
                    bodyType = expressionType(element, body, functionDefinitionEnvironment);
                } else if (isJavaFunction(kind)) {
                    bodyType = AnyType.ANY;
                }
                List<FormalParameter> parameters = new ArrayList<>();
                for(TInformationItem param: functionDefinition.getFormalParameter()) {
                    Type paramType = toFEELType(model, QualifiedName.toQualifiedName(model, param.getTypeRef()));
                    parameters.add(new FormalParameter(param.getName(), paramType));
                }
                if (bodyType != null) {
                    return new DMNFunctionType(parameters, bodyType, element);
                }
            }
        }
        return null;
    }

    public boolean isFEELFunction(TFunctionKind kind) {
        return kind == null || kind == TFunctionKind.FEEL;
    }

    public boolean isJavaFunction(TFunctionKind kind) {
        return kind == TFunctionKind.JAVA;
    }

    private Type literalExpressionType(TNamedElement element, TLiteralExpression body, Environment environment) {
        FEELContext context = FEELContext.makeContext(element, environment);
        Expression expression = this.feelTranslator.analyzeExpression(body.getText(), context);
        return expression.getType();
    }

    public Environment makeRelationEnvironment(TNamedElement element, TRelation relation, Environment environment) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        Environment relationEnvironment = this.environmentFactory.makeEnvironment(environment);
        for(TInformationItem column: relation.getColumn()) {
            QualifiedName typeRef = QualifiedName.toQualifiedName(model, column.getTypeRef());
            if (typeRef != null) {
                String name = column.getName();
                relationEnvironment.addDeclaration(this.environmentFactory.makeVariableDeclaration(name, toFEELType(model, typeRef)));
            }
        }
        return relationEnvironment;
    }

    protected Declaration makeVariableDeclaration(TDRGElement element, TInformationItem variable) {
        // Check variable
        String name = element.getName();
        if (StringUtils.isBlank(name) && variable != null) {
            name = variable.getName();
        }
        if (StringUtils.isBlank(name) || variable == null) {
            throw new DMNRuntimeException(String.format("Name and variable cannot be null. Found '%s' and '%s'", name, variable));
        }

        Type variableType = drgElementVariableFEELType(element);
        return this.environmentFactory.makeVariableDeclaration(name, variableType);
    }

    protected FunctionDeclaration makeInvocableDeclaration(TInvocable invocable) {
        if (invocable instanceof TBusinessKnowledgeModel) {
            return makeBKMDeclaration((TBusinessKnowledgeModel) invocable);
        } else if (invocable instanceof TDecisionService) {
            return makeDSDeclaration((TDecisionService) invocable);
        } else {
            throw new UnsupportedOperationException(String.format("'%s' is not supported yet", invocable.getClass().getSimpleName()));
        }
    }

    protected FunctionDeclaration makeDSDeclaration(TDecisionService ds) {
        TInformationItem variable = ds.getVariable();
        String name = ds.getName();
        if (StringUtils.isBlank(name) && variable != null) {
            name = variable.getName();
        }
        if (StringUtils.isBlank(name)) {
            throw new DMNRuntimeException(String.format("Name and variable cannot be null. Found '%s' and '%s'", name, variable));
        }
        FunctionType serviceType = makeDSType(ds);
        return this.environmentFactory.makeDecisionServiceDeclaration(name, serviceType);
    }

    protected FunctionDeclaration makeBKMDeclaration(TBusinessKnowledgeModel bkm) {
        TInformationItem variable = bkm.getVariable();
        String name = bkm.getName();
        if (StringUtils.isBlank(name) && variable != null) {
            name = variable.getName();
        }
        if (StringUtils.isBlank(name)) {
            throw new DMNRuntimeException(String.format("Name and variable cannot be null. Found '%s' and '%s'", name, variable));
        }
        List<FormalParameter> parameters = bkmFEELParameters(bkm);
        Type returnType = drgElementOutputFEELType(bkm);
        return this.environmentFactory.makeBusinessKnowledgeModelDeclaration(name, new DMNFunctionType(parameters, returnType, bkm));
    }
}
