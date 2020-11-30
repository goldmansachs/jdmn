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
package com.gs.dmn.signavio.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.metadata.DMNMetadata;
import com.gs.dmn.serialization.JsonSerializer;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.AbstractDMNToNativeTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20191111.model.TDefinitions;

import java.io.File;
import java.nio.file.Path;

import static com.gs.dmn.serialization.DMNReader.isDMNFile;

public class SignavioDMNToJavaTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractDMNToNativeTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestLab> {
    private static final String DMN_METADATA_FILE_NAME = "DMNMetadata";
    private String schemaNamespace;

    public SignavioDMNToJavaTransformer(DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestLab> dialectDefinition, DMNValidator dmnValidator, DMNTransformer<TestLab> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, InputParameters inputParameters, BuildLogger logger) {
        super(dialectDefinition, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
        this.schemaNamespace = inputParameters.getSchemaNamespace();
        if (StringUtils.isEmpty(this.schemaNamespace)) {
            this.schemaNamespace = "http://www.signavio.com/schema/dmn/1.1/";
        }
    }

    @Override
    protected String getFileExtension() {
        return ".java";
    }

    @Override
    protected DMNModelRepository readModels(File file) {
        if (isDMNFile(file)) {
            Pair<TDefinitions, PrefixNamespaceMappings> result = dmnReader.read(file);
            DMNModelRepository repository = new SignavioDMNModelRepository(result, this.schemaNamespace);
            return repository;
        } else {
            throw new DMNRuntimeException(String.format("Invalid DMN file %s", file.getAbsoluteFile()));
        }
    }

    @Override
    protected void transform(BasicDMNToNativeTransformer dmnTransformer, DMNModelRepository dmnModelRepository, Path outputPath) {
        super.transform(dmnTransformer, dmnModelRepository, outputPath);

        // Generate metadata
        processManifest(dmnTransformer, DMN_METADATA_FILE_NAME, outputPath);
    }

    private void processManifest(BasicDMNToNativeTransformer dmnTransformer, String jsonFileName, Path outputPath) {
        String javaPackageName = dmnTransformer.nativeRootPackageName();
        String filePath = javaPackageName.replace('.', '/');
        String fileExtension = ".json";

        try {
            DMNToManifestTransformer dmnToManifestTransformer = new DMNToManifestTransformer(dmnTransformer);
            String dmnNamespace = dmnTransformer.getDMNModelRepository().getRootDefinitions().getNamespace();
            String nativeNamespace = dmnTransformer.nativeRootPackageName();
            String dmnVersion= this.inputParameters.getDmnVersion();
            String modelVersion = this.inputParameters.getModelVersion();
            String platformVersion = this.inputParameters.getPlatformVersion();
            DMNMetadata manifest = dmnToManifestTransformer.toManifest(dmnNamespace, nativeNamespace, dmnVersion, modelVersion, platformVersion);
            File resultFile = makeOutputFile(outputPath, filePath, jsonFileName, fileExtension);
            JsonSerializer.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(resultFile, manifest);
        } catch (Exception e) {
            throw new DMNRuntimeException("Cannot process manifest file", e);
        }
    }
}
