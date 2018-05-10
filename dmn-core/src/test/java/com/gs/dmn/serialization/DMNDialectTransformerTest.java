package com.gs.dmn.serialization;

import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.AbstractFileTransformerTest;
import org.junit.Test;

import java.io.File;

public class DMNDialectTransformerTest extends AbstractFileTransformerTest {
    private final DMNDialectTransformer transformer = new DMNDialectTransformer(LOGGER);
    private final DMNReader dmnReader = new DMNReader(LOGGER, false);
    private final DMNWriter dmnWriter = new DMNWriter(LOGGER);

    @Test
    public void testTransform() throws Exception {
        doTest("0004-lending.dmn", new Pair<>("http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b", "tns"));
        doTest("0014-loan-comparison.dmn", new Pair<>("http://www.trisotech.com/definitions/_69430b3e-17b8-430d-b760-c505bf6469f9", "tns"));
    }

    protected void doTest(String inputFileName, Pair<String, String> dmnNamespacePrefixMapping) throws Exception {
        // Read
        File inputFile = new File(resource(getInputPath() + inputFileName));
        Object object = dmnReader.readObject(inputFile);
        org.omg.spec.dmn._20151101.model.TDefinitions dmn11Definitions = (org.omg.spec.dmn._20151101.model.TDefinitions) object;

        // Transform
        org.omg.spec.dmn._20180521.model.TDefinitions dmn12Definitions = transformer.transformRepository(dmn11Definitions).getDefinitions();

        // Write
        File targetFolder = new File(getTargetPath());
        targetFolder.mkdirs();
        File actualOutputFile = new File(targetFolder, inputFileName);
        dmnWriter.write(dmn12Definitions, actualOutputFile, new DMNNamespacePrefixMapper(dmnNamespacePrefixMapping.getLeft(), dmnNamespacePrefixMapping.getRight(), DMNVersion.DMN_12));

        // Compare
        File expectedOutputFile = new File(resource(getExpectedPath() + inputFileName));
        compareFile(expectedOutputFile, actualOutputFile);
    }

    private String getInputPath() {
        return "dmn/input/";
    }

    private String getTargetPath() {
        return "target/version/" + getInputPath();
    }

    private String getExpectedPath() {
        return "dmn/expected/1.2/";
    }

}