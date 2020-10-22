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
package com.gs.dmn.maven.configuration.components;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gs.dmn.maven.configuration.OptionallyConfigurableMojoComponent;

import java.util.Map;

public class DMNTransformerComponent extends OptionallyConfigurableMojoComponent {
    public DMNTransformerComponent() {
        super();
    }

    public DMNTransformerComponent(String name) {
        super(name);
    }

    public DMNTransformerComponent(String name, Map<String, Object> configuration) {
        super(name, configuration);
    }


    @Override
    @JsonIgnore
    public String getElementName() {
        return "dmnTransformer";
    }
}
