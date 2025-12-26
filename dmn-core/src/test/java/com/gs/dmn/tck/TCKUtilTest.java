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
package com.gs.dmn.tck;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.TDecision;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.JavaTimeDMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.tck.ast.ResultNode;
import com.gs.dmn.tck.ast.TestCase;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TCKUtilTest {
    @Test
    public void testExtractResultNodeInfoListPreservesOrder() {
        JavaTimeDMNDialectDefinition dialect = new JavaTimeDMNDialectDefinition();
        TDefinitions definitions = new TDefinitions();
        definitions.setName("model");
        definitions.setNamespace("http://example");

        TDecision decision = new TDecision();
        decision.setName("Decision1");
        definitions.getDrgElement().add(decision);

        DMNModelRepository repository = new DMNModelRepository(definitions);
        BasicDMNToNativeTransformer<Type, DMNContext> transformer =
                dialect.createBasicTransformer(repository, new NopLazyEvaluationDetector(), new InputParameters());
        FEELLib<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> feelLib = dialect.createFEELLib();

        TCKUtil<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> tckUtil =
                new TCKUtil<>(transformer, feelLib);

        TestCases testCases = new TestCases();
        testCases.setModelName("model.dmn");
        testCases.setNamespace("http://example");

        TestCase testCase = new TestCase();
        testCase.setType(com.gs.dmn.tck.ast.TestCaseType.DECISION);

        ResultNode first = new ResultNode();
        first.setName("Decision1");
        ResultNode second = new ResultNode();
        second.setName("Decision1");

        testCase.getResultNode().add(first);
        testCase.getResultNode().add(second);
        testCases.getTestCase().add(testCase);

        List<ResultNodeInfo> infos = tckUtil.extractResultNodeInfoList(testCases, testCase);
        assertEquals(2, infos.size());
        assertEquals("Decision1", infos.get(0).getNodeName());
        assertEquals("Decision1", infos.get(1).getNodeName());
    }

    @Test
    public void testTestCaseIdUsesNativeFriendlyName() {
        JavaTimeDMNDialectDefinition dialect = new JavaTimeDMNDialectDefinition();
        DMNModelRepository repository = new DMNModelRepository();
        BasicDMNToNativeTransformer<Type, DMNContext> transformer =
                dialect.createBasicTransformer(repository, new NopLazyEvaluationDetector(), new InputParameters());

        TCKUtil<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> tckUtil =
                new TCKUtil<>(transformer, dialect.createFEELLib());

        TestCase testCase = new TestCase();
        testCase.setId("A B");
        assertEquals("AB", tckUtil.testCaseId(testCase));
    }
}
