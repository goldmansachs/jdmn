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
package com.gs.dmn.signavio.runtime.compiler;

import com.gs.dmn.runtime.compiler.AbstractDMNToJavaTranslatorBuilder;
import com.gs.dmn.signavio.dialect.JavaTimeSignavioDMNDialectDefinition;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.signavio.transformation.template.SignavioTreeTemplateProvider;
import java.util.LinkedHashMap;
import java.util.Map;

public class SignavioToJavaTranslatorBuilder extends AbstractDMNToJavaTranslatorBuilder<TestLab> {
    private static final Map<String, String> MAP;
    static {
        MAP = new LinkedHashMap<>();
        MAP.put("dmn.version", "1.5");
        MAP.put("model.version", "1.0");
        MAP.put("platform.version", "10.0.0");
        MAP.put("xsdValidation", "false");
    }

    public SignavioToJavaTranslatorBuilder() {
        // Default is Signavio DMN and Java
        super(new JavaTimeSignavioDMNDialectDefinition());
        this.templateProvider = new SignavioTreeTemplateProvider();
    }

    @Override
    protected Map<String, String> makeDefaultConfiguration() {
        return MAP;
    }
}


