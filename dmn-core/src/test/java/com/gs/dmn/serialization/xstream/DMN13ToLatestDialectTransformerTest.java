package com.gs.dmn.serialization.xstream;

import com.gs.dmn.serialization.DMNDialectTransformerTest;
import com.gs.dmn.serialization.SimpleDMNDialectTransformer;
import org.junit.jupiter.api.Test;

public class DMN13ToLatestDialectTransformerTest extends DMNDialectTransformerTest {
    @Test
    public void testTransform() throws Exception {
        doTest("0004-lending.dmn");
        doTest("0014-loan-comparison.dmn");
        doTest("0087-chapter-11-example.dmn");
    }

    @Override
    protected SimpleDMNDialectTransformer getTransformer() {
        return new DMN13ToLatestDialectTransformer(LOGGER);
    }

    @Override
    protected String getSourceVersion() {
        return "1.3";
    }

    @Override
    protected String getTargetVersion() {
        return "1.4";
    }
}