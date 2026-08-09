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
package com.gs.dmn.serialization.xstream.v1_5;

import com.gs.dmn.ast.*;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.xstream.DMNExtensionRegister;
import com.gs.dmn.serialization.xstream.VersionXStreamMarshaller;
import com.gs.dmn.serialization.xstream.v1_1.*;
import com.gs.dmn.serialization.xstream.v1_2.*;
import com.gs.dmn.serialization.xstream.v1_2.AuthorityRequirementConverter;
import com.gs.dmn.serialization.xstream.v1_2.BusinessKnowledgeModelConverter;
import com.gs.dmn.serialization.xstream.v1_2.ContextEntryConverter;
import com.gs.dmn.serialization.xstream.v1_2.DecisionRuleConverter;
import com.gs.dmn.serialization.xstream.v1_2.DecisionServiceConverter;
import com.gs.dmn.serialization.xstream.v1_2.DecisionTableConverter;
import com.gs.dmn.serialization.xstream.v1_2.FunctionDefinitionConverter;
import com.gs.dmn.serialization.xstream.v1_2.ImportConverter;
import com.gs.dmn.serialization.xstream.v1_2.ImportedValuesConverter;
import com.gs.dmn.serialization.xstream.v1_2.InformationRequirementConverter;
import com.gs.dmn.serialization.xstream.v1_2.KnowledgeRequirementConverter;
import com.gs.dmn.serialization.xstream.v1_3.DefinitionsConverter;
import com.gs.dmn.serialization.xstream.v1_3.FunctionItemConverter;
import com.gs.dmn.serialization.xstream.v1_3.GroupConverter;
import com.gs.dmn.serialization.xstream.v1_3.ItemDefinitionConverter;
import com.gs.dmn.serialization.xstream.v1_4.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import javax.xml.namespace.QName;
import java.util.List;

public class XStreamMarshaller extends VersionXStreamMarshaller {
    private static final StaxDriver STAX_DRIVER = makeStaxDriver(DMNVersion.DMN_15);

    public XStreamMarshaller() {
        super(DMNVersion.DMN_15);
    }

    public XStreamMarshaller(List<DMNExtensionRegister> extensionRegisters) {
        super(DMNVersion.DMN_15, extensionRegisters);
    }

    @Override
    protected XStream newXStream() {
        XStream xStream = createXStream();

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
        xStream.registerConverter(new RuleAnnotationClauseConverter(xStream, this.version));
        xStream.registerConverter(new RuleAnnotationConverter(xStream, this.version));

        register13DMNDIParts(xStream);

        xStream.alias("for", TFor.class);
        xStream.alias("every", TEvery.class);
        xStream.alias("some", TSome.class);
        xStream.alias("conditional", TConditional.class);
        xStream.alias("filter", TFilter.class);

        xStream.registerConverter(new AssociationConverter(xStream, this.version));
        xStream.registerConverter(new AuthorityRequirementConverter(xStream, this.version));
        xStream.registerConverter(new BindingConverter(xStream, this.version));
        xStream.registerConverter(new BusinessKnowledgeModelConverter(xStream, this.version));
        xStream.registerConverter(new ContextConverter(xStream, this.version));
        xStream.registerConverter(new ContextEntryConverter(xStream, this.version));
        xStream.registerConverter(new DecisionConverter(xStream, this.version));
        xStream.registerConverter(new DecisionRuleConverter(xStream, this.version));
        xStream.registerConverter(new DecisionServiceConverter(xStream, this.version));
        xStream.registerConverter(new DecisionTableConverter(xStream, this.version));
        xStream.registerConverter(new DefinitionsConverter(xStream, this.version));
        xStream.registerConverter(new DMNElementReferenceConverter(xStream, this.version));
        xStream.registerConverter(new GroupConverter(xStream, this.version));
        xStream.registerConverter(new FunctionDefinitionConverter(xStream, this.version));
        xStream.registerConverter(new ImportConverter(xStream, this.version));
        xStream.registerConverter(new ImportedValuesConverter(xStream, this.version));
        xStream.registerConverter(new InformationItemConverter(xStream, this.version));
        xStream.registerConverter(new InformationRequirementConverter(xStream, this.version));
        xStream.registerConverter(new InputClauseConverter(xStream, this.version));
        xStream.registerConverter(new InputDataConverter(xStream, this.version));
        xStream.registerConverter(new InvocationConverter(xStream, this.version));
        xStream.registerConverter(new ItemDefinitionConverter(xStream, this.version));
        xStream.registerConverter(new KnowledgeRequirementConverter(xStream, this.version));
        xStream.registerConverter(new KnowledgeSourceConverter(xStream, this.version));
        xStream.registerConverter(new LiteralExpressionConverter(xStream, this.version));
        xStream.registerConverter(new OrganizationUnitConverter(xStream, this.version));
        xStream.registerConverter(new OutputClauseConverter(xStream, this.version));
        xStream.registerConverter(new PerformanceIndicatorConverter(xStream, this.version));
        xStream.registerConverter(new RelationConverter(xStream, this.version));
        xStream.registerConverter(new TextAnnotationConverter(xStream, this.version));
        xStream.registerConverter(new UnaryTestsConverter(xStream, this.version));
        xStream.registerConverter(new FunctionItemConverter(xStream, this.version));

        xStream.registerConverter(new ChildExpressionConverter(xStream, this.version));
        xStream.registerConverter(new TypedChildExpressionConverter(xStream, this.version));
        xStream.registerConverter(new ForConverter(xStream, this.version));
        xStream.registerConverter(new EveryConverter(xStream, this.version));
        xStream.registerConverter(new SomeConverter(xStream, this.version));
        xStream.registerConverter(new ConditionalConverter(xStream, this.version));
        xStream.registerConverter(new FilterConverter(xStream, this.version));

        xStream.registerConverter(new QNameConverter(this.version));
        xStream.registerConverter(new DMNListConverter(xStream, this.version));
        xStream.registerConverter(new ElementCollectionConverter(xStream, this.version));
        xStream.registerConverter(new ExtensionElementsConverter(xStream, this.version, extensionRegisters));

        xStream.ignoreUnknownElements();

        registerExtensionConverters(xStream);

        return xStream;
    }

    @Override
    protected HierarchicalStreamDriver getStaxDriver() {
        return STAX_DRIVER;
    }
}
