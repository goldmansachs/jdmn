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
package com.gs.dmn.transformation.lazy;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.DMNModelRepositoryTest;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.serialization.DMNReader;
import org.junit.Before;
import org.junit.Test;
import org.omg.spec.dmn._20180521.model.TDRGElement;
import org.omg.spec.dmn._20180521.model.TDecisionTable;
import org.omg.spec.dmn._20180521.model.TExpression;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SparseDecisionDetectorTest {
    private static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(DMNModelRepositoryTest.class));

    private SparseDecisionDetector detector;
    private DMNModelRepository dmnModelRepository;
    private final DMNReader dmnReader = new DMNReader(LOGGER, false);

    @Before
    public void setUp() {
        String pathName = "dmn/input/0004-lending.dmn";
        dmnModelRepository = readDMN(pathName);
    }

    @Test
    public void testLazyEvaluationOptimisation() {
        Map<String, String> inputParameters = new LinkedHashMap<String, String>() {{ put("sparsityThreshold", "0.10");}};
        detector = new SparseDecisionDetector(inputParameters, LOGGER);
        LazyEvaluationOptimisation lazyEvaluationOptimisation = detector.detect(dmnModelRepository);

        assertEquals(Arrays.asList("BureauCallType", "Eligibility"), new ArrayList<>(lazyEvaluationOptimisation.getLazyEvaluatedDecisions()));
    }

    @Test
    public void testIsSparseDecisionTable() {
        checkDecisionTable(dmnModelRepository.findDRGElementByName("EligibilityRules"), 0.75, true);
        checkDecisionTable(dmnModelRepository.findDRGElementByName("Strategy"), 0.75, false);
    }

    private void checkDecisionTable(TDRGElement element, Double sparsityThreshold, boolean expectedResult) {
        Map<String, String> inputParameters = new LinkedHashMap<String, String>() {{ put("sparsityThreshold", sparsityThreshold.toString());}};
        detector = new SparseDecisionDetector(inputParameters, LOGGER);
        TExpression expression = dmnModelRepository.expression(element);
        assertEquals(expectedResult, detector.isSparseDecisionTable((TDecisionTable) expression, sparsityThreshold));
    }

    private DMNModelRepository readDMN(String pathName) {
        File input = new File(DMNModelRepositoryTest.class.getClassLoader().getResource(pathName).getFile());
        return dmnReader.read(input);
    }
}