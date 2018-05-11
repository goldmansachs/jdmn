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
package com.gs.dmn.tck.cl3_0020_vacation_days;

import com.gs.dmn.AbstractHandwrittenDecisionTest;
import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.log.NopBuildLogger;
import com.gs.dmn.runtime.annotation.AnnotationSet;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironmentFactory;
import com.gs.dmn.transformation.NameTransformer;
import com.gs.dmn.transformation.ToQuotedNameTransformer;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class HandwrittenDecisionTest extends AbstractHandwrittenDecisionTest {
    private final DMNDialectDefinition dialectDefinition = new StandardDMNDialectDefinition();
    private final TotalVacationDays decision = new TotalVacationDays();
    private final NameTransformer nameTransformer = new ToQuotedNameTransformer(new NopBuildLogger());

    @Test
    public void applyCompiler() {
        AnnotationSet annotationSet = new AnnotationSet();
        assertEquals("27", decision.apply("16", "1", annotationSet).toPlainString());
        assertEquals("22", decision.apply("25", "5", annotationSet).toPlainString());
        assertEquals("24", decision.apply("25", "20", annotationSet).toPlainString());
        assertEquals("30", decision.apply("44", "30", annotationSet).toPlainString());
        assertEquals("24", decision.apply("50", "20", annotationSet).toPlainString());
        assertEquals("30", decision.apply("50", "30", annotationSet).toPlainString());
        assertEquals("30", decision.apply("60", "20", annotationSet).toPlainString());
    }

    @Test
    public void applyInterpreter() throws Exception {
        checkInterpreter("27", "16", "1");
        checkInterpreter("22", "25", "5");
        checkInterpreter("24", "25", "20");
        checkInterpreter("30", "44", "30");
        checkInterpreter("24", "50", "20");
        checkInterpreter("30", "50", "30");
        checkInterpreter("30", "60", "20");
    }

    private void checkInterpreter(String expected, String i1, String i2) throws Exception {
        FEELLib lib = this.dialectDefinition.createFEELLib();

        String pathName = "tck/cl3/0020-vacation-days.dmn";
        DMNModelRepository repository = readDMN(pathName);
        nameTransformer.transform(repository);
        DMNInterpreter interpreter = this.dialectDefinition.createDMNInterpreter(repository);

        RuntimeEnvironment environment = RuntimeEnvironmentFactory.instance().makeEnvironment();
        environment.bind("Age", lib.number(i1));
        environment.bind(nameTransformer.transformName("Years of Service"), lib.number(i2));

        Object actual = interpreter.evaluate(nameTransformer.transformName("Total Vacation Days"), environment);

        assertEquals(expected, ((BigDecimal) actual).toPlainString());
    }

    @Override
    protected void applyDecision() {
        AnnotationSet annotationSet = new AnnotationSet();
        decision.apply((String) null, (String) null, annotationSet);
    }

}