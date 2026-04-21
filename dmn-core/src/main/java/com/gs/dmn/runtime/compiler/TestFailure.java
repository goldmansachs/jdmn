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

import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.annotation.TestCase;
import com.gs.dmn.runtime.annotation.TestCases;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.lang.reflect.Method;
import java.util.Optional;

public class TestFailure {
    public static TestFailure from(TestExecutionSummary.Failure junitFailure) {
        if (junitFailure != null) {
            Optional<TestSource> testSource = junitFailure.getTestIdentifier() == null ? Optional.empty() : junitFailure.getTestIdentifier().getSource();
            Pair<Class<?>, Method> classMethodPair = extractJavaInfo(testSource);
            Class<?> javaClass = classMethodPair.getLeft();
            Method javaMethod = classMethodPair.getRight();
            String className = javaClass == null ? "" : javaClass.getName();
            String methodName = javaMethod == null ? "" : javaMethod.getName();
            String message = junitFailure.getException() == null ? null : junitFailure.getException().getMessage();
            message = message == null ? "" : message;
            String testCasesName = extractTestCasesName(javaClass);
            String testCaseId = extractTestCaseId(javaMethod);
            return new TestFailure(
                    className,
                    methodName,
                    message,
                    testCasesName,
                    testCaseId);
        } else {
            throw new DMNRuntimeException("Cannot create TestFailure from null junitFailure");
        }
    }

    private static Pair<Class<?>, Method> extractJavaInfo(Optional<TestSource> source) {
        Class<?> javaClass = null;
        Method javaMethod = null;
        if (source.isPresent()) {
            TestSource s = source.get();
            if (s instanceof MethodSource) {
                javaClass = ((MethodSource) s).getJavaClass();
                javaMethod = ((MethodSource) s).getJavaMethod();
            } else if (s instanceof ClassSource) {
                javaClass = ((ClassSource) s).getJavaClass();
            }
        }
        return new Pair<>(javaClass, javaMethod);
    }

    private static String extractTestCasesName(Class<?> javaClass) {
        String testCasesName = "";
        if (javaClass != null) {
            TestCases annotation = javaClass.getAnnotation(TestCases.class);
            return annotation == null ? "" : annotation.testCasesName();
        }
        return testCasesName;
    }

    private static String extractTestCaseId(Method javaMethod) {
        String testCaseId = "";
        if (javaMethod != null) {
            TestCase annotation = javaMethod.getAnnotation(TestCase.class);
            return annotation == null ? "" : annotation.id();
        }
        return testCaseId;
    }

    // TCK location
    private final String testCasesName;
    private final String testCaseId;

    // Native location
    private final String className;
    private final String methodName;
    private final String message;

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
