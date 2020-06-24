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
import com.gs.dmn.feel.analysis.semantics.type.AnyType;
import com.gs.dmn.feel.analysis.semantics.type.DMNFunctionType;
import com.gs.dmn.feel.analysis.semantics.type.FunctionType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.interpreter.ImportPath;
import com.gs.dmn.transformation.AbstractDMNToNativeTransformer;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.*;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StandardDMNEnvironmentFactory implements DMNEnvironmentFactory {
    protected final DMNModelRepository dmnModelRepository;
    protected final EnvironmentFactory environmentFactory;
    private final EnvironmentMemoizer environmentMemoizer;
    protected final BasicDMNToNativeTransformer dmnTransformer;
    private final FEELTranslator feelTranslator;
    private final StandardFEELTypeFactory feelTypeFactory;

    public StandardDMNEnvironmentFactory(BasicDMNToNativeTransformer dmnTransformer) {
        this.dmnTransformer = dmnTransformer;

        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();
        this.feelTranslator = dmnTransformer.getFEELTranslator();
        this.feelTypeFactory = dmnTransformer.getFEELTypeFactory();

        this.environmentMemoizer = new EnvironmentMemoizer();
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
        Declaration declaration = makeDeclaration(element, elementEnvironment, element);
        addDeclaration(elementEnvironment, declaration, element, element);

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
        FunctionType serviceType = this.feelTypeFactory.makeDSType(ds);
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
        return this.environmentFactory.makeBusinessKnowledgeModelDeclaration(name, new DMNFunctionType(parameters, returnType, bkm));
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
