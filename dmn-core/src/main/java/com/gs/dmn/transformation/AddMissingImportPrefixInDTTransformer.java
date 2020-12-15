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
import com.gs.dmn.feel.analysis.syntax.antlrv4.FEELLexer;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.apache.commons.lang3.StringUtils;
import org.omg.dmn.tck.marshaller._20160719.TestCases;
import org.omg.spec.dmn._20191111.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AddMissingImportPrefixInDTTransformer extends SimpleDMNTransformer<TestCases> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddMissingImportPrefixInDTTransformer.class);

    private final BuildLogger logger;
    private boolean transformRepository = true;

    public AddMissingImportPrefixInDTTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public AddMissingImportPrefixInDTTransformer(BuildLogger logger) {
        this.logger = logger;
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("DMN repository is empty; transformer will not run");
            return repository;
        }

        for (TDefinitions definitions: repository.getAllDefinitions()) {
            for (TDecision decision: repository.findDecisions(definitions)) {
                TExpression expression = repository.expression(decision);
                if (expression instanceof TDecisionTable) {
                    logger.debug(String.format("Process decision table in decision '%s'", decision.getName()));
                    transform(repository, decision, (TDecisionTable) expression);
                }
            }
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
        if (transformRepository) {
            transform(repository);
        }

        return new Pair<>(repository, testCasesList);
    }

    private void transform(DMNModelRepository repository, TDecision decision, TDecisionTable decisionTable) {
        // Collect names in scope
        Set<String> names = new LinkedHashSet<>();
        for (TInformationRequirement ir: decision.getInformationRequirement()) {
            TDMNElementReference requiredInput = ir.getRequiredInput();
            if (requiredInput != null) {
                TDRGElement drgElement = repository.findDRGElementByRef(decision, requiredInput.getHref());
                names.add(drgElement.getName());
            }
            TDMNElementReference requiredDecision = ir.getRequiredDecision();
            if (requiredDecision != null) {
                TDRGElement drgElement = repository.findDRGElementByRef(decision, requiredDecision.getHref());
                names.add(drgElement.getName());
            }
        }

        // Add missing import prefix in InputClause
        for (TInputClause input: decisionTable.getInput()) {
            if (input != null) {
                addMissingPrefix(input.getInputExpression(), names);
            }
        }

        // Add missing import prefix in OutputClause
        for (TOutputClause output: decisionTable.getOutput()) {
            if (output != null) {
                addMissingPrefix(output.getOutputValues(), names);
                addMissingPrefix(output.getDefaultOutputEntry(), names);
            }
        }

        // Add missing import prefix in Rule
        for (TDecisionRule rule: decisionTable.getRule()) {
            // InputEntry
            for (TUnaryTests test: rule.getInputEntry()) {
                addMissingPrefix(test, names);
            }
            // OutputEntry
            for (TLiteralExpression exp: rule.getOutputEntry()) {
                addMissingPrefix(exp, names);
            }
        }
    }

    private void addMissingPrefix(TLiteralExpression exp, Set<String> names) {
        if (exp != null) {
            String newText = addMissingPrefix(exp.getText(), names);
            exp.setText(newText);
        }
    }

    private void addMissingPrefix(TUnaryTests test, Set<String> names) {
        if (test != null) {
            String newText = addMissingPrefix(test.getText(), names);
            test.setText(newText);
        }
    }

    String addMissingPrefix(String text, Set<String> names) {
        if (StringUtils.isEmpty(text)) {
            return text;
        }

        // Extract tokens
        Lexer lexer = makeLexer(text);
        List<Token> tokens = new ArrayList<>();
        Token token;
        do {
            token = lexer.nextToken();
            tokens.add(token);
        } while (token.getType() != FEELLexer.EOF);

        // Add prefix
        StringBuilder newText = new StringBuilder();
        int card = tokens.size() - 1;
        for (int i = 0; i < card; i++) {
            token = tokens.get(i);
            String tokenText = token.getText();
            if (isInputName(i, tokens)) {
                // Replace name
                if (names.contains(tokenText)) {
                    // Prefix same with DRG name
                    newText.append(tokenText).append(".").append(tokenText);
                } else {
                    newText.append(tokenText);
                }
            } else {
                newText.append(tokenText);
            }
            newText.append(" ");
        }
        return newText.toString().trim();
    }

    private boolean isInputName(int index, List<Token> tokens) {
        if (tokens.get(index).getType() != FEELLexer.NAME) {
            return false;
        }
        if (index + 1 < tokens.size() && tokens.get(index + 1).getType() == FEELLexer.PAREN_OPEN) {
            return false;
        }
        if (index - 1 >= 0 && tokens.get(index - 1).getType() == FEELLexer.DOT) {
            return false;
        }
        return true;
    }

    private Lexer makeLexer(java.lang.String text) {
        CharStream cs = CharStreams.fromString(text);
        return new FEELLexer(cs);
    }
}
