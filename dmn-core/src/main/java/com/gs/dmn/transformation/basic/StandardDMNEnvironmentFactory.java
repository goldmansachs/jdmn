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
import com.gs.dmn.DRGElementReference;
import com.gs.dmn.feel.analysis.semantics.environment.*;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.Context;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ContextEntry;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.StringLiteral;
import com.gs.dmn.feel.lib.StringEscapeUtil;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.interpreter.ImportPath;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.transformation.AbstractDMNToNativeTransformer;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20191111.model.*;

import javax.xml.bind.JAXBElement;
import java.util.*;

public class StandardDMNEnvironmentFactory implements DMNEnvironmentFactory {
    protected final BasicDMNToNativeTransformer dmnTransformer;

    protected final DMNModelRepository dmnModelRepository;
    protected final EnvironmentFactory environmentFactory;

    protected final FEELTranslator feelTranslator;

    private final FEELTypeMemoizer feelTypeMemoizer;
    private final EnvironmentMemoizer environmentMemoizer;

    public StandardDMNEnvironmentFactory(BasicDMNToNativeTransformer dmnTransformer) {
        this.dmnTransformer = dmnTransformer;

        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();

        this.feelTranslator = dmnTransformer.getFEELTranslator();

        this.environmentMemoizer = new EnvironmentMemoizer();
        this.feelTypeMemoizer = new FEELTypeMemoizer();
    }

    //
    // DRG Elements
    //
    @Override
    public Type drgElementOutputFEELType(TDRGElement element) {
        return drgElementOutputFEELType(element, this.dmnTransformer.makeEnvironment(element));
    }

    @Override
    public Type drgElementOutputFEELType(TDRGElement element, Environment environment) {
        Type type = this.drgElementVariableFEELType(element, environment);
        return drgElementOutputFEELType(element, type);
    }

    private Type drgElementOutputFEELType(TDRGElement element, Type type) {
        if (element instanceof TInputData) {
            return type;
        } else if (element instanceof TDecision) {
            return type;
        } else if (element instanceof TInvocable) {
            if (type instanceof FunctionType) {
                return ((FunctionType) type).getReturnType();
            } else {
                throw new DMNRuntimeException(String.format("Expected function type for element '%s'. Found '%s'", element.getName(), type));
            }
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", element.getClass().getName()));
        }
    }

    @Override
    public Type drgElementVariableFEELType(TDRGElement element) {
        return drgElementVariableFEELType(element, this.dmnTransformer.makeEnvironment(element));
    }

    @Override
    public Type drgElementVariableFEELType(TDRGElement element, Environment environment) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        QualifiedName typeRef = this.dmnModelRepository.variableTypeRef(model, element);
        Type declaredType = typeRef == null ? null : toFEELType(model, typeRef);
        if (declaredType == null) {
            // Infer type from body
            return inferDRGElementVariableFEELType(element, environment);
        } else if (!declaredType.isValid()) {
            Type inferredType = inferDRGElementVariableFEELType(element, environment);
            return refineDeclaredType(declaredType, inferredType);
        } else {
            return declaredType;
        }
    }

    private Type refineDeclaredType(Type declaredType, Type inferredType) {
        if (declaredType == null) {
            return inferredType;
        }
        if (declaredType instanceof ItemDefinitionType && inferredType instanceof CompositeDataType) {
            ItemDefinitionType oldType = (ItemDefinitionType) declaredType;
            ItemDefinitionType newType = new ItemDefinitionType(oldType.getName(), oldType.getModelName());
            for (String member: oldType.getMembers()) {
                Type oldMemberType = oldType.getMemberType(member);
                if (oldMemberType == null || oldMemberType == AnyType.ANY) {
                    Type newMemberType = ((CompositeDataType) inferredType).getMemberType(member);
                    if (newMemberType != null && newMemberType != AnyType.ANY) {
                        newType.addMember(member, oldType.getAliases(member), newMemberType);
                    } else {
                        newType.addMember(member, oldType.getAliases(member), oldMemberType);
                    }
                }
            }
            return newType;
        }
        return declaredType;
    }

    private Type inferDRGElementOutputFEELType(TDRGElement element, Environment environment) {
        if (element instanceof TDecision) {
            return expressionType(element, ((TDecision) element).getExpression(), environment);
        } else if (element instanceof TBusinessKnowledgeModel) {
            Type type = expressionType(element, ((TBusinessKnowledgeModel) element).getEncapsulatedLogic(), environment);
            return ((FunctionType)type).getReturnType();
        } else if (element instanceof TDecisionService) {
            return this.makeDSOutputType((TDecisionService) element);
        }
        throw new DMNRuntimeException(String.format("Cannot infer the output type of '%s'", element.getName()));
    }

    private Type inferDRGElementVariableFEELType(TDRGElement element, Environment environment) {
        if (element instanceof TDecision) {
            return expressionType(element, ((TDecision) element).getExpression(), environment);
        } else if (element instanceof TBusinessKnowledgeModel) {
            return expressionType(element, ((TBusinessKnowledgeModel) element).getEncapsulatedLogic(), environment);
        } else if (element instanceof TDecisionService) {
            return makeDSVariableType((TDecisionService) element);
        }
        throw new DMNRuntimeException(String.format("Cannot infer the output type of '%s'", element.getName()));
    }

    @Override
    public Type toFEELType(TInputData inputData) {
        TDefinitions model = this.dmnModelRepository.getModel(inputData);
        String typeRefString = inputData.getVariable().getTypeRef();
        QualifiedName typeRef = QualifiedName.toQualifiedName(model, typeRefString);
        return toFEELType(model, typeRef);
    }

    //
    // Expression related functions
    //
    @Override
    public Type expressionType(TDRGElement element, JAXBElement<? extends TExpression> jElement, Environment environment) {
        return jElement == null ? null : expressionType(element, jElement.getValue(), environment);
    }

    @Override
    public Type expressionType(TDRGElement element, TExpression expression, Environment environment) {
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
                    Type entryType = this.entryType(element, entry, contextEnvironment);
                    contextEnvironment.addDeclaration(this.environmentFactory.makeVariableDeclaration(name, entryType));
                    members.add(new Pair<>(name, entryType));
                } else {
                    returnType = this.entryType(element, entry, contextEnvironment);
                }
            }
            // Infer return type
            if (returnType == null) {
                ContextType contextType = new ContextType();
                for (Pair<String, Type> member: members) {
                    contextType.addMember(member.getLeft(), new ArrayList<>(), member.getRight());
                }
                return contextType;
            } else {
                return returnType;
            }
        } else if (expression instanceof TFunctionDefinition) {
            return functionDefinitionType(element, (TFunctionDefinition) expression, environment);
        } else if (expression instanceof TLiteralExpression) {
            return literalExpressionType(element, (TLiteralExpression) expression, environment);
        } else if (expression instanceof TInvocation) {
            TExpression body = ((TInvocation) expression).getExpression().getValue();
            if (body instanceof TLiteralExpression) {
                String bkmName = ((TLiteralExpression) body).getText();
                TBusinessKnowledgeModel bkm = this.dmnModelRepository.findKnowledgeModelByName(bkmName);
                if (bkm == null) {
                    throw new DMNRuntimeException(String.format("Cannot find BKM for '%s'", bkmName));
                }
                return drgElementOutputFEELType(bkm);
            } else {
                throw new DMNRuntimeException(String.format("Not supported '%s'", body.getClass().getSimpleName()));
            }
        } else if (expression instanceof TDecisionTable) {
            // Derive from outputClauses clauses and rules
            TDecisionTable dt = (TDecisionTable) expression;
            List<TOutputClause> outputClauses = dt.getOutput();
            List<TLiteralExpression> outputEntries = outputEntries(dt);
            Map<String, Type> members = new LinkedHashMap<>();
            if (outputClauses != null) {
                for (int i=0; i<outputClauses.size(); i++) {
                    // Derive typeRef from output clause
                    TOutputClause outputClause = outputClauses.get(i);
                    Type type = toFEELType(model, element, outputEntries, outputClause, i, environment);
                    members.put(outputClause.getName(), type);
                }
                Type expressionType;
                if (members.isEmpty()) {
                    throw new DMNRuntimeException(String.format("Cannot infer type for '%s' from empty OutputClauses", element.getName()));
                } else if (members.size() == 1) {
                    expressionType = members.values().iterator().next();
                } else {
                    expressionType = new ContextType(members);
                }
                return applyPolicies(element, dt, expressionType);
            }
            throw new DMNRuntimeException(String.format("Cannot infer type for '%s' from empty OutputClauses", element.getName()));
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", expression.getClass().getSimpleName()));
        }
    }

    private List<TLiteralExpression> outputEntries(TDecisionTable dt) {
        List<TLiteralExpression> outputEntries = new ArrayList<>();
        List<TDecisionRule> rules = dt.getRule();
        if (rules != null && !rules.isEmpty()) {
            outputEntries = rules.get(0).getOutputEntry();
        }
        return outputEntries;
    }

    @Override
    public Type toFEELType(TDRGElement element, TOutputClause outputClause, int index) {
        TExpression expression = this.dmnModelRepository.expression(element);
        if (!(expression instanceof TDecisionTable)) {
            throw new DMNRuntimeException(String.format("Expected Decision Table in element '%s', found '%s'", element.getName(), expression == null ? null : expression.getClass().getName()));
        }

        TDefinitions model = this.dmnModelRepository.getModel(element);
        TDecisionTable dt = (TDecisionTable) expression;
        List<TLiteralExpression> outputEntries = this.outputEntries(dt);
        Environment environment = this.makeEnvironment(element);
        return this.toFEELType(model, element, outputEntries, outputClause, index, environment);
    }

    private Type toFEELType(TDefinitions model, TDRGElement element, List<TLiteralExpression> outputEntries, TOutputClause outputClause, int index, Environment environment) {
        String outputTypeRef = outputClause.getTypeRef();
        Type type;
        if (outputTypeRef == null) {
            if (index < outputEntries.size()) {
                type = expressionType(element, outputEntries.get(index), environment);
                if (type == null) {
                    throw new DMNRuntimeException(String.format("Cannot infer type for '%s' from OutputEntries", element.getName()));
                }
            } else {
                throw new DMNRuntimeException(String.format("Cannot infer type for '%s' from OutputEntries", element.getName()));
            }
        } else {
            type = toFEELType(model, outputTypeRef);
        }
        return type;
    }

    private Type applyPolicies(TDRGElement element, TDecisionTable decisionTable, Type type) {
        TBuiltinAggregator aggregation = decisionTable.getAggregation();
        if (decisionTable.getHitPolicy() == THitPolicy.COLLECT && type != null) {
            type = new ListType(type);
        }
        if (aggregation == TBuiltinAggregator.COUNT || aggregation == TBuiltinAggregator.SUM) {
            return NumberType.NUMBER;
        } else if (aggregation == TBuiltinAggregator.MIN || aggregation == TBuiltinAggregator.MAX) {
            if (type instanceof ListType) {
                return ((ListType) type).getElementType();
            } else {
                throw new DMNRuntimeException(String.format("Expected list type, found '%s' for element '%s", type, element.getName()));
            }
        } else {
            return type;
        }
    }

    @Override
    public Type convertType(Type type, boolean convertToContext) {
        if (convertToContext) {
            if (type instanceof ItemDefinitionType) {
                type = ((ItemDefinitionType) type).toContextType();
            }
        }
        return type;
    }

    //
    // Common Type functions
    //
    @Override
    public Type toFEELType(TDefinitions model, String typeName) {
        QualifiedName qName = QualifiedName.toQualifiedName(model, typeName);
        return toFEELType(model, qName);
    }

    @Override
    public Type toFEELType(TDefinitions model, QualifiedName typeRef) {
        if (typeRef == null) {
            throw new DMNRuntimeException(String.format("Cannot infer type for typeRef '%s'", typeRef));
        }
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

    @Override
    public Type toFEELType(TItemDefinition itemDefinition) {
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
                ((ItemDefinitionType)type).addMember(item.getName(), Collections.singletonList(item.getLabel()), toFEELType(item));
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

    private Type makeDSOutputType(TDecisionService decisionService) {
        TDefinitions model = this.dmnModelRepository.getModel(decisionService);
        // Derive from variable
        TInformationItem variable = decisionService.getVariable();
        if (variable != null && variable.getTypeRef() != null) {
            return toFEELType(model, variable.getTypeRef());
        }
        // Derive from decisions
        Environment environment = this.makeEnvironment(decisionService);
        List<TDMNElementReference> outputDecisions = decisionService.getOutputDecision();
        if (outputDecisions.size() == 1) {
            TDecision decision = this.dmnModelRepository.findDecisionByRef(decisionService, outputDecisions.get(0).getHref());
            String decisionName = decision.getName();
            VariableDeclaration declaration = (VariableDeclaration) environment.lookupVariableDeclaration(decisionName);
            return declaration.getType();
        } else {
            ContextType type = new ContextType();
            for (TDMNElementReference er: outputDecisions) {
                TDecision decision = this.dmnModelRepository.findDecisionByRef(decisionService, er.getHref());
                String decisionName = decision.getName();
                VariableDeclaration declaration = (VariableDeclaration) environment.lookupVariableDeclaration(decisionName);
                type.addMember(decisionName, Collections.emptyList(), declaration.getType());
            }
            return type;
        }
    }

    private FunctionType makeDSVariableType(TDecisionService decisionService) {
        List<FormalParameter> parameters = this.dmnTransformer.dsFEELParameters(decisionService);
        return new DMNFunctionType(parameters, makeDSOutputType(decisionService), decisionService);
    }

    private Type functionDefinitionType(TDRGElement element, TFunctionDefinition functionDefinition, Environment environment) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        JAXBElement<? extends TExpression> expressionElement = functionDefinition.getExpression();
        if (expressionElement != null) {
            // Calculate body type
            Type bodyType;
            TExpression body = expressionElement.getValue();
            QualifiedName typeRef = QualifiedName.toQualifiedName(model, body.getTypeRef());
            if (typeRef != null) {
                bodyType = toFEELType(model, typeRef);
            } else {
                Environment functionDefinitionEnvironment = this.makeFunctionDefinitionEnvironment(element, functionDefinition, environment);
                TFunctionKind kind = functionDefinition.getKind();
                if (this.dmnTransformer.isFEELFunction(kind)) {
                    bodyType = expressionType(element, body, functionDefinitionEnvironment);
                } else if (this.dmnTransformer.isJavaFunction(kind)) {
                    bodyType = AnyType.ANY;
                } else {
                    throw new DMNRuntimeException(String.format("DRGElement '%s': Kind '%s' is not supported yet", element.getName(), kind));
                }
            }
            // Make function type
            List<FormalParameter> parameters = new ArrayList<>();
            for(TInformationItem param: functionDefinition.getFormalParameter()) {
                Type paramType = toFEELType(model, QualifiedName.toQualifiedName(model, param.getTypeRef()));
                parameters.add(new FormalParameter(param.getName(), paramType));
            }
            if (bodyType != null) {
                return new DMNFunctionType(parameters, bodyType, element, functionDefinition);
            }
        }
        return null;
    }

    private Type literalExpressionType(TNamedElement element, TLiteralExpression body, Environment environment) {
        FEELContext context = FEELContext.makeContext(element, environment);
        Expression expression = this.feelTranslator.analyzeExpression(body.getText(), context);
        return expression.getType();
    }

    @Override
    public Type externalFunctionReturnFEELType(TNamedElement element, Expression body) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        if (body instanceof Context) {
            ContextEntry javaEntry = ((Context) body).entry("java");
            if (javaEntry != null) {
                Expression javaExpression = javaEntry.getExpression();
                if (javaExpression instanceof Context) {
                    ContextEntry returnTypeEntry = ((Context) javaExpression).entry("returnType");
                    if (returnTypeEntry != null) {
                        Expression returnTypeExp = returnTypeEntry.getExpression();
                        if (returnTypeExp instanceof StringLiteral) {
                            String lexeme = ((StringLiteral) returnTypeExp).getLexeme();
                            String typeName = StringEscapeUtil.stripQuotes(lexeme);
                            return toFEELType(model, QualifiedName.toQualifiedName(model, typeName));
                        }
                    }
                }
            }
        }
        throw new DMNRuntimeException(String.format("Missing returnType in '%s'", body));
    }

    @Override
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

    @Override
    public Environment makeEnvironment(TDRGElement element, Environment parentEnvironment) {
        return makeEnvironment(element, parentEnvironment, true);
    }

    @Override
    public Environment makeEnvironment(TDRGElement element, Environment parentEnvironment, boolean isRecursive) {
        Environment elementEnvironment = this.environmentFactory.makeEnvironment(parentEnvironment);

        // Add declaration for each direct child
        List<DRGElementReference<? extends TDRGElement>> directReferences = this.dmnModelRepository.directDRGElements(element);
        for (DRGElementReference<? extends TDRGElement> reference: directReferences) {
            // Create child environment to infer type if needed
            TDRGElement child = reference.getElement();
            Declaration declaration = makeDeclaration(element, elementEnvironment, child);
            addDeclaration(elementEnvironment, declaration, element, child);
        }

        // Add it to cache to avoid infinite loops
        this.environmentMemoizer.put(element, elementEnvironment);
        // Add declaration of element to support recursion
        if (isRecursive) {
            Declaration declaration = makeDeclaration(element, elementEnvironment, element);
            addDeclaration(elementEnvironment, declaration, element, element);
        }

        // Add declaration for parameters
        if (element instanceof TBusinessKnowledgeModel) {
            Environment bkmEnvironment = this.environmentFactory.makeEnvironment(elementEnvironment);
            TDefinitions definitions = this.dmnModelRepository.getModel(element);
            TFunctionDefinition functionDefinition = ((TBusinessKnowledgeModel) element).getEncapsulatedLogic();
            if (functionDefinition != null) {
                functionDefinition.getFormalParameter().forEach(
                        p -> bkmEnvironment.addDeclaration(this.environmentFactory.makeVariableDeclaration(p.getName(), this.dmnTransformer.toFEELType(definitions, QualifiedName.toQualifiedName(definitions, p.getTypeRef())))));
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
                Type importType = importDeclaration.getType();
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

    //
    // Decision Table
    //
    @Override
    public Environment makeInputEntryEnvironment(TDRGElement element, Expression inputExpression) {
        Environment environment = this.environmentFactory.makeEnvironment(this.dmnTransformer.makeEnvironment(element), inputExpression);
        environment.addDeclaration(AbstractDMNToNativeTransformer.INPUT_ENTRY_PLACE_HOLDER, this.environmentFactory.makeVariableDeclaration(AbstractDMNToNativeTransformer.INPUT_ENTRY_PLACE_HOLDER, inputExpression.getType()));
        return environment;
    }

    @Override
    public Environment makeOutputEntryEnvironment(TDRGElement element, EnvironmentFactory environmentFactory) {
        return this.environmentFactory.makeEnvironment(this.dmnTransformer.makeEnvironment(element));
    }

    //
    // Function Definition
    //
    @Override
    public Environment makeFunctionDefinitionEnvironment(TNamedElement element, TFunctionDefinition functionDefinition, Environment parentEnvironment) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        Environment environment = this.environmentFactory.makeEnvironment(parentEnvironment);
        functionDefinition.getFormalParameter().forEach(
                p -> environment.addDeclaration(this.environmentFactory.makeVariableDeclaration(p.getName(), this.dmnTransformer.toFEELType(model, QualifiedName.toQualifiedName(model, p.getTypeRef())))));
        return environment;
    }

    @Override
    public Declaration makeVariableDeclaration(TDRGElement element, TInformationItem variable) {
        // Check variable
        String name = element.getName();
        if (StringUtils.isBlank(name) && variable != null) {
            name = variable.getName();
        }
        if (StringUtils.isBlank(name) || variable == null) {
            throw new DMNRuntimeException(String.format("Name and variable cannot be null. Found '%s' and '%s'", name, variable));
        }

        Type variableType = this.dmnTransformer.drgElementVariableFEELType(element);
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

    private FunctionDeclaration makeDSDeclaration(TDecisionService ds) {
        TInformationItem variable = ds.getVariable();
        String name = ds.getName();
        if (StringUtils.isBlank(name) && variable != null) {
            name = variable.getName();
        }
        if (StringUtils.isBlank(name)) {
            throw new DMNRuntimeException(String.format("Name and variable cannot be null. Found '%s' and '%s'", name, variable));
        }
        FunctionType serviceType = (FunctionType) this.drgElementVariableFEELType(ds);
        return this.environmentFactory.makeDecisionServiceDeclaration(name, serviceType);
    }

    private FunctionDeclaration makeBKMDeclaration(TBusinessKnowledgeModel bkm) {
        TInformationItem variable = bkm.getVariable();
        String name = bkm.getName();
        if (StringUtils.isBlank(name) && variable != null) {
            name = variable.getName();
        }
        if (StringUtils.isBlank(name)) {
            throw new DMNRuntimeException(String.format("Name and variable cannot be null. Found '%s' and '%s'", name, variable));
        }
        List<FormalParameter> parameters = this.dmnTransformer.bkmFEELParameters(bkm);
        Type returnType = this.dmnTransformer.drgElementOutputFEELType(bkm);
        return this.environmentFactory.makeBusinessKnowledgeModelDeclaration(name, new DMNFunctionType(parameters, returnType, bkm, bkm.getEncapsulatedLogic()));
    }

    //
    // Context
    //
    @Override
    public Pair<Environment, Map<TContextEntry, Expression>> makeContextEnvironment(TDRGElement element, TContext context, Environment parentEnvironment) {
        Environment contextEnvironment = this.dmnTransformer.makeEnvironment(element, parentEnvironment);
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

    @Override
    public Type entryType(TDRGElement element, TContextEntry entry, TExpression expression, Expression feelExpression) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        TInformationItem variable = entry.getVariable();
        Type entryType = variableType(element, variable);
        if (entryType != null) {
            return entryType;
        }
        QualifiedName typeRef = expression == null ? null : QualifiedName.toQualifiedName(model, expression.getTypeRef());
        if (typeRef != null) {
            entryType = this.dmnTransformer.toFEELType(model, typeRef);
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

    @Override
    public Type entryType(TDRGElement element, TContextEntry entry, Environment contextEnvironment) {
        TInformationItem variable = entry.getVariable();
        Type feelType = variableType(element, variable);
        if (feelType != null) {
            return feelType;
        }
        // Infer type from expression
        feelType = this.dmnTransformer.expressionType(element, entry.getExpression(), contextEnvironment);
        return feelType == null ? AnyType.ANY : feelType;
    }

    private Type variableType(TNamedElement element, TInformationItem variable) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        if (variable != null) {
            QualifiedName typeRef = QualifiedName.toQualifiedName(model, variable.getTypeRef());
            if (typeRef != null) {
                return this.dmnTransformer.toFEELType(model, typeRef);
            }
        }
        return null;
    }

    //
    // Relation
    //
    @Override
    public Environment makeRelationEnvironment(TNamedElement element, TRelation relation, Environment environment) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        Environment relationEnvironment = this.environmentFactory.makeEnvironment(environment);
        for(TInformationItem column: relation.getColumn()) {
            QualifiedName typeRef = QualifiedName.toQualifiedName(model, column.getTypeRef());
            if (typeRef != null) {
                String name = column.getName();
                relationEnvironment.addDeclaration(this.environmentFactory.makeVariableDeclaration(name, this.dmnTransformer.toFEELType(model, typeRef)));
            }
        }
        return relationEnvironment;
    }
}
