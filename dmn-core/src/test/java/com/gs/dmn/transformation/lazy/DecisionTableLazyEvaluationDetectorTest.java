/*
 * Copyright 2026 Patrick Ribbsaeter.
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DecisionTableLazyEvaluationDetectorTest extends AbstractTest {
    private static final List<String> EXPECTED_DECISIONS = List.of(
            "http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b#BureauCallType",
            "http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b#Eligibility"
    );

    private DMNModelRepository dmnModelRepository;
    private final DMNSerializer dmnReader = new XMLDMNSerializer(LOGGER, this.inputParameters);

    @BeforeEach
    public void setUp() {
        this.dmnModelRepository = readDMN("dmn/input/1.1/0004-lending.dmn");
    }

    @Test
    public void testLazyEvaluationOptimisationWithDefaultConstructor() {
        DecisionTableLazyEvaluationDetector detector = new DecisionTableLazyEvaluationDetector();

        LazyEvaluationOptimisation optimisation = detector.detect(this.dmnModelRepository);

        assertEquals(EXPECTED_DECISIONS, new ArrayList<>(optimisation.getLazyEvaluatedDecisions()));
        assertFalse(optimisation.isLazyEvaluated(
                "http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b#Pre-bureauRiskCategory"
        ));
    }

    @Test
    public void testLazyEvaluationOptimisation() {
        DecisionTableLazyEvaluationDetector detector = new DecisionTableLazyEvaluationDetector(
                new InputParameters(), LOGGER
        );

        LazyEvaluationOptimisation optimisation = detector.detect(this.dmnModelRepository);

        assertEquals(EXPECTED_DECISIONS, new ArrayList<>(optimisation.getLazyEvaluatedDecisions()));
    }

    private DMNModelRepository readDMN(String pathName) {
        File input = new File(resource(pathName));
        TDefinitions definitions = this.dmnReader.readModel(input);
        return new DMNModelRepository(definitions);
    }
}
