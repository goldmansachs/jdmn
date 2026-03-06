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
package com.gs.dmn.tck.validation;

import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.tck.ast.*;
import com.gs.dmn.tck.ast.visitor.TraversalVisitor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DefaultTCKValidator extends SimpleTCKValidator {
    public DefaultTCKValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public DefaultTCKValidator(BuildLogger logger) {
        super(logger);
    }

    @Override
    public List<ValidationError> validate(TestCases testCases) {
        ValidationContext context = new ValidationContext(testCases);
        if (isEmpty(testCases)) {
            this.logger.warn("TestCases is empty; validator will not run");
            return context.getErrors();
        }

        DefaultTCKValidatorVisitor visitor = new DefaultTCKValidatorVisitor(this.logger, this.errorHandler, this);
        testCases.accept(visitor, context);

        return context.getErrors();
    }
}

class DefaultTCKValidatorVisitor extends TraversalVisitor<ValidationContext> {
    private final DefaultTCKValidator validator;

    public DefaultTCKValidatorVisitor(BuildLogger logger, ErrorHandler errorHandler, DefaultTCKValidator validator) {
        super(logger, errorHandler);
        this.validator = validator;
    }

    @Override
    public TCKBaseElement visit(TestCases element, ValidationContext context) {
        if (element != null) {
            validateTestCases(element, context);

            // Visit children
            super.visit(element, context);
        }

        return element;
    }

    @Override
    public TCKBaseElement visit(TestCase element, ValidationContext context) {
        if (element != null) {
            validateTestCase(element, context);

            // Visit children
            super.visit(element, context);
        }

        return element;
    }

    @Override
    public TCKBaseElement visit(InputNode element, ValidationContext context) {
        if (element != null) {
            TestCase testCase = (TestCase) (element.getParent());
            validateInputNode(testCase, element, context);

            // Visit children
            super.visit(element, context);
        }

        return element;
    }

    @Override
    public TCKBaseElement visit(ResultNode element, ValidationContext context) {
        if (element != null) {
            TestCase testCase = (TestCase) (element.getParent());
            validateResultNode(testCase, element, context);

            // Visit children
            super.visit(element, context);
        }

        return element;
    }

    @Override
    public TCKBaseElement visit(Component element, ValidationContext context) {
        if (element != null) {
            validateComponent(element.getComponent(), context);

            // Visit children
            super.visit(element, context);
        }
        return element;
    }

    void validateTestCases(TestCases element, ValidationContext context) {
        // The testCasesName is mandatory, as is the name of the file
        if (StringUtils.isBlank(element.getTestCasesName())) {
            String errorMessage = String.format("Missing testCasesName for element %s", element.getClass().getSimpleName());
            this.validator.addValidationError(context, element, errorMessage);
        }
        // ModelName is mandatory as it used to find the model under test
        if (element.getModelName() == null) {
            String errorMessage = String.format("Missing modelName for element %s", element.getClass().getSimpleName());
            this.validator.addValidationError(context, element, errorMessage);
        }
        // The id of a test case must be unique
        validateUnique(
                new ArrayList<>(element.getTestCase()), "TestCase", "id",
                TestCase::getId, null, context
        );
    }

    public void validateTestCase(TestCase element, ValidationContext context) {
        // id is mandatory
        if (StringUtils.isBlank(element.getId())) {
            String errorMessage = "Missing id of testCase";
            this.validator.addValidationError(context, element, errorMessage);
        }
        // invocableName is mandatory when type is bkm or decisionService
        if (isInvocable(element) && missingInvocableName(element)) {
            String errorMessage = "Missing invocableName of testCase with id '%s'".formatted(element.getId());
            this.validator.addValidationError(context, element, errorMessage);
        }
        // type must be bkm or decisionService if invocableName is provided
        if (!missingInvocableName(element) && !isInvocable(element)) {
            String errorMessage = "Invalid type of testCase with id '%s'. Expected type is bkm or decisionService when invocableName is provided.".formatted(element.getId());
            this.validator.addValidationError(context, element, errorMessage);
        }
        // Validate that the input nodes and result nodes have unique names
        validateUnique(
                new ArrayList<>(element.getInputNode()), "InputNode", "name",
                InputNode::getName, null, context
        );
        validateUnique(
                new ArrayList<>(element.getResultNode()), "ResultNode", "name",
                ResultNode::getName, null, context
        );
    }

    private static boolean missingInvocableName(TestCase element) {
        return StringUtils.isBlank(element.getInvocableName());
    }

    private static boolean isInvocable(TestCase element) {
        return element.getType() == TestCaseType.BKM || element.getType() == TestCaseType.DECISION_SERVICE;
    }

    public void validateInputNode(TestCase testCase, InputNode element, ValidationContext context) {
        // Name is mandatory
        if (StringUtils.isBlank(element.getName())) {
            String errorMessage = "Missing name of inputNode in testCase with id '%s'".formatted(testCase.getId());
            this.validator.addValidationError(context, element, errorMessage);
        }
        // The value is mandatory
        if (!hasValue(element)) {
            String errorMessage = "Missing value of inputNode '%s' in testCase with id '%s'".formatted(element.getName(), testCase.getId());
            this.validator.addValidationError(context, element, errorMessage);
        }
        validateComponent(element.getComponent(), context);
    }

    private boolean hasValue(InputNode element) {
        return element.getValue() != null || !element.getComponent().isEmpty() || element.getList() != null;
    }

    public void validateResultNode(TestCase testCase, ResultNode element, ValidationContext context) {
        // Name is mandatory
        if (StringUtils.isBlank(element.getName())) {
            String errorMessage = "Missing name of resultNode in testCase with id '%s'".formatted(testCase.getId());
            this.validator.addValidationError(context, element, errorMessage);
        }
        // The value is mandatory
        ValueType expected = element.getExpected();
        if (expected == null) {
            String errorMessage = "Missing value of resultNode '%s' in testCase with id '%s'".formatted(element.getName(), testCase.getId());
            this.validator.addValidationError(context, element, errorMessage);
        } else {
            validateComponent(expected.getComponent(), context);
        }
    }

    protected <T> void validateUnique(List<T> elements, String elementType, String property, Function<T, String> accessor, String errorMessage, ValidationContext context) {
        if (errorMessage == null) {
            errorMessage = String.format("The %s of a %s must be unique.", property, elementType);
        }

        // Create a map
        Map<String, List<T>> map = new LinkedHashMap<>();
        for (T element : elements) {
            String key = accessor.apply(element);
            if (key != null) {
                List<T> list = map.get(key);
                if (list == null) {
                    list = new ArrayList<>();
                    list.add(element);
                    map.put(key, list);
                } else {
                    list.add(element);
                }
            }
        }
        // Find duplicates
        List<String> duplicates = new ArrayList<>();
        for (Map.Entry<String, List<T>> entry : map.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue().size() > 1) {
                duplicates.add(key);
            }
        }
        // Report error
        if (!duplicates.isEmpty()) {
            String message = String.join(", ", duplicates);
            String finalErrorMessage = String.format("%s Found duplicates for '%s'.", errorMessage, message);
            this.validator.addValidationError(context, null, finalErrorMessage);
        }
    }

    public void validateComponent(List<Component> elementList, ValidationContext context) {
        List<String> names = new ArrayList<>();
        for (Component component : elementList) {
            String name = component.getName();
            if (names.contains(name)) {
                this.validator.addValidationError(context, component, String.format("The name of a component must be unique. Found duplicates for '%s'.", name));
                break;
            }
            names.add(name);
        }
    }

}
