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
package com.gs.dmn.signavio.rdf2dmn.json;

public class ItemDefinitionRelationValue {
    private Boolean isList;
    private String definitionType;
    private String type;
    private String name;
    private Boolean isIgnoreCase;
    private String min;
    private String max;
    private String unit;
    private Integer width;

    public Boolean getList() {
        return isList;
    }

    public String getDefinitionType() {
        return definitionType;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Boolean getIgnoreCase() {
        return isIgnoreCase;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }

    public String getUnit() {
        return unit;
    }

    public Integer getWidth() {
        return width;
    }
}
