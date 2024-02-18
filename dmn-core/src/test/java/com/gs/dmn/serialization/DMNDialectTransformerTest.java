package com.gs.dmn.serialization;

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.serialization.xstream.DMNMarshallerFactory;
import com.gs.dmn.transformation.AbstractFileTransformerTest;

import java.io.File;

public abstract class DMNDialectTransformerTest extends AbstractFileTransformerTest {
    protected final DMNMarshaller dmnMarshaller = getDMNMarshaller();

    protected void doTest(String inputFileName) throws Exception {
        // Read
        File inputFile = new File(resource(getInputPath() + inputFileName));
        TDefinitions sourceDefinitions = dmnMarshaller.unmarshal(inputFile, true);

        // Transform
        TDefinitions targetDefinitions = transform(sourceDefinitions);

        // Write
        File actualOutputFile = getActualOutputFile(inputFileName);
        dmnMarshaller.marshal(targetDefinitions, actualOutputFile);

        // Compare
        File expectedOutputFile = getExpectedOutputFile(inputFileName);
        compareFile(expectedOutputFile, actualOutputFile);
    }

    protected File getActualOutputFile(String inputFileName) {
        File targetFolder = new File(getTargetPath());
        targetFolder.mkdirs();
        return new File(targetFolder, inputFileName);
    }

    protected DMNMarshaller getDMNMarshaller() {
        return DMNMarshallerFactory.newDefaultMarshaller();
    }

    protected File getExpectedOutputFile(String inputFileName) {
        return new File(resource(getExpectedPath() + inputFileName));
    }

    protected TDefinitions transform(TDefinitions sourceDefinitions) {
        return getTransformer().transformDefinitions(sourceDefinitions);
    }

    protected String getInputPath() {
        return "dmn/input/%s/".formatted(getSourceVersion());
    }

    protected String getTargetPath() {
        return "target/version/%s/%s/".formatted(getSourceVersion(), getTargetVersion());
    }

    protected String getExpectedPath() {
        return "dmn/expected/%s/%s/".formatted(getSourceVersion(), getTargetVersion());
    }

    protected abstract SimpleDMNDialectTransformer getTransformer();

    protected abstract String getSourceVersion();

    protected abstract String getTargetVersion();
}