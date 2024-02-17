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
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RuleDescriptionTransformer extends SimpleDMNTransformer<TestLab> {
    public RuleDescriptionTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public RuleDescriptionTransformer(BuildLogger logger) {
        super(logger);
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("DMN repository is empty; transformer will not run");
            return repository;
        }

        this.transformRepository = false;
        return cleanRuleDescription(repository, logger);
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

    private DMNModelRepository cleanRuleDescription(DMNModelRepository repository, BuildLogger logger) {
        RuleDescriptionVisitor dmnVisitor = new RuleDescriptionVisitor(this.errorHandler, this.logger);
        TransformationContext context = new TransformationContext(repository);
        for (TDefinitions definitions: repository.getAllDefinitions()) {
            definitions.accept(dmnVisitor, context);
        }

        return repository;
    }
}

class RuleDescriptionVisitor extends TraversalVisitor<TransformationContext> {
    private static final Map<String, String> PATTERNS = new LinkedHashMap<>();
    static {
        PATTERNS.put("[ ,", "[");
        PATTERNS.put(",  ,", ",");
        PATTERNS.put(", ]", "]");

        PATTERNS.put("string(-)", "\"\"");

        PATTERNS.put("\u00A0", " ");
    }

    private final BuildLogger logger;

    public RuleDescriptionVisitor(ErrorHandler errorHandler, BuildLogger logger) {
        super(errorHandler);
        this.logger = logger;
    }

    @Override
    public DMNBaseElement visit(TDecision element, TransformationContext context) {
        logger.debug("Process decision table in decision '%s'".formatted(element.getName()));

        DMNModelRepository repository = context.getRepository();
        TExpression expression = repository.expression(element);
        if (expression instanceof TDecisionTable table) {
            logger.debug("Process decision table in decision '%s'".formatted(element.getName()));
            table.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TDecisionRule element, TransformationContext context) {
        cleanRuleDescription(element);
        return element;
    }

    void cleanRuleDescription(TDecisionRule rule) {
        String description = rule.getDescription();
        if (StringUtils.isNotBlank(description)) {
            for (Map.Entry<String, String> entry : PATTERNS.entrySet()) {
                description = description.replace(entry.getKey(), entry.getValue());
            }
            rule.setDescription(description);
        }
    }
}
