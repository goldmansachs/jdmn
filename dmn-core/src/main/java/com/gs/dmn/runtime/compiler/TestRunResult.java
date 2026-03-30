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
package com.gs.dmn.runtime.compiler;

import java.util.List;

public class TestRunResult {
    private long testsFound;
    private long testsStarted;
    private long testsSkipped;
    private long testsAborted;
    private long testsSucceeded;
    private long testsFailed;
    private List<TestFailure> failures;

    public TestRunResult() {
    }

    public TestRunResult(long testsFound, long testsStarted, long testsSkipped, long testsAborted,
                         long testsSucceeded, long testsFailed, List<TestFailure> failures) {
        this.testsFound = testsFound;
        this.testsStarted = testsStarted;
        this.testsSkipped = testsSkipped;
        this.testsAborted = testsAborted;
        this.testsSucceeded = testsSucceeded;
        this.testsFailed = testsFailed;
        this.failures = failures;
    }

    // Getters
    public long getTestsFound() {
        return testsFound;
    }

    public long getTestsStarted() {
        return testsStarted;
    }

    public long getTestsSkipped() {
        return testsSkipped;
    }

    public long getTestsAborted() {
        return testsAborted;
    }

    public long getTestsSucceeded() {
        return testsSucceeded;
    }

    public long getTestsFailed() {
        return testsFailed;
    }

    public List<TestFailure> getFailures() {
        return failures;
    }

    @Override
    public String toString() {
        return String.format(
                "TestRunResult{testsFound=%s, testsStarted=%s, testsSkipped=%s, testsAborted=%s, testsSucceeded=%s, testsFailed=%s, failures=%s}",
                testsFound, testsStarted, testsSkipped, testsAborted, testsSucceeded, testsFailed, failures
        );
    }
}

