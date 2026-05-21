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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModelElementRegistry {
    private static void requireNonNull(String str, Supplier<String> errorMessageSupplier) {
        if (StringUtils.isBlank(str)) {
            throw new DMNRuntimeException(errorMessageSupplier.get());
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelElementRegistry.class);

    private final ConcurrentMap<String, String> classNameMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, ExecutableDRGElement<?>> executableElementMap = new ConcurrentHashMap<>();

    public void register(String qName, String className) {
        requireNonNull(qName, () -> "Missing qualified name of DMN element");
        requireNonNull(className, () -> "Missing class name");

        String existing = classNameMap.putIfAbsent(qName, className);
        if (existing == null) {
            // successfully registered
        } else if (!existing.equals(className)) {
            throw new DMNRuntimeException(String.format("Name '%s' is not unique", qName));
        } else {
            LOGGER.warn("Name {} and str {} were already registered", qName, className);
        }
    }

    public Set<String> keys() {
        return this.classNameMap.keySet();
    }

    public <T> ExecutableDRGElement<T> discover(String qName, Class<T> outputClass) {
        return putIfAbsent(qName, ModelElementRegistry::makeInstance);
    }

    public <T> ExecutableDRGElement<T> discoverSingleton(String qName, Class<T> outputClass) {
        return putIfAbsent(qName, ModelElementRegistry::makeSingleton);
    }

    private <T> ExecutableDRGElement<T> putIfAbsent(String qName, Function<Class<T>, ExecutableDRGElement<T>> creator) {
        ExecutableDRGElement<T> executableDRGElement = (ExecutableDRGElement<T>) this.executableElementMap.get(qName);
        if (executableDRGElement == null) {
            String clsName = this.classNameMap.get(qName);
            requireNonNull(clsName, () -> String.format("Element '%s' is not registered. Registered elements are %s", qName, keys()));
            try {
                Class<T> elementClass = (Class<T>) Class.forName(clsName, true, this.getClass().getClassLoader());
                executableDRGElement = creator.apply(elementClass);
            } catch (Exception e) {
                throw new DMNRuntimeException(String.format("Cannot instantiate class '%s' for name '%s'", clsName, qName), e);
            }
            // Try to put the freshly created instance into the concurrent map. If another thread put one first, use that one.
            ExecutableDRGElement<?> previous = this.executableElementMap.putIfAbsent(qName, executableDRGElement);
            if (previous != null) {
                executableDRGElement = (ExecutableDRGElement<T>) previous;
            }
        }
        return executableDRGElement;
    }

    private static <T> ExecutableDRGElement<T> makeInstance(Class<T> elementClass) {
        try {
            DRGElement annotation = elementClass.getAnnotation(DRGElement.class);
            LOGGER.debug("Instantiating element '{}'", annotationName(annotation));
            if (isDecision(annotation)) {
                // Invoke the default constructor
                return (ExecutableDRGElement<T>) elementClass.getConstructor().newInstance();
            } else if (isInvocable(annotation)) {
                // Invoke the static instance() method
                return (ExecutableDRGElement<T>) elementClass.getMethod("instance").invoke(null);
            } else {
                throw new DMNRuntimeException(String.format("Cannot instantiate element '%s'. Element is neither Decision nor Invocable.", annotationName(annotation)));
            }
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot instantiate class '%s'", elementClass.getName()), e);
        }
    }

    private static <T> ExecutableDRGElement<T> makeSingleton(Class<T> elementClass) {
        try {
            DRGElement annotation = elementClass.getAnnotation(DRGElement.class);
            LOGGER.debug("Instantiating singleton element '{}'", annotationName(annotation));
            if (isDecision(annotation) || isInvocable(annotation)) {
                // Invoke the static instance() method
                return (ExecutableDRGElement<T>) elementClass.getMethod("instance").invoke(null);
            } else {
                throw new DMNRuntimeException(String.format("Cannot instantiate singleton element '%s'. Element is neither Decision nor Invocable.", annotationName(annotation)));
            }
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot instantiate class '%s'", elementClass.getName()), e);
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

    private static Object annotationName(DRGElement annotation) {
        return annotation == null ? null : annotation.name();
    }
}
