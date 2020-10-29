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

import org.junit.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class RDFToDMNMojoTest<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> extends AbstractMojoTest {
    private final RDFToDMNMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> mojo = new RDFToDMNMojo<>();

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteWhenMissingInput() throws Exception {
        mojo.inputParameters = makeParams();
        mojo.execute();
        assertTrue(true);
    }

    @Test
    public void testExecute() throws Exception {
        String input = this.getClass().getClassLoader().getResource("input/NPEValidation2.xml").getFile();
        mojo.project = project;
        mojo.inputFileDirectory = new File(input);
        mojo.outputFileDirectory = new File("target/output");
        mojo.inputParameters = makeParams();

        mojo.execute();
        assertTrue(true);
    }

    @Override
    protected Map<String, String> makeParams() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("namespace", "http://wwww.org.com/cip");
        params.put("prefix", "cip");
        return params;
    }
}