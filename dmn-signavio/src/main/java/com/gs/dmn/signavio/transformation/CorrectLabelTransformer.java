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
import com.gs.dmn.ast.*;
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
    public CorrectLabelTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public CorrectLabelTransformer(BuildLogger logger) {
        super(logger);
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
        CorrectLabelVisitor dmnVisitor = new CorrectLabelVisitor(this.logger, this.errorHandler);
        TransformationContext context = new TransformationContext(repository);
        for (TDefinitions definitions: repository.getAllDefinitions()) {
            definitions.accept(dmnVisitor, context);
        }

        return repository;
    }
}

class CorrectLabelVisitor extends TraversalVisitor<TransformationContext> {
    public CorrectLabelVisitor(BuildLogger logger, ErrorHandler errorHandler) {
        super(logger, errorHandler);
    }

    @Override
    protected void visitTDMNElementProperties(TDMNElement element, TransformationContext context) {
        if (element == null) {
            return;
        }

        element.setLabel(cleanLabel(element.getLabel()));
        super.visitTDMNElementProperties(element, context);
    }

    String cleanLabel(String label) {
        if (label == null) {
            return null;
        } else {
            // Remove white spaces from label
            return label.strip();
        }
    }
}
