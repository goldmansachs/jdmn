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
package com.gs.dmn.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import org.omg.spec.dmn._20180521.model.TBusinessKnowledgeModel;
import org.omg.spec.dmn._20180521.model.TDecision;
import org.omg.spec.dmn._20180521.model.TItemDefinition;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

public class DMNToJavaTransformer extends AbstractDMNTransformer {
    public static final String DATA_PACKAGE = "type";
    public static final String DECISION_RULE_OUTPUT_CLASS_SUFFIX = "RuleOutput";
    public static final String PRIORITY_SUFFIX = "Priority";
    public static final String INPUT_ENTRY_PLACE_HOLDER = "?";

    public static final String LIST_TYPE = "List";
    public static final String QUALIFIED_LIST_TYPE = "java.util.List";
    public static final String CONTEXT_CLASS_NAME = Context.class.getName();

    public static final String FREE_TEXT_LANGUAGE = "free_text";
    public static final List<String> SUPPORTED_LANGUAGES = Arrays.asList(DMNConstants.FEEL_11_NS, DMNConstants.FEEL_12_NS, DMNConstants.FEEL_12_PREFIX, FREE_TEXT_LANGUAGE);

    protected final String dmnVersion;
    protected final String modelVersion;
    protected final String platformVersion;

    public DMNToJavaTransformer(DMNDialectDefinition dialectDefinition, DMNValidator dmnValidator, DMNTransformer dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, Map<String, String> inputParameters, BuildLogger logger) {
        super(dialectDefinition, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, inputParameters, logger);

        this.dmnVersion = InputParamUtil.getRequiredParam(inputParameters, "dmnVersion");
        this.modelVersion = InputParamUtil.getRequiredParam(inputParameters, "modelVersion");
        this.platformVersion = InputParamUtil.getRequiredParam(inputParameters, "platformVersion");
    }

    @Override
    protected boolean shouldTransform(File inputFile) {
        String name = inputFile.getName();
        if (inputFile.isDirectory()) {
            return !name.endsWith(".svn");
        } else {
            return name.endsWith(DMNConstants.DMN_FILE_EXTENSION);
        }
    }

    @Override
    protected void transformFile(File file, File root, Path outputPath) {
        logger.info("Processing DMN ...");

        // Read and validate DMN
        DMNModelRepository repository = readDMN(file);
        dmnTransformer.transform(repository);
        BasicDMN2JavaTransformer dmnTransformer = dialectDefinition.createBasicTransformer(repository, lazyEvaluationDetector, inputParameters);
        DMNModelRepository dmnModelRepository = dmnTransformer.getDMNModelRepository();
        handleValidationErrors(this.dmnValidator.validate(dmnModelRepository));

        // Transform
        transform(dmnTransformer, dmnModelRepository, outputPath);
    }

    protected void transform(BasicDMN2JavaTransformer dmnTransformer, DMNModelRepository dmnModelRepository, Path outputPath) {
        // Generate data types
        List<String> generatedClasses = new ArrayList<>();
        List<TItemDefinition> itemDefinitions = dmnModelRepository.itemDefinitions();
        transformItemDefinitionList(itemDefinitions, dmnTransformer, generatedClasses, outputPath);

        // Generate BKMs
        List<TBusinessKnowledgeModel> businessKnowledgeModels = dmnModelRepository.businessKnowledgeModels();
        transformBKMList(businessKnowledgeModels, dmnTransformer, generatedClasses, outputPath);

        // Generate decisions
        List<TDecision> decisions = dmnModelRepository.decisions();
        transformDecisionList(decisions, dmnTransformer, generatedClasses, outputPath, decisionBaseClass);
    }

    private void transformItemDefinitionList(List<TItemDefinition> itemDefinitionList, BasicDMN2JavaTransformer dmnTransformer, List<String> generatedClasses, Path outputPath) {
        if (itemDefinitionList != null) {
            for (TItemDefinition itemDefinition : itemDefinitionList) {
                transformItemDefinition(itemDefinition, dmnTransformer, generatedClasses, outputPath);
            }
        }
    }

    private void transformItemDefinition(TItemDefinition itemDefinition, BasicDMN2JavaTransformer dmnTransformer, List<String> generatedClasses, Path outputPath) {
        if (itemDefinition == null) {
            return;
        }

        // Complex type
        if (!dmnTransformer.getDMNModelRepository().isEmpty(itemDefinition.getItemComponent())) {
            String typePackageName = dmnTransformer.javaTypePackageName();

            // Generate interface and class
            String javaInterfaceName = dmnTransformer.itemDefinitionJavaInterfaceName(itemDefinition);
            transformItemDefinition(itemDefinition, dmnTransformer, templateProvider.baseTemplatePath(), templateProvider.itemDefinitionInterfaceTemplate(), generatedClasses, outputPath, typePackageName, javaInterfaceName);
            transformItemDefinition(itemDefinition, dmnTransformer, templateProvider.baseTemplatePath(), templateProvider.itemDefinitionClassTemplate(), generatedClasses, outputPath, typePackageName, dmnTransformer.itemDefinitionJavaClassName(javaInterfaceName));

            // Process children
            transformItemDefinitionList(itemDefinition.getItemComponent(), dmnTransformer, generatedClasses, outputPath);
        }
    }

    private void transformItemDefinition(TItemDefinition itemDefinition, BasicDMN2JavaTransformer dmnTransformer, String baseTemplatePath, String itemDefinitionTemplate, List<String> generatedClasses, Path outputPath, String typePackageName, String typeName) {
        String qualifiedName = dmnTransformer.qualifiedName(typePackageName, typeName);
        if (generatedClasses.contains(qualifiedName)) {
            logger.warn(String.format("Class '%s' has already been generated", typeName));
        } else {
            processTemplate(itemDefinition, baseTemplatePath, itemDefinitionTemplate, dmnTransformer, outputPath, typePackageName, typeName);
            generatedClasses.add(qualifiedName);
        }
    }

    private void transformBKMList(List<TBusinessKnowledgeModel> bkmList, BasicDMN2JavaTransformer dmnTransformer, List<String> generatedClasses, Path outputPath) {
        for (TBusinessKnowledgeModel bkm : bkmList) {
            transformBKM(bkm, dmnTransformer, generatedClasses, outputPath, decisionBaseClass);
        }
    }

    private void transformBKM(TBusinessKnowledgeModel bkm, BasicDMN2JavaTransformer dmnTransformer, List<String> generatedClasses, Path outputPath, String decisionBaseClass) {
        String bkmPackageName = dmnTransformer.javaRootPackageName();
        String bkmClassName = dmnTransformer.drgElementClassName(bkm);
        checkDuplicate(generatedClasses, bkmPackageName, bkmClassName, dmnTransformer);
        processTemplate(bkm, templateProvider.baseTemplatePath(), templateProvider.bkmTemplateName(), dmnTransformer, outputPath, bkmPackageName, bkmClassName, decisionBaseClass);

        if (dmnTransformer.getDMNModelRepository().isDecisionTableExpression(bkm)) {
            String decisionRuleOutputClassName = dmnTransformer.ruleOutputClassName(bkm);
            checkDuplicate(generatedClasses, bkmPackageName, decisionRuleOutputClassName, dmnTransformer);
            processTemplate(bkm, templateProvider.baseTemplatePath(), templateProvider.decisionTableRuleOutputTemplate(), dmnTransformer, outputPath, bkmPackageName, decisionRuleOutputClassName, decisionBaseClass);
        }
    }

    private void transformDecisionList(List<TDecision> decisions, BasicDMN2JavaTransformer dmnTransformer, List<String> generatedClasses, Path outputPath, String decisionBaseClass) {
        for (TDecision decision : decisions) {
            transformDecision(decision, dmnTransformer, generatedClasses, outputPath, decisionBaseClass);
        }
    }

    private void transformDecision(TDecision decision, BasicDMN2JavaTransformer dmnTransformer, List<String> generatedClasses, Path outputPath, String decisionBaseClass) {
        String decisionPackageName = dmnTransformer.javaRootPackageName();
        String decisionClassName = dmnTransformer.drgElementClassName(decision);
        checkDuplicate(generatedClasses, decisionPackageName, decisionClassName, dmnTransformer);
        processTemplate(decision, templateProvider.baseTemplatePath(), templateProvider.decisionTemplateName(), dmnTransformer, outputPath, decisionPackageName, decisionClassName, decisionBaseClass);

        if (dmnTransformer.getDMNModelRepository().isDecisionTableExpression(decision)) {
            String decisionRuleOutputClassName = dmnTransformer.ruleOutputClassName(decision);
            checkDuplicate(generatedClasses, decisionPackageName, decisionRuleOutputClassName, dmnTransformer);
            processTemplate(decision, templateProvider.baseTemplatePath(), templateProvider.decisionTableRuleOutputTemplate(), dmnTransformer, outputPath, decisionPackageName, decisionRuleOutputClassName, decisionBaseClass);
        }
    }

    private void checkDuplicate(List<String> generatedClasses, String pkg, String className, BasicDMN2JavaTransformer dmnTransformer) {
        String qualifiedName = dmnTransformer.qualifiedName(pkg, className);
        if (generatedClasses.contains(qualifiedName)) {
            throw new DMNRuntimeException(String.format("Class '%s' has already been generated", qualifiedName));
        } else {
            generatedClasses.add(qualifiedName);
        }
    }

    private void processTemplate(TItemDefinition itemDefinition, String baseTemplatePath, String templateName, BasicDMN2JavaTransformer dmnTransformer, Path outputPath, String javaPackageName, String javaClassName) {
        try {
            // Make parameters
            Map<String, Object> params = makeTemplateParams(itemDefinition, javaPackageName, javaClassName, dmnTransformer);

            // Make output file
            String relativeFilePath = javaPackageName.replace('.', '/');
            String fileExtension = ".java";
            File outputFile = makeOutputFile(outputPath, relativeFilePath, javaClassName, fileExtension);

            // Process template
            processTemplate(baseTemplatePath, templateName, params, outputFile, false);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot process template '%s' for itemDefinition '%s'", templateName, itemDefinition.getName()), e);
        }
    }

    private void processTemplate(TBusinessKnowledgeModel bkm, String baseTemplatePath, String templateName, BasicDMN2JavaTransformer dmnTransformer, Path outputPath, String javaPackageName, String javaClassName, String decisionBaseClass) {
        try {
            // Make parameters
            Map<String, Object> params = makeTemplateParams(bkm, javaPackageName, javaClassName, decisionBaseClass, dmnTransformer);

            // Make output file
            String relativeFilePath = javaPackageName.replace('.', '/');
            String fileExtension = ".java";
            File outputFile = makeOutputFile(outputPath, relativeFilePath, javaClassName, fileExtension);

            // Process template
            processTemplate(baseTemplatePath, templateName, params, outputFile, true);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot process template '%s' for BKMs", templateName), e);
        }
    }

    private void processTemplate(TDecision decision, String baseTemplatePath, String templateName, BasicDMN2JavaTransformer dmnTransformer, Path outputPath, String javaPackageName, String javaClassName, String decisionBaseClass) {
        try {
            // Make parameters
            Map<String, Object> params = makeTemplateParams(decision, javaPackageName, javaClassName, decisionBaseClass, dmnTransformer);

            // Make output file
            String relativeFilePath = javaPackageName.replace('.', '/');
            String fileExtension = ".java";
            File outputFile = makeOutputFile(outputPath, relativeFilePath, javaClassName, fileExtension);

            // Process template
            processTemplate(baseTemplatePath, templateName, params, outputFile, true);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot process template '%s' for decision '%s'", templateName, decision.getName()), e);
        }
    }

    //
    // FreeMarker model methods
    //
    private Map<String, Object> makeTemplateParams(TItemDefinition itemDefinition, String javaPackageName, String javaClassName, BasicDMN2JavaTransformer dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("itemDefinition", itemDefinition);
        addCommonParams(params, javaPackageName, javaClassName, dmnTransformer);
        return params;
    }

    private Map<String, Object> makeTemplateParams(TBusinessKnowledgeModel bkm, String javaPackageName, String javaClassName, String decisionBaseClass, BasicDMN2JavaTransformer dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("drgElement", bkm);
        params.put("decisionBaseClass", decisionBaseClass);
        addCommonParams(params, javaPackageName, javaClassName, dmnTransformer);
        return params;
    }

    private Map<String, Object> makeTemplateParams(TDecision decision, String javaPackageName, String javaClassName, String decisionBaseClass, BasicDMN2JavaTransformer dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("drgElement", decision);
        params.put("decisionBaseClass", decisionBaseClass);
        addCommonParams(params, javaPackageName, javaClassName, dmnTransformer);
        return params;
    }

    private void addCommonParams(Map<String, Object> params, String javaPackageName, String javaClassName, BasicDMN2JavaTransformer dmnTransformer) {
        params.put("javaPackageName", javaPackageName);
        params.put("javaClassName", javaClassName);
        params.put("transformer", dmnTransformer);
        params.put("modelRepository", dmnTransformer.getDMNModelRepository());
    }
}
