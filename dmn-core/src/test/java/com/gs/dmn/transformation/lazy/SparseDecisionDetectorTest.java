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
package com.gs.dmn.transformation.lazy;

import com.gs.dmn.AbstractTest;
import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import com.gs.dmn.transformation.InputParameters;
import org.junit.Before;
import org.junit.Test;
import org.omg.spec.dmn._20191111.model.TDRGElement;
import org.omg.spec.dmn._20191111.model.TDecisionTable;
import org.omg.spec.dmn._20191111.model.TDefinitions;
import org.omg.spec.dmn._20191111.model.TExpression;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SparseDecisionDetectorTest extends AbstractTest {
    private SparseDecisionDetector detector;
    private DMNModelRepository dmnModelRepository;
    private final DMNReader dmnReader = new DMNReader(LOGGER, false);

    @Before
    public void setUp() {
        String pathName = "dmn/input/1.1/0004-lending.dmn";
        dmnModelRepository = readDMN(pathName);
    }

    @Test
    public void testLazyEvaluationOptimisation() {
        Map<String, String> inputParametersMap = new LinkedHashMap<String, String>() {{ put("sparsityThreshold", "0.10");}};
        detector = new SparseDecisionDetector(makeInputParameters(inputParametersMap), LOGGER);
        LazyEvaluationOptimisation lazyEvaluationOptimisation = detector.detect(dmnModelRepository);

        assertEquals(Arrays.asList("BureauCallType", "Eligibility"), new ArrayList<>(lazyEvaluationOptimisation.getLazyEvaluatedDecisions()));
    }

    @Test
    public void testIsSparseDecisionTable() {
        TDefinitions definitions = this.dmnModelRepository.getRootDefinitions();
        checkDecisionTable(dmnModelRepository.findDRGElementByName(definitions, "EligibilityRules"), 0.75, true);
        checkDecisionTable(dmnModelRepository.findDRGElementByName(definitions, "Strategy"), 0.75, false);
    }

    private void checkDecisionTable(TDRGElement element, Double sparsityThreshold, boolean expectedResult) {
        Map<String, String> inputParametersMap = new LinkedHashMap<String, String>() {{ put("sparsityThreshold", sparsityThreshold.toString());}};
        this.detector = new SparseDecisionDetector(makeInputParameters(inputParametersMap), LOGGER);
        TExpression expression = this.dmnModelRepository.expression(element);
        assertEquals(expectedResult, this.detector.isSparseDecisionTable((TDecisionTable) expression, sparsityThreshold));
    }

    private DMNModelRepository readDMN(String pathName) {
        File input = new File(resource(pathName));
        Pair<TDefinitions, PrefixNamespaceMappings> pair = dmnReader.read(input);
        return new DMNModelRepository(pair);
    }

    protected InputParameters makeInputParameters(Map<String, String> inputParameters) {
        return new InputParameters(makeInputParametersMap(inputParameters));
    }

    private Map<String, String> makeInputParametersMap(Map<String, String> inputParameters) {
        Map<String, String> map = super.makeInputParametersMap();
        map.putAll(inputParameters);
        return map;
    }
}