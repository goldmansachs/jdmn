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

public class RuleDescriptionToAnnotationTransformer extends SimpleDMNTransformer<TestLab> {
    public RuleDescriptionToAnnotationTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public RuleDescriptionToAnnotationTransformer(BuildLogger logger) {
        super(logger);
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("DMN repository is empty");
            return repository;
        }

        // Transform models
        return ruleDescriptionToAnnotation(repository, logger);
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

    private DMNModelRepository ruleDescriptionToAnnotation(DMNModelRepository repository, BuildLogger logger) {
        logger.debug("Transform rule descriptions ...");
        RuleDescriptionToAnnotationVisitor dmnVisitor = new RuleDescriptionToAnnotationVisitor(this.logger, this.errorHandler);
        TransformationContext context = new TransformationContext(repository);
        for (TDefinitions definitions: repository.getAllDefinitions()) {
            definitions.accept(dmnVisitor, context);
        }

        return repository;
    }
}

class RuleDescriptionToAnnotationVisitor extends TraversalVisitor<TransformationContext> {
    private static final Map<String, String> PATTERNS = new LinkedHashMap<>();
    static {
        PATTERNS.put("[ ,", "[");
        PATTERNS.put(",  ,", ",");
        PATTERNS.put(", ]", "]");

        PATTERNS.put("string(-)", "\"\"");

        PATTERNS.put("\u00A0", " ");
    }

    public RuleDescriptionToAnnotationVisitor(BuildLogger logger, ErrorHandler errorHandler) {
        super(logger, errorHandler);
    }

    @Override
    public DMNBaseElement visit(TDecisionTable element, TransformationContext context) {
        if (element == null)  {
            return null;
        }

        // Visit children
        super.visit(element, context);

        // Add annotation
        addAnnotation(element);

        return element;
    }

    @Override
    public DMNBaseElement visit(TDecisionRule element, TransformationContext context) {
        if (element != null)  {
            if (!StringUtils.isBlank(element.getDescription())) {
                // Correct the description
                String newDescription = cleanRuleDescription(element.getDescription());
                if (!StringUtils.isBlank(newDescription)) {
                    // Add annotation entry
                    TRuleAnnotation annotation = new TRuleAnnotation();
                    annotation.setText(newDescription);
                    element.getAnnotationEntry().add(annotation);
                    // Remove description
                    element.setDescription(null);
                }
            }
        }

        return element;
    }

    String cleanRuleDescription(String description) {
        for (Map.Entry<String, String> entry : PATTERNS.entrySet()) {
            description = description.replace(entry.getKey(), entry.getValue());
        }
        return description;
    }

    private void addAnnotation(TDecisionTable element) {
        boolean rulesHaveAnnotations = element.getRule().stream().anyMatch(r -> !r.getAnnotationEntry().isEmpty());
        if (rulesHaveAnnotations) {
            TRuleAnnotationClause annotation = new TRuleAnnotationClause();
            annotation.setName("Annotation");
            element.getAnnotation().add(annotation);
        }
    }

}
