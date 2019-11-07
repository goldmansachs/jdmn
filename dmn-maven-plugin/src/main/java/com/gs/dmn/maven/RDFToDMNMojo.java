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

import com.gs.dmn.signavio.rdf2dmn.RDFToDMNTransformer;
import com.gs.dmn.transformation.FileTransformer;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings("CanBeFinal")
@Mojo(name = "rdf-to-dmn", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class RDFToDMNMojo extends AbstractDMNMojo {
    @Parameter(required = false)
    public Map<String, String> inputParameters;

    @Parameter(required = true, defaultValue = "${project.basedir}/src/main/resources/signavio")
    public File inputFileDirectory;

    @Parameter(required = true, defaultValue = "${project.build.directory}/generated-resources/dmn")
    public File outputFileDirectory;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        checkMandatoryField(inputFileDirectory, "inputFileDirectory");
        checkMandatoryField(outputFileDirectory, "outputFileDirectory");

        this.getLog().info(String.format("Transforming '%s' to '%s' ...", this.inputFileDirectory, this.outputFileDirectory));

        MavenBuildLogger logger = new MavenBuildLogger(this.getLog());
        FileTransformer transformer = new RDFToDMNTransformer(
                inputParameters,
                logger
        );
        transformer.transform(inputFileDirectory.toPath(), outputFileDirectory.toPath());

        try {
            this.project.addCompileSourceRoot(this.outputFileDirectory.getCanonicalPath());
        } catch (IOException e) {
            throw new MojoExecutionException("", e);
        }
    }
}
