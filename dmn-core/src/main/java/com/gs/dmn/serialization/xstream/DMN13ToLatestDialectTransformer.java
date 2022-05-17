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
package com.gs.dmn.serialization.xstream;

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.serialization.SimpleDMNDialectTransformer;

import static com.gs.dmn.serialization.DMNVersion.DMN_13;
import static com.gs.dmn.serialization.DMNVersion.LATEST;

public class DMN13ToLatestDialectTransformer extends SimpleDMNDialectTransformer {
    public DMN13ToLatestDialectTransformer(BuildLogger logger) {
        super(logger, DMN_13, LATEST);
    }
}
