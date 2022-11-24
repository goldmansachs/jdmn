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
package com.gs.dmn.serialization.xstream.v1_3;

import com.gs.dmn.ast.*;
import com.gs.dmn.ast.dmndi.*;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.xstream.*;
import com.gs.dmn.serialization.xstream.v1_1.DMNElementConverter;
import com.gs.dmn.serialization.xstream.v1_1.QNameConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.AbstractPullReader;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.io.xml.StaxWriter;
import com.thoughtworks.xstream.security.TypeHierarchyPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XStreamMarshaller implements SimpleDMNMarshaller {
    private static final Logger LOGGER = LoggerFactory.getLogger(XStreamMarshaller.class);

    private static final StaxDriver STAX_DRIVER;

    static {
        STAX_DRIVER = new StaxDriver() {
            @Override
            public AbstractPullReader createStaxReader(XMLStreamReader in) {
                return new CustomStaxReader(getQnameMap(), in);
            }

            @Override
            public StaxWriter createStaxWriter(XMLStreamWriter out, boolean writeStartEndDocument) throws XMLStreamException {
                return new CustomStaxWriter(newQNameMap(), out, false, isRepairingNamespace(), getNameCoder());
            }

            public QNameMap newQNameMap() {
                QNameMap qmap = new QNameMap();
                configureQNameMap(qmap);
                return qmap;
            }
        };
        QNameMap nameMap = new QNameMap();
        configureQNameMap(nameMap);
        STAX_DRIVER.setQnameMap(nameMap);
        STAX_DRIVER.setRepairingNamespace(false);
    }

    public static void configureQNameMap(QNameMap nameMap) {
        nameMap.setDefaultNamespace(DMNVersion.DMN_13.getNamespace());
    }

    private final List<DMNExtensionRegister> extensionRegisters = new ArrayList<>();

    public XStreamMarshaller() {
    }

    public XStreamMarshaller(List<DMNExtensionRegister> extensionRegisters) {
        this.extensionRegisters.addAll(extensionRegisters);
    }

    @Override
    public Object unmarshal(String input) {
        return unmarshal(new StringReader(input));
    }

    @Override
    public Object unmarshal(File input) {
        try {
            XStream xStream = newXStream();
            return xStream.fromXML(input);
        } catch (Exception e) {
            LOGGER.error(String.format("Error unmarshalling DMN model from file '%s'.", input.getAbsolutePath()), e);
        }
        return null;
    }

    @Override
    public Object unmarshal(URL input) {
        try {
            XStream xStream = newXStream();
            return xStream.fromXML(input);
        } catch (Exception e) {
            LOGGER.error(String.format("Error unmarshalling DMN model from file '%s'.", input), e);
        }
        return null;
    }

    @Override
    public Object unmarshal(InputStream input) {
        try {
            XStream xStream = newXStream();
            return xStream.fromXML(input);
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling DMN model from input.", e);
        }
        return null;
    }

    @Override
    public Object unmarshal(Reader input) {
        try {
            XStream xStream = newXStream();
            return xStream.fromXML(input);
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling DMN model from reader.", e);
        }
        return null;
    }

    @Override
    public String marshal(Object o) {
        try (
                Writer writer = new StringWriter();
                CustomStaxWriter hsWriter = (CustomStaxWriter) STAX_DRIVER.createWriter(writer)) {

            XStream xStream = newXStream();
            if (o instanceof DMNBaseElement) {
                DMNBaseElement base = (DMNBaseElement) o;
                String dmnPrefix = base.getElementInfo().getNsContext().entrySet().stream().filter(kv -> DMNVersion.DMN_13.getNamespace().equals(kv.getValue())).findFirst().map(Map.Entry::getKey).orElse("");
                hsWriter.getQNameMap().setDefaultPrefix(dmnPrefix);
            }
            extensionRegisters.forEach(r -> r.beforeMarshal(o, hsWriter.getQNameMap()));
            xStream.marshal(o, hsWriter);
            hsWriter.flush();
            return writer.toString();
        } catch (Exception e) {
            LOGGER.error("Error marshalling DMN model to XML.", e);
        }
        return null;
    }

    @Override
    public void marshal(Object o, File output) {
        try (FileWriter fileWriter = new FileWriter(output)) {
            marshal(o, fileWriter);
        } catch (IOException e) {
            LOGGER.error("Error marshalling DMN model to XML.", e);
        }
    }

    @Override
    public void marshal(Object o, OutputStream output) {
        try (OutputStreamWriter streamWriter = new OutputStreamWriter(output)) {
            marshal(o, streamWriter);
        } catch (Exception e) {
            LOGGER.error("Error marshalling DMN model to XML.", e);
        }
    }

    @Override
    public void marshal(Object o, Writer output) {
        try {
            output.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            output.write(marshal(o));
        } catch (Exception e) {
            LOGGER.error("Error marshalling DMN model to XML.", e);
        }
    }

    private XStream newXStream() {
        XStream xStream = XStreamUtils.createNonTrustingXStream(STAX_DRIVER, TDefinitions.class.getClassLoader(), DMNXStream::from);
        xStream.addPermission(new TypeHierarchyPermission(QName.class));
        xStream.addPermission(new TypeHierarchyPermission(DMNBaseElement.class));

        xStream.alias("artifact", TArtifact.class);
        xStream.alias("definitions", TDefinitions.class);
        xStream.alias("inputData", TInputData.class);
        xStream.alias("decision", TDecision.class);
        xStream.alias("variable", TInformationItem.class);
        xStream.alias("informationRequirement", TInformationRequirement.class);
        xStream.alias("requiredInput", TDMNElementReference.class);
        xStream.alias("literalExpression", TLiteralExpression.class);

        xStream.alias("allowedValues", TUnaryTests.class);
        xStream.alias("artifact", TArtifact.class);
        xStream.alias("association", TAssociation.class);
        xStream.alias("authorityRequirement", TAuthorityRequirement.class);
        xStream.alias("binding", TBinding.class);
        xStream.alias("businessContextElement", TBusinessContextElement.class);
        xStream.alias("businessKnowledgeModel", TBusinessKnowledgeModel.class);
        xStream.alias("column", TInformationItem.class);
        xStream.alias("context", TContext.class);
        xStream.alias("contextEntry", TContextEntry.class);
        xStream.alias("decision", TDecision.class);
        xStream.alias("decisionMade", TDMNElementReference.class);
        xStream.alias("decisionMaker", TDMNElementReference.class);
        xStream.alias("decisionOwned", TDMNElementReference.class);
        xStream.alias("decisionOwner", TDMNElementReference.class);
        xStream.alias("decisionService", TDecisionService.class);
        xStream.alias("decisionTable", TDecisionTable.class);
        xStream.alias("defaultOutputEntry", TLiteralExpression.class);
        xStream.alias("definitions", TDefinitions.class);
        xStream.alias("drgElement", TDMNElementReference.class);
        xStream.alias("elementCollection", TElementCollection.class);
        xStream.alias("encapsulatedDecision", TDMNElementReference.class);
        xStream.alias("encapsulatedLogic", TFunctionDefinition.class);
        xStream.alias("expression", TExpression.class);
        xStream.alias("formalParameter", TInformationItem.class);
        xStream.alias("functionItem", TFunctionItem.class);
        xStream.alias("functionDefinition", TFunctionDefinition.class);
        xStream.alias("group", TGroup.class);
        xStream.alias("impactedPerformanceIndicator", TDMNElementReference.class);
        xStream.alias("impactingDecision", TDMNElementReference.class);
        xStream.alias("import", TImport.class);
        xStream.alias("import", TImport.class);
        xStream.alias("importedElement", String.class);
        xStream.alias("importedValues", TImportedValues.class);
        xStream.alias("informationItem", TInformationItem.class);
        xStream.alias("informationRequirement", TInformationRequirement.class);
        xStream.alias("input", TInputClause.class);
        xStream.alias("inputData", TInputData.class);
        xStream.alias("inputDecision", TDMNElementReference.class);
        xStream.alias("inputEntry", TUnaryTests.class);
        xStream.alias("inputExpression", TLiteralExpression.class);
        xStream.alias("inputValues", TUnaryTests.class);
        xStream.alias("invocation", TInvocation.class);
        xStream.alias("itemComponent", TItemDefinition.class);
        xStream.alias("itemDefinition", TItemDefinition.class);
        xStream.alias("knowledgeRequirement", TKnowledgeRequirement.class);
        xStream.alias("knowledgeSource", TKnowledgeSource.class);
        xStream.alias("literalExpression", TLiteralExpression.class);
        xStream.alias("namedElement", TNamedElement.class);
        xStream.alias("organizationUnit", TOrganizationUnit.class);
        xStream.alias("output", TOutputClause.class);
        xStream.alias("outputDecision", TDMNElementReference.class);
        xStream.alias("outputEntry", TLiteralExpression.class);
        xStream.alias("outputValues", TUnaryTests.class);
        xStream.alias("owner", TDMNElementReference.class);
        xStream.alias("parameter", TInformationItem.class);
        xStream.alias("parameters", TInformationItem.class);
        xStream.alias("performanceIndicator", TPerformanceIndicator.class);
        xStream.alias("relation", TRelation.class);
        xStream.alias("requiredAuthority", TDMNElementReference.class);
        xStream.alias("requiredDecision", TDMNElementReference.class);
        xStream.alias("requiredInput", TDMNElementReference.class);
        xStream.alias("requiredKnowledge", TDMNElementReference.class);
        xStream.alias("rule", TDecisionRule.class);
        xStream.alias("sourceRef", TDMNElementReference.class);
        xStream.alias("supportedObjective", TDMNElementReference.class);
        xStream.alias("targetRef", TDMNElementReference.class);
        xStream.alias("textAnnotation", TTextAnnotation.class);
        xStream.alias("type", String.class);
        xStream.alias("typeRef", QName.class);
        xStream.alias("usingProcess", TDMNElementReference.class);
        xStream.alias("usingTask", TDMNElementReference.class);
        xStream.alias("variable", TInformationItem.class);
        xStream.alias("row", TList.class);
        xStream.alias("list", TList.class);
        xStream.alias("extensionElements", TDMNElement.ExtensionElements.class);

        // Manually imported TEXT = String
        xStream.alias(LiteralExpressionConverter.TEXT, String.class);
        xStream.alias(DecisionConverter.QUESTION, String.class);
        xStream.alias(DecisionConverter.ALLOWED_ANSWERS, String.class);
        xStream.alias(DMNElementConverter.DESCRIPTION, String.class);

        xStream.alias("annotation", TRuleAnnotationClause.class);
        xStream.alias("annotationEntry", TRuleAnnotation.class);
        xStream.registerConverter(new RuleAnnotationClauseConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new RuleAnnotationConverter(xStream, DMNVersion.DMN_13));
        xStream.alias("DMNDI", DMNDI.class);
        xStream.registerConverter(new DMNDIConverter(xStream, DMNVersion.DMN_13));
        xStream.alias("DMNDiagram", DMNDiagram.class);
        xStream.registerConverter(new DMNDiagramConverter(xStream, DMNVersion.DMN_13));
        xStream.alias("DMNStyle", DMNStyle.class);
        xStream.registerConverter(new DMNStyleConverter(xStream, DMNVersion.DMN_13));
        xStream.alias("Size", Dimension.class);
        xStream.registerConverter(new DimensionConverter(xStream, DMNVersion.DMN_13));
        xStream.alias("DMNShape", DMNShape.class);
        xStream.registerConverter(new DMNShapeConverter(xStream, DMNVersion.DMN_13));
        xStream.alias("FillColor", Color.class);
        xStream.alias("StrokeColor", Color.class);
        xStream.alias("FontColor", Color.class);
        xStream.registerConverter(new ColorConverter(xStream, DMNVersion.DMN_13));
        xStream.alias("Bounds", Bounds.class);
        xStream.registerConverter(new BoundsConverter(xStream, DMNVersion.DMN_13));
        xStream.alias("DMNLabel", DMNLabel.class);
        xStream.registerConverter(new DMNLabelConverter(xStream, DMNVersion.DMN_13));
        xStream.alias("DMNEdge", DMNEdge.class);
        xStream.registerConverter(new DMNEdgeConverter(xStream, DMNVersion.DMN_13));
        xStream.alias("DMNDecisionServiceDividerLine", DMNDecisionServiceDividerLine.class);
        xStream.registerConverter(new DMNDecisionServiceDividerLineConverter(xStream, DMNVersion.DMN_13));
        xStream.alias("waypoint", Point.class);
        xStream.registerConverter(new PointConverter(xStream, DMNVersion.DMN_13));
        xStream.alias("extension", DiagramElement.Extension.class);
        xStream.alias(DMNLabelConverter.TEXT, String.class);

        xStream.registerConverter(new AssociationConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new AuthorityRequirementConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new BindingConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new BusinessKnowledgeModelConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new ContextConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new ContextEntryConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new DecisionConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new DecisionRuleConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new DecisionServiceConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new DecisionTableConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new DefinitionsConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new DMNElementReferenceConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new GroupConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new FunctionDefinitionConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new ImportConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new ImportedValuesConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new InformationItemConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new InformationRequirementConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new InputClauseConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new InputDataConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new InvocationConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new ItemDefinitionConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new KnowledgeRequirementConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new KnowledgeSourceConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new LiteralExpressionConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new OrganizationUnitConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new OutputClauseConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new PerformanceIndicatorConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new RelationConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new TextAnnotationConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new UnaryTestsConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new FunctionItemConverter(xStream, DMNVersion.DMN_13));

        xStream.registerConverter(new QNameConverter(DMNVersion.DMN_13));
        xStream.registerConverter(new DMNListConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new ElementCollectionConverter(xStream, DMNVersion.DMN_13));
        xStream.registerConverter(new ExtensionElementsConverter(xStream, DMNVersion.DMN_13, extensionRegisters));
        xStream.registerConverter(new DiagramElementExtensionConverter(xStream, DMNVersion.DMN_13, extensionRegisters));

        for (DMNExtensionRegister extensionRegister : extensionRegisters) {
            extensionRegister.registerExtensionConverters(xStream);
        }

        xStream.ignoreUnknownElements();
        return xStream;
    }
}
