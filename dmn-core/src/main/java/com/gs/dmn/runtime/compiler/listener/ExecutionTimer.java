/*
 * Copyright 2026.
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
package com.gs.dmn.runtime.compiler.listener;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple thread-safe timer that records a start timestamp for named events and calculates the elapsed time when ended.
 *
 * <p>Timing details and semantics:
 * <ul>
 *   <li>Timings are recorded in nanoseconds for precision.</li>
 *   <li> The ExecutionTimer records timings for the ExecutionListener lifecycle events. Each startXXX
 *   method records a start timestamp (overwriting any previous start). Each endXXX computes the
 *   elapsed duration since the matching start and accumulates it under the same logical name.</li>
 *   <li>If endXXX() is called without a matching startXXX an {@link IllegalStateException} is thrown.</li>
 * </ul>
 */
public class ExecutionTimer implements ExecutionListener {
    public static final String DMN_TRANSLATION = "DMN Translation";
    public static final String TEST_CASES_TRANSLATION = "TestCases Translation";
    public static final String COMPILATION = "Compilation";
    public static final String TEST_EXECUTION = "Test Execution";

    // store start times in nanoseconds for precision
    final ConcurrentHashMap<String, Long> startTimes = new ConcurrentHashMap<>();
    // store accumulated elapsed time (in nanoseconds) for named events
    final ConcurrentHashMap<String, Long> elapsedNanos = new ConcurrentHashMap<>();

    @Override
    public void startDMNTranslation() {
        start(DMN_TRANSLATION);
    }

    @Override
    public void endDMNTranslation() {
        end(DMN_TRANSLATION);
    }

    @Override
    public void startTestCasesTranslation() {
        start(TEST_CASES_TRANSLATION);
    }

    @Override
    public void endTestCasesTranslation() {
        end(TEST_CASES_TRANSLATION);
    }

    @Override
    public void startCompilation() {
        start(COMPILATION);
    }

    @Override
    public void endCompilation() {
        end(COMPILATION);
    }

    @Override
    public void startTestExecution() {
        start(TEST_EXECUTION);
    }

    @Override
    public void endTestExecution() {
        end(TEST_EXECUTION);
    }

    /**
     * Check whether an event has a recorded start time.
     * @param eventName non-null event identifier
     * @return true if started and not yet ended
     */
    public boolean isRunning(String eventName) {
        Objects.requireNonNull(eventName, "eventName");
        return startTimes.containsKey(eventName);
    }

    /**
     * Reset any accumulated elapsed time for the given event name.
     * Does not affect any currently running start time recorded via {@link #start(String)}.
     * @param eventName non-null event identifier
     */
    public void resetAccumulated(String eventName) {
        Objects.requireNonNull(eventName, "eventName");
        elapsedNanos.remove(eventName);
    }

    /**
     * Return the accumulated elapsed {@link Duration} (sum of all end-start pairs) for the given event
     * name, or null if no elapsed time has been recorded for that name.
     */
    public Duration getAccumulatedDuration(String eventName) {
        Objects.requireNonNull(eventName, "eventName");
        Long nanos = elapsedNanos.get(eventName);
        return nanos == null ? null : Duration.ofNanos(nanos);
    }

    /**
     * Record the start time for the given event name. If an existing start time exists it will be overwritten.
     * @param eventName non-null event identifier
     */
    private void start(String eventName) {
        Objects.requireNonNull(eventName, "eventName");
        startTimes.put(eventName, System.nanoTime());
    }

    /**
     * End the given event and calculates the elapsed time as a {@link Duration}.
     * @param eventName non-null event identifier
     * @throws IllegalStateException if the event was not previously started
     */
    private void end(String eventName) {
        Objects.requireNonNull(eventName, "eventName");
        Long start = startTimes.remove(eventName);
        if (start == null) {
            throw new IllegalStateException("No start time recorded for event: " + eventName);
        }
        long now = System.nanoTime();
        long elapsedNanos = Math.max(0, now - start);
        // record the elapsed time for this named event (accumulate if called multiple times)
        this.elapsedNanos.merge(eventName, elapsedNanos, Long::sum);
    }
}

