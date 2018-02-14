/**
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

import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.runtime.DefaultDMNBaseDecision;
import com.gs.dmn.transformation.NopDMNTransformer;
import com.gs.dmn.transformation.ToJavaNameTransformer;
import org.apache.maven.project.MavenProject;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class DMN2JavaMojoTest extends AbstractMojoTest {
    private final DMNToJavaMojo mojo = new DMNToJavaMojo();
    private final MavenProject project = new MavenProject();

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteWhenMissingInput() throws Exception {
        mojo.inputParameters = makeParams();
        mojo.execute();
        assertTrue(true);
    }

    @Test
    public void testExecute() throws Exception {
        String input = this.getClass().getClassLoader().getResource("input/0004-lending.dmn").getFile();
        mojo.project = project;
        mojo.dmnDialect = StandardDMNDialectDefinition.class.getName();
        mojo.dmnTransformer = ToJavaNameTransformer.class.getName();
        mojo.inputFileDirectory = new File(input);
        mojo.outputFileDirectory = new File("target/output");
        mojo.inputParameters = makeParams();
        mojo.execute();
        assertTrue(true);
    }
}