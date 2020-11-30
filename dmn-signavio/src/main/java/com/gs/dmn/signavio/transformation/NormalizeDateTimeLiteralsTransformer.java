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
import com.gs.dmn.feel.analysis.syntax.antlrv4.FEELLexer;
import com.gs.dmn.feel.lib.MixedJavaTimeFEELLib;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.SimpleDMNTransformer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20191111.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneOffset;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

public class NormalizeDateTimeLiteralsTransformer extends SimpleDMNTransformer<TestLab> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NormalizeDateTimeLiteralsTransformer.class);

    private final BuildLogger logger;
    private boolean transformRepository = true;

    private MixedJavaTimeFEELLib feelLib = new MixedJavaTimeFEELLib();

    public NormalizeDateTimeLiteralsTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public NormalizeDateTimeLiteralsTransformer(BuildLogger logger) {
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
                    transform((TDecisionTable) expression);
                } else if (expression instanceof TLiteralExpression) {
                    logger.debug(String.format("Process literal expression in decision '%s'", decision.getName()));
                    transform((TLiteralExpression) expression);
                }
            }
        }
        this.transformRepository = false;
        return repository;
    }

    @Override
    public Pair<DMNModelRepository, List<TestLab>> transform(DMNModelRepository repository, List<TestLab> testCasesList) {
        if (isEmpty(repository, testCasesList)) {
            logger.warn("DMN repository or test cases list is empty; transformer will not run");
            return new Pair<>(repository, testCasesList);
        }

        // Transform model
        if (transformRepository) {
            transform(repository);
        }

        // Signavio export of TestLab seems to produce normalized date and time / time literals
        return new Pair<>(repository, testCasesList);
    }

    private void transform(TLiteralExpression literalExpression) {
        // Normalize LiteralExpression
        normalize(literalExpression);
    }

    private void transform(TDecisionTable decisionTable) {
        // Normalize InputClause
        for (TInputClause input: decisionTable.getInput()) {
            if (input != null) {
                normalize(input.getInputExpression());
            }
        }

        // Normalize OutputClause
        for (TOutputClause output: decisionTable.getOutput()) {
            if (output != null) {
                normalize(output.getOutputValues());
                normalize(output.getDefaultOutputEntry());
            }
        }

        // Normalize Rule
        for (TDecisionRule rule: decisionTable.getRule()) {
            // InputEntry
            for (TUnaryTests test: rule.getInputEntry()) {
                normalize(test);
            }
            // OutputEntry
            for (TLiteralExpression exp: rule.getOutputEntry()) {
                normalize(exp);
            }
        }
    }

    private void normalize(TLiteralExpression exp) {
        if (exp != null) {
            String newText = normalize(exp.getText());
            exp.setText(newText);
        }
    }

    private void normalize(TUnaryTests test) {
        if (test != null) {
            String newText = normalize(test.getText());
            test.setText(newText);
        }
    }

    String normalize(String text) {
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

        // Do not process unless neecessary
        if (!containsDateTimeLiterals(tokens)) {
            return text;
        }

        // Normalize
        StringBuilder newText = new StringBuilder();
        int size = tokens.size();
        int i=0;
        while (i < size -1) {
            token = tokens.get(i);
            String tokenText = token.getText();
            if (isDateTimeLiteralStart(i, tokens)) {
                // Replace literal
                int textIndex = i + 2;
                if (textIndex < size && tokens.get(textIndex).getType() == FEELLexer.STRING) {
                    newText.append(tokens.get(i).getText());
                    newText.append("(");
                    String newTokenText = normalizeLiteral(tokenText, tokens.get(textIndex).getText());
                    newText.append(newTokenText);
                    newText.append(")");
                    i += 4;
                } else {
                    newText.append(tokenText);
                    i++;
                }
            } else {
                newText.append(tokenText);
                i++;
            }
            newText.append(" ");
        }
        return newText.toString().trim();
    }

    private boolean containsDateTimeLiterals(List<Token> tokens) {
        for (int i=0; i<tokens.size()-1; i++) {
            if (isDateTimeLiteralStart(i, tokens)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDateTimeLiteralStart(int index, List<Token> tokens) {
        // ('date and time' | 'time' ) '(' STRING ')'
        if (!(isToken(index, tokens, FEELLexer.NAME) && (tokens.get(index).getText().equals("date and time") || tokens.get(index).getText().equals("time")))) {
            return false;
        }
        if (!isToken(index + 1, tokens, FEELLexer.PAREN_OPEN)) {
            return false;
        }
        if (!isToken(index + 2, tokens, FEELLexer.STRING)) {
            return false;
        }
        if (!isToken(index + 3, tokens, FEELLexer.PAREN_CLOSE)) {
            return false;
        }
        return true;
    }

    private boolean isToken(int index, List<Token> tokens, int kind) {
        return index < tokens.size() && tokens.get(index).getType() == kind;
    }

    private String normalizeLiteral(String feelFunctionName, String literalText) {
        try {
            Temporal temporal;
            String rawText = literalText.replaceAll("\"", "");
            if ("date and time".equals(feelFunctionName)) {
                // date and time
                temporal = this.feelLib.dateAndTime(rawText).withZoneSameInstant(ZoneOffset.UTC);
            } else {
                // time
                temporal = this.feelLib.time(rawText).withOffsetSameInstant(ZoneOffset.UTC);
            }
            return String.format("\"%s\"", this.feelLib.string(temporal));
        } catch (Exception e) {
            return literalText;
        }
    }

    private Lexer makeLexer(String text) {
        CharStream cs = CharStreams.fromString(text);
        return new FEELLexer(cs);
    }
}
