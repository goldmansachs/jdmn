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

import com.gs.dmn.generated.example_credit_decision_mixed.GenerateOutputData;
import com.gs.dmn.generated.example_credit_decision_mixed.type.ApplicantImpl;
import com.gs.dmn.jmh.PerformanceUtils;
import com.gs.dmn.runtime.annotation.AnnotationSet;
import com.gs.dmn.runtime.cache.Cache;
import com.gs.dmn.runtime.cache.DefaultCache;
import com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor;
import com.gs.dmn.runtime.external.ExternalFunctionExecutor;
import com.gs.dmn.runtime.listener.EventListener;
import com.gs.dmn.runtime.listener.NopEventListener;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.gs.dmn.jmh.PerformanceUtils.printReport;

@State(Scope.Benchmark)
public class CreditDecisionMixedSignavioBenchmarkTest {
    private static final GenerateOutputData decision = new GenerateOutputData();

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testCompiler() {
        executeCompiled(System.currentTimeMillis());
    }

    private void executeCompiled(long startTime) {
        AnnotationSet annotationSet_ = new AnnotationSet();
        EventListener eventListener_ = new NopEventListener();
        ExternalFunctionExecutor externalExecutor_ = new DefaultExternalFunctionExecutor();
        Cache cache_ = new DefaultCache();

        ApplicantImpl applicant = new ApplicantImpl();
        applicant.setName("Amy");
        applicant.setAge(decision.number("38"));
        applicant.setCreditScore(decision.number("100"));
        applicant.setPriorIssues(decision.asList("Late payment"));

        BigDecimal currentRiskAppetite = decision.number("50");
        BigDecimal lendingThreshold = decision.number("25");

        List<?> result = decision.apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_, eventListener_, externalExecutor_, cache_);
//        System.out.println(result);

        long endTime = System.currentTimeMillis();
//        System.out.println(String.format("Compiled version took %s ms", endTime - startTime));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CreditDecisionMixedSignavioBenchmarkTest.class.getSimpleName())
                .warmupIterations(PerformanceUtils.WARMUP_ITERATIONS)
                .measurementIterations(PerformanceUtils.MEASUREMENT_ITERATIONS)
                .forks(1)
                .build();

        System.out.println("Print performance results for Signavio");
        Collection<RunResult> runResults = new Runner(opt).run();
        printReport(runResults, "signavio-mixed-benchmark.txt");
        System.out.println("Done");
    }
}
