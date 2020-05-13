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
import com.gs.dmn.signavio.testlab.TestLabToJUnitTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.FileTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.Map;

@SuppressWarnings("CanBeFinal")
@Mojo(name = "testlab-to-java", defaultPhase = LifecyclePhase.GENERATE_TEST_SOURCES, configurator = "dmn-mojo-configurator")
public class TestLabToJavaJUnitMojo extends AbstractDMNMojo {
    @Parameter(required = true, defaultValue = "com.gs.dmn.signavio.dialect.SignavioDMNDialectDefinition")
    public String dmnDialect;

    @Parameter(required = false)
    public String[] dmnValidators;

    @Parameter(required = false)
    public DMNTransformerComponent[] dmnTransformers;

    @Parameter(required = true, defaultValue = "com.gs.dmn.signavio.transformation.template.SignavioTreeTemplateProvider")
    public String templateProvider;

    @Parameter(required = false)
    public String[] lazyEvaluationDetectors;

    @Parameter(required = false, defaultValue = "com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer")
    public String typeDeserializationConfigurer;

    @Parameter(required = false)
    public Map<String, String> inputParameters;

    @Parameter(required = true, defaultValue = "${project.basedir}/src/main/resources/signavio")
    public File inputTestFileDirectory;

    @Parameter(required = true, defaultValue = "${project.basedir}/src/main/resources/signavio")
    public File inputModelFileDirectory;

    @Parameter(required = true, defaultValue = "${project.build.directory}/generated-test-sources/junit")
    public File outputFileDirectory;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        checkMandatoryField(inputTestFileDirectory, "inputTestFileDirectory");
        checkMandatoryField(inputModelFileDirectory, "inputModelFileDirectory");
        checkMandatoryField(outputFileDirectory, "outputFileDirectory");
        checkMandatoryField(dmnDialect, "dmnDialect");

        try {
            // Create transformer
            MavenBuildLogger logger = new MavenBuildLogger(this.getLog());
            Class<?> dialectClass = Class.forName(dmnDialect);
            DMNDialectDefinition dmnDialect = (DMNDialectDefinition) dialectClass.newInstance();
            DMNValidator dmnValidator = makeDMNValidator(this.dmnValidators, logger);
            DMNTransformer dmnTransformer = makeDMNTransformer(this.dmnTransformers, logger);
            TemplateProvider templateProvider = makeTemplateProvider(this.templateProvider, logger);
            LazyEvaluationDetector lazyEvaluationDetector = makeLazyEvaluationDetector(this.lazyEvaluationDetectors, logger, this.inputParameters);
            TypeDeserializationConfigurer typeDeserializationConfigurer = makeTypeDeserializationConfigurer(this.typeDeserializationConfigurer, logger);
            FileTransformer transformer = new TestLabToJUnitTransformer(
                    dmnDialect, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer,
                    inputModelFileDirectory.toPath(), inputParameters,
                    logger
            );

            // Transform
            this.getLog().info(String.format("Transforming '%s' to '%s' ...", this.inputTestFileDirectory, this.outputFileDirectory));
            transformer.transform(inputTestFileDirectory.toPath(), outputFileDirectory.toPath());

            // Add sources
            this.project.addTestCompileSourceRoot(this.outputFileDirectory.getCanonicalPath());
        } catch (Exception e) {
            throw new MojoExecutionException("", e);
        }
    }
}