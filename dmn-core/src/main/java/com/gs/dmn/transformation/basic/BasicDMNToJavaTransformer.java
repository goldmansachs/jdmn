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
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.lib.StringEscapeUtil;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.feel.synthesis.FEELTranslatorImpl;
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
import com.gs.dmn.runtime.external.JavaExternalFunction;
import com.gs.dmn.runtime.interpreter.ImportPath;
import com.gs.dmn.runtime.listener.Arguments;
import com.gs.dmn.runtime.listener.EventListener;
import com.gs.dmn.runtime.listener.LoggingEventListener;
import com.gs.dmn.runtime.listener.NopEventListener;
import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.LazyEvaluationOptimisation;
import com.gs.dmn.transformation.native_.JavaFactory;
import com.gs.dmn.transformation.native_.NativeFactory;
import com.gs.dmn.transformation.native_.statement.CompoundStatement;
import com.gs.dmn.transformation.native_.statement.ExpressionStatement;
import com.gs.dmn.transformation.native_.statement.Statement;
import com.gs.dmn.transformation.proto.MessageType;
import com.gs.dmn.transformation.proto.ProtoBufferFactory;
import com.gs.dmn.transformation.proto.ProtoBufferJavaFactory;
import com.gs.dmn.transformation.proto.Service;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20191111.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BasicDMNToJavaTransformer implements BasicDMNToNativeTransformer {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BasicDMNToJavaTransformer.class);

    private DMNDialectDefinition<?, ?, ?, ?, ?, ?> dialect;
    protected final DMNModelRepository dmnModelRepository;
    protected final EnvironmentFactory environmentFactory;
    protected final NativeTypeFactory nativeTypeFactory;
    protected ProtoBufferFactory protoFactory;
    private final LazyEvaluationDetector lazyEvaluationDetector;

    private final InputParameters inputParameters;
    private final boolean onePackage;

    private final LazyEvaluationOptimisation lazyEvaluationOptimisation;
    private final Set<String> cachedElements;

    protected DMNEnvironmentFactory dmnEnvironmentFactory;
    protected NativeFactory nativeFactory;
    protected FEELTranslator feelTranslator;
    protected DMNExpressionToNativeTransformer expressionToNativeTransformer;
    protected final DRGElementFilter drgElementFilter;
    protected final JavaTypeMemoizer nativeTypeMemoizer;

    public BasicDMNToJavaTransformer(DMNDialectDefinition<?, ?, ?, ?, ?, ?> dialect, DMNModelRepository dmnModelRepository, EnvironmentFactory environmentFactory, NativeTypeFactory nativeTypeFactory, LazyEvaluationDetector lazyEvaluationDetector, InputParameters inputParameters) {
        this.dialect = dialect;
        this.dmnModelRepository = dmnModelRepository;
        this.environmentFactory = environmentFactory;
        this.nativeTypeFactory = nativeTypeFactory;
        this.lazyEvaluationDetector = lazyEvaluationDetector;

        // Configuration
        this.inputParameters = inputParameters;
        this.onePackage = this.inputParameters.isOnePackage() || dmnModelRepository.getAllDefinitions().size() == 1;

        // Derived data
        this.lazyEvaluationOptimisation = this.lazyEvaluationDetector.detect(this.dmnModelRepository);
        this.cachedElements = this.dmnModelRepository.computeCachedElements(this.inputParameters.isCaching(), this.inputParameters.getCachingThreshold());

        // Helpers
        setProtoBufferFactory(this);
        setNativeFactory(this);
        setFEELTranslator(this);
        setDMNEnvironmentFactory(this);
        setExpressionToNativeTransformer(this);

        this.drgElementFilter = new DRGElementFilter(this.inputParameters.isSingletonInputData());
        this.nativeTypeMemoizer = new JavaTypeMemoizer();
    }

    protected void setProtoBufferFactory(BasicDMNToJavaTransformer transformer) {
        this.protoFactory = new ProtoBufferJavaFactory(this);
    }

    protected void setDMNEnvironmentFactory(BasicDMNToNativeTransformer transformer) {
        this.dmnEnvironmentFactory = new StandardDMNEnvironmentFactory(transformer);
    }

    protected void setNativeFactory(BasicDMNToNativeTransformer transformer) {
        this.nativeFactory = new JavaFactory(transformer);
    }

    private void setExpressionToNativeTransformer(BasicDMNToNativeTransformer transformer) {
        this.expressionToNativeTransformer = new DMNExpressionToNativeTransformer(transformer);
    }

    private void setFEELTranslator(BasicDMNToNativeTransformer transformer) {
        this.feelTranslator = new FEELTranslatorImpl(transformer);
    }

    @Override
    public DMNDialectDefinition<?, ?, ?, ?, ?, ?> getDialect() {
        return this.dialect;
    }

    @Override
    public DMNModelRepository getDMNModelRepository() {
        return this.dmnModelRepository;
    }

    @Override
    public EnvironmentFactory getEnvironmentFactory() {
        return this.environmentFactory;
    }

    @Override
    public DMNEnvironmentFactory getDMNEnvironmentFactory() {
        return this.dmnEnvironmentFactory;
    }

    @Override
    public FEELTranslator getFEELTranslator() {
        return this.feelTranslator;
    }

    @Override
    public NativeTypeFactory getNativeTypeFactory() {
        return this.nativeTypeFactory;
    }

    @Override
    public NativeFactory getNativeFactory() {
        return this.nativeFactory;
    }

    @Override
    public DMNExpressionToNativeTransformer getExpressionToNativeTransformer() {
        return this.expressionToNativeTransformer;
    }

    @Override
    public DRGElementFilter getDrgElementFilter() {
        return this.drgElementFilter;
    }

    @Override
    public ProtoBufferFactory getProtoFactory() {
        return this.protoFactory;
    }

    //
    // Configuration
    //
    @Override
    public boolean isOnePackage() {
        return this.onePackage;
    }

    @Override
    public boolean isSingletonInputData() {
        return this.inputParameters.isSingletonInputData();
    }

    //
    // TItemDefinition related functions
    //
    @Override
    public boolean isComplexType(TItemDefinition itemDefinition) {
        Type type = toFEELType(itemDefinition);
        return isComplexType(type);
    }

    @Override
    public String itemDefinitionNativeSimpleInterfaceName(TItemDefinition itemDefinition) {
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

    @Override
    public String itemDefinitionNativeSimpleInterfaceName(String className) {
        return className.substring(0, className.length() - "Impl".length());
    }

    @Override
    public String itemDefinitionNativeClassName(String interfaceName) {
        return interfaceName + "Impl";
    }

    @Override
    public String itemDefinitionNativeQualifiedInterfaceName(TItemDefinition itemDefinition) {
        Type type = toFEELType(itemDefinition);
        return toNativeType(type);
    }

    @Override
    public String itemDefinitionSignature(TItemDefinition itemDefinition) {
        List<Pair<String, String>> parameters = new ArrayList<>();
        List<TItemDefinition> itemComponents = itemDefinition.getItemComponent();
        this.dmnModelRepository.sortNamedElements(itemComponents);
        for (TItemDefinition child : itemComponents) {
            parameters.add(new Pair<>(namedElementVariableName(child), itemDefinitionNativeQualifiedInterfaceName(child)));
        }
        return parameters.stream().map(p -> this.nativeFactory.nullableParameter(p.getRight(), p.getLeft())).collect(Collectors.joining(", "));
    }

    @Override
    public String getter(TItemDefinition itemDefinition) {
        return getter(namedElementVariableName(itemDefinition));
    }

    @Override
    public String setter(TItemDefinition itemDefinition) {
        return setter(namedElementVariableName(itemDefinition));
    }

    @Override
    public String protoGetter(TItemDefinition itemDefinition) {
        return this.protoFactory.protoGetter(namedElementVariableName(itemDefinition), toFEELType(itemDefinition));
    }

    @Override
    public String protoGetter(TDRGElement drgElement) {
        return this.protoGetter(drgElement, drgElementOutputFEELType(drgElement));
    }

    private String protoGetter(TNamedElement element, Type type) {
        return this.protoFactory.protoGetter(namedElementVariableName(element), type);
    }

    @Override
    public String protoSetter(TItemDefinition itemDefinition) {
        return this.protoSetter(itemDefinition, toFEELType(itemDefinition));
    }

    @Override
    public String protoSetter(TDRGElement drgElement) {
        return this.protoSetter(drgElement, drgElementOutputFEELType(drgElement));
    }

    private String protoSetter(TNamedElement namedElement, Type type) {
        return this.protoFactory.protoSetter(namedElementVariableName(namedElement), type);
    }

    //
    // TInformationItem related functions
    //
    private Type informationItemType(TBusinessKnowledgeModel bkm, TInformationItem element) {
        TDefinitions model = this.dmnModelRepository.getModel(bkm);
        return toFEELType(model, QualifiedName.toQualifiedName(model, element.getTypeRef()));
    }

    @Override
    public String defaultConstructor(String className) {
        return this.nativeFactory.constructor(className, "");
    }

    @Override
    public String constructor(String className, String arguments) {
        return this.nativeFactory.constructor(className, arguments);
    }

    //
    // DRGElement related functions
    //
    @Override
    public boolean hasListType(TDRGElement element) {
        Type feelType = drgElementOutputFEELType(element);
        return feelType instanceof ListType;
    }

    @Override
    public String drgElementClassName(TDRGElement element) {
        return upperCaseFirst(element.getName());
    }

    @Override
    public String drgElementReferenceVariableName(DRGElementReference<? extends TDRGElement> reference) {
        String name = reference.getElementName();
        if (name == null) {
            throw new DMNRuntimeException(String.format("Variable name cannot be null. Decision id '%s'", reference.getElement().getId()));
        }
        return drgReferenceQualifiedName(reference);
    }

    @Override
    public String drgElementOutputType(DRGElementReference<? extends TDRGElement> reference) {
        return drgElementOutputType(reference.getElement());
    }

    @Override
    public String drgElementOutputType(TDRGElement element) {
        Type type = drgElementOutputFEELType(element);
        return toNativeType(type);
    }

    @Override
    public String drgElementOutputClassName(TDRGElement element) {
        Type type = drgElementOutputFEELType(element);
        if (type instanceof ListType) {
            type = ((ListType) type).getElementType();
        }
        return toNativeType(type);
    }

    @Override
    public Type drgElementOutputFEELType(TDRGElement element) {
        return this.dmnEnvironmentFactory.drgElementOutputFEELType(element);
    }

    @Override
    public Type drgElementOutputFEELType(TDRGElement element, Environment environment) {
        return this.dmnEnvironmentFactory.drgElementOutputFEELType(element, environment);
    }

    @Override
    public Type drgElementVariableFEELType(TDRGElement element) {
        return this.dmnEnvironmentFactory.drgElementVariableFEELType(element);
    }

    @Override
    public Type drgElementVariableFEELType(TDRGElement element, Environment environment) {
        return this.dmnEnvironmentFactory.drgElementVariableFEELType(element, environment);
    }

    @Override
    public String annotation(TDRGElement element, String description) {
        if (StringUtils.isBlank(description)) {
            return "\"\"";
        }
        try {
            Environment environment = makeEnvironment(element);
            Statement statement = this.expressionToNativeTransformer.literalExpressionToNative(element, description, environment);
            return ((ExpressionStatement) statement).getExpression();
        } catch (Exception e) {
            LOGGER.warn(String.format("Cannot process description '%s' for element '%s'", description, element == null ? "" : element.getName()));
            return String.format("\"%s\"", description.replaceAll("\"", "\\\\\""));
        }
    }

    @Override
    public String drgElementSignature(TDRGElement element) {
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return drgElementSignature(reference);
    }

    @Override
    public String drgElementSignature(DRGElementReference<? extends TDRGElement> reference) {
        List<Pair<String, Type>> parameters = drgElementTypeSignature(reference, this::nativeName);
        String decisionSignature = parameters.stream().map(p -> this.nativeFactory.nullableParameter(toNativeType(p.getRight()), p.getLeft())).collect(Collectors.joining(", "));
        return augmentSignature(decisionSignature);
    }

    @Override
    public List<Pair<String, Type>> drgElementTypeSignature(DRGElementReference<? extends TDRGElement> reference, Function<Object, String> nameProducer) {
        TDRGElement element = reference.getElement();
        if (element instanceof TBusinessKnowledgeModel) {
            return bkmParameters((DRGElementReference<TBusinessKnowledgeModel>) reference, nameProducer);
        } else if (element instanceof TDecision) {
            return inputDataParametersClosure((DRGElementReference<TDecision>) reference, nameProducer);
        } else {
            throw new DMNRuntimeException(String.format("Not supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    @Override
    public List<Pair<String, Type>> drgElementTypeSignature(TDRGElement element, Function<Object, String> nameProducer) {
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return drgElementTypeSignature(reference, nameProducer);
    }

    @Override
    public List<Pair<String, Type>> drgElementTypeSignature(TDRGElement element) {
        return drgElementTypeSignature(element, this::nativeName);
    }

    @Override
    public String drgElementArgumentList(TDRGElement element) {
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return drgElementArgumentList(reference);
    }

    @Override
    public String drgElementArgumentList(DRGElementReference<? extends TDRGElement> reference) {
        List<Pair<String, Type>> parameters = drgElementTypeSignature(reference, this::nativeName);
        String arguments = parameters.stream().map(p -> String.format("%s", p.getLeft())).collect(Collectors.joining(", "));
        return augmentArgumentList(arguments);
    }

    @Override
    public String drgElementConvertedArgumentList(TDRGElement element) {
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return drgElementConvertedArgumentList(reference);
    }

    @Override
    public String drgElementConvertedArgumentList(DRGElementReference<? extends TDRGElement> reference) {
        List<Pair<String, Type>> parameters = drgElementTypeSignature(reference, this::nativeName);
        String arguments = parameters.stream().map(p -> String.format("%s", convertDecisionArgument(p.getLeft(), p.getRight()))).collect(Collectors.joining(", "));
        return augmentArgumentList(arguments);
    }

    @Override
    public List<String> drgElementArgumentNameList(TDRGElement element) {
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return drgElementArgumentNameList(reference);
    }

    @Override
    public List<String> drgElementArgumentDisplayNameList(TDRGElement element) {
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return drgElementArgumentDisplayNameList(reference);
    }

    @Override
    public List<String> drgElementArgumentNameList(DRGElementReference<? extends TDRGElement> reference) {
        return drgElementArgumentNameList(reference, this::nativeName);
    }

    @Override
    public List<String> drgElementArgumentDisplayNameList(DRGElementReference<? extends TDRGElement> reference) {
        return drgElementArgumentNameList(reference, this::displayName);
    }

    protected List<String> drgElementArgumentNameList(DRGElementReference<? extends TDRGElement> reference, Function<Object, String> nameProducer) {
        List<Pair<String, Type>> parameters = drgElementTypeSignature(reference, nameProducer);
        return parameters.stream().map(Pair::getLeft).collect(Collectors.toList());
    }

    @Override
    public String elementName(Object obj) {
        if (obj instanceof DRGElementReference) {
            DRGElementReference reference = (DRGElementReference) obj;
            String elementName = this.dmnModelRepository.name(reference.getElement());
            return drgReferenceQualifiedDisplayName(reference.getImportPath(), reference.getModelName(), elementName);
        } else if (obj instanceof TNamedElement) {
            return this.dmnModelRepository.name((TNamedElement) obj);
        }
        throw new DMNRuntimeException(String.format("Variable name cannot be null for '%s'", obj));
    }

    @Override
    public String displayName(Object obj) {
        if (obj instanceof DRGElementReference) {
            DRGElementReference reference = (DRGElementReference) obj;
            String elementName = this.dmnModelRepository.displayName(reference.getElement());
            return drgReferenceQualifiedDisplayName(reference.getImportPath(), reference.getModelName(), elementName);
        } else if (obj instanceof TNamedElement) {
            return this.dmnModelRepository.displayName((TNamedElement) obj);
        }
        throw new DMNRuntimeException(String.format("Variable name cannot be null for '%s'", obj));
    }

    @Override
    public String nativeName(Object obj) {
        if (obj instanceof DRGElementReference) {
            return drgElementReferenceVariableName((DRGElementReference) obj);
        } else if (obj instanceof TNamedElement) {
            return namedElementVariableName((TNamedElement) obj);
        }
        throw new DMNRuntimeException(String.format("Variable name cannot be null for '%s'", obj));
    }

    @Override
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

    @Override
    public String drgElementSignatureExtraCacheWithConversionFromString(TDRGElement element) {
        return drgElementSignatureExtraCache(drgElementSignatureExtraWithConversionFromString(element));
    }

    @Override
    public String drgElementSignatureExtraWithConversionFromString(TDRGElement element) {
        return drgElementSignatureExtra(drgElementSignatureWithConversionFromString(element));
    }

    @Override
    public String drgElementSignatureWithConversionFromString(TDRGElement element) {
        if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure(this.dmnModelRepository.makeDRGElementReference((TDecision) element));
            String decisionSignature = parameters.stream().map(p -> this.nativeFactory.nullableParameter(toStringNativeType(p.getRight()), p.getLeft())).collect(Collectors.joining(", "));
            return augmentSignature(decisionSignature);
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    @Override
    public String drgElementArgumentListExtraCacheWithConversionFromString(TDRGElement element) {
        String arguments = drgElementArgumentListExtraWithConversionFromString(element);
        return drgElementArgumentListExtraCache(arguments);
    }

    private String drgElementArgumentListExtraWithConversionFromString(TDRGElement element) {
        String arguments = drgElementArgumentListWithConversionFromString(element);
        return drgElementArgumentListExtra(arguments);
    }

    @Override
    public String drgElementArgumentListExtraCacheWithConvertedArgumentList(TDRGElement element) {
        String arguments = drgElementArgumentListExtraWithConvertedArgumentList(element);
        return drgElementArgumentListExtraCache(arguments);
    }

    private String drgElementArgumentListExtraWithConvertedArgumentList(TDRGElement element) {
        String arguments = drgElementConvertedArgumentList(element);
        return drgElementArgumentListExtra(arguments);
    }

    @Override
    public String drgElementDefaultArgumentListExtraCacheWithConversionFromString(TDRGElement element) {
        String arguments = drgElementDefaultArgumentListExtraWithConversionFromString(element);
        return drgElementDefaultArgumentListExtraCache(arguments);
    }

    private String drgElementDefaultArgumentListExtraWithConversionFromString(TDRGElement element) {
        String arguments = drgElementArgumentListWithConversionFromString(element);
        return drgElementDefaultArgumentListExtra(arguments);
    }

    @Override
    public String drgElementDefaultArgumentListExtraCache(TDRGElement element) {
        String arguments = drgElementDefaultArgumentListExtra(element);
        return drgElementDefaultArgumentListExtraCache(arguments);
    }

    private String drgElementDefaultArgumentListExtra(TDRGElement element) {
        String arguments = drgElementArgumentList(element);
        return drgElementDefaultArgumentListExtra(arguments);
    }

    @Override
    public String drgElementArgumentListWithConversionFromString(TDRGElement element) {
        if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure(this.dmnModelRepository.makeDRGElementReference((TDecision) element));
            String arguments = parameters.stream().map(p -> String.format("%s", this.nativeFactory.convertDecisionArgumentFromString(p.getLeft(), p.getRight()))).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    @Override
    public String decisionConstructorSignature(TDecision decision) {
        List<DRGElementReference<TDecision>> subDecisionReferences = this.dmnModelRepository.directSubDecisions(decision);
        this.dmnModelRepository.sortNamedElementReferences(subDecisionReferences);
        return subDecisionReferences.stream().map(d -> this.nativeFactory.decisionConstructorParameter(d)).collect(Collectors.joining(", "));
    }

    @Override
    public String decisionConstructorNewArgumentList(TDecision decision) {
        List<DRGElementReference<TDecision>> subDecisionReferences = this.dmnModelRepository.directSubDecisions(decision);
        this.dmnModelRepository.sortNamedElementReferences(subDecisionReferences);
        return subDecisionReferences
                .stream()
                .map(d -> String.format("%s", defaultConstructor(qualifiedName(d))))
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean hasDirectSubDecisions(TDecision decision) {
        return !this.dmnModelRepository.directSubDecisions(decision).isEmpty();
    }

    //
    // NamedElement related functions
    //
    @Override
    public String namedElementVariableName(TNamedElement element) {
        String name = element.getName();
        if (StringUtils.isBlank(name)) {
            throw new DMNRuntimeException(String.format("Variable name cannot be null. ItemDefinition id '%s'", element.getId()));
        }
        return lowerCaseFirst(name);
    }

    //
    // Evaluate method related functions
    //
    @Override
    public String drgElementEvaluateSignature(TDRGElement element) {
        return drgElementSignatureExtraCache(drgElementSignatureExtra(drgElementDirectSignature(element)));
    }

    @Override
    public String drgElementEvaluateArgumentList(TDRGElement element) {
        return drgElementArgumentListExtraCache(drgElementArgumentListExtra(drgElementDirectArgumentList(element)));
    }

    protected String drgElementDirectSignature(TDRGElement element) {
        if (element instanceof TDecision) {
            List<Pair<String, String>> parameters = directInformationRequirementParameters(element);
            String javaParameters = parameters.stream().map(p -> this.nativeFactory.nullableParameter(p.getRight(), p.getLeft())).collect(Collectors.joining(", "));
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
                String parameterName = drgElementReferenceVariableName(reference);
                String parameterNativeType = inputDataType(inputData);
                parameters.add(new Pair<>(parameterName, parameterNativeType));
            } else if (input instanceof TDecision) {
                TDecision subDecision = (TDecision) input;
                String parameterName = drgElementReferenceVariableName(reference);
                String parameterNativeType = drgElementOutputType(subDecision);
                parameters.add(new Pair<>(parameterName, lazyEvaluationType(input, parameterNativeType)));
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
    @Override
    public String startElementCommentText(TDRGElement element) {
        if (element instanceof TDecision) {
            return String.format("Start decision '%s'", element.getName());
        } else if (element instanceof TBusinessKnowledgeModel) {
            return String.format("Start BKM '%s'", element.getName());
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    @Override
    public String endElementCommentText(TDRGElement element) {
        if (element instanceof TDecision) {
            return String.format("End decision '%s'", element.getName());
        } else if (element instanceof TBusinessKnowledgeModel) {
            return String.format("End BKM '%s'", element.getName());
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    @Override
    public String evaluateElementCommentText(TDRGElement element) {
        if (element instanceof TDecision) {
            return String.format("Evaluate decision '%s'", element.getName());
        } else if (element instanceof TBusinessKnowledgeModel) {
            return String.format("Evaluate BKM '%s'", element.getName());
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    @Override
    public QualifiedName drgElementOutputTypeRef(TDRGElement element) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        QualifiedName typeRef = this.dmnModelRepository.outputTypeRef(model, element);
        if (typeRef == null) {
            throw new DMNRuntimeException(String.format("Cannot infer return type for BKM '%s'", element.getName()));
        }
        return typeRef;
    }

    //
    // InputData related functions
    //
    @Override
    public Type toFEELType(TInputData inputData) {
        return this.dmnEnvironmentFactory.toFEELType(inputData);
    }

    private String inputDataType(TInputData inputData) {
        Type type = toFEELType(inputData);
        return toNativeType(type);
    }

    //
    // Invocable  related functions
    //
    @Override
    public List<FormalParameter> invocableFEELParameters(TDRGElement invocable) {
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
    @Override
    public String bkmFunctionName(DRGElementReference<? extends TDRGElement> reference) {
        return bkmFunctionName((TBusinessKnowledgeModel) reference.getElement());
    }

    @Override
    public String bkmFunctionName(TBusinessKnowledgeModel bkm) {
        String name = bkm.getName();
        return bkmFunctionName(name);
    }

    @Override
    public String bkmFunctionName(String name) {
        return nativeFriendlyName(name);
    }

    @Override
    public String bkmQualifiedFunctionName(TBusinessKnowledgeModel bkm) {
        String javaPackageName = qualifiedName(bkm);
        String javaFunctionName = bkmFunctionName(bkm);
        return qualifiedName(javaPackageName, javaFunctionName);
    }

    @Override
    public List<FormalParameter> bkmFEELParameters(TBusinessKnowledgeModel bkm) {
        TDefinitions model = this.dmnModelRepository.getModel(bkm);
        List<FormalParameter> parameters = new ArrayList<>();
        bkm.getEncapsulatedLogic().getFormalParameter().forEach(p -> parameters.add(new FormalParameter(p.getName(), toFEELType(model, QualifiedName.toQualifiedName(model, p.getTypeRef())))));
        return parameters;
    }

    @Override
    public List<String> bkmFEELParameterNames(TBusinessKnowledgeModel bkm) {
        return bkmFEELParameters(bkm).stream().map(FormalParameter::getName).collect(Collectors.toList());
    }

    protected List<Pair<String, Type>> bkmParameters(DRGElementReference<TBusinessKnowledgeModel> reference) {
        return bkmParameters(reference, this::nativeName);
    }

    protected List<Pair<String, Type>> bkmParameters(DRGElementReference<TBusinessKnowledgeModel> reference, Function<Object, String> nameProducer) {
        List<Pair<String, Type>> parameters = new ArrayList<>();
        TBusinessKnowledgeModel bkm = reference.getElement();
        TFunctionDefinition encapsulatedLogic = bkm.getEncapsulatedLogic();
        List<TInformationItem> formalParameters = encapsulatedLogic.getFormalParameter();
        for (TInformationItem parameter : formalParameters) {
            String parameterName = nameProducer.apply(parameter);
            Type parameterType = informationItemType(bkm, parameter);
            parameters.add(new Pair<>(parameterName, parameterType));
        }
        return parameters;
    }

    //
    // Decision Service related functions
    //
    @Override
    public String dsFunctionName(TDecisionService service) {
        String name = service.getName();
        return dsFunctionName(name);
    }

    @Override
    public String dsFunctionName(String name) {
        return nativeFriendlyName(name);
    }

    @Override
    public List<FormalParameter> dsFEELParameters(TDecisionService service) {
        List<FormalParameter> parameters = new ArrayList<>();
        for (TDMNElementReference er : service.getInputData()) {
            TInputData inputData = getDMNModelRepository().findInputDataByRef(service, er.getHref());
            parameters.add(new FormalParameter(inputData.getName(), toFEELType(inputData)));
        }
        for (TDMNElementReference er : service.getInputDecision()) {
            TDecision decision = getDMNModelRepository().findDecisionByRef(service, er.getHref());
            parameters.add(new FormalParameter(decision.getName(), drgElementOutputFEELType(decision)));
        }
        return parameters;
    }

    @Override
    public List<String> dsFEELParameterNames(TDecisionService service) {
        return dsFEELParameters(service).stream().map(FormalParameter::getName).collect(Collectors.toList());
    }

    private List<Pair<String, Type>> dsParameters(DRGElementReference<TDecisionService> reference, Function<Object, String> nameProducer) {
        TDecisionService service = reference.getElement();
        List<Pair<String, Type>> parameters = new ArrayList<>();
        for (TDMNElementReference er : service.getInputData()) {
            TInputData inputData = this.dmnModelRepository.findInputDataByRef(service, er.getHref());
            String importName = this.dmnModelRepository.findImportName(service, er);
            String parameterName = nameProducer.apply(this.dmnModelRepository.makeDRGElementReference(importName, inputData));
            Type parameterType = toFEELType(inputData);
            parameters.add(new Pair<>(parameterName, parameterType));
        }
        for (TDMNElementReference er : service.getInputDecision()) {
            TDecision decision = this.dmnModelRepository.findDecisionByRef(service, er.getHref());
            String importName = this.dmnModelRepository.findImportName(service, er);
            String parameterName = nameProducer.apply(this.dmnModelRepository.makeDRGElementReference(importName, decision));
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
            return this.nativeFactory.convertToItemDefinitionType(paramName, (ItemDefinitionType) type);
        } else {
            return paramName;
        }
    }

    protected Statement convertExpression(Statement statement, Type expectedType) {
        if (!(statement instanceof ExpressionStatement)) {
            return statement;
        }
        String javaExpression = ((ExpressionStatement) statement).getExpression();
        Type expressionType = ((ExpressionStatement) statement).getExpressionType();
        if ("null".equals(javaExpression)) {
            return this.nativeFactory.makeExpressionStatement(javaExpression, expectedType);
        }
        if (expectedType instanceof ListType && expressionType instanceof ListType) {
            Type expectedElementType = ((ListType) expectedType).getElementType();
            Type expressionElementType = ((ListType) expressionType).getElementType();
            if (expectedElementType instanceof ItemDefinitionType) {
                if (expressionElementType.conformsTo(expectedElementType) || expressionElementType == AnyType.ANY || expressionElementType instanceof ContextType) {
                    String conversionText = this.nativeFactory.makeListConversion(javaExpression, (ItemDefinitionType) expectedElementType);
                    return this.nativeFactory.makeExpressionStatement(conversionText, expectedType);
                }
            }
        } else if (expectedType instanceof ListType) {
            return this.nativeFactory.makeExpressionStatement(this.nativeFactory.convertElementToList(javaExpression, expectedType), expectedType);
        } else if (expressionType instanceof ListType) {
            return this.nativeFactory.makeExpressionStatement(this.nativeFactory.convertListToElement(javaExpression, expectedType), expectedType);
        } else if (expectedType instanceof ItemDefinitionType) {
            if (expressionType.conformsTo(expectedType) || expressionType == AnyType.ANY || expressionType instanceof ContextType) {
                return this.nativeFactory.makeExpressionStatement(this.nativeFactory.convertToItemDefinitionType(javaExpression, (ItemDefinitionType) expectedType), expectedType);
            }
        }
        return statement;
    }

    @Override
    public String convertMethodName(TItemDefinition itemDefinition) {
        return this.nativeFactory.convertMethodName(itemDefinition);
    }

    @Override
    public String augmentSignature(String signature) {
        String annotationParameter = this.nativeFactory.parameter(annotationSetClassName(), annotationSetVariableName());
        if (StringUtils.isBlank(signature)) {
            return annotationParameter;
        } else {
            return String.format("%s, %s", signature, annotationParameter);
        }
    }

    @Override
    public String augmentArgumentList(String arguments) {
        String extra = annotationSetVariableName();
        if (StringUtils.isBlank(arguments)) {
            return extra;
        } else {
            return String.format("%s, %s", arguments, extra);
        }
    }

    @Override
    public List<Pair<String, Type>> inputDataParametersClosure(DRGElementReference<TDecision> reference) {
        return inputDataParametersClosure(reference, this::nativeName);
    }

    private List<Pair<String, Type>> inputDataParametersClosure(DRGElementReference<TDecision> reference, Function<Object, String> nameProducer) {
        List<DRGElementReference<TInputData>> allInputDataReferences = inputDataClosure(reference);

        List<Pair<String, Type>> parameters = new ArrayList<>();
        for (DRGElementReference<TInputData> inputData : allInputDataReferences) {
            String parameterName = nameProducer.apply(inputData);
            Type parameterType = toFEELType(inputData.getElement());
            parameters.add(new Pair<>(parameterName, parameterType));
        }
        return parameters;
    }

    @Override
    public List<DRGElementReference<TInputData>> inputDataClosure(DRGElementReference<TDecision> reference) {
        List<DRGElementReference<TInputData>> allInputDataReferences = this.dmnModelRepository.inputDataClosure(reference, this.drgElementFilter);
        this.dmnModelRepository.sortNamedElementReferences(allInputDataReferences);
        return allInputDataReferences;
    }

    @Override
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

    private String drgReferenceQualifiedDisplayName(ImportPath importPath, String modelName, String elementName) {
        Pair<List<String>, String> qName = qualifiedName(importPath, modelName, elementName);

        String modelPrefix = String.join(".", qName.getLeft());
        String localName = qName.getRight();
        if (StringUtils.isBlank(modelPrefix)) {
            return localName;
        } else {
            return String.format("%s.%s", modelPrefix, localName);
        }
    }

    @Override
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
        } else if (this.inputParameters.isSingletonInputData()) {
            if (ImportPath.isEmpty(importPath)) {
                modelName = "";
            }
            return new Pair<>(Collections.singletonList(modelName), elementName);
        } else {
            return new Pair<>(importPath.getPathElements(), elementName);
        }
    }

    @Override
    public String parameterNativeType(TDefinitions model, TInformationItem element) {
        return parameterType(model, element);
    }

    @Override
    public String parameterNativeType(TDRGElement element) {
        String parameterNativeType;
        if (element instanceof TInputData) {
            parameterNativeType = inputDataType((TInputData) element);
        } else if (element instanceof TDecision) {
            parameterNativeType = drgElementOutputType(element);
        } else {
            throw new UnsupportedOperationException(String.format("'%s' is not supported", element.getClass().getSimpleName()));
        }
        return parameterNativeType;
    }

    private String parameterType(TDefinitions model, TInformationItem element) {
        QualifiedName typeRef = this.dmnModelRepository.variableTypeRef(model, element);
        if (typeRef == null) {
            throw new IllegalArgumentException(String.format("Cannot resolve typeRef for element '%s'", element.getName()));
        }
        Type type = toFEELType(model, typeRef);
        return toNativeType(type);
    }

    @Override
    public boolean isLazyEvaluated(DRGElementReference<? extends TDRGElement> reference) {
        return this.isLazyEvaluated(reference.getElement());
    }

    @Override
    public boolean isLazyEvaluated(TDRGElement element) {
        return this.isLazyEvaluated(element.getName());
    }

    @Override
    public boolean isLazyEvaluated(String name) {
        return this.lazyEvaluationOptimisation.isLazyEvaluated(name);
    }

    @Override
    public String lazyEvaluationType(TDRGElement input, String inputNativeType) {
        return isLazyEvaluated(input) ? String.format("%s<%s>", lazyEvalClassName(), inputNativeType) : inputNativeType;
    }

    @Override
    public String lazyEvaluation(String elementName, String nativeName) {
        return isLazyEvaluated(elementName) ? String.format("%s.getOrCompute()", nativeName) : nativeName;
    }

    @Override
    public String pairClassName() {
        return Pair.class.getName();
    }

    @Override
    public String pairComparatorClassName() {
        return PairComparator.class.getName();
    }

    @Override
    public String argumentsClassName() {
        return Arguments.class.getName();
    }

    @Override
    public String argumentsVariableName(TDRGElement element) {
        return String.format("%sArguments_", namedElementVariableName(element));
    }

    @Override
    public String dmnTypeClassName() {
        return DMNType.class.getName();
    }

    @Override
    public String dmnRuntimeExceptionClassName() {
        return DMNRuntimeException.class.getName();
    }

    @Override
    public String lazyEvalClassName() {
        return LazyEval.class.getName();
    }

    @Override
    public String contextClassName() {
        return Context.class.getName();
    }

    @Override
    public String annotationSetClassName() {
        return AnnotationSet.class.getName();
    }

    @Override
    public String annotationSetVariableName() {
        return "annotationSet_";
    }

    @Override
    public String eventListenerClassName() {
        return EventListener.class.getName();
    }

    @Override
    public String eventListenerVariableName() {
        return "eventListener_";
    }

    @Override
    public String defaultEventListenerClassName() {
        return NopEventListener.class.getName();
    }

    @Override
    public String loggingEventListenerClassName() {
        return LoggingEventListener.class.getName();
    }

    @Override
    public String externalExecutorClassName() {
        return ExternalFunctionExecutor.class.getName();
    }

    @Override
    public String externalExecutorVariableName() {
        return "externalExecutor_";
    }

    @Override
    public String defaultExternalExecutorClassName() {
        return DefaultExternalFunctionExecutor.class.getName();
    }

    @Override
    public String cacheInterfaceName() {
        return Cache.class.getName();
    }

    @Override
    public String cacheVariableName() {
        return "cache_";
    }

    @Override
    public String defaultCacheClassName() {
        return DefaultCache.class.getName();
    }

    @Override
    public String drgElementSignatureExtra(DRGElementReference<? extends TDRGElement> reference) {
        String signature = drgElementSignature(reference);
        return drgElementSignatureExtra(signature);
    }

    @Override
    public String drgElementSignatureExtra(TDRGElement element) {
        String signature = drgElementSignature(element);
        return drgElementSignatureExtra(signature);
    }

    @Override
    public String drgElementSignatureExtra(String signature) {
        String listenerParameter = this.nativeFactory.parameter(eventListenerClassName(), eventListenerVariableName());
        String executorParameter = this.nativeFactory.parameter(externalExecutorClassName(), externalExecutorVariableName());
        if (StringUtils.isBlank(signature)) {
            return String.format("%s, %s", listenerParameter, executorParameter);
        } else {
            return String.format("%s, %s, %s", signature, listenerParameter, executorParameter);
        }
    }

    @Override
    public String drgElementArgumentListExtra(DRGElementReference<? extends TDRGElement> reference) {
        String arguments = drgElementArgumentList(reference);
        return drgElementArgumentListExtra(arguments);
    }

    @Override
    public String drgElementArgumentListExtra(TDRGElement element) {
        String arguments = drgElementArgumentList(element);
        return drgElementArgumentListExtra(arguments);
    }

    @Override
    public String drgElementArgumentListExtra(String arguments) {
        if (StringUtils.isBlank(arguments)) {
            return String.format("%s, %s", eventListenerVariableName(), externalExecutorVariableName());
        } else {
            return String.format("%s, %s, %s", arguments, eventListenerVariableName(), externalExecutorVariableName());
        }
    }

    @Override
    public String drgElementDefaultArgumentListExtra(String arguments) {
        String loggerArgument = constructor(loggingEventListenerClassName(), "LOGGER");
        String executorArgument = defaultConstructor(defaultExternalExecutorClassName());
        if (StringUtils.isBlank(arguments)) {
            return String.format("%s, %s", loggerArgument, executorArgument);
        } else {
            return String.format("%s, %s, %s", arguments, loggerArgument, executorArgument);
        }
    }

    @Override
    public boolean isCaching() {
        return this.inputParameters.isCaching();
    }

    @Override
    public boolean isCached(String elementName) {
        if (!this.inputParameters.isCaching()) {
            return false;
        }
        return this.cachedElements.contains(elementName);
    }

    @Override
    public boolean isParallelStream() {
        return this.inputParameters.isParallelStream();
    }

    @Override
    public String getStream() {
        return this.isParallelStream() ? "parallelStream()" : "stream()";
    }

    @Override
    public String drgElementSignatureExtraCache(DRGElementReference<? extends TDRGElement> reference) {
        String signature = drgElementSignatureExtra(reference);
        return drgElementSignatureExtraCache(signature);
    }

    @Override
    public String drgElementSignatureExtraCache(TDRGElement element) {
        String signature = drgElementSignatureExtra(element);
        return drgElementSignatureExtraCache(signature);
    }

    @Override
    public String drgElementSignatureExtraCache(String signature) {
        String cacheParameter = this.nativeFactory.parameter(cacheInterfaceName(), cacheVariableName());
        if (StringUtils.isBlank(signature)) {
            return cacheParameter;
        } else {
            return String.format("%s, %s", signature, cacheParameter);
        }
    }

    @Override
    public String drgElementArgumentListExtraCache(DRGElementReference<? extends TDRGElement> reference) {
        String arguments = drgElementArgumentListExtra(reference);
        return drgElementArgumentListExtraCache(arguments);
    }

    @Override
    public String drgElementArgumentListExtraCache(TDRGElement element) {
        String arguments = drgElementArgumentListExtra(element);
        return drgElementArgumentListExtraCache(arguments);
    }

    @Override
    public String drgElementArgumentListExtraCache(String arguments) {
        if (StringUtils.isBlank(arguments)) {
            return String.format("%s", cacheVariableName());
        } else {
            return String.format("%s, %s", arguments, cacheVariableName());
        }
    }

    @Override
    public String drgElementDefaultArgumentListExtraCache(String arguments) {
        String defaultCacheArgument = defaultConstructor(defaultCacheClassName());
        if (StringUtils.isBlank(arguments)) {
            return defaultCacheArgument;
        } else {
            return String.format("%s, %s", arguments, defaultCacheArgument);
        }
    }

    @Override
    public String drgElementAnnotationClassName() {
        return com.gs.dmn.runtime.annotation.DRGElement.class.getName();
    }

    @Override
    public String elementKindAnnotationClassName() {
        return DRGElementKind.class.getName();
    }

    @Override
    public String expressionKindAnnotationClassName() {
        return ExpressionKind.class.getName();
    }

    @Override
    public String drgElementMetadataClassName() {
        return com.gs.dmn.runtime.listener.DRGElement.class.getName();
    }

    @Override
    public String drgElementMetadataFieldName() {
        return "DRG_ELEMENT_METADATA";
    }

    @Override
    public String drgRuleMetadataClassName() {
        return com.gs.dmn.runtime.listener.Rule.class.getName();
    }

    @Override
    public String drgRuleMetadataFieldName() {
        return "drgRuleMetadata";
    }

    @Override
    public String assertClassName() {
        return Assert.class.getName();
    }

    //
    // Decision Table related functions
    //
    @Override
    public String defaultValue(TDRGElement element) {
        return this.expressionToNativeTransformer.defaultValue(element);
    }

    @Override
    public String defaultValue(TDRGElement element, TOutputClause output) {
        return this.expressionToNativeTransformer.defaultValue(element, output);
    }

    @Override
    public String condition(TDRGElement element, TDecisionRule rule) {
        return this.expressionToNativeTransformer.condition(element, rule);
    }

    @Override
    public String outputEntryToNative(TDRGElement element, TLiteralExpression outputEntryExpression, int outputIndex) {
        return this.expressionToNativeTransformer.outputEntryToNative(element, outputEntryExpression, outputIndex);
    }

    @Override
    public String outputClauseName(TDRGElement element, TOutputClause output) {
        return this.dmnModelRepository.outputClauseName(element, output);
    }

    @Override
    public String outputClauseClassName(TDRGElement element, TOutputClause outputClause, int index) {
        return this.expressionToNativeTransformer.outputClauseClassName(element, outputClause, index);
    }

    @Override
    public String outputClauseVariableName(TDRGElement element, TOutputClause outputClause) {
        return this.expressionToNativeTransformer.outputClauseVariableName(element, outputClause);
    }

    @Override
    public String outputClausePriorityVariableName(TDRGElement element, TOutputClause outputClause) {
        return this.expressionToNativeTransformer.outputClausePriorityVariableName(element, outputClause);
    }

    @Override
    public String getter(TDRGElement element, TOutputClause output) {
        return this.expressionToNativeTransformer.getter(element, output);
    }

    @Override
    public String setter(TDRGElement element, TOutputClause output) {
        return this.expressionToNativeTransformer.setter(element, output);
    }

    @Override
    public Integer priority(TDRGElement element, TLiteralExpression literalExpression, int outputIndex) {
        return this.expressionToNativeTransformer.priority(element, literalExpression, outputIndex);
    }

    @Override
    public String priorityGetter(TDRGElement element, TOutputClause output) {
        return this.expressionToNativeTransformer.priorityGetter(element, output);
    }

    @Override
    public String prioritySetter(TDRGElement element, TOutputClause output) {
        return this.expressionToNativeTransformer.prioritySetter(element, output);
    }

    @Override
    public HitPolicy hitPolicy(TDRGElement element) {
        return this.expressionToNativeTransformer.hitPolicy(element);
    }

    @Override
    public String aggregation(TDecisionTable decisionTable) {
        return this.expressionToNativeTransformer.aggregation(decisionTable);
    }

    @Override
    public String aggregator(TDRGElement element, TDecisionTable decisionTable, TOutputClause outputClause, String ruleOutputListVariable) {
        return this.expressionToNativeTransformer.aggregator(element, decisionTable, outputClause, ruleOutputListVariable);
    }

    @Override
    public String annotation(TDRGElement element, TDecisionRule rule) {
        return this.expressionToNativeTransformer.annotation(element, rule);
    }

    @Override
    public String annotationEscapedText(TDecisionRule rule) {
        return this.expressionToNativeTransformer.annotationEscapedText(rule);
    }

    @Override
    public String escapeInString(String text) {
        return StringEscapeUtil.escapeInString(text);
    }

    //
    // Rule related functions
    //
    @Override
    public String ruleOutputClassName(TDRGElement element) {
        return this.expressionToNativeTransformer.ruleOutputClassName(element);
    }

    @Override
    public String ruleId(List<TDecisionRule> rules, TDecisionRule rule) {
        return this.expressionToNativeTransformer.ruleId(rules, rule);
    }

    @Override
    public String abstractRuleOutputClassName() {
        return this.expressionToNativeTransformer.abstractRuleOutputClassName();
    }

    @Override
    public String ruleOutputListClassName() {
        return this.expressionToNativeTransformer.ruleOutputListClassName();
    }

    @Override
    public String ruleSignature(TDecision decision) {
        return this.expressionToNativeTransformer.ruleSignature(decision);
    }

    @Override
    public String ruleArgumentList(TDecision decision) {
        return this.expressionToNativeTransformer.ruleArgumentList(decision);
    }

    @Override
    public String ruleSignature(TBusinessKnowledgeModel bkm) {
        return this.expressionToNativeTransformer.ruleSignature(bkm);
    }

    @Override
    public String ruleArgumentList(TBusinessKnowledgeModel bkm) {
        return this.expressionToNativeTransformer.ruleArgumentList(bkm);
    }

    @Override
    public String hitPolicyAnnotationClassName() {
        return this.expressionToNativeTransformer.hitPolicyAnnotationClassName();
    }

    @Override
    public String ruleAnnotationClassName() {
        return this.expressionToNativeTransformer.ruleAnnotationClassName();
    }

    //
    // Expression related functions
    //
    @Override
    public Type expressionType(TDRGElement element, JAXBElement<? extends TExpression> jElement, Environment environment) {
        return this.dmnEnvironmentFactory.expressionType(element, jElement, environment);
    }

    @Override
    public Type expressionType(TDRGElement element, TExpression expression, Environment environment) {
        return this.dmnEnvironmentFactory.expressionType(element, expression, environment);
    }

    @Override
    public Type convertType(Type type, boolean convertToContext) {
        return this.dmnEnvironmentFactory.convertType(type, convertToContext);
    }

    @Override
    public Statement expressionToNative(TDRGElement element) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        TExpression expression = this.dmnModelRepository.expression(element);
        if (expression instanceof TContext) {
            return this.expressionToNativeTransformer.contextExpressionToNative(element, (TContext) expression);
        } else if (expression instanceof TLiteralExpression) {
            Statement statement = this.expressionToNativeTransformer.literalExpressionToNative(element, ((TLiteralExpression) expression).getText());
            Type expectedType = drgElementOutputFEELType(element);
            return convertExpression(statement, expectedType);
        } else if (expression instanceof TInvocation) {
            return this.expressionToNativeTransformer.invocationExpressionToNative(element, (TInvocation) expression);
        } else if (expression instanceof TRelation) {
            return this.expressionToNativeTransformer.relationExpressionToNative(element, (TRelation) expression);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s'", expression.getClass().getSimpleName()));
        }
    }

    @Override
    public Statement expressionToNative(TDRGElement element, TExpression expression, Environment environment) {
        if (expression instanceof TContext) {
            return this.expressionToNativeTransformer.contextExpressionToNative(element, (TContext) expression, environment);
        } else if (expression instanceof TFunctionDefinition) {
            return this.expressionToNativeTransformer.functionDefinitionToNative(element, (TFunctionDefinition) expression, environment);
        } else if (expression instanceof TLiteralExpression) {
            return this.expressionToNativeTransformer.literalExpressionToNative(element, ((TLiteralExpression) expression).getText(), environment);
        } else if (expression instanceof TInvocation) {
            return this.expressionToNativeTransformer.invocationExpressionToNative(element, (TInvocation) expression, environment);
        } else if (expression instanceof TRelation) {
            return this.expressionToNativeTransformer.relationExpressionToNative(element, (TRelation) expression, environment);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s'", expression.getClass().getSimpleName()));
        }
    }

    @Override
    public String literalExpressionToNative(TDRGElement element, String expressionText) {
        Statement statement = this.expressionToNativeTransformer.literalExpressionToNative(element, expressionText);
        return ((ExpressionStatement) statement).getExpression();
    }

    @Override
    public String functionDefinitionToNative(TDRGElement element, FunctionDefinition functionDefinition, boolean convertTypeToContext, String body) {
        return this.expressionToNativeTransformer.functionDefinitionToNative(element, functionDefinition, body, convertTypeToContext);
    }

    @Override
    public boolean isCompoundStatement(Statement stm) {
        return stm instanceof CompoundStatement;
    }

    //
    // Type related functions
    //
    @Override
    public boolean isComplexType(Type type) {
        return type instanceof ItemDefinitionType
                || type instanceof ListType && ((ListType) type).getElementType() instanceof ItemDefinitionType;
    }

    @Override
    public boolean isDateTimeType(Type type) {
        return isSimpleDateTimeType(type) || (type instanceof ListType && isSimpleDateTimeType(((ListType) type).getElementType()));
    }

    private boolean isSimpleDateTimeType(Type type) {
        return isDate(type) || isTime(type) || isDateTime(type) || isDurationTime(type);
    }

    private boolean isDate(Type type) {
        return type instanceof DateType;
    }

    private boolean isTime(Type type) {
        return type instanceof TimeType;
    }

    private boolean isDateTime(Type type) {
        return type instanceof DateTimeType;
    }

    private boolean isDurationTime(Type type) {
        return type instanceof DurationType;
    }

    @Override
    public Type toFEELType(TDefinitions model, String typeName) {
        return this.dmnEnvironmentFactory.toFEELType(model, typeName);
    }

    @Override
    public Type toFEELType(TDefinitions model, QualifiedName typeRef) {
        return this.dmnEnvironmentFactory.toFEELType(model, typeRef);
    }

    @Override
    public Type toFEELType(TItemDefinition itemDefinition) {
        return this.dmnEnvironmentFactory.toFEELType(itemDefinition);
    }

    //
    // Common functions
    //
    @Override
    public String toNativeType(TDecision decision) {
        String javaType = this.nativeTypeMemoizer.get(decision);
        if (javaType == null) {
            javaType = toNativeTypeNoCache(decision);
            this.nativeTypeMemoizer.put(decision, javaType);
        }
        return javaType;
    }

    private String toNativeTypeNoCache(TDecision decision) {
        Type type = this.drgElementOutputFEELType(decision);
        return toNativeType(type);
    }

    @Override
    public String toStringNativeType(Type type) {
        return toNativeType(StringType.STRING);
    }

    @Override
    public String toNativeType(Type type) {
        String javaType = this.nativeTypeMemoizer.get(type);
        if (javaType == null) {
            javaType = toNativeTypeNoCache(type);
            this.nativeTypeMemoizer.put(type, javaType);
        }
        return javaType;
    }

    private String toNativeTypeNoCache(Type type) {
        if (type instanceof NamedType) {
            String typeName = ((NamedType) type).getName();
            if (StringUtils.isBlank(typeName)) {
                throw new DMNRuntimeException(String.format("Missing type name in '%s'", type));
            }
            String primitiveType = this.nativeTypeFactory.toNativeType(typeName);
            if (!StringUtils.isBlank(primitiveType)) {
                return primitiveType;
            } else {
                if (type instanceof ItemDefinitionType) {
                    String modelName = ((ItemDefinitionType) type).getModelName();
                    return qualifiedName(nativeTypePackageName(modelName), upperCaseFirst(typeName));
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
                String elementType = toNativeType(((ListType) type).getElementType());
                return makeListType(DMNToJavaTransformer.LIST_TYPE, elementType);
            }
        } else if (type instanceof FunctionType) {
            if (type instanceof FEELFunctionType) {
                if (((FEELFunctionType) type).isExternal()) {
                    String returnType = toNativeType(((FunctionType) type).getReturnType());
                    return makeFunctionType(JavaExternalFunction.class.getName(), returnType);
                } else {
                    String returnType = toNativeType(((FunctionType) type).getReturnType());
                    return makeFunctionType(LambdaExpression.class.getName(), returnType);
                }
            } else if (type instanceof DMNFunctionType) {
                if (isFEELFunction(((DMNFunctionType) type).getKind())) {
                    String returnType = toNativeType(((FunctionType) type).getReturnType());
                    return makeFunctionType(LambdaExpression.class.getName(), returnType);
                } else if (isJavaFunction(((DMNFunctionType) type).getKind())) {
                    String returnType = toNativeType(((FunctionType) type).getReturnType());
                    return makeFunctionType(JavaExternalFunction.class.getName(), returnType);
                }
                throw new DMNRuntimeException(String.format("Type %s is not supported yet", type));
            }
            throw new DMNRuntimeException(String.format("Type %s is not supported yet", type));
        }
        throw new IllegalArgumentException(String.format("Type '%s' is not supported yet", type));
    }

    @Override
    public String makeListType(String listType, String elementType) {
        return String.format("%s<%s>", listType, elementType);
    }

    @Override
    public String makeListType(String listType) {
        return String.format("%s<? extends Object>", listType);
    }

    protected String makeFunctionType(String name, String returnType) {
        return String.format("%s<%s>", name, returnType);
    }

    @Override
    public String qualifiedName(String pkg, String name) {
        if (StringUtils.isBlank(pkg)) {
            return name;
        } else {
            return pkg + "." + name;
        }
    }

    @Override
    public String qualifiedName(DRGElementReference<? extends TDRGElement> reference) {
        return qualifiedName(reference.getElement());
    }

    @Override
    public String qualifiedName(TDRGElement element) {
        TDefinitions definitions = this.dmnModelRepository.getModel(element);
        String pkg = this.nativeModelPackageName(definitions.getName());
        String name = drgElementClassName(element);
        if (StringUtils.isBlank(pkg)) {
            return name;
        } else {
            return pkg + "." + name;
        }
    }

    @Override
    public String getterName(String name) {
        return String.format("get%s", this.upperCaseFirst(name));
    }

    @Override
    public String getter(String name) {
        return String.format("%s()", getterName(name));
    }

    @Override
    public String setter(String name) {
        return String.format("set%s", this.upperCaseFirst(name));
    }

    @Override
    public String contextGetter(String name) {
        return String.format("get(\"%s\")", name);
    }

    @Override
    public String contextSetter(String name) {
        return String.format("put(\"%s\", ", name);
    }

    @Override
    public String nativeFriendlyVariableName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new DMNRuntimeException("Cannot build variable name from empty string");
        }
        name = this.dmnModelRepository.removeSingleQuotes(name);
        String[] parts = name.split("\\.");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            String firstChar = Character.toString(Character.toLowerCase(part.charAt(0)));
            String partName = nativeFriendlyName(part.length() == 1 ? firstChar : firstChar + part.substring(1));
            if (i != 0) {
                result.append(".");
            }
            result.append(partName);
        }
        return result.toString();
    }

    @Override
    public String nativeFriendlyName(String name) {
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

    @Override
    public String upperCaseFirst(String name) {
        name = this.dmnModelRepository.removeSingleQuotes(name);
        String firstChar = Character.toString(Character.toUpperCase(name.charAt(0)));
        return nativeFriendlyName(name.length() == 1 ? firstChar : firstChar + name.substring(1));
    }

    @Override
    public String lowerCaseFirst(String name) {
        name = this.dmnModelRepository.removeSingleQuotes(name);
        String firstChar = Character.toString(Character.toLowerCase(name.charAt(0)));
        return nativeFriendlyName(name.length() == 1 ? firstChar : firstChar + name.substring(1));
    }

    @Override
    public String nativeModelPackageName(String modelName) {
        if (modelName != null && modelName.endsWith(DMNConstants.DMN_FILE_EXTENSION)) {
            modelName = modelName.substring(0, modelName.length() - 4);
        }
        if (this.onePackage) {
            modelName = "";
        }

        String modelPackageName = javaModelName(modelName);
        String elementPackageName = this.inputParameters.getJavaRootPackage();
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
        for (int ch : name.codePoints().toArray()) {
            if (Character.isJavaIdentifierPart(ch)) {
                if (skippedPrevious && !first) {
                    result.append('_');
                }
                result.append((char) ch);
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

    @Override
    public String nativeRootPackageName() {
        String javaRootPackage = this.inputParameters.getJavaRootPackage();
        if (javaRootPackage == null) {
            return "";
        } else {
            return javaRootPackage;
        }
    }

    @Override
    public String nativeTypePackageName(String modelName) {
        String modelPackageName = nativeModelPackageName(modelName);
        if (StringUtils.isBlank(modelPackageName)) {
            return DMNToJavaTransformer.DATA_PACKAGE;
        } else {
            return modelPackageName + "." + DMNToJavaTransformer.DATA_PACKAGE;
        }
    }

    @Override
    public DRGElementKind elementKind(TDRGElement element) {
        return DRGElementKind.kindByClass(element.getClass());
    }

    @Override
    public ExpressionKind expressionKind(TDRGElement element) {
        TExpression expression = this.dmnModelRepository.expression(element);
        if (expression != null) {
            return ExpressionKind.kindByClass(expression.getClass());
        }
        return ExpressionKind.OTHER;
    }

    @Override
    public String asEmptyList(TDRGElement element) {
        Type type = drgElementOutputFEELType(element);
        if (type instanceof ListType) {
            return this.nativeFactory.asList(((ListType) type).getElementType(), "");
        }
        throw new DMNRuntimeException(String.format("Expected List Type found '%s' instead", type));
    }

    @Override
    public String asList(Type elementType, String exp) {
        return this.nativeFactory.asList(elementType, exp);
    }

    //
    // Environment related functions
    //
    @Override
    public Environment makeEnvironment(TDRGElement element) {
        return this.dmnEnvironmentFactory.makeEnvironment(element);
    }

    @Override
    public Environment makeEnvironment(TDRGElement element, Environment parentEnvironment) {
        return this.dmnEnvironmentFactory.makeEnvironment(element, parentEnvironment);
    }

    @Override
    public Environment makeInputEntryEnvironment(TDRGElement element, Expression inputExpression) {
        return this.dmnEnvironmentFactory.makeInputEntryEnvironment(element, inputExpression);
    }

    @Override
    public Environment makeOutputEntryEnvironment(TDRGElement element, EnvironmentFactory environmentFactory) {
        return this.dmnEnvironmentFactory.makeOutputEntryEnvironment(element, environmentFactory);
    }

    @Override
    public Pair<Environment, Map<TContextEntry, Expression>> makeContextEnvironment(TDRGElement element, TContext context, Environment parentEnvironment) {
        return this.dmnEnvironmentFactory.makeContextEnvironment(element, context, parentEnvironment);
    }

    @Override
    public Environment makeRelationEnvironment(TNamedElement element, TRelation relation, Environment environment) {
        return this.dmnEnvironmentFactory.makeRelationEnvironment(element, relation, environment);
    }

    @Override
    public Environment makeFunctionDefinitionEnvironment(TNamedElement element, TFunctionDefinition functionDefinition, Environment parentEnvironment) {
        return this.dmnEnvironmentFactory.makeFunctionDefinitionEnvironment(element, functionDefinition, parentEnvironment);
    }

    @Override
    public boolean isFEELFunction(TFunctionKind kind) {
        return kind == null || kind == TFunctionKind.FEEL;
    }

    @Override
    public boolean isJavaFunction(TFunctionKind kind) {
        return kind == TFunctionKind.JAVA;
    }

    //
    // .proto related functions
    //
    @Override
    public boolean isGenerateProto() {
        return this.isGenerateProtoMessages() || this.isGenerateProtoServices();
    }

    @Override
    public boolean isGenerateProtoMessages() {
        return this.inputParameters.isGenerateProtoMessages();
    }

    @Override
    public boolean isGenerateProtoServices() {
        return this.inputParameters.isGenerateProtoServices();
    }

    @Override
    public String getProtoVersion() {
        String protoVersion = this.inputParameters.getProtoVersion();
        if ("proto3".equals(protoVersion)) {
            return protoVersion;
        }
        throw new DMNRuntimeException(String.format("Illegal proto version '%s'", protoVersion));
    }

    @Override
    public String protoPackage(String javaPackageName) {
        if (StringUtils.isBlank(javaPackageName)) {
            return "proto";
        } else {
            return javaPackageName + ".proto";
        }
    }

    @Override
    public Pair<Pair<List<MessageType>, List<MessageType>>, List<Service>> dmnToProto(TDefinitions definitions) {
        return this.protoFactory.dmnToProto(definitions);
    }

    @Override
    public String drgElementSignatureProto(TDRGElement element) {
        String decisionSignature = this.protoFactory.drgElementSignatureProto(element);
        return augmentSignature(decisionSignature);
    }

    @Override
    public String drgElementSignatureExtraCacheProto(TDRGElement element) {
        String decisionSignature = drgElementSignatureExtraProto(element);
        return drgElementSignatureExtraCache(decisionSignature);
    }

    private String drgElementSignatureExtraProto(TDRGElement element) {
        String signature = drgElementSignatureProto(element);
        return drgElementSignatureExtra(signature);
    }

    @Override
    public String drgElementArgumentListProto(TDRGElement element) {
        return augmentArgumentList(this.protoFactory.drgElementArgumentListProto(element));
    }

    @Override
    public String drgElementArgumentListExtraCacheProto(TDRGElement element) {
        String arguments = drgElementArgumentListExtraProto(element);
        return drgElementArgumentListExtraCache(arguments);
    }

    private String drgElementArgumentListExtraProto(TDRGElement element) {
        String arguments = drgElementArgumentListProto(element);
        return drgElementArgumentListExtra(arguments);
    }

    @Override
    public String drgElementDefaultArgumentListExtraCacheProto(TDRGElement element) {
        String arguments = drgElementDefaultArgumentListExtraProto(element);
        return drgElementDefaultArgumentListExtraCache(arguments);
    }

    private String drgElementDefaultArgumentListExtraProto(TDRGElement element) {
        String arguments = drgElementArgumentListProto(element);
        return drgElementDefaultArgumentListExtra(arguments);
    }

    @Override
    public String convertProtoMember(String source, TItemDefinition parent, TItemDefinition child, boolean staticContext) {
        return this.nativeFactory.convertProtoMember(source, parent, child, staticContext);
    }

    @Override
    public String convertMemberToProto(String source, String sourceType, TItemDefinition child, boolean staticContext) {
        return this.nativeFactory.convertMemberToProto(source, sourceType, child, staticContext);
    }

    @Override
    public String qualifiedProtoMessageName(TItemDefinition itemDefinition) {
        return this.protoFactory.qualifiedProtoMessageName(itemDefinition);
    }

    @Override
    public String qualifiedRequestMessageName(TDRGElement element) {
        return this.protoFactory.qualifiedRequestMessageName(element);
    }

    @Override
    public String qualifiedResponseMessageName(TDRGElement element) {
        return this.protoFactory.qualifiedResponseMessageName(element);
    }

    @Override
    public String requestVariableName(TDRGElement element) {
        return this.protoFactory.requestVariableName(element);
    }

    @Override
    public String responseVariableName(TDRGElement element) {
        return this.protoFactory.responseVariableName(element);
    }

    @Override
    public String namedElementVariableNameProto(TNamedElement element) {
        return this.protoFactory.namedElementVariableNameProto(element);
    }

    @Override
    public String drgElementOutputTypeProto(TDRGElement element) {
        Type type = drgElementOutputFEELType(element);
        return this.protoFactory.toNativeProtoType(type);
    }

    @Override
    public String qualifiedNativeProtoType(TItemDefinition itemDefinition) {
        Type type = toFEELType(itemDefinition);
        return this.protoFactory.toNativeProtoType(type);
    }

    @Override
    public boolean isProtoReference(TItemDefinition itemDefinition) {
        Type type = toFEELType(itemDefinition);
        return isProtoReference(type);
    }

    @Override
    public boolean isProtoReference(Type type) {
        return isComplexType(type) || type instanceof ListType;
    }

    @Override
    public String protoFieldName(TNamedElement element) {
        return this.protoFactory.protoFieldName(element);
    }

    @Override
    public String extractParameterFromRequestMessage(TDRGElement element, Pair<String, Type> parameter, boolean staticContext) {
        return this.nativeFactory.extractParameterFromRequestMessage(element, parameter, staticContext);
    }

    @Override
    public String convertValueToProtoNativeType(String value, Type type, boolean staticContext) {
        return this.nativeFactory.convertValueToProtoNativeType(value, type, staticContext);
    }

    @Override
    public String extractMemberFromProtoValue(String protoValue, Type type, boolean staticContext) {
        return this.nativeFactory.extractMemberFromProtoValue(protoValue, type, staticContext);
    }
}