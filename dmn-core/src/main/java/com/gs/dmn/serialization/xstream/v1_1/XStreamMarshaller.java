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
package com.gs.dmn.serialization.xstream.v1_1;

import com.gs.dmn.ast.*;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.serialization.*;
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
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XStreamMarshaller implements DMNMarshaller {
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
                QNameMap nameMap = new QNameMap();
                configureQNameMap(nameMap);
                return nameMap;
            }
        };
        QNameMap nameMap = new QNameMap();
        configureQNameMap(nameMap);
        STAX_DRIVER.setQnameMap(nameMap);
        STAX_DRIVER.setRepairingNamespace(false);
    }

    private static void configureQNameMap(QNameMap nameMap) {
        nameMap.setDefaultNamespace(DMNVersion.DMN_11.getNamespace());
    }

    private final List<DMNExtensionRegister> extensionRegisters = new ArrayList<>();

    public XStreamMarshaller() {
    }

    public XStreamMarshaller(List<DMNExtensionRegister> extensionRegisters) {
        this.extensionRegisters.addAll(extensionRegisters);
    }

    @Override
    public TDefinitions<DMNContext> unmarshal(String xml) {
        return unmarshal(new StringReader(xml));
    }

    @Override
    public TDefinitions<DMNContext> unmarshal(Reader isr) {
        try {
            XStream xStream = newXStream();
            return (TDefinitions<DMNContext>) xStream.fromXML(isr);
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
                String dmnPrefix = base.getNsContext().entrySet().stream().filter(kv -> DMNVersion.DMN_11.getNamespace().equals(kv.getValue())).findFirst().map(Map.Entry::getKey).orElse("");
                hsWriter.getQNameMap().setDefaultPrefix(dmnPrefix);
            }
            this.extensionRegisters.forEach(r -> r.beforeMarshal(o, hsWriter.getQNameMap()));
            xStream.marshal(o, hsWriter);
            hsWriter.flush();
            return writer.toString();
        } catch (Exception e) {
            LOGGER.error("Error marshalling DMN model to XML.", e);
        }
        return null;
    }

    @Override
    public void marshal(Object o, Writer out) {
        try {
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.write(marshal(o));
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
        xStream.alias("importedElement", String.class); // TODO where?
        xStream.alias("importedValues", TImportedValues.class);
        xStream.alias("TInformationItem", TInformationItem.class);
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
        xStream.alias("type", String.class); // TODO where?
        xStream.alias("typeRef", QName.class);
        xStream.alias("usingProcess", TDMNElementReference.class);
        xStream.alias("usingTask", TDMNElementReference.class);
        xStream.alias("variable", TInformationItem.class);
        xStream.alias("row", TList.class);
        xStream.alias("list", TList.class);
        xStream.alias("extensionElements", TDMNElement.ExtensionElements.class);

        // Manually imported TEXT = String
        xStream.alias(LiteralExpressionConverter.TEXT, String.class);
        xStream.alias(TextAnnotationConverter.TEXT, String.class);
        xStream.alias(UnaryTestsConverter.TEXT, String.class);
        xStream.alias(DecisionConverter.QUESTION, String.class);
        xStream.alias(DecisionConverter.ALLOWED_ANSWERS, String.class);
        xStream.alias(DMNElementConverter.DESCRIPTION, String.class);

        xStream.registerConverter(new AssociationConverter(xStream));
        xStream.registerConverter(new AuthorityRequirementConverter(xStream));
        xStream.registerConverter(new BindingConverter(xStream));
        xStream.registerConverter(new BusinessKnowledgeModelConverter(xStream));
        xStream.registerConverter(new ContextConverter(xStream));
        xStream.registerConverter(new ContextEntryConverter(xStream));
        xStream.registerConverter(new DecisionConverter(xStream));
        xStream.registerConverter(new DecisionRuleConverter(xStream));
        xStream.registerConverter(new DecisionServiceConverter(xStream));
        xStream.registerConverter(new DecisionTableConverter(xStream));
        xStream.registerConverter(new DefinitionsConverter(xStream));
        xStream.registerConverter(new DMNElementReferenceConverter(xStream));
        xStream.registerConverter(new FunctionDefinitionConverter(xStream));
        xStream.registerConverter(new ImportConverter(xStream));
        xStream.registerConverter(new ImportedValuesConverter(xStream));
        xStream.registerConverter(new InformationItemConverter(xStream));
        xStream.registerConverter(new InformationRequirementConverter(xStream));
        xStream.registerConverter(new InputClauseConverter(xStream));
        xStream.registerConverter(new InputDataConverter(xStream));
        xStream.registerConverter(new InvocationConverter(xStream));
        xStream.registerConverter(new ItemDefinitionConverter(xStream));
        xStream.registerConverter(new KnowledgeRequirementConverter(xStream));
        xStream.registerConverter(new KnowledgeSourceConverter(xStream));
        xStream.registerConverter(new LiteralExpressionConverter(xStream));
        xStream.registerConverter(new OrganizationUnitConverter(xStream));
        xStream.registerConverter(new OutputClauseConverter(xStream));
        xStream.registerConverter(new PerformanceIndicatorConverter(xStream));
        xStream.registerConverter(new RelationConverter(xStream));
        xStream.registerConverter(new TextAnnotationConverter(xStream));
        xStream.registerConverter(new UnaryTestsConverter(xStream));

        xStream.registerConverter(new QNameConverter());
        xStream.registerConverter(new DMNListConverter(xStream));
        xStream.registerConverter(new ElementCollectionConverter(xStream));
        xStream.registerConverter(new ExtensionElementsConverter(xStream, extensionRegisters));

        xStream.ignoreUnknownElements();

        for (DMNExtensionRegister extensionRegister : extensionRegisters) {
            extensionRegister.registerExtensionConverters(xStream);
        }

        return xStream;
    }
}
