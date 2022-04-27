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
package com.gs.dmn.jmh.example_credit_decision_mixed;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.DRGElementReference;
import com.gs.dmn.ast.TDecision;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.jmh.ApplicantImpl;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.SignavioTestConstants;
import com.gs.dmn.signavio.dialect.MixedJavaTimeSignavioDMNDialectDefinition;
import com.gs.dmn.signavio.feel.lib.MixedJavaTimeSignavioLib;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.InputParameters;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class CredDecMixedBenchmarkTest {
    private static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(CredDecMixedBenchmarkTest.class));

    private static final DMNDialectDefinition<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration, TestLab> dialectDefinition = new MixedJavaTimeSignavioDMNDialectDefinition();
    private static final DMNReader dmnReader = dialectDefinition.createDMNReader(LOGGER, makeInputParameters());
    private static InputParameters makeInputParameters() {
        Map<String, String> inputParams = new LinkedHashMap<>();
        inputParams.put("dmnVersion", "1.1");
        inputParams.put("modelVersion", "1.0");
        inputParams.put("platformVersion", "1.0");
        inputParams.put("signavioSchemaNamespace", SignavioTestConstants.SIG_EXT_NAMESPACE);
        return new InputParameters(inputParams);
    }

    private static final MixedJavaTimeSignavioLib decision = (MixedJavaTimeSignavioLib) dialectDefinition.createFEELLib();

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testInterpreter() throws Exception {
        executeInterpreter(System.currentTimeMillis());
    }

    public void executeInterpreter(long startTime) throws Exception {
        ApplicantImpl applicant = new ApplicantImpl();
        applicant.setName("Amy");
        applicant.setAge(decision.number("38"));
        applicant.setCreditScore(decision.number("100"));
        applicant.setPriorIssues(decision.asList("Late payment"));

        BigDecimal currentRiskAppetite = decision.number("50");
        BigDecimal lendingThreshold = decision.number("25");

        String pathName = "exported/dmn/complex/Example credit decision.dmn";
        DMNModelRepository repository = readDMN(pathName);
        DMNInterpreter<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> interpreter = dialectDefinition.createDMNInterpreter(repository, new InputParameters());

        Map<String, Object> inputRequirements = new LinkedHashMap<>();
        inputRequirements.put("applicant", applicant);
        inputRequirements.put("currentRiskAppetite", currentRiskAppetite);
        inputRequirements.put("lendingThreshold", lendingThreshold);

        TDecision decision = (TDecision) repository.findDRGElementByName(repository.getRootDefinitions(), "generateOutputData");
        DRGElementReference<TDecision> reference = repository.makeDRGElementReference(decision);
        Object result = interpreter.evaluateDecision(reference.getNamespace(), reference.getElementName(), inputRequirements);
        System.out.println(result);

        long endTime = System.currentTimeMillis();
        System.out.println(String.format("Interpreted version took %s ms", endTime - startTime));
    }

    private DMNModelRepository readDMN(String pathName) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(pathName).toURI().toURL();
        return new SignavioDMNModelRepository(dmnReader.read(url));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CredDecMixedBenchmarkTest.class.getSimpleName())
                .warmupIterations(10)
                .measurementIterations(20)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
