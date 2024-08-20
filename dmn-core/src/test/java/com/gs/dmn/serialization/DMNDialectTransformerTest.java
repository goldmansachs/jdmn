package com.gs.dmn.serialization;

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.serialization.xstream.DMNMarshallerFactory;
import com.gs.dmn.transformation.AbstractFileTransformerTest;

import java.io.*;
import java.nio.charset.Charset;

public abstract class DMNDialectTransformerTest extends AbstractFileTransformerTest {
    protected final DMNMarshaller dmnMarshaller = getDMNMarshaller();

    protected void doTest(String inputFileName) throws Exception {
        Charset charset = this.inputParameters.getCharset();

        // Read
        File inputFile = new File(resource(getInputPath() + inputFileName));
        TDefinitions sourceDefinitions;
        try (FileInputStream fis = new FileInputStream(inputFile); InputStreamReader isr = new InputStreamReader(fis, charset)) {
            sourceDefinitions = dmnMarshaller.unmarshal(isr, true);
        }

        // Transform
        TDefinitions targetDefinitions = transform(sourceDefinitions);

        // Write
        File actualOutputFile = getActualOutputFile(inputFileName);
        try (FileOutputStream fos = new FileOutputStream(actualOutputFile); OutputStreamWriter osw = new OutputStreamWriter(fos, charset)) {
            dmnMarshaller.marshal(targetDefinitions, osw);
        }

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
        return String.format("dmn/input/%s/", getSourceVersion());
    }

    protected String getTargetPath() {
        return String.format("target/version/%s/%s/", getSourceVersion(), getTargetVersion());
    }

    protected String getExpectedPath() {
        return String.format("dmn/expected/%s/%s/", getSourceVersion(), getTargetVersion());
    }

    protected abstract SimpleDMNDialectTransformer getTransformer();

    protected abstract String getSourceVersion();

    protected abstract String getTargetVersion();
}