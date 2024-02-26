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
package com.gs.dmn.signavio.testlab;

public abstract class ParameterDefinition extends TestLabElement {
    private String id;
    private String diagramId;
    private String shapeId;
    private String modelName;
    private String requirementName;

    public String getId() {
        return id;
    }

    public String getDiagramId() {
        try {
            if (diagramId != null) {
                return diagramId;
            } else {
                return getIds()[0];
            }
        } catch (Exception e) {
            return "-1";
        }
    }

    public String getShapeId() {
        try {
            if (shapeId != null) {
                return shapeId;
            } else {
                return getIds()[1];
            }
        } catch (Exception e) {
            return "-1";
        }
    }

    public String getModelName() {
        return modelName;
    }

    public String getRequirementName() {
        return requirementName;
    }

    private String[] getIds() {
        return id.split("/");
    }

    public void setDiagramId(String diagramId) {
        this.diagramId = diagramId;
    }

    public void setShapeId(String shapeId) {
        this.shapeId = shapeId;
    }

    @Override
    public String toString() {
        return "%s(%s, %s, %s)".formatted(this.getClass().getSimpleName(), id, modelName, requirementName);
    }
}
