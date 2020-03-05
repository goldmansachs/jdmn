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

import org.junit.Test;
import org.omg.spec.dmn._20180521.model.TLiteralExpression;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class AddMissingImportPrefixInDTTransformerTest {
    private AddMissingImportPrefixInDTTransformer transformer = new AddMissingImportPrefixInDTTransformer();
    private Set<String> names = new LinkedHashSet<>(Arrays.asList("ident1", "ident2"));

    @Test
    public void testAddMissingPrefixWhenNull() {
        String text = null;
        TLiteralExpression exp = makeExpression(text);
        transformer.addMissingPrefix(exp, names);
        assertEquals(text, exp.getText());
    }

    @Test
    public void testAddMissingPrefixWhenEmpty() {
        String text = "";
        TLiteralExpression exp = makeExpression(text);
        transformer.addMissingPrefix(exp, names);
        assertEquals(text, exp.getText());
    }

    @Test
    public void testAddMissingPrefixWhenLiteral() {
        TLiteralExpression exp = makeExpression("\"ident1\"");
        transformer.addMissingPrefix(exp, names);
        assertEquals("\"ident1\"  ", exp.getText());
    }

    @Test
    public void testAddMissingPrefix() {
        TLiteralExpression exp = makeExpression("ident1+ident2");
        transformer.addMissingPrefix(exp, names);
        assertEquals("ident1.ident1 + ident2.ident2  ", exp.getText());
    }

    private TLiteralExpression makeExpression(String text) {
        TLiteralExpression exp = new TLiteralExpression();
        exp.setText(text);
        return exp;
    }
}