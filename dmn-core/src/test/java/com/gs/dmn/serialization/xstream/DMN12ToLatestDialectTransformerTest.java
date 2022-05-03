package com.gs.dmn.serialization.xstream;

import com.gs.dmn.serialization.DMNDialectTransformerTest;
import com.gs.dmn.serialization.SimpleDMNDialectTransformer;
import org.junit.Test;

public class DMN12ToLatestDialectTransformerTest extends DMNDialectTransformerTest {
    @Test
    public void testTransform() throws Exception {
        doTest("0004-lending.dmn");
        doTest("0014-loan-comparison.dmn");
        doTest("0087-chapter-11-example.dmn");
    }

    @Override
    protected SimpleDMNDialectTransformer getTransformer() {
        return new DMN12ToLatestDialectTransformer(LOGGER);
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