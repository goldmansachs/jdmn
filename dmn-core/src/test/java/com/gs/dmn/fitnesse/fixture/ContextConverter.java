/**
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
package com.gs.dmn.fitnesse.fixture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.DMNRuntimeException;
import fitnesse.slim.Converter;

public class ContextConverter implements Converter<Context> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String toString(Context o) {
        try {
            return MAPPER.writeValueAsString(o.getBindings());
        } catch (Exception e) {
            throw new DMNRuntimeException(e);
        }
    }

    @Override
    public Context fromString(String text) {
        throw new DMNRuntimeException("Not implemented yet");
    }
}
