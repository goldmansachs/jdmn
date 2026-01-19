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
package com.gs.dmn.runtime.discovery;

import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.ExecutableDRGElement;
import com.gs.dmn.runtime.annotation.DRGElement;
import com.gs.dmn.runtime.annotation.DRGElementKind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ModelElementRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelElementRegistry.class);

    private final Map<String, String> classNameMap = new LinkedHashMap<>();
    private final Map<String, ExecutableDRGElement> executableElementMap = new LinkedHashMap<>();

    public void register(String qName, String className) {
        Objects.requireNonNull(qName, "Missing qName");
        Objects.requireNonNull(className, "Missing class name");

        String value = classNameMap.get(qName);
        if (value == null) {
            classNameMap.put(qName, className);
        } else if (!value.equals(className)) {
            throw new DMNRuntimeException(String.format("Name '%s' is not unique", qName));
        } else {
            LOGGER.warn("Name {} and value {} were already registered", qName, className);
        }
    }

    public Set<String> keys() {
        return this.classNameMap.keySet();
    }

    public <T> ExecutableDRGElement<T> discover(String qName, Class<T> outputClass) {
        return makeInstance(qName, outputClass);
    }

    private <T> ExecutableDRGElement<T> makeInstance(String qName, Class<T> outputClass) {
        ExecutableDRGElement executableDRGElement = this.executableElementMap.get(qName);
        if (executableDRGElement == null) {
            String clsName = this.classNameMap.get(qName);
            if (clsName == null) {
                throw new DMNRuntimeException(String.format("Element '%s' is not registered. Registered elements are %s", qName, keys()));
            }
            try {
                Class<?> elementClass = Class.forName(clsName);
                executableDRGElement = makeInstance(elementClass);
            } catch (Exception e) {
                throw new DMNRuntimeException(String.format("Cannot instantiate class '%s' for name '%s'", clsName, qName));
            }
            this.executableElementMap.put(qName, executableDRGElement);
        }
        return executableDRGElement;
    }

    private static <T> ExecutableDRGElement<T> makeInstance(Class<T> elementClass) throws Exception {
        DRGElement annotation = elementClass.getAnnotation(DRGElement.class);
        LOGGER.debug("Instantiating element '{}'", annotation.name());
        if (isDecision(annotation)) {
            // Invoke the default constructor
            return (ExecutableDRGElement) elementClass.getConstructor().newInstance();
        } else if (isInvocable(annotation)) {
            // Invoke the static instance() method
            return (ExecutableDRGElement) elementClass.getMethod("instance").invoke(null);
        } else {
            throw new DMNRuntimeException(String.format("Cannot instantiate element '%s'. Element is neither Decision nor Invocable.", annotation.name()));
        }
    }

    private static boolean isDecision(DRGElement annotation) {
        if (annotation == null) {
            return false;
        } else {
            return annotation.elementKind() == DRGElementKind.DECISION;
        }
    }

    private static boolean isInvocable(DRGElement annotation) {
        if (annotation == null) {
            return false;
        } else {
            DRGElementKind drgElementKind = annotation.elementKind();
            return drgElementKind == DRGElementKind.DECISION_SERVICE || drgElementKind == DRGElementKind.BUSINESS_KNOWLEDGE_MODEL;
        }
    }

}

