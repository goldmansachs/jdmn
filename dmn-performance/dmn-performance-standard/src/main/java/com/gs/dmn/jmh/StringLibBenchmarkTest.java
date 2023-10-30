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
package com.gs.dmn.jmh;

import com.gs.dmn.feel.lib.PureJavaTimeFEELLib;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class StringLibBenchmarkTest {
    private PureJavaTimeFEELLib lib = new PureJavaTimeFEELLib();

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testLib() {
//        long startTime = System.currentTimeMillis();

        lib.matches("1234","[a-z]{3}");

//        long endTime = System.currentTimeMillis();
//        System.out.println(String.format("Compiled version took %s ms", endTime - startTime));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(StringLibBenchmarkTest.class.getSimpleName())
                .warmupIterations(PerformanceUtils.WARMUP_ITERATIONS)
                .measurementIterations(PerformanceUtils.MEASUREMENT_ITERATIONS)
                .forks(1)
                .build();

        System.out.println("Print performance results for Standard");
        Collection<RunResult> runResults = new Runner(opt).run();
        PerformanceUtils.printReport(runResults, "string-benchmark.txt");
        System.out.println("Done");
    }
}
