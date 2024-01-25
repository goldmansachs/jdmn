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

import com.gs.dmn.ast.TDecisionRule;
import com.gs.dmn.error.NopErrorHandler;
import com.gs.dmn.log.NopBuildLogger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RuleDescriptionTransformerTest extends AbstractSignavioFileTransformerTest {
    private final RuleDescriptionVisitor visitor = new RuleDescriptionVisitor(new NopErrorHandler(), new NopBuildLogger());
    private final RuleDescriptionTransformer transformer = new RuleDescriptionTransformer(LOGGER);

    @Test
    public void testTransformForIncorrectLists() {
        TDecisionRule rule = makeRule("[ , string(\"\") ,  , string(\"\") , ]");
        visitor.cleanRuleDescription(rule);

        assertEquals("[ string(\"\") , string(\"\") ]", rule.getDescription());
    }

    @Test
    public void testTransformForIllegalString() {
        TDecisionRule rule = makeRule("[ string(-) ]");
        visitor.cleanRuleDescription(rule);

        assertEquals("[ \"\" ]", rule.getDescription());
    }

    @Test
    public void testTransformForIllegalCharacters() {
        TDecisionRule rule = makeRule("[ string(\"abc \u00A0 123\") ]");
        visitor.cleanRuleDescription(rule);

        assertEquals("[ string(\"abc   123\") ]", rule.getDescription());
    }

    private TDecisionRule makeRule(String description) {
        TDecisionRule rule = new TDecisionRule();
        rule.setDescription(description);
        return rule;
    }
}