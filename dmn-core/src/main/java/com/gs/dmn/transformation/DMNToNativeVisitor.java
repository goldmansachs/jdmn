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
package com.gs.dmn.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.*;
import com.gs.dmn.ast.visitor.TraversalVisitor;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.error.ErrorFactory;
import com.gs.dmn.error.LogErrorHandler;
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class DMNToNativeVisitor extends TraversalVisitor<NativeVisitorContext> {
    private final BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer;
    private final DMNModelRepository dmnModelRepository;
    private final TemplateProcessor templateProcessor;
    private final Path outputPath;
    private final List<String> generatedClasses;
    private final String decisionBaseClass;

    public DMNToNativeVisitor(BuildLogger logger, LogErrorHandler errorHandler, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, DMNModelRepository dmnModelRepository, TemplateProcessor templateProcessor, Path outputPath, List<String> generatedClasses, String decisionBaseClass) {
        super(logger, errorHandler);
        this.dmnTransformer = dmnTransformer;
        this.dmnModelRepository = dmnModelRepository;
        this.templateProcessor = templateProcessor;
        this.outputPath = outputPath;
        this.generatedClasses = generatedClasses;
        this.decisionBaseClass = decisionBaseClass;
    }

    @Override
    public DMNBaseElement visit(TDefinitions element, NativeVisitorContext context) {
        Objects.requireNonNull(element, "Missing " + TDefinitions.class.getSimpleName());

        this.logger.debug(String.format("Generating code for %s '%s'", element.getClass().getSimpleName(), element.getName()));

        // Generate data types
        List<TItemDefinition> itemDefinitions = dmnModelRepository.findItemDefinitions(element);
        if (itemDefinitions != null) {
            for (TItemDefinition itemDefinition : itemDefinitions) {
                itemDefinition.accept(this, context);
            }
        }

        // Generate BKMs
        List<TBusinessKnowledgeModel> businessKnowledgeModels = dmnModelRepository.findBKMs(element);
        for (TBusinessKnowledgeModel bkm : businessKnowledgeModels) {
            bkm.accept(this, context);
        }

        // Generate DSs
        List<TDecisionService> decisionServices = dmnModelRepository.findDSs(element);
        for (TDecisionService ds : decisionServices) {
            ds.accept(this, context);
        }

        // Generate decisions
        List<TDecision> decisions = dmnModelRepository.findDecisions(element);
        for (TDecision decision : decisions) {
            decision.accept(this, context);
        }

        return element;
    }

    @Override
    public DMNBaseElement visit(TItemDefinition element, NativeVisitorContext context) {
        Objects.requireNonNull(element, "Missing " + TItemDefinition.class.getSimpleName());

        this.logger.debug(String.format("Generating code for %s '%s'", element.getClass().getSimpleName(), element.getName()));

        TDefinitions definitions = context.getDefinitions();
        try {
            // Complex type
            if (dmnTransformer.getDMNModelRepository().hasComponents(element)) {
                this.logger.debug(String.format("Generating code for ItemDefinition '%s'", element.getName()));

                String typePackageName = dmnTransformer.nativeTypePackageName(definitions.getName());

                // Generate interface and class
                String nativeInterfaceName = dmnTransformer.itemDefinitionNativeSimpleInterfaceName(element);
                transformItemDefinition(definitions, element, dmnTransformer, templateProcessor.getTemplateProvider().baseTemplatePath(), templateProcessor.getTemplateProvider().itemDefinitionInterfaceTemplate(), generatedClasses, outputPath, typePackageName, nativeInterfaceName);
                transformItemDefinition(definitions, element, dmnTransformer, templateProcessor.getTemplateProvider().baseTemplatePath(), templateProcessor.getTemplateProvider().itemDefinitionClassTemplate(), generatedClasses, outputPath, typePackageName, dmnTransformer.itemDefinitionNativeClassName(nativeInterfaceName));

                // Process children
                List<TItemDefinition> itemDefinitionList = element.getItemComponent();
                if (itemDefinitionList != null) {
                    for (TItemDefinition itemDefinition1 : itemDefinitionList) {
                        itemDefinition1.accept(this, context);
                    }
                }
            }

            return element;
        } catch (Exception e) {
            String errorMessage = makeErrorMessage(element, definitions);
            throw new SemanticError(errorMessage, e);
        }
    }

    private void transformItemDefinition(TDefinitions definitions, TItemDefinition itemDefinition, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, String baseTemplatePath, String itemDefinitionTemplate, List<String> generatedClasses, Path outputPath, String typePackageName, String typeName) {
        String qualifiedName = dmnTransformer.qualifiedName(typePackageName, typeName);
        if (generatedClasses.contains(qualifiedName)) {
            this.logger.warn(String.format("Class '%s' has already been generated", typeName));
        } else {
            templateProcessor.processTemplate(definitions, itemDefinition, baseTemplatePath, itemDefinitionTemplate, dmnTransformer, outputPath, typePackageName, typeName);
            generatedClasses.add(qualifiedName);
        }
    }

    @Override
    public DMNBaseElement visit(TBusinessKnowledgeModel element, NativeVisitorContext context) {
        Objects.requireNonNull(element, "Missing " + TBusinessKnowledgeModel.class.getSimpleName());

        this.logger.debug(String.format("Generating code for %s '%s'", element.getClass().getSimpleName(), element.getName()));

        TDefinitions definitions = context.getDefinitions();
        try {
            String bkmPackageName = dmnTransformer.nativeModelPackageName(definitions.getName());
            String bkmClassName = dmnTransformer.drgElementClassName(element);
            checkDuplicate(generatedClasses, bkmPackageName, bkmClassName, dmnTransformer);
            templateProcessor.processTemplate(definitions, element, templateProcessor.getTemplateProvider().baseTemplatePath(), templateProcessor.getTemplateProvider().bkmTemplateName(), dmnTransformer, outputPath, bkmPackageName, bkmClassName, decisionBaseClass);

            if (dmnTransformer.getDMNModelRepository().isDecisionTableExpression(element)) {
                String decisionRuleOutputClassName = dmnTransformer.ruleOutputClassName(element);
                checkDuplicate(generatedClasses, bkmPackageName, decisionRuleOutputClassName, dmnTransformer);
                templateProcessor.processTemplate(definitions, element, templateProcessor.getTemplateProvider().baseTemplatePath(), templateProcessor.getTemplateProvider().decisionTableRuleOutputTemplate(), dmnTransformer, outputPath, bkmPackageName, decisionRuleOutputClassName, decisionBaseClass);
            }

            return element;
        } catch (Exception e) {
            String errorMessage = makeErrorMessage(element, definitions);
            throw new SemanticError(errorMessage, e);
        }
    }

    @Override
    public DMNBaseElement visit(TDecisionService element, NativeVisitorContext context) {
        Objects.requireNonNull(element, "Missing " + TDecisionService.class.getSimpleName());

        this.logger.debug(String.format("Generating code for %s '%s'", element.getClass().getSimpleName(), element.getName()));

        TDefinitions definitions = context.getDefinitions();
        try {
            String dsPackageName = dmnTransformer.nativeModelPackageName(definitions.getName());
            String dsClassName = dmnTransformer.drgElementClassName(element);
            checkDuplicate(generatedClasses, dsPackageName, dsClassName, dmnTransformer);
            templateProcessor.processTemplate(definitions, element, templateProcessor.getTemplateProvider().baseTemplatePath(), templateProcessor.getTemplateProvider().dsTemplateName(), dmnTransformer, outputPath, dsPackageName, dsClassName, decisionBaseClass);

            return element;
        } catch (Exception e) {
            String errorMessage = makeErrorMessage(element, definitions);
            throw new SemanticError(errorMessage, e);
        }
    }

    @Override
    public DMNBaseElement visit(TDecision element, NativeVisitorContext context) {
        Objects.requireNonNull(element, "Missing " + TDecision.class.getSimpleName());

        this.logger.debug(String.format("Generating code for %s '%s'", element.getClass().getSimpleName(), element.getName()));

        TDefinitions definitions = context.getDefinitions();
        try {
            String decisionPackageName = dmnTransformer.nativeModelPackageName(definitions.getName());
            String decisionClassName = dmnTransformer.drgElementClassName(element);
            checkDuplicate(generatedClasses, decisionPackageName, decisionClassName, dmnTransformer);
            templateProcessor.processTemplate(definitions, element, templateProcessor.getTemplateProvider().baseTemplatePath(), templateProcessor.getTemplateProvider().decisionTemplateName(), dmnTransformer, outputPath, decisionPackageName, decisionClassName, decisionBaseClass);

            if (dmnTransformer.getDMNModelRepository().isDecisionTableExpression(element)) {
                String decisionRuleOutputClassName = dmnTransformer.ruleOutputClassName(element);
                checkDuplicate(generatedClasses, decisionPackageName, decisionRuleOutputClassName, dmnTransformer);
                templateProcessor.processTemplate(definitions, element, templateProcessor.getTemplateProvider().baseTemplatePath(), templateProcessor.getTemplateProvider().decisionTableRuleOutputTemplate(), dmnTransformer, outputPath, decisionPackageName, decisionRuleOutputClassName, decisionBaseClass);
            }

            return element;
        } catch (Exception e) {
            String errorMessage = makeErrorMessage(element, definitions);
            throw new SemanticError(errorMessage, e);
        }
    }

    private void checkDuplicate(List<String> generatedClasses, String pkg, String className, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        String qualifiedName = dmnTransformer.qualifiedName(pkg, className);
        if (generatedClasses.contains(qualifiedName)) {
            throw new DMNRuntimeException(String.format("Class '%s' has already been generated", qualifiedName));
        } else {
            generatedClasses.add(qualifiedName);
        }
    }

    public static String makeErrorMessage(TNamedElement element, TDefinitions definitions) {
        String errorMessage = String.format("Error translating DMN element '%s' to native platform", element);
        return ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage);
    }
}
