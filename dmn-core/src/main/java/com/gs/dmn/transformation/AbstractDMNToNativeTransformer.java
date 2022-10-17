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
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.proto.MessageType;
import com.gs.dmn.transformation.proto.Service;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import org.apache.commons.lang3.time.StopWatch;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

import static com.gs.dmn.serialization.DMNSerializer.isDMNFile;

public abstract class AbstractDMNToNativeTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> extends AbstractDMNTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> implements DMNToNativeTransformer {
    private static final String DMN_PROTO_FILE_NAME = "jdmn";

    public static final String DATA_PACKAGE = "type";
    public static final String DECISION_RULE_OUTPUT_CLASS_SUFFIX = "RuleOutput";
    public static final String PRIORITY_SUFFIX = "Priority";

    public static final String LIST_TYPE = "List";

    private static final String REGISTRY_CLASS_NAME = "ModelElementRegistry";

    public static final List<String> SUPPORTED_LANGUAGES = Arrays.asList(DMNVersion.LATEST.getFeelPrefix(), DMNModelRepository.FREE_TEXT_LANGUAGE);

    protected AbstractDMNToNativeTransformer(DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> dialectDefinition, DMNValidator dmnValidator, DMNTransformer<TEST> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, InputParameters inputParameters, BuildLogger logger) {
        super(dialectDefinition, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
    }

    @Override
    protected boolean shouldTransformFile(File inputFile) {
        if (inputFile == null) {
            return false;
        } else if (inputFile.isDirectory()) {
            return !inputFile.getName().endsWith(".svn");
        } else {
            return isDMNFile(inputFile);
        }
    }

    @Override
    protected void transformFile(File file, File root, Path outputPath) {
        this.logger.info(String.format("Processing DMN file '%s'", file.getPath()));
        StopWatch watch = new StopWatch();
        watch.start();

        // Read and validate DMN
        DMNModelRepository repository = readModels(file);
        handleValidationErrors(this.dmnValidator.validate(repository));
        this.dmnTransformer.transform(repository);
        BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer = this.dialectDefinition.createBasicTransformer(repository, this.lazyEvaluationDetector, this.inputParameters);

        // Transform
        transform(dmnTransformer, repository, outputPath);

        watch.stop();
        this.logger.info("DMN processing time: " + watch);
    }

    protected void transform(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, DMNModelRepository dmnModelRepository, Path outputPath) {
        for(TDefinitions definitions: dmnModelRepository.getAllDefinitions()) {
            // Generate data types
            List<String> generatedClasses = new ArrayList<>();
            List<TItemDefinition> itemDefinitions = dmnModelRepository.findItemDefinitions(definitions);
            transformItemDefinitionList(definitions, itemDefinitions, dmnTransformer, generatedClasses, outputPath);

            // Generate BKMs
            List<TBusinessKnowledgeModel> businessKnowledgeModels = dmnModelRepository.findBKMs(definitions);
            transformBKMList(definitions, businessKnowledgeModels, dmnTransformer, generatedClasses, outputPath);

            // Generate DSs
            List<TDecisionService> decisionServices = dmnModelRepository.findDSs(definitions);
            transformDSList(definitions, decisionServices, dmnTransformer, generatedClasses, outputPath);

            // Generate decisions
            List<TDecision> decisions = dmnModelRepository.findDecisions(definitions);
            transformDecisionList(definitions, decisions, dmnTransformer, generatedClasses, outputPath, this.decisionBaseClass);

            // Generate .proto file
            generateProtoFile(definitions, dmnTransformer, outputPath);
        }

        // Generate registry
        generateRegistry(dmnModelRepository.getAllDefinitions(), dmnTransformer, outputPath);

        // Generate extra
        generateExtra(dmnTransformer, dmnModelRepository, outputPath);
    }

    private void transformItemDefinitionList(TDefinitions definitions, List<TItemDefinition> itemDefinitionList, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, List<String> generatedClasses, Path outputPath) {
        if (itemDefinitionList != null) {
            for (TItemDefinition itemDefinition : itemDefinitionList) {
                transformItemDefinition(definitions, itemDefinition, dmnTransformer, generatedClasses, outputPath);
            }
        }
    }

    private void transformItemDefinition(TDefinitions definitions, TItemDefinition itemDefinition, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, List<String> generatedClasses, Path outputPath) {
        if (itemDefinition == null) {
            return;
        }

        // Complex type
        if (dmnTransformer.getDMNModelRepository().hasComponents(itemDefinition)) {
            this.logger.debug(String.format("Generating code for ItemDefinition '%s'", itemDefinition.getName()));

            String typePackageName = dmnTransformer.nativeTypePackageName(definitions.getName());

            // Generate interface and class
            String javaInterfaceName = dmnTransformer.itemDefinitionNativeSimpleInterfaceName(itemDefinition);
            transformItemDefinition(definitions, itemDefinition, dmnTransformer, this.templateProvider.baseTemplatePath(), this.templateProvider.itemDefinitionInterfaceTemplate(), generatedClasses, outputPath, typePackageName, javaInterfaceName);
            transformItemDefinition(definitions, itemDefinition, dmnTransformer, this.templateProvider.baseTemplatePath(), this.templateProvider.itemDefinitionClassTemplate(), generatedClasses, outputPath, typePackageName, dmnTransformer.itemDefinitionNativeClassName(javaInterfaceName));

            // Process children
            transformItemDefinitionList(definitions, itemDefinition.getItemComponent(), dmnTransformer, generatedClasses, outputPath);
        }
    }

    private void transformItemDefinition(TDefinitions definitions, TItemDefinition itemDefinition, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, String baseTemplatePath, String itemDefinitionTemplate, List<String> generatedClasses, Path outputPath, String typePackageName, String typeName) {
        String qualifiedName = dmnTransformer.qualifiedName(typePackageName, typeName);
        if (generatedClasses.contains(qualifiedName)) {
            this.logger.warn(String.format("Class '%s' has already been generated", typeName));
        } else {
            processTemplate(definitions, itemDefinition, baseTemplatePath, itemDefinitionTemplate, dmnTransformer, outputPath, typePackageName, typeName);
            generatedClasses.add(qualifiedName);
        }
    }

    private void transformBKMList(TDefinitions definitions, List<TBusinessKnowledgeModel> bkmList, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, List<String> generatedClasses, Path outputPath) {
        for (TBusinessKnowledgeModel bkm : bkmList) {
            transformBKM(definitions, bkm, dmnTransformer, generatedClasses, outputPath, this.decisionBaseClass);
        }
    }

    private void transformBKM(TDefinitions definitions, TBusinessKnowledgeModel bkm, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, List<String> generatedClasses, Path outputPath, String decisionBaseClass) {
        this.logger.debug(String.format("Generating code for BKM '%s'", bkm.getName()));

        String bkmPackageName = dmnTransformer.nativeModelPackageName(definitions.getName());
        String bkmClassName = dmnTransformer.drgElementClassName(bkm);
        checkDuplicate(generatedClasses, bkmPackageName, bkmClassName, dmnTransformer);
        processTemplate(definitions, bkm, this.templateProvider.baseTemplatePath(), this.templateProvider.bkmTemplateName(), dmnTransformer, outputPath, bkmPackageName, bkmClassName, decisionBaseClass);

        if (dmnTransformer.getDMNModelRepository().isDecisionTableExpression(bkm)) {
            String decisionRuleOutputClassName = dmnTransformer.ruleOutputClassName(bkm);
            checkDuplicate(generatedClasses, bkmPackageName, decisionRuleOutputClassName, dmnTransformer);
            processTemplate(definitions, bkm, this.templateProvider.baseTemplatePath(), this.templateProvider.decisionTableRuleOutputTemplate(), dmnTransformer, outputPath, bkmPackageName, decisionRuleOutputClassName, decisionBaseClass);
        }
    }

    private void transformDSList(TDefinitions definitions, List<TDecisionService> dsList, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, List<String> generatedClasses, Path outputPath) {
        for (TDecisionService ds : dsList) {
            transformDS(definitions, ds, dmnTransformer, generatedClasses, outputPath, this.decisionBaseClass);
        }
    }

    private void transformDS(TDefinitions definitions, TDecisionService ds, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, List<String> generatedClasses, Path outputPath, String decisionBaseClass) {
        this.logger.debug(String.format("Generating code for DS '%s'", ds.getName()));

        String dsPackageName = dmnTransformer.nativeModelPackageName(definitions.getName());
        String dsClassName = dmnTransformer.drgElementClassName(ds);
        checkDuplicate(generatedClasses, dsPackageName, dsClassName, dmnTransformer);
        processTemplate(definitions, ds, this.templateProvider.baseTemplatePath(), this.templateProvider.dsTemplateName(), dmnTransformer, outputPath, dsPackageName, dsClassName, decisionBaseClass);
    }

    private void transformDecisionList(TDefinitions definitions, List<TDecision> decisions, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, List<String> generatedClasses, Path outputPath, String decisionBaseClass) {
        for (TDecision decision : decisions) {
            transformDecision(definitions, decision, dmnTransformer, generatedClasses, outputPath, decisionBaseClass);
        }
    }

    private void transformDecision(TDefinitions definitions, TDecision decision, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, List<String> generatedClasses, Path outputPath, String decisionBaseClass) {
        this.logger.debug(String.format("Generating code for Decision '%s'", decision.getName()));

        String decisionPackageName = dmnTransformer.nativeModelPackageName(definitions.getName());
        String decisionClassName = dmnTransformer.drgElementClassName(decision);
        checkDuplicate(generatedClasses, decisionPackageName, decisionClassName, dmnTransformer);
        processTemplate(definitions, decision, this.templateProvider.baseTemplatePath(), this.templateProvider.decisionTemplateName(), dmnTransformer, outputPath, decisionPackageName, decisionClassName, decisionBaseClass);

        if (dmnTransformer.getDMNModelRepository().isDecisionTableExpression(decision)) {
            String decisionRuleOutputClassName = dmnTransformer.ruleOutputClassName(decision);
            checkDuplicate(generatedClasses, decisionPackageName, decisionRuleOutputClassName, dmnTransformer);
            processTemplate(definitions, decision, this.templateProvider.baseTemplatePath(), this.templateProvider.decisionTableRuleOutputTemplate(), dmnTransformer, outputPath, decisionPackageName, decisionRuleOutputClassName, decisionBaseClass);
        }
    }

    private void generateProtoFile(TDefinitions definitions, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, Path outputPath) {
        Pair<Pair<List<MessageType>, List<MessageType>>, List<Service>> pair = dmnTransformer.dmnToProto(definitions);
        if (pair == null) {
            return;
        }

        try {
            // Make output file
            String protoFileName = DMN_PROTO_FILE_NAME;
            String modelPackageName = dmnTransformer.nativeModelPackageName(definitions.getName());
            String relativeFilePath = modelPackageName.replace('.', '/');
            String fileExtension = ".proto";
            File outputFile = makeOutputFile(outputPath, relativeFilePath, protoFileName, fileExtension);

            // Make parameters
            Map<String, Object> params = makeProtoTemplateParams(pair.getLeft(), pair.getRight(), modelPackageName, dmnTransformer);
            processTemplate(this.templateProvider.baseTemplatePath(), "common/proto.ftl", params, outputFile, false);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Error when generating .proto file for model '%s'", definitions.getName()), e);
        }
    }

    private void generateRegistry(List<TDefinitions> definitionsList, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, Path outputPath) {
        try {
            // Make output file
            String registryClassName = REGISTRY_CLASS_NAME;
            String modelPackageName = dmnTransformer.nativeRootPackageName();
            String relativeFilePath = modelPackageName.replace('.', '/');
            String fileExtension = getFileExtension();
            File outputFile = makeOutputFile(outputPath, relativeFilePath, registryClassName, fileExtension);

            // Make parameters
            Map<String, Object> params = makeModelRegistryTemplateParams(definitionsList, modelPackageName, registryClassName, dmnTransformer);
            processTemplate(this.templateProvider.baseTemplatePath(), "common/registry.ftl", params, outputFile, false);
        } catch (Exception e) {
            throw new DMNRuntimeException("Error when generating registry file for model(s)", e);
        }

    }

    protected void generateExtra(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, DMNModelRepository dmnModelRepository, Path outputPath) {
    }

    private void checkDuplicate(List<String> generatedClasses, String pkg, String className, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        String qualifiedName = dmnTransformer.qualifiedName(pkg, className);
        if (generatedClasses.contains(qualifiedName)) {
            throw new DMNRuntimeException(String.format("Class '%s' has already been generated", qualifiedName));
        } else {
            generatedClasses.add(qualifiedName);
        }
    }

    private void processTemplate(TDefinitions definitions, TItemDefinition itemDefinition, String baseTemplatePath, String templateName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, Path outputPath, String javaPackageName, String javaClassName) {
        try {
            // Make parameters
            Map<String, Object> params = makeTemplateParams(definitions, itemDefinition, javaPackageName, javaClassName, dmnTransformer);

            // Make output file
            String relativeFilePath = javaPackageName.replace('.', '/');
            String fileExtension = getFileExtension();
            File outputFile = makeOutputFile(outputPath, relativeFilePath, javaClassName, fileExtension);

            // Process template
            processTemplate(baseTemplatePath, templateName, params, outputFile, false);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot process template '%s' for itemDefinition '%s'", templateName, itemDefinition.getName()), e);
        }
    }

    private void processTemplate(TDefinitions definitions, TInvocable in, String baseTemplatePath, String templateName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, Path outputPath, String javaPackageName, String javaClassName, String decisionBaseClass) {
        try {
            // Make parameters
            Map<String, Object> params = makeTemplateParams(definitions, in, javaPackageName, javaClassName, decisionBaseClass, dmnTransformer);

            // Make output file
            String relativeFilePath = javaPackageName.replace('.', '/');
            String fileExtension = getFileExtension();
            File outputFile = makeOutputFile(outputPath, relativeFilePath, javaClassName, fileExtension);

            // Process template
            processTemplate(baseTemplatePath, templateName, params, outputFile, true);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot process template '%s' for BKM '%s'", templateName, in.getName()), e);
        }
    }

    private void processTemplate(TDefinitions definitions, TDecision decision, String baseTemplatePath, String templateName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, Path outputPath, String javaPackageName, String javaClassName, String decisionBaseClass) {
        try {
            // Make parameters
            Map<String, Object> params = makeTemplateParams(definitions, decision, javaPackageName, javaClassName, decisionBaseClass, dmnTransformer);

            // Make output file
            String relativeFilePath = javaPackageName.replace('.', '/');
            String fileExtension = getFileExtension();
            File outputFile = makeOutputFile(outputPath, relativeFilePath, javaClassName, fileExtension);

            // Process template
            processTemplate(baseTemplatePath, templateName, params, outputFile, true);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot process template '%s' for decision '%s'", templateName, decision.getName()), e);
        }
    }

    protected abstract String getFileExtension();

    //
    // FreeMarker model methods
    //
    private Map<String, Object> makeTemplateParams(TDefinitions definitions, TItemDefinition itemDefinition, String javaPackageName, String javaClassName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("modelName", definitions.getName());
        params.put("itemDefinition", itemDefinition);

        String qualifiedName = dmnTransformer.qualifiedName(javaPackageName, dmnTransformer.itemDefinitionNativeSimpleInterfaceName(itemDefinition));
        String serializationClass = this.typeDeserializationConfigurer.deserializeTypeAs(qualifiedName);
        params.put("serializationClass", serializationClass);

        addCommonParams(params, javaPackageName, javaClassName, dmnTransformer);
        return params;
    }

    private Map<String, Object> makeTemplateParams(TDefinitions definitions, TInvocable invocable, String javaPackageName, String javaClassName, String decisionBaseClass, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("modelName", definitions.getName());
        params.put("drgElement", invocable);
        params.put("decisionBaseClass", decisionBaseClass);
        addCommonParams(params, javaPackageName, javaClassName, dmnTransformer);
        return params;
    }

    private Map<String, Object> makeTemplateParams(TDefinitions definitions, TDecision decision, String javaPackageName, String javaClassName, String decisionBaseClass, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("modelName", definitions.getName());
        params.put("drgElement", decision);
        params.put("decisionBaseClass", decisionBaseClass);
        addCommonParams(params, javaPackageName, javaClassName, dmnTransformer);
        return params;
    }

    private Map<String, Object> makeProtoTemplateParams(Pair<List<MessageType>, List<MessageType>> messageTypes, List<Service> services, String javaPackageName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("protoPackageName", dmnTransformer.protoPackage(javaPackageName));
        params.put("dataTypes", messageTypes.getLeft());
        params.put("responseRequestTypes", messageTypes.getRight());
        params.put("services", services);
        params.put("transformer", dmnTransformer);
        return params;
    }

    private Map<String, Object> makeModelRegistryTemplateParams(List<TDefinitions> definitionsList, String javaPackageName, String javaClassName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("definitionsList", definitionsList);
        addCommonParams(params, javaPackageName, javaClassName, dmnTransformer);
        return params;
    }


    private void addCommonParams(Map<String, Object> params, String javaPackageName, String javaClassName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        params.put("javaPackageName", javaPackageName);
        params.put("javaClassName", javaClassName);
        params.put("transformer", dmnTransformer);
        params.put("modelRepository", dmnTransformer.getDMNModelRepository());
    }
}
