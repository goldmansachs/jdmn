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

import org.apache.commons.lang3.StringUtils;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.util.Optional;

class TestFailure {
    private static String extractClassName(Optional<TestSource> source) {
        String className = "";
        if (source.isPresent()) {
            TestSource s = source.get();
            if (s instanceof MethodSource) {
                className = ((MethodSource) s).getClassName();
            } else if (s instanceof ClassSource) {
                className = ((ClassSource) s).getClassName();
            } else {
                // fallback to source.toString() if it's an unknown TestSource implementation
                className = s.toString();
            }
        }
        return className;
    }

    static String extractTestCaseId(String methodName) {
        if (StringUtils.isBlank(methodName)) {
            return methodName;
        }

        // Remove suffix ()
        if (methodName.endsWith("()")) {
            methodName = methodName.substring(0, methodName.length() - 2);
        }
        // Remove prefix "testCase" and suffix "_XXX"
        if (methodName.startsWith("testCase")) {
            methodName = methodName.substring(8);
            int index = methodName.indexOf('_');
            if (index != -1) {
                methodName = methodName.substring(0, index);
            }
        }
        return methodName;
    }

    // TCK location
    private final String testCasesName;
    private final String testCaseId;

    // Native location
    private final String className;
    private final String methodName;
    private final String message;

    public TestFailure(TestExecutionSummary.Failure junitFailure) {
        // TODO - extract TCK location from class annotations
        this(extractClassName(junitFailure.getTestIdentifier().getSource()),
                junitFailure.getTestIdentifier().getDisplayName(),
                junitFailure.getException() == null ? null : junitFailure.getException().getMessage(),
                null,
                extractTestCaseId(junitFailure.getTestIdentifier().getDisplayName()));
    }

    public TestFailure(String className, String methodName, String message, String testCasesName, String testCaseId) {
        this.testCasesName = testCasesName;
        this.testCaseId = testCaseId;
        this.className = className;
        this.methodName = methodName;
        this.message = message;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMessage() {
        return message;
    }

    public String getTestCasesName() {
        return testCasesName;
    }

    public String getTestCaseId() {
        return testCaseId;
    }

    @Override
    public String toString() {
        return String.format("TestFailure{className='%s', testName='%s', message='%s'}", className, methodName, message);
    }
}
