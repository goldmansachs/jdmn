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
import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.tck.ast.*;
import com.gs.dmn.tck.ast.visitor.TraversalVisitor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class AddMissingNamespaceInTestCasesTransformer extends SimpleDMNTransformer<TestCases> {
    public AddMissingNamespaceInTestCasesTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public AddMissingNamespaceInTestCasesTransformer(BuildLogger logger) {
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

        AddMissingImportPrefixInDTVisitor visitor = new AddMissingImportPrefixInDTVisitor(this.errorHandler);
        TransformationContext context = new TransformationContext(repository);
        for (TestCases testCases : testCasesList) {
            testCases.accept(visitor, context);
        }

        return new Pair<>(repository, testCasesList);
    }
}

class AddMissingImportPrefixInDTVisitor extends TraversalVisitor<TransformationContext> {
    public AddMissingImportPrefixInDTVisitor(ErrorHandler errorHandler) {
        super(errorHandler);
    }

    @Override
    public TCKBaseElement visit(TestCases element, TransformationContext context) {
        DMNModelRepository repository = context.getRepository();
        if (element != null) {
            // Search model by name
            if (StringUtils.isBlank(element.getNamespace())) {
                String modelName = element.getModelName();
                List<TDefinitions> definitionsList = repository.findDefinitionByName(modelName);
                if (definitionsList.size() == 1) {
                    element.setNamespace(definitionsList.get(0).getNamespace());
                }
            }

            // Set namespace for nodes
            for (TestCase testCase : element.getTestCase()) {
                for (InputNode node : testCase.getInputNode()) {
                    if (StringUtils.isBlank(node.getNamespace())) {
                        String name = node.getName();
                        TDRGElement drgElement = repository.findDRGElementByName(name);
                        if (drgElement != null) {
                            node.setNamespace(repository.getNamespace(drgElement));
                        }
                    }
                }
                for (ResultNode node : testCase.getResultNode()) {
                    if (StringUtils.isBlank(node.getNamespace())) {
                        String name = node.getName();
                        TDRGElement drgElement = repository.findDRGElementByName(name);
                        if (drgElement != null) {
                            node.setNamespace(repository.getNamespace(drgElement));
                        }
                    }
                }
            }
        }
        return element;
    }
}
