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
package com.gs.dmn.ast.visitor;

import com.gs.dmn.ast.*;
import com.gs.dmn.ast.dmndi.*;
import com.gs.dmn.error.NopErrorHandler;
import com.gs.dmn.log.NopBuildLogger;
import com.gs.dmn.serialization.DMNMarshaller;
import com.gs.dmn.serialization.xstream.DMNMarshallerFactory;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertNull;

public class NopVisitorTest {
    private final DMNMarshaller marshaller = DMNMarshallerFactory.newDefaultMarshaller();
    private final Visitor<?, Object> visitor = new NopVisitor<>(new NopBuildLogger(), new NopErrorHandler());

    @Test
    public void visit() throws Exception {
        testVisit("xstream/v1_1/", "0001-input-data-string.dmn");
        testVisit("xstream/v1_2/", "ch11example.dmn");
        testVisit("xstream/v1_3/", "Chapter 11 Example.dmn");
    }

    private void testVisit(String subDir, String xmlFile) throws Exception {
        File baseInputDir = new File("target/test-classes/");

        File inputXMLFile = new File(baseInputDir, subDir + xmlFile);
        TDefinitions definitions = marshaller.unmarshal(new FileReader(inputXMLFile), true);
        Object result = definitions.accept(visitor, null);
        assertNull(result);
    }

    @Test
    public void testVisitTDefinitions() {
        assertNull(visitor.visit((TDefinitions) null, null));
    }

    @Test
    public void testVisitTImport() {
        assertNull(visitor.visit((TImport) null, null));
    }

    @Test
    public void testVisitTImportedValues() {
        assertNull(visitor.visit((TImportedValues) null, null));
    }

    @Test
    public void testVisitTItemDefinition() {
        assertNull(visitor.visit((TItemDefinition) null, null));
    }

    @Test
    public void testVisitTFunctionItem() {
        assertNull(visitor.visit((TFunctionItem) null, null));
    }

    @Test
    public void testVisitTInputData() {
        assertNull(visitor.visit((TInputData) null, null));
    }

    @Test
    public void testVisitTDecision() {
        assertNull(visitor.visit((TDecision) null, null));
    }

    @Test
    public void testVisitTBusinessKnowledgeModel() {
        assertNull(visitor.visit((TBusinessKnowledgeModel) null, null));
    }

    @Test
    public void testVisitTDecisionService() {
        assertNull(visitor.visit((TDecisionService) null, null));
    }

    @Test
    public void testVisitTKnowledgeSource() {
        assertNull(visitor.visit((TKnowledgeSource) null, null));
    }

    @Test
    public void testVisitTContext() {
        assertNull(visitor.visit((TContext) null, null));
    }

    @Test
    public void testVisitTContextEntry() {
        assertNull(visitor.visit((TContextEntry) null, null));
    }

    @Test
    public void testVisitTDecisionTable() {
        assertNull(visitor.visit((TDecisionTable) null, null));
    }

    @Test
    public void testVisitTInputClause() {
        assertNull(visitor.visit((TInputClause) null, null));
    }

    @Test
    public void testVisitTOutputClause() {
        assertNull(visitor.visit((TOutputClause) null, null));
    }

    @Test
    public void testVisitTRuleAnnotationClause() {
        assertNull(visitor.visit((TRuleAnnotationClause) null, null));
    }

    @Test
    public void testVisitTDecisionRule() {
        assertNull(visitor.visit((TDecisionRule) null, null));
    }

    @Test
    public void testVisitTRuleAnnotation() {
        assertNull(visitor.visit((TRuleAnnotation) null, null));
    }

    @Test
    public void testVisitTFunctionDefinition() {
        assertNull(visitor.visit((TFunctionDefinition) null, null));
    }

    @Test
    public void testVisitTInvocation() {
        assertNull(visitor.visit((TInvocation) null, null));
    }

    @Test
    public void testVisitTBinding() {
        assertNull(visitor.visit((TBinding) null, null));
    }

    @Test
    public void testVisitTList() {
        assertNull(visitor.visit((TList) null, null));
    }

    @Test
    public void testVisitTLiteralExpression() {
        assertNull(visitor.visit((TLiteralExpression) null, null));
    }

    @Test
    public void testVisitTRelation() {
        assertNull(visitor.visit((TRelation) null, null));
    }

    @Test
    public void testVisitTUnaryTests() {
        assertNull(visitor.visit((TUnaryTests) null, null));
    }

    @Test
    public void testVisitTConditional() {
        assertNull(visitor.visit((TConditional) null, null));
    }

    @Test
    public void testVisitTFor() {
        assertNull(visitor.visit((TFor) null, null));
    }

    @Test
    public void testVisitTFilter() {
        assertNull(visitor.visit((TFilter) null, null));
    }

    @Test
    public void testVisitTEvery() {
        assertNull(visitor.visit((TEvery) null, null));
    }

    @Test
    public void testVisitTSome() {
        assertNull(visitor.visit((TSome) null, null));
    }

    @Test
    public void testVisitTChildExpression() {
        assertNull(visitor.visit((TChildExpression) null, null));
    }

    @Test
    public void testVisitTTypedChildExpression() {
        assertNull(visitor.visit((TTypedChildExpression) null, null));
    }

    @Test
    public void testVisitTAuthorityRequirement() {
        assertNull(visitor.visit((TAuthorityRequirement) null, null));
    }

    @Test
    public void testVisitTInformationRequirement() {
        assertNull(visitor.visit((TInformationRequirement) null, null));
    }

    @Test
    public void testVisitTKnowledgeRequirement() {
        assertNull(visitor.visit((TKnowledgeRequirement) null, null));
    }

    @Test
    public void testVisitTInformationItem() {
        assertNull(visitor.visit((TInformationItem) null, null));
    }

    @Test
    public void testVisitTDMNElementReference() {
        assertNull(visitor.visit((TDMNElementReference) null, null));
    }

    @Test
    public void testVisitTAssociation() {
        assertNull(visitor.visit((TAssociation) null, null));
    }

    @Test
    public void testVisitTGroup() {
        assertNull(visitor.visit((TGroup) null, null));
    }

    @Test
    public void testVisitTTextAnnotation() {
        assertNull(visitor.visit((TTextAnnotation) null, null));
    }

    @Test
    public void testVisitTBusinessContextElement() {
        assertNull(visitor.visit((TBusinessContextElement) null, null));
    }

    @Test
    public void testVisitTPerformanceIndicator() {
        assertNull(visitor.visit((TPerformanceIndicator) null, null));
    }

    @Test
    public void testVisitTOrganizationUnit() {
        assertNull(visitor.visit((TOrganizationUnit) null, null));
    }

    @Test
    public void testVisitTElementCollection() {
        assertNull(visitor.visit((TElementCollection) null, null));
    }

    @Test
    public void testVisitTDMNElementExtensionElement() {
        assertNull(visitor.visit((TDMNElement.ExtensionElements) null, null));
    }

    @Test
    public void testVisitDMNDI() {
        assertNull(visitor.visit((DMNDI) null, null));
    }

    @Test
    public void testVisitDMNDiagram() {
        assertNull(visitor.visit((DMNDiagram) null, null));
    }

    @Test
    public void testVisitDMNShape() {
        assertNull(visitor.visit((DMNShape) null, null));
    }

    @Test
    public void testVisitDMNEdge() {
        assertNull(visitor.visit((DMNEdge) null, null));
    }

    @Test
    public void testVisitDMNStyle() {
        assertNull(visitor.visit((DMNStyle) null, null));
    }

    @Test
    public void testVisitDMNLabel() {
        assertNull(visitor.visit((DMNLabel) null, null));
    }

    @Test
    public void testVisitDMNDecisionServiceDividerLine() {
        assertNull(visitor.visit((DMNDecisionServiceDividerLine) null, null));
    }

    @Test
    public void testVisitColor() {
        assertNull(visitor.visit((Color) null, null));
    }

    @Test
    public void testVisitPoint() {
        assertNull(visitor.visit((Point) null, null));
    }

    @Test
    public void testVisitBounds() {
        assertNull(visitor.visit((Bounds) null, null));
    }

    @Test
    public void testVisitDimension() {
        assertNull(visitor.visit((Dimension) null, null));
    }

    @Test
    public void testVisitDiagramElementExtension() {
        assertNull(visitor.visit((DiagramElement.Extension) null, null));
    }

    @Test
    public void testVisitStyleExtension() {
        assertNull(visitor.visit((Style.Extension) null, null));
    }
}