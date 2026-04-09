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
import com.gs.dmn.transformation.repository.OutputElement;
import com.gs.dmn.transformation.repository.OutputRepository;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;

import java.nio.charset.Charset;
import java.util.ArrayList;
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

        // Make a list of all the relative paths from packageParts
        List<String> packageNames = new ArrayList<>();
        String currentPackage = "";
        for (String packagePart : packageParts) {
            if (currentPackage.isBlank()) {
                currentPackage = packagePart;
            } else {
                currentPackage = String.join(".", currentPackage, packagePart);
            }
            packageNames.add(currentPackage);
        }
        // Create init files
        for (String packageName : packageNames) {
            createInitFile(outputRepository, packageName, charset);
        }
    }

    private static void createInitFile(OutputRepository outputRepository, String nativePackageName, Charset charset) {
        if (outputRepository.notEmptyPackage(nativePackageName)) {
            OutputElement outElement = outputRepository.makeOutputElement(nativePackageName, "__init__", ".py");
            outElement.writeText("", charset);
        }
    }

    private static void createInitFile(OutputRepository outputRepository, Charset charset) {
        createInitFile(outputRepository, "", charset);
    }

    @Override
    protected String getFileExtension() {
        return ".py";
    }
}
