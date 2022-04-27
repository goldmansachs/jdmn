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
package com.gs.dmn.serialization;

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.Pair;

public class DMNDialectTransformer {
    private final DMN11To13DialectTransformer dmn11To13DialectTransformer;
    private final DMN12To13DialectTransformer dmn12To13DialectTransformer;

    public DMNDialectTransformer(BuildLogger logger) {
        this.dmn11To13DialectTransformer = new DMN11To13DialectTransformer(logger);
        this.dmn12To13DialectTransformer = new DMN12To13DialectTransformer(logger);
    }

    public Pair<TDefinitions, PrefixNamespaceMappings> transform11To13Definitions(TDefinitions sourceDefinitions) {
        return this.dmn11To13DialectTransformer.transformDefinitions(sourceDefinitions);
    }

    public Pair<TDefinitions, PrefixNamespaceMappings> transform12To13Definitions(TDefinitions sourceDefinitions) {
        return this.dmn12To13DialectTransformer.transformDefinitions(sourceDefinitions);
    }
}