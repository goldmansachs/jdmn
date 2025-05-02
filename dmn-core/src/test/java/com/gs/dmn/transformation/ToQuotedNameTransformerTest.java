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
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.tck.ast.TestCases;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;

import static com.gs.dmn.runtime.Assert.assertEquals;
import static com.gs.dmn.serialization.DMNConstants.XSI_NS;
import static com.gs.dmn.serialization.DMNConstants.XSI_PREFIX;

public class ToQuotedNameTransformerTest extends SimpleDMNTransformerTest {
    @Test
    public void testTransform() throws Exception {
        doTest("1.1", Collections.singletonList("0004-lending.dmn"),
                "0004-lending-test-01.xml", new LinkedHashMap<String, Pair<String, String>>() {{
                    put("0004-lending.dmn", new Pair<>("http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b", "tns"));
                    put("0004-lending-test-01.xml", new Pair<>(XSI_NS, XSI_PREFIX));
                }}
        );
        doTest("1.1", Collections.singletonList("0007-date-time.dmn"),
                "0007-date-time-test-01.xml", new LinkedHashMap<String, Pair<String, String>>() {{
                    put("0007-date-time.dmn", new Pair<>("http://www.trisotech.com/definitions/_69430b3e-17b8-430d-b760-c505bf6469f9", "tns"));
                    put("0007-date-time-test-01.xml",  new Pair<>(XSI_NS, XSI_PREFIX));
                }}
        );
        doTest("1.1", Collections.singletonList("0034-drg-scopes.dmn"),
                "0034-drg-scopes-test-01.xml", new LinkedHashMap<String, Pair<String, String>>() {{
                    put("0034-drg-scopes.dmn", new Pair<>("http://www.actico.com/spec/DMN/0.1.0/0034-drg-scopes", "tns"));
                    put("0034-drg-scopes-test-01.xml", new Pair<>(XSI_NS, XSI_PREFIX));
                }}
        );
        doTest("1.2", Collections.singletonList("0087-chapter-11-example.dmn"),
                "0087-chapter-11-example-test-01.xml", new LinkedHashMap<String, Pair<String, String>>() {{
                    put("0087-chapter-11-example.dmn", new Pair<>("http://www.trisotech.com/definitions/_9d01a0c4-f529-4ad8-ad8e-ec5fb5d96ad4", "tns"));
                    put("0087-chapter-11-example-test-01.xml", new Pair<>(XSI_NS, XSI_PREFIX));
                }}
        );
        doTest("1.2", Arrays.asList("0089-nested-inputdata-imports.dmn", "0089-model-a.dmn", "0089-model-b.dmn", "0089-model-b2.dmn"),
                "0089-nested-inputdata-imports-test-01.xml", new LinkedHashMap<String, Pair<String, String>>() {{
                    put("0089-nested-inputdata-imports.dmn", new Pair<>("http://www.trisotech.com/definitions/_10435dcd-8774-4575-a338-49dd554a0928", null));
                    put("0089-model-a.dmn", new Pair<>("http://www.trisotech.com/definitions/_ae5b3c17-1ac3-4e1d-b4f9-2cf861aec6d9", null));
                    put("0089-model-b.dmn", new Pair<>("http://www.trisotech.com/definitions/_2a1d771a-a899-4fef-abd6-fc894332337c", null));
                    put("0089-model-b2.dmn", new Pair<>("http://www.trisotech.com/definitions/_9d46ece4-a96c-4cb0-abc0-0ca121ac3768", null));
                    put("0089-nested-inputdata-imports-test-01.xml", new Pair<>(XSI_NS, XSI_PREFIX));
                }}
        );
    }

    @Test
    public void testTransformEmpty() {
        ToQuotedNameTransformer transformer = new ToQuotedNameTransformer();

        DMNModelRepository repository = new DMNModelRepository();
        ArrayList<TestCases> testCasesList = new ArrayList<>();
        assertEquals(repository, transformer.transform(repository));
        assertEquals(new Pair<>(repository, testCasesList), transformer.transform(repository, testCasesList));
    }

    @Test
    public void testTransformName() {
        ToQuotedNameTransformer transformer = (ToQuotedNameTransformer) getTransformer();

        // Transform first name
        String firstName = transformer.transformName("abc ? x");
        assertEquals("'abc ? x'", firstName);

        // Transform second name
        String secondName = transformer.transformName("abc?x");
        assertEquals("'abc?x'", secondName);

        // Transform names with unicode
        String result = transformer.transformName("a \uD83D\uDC0E bc");
        assertEquals("'a \uD83D\uDC0E bc'", result);
    }

    @Test
    public void testContextKeys() {
        NameTransformer transformer = (NameTransformer) getTransformer();

        //
        // Test name in key
        //
        String result = transformer.replaceNamesInText("{foo bar: \"foo\"}", new LexicalContext());
        assertEquals("{'foo bar': \"foo\"}", result);

        result = transformer.replaceNamesInText("{foo+bar: \"foo\"}", new LexicalContext());
        assertEquals("{'foo+bar': \"foo\"}", result);

        result = transformer.replaceNamesInText("{\uD83D\uDC0E: \"bar\"}", new LexicalContext());
        assertEquals("{'\uD83D\uDC0E': \"bar\"}", result);

        //
        // Test qualified name in key
        //
        result = transformer.replaceNamesInText("{'foo bar': \"foo\"}", new LexicalContext());
        assertEquals("{'foo bar': \"foo\"}", result);

        //
        // Test string in key
        //
        result = transformer.replaceNamesInText("{\"\": \"foo\"}", new LexicalContext());
        assertEquals("{\"\": \"foo\"}", result);

        result = transformer.replaceNamesInText("{\"foo+bar((!!],foo\": \"foo\"}", new LexicalContext());
        assertEquals("{\"'foo+bar((!!],foo'\": \"foo\"}", result);

        //
        // Test complex context
        //
        result = transformer.replaceNamesInText("{a: 1 + 2, b: a + 3}", new LexicalContext());
        assertEquals("{a: 1 + 2, b: a + 3}", result);

        result = transformer.replaceNamesInText("{name 1: 1 + 2, name 2: 3, c: name 1 + name 2}", new LexicalContext("name 1", "name 2"));
        assertEquals("{'name 1': 1 + 2, 'name 2': 3, c: 'name 1' + 'name 2'}", result);

        result = transformer.replaceNamesInText("{name 1: 1 + 2, name 2: 3, c: {d e: name 1 + name 2}}", new LexicalContext("name 1", "name 2"));
        assertEquals("{'name 1': 1 + 2, 'name 2': 3, c: {'d e': 'name 1' + 'name 2'}}", result);

//        Not supported yet
//        result = transformer.replaceNamesInText("{a: 1 + 2, b: 3, c: {d e: 'a b' + b}}", new LexicalContext());
//        assertEquals("{a: 1 + 2, b: 3, c: {'d e': 'a b' + b}}", result);

        //
        // Test mapping to external functions
        //
        String text = "function(s1, s2) external {java:{class:\"java.lang.Math\", method signature:\"max(java.lang.String, java.lang.String)\"}}";
        result = transformer.replaceNamesInText(text, new LexicalContext("mathMaxString"));
        assertEquals("function(s1, s2) external {java:{class:\"java.lang.Math\", 'method signature':\"max(java.lang.String, java.lang.String)\"}}", result);

        text = "function(s1, s2) external {java:{class:\"java.lang.Math\",'method signature':\"max(java.lang.String, java.lang.String)\"}}";
        result = transformer.replaceNamesInText(text, new LexicalContext());
        assertEquals("function(s1, s2) external {java:{class:\"java.lang.Math\",'method signature':\"max(java.lang.String, java.lang.String)\"}}", result);
    }

    @Test
    public void testBuiltinFunction() {
        NameTransformer transformer = (NameTransformer) getTransformer();

        String result = transformer.replaceNamesInText("number(from: \"1.000.000,01\", decimal separator:\",\", grouping separator:\".\")", new LexicalContext("decimal separator", "grouping separator"));
        assertEquals("number(from: \"1.000.000,01\", 'decimal separator':\",\", 'grouping separator':\".\")", result);

        result = transformer.replaceNamesInText("substring(string:\"abc\", starting position:2)", new LexicalContext("starting position"));
        assertEquals("substring(string:\"abc\", 'starting position':2)", result);

        result = transformer.replaceNamesInText("number(from: \"1.000.000,01\", 'decimal separator':\",\", 'grouping separator':\".\")", new LexicalContext("decimal separator", "grouping separator"));
        assertEquals("number(from: \"1.000.000,01\", 'decimal separator':\",\", 'grouping separator':\".\")", result);
    }

    @Test
    public void testNamesFromContext() {
        NameTransformer transformer = (NameTransformer) getTransformer();

        String result = transformer.replaceNamesInText("name 1 + name 12", new LexicalContext("name 1", "name 12"));
        assertEquals("'name 1' + 'name 12'", result);

        result = transformer.replaceNamesInText("Applicant data.Monthly.Expenses", new LexicalContext("Applicant data"));
        assertEquals("'Applicant data'.Monthly.Expenses", result);

        result = transformer.replaceNamesInText("'Applicant data'.Monthly.Expenses", new LexicalContext("Applicant data"));
        assertEquals("'Applicant data'.Monthly.Expenses", result);

        result = transformer.replaceNamesInText("ApplicantData.Monthly.Expenses", new LexicalContext("ApplicantData"));
        assertEquals("ApplicantData.Monthly.Expenses", result);

        result = transformer.replaceNamesInText("f in factors return is factor", new LexicalContext("factors", "is factor"));
        assertEquals("f in factors return 'is factor'", result);

        result = transformer.replaceNamesInText("f in factors return 'is factor'", new LexicalContext("factors", "is factor"));
        assertEquals("f in factors return 'is factor'", result);

        result = transformer.replaceNamesInText("'name 1' + 'name 12'", new LexicalContext("name 1", "name 12"));
        assertEquals("'name 1' + 'name 12'", result);

        result = transformer.replaceNamesInText("{a : name 1, b: name 12}", new LexicalContext("name 1", "name 12"));
        assertEquals("{a : 'name 1', b: 'name 12'}", result);

        result = transformer.replaceNamesInText("1 + /* 1 + */ 1", new LexicalContext("name 1", "name 12"));
        assertEquals("1 + /* 1 + */ 1", result);
    }

    @Override
    protected DMNTransformer<TestCases> getTransformer() {
        return new ToQuotedNameTransformer();
    }

    @Override
    protected String getInputPath() {
        return "dmn/input/";
    }

    @Override
    protected  String getTargetPath() {
        return "target/quoted/";
    }

    @Override
    protected  String getExpectedPath() {
        return "dmn/expected/quoted/";
    }
}