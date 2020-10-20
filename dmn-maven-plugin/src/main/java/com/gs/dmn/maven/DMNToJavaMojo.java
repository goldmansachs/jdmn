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
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.maven.configuration.components.DMNTransformerComponent;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.FileTransformer;
import com.gs.dmn.transformation.InputParamUtil;
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
@Mojo(name = "dmn-to-java", defaultPhase = LifecyclePhase.GENERATE_SOURCES, configurator = "dmn-mojo-configurator")
public class DMNToJavaMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractDMNMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> {
    @Parameter(required = true, defaultValue = "com.gs.dmn.dialect.StandardDMNDialectDefinition")
    public String dmnDialect;

    @Parameter(required = false)
    public String[] dmnValidators;

    @Parameter(required = false)
    public DMNTransformerComponent[] dmnTransformers;

    @Parameter(required = true, defaultValue = "com.gs.dmn.transformation.template.TreeTemplateProvider")
    public String templateProvider;

    @Parameter(required = false)
    public String[] lazyEvaluationDetectors;

    @Parameter(required = false, defaultValue = "com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer")
    public String typeDeserializationConfigurer;

    @Parameter(required = false)
    public Map<String, String> inputParameters;

    @Parameter(required = true, defaultValue = "${project.basedir}/src/main/resources/dmn")
    public File inputFileDirectory;

    @Parameter(required = true, defaultValue = "${project.build.directory}/generated-sources/dmn")
    public File outputFileDirectory;

    @Override
    public void execute() throws MojoExecutionException {
        transform(this.inputFileDirectory, this.outputFileDirectory);
    }

    @Override
    protected void checkMandatoryFields() {
        checkMandatoryField(this.project, "project");
        checkMandatoryField(this.inputFileDirectory, "inputFileDirectory");
        checkMandatoryField(this.outputFileDirectory, "outputFileDirectory");
        checkMandatoryField(this.dmnDialect, "dmnDialect");
    }

    @Override
    protected FileTransformer makeTransformer(BuildLogger logger) throws Exception {
        // Create and validate arguments
        Class<?> dialectClass = Class.forName(this.dmnDialect);
        DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> dmnDialect = makeDialect(dialectClass);
        DMNValidator dmnValidator = makeDMNValidator(this.dmnValidators, logger);
        DMNTransformer<TestCases> dmnTransformer = makeDMNTransformer(this.dmnTransformers, logger);
        TemplateProvider templateProvider = makeTemplateProvider(this.templateProvider, logger);
        LazyEvaluationDetector lazyEvaluationDetector = makeLazyEvaluationDetector(this.lazyEvaluationDetectors, logger, this.inputParameters);
        TypeDeserializationConfigurer typeDeserializationConfigurer = makeTypeDeserializationConfigurer(this.typeDeserializationConfigurer, logger);
        validateParameters(dmnDialect, dmnValidator, dmnTransformer, templateProvider, this.inputParameters);

        // Create transformer
        FileTransformer transformer = dmnDialect.createDMNToNativeTransformer(
                dmnValidator,
                dmnTransformer,
                templateProvider,
                lazyEvaluationDetector,
                typeDeserializationConfigurer,
                this.inputParameters,
                logger
        );
        return transformer;
    }

    @Override
    protected void addSourceRoot(File outputFileDirectory) throws IOException {
        this.project.addCompileSourceRoot(outputFileDirectory.getCanonicalPath());
    }

    private void validateParameters(DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> dmnDialect, DMNValidator dmnValidator, DMNTransformer<TestCases> dmnTransformer, TemplateProvider templateProvider, Map<String, String> inputParameters) {
        boolean onePackage = InputParamUtil.getOptionalBooleanParam(inputParameters, "onePackage");
        String singletonInputData = InputParamUtil.getOptionalParam(inputParameters, "singletonInputData");
        String caching = InputParamUtil.getOptionalParam(inputParameters, "caching");
        if (onePackage) {
            this.getLog().warn("Use 'onePackage' carefully, names must be unique across all the DMs.");
        }
        if ("false".equals(singletonInputData) && "true".equals(caching)) {
            this.getLog().error(String.format("Incompatible 'singletonInputData=%s' and 'caching=%s'", singletonInputData, caching));
        }
    }
}
