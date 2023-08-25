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
package com.gs.dmn.jmh.cl3_0004_lending;

import com.gs.dmn.generated.tck.cl3_0004_lending.*;
import com.gs.dmn.generated.tck.cl3_0004_lending.type.*;
import com.gs.dmn.jmh.PerformanceUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class LendingStandardBenchmarkTest {
    private Adjudication decision = new Adjudication();

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testCompiler() {
        executeCompiled(System.currentTimeMillis());
    }

    private void executeCompiled(long startTime) {
        testCase001();

        long endTime = System.currentTimeMillis();
//        System.out.println(String.format("Compiled version took %s ms", endTime - startTime));
    }

    private void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        TApplicantData applicantData = new TApplicantDataImpl(decision.number("35"), "EMPLOYED", true, "M", new MonthlyImpl(decision.number("2000"), decision.number("6000"), decision.number("0")));
        TRequestedProduct requestedProduct = new TRequestedProductImpl(decision.number("350000"), "STANDARD LOAN", decision.number("0.0395"), decision.number("360"));
        TBureauData bureauData = new TBureauDataImpl(false, decision.number("649"));
        String supportingDocuments = "YES";

        // Check Adjudication
        new Adjudication().apply(applicantData, bureauData, supportingDocuments, annotationSet_, eventListener_, externalExecutor_, cache_);
        // Check ApplicationRiskScore
        new ApplicationRiskScore().apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_);
        // Check 'Pre-bureauRiskCategory'
        new PreBureauRiskCategory().apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_);
        // Check BureauCallType
        new BureauCallType().apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_);
        // Check 'Post-bureauRiskCategory'
        new PostBureauRiskCategory().apply(applicantData, bureauData, annotationSet_, eventListener_, externalExecutor_, cache_);
        // Check RequiredMonthlyInstallment
        new RequiredMonthlyInstallment().apply(requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_);
        // Check 'Pre-bureauAffordability'
        new PreBureauAffordability().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_);
        // Check Eligibility
        new Eligibility().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_);
        // Check Strategy
        new Strategy().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_);
        // Check 'Post-bureauAffordability'
        new PostBureauAffordability().apply(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_);
        // Check Routing
        new Routing().apply(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(LendingStandardBenchmarkTest.class.getSimpleName())
                .warmupIterations(PerformanceUtils.WARMUP_ITERATIONS)
                .measurementIterations(PerformanceUtils.MEASUREMENT_ITERATIONS)
                .forks(1)
                .build();

        System.out.println("Print performance results for Standard");
        Collection<RunResult> runResults = new Runner(opt).run();
        PerformanceUtils.printReport(runResults, "standard-benchmark.txt");
        System.out.println("Done");
    }
}
