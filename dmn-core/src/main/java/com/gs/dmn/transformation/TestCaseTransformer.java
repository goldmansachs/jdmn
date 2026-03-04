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
package com.gs.dmn.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.*;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.tck.ast.*;

import java.util.ArrayList;
import java.util.List;

public class TestCaseTransformer extends SimpleDMNTransformer<TestCases> {
    public TestCaseTransformer(BuildLogger logger) {
        super(logger);
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("DMN repository is empty; transformer will not run");
            return repository;
        }

        this.transformRepository = false;
        return repository;
    }

    @Override
    public Pair<DMNModelRepository, List<TestCases>> transform(DMNModelRepository repository, List<TestCases> testCasesList) {
        if (isEmpty(repository, testCasesList)) {
            logger.warn("DMN repository or test cases list is empty; transformer will not run");
            return new Pair<>(repository, testCasesList);
        }

        // Transform model
        if (this.transformRepository) {
            transform(repository);
        }

        // Transform test cases
        for (TestCases testCases : testCasesList) {
            transformTestCases(testCases);
        }
        return new Pair<>(repository, testCasesList);
    }

    private void transformTestCases(TestCases testCases) {
        // Transform test cases
        for (TestCase testCase : testCases.getTestCase()) {
            transformTestCase(testCase);
        }
    }

    private void transformTestCase(TestCase testCase) {
        // Transform testCase for invocables
        TestCaseType type = testCase.getType();
        String invocableName = testCase.getInvocableName();
        List<ResultNode> resultNode = testCase.getResultNode();
        if (TestCaseType.BKM == type) {
            // Remove invocable name and type
            testCase.setInvocableName(null);
            testCase.setType(null);
            // Change name of the result node to invocable name
            if (resultNode.size() == 1) {
                resultNode.get(0).setName(invocableName);
            } else {
                throw new DMNRuntimeException("BKM test case should have exactly one result node");
            }
        } else if (TestCaseType.DECISION_SERVICE == type) {
            // Remove invocable name and type
            testCase.setInvocableName(null);
            testCase.setType(null);
            if (resultNode.size() == 1) {
                // One output decision -> change name of the result node to invocable name
                resultNode.get(0).setName(invocableName);
            } else {
                // Several output decisions -> make a component for every resultNode
                List<Component> components = new ArrayList<>();
                for (ResultNode node : resultNode) {
                    Component component = new Component();
                    component.setName(node.getName());
                    setValue(component, node.getExpected());
                    components.add(component);
                }
                // Make one result node with the name of the decision service and a list of components as expected value
                ResultNode newResultNode = new ResultNode();
                newResultNode.setName(invocableName);
                ValueType newExpected = new ValueType();
                newExpected.getComponent().addAll(components);
                newResultNode.setExpected(newExpected);
                // Replace result nodes with the new one
                testCase.getResultNode().clear();
                testCase.getResultNode().add(newResultNode);
            }
        }
    }

    private void setValue(Component component, ValueType expected) {
        if (expected == null) {
            return;
        }

        if (expected.getValue() != null) {
            component.setValue(expected.getValue());
        } else if (expected.getList() != null) {
            component.setList(expected.getList());
        } else if (expected.getComponent() != null) {
            component.getComponent().addAll(expected.getComponent());
        }
    }
}
