package com.gs.dmn.serialization;

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.runtime.Pair;
import org.junit.Test;

import java.io.File;

public class DMN13ToASTTransformerTest extends DMNDialectTransformerTest<org.omg.spec.dmn._20191111.model.TDefinitions, TDefinitions<DMNContext>> {
    @Test
    public void testTransform() throws Exception {
        doTest("0004-lending.dmn", new Pair<>("http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b", "tns"));
        doTest("0014-loan-comparison.dmn", new Pair<>("http://www.trisotech.com/definitions/_69430b3e-17b8-430d-b760-c505bf6469f9", "tns"));
        doTest("0087-chapter-11-example.dmn", new Pair<>("http://www.trisotech.com/definitions/_9d01a0c4-f529-4ad8-ad8e-ec5fb5d96ad4", "tns"));
    }

    @Override
    protected SimpleDMNDialectTransformer<org.omg.spec.dmn._20191111.model.TDefinitions, TDefinitions<DMNContext>> getTransformer() {
        return new DMN13ToASTTransformer<>(LOGGER);
    }

    @Override
    protected String getSourceVersion() {
        return "1.3";
    }

    @Override
    protected String getTargetVersion() {
        return "ast";
    }

    @Override
    protected DMNVersion getDMNTargetVersion() {
        return DMNVersion.DMN_13;
    }

    @Override
    protected File getActualOutputFile(String inputFileName) {
        File targetFolder = new File(getTargetPath());
        targetFolder.mkdirs();
        File actualOutputFile = new File(targetFolder, getOutputFileName(inputFileName));
        return actualOutputFile;
    }

    @Override
    protected void writeModel(TDefinitions<DMNContext> targetDefinitions, Pair<String, String> dmnNamespacePrefixMapping, File actualOutputFile) {
        dmnWriter.writeASTAsJson(targetDefinitions, actualOutputFile, new DMNNamespacePrefixMapper(dmnNamespacePrefixMapping.getLeft(), dmnNamespacePrefixMapping.getRight(), getDMNTargetVersion()));
    }

    @Override
    protected File getExpectedOutputFile(String inputFileName) {
        return new File(resource(getExpectedPath() + getOutputFileName(inputFileName)));
    }

    private String getOutputFileName(String inputFileName) {
        return inputFileName.replace(".dmn", ".json");
    }
}