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

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.transformation.FileTransformer;
import com.gs.dmn.transformation.InputParameters;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;

public abstract class AbstractFileTransformerMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> extends AbstractMojo {
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    public MavenProject project;

    protected void transform(File inputFileDirectory, File outputFileDirectory) throws MojoExecutionException {
        checkMandatoryFields();

        try {
            // Create transformer
            FileTransformer transformer = makeTransformer(new MavenBuildLogger(this.getLog()));

            // Transform
            this.getLog().info(String.format("Transforming '%s' to '%s' ...", inputFileDirectory, outputFileDirectory));
            transformer.transform(inputFileDirectory.toPath(), outputFileDirectory.toPath());

            // Add sources
            addSourceRoot(outputFileDirectory);
        } catch (Exception e) {
            throw new MojoExecutionException("", e);
        }
    }

    protected void checkMandatoryField(Object fieldValue, String fieldName) {
        if (fieldValue == null) {
            throw new IllegalArgumentException(String.format("'%s' is mandatory.", fieldName));
        }
    }

    protected abstract void addSourceRoot(File outputFileDirectory) throws IOException;

    protected abstract FileTransformer makeTransformer(BuildLogger logger) throws Exception;
    protected abstract InputParameters makeInputParameters();

    protected abstract void checkMandatoryFields();
}
