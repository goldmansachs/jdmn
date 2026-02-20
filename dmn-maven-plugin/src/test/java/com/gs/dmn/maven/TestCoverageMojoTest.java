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

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCoverageMojoTest extends AbstractMojoTest {
    @Test
    public void testExecute() throws Exception {
        TestCoverageMojo mojo = new TestCoverageMojo();

        mojo.project = project;
        mojo.tracesDirectory = new File("target/traces");
        mojo.reportsDirectory = new File("target/reports");
        mojo.execute();
        assertTrue(true);
    }
}