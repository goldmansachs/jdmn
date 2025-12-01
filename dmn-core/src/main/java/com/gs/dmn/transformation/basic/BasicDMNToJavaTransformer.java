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
import com.gs.dmn.error.ErrorFactory;
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.error.SemanticErrorException;
import com.gs.dmn.feel.DMNExpressionLocation;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.analysis.syntax.ast.library.LibraryRepository;
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
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.LazyEvaluationOptimisation;
import com.gs.dmn.transformation.native_.JavaFactory;
import com.gs.dmn.transformation.native_.NativeFactory;
import com.gs.dmn.transformation.native_.statement.CompoundStatement;
import com.gs.dmn.transformation.native_.statement.Statement;
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
    protected final LibraryRepository libraryRepository;
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

    public BasicDMNToJavaTransformer(DMNDialectDefinition<?, ?, ?, ?, ?, ?> dialect, DMNModelRepository dmnModelRepository, LazyEvaluationDetector lazyEvaluationDetector, InputParameters inputParameters) {
        this.dialect = dialect;
        this.dmnModelRepository = dmnModelRepository;
        this.libraryRepository = dialect.createLibraryRepository(inputParameters);
        this.environmentFactory = dialect.createEnvironmentFactory();
        this.nativeTypeFactory = dialect.createNativeTypeFactory();
        this.lazyEvaluationDetector = lazyEvaluationDetector;

        // Configuration
        this.inputParameters = inputParameters;
        this.onePackage = this.inputParameters.isOnePackage() || dmnModelRepository.getAllDefinitions().size() == 1;

        // Derived data
        this.lazyEvaluationOptimisation = this.lazyEvaluationDetector.detect(this.dmnModelRepository);
        this.cachedElements = this.dmnModelRepository.computeCachedElements(this.inputParameters.isCaching(), this.inputParameters.getCachingThreshold());

        // Helpers
        setNativeFactory(this);
        setFEELTranslator(this);
        setDMNEnvironmentFactory(this);
        setExpressionToNativeTransformer(this);

        this.drgElementFilter = new DRGElementFilter(this.inputParameters.isSingletonInputData());
        this.nativeTypeMemoizer = new JavaTypeMemoizer();
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
    public LibraryRepository getLibraryRepository() {
        return this.libraryRepository;
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
        return this.nativeFactory.constructor(className, "", false);
    }

    @Override
    public String constructor(String className, String arguments) {
        return this.nativeFactory.constructor(className, arguments, false);
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
            throw new SemanticErrorException(String.format("Variable name cannot be null. Decision id '%s'", reference.getElement().getId()));
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
        if (type instanceof ListType) {
            type = ((ListType) type).getElementType();
        }
        return toNativeType(type);
    }

    @Override
    public String drgElementOutputClassName(TDRGElement element) {
        Type type = drgElementOutputFEELType(element);
        if (type instanceof ListType) {
            type = ((ListType) type).getElementType();
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
    public List<String> annotations(TDRGElement element, List<String> annotationTexts) {
        List<String> annotationStatements = new ArrayList<>();
        DMNContext context = this.makeGlobalContext(element);
        for (String annotationText : annotationTexts) {
            try {
                // Add rule annotation
                if (!StringUtils.isBlank(annotationText)) {
                    Statement statement = annotation(element, annotationText, context);
                    String code = statement.getText();
                    if (!StringUtils.isBlank(code)) {
                        annotationStatements.add(code);
                    }
                }
            } catch (Exception e) {
                throw new SemanticErrorException(String.format("Cannot process annotation '%s' for element '%s'", annotationText, element == null ? "" : element.getName()), e);
            }
        }
        return annotationStatements;
    }

    protected Statement annotation(TDRGElement element, String annotationText, DMNContext context) {
        return this.expressionToNativeTransformer.literalExpressionToNative(element, annotationText, context);
    }

    @Override
    public boolean canGenerateApplyWithMap(TDRGElement element) {
        if (element instanceof TDecision) {
            List<FEELParameter> parameters = drgElementTypeSignature(element);
            return parameters.stream().allMatch(p -> this.nativeFactory.isSerializable(p.getType()));
        } else if (element instanceof TInvocable) {
            List<FEELParameter> parameters = drgElementTypeSignature(element);
            return parameters.stream().allMatch(p -> this.nativeFactory.isSerializable(p.getType()));
        } else {
            throw new SemanticErrorException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    @Override
    public String drgElementSignatureWithMap(TDRGElement element) {
        return String.format("%s %s, %s %s", inputClassName(), inputVariableName(), executionContextClassName(), executionContextVariableName());
    }

    @Override
    public String drgElementSignature(TDRGElement element) {
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return drgElementSignature(reference);
    }

    @Override
    public String drgElementSignature(DRGElementReference<? extends TDRGElement> reference) {
        List<FEELParameter> parameters = drgElementTypeSignature(reference, this::nativeName);
        String decisionSignature = parameters.stream().map(p -> this.nativeFactory.nullableParameter(toNativeType(p.getType()), p.getName())).collect(Collectors.joining(", "));
        return augmentSignature(decisionSignature);
    }

    @Override
    public List<NativeParameter> drgElementSignatureParameters(TDRGElement element) {
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return drgElementSignatureParameters(reference);
    }

    @Override
    public List<NativeParameter> drgElementSignatureParameters(DRGElementReference<? extends TDRGElement> reference) {
        TDRGElement element = reference.getElement();
        List<FEELParameter> parameters = drgElementTypeSignature(reference, this::nativeName);
        List<NativeParameter> decisionSignature = parameters.stream().map(p -> new NativeParameter(this.nativeFactory.nullableParameterType(toNativeType(p.getType())), p.getName())).collect(Collectors.toList());
        return augmentSignatureParameters(decisionSignature);
    }

    @Override
    public List<FEELParameter> drgElementTypeSignature(DRGElementReference<? extends TDRGElement> reference, Function<Object, String> nameProducer) {
        TDRGElement element = reference.getElement();
        if (element instanceof TBusinessKnowledgeModel) {
            return bkmParameters((DRGElementReference<TBusinessKnowledgeModel>) reference, nameProducer);
        } else if (element instanceof TDecisionService) {
            return dsParameters((DRGElementReference<TDecisionService>) reference, nameProducer);
        } else if (element instanceof TDecision) {
            return inputDataParametersClosure((DRGElementReference<TDecision>) reference, nameProducer);
        } else {
            throw new SemanticErrorException(String.format("Not supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    @Override
    public List<FEELParameter> drgElementTypeSignature(TDRGElement element, Function<Object, String> nameProducer) {
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return drgElementTypeSignature(reference, nameProducer);
    }

    @Override
    public List<FEELParameter> drgElementTypeSignature(TDRGElement element) {
        return drgElementTypeSignature(element, this::nativeName);
    }

    @Override
    public String drgElementArgumentListWithMap(DRGElementReference<? extends TDRGElement> reference) {
        TDRGElement element = reference.getElement();
        if (element instanceof TDecision) {
            List<FEELParameter> parameters = drgElementTypeSignature(reference, this::displayName);
            String arguments = parameters.stream().map(this::extractAndConvertInputMember).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else if (element instanceof TInvocable) {
            List<FEELParameter> parameters = drgElementTypeSignature(reference, this::displayName);
            String arguments = parameters.stream().map(this::extractAndConvertInputMember).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else {
            throw new SemanticErrorException(String.format("Not supported yet for '%s'", element.getClass().getSimpleName()));
        }
    }

    private String extractInputMember(String name) {
        return String.format("%s.%s", inputVariableName(), contextGetter(name));
    }

    private String extractAndConvertInputMember(FEELParameter parameter) {
        String member = extractInputMember(parameter.getName());
        return String.format("%s", this.nativeFactory.convertArgumentFromString(member, parameter.getType()));
    }

    @Override
    public List<String> drgElementComplexInputClassNames(TDRGElement element) {
        Set<String> nameSet = new LinkedHashSet<>();
        // Input
        List<FEELParameter> parameters = drgElementTypeSignature(element);
        for (FEELParameter parameter : parameters) {
            Type type = parameter.getType();
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
        if (type instanceof ItemDefinitionType) {
            ItemDefinitionType itemType = (ItemDefinitionType) type;
            Set<ItemDefinitionType> types = new LinkedHashSet<>();
            collect(itemType, types);
            for (ItemDefinitionType node : types) {
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
        for (String member : itemType.getMembers()) {
            Type memberType = itemType.getMemberType(member);
            while (memberType instanceof ListType) {
                memberType = ((ListType) memberType).getElementType();
            }
            if (memberType instanceof ItemDefinitionType && !types.contains(memberType)) {
                collect((ItemDefinitionType) memberType, types);
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
        List<FEELParameter> parameters = drgElementTypeSignature(reference, this::nativeName);
        String arguments = parameters.stream().map(p -> String.format("%s", p.getName())).collect(Collectors.joining(", "));
        return augmentArgumentList(arguments);
    }

    @Override
    public String drgElementConvertedArgumentList(TDRGElement element) {
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return drgElementConvertedArgumentList(reference);
    }

    @Override
    public String drgElementConvertedArgumentList(DRGElementReference<? extends TDRGElement> reference) {
        List<FEELParameter> parameters = drgElementTypeSignature(reference, this::nativeName);
        String arguments = parameters.stream().map(p -> String.format("%s", convertDecisionArgument(p.getName(), p.getType()))).collect(Collectors.joining(", "));
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
        List<FEELParameter> parameters = drgElementTypeSignature(reference, nameProducer);
        return parameters.stream().map(FEELParameter::getName).collect(Collectors.toList());
    }

    @Override
    public String elementName(Object obj) {
        if (obj instanceof DRGElementReference) {
            DRGElementReference<? extends TDRGElement> reference = (DRGElementReference<? extends TDRGElement>) obj;
            String elementName = this.dmnModelRepository.name(reference.getElement());
            return drgReferenceQualifiedDisplayName(reference.getImportPath(), reference.getModelName(), elementName);
        } else if (obj instanceof TNamedElement) {
            return this.dmnModelRepository.name((TNamedElement) obj);
        }
        throw new SemanticErrorException(String.format("Variable name cannot be null for '%s'", obj));
    }

    @Override
    public String displayName(Object obj) {
        if (obj instanceof DRGElementReference) {
            DRGElementReference<? extends TDRGElement> reference = (DRGElementReference<? extends TDRGElement>) obj;
            String elementName = this.dmnModelRepository.displayName(reference.getElement());
            return drgReferenceQualifiedDisplayName(reference.getImportPath(), reference.getModelName(), elementName);
        } else if (obj instanceof TNamedElement) {
            return this.dmnModelRepository.displayName((TNamedElement) obj);
        }
        throw new SemanticErrorException(String.format("Variable name cannot be null for '%s'", obj));
    }

    @Override
    public String nativeName(Object obj) {
        if (obj instanceof DRGElementReference) {
            return drgElementReferenceVariableName((DRGElementReference<? extends TDRGElement>) obj);
        } else if (obj instanceof TNamedElement) {
            return namedElementVariableName((TNamedElement) obj);
        }
        throw new SemanticErrorException(String.format("Variable name cannot be null for '%s'", obj));
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
        if (element instanceof TDecision) {
            List<FEELParameter> parameters = inputDataParametersClosure(this.dmnModelRepository.makeDRGElementReference((TDecision) element));
            return parameters.stream().anyMatch(p -> p.getType() != StringType.STRING) && parameters.stream().allMatch(p -> this.nativeFactory.isSerializable(p.getType()));
        } else if (element instanceof TInvocable) {
            return false;
        } else {
            throw new SemanticErrorException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    @Override
    public String drgElementSignatureWithConversionFromString(TDRGElement element) {
        if (element instanceof TDecision) {
            List<FEELParameter> parameters = inputDataParametersClosure(this.dmnModelRepository.makeDRGElementReference((TDecision) element));
            String decisionSignature = parameters.stream().map(p -> this.nativeFactory.nullableParameter(toStringNativeType(p.getType()), p.getName())).collect(Collectors.joining(", "));
            return augmentSignature(decisionSignature);
        } else {
            throw new SemanticErrorException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    @Override
    public String drgElementArgumentListWithConversionFromString(TDRGElement element) {
        if (element instanceof TDecision) {
            List<FEELParameter> parameters = inputDataParametersClosure(this.dmnModelRepository.makeDRGElementReference((TDecision) element));
            String arguments = parameters.stream().map(p -> String.format("%s", this.nativeFactory.convertArgumentFromString(p.getName(), p.getType()))).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else {
            throw new SemanticErrorException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
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
                .map(d -> String.format("%s", defaultConstructor(qualifiedName(d))))
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
        return String.format("%s.instance()", decisionQName);
    }

    //
    // NamedElement related functions
    //
    @Override
    public String namedElementVariableName(TNamedElement element) {
        String name = element.getName();
        if (StringUtils.isBlank(name)) {
            throw new SemanticErrorException(String.format("Variable name cannot be null. ItemDefinition id '%s'", element.getId()));
        }
        return lowerCaseFirst(name);
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
        } else if (element instanceof TDecisionService) {
            return String.format("Start DS '%s'", element.getName());
        } else {
            throw new SemanticErrorException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    @Override
    public String endElementCommentText(TDRGElement element) {
        if (element instanceof TDecision) {
            return String.format("End decision '%s'", element.getName());
        } else if (element instanceof TBusinessKnowledgeModel) {
            return String.format("End BKM '%s'", element.getName());
        } else if (element instanceof TDecisionService) {
            return String.format("End DS '%s'", element.getName());
        } else {
            throw new SemanticErrorException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    @Override
    public String evaluateElementCommentText(TDRGElement element) {
        if (element instanceof TDecision) {
            return String.format("Evaluate decision '%s'", element.getName());
        } else if (element instanceof TBusinessKnowledgeModel) {
            return String.format("Evaluate BKM '%s'", element.getName());
        } else if (element instanceof TDecisionService) {
            return String.format("Evaluate DS '%s'", element.getName());
        } else {
            throw new SemanticErrorException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    @Override
    public QualifiedName drgElementOutputTypeRef(TDRGElement element) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        QualifiedName typeRef = this.dmnModelRepository.outputTypeRef(model, element);
        if (this.dmnModelRepository.isNull(typeRef)) {
            throw new SemanticErrorException(String.format("Cannot infer return type for BKM '%s'", element.getName()));
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
        if (invocable instanceof TBusinessKnowledgeModel) {
            return bkmFEELParameters((TBusinessKnowledgeModel) invocable);
        } else if (invocable instanceof TDecisionService) {
            return dsFEELParameters((TDecisionService) invocable);
        } else {
            throw new SemanticErrorException(String.format("Illegal invocable '%s'", invocable.getClass().getSimpleName()));
        }
    }

    @Override
    public List<String> invocableFEELParameterNames(TDRGElement invocable) {
        if (invocable instanceof TBusinessKnowledgeModel) {
            return bkmFEELParameterNames((TBusinessKnowledgeModel) invocable);
        } else if (invocable instanceof TDecisionService) {
            return dsFEELParameterNames((TDecisionService) invocable);
        } else {
            throw new SemanticErrorException(String.format("Illegal invocable '%s'", invocable.getClass().getSimpleName()));
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
            if (type instanceof DMNFunctionType) {
                return ((DMNFunctionType) type).getParameters();
            } else if (Type.isNullOrAny(type)) {
                // infer from inputs
            } else {
                throw new SemanticErrorException(String.format("Expected DMN function type, found '%s'", type));
            }
        }
        // Infer from expression
        List<FormalParameter<Type>> parameters = new ArrayList<>();
        for (TInformationItem p : bkm.getEncapsulatedLogic().getFormalParameter()) {
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

    protected List<FEELParameter> bkmParameters(DRGElementReference<TBusinessKnowledgeModel> reference, Function<Object, String> nameProducer) {
        List<FEELParameter> parameters = new ArrayList<>();
        TBusinessKnowledgeModel bkm = reference.getElement();
        TFunctionDefinition encapsulatedLogic = bkm.getEncapsulatedLogic();
        List<TInformationItem> formalParameters = encapsulatedLogic.getFormalParameter();
        for (TInformationItem parameter : formalParameters) {
            String parameterName = nameProducer.apply(parameter);
            Type parameterType = informationItemType(bkm, parameter);
            parameters.add(new FEELParameter(parameterName, parameterType));
        }
        return parameters;
    }

    //
    // Decision Service related functions
    //
    @Override
    public List<TDRGElement> dsInputs(TDecisionService service) {
        List<TDRGElement> inputs = new ArrayList<>();
        for (TDMNElementReference er : service.getInputData()) {
            TInputData inputData = getDMNModelRepository().findInputDataByRef(service, er.getHref());
            inputs.add(inputData);
        }
        for (TDMNElementReference er : service.getInputDecision()) {
            TDecision decision = getDMNModelRepository().findDecisionByRef(service, er.getHref());
            inputs.add(decision);
        }
        return inputs;
    }

    @Override
    public List<FormalParameter<Type>> dsFEELParameters(TDecisionService service) {
        // Check variable.typeRef
        TDefinitions model = this.dmnModelRepository.getModel(service);
        QName invocableTypeRef = service.getVariable().getTypeRef();
        if (invocableTypeRef != null) {
            Type type = toFEELType(model, invocableTypeRef.getLocalPart());
            if (type instanceof DMNFunctionType) {
                return ((DMNFunctionType) type).getParameters();
            } else if (Type.isNullOrAny(type)) {
                // infer from inputs
            } else {
                throw new SemanticErrorException(String.format("Expected DMN function type, found '%s'", type));
            }
        }
        // Infer from inputs
        List<FormalParameter<Type>> parameters = new ArrayList<>();
        List<TDRGElement> inputs = dsInputs(service);
        for (TDRGElement input : inputs) {
            if (input instanceof TInputData) {
                // TInputData
                parameters.add(new FormalParameter<>(input.getName(), toFEELType((TInputData) input)));
            } else {
                // TDecision
                parameters.add(new FormalParameter<>(input.getName(), drgElementOutputFEELType(input)));
            }
        }
        return parameters;
    }

    @Override
    public List<String> dsFEELParameterNames(TDecisionService service) {
        return dsFEELParameters(service).stream().map(FormalParameter::getName).collect(Collectors.toList());
    }

    private List<FEELParameter> dsParameters(DRGElementReference<TDecisionService> reference, Function<Object, String> nameProducer) {
        TDecisionService service = reference.getElement();
        List<FEELParameter> parameters = new ArrayList<>();
        for (TDMNElementReference er : service.getInputData()) {
            TInputData inputData = this.dmnModelRepository.findInputDataByRef(service, er.getHref());
            ImportPath importPath = this.dmnModelRepository.findRelativeImportPath(service, er);
            String parameterName = nameProducer.apply(this.dmnModelRepository.makeDRGElementReference(importPath, inputData));
            Type parameterType = toFEELType(inputData);
            parameters.add(new FEELParameter(parameterName, parameterType));
        }
        for (TDMNElementReference er : service.getInputDecision()) {
            TDecision decision = this.dmnModelRepository.findDecisionByRef(service, er.getHref());
            ImportPath importPath = this.dmnModelRepository.findRelativeImportPath(service, er);
            String parameterName = nameProducer.apply(this.dmnModelRepository.makeDRGElementReference(importPath, decision));
            Type parameterType = drgElementOutputFEELType(decision);
            parameters.add(new FEELParameter(parameterName, parameterType));
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

    @Override
    public String convertMethodName(TItemDefinition itemDefinition) {
        return this.nativeFactory.convertMethodName(itemDefinition);
    }

    @Override
    public String augmentSignature(String signature) {
        String contextParameter = this.nativeFactory.parameter(executionContextClassName(), executionContextVariableName());
        if (StringUtils.isBlank(signature)) {
            return String.format("%s", contextParameter);
        } else {
            return String.format("%s, %s", signature, contextParameter);
        }
    }

    @Override
    public List<NativeParameter> augmentSignatureParameters(List<NativeParameter> signature) {
        List<NativeParameter> result = new ArrayList<>(signature);
        result.add(new NativeParameter(executionContextClassName(), executionContextVariableName()));
        return result;
    }

    @Override
    public String augmentArgumentList(String arguments) {
        String contextVar = executionContextVariableName();
        if (StringUtils.isBlank(arguments)) {
            return String.format("%s", contextVar);
        } else {
            return String.format("%s, %s", arguments, contextVar);
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
        return String.format("%s %s = %s != null ? %s.%s : null;",
                type, varName, executionContextVariableName(), executionContextVariableName(), getter(propertyName));
    }

    @Override
    public List<FEELParameter> inputDataParametersClosure(DRGElementReference<TDecision> reference) {
        return inputDataParametersClosure(reference, this::nativeName);
    }

    private List<FEELParameter> inputDataParametersClosure(DRGElementReference<TDecision> reference, Function<Object, String> nameProducer) {
        List<DRGElementReference<TInputData>> allInputDataReferences = inputDataClosure(reference);

        List<FEELParameter> parameters = new ArrayList<>();
        for (DRGElementReference<TInputData> inputDataReference : allInputDataReferences) {
            String parameterName = nameProducer.apply(inputDataReference);
            TInputData element = inputDataReference.getElement();
            Type parameterType = toFEELType(element);
            parameters.add(new FEELParameter(parameterName, parameterType));
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
    public String bindingName(QualifiedName reference) {
        return bindingName(reference.getNamespace(), reference.getLocalPart());
    }

    @Override
    public String bindingName(DRGElementReference<? extends TDRGElement> reference) {
        return bindingName(reference.getNamespace(), reference.getElementName());
    }

    private static String bindingName(String namespace, String elementName) {
        if (StringUtils.isBlank(namespace)) {
            return elementName;
        } else {
            return String.format("%s#%s", namespace, elementName);
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
            return bindingName(modelId, displayName(element));
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

    @Override
    public String extractParameterFromArgs(NativeParameter parameter, int index) {
        String type = parameter.getType();
        String name = parameter.getName();
        return String.format("%s %s = %s < %s.length ? (%s) %s[%s] : null;",
                type, name, index, lambdaArgsVariableName(), type, lambdaArgsVariableName(), index);
    }

    private String parameterType(TDefinitions model, TInformationItem element) {
        QualifiedName typeRef = this.dmnModelRepository.variableTypeRef(model, element);
        if (this.dmnModelRepository.isNull(typeRef)) {
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
        return String.format("%sArguments_", namedElementVariableName(element));
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
    public String expressionDefaultValue(TDRGElement element) {
        return this.expressionToNativeTransformer.expressionDefaultValue(element);
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
    public List<String> annotations(TDRGElement element, TDecisionRule rule) {
        return this.expressionToNativeTransformer.annotations(element, rule);
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
        if (outputDecisions.size() == 0) {
            return this.nativeFactory.makeExpressionStatement(this.nativeFactory.nullLiteral(), NullType.NULL);
        } else if (outputDecisions.size() == 1) {
            TDecision decision = outputDecisions.get(0).getElement();
            String decisionVarName = namedElementVariableName(decision);
            Statement statement = this.nativeFactory.makeExpressionStatement(decisionVarName, drgElementOutputFEELType(decision));

            // Implicit conversions
            Type expectedType = drgElementOutputFEELType(element);
            statement = this.expressionToNativeTransformer.convertExpression(statement, expectedType);

            return statement;
        } else {
            // Make statements
            CompoundStatement statement = new CompoundStatement();
            // Create an empty context
            String outputVar = "output_";
            String init = this.nativeFactory.makeVariableAssignment(contextClassName(), outputVar, defaultConstructor(contextClassName()));
            statement.add(this.nativeFactory.makeExpressionStatement(init, null));
            // Add members
            for (DRGElementReference<TDecision> ref : outputDecisions) {
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
        try {
            TExpression expression = this.dmnModelRepository.expression(element);
            Statement statement;
            if (expression instanceof TContext) {
                statement = this.expressionToNativeTransformer.contextExpressionToNative(element, (TContext) expression);
            } else if (expression instanceof TFunctionDefinition) {
                statement = this.expressionToNativeTransformer.functionDefinitionToNative(element, (TFunctionDefinition) expression);
            } else if (expression instanceof TInvocation) {
                statement = this.expressionToNativeTransformer.invocationExpressionToNative(element, (TInvocation) expression);
            } else if (expression instanceof TLiteralExpression) {
                statement = this.expressionToNativeTransformer.literalExpressionToNative(element, ((TLiteralExpression) expression).getText());
            } else if (expression instanceof TList) {
                statement = this.expressionToNativeTransformer.listExpressionToNative(element, (TList) expression);
            } else if (expression instanceof TRelation) {
                statement = this.expressionToNativeTransformer.relationExpressionToNative(element, (TRelation) expression);
            } else if (expression instanceof TConditional) {
                statement = this.expressionToNativeTransformer.conditionalExpressionToNative(element, (TConditional) expression);
            } else if (expression instanceof TFilter) {
                statement = this.expressionToNativeTransformer.filterExpressionToNative(element, (TFilter) expression);
            } else if (expression instanceof TFor) {
                statement = this.expressionToNativeTransformer.forExpressionToNative(element, (TFor) expression);
            } else if (expression instanceof TSome) {
                statement = this.expressionToNativeTransformer.someExpressionToNative(element, (TSome) expression);
            } else if (expression instanceof TEvery) {
                statement = this.expressionToNativeTransformer.everyExpressionToNative(element, (TEvery) expression);
            } else {
                throw new UnsupportedOperationException(String.format("Not supported '%s'", expression.getClass().getSimpleName()));
            }

            // Implicit conversions
            Type expectedType = drgElementOutputFEELType(element);
            return this.expressionToNativeTransformer.convertExpression(statement, expectedType);
        } catch (Exception e) {
            TDefinitions definitions = this.dmnModelRepository.getModel(element);
            TExpression expression = this.dmnModelRepository.expression(element);
            String errorMessage = "Error translating expression to native platform";
            SemanticError error = ErrorFactory.makeDMNExpressionError(new DMNExpressionLocation(definitions, element, expression), errorMessage);
            throw new SemanticErrorException(error.toText(), e);
        }
    }

    @Override
    public Statement expressionToNative(TDRGElement element, TExpression expression, DMNContext context) {
        if (expression instanceof TContext) {
            return this.expressionToNativeTransformer.contextExpressionToNative(element, (TContext) expression, context);
        } else if (expression instanceof TFunctionDefinition) {
            return this.expressionToNativeTransformer.functionDefinitionToNative(element, (TFunctionDefinition) expression, context);
        } else if (expression instanceof TInvocation) {
            return this.expressionToNativeTransformer.invocationExpressionToNative(element, (TInvocation) expression, context);
        } else if (expression instanceof TLiteralExpression) {
            return this.expressionToNativeTransformer.literalExpressionToNative(element, ((TLiteralExpression) expression).getText(), context);
        } else if (expression instanceof TRelation) {
            return this.expressionToNativeTransformer.relationExpressionToNative(element, (TRelation) expression, context);
        } else if (expression instanceof TConditional) {
            return this.expressionToNativeTransformer.conditionalExpressionToNative(element, (TConditional) expression, context);
        } else if (expression instanceof TFilter) {
            return this.expressionToNativeTransformer.filterExpressionToNative(element, (TFilter) expression, context);
        } else if (expression instanceof TFor) {
            return this.expressionToNativeTransformer.forExpressionToNative(element, (TFor) expression, context);
        } else if (expression instanceof TSome) {
            return this.expressionToNativeTransformer.someExpressionToNative(element, (TSome) expression, context);
        } else if (expression instanceof TEvery) {
            return this.expressionToNativeTransformer.everyExpressionToNative(element, (TEvery) expression, context);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s'", expression.getClass().getSimpleName()));
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
        type = Type.extractTypeFromConstraint(type);
        if (com.gs.dmn.el.analysis.semantics.type.Type.isNull(type)) {
            return this.nativeTypeFactory.toNativeType(ANY.getName());
        }

        if (type instanceof com.gs.dmn.el.analysis.semantics.type.NamedType) {
            String typeName = ((com.gs.dmn.el.analysis.semantics.type.NamedType) type).getName();
            if (StringUtils.isBlank(typeName)) {
                throw new SemanticErrorException(String.format("Missing type name in '%s'", type));
            }
            if (type instanceof ItemDefinitionType) {
                String modelName = ((ItemDefinitionType) type).getModelName();
                return qualifiedName(nativeTypePackageName(modelName), upperCaseFirst(typeName));
            } else {
                String primitiveType = this.nativeTypeFactory.toNativeType(typeName);
                if (!StringUtils.isBlank(primitiveType)) {
                    return primitiveType;
                } else {
                    throw new SemanticErrorException(String.format("Cannot infer native type for '%s' type", type));
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
        } else if (type instanceof RangeType) {
            if (((RangeType) type).getRangeType() instanceof AnyType) {
                return makeRangeType(rangeClassName());
            } else {
                String elementType = toNativeType(((RangeType) type).getRangeType());
                return makeRangeType(rangeClassName(), elementType);
            }
        } else if (type instanceof FunctionType) {
            if (type instanceof FEELFunctionType) {
                String returnType = toNativeType(((FunctionType) type).getReturnType());
                if (((FEELFunctionType) type).isExternal()) {
                    return makeFunctionType(javaExternalFunctionClassName(), returnType);
                } else {
                    return makeFunctionType(lambdaExpressionClassName(), returnType);
                }
            } else if (type instanceof DMNFunctionType) {
                TFunctionKind kind = ((DMNFunctionType) type).getKind();
                if (this.dmnModelRepository.isFEELFunction(kind)) {
                    String returnType = toNativeType(((FunctionType) type).getReturnType());
                    return makeFunctionType(lambdaExpressionClassName(), returnType);
                } else if (this.dmnModelRepository.isJavaFunction(kind)) {
                    String returnType = toNativeType(((FunctionType) type).getReturnType());
                    return makeFunctionType(javaExternalFunctionClassName(), returnType);
                }
                throw new SemanticErrorException(String.format("Type %s is not supported yet", type));
            }
            throw new SemanticErrorException(String.format("Type %s is not supported yet", type));
        }
        throw new IllegalArgumentException(String.format("Type '%s' is not supported yet", type));
    }

    @Override
    public String makeListType(String listType, String elementType) {
        return String.format("%s<%s>", listType, elementType);
    }

    @Override
    public String makeListType(String listType) {
        return makeListType(listType, "?");
    }

    @Override
    public String makeRangeType(String rangeClassName, String endpointType) {
        return String.format("%s<%s>", rangeClassName, endpointType);
    }

    @Override
    public String makeRangeType(String rangeClassName) {
        return makeRangeType(rangeClassName, "?");
    }

    @Override
    public String nullableType(String type) {
        return this.nativeTypeFactory.nullableType(type);
    }

    protected String makeFunctionType(String name, String returnType) {
        return String.format("%s<%s>", name, returnType);
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
            return String.format("%s.%s", pkg, clsName);
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
        throw new SemanticErrorException("Not supported yet");
    }

    @Override
    public String qualifiedModuleName(TDRGElement element) {
        throw new SemanticErrorException("Not supported yet");
    }

    @Override
    public String qualifiedModuleName(String pkg, String moduleName) {
        throw new SemanticErrorException("Not supported yet");
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
    public String setter(String name, String args) {
        return String.format("set%s(%s)", this.upperCaseFirst(name), args);
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
            throw new SemanticErrorException("Cannot build variable name from empty string");
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
        if (modelName != null && modelName.endsWith(this.inputParameters.getDmnFileExtension())) {
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
        if (type instanceof ListType) {
            return this.nativeFactory.asList(((ListType) type).getElementType(), "");
        }
        throw new SemanticErrorException(String.format("Expected List Type found '%s' instead", type));
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
    public boolean isRecursiveCalls() {
        return this.inputParameters.isRecursiveCalls();
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
        return this.nativeTypeFactory.getNativeNumberType();
    }

    @Override
    public String getNativeDateType() {
        return this.nativeTypeFactory.getNativeDateType();
    }

    @Override
    public String getNativeTimeType() {
        return this.nativeTypeFactory.getNativeTimeType();
    }

    @Override
    public String getNativeDateAndTimeType() {
        return this.nativeTypeFactory.getNativeDateAndTimeType();
    }

    @Override
    public String getNativeDurationType() {
        return this.nativeTypeFactory.getNativeDurationType();
    }

    @Override
    public String getDefaultIntegerValue() {
        return this.nativeFactory.constructor(this.nativeTypeFactory.getNativeNumberConcreteType(), "\"0\"", false);
    }

    @Override
    public String getDefaultDecimalValue() {
        return this.nativeFactory.constructor(this.nativeTypeFactory.getNativeNumberConcreteType(), "\"0.0\"", false);
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
        return this.nativeFactory.constructor(this.nativeTypeFactory.getNativeNumberConcreteType(), String.format("java.lang.Integer.toString(%s)", text), false);
    }

    @Override
    public String makeDecimalForInput(String text) {
        return this.nativeFactory.constructor(this.nativeTypeFactory.getNativeNumberConcreteType(), String.format("java.lang.Double.toString(%s)", text), false);
    }

    @Override
    public String makeDecimalForDecision(String text) {
        if (StringUtils.isBlank(text)) {
            return this.nativeFactory.nullLiteral();
        } else {
            return this.nativeFactory.constructor(this.nativeTypeFactory.getNativeNumberConcreteType(), text, false);
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
            text = String.format("%s(\"%s\")", constructor, text);
        }
        return this.nativeFactory.prefixWithSelf(text);
    }
}