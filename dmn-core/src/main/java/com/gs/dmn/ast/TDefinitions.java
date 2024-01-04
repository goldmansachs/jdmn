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
package com.gs.dmn.ast;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gs.dmn.ast.dmndi.DMNDI;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({
        "name",
        "id",
        "label",
        "expressionLanguage",
        "typeLanguage",
        "namespace",
        "exporter",
        "exporterVersion",
        "otherAttributes",
        "description",
        "import",
        "itemDefinition",
        "drgElement",
        "artifact",
        "elementCollection",
        "businessContextElement",
        "dmndi",
        "extensionElements"
})
public class TDefinitions extends TNamedElement implements Visitable {
    @JsonProperty("import")
    private List<TImport> _import;
    private List<TItemDefinition> itemDefinition;
    private List<TDRGElement> drgElement;
    private List<TArtifact> artifact;
    private List<TElementCollection> elementCollection;
    private List<TBusinessContextElement> businessContextElement;
    private DMNDI dmndi;
    private String expressionLanguage;
    private String typeLanguage;
    private String namespace;
    private String exporter;
    private String exporterVersion;

    public List<TImport> getImport() {
        if (_import == null) {
            _import = new ArrayList<>();
        }
        return this._import;
    }

    public List<TItemDefinition> getItemDefinition() {
        if (itemDefinition == null) {
            itemDefinition = new ArrayList<>();
        }
        return this.itemDefinition;
    }

    public List<TDRGElement> getDrgElement() {
        if (drgElement == null) {
            drgElement = new ArrayList<>();
        }
        return this.drgElement;
    }

    public List<TArtifact> getArtifact() {
        if (artifact == null) {
            artifact = new ArrayList<>();
        }
        return this.artifact;
    }

    public List<TElementCollection> getElementCollection() {
        if (elementCollection == null) {
            elementCollection = new ArrayList<>();
        }
        return this.elementCollection;
    }

    public List<TBusinessContextElement> getBusinessContextElement() {
        if (businessContextElement == null) {
            businessContextElement = new ArrayList<>();
        }
        return this.businessContextElement;
    }

    public DMNDI getDMNDI() {
        return dmndi;
    }

    public void setDMNDI(DMNDI value) {
        this.dmndi = value;
    }

    public String getExpressionLanguage() {
        return expressionLanguage;
    }

    public void setExpressionLanguage(String value) {
        this.expressionLanguage = value;
    }

    public String getTypeLanguage() {
        return typeLanguage;
    }

    public void setTypeLanguage(String value) {
        this.typeLanguage = value;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String value) {
        this.namespace = value;
    }

    public String getExporter() {
        return exporter;
    }

    public void setExporter(String value) {
        this.exporter = value;
    }

    public String getExporterVersion() {
        return exporterVersion;
    }

    public void setExporterVersion(String value) {
        this.exporterVersion = value;
    }

    @Override
    public <C, R> R accept(Visitor<C, R> visitor, C context) {
        return visitor.visit(this, context);
    }
}
