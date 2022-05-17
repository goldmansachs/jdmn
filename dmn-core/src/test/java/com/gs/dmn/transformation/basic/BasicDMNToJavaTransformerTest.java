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
package com.gs.dmn.transformation.basic;

import com.gs.dmn.AbstractTest;
import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.TDecision;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.junit.Before;
import org.junit.Test;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BasicDMNToJavaTransformerTest extends AbstractTest {
    private final DMNDialectDefinition<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration, TestCases> dialectDefinition = new StandardDMNDialectDefinition();
    private final DMNSerializer serializer = this.dialectDefinition.createDMNSerializer(LOGGER, new InputParameters(makeInputParametersMap()));
    private BasicDMNToJavaTransformer dmnTransformer;
    private String href;

    @Before
    public void setUp() {
        String pathName = "dmn/input/1.1/0004-lending.dmn";
        DMNModelRepository repository = readDMN(pathName);
        TDefinitions definitions = repository.getRootDefinitions();
        this.href = definitions.getNamespace() + "#d_RequiredMonthlyInstallment";
        this.dmnTransformer = this.dialectDefinition.createBasicTransformer(repository, new NopLazyEvaluationDetector(), makeInputParameters());
    }

    @Test
    public void testJavaFriendlyName() {
        assertEquals("ClientLevelRequirementRules", this.dmnTransformer.nativeFriendlyName("Client-level Requirement Rules"));
        assertEquals("ClientLevelRequirementRulesType", this.dmnTransformer.nativeFriendlyName("Client-level Requirement RulesType"));
        assertEquals("LinkedToList12ApprovedRegulator", this.dmnTransformer.nativeFriendlyName("Linked to List 1,2 Approved Regulator"));
        assertEquals("PrivateFundAssessmentPotentialSubClassifications", this.dmnTransformer.nativeFriendlyName("Private Fund Assessment.Potential SubClassifications"));
    }

    @Test
    public void testJavaFriendlyVariableName() {
        assertEquals("clientLevelRequirementRules", this.dmnTransformer.nativeFriendlyVariableName("Client-level Requirement Rules"));
        assertEquals("clientLevelRequirementRulesType", this.dmnTransformer.nativeFriendlyVariableName("Client-level Requirement RulesType"));
        assertEquals("linkedToList12ApprovedRegulator", this.dmnTransformer.nativeFriendlyVariableName("Linked to List 1,2 Approved Regulator"));
        assertEquals("privateFundAssessment.potentialSubClassifications", this.dmnTransformer.nativeFriendlyVariableName("Private Fund Assessment.Potential SubClassifications"));
        assertEquals("totalVacationDays", this.dmnTransformer.nativeFriendlyVariableName("'Total Vacation Days'"));
    }

    @Test
    public void testUpperCaseFirst() {
        assertEquals("ClientLevelRequirementRules", this.dmnTransformer.upperCaseFirst("Client-level Requirement Rules"));
        assertEquals("ClientLevelRequirementRulesType", this.dmnTransformer.upperCaseFirst("Client-level Requirement RulesType"));
        assertEquals("LinkedToList12ApprovedRegulator", this.dmnTransformer.upperCaseFirst("Linked to List 1,2 Approved Regulator"));
        assertEquals("LinkedToList12ApprovedRegulator", this.dmnTransformer.upperCaseFirst("'Linked to List 1,2 Approved Regulator'"));
    }

    @Test
    public void testLowerCaseFirst() {
        assertEquals("clientLevelRequirementRules", this.dmnTransformer.lowerCaseFirst("Client-level Requirement Rules"));
        assertEquals("clientLevelRequirementRulesType", this.dmnTransformer.lowerCaseFirst("Client-level Requirement RulesType"));
        assertEquals("linkedToList12ApprovedRegulator", this.dmnTransformer.lowerCaseFirst("Linked to List 1,2 Approved Regulator"));
        assertEquals("linkedToList12ApprovedRegulator", this.dmnTransformer.lowerCaseFirst("'Linked to List 1,2 Approved Regulator'"));
    }

    @Test
    public void testEmptyAnnotation() {
        assertEquals("\"\"", this.dmnTransformer.annotation(null, (String) null));
        assertEquals("\"\"", this.dmnTransformer.annotation(null, ""));
    }

    @Test
    public void testAnnotationWithOneString() {
        TDecision decision = this.dmnTransformer.getDMNModelRepository().findDecisionByRef(null, this.href);
        assertEquals("string(\"plain text\")", this.dmnTransformer.annotation(decision, "string(\"plain text\")"));
        assertEquals("string(((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getTerm() : null)))", this.dmnTransformer.annotation(decision, "string(RequestedProduct.Term)"));
        assertEquals("string(\"\")", this.dmnTransformer.annotation(decision, "string(\"\")"));
    }

    @Test
    public void testAnnotationWithExpression() {
        TDecision decision = this.dmnTransformer.getDMNModelRepository().findDecisionByRef(null, this.href);
        assertEquals("string(numericAdd(((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getRate() : null)), number(\"2\")))", this.dmnTransformer.annotation(decision, "string(RequestedProduct.Rate + 2)"));
    }

    @Test
    public void testAnnotationWithSeveralStrings() {
        TDecision decision = this.dmnTransformer.getDMNModelRepository().findDecisionByRef(null, this.href);
        String expected = "stringAdd(stringAdd(stringAdd(stringAdd(string(\"Rate is \"), string(((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getRate() : null)))), " +
                "string(\". And term is \")), string(((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getTerm() : null)))), string(\"!\"))";
        assertEquals(expected, this.dmnTransformer.annotation(decision, "string(\"Rate is \") + string(RequestedProduct.Rate) + string(\". And term is \") + string(RequestedProduct.Term) + string(\"!\")"));
        assertEquals("asList(string(\"\"), string(\"\"), string(\"\"))", this.dmnTransformer.annotation(decision, "[string(\"\"), string(\"\"), string(\"\")]"));
    }

    @Test
    public void testEscapeInString() {
        assertNull(this.dmnTransformer.escapeInString(null));
        assertEquals("", this.dmnTransformer.escapeInString(""));
        assertEquals("abc", this.dmnTransformer.escapeInString("abc"));
        assertEquals("ab\\\"abc", this.dmnTransformer.escapeInString("ab\\\"abc"));
        assertEquals("ab\\\"abc", this.dmnTransformer.escapeInString("ab\"abc"));
        assertEquals("“£%$&3332", this.dmnTransformer.escapeInString("“£%$&3332"));
        assertEquals("ab\\\\dc", this.dmnTransformer.escapeInString("ab\\dc"));
        assertEquals("\u0009", this.dmnTransformer.escapeInString("\u0009"));
        assertEquals("\\u0009", this.dmnTransformer.escapeInString("\\u0009"));
        assertEquals("\uD83D\uDCA9", this.dmnTransformer.escapeInString("\uD83D\uDCA9"));
        assertEquals("\ud83d\udca9", this.dmnTransformer.escapeInString("\ud83d\udca9"));
        assertEquals("\ud83d\udc0e\uD83D\uDE00", this.dmnTransformer.escapeInString("\ud83d\udc0e\uD83D\uDE00"));
        assertEquals("🐎😀", this.dmnTransformer.escapeInString("🐎😀"));
    }

    @Test
    public void testJavaModelName() {
        assertEquals("", this.dmnTransformer.javaModelName(null));
        assertEquals("", this.dmnTransformer.javaModelName(""));
        assertEquals("abc", this.dmnTransformer.javaModelName("abc"));
        assertEquals("abc", this.dmnTransformer.javaModelName("aBc"));
        assertEquals("p_123abc", this.dmnTransformer.javaModelName("123aBc"));
        assertEquals("literal_arithmetic", this.dmnTransformer.javaModelName("literal - arithmetic"));
    }

    private DMNModelRepository readDMN(String pathName) {
        File input = new File(resource(pathName));
        TDefinitions definitions = this.serializer.readModel(input);
        return new DMNModelRepository(definitions);
    }

}