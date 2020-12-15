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

import com.gs.dmn.feel.analysis.semantics.type.Type;
import org.omg.spec.dmn._20191111.model.TDefinitions;
import org.omg.spec.dmn._20191111.model.TItemDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.WeakHashMap;

public class FEELTypeMemoizer {
    protected static final Logger LOGGER = LoggerFactory.getLogger(FEELTypeMemoizer.class);

    private final WeakHashMap<String, Type> typeOfQName = new WeakHashMap<>();
    private final WeakHashMap<TItemDefinition, Type> typeOfItemDefinition = new WeakHashMap<>();

    public Type get(TDefinitions model, QualifiedName qName) {
        String key = makeKey(model, qName);
        return this.typeOfQName.get(key);
    }

    public void put(TDefinitions model, QualifiedName qName, Type type) {
        String key = makeKey(model, qName);
        this.typeOfQName.put(key, type);
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
