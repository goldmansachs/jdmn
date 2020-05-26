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

import java.io.File;
import java.util.Map;

@SuppressWarnings("CanBeFinal")
@Mojo(name = "dmn-to-java", defaultPhase = LifecyclePhase.GENERATE_SOURCES, configurator = "dmn-mojo-configurator")
public class DMNToJavaMojo extends AbstractDMNMojo {
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
        checkMandatoryField(project, "project");
        checkMandatoryField(inputFileDirectory, "inputFileDirectory");
        checkMandatoryField(outputFileDirectory, "outputFileDirectory");
        checkMandatoryField(dmnDialect, "dmnDialect");

        try {
            // Create and validate arguments
            BuildLogger logger = new MavenBuildLogger(this.getLog());
            Class<?> dialectClass = Class.forName(dmnDialect);
            DMNDialectDefinition dmnDialect = makeDialect(dialectClass);
            DMNValidator dmnValidator = makeDMNValidator(this.dmnValidators, logger);
            DMNTransformer dmnTransformer = makeDMNTransformer(this.dmnTransformers, logger);
            TemplateProvider templateProvider = makeTemplateProvider(this.templateProvider, logger);
            LazyEvaluationDetector lazyEvaluationDetector = makeLazyEvaluationDetector(this.lazyEvaluationDetectors, logger, this.inputParameters);
            TypeDeserializationConfigurer typeDeserializationConfigurer = makeTypeDeserializationConfigurer(this.typeDeserializationConfigurer, logger);
            validateParameters(dmnDialect, dmnValidator, dmnTransformer, templateProvider, inputParameters);

            // Create transformer
            FileTransformer transformer = dmnDialect.createDMNToNativeTransformer(
                    dmnValidator,
                    dmnTransformer,
                    templateProvider,
                    lazyEvaluationDetector,
                    typeDeserializationConfigurer,
                    inputParameters,
                    logger
            );

            // Transform
            transformer.transform(inputFileDirectory.toPath(), outputFileDirectory.toPath());
            this.getLog().info(String.format("Transforming '%s' to '%s' ...", this.inputFileDirectory, this.outputFileDirectory));

            // Add sources
            this.project.addCompileSourceRoot(this.outputFileDirectory.getCanonicalPath());
        } catch (Exception e) {
            throw new MojoExecutionException("", e);
        }
    }

    private void validateParameters(DMNDialectDefinition dmnDialect, DMNValidator dmnValidator, DMNTransformer dmnTransformer, TemplateProvider templateProvider, Map<String, String> inputParameters) {
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
