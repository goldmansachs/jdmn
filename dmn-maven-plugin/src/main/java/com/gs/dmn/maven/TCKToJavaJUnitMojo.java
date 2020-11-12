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
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.tck.TCKTestCasesToJavaJUnitTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.FileTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.omg.dmn.tck.marshaller._20160719.TestCases;

import java.io.File;

@SuppressWarnings("CanBeFinal")
@Mojo(name = "tck-to-java", defaultPhase = LifecyclePhase.GENERATE_TEST_SOURCES, configurator = "dmn-mojo-configurator")
public class TCKToJavaJUnitMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractTestToJunitMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> {
    @Parameter(required = true, defaultValue = "com.gs.dmn.dialect.StandardDMNDialectDefinition")
    public String dmnDialect;

    @Parameter(required = true, defaultValue = "com.gs.dmn.transformation.template.TreeTemplateProvider")
    public String templateProvider;

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
        return super.makeTransformer(logger, this.dmnDialect, this.templateProvider);
    }

    @Override
    protected FileTransformer makeTransformer(DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> dmnDialect, DMNValidator dmnValidator, DMNTransformer<TestCases> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, InputParameters inputParameters, BuildLogger logger) {
        FileTransformer transformer = new TCKTestCasesToJavaJUnitTransformer<>(
                dmnDialect,
                dmnValidator,
                dmnTransformer,
                templateProvider,
                lazyEvaluationDetector,
                typeDeserializationConfigurer,
                this.inputModelFileDirectory.toPath(),
                makeInputParameters(),
                logger
        );
        return transformer;
    }
}
