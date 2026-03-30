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

import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation: runs JUnit 5 tests in-memory from provided binary code.
 */
public class InMemoryJUnitRunner {
    // Implementation uses package-level helper classes extracted into separate files

    /**
     * Run JUnit 5 tests found in them.
     * Returns a TestRunResult with counts and failures.
     */
    public TestRunResult run(Collection<String> qualifiedClassNames, Map<String, byte[]> classBytes) throws Exception {
        if (classBytes == null || classBytes.isEmpty()) {
            return new TestRunResult(0, 0, 0, 0, 0, 0, Collections.emptyList());
        }

        // Load classes into InMemoryClassLoader
        InMemoryClassLoader classLoader = new InMemoryClassLoader(getClass().getClassLoader(), classBytes);
        List<Class<?>> testClasses = new ArrayList<>();
        for (String qualifiedName : qualifiedClassNames) {
            Class<?> cls = classLoader.loadClass(qualifiedName);
            testClasses.add(cls);
        }

        // Build and execute JUnit Launcher request
        try {
            List<ClassSelector> classSelectors = testClasses.stream().map(DiscoverySelectors::selectClass).collect(Collectors.toList());
            LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                    .selectors(classSelectors)
                    .build();
            Launcher launcher = LauncherFactory.create();
            SummaryGeneratingListener listener = new SummaryGeneratingListener();
            launcher.registerTestExecutionListeners(listener);
            launcher.execute(request);

            // TestExecutionSummary summary = listener.getSummary();
            TestExecutionSummary summary = listener.getSummary();

            List<TestFailure> failures = summary.getFailures().stream().map(this::makeFailure).toList();
            return new TestRunResult(
                    summary.getTestsFoundCount(),
                    summary.getTestsStartedCount(),
                    summary.getTestsSkippedCount(),
                    summary.getTestsAbortedCount(),
                    summary.getTestsSucceededCount(),
                    summary.getTestsFailedCount(),
                    failures
            );
        } catch (Exception e) {
            throw new IllegalStateException("Error when executing tests", e);
        }
    }

    static class InMemoryClassLoader extends ClassLoader {
        private final Map<String, byte[]> classes;

        InMemoryClassLoader(ClassLoader parent, Map<String, byte[]> classes) {
            super(parent);
            this.classes = classes;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] b = classes.get(name);
            if (b != null) {
                return defineClass(name, b, 0, b.length);
            }
            return super.findClass(name);
        }
    }

    private TestFailure makeFailure(TestExecutionSummary.Failure junitFailure) {
        return new TestFailure(junitFailure);
    }
}

