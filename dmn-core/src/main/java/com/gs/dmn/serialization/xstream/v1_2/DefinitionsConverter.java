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
package com.gs.dmn.serialization.xstream.v1_2;

import com.gs.dmn.ast.*;
import com.gs.dmn.ast.dmndi.DMNDI;
import com.gs.dmn.serialization.DMNVersion;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DefinitionsConverter extends NamedElementConverter {
    private static final String EXPRESSION_LANGUAGE = "expressionLanguage";
    private static final String TYPE_LANGUAGE = "typeLanguage";
    private static final String NAMESPACE = "namespace";
    private static final String EXPORTER = "exporter";
    private static final String EXPORTER_VERSION = "exporterVersion";

    public static final String IMPORT = "import";
    public static final String ITEM_DEFINITION = "itemDefinition";
    public static final String DRG_ELEMENT = "drgElement";
    public static final String ARTIFACT = "artifact";
    public static final String ELEMENT_COLLECTION = "elementCollection";
    public static final String BUSINESS_CONTEXT_ELEMENT = "businessContextElement";

    public DefinitionsConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TDefinitions.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TDefinitions();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TDefinitions def = (TDefinitions) parent;
        if (IMPORT.equals(nodeName)) {
            def.getImport().add((TImport) child);
        } else if (ITEM_DEFINITION.equals(nodeName)) {
            def.getItemDefinition().add((TItemDefinition) child);
        } else if (child instanceof TDRGElement) {
            def.getDrgElement().add((TDRGElement) child);
        } else if (child instanceof TArtifact) {
            def.getArtifact().add((TArtifact) child);
        } else if (ELEMENT_COLLECTION.equals(nodeName)) {
            def.getElementCollection().add((TElementCollection) child);
        } else if (child instanceof TBusinessContextElement) {
            def.getBusinessContextElement().add((TBusinessContextElement) child);
        } else if (child instanceof DMNDI) {
            DMNDI dmndi = (DMNDI) child;
            dmndi.normalize();
            def.setDMNDI(dmndi);
        } else {
            super.assignChildElement(def, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        TDefinitions def = (TDefinitions) parent;

        String exprLang = reader.getAttribute(EXPRESSION_LANGUAGE);
        String typeLang = reader.getAttribute(TYPE_LANGUAGE);
        String namespace = reader.getAttribute(NAMESPACE);
        String exporter = reader.getAttribute(EXPORTER);
        String exporterVersion = reader.getAttribute(EXPORTER_VERSION);

        def.setExpressionLanguage(exprLang);
        def.setTypeLanguage(typeLang);
        def.setNamespace(namespace);
        def.setExporter(exporter);
        def.setExporterVersion(exporterVersion);
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        TDefinitions def = (TDefinitions) parent;

        for (TImport i : def.getImport()) {
            writeChildrenNode(writer, context, i, IMPORT);
        }
        for (TItemDefinition id : def.getItemDefinition()) {
            writeChildrenNode(writer, context, id, ITEM_DEFINITION);
        }
        for (TDRGElement e : def.getDrgElement()) {
            String nodeName = DRG_ELEMENT;
            if (e instanceof TBusinessKnowledgeModel) {
                nodeName = "businessKnowledgeModel";
            } else if (e instanceof TDecision) {
                nodeName = "decision";
            } else if (e instanceof TInputData) {
                nodeName = "inputData";
            } else if (e instanceof TKnowledgeSource) {
                nodeName = "knowledgeSource";
            } else if (e instanceof TDecisionService) {
                nodeName = "decisionService";
            }
            writeChildrenNode(writer, context, e, nodeName);
        }
        for (TArtifact a : def.getArtifact()) {
            String nodeName = ARTIFACT;
            if (a instanceof TAssociation) {
                nodeName = "association";
            } else if (a instanceof TTextAnnotation) {
                nodeName = "textAnnotation";
            }
            writeChildrenNode(writer, context, a, nodeName);
        }
        for (TElementCollection ec : def.getElementCollection()) {
            writeChildrenNode(writer, context, ec, ELEMENT_COLLECTION);
        }
        for (TBusinessContextElement bce : def.getBusinessContextElement()) {
            String nodeName = BUSINESS_CONTEXT_ELEMENT;
            if (bce instanceof TOrganizationUnit) {
                nodeName = "organizationUnit";
            } else if (bce instanceof TPerformanceIndicator) {
                nodeName = "performanceIndicator";
            }
            writeChildrenNode(writer, context, bce, nodeName);
        }

        if (def.getDMNDI() != null) {
            writeChildrenNode(writer, context, def.getDMNDI(), "DMNDI");
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        TDefinitions def = (TDefinitions) parent;

        if (def.getExpressionLanguage() != null) {
            writer.addAttribute(EXPRESSION_LANGUAGE, def.getExpressionLanguage());
        }
        if (def.getTypeLanguage() != null) {
            writer.addAttribute(TYPE_LANGUAGE, def.getTypeLanguage());
        }
        if (def.getNamespace() != null) {
            writer.addAttribute(NAMESPACE, def.getNamespace());
        }
        if (def.getExporter() != null) {
            writer.addAttribute(EXPORTER, def.getExporter());
        }
        if (def.getExporterVersion() != null) {
            writer.addAttribute(EXPORTER_VERSION, def.getExporterVersion());
        }
    }
}
