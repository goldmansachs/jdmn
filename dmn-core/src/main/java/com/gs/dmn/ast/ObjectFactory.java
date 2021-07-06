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

import com.gs.dmn.ast.dmndi.*;

import javax.xml.namespace.QName;

public class ObjectFactory {
    private final static QName _DMNElement_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "DMNElement");
    private final static QName _NamedElement_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "namedElement");
    private final static QName _Definitions_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "definitions");
    private final static QName _Import_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "import");
    private final static QName _ElementCollection_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "elementCollection");
    private final static QName _DrgElement_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "drgElement");
    private final static QName _Decision_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "decision");
    private final static QName _BusinessContextElement_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "businessContextElement");
    private final static QName _PerformanceIndicator_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "performanceIndicator");
    private final static QName _OrganizationUnit_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "organizationUnit");
    private final static QName _Invocable_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "invocable");
    private final static QName _BusinessKnowledgeModel_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "businessKnowledgeModel");
    private final static QName _InputData_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "inputData");
    private final static QName _KnowledgeSource_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "knowledgeSource");
    private final static QName _InformationRequirement_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "informationRequirement");
    private final static QName _KnowledgeRequirement_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "knowledgeRequirement");
    private final static QName _AuthorityRequirement_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "authorityRequirement");
    private final static QName _Expression_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "expression");
    private final static QName _ItemDefinition_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "itemDefinition");
    private final static QName _FunctionItem_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "functionItem");
    private final static QName _LiteralExpression_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "literalExpression");
    private final static QName _Invocation_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "invocation");
    private final static QName _InformationItem_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "informationItem");
    private final static QName _DecisionTable_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "decisionTable");
    private final static QName _Artifact_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "artifact");
    private final static QName _Group_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "group");
    private final static QName _TextAnnotation_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "textAnnotation");
    private final static QName _Association_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "association");
    private final static QName _Context_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "context");
    private final static QName _ContextEntry_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "contextEntry");
    private final static QName _FunctionDefinition_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "functionDefinition");
    private final static QName _Relation_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "relation");
    private final static QName _List_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "list");
    private final static QName _DecisionService_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/MODEL/", "decisionService");
    private final static QName _DMNDI_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/DMNDI/", "DMNDI");
    private final static QName _DMNDiagram_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/DMNDI/", "DMNDiagram");
    private final static QName _DMNDiagramElement_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/DMNDI/", "DMNDiagramElement");
    private final static QName _DMNShape_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/DMNDI/", "DMNShape");
    private final static QName _DMNEdge_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/DMNDI/", "DMNEdge");
    private final static QName _DMNStyle_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/DMNDI/", "DMNStyle");
    private final static QName _Style_QNAME = new QName("http://www.omg.org/spec/DMN/20180521/DI/", "Style");
    private final static QName _DMNLabel_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/DMNDI/", "DMNLabel");
    private final static QName _DMNDecisionServiceDividerLine_QNAME = new QName("https://www.omg.org/spec/DMN/20191111/DMNDI/", "DMNDecisionServiceDividerLine");
    private final static QName _Color_QNAME = new QName("http://www.omg.org/spec/DMN/20180521/DC/", "Color");
    private final static QName _Point_QNAME = new QName("http://www.omg.org/spec/DMN/20180521/DC/", "Point");
    private final static QName _Bounds_QNAME = new QName("http://www.omg.org/spec/DMN/20180521/DC/", "Bounds");
    private final static QName _Dimension_QNAME = new QName("http://www.omg.org/spec/DMN/20180521/DC/", "Dimension");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.omg.spec.dmn._20191111.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TDefinitions }
     * 
     */
    public TDefinitions createTDefinitions() {
        return new TDefinitions();
    }

    /**
     * Create an instance of {@link TImport }
     * 
     */
    public TImport createTImport() {
        return new TImport();
    }

    /**
     * Create an instance of {@link TElementCollection }
     * 
     */
    public TElementCollection createTElementCollection() {
        return new TElementCollection();
    }

    /**
     * Create an instance of {@link TDecision }
     * 
     */
    public TDecision createTDecision() {
        return new TDecision();
    }

    /**
     * Create an instance of {@link TBusinessContextElement }
     * 
     */
    public TBusinessContextElement createTBusinessContextElement() {
        return new TBusinessContextElement();
    }

    /**
     * Create an instance of {@link TPerformanceIndicator }
     * 
     */
    public TPerformanceIndicator createTPerformanceIndicator() {
        return new TPerformanceIndicator();
    }

    /**
     * Create an instance of {@link TOrganizationUnit }
     * 
     */
    public TOrganizationUnit createTOrganizationUnit() {
        return new TOrganizationUnit();
    }

    /**
     * Create an instance of {@link TBusinessKnowledgeModel }
     * 
     */
    public TBusinessKnowledgeModel createTBusinessKnowledgeModel() {
        return new TBusinessKnowledgeModel();
    }

    /**
     * Create an instance of {@link TInputData }
     * 
     */
    public TInputData createTInputData() {
        return new TInputData();
    }

    /**
     * Create an instance of {@link TKnowledgeSource }
     * 
     */
    public TKnowledgeSource createTKnowledgeSource() {
        return new TKnowledgeSource();
    }

    /**
     * Create an instance of {@link TInformationRequirement }
     * 
     */
    public TInformationRequirement createTInformationRequirement() {
        return new TInformationRequirement();
    }

    /**
     * Create an instance of {@link TKnowledgeRequirement }
     * 
     */
    public TKnowledgeRequirement createTKnowledgeRequirement() {
        return new TKnowledgeRequirement();
    }

    /**
     * Create an instance of {@link TAuthorityRequirement }
     * 
     */
    public TAuthorityRequirement createTAuthorityRequirement() {
        return new TAuthorityRequirement();
    }

    /**
     * Create an instance of {@link TItemDefinition }
     * 
     */
    public TItemDefinition createTItemDefinition() {
        return new TItemDefinition();
    }

    /**
     * Create an instance of {@link TFunctionItem }
     * 
     */
    public TFunctionItem createTFunctionItem() {
        return new TFunctionItem();
    }

    /**
     * Create an instance of {@link TLiteralExpression }
     * 
     */
    public TLiteralExpression createTLiteralExpression() {
        return new TLiteralExpression();
    }

    /**
     * Create an instance of {@link TInvocation }
     * 
     */
    public TInvocation createTInvocation() {
        return new TInvocation();
    }

    /**
     * Create an instance of {@link TInformationItem }
     * 
     */
    public TInformationItem createTInformationItem() {
        return new TInformationItem();
    }

    /**
     * Create an instance of {@link TDecisionTable }
     * 
     */
    public TDecisionTable createTDecisionTable() {
        return new TDecisionTable();
    }

    /**
     * Create an instance of {@link TGroup }
     * 
     */
    public TGroup createTGroup() {
        return new TGroup();
    }

    /**
     * Create an instance of {@link TTextAnnotation }
     * 
     */
    public TTextAnnotation createTTextAnnotation() {
        return new TTextAnnotation();
    }

    /**
     * Create an instance of {@link TAssociation }
     * 
     */
    public TAssociation createTAssociation() {
        return new TAssociation();
    }

    /**
     * Create an instance of {@link TContext }
     * 
     */
    public TContext createTContext() {
        return new TContext();
    }

    /**
     * Create an instance of {@link TContextEntry }
     * 
     */
    public TContextEntry createTContextEntry() {
        return new TContextEntry();
    }

    /**
     * Create an instance of {@link TFunctionDefinition }
     * 
     */
    public TFunctionDefinition createTFunctionDefinition() {
        return new TFunctionDefinition();
    }

    /**
     * Create an instance of {@link TRelation }
     * 
     */
    public TRelation createTRelation() {
        return new TRelation();
    }

    /**
     * Create an instance of {@link TList }
     * 
     */
    public TList createTList() {
        return new TList();
    }

    /**
     * Create an instance of {@link TDecisionService }
     * 
     */
    public TDecisionService createTDecisionService() {
        return new TDecisionService();
    }

    /**
     * Create an instance of {@link TDMNElementReference }
     * 
     */
    public TDMNElementReference createTDMNElementReference() {
        return new TDMNElementReference();
    }

    /**
     * Create an instance of {@link TBinding }
     * 
     */
    public TBinding createTBinding() {
        return new TBinding();
    }

    /**
     * Create an instance of {@link TInputClause }
     * 
     */
    public TInputClause createTInputClause() {
        return new TInputClause();
    }

    /**
     * Create an instance of {@link TOutputClause }
     * 
     */
    public TOutputClause createTOutputClause() {
        return new TOutputClause();
    }

    /**
     * Create an instance of {@link TRuleAnnotationClause }
     * 
     */
    public TRuleAnnotationClause createTRuleAnnotationClause() {
        return new TRuleAnnotationClause();
    }

    /**
     * Create an instance of {@link TDecisionRule }
     * 
     */
    public TDecisionRule createTDecisionRule() {
        return new TDecisionRule();
    }

    /**
     * Create an instance of {@link TRuleAnnotation }
     * 
     */
    public TRuleAnnotation createTRuleAnnotation() {
        return new TRuleAnnotation();
    }

    /**
     * Create an instance of {@link TImportedValues }
     * 
     */
    public TImportedValues createTImportedValues() {
        return new TImportedValues();
    }

    /**
     * Create an instance of {@link TUnaryTests }
     * 
     */
    public TUnaryTests createTUnaryTests() {
        return new TUnaryTests();
    }

    /**
     * Create an instance of {@link DMNDI }
     * 
     */
    public DMNDI createDMNDI() {
        return new DMNDI();
    }

    /**
     * Create an instance of {@link DMNDiagram }
     * 
     */
    public DMNDiagram createDMNDiagram() {
        return new DMNDiagram();
    }

    /**
     * Create an instance of {@link DMNShape }
     * 
     */
    public DMNShape createDMNShape() {
        return new DMNShape();
    }

    /**
     * Create an instance of {@link DMNEdge }
     * 
     */
    public DMNEdge createDMNEdge() {
        return new DMNEdge();
    }

    /**
     * Create an instance of {@link DMNStyle }
     * 
     */
    public DMNStyle createDMNStyle() {
        return new DMNStyle();
    }

    /**
     * Create an instance of {@link DMNLabel }
     * 
     */
    public DMNLabel createDMNLabel() {
        return new DMNLabel();
    }

    /**
     * Create an instance of {@link DMNDecisionServiceDividerLine }
     * 
     */
    public DMNDecisionServiceDividerLine createDMNDecisionServiceDividerLine() {
        return new DMNDecisionServiceDividerLine();
    }

    /**
     * Create an instance of {@link Color }
     * 
     */
    public Color createColor() {
        return new Color();
    }

    /**
     * Create an instance of {@link Point }
     * 
     */
    public Point createPoint() {
        return new Point();
    }

    /**
     * Create an instance of {@link Bounds }
     * 
     */
    public Bounds createBounds() {
        return new Bounds();
    }

    /**
     * Create an instance of {@link Dimension }
     * 
     */
    public Dimension createDimension() {
        return new Dimension();
    }

    /**
     * Create an instance of {@link DiagramElement.Extension }
     * 
     */
    public DiagramElement.Extension createDiagramElementExtension() {
        return new DiagramElement.Extension();
    }

    /**
     * Create an instance of {@link Style.Extension }
     * 
     */
    public Style.Extension createStyleExtension() {
        return new Style.Extension();
    }

    /**
     * Create an instance of {@link TDMNElement.ExtensionElements }
     * 
     */
    public TDMNElement.ExtensionElements createTDMNElementExtensionElements() {
        return new TDMNElement.ExtensionElements();
    }
}
