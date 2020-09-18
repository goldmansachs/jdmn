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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Decision extends DRGElement {
    @JsonProperty("javaOutputTypeName")
    private String javaOutputTypeName;

    @JsonProperty("informationReferences")
    private List<DRGElementReference> informationReferences;

    @JsonProperty("knowledgeReferences")
    private List<DRGElementReference> knowledgeReferences;

    @JsonProperty("extensions")
    private List<ExtensionElement> extensionElements;

    @JsonProperty("transitiveRequiredInput")
    private List<InputData> transitiveRequiredInputs;

    @JsonProperty("protoRequestName")
    private String protoRequestName;

    @JsonProperty("protoResponseName")
    private String protoResponseName;

    // Required by ObjectMapper
    Decision() {
        super();
    }

    public Decision(String id, String name, String label, String diagramId, String shapeId, String javaParameterName, String javaTypeName, String javaOutputTypeName, QName typeRef, List<DRGElementReference> informationReferences, List<DRGElementReference> knowledgeReferences, List<ExtensionElement> extensionElements, List<InputData> transitiveRequiredInputs, String protoRequestName, String protoResponseName) {
        super(id, name, label, diagramId, shapeId, javaParameterName, javaTypeName, typeRef);
        this.javaOutputTypeName = javaOutputTypeName;
        this.informationReferences = informationReferences;
        this.knowledgeReferences = knowledgeReferences;
        this.extensionElements = extensionElements;
        this.transitiveRequiredInputs = transitiveRequiredInputs;
        this.protoRequestName = protoRequestName;
        this.protoResponseName = protoResponseName;
    }

    public List<DRGElementReference> getInformationReferences() {
        return informationReferences;
    }

    public List<ExtensionElement> getExtensionElements() {
        return extensionElements;
    }

}
