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
import com.gs.dmn.DRGElementReference;
import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TDecision;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TInformationItem;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.JavaTimeDMNDialectDefinition;
import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.NameKind;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicDMNToJavaTransformerTest extends AbstractTest {
    private final DMNDialectDefinition<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount, TestCases> dialectDefinition = new JavaTimeDMNDialectDefinition();
    private final DMNSerializer serializer = this.dialectDefinition.createDMNSerializer(LOGGER, new InputParameters(makeInputParametersMap()));
    private BasicDMNToJavaTransformer transformer;
    private DMNModelRepository repository;
    private TDefinitions definitions;
    private String href;

    @BeforeEach
    public void setUp() {
        String pathName = "dmn/input/1.1/0004-lending.dmn";
        this.repository = readDMN(pathName);
        this.definitions = getDefinitions(repository);
        this.href = this.definitions.getNamespace() + "#d_RequiredMonthlyInstallment";
        this.transformer = this.dialectDefinition.createBasicTransformer(repository, new NopLazyEvaluationDetector(), this.inputParameters);
    }

    @Test
    public void testRegistryKey() {
        // Test TNamedElement
        TDRGElement element = this.repository.findDRGElementByName(definitions, "BureauCallType");
        assertEquals("http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b#BureauCallType", transformer.registryKey(element));

        // Test another TNamedElement
        TDRGElement eligibility = this.repository.findDRGElementByName(definitions, "Eligibility");
        assertEquals("http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b#Eligibility", transformer.registryKey(eligibility));
    }

    @Test
    public void testApplyMapKey() {
        // Test TNamedElement and DisplayName kind
        TDRGElement element = this.repository.findDRGElementByName(definitions, "BureauCallType");
        assertEquals("BureauCallType", transformer.applyMapKey(new FEELParameter(element, null)));

        // Test TNamedElement that has a label and DisplayName kind
        element.setLabel("Bureau Call Type Label");
        assertEquals("Bureau Call Type Label", transformer.applyMapKey(new FEELParameter(element, null)));

        // Test TNamedElement that has a label and SimpleName kind
        assertEquals("BureauCallType", new MockTransformer(this.repository).applyMapKey(new FEELParameter(element, null)));

        // Test reference for single models and DisplayName kind
        DRGElementReference<TDRGElement> reference = this.repository.makeDRGElementReference(element);
        assertEquals("Bureau Call Type Label", transformer.applyMapKey(new FEELParameter(reference, null)));

        // Test reference for multiple models and SimpleName kind
        reference = this.repository.makeDRGElementReference("", element);
        assertEquals("BureauCallType", new MockTransformer(this.repository).applyMapKey(new FEELParameter(reference, null)));
        reference = this.repository.makeDRGElementReference("prefix", element);
        assertEquals("0004-lending.BureauCallType", new MockTransformer(this.repository).applyMapKey(new FEELParameter(reference, null)));
    }

    @Test
    public void testApplyContextKey() {
        // Test TNamedElement and DisplayName kind
        TDRGElement element = this.repository.findDRGElementByName(definitions, "BureauCallType");
        assertEquals("BureauCallType", transformer.applyContextKey(new FEELParameter(element, null)));

        // Test TNamedElement that has a label and DisplayName kind
        element.setLabel("Bureau Call Type Label");
        assertEquals("Bureau Call Type Label", transformer.applyContextKey(new FEELParameter(element, null)));

        // Test TNamedElement that has a label, and SimpleName kind
        assertEquals("BureauCallType", new MockTransformer(this.repository).applyContextKey(new FEELParameter(element, null)));

        // Test reference for single models and DisplayName kind
        DRGElementReference<TDRGElement> reference = this.repository.makeDRGElementReference(element);
        assertEquals("Bureau Call Type Label", transformer.applyContextKey(new FEELParameter(reference, null)));

        // Test reference for multiple models and SimpleName kind
        reference = this.repository.makeDRGElementReference("", element);
        assertEquals("BureauCallType", new MockTransformer(this.repository).applyContextKey(new FEELParameter(reference, null)));
        reference = this.repository.makeDRGElementReference("prefix", element);
        assertEquals("0004-lending.BureauCallType", new MockTransformer(this.repository).applyContextKey(new FEELParameter(reference, null)));
    }

    @Test
    public void testCacheKey() {
        // Test TNamedElement and DisplayName kind
        TDRGElement element = this.repository.findDRGElementByName(definitions, "BureauCallType");
        assertEquals("BureauCallType", transformer.cacheKey(element));

        // Test TNamedElement that has a label, and DisplayName kind
        element.setLabel("Bureau Call Type Label");
        assertEquals("BureauCallType", transformer.cacheKey(element));

        // Test TNamedElement that has a label, and SimpleName kind
        assertEquals("BureauCallType", new MockTransformer(this.repository).cacheKey(element));
    }

    @Test
    public void testListenerArgumentsKey() {
        // Test TNamedElement and DisplayName kind
        TDRGElement element = this.repository.findDRGElementByName(definitions, "BureauCallType");
        assertEquals("BureauCallType", transformer.listenerArgumentsKey(new FEELParameter(element, null)));

        // Test TNamedElement that has a label, and DisplayName kind
        element.setLabel("Bureau Call Type Label");
        assertEquals("Bureau Call Type Label", transformer.listenerArgumentsKey(new FEELParameter(element, null)));

        // Test TNamedElement that has a label, and SimpleName kind
        assertEquals("BureauCallType", new MockTransformer(this.repository).listenerArgumentsKey(new FEELParameter(element, null)));

        // Test reference for single models and DisplayName kind
        DRGElementReference<TDRGElement> reference = this.repository.makeDRGElementReference(element);
        assertEquals("Bureau Call Type Label", transformer.listenerArgumentsKey(new FEELParameter(reference, null)));

        // Test reference for multiple models and SimpleName kind
        reference = this.repository.makeDRGElementReference("", element);
        assertEquals("BureauCallType", new MockTransformer(this.repository).listenerArgumentsKey(new FEELParameter(reference, null)));
        reference = this.repository.makeDRGElementReference("prefix", element);
        assertEquals("0004-lending.BureauCallType", new MockTransformer(this.repository).listenerArgumentsKey(new FEELParameter(reference, null)));
    }

    @Test
    public void testNativeVariableName() {
        // Get existing DRG element from the loaded model
        TDRGElement element = this.repository.findDRGElementByName(definitions, "BureauCallType");

        // Test TNamedElement and DisplayName kind
        assertEquals("bureauCallType", transformer.nativeVariableName(element));

        // Test TNamedElement that has a label, and DisplayName kind
        element.setLabel("Bureau Call Type Label");
        assertEquals("bureauCallType", transformer.nativeVariableName(element));

        // Test TInformationItem that has a label, and DisplayName kind
        element.setLabel("Bureau Call Type Label");
        TInformationItem variable = ((TDecision) element).getVariable();
        assertEquals("bureauCallType", transformer.nativeVariableName(new FEELParameter(variable, null)));

        // Test reference for single models and DisplayName kind
        DRGElementReference<TDRGElement> reference = this.repository.makeDRGElementReference(element);
        assertEquals("bureauCallType", transformer.nativeVariableName(new FEELParameter(reference, null)));

        // Test reference for multiple models and SimpleName kind
        reference = this.repository.makeDRGElementReference("prefix", element);
        assertEquals("p_0004_lending_bureauCallType", new MockTransformer(this.repository).nativeVariableName(new FEELParameter(reference, null)));
    }

    @Test
    public void testJavaFriendlyName() {
        assertEquals("ClientLevelRequirementRules", this.transformer.nativeFriendlyName("Client-level Requirement Rules"));
        assertEquals("ClientLevelRequirementRulesType", this.transformer.nativeFriendlyName("Client-level Requirement RulesType"));
        assertEquals("LinkedToList12ApprovedRegulator", this.transformer.nativeFriendlyName("Linked to List 1,2 Approved Regulator"));
        assertEquals("PrivateFundAssessmentPotentialSubClassifications", this.transformer.nativeFriendlyName("Private Fund Assessment.Potential SubClassifications"));
    }

    @Test
    public void testJavaFriendlyVariableName() {
        assertEquals("clientLevelRequirementRules", this.transformer.nativeFriendlyVariableName("Client-level Requirement Rules"));
        assertEquals("clientLevelRequirementRulesType", this.transformer.nativeFriendlyVariableName("Client-level Requirement RulesType"));
        assertEquals("linkedToList12ApprovedRegulator", this.transformer.nativeFriendlyVariableName("Linked to List 1,2 Approved Regulator"));
        assertEquals("privateFundAssessment.potentialSubClassifications", this.transformer.nativeFriendlyVariableName("Private Fund Assessment.Potential SubClassifications"));
        assertEquals("totalVacationDays", this.transformer.nativeFriendlyVariableName("'Total Vacation Days'"));
    }

    @Test
    public void testUpperCaseFirst() {
        assertEquals("ClientLevelRequirementRules", this.transformer.upperCaseFirst("Client-level Requirement Rules"));
        assertEquals("ClientLevelRequirementRulesType", this.transformer.upperCaseFirst("Client-level Requirement RulesType"));
        assertEquals("LinkedToList12ApprovedRegulator", this.transformer.upperCaseFirst("Linked to List 1,2 Approved Regulator"));
        assertEquals("LinkedToList12ApprovedRegulator", this.transformer.upperCaseFirst("'Linked to List 1,2 Approved Regulator'"));
    }

    @Test
    public void testLowerCaseFirst() {
        assertEquals("clientLevelRequirementRules", this.transformer.lowerCaseFirst("Client-level Requirement Rules"));
        assertEquals("clientLevelRequirementRulesType", this.transformer.lowerCaseFirst("Client-level Requirement RulesType"));
        assertEquals("linkedToList12ApprovedRegulator", this.transformer.lowerCaseFirst("Linked to List 1,2 Approved Regulator"));
        assertEquals("linkedToList12ApprovedRegulator", this.transformer.lowerCaseFirst("'Linked to List 1,2 Approved Regulator'"));
    }

    @Test
    public void testEmptyAnnotation() {
        TDecision decision = this.transformer.getDMNModelRepository().findDecisionByRef(null, this.href);
        assertEquals(Collections.emptyList(), this.transformer.annotations(decision, Collections.singletonList(null)));
        assertEquals(Collections.emptyList(), this.transformer.annotations(decision, Collections.singletonList("")));
    }

    @Test
    public void testAnnotationWithOneString() {
        TDecision decision = this.transformer.getDMNModelRepository().findDecisionByRef(null, this.href);
        assertEquals(Collections.singletonList("string(\"plain text\")"), this.transformer.annotations(decision, Collections.singletonList("string(\"plain text\")")));
        assertEquals(Collections.singletonList("string(((java.lang.Number)(requestedProduct != null ? requestedProduct.getTerm() : null)))"), this.transformer.annotations(decision, Collections.singletonList("string(RequestedProduct.Term)")));
        assertEquals(Collections.singletonList("string(\"\")"), this.transformer.annotations(decision, Collections.singletonList("string(\"\")")));
    }

    @Test
    public void testAnnotationWithExpression() {
        TDecision decision = this.transformer.getDMNModelRepository().findDecisionByRef(null, this.href);
        assertEquals(Collections.singletonList("string(numericAdd(((java.lang.Number)(requestedProduct != null ? requestedProduct.getRate() : null)), number(\"2\")))"), this.transformer.annotations(decision, Collections.singletonList("string(RequestedProduct.Rate + 2)")));
    }

    @Test
    public void testAnnotationWithSeveralStrings() {
        TDecision decision = this.transformer.getDMNModelRepository().findDecisionByRef(null, this.href);
        List<String> expected = Collections.singletonList(
                "stringAdd(stringAdd(stringAdd(stringAdd(string(\"Rate is \"), string(((java.lang.Number)(requestedProduct != null ? requestedProduct.getRate() : null)))), " +
                "string(\". And term is \")), string(((java.lang.Number)(requestedProduct != null ? requestedProduct.getTerm() : null)))), string(\"!\"))");
        assertEquals(expected, this.transformer.annotations(decision, Collections.singletonList("string(\"Rate is \") + string(RequestedProduct.Rate) + string(\". And term is \") + string(RequestedProduct.Term) + string(\"!\")")));
        assertEquals(Collections.singletonList("asList(string(\"\"), string(\"\"), string(\"\"))"), this.transformer.annotations(decision, Collections.singletonList("[string(\"\"), string(\"\"), string(\"\")]")));
    }

    @Test
    public void testEscapeInString() {
        assertEquals("", this.transformer.escapeInString(null));
        assertEquals("", this.transformer.escapeInString(""));
        assertEquals("abc", this.transformer.escapeInString("abc"));
        assertEquals("ab\\\"abc", this.transformer.escapeInString("ab\\\"abc"));
        assertEquals("ab\\\"abc", this.transformer.escapeInString("ab\"abc"));
        assertEquals("“£%$&3332", this.transformer.escapeInString("“£%$&3332"));
        assertEquals("ab\\\\dc", this.transformer.escapeInString("ab\\dc"));
        assertEquals("\u0009", this.transformer.escapeInString("\u0009"));
        assertEquals("\\u0009", this.transformer.escapeInString("\\u0009"));
        assertEquals("\uD83D\uDCA9", this.transformer.escapeInString("\uD83D\uDCA9"));
        assertEquals("\ud83d\udca9", this.transformer.escapeInString("\ud83d\udca9"));
        assertEquals("\ud83d\udc0e\uD83D\uDE00", this.transformer.escapeInString("\ud83d\udc0e\uD83D\uDE00"));
        assertEquals("🐎😀", this.transformer.escapeInString("🐎😀"));
    }

    @Test
    public void testNativePackageName() {
        assertEquals("", this.transformer.nativePackageName(null));
        assertEquals("", this.transformer.nativePackageName(""));
        assertEquals("abc", this.transformer.nativePackageName("abc"));
        assertEquals("abc", this.transformer.nativePackageName("aBc"));
        assertEquals("p_123abc", this.transformer.nativePackageName("123aBc"));
        assertEquals("literal_arithmetic", this.transformer.nativePackageName("literal - arithmetic"));
    }

    @Test
    public void testDefaultValues() {
        assertEquals("new java.math.BigDecimal(\"0\")", this.transformer.getDefaultIntegerValue());
        assertEquals("new java.math.BigDecimal(\"0.0\")", this.transformer.getDefaultDecimalValue());
        assertEquals("Boolean.FALSE", this.transformer.getDefaultBooleanValue());
        assertEquals("null", this.transformer.getDefaultStringValue());
        assertEquals("null", this.transformer.getDefaultDateValue());
        assertEquals("null", this.transformer.getDefaultTimeValue());
        assertEquals("null", this.transformer.getDefaultDateAndTimeValue());
    }

    private DMNModelRepository readDMN(String pathName) {
        File input = new File(resource(pathName));
        TDefinitions definitions = this.serializer.readModel(input);
        return new DMNModelRepository(definitions);
    }

    @Override
    protected Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = super.makeInputParametersMap();
        inputParams.put(InputParameters.APPLY_NAME_KIND_KEY, "DisplayName");
        return inputParams;
    }

}

class MockTransformer extends BasicDMNToJavaTransformer {
    public MockTransformer(DMNModelRepository dmnModelRepository) {
        this(new JavaTimeDMNDialectDefinition(), dmnModelRepository, new NopLazyEvaluationDetector(), new InputParameters(makeInputParametersMap()));
    }

    public MockTransformer(DMNDialectDefinition<?, ?, ?, ?, ?, ?> dialect, DMNModelRepository dmnModelRepository, LazyEvaluationDetector lazyEvaluationDetector, InputParameters inputParameters) {
        super(dialect, dmnModelRepository, lazyEvaluationDetector, inputParameters);
    }

    @Override
    public boolean isOnePackage() {
        return false;
    }

    private static Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = new LinkedHashMap<>();
        inputParams.put("dmnVersion", "1.1");
        inputParams.put("modelVersion", "2.0");
        inputParams.put("platformVersion", "1.0");
        inputParams.put(InputParameters.APPLY_NAME_KIND_KEY, NameKind.SimpleName.name());
        return inputParams;
    }
}