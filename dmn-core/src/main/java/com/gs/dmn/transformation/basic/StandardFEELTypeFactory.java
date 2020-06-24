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
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.environment.VariableDeclaration;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.Context;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.StringLiteral;
import com.gs.dmn.feel.lib.StringEscapeUtil;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNVersion;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.*;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StandardFEELTypeFactory implements FEELTypeFactory {
    protected final FEELTypeMemoizer feelTypeMemoizer;
    protected final BasicDMNToNativeTransformer dmnTransformer;
    protected final DMNModelRepository dmnModelRepository;
    protected final EnvironmentFactory environmentFactory;
    protected final DMNEnvironmentFactory dmnEnvironmentFactory;
    protected final FEELTranslator feelTranslator;

    public StandardFEELTypeFactory(BasicDMNToNativeTransformer dmnTransformer) {
        this.dmnTransformer = dmnTransformer;
        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();
        this.dmnEnvironmentFactory = dmnTransformer.getDMNEnvironmentFactory();
        this.feelTranslator = dmnTransformer.getFEELTranslator();

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
        TDefinitions model = this.dmnModelRepository.getModel(element);
        QualifiedName typeRef = this.dmnModelRepository.typeRef(model, element);
        if (typeRef != null) {
            return toFEELType(model, typeRef);
        } else {
            // Infer type from body
            return inferDRGElementOutputFEELType(element, environment);
        }
    }

    @Override
    public Type drgElementVariableFEELType(TDRGElement element) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        QualifiedName typeRef = this.dmnModelRepository.typeRef(model, element);
        Type type = typeRef == null ? null : toFEELType(model, typeRef);
        if (type == null || !type.isValid()) {
            // Infer type from body
            Environment environment = this.dmnTransformer.makeEnvironment(element);
            return inferDRGElementVariableFEELType(element, environment);
        }
        return type;
    }

    @Override
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
            return makeDSType((TDecisionService) element);
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
                    Type entryType = this.dmnEnvironmentFactory.entryType(element, entry, contextEnvironment);
                    contextEnvironment.addDeclaration(this.environmentFactory.makeVariableDeclaration(name, entryType));
                    members.add(new Pair<>(name, entryType));
                } else {
                    returnType = this.dmnEnvironmentFactory.entryType(element, entry, contextEnvironment);
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
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", expression.getClass().getSimpleName()));
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

    @Override
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
        Environment environment = this.dmnEnvironmentFactory.makeEnvironment(decisionService);
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

    @Override
    public FunctionType makeDSType(TDecisionService decisionService) {
        List<FormalParameter> parameters = this.dmnTransformer.dsFEELParameters(decisionService);
        return new DMNFunctionType(parameters, makeDSOutputType(decisionService), decisionService);
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
                Environment functionDefinitionEnvironment = this.dmnEnvironmentFactory.makeFunctionDefinitionEnvironment(element, functionDefinition, environment);
                TFunctionKind kind = functionDefinition.getKind();
                Type bodyType = null;
                if (this.dmnTransformer.isFEELFunction(kind)) {
                    bodyType = expressionType(element, body, functionDefinitionEnvironment);
                } else if (this.dmnTransformer.isJavaFunction(kind)) {
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

    private Type literalExpressionType(TNamedElement element, TLiteralExpression body, Environment environment) {
        FEELContext context = FEELContext.makeContext(element, environment);
        Expression expression = this.feelTranslator.analyzeExpression(body.getText(), context);
        return expression.getType();
    }

    @Override
    public Type externalFunctionReturnFEELType(TNamedElement element, Expression body) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        if (body instanceof Context) {
            Expression javaExpression = ((Context) body).entry("java").getExpression();
            if (javaExpression instanceof Context) {
                Expression returnTypeExp = ((Context) javaExpression).entry("returnType").getExpression();
                if (returnTypeExp instanceof StringLiteral) {
                    String lexeme = ((StringLiteral) returnTypeExp).getLexeme();
                    String typeName = StringEscapeUtil.stripQuotes(lexeme);
                    return toFEELType(model, QualifiedName.toQualifiedName(model, typeName));
                }
            }
        }
        throw new DMNRuntimeException(String.format("Missing returnType in '%s'", body));
    }
}
