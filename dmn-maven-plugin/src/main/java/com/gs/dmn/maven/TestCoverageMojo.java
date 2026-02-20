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

import com.gs.dmn.runtime.coverage.report.*;
import com.gs.dmn.serialization.JsonSerializer;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;

@Mojo(name = "test-coverage-report", defaultPhase = LifecyclePhase.VERIFY, configurator = "dmn-mojo-configurator")
public class TestCoverageMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    public MavenProject project;

    @Parameter(required = true, defaultValue = "${project.build.directory}/coverage-traces")
    public File tracesDirectory;

    @Parameter(required = true, defaultValue = "${project.build.directory}/coverage-reports")
    public File reportsDirectory;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        transform(this.tracesDirectory, this.reportsDirectory);
    }

    protected void transform(File tracesDirectory, File reportsDirectory) throws MojoExecutionException {
        checkMandatoryFields();

        try {
            if (!tracesDirectory.exists()) {
                this.getLog().warn(String.format("Traces directory '%s' does not exist. No coverage report will be generated.", tracesDirectory));
                return;
            }
            if (!reportsDirectory.exists()) {
                this.getLog().info(String.format("Creating reports directory '%s' ...", reportsDirectory));
                if (!reportsDirectory.mkdirs()) {
                    throw new IOException(String.format("Failed to create reports directory '%s'.", reportsDirectory));
                }
            }

            // Create model report
            this.getLog().info(String.format("Generating coverage for models '%s' to '%s' ...", tracesDirectory, reportsDirectory));
            generateCoverageReport(new ModelCoverageReportGenerator(), "model-coverage");

            // Create element report
            this.getLog().info(String.format("Generating coverage for model elements '%s' to '%s' ...", tracesDirectory, reportsDirectory));
            generateCoverageReport(new ElementsCoverageReportGenerator(), "model-element-coverage");
        } catch (Exception e) {
            throw new MojoExecutionException("", e);
        }
    }

    private void generateCoverageReport(CoverageReportGenerator transformer, String reportFileName) throws IOException {
        // Create report
        CoverageReport coverageReport = transformer.generateCoverageReport(tracesDirectory);
        Table table = coverageReport.toTable();

        // Write report in json format
        File outputFile = new File(this.reportsDirectory, reportFileName + ".json");
        JsonSerializer.OBJECT_MAPPER.writeValue(outputFile, table);

        // Write report in csv format
        outputFile = new File(this.reportsDirectory, reportFileName + ".csv");
        FileUtils.writeLines(outputFile, table.toCsvLines());
    }

    protected void checkMandatoryFields() {
        checkMandatoryField(this.project, "project");
        checkMandatoryField(this.tracesDirectory, "tracesDirectory");
        checkMandatoryField(this.reportsDirectory, "reportsDirectory");
    }

    protected void checkMandatoryField(Object fieldValue, String fieldName) {
        if (fieldValue == null) {
            throw new IllegalArgumentException(String.format("'%s' is mandatory.", fieldName));
        }
    }
}
