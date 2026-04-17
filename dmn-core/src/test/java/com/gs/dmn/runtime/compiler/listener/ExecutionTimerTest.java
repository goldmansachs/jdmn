package com.gs.dmn.runtime.compiler.listener;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.gs.dmn.runtime.compiler.listener.ExecutionTimer.*;
import static org.junit.jupiter.api.Assertions.*;

class ExecutionTimerTest {
    // small sleep to ensure measured durations are non-zero
    private static final long SLEEP_MS = 5L;

    @Test
    void testAllLifecycleEventsRecordDuration() throws Exception {
        ExecutionTimer timer = new ExecutionTimer();

        // DMN translation
        timer.startDMNTranslation();
        assertTrue(timer.isRunning(DMN_TRANSLATION));
        Thread.sleep(SLEEP_MS);
        timer.endDMNTranslation();
        assertFalse(timer.isRunning(DMN_TRANSLATION));
        Duration d1 = timer.getAccumulatedDuration(DMN_TRANSLATION);
        assertNotNull(d1);
        assertTrue(d1.toNanos() > 0, "DMN Translation should record a positive duration");

        // Test cases translation
        timer.startTestCasesTranslation();
        assertTrue(timer.isRunning(TEST_CASES_TRANSLATION));
        Thread.sleep(SLEEP_MS);
        timer.endTestCasesTranslation();
        assertFalse(timer.isRunning(TEST_CASES_TRANSLATION));
        Duration d2 = timer.getAccumulatedDuration(TEST_CASES_TRANSLATION);
        assertNotNull(d2);
        assertTrue(d2.toNanos() > 0, "TestCases Translation should record a positive duration");

        // Compilation
        timer.startCompilation();
        assertTrue(timer.isRunning(COMPILATION));
        Thread.sleep(SLEEP_MS);
        timer.endCompilation();
        assertFalse(timer.isRunning(COMPILATION));
        Duration d3 = timer.getAccumulatedDuration("Compilation");
        assertNotNull(d3);
        assertTrue(d3.toNanos() > 0, "Compilation should record a positive duration");

        // Test execution
        timer.startTestExecution();
        assertTrue(timer.isRunning(TEST_EXECUTION));
        Thread.sleep(SLEEP_MS);
        timer.endTestExecution();
        assertFalse(timer.isRunning(TEST_EXECUTION));
        Duration d4 = timer.getAccumulatedDuration(TEST_EXECUTION);
        assertNotNull(d4);
        assertTrue(d4.toNanos() > 0, "Test Execution should record a positive duration");
    }

    @Test
    void testAccumulationAcrossMultipleCycles() throws Exception {
        ExecutionTimer timer = new ExecutionTimer();
        String name = "Compilation";

        // ensure clean state
        timer.resetAccumulated(name);

        timer.startCompilation();
        Thread.sleep(SLEEP_MS);
        timer.endCompilation();
        Duration first = timer.getAccumulatedDuration(name);
        assertNotNull(first);
        assertTrue(first.toNanos() > 0);

        // second cycle
        timer.startCompilation();
        Thread.sleep(SLEEP_MS);
        timer.endCompilation();
        Duration total = timer.getAccumulatedDuration(name);
        assertNotNull(total);
        assertTrue(total.toNanos() > first.toNanos(), "Accumulated duration should increase after a second cycle");
    }

    @Test
    void testEndWithoutStart() {
        assertThrows(IllegalStateException.class, () -> {
            ExecutionTimer timer = new ExecutionTimer();
            timer.endCompilation();
        });
    }
}