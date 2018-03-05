/**
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
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.environment.FunctionDeclaration;
import com.gs.dmn.feel.analysis.semantics.environment.VariableDeclaration;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.feel.synthesis.FEELTranslatorImpl;
import com.gs.dmn.feel.synthesis.type.FEELTypeTranslator;
import com.gs.dmn.runtime.*;
import com.gs.dmn.runtime.annotation.AnnotationSet;
import com.gs.dmn.runtime.annotation.DRGElementKind;
import com.gs.dmn.runtime.annotation.ExpressionKind;
import com.gs.dmn.runtime.annotation.HitPolicy;
import com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor;
import com.gs.dmn.runtime.external.ExternalFunctionExecutor;
import com.gs.dmn.runtime.listener.Arguments;
import com.gs.dmn.runtime.listener.EventListener;
import com.gs.dmn.runtime.listener.LoggingEventListener;
import com.gs.dmn.runtime.listener.NopEventListener;
import com.gs.dmn.serialization.JsonSerializer;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.InputParamUtil;
import com.gs.dmn.transformation.java.CompoundStatement;
import com.gs.dmn.transformation.java.ExpressionStatement;
import com.gs.dmn.transformation.java.Statement;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20151101.dmn.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gs.dmn.serialization.DMNNamespacePrefixMapper.FEEL_NS;

public class BasicDMN2JavaTransformer {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BasicDMN2JavaTransformer.class);

    protected final DMNModelRepository dmnModelRepository;
    protected final EnvironmentFactory environmentFactory;
    protected final FEELTypeTranslator feelTypeTranslator;
    protected final FEELTranslator feelTranslator;
    private final String javaRootPackage;
    private final boolean lazyEvaluation;

    private final ContextToJavaTransformer contextToJavaTransformer;
    private final DecisionTableToJavaTransformer decisionTableToJavaTransformer;
    private final FunctionDefinitionToJavaTransformer functionDefinitionToJavaTransformer;
    private final LiteralExpressionToJavaTransformer literalExpressionToJavaTransformer;
    private final InvocationToJavaTransformer invocationToJavaTransformer;
    private final RelationToJavaTransformer relationToJavaTransformer;

    public BasicDMN2JavaTransformer(DMNModelRepository dmnModelRepository, EnvironmentFactory environmentFactory, FEELTypeTranslator feelTypeTranslator, Map<String, String> inputParameters) {
        this.dmnModelRepository = dmnModelRepository;
        this.environmentFactory = environmentFactory;
        this.feelTypeTranslator = feelTypeTranslator;
        this.javaRootPackage = InputParamUtil.getOptionalParam(inputParameters, "javaRootPackage");
        this.lazyEvaluation = InputParamUtil.getOptionalBooleanParam(inputParameters, "lazyEvaluation");
        this.feelTranslator = new FEELTranslatorImpl(this);

        this.contextToJavaTransformer = new ContextToJavaTransformer(this);
        this.decisionTableToJavaTransformer = new DecisionTableToJavaTransformer(this);
        this.functionDefinitionToJavaTransformer = new FunctionDefinitionToJavaTransformer(this);
        this.invocationToJavaTransformer = new InvocationToJavaTransformer(this);
        this.literalExpressionToJavaTransformer = new LiteralExpressionToJavaTransformer(this);
        this.relationToJavaTransformer = new RelationToJavaTransformer(this);
    }

    public DMNModelRepository getDMNModelRepository() {
        return dmnModelRepository;
    }

    public EnvironmentFactory getEnvironmentFactory() {
        return environmentFactory;
    }

    public FEELTypeTranslator getFEELTypeTranslator() {
        return feelTypeTranslator;
    }

    public FEELTranslator getFEELTranslator() {
        return feelTranslator;
    }

    public boolean isList(TDRGElement element) {
        Type feelType = drgElementOutputFEELType(element);
        return feelType instanceof ListType;
    }

    //
    // TItemDefinition related functions
    //
    public boolean isComplexType(TItemDefinition itemDefinition) {
        Type type = toFEELType(itemDefinition);
        return type instanceof ItemDefinitionType
                || type instanceof ListType && ((ListType) type).getElementType() instanceof ItemDefinitionType;
    }

    public String itemDefinitionJavaInterfaceName(TItemDefinition itemDefinition) {
        Type type = toFEELType(itemDefinition);
        if (type instanceof ListType && ((ListType) type).getElementType() instanceof ItemDefinitionType) {
            type = ((ListType) type).getElementType();
        }
        if (type instanceof ItemDefinitionType) {
            return upperCaseFirst(((ItemDefinitionType) type).getName());
        } else {
            throw new IllegalArgumentException(String.format("Unexpected type '%s' for ItemDefinition '%s'", type, itemDefinition.getName()));
        }
    }

    public String itemDefinitionJavaInterfaceName(String className) {
        return className.substring(0, className.length() - "Impl".length());
    }

    public String itemDefinitionJavaClassName(String interfaceName) {
        return interfaceName + "Impl";
    }

    public String itemDefinitionTypeName(TItemDefinition itemDefinition) {
        Type type = toFEELType(itemDefinition);
        return toJavaType(type);
    }

    public String itemDefinitionVariableName(TItemDefinition itemDefinition) {
        String name = itemDefinition.getName();
        return lowerCaseFirst(name);
    }

    public String itemDefinitionSignature(TItemDefinition itemDefinition) {
        List<Pair<String, String>> parameters = new ArrayList<>();
        List<TItemDefinition> itemComponents = itemDefinition.getItemComponent();
        this.dmnModelRepository.sortNamedElements(itemComponents);
        for (TItemDefinition child : itemComponents) {
            parameters.add(new Pair(itemDefinitionVariableName(child), itemDefinitionTypeName(child)));
        }
        return parameters.stream().map(p -> String.format("%s %s", p.getRight(), p.getLeft())).collect(Collectors.joining(", "));
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
    public String informationItemTypeName(TInformationItem element) {
        Type type = toFEELType(element.getTypeRef());
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
        return constructor(className, "");
    }

    public String constructor(String className, String arguments) {
        return String.format("new %s(%s)", className, arguments);
    }

    public String fluentConstructor(String className, String addMethods) {
        return String.format("new %s()%s", className, addMethods);
    }

    public String functionalInterfaceConstructor(String functionalInterface, String returnType, String applyMethod) {
        return String.format("new %s<%s>() {%s}", functionalInterface, returnType, applyMethod);
    }

    //
    // DRGElement related functions
    //
    public String drgElementClassName(TDRGElement element) {
        return upperCaseFirst(element.getName());
    }

    public String drgElementVariableName(TDRGElement element) {
        String name = element.getName();
        if (name == null) {
            throw new DMNRuntimeException(String.format("Variable name cannot be null. Decision id '%s'", element.getId()));
        }
        return lowerCaseFirst(name);
    }

    public String drgElementOutputType(TNamedElement element) {
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

    protected Type drgElementOutputFEELType(TNamedElement element) {
        QName typeRef = this.dmnModelRepository.typeRef(element);
        return toFEELType(typeRef);
    }

    public String annotation(TDRGElement element, String description) {
        if (StringUtils.isBlank(description)) {
            return "\"\"";
        }
        description = description.trim().replace("string(-)", "\"\"");
        try {
            Environment environment = makeEnvironment(element);
            Statement statement = literalExpressionToJavaTransformer.literalExpressionToJava(description, environment, element);
            return ((ExpressionStatement)statement).getExpression();
        } catch (Exception e) {
            LOGGER.warn(String.format("Cannot process description '%s' for element '%s'", description, element == null ? "" : element.getName()));
            return String.format("\"%s\"", description.replaceAll("\"", "\\\\\""));
        }
    }

    public String drgElementSignature(TDRGElement element) {
        if (element instanceof TBusinessKnowledgeModel) {
            List<Pair<String, String>> parameters = bkmParameters((TBusinessKnowledgeModel) element);
            String signature = parameters.stream().map(p -> String.format("%s %s", p.getRight(), p.getLeft())).collect(Collectors.joining(", "));
            return augmentSignature(signature);
        } else if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure((TDecision) element);
            String decisionSignature = parameters.stream().map(p -> String.format("%s %s", toJavaType(p.getRight()), p.getLeft())).collect(Collectors.joining(", "));
            return augmentSignature(decisionSignature);
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    public String drgElementArgumentList(TDRGElement element) {
        if (element instanceof TBusinessKnowledgeModel) {
            List<Pair<String, String>> parameters = bkmParameters((TBusinessKnowledgeModel) element);
            String arguments = parameters.stream().map(p -> String.format("%s", p.getLeft())).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure((TDecision) element);
            String arguments = parameters.stream().map(p -> String.format("%s", p.getLeft())).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    public String drgElementConvertedArgumentList(TDRGElement element) {
        if (element instanceof TBusinessKnowledgeModel) {
            List<Pair<String, String>> parameters = bkmParameters((TBusinessKnowledgeModel) element);
            String arguments = parameters.stream().map(p -> String.format("%s", p.getLeft())).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure((TDecision) element);
            String arguments = parameters.stream().map(p -> String.format("%s", convertDecisionArgument(p.getLeft(), p.getRight()))).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    public List<String> drgElementArgumentNameList(TDRGElement element) {
        if (element instanceof TBusinessKnowledgeModel) {
            List<Pair<String, String>> parameters = bkmParameters((TBusinessKnowledgeModel) element);
            return parameters.stream().map(p -> p.getLeft()).collect(Collectors.toList());
        } else if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure((TDecision) element);
            return parameters.stream().map(p -> p.getLeft()).collect(Collectors.toList());
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    public boolean shouldGenerateApplyWithConversionFromString(TDRGElement element) {
        if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure((TDecision) element);
            return parameters.stream().anyMatch(p -> p.getRight() != StringType.STRING);
        } else if (element instanceof TBusinessKnowledgeModel) {
            return false;
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    public String drgElementSignatureWithConversionFromString(TDRGElement element) {
        if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure((TDecision) element);
            String decisionSignature = parameters.stream().map(p -> String.format("%s %s", toStringJavaType(p.getRight()), p.getLeft())).collect(Collectors.joining(", "));
            return augmentSignature(decisionSignature);
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    public String drgElementArgumentListWithConversionFromString(TDRGElement element) {
        if (element instanceof TDecision) {
            List<Pair<String, Type>> parameters = inputDataParametersClosure((TDecision) element);
            String arguments = parameters.stream().map(p -> String.format("%s", convertDecisionArgumentFromString(p.getLeft(), p.getRight()))).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    public String decisionConstructorSignature(TDecision decision) {
        List<TDecision> subDecisions = dmnModelRepository.directSubDecisions(decision);
        subDecisions.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        String signature = subDecisions.stream().map(d -> String.format("%s %s", qualifiedName(javaRootPackage, drgElementClassName(d)), drgElementVariableName(d))).collect(Collectors.joining(", "));
        return signature;
    }

    public String decisionConstructorNewArgumentList(TDecision decision) {
        List<TDecision> subDecisions = dmnModelRepository.directSubDecisions(decision);
        subDecisions.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        String arguments = subDecisions
                .stream()
                .map(d -> String.format("%s", defaultConstructor(qualifiedName(javaRootPackage, drgElementClassName(d)))))
                .collect(Collectors.joining(", "));
        return arguments;
    }

    public String decisionTopologicalConstructorSignature(TDecision decision) {
        List<TDecision> subDecisions = dmnModelRepository.topologicalSort(decision);
        subDecisions.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        String signature = subDecisions.stream().map(d -> String.format("%s %s", qualifiedName(javaRootPackage, drgElementClassName(d)), drgElementVariableName(d))).collect(Collectors.joining(", "));
        return signature;
    }

    public String decisionTopologicalConstructorNewArgumentList(TDecision decision) {
        List<TDecision> subDecisions = dmnModelRepository.topologicalSort(decision);
        subDecisions.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        String arguments = subDecisions
                .stream()
                .map(d -> String.format("%s", defaultConstructor(qualifiedName(javaRootPackage, drgElementClassName(d)))))
                .collect(Collectors.joining(", "));
        return arguments;
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
            String javaParameters = parameters.stream().map(p -> String.format("%s %s", p.getRight(), p.getLeft())).collect(Collectors.joining(", "));
            return augmentSignature(javaParameters);
        } else if (element instanceof TBusinessKnowledgeModel) {
            return drgElementSignature(element);
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
            return drgElementArgumentList(element);
        } else {
            throw new DMNRuntimeException(String.format("No supported yet '%s'", element.getClass().getSimpleName()));
        }
    }

    private List<Pair<String, String>> directInformationRequirementParameters(TDRGElement element) {
        List<TDRGElement> inputs = directInformationRequirements(element);
        this.dmnModelRepository.sortNamedElements(inputs);

        List<Pair<String, String>> parameters = new ArrayList<>();
        for (TDRGElement input : inputs) {
            if (input instanceof TInputData) {
                TInputData inputData = (TInputData) input;
                String parameterName = inputDataVariableName(inputData);
                String parameterJavaType = inputDataType(inputData);
                parameters.add(new Pair<>(parameterName, parameterJavaType));
            } else if (input instanceof TDecision) {
                TDecision subDecision = (TDecision) input;
                String parameterName = drgElementVariableName(subDecision);
                String parameterJavaType = drgElementOutputType(subDecision);
                parameters.add(new Pair<>(parameterName, lazyEvaluationType(element, input, parameterJavaType)));
            } else {
                throw new UnsupportedOperationException(String.format("'%s' is not supported yet", input.getClass().getSimpleName()));
            }
        }
        return parameters;
    }

    protected List<TDRGElement> directInformationRequirements(TDRGElement element) {
        List<TInputData> directInputDatas = this.dmnModelRepository.directInputDatas(element);
        List<TDecision> decisions = this.dmnModelRepository.directSubDecisions(element);
        List<TDRGElement> inputs = new ArrayList<>(directInputDatas);
        inputs.addAll(decisions);
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

    public QName drgElementOutputTypeRef(TDRGElement element) {
        if (element instanceof TBusinessKnowledgeModel) {
            QName typeRef = dmnModelRepository.typeRef(element);
            if (typeRef == null) {
                throw new DMNRuntimeException(String.format("Cannot infer return type for BKM '%s'", element.getName()));
            }
            return typeRef;
        } else {
            TInformationItem variable = dmnModelRepository.variable(element);
            if (variable != null) {
                return variable.getTypeRef();
            } else {
                throw new DMNRuntimeException(String.format("Missing variable in element '%s'", element.getClass().getSimpleName()));
            }
        }
    }

    //
    // InputData related functions
    //
    public String inputDataVariableName(TInputData inputData) {
        String name = inputData.getName();
        if (name == null) {
            throw new DMNRuntimeException(String.format("Variable name cannot be null. InputData id '%s'", inputData.getId()));
        }
        return lowerCaseFirst(name);
    }

    public Type toFEELType(TInputData inputData) {
        QName typeRef = inputData.getVariable().getTypeRef();
        return toFEELType(typeRef);
    }

    private String inputDataType(TInputData inputData) {
        Type type = toFEELType(inputData);
        return toJavaType(type);
    }

    //
    // BKM related functions
    //
    public String bkmFunctionName(TBusinessKnowledgeModel bkm) {
        String name = bkm.getName();
        return bkmFunctionName(name);
    }

    public String bkmFunctionName(String name) {
        return javaFriendlyName(name);
    }

    protected List<FormalParameter> bkmFEELParameters(TBusinessKnowledgeModel bkm) {
        List<FormalParameter> parameters = new ArrayList<>();
        bkm.getEncapsulatedLogic().getFormalParameter().forEach(p -> parameters.add(new FormalParameter(p.getName(), toFEELType(p.getTypeRef()))));
        return parameters;
    }

    public List<String> bkmFEELParameterNames(TBusinessKnowledgeModel bkm) {
        return bkmFEELParameters(bkm).stream().map(p -> p.getName()).collect(Collectors.toList());
    }

    private List<Pair<String, String>> bkmParameters(TBusinessKnowledgeModel bkm) {
        List<Pair<String, String>> parameters = new ArrayList<>();
        List<TInformationItem> formalParameters = bkm.getEncapsulatedLogic().getFormalParameter();
        for (TInformationItem parameter : formalParameters) {
            String parameterName = informationItemVariableName(parameter);
            String parameterType = informationItemTypeName(parameter);
            parameters.add(new Pair(parameterName, parameterType));
        }
        return parameters;
    }

    //
    // Decision related functions
    //
    protected String convertDecisionArgument(String paramName, Type type) {
        if (type instanceof ItemDefinitionType) {
            return convertToItemDefinitionType(paramName, (ItemDefinitionType) type);
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
                    String conversionText = String.format("%s.stream().map(x -> " + convertToItemDefinitionType("x", (ItemDefinitionType) expectedElementType) + ").collect(Collectors.toList())", javaExpression, toJavaType(expectedElementType));
                    return new ExpressionStatement(conversionText, expectedType);
                }
            }
        } else if (expectedType instanceof ListType && !(expressionType instanceof ListType)) {
            return new ExpressionStatement(convertElementToList(javaExpression, expectedType), expectedType);
        } else if (expressionType instanceof ListType && !(expectedType instanceof ListType)) {
            return new ExpressionStatement(convertListToElement(javaExpression, expectedType), expectedType);
        } else if (expectedType instanceof ItemDefinitionType) {
            if (expressionType.conformsTo(expectedType) || expressionType == AnyType.ANY || expressionType instanceof ContextType) {
                return new ExpressionStatement(convertToItemDefinitionType(javaExpression, (ItemDefinitionType) expectedType), expectedType);
            }
        }
        return statement;
    }

    private String convertListToElement(String expression, Type type) {
        return String.format("this.<%s>%s", toJavaType(type), asElement(expression));
    }

    private String convertElementToList(String expression, Type type) {
        return String.format("%s", asList(expression));
    }

    String convertToItemDefinitionType(String expression, ItemDefinitionType type) {
        String convertMethodName = convertMethodName(type);
        String interfaceName = toJavaType(type);
        return String.format("%s.%s(%s)", interfaceName, convertMethodName, expression);
    }

    public String convertMethodName(TItemDefinition itemDefinition) {
        String javaInterfaceName = upperCaseFirst(itemDefinition.getName());
        return String.format("to%s", javaInterfaceName);
    }

    private String convertMethodName(ItemDefinitionType type) {
        String javaInterfaceName = upperCaseFirst(type.getName());
        return String.format("to%s", javaInterfaceName);
    }

    private String convertDecisionArgumentFromString(String paramName, Type type) {
        if (FEELTypes.FEEL_PRIMITIVE_TYPES.contains(type)) {
            String conversionMethod = FEELTypes.FEEL_PRIMITIVE_TYPE_TO_JAVA_CONVERSION_FUNCTION.get(type);
            if (conversionMethod != null) {
                return String.format("(%s != null ? %s(%s) : null)", paramName, conversionMethod, paramName);
            } else if (type == StringType.STRING) {
                return paramName;
            } else if (type == BooleanType.BOOLEAN) {
                return String.format("(%s != null ? Boolean.valueOf(%s) : null)", paramName, paramName);
            } else {
                throw new DMNRuntimeException(String.format("Cannot convert String to type '%s'", type));
            }
        } else if (type instanceof ListType) {
            Type elementType = ((ListType) type).getElementType();
            String arrayElementType;
            if (elementType instanceof ListType) {
                arrayElementType = "java.util.List";
            } else if (FEELTypes.FEEL_PRIMITIVE_TYPES.contains(elementType)) {
                arrayElementType = toJavaType(elementType);
            } else {
                arrayElementType = itemDefinitionJavaClassName(toJavaType(elementType));
            }
            return String.format("(%s != null ? asList(%s.readValue(%s, %s[].class)) : null)", paramName, objectMapper(), paramName, arrayElementType);
        } else {
            return String.format("(%s != null ? %s.readValue(%s, %s.class) : null)", paramName, objectMapper(), paramName, itemDefinitionJavaClassName(toJavaType(type)));
        }
    }

    protected String augmentSignature(String signature) {
        String extra = String.format("%s %s", annotationSetClassName(), annotationSetVariableName());
        if (StringUtils.isBlank(signature)) {
            return extra;
        } else {
            return String.format("%s, %s", signature, extra);
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

    private String objectMapper() {
        return JsonSerializer.class.getName() + ".OBJECT_MAPPER";
    }

    public List<Pair<String, Type>> inputDataParametersClosure(TDecision decision) {
        List<TInputData> inputDatas = this.dmnModelRepository.allInputDatas(decision);
        this.dmnModelRepository.sortNamedElements(inputDatas);

        List<Pair<String, Type>> parameters = new ArrayList<>();
        for (TInputData inputData : inputDatas) {
            String parameterName = inputDataVariableName(inputData);
            Type parameterType = toFEELType(inputData);
            parameters.add(new Pair<>(parameterName, parameterType));
        }
        return parameters;
    }

    public String parameterJavaType(TNamedElement element) {
        String parameterJavaType;
        if (element instanceof TInputData) {
            parameterJavaType = inputDataType((TInputData) element);
        } else if (element instanceof TDecision) {
            parameterJavaType = drgElementOutputType((TDecision) element);
        } else if (element instanceof TInformationItem) {
            parameterJavaType = parameterType((TInformationItem) element);
        } else {
            throw new UnsupportedOperationException(String.format("'%s' is not supported", element.getClass().getSimpleName()));
        }
        return parameterJavaType;
    }

    private String parameterType(TInformationItem element) {
        QName typeRef = this.dmnModelRepository.typeRef(element);
        if (typeRef == null) {
            throw new IllegalArgumentException(String.format("Cannot resolve typeRef for element '%s'", element.getName()));
        }
        Type type = toFEELType(typeRef);
        return toJavaType(type);
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

    // Check if decision should be lazily evaluated
    public boolean lazyEvaluation(TDRGElement element) {
        if (!this.lazyEvaluation) {
            return false;
        }
        return isSparseDecisionTable(element);
    }

    public boolean isDecision(String name) {
        try {
            return dmnModelRepository.findDRGElementByName(name) instanceof TDecision;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isSparseDecisionTable(TDRGElement element) {
        return element instanceof TDecision && dmnModelRepository.expression(element) instanceof TDecisionTable;
    }

    String lazyEvaluationType(TDRGElement parent, TDRGElement input, String inputJavaType) {
        return lazyEvaluation(parent) && input instanceof TDecision ? String.format("%s<%s>", lazyEvalClassName(), inputJavaType) : inputJavaType;
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

    public String drgElementSignatureExtra(String signature) {
        if (StringUtils.isBlank(signature)) {
            return String.format("%s %s, %s %s",
                    eventListenerClassName(), eventListenerVariableName(),
                    externalExecutorClassName(), externalExecutorVariableName()
            );
        } else {
            return String.format("%s, %s %s, %s %s",
                    signature,
                    eventListenerClassName(), eventListenerVariableName(),
                    externalExecutorClassName(), externalExecutorVariableName()
            );
        }
    }

    public String drgElementArgumentsExtra(String arguments) {
        if (StringUtils.isBlank(arguments)) {
            return String.format("%s, %s", eventListenerVariableName(), externalExecutorVariableName());
        } else {
            return String.format("%s, %s, %s", arguments, eventListenerVariableName(), externalExecutorVariableName());
        }
    }

    public String drgElementDefaultArgumentsExtra(String arguments) {
        if (StringUtils.isBlank(arguments)) {
            return String.format("new %s(LOGGER), new %s()", loggingEventListenerClassName(), defaultExternalExecutorClassName());
        } else {
            return String.format("%s, new %s(LOGGER), new %s()", arguments, loggingEventListenerClassName(), defaultExternalExecutorClassName());
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
        return decisionTableToJavaTransformer.defaultValue(element);
    }

    public String defaultValue(TDRGElement element, TOutputClause output) {
        return decisionTableToJavaTransformer.defaultValue(element, output);
    }

    public String condition(TDRGElement element, TDecisionRule rule) {
        return decisionTableToJavaTransformer.condition(element, rule);
    }

    public String outputEntryToJava(TDRGElement element, TLiteralExpression outputEntryExpression, int outputIndex) {
        return decisionTableToJavaTransformer.outputEntryToJava(element, outputEntryExpression, outputIndex);
    }

    public String outputClauseClassName(TDRGElement element, TOutputClause outputClause) {
        return decisionTableToJavaTransformer.outputClauseClassName(element, outputClause);
    }

    public String outputClauseVariableName(TDRGElement element, TOutputClause outputClause) {
        return decisionTableToJavaTransformer.outputClauseVariableName(element, outputClause);
    }

    public String outputClausePriorityVariableName(TDRGElement element, TOutputClause outputClause) {
        return decisionTableToJavaTransformer.outputClausePriorityVariableName(element, outputClause);
    }

    public String getter(TDRGElement element, TOutputClause output) {
        return decisionTableToJavaTransformer.getter(element, output);
    }

    public String setter(TDRGElement element, TOutputClause output) {
        return decisionTableToJavaTransformer.setter(element, output);
    }

    public Integer priority(TDRGElement element, TLiteralExpression literalExpression, int outputIndex) {
        return decisionTableToJavaTransformer.priority(element, literalExpression, outputIndex);
    }

    public String priorityGetter(TDRGElement element, TOutputClause output) {
        return decisionTableToJavaTransformer.priorityGetter(element, output);
    }

    public String prioritySetter(TDRGElement element, TOutputClause output) {
        return decisionTableToJavaTransformer.prioritySetter(element, output);
    }

    public HitPolicy hitPolicy(TDRGElement element) {
        return decisionTableToJavaTransformer.hitPolicy(element);
    }

    public String aggregation(TDecisionTable decisionTable) {
        return decisionTableToJavaTransformer.aggregation(decisionTable);
    }

    public String aggregator(TDRGElement element, TDecisionTable decisionTable, TOutputClause outputClause, String variableName) {
        return decisionTableToJavaTransformer.aggregator(element, decisionTable, outputClause, variableName);
    }

    public String annotation(TDRGElement element, TDecisionRule rule) {
        return decisionTableToJavaTransformer.annotation(element, rule);
    }

    public String annotationEscapedText(TDecisionRule rule) {
        return decisionTableToJavaTransformer.annotationEscapedText(rule);
    }

    public String escapeInString(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            char ch = text.charAt(i);
            if (ch == '\\') {
                int nextChar = nextChar(text, i);
                if (nextChar == 'd') {
                    // \d used in regular expressions (see replace)
                    builder.append("\\\\d");
                } else if (nextChar == '\"') {
                    // \"
                    builder.append("\\\"");
                } else {
                    builder.append('\\');
                    builder.append(nextChar);
                }
                i = i + 2;
            } else if (ch == '"') {
                if (0 < i && i < text.length() - 1) {
                    // Unescaped inner "
                    builder.append("\\\"");
                } else {
                    builder.append("\"");
                }
                i++;
            } else {
                builder.append(ch);
                i++;
            }
        }
        return builder.toString();
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
        return decisionTableToJavaTransformer.ruleOutputClassName(element);
    }

    public String ruleId(List<TDecisionRule> rules, TDecisionRule rule) {
        return decisionTableToJavaTransformer.ruleId(rules, rule);
    }

    public String abstractRuleOutputClassName() {
        return decisionTableToJavaTransformer.abstractRuleOutputClassName();
    }

    public String ruleOutputListClassName() {
        return decisionTableToJavaTransformer.ruleOutputListClassName();
    }

    public String ruleSignature(TDecision decision) {
        return decisionTableToJavaTransformer.ruleSignature(decision);
    }

    public String ruleArgumentList(TDecision decision) {
        return decisionTableToJavaTransformer.ruleArgumentList(decision);
    }

    public String ruleSignature(TBusinessKnowledgeModel bkm) {
        return decisionTableToJavaTransformer.ruleSignature(bkm);
    }

    public String ruleArgumentList(TBusinessKnowledgeModel bkm) {
        return decisionTableToJavaTransformer.ruleArgumentList(bkm);
    }

    public String hitPolicyAnnotationClassName() {
        return decisionTableToJavaTransformer.hitPolicyAnnotationClassName();
    }

    public String ruleAnnotationClassName() {
        return decisionTableToJavaTransformer.ruleAnnotationClassName();
    }

    //
    // Expression related functions
    //
    public boolean isCompoundStatement(Statement stm) {
        return stm instanceof CompoundStatement;
    }

    public Statement expressionToJava(TDRGElement element) {
        TExpression expression = dmnModelRepository.expression(element);
        if (expression instanceof TContext) {
            return contextToJavaTransformer.expressionToJava((TContext) expression, element);
        } else if (expression instanceof TLiteralExpression) {
            Statement statement = literalExpressionToJavaTransformer.expressionToJava(((TLiteralExpression) expression).getText(), element);
            Type expectedType = toFEELType(drgElementOutputTypeRef(element));
            return convertExpression(statement, expectedType);
        } else if (expression instanceof TInvocation) {
            return invocationToJavaTransformer.expressionToJava((TInvocation) expression, element);
        } else if (expression instanceof TRelation) {
            return relationToJavaTransformer.expressionToJava((TRelation) expression, element);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s'", expression.getClass().getSimpleName()));
        }
    }

    Statement expressionToJava(TExpression expression, Environment environment, TDRGElement element) {
        if (expression instanceof TContext) {
            return contextToJavaTransformer.contextExpressionToJava((TContext) expression, environment, element);
        } else if (expression instanceof TFunctionDefinition) {
            return functionDefinitionToJavaTransformer.functionDefinitionToJava((TFunctionDefinition) expression, environment, element);
        } else if (expression instanceof TLiteralExpression) {
            return literalExpressionToJavaTransformer.literalExpressionToJava(((TLiteralExpression) expression).getText(), environment, element);
        } else if (expression instanceof TInvocation) {
            return invocationToJavaTransformer.invocationExpressionToJava((TInvocation) expression, environment, element);
        } else if (expression instanceof TRelation) {
            return relationToJavaTransformer.relationExpressionToJava((TRelation) expression, environment, element);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s'", expression.getClass().getSimpleName()));
        }
    }

    public String literalExpressionToJava(String expressionText, TDRGElement element) {
        Statement statement = literalExpressionToJavaTransformer.expressionToJava(expressionText, element);
        return ((ExpressionStatement)statement).getExpression();
    }

    public String functionDefinitionToJava(FunctionDefinition element, boolean convertTypeToContext, String body) {
        return this.functionDefinitionToJavaTransformer.functionDefinitionToJava(element, body, convertTypeToContext);
    }

    public String applyMethod(FunctionType functionType, String signature, boolean convertTypeToContext, String body) {
        String returnType = toJavaType(convertType(functionType.getReturnType(), convertTypeToContext));
        String parametersAssignment = parametersAssignment(functionType.getParameters(), convertTypeToContext);
        return applyMethod(returnType, signature, parametersAssignment, body);
    }

    private String parametersAssignment(List<FormalParameter> formalParameters, boolean convertTypeToContext) {
        List<String> parameters = new ArrayList<>();
        for(int i = 0; i< formalParameters.size(); i++) {
            FormalParameter p = formalParameters.get(i);
            String type = toJavaType(convertType(p.getType(), convertTypeToContext));
            String name = javaFriendlyVariableName(p.getName());
            parameters.add(String.format("%s %s = (%s)args[%s];", type, name, type, i));
        }
        return parameters.stream().collect(Collectors.joining(" "));
    }

    public Type convertType(Type type, boolean convertToContext) {
        if (convertToContext) {
            if (type instanceof ItemDefinitionType) {
                type = ((ItemDefinitionType) type).toContextType();
            }
        }
        return type;
    }


    private String applyMethod(String returnType, String signature, String parametersAssignment, String body) {
        String method = String.format(
                "public %s apply(%s) {" +
                    "%s" +
                    "return %s;" +
                "}",
                returnType, signature, parametersAssignment, body);
        return method;
    }

    //
    // Type related functions
    //
    public boolean isComplexType(Type type) {
        return type instanceof ItemDefinitionType
                || type instanceof ListType && ((ListType) type).getElementType() instanceof ItemDefinitionType;
    }

    public Type toFEELType(String typeName) {
        if (StringUtils.isBlank(typeName)) {
            return null;
        }
        // Lookup primitive types
        Type primitiveType = lookupPrimitiveType(new QName(FEEL_NS, typeName));
        if (primitiveType != null) {
            return primitiveType;
        }
        // Lookup item definitions
        String namespace = dmnModelRepository.getDefinitions().getNamespace();
        QName typeRef = new QName(namespace, typeName);
        TItemDefinition itemDefinition = this.dmnModelRepository.lookupItemDefinition(typeRef);
        if (itemDefinition != null) {
            return toFEELType(itemDefinition);
        } else {
            throw new DMNRuntimeException(String.format("Cannot map type '%s' to FEEL", typeRef.toString()));
        }
    }

    public Type toFEELType(QName typeRef) {
        // Lookup primitive types
        Type primitiveType = lookupPrimitiveType(typeRef);
        if (primitiveType != null) {
            return primitiveType;
        }
        // Lookup item definitions
        TItemDefinition itemDefinition = this.dmnModelRepository.lookupItemDefinition(typeRef);
        if (itemDefinition != null) {
            return toFEELType(itemDefinition);
        } else {
            throw new DMNRuntimeException(String.format("Cannot map type '%s' to FEEL", typeRef.toString()));
        }
    }

    Type toFEELType(TItemDefinition itemDefinition) {
        itemDefinition = this.dmnModelRepository.normalize(itemDefinition);
        QName typeRef = itemDefinition.getTypeRef();
        List<TItemDefinition> itemComponent = itemDefinition.getItemComponent();
        if (typeRef == null && (itemComponent == null || itemComponent.isEmpty())) {
            return AnyType.ANY;
        }
        Type type = null;
        if (typeRef != null) {
            type = toFEELType(typeRef);
        } else {
            type = new ItemDefinitionType(itemDefinition.getName());
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

    Type lookupPrimitiveType(QName typeRef) {
        if (!FEEL_NS.equals(typeRef.getNamespaceURI())) {
            return null;
        }
        String typeName = typeRef.getLocalPart();
        Type primitiveType = FEELTypes.FEEL_NAME_TO_FEEL_TYPE.get(typeName);
        return primitiveType;
    }

    //
    // Common functions
    //
    private String toJavaType(QName typeRef) {
        try {
            Type type = toFEELType(typeRef);
            return toJavaType(type);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot map typeRef '%s' to java", typeRef));
        }
    }

    public String toJavaType(TDecision decision) {
        Environment environment = makeDecisionEnvironment(decision);
        TLiteralExpression expression = (TLiteralExpression) decision.getExpression().getValue();
        Type type = feelTranslator.analyzeExpression(expression.getText(), FEELContext.makeContext(environment)).getType();
        return toJavaType(type);
    }

    public String toStringJavaType(Type type) {
        return toJavaType(StringType.STRING);
    }

    public String toQualifiedJavaType(Type type) {
        if (type instanceof NamedType) {
            String typeName = ((NamedType) type).getName();
            String primitiveType = feelTypeTranslator.toQualifiedJavaType(typeName);
            if (!StringUtils.isBlank(primitiveType)) {
                return primitiveType;
            } else {
                return qualifiedName(javaTypePackageName(), upperCaseFirst(typeName));
            }
        } else if (type instanceof ListType) {
            String elementType = toJavaType(((ListType) type).getElementType());
            return String.format("%s<%s>", DMNToJavaTransformer.QUALIFIED_LIST_TYPE, elementType);
        } else if (type instanceof AnyType) {
            return "Object";
        }
        throw new IllegalArgumentException(String.format("Cannot map type '%s' to Java", type.toString()));
    }

    public String toJavaType(Type type) {
        if (type instanceof NamedType) {
            String typeName = ((NamedType) type).getName();
            String primitiveType = feelTypeTranslator.toJavaType(typeName);
            if (!StringUtils.isBlank(primitiveType)) {
                return primitiveType;
            } else {
                return qualifiedName(javaTypePackageName(), upperCaseFirst(typeName));
            }
        } else if (type instanceof ContextType) {
            return contextClassName();
        } else if (type instanceof ListType) {
            if (((ListType) type).getElementType() instanceof AnyType) {
                return String.format("%s<? extends Object>", DMNToJavaTransformer.LIST_TYPE);
            } else {
                String elementType = toJavaType(((ListType) type).getElementType());
                return String.format("%s<%s>", DMNToJavaTransformer.LIST_TYPE, elementType);
            }
        } else if (type instanceof AnyType) {
            return "Object";
        } else if (type instanceof FunctionType) {
            String returnType = toJavaType(((FunctionType) type).getReturnType());
            return String.format("%s<%s>", LambdaExpression.class.getName(), returnType);
        }
        throw new IllegalArgumentException(String.format("Cannot map type '%s' to Java", type));
    }

    public String qualifiedName(String pkg, String name) {
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
            throw new DMNRuntimeException(String.format("Cannot build variable name from empty string"));
        }
        name = this.dmnModelRepository.removeSingleQuotes(name);
        String[] parts = name.split("\\.");
        String result = "";
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            String firstChar = Character.toString(Character.toLowerCase(part.charAt(0)));
            String partName = javaFriendlyName(part.length() == 1 ? firstChar : firstChar + part.substring(1));
            if (i != 0) {
                result += ".";
            }
            result += partName;
        }
        return result;
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

    public String javaRootPackageName() {
        if (StringUtils.isBlank(javaRootPackage)) {
            return "";
        } else {
            return javaRootPackage;
        }
    }

    public String javaTypePackageName() {
        if (StringUtils.isBlank(javaRootPackage)) {
            return DMNToJavaTransformer.DATA_PACKAGE;
        } else {
            return javaRootPackage + "." + DMNToJavaTransformer.DATA_PACKAGE;
        }
    }

    public DRGElementKind elementKind(TDRGElement element) {
        return DRGElementKind.kindByClass(element);
    }

    public ExpressionKind expressionKind(TDRGElement element) {
        TExpression expression = this.dmnModelRepository.expression(element);
        if (expression != null) {
            return ExpressionKind.kindByClass(expression);
        }
        return ExpressionKind.OTHER;
    }

    public String asList(String exp) {
        return String.format("asList(%s)", exp);
    }

    public String asElement(String exp) {
        return String.format("asElement(%s)", exp);
    }

    //
    // Environment related functions
    //
    public Environment makeEnvironment(TDRGElement element) {
        if (element instanceof TBusinessKnowledgeModel) {
            return makeBKMEnvironment((TBusinessKnowledgeModel) element);
        } else if (element instanceof TDecision) {
            return makeDecisionEnvironment((TDecision) element);
        } else {
            throw new UnsupportedOperationException(String.format("'%s' is not supported yet", element.getClass().getSimpleName()));
        }
    }

    private Environment makeBKMEnvironment(TBusinessKnowledgeModel bkm) {
        Environment bkmEnvironment = environmentFactory.makeEnvironment(environmentFactory.getRootEnvironment());
        bkmEnvironment.addDeclaration(makeBusinessKnowledgeModelDeclaration(bkm));
        TFunctionDefinition functionDefinition = bkm.getEncapsulatedLogic();
        functionDefinition.getFormalParameter().forEach(
                p -> bkmEnvironment.addDeclaration(environmentFactory.makeVariableDeclaration(p.getName(), toFEELType(p.getTypeRef()))));
        getDMNModelRepository().allKnowledgeModels(bkm).forEach(
                e -> bkmEnvironment.addDeclaration(makeBusinessKnowledgeModelDeclaration(e)));
        return bkmEnvironment;
    }

    public Environment makeFunctionDefinitionEnvironment(TFunctionDefinition functionDefinition, Environment parentEnvironment) {
        Environment environment = environmentFactory.makeEnvironment(parentEnvironment);
        functionDefinition.getFormalParameter().forEach(
                p -> environment.addDeclaration(environmentFactory.makeVariableDeclaration(p.getName(), toFEELType(p.getTypeRef()))));
        return environment;
    }

    private Environment makeDecisionEnvironment(TDecision decision) {
        Environment decisionEnvironment = environmentFactory.makeEnvironment(environmentFactory.getRootEnvironment());
        decisionEnvironment.addDeclaration(makeVariableDeclaration(decision, decision.getVariable(), decisionEnvironment));
        getDMNModelRepository().allSubDecisions(decision).forEach(
                d -> decisionEnvironment.addDeclaration(makeVariableDeclaration(d, d.getVariable(), decisionEnvironment)));
        getDMNModelRepository().allInputDatas(decision).forEach(
                id -> decisionEnvironment.addDeclaration(makeVariableDeclaration(id, id.getVariable(), decisionEnvironment)));
        getDMNModelRepository().allKnowledgeModels(decision).forEach(
                bkm -> decisionEnvironment.addDeclaration(makeBusinessKnowledgeModelDeclaration(bkm)));
        return decisionEnvironment;
    }

    public Environment makeInputEntryEnvironment(TDRGElement element, Expression inputExpression) {
        Environment environment = environmentFactory.makeEnvironment(makeEnvironment(element), inputExpression);
        environment.addDeclaration(DMNToJavaTransformer.INPUT_ENTRY_PLACE_HOLDER, environmentFactory.makeVariableDeclaration(DMNToJavaTransformer.INPUT_ENTRY_PLACE_HOLDER, inputExpression.getType()));
        return environment;
    }

    Environment makeOutputEntryEnvironment(TDRGElement element, EnvironmentFactory environmentFactory) {
        Environment environment = environmentFactory.makeEnvironment(makeEnvironment(element));
        return environment;
    }

    public Environment makeContextEnvironment(TContext context, Environment elementEnvironment) {
        Environment contextEnvironment = environmentFactory.makeEnvironment(elementEnvironment);
        for(TContextEntry entry: context.getContextEntry()) {
            TInformationItem variable = entry.getVariable();
            if (variable != null) {
                String name = variable.getName();
                Type entryType = entryType(entry, contextEnvironment);
                if (entryType instanceof FunctionType) {
                    contextEnvironment.addDeclaration(environmentFactory.makeFunctionDeclaration(name, (FunctionType) entryType));
                } else {
                    contextEnvironment.addDeclaration(environmentFactory.makeVariableDeclaration(name, entryType));
                }
            }
        }
        return contextEnvironment;
    }

    Type entryType(TContextEntry entry, Environment contextEnvironment) {
        TInformationItem variable = entry.getVariable();
        if (variable != null) {
            QName typeRef = variable.getTypeRef();
            if (typeRef != null) {
                Type feelType = toFEELType(typeRef);
                return feelType;
            }
        }
        JAXBElement<? extends TExpression> expressionElement = entry.getExpression();
        if (expressionElement != null) {
            TExpression value = expressionElement.getValue();
            Type feelType = expressionType(value, contextEnvironment);
            if (feelType != null) return feelType;
        }
        return AnyType.ANY;
    }

    Type expressionType(TExpression expression, Environment environment) {
        if (expression == null) {
            return null;
        }
        QName typeRef = expression.getTypeRef();
        if (typeRef != null) {
            Type feelType = toFEELType(typeRef);
            return feelType;
        }
        if (expression instanceof TContext) {
            List<TContextEntry> contextEntry = ((TContext) expression).getContextEntry();
            ItemDefinitionType contextType = new ItemDefinitionType("");
            for(TContextEntry entry: contextEntry) {
                TInformationItem variable = entry.getVariable();
                if (variable != null) {
                    String name = variable.getName();
                    Type entryType = entryType(entry, environment);
                    contextType.addMember(name, new ArrayList<>(), entryType);
                }
            }
            return contextType;
        } if (expression instanceof TFunctionDefinition) {
            Type type = functionDefinitionType((TFunctionDefinition) expression, environment);
            if (type != null) {
                return type;
            }
        } else if (expression instanceof TLiteralExpression) {
            Type type = literalExpressionType((TLiteralExpression) expression, environment);
            if (type != null) {
                return type;
            }
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", expression.getClass().getSimpleName()));
        }
        return null;
    }

    private Type functionDefinitionType(TFunctionDefinition functionDefinition, Environment environment) {
        JAXBElement<? extends TExpression> expressionElement = functionDefinition.getExpression();
        if (expressionElement != null) {
            TExpression body = expressionElement.getValue();
            QName typeRef = body.getTypeRef();
            if (typeRef != null) {
                return toFEELType(typeRef);
            } else {
                Environment functionDefinitionEnvironment = makeFunctionDefinitionEnvironment(functionDefinition, environment);
                Type bodyType = expressionType(body, functionDefinitionEnvironment);
                List<FormalParameter> parameters = new ArrayList<>();
                for(TInformationItem param: functionDefinition.getFormalParameter()) {
                    Type paramType = toFEELType(param.getTypeRef());
                    parameters.add(new FormalParameter(param.getName(), paramType));
                }
                if (bodyType != null) {
                    return new DMNFunctionType(parameters, bodyType);
                }
            }
        }
        return null;
    }

    private Type literalExpressionType(TLiteralExpression body, Environment environment) {
        FEELContext context = FEELContext.makeContext(environment);
        Expression expression = feelTranslator.analyzeExpression(body.getText(), context);
        return expression.getType();
    }

    public Environment makeRelationEnvironment(TRelation relation, Environment environment) {
        Environment relationEnvironment = environmentFactory.makeEnvironment(environment);
        for(TInformationItem column: relation.getColumn()) {
            QName typeRef = column.getTypeRef();
            if (typeRef != null) {
                String name = column.getName();
                relationEnvironment.addDeclaration(environmentFactory.makeVariableDeclaration(name, toFEELType(typeRef)));
            }
        }
        return relationEnvironment;
    }

    private VariableDeclaration makeVariableDeclaration(TNamedElement element, TInformationItem variable, Environment environment) {
        String name = element.getName();
        if (StringUtils.isBlank(name) || variable == null) {
            throw new DMNRuntimeException(String.format("Name and typeRef cannot be null. Found '%s' and '%s'", name, variable));
        }
        QName typeRef = variable.getTypeRef();
        Type variableType = toFEELType(typeRef);
        if (!variableType.isValid()) {
            TExpression expression = dmnModelRepository.expression(element);
            if (expression != null) {
                variableType = expressionType(expression, environment);
                variableType.validate();
            }
        }
        return environmentFactory.makeVariableDeclaration(name, variableType);
    }

    private FunctionDeclaration makeBusinessKnowledgeModelDeclaration(TBusinessKnowledgeModel bkm) {
        String name = bkm.getName();
        if (StringUtils.isBlank(name)) {
            name = bkm.getVariable().getName();
        }
        List<FormalParameter> parameters = bkmFEELParameters(bkm);
        Type returnType = drgElementOutputFEELType(bkm);
        return environmentFactory.makeBusinessKnowledgeModelDeclaration(name, new DMNFunctionType(parameters, returnType));
    }
}
