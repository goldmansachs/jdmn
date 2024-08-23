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
package com.gs.dmn.serialization.jackson;

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.transformation.InputParameters;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonDMNSerializer extends DMNSerializer {
    public JsonDMNSerializer(BuildLogger logger, InputParameters inputParameters) {
        this(logger, new LinkedHashMap<>(), inputParameters);
    }

    public JsonDMNSerializer(BuildLogger logger, Map<String, Class<?>> mapper, InputParameters inputParameters) {
        super(logger, DMNMarshallerFactory.newMarshallerWithExtensions(mapper), inputParameters);
    }
}