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
package com.gs.dmn.maven;

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.maven.configuration.components.DMNTransformerComponent;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.tck.TCKTestCasesToKotlinJUnitTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.FileTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.omg.dmn.tck.marshaller._20160719.TestCases;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings("CanBeFinal")
@Mojo(name = "tck-to-kotlin", defaultPhase = LifecyclePhase.GENERATE_TEST_SOURCES, configurator = "dmn-mojo-configurator")
public class TCKToKotlinJUnitMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractDMNMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> {
    @Parameter(required = true, defaultValue = "com.gs.dmn.dialect.KotlinStandardDMNDialectDefinition")
    public String dmnDialect;

    @Parameter(required = false)
    public String[] dmnValidators;

    @Parameter(required = false)
    public DMNTransformerComponent[] dmnTransformers;

    @Parameter(required = true, defaultValue = "com.gs.dmn.transformation.template.KotlinTreeTemplateProvider")
    public String templateProvider;

    @Parameter(required = false)
    public String[] lazyEvaluationDetectors;

    @Parameter(required = false, defaultValue = "com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer")
    public String typeDeserializationConfigurer;

    @Parameter(required = false)
    public Map<String, String> inputParameters;

    @Parameter(required = true, defaultValue = "${project.basedir}/src/main/resources/tck")
    public File inputTestFileDirectory;

    @Parameter(required = true, defaultValue = "${project.basedir}/src/main/resources/tck")
    public File inputModelFileDirectory;

    @Parameter(required = true, defaultValue = "${project.build.directory}/generated-test-sources/junit")
    public File outputFileDirectory;

    @Override
    public void execute() throws MojoExecutionException {
        transform(this.inputTestFileDirectory, this.outputFileDirectory);
    }

    @Override
    protected void checkMandatoryFields() {
        checkMandatoryField(this.inputTestFileDirectory, "inputTestFileDirectory");
        checkMandatoryField(this.inputModelFileDirectory, "inputModelFileDirectory");
        checkMandatoryField(this.outputFileDirectory, "outputFileDirectory");
        checkMandatoryField(this.dmnDialect, "dmnDialect");
    }

    @Override
    protected FileTransformer makeTransformer(com.gs.dmn.log.BuildLogger logger) throws Exception {
        Class<?> dialectClass = Class.forName(dmnDialect);
        DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> dmnDialect = makeDialect(dialectClass);
        DMNValidator dmnValidator = makeDMNValidator(this.dmnValidators, logger);
        DMNTransformer<TestCases> dmnTransformer = makeDMNTransformer(this.dmnTransformers, logger);
        TemplateProvider templateProvider = makeTemplateProvider(this.templateProvider, logger);
        LazyEvaluationDetector lazyEvaluationDetector = makeLazyEvaluationDetector(this.lazyEvaluationDetectors, logger, this.inputParameters);
        TypeDeserializationConfigurer typeDeserializationConfigurer = makeTypeDeserializationConfigurer(this.typeDeserializationConfigurer, logger);

        // Create transformer
        FileTransformer transformer = new TCKTestCasesToKotlinJUnitTransformer<>(
                dmnDialect,
                dmnValidator,
                dmnTransformer,
                templateProvider,
                lazyEvaluationDetector,
                typeDeserializationConfigurer,
                this.inputModelFileDirectory.toPath(),
                this.inputParameters,
                logger
        );
        return transformer;
    }

    @Override
    protected void addSourceRoot(File outputFileDirectory) throws IOException {
        this.project.addTestCompileSourceRoot(outputFileDirectory.getCanonicalPath());
    }
}
