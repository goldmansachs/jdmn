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
import com.gs.dmn.feel.analysis.scanner.LexicalContext;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import org.apache.commons.lang3.StringUtils;
import org.omg.dmn.tck.marshaller._20160719.TestCases;
import org.omg.dmn.tck.marshaller._20160719.ValueType;
import org.omg.spec.dmn._20191111.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.gs.dmn.feel.analysis.scanner.ContextDependentFEELLexer.*;

public abstract class NameTransformer extends SimpleDMNTransformer<TestCases> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NameTransformer.class);
    private DMNModelRepository repository;

    public static boolean isSimpleNameStart(int codePoint) {
        return Character.isJavaIdentifierStart(codePoint);
    }

    public static boolean isSimpleNamePart(int codePoint) {
        return Character.isJavaIdentifierPart(codePoint);
    }

    public static boolean isSimpleName(String name) {
        if (!isSimpleNameStart(name.codePointAt(0))) {
            return false;
        }
        for (int cp : name.codePoints().toArray()) {
            if (!(isSimpleNamePart(cp))) {
                return false;
            }
        }
        return true;
    }

    protected final BuildLogger logger;
    private boolean transformDefinition = true;
    private final Set<TDMNElement> renamedElements = new LinkedHashSet<>();

    protected NameTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    protected NameTransformer(BuildLogger logger) {
        this.logger = logger;
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("DMN repository is empty; transformer will not run");
            return repository;
        }

        this.repository = repository;
        transformDefinitions(repository);
        this.transformDefinition = false;
        return repository;
    }

    @Override
    public Pair<DMNModelRepository, List<TestCases>> transform(DMNModelRepository repository, List<TestCases> testCasesList) {
        if (isEmpty(repository, testCasesList)) {
            logger.warn("DMN repository or test cases list is empty; transformer will not run");
            return new Pair<>(repository, testCasesList);
        }

        // Transform model
        if (transformDefinition) {
            transform(repository);
        }

        // Transform test cases
        for (TestCases testCases: testCasesList) {
            if (testCases != null) {
                for (TestCases.TestCase testCase : testCases.getTestCase()) {
                    transform(testCase);
                }
            }
        }
        return new Pair<>(repository, testCasesList);
    }

    protected void transform(TestCases.TestCase testCase) {
        // Rename
        for (TestCases.TestCase.InputNode n : testCase.getInputNode()) {
            String newName = transformName(n.getName());
            n.setName(newName);
            rename(n);
        }
        for (TestCases.TestCase.ResultNode n : testCase.getResultNode()) {
            String newName = transformName(n.getName());
            n.setName(newName);
            rename(n.getExpected());
        }
    }

    protected void rename(ValueType valueType) {
        if (valueType instanceof ValueType.Component) {
            String newName = transformName(((ValueType.Component) valueType).getName());
            ((ValueType.Component) valueType).setName(newName);
        }
        JAXBElement<ValueType.List> jaxbList = valueType.getList();
        if (jaxbList != null) {
            ValueType.List list = jaxbList.getValue();
            for (ValueType vt : list.getItem()) {
                rename(vt);
            }
        }
        List<ValueType.Component> componentList = valueType.getComponent();
        if (componentList != null) {
            for (ValueType.Component component : componentList) {
                rename(component);
            }
        }
    }

    protected void transformDefinitions(DMNModelRepository repository) {
        replace(repository);
        rename(repository);
    }

    // Replace old names with new names in expressions
    protected void replace(DMNModelRepository repository) {
        for (TDefinitions definitions: repository.getAllDefinitions()) {
            for (TDRGElement element : repository.findDRGElements(definitions)) {
                if (element instanceof TBusinessKnowledgeModel) {
                    // Replace old names with new names in body
                    LexicalContext lexicalContext = makeLexicalContext(element, repository);
                    TFunctionDefinition encapsulatedLogic = ((TBusinessKnowledgeModel) element).getEncapsulatedLogic();
                    if (encapsulatedLogic != null) {
                        JAXBElement<? extends TExpression> expression = encapsulatedLogic.getExpression();
                        if (expression != null) {
                            replace(expression.getValue(), lexicalContext);
                        }
                    }
                } else if (element instanceof TDecision) {
                    // Replace old names with new names in body
                    LexicalContext lexicalContext = makeLexicalContext(element, repository);
                    JAXBElement<? extends TExpression> expression = ((TDecision) element).getExpression();
                    if (expression != null) {
                        replace(expression.getValue(), lexicalContext);
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
            for (TInputClause input : ((TDecisionTable) expression).getInput()) {
                TLiteralExpression inputExpression = input.getInputExpression();
                replaceNamesInText(inputExpression, lexicalContext);
            }
            for (TOutputClause output : ((TDecisionTable) expression).getOutput()) {
                TLiteralExpression defaultOutputEntry = output.getDefaultOutputEntry();
                replaceNamesInText(defaultOutputEntry, lexicalContext);
            }
            for (TDecisionRule rule : ((TDecisionTable) expression).getRule()) {
                for (TUnaryTests inputEntry : rule.getInputEntry()) {
                    replaceNamesInText(inputEntry, lexicalContext);
                }
                for (TLiteralExpression outputEntry : rule.getOutputEntry()) {
                    replaceNamesInText(outputEntry, lexicalContext);
                }
            }
        } else if (expression instanceof TFunctionDefinition) {
            JAXBElement<? extends TExpression> jaxbElement = ((TFunctionDefinition) expression).getExpression();
            TExpression body = jaxbElement.getValue();
            LexicalContext bodyContext = new LexicalContext(lexicalContext);
            for (TInformationItem parameter : ((TFunctionDefinition) expression).getFormalParameter()) {
                bodyContext.addName(parameter.getName());
            }
            replace(body, lexicalContext);
        } else if (expression instanceof TInvocation) {
            JAXBElement<? extends TExpression> jaxbElement = ((TInvocation) expression).getExpression();
            if (jaxbElement != null && jaxbElement.getValue() != null) {
                replace(jaxbElement.getValue(), lexicalContext);
            }
            List<TBinding> bindingList = ((TInvocation) expression).getBinding();
            for (TBinding binding : bindingList) {
                replace(binding.getExpression().getValue(), lexicalContext);
            }
        } else if (expression instanceof TContext) {
            List<TContextEntry> contextEntry = ((TContext) expression).getContextEntry();
            LexicalContext entryContext = new LexicalContext(lexicalContext);
            for (TContextEntry ce : contextEntry) {
                TInformationItem variable = ce.getVariable();
                if (variable != null) {
                    entryContext.addName(variable.getName());
                }
                JAXBElement<? extends TExpression> jaxbElement = ce.getExpression();
                if (jaxbElement != null && jaxbElement.getValue() != null) {
                    replace(jaxbElement.getValue(), entryContext);
                }
            }
        } else if (expression instanceof TRelation) {
            List<TList> lists = ((TRelation) expression).getRow();
            LexicalContext relationContext = new LexicalContext(lexicalContext);
            for (TInformationItem ii : ((TRelation) expression).getColumn()) {
                relationContext.addName(ii.getName());
            }
            for (TList list : lists) {
                replace(list, relationContext);
            }
        } else if (expression instanceof TList) {
            List<JAXBElement<? extends TExpression>> jaxbElements = ((TList) expression).getExpression();
            for (JAXBElement<? extends TExpression> jaxbElement : jaxbElements) {
                TExpression subExpression = jaxbElement.getValue();
                replace(subExpression, lexicalContext);
            }
        } else {
            throw new UnsupportedOperationException("Not supported yet " + expression.getClass().getSimpleName());
        }
    }

    protected void rename(DMNModelRepository repository) {
        for (TDefinitions definitions: repository.getAllDefinitions()) {
            for (TImport imp: definitions.getImport()) {
                if (imp != null && imp.getName() != null) {
                    String oldName = imp.getName();
                    String newName = transformName(oldName);
                    if (!oldName.equals(newName)) {
                        imp.setName(newName);
                        PrefixNamespaceMappings prefixNamespaceMappings = repository.getPrefixNamespaceMappings();
                        prefixNamespaceMappings.renameKey(oldName, newName);
                    }
                }
            }
        }
        for (TDefinitions definitions: repository.getAllDefinitions()) {
            for (TItemDefinition itemDefinition : repository.findItemDefinitions(definitions)) {
                renameItemDefinitionMembers(itemDefinition);
            }
            for (TDRGElement element : repository.findDRGElements(definitions)) {
                if (element instanceof TInputData) {
                    // Rename element and variable
                    renameElement(element);
                    renameElement(((TInputData) element).getVariable());
                } else if (element instanceof TBusinessKnowledgeModel) {
                    // Rename element and variable
                    renameElement(element);
                    renameElement(((TBusinessKnowledgeModel) element).getVariable());

                    // Rename in body
                    TFunctionDefinition encapsulatedLogic = ((TBusinessKnowledgeModel) element).getEncapsulatedLogic();
                    if (encapsulatedLogic != null) {
                        List<TInformationItem> formalParameterList = encapsulatedLogic.getFormalParameter();
                        for (TInformationItem param : formalParameterList) {
                            renameElement(param);
                        }
                        rename(encapsulatedLogic);
                    }
                } else if (element instanceof TDecision) {
                    // Rename element and variable
                    renameElement(element);
                    renameElement(((TDecision) element).getVariable());

                    // Rename in body
                    JAXBElement<? extends TExpression> expression = ((TDecision) element).getExpression();
                    if (expression != null) {
                        rename(expression.getValue());
                    }
                }
            }
        }
    }

    protected void rename(TExpression expression) {
        if (expression instanceof TLiteralExpression) {
        } else if (expression instanceof TDecisionTable) {
            for (TOutputClause outputClause : ((TDecisionTable) expression).getOutput()) {
                renameElement(outputClause);
            }
        } else if (expression instanceof TFunctionDefinition) {
            for (TInformationItem parameter : ((TFunctionDefinition) expression).getFormalParameter()) {
                renameElement(parameter);
            }
            JAXBElement<? extends TExpression> jaxbElement = ((TFunctionDefinition) expression).getExpression();
            if (jaxbElement != null && jaxbElement.getValue() != null) {
                rename(jaxbElement.getValue());
            }
        } else if (expression instanceof TInvocation) {
            List<TBinding> bindingList = ((TInvocation) expression).getBinding();
            for (TBinding binding : bindingList) {
                renameElement(binding.getParameter());
            }
        } else if (expression instanceof TContext) {
            List<TContextEntry> contextEntry = ((TContext) expression).getContextEntry();
            for (TContextEntry ce : contextEntry) {
                TInformationItem variable = ce.getVariable();
                if (variable != null) {
                    renameElement(variable);
                }
                JAXBElement<? extends TExpression> jaxbElement = ce.getExpression();
                if (jaxbElement != null && jaxbElement.getValue() != null) {
                    rename(jaxbElement.getValue());
                }
            }
        } else if (expression instanceof TRelation) {
            for (TInformationItem ii : ((TRelation) expression).getColumn()) {
                renameElement(ii);
            }
        } else if (expression instanceof TList) {
        } else {
            throw new UnsupportedOperationException("Not supported yet " + expression.getClass().getSimpleName());
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
            for (TInformationItem param : formalParameterList) {
                names.add(param.getName());
            }
        }
        if (informationRequirement != null) {
            for (TInformationRequirement tir : informationRequirement) {
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
            for (TKnowledgeRequirement tkr : knowledgeRequirement) {
                TDMNElementReference requiredKnowledge = tkr.getRequiredKnowledge();
                addName(element, requiredKnowledge.getHref(), names, repository);
            }
        }

        LexicalContext lexicalContext = new LexicalContext(names);
        lexicalContext.addNames(this.repository.getImportedNames());

        return lexicalContext;
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
        StringBuilder newText = new StringBuilder();
        lexicalContext.addName("grouping separator");
        lexicalContext.addName("decimal separator");
        lexicalContext.addName("start position");

        if (!StringUtils.isEmpty(text)) {
            int index = 0;
            replaceNamesInText(text, index, lexicalContext, newText);
        }

        return newText.toString();
    }

    private int replaceNamesInText(String text, int index, LexicalContext lexicalContext, StringBuilder newText) {
        while (index < text.length()) {
            int ch = codePointAt(text, index);
            if (ch == '"') {
                index = appendStrings(text, index, newText);
            } else if (ch == '{') {
                index = replaceContextKeys(text, index, lexicalContext, newText);
            } else if (ch == '[') {
                index = replaceNamesInList(text, index, lexicalContext, newText);
            } else if (isNameStartChar(ch)) {
                index = replaceIdentifiers(text, index, lexicalContext, newText, ch);
            } else {
                newText.appendCodePoint(ch);
                index = nextIndex(text, index);
            }
        }
        return index;
    }

    private int appendStrings(String text, int index, StringBuilder newText) {
        // skip strings
        newText.appendCodePoint(codePointAt(text, index));
        index = nextIndex(text, index);
        while (index < text.length()) {
            newText.appendCodePoint(codePointAt(text, index));
            if (codePointAt(text, index) == '"') {
                index = nextIndex(text, index);
                break;
            } else {
                index = nextIndex(text, index);
            }
        }
        return index;
    }

    protected int replaceContextKeys(String text, int index, LexicalContext lexicalContext, StringBuilder newText) {
        // process {
        newText.appendCodePoint(codePointAt(text, index));
        index = nextIndex(text, index);

        // process context
        while (index < text.length()) {
            if (codePointAt(text, index) == '}') {
                break;
            }
            // collect key
            StringBuilder keyBuilder = new StringBuilder();
            while (index < text.length() && codePointAt(text, index) != ':' && codePointAt(text, index) != '}') {
                keyBuilder.appendCodePoint(codePointAt(text, index));
                index = nextIndex(text, index);
            }
            // transform and add key
            String key = keyBuilder.toString().trim();
            if (key.length() != 0) {
                if (key.startsWith("\"") && key.endsWith("\"")) {
                    key = key.substring(1, key.length() - 1);
                    newText.append("\"").append(transformName(key)).append("\"");
                } else {
                    newText.append(transformName(key));
                }
            }
            if (index < text.length() && codePointAt(text, index) == ':') {
                newText.append(":");
                index = nextIndex(text, index);
            }
            // process value
            while (index < text.length() && codePointAt(text, index) != ',' && codePointAt(text, index) != '}') {
                if (codePointAt(text, index) == '\"') {
                    index = appendStrings(text, index, newText);
                } else if (codePointAt(text, index) == '{') {
                    index = replaceContextKeys(text, index, lexicalContext, newText);
                } else if (codePointAt(text, index) == '[') {
                    index = replaceNamesInList(text, index, lexicalContext, newText);
                } else {
                    newText.appendCodePoint(codePointAt(text, index));
                    index = nextIndex(text, index);
                }
            }
            if (index < text.length() && codePointAt(text, index) == ',') {
                newText.append(", ");
                index = nextIndex(text, index);
            }
        }
        if (index < text.length() && codePointAt(text, index) == '}') {
            newText.append('}');
            index = nextIndex(text, index);
        }
        return index;
    }

    protected int replaceNamesInList(String text, int index, LexicalContext lexicalContext, StringBuilder newText) {
        // process [
        newText.appendCodePoint(codePointAt(text, index));
        index = nextIndex(text, index);

        // process list
        while (index < text.length()) {
            int ch = codePointAt(text, index);
            if (ch == ']') {
                newText.append(']');
                index = nextIndex(text, index);
                break;
            } else if (ch == '\"') {
                index = appendStrings(text, index, newText);
            } else if (ch == '{') {
                index = replaceContextKeys(text, index, lexicalContext, newText);
            } else if (ch == '[') {
                index = replaceNamesInList(text, index, lexicalContext, newText);
            } else if (isNameStartChar(ch)) {
                index = replaceIdentifiers(text, index, lexicalContext, newText, ch);
            } else {
                newText.appendCodePoint(ch);
                index = nextIndex(text, index);
            }
        }
        return index;
    }

    protected int replaceIdentifiers(String text, int index, LexicalContext lexicalContext, StringBuilder newText, int ch) {
        // Check keywords
        boolean foundKeyword = false;
        for (String keyword : KEYWORDS.keySet()) {
            if (text.startsWith(keyword, index)) {
                newText.append(keyword);
                index += keyword.length();
                foundKeyword = true;
                break;
            }
        }
        if (!foundKeyword) {
            // Check names
            boolean foundName = false;
            for (String name : lexicalContext.orderedNames()) {
                if (text.startsWith(name, index)) {
                    newText.append(transformName(name));
                    index += name.length();
                    foundName = true;
                    break;
                }
            }
            if (!foundName) {
                do {
                    newText.appendCodePoint(ch);
                    index = nextIndex(text, index);
                    if (index < text.length()) {
                        ch = codePointAt(text, index);
                    } else {
                        break;
                    }
                } while (isNamePartChar(ch));
            }
        }
        return index;
    }

    protected void renameItemDefinitionMembers(TItemDefinition itemDefinition) {
        List<TItemDefinition> itemComponent = itemDefinition.getItemComponent();
        if (itemComponent != null) {
            for (TItemDefinition member : itemComponent) {
                renameElement(member);
                renameItemDefinitionMembers(member);
            }
        }
    }

    protected void renameElement(TNamedElement element) {
        if (element == null) {
            return;
        }
        if (renamedElements.contains(element)) {
            return;
        }
        renamedElements.add(element);
        if (element.getName() != null) {
            String newValue = transformName(element.getName());
            setName(element, newValue);
        }
    }

    protected void renameElement(TOutputClause element) {
        if (element == null) {
            return;
        }
        if (renamedElements.contains(element)) {
            return;
        }
        renamedElements.add(element);
        if (element.getName() != null) {
            String newValue = transformName(element.getName());
            setName(element, newValue);
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

    private int codePointAt(String text, int index) {
        return text.codePointAt(index);
    }

    private int nextIndex(String text, int index) {
        int codePoint = text.codePointAt(index);
        index += Character.charCount(codePoint);
        return index;
    }
}
