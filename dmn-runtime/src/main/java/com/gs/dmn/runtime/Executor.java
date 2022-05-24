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
package com.gs.dmn.runtime;

import com.gs.dmn.runtime.discovery.ModelElementRegistry;

import java.util.LinkedHashMap;
import java.util.Map;

public class Executor {
    private final Map<String, DMNDecision> map = new LinkedHashMap<>();
    private final ModelElementRegistry registry;

    public Executor(ModelElementRegistry registry) {
        this.registry = registry;
    }

    public Object execute(String qName, Map<String, String> input, ExecutionContext context) {
        DMNDecision dmnDecision = makeInstance(qName);
        return dmnDecision.apply(input, context);
    }

    private DMNDecision makeInstance(String qName) {
        DMNDecision dmnDecision = map.get(qName);
        if (dmnDecision == null) {
            String clsName = registry.discover(qName);
            if (clsName == null) {
                throw new DMNRuntimeException(String.format("Element %s is not registered in %s", qName, registry.keys()));
            }
            try {
                dmnDecision = (DMNDecision) Class.forName(clsName).getConstructor().newInstance();
            } catch (Exception e) {
                throw new DMNRuntimeException(String.format("Cannot instantiate class '%s' for name '%s'", clsName, qName));
            }
            this.map.put(qName, dmnDecision);
        }
        return dmnDecision;
    }
}
