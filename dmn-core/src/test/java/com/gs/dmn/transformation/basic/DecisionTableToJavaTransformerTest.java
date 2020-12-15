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
package com.gs.dmn.transformation.basic;

import com.gs.dmn.AbstractTest;
import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.junit.Test;
import org.omg.spec.dmn._20191111.model.TDecisionRule;

import static org.junit.Assert.assertEquals;

public class DecisionTableToJavaTransformerTest extends AbstractTest {
    private final DMNDialectDefinition<?, ?, ?, ?, ?, ?> dialect = new StandardDMNDialectDefinition();
    private final DMNModelRepository repository = new DMNModelRepository();
    private final InputParameters inputParameters = makeInputParameters();
    private final DMNExpressionToNativeTransformer transformer = new DMNExpressionToNativeTransformer(new BasicDMNToJavaTransformer(dialect, repository, null, null, new NopLazyEvaluationDetector(), inputParameters));

    @Test
    public void testAnnotationEscapedText() {
        assertEquals("", transformer.annotationEscapedText(makeDecisionRule(null)));
        assertEquals("", transformer.annotationEscapedText(makeDecisionRule("")));
        assertEquals("string(-)", transformer.annotationEscapedText(makeDecisionRule("string(-)")));
        assertEquals("[string(-) , string(-) , string(-) , string(-)]", transformer.annotationEscapedText(makeDecisionRule("[string(-) , string(-) , string(-) , string(-)]")));
        assertEquals("string(\\\"abc\\\")", transformer.annotationEscapedText(makeDecisionRule("string(\"abc\")")));
        assertEquals("[string(-) , string(\\\"DEFAULT_VALUE_FIELD_35 |\\\") , string(\\\"Field 35 is not required, because Product Classification CFI Code does not indicate \\\"D\\\"ebt instrument |\\\") , string(\\\"BR147462 |\\\")]", transformer.annotationEscapedText(
                makeDecisionRule("[string(-) , string(\"DEFAULT_VALUE_FIELD_35 |\") , string(\"Field 35 is not required, because Product Classification CFI Code does not indicate \\\"D\\\"ebt instrument |\") , string(\"BR147462 |\")]")));
    }

    public TDecisionRule makeDecisionRule(String description) {
        TDecisionRule rule = new TDecisionRule();
        rule.setDescription(description);
        return rule;
    }
}