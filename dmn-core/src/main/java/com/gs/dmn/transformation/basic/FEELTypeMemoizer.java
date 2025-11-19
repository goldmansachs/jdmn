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
package com.gs.dmn.transformation.basic;

import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class FEELTypeMemoizer {
    protected static final Logger LOGGER = LoggerFactory.getLogger(FEELTypeMemoizer.class);

    private final Map<String, Type> typeOfQName = new LinkedHashMap<>();
    private final Map<TItemDefinition, Type> typeOfItemDefinition = new LinkedHashMap<>();

    public boolean contains(TDefinitions model, QualifiedName qName) {
        String key = makeKey(model, qName);
        return this.typeOfQName.containsKey(key);
    }

    public Type get(TDefinitions model, QualifiedName qName) {
        String key = makeKey(model, qName);
        return this.typeOfQName.get(key);
    }

    public void put(TDefinitions model, QualifiedName qName, Type type) {
        String key = makeKey(model, qName);
        this.typeOfQName.put(key, type);
    }

    public boolean contains(TItemDefinition itemDefinition) {
        return this.typeOfItemDefinition.containsKey(itemDefinition);
    }

    public Type get(TItemDefinition itemDefinition) {
        return this.typeOfItemDefinition.get(itemDefinition);
    }

    public void put(TItemDefinition itemDefinition, Type type) {
        this.typeOfItemDefinition.put(itemDefinition, type);
    }

    private String makeKey(TDefinitions model, QualifiedName qName) {
        return String.format("%s:%s:%s", getModelNamespace(model), qName.getNamespace(), qName.getLocalPart());
    }

    private String getModelNamespace(TDefinitions model) {
        return model != null ? model.getNamespace() : null;
    }
}
