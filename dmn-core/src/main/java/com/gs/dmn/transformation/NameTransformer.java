/**
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

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.omg.dmn.tck.marshaller._20160719.TestCases;
import org.omg.spec.dmn._20151101.dmn.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import java.lang.reflect.Field;
import java.util.*;

public abstract class NameTransformer extends SimpleDMNTransformer<TestCases> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ToJavaNameTransformer.class);
    protected final BuildLogger logger;
    private NameMappings namesMapping;

    protected NameTransformer(BuildLogger logger) {
        this.logger = logger;
    }

    @Override
    public TDefinitions transform(TDefinitions definitions) {
        this.namesMapping = new NameMappings();
        transform(definitions, namesMapping);
        return definitions;
    }

    @Override
    public Pair<TDefinitions, TestCases> transform(TDefinitions definitions, TestCases testCases) {
        if (namesMapping == null) {
            transform(definitions);
        }

        // Clean each TestCase
        if (testCases != null) {
            for(TestCases.TestCase testCase: testCases.getTestCase()) {
                transform(testCase);
            }
        }
        return new Pair<>(definitions, testCases);
    }

    private void transform(TestCases.TestCase testCase) {
        // Rename
        for (TestCases.TestCase.InputNode n : testCase.getInputNode()) {
            String newName = namesMapping.get(n.getName());
            n.setName(newName);
        }
        for (TestCases.TestCase.ResultNode n : testCase.getResultNode()) {
            String newName = namesMapping.get(n.getName());
            n.setName(newName);
        }
    }

    private void transform(TDefinitions definitions, NameMappings namesMapping) {
        // Rename
        for (JAXBElement<? extends TDRGElement> element : definitions.getDrgElement()) {
            TDRGElement value = element.getValue();
            if (value instanceof TInputData) {
                addNameMapping(value, namesMapping);
                renameElement(value, namesMapping);
                renameElement(((TInputData) value).getVariable(), namesMapping);
            } else if (value instanceof TBusinessKnowledgeModel) {
                addNameMapping(value, namesMapping);
                renameElement(value, namesMapping);
                renameElement(((TBusinessKnowledgeModel) value).getVariable(), namesMapping);
            } else if (value instanceof TDecision) {
                addNameMapping(value, namesMapping);
                renameElement(value, namesMapping);
                renameElement(((TDecision) value).getVariable(), namesMapping);
            }
        }

        // Replace references
        for (JAXBElement<? extends TDRGElement> element : definitions.getDrgElement()) {
            TDRGElement value = element.getValue();
            if (value instanceof TInputData) {
            } else if (value instanceof TBusinessKnowledgeModel) {
                TFunctionDefinition encapsulatedLogic = ((TBusinessKnowledgeModel) value).getEncapsulatedLogic();
                List<TInformationItem> formalParameterList = encapsulatedLogic.getFormalParameter();
                for(TInformationItem param: formalParameterList) {
                    addNameMapping(param, namesMapping);
                    renameElement(param, namesMapping);
                }
                replaceNames(encapsulatedLogic.getExpression().getValue(), namesMapping);
            } else if (value instanceof TDecision) {
                replaceNames(((TDecision) value).getExpression().getValue(), namesMapping);
            } else {
            }
        }
    }

    private void replaceNames(TExpression value, NameMappings namesMapping) {
        if (value instanceof TLiteralExpression) {
            replaceNamesInText((TLiteralExpression) value, namesMapping);
        } else if (value instanceof TDecisionTable) {
            for (TInputClause input : ((TDecisionTable) value).getInput()) {
                TLiteralExpression inputExpression = input.getInputExpression();
                replaceNamesInText(inputExpression, namesMapping);
            }
        } else if (value instanceof TFunctionDefinition) {
            for(TInformationItem parameter: ((TFunctionDefinition) value).getFormalParameter()) {
                addNameMapping(parameter, namesMapping);
                renameElement(parameter, namesMapping);
            }
        } else if (value instanceof TInvocation) {
            JAXBElement<? extends TExpression> expression = ((TInvocation) value).getExpression();
            if (expression != null && expression.getValue() != null) {
                replaceNames(expression.getValue(), namesMapping);
            }
            List<TBinding> bindingList = ((TInvocation) value).getBinding();
            for(TBinding binding: bindingList) {
                addNameMapping(binding.getParameter(), namesMapping);
                renameElement(binding.getParameter(), namesMapping);
            }
            for(TBinding binding: bindingList) {
                replaceNames(binding.getExpression().getValue(), namesMapping);
            }
        } else if (value instanceof TContext) {
            List<TContextEntry> contextEntry = ((TContext) value).getContextEntry();
            for(TContextEntry ce: contextEntry) {
                JAXBElement<? extends TExpression> expression = ce.getExpression();
                if (expression != null && expression.getValue() != null) {
                    replaceNames(expression.getValue(), namesMapping);
                }
            }
        } else if (value instanceof TRelation) {
            for(TInformationItem ii: ((TRelation) value).getColumn()) {
                addNameMapping(ii, namesMapping);
                renameElement(ii, namesMapping);
            }
        } else {
            throw new UnsupportedOperationException("Not supported yet " + value.getClass().getSimpleName());
        }
    }

    private void replaceNamesInText(TLiteralExpression value, NameMappings namesMapping) {
        String text = value.getText();

        for (String oldName: namesMapping.orderedKeys()) {
            String newName = namesMapping.get(oldName);
            text = text.replaceAll(oldName, newName);
        }

        setField(value, "text", text);
    }

    private void renameElement(TNamedElement element, NameMappings namesMapping) {
        if (element != null) {
            String fieldName = "name";
            String newValue = namesMapping.get(element.getName());
            setField(element, fieldName, newValue);
        }
    }

    private void setField(TDMNElement element, String fieldName, String newName) {
        try {
            Field nameField = FieldUtils.getField(element.getClass(), fieldName, true);
            nameField.set(element, newName);
        } catch (Exception e) {
            throw new DMNRuntimeException("Cannot set field 'name'", e);
        }
    }

    public abstract String transformName(String name);

    protected abstract void addNameMapping(TNamedElement element, NameMappings namesMapping);
}

class NameMappings {
    private final Map<String, String> mappings = new LinkedHashMap<>();
    private List<String> orderedKeys;

    public String get(String name) {
        return mappings.get(name);
    }

    public void put(String key, String value) {
        this.mappings.put(key, value);
    }

    public List<String> orderedKeys() {
        if (orderedKeys == null) {
            orderedKeys = new ArrayList<>(mappings.keySet());
            Collections.sort(orderedKeys, (o1, o2) -> o2.length() - o1.length());
        }
        return orderedKeys;
    }

    public List<String> keys() {
        return new ArrayList<>(mappings.keySet());
    }

    public List<String> values() {
        return new ArrayList<>(mappings.values());
    }
}