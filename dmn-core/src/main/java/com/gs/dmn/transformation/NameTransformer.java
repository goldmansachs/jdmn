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
import com.gs.dmn.ast.*;
import com.gs.dmn.feel.analysis.scanner.ContextDependentFEELLexer;
import com.gs.dmn.feel.analysis.scanner.LexicalContext;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.tck.ast.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

import static com.gs.dmn.feel.analysis.syntax.antlrv4.FEELLexer.*;
import static org.antlr.v4.runtime.Recognizer.EOF;

public abstract class NameTransformer extends SimpleDMNTransformer<TestCases> {
    protected NameTransformer(BuildLogger logger) {
        super(logger);
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("DMN repository is empty; transformer will not run");
            return repository;
        }

        transformDefinitions(repository);
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

        return new Pair<>(repository, testCasesList);
    }

    protected void transformDefinitions(DMNModelRepository repository) {
        replace(repository);
    }

    // Replace old names with new names in expressions
    protected void replace(DMNModelRepository repository) {
        for (TDefinitions definitions: repository.getAllDefinitions()) {
            for (TDRGElement element: repository.findDRGElements(definitions)) {
                if (element instanceof TBusinessKnowledgeModel) {
                    // Replace old names with new names in body
                    LexicalContext lexicalContext = makeLexicalContext(element, repository);
                    TFunctionDefinition encapsulatedLogic = ((TBusinessKnowledgeModel) element).getEncapsulatedLogic();
                    if (encapsulatedLogic != null) {
                        TExpression expression = encapsulatedLogic.getExpression();
                        if (expression != null) {
                            replace(expression, lexicalContext);
                        }
                    }
                } else if (element instanceof TDecision) {
                    // Replace old names with new names in body
                    LexicalContext lexicalContext = makeLexicalContext(element, repository);
                    TExpression expression = ((TDecision) element).getExpression();
                    if (expression != null) {
                        replace(expression, lexicalContext);
                    }
                }
            }
        }
    }

    // Replace old names with new names in expressions
    protected void replace(TExpression expression, LexicalContext lexicalContext) {
        if (expression instanceof TLiteralExpression) {
            replaceNamesInText((TLiteralExpression) expression, lexicalContext);
        } else if (expression instanceof TDecisionTable) {
            for (TInputClause input: ((TDecisionTable) expression).getInput()) {
                TLiteralExpression inputExpression = input.getInputExpression();
                replaceNamesInText(inputExpression, lexicalContext);
            }
            for (TOutputClause output: ((TDecisionTable) expression).getOutput()) {
                TLiteralExpression defaultOutputEntry = output.getDefaultOutputEntry();
                replaceNamesInText(defaultOutputEntry, lexicalContext);
            }
            for (TDecisionRule rule: ((TDecisionTable) expression).getRule()) {
                for (TUnaryTests inputEntry: rule.getInputEntry()) {
                    replaceNamesInText(inputEntry, lexicalContext);
                }
                for (TLiteralExpression outputEntry: rule.getOutputEntry()) {
                    replaceNamesInText(outputEntry, lexicalContext);
                }
            }
        } else if (expression instanceof TFunctionDefinition) {
            TExpression body = ((TFunctionDefinition) expression).getExpression();
            List<String> bodyNames = new ArrayList<>(lexicalContext.getNames());
            for (TInformationItem parameter: ((TFunctionDefinition) expression).getFormalParameter()) {
                bodyNames.add(parameter.getName());
            }
            replace(body, new LexicalContext(bodyNames));
        } else if (expression instanceof TInvocation) {
            TExpression exp = ((TInvocation) expression).getExpression();
            if (exp != null) {
                replace(exp, lexicalContext);
            }
            List<TBinding> bindingList = ((TInvocation) expression).getBinding();
            for (TBinding binding: bindingList) {
                replace(binding.getExpression(), lexicalContext);
            }
        } else if (expression instanceof TContext) {
            List<TContextEntry> contextEntry = ((TContext) expression).getContextEntry();
            List<String> entryNames = new ArrayList<>(lexicalContext.getNames());
            for (TContextEntry ce: contextEntry) {
                TInformationItem variable = ce.getVariable();
                if (variable != null) {
                    entryNames.add(variable.getName());
                }
                TExpression exp = ce.getExpression();
                if (exp != null) {
                    replace(exp, new LexicalContext(entryNames));
                }
            }
        } else if (expression instanceof TRelation) {
            List<TList> lists = ((TRelation) expression).getRow();
            List<String> relationNames = new ArrayList<>(lexicalContext.getNames());
            for (TInformationItem ii: ((TRelation) expression).getColumn()) {
                relationNames.add(ii.getName());
            }
            for (TList list: lists) {
                replace(list, new LexicalContext(relationNames));
            }
        } else if (expression instanceof TList) {
            List<? extends TExpression> expressionList = ((TList) expression).getExpression();
            for (TExpression subExpression: expressionList) {
                replace(subExpression, lexicalContext);
            }
        } else if (expression instanceof TConditional) {
            TChildExpression if_ = ((TConditional) expression).getIf();
            replace(if_, lexicalContext);
            TChildExpression then_ = ((TConditional) expression).getThen();
            replace(then_, lexicalContext);
            TChildExpression else_ = ((TConditional) expression).getElse();
            replace(else_, lexicalContext);
        } else if (expression instanceof TFilter) {
            TChildExpression in = ((TFilter) expression).getIn();
            replace(in, lexicalContext);
            TChildExpression match = ((TFilter) expression).getMatch();
            replace( match, lexicalContext);
        } else if (expression instanceof TFor) {
            TChildExpression in = ((TFor) expression).getIn();
            replace(in, lexicalContext);
            TChildExpression return_ = ((TFor) expression).getReturn();
            replace( return_, lexicalContext);
        } else if (expression instanceof TSome) {
            TChildExpression in = ((TSome) expression).getIn();
            replace(in, lexicalContext);
            TChildExpression satisfies = ((TSome) expression).getSatisfies();
            replace(satisfies, lexicalContext);
        } else if (expression instanceof TEvery) {
            TChildExpression in = ((TEvery) expression).getIn();
            replace(in, lexicalContext);
            TChildExpression satisfies = ((TEvery) expression).getSatisfies();
            replace(satisfies, lexicalContext);
        } else {
            throw new UnsupportedOperationException("Not supported yet " + expression.getClass().getSimpleName());
        }
    }

    private void replace(TChildExpression childExpression, LexicalContext lexicalContext) {
        if (childExpression != null) {
            replace(childExpression.getExpression(), lexicalContext);
        }
    }

    protected LexicalContext makeLexicalContext(TDRGElement element, DMNModelRepository repository) {
        List<String> names = new ArrayList<>();

        List<TInformationRequirement> informationRequirement = null;
        List<TKnowledgeRequirement> knowledgeRequirement = null;
        if (element instanceof TDecision) {
            informationRequirement = ((TDecision) element).getInformationRequirement();
            knowledgeRequirement = ((TDecision) element).getKnowledgeRequirement();
        } else if (element instanceof TBusinessKnowledgeModel) {
            knowledgeRequirement = ((TBusinessKnowledgeModel) element).getKnowledgeRequirement();

            TFunctionDefinition encapsulatedLogic = ((TBusinessKnowledgeModel) element).getEncapsulatedLogic();
            List<TInformationItem> formalParameterList = encapsulatedLogic.getFormalParameter();
            for (TInformationItem param: formalParameterList) {
                names.add(param.getName());
            }
        }
        if (informationRequirement != null) {
            for (TInformationRequirement tir: informationRequirement) {
                TDMNElementReference requiredInput = tir.getRequiredInput();
                TDMNElementReference requiredDecision = tir.getRequiredDecision();
                if (requiredInput != null) {
                    String href = requiredInput.getHref();
                    addName(element, href, names, repository);
                }
                if (requiredDecision != null) {
                    String href = requiredDecision.getHref();
                    addName(element, href, names, repository);
                }
            }
        }
        if (knowledgeRequirement != null) {
            for (TKnowledgeRequirement tkr: knowledgeRequirement) {
                TDMNElementReference requiredKnowledge = tkr.getRequiredKnowledge();
                addName(element, requiredKnowledge.getHref(), names, repository);
            }
        }

        names.addAll(repository.getImportedNames(repository.getModel(element)));

        return new LexicalContext(names);
    }

    protected void addName(TDRGElement parent, String href, List<String> names, DMNModelRepository repository) {
        TDRGElement requiredDRG = repository.findDRGElementByRef(parent, href);
        if (requiredDRG != null) {
            names.add(requiredDRG.getName());
        }
    }

    protected void replaceNamesInText(TLiteralExpression literalExpression, LexicalContext lexicalContext) {
        if (literalExpression == null) {
            return;
        }

        String text = literalExpression.getText();
        String newText = replaceNamesInText(text, lexicalContext);
        setText(literalExpression, newText);
    }

    protected void replaceNamesInText(TUnaryTests unaryTests, LexicalContext lexicalContext) {
        if (unaryTests == null) {
            return;
        }

        String text = unaryTests.getText();
        String newText = replaceNamesInText(text, lexicalContext);
        setText(unaryTests, newText);
    }

    protected String replaceNamesInText(String text, LexicalContext lexicalContext) {
        List<String> names = new ArrayList<>(lexicalContext.getNames());
        names.add("grouping separator");
        names.add("decimal separator");
        names.add("start position");
        names.add("new item");
        lexicalContext = new LexicalContext(names);

        // Collect all tokens
        List<Pair<String, Token>> pairs = collectTokens(text, lexicalContext);

        // Scan tokens
        StringBuilder newText = new StringBuilder();
        for (int i=0; i<pairs.size(); i++) {
            Pair<String, Token> pair = pairs.get(i);
            newText.append(pair.getLeft());
            Token token = pair.getRight();
            String lexeme = token.getText();
            if (token.getType() == NAME) {
                // Transform lexeme if present in context
                if (isContextKey(token, pairs, i)) {
                    transformAndAddKey(token.getText(), newText);
                } else {
                    String newLexeme = lexeme;
                    for (String name: lexicalContext.getNames()) {
                        String transformedName = transformName(name);
                        if (!newLexeme.contains(transformedName)) {
                            String regExp = String.format("\\b%s\\b", name);
                            newLexeme = newLexeme.replaceAll(regExp, transformedName);
                        }
                    }
                    newText.append(newLexeme);
                }
            } else if (isContextKey(token, pairs, i)) {
                transformAndAddKey(token.getText(), newText);
            } else {
                newText.append(lexeme);
            }
        }
        return newText.toString();
    }

    private static List<Pair<String, Token>> collectTokens(String text, LexicalContext lexicalContext) {
        ContextDependentFEELLexer lexer = new ContextDependentFEELLexer(CharStreams.fromString(text));
        List<Pair<String, Token>> pairs = new ArrayList<>();
        Token token;
        do {
            Pair<String, Token> pair = lexer.nextToken(lexicalContext);
            token = pair.getRight();
            if (token.getType() != EOF) {
                pairs.add(pair);
            }
        } while (token.getType() != EOF);
        return pairs;
    }

    private boolean isContextKey(Token token, List<Pair<String, Token>> tokens, int index) {
        int type = token.getType();
        if (type != NAME && type != STRING) {
            return false;
        }
        if (!insideContext(index, tokens)) {
            return false;
        }
        Token previousToken = index == 0 ? null : tokens.get(index -1).getRight();
        return previousToken != null && (previousToken.getType() == BRACE_OPEN || previousToken.getType() == COMMA);
    }

    private boolean insideContext(int index, List<Pair<String, Token>> tokens) {
        boolean foundLeft = false;
        for (int i=index-1; i>=0; i--) {
            if (tokens.get(i).getRight().getType() == BRACE_OPEN) {
                foundLeft = true;
                break;
            }
        }
        if (!foundLeft) {
            return false;
        }
        boolean foundRight = false;
        for (int i=index+1; i<tokens.size(); i++) {
            if (tokens.get(i).getRight().getType() == BRACE_CLOSE) {
                foundRight = true;
                break;
            }
        }
        return foundRight;
    }

    private String transformKey(String key) {
        if (!key.isEmpty()) {
            if (key.startsWith("\"") && key.endsWith("\"")) {
                String name = key.substring(1, key.length() - 1);
                return String.format("\"%s\"", transformName(name));
            } else {
                return transformName(key);
            }
        }
        return key;
    }

    private void transformAndAddKey(String key, StringBuilder newText) {
        if (!key.isEmpty()) {
            key = transformKey(key);
            newText.append(key);
        }
    }

    protected void setName(TNamedElement element, String newName) {
        element.setName(newName);
    }

    protected void setName(TOutputClause element, String newName) {
        element.setName(newName);
    }

    protected void setText(TLiteralExpression element, String text) {
        element.setText(text);
    }

    protected void setText(TUnaryTests element, String text) {
        element.setText(text);
    }

    public abstract String transformName(String name);
}
