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
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

public class DMNToPythonTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractDMNToNativeTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> {
    public DMNToPythonTransformer(DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> dialectDefinition, DMNValidator dmnValidator, DMNTransformer<TestCases> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, InputParameters inputParameters, BuildLogger logger) {
        super(dialectDefinition, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
    }

    @Override
    protected void generateExtra(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, DMNModelRepository dmnModelRepository, Path outputPath) {
        if (dmnTransformer.isGenerateExtra()) {
            generateInitFiles(dmnTransformer, dmnModelRepository, outputPath, inputParameters.getCharset(), true);
        }
    }

    public static void generateInitFiles(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, DMNModelRepository dmnModelRepository, Path outputPath, Charset charset, boolean srcFolder) {
        for (TDefinitions definitions : dmnModelRepository.getAllDefinitions()) {
            String packageName = dmnTransformer.nativeModelPackageName(definitions.getName());
            if (srcFolder) {
                generateInitFile(packageName.split("\\."), outputPath, charset);
                String typePackageName = dmnTransformer.nativeTypePackageName(definitions.getName());
                generateInitFile(typePackageName.split("\\."), outputPath, charset);
            } else {
                createInitFile(outputPath.toFile(), charset);
                generateInitFile(packageName.split("\\."), outputPath, charset);
            }
        }
    }

    private static void generateInitFile(String[] packageParts, Path outputPath, Charset charset) {
        if (packageParts ==  null) {
            return;
        }

        File currentDir = outputPath.toFile();
        for (String part : packageParts) {
            if (!StringUtils.isBlank(part)) {
                currentDir = new File(currentDir, part);
                createInitFile(currentDir, charset);
            }
        }
    }

    private static void createInitFile(File currentDir, Charset charset) {
        try {
            // Do not generate if folder is empty
            if (currentDir.exists()) {
                File[] files = currentDir.listFiles();
                if (files != null && files.length > 0) {
                    File initFile = new File(currentDir, "__init__.py");
                    FileUtils.write(initFile, "", charset);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String getFileExtension() {
        return ".py";
    }
}
