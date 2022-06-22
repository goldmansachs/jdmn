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
import com.gs.dmn.ast.SpecialVariableTransformerVisitor;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.Visitor;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.tck.ast.TestCases;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SpecialVariableTransformer extends SimpleDMNTransformer<TestCases> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SpecialVariableTransformer.class);

    protected final BuildLogger logger;
    protected boolean transformRepository = true;

    private final Visitor visitor = new SpecialVariableTransformerVisitor();

    public SpecialVariableTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    protected SpecialVariableTransformer(BuildLogger logger) {
        this.logger = logger;
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("Repository is empty; transformer will not run");
            return repository;
        }

        for (TDefinitions definitions: repository.getAllDefinitions()) {
            logger.info(String.format("Replace inputExpressions with ? in inputEntries in model '%s'", definitions.getName()));
            definitions.accept(visitor, null);
        }

        this.transformRepository = false;
        return repository;
    }

    @Override
    public Pair<DMNModelRepository, List<TestCases>> transform(DMNModelRepository repository, List<TestCases> testCasesList) {
        if (isEmpty(repository, testCasesList)) {
            logger.warn("DMN repository or test list is empty; transformer will not run");
            return new Pair<>(repository, testCasesList);
        }

        // Transform model
        if (this.transformRepository) {
            transform(repository);
        }

        return new Pair<>(repository, testCasesList);
    }
}
