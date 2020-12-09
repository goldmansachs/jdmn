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

import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import org.junit.Before;
import org.junit.Test;
import org.omg.spec.dmn._20191111.model.*;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class DMNModelRepositoryTest extends AbstractTest {
    private DMNModelRepository dmnModelRepository;
    private final DMNReader dmnReader = new DMNReader(LOGGER, false);

    @Before
    public void setUp() {
        String pathName = "tck/1.1/cl3/0004-lending/0004-lending.dmn";
        this.dmnModelRepository = readDMN(pathName);
    }

    @Test
    public void testFindDecisionByRef() {
        String id = "d_BureauCallType";
        TDefinitions definitions = this.dmnModelRepository.getRootDefinitions();
        String namespace = definitions.getNamespace();
        TDecision decision = this.dmnModelRepository.findDecisionByRef(null,namespace + "#" + id);
        assertEquals(id, decision.getId());
        assertEquals("BureauCallType", decision.getName());
    }

    @Test
    public void testCachedElements() {
        Set<String> cachedElements = this.dmnModelRepository.computeCachedElements(true, 1);
        List<String> expected = Arrays.asList("Pre-bureauRiskCategory", "RequiredMonthlyInstallment", "Post-bureauRiskCategory", "ApplicationRiskScore");
        assertEquals(expected, new ArrayList<>(cachedElements));

        cachedElements = this.dmnModelRepository.computeCachedElements(true, 0);
        expected = Arrays.asList(
                "Pre-bureauRiskCategory", "Pre-bureauAffordability", "RequiredMonthlyInstallment", "Post-bureauRiskCategory", "ApplicationRiskScore",
                "Post-bureauAffordability", "BureauCallType", "Eligibility");
        assertEquals(expected, new ArrayList<>(cachedElements));
    }

    @Test
    public void testDirectInputDatas() {
        TDefinitions definitions = this.dmnModelRepository.getRootDefinitions();
        TDRGElement root = this.dmnModelRepository.findDRGElementByName(definitions, "Adjudication");
        List<DRGElementReference<TInputData>> references = this.dmnModelRepository.directInputDatas(root);
        this.dmnModelRepository.sortNamedElementReferences(references);

        List<String> actual = references.stream().map(DRGElementReference::toString).collect(Collectors.toList());
        List<String> expected = Arrays.asList(
                "DMNReference(import='[]', namespace='http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b', model='Lending1', element='ApplicantData')",
                "DMNReference(import='[]', namespace='http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b', model='Lending1', element='BureauData')",
                "DMNReference(import='[]', namespace='http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b', model='Lending1', element='SupportingDocuments')"
        );
        assertEquals(expected, actual);
    }

    @Test
    public void testAllInputDatas() {
        TDefinitions definitions = this.dmnModelRepository.getRootDefinitions();
        TDRGElement root = this.dmnModelRepository.findDRGElementByName(definitions, "Pre-bureauAffordability");
        List<DRGElementReference<TInputData>> references = this.dmnModelRepository.inputDataClosure(makeRootReference(root), new DRGElementFilter(true));
        this.dmnModelRepository.sortNamedElementReferences(references);

        List<String> actual = references.stream().map(DRGElementReference::toString).collect(Collectors.toList());
        List<String> expected = Arrays.asList(
                "DMNReference(import='[]', namespace='http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b', model='Lending1', element='ApplicantData')",
                "DMNReference(import='[]', namespace='http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b', model='Lending1', element='RequestedProduct')"
        );
        assertEquals(expected, actual);
    }

    @Test
    public void testDirectSubDecisions() {
        TDefinitions definitions = this.dmnModelRepository.getRootDefinitions();
        TDRGElement root = this.dmnModelRepository.findDRGElementByName(definitions, "Strategy");
        List<DRGElementReference<TDecision>> references = this.dmnModelRepository.directSubDecisions(root);

        List<String> actual = references.stream().map(DRGElementReference::toString).collect(Collectors.toList());
        List<String> expected = Arrays.asList(
                "DMNReference(import='[]', namespace='http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b', model='Lending1', element='BureauCallType')",
                "DMNReference(import='[]', namespace='http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b', model='Lending1', element='Eligibility')"
        );
        assertEquals(expected, actual);
    }

    @Test
    public void testDirectSubInvocables() {
        TDefinitions definitions = this.dmnModelRepository.getRootDefinitions();
        TDRGElement root = this.dmnModelRepository.findDRGElementByName(definitions, "BureauCallType");
        List<DRGElementReference<TInvocable>> references = this.dmnModelRepository.directSubInvocables(root);

        List<String> actual = references.stream().map(DRGElementReference::toString).collect(Collectors.toList());
        List<String> expected = Arrays.asList(
                "DMNReference(import='[]', namespace='http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b', model='Lending1', element='BureauCallTypeTable')"
        );
        assertEquals(expected, actual);
    }

    @Test
    public void testCollectAllInputDatas() {
        this.dmnModelRepository = readDMN("composite/1.2/0003-name-conflicts/translator/");

        TDRGElement root = this.dmnModelRepository.findDRGElementByName("http://www.provider.com/definitions/model-c", "modelCDecisionBasedOnBs");
        List<DRGElementReference<TInputData>> references = this.dmnModelRepository.collectTransitiveInputDatas(makeRootReference(root));
        this.dmnModelRepository.sortNamedElementReferences(references);

        List<String> actual = references.stream().map(DRGElementReference::toString).collect(Collectors.toList());
        List<String> expected = Arrays.asList(
                "DMNReference(import='[modelB1, modelA]', namespace='http://www.provider.com/definitions/model-a', model='model-a', element='personName')",
                "DMNReference(import='[modelB2, modelB1, modelA]', namespace='http://www.provider.com/definitions/model-a', model='model-a', element='personName')"
        );
        assertEquals(expected, actual);
    }

    @Test
    public void testAllInputDatasWithImports() {
        this.dmnModelRepository = readDMN("composite/1.2/0003-name-conflicts/translator/");

        TDRGElement root = this.dmnModelRepository.findDRGElementByName("http://www.provider.com/definitions/model-c", "modelCDecisionBasedOnBs");
        List<DRGElementReference<TInputData>> references = this.dmnModelRepository.collectTransitiveInputDatas(makeRootReference(root));
        this.dmnModelRepository.sortNamedElementReferences(references);

        List<String> actual = references.stream().map(DRGElementReference::toString).collect(Collectors.toList());
        List<String> expected = Arrays.asList(
                "DMNReference(import='[modelB1, modelA]', namespace='http://www.provider.com/definitions/model-a', model='model-a', element='personName')",
                "DMNReference(import='[modelB2, modelB1, modelA]', namespace='http://www.provider.com/definitions/model-a', model='model-a', element='personName')"
        );
        assertEquals(expected, actual);
    }

    @Override
    protected URI resource(String path) {
        return tckResource(path);
    }

    private DMNModelRepository readDMN(String pathName) {
        File input = new File(resource(pathName));
        List<Pair<TDefinitions, PrefixNamespaceMappings>> pairs = this.dmnReader.readModels(input);
        return new DMNModelRepository(pairs);
    }

    private DRGElementReference<? extends TDRGElement> makeRootReference(TDRGElement root) {
        return this.dmnModelRepository.makeDRGElementReference(root);
    }
}