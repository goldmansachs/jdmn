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
package com.gs.dmn.maven.configuration;

import org.apache.maven.plugins.annotations.Parameter;

import java.util.HashMap;
import java.util.Map;

public abstract class OptionallyConfigurableMojoComponent {
    public static final String ELEMENT_NAME = "name";
    public static final String ELEMENT_CONFIGURATION = "configuration";

    @Parameter(required = false)
    private String name;

    @Parameter(required = false)
    private Map<String, Object> configuration;


    protected OptionallyConfigurableMojoComponent() {
        this("", new HashMap<>());
    }

    protected OptionallyConfigurableMojoComponent(String name) {
        this(name, new HashMap<>());
    }

    protected OptionallyConfigurableMojoComponent(String name, Map<String, Object> configuration) {
        this.name = name;
        this.configuration = configuration;
    }

    /**
     * Returns the element name that is expected by the concrete subclass.  Only elements with this name
     * will be deserialized into the parameter type
     *
     * @return              Expected configuration element name
     */
    public abstract String getElementName();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, Object> configuration) {
        this.configuration = configuration;
    }
}
