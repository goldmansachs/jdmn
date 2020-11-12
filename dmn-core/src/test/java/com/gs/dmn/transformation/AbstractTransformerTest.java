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
package com.gs.dmn.transformation;

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public abstract class AbstractTransformerTest<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> extends AbstractFileTransformerTest {
    protected Path path(String path) {
        File file = new File(resource(path));
        return file.toPath();
    }

    protected String friendlyFolderName(String name) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (Character.isAlphabetic(ch) || Character.isDigit(ch)) {
                result.append(ch);
            } else {
                result.append("-");
            }
        }
        return result.toString().toLowerCase();
    }

    protected Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = new LinkedHashMap<>();
        inputParams.put("dmnVersion", "1.1");
        inputParams.put("modelVersion", "2.0");
        inputParams.put("platformVersion", "1.0");
        return inputParams;
    }

    protected abstract DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> makeDialectDefinition();

    protected abstract DMNValidator makeDMNValidator(BuildLogger logger);

    protected abstract DMNTransformer<TEST> makeDMNTransformer(BuildLogger logger);

    protected abstract TemplateProvider makeTemplateProvider();

    protected abstract LazyEvaluationDetector makeLazyEvaluationDetector(InputParameters inputParameters, BuildLogger logger);

    protected abstract TypeDeserializationConfigurer makeTypeDeserializationConfigurer(BuildLogger logger);

    @Test
    public void testRelativePath() {
        AbstractFileTransformer transformer = new DefaultTransformer(null, null);
        assertEquals("", transformer.relativePath("", ""));
        assertEquals("", transformer.relativePath("abc", "abc"));
        assertEquals("", transformer.relativePath("abc/", "abc"));
        assertEquals("abc", transformer.relativePath("abc/complex/", "abc/complex/abc"));
    }
}

class DefaultTransformer extends AbstractFileTransformer {
    public DefaultTransformer(InputParameters inputParameters, BuildLogger logger) {
        super(inputParameters, logger);
    }

    @Override
    protected boolean shouldTransformFile(File inputFile) {
        return false;
    }

    @Override
    protected void transformFile(File child, File root, Path outputPath) {
    }
}