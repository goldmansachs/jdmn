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
package com.gs.dmn.ast;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SpecialVariableTransformerVisitor extends DefaultDMNVisitor {
    public SpecialVariableTransformerVisitor() {
    }

    @Override
    public <C> Object visit(TDecisionTable element, C context) {
        // Collect input expressions
        List<String> inputExpressions = new ArrayList<>();
        for (TInputClause ic : element.getInput()) {
            String text = ic.getInputExpression().getText();
            inputExpressions.add(text);
        }
        // Replace them
        for (TDecisionRule rule : element.getRule()) {
            List<TUnaryTests> inputEntries = rule.getInputEntry();
            for (int i=0; i<inputEntries.size(); i++) {
                TUnaryTests unaryTests = inputEntries.get(i);
                String inputExp = inputExpressions.get(i);
                updateUnaryTests(unaryTests, inputExp);
            }
        }

        return element;
    }

    void updateUnaryTests(TUnaryTests unaryTests, String inputExp) {
        String text = unaryTests.getText();
        if (StringUtils.isBlank(text) || StringUtils.isBlank(inputExp)) {
            return;
        }

        int textLen = text.length();
        int inputLen = inputExp.length();
        StringBuilder newText = new StringBuilder();
        int i = 0;
        while (true) {
            if (i == textLen) {
                break;
            }
            int index = text.indexOf(inputExp, i);
            if (index == -1) {
                break;
            } else {
                // Found it
                appendFrom(text, newText, i, index);
                if (okChar(index - 1, text, textLen) && okChar(index + inputLen, text, textLen)) {
                    newText.append("?");
                } else {
                    newText.append(inputExp);
                }
                i = index + inputLen;
            }
        }
        appendFrom(text, newText, i, textLen);
        unaryTests.setText(newText.toString());
    }

    private void appendFrom(String text, StringBuilder newText, int start, int end) {
        for (int j=start; j<end; j++) {
            newText.append(text.charAt(j));
        }
    }

    private boolean okChar(int i, String text, int len) {
        if (0 <= i && i < len) {
            char ch = text.charAt(i);
            return !Character.isJavaIdentifierPart(ch);
        } else {
            return true;
        }
    }
}
