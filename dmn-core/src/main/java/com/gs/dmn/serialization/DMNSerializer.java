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

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.Map;

public abstract class DMNSerializer {
    protected static final Map<DMNVersion, JAXBContext> JAXB_CONTEXTS = new HashMap<>();

    static {
        try {
            JAXB_CONTEXTS.put(DMNVersion.DMN_11, JAXBContext.newInstance(DMNVersion.DMN_11.getJavaPackage()));
            JAXB_CONTEXTS.put(DMNVersion.DMN_12, JAXBContext.newInstance(DMNVersion.DMN_12.getJavaPackage()));
            JAXB_CONTEXTS.put(DMNVersion.DMN_13, JAXBContext.newInstance(DMNVersion.DMN_13.getJavaPackage()));
        } catch (JAXBException e) {
            throw new DMNRuntimeException("Cannot create JAXB Context", e);
        }
    }

    protected final BuildLogger logger;

    protected DMNSerializer(BuildLogger logger) {
        this.logger = logger;
    }

    protected JAXBContext getJAXBContext(DMNVersion dmnVersion) {
        JAXBContext context = JAXB_CONTEXTS.get(dmnVersion);
        if (context == null) {
            throw new IllegalArgumentException(String.format("Cannot find context for '%s'", dmnVersion.getVersion()));
        }
        return context;
    }
}
