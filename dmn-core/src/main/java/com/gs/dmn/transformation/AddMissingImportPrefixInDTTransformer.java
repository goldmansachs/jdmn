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
import com.gs.dmn.runtime.Pair;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.apache.commons.lang3.StringUtils;
import org.omg.dmn.tck.marshaller._20160719.TestCases;
import org.omg.spec.dmn._20180521.model.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AddMissingImportPrefixInDTTransformer extends SimpleDMNTransformer<TestCases> {
    private boolean transformRepository = true;

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        for (TDecision decision: repository.decisions()) {
            TExpression expression = repository.expression(decision);
            if (expression instanceof TDecisionTable) {
                transform(repository, decision, (TDecisionTable) expression);
            }
        }
        this.transformRepository = false;
        return repository;
    }

    @Override
    public Pair<DMNModelRepository, List<TestCases>> transform(DMNModelRepository repository, List<TestCases> testCases) {
        if (transformRepository) {
            transform(repository);
        }
        return new Pair<>(repository, testCases);
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

        // Add missing import prefix in OutputEntry
        for (TDecisionRule rule: decisionTable.getRule()) {
            List<TLiteralExpression> outputEntry = rule.getOutputEntry();
            for (TLiteralExpression exp: rule.getOutputEntry()) {
                addMissingPrefix(exp, names);
            }
        }
    }

    void addMissingPrefix(TLiteralExpression exp, Set<String> names) {
        if (exp == null || StringUtils.isEmpty(exp.getText())) {
            return;
        }
        String oldText = exp.getText();
        StringBuilder newText = new StringBuilder();
        Lexer lexer = makeLexer(oldText);
        Token token;
        do {
            token = lexer.nextToken();
            if (token.getType() == FEELLexer.NAME) {
                String name = token.getText();
                if (names.contains(name)) {
                    // Prefix same with DRG name
                    newText.append(name).append(".").append(name);
                } else {
                    newText.append(name);
                }
            } else if (token.getType() != FEELLexer.EOF) {
                newText.append(token.getText());
            }
            newText.append(" ");
        } while (token.getType() != FEELLexer.EOF);
        exp.setText(newText.toString());
    }

    private Lexer makeLexer(java.lang.String text) {
        CharStream cs = CharStreams.fromString(text);
        return new FEELLexer(cs);
    }
}
