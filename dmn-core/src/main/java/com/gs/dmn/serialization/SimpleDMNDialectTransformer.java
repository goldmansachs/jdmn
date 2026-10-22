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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public abstract class SimpleDMNDialectTransformer {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SimpleDMNDialectTransformer.class);

    protected final BuildLogger logger;
    protected final ErrorHandler errorHandler;
    protected final DMNVersion sourceVersion;
    protected final DMNVersion targetVersion;
    protected final DMNVersionTransformerVisitor<?> visitor;

    public SimpleDMNDialectTransformer(BuildLogger logger, DMNVersion sourceVersion, DMNVersion targetVersion) {
        this.logger = logger;
        this.errorHandler =  new LogErrorHandler(LOGGER);
        this.sourceVersion = sourceVersion;
        this.targetVersion = targetVersion;
        this.visitor = new DMNVersionTransformerVisitor<>(logger, this.errorHandler, sourceVersion, targetVersion);
    }

    public TDefinitions transformDefinitions(TDefinitions sourceDefinitions) {
        if (this.sourceVersion != this.targetVersion) {
            this.logger.info(String.format("Transforming '%s' from DMN %s to DMN %s ...", sourceDefinitions.getName(), this.sourceVersion.getVersion(), this.targetVersion.getVersion()));
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
        element = (TDefinitions) super.visit(element, context);

        if (element != null) {
            // Remove 'schemaLocation'
            if (this.sourceVersion != DMNVersion.LATEST) {
                element.getOtherAttributes().remove(SCHEMA_LOCATION);
                element.getOtherAttributes().remove(NO_NAMESPACE_SCHEMA_LOCATION);
            }
            // Update expression language
            if (isFEELNamespace(element.getTypeLanguage())) {
                element.setTypeLanguage(this.targetVersion.getFeelNamespace());
            }
            if (isFEELNamespace(element.getExpressionLanguage())) {
                element.setExpressionLanguage(this.targetVersion.getFeelNamespace());
            }
        }

        return element;
    }

    // Import
    @Override
    public DMNBaseElement visit(TImport element, C context) {
        element = (TImport) super.visit(element, context);

        if (element != null) {
            updateImportType(element);
        }

        return element;
    }

    private void updateImportType(TImport element) {
        String importType = element.getImportType();
        if (isDMNNamespace(importType)) {
            element.setImportType(targetVersion.getNamespace());
        }
        if (isFEELNamespace(importType)) {
            element.setImportType(targetVersion.getFeelNamespace());
        }
    }

    @Override
    public DMNBaseElement visit(TImportedValues element, C context) {
        element = (TImportedValues) super.visit(element, context);

        if (element != null) {
            if (isFEELNamespace(element.getExpressionLanguage())) {
                element.setExpressionLanguage(this.targetVersion.getFeelNamespace());
            }
        }

        return element;
    }

    @Override
    public DMNBaseElement visit(TLiteralExpression  element, C context) {
        element = (TLiteralExpression) super.visit(element, context);

        if (element != null) {
            if (isFEELNamespace(element.getExpressionLanguage())) {
                element.setExpressionLanguage(this.targetVersion.getFeelNamespace());
            }
        }

        return element;
    }

    @Override
    public DMNBaseElement visit(TUnaryTests  element, C context) {
        element = (TUnaryTests) super.visit(element, context);

        if (element != null) {
            if (isFEELNamespace(element.getExpressionLanguage())) {
                element.setExpressionLanguage(this.targetVersion.getFeelNamespace());
            }
        }

        return element;
    }

    @Override
    public DMNBaseElement visit(TInformationRequirement  element, C context) {
        element = (TInformationRequirement) super.visit(element, context);

        if (element != null) {
            if (sourceVersion == DMNVersion.DMN_11) {
                // DMN 1.1
                // An InformationRequirement does not have an ID, is needed for edges in diagrams.
                if (StringUtils.isBlank(element.getId())) {
                    String id = makeXsdId(element);
                    element.setId(id);
                }
            }
        }

        return element;
    }

    //
    // DMN properties
    //
    @Override
    protected void visitBaseElementProperties(DMNBaseElement element, C context) {
        updateXMLNamespaces(element);
    }

    @Override
    protected QName visitTypeRef(QName typeRef, C context) {
        if (typeRef != null) {
            if (this.sourceVersion == DMNVersion.DMN_11) {
                // DMN 1.1
                // An ItemDefinition element SHALL have a typeRef, which is a QName that references, by namespace prefix and
                // local name, either an ItemDefinition in the current instance of Definitions or a built-in type in the specified
                // typeLanguage or a type defined in an imported DMN, XSD, or other document. In the latter case, the external
                // document SHALL be imported in the Definitions element that contains the instance of ItemDefinition, using
                // an Import element. For example, in the case of data structures contributed by an XML schema, an Import would be
                // used to specify the file location of that schema, and the typeRef attribute would reference the type or element definition
                // in the imported schema. If the type language is FEEL the built-in types are the FEEL built-in data types: number, string,
                // boolean, days and time duration, years and months duration, time, and date and time.

                // DMN 1.2
                // An ItemDefinition element MAY have a typeRef, which is a string that references, as a qualified name, either an
                // ItemDefinition in the current instance of Definitions or a built-in type in the specified typeLanguage or a
                // type defined in an imported DMN, XSD, or other document. In the latter case, the external document SHALL be
                // imported in the Definitions element that contains the instance of ItemDefinition, using an Import element
                // specifying both the namespace value and its name when used a qualifier. For example, in the case of data structures
                // contributed by an XML schema, an Import would be used to specify the file location of that schema, and the typeRef
                // attribute would reference the type or element definition in the imported schema. If the type language is FEEL the built-in
                // types are the FEEL built-in data types: number, string, boolean, days and time duration, years and months duration, time,
                // and date and time. A typeRef referencing a built-in type SHALL omit the prefix.
                String typeName = typeRef.getLocalPart();
                if (isFEELQName(typeRef)) {
                    if (isDefinedInCurrentModel(typeName)) {
                        // Keep the feel prefix to avoid conflicts
                        return new QName(String.format("%s.%s", this.targetVersion.getFeelPrefix(), typeName));
                    } else {
                        // Remove the prefix for FEEL types
                        return new QName(typeName);
                    }
                } else {
                    // Type reference via XML namespace or imports
                    if (isImported(typeRef)) {
                        // Imported definition
                        return typeRef;
                    } else {
                        // Local definition
                        return new QName(typeName);
                    }
                }
            } else {
                // Remove the prefix for FEEL types, prefix is the same for all DMN versions
                String localPart = typeRef.getLocalPart();
                if (startsWithFEELPrefix(localPart, this.targetVersion)) {
                    String typeName = localPart.substring(this.targetVersion.getFeelPrefix().length() + 1);
                    if (isDefinedInCurrentModel(typeName)) {
                        // Keep the feel prefix to avoid conflicts
                        return typeRef;
                    } else {
                        return new QName(typeName);
                    }
                }
            }
        }
        return typeRef;
    }

    private boolean isFEELQName(QName typeRef) {
        // Namespace is defined in <definitions> or an enclosing tag e.g. <typeRef>
        String namespaceURI = typeRef.getNamespaceURI();
        if (StringUtils.isBlank(namespaceURI)) {
            // Namespace is defined in <definitions>
            String prefix = typeRef.getPrefix();
            namespaceURI = this.definitions.getNamespaceURI(prefix);
        }
        return isFEELNamespace(namespaceURI);
    }

    private boolean isDefinedInCurrentModel(String name) {
        for (TItemDefinition itemDefinition : this.definitions.getItemDefinition()) {
            if (itemDefinition.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private boolean isImported(QName typeRef) {
        for (TImport import_ : this.definitions.getImport()) {
            String namespaceURI = typeRef.getNamespaceURI();
            String prefix = typeRef.getPrefix();
            if (import_.getNamespace().equals(namespaceURI) || import_.getName().equals(prefix)) {
                return true;
            }
        }
        return false;
    }

    private boolean startsWithFEELPrefix(String localPart, DMNVersion version) {
        return localPart.startsWith(version.getFeelPrefix() + ".");
    }

    private void updateXMLNamespaces(DMNBaseElement element) {
        if (element == null || element.getElementInfo() == null) {
            return;
        }

        ElementInfo elementInfo = element.getElementInfo();
        // DMN namespace
        String newNamespaceURI = elementInfo.getNamespaceURI();
        if (isDMNNamespace(elementInfo.getNamespaceURI())) {
            newNamespaceURI = this.targetVersion.getNamespace();
        }
        Map<String, String> newNsContext = new LinkedHashMap<>(elementInfo.getNsContext());
        for (Map.Entry<String, String> entry : newNsContext.entrySet()) {
            // DMN namespace
            if (isDMNNamespace(entry.getValue())) {
                entry.setValue(this.targetVersion.getNamespace());
            // FEEL namespace
            } else if (isFEELNamespace(entry.getValue())) {
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

    // Include all version to recover from errors when the namespaces do not match the sourceVersion
    private boolean isDMNNamespace(String namespace) {
        return DMNVersion.VERSIONS.stream().anyMatch(v -> v.getNamespace().equals(namespace));
    }

    // Include all version to recover from errors when the namespaces do not match the sourceVersion
    private boolean isFEELNamespace(String namespace) {
        return DMNVersion.VERSIONS.stream().anyMatch(v -> v.getFeelNamespace().equals(namespace));
    }

    private String makeXsdId(TInformationRequirement element) {
        DMNBaseElement parent = element.getParent();
        String value = parent != null ? ((TDMNElement) parent).getId() : "";
        value += element.getRequiredInput() != null ? element.getRequiredInput().getHref() : element.getRequiredDecision().getHref();
        return "_" + UUID.nameUUIDFromBytes(value.getBytes());
    }
}
