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
package com.gs.dmn.serialization.xstream;

import com.gs.dmn.ast.*;
import com.gs.dmn.ast.dmndi.*;
import com.gs.dmn.serialization.DMNVersion;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class VersionXStreamMarshaller implements SimpleXStreamMarshaller {
    private static final Logger LOGGER = LoggerFactory.getLogger(VersionXStreamMarshaller.class);

    protected static StaxDriver makeStaxDriver(DMNVersion version) {
        StaxDriver driver = new SafeStaxDriver() {
            @Override
            public AbstractPullReader createStaxReader(XMLStreamReader in) {
                return new CustomStaxReader(getQnameMap(), in);
            }

            @Override
            public StaxWriter createStaxWriter(XMLStreamWriter out, boolean writeStartEndDocument) throws XMLStreamException {
                return new CustomStaxWriter(newQNameMap(version), out, false, isRepairingNamespace(), getNameCoder());
            }
        };
        QNameMap nameMap = newQNameMap(version);
        driver.setQnameMap(nameMap);
        driver.setRepairingNamespace(false);

        return driver;
    }

    private static QNameMap newQNameMap(DMNVersion version) {
        QNameMap nameMap = new QNameMap();
        nameMap.setDefaultNamespace(version.getNamespace());
        return nameMap;
    }

    protected final DMNVersion version;
    protected final List<DMNExtensionRegister> extensionRegisters = new ArrayList<>();

    protected VersionXStreamMarshaller(DMNVersion version) {
        this.version = version;
    }

    protected VersionXStreamMarshaller(DMNVersion version, List<DMNExtensionRegister> extensionRegisters) {
        this.version = version;
        this.extensionRegisters.addAll(extensionRegisters);
    }

    @Override
    public String marshal(Object o) {
        try (
                Writer writer = new StringWriter();
                CustomStaxWriter hsWriter = (CustomStaxWriter) getStaxDriver().createWriter(writer)) {

            XStream xStream = newXStream();
            if (o instanceof DMNBaseElement base) {
                String dmnPrefix = base.getElementInfo().getNsContext().entrySet().stream().filter(kv -> version.getNamespace().equals(kv.getValue())).findFirst().map(Map.Entry::getKey).orElse("");
                hsWriter.getQNameMap().setDefaultPrefix(dmnPrefix);
            }
            this.extensionRegisters.forEach(r -> r.beforeMarshal(o, hsWriter.getQNameMap()));
            xStream.marshal(o, hsWriter);
            hsWriter.flush();
            return writer.toString();
        } catch (Exception e) {
            logError("Error marshalling {} to string.", artifactName(), e);
        }
        return null;
    }

    @Override
    public void logError(String message, Object argument1, Exception exception) {
        LOGGER.error(message, argument1, exception);
    }

    @Override
    public void logError(String message, Object argument1, Object argument2, Exception exception) {
        LOGGER.error(message, argument1, argument2, exception);
    }

    @Override
    public String artifactName() {
        return "DMN model";
    }

    protected XStream createXStream() {
        XStream xStream = XStreamUtils.createNonTrustingXStream(getStaxDriver(), TDefinitions.class.getClassLoader(), DMNXStream::from);
        xStream.addPermission(new TypeHierarchyPermission(QName.class));
        xStream.addPermission(new TypeHierarchyPermission(DMNBaseElement.class));
        return xStream;
    }

    protected void registerExtensionConverters(XStream xStream) {
        for (DMNExtensionRegister extensionRegister : extensionRegisters) {
            extensionRegister.registerExtensionConverters(xStream);
        }
    }

    protected void registerCommonAliases(XStream xStream) {
        xStream.alias("artifact", TArtifact.class);
        xStream.alias("definitions", TDefinitions.class);
        xStream.alias("inputData", TInputData.class);
        xStream.alias("decision", TDecision.class);
        xStream.alias("variable", TInformationItem.class);
        xStream.alias("informationRequirement", TInformationRequirement.class);
        xStream.alias("requiredInput", TDMNElementReference.class);

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
        xStream.alias("functionDefinition", TFunctionDefinition.class);
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
        xStream.alias(com.gs.dmn.serialization.xstream.v1_1.LiteralExpressionConverter.TEXT, String.class);
        xStream.alias(com.gs.dmn.serialization.xstream.v1_1.DecisionConverter.QUESTION, String.class);
        xStream.alias(com.gs.dmn.serialization.xstream.v1_1.DecisionConverter.ALLOWED_ANSWERS, String.class);
        xStream.alias(com.gs.dmn.serialization.xstream.v1_1.DMNElementConverter.DESCRIPTION, String.class);
    }

    protected void registerCommon11Converters(XStream xStream) {
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.AssociationConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.BindingConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.ContextConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.DecisionConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.DMNElementReferenceConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.InformationItemConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.InputClauseConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.InputDataConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.InvocationConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.KnowledgeSourceConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.LiteralExpressionConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.OrganizationUnitConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.OutputClauseConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.PerformanceIndicatorConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.RelationConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.TextAnnotationConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.UnaryTestsConverter(xStream, this.version));

        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.QNameConverter(this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.DMNListConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.ElementCollectionConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_1.ExtensionElementsConverter(xStream, this.version, extensionRegisters));
    }

    protected void registerCommon12Converters(XStream xStream) {
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.AuthorityRequirementConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.BusinessKnowledgeModelConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.ContextEntryConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DecisionRuleConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DecisionServiceConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DecisionTableConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.FunctionDefinitionConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.ImportConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.ImportedValuesConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.InformationRequirementConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.KnowledgeRequirementConverter(xStream, this.version));
    }

    protected void registerCommon13Converters(XStream xStream) {
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_3.DefinitionsConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_3.GroupConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_3.ItemDefinitionConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_3.FunctionItemConverter(xStream, this.version));
    }

    protected void registerCommon14Converters(XStream xStream) {
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_4.ChildExpressionConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_4.TypedChildExpressionConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_4.ForConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_4.EveryConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_4.SomeConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_4.ConditionalConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_4.FilterConverter(xStream, this.version));
    }

    protected void registerNew12Parts(XStream xStream) {
        xStream.alias("annotation", TRuleAnnotationClause.class);
        xStream.alias("annotationEntry", TRuleAnnotation.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.RuleAnnotationClauseConverter(xStream, this.version));
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.RuleAnnotationConverter(xStream, this.version));
    }

    protected void registerNew13Parts(XStream xStream) {
        xStream.alias("functionItem", TFunctionItem.class);
        xStream.alias("group", TGroup.class);
        xStream.alias("parameters", TInformationItem.class);
    }

    protected void registerNew14Parts(XStream xStream) {
        xStream.alias("for", TFor.class);
        xStream.alias("every", TEvery.class);
        xStream.alias("some", TSome.class);
        xStream.alias("conditional", TConditional.class);
        xStream.alias("filter", TFilter.class);
    }

    protected void registerCommonDMNDIParts(XStream xStream) {
        xStream.alias("DMNDI", DMNDI.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DMNDIConverter(xStream, this.version));
        xStream.alias("DMNDiagram", DMNDiagram.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DMNDiagramConverter(xStream, this.version));
        xStream.alias("DMNStyle", DMNStyle.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DMNStyleConverter(xStream, this.version));
        xStream.alias("Size", Dimension.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DimensionConverter(xStream, this.version));
        xStream.alias("DMNShape", DMNShape.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DMNShapeConverter(xStream, this.version));
        xStream.alias("FillColor", Color.class);
        xStream.alias("StrokeColor", Color.class);
        xStream.alias("FontColor", Color.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.ColorConverter(xStream, this.version));
        xStream.alias("Bounds", Bounds.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.BoundsConverter(xStream, this.version));
        xStream.alias("DMNLabel", DMNLabel.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DMNLabelConverter(xStream, this.version));
        xStream.alias("DMNEdge", DMNEdge.class);
        xStream.alias("DMNDecisionServiceDividerLine", DMNDecisionServiceDividerLine.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DMNDecisionServiceDividerLineConverter(xStream, this.version));
        xStream.alias("waypoint", Point.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.PointConverter(xStream, this.version));
        xStream.alias("extension", DiagramElement.Extension.class);
        xStream.alias(com.gs.dmn.serialization.xstream.v1_2.DMNLabelConverter.TEXT, String.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DiagramElementExtensionConverter(xStream, this.version, extensionRegisters));
    }

    protected void register13DMNDIParts(XStream xStream) {
        registerCommonDMNDIParts(xStream);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_3.DMNEdgeConverter(xStream, this.version));
    }

    protected abstract HierarchicalStreamDriver getStaxDriver();
}
