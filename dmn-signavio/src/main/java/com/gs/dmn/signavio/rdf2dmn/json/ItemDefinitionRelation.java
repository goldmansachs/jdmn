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

public class ItemDefinitionRelation {
    private String relationId;
    private String gitemTitle;
    private String relationType;
    private String title;
    private ItemDefinitionRelationValue value;

    public String getRelationId() {
        return relationId;
    }

    public String getGitemTitle() {
        return gitemTitle;
    }

    public String getRelationType() {
        return relationType;
    }

    public String getTitle() {
        return title;
    }

    public ItemDefinitionRelationValue getValue() {
        return value;
    }
}
