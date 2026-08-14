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
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.serialization.xstream.XMLDMNSerializer;
import com.gs.dmn.transformation.InputParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlwaysLazyEvaluationDetectorTest extends AbstractTest {
    private DMNModelRepository dmnModelRepository;
    private final DMNSerializer dmnReader = new XMLDMNSerializer(LOGGER, this.inputParameters);

    @BeforeEach
    public void setUp() {
        String pathName = "dmn/input/1.1/0004-lending.dmn";
        this.dmnModelRepository = readDMN(pathName);
    }

    @Test
    public void testLazyEvaluationOptimisationWithDefaultConstructor() {
        AlwaysLazyEvaluationDetector detector = new AlwaysLazyEvaluationDetector();
        LazyEvaluationOptimisation lazyEvaluationOptimisation = detector.detect(this.dmnModelRepository);

        List<String> expected = List.of(
                "http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b#BureauCallType",
                "http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b#Eligibility"
        );
        assertEquals(expected, normalize(lazyEvaluationOptimisation.getLazyEvaluatedDecisions()));
    }

    @Test
    public void testLazyEvaluationOptimisation() {
        Map<String, String> inputParametersMap = new LinkedHashMap<>();
        AlwaysLazyEvaluationDetector detector = new AlwaysLazyEvaluationDetector(makeInputParameters(inputParametersMap), LOGGER);
        LazyEvaluationOptimisation lazyEvaluationOptimisation = detector.detect(this.dmnModelRepository);

        List<String> expected = List.of(
                "http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b#BureauCallType",
                "http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b#Eligibility"
        );
        assertEquals(expected, normalize(lazyEvaluationOptimisation.getLazyEvaluatedDecisions()));
    }

    private DMNModelRepository readDMN(String pathName) {
        File input = new File(resource(pathName));
        TDefinitions definitions = this.dmnReader.readModel(input);
        return new DMNModelRepository(definitions);
    }

    protected InputParameters makeInputParameters(Map<String, String> inputParameters) {
        return new InputParameters(makeInputParametersMap(inputParameters));
    }

    private Map<String, String> makeInputParametersMap(Map<String, String> inputParameters) {
        Map<String, String> map = super.makeInputParametersMap();
        map.putAll(inputParameters);
        return map;
    }

    private List<String> normalize(Set<String> lazyEvaluatedDecisions) {
        ArrayList<String> names = new ArrayList<>(lazyEvaluatedDecisions);
        names.sort(String::compareTo);
        return names;
    }
}