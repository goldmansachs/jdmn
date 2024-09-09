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
package com.gs.dmn.serialization;

import com.gs.dmn.ast.*;
import com.gs.dmn.ast.dmndi.*;
import com.gs.dmn.ast.visitor.TraversalVisitor;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.error.LogErrorHandler;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.serialization.xstream.dom.ElementInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SimpleDMNDialectTransformer {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SimpleDMNDialectTransformer.class);

    protected final BuildLogger logger;
    protected final ErrorHandler errorHandler;
    protected final DMNVersion sourceVersion;
    protected final DMNVersion targetVersion;
    protected final DMNVersionTransformerVisitor visitor;

    public SimpleDMNDialectTransformer(BuildLogger logger, DMNVersion sourceVersion, DMNVersion targetVersion) {
        this.logger = logger;
        this.errorHandler =  new LogErrorHandler(LOGGER);
        this.sourceVersion = sourceVersion;
        this.targetVersion = targetVersion;
        this.visitor = new DMNVersionTransformerVisitor(logger, this.errorHandler, sourceVersion, targetVersion);
    }

    public TDefinitions transformDefinitions(TDefinitions sourceDefinitions) {
        this.logger.info(String.format("Transforming '%s' from DMN %s to DMN %s ...", sourceDefinitions.getName(), this.sourceVersion.getVersion(), this.targetVersion.getVersion()));
        if (this.sourceVersion != this.targetVersion) {
            sourceDefinitions.accept(this.visitor, null);
        }
        return sourceDefinitions;
    }
}

class DMNVersionTransformerVisitor<C> extends TraversalVisitor<C> {
    private static final QName SCHEMA_LOCATION = new QName(DMNConstants.XSI_NS, "schemaLocation", DMNConstants.XSI_PREFIX);
    private static final QName NO_NAMESPACE_SCHEMA_LOCATION = new QName(DMNConstants.XSI_NS, "noNamespaceSchemaLocation", DMNConstants.XSI_PREFIX);

    private final DMNVersion sourceVersion;
    private final DMNVersion targetVersion;
    private TDefinitions definitions;

    public DMNVersionTransformerVisitor(BuildLogger logger, ErrorHandler errorHandler, DMNVersion sourceVersion, DMNVersion targetVersion) {
        super(logger, errorHandler);
        this.sourceVersion = sourceVersion;
        this.targetVersion = targetVersion;
    }

    //
    // DMN Elements
    //
    // Definitions
    @Override
    public DMNBaseElement visit(TDefinitions element, C context) {
        this.definitions = element;
        // Remove 'schemaLocation'
        if (this.sourceVersion != DMNVersion.LATEST) {
            element.getOtherAttributes().remove(SCHEMA_LOCATION);
            element.getOtherAttributes().remove(NO_NAMESPACE_SCHEMA_LOCATION);
        }
        // Update expression language
        if (this.sourceVersion.getFeelNamespace().equals(element.getTypeLanguage())) {
            element.setTypeLanguage(this.targetVersion.getFeelNamespace());
        }
        if (this.sourceVersion.getFeelNamespace().equals(element.getExpressionLanguage())) {
            element.setExpressionLanguage(this.targetVersion.getFeelNamespace());
        }
        // Update XML namespaces
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    // Import
    @Override
    public DMNBaseElement visit(TImport element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TImportedValues element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    // Data types
    @Override
    public DMNBaseElement visit(TItemDefinition  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TFunctionItem  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    // DRG Elements
    @Override
    public DMNBaseElement visit(TInputData  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TDecision  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TBusinessKnowledgeModel  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TDecisionService  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TKnowledgeSource  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    // Expressions
    @Override
    public DMNBaseElement visit(TContext  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TContextEntry  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TDecisionTable  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TInputClause  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TOutputClause  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TRuleAnnotationClause  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TDecisionRule  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TRuleAnnotation  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TFunctionDefinition  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TInvocation  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TBinding  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TList  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TLiteralExpression  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TRelation  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TUnaryTests  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TConditional  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TFor  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TFilter  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TEvery  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TSome  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TChildExpression  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TTypedChildExpression  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    // Requirements
    @Override
    public DMNBaseElement visit(TAuthorityRequirement  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TInformationRequirement  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TKnowledgeRequirement  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TInformationItem  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TDMNElementReference  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    // Artifacts
    @Override
    public DMNBaseElement visit(TAssociation  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TGroup  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TTextAnnotation  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    // Other
    @Override
    public DMNBaseElement visit(TBusinessContextElement  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TPerformanceIndicator  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TOrganizationUnit  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TElementCollection  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    // Extensions
    @Override
    public DMNBaseElement visit(TDMNElement.ExtensionElements element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    //
    // DMNDI elements
    //
    @Override
    public DMNBaseElement visit(DMNDI  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(DMNDiagram  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(DMNShape  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(DMNEdge  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(DMNStyle  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(DMNLabel  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(DMNDecisionServiceDividerLine  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(Color  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(Point  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(Bounds  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(Dimension  element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(DiagramElement.Extension element, C context) {
        updateXMLNamespaces(element);

        super.visit(element, context);
        return element;
    }

    @Override
    public Object visit(Style.Extension element, C context) {
        return element;
    }


    @Override
    protected DMNBaseElement visitExtensions(TDMNElement.ExtensionElements element, C context) {
        updateXMLNamespaces(element);

        return element;
    }

    @Override
    protected QName visitTypeRef(QName typeRef, C context) {
        if (this.sourceVersion == DMNVersion.DMN_11) {
            if (typeRef != null) {
                String namespaceURI = typeRef.getNamespaceURI();
                String prefix = typeRef.getPrefix();
                String nsForPrefix = this.definitions.getNamespaceURI(prefix);
                String localPart = typeRef.getLocalPart();
                if (this.sourceVersion.getFeelNamespace().equals(namespaceURI) || this.targetVersion.getFeelNamespace().equals(nsForPrefix)) {
                    return new QName(String.format("%s.%s", this.targetVersion.getFeelPrefix(), localPart));
                } else {
                    return new QName(typeRef.getLocalPart());
                }
            }
        }
        return typeRef;
    }

    private void updateXMLNamespaces(DMNBaseElement element) {
        if (element == null || element.getElementInfo() == null) {
            return;
        }

        ElementInfo elementInfo = element.getElementInfo();
        // DMN namespace
        String newNamespaceURI = elementInfo.getNamespaceURI();
        if (this.sourceVersion.getNamespace().equals(elementInfo.getNamespaceURI())) {
            newNamespaceURI = this.targetVersion.getNamespace();
        }
        Map<String, String> newNsContext = new LinkedHashMap<>(elementInfo.getNsContext());
        for (Map.Entry<String, String> entry : newNsContext.entrySet()) {
            // DMN namespace
            if (this.sourceVersion.getNamespace().equals(entry.getValue())) {
                entry.setValue(this.targetVersion.getNamespace());
            // FEEL namespace
            } else if (this.sourceVersion.getFeelNamespace().equals(entry.getValue())) {
                entry.setValue(this.targetVersion.getFeelNamespace());
            }
        }
        // Add missing DMNDI namespaces
        if (this.sourceVersion == DMNVersion.DMN_11) {
            for (Map.Entry<String, String> entry : this.targetVersion.getPrefixToNamespaceMap().entrySet()) {
                String key = entry.getKey();
                if ("dmndi".equals(key) || "di".equals(key) || "dc".equals(key)) {
                    newNsContext.put(key, entry.getValue());
                }
            }
        }

        ElementInfo newElementInfo = new ElementInfo(elementInfo.getLocation(), elementInfo.getPrefix(), newNamespaceURI, newNsContext);
        element.setElementInfo(newElementInfo);
    }
}
