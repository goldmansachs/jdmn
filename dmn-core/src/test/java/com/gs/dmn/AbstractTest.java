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
package com.gs.dmn;

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.transformation.InputParameters;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractTest {
    protected static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(AbstractTest.class));
    protected static final File STANDARD_FOLDER = new File("../dmn-test-cases/standard");
    private static final File SIGNAVIO_FOLDER = new File("../dmn-test-cases/signavio");

    protected URI tckResource(String path) {
        File file = new File(STANDARD_FOLDER, path);
        if (!file.exists()) {
            throw new DMNRuntimeException("Cannot find file '%s'".formatted(file.getPath()));
        }
        return file.toURI();
    }

    protected URI signavioResource(String path) {
        File file = new File(SIGNAVIO_FOLDER, path);
        if (!file.exists()) {
            throw new DMNRuntimeException("Cannot find file '%s'".formatted(file.getPath()));
        }
        return file.toURI();
    }

    protected URI resource(String path) {
        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            if (url == null) {
                throw new DMNRuntimeException("Cannot find resource '%s'".formatted(path));
            }
            return url.toURI();
        } catch (URISyntaxException e) {
            throw new DMNRuntimeException(e);
        }
    }

    protected String completePath(String pathFormat, String dmnVersion, String dmnFileName) {
        return pathFormat.formatted(dmnVersion, dmnFileName);
    }

    protected InputParameters makeInputParameters() {
        return new InputParameters(makeInputParametersMap());
    }

    protected Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = new LinkedHashMap<>();
        inputParams.put("dmnVersion", "1.1");
        inputParams.put("modelVersion", "1.0");
        inputParams.put("platformVersion", "1.0");
        return inputParams;
    }
}
