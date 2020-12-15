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

import com.gs.dmn.feel.analysis.scanner.LexicalContext;
import com.gs.dmn.runtime.Pair;
import org.junit.Test;
import org.omg.dmn.tck.marshaller._20160719.TestCases;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static com.gs.dmn.runtime.Assert.assertEquals;

public class ToSimpleNameTransformerTest extends NameTransformerTest {
    @Test
    public void testTransform() throws Exception {
        doTest("1.1", Arrays.asList("0004-lending.dmn"),
                "0004-lending-test-01.xml", new LinkedHashMap<String, Pair<String, String>>() {{
                    put("0004-lending.dmn", new Pair<>("http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b", "tns"));
                    put("0004-lending-test-01.xml", new Pair<>("http://www.w3.org/2001/XMLSchema-instance", "xsi"));
                }}
        );
        doTest("1.1", Arrays.asList("0007-date-time.dmn"),
                "0007-date-time-test-01.xml", new LinkedHashMap<String, Pair<String, String>>() {{
                    put("0007-date-time.dmn", new Pair<>("http://www.trisotech.com/definitions/_69430b3e-17b8-430d-b760-c505bf6469f9", "tns"));
                    put("0007-date-time-test-01.xml",  new Pair<>("http://www.w3.org/2001/XMLSchema-instance", "xsi"));
                }}
        );
        doTest("1.1", Arrays.asList("0034-drg-scopes.dmn") ,
                "0034-drg-scopes-test-01.xml", new LinkedHashMap<String, Pair<String, String>>() {{
                    put("0034-drg-scopes.dmn", new Pair<>("http://www.actico.com/spec/DMN/0.1.0/0034-drg-scopes", "tns"));
                    put("0034-drg-scopes-test-01.xml", new Pair<>("http://www.w3.org/2001/XMLSchema-instance", "xsi"));
                }}
        );
        doTest("1.2", Arrays.asList("0087-chapter-11-example.dmn") ,
                "0087-chapter-11-example-test-01.xml", new LinkedHashMap<String, Pair<String, String>>() {{
                    put("0087-chapter-11-example.dmn", new Pair<>("http://www.trisotech.com/definitions/_9d01a0c4-f529-4ad8-ad8e-ec5fb5d96ad4", "tns"));
                    put("0087-chapter-11-example-test-01.xml", new Pair<>("http://www.w3.org/2001/XMLSchema-instance", "xsi"));
                }}
        );
        doTest("1.2", Arrays.asList("0089-nested-inputdata-imports.dmn", "0089-model-a.dmn", "0089-model-b.dmn", "0089-model-b2.dmn"),
                "0089-nested-inputdata-imports-test-01.xml", new LinkedHashMap<String, Pair<String, String>>() {{
                    put("0089-nested-inputdata-imports.dmn", new Pair<>("http://www.trisotech.com/definitions/_10435dcd-8774-4575-a338-49dd554a0928", null));
                    put("0089-model-a.dmn", new Pair<>("http://www.trisotech.com/definitions/_ae5b3c17-1ac3-4e1d-b4f9-2cf861aec6d9", null));
                    put("0089-model-b.dmn", new Pair<>("http://www.trisotech.com/definitions/_2a1d771a-a899-4fef-abd6-fc894332337c", null));
                    put("0089-model-b2.dmn", new Pair<>("http://www.trisotech.com/definitions/_9d46ece4-a96c-4cb0-abc0-0ca121ac3768", null));
                    put("0089-nested-inputdata-imports-test-01.xml", new Pair<>("http://www.w3.org/2001/XMLSchema-instance", "xsi"));
                }}
        );
    }

    @Test
    public void testTransformName() {
        ToSimpleNameTransformer transformer = (ToSimpleNameTransformer) getTransformer();

        // Transform first name
        String firstName = transformer.transformName("abc ? x");
        assertEquals("abcX", firstName);

        // Transform second name
        String secondName = transformer.transformName("abc?x");
        assertEquals("abcX_1", secondName);

        // Transform names with unicode
        String result = transformer.transformName("a \uD83D\uDC0E bc");
        assertEquals("aBc", result);
    }

    @Test
    public void testContextKeys() {
        NameTransformer transformer = (NameTransformer) getTransformer();

        String result = transformer.replaceNamesInText("{foo bar: \"foo\"}", new LexicalContext());
        assertEquals("{fooBar: \"foo\"}", result);

        result = transformer.replaceNamesInText("{foo+bar: \"foo\"}", new LexicalContext());
        assertEquals("{fooBar_1: \"foo\"}", result);

        result = transformer.replaceNamesInText("{\"foo+bar((!!],foo\": \"foo\"}", new LexicalContext());
        assertEquals("{\"fooBarFoo\": \"foo\"}", result);

        result = transformer.replaceNamesInText("{\"\": \"foo\"}", new LexicalContext());
        assertEquals("{\"\": \"foo\"}", result);

        result = transformer.replaceNamesInText("{a: 1 + 2, b: a + 3}", new LexicalContext());
        assertEquals("{a: 1 + 2, b: a + 3}", result);

        result = transformer.replaceNamesInText("{a: 1 + 2, b: 3, c: {d e: a + b}}", new LexicalContext());
        assertEquals("{a: 1 + 2, b: 3, c: {dE: a + b}}", result);

        result = transformer.replaceNamesInText("[1,2,{a: [3,4]}] = [1,2,{a: [3,4], b: \"foo\"}]", new LexicalContext());
        assertEquals("[1,2,{a: [3,4]}] = [1,2,{a: [3,4], b: \"foo\"}]", result);

        result = new ToSimpleNameTransformer().replaceNamesInText("{\uD83D\uDC0E: \"bar\"}", new LexicalContext());
        assertEquals("{_: \"bar\"}", result);

        String text = "function(s1, s2) external {java:{class:\"java.lang.Math\",method signature:\"max(java.lang.String, java.lang.String)\"}}";
        LexicalContext context = new LexicalContext("mathMaxString");
        result = transformer.replaceNamesInText(text, context);
        assertEquals("function(s1, s2) external {java:{class:\"java.lang.Math\", methodSignature:\"max(java.lang.String, java.lang.String)\"}}", result);
    }

    @Test
    public void testBuiltinFunction() {
        NameTransformer transformer = (NameTransformer) getTransformer();

        String result = transformer.replaceNamesInText("number(from: \"1.000.000,01\", decimal separator:\",\", grouping separator:\".\")", new LexicalContext("decimal separator", "grouping separator"));
        assertEquals("number(from: \"1.000.000,01\", decimalSeparator:\",\", groupingSeparator:\".\")", result);

        result = transformer.replaceNamesInText("substring(string:\"abc\", starting position:2)", new LexicalContext("starting position"));
        assertEquals("substring(string:\"abc\", startingPosition:2)", result);
    }

    @Override
    protected DMNTransformer<TestCases> getTransformer() {
        return new ToSimpleNameTransformer(LOGGER);
    }

    @Override
    protected String getInputPath() {
        return "dmn/input/";
    }

    @Override
    protected  String getTargetPath() {
        return "target/simple/";
    }

    @Override
    protected  String getExpectedPath() {
        return "dmn/expected/simple/";
    }
}