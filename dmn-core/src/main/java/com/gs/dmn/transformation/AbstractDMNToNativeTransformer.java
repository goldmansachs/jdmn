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
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.error.LogErrorHandler;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.gs.dmn.serialization.DMNConstants.isDMNFile;

public abstract class AbstractDMNToNativeTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> extends AbstractDMNTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> implements DMNToNativeTransformer {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDMNToNativeTransformer.class.getName());
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
        return isDMNFile(inputFile, inputParameters.getDmnFileExtension());
    }

    @Override
    protected void transformFiles(List<File> files, File rootFile, Path outputPath) {
        this.logger.info(String.format("Processing DMN files in '%s'", rootFile.getPath()));
        StopWatch watch = new StopWatch();
        watch.start();

        // Read and validate DMN
        DMNModelRepository repository = readModels(files);
        handleValidationErrors(this.dmnValidator.validate(repository));
        this.dmnTransformer.transform(repository);
        BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer = this.dialectDefinition.createBasicTransformer(repository, this.lazyEvaluationDetector, this.inputParameters);

        // Transform
        transform(dmnTransformer, repository, outputPath);

        watch.stop();
        this.logger.info("DMN processing time: " + watch);
    }

    protected void transform(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, DMNModelRepository dmnModelRepository, Path outputPath) {
        DMNToNativeVisitor visitor = new DMNToNativeVisitor(this.logger, new LogErrorHandler(LOGGER), dmnTransformer, dmnModelRepository, this.templateProcessor, outputPath, new ArrayList<>(), this.decisionBaseClass);
        for(TDefinitions definitions: dmnModelRepository.getAllDefinitions()) {
            definitions.accept(visitor, new NativeVisitorContext(definitions));

            // Generate .proto file
            generateProtoFile(definitions, dmnTransformer, outputPath);
        }

        // Generate registry
        generateRegistry(dmnModelRepository.getAllDefinitions(), dmnTransformer, outputPath);

        // Generate extra
        generateExtra(dmnTransformer, dmnModelRepository, outputPath);
    }

    private void generateProtoFile(TDefinitions definitions, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, Path outputPath) {
        Pair<Pair<List<MessageType>, List<MessageType>>, List<Service>> pair = dmnTransformer.dmnToProto(definitions);
        if (pair == null) {
            return;
        }

        try {
            // Make output file
            String modelPackageName = dmnTransformer.nativeModelPackageName(definitions.getName());
            String relativeFilePath = modelPackageName.replace('.', '/');
            String fileExtension = ".proto";
            File outputFile = this.templateProcessor.makeOutputFile(outputPath, relativeFilePath, DMN_PROTO_FILE_NAME, fileExtension);

            // Make parameters
            Map<String, Object> params = this.templateProcessor.makeProtoTemplateParams(pair.getLeft(), pair.getRight(), modelPackageName, dmnTransformer);
            this.templateProcessor.processTemplate(this.templateProcessor.getTemplateProvider().baseTemplatePath(), "common/proto.ftl", params, outputFile, false);
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
            File outputFile = this.templateProcessor.makeOutputFile(outputPath, relativeFilePath, registryClassName, fileExtension);

            // Make parameters
            Map<String, Object> params = this.templateProcessor.makeModelRegistryTemplateParams(definitionsList, modelPackageName, registryClassName, dmnTransformer);
            this.templateProcessor.processTemplate(this.templateProcessor.getTemplateProvider().baseTemplatePath(), "common/registry.ftl", params, outputFile, false);
        } catch (Exception e) {
            throw new DMNRuntimeException("Error when generating registry file for model(s)", e);
        }

    }

    protected void generateExtra(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, DMNModelRepository dmnModelRepository, Path outputPath) {
    }
}
