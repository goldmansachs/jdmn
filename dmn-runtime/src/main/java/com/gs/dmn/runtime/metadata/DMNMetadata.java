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
package com.gs.dmn.runtime.metadata;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonPropertyOrder(value = {"dmnVersion", "modelVersion", "platformVersion", "dmnNamespace", "nativeNamespace", "types", "elements"})
public class DMNMetadata {
    private String dmnVersion;
    private String modelVersion;
    private String platformVersion;
    private String dmnNamespace;
    private String nativeNamespace;
    private final List<Type> types = new ArrayList<>();
    private final List<DRGElement> elements = new ArrayList<>();

    // Required by ObjectMapper
    public DMNMetadata() {
    }

    public DMNMetadata(String dmnNamespace, String nativeNamespace, String dmnVersion, String modelVersion, String platformVersion) {
        this.dmnVersion = dmnVersion;
        this.modelVersion = modelVersion;
        this.platformVersion = platformVersion;
        this.dmnNamespace = dmnNamespace;
        this.nativeNamespace = nativeNamespace;
    }

    @JsonGetter("dmnNamespace")
    public String getDmnNamespace() {
        return dmnNamespace;
    }

    @JsonGetter("nativeNamespace")
    public String getNativeNamespace() {
        return nativeNamespace;
    }

    @JsonGetter("dmnVersion")
    public String getDmnVersion() {
        return dmnVersion;
    }

    @JsonGetter("modelVersion")
    public String getModelVersion() {
        return modelVersion;
    }

    @JsonGetter("platformVersion")
    public String getPlatformVersion() {
        return platformVersion;
    }

    @JsonGetter("types")
    public List<Type> getTypes() {
        return types;
    }

    public void addType(Type type) {
        if (type != null) {
            types.add(type);
        }
    }

    @JsonGetter("elements")
    public List<DRGElement> getElements() {
        return elements;
    }

    public void addElement(DRGElement element) {
        if (element != null) {
            elements.add(element);
        }
    }

    @JsonIgnore
    public List<DRGElement> getInputDatas() {
        return getElements().stream().filter(e -> e instanceof InputData).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<DRGElement> getDecisions() {
        return getElements().stream().filter(e -> e instanceof Decision).collect(Collectors.toList());
    }

    public DRGElement findElementByName(String name) {
        List<DRGElement> result = getElements().stream().filter(e -> e.getName().equals(name)).collect(Collectors.toList());
        return checkFilter(result, String.format("Found multiple elements with name '%s'. Expected only one.", name));
    }

    public DRGElement findElementById(String id) {
        List<DRGElement> result = getElements().stream().filter(e -> e.getId().equals(id)).collect(Collectors.toList());
        return checkFilter(result, String.format("Found multiple elements with id '%s'. Expected only one.", id));
    }

    public Type findTypeByName(String name) {
        List<Type> result = getTypes().stream().filter(t -> t.getName().equals(name)).collect(Collectors.toList());
        return checkFilter(result, String.format("Found multiple types with name '%s'. Expected only one.", name));
    }

    private <T> T checkFilter(List<T> result, String errorMessage) {
        if (result == null || result.isEmpty()) {
            return null;
        } else if (result.size() == 1) {
            return result.get(0);
        } else {
            throw new DMNRuntimeException(errorMessage);
        }
    }
}
