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

import com.gs.dmn.ast.dmndi.DMNDI;
import com.gs.dmn.serialization.DMNVersion;

import java.util.ArrayList;
import java.util.List;

public class TDefinitions extends TNamedElement {
    private List<TImport> _import;
    private List<TItemDefinition> itemDefinition;
    private List<? extends TDRGElement> drgElement;
    private List<? extends TArtifact> artifact;
    private List<TElementCollection> elementCollection;
    private List<? extends TBusinessContextElement> businessContextElement;
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

    public List<? extends TDRGElement> getDrgElement() {
        if (drgElement == null) {
            drgElement = new ArrayList<>();
        }
        return this.drgElement;
    }

    public List<? extends TArtifact> getArtifact() {
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

    public List<? extends TBusinessContextElement> getBusinessContextElement() {
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
        if (expressionLanguage == null) {
            return DMNVersion.LATEST.getFeelNamespace();
        } else {
            return expressionLanguage;
        }
    }

    public void setExpressionLanguage(String value) {
        this.expressionLanguage = value;
    }

    public String getTypeLanguage() {
        if (typeLanguage == null) {
            return DMNVersion.LATEST.getFeelNamespace();
        } else {
            return typeLanguage;
        }
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
}
