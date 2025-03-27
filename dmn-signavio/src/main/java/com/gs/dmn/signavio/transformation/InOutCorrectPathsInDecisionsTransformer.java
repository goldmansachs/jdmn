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
import com.gs.dmn.feel.analysis.semantics.type.FEELType;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.SimpleDMNTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class InOutCorrectPathsInDecisionsTransformer extends SimpleDMNTransformer<TestLab> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(InOutCorrectPathsInDecisionsTransformer.class);

    public InOutCorrectPathsInDecisionsTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    protected InOutCorrectPathsInDecisionsTransformer(BuildLogger logger) {
        super(logger);
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("Repository is empty; transformer will not run");
            return repository;
        }

        correctDecisions(repository);

        this.transformRepository = false;
        return repository;
    }

    @Override
    public Pair<DMNModelRepository, List<TestLab>> transform(DMNModelRepository repository, List<TestLab> testCasesList) {
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

    private void correctDecisions(DMNModelRepository repository) {
        for (TDefinitions definitions : repository.getAllDefinitions()) {
            for (TDecision decision : repository.findDecisions(definitions)) {
                TExpression expression = repository.expression(decision);
                if (expression instanceof TDecisionTable) {
                    List<String> childNames = directChildDecisionNames(decision, repository);
                    correctDecision(decision, childNames, (TDecisionTable) expression);
                }
            }
        }
    }

    private List<String> directChildDecisionNames(TDecision decision, DMNModelRepository repository) {
        List<String> result = new ArrayList<>();
        for (TInformationRequirement informationRequirement : decision.getInformationRequirement()) {
            TDMNElementReference requiredDecision = informationRequirement.getRequiredDecision();
            if (requiredDecision != null) {
                String href = requiredDecision.getHref();
                TDecision child = repository.findDecisionByRef(decision, href);
                if (isPrimitiveType(child, repository))  {
                    result.add(child.getName());
                }
            }
        }

        return result;
    }

    private boolean isPrimitiveType(TDecision decision, DMNModelRepository repository) {
        TInformationItem variable = repository.variable(decision);
        if (variable == null || variable.getTypeRef() == null) {
            return false;
        }

        TItemDefinition itemDefinition = repository.lookupItemDefinition(variable.getTypeRef().getLocalPart());
        while (itemDefinition != null && itemDefinition.getTypeRef() != null) {
            if (isPrimitiveType(itemDefinition)) {
                return true;
            } else {
                itemDefinition = repository.lookupItemDefinition(itemDefinition.getTypeRef().getLocalPart());
            }
        }
        return false;
    }

    private boolean isPrimitiveType(TItemDefinition itemDefinition) {
        if (itemDefinition == null || itemDefinition.getTypeRef() == null) {
            return false;
        }

        String name = itemDefinition.getTypeRef().getLocalPart();
        return (FEELType.FEEL_TYPE_NAMES.contains(name) || name.startsWith(DMNVersion.LATEST.getFeelPrefix())) && !itemDefinition.isIsCollection();
    }

    private void correctDecision(TDecision decision, List<String> childNames, TDecisionTable dte) {
        if (childNames.isEmpty()) {
            return;
        }

        for (String childName : childNames) {
            String oldValue = String.format("%s.%s", childName, childName);
            String newValue = childName;
            // Correct rules
            correctRules(decision, oldValue, newValue, dte);
        }
    }

    private void correctRules(TDecision decision, String oldValue, String newValue, TDecisionTable dte) {
        List<TDecisionRule> ruleList = dte.getRule();
        for (TDecisionRule rule : ruleList) {
            for (TLiteralExpression outputEntry : rule.getOutputEntry()) {
                if (oldValue.equals(outputEntry.getText().trim())) {
                    updateLiteralExpression(outputEntry, oldValue, newValue, decision);
                }
            }
        }
    }

    private void updateLiteralExpression(TLiteralExpression expression, String oldValue, String newValue, TDecision decision) {
        String oldText = expression.getText();
        String newText = oldText.replace(oldValue, newValue);

        logger.info(String.format("Replacing expression '%s' with '%s' in decision '%s'", oldText, newText, decision.getName()));

        expression.setText(newText);
    }
}
