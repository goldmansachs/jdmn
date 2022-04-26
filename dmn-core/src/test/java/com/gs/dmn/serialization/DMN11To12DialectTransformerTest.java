package com.gs.dmn.serialization;

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.runtime.Pair;
import org.junit.Test;

import java.io.File;

public class DMN11To12DialectTransformerTest extends DMNDialectTransformerTest<TDefinitions, TDefinitions> {
    @Test
    public void testTransform() throws Exception {
        doTest("0004-lending.dmn", new Pair<>("http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b", "tns"));
        doTest("0014-loan-comparison.dmn", new Pair<>("http://www.trisotech.com/definitions/_69430b3e-17b8-430d-b760-c505bf6469f9", "tns"));
    }

    @Override
    protected SimpleDMNDialectTransformer<TDefinitions, TDefinitions> getTransformer() {
        return new DMN11To12DialectTransformer(LOGGER);
    }

    @Override
    protected Object readModel(File inputFile) {
        return this.dmnReader.readAST(inputFile);
    }

    @Override
    protected void writeModel(TDefinitions targetDefinitions, Pair<String, String> dmnNamespacePrefixMapping, File actualOutputFile) {
        this.dmnWriter.writeAST(targetDefinitions, actualOutputFile);
    }

    @Override
    protected String getSourceVersion() {
        return "1.1";
    }

    @Override
    protected String getTargetVersion() {
        return "1.2";
    }

    @Override
    protected DMNVersion getDMNTargetVersion() {
        return DMNVersion.DMN_12;
    }
}