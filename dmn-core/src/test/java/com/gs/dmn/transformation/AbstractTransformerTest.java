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
package com.gs.dmn.transformation;

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.transformation.template.TemplateProvider;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public abstract class AbstractTransformerTest extends AbstractFileTransformerTest {
    protected URI resource(String path) {
        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            if (url == null) {
                throw new DMNRuntimeException(String.format("Cannot find resource '%s'", path));
            }
            return url.toURI();
        } catch (URISyntaxException e) {
            throw new DMNRuntimeException(e);
        }
    }

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

    protected abstract DMNDialectDefinition makeDialectDefinition();

    protected abstract DMNTransformer makeDMNTransformer(BuildLogger logger);

    protected abstract TemplateProvider makeTemplateProvider();

    protected abstract Map<String, String> makeInputParameters();

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
    public DefaultTransformer(Map<String, String> inputParameters, BuildLogger logger) {
        super(inputParameters, logger);
    }

    @Override
    protected boolean shouldTransform(File inputFile) {
        return false;
    }

    @Override
    protected void transformFile(File child, File root, Path outputPath) {
    }
}