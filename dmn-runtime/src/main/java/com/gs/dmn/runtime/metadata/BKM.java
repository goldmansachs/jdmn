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

public class BKM extends DRGElement {
    @JsonProperty("javaOutputTypeName")
    private String javaOutputTypeName;

    @JsonProperty("knowledgeReferences")
    private List<DRGElementReference> knowledgeReferences;

    // Required by ObjectMapper
    BKM() {
        super();
    }

    public BKM(String id, String name, String label, String diagramId, String shapeId, String javaParameterName, String javaTypeName, String javaOutputTypeName, QName typeRef, List<DRGElementReference> knowledgeReferences) {
        super(id, name, label, diagramId, shapeId, javaParameterName, javaTypeName, typeRef);
        this.javaOutputTypeName = javaOutputTypeName;
        this.knowledgeReferences = knowledgeReferences;
    }

    public String getJavaOutputTypeName() {
        return javaOutputTypeName;
    }

    public List<DRGElementReference> getKnowledgeReferences() {
        return knowledgeReferences;
    }
}
