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
package com.gs.dmn.signavio.validation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.validation.SimpleDMNValidator;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RuleDescriptionValidator extends SimpleDMNValidator {
    private static Map<String, String> PATTERNS = new LinkedHashMap<>();

    static {
        PATTERNS.put("[ ,", "[ ,");
        PATTERNS.put(",  ,", ",  ,");
        PATTERNS.put(", ]", ", ]");

        PATTERNS.put("string(-)", "string(-)");

        PATTERNS.put("\u00A0", "NO-BREAK SPACE");
    }

    public RuleDescriptionValidator() {
        super(new Slf4jBuildLogger(LOGGER));
    }

    public RuleDescriptionValidator(BuildLogger logger) {
        super(logger);
    }

    @Override
    public List<String> validate(DMNModelRepository dmnModelRepository) {
        List<String> errors = new ArrayList<>();

        if (dmnModelRepository == null) {
            throw new IllegalArgumentException("Missing definitions");
        }

        for (TDefinitions definitions: dmnModelRepository.getAllDefinitions()) {
            for (TDecision decision : dmnModelRepository.findDecisions(definitions)) {
                TExpression expression = dmnModelRepository.expression(decision);
                if (expression instanceof TDecisionTable) {
                    List<TDecisionRule> rules = ((TDecisionTable) expression).getRule();
                    for (int i = 0; i < rules.size(); i++) {
                        TDecisionRule rule = rules.get(i);
                        validate(rule.getDescription(), i, decision, errors);
                    }
                }
            }
        }

        return errors;
    }

    protected void validate(String description, int ruleIndex, TDecision decision, List<String> errors) {
        if (StringUtils.isNotBlank(description)) {
            for (Map.Entry<String, String> entry : PATTERNS.entrySet()) {
                if (description.contains(entry.getKey())) {
                    String error = String.format("Description of rule %d in decision '%s' contains illegal sequence '%s'", ruleIndex, decision.getName(), entry.getValue());
                    errors.add(error);
                }
            }
        }
    }
}
