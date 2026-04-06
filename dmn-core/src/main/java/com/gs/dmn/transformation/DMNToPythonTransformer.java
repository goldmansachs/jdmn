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
import com.gs.dmn.transformation.repository.OutputRepository;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class DMNToPythonTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractDMNToNativeTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> {
    public DMNToPythonTransformer(DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> dialectDefinition, DMNValidator dmnValidator, DMNTransformer<TestCases> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, InputParameters inputParameters, BuildLogger logger) {
        super(dialectDefinition, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
    }

    @Override
    protected void generateExtra(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, DMNModelRepository dmnModelRepository, OutputRepository outputRepository) {
        if (dmnTransformer.isGenerateExtra()) {
            generateInitFiles(dmnTransformer, dmnModelRepository, outputRepository, inputParameters.getCharset(), true);
        }
    }

    public static void generateInitFiles(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, DMNModelRepository dmnModelRepository, OutputRepository outputRepository, Charset charset, boolean srcFolder) {
        for (TDefinitions definitions : dmnModelRepository.getAllDefinitions()) {
            String packageName = dmnTransformer.nativeModelPackageName(definitions.getName());
            if (srcFolder) {
                generateInitFile(packageName.split("\\."), outputRepository, charset);
                String typePackageName = dmnTransformer.nativeTypePackageName(definitions.getName());
                generateInitFile(typePackageName.split("\\."), outputRepository, charset);
            } else {
                createInitFile(outputRepository, charset);
                generateInitFile(packageName.split("\\."), outputRepository, charset);
            }
        }
    }

    private static void generateInitFile(String[] packageParts, OutputRepository outputRepository, Charset charset) {
        if (packageParts ==  null) {
            return;
        }

        List<File> folders = outputRepository.makeOutputFolders(packageParts);
        for (File folder : folders) {
            createInitFile(outputRepository, folder, charset);
        }
    }

    private static void createInitFile(OutputRepository outputRepository, Charset charset) {
        createInitFile(outputRepository, outputRepository.getRootFile(), charset);
    }

    private static void createInitFile(OutputRepository outputRepository, File folder, Charset charset) {
        try {
            // Do not generate if folder is empty
            if (folder.exists()) {
                File[] files = folder.listFiles();
                if (files != null && files.length > 0) {
                    File initFile = outputRepository.makeOutputFile(folder, "__init__.py");
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
