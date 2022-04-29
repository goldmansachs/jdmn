package com.gs.dmn.serialization;

import org.junit.Test;

public class DMN12To13DialectTransformerTest extends DMNDialectTransformerTest {
    @Test
    public void testTransform() throws Exception {
        doTest("0004-lending.dmn");
        doTest("0014-loan-comparison.dmn");
        doTest("0087-chapter-11-example.dmn");
    }

    @Override
    protected SimpleDMNDialectTransformer getTransformer() {
        return new DMN12To13DialectTransformer(LOGGER);
    }

    @Override
    protected String getSourceVersion() {
        return "1.2";
    }

    @Override
    protected String getTargetVersion() {
        return "1.3";
    }
}