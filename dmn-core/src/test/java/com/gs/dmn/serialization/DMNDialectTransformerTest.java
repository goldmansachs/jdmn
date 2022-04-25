package com.gs.dmn.serialization;

import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.AbstractFileTransformerTest;

import java.io.File;

public abstract class DMNDialectTransformerTest<S, T> extends AbstractFileTransformerTest {
    protected final DMNReader dmnReader = getDMNReader();
    protected final DMNWriter dmnWriter = getDMNWriter();

    protected void doTest(String inputFileName, Pair<String, String> dmnNamespacePrefixMapping) throws Exception {
        // Read
        File inputFile = new File(resource(getInputPath() + inputFileName));
        Object object = readModel(inputFile);
        S sourceDefinitions = (S) object;

        // Transform
        T targetDefinitions = transform(sourceDefinitions);

        // Write
        File actualOutputFile = getActualOutputFile(inputFileName);
        writeModel(targetDefinitions, dmnNamespacePrefixMapping, actualOutputFile);

        // Compare
        File expectedOutputFile = getExpectedOutputFile(inputFileName);
        compareFile(expectedOutputFile, actualOutputFile);
    }

    protected File getActualOutputFile(String inputFileName) {
        File targetFolder = new File(getTargetPath());
        targetFolder.mkdirs();
        return new File(targetFolder, inputFileName);
    }

    protected DMNReader getDMNReader() {
        return new DMNReader(LOGGER, false);
    }

    protected Object readModel(File inputFile) throws Exception {
        return dmnReader.readObject(inputFile);
    }

    protected DMNWriter getDMNWriter() {
        return new DMNWriter(LOGGER);
    }

    protected void writeModel(T targetDefinitions, Pair<String, String> dmnNamespacePrefixMapping, File actualOutputFile) {
        dmnWriter.write(targetDefinitions, actualOutputFile, new DMNNamespacePrefixMapper(dmnNamespacePrefixMapping.getLeft(), dmnNamespacePrefixMapping.getRight(), getDMNTargetVersion()));
    }

    protected File getExpectedOutputFile(String inputFileName) {
        return new File(resource(getExpectedPath() + inputFileName));
    }

    protected abstract DMNVersion getDMNTargetVersion();

    protected T transform(S sourceDefinitions) {
        Pair<T, PrefixNamespaceMappings> pair = getTransformer().transformDefinitions(sourceDefinitions);
        return pair.getLeft();
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

    protected abstract SimpleDMNDialectTransformer<S, T> getTransformer();

    protected abstract String getSourceVersion();

    protected abstract String getTargetVersion();
}