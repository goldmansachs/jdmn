package com.gs.dmn.serialization;

import com.gs.dmn.runtime.Pair;
import org.junit.Test;
import org.omg.spec.dmn._20191111.model.TDefinitions;

public class DMN12To13DialectTransformerTest extends DMNDialectTransformerTest<org.omg.spec.dmn._20180521.model.TDefinitions, TDefinitions> {
    @Test
    public void testTransform() throws Exception {
        doTest("0004-lending.dmn", new Pair<>("http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b", "tns"));
        doTest("0014-loan-comparison.dmn", new Pair<>("http://www.trisotech.com/definitions/_69430b3e-17b8-430d-b760-c505bf6469f9", "tns"));
        doTest("0087-chapter-11-example.dmn", new Pair<>("http://www.trisotech.com/definitions/_9d01a0c4-f529-4ad8-ad8e-ec5fb5d96ad4", "tns"));
    }

    @Override
    protected SimpleDMNDialectTransformer<org.omg.spec.dmn._20180521.model.TDefinitions, TDefinitions> getTransformer() {
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

    @Override
    protected DMNVersion getDMNTargetVersion() {
        return DMNVersion.DMN_13;
    }
}