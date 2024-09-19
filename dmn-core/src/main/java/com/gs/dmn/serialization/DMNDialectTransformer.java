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
import com.gs.dmn.serialization.xstream.*;

public class DMNDialectTransformer {
    private final DMN11ToLatestDialectTransformer dmn11ToLatestDialectTransformer;
    private final DMN12ToLatestDialectTransformer dmn12ToLatestDialectTransformer;
    private final DMN13ToLatestDialectTransformer dmn13ToLatestDialectTransformer;
    private final DMN14ToLatestDialectTransformer dmn14ToLatestDialectTransformer;
    private final DMN15ToLatestDialectTransformer dmn15ToLatestDialectTransformer;

    public DMNDialectTransformer(BuildLogger logger) {
        this.dmn11ToLatestDialectTransformer = new DMN11ToLatestDialectTransformer(logger);
        this.dmn12ToLatestDialectTransformer = new DMN12ToLatestDialectTransformer(logger);
        this.dmn13ToLatestDialectTransformer = new DMN13ToLatestDialectTransformer(logger);
        this.dmn14ToLatestDialectTransformer = new DMN14ToLatestDialectTransformer(logger);
        this.dmn15ToLatestDialectTransformer = new DMN15ToLatestDialectTransformer(logger);
    }

    public TDefinitions transform11ToLatestDefinitions(TDefinitions sourceDefinitions) {
        return this.dmn11ToLatestDialectTransformer.transformDefinitions(sourceDefinitions);
    }

    public TDefinitions transform12ToLatestDefinitions(TDefinitions sourceDefinitions) {
        return this.dmn12ToLatestDialectTransformer.transformDefinitions(sourceDefinitions);
    }

    public TDefinitions transform13ToLatestDefinitions(TDefinitions sourceDefinitions) {
        return this.dmn13ToLatestDialectTransformer.transformDefinitions(sourceDefinitions);
    }

    public TDefinitions transform14ToLatestDefinitions(TDefinitions sourceDefinitions) {
        return this.dmn14ToLatestDialectTransformer.transformDefinitions(sourceDefinitions);
    }

    public TDefinitions transform15ToLatestDefinitions(TDefinitions sourceDefinitions) {
        return this.dmn15ToLatestDialectTransformer.transformDefinitions(sourceDefinitions);
    }
}