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
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.junit.Before;
import org.junit.Test;
import org.omg.dmn.tck.marshaller._20160719.TestCases;
import org.omg.spec.dmn._20191111.model.TDecision;
import org.omg.spec.dmn._20191111.model.TDefinitions;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BasicDMNToJavaTransformerTest extends AbstractTest {
    private final DMNDialectDefinition<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration, TestCases> dialectDefinition = new StandardDMNDialectDefinition();
    private final DMNReader dmnReader = new DMNReader(LOGGER, false);
    private BasicDMNToJavaTransformer dmnTransformer;
    private String href;

    @Before
    public void setUp() {
        String pathName = "dmn/input/1.1/0004-lending.dmn";
        DMNModelRepository repository = readDMN(pathName);
        TDefinitions definitions = repository.getRootDefinitions();
        this.href = definitions.getNamespace() + "#d_RequiredMonthlyInstallment";
        this.dmnTransformer = dialectDefinition.createBasicTransformer(repository, new NopLazyEvaluationDetector(), makeInputParameters());
    }

    @Test
    public void testJavaFriendlyName() {
        assertEquals("ClientLevelRequirementRules", dmnTransformer.nativeFriendlyName("Client-level Requirement Rules"));
        assertEquals("ClientLevelRequirementRulesType", dmnTransformer.nativeFriendlyName("Client-level Requirement RulesType"));
        assertEquals("LinkedToList12ApprovedRegulator", dmnTransformer.nativeFriendlyName("Linked to List 1,2 Approved Regulator"));
        assertEquals("PrivateFundAssessmentPotentialSubClassifications", dmnTransformer.nativeFriendlyName("Private Fund Assessment.Potential SubClassifications"));
    }

    @Test
    public void testJavaFriendlyVariableName() {
        assertEquals("clientLevelRequirementRules", dmnTransformer.nativeFriendlyVariableName("Client-level Requirement Rules"));
        assertEquals("clientLevelRequirementRulesType", dmnTransformer.nativeFriendlyVariableName("Client-level Requirement RulesType"));
        assertEquals("linkedToList12ApprovedRegulator", dmnTransformer.nativeFriendlyVariableName("Linked to List 1,2 Approved Regulator"));
        assertEquals("privateFundAssessment.potentialSubClassifications", dmnTransformer.nativeFriendlyVariableName("Private Fund Assessment.Potential SubClassifications"));
        assertEquals("totalVacationDays", dmnTransformer.nativeFriendlyVariableName("'Total Vacation Days'"));
    }

    @Test
    public void testUpperCaseFirst() {
        assertEquals("ClientLevelRequirementRules", dmnTransformer.upperCaseFirst("Client-level Requirement Rules"));
        assertEquals("ClientLevelRequirementRulesType", dmnTransformer.upperCaseFirst("Client-level Requirement RulesType"));
        assertEquals("LinkedToList12ApprovedRegulator", dmnTransformer.upperCaseFirst("Linked to List 1,2 Approved Regulator"));
        assertEquals("LinkedToList12ApprovedRegulator", dmnTransformer.upperCaseFirst("'Linked to List 1,2 Approved Regulator'"));
    }

    @Test
    public void testLowerCaseFirst() {
        assertEquals("clientLevelRequirementRules", dmnTransformer.lowerCaseFirst("Client-level Requirement Rules"));
        assertEquals("clientLevelRequirementRulesType", dmnTransformer.lowerCaseFirst("Client-level Requirement RulesType"));
        assertEquals("linkedToList12ApprovedRegulator", dmnTransformer.lowerCaseFirst("Linked to List 1,2 Approved Regulator"));
        assertEquals("linkedToList12ApprovedRegulator", dmnTransformer.lowerCaseFirst("'Linked to List 1,2 Approved Regulator'"));
    }

    @Test
    public void testEmptyAnnotation() {
        assertEquals("\"\"", dmnTransformer.annotation(null, (String)null));
        assertEquals("\"\"", dmnTransformer.annotation(null, ""));
    }

    @Test
    public void testAnnotationWithOneString() {
        TDecision decision = dmnTransformer.getDMNModelRepository().findDecisionByRef(null, href);
        assertEquals("string(\"plain text\")", dmnTransformer.annotation(decision, "string(\"plain text\")"));
        assertEquals("string(((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getTerm() : null)))", dmnTransformer.annotation(decision, "string(RequestedProduct.Term)"));
        assertEquals("string(\"\")", dmnTransformer.annotation(decision, "string(\"\")"));
    }

    @Test
    public void testAnnotationWithExpression() {
        TDecision decision = dmnTransformer.getDMNModelRepository().findDecisionByRef(null, href);
        assertEquals("string(numericAdd(((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getRate() : null)), number(\"2\")))", dmnTransformer.annotation(decision, "string(RequestedProduct.Rate + 2)"));
    }

    @Test
    public void testAnnotationWithSeveralStrings() {
        TDecision decision = dmnTransformer.getDMNModelRepository().findDecisionByRef(null, href);
        String expected = "stringAdd(stringAdd(stringAdd(stringAdd(string(\"Rate is \"), string(((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getRate() : null)))), " +
                "string(\". And term is \")), string(((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getTerm() : null)))), string(\"!\"))";
        assertEquals(expected, dmnTransformer.annotation(decision, "string(\"Rate is \") + string(RequestedProduct.Rate) + string(\". And term is \") + string(RequestedProduct.Term) + string(\"!\")"));
        assertEquals("asList(string(\"\"), string(\"\"), string(\"\"))", dmnTransformer.annotation(decision, "[string(\"\"), string(\"\"), string(\"\")]"));
    }

    @Test
    public void testEscapeInString() {
        assertNull(dmnTransformer.escapeInString(null));
        assertEquals("", dmnTransformer.escapeInString(""));
        assertEquals("abc", dmnTransformer.escapeInString("abc"));
        assertEquals("ab\\\"abc", dmnTransformer.escapeInString("ab\\\"abc"));
        assertEquals("ab\\\"abc", dmnTransformer.escapeInString("ab\"abc"));
        assertEquals("“£%$&3332", dmnTransformer.escapeInString("“£%$&3332"));
        assertEquals("ab\\\\dc", dmnTransformer.escapeInString("ab\\dc"));
        assertEquals("\u0009", dmnTransformer.escapeInString("\u0009"));
        assertEquals("\\u0009", dmnTransformer.escapeInString("\\u0009"));
        assertEquals("\uD83D\uDCA9", dmnTransformer.escapeInString("\uD83D\uDCA9"));
        assertEquals("\ud83d\udca9", dmnTransformer.escapeInString("\ud83d\udca9"));
        assertEquals("\ud83d\udc0e\uD83D\uDE00", dmnTransformer.escapeInString("\ud83d\udc0e\uD83D\uDE00"));
        assertEquals("🐎😀", dmnTransformer.escapeInString("🐎😀"));
    }

    @Test
    public void testJavaModelName() {
        assertEquals("", dmnTransformer.javaModelName(null));
        assertEquals("", dmnTransformer.javaModelName(""));
        assertEquals("abc", dmnTransformer.javaModelName("abc"));
        assertEquals("abc", dmnTransformer.javaModelName("aBc"));
        assertEquals("p_123abc", dmnTransformer.javaModelName("123aBc"));
        assertEquals("literal_arithmetic", dmnTransformer.javaModelName("literal - arithmetic"));
    }

    private DMNModelRepository readDMN(String pathName) {
        File input = new File(resource(pathName));
        Pair<TDefinitions, PrefixNamespaceMappings> pair = dmnReader.read(input);
        return new DMNModelRepository(pair);
    }

}