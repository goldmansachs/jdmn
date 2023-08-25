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

import com.gs.dmn.runtime.DMNRuntimeException;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.results.format.ResultFormat;
import org.openjdk.jmh.results.format.ResultFormatFactory;
import org.openjdk.jmh.results.format.ResultFormatType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Collection;

public class PerformanceUtils {
    public static final int WARMUP_ITERATIONS = 10;
    public static final int MEASUREMENT_ITERATIONS = 20;

    public static void printReport(Collection<RunResult> runResults, String fileName) {
        File outputFile = new File(fileName);
        PrintStream out;
        try {
            out = new PrintStream(outputFile);
        } catch (FileNotFoundException e) {
            throw new DMNRuntimeException(e);
        }
        ResultFormat resultFormat = ResultFormatFactory.getInstance(ResultFormatType.TEXT, out);
        resultFormat.writeOut(runResults);
    }
}
