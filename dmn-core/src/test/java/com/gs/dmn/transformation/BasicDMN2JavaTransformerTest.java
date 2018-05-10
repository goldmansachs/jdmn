/**
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
package com.gs.dmn.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.junit.Before;
import org.junit.Test;
import org.omg.spec.dmn._20180521.model.TDecision;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BasicDMN2JavaTransformerTest {
    private static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(BasicDMN2JavaTransformerTest.class));

    private final DMNDialectDefinition dialectDefinition = new StandardDMNDialectDefinition();
    private final DMNReader dmnReader = new DMNReader(LOGGER, false);
    private BasicDMN2JavaTransformer dmnTransformer;
    private final String href = "d_RequiredMonthlyInstallment";

    @Before
    public void setUp() {
        String pathName = "dmn/input/0004-lending.dmn";
        DMNModelRepository repository = readDMN(pathName);
        this.dmnTransformer = dialectDefinition.createBasicTransformer(repository, new NopLazyEvaluationDetector(), new LinkedHashMap<>());
    }

    @Test
    public void testJavaFriendlyName() {
        assertEquals("ClientLevelRequirementRules", dmnTransformer.javaFriendlyName("Client-level Requirement Rules"));
        assertEquals("ClientLevelRequirementRulesType", dmnTransformer.javaFriendlyName("Client-level Requirement RulesType"));
        assertEquals("LinkedToList12ApprovedRegulator", dmnTransformer.javaFriendlyName("Linked to List 1,2 Approved Regulator"));
        assertEquals("PrivateFundAssessmentPotentialSubClassifications", dmnTransformer.javaFriendlyName("Private Fund Assessment.Potential SubClassifications"));
    }

    @Test
    public void testJavaFriendlyVariableName() {
        assertEquals("clientLevelRequirementRules", dmnTransformer.javaFriendlyVariableName("Client-level Requirement Rules"));
        assertEquals("clientLevelRequirementRulesType", dmnTransformer.javaFriendlyVariableName("Client-level Requirement RulesType"));
        assertEquals("linkedToList12ApprovedRegulator", dmnTransformer.javaFriendlyVariableName("Linked to List 1,2 Approved Regulator"));
        assertEquals("privateFundAssessment.potentialSubClassifications", dmnTransformer.javaFriendlyVariableName("Private Fund Assessment.Potential SubClassifications"));
        assertEquals("totalVacationDays", dmnTransformer.javaFriendlyVariableName("'Total Vacation Days'"));
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
        TDecision decision = dmnTransformer.getDMNModelRepository().findDecisionById(href);
        assertEquals("string(\"plain text\")", dmnTransformer.annotation(decision, "string(\"plain text\")"));
        assertEquals("string(((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getTerm() : null)))", dmnTransformer.annotation(decision, "string(RequestedProduct.Term)"));
        assertEquals("\"\"", dmnTransformer.annotation(decision, "string(-)"));
    }

    @Test
    public void testAnnotationWithExpression() {
        TDecision decision = dmnTransformer.getDMNModelRepository().findDecisionById(href);
        assertEquals("string(numericAdd(((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getRate() : null)), number(\"2\")))", dmnTransformer.annotation(decision, "string(RequestedProduct.Rate + 2)"));
    }

    @Test
    public void testAnnotationWithSeveralStrings() {
        TDecision decision = dmnTransformer.getDMNModelRepository().findDecisionById(href);
        String expected = "stringAdd(stringAdd(stringAdd(stringAdd(string(\"Rate is \"), string(((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getRate() : null)))), " +
                "string(\". And term is \")), string(((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getTerm() : null)))), string(\"!\"))";
        assertEquals(expected, dmnTransformer.annotation(decision, "string(\"Rate is \") + string(RequestedProduct.Rate) + string(\". And term is \") + string(RequestedProduct.Term) + string(\"!\")"));
        assertEquals("asList(\"\", \"\", \"\")", dmnTransformer.annotation(decision, "[string(-), string(-), string(-)]"));
    }

    @Test
    public void testEscapeInString() {
        assertNull(dmnTransformer.escapeInString(null));
        assertEquals("", dmnTransformer.escapeInString(""));
        assertEquals("abc", dmnTransformer.escapeInString("abc"));
        assertEquals("ab\\\"abc", dmnTransformer.escapeInString("ab\"abc"));
        assertEquals("“£%$&3332", dmnTransformer.escapeInString("“£%$&3332"));
        assertEquals("ab\\\\dc", dmnTransformer.escapeInString("ab\\dc"));
    }

    private DMNModelRepository readDMN(String pathName) {
        File input = new File(BasicDMN2JavaTransformerTest.class.getClassLoader().getResource(pathName).getFile());
        return dmnReader.read(input);
    }

}