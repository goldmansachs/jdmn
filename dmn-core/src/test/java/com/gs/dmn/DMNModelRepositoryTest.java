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
package com.gs.dmn;

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.serialization.DMNReader;
import org.junit.Before;
import org.junit.Test;
import org.omg.spec.dmn._20151101.dmn.TDMNElement;
import org.omg.spec.dmn._20151101.dmn.TDecision;
import org.omg.spec.dmn._20151101.dmn.TDefinitions;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class DMNModelRepositoryTest {
    private static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(DMNModelRepositoryTest.class));

    private DMNModelRepository dmnModelRepository;
    private DMNReader dmnReader = new DMNReader(LOGGER, false);

    @Before
    public void setUp() throws Exception {
        String pathName = "dmn/input/0004-lending.dmn";
        TDMNElement root = readDMN(pathName);
        dmnModelRepository = new DMNModelRepository((TDefinitions) root);
    }

    @Test
    public void testFindDecisionById() {
        String id = "d_BureauCallType";
        TDecision decision = dmnModelRepository.findDecisionById(id);
        assertEquals(id, decision.getId());
        assertEquals("BureauCallType", decision.getName());
    }

    @Test
    public void testTopologicalSort() {
        TDMNElement root = dmnModelRepository.findDRGElementByName("Strategy");

        List<TDecision> decisions = dmnModelRepository.topologicalSort((TDecision)root);

        List<String> actualNames = decisions.stream().map(d -> d.getName()).collect(Collectors.toList());
        List<String> expectedNames = Arrays.asList("ApplicationRiskScore", "Pre-bureauRiskCategory", "BureauCallType", "RequiredMonthlyInstallment", "Pre-bureauAffordability", "Eligibility", "Strategy");
        assertEquals(expectedNames, actualNames);
    }

    private TDMNElement readDMN(String pathName) throws Exception {
        File input = new File(DMNModelRepositoryTest.class.getClassLoader().getResource(pathName).getFile());
        return dmnReader.read(input);
    }

}