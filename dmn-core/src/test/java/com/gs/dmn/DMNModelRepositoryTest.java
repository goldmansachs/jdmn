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
package com.gs.dmn;

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import org.junit.Before;
import org.junit.Test;
import org.omg.spec.dmn._20180521.model.*;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class DMNModelRepositoryTest {
    private static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(DMNModelRepositoryTest.class));

    private DMNModelRepository dmnModelRepository;
    private final DMNReader dmnReader = new DMNReader(LOGGER, false);

    @Before
    public void setUp() {
        String pathName = "dmn/input/0004-lending.dmn";
        this.dmnModelRepository = readDMN(pathName);
    }

    @Test
    public void testFindDecisionByRef() {
        String id = "d_BureauCallType";
        TDefinitions definitions = dmnModelRepository.getRootDefinitions();
        String namespace = definitions.getNamespace();
        TDecision decision = dmnModelRepository.findDecisionByRef(null,namespace + "#" + id);
        assertEquals(id, decision.getId());
        assertEquals("BureauCallType", decision.getName());
    }

    @Test
    public void testTopologicalSort() {
        TDMNElement root = dmnModelRepository.findDRGElementByName("Strategy");

        List<TDecision> decisions = dmnModelRepository.topologicalSort((TDecision)root);

        List<String> actualNames = decisions.stream().map(TNamedElement::getName).collect(Collectors.toList());
        List<String> expectedNames = Arrays.asList("ApplicationRiskScore", "Pre-bureauRiskCategory", "BureauCallType", "RequiredMonthlyInstallment", "Pre-bureauAffordability", "Eligibility");
        assertEquals(expectedNames, actualNames);
    }

    @Test
    public void testCachedElements() {
        Set<String> cachedElements = dmnModelRepository.computeCachedElements(true);

        assertEquals(Arrays.asList("Pre-bureauRiskCategory", "RequiredMonthlyInstallment", "Post-bureauRiskCategory", "ApplicationRiskScore"), cachedElements.stream().collect(Collectors.toList()));
    }

    @Test
    public void testDirectInputDatas() {
        TDRGElement root = dmnModelRepository.findDRGElementByName("Adjudication");
        List<TInputData> elements = dmnModelRepository.directInputDatas(root);
        dmnModelRepository.sortNamedElements(elements);

        List<String> actualNames = elements.stream().map(p -> p.getName()).collect(Collectors.toList());
        List<String> expectedNames = Arrays.asList("ApplicantData", "BureauData", "SupportingDocuments");
        assertEquals(expectedNames, actualNames);
    }

    @Test
    public void testAllInputDatas() {
        TDRGElement root = dmnModelRepository.findDRGElementByName("Pre-bureauAffordability");
        List<TInputData> elements = dmnModelRepository.allInputDatas(root);
        dmnModelRepository.sortNamedElements(elements);

        List<String> actualNames = elements.stream().map(p -> p.getName()).collect(Collectors.toList());
        List<String> expectedNames = Arrays.asList(
                "ApplicantData", "RequestedProduct");
        assertEquals(expectedNames, actualNames);
    }

    @Test
    public void testDirectSubDecisions() {
        TDRGElement root = dmnModelRepository.findDRGElementByName("Strategy");
        List<TDecision> elements = dmnModelRepository.directSubDecisions(root);
        dmnModelRepository.sortNamedElements(elements);

        List<String> actualNames = elements.stream().map(p -> p.getName()).collect(Collectors.toList());
        List<String> expectedNames = Arrays.asList("BureauCallType", "Eligibility");
        assertEquals(expectedNames, actualNames);
    }

    @Test
    public void testAllSubDecisions() {
        TDRGElement root = dmnModelRepository.findDRGElementByName("Strategy");
        List<TDecision> elements = dmnModelRepository.allSubDecisions(root);
        dmnModelRepository.sortNamedElements(elements);

        List<String> actualNames = elements.stream().map(p -> p.getName()).collect(Collectors.toList());
        List<String> expectedNames = Arrays.asList(
                "ApplicationRiskScore", "BureauCallType", "Eligibility", "Pre-bureauAffordability", "Pre-bureauRiskCategory", "RequiredMonthlyInstallment");
        assertEquals(expectedNames, actualNames);
    }

    @Test
    public void testDirectSubInvocables() {
        TDRGElement root = dmnModelRepository.findDRGElementByName("BureauCallType");
        List<TInvocable> elements = dmnModelRepository.directSubInvocables(root);
        dmnModelRepository.sortNamedElements(elements);

        List<String> actualNames = elements.stream().map(p -> p.getName()).collect(Collectors.toList());
        List<String> expectedNames = Arrays.asList("BureauCallTypeTable");
        assertEquals(expectedNames, actualNames);
    }

    @Test
    public void testAllInvocables() {
        TDRGElement root = dmnModelRepository.findDRGElementByName("Routing");
        List<TInvocable> elements = dmnModelRepository.allInvocables(root);
        dmnModelRepository.sortNamedElements(elements);

        List<String> actualNames = elements.stream().map(p -> p.getName()).collect(Collectors.toList());
        List<String> expectedNames = Arrays.asList(
                "AffordabilityCalculation", "ApplicationRiskScoreModel", "CreditContingencyFactorTable", "InstallmentCalculation", "Post-bureauRiskCategoryTable", "RoutingRules");
        assertEquals(expectedNames, actualNames);
    }

    private DMNModelRepository readDMN(String pathName) {
        File input = new File(DMNModelRepositoryTest.class.getClassLoader().getResource(pathName).getFile());
        Pair<TDefinitions, PrefixNamespaceMappings> pair = dmnReader.read(input);
        return new DMNModelRepository(pair);
    }

}