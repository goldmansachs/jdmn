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
package com.gs.dmn.signavio.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.TDMNElement;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.visitor.TraversalVisitor;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.SimpleDMNTransformer;
import com.gs.dmn.transformation.TransformationContext;

import java.util.List;

public class CorrectLabelTransformer extends SimpleDMNTransformer<TestLab> {
    public enum TransformationKind {
        STRIP,
        CAMEL_CASE,
        REMOVE_ILLEGAL_CHARACTERS
    }

    private final TransformationKind transformationKind;

    public CorrectLabelTransformer() {
        this(TransformationKind.STRIP, new Slf4jBuildLogger(LOGGER));
    }

    public CorrectLabelTransformer(BuildLogger logger) {
        this(TransformationKind.STRIP, logger);
    }

    public CorrectLabelTransformer(TransformationKind transformationKind, BuildLogger logger) {
        super(logger);
        this.transformationKind = transformationKind;
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("DMN repository is empty");
            return repository;
        }

        // Transform models
        return correctLabels(repository, logger);
    }

    @Override
    public Pair<DMNModelRepository, List<TestLab>> transform(DMNModelRepository repository, List<TestLab> testCasesList) {
        // Transform model
        repository = transform(repository);

        // Transform test cases
        if (isEmpty(testCasesList)) {
            logger.warn("List of test cases is empty");
            return new Pair<>(repository, testCasesList);
        }

        return new Pair<>(repository, testCasesList);
    }

    private DMNModelRepository correctLabels(DMNModelRepository repository, BuildLogger logger) {
        logger.debug("Transform labels ...");
        CorrectLabelVisitor dmnVisitor = new CorrectLabelVisitor(this.transformationKind, this.logger, this.errorHandler);
        TransformationContext context = new TransformationContext(repository);
        for (TDefinitions definitions: repository.getAllDefinitions()) {
            definitions.accept(dmnVisitor, context);
        }

        return repository;
    }
}

class CorrectLabelVisitor extends TraversalVisitor<TransformationContext> {
    private final CorrectLabelTransformer.TransformationKind transformationKind;

    public CorrectLabelVisitor(BuildLogger logger, ErrorHandler errorHandler) {
        this(CorrectLabelTransformer.TransformationKind.STRIP, logger, errorHandler);
    }

    public CorrectLabelVisitor(CorrectLabelTransformer.TransformationKind transformationKind, BuildLogger logger, ErrorHandler errorHandler) {
        super(logger, errorHandler);
        this.transformationKind = transformationKind;
    }

    @Override
    protected void visitTDMNElementProperties(TDMNElement element, TransformationContext context) {
        if (element == null) {
            return;
        }

        element.setLabel(transformLabel(element.getLabel()));
        super.visitTDMNElementProperties(element, context);
    }

    String transformLabel(String label) {
        if (label == null) {
            return null;
        }

        // Remove white spaces from label's beginning and end
        label = label.strip();
        if (transformationKind == CorrectLabelTransformer.TransformationKind.CAMEL_CASE) {
            return camelCase(label);
        } else if (transformationKind == CorrectLabelTransformer.TransformationKind.REMOVE_ILLEGAL_CHARACTERS) {
            return removeCharacters(label);
        } else {
            return label;
        }
    }

    // Only letters, digits and '_' are allowed. All other characters are removed and the next character is capitalized.
    // Label cannot start with a digit. If it does, an '_' is prepended to the label.
    String camelCase(String label) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;

        for (int i = 0; i < label.length(); i++) {
            char c = label.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '_') {
                if (!result.isEmpty()) {
                    capitalizeNext = true;
                }
                continue;
            }

            if (result.isEmpty() && Character.isDigit(c)) {
                result.append('_');
            }

            result.append(capitalizeNext ? Character.toUpperCase(c) : Character.toLowerCase(c));
            capitalizeNext = false;
        }

        return result.toString();
    }

    // Only letters, digits, spaces, '-' and '_' are allowed. All other characters are removed and the next character is capitalized.
    // Label cannot start with a digit. If it does, an '_' is prepended to the label.
    String removeCharacters(String label) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < label.length(); i++) {
            char ch = label.charAt(i);
            if (isAllowedCharacter(ch)) {
                result.append(ch);
            } else {
                if (result.isEmpty() && !(Character.isLetter(ch) || ch == '_')) {
                    result.append('_');
                }
            }
        }

        return result.toString().strip();
    }

    private static boolean isAllowedCharacter(char c) {
        return Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == ' ';
    }
}
