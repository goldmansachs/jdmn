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

import com.gs.dmn.*;
import com.gs.dmn.ast.*;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.Environment;
import com.gs.dmn.context.environment.EnvironmentFactory;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.AnyType;
import com.gs.dmn.el.analysis.semantics.type.NullType;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.el.synthesis.ELTranslator;
import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.lib.StringEscapeUtil;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.runtime.*;
import com.gs.dmn.runtime.annotation.AnnotationSet;
import com.gs.dmn.runtime.annotation.DRGElementKind;
import com.gs.dmn.runtime.annotation.ExpressionKind;
import com.gs.dmn.runtime.annotation.HitPolicy;
import com.gs.dmn.runtime.cache.Cache;
import com.gs.dmn.runtime.cache.DefaultCache;
import com.gs.dmn.runtime.discovery.ModelElementRegistry;
import com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor;
import com.gs.dmn.runtime.external.ExternalFunctionExecutor;
import com.gs.dmn.runtime.external.JavaExternalFunction;
import com.gs.dmn.runtime.external.JavaFunctionInfo;
import com.gs.dmn.runtime.listener.EventListener;
import com.gs.dmn.runtime.listener.*;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;

public class BasicDMNToJavaTransformer implements BasicDMNToNativeTransformer<Type, DMNContext> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BasicDMNToJavaTransformer.class);

    private final DMNDialectDefinition<?, ?, ?, ?, ?, ?> dialect;
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
    protected ELTranslator<Type, DMNContext> feelTranslator;
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

    protected void setDMNEnvironmentFactory(BasicDMNToNativeTransformer<Type, DMNContext> transformer) {
        this.dmnEnvironmentFactory = new StandardDMNEnvironmentFactory(transformer);
    }

    protected void setNativeFactory(BasicDMNToNativeTransformer<Type, DMNContext> transformer) {
        this.nativeFactory = new JavaFactory(transformer);
    }

    protected void setExpressionToNativeTransformer(BasicDMNToNativeTransformer<Type, DMNContext> transformer) {
        this.expressionToNativeTransformer = new DMNExpressionToNativeTransformer(transformer);
    }

    private void setFEELTranslator(BasicDMNToNativeTransformer<Type, DMNContext> transformer) {
        this.feelTranslator = new FEELTranslator(transformer);
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
    public ELTranslator<Type, DMNContext> getFEELTranslator() {
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

    @Override
    public boolean isStrongTyping() {
        return this.inputParameters.isStrongTyping();
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
        if (type instanceof ListType listType && listType.getElementType() instanceof ItemDefinitionType) {
            type = listType.getElementType();
        }
        if (type instanceof ItemDefinitionType definitionType) {
            return upperCaseFirst(definitionType.getName());
        } else {
            throw new IllegalArgumentException("Unexpected type '%s' for ItemDefinition '%s'".formatted(type, itemDefinition.getName()));
        }
    }

    @Override
    public String itemDefinitionNativeQualifiedInterfaceName(TItemDefinition itemDefinition) {
        Type type = toFEELType(itemDefinition);
        return toNativeType(type);
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
    public List<String> itemDefinitionComplexComponents(TItemDefinition itemDefinition) {
        Type type = toFEELType(itemDefinition);
        Set<String> nameSet = new LinkedHashSet<>();
        addClassName(type, nameSet, false);
        return sortNames(nameSet);
    }

    @Override
    public String getter(TItemDefinition itemDefinition) {
        return getter(namedElementVariableName(itemDefinition));
    }

    @Override
    public String setter(TItemDefinition itemDefinition, String args) {
        return setter(namedElementVariableName(itemDefinition), args);
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
    public String protoSetter(TItemDefinition itemDefinition, String args) {
        return this.protoSetter(itemDefinition, toFEELType(itemDefinition), args);
    }

    @Override
    public String protoSetter(TDRGElement drgElement, String args) {
        return this.protoSetter(drgElement, drgElementOutputFEELType(drgElement), args);
    }

    private String protoSetter(TNamedElement namedElement, Type type, String args) {
        return this.protoFactory.protoSetter(namedElementVariableName(namedElement), type, args);
    }

    //
    // TInformationItem related functions
    //
    private Type informationItemType(TBusinessKnowledgeModel bkm, TInformationItem element) {
        TDefinitions model = this.dmnModelRepository.getModel(bkm);
        String typeRef = QualifiedName.toName(element.getTypeRef());
        Type type = null;
        if (!StringUtils.isEmpty(typeRef)) {
            type = toFEELType(model, QualifiedName.toQualifiedName(model, typeRef));
        }
        return type;
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
    public String drgElementClassName(DRGElementReference<? extends TDRGElement> reference) {
        return drgElementClassName(reference.getElement());
    }

    @Override
    public String drgElementClassName(TDRGElement element) {
        return upperCaseFirst(element.getName());
    }

    @Override
    public String drgElementReferenceVariableName(DRGElementReference<? extends TDRGElement> reference) {
        String name = reference.getElementName();
        if (name == null) {
            throw new DMNRuntimeException("Variable name cannot be null. Decision id '%s'".formatted(reference.getElement().getId()));
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
    public String drgElementOutputInterfaceName(TDRGElement element) {
        Type type = drgElementOutputFEELType(element);
        if (type instanceof ListType listType) {
            type = listType.getElementType();
        }
        return toNativeType(type);
    }

    @Override
    public String drgElementOutputClassName(TDRGElement element) {
        Type type = drgElementOutputFEELType(element);
        if (type instanceof ListType listType) {
            type = listType.getElementType();
        }
        String interfaceName = toNativeType(type);
        return type instanceof ItemDefinitionType ? itemDefinitionNativeClassName(interfaceName) : interfaceName;
    }

    @Override
    public Type drgElementOutputFEELType(TDRGElement element) {
        return this.dmnEnvironmentFactory.drgElementOutputFEELType(element);
    }

    @Override
    public Type drgElementOutputFEELType(TDRGElement element, DMNContext context) {
        return this.dmnEnvironmentFactory.drgElementOutputFEELType(element, context);
    }

    @Override
    public Type drgElementVariableFEELType(TDRGElement element) {
        return this.dmnEnvironmentFactory.drgElementVariableFEELType(element);
    }

    @Override
    public Type drgElementVariableFEELType(TDRGElement element, DMNContext context) {
        return this.dmnEnvironmentFactory.drgElementVariableFEELType(element, context);
    }

    @Override
    public String annotation(TDRGElement element, String description) {
        if (StringUtils.isBlank(description)) {
            return "\"\"";
        }
        try {
            DMNContext context = this.makeGlobalContext(element);
            Statement statement = this.expressionToNativeTransformer.literalExpressionToNative(element, description, context);
            return statement.getText();
        } catch (Exception e) {
            LOGGER.warn("Cannot process description '%s' for element '%s'".formatted(description, element == null ? "" : element.getName()));
            return "\"%s\"".formatted(description.replaceAll("\"", "\\\\\""));
        }
    }

    @Override
    public boolean canGenerateApplyWithMap(TDRGElement element) {
        if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = drgElementTypeSignature(element);
            return parameters.stream().allMatch(p -> this.nativeFactory.isSerializable(p.getRight()));
        } else if (element instanceof TInvocable) {
            List<Pair<String, Type>> parameters = drgElementTypeSignature(element);
            return parameters.stream().allMatch(p -> this.nativeFactory.isSerializable(p.getRight()));
        } else {
            throw new DMNRuntimeException("No supported yet '%s'".formatted(element.getClass().getSimpleName()));
        }
    }

    @Override
    public String drgElementSignatureWithMap(TDRGElement element) {
        return "%s %s, %s %s".formatted(inputClassName(), inputVariableName(), executionContextClassName(), executionContextVariableName());
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
    public List<Pair<String, String>> drgElementSignatureParameters(TDRGElement element) {
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return drgElementSignatureParameters(reference);
    }

    @Override
    public List<Pair<String, String>> drgElementSignatureParameters(DRGElementReference<? extends TDRGElement> reference) {
        List<Pair<String, Type>> parameters = drgElementTypeSignature(reference, this::nativeName);
        List<Pair<String, String>> decisionSignature = parameters.stream().map(p -> new Pair<>(this.nativeFactory.nullableParameterType(toNativeType(p.getRight())), p.getLeft())).collect(Collectors.toList());
        return augmentSignatureParameters(decisionSignature);
    }

    @Override
    public List<Pair<String, Type>> drgElementTypeSignature(DRGElementReference<? extends TDRGElement> reference, Function<Object, String> nameProducer) {
        TDRGElement element = reference.getElement();
        if (element instanceof TBusinessKnowledgeModel) {
            return bkmParameters((DRGElementReference<TBusinessKnowledgeModel>) reference, nameProducer);
        } else if (element instanceof TDecisionService) {
            return dsParameters((DRGElementReference<TDecisionService>) reference, nameProducer);
        } else if (element instanceof TDecision) {
            return inputDataParametersClosure((DRGElementReference<TDecision>) reference, nameProducer);
        } else {
            throw new DMNRuntimeException("Not supported yet '%s'".formatted(element.getClass().getSimpleName()));
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
    public String drgElementArgumentListWithMap(DRGElementReference<? extends TDRGElement> reference) {
        TDRGElement element = reference.getElement();
        if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = drgElementTypeSignature(reference, this::displayName);
            String arguments = parameters.stream().map(this::extractAndConvertInputMember).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else if (element instanceof TInvocable) {
            List<Pair<String, Type>> parameters = drgElementTypeSignature(reference, this::displayName);
            String arguments = parameters.stream().map(this::extractAndConvertInputMember).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else {
            throw new DMNRuntimeException("Not supported yet for '%s'".formatted(element.getClass().getSimpleName()));
        }
    }

    private String extractInputMember(String name) {
        return "%s.%s".formatted(inputVariableName(), contextGetter(name));
    }

    private String extractAndConvertInputMember(Pair<String, Type> pair) {
        String member = extractInputMember(pair.getLeft());
        return "%s".formatted(this.nativeFactory.convertArgumentFromString(member, pair.getRight()));
    }

    @Override
    public List<String> drgElementComplexInputClassNames(TDRGElement element) {
        Set<String> nameSet = new LinkedHashSet<>();
        // Input
        List<Pair<String, Type>> parameters = drgElementTypeSignature(element);
        for (Pair<String, Type> parameter : parameters) {
            Type type = parameter.getRight();
            addClassName(type, nameSet, true);
        }
        // Output
        Type type = drgElementOutputFEELType(element);
        addClassName(type, nameSet, true);
        return sortNames(nameSet);
    }

    private ArrayList<String> sortNames(Set<String> nameSet) {
        ArrayList<String> list = new ArrayList<>(nameSet);
        Collections.sort(list);
        return list;
    }

    private void addClassName(Type type, Set<String> result, boolean addClass) {
        while (type instanceof ListType) {
            type = ((ListType) type).getElementType();
        }
        if (type instanceof ItemDefinitionType itemType) {
            Set<ItemDefinitionType> types = new LinkedHashSet<>();
            collect(itemType, types);
            for (ItemDefinitionType node: types) {
                String packageName = nativeTypePackageName(node.getModelName());
                String moduleName = this.upperCaseFirst(node.getName());
                result.add(qualifiedModuleName(packageName, moduleName));
                if (addClass) {
                    result.add(qualifiedModuleName(packageName, itemDefinitionNativeClassName(moduleName)));
                }
            }
        }
    }

    private void collect(ItemDefinitionType itemType, Set<ItemDefinitionType> types) {
        types.add(itemType);
        for (String member: itemType.getMembers()) {
            Type memberType = itemType.getMemberType(member);
            while (memberType instanceof ListType) {
                memberType = ((ListType) memberType).getElementType();
            }
            if (memberType instanceof ItemDefinitionType type && !types.contains(memberType)) {
                collect(type, types);
            }
        }
    }

    @Override
    public String drgElementArgumentListWithMap(TDRGElement element) {
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return drgElementArgumentListWithMap(reference);
    }

    @Override
    public String drgElementArgumentList(TDRGElement element) {
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return drgElementArgumentList(reference);
    }

    @Override
    public String drgElementArgumentList(DRGElementReference<? extends TDRGElement> reference) {
        List<Pair<String, Type>> parameters = drgElementTypeSignature(reference, this::nativeName);
        String arguments = parameters.stream().map(p -> "%s".formatted(p.getLeft())).collect(Collectors.joining(", "));
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
        String arguments = parameters.stream().map(p -> "%s".formatted(convertDecisionArgument(p.getLeft(), p.getRight()))).collect(Collectors.joining(", "));
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
        if (obj instanceof DRGElementReference reference) {
            String elementName = this.dmnModelRepository.name(reference.getElement());
            return drgReferenceQualifiedDisplayName(reference.getImportPath(), reference.getModelName(), elementName);
        } else if (obj instanceof TNamedElement element) {
            return this.dmnModelRepository.name(element);
        }
        throw new DMNRuntimeException("Variable name cannot be null for '%s'".formatted(obj));
    }

    @Override
    public String displayName(Object obj) {
        if (obj instanceof DRGElementReference reference) {
            String elementName = this.dmnModelRepository.displayName(reference.getElement());
            return drgReferenceQualifiedDisplayName(reference.getImportPath(), reference.getModelName(), elementName);
        } else if (obj instanceof TNamedElement element) {
            return this.dmnModelRepository.displayName(element);
        }
        throw new DMNRuntimeException("Variable name cannot be null for '%s'".formatted(obj));
    }

    @Override
    public String nativeName(Object obj) {
        if (obj instanceof DRGElementReference reference) {
            return drgElementReferenceVariableName(reference);
        } else if (obj instanceof TNamedElement element) {
            return namedElementVariableName(element);
        }
        throw new DMNRuntimeException("Variable name cannot be null for '%s'".formatted(obj));
    }

    @Override
    public String lambdaApplySignature() {
        return "Object... " + lambdaArgsVariableName();
    }

    @Override
    public String lambdaArgsVariableName() {
        return "args_";
    }

    @Override
    public boolean shouldGenerateApplyWithConversionFromString(TDRGElement element) {
        if (element instanceof TDecision decision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure(this.dmnModelRepository.makeDRGElementReference(decision));
            return parameters.stream().anyMatch(p -> p.getRight() != StringType.STRING) && parameters.stream().allMatch(p -> this.nativeFactory.isSerializable(p.getRight()));
        } else if (element instanceof TInvocable) {
            return false;
        } else {
            throw new DMNRuntimeException("No supported yet '%s'".formatted(element.getClass().getSimpleName()));
        }
    }

    @Override
    public String drgElementSignatureWithConversionFromString(TDRGElement element) {
        if (element instanceof TDecision decision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure(this.dmnModelRepository.makeDRGElementReference(decision));
            String decisionSignature = parameters.stream().map(p -> this.nativeFactory.nullableParameter(toStringNativeType(p.getRight()), p.getLeft())).collect(Collectors.joining(", "));
            return augmentSignature(decisionSignature);
        } else {
            throw new DMNRuntimeException("No supported yet '%s'".formatted(element.getClass().getSimpleName()));
        }
    }

    @Override
    public String drgElementArgumentListWithConversionFromString(TDRGElement element) {
        if (element instanceof TDecision decision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure(this.dmnModelRepository.makeDRGElementReference(decision));
            String arguments = parameters.stream().map(p -> "%s".formatted(this.nativeFactory.convertArgumentFromString(p.getLeft(), p.getRight()))).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else {
            throw new DMNRuntimeException("No supported yet '%s'".formatted(element.getClass().getSimpleName()));
        }
    }

    @Override
    public boolean hasComplexInputDatas(TDRGElement element) {
        return !this.drgElementComplexInputClassNames(element).isEmpty();
    }

    @Override
    public boolean hasDirectSubDecisions(TDRGElement element) {
        return !this.dmnModelRepository.directSubDecisions(element).isEmpty();
    }

    @Override
    public boolean hasDirectSubInvocables(TDRGElement element) {
        return !this.dmnModelRepository.directSubInvocables(element).isEmpty();
    }

    @Override
    public String drgElementConstructorSignature(TDRGElement element) {
        List<DRGElementReference<TDecision>> subDecisionReferences = this.dmnModelRepository.directSubDecisions(element);
        this.dmnModelRepository.sortNamedElementReferences(subDecisionReferences);
        return subDecisionReferences.stream().map(d -> this.nativeFactory.decisionConstructorParameter(d)).collect(Collectors.joining(", "));
    }

    @Override
    public String drgElementConstructorNewArgumentList(TDRGElement element) {
        List<DRGElementReference<TDecision>> directSubDecisionReferences = this.dmnModelRepository.directSubDecisions(element);
        this.dmnModelRepository.sortNamedElementReferences(directSubDecisionReferences);
        return directSubDecisionReferences
                .stream()
                .map(d -> "%s".formatted(defaultConstructor(qualifiedName(d))))
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean isSingletonDecision() {
        return this.inputParameters.isSingletonDecision();
    }

    @Override
    public String singletonDecisionConstructor(String javaClassName, TDecision decision) {
        List<DRGElementReference<TDecision>> directSubDecisionReferences = this.dmnModelRepository.directSubDecisions(decision);
        if (directSubDecisionReferences.isEmpty()) {
            return defaultConstructor(javaClassName);
        } else {
            this.dmnModelRepository.sortNamedElementReferences(directSubDecisionReferences);
            String arguments = directSubDecisionReferences
                    .stream()
                    .map(d -> singletonDecisionInstance(qualifiedName(d)))
                    .collect(Collectors.joining(", "));
            return constructor(javaClassName, arguments);
        }
    }

    @Override
    public String singletonDecisionInstance(String decisionQName) {
        return "%s.instance()".formatted(decisionQName);
    }

    //
    // NamedElement related functions
    //
    @Override
    public String namedElementVariableName(TNamedElement element) {
        String name = element.getName();
        if (StringUtils.isBlank(name)) {
            throw new DMNRuntimeException("Variable name cannot be null. ItemDefinition id '%s'".formatted(element.getId()));
        }
        return lowerCaseFirst(name);
    }

    //
    // Comment related functions
    //
    @Override
    public String startElementCommentText(TDRGElement element) {
        if (element instanceof TDecision) {
            return "Start decision '%s'".formatted(element.getName());
        } else if (element instanceof TBusinessKnowledgeModel) {
            return "Start BKM '%s'".formatted(element.getName());
        } else if (element instanceof TDecisionService) {
            return "Start DS '%s'".formatted(element.getName());
        } else {
            throw new DMNRuntimeException("No supported yet '%s'".formatted(element.getClass().getSimpleName()));
        }
    }

    @Override
    public String endElementCommentText(TDRGElement element) {
        if (element instanceof TDecision) {
            return "End decision '%s'".formatted(element.getName());
        } else if (element instanceof TBusinessKnowledgeModel) {
            return "End BKM '%s'".formatted(element.getName());
        } else if (element instanceof TDecisionService) {
            return "End DS '%s'".formatted(element.getName());
        } else {
            throw new DMNRuntimeException("No supported yet '%s'".formatted(element.getClass().getSimpleName()));
        }
    }

    @Override
    public String evaluateElementCommentText(TDRGElement element) {
        if (element instanceof TDecision) {
            return "Evaluate decision '%s'".formatted(element.getName());
        } else if (element instanceof TBusinessKnowledgeModel) {
            return "Evaluate BKM '%s'".formatted(element.getName());
        } else if (element instanceof TDecisionService) {
            return "Evaluate DS '%s'".formatted(element.getName());
        } else {
            throw new DMNRuntimeException("No supported yet '%s'".formatted(element.getClass().getSimpleName()));
        }
    }

    @Override
    public QualifiedName drgElementOutputTypeRef(TDRGElement element) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        QualifiedName typeRef = this.dmnModelRepository.outputTypeRef(model, element);
        if (this.dmnModelRepository.isNull(typeRef)) {
            throw new DMNRuntimeException("Cannot infer return type for BKM '%s'".formatted(element.getName()));
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
    public String singletonInvocableInstance(TInvocable invocable) {
        return qualifiedName(qualifiedName(invocable), "instance()");
    }

    @Override
    public List<FormalParameter<Type>> invocableFEELParameters(TDRGElement invocable) {
        if (invocable instanceof TBusinessKnowledgeModel model) {
            return bkmFEELParameters(model);
        } else if (invocable instanceof TDecisionService service) {
            return dsFEELParameters(service);
        } else {
            throw new DMNRuntimeException("Illegal invocable '%s'".formatted(invocable.getClass().getSimpleName()));
        }
    }

    @Override
    public List<String> invocableFEELParameterNames(TDRGElement invocable) {
        if (invocable instanceof TBusinessKnowledgeModel model) {
            return bkmFEELParameterNames(model);
        } else if (invocable instanceof TDecisionService service) {
            return dsFEELParameterNames(service);
        } else {
            throw new DMNRuntimeException("Illegal invocable '%s'".formatted(invocable.getClass().getSimpleName()));
        }
    }

    //
    // BKM related functions
    //
    @Override
    public List<FormalParameter<Type>> bkmFEELParameters(TBusinessKnowledgeModel bkm) {
        // Check variable.typeRef
        TDefinitions model = this.dmnModelRepository.getModel(bkm);
        QName bkmTypeRef = bkm.getVariable().getTypeRef();
        if (bkmTypeRef != null) {
            Type type = toFEELType(model, bkmTypeRef.getLocalPart());
            if (type instanceof DMNFunctionType functionType) {
                return functionType.getParameters();
            } else if (Type.isNullOrAny(type)) {
                // infer from inputs
            } else {
                throw new SemanticError("Expected DMN function type, found '%s'".formatted(type));
            }
        }
        // Infer from expression
        List<FormalParameter<Type>> parameters = new ArrayList<>();
        for (TInformationItem p: bkm.getEncapsulatedLogic().getFormalParameter()) {
            String typeRef = QualifiedName.toName(p.getTypeRef());
            Type type = null;
            if (!StringUtils.isEmpty(typeRef)) {
                type = toFEELType(model, QualifiedName.toQualifiedName(model, typeRef));
            }
            parameters.add(new FormalParameter<>(p.getName(), type));
        }
        return parameters;
    }

    @Override
    public List<String> bkmFEELParameterNames(TBusinessKnowledgeModel bkm) {
        return bkmFEELParameters(bkm).stream().map(FormalParameter::getName).collect(Collectors.toList());
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
    public List<FormalParameter<Type>> dsFEELParameters(TDecisionService service) {
        // Check variable.typeRef
        TDefinitions model = this.dmnModelRepository.getModel(service);
        QName invocableTypeRef = service.getVariable().getTypeRef();
        if (invocableTypeRef != null) {
            Type type = toFEELType(model, invocableTypeRef.getLocalPart());
            if (type instanceof DMNFunctionType functionType) {
                return functionType.getParameters();
            } else if (Type.isNullOrAny(type)) {
                // infer from inputs
            } else {
                throw new SemanticError("Expected DMN function type, found '%s'".formatted(type));
            }
        }
        // Infer from inputs
        List<FormalParameter<Type>> parameters = new ArrayList<>();
        for (TDMNElementReference er : service.getInputData()) {
            TInputData inputData = getDMNModelRepository().findInputDataByRef(service, er.getHref());
            parameters.add(new FormalParameter<>(inputData.getName(), toFEELType(inputData)));
        }
        for (TDMNElementReference er : service.getInputDecision()) {
            TDecision decision = getDMNModelRepository().findDecisionByRef(service, er.getHref());
            parameters.add(new FormalParameter<>(decision.getName(), drgElementOutputFEELType(decision)));
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
        if (type instanceof ItemDefinitionType definitionType) {
            return this.nativeFactory.convertToItemDefinitionType(paramName, definitionType);
        } else {
            return paramName;
        }
    }

    protected Statement convertExpression(Statement statement, Type expectedType) {
        if (!(statement instanceof ExpressionStatement)) {
            return statement;
        }
        String javaExpression = statement.getText();
        Type expressionType = ((ExpressionStatement) statement).getExpressionType();
        if ("null".equals(javaExpression)) {
            return this.nativeFactory.makeExpressionStatement(javaExpression, expectedType);
        }
        if (expectedType instanceof ListType type && expressionType instanceof ListType) {
            Type expectedElementType = type.getElementType();
            if (expectedElementType instanceof ItemDefinitionType itemDefinitionType) {
                String conversionText = this.nativeFactory.makeListConversion(javaExpression, itemDefinitionType);
                return this.nativeFactory.makeExpressionStatement(conversionText, expectedType);
            }
        } else if (expectedType instanceof ListType) {
            return this.nativeFactory.makeExpressionStatement(this.nativeFactory.convertElementToList(javaExpression, expectedType), expectedType);
        } else if (expressionType instanceof ListType) {
            return this.nativeFactory.makeExpressionStatement(this.nativeFactory.convertListToElement(javaExpression, expectedType), expectedType);
        } else if (expectedType instanceof ItemDefinitionType type) {
            return this.nativeFactory.makeExpressionStatement(this.nativeFactory.convertToItemDefinitionType(javaExpression, type), expectedType);
        }
        return statement;
    }

    @Override
    public String convertMethodName(TItemDefinition itemDefinition) {
        return this.nativeFactory.convertMethodName(itemDefinition);
    }

    @Override
    public String augmentSignature(String signature) {
        String contextParameter = this.nativeFactory.parameter(executionContextClassName(), executionContextVariableName());
        if (StringUtils.isBlank(signature)) {
            return "%s".formatted(contextParameter);
        } else {
            return "%s, %s".formatted(signature, contextParameter);
        }
    }

    @Override
    public List<Pair<String, String>> augmentSignatureParameters(List<Pair<String, String>> signature) {
        List<Pair<String, String>> result = new ArrayList<>(signature);
        result.add(new Pair<>(executionContextClassName(), executionContextVariableName()));
        return result;
    }

    @Override
    public String augmentArgumentList(String arguments) {
        String contextVar = executionContextVariableName();
        if (StringUtils.isBlank(arguments)) {
            return "%s".formatted(contextVar);
        } else {
            return "%s, %s".formatted(arguments, contextVar);
        }
    }

    @Override
    public List<String> extractExtraParametersFromExecutionContext() {
        List<Pair<Pair<String, String>, String>> result = new ArrayList<>();
        result.add(new Pair<>(new Pair<>(annotationSetClassName(), annotationSetVariableName()), "annotations"));
        result.add(new Pair<>(new Pair<>(eventListenerClassName(), eventListenerVariableName()), "eventListener"));
        result.add(new Pair<>(new Pair<>(externalExecutorClassName(), externalExecutorVariableName()), "externalFunctionExecutor"));
        result.add(new Pair<>(new Pair<>(cacheInterfaceName(), cacheVariableName()), "cache"));

        return result.stream().map(this::extractParameter).collect(Collectors.toList());
    }

    protected String extractParameter(Pair<Pair<String, String>, String> param) {
        String type = param.getLeft().getLeft();
        String varName = param.getLeft().getRight();
        String propertyName = param.getRight();
        return "%s %s = %s != null ? %s.%s : null;".formatted(
            type, varName, executionContextVariableName(), executionContextVariableName(), getter(propertyName));
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
            return "%s_%s".formatted(javaPrefix, javaName);
        }
    }

    private String drgReferenceQualifiedDisplayName(ImportPath importPath, String modelName, String elementName) {
        Pair<List<String>, String> qName = qualifiedName(importPath, modelName, elementName);

        String modelPrefix = String.join(".", qName.getLeft());
        String localName = qName.getRight();
        if (StringUtils.isBlank(modelPrefix)) {
            return localName;
        } else {
            return "%s.%s".formatted(modelPrefix, localName);
        }
    }

    @Override
    public String bindingName(DRGElementReference<? extends TDRGElement> reference) {
        Pair<List<String>, String> qName = qualifiedName(reference.getImportPath(), reference.getModelName(), reference.getElementName());

        String prefix = String.join(".", qName.getLeft());
        if (StringUtils.isBlank(prefix)) {
            return qName.getRight();
        } else {
            return "%s.%s".formatted(prefix, qName.getRight());
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
    public String registryId(TDRGElement element) {
        if (this.dmnModelRepository.getAllDefinitions().size() == 1 || this.onePackage) {
            return displayName(element);
        } else {
            TDefinitions model = this.dmnModelRepository.getModel(element);
            String modelId = model.getNamespace();
            return "%s#%s".formatted(modelId, displayName(element));
        }
    }

    @Override
    public String parameterNativeType(TDefinitions model, TInformationItem element) {
        return parameterType(model, element);
    }

    @Override
    public String parameterNativeType(TDRGElement element) {
        String parameterNativeType;
        if (element instanceof TInputData data) {
            parameterNativeType = inputDataType(data);
        } else if (element instanceof TDecision) {
            parameterNativeType = drgElementOutputType(element);
        } else {
            throw new UnsupportedOperationException("'%s' is not supported".formatted(element.getClass().getSimpleName()));
        }
        return parameterNativeType;
    }

    @Override
    public String extractParameterFromArgs(Pair<String, String> parameter, int index) {
        String type = parameter.getLeft();
        String name = parameter.getRight();
        return "%s %s = %s < %s.length ? (%s) %s[%s] : null;".formatted(
            type, name, index, lambdaArgsVariableName(), type, lambdaArgsVariableName(), index);
    }

    private String parameterType(TDefinitions model, TInformationItem element) {
        QualifiedName typeRef = this.dmnModelRepository.variableTypeRef(model, element);
        if (this.dmnModelRepository.isNull(typeRef)) {
            throw new IllegalArgumentException("Cannot resolve typeRef for element '%s'".formatted(element.getName()));
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
        return isLazyEvaluated(input) ? "%s<%s>".formatted(lazyEvalClassName(), inputNativeType) : inputNativeType;
    }

    @Override
    public String lazyEvaluation(String elementName, String nativeName) {
        return isLazyEvaluated(elementName) ? "%s.getOrCompute()".formatted(nativeName) : nativeName;
    }

    @Override
    public String pairClassName() {
        return qualifiedName(Pair.class);
    }

    @Override
    public String pairComparatorClassName() {
        return qualifiedName(PairComparator.class);
    }

    @Override
    public String argumentsClassName() {
        return qualifiedName(Arguments.class);
    }

    @Override
    public String argumentsVariableName(TDRGElement element) {
        return "%sArguments_".formatted(namedElementVariableName(element));
    }

    @Override
    public String dmnTypeClassName() {
        return qualifiedName(DMNType.class);
    }

    @Override
    public String dmnRuntimeExceptionClassName() {
        return qualifiedName(DMNRuntimeException.class);
    }

    @Override
    public String lazyEvalClassName() {
        return qualifiedName(LazyEval.class);
    }

    @Override
    public String contextClassName() {
        return qualifiedName(Context.class);
    }

    @Override
    public String rangeClassName() {
        return qualifiedName(Range.class);
    }

    @Override
    public String executorClassName() {
        return qualifiedName(Executor.class);
    }

    @Override
    public String registryClassName() {
        return qualifiedName(ModelElementRegistry.class);
    }

    protected String inputClassName() {
        return qualifiedName(Map.class) + "<String, String>";
    }

    protected String inputVariableName() {
        return "input_";
    }

    @Override
    public String executionContextClassName() {
        return qualifiedName(ExecutionContext.class);
    }

    @Override
    public String executionContextVariableName() {
        return "context_";
    }

    @Override
    public String annotationSetClassName() {
        return qualifiedName(AnnotationSet.class);
    }

    @Override
    public String annotationSetVariableName() {
        return "annotationSet_";
    }

    @Override
    public String eventListenerClassName() {
        return qualifiedName(EventListener.class);
    }

    @Override
    public String eventListenerVariableName() {
        return "eventListener_";
    }

    @Override
    public String defaultEventListenerClassName() {
        return qualifiedName(NopEventListener.class);
    }

    @Override
    public String loggingEventListenerClassName() {
        return qualifiedName(LoggingEventListener.class);
    }

    @Override
    public String treeTraceEventListenerClassName() {
        return qualifiedName(TreeTraceEventListener.class);
    }

    @Override
    public String externalExecutorClassName() {
        return qualifiedName(ExternalFunctionExecutor.class);
    }

    @Override
    public String externalExecutorVariableName() {
        return "externalExecutor_";
    }

    @Override
    public String defaultExternalExecutorClassName() {
        return qualifiedName(DefaultExternalFunctionExecutor.class);
    }

    @Override
    public String cacheInterfaceName() {
        return qualifiedName(Cache.class);
    }

    @Override
    public String cacheVariableName() {
        return "cache_";
    }

    @Override
    public String defaultCacheClassName() {
        return qualifiedName(DefaultCache.class);
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
    public String drgElementAnnotationClassName() {
        return qualifiedName(com.gs.dmn.runtime.annotation.DRGElement.class);
    }

    @Override
    public String elementKindAnnotationClassName() {
        return qualifiedName(DRGElementKind.class);
    }

    @Override
    public String expressionKindAnnotationClassName() {
        return qualifiedName(ExpressionKind.class);
    }

    @Override
    public String drgElementMetadataClassName() {
        return qualifiedName(com.gs.dmn.runtime.listener.DRGElement.class);
    }

    @Override
    public String drgElementMetadataFieldName() {
        return "DRG_ELEMENT_METADATA";
    }

    @Override
    public String drgRuleMetadataClassName() {
        return qualifiedName(com.gs.dmn.runtime.listener.Rule.class);
    }

    @Override
    public String drgRuleMetadataFieldName() {
        return "drgRuleMetadata";
    }

    @Override
    public String assertClassName() {
        return qualifiedName(Assert.class);
    }

    @Override
    public String javaExternalFunctionClassName() {
        return qualifiedName(JavaExternalFunction.class);
    }

    @Override
    public String lambdaExpressionClassName() {
        return qualifiedName(LambdaExpression.class);
    }

    @Override
    public String javaFunctionInfoClassName() {
        return qualifiedName(JavaFunctionInfo.class);
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
    public String condition(TDRGElement element, TDecisionRule rule, int ruleIndex) {
        return this.expressionToNativeTransformer.condition(element, rule, ruleIndex);
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
    public String outputClauseGetter(TDRGElement element, TOutputClause output) {
        return this.expressionToNativeTransformer.outputClauseGetter(element, output);
    }

    @Override
    public String drgElementOutputGetter(TDRGElement element, TOutputClause output) {
        return this.expressionToNativeTransformer.drgElementOutputGetter(element, output);
    }

    @Override
    public String outputClauseSetter(TDRGElement element, TOutputClause output, String args) {
        return this.expressionToNativeTransformer.outputClauseSetter(element, output, args);
    }

    @Override
    public String drgElementOutputSetter(TDRGElement element, TOutputClause output, String args) {
        return this.expressionToNativeTransformer.drgElementOutputSetter(element, output, args);
    }

    @Override
    public Integer outputClausePriority(TDRGElement element, TLiteralExpression literalExpression, int outputIndex) {
        return this.expressionToNativeTransformer.outputClausePriority(element, literalExpression, outputIndex);
    }

    @Override
    public String outputClausePriorityGetter(TDRGElement element, TOutputClause output) {
        return this.expressionToNativeTransformer.outputClausePriorityGetter(element, output);
    }

    @Override
    public String outputClausePrioritySetter(TDRGElement element, TOutputClause output, String args) {
        return this.expressionToNativeTransformer.prioritySetter(element, output, args);
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
    public String qualifiedRuleOutputClassName(TDRGElement element) {
        return this.expressionToNativeTransformer.qualifiedRuleOutputClassName(element);
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
    public Type expressionType(TDRGElement element, TExpression expression, DMNContext context) {
        return this.dmnEnvironmentFactory.expressionType(element, expression, context);
    }

    @Override
    public Type convertType(Type type, boolean convertToContext) {
        return this.dmnEnvironmentFactory.convertType(type, convertToContext);
    }

    @Override
    public Statement serviceToNative(TDecisionService element) {
        List<DRGElementReference<TDecision>> outputDecisions = this.dmnModelRepository.directSubDecisions(element);
        if (outputDecisions.isEmpty()) {
            return this.nativeFactory.makeExpressionStatement(this.nativeFactory.nullLiteral(), NullType.NULL);
        } else if (outputDecisions.size() == 1) {
            TDecision decision = outputDecisions.get(0).getElement();
            String decisionVarName = namedElementVariableName(decision);
            return this.nativeFactory.makeExpressionStatement(decisionVarName, drgElementOutputFEELType(decision));
        } else {
            // Make statements
            CompoundStatement statement = new CompoundStatement();
            // Create an empty context
            String outputVar = "output_";
            String init = this.nativeFactory.makeVariableAssignment(contextClassName(), outputVar, defaultConstructor(contextClassName()));
            statement.add(this.nativeFactory.makeExpressionStatement(init, null));
            // Add members
            for (DRGElementReference<TDecision> ref: outputDecisions) {
                TDecision od = ref.getElement();
                String add = this.nativeFactory.makeContextMemberAssignment(outputVar, elementName(od), namedElementVariableName(od));
                statement.add(this.nativeFactory.makeExpressionStatement(add, null));
            }
            // Return output
            String return_ = this.nativeFactory.makeReturn(outputVar);
            statement.add(this.nativeFactory.makeExpressionStatement(return_, null));
            return statement;
        }
    }

    @Override
    public Statement expressionToNative(TDRGElement element) {
        TExpression expression = this.dmnModelRepository.expression(element);
        if (expression instanceof TContext context) {
            return this.expressionToNativeTransformer.contextExpressionToNative(element, context);
        } else if (expression instanceof TFunctionDefinition definition) {
            return this.expressionToNativeTransformer.functionDefinitionToNative(element, definition);
        } else if (expression instanceof TInvocation invocation) {
            return this.expressionToNativeTransformer.invocationExpressionToNative(element, invocation);
        } else if (expression instanceof TLiteralExpression literalExpression) {
            Statement statement = this.expressionToNativeTransformer.literalExpressionToNative(element, literalExpression.getText());
            Type expectedType = drgElementOutputFEELType(element);
            return convertExpression(statement, expectedType);
        } else if (expression instanceof TRelation relation) {
            return this.expressionToNativeTransformer.relationExpressionToNative(element, relation);
        } else if (expression instanceof TConditional conditional) {
            return this.expressionToNativeTransformer.conditionalExpressionToNative(element, conditional);
        } else if (expression instanceof TFilter filter) {
            return this.expressionToNativeTransformer.filterExpressionToNative(element, filter);
        } else if (expression instanceof TFor for1) {
            return this.expressionToNativeTransformer.forExpressionToNative(element, for1);
        } else if (expression instanceof TSome some) {
            return this.expressionToNativeTransformer.someExpressionToNative(element, some);
        } else if (expression instanceof TEvery every) {
            return this.expressionToNativeTransformer.everyExpressionToNative(element, every);
        } else {
            throw new UnsupportedOperationException("Not supported '%s'".formatted(expression.getClass().getSimpleName()));
        }
    }

    @Override
    public Statement expressionToNative(TDRGElement element, TExpression expression, DMNContext context) {
        if (expression instanceof TContext tContext) {
            return this.expressionToNativeTransformer.contextExpressionToNative(element, tContext, context);
        } else if (expression instanceof TFunctionDefinition definition) {
            return this.expressionToNativeTransformer.functionDefinitionToNative(element, definition, context);
        } else if (expression instanceof TInvocation invocation) {
            return this.expressionToNativeTransformer.invocationExpressionToNative(element, invocation, context);
        } else if (expression instanceof TLiteralExpression literalExpression) {
            return this.expressionToNativeTransformer.literalExpressionToNative(element, literalExpression.getText(), context);
        } else if (expression instanceof TRelation relation) {
            return this.expressionToNativeTransformer.relationExpressionToNative(element, relation, context);
        } else if (expression instanceof TConditional conditional) {
            return this.expressionToNativeTransformer.conditionalExpressionToNative(element, conditional, context);
        } else if (expression instanceof TFilter filter) {
            return this.expressionToNativeTransformer.filterExpressionToNative(element, filter, context);
        } else if (expression instanceof TFor for1) {
            return this.expressionToNativeTransformer.forExpressionToNative(element, for1, context);
        } else if (expression instanceof TSome some) {
            return this.expressionToNativeTransformer.someExpressionToNative(element, some, context);
        } else if (expression instanceof TEvery every) {
            return this.expressionToNativeTransformer.everyExpressionToNative(element, every, context);
        } else {
            throw new UnsupportedOperationException("Not supported '%s'".formatted(expression.getClass().getSimpleName()));
        }
    }

    @Override
    public String literalExpressionToNative(TDRGElement element, String expressionText) {
        Statement statement = this.expressionToNativeTransformer.literalExpressionToNative(element, expressionText);
        return statement.getText();
    }

    @Override
    public String functionDefinitionToNative(TDRGElement element, FunctionDefinition<Type> functionDefinition, boolean convertTypeToContext, String body) {
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
                || type instanceof ListType lt && lt.getElementType() instanceof ItemDefinitionType;
    }

    @Override
    public boolean isDateTimeType(Type type) {
        return isSimpleDateTimeType(type) || (type instanceof ListType lt && isSimpleDateTimeType(lt.getElementType()));
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
        type = Type.extractTypeFromConstraint(type);
        if (com.gs.dmn.el.analysis.semantics.type.Type.isNull(type)) {
            if (isStrongTyping()) {
                throw new DMNRuntimeException("Cannot infer native type for '%s' type".formatted(type));
            } else {
                return this.nativeTypeFactory.toNativeType(ANY.getName());
            }
        }

        if (type instanceof com.gs.dmn.el.analysis.semantics.type.NamedType namedType) {
            String typeName = namedType.getName();
            if (StringUtils.isBlank(typeName)) {
                throw new DMNRuntimeException("Missing type name in '%s'".formatted(type));
            }
            String primitiveType = this.nativeTypeFactory.toNativeType(typeName);
            if (!StringUtils.isBlank(primitiveType)) {
                return primitiveType;
            } else {
                if (type instanceof ItemDefinitionType definitionType) {
                    String modelName = definitionType.getModelName();
                    return qualifiedName(nativeTypePackageName(modelName), upperCaseFirst(typeName));
                } else {
                    throw new DMNRuntimeException("Cannot infer native type for '%s' type".formatted(type));
                }
            }
        } else if (type instanceof ContextType) {
            return contextClassName();
        } else if (type instanceof ListType listType) {
            if (listType.getElementType() instanceof AnyType) {
                return makeListType(DMNToJavaTransformer.LIST_TYPE);
            } else {
                String elementType = toNativeType(listType.getElementType());
                return makeListType(DMNToJavaTransformer.LIST_TYPE, elementType);
            }
        } else if (type instanceof FunctionType) {
            if (type instanceof FEELFunctionType functionType) {
                String returnType = toNativeType(functionType.getReturnType());
                if (functionType.isExternal()) {
                    return makeFunctionType(javaExternalFunctionClassName(), returnType);
                } else {
                    return makeFunctionType(lambdaExpressionClassName(), returnType);
                }
            } else if (type instanceof DMNFunctionType functionType) {
                TFunctionKind kind = functionType.getKind();
                if (isFEELFunction(kind)) {
                    String returnType = toNativeType(functionType.getReturnType());
                    return makeFunctionType(lambdaExpressionClassName(), returnType);
                } else if (isJavaFunction(kind)) {
                    String returnType = toNativeType(functionType.getReturnType());
                    return makeFunctionType(javaExternalFunctionClassName(), returnType);
                }
                throw new DMNRuntimeException("Type %s is not supported yet".formatted(type));
            }
            throw new DMNRuntimeException("Type %s is not supported yet".formatted(type));
        }
        throw new IllegalArgumentException("Type '%s' is not supported yet".formatted(type));
    }

    @Override
    public String makeListType(String listType, String elementType) {
        return "%s<%s>".formatted(listType, elementType);
    }

    @Override
    public String makeListType(String listType) {
        return makeListType(listType, "? extends Object");
    }

    @Override
    public String nullableType(String type) {
        return this.nativeTypeFactory.nullableType(type);
    }

    protected String makeFunctionType(String name, String returnType) {
        return "%s<%s>".formatted(name, returnType);
    }

    @Override
    public String jdmnRootPackage() {
        return "com.gs.dmn";
    }

    @Override
    public String qualifiedName(String pkg, String clsName) {
        if (StringUtils.isBlank(pkg)) {
            return clsName;
        } else {
            return "%s.%s".formatted(pkg, clsName);
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
        return qualifiedName(pkg, name);
    }

    @Override
    public String qualifiedName(Class<?> cls) {
        return cls.getName();
    }

    @Override
    public String qualifiedModuleName(DRGElementReference<? extends TDRGElement> reference) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String qualifiedModuleName(TDRGElement element) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String qualifiedModuleName(String pkg, String moduleName) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String getterName(String name) {
        return "get%s".formatted(this.upperCaseFirst(name));
    }

    @Override
    public String getter(String name) {
        return "%s()".formatted(getterName(name));
    }

    @Override
    public String setter(String name, String args) {
        return "set%s(%s)".formatted(this.upperCaseFirst(name), args);
    }

    @Override
    public String contextGetter(String name) {
        return "get(\"%s\")".formatted(name);
    }

    @Override
    public String contextSetter(String name) {
        return "put(\"%s\", ".formatted(name);
    }

    @Override
    public String nativeFriendlyVariableName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new DMNRuntimeException("Cannot build variable name from empty string");
        }
        name = NameUtils.removeSingleQuotes(name);
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
        name = NameUtils.removeSingleQuotes(name);
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
        name = NameUtils.removeSingleQuotes(name);
        String firstChar = Character.toString(Character.toUpperCase(name.charAt(0)));
        return nativeFriendlyName(name.length() == 1 ? firstChar : firstChar + name.substring(1));
    }

    @Override
    public String lowerCaseFirst(String name) {
        name = NameUtils.removeSingleQuotes(name);
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
        return Objects.requireNonNullElse(javaRootPackage, "");
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
        if (type instanceof ListType listType) {
            return this.nativeFactory.asList(listType.getElementType(), "");
        }
        throw new DMNRuntimeException("Expected List Type found '%s' instead".formatted(type));
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
    public Pair<DMNContext, Map<TContextEntry, Expression<Type>>> makeContextEnvironment(TDRGElement element, TContext context, DMNContext parentContext) {
        return this.dmnEnvironmentFactory.makeContextEnvironment(element, context, parentContext);
    }

    @Override
    public Environment makeRelationEnvironment(TNamedElement element, TRelation relation) {
        return this.dmnEnvironmentFactory.makeRelationEnvironment(element, relation);
    }

    @Override
    public Environment makeFunctionDefinitionEnvironment(TNamedElement element, TFunctionDefinition functionDefinition) {
        return this.dmnEnvironmentFactory.makeFunctionDefinitionEnvironment(element, functionDefinition);
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
        throw new DMNRuntimeException("Illegal proto version '%s'".formatted(protoVersion));
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
    public String drgElementArgumentListProto(TDRGElement element) {
        return augmentArgumentList(this.protoFactory.drgElementArgumentListProto(element));
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

    //
    // Mock testing related methods
    //
    @Override
    public boolean isMockTesting() {
        return this.inputParameters.isMockTesting();
    }

    @Override
    public boolean isGenerateExtra() {
        return this.inputParameters.isGenerateExtra();
    }

    @Override
    public String getNativeNumberType() {
        return this.getDialect().getNativeNumberType();
    }

    @Override
    public String getNativeDateType() {
        return this.getDialect().getNativeDateType();
    }

    @Override
    public String getNativeTimeType() {
        return this.getDialect().getNativeTimeType();
    }

    @Override
    public String getNativeDateAndTimeType() {
        return this.getDialect().getNativeDateAndTimeType();
    }

    @Override
    public String getNativeDurationType() {
        return this.getDialect().getNativeDurationType();
    }

    @Override
    public String getDefaultIntegerValue() {
        return this.nativeFactory.constructor(getNativeNumberType(), "\"0\"");
    }

    @Override
    public String getDefaultDecimalValue() {
        return this.nativeFactory.constructor(getNativeNumberType(), "\"0.0\"");
    }

    @Override
    public String getDefaultStringValue() {
        return this.nativeFactory.nullLiteral();
    }

    @Override
    public String getDefaultBooleanValue() {
        return this.nativeFactory.falseConstant();
    }

    @Override
    public String getDefaultDateValue() {
        return this.nativeFactory.nullLiteral();
    }

    @Override
    public String getDefaultTimeValue() {
        return this.nativeFactory.nullLiteral();
    }

    @Override
    public String getDefaultDateAndTimeValue() {
        return this.nativeFactory.nullLiteral();
    }

    @Override
    public String getDefaultDurationValue() {
        return this.nativeFactory.nullLiteral();
    }

    @Override
    public boolean isInteger(TItemDefinition element) {
        if (element != null) {
            TDMNElement.ExtensionElements extensionElements = element.getExtensionElements();
            if (extensionElements != null) {
                for (Object any : extensionElements.getAny()) {
                    if ("non_decimal_number".equals(any)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String makeIntegerForInput(String text) {
        return this.nativeFactory.constructor(getNativeNumberType(), "java.lang.Integer.toString(%s)".formatted(text));
    }

    @Override
    public String makeDecimalForInput(String text) {
        return this.nativeFactory.constructor(getNativeNumberType(), "java.lang.Double.toString(%s)".formatted(text));
    }

    @Override
    public String makeDecimalForDecision(String text) {
        if (StringUtils.isBlank(text)) {
            return this.nativeFactory.nullLiteral();
        } else {
            return this.nativeFactory.constructor(getNativeNumberType(), text);
        }
    }

    @Override
    public String makeDate(String text) {
        return makeDateTimeLiteral("date", text.trim());
    }

    @Override
    public String makeTime(String text) {
        return makeDateTimeLiteral("time", text.trim());
    }

    @Override
    public String makeDateTime(String text) {
        return makeDateTimeLiteral("dateAndTime", text.trim());
    }

    @Override
    public String makeDuration(String text) {
        return makeDateTimeLiteral("duration", text.trim());
    }

    @Override
    public String getDefaultValue(Type memberType, TItemDefinition memberItemDefinition) {
        String value = this.nativeFactory.nullLiteral();
        if (memberType == NumberType.NUMBER) {
            if (isInteger(memberItemDefinition)) {
                value = this.nativeFactory.prefixWithSelf("DEFAULT_INTEGER_NUMBER");
            } else {
                value = this.nativeFactory.prefixWithSelf("DEFAULT_DECIMAL_NUMBER");
            }
        } else if (memberType == StringType.STRING) {
            value = this.nativeFactory.prefixWithSelf("DEFAULT_STRING");
        } else if (memberType == BooleanType.BOOLEAN) {
            value = this.nativeFactory.prefixWithSelf("DEFAULT_BOOLEAN");
        } else if (memberType == DateType.DATE) {
            value = this.nativeFactory.prefixWithSelf("DEFAULT_DATE");
        } else if (memberType == TimeType.TIME) {
            value = this.nativeFactory.prefixWithSelf("DEFAULT_TIME");
        } else if (memberType == DateTimeType.DATE_AND_TIME) {
            value = this.nativeFactory.prefixWithSelf("DEFAULT_DATE_TIME");
        } else if (memberType instanceof DurationType) {
            value = this.nativeFactory.prefixWithSelf("DEFAULT_DURATION");
        }
        return value;
    }

    @Override
    public boolean isCheckConstraints() {
        return this.inputParameters.isCheckConstraints();
    }

    private String makeDateTimeLiteral(String constructor, String text) {
        if (!text.startsWith(constructor)) {
            text = "%s(\"%s\")".formatted(constructor, text);
        }
        return this.nativeFactory.prefixWithSelf(text);
    }
}