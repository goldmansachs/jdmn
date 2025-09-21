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

import com.gs.dmn.ast.*;
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.serialization.xstream.XMLDMNSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.io.File;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class DMNModelRepositoryTest extends AbstractTest {
    private DMNModelRepository dmnModelRepository;
    private final DMNSerializer dmnSerializer = new XMLDMNSerializer(LOGGER, this.inputParameters);

    @BeforeEach
    public void setUp() {
        String pathName = "tck/1.1/cl3/0004-lending/0004-lending.dmn";
        this.dmnModelRepository = readModels(pathName);
    }

    @Test
    public void testFindDecisionByRef() {
        String id = "d_BureauCallType";
        TDefinitions definitions = this.dmnModelRepository.getRootDefinitions();
        String namespace = definitions.getNamespace();
        TDecision decision = this.dmnModelRepository.findDecisionByRef(null, namespace + "#" + id);
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
        List<String> expected = Collections.singletonList(
                "DMNReference(import='[]', namespace='http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b', model='Lending1', element='BureauCallTypeTable')"
        );
        assertEquals(expected, actual);
    }

    @Test
    public void testCollectAllInputDatas() {
        this.dmnModelRepository = readModels("composite/1.2/0003-name-conflicts/translator/");

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
        this.dmnModelRepository = readModels("composite/1.2/0003-name-conflicts/translator/");

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
    public void testGetModelForCyclicTypRefs() {
        // Read test models
        this.dmnModelRepository = readModels("other/1.5/cycles-no-prefix/translator/");

        // Find model-a and model-b
        TDefinitions modelA = this.dmnModelRepository.findModelByName("model-a");
        assertNotNull(modelA);
        TDefinitions modelB = this.dmnModelRepository.findModelByName("model-b");
        assertNotNull(modelB);

        // Find ItemDefinitions to test
        TItemDefinition t1 = findItemDefinition(modelA, "t1");
        TItemDefinition t2 = findItemDefinition(modelA, "t2");
        TItemDefinition other = findItemDefinition(modelB, "other");
        TItemDefinition node = findItemDefinition(modelA, "node");
        TItemDefinition key = findItemDefinition(node, "key");
        TItemDefinition defReference = findItemDefinition(node, "def");
        TItemDefinition defDefinition = findItemDefinition(modelB, "def");
        TItemDefinition next = findItemDefinition(node, "next");

        // Test first model
        assertEquals(modelA, this.dmnModelRepository.getModel(t1));
        assertEquals(modelA, this.dmnModelRepository.getModel(t2));
        assertEquals(modelA, this.dmnModelRepository.getModel(node));
        assertEquals(modelA, this.dmnModelRepository.getModel(key));
        assertEquals(modelA, this.dmnModelRepository.getModel(next));
        assertEquals(modelA, this.dmnModelRepository.getModel(defReference));

        // Test second model
        assertEquals(modelB, this.dmnModelRepository.getModel(other));
        assertEquals(modelB, this.dmnModelRepository.getModel(defDefinition));
    }

    @Test
    public void testLookupForCyclicTypRefsWhenNoPrefix() {
        // Read test models
        this.dmnModelRepository = readModels("other/1.5/cycles-no-prefix/translator/");
        doTest("", "");
    }

    @Test
    public void testLookupForCyclicTypRefsWhenPrefix() {
        // Read test models
        this.dmnModelRepository = readModels("other/1.5/cycles-with-prefix/translator/");
        doTest("a", "b");
    }

    private void doTest(String prefixA, String prefixB) {
        // Find model-a and model-b
        TDefinitions modelA = this.dmnModelRepository.findModelByName("model-a");
        assertNotNull(modelA);
        TDefinitions modelB = this.dmnModelRepository.findModelByName("model-b");
        assertNotNull(modelB);

        // Find ItemDefinitions to test
        TItemDefinition t1 = findItemDefinition(modelA, "t1");
        TItemDefinition t2 = findItemDefinition(modelA, "t2");
        TItemDefinition other = findItemDefinition(modelB, "other");
        TItemDefinition node = findItemDefinition(modelA, "node");
        TItemDefinition key = findItemDefinition(node, "key");
        TItemDefinition defReference = findItemDefinition(node, "def");
        TItemDefinition defDefinition = findItemDefinition(modelB, "def");
        TItemDefinition next = findItemDefinition(node, "next");

        // Test findItemDefinitionAndAllowedValuesFor()
        assertThrows(SemanticError.class, () -> {
            this.dmnModelRepository.findItemDefinitionAndAllowedValuesFor(t1);
        });
        assertThrows(SemanticError.class, () -> {
            this.dmnModelRepository.findItemDefinitionAndAllowedValuesFor(t2);
        });
        assertThrows(SemanticError.class, () -> {
            this.dmnModelRepository.findItemDefinitionAndAllowedValuesFor(other);
        });
        assertEquals(node, this.dmnModelRepository.findItemDefinitionAndAllowedValuesFor(node).getLeft());
        assertEquals(key, this.dmnModelRepository.findItemDefinitionAndAllowedValuesFor(key).getLeft());
        assertEquals(defDefinition, this.dmnModelRepository.findItemDefinitionAndAllowedValuesFor(defReference).getLeft());
        assertEquals(node, this.dmnModelRepository.findItemDefinitionAndAllowedValuesFor(next).getLeft());

        // Test next() for root types and member types
        assertEquals(t2, this.dmnModelRepository.next(t1));
        assertEquals(other, this.dmnModelRepository.next(t2));
        assertEquals(t1, this.dmnModelRepository.next(other));

        assertNull(this.dmnModelRepository.next(node));
        assertNull(this.dmnModelRepository.next(key));
        assertEquals(defDefinition, this.dmnModelRepository.next(defReference));
        assertEquals(node, this.dmnModelRepository.next(next));

        // Test lookupItemDefinition() for root types and member types
        assertEquals(t1, this.dmnModelRepository.lookupItemDefinition(modelA, QualifiedName.toQualifiedName("", "t1")));
        assertEquals(t1, this.dmnModelRepository.lookupItemDefinition(modelB, QualifiedName.toQualifiedName(prefixA, "t1")));
        assertEquals(t2, this.dmnModelRepository.lookupItemDefinition(modelA, QualifiedName.toQualifiedName("","t2")));
        assertEquals(t2, this.dmnModelRepository.lookupItemDefinition(modelB, QualifiedName.toQualifiedName(prefixA,"t2")));
        assertEquals(other, this.dmnModelRepository.lookupItemDefinition(modelB, QualifiedName.toQualifiedName("","other")));
        assertEquals(other, this.dmnModelRepository.lookupItemDefinition(modelA, QualifiedName.toQualifiedName(prefixB,"other")));

        assertEquals(node, this.dmnModelRepository.lookupItemDefinition(modelA, QualifiedName.toQualifiedName("", "node")));
        assertNull(this.dmnModelRepository.lookupItemDefinition(modelA, QualifiedName.toQualifiedName("","key")));
        assertNull(this.dmnModelRepository.lookupItemDefinition(modelB, QualifiedName.toQualifiedName(prefixA,"key")));
        assertEquals(defDefinition, this.dmnModelRepository.lookupItemDefinition(modelA, QualifiedName.toQualifiedName(prefixB,"def")));
        assertEquals(defDefinition, this.dmnModelRepository.lookupItemDefinition(modelB, QualifiedName.toQualifiedName("","def")));
        if (StringUtils.isBlank(prefixA)) {
            // Finds definition
            assertEquals(defDefinition, this.dmnModelRepository.lookupItemDefinition(modelA, QualifiedName.toQualifiedName("","def")));
        } else {
            // Cannot find reference as is a child
            assertNull(this.dmnModelRepository.lookupItemDefinition(modelA, QualifiedName.toQualifiedName("","def")));
        }
        if (StringUtils.isBlank(prefixB)) {
            // Finds definition
            assertEquals(defDefinition, this.dmnModelRepository.lookupItemDefinition(modelB, QualifiedName.toQualifiedName(prefixA, "def")));
        } else {
            // Cannot find reference as is a child
            assertNull(this.dmnModelRepository.lookupItemDefinition(modelB, QualifiedName.toQualifiedName(prefixA, "def")));
        }
        assertNull(this.dmnModelRepository.lookupItemDefinition(modelA, QualifiedName.toQualifiedName("","next")));
        assertNull(this.dmnModelRepository.lookupItemDefinition(modelB, QualifiedName.toQualifiedName(prefixA,"next")));
    }

    private TItemDefinition findItemDefinition(TDefinitions modelA, String name) {
        return modelA.getItemDefinition().stream().filter(i -> i.getName().equals(name)).findFirst().orElseThrow();
    }

    private TItemDefinition findItemDefinition(TItemDefinition parent, String name) {
        return parent.getItemComponent().stream().filter(i -> i.getName().equals(name)).findFirst().orElseThrow();
    }

    @Override
    protected URI resource(String path) {
        return tckResource(path);
    }

    private DMNModelRepository readModels(String pathName) {
        File input = new File(resource(pathName));
        List<TDefinitions> definitionsList = this.dmnSerializer.readModels(input);
        return new DMNModelRepository(definitionsList);
    }

    private DRGElementReference<? extends TDRGElement> makeRootReference(TDRGElement root) {
        return this.dmnModelRepository.makeDRGElementReference(root);
    }
}