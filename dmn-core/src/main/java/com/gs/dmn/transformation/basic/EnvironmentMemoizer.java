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

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import org.omg.spec.dmn._20191111.model.TDRGElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.WeakHashMap;

public class EnvironmentMemoizer {
    protected static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentMemoizer.class);

    private final WeakHashMap<TDRGElement, Environment> environmentOfElement = new WeakHashMap<>();

    public Environment get(TDRGElement tdrgElement) {
        return this.environmentOfElement.get(tdrgElement);
    }

    public void put(TDRGElement tdrgElement, Environment environment) {
        this.environmentOfElement.put(tdrgElement, environment);
    }
}
