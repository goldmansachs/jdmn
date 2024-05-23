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

import java.util.ArrayList;
import java.util.List;

public class UniqueInformationRequirementTransformer extends SimpleDMNTransformer<TestLab> {
    public UniqueInformationRequirementTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public UniqueInformationRequirementTransformer(BuildLogger logger) {
        super(logger);
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("DMN repository is empty; transformer will not run");
            return repository;
        }

        this.transformRepository = false;
        return removeDuplicateInformationRequirements(repository);
    }

    @Override
    public Pair<DMNModelRepository, List<TestLab>> transform(DMNModelRepository repository, List<TestLab> testCasesList) {
        if (isEmpty(repository, testCasesList)) {
            logger.warn("DMN repository or test cases list is empty; transformer will not run");
            return new Pair<>(repository, testCasesList);
        }

        // Transform model
        if (this.transformRepository) {
            transform(repository);
        }

        return new Pair<>(repository, testCasesList);
    }

    private DMNModelRepository removeDuplicateInformationRequirements(DMNModelRepository repository) {
        UniqueInformationRequirementVisitor dmnVisitor = new UniqueInformationRequirementVisitor(logger, this.errorHandler);
        TransformationContext context = new TransformationContext(repository);
        for (TDefinitions definitions: repository.getAllDefinitions()) {
            definitions.accept(dmnVisitor, context);
        }

        return repository;
    }
}

class UniqueInformationRequirementVisitor extends TraversalVisitor<TransformationContext> {
    public UniqueInformationRequirementVisitor(BuildLogger logger, ErrorHandler errorHandler) {
        super(logger, errorHandler);
    }

    @Override
    public DMNBaseElement visit(TDecision element, TransformationContext context) {
        logger.debug(String.format("Process decision table in decision '%s'", element.getName()));

        List<String> hrefs = new ArrayList<>();
        List<TInformationRequirement> newList = new ArrayList<>();
        for(TInformationRequirement ir: element.getInformationRequirement()) {
            String href = null;
            TDMNElementReference requiredInput = ir.getRequiredInput();
            TDMNElementReference requiredDecision = ir.getRequiredDecision();
            if (requiredInput != null) {
                href = requiredInput.getHref();
            }
            if (requiredDecision != null) {
                href = requiredDecision.getHref();
            }
            if (!hrefs.contains(href)) {
                newList.add(ir);
                hrefs.add(href);
            }
        }
        element.getInformationRequirement().clear();
        element.getInformationRequirement().addAll(newList);

        return element;
    }
}
