package com.gs.dmn.serialization;

import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.AbstractFileTransformerTest;

import java.io.File;

public abstract class DMNDialectTransformerTest<S, T> extends AbstractFileTransformerTest {
    protected final DMNReader dmnReader = new DMNReader(LOGGER, false);
    protected final DMNWriter dmnWriter = new DMNWriter(LOGGER);

    protected void doTest(String inputFileName, Pair<String, String> dmnNamespacePrefixMapping) throws Exception {
        // Read
        File inputFile = new File(resource(getInputPath() + inputFileName));
        Object object = dmnReader.readObject(inputFile);
        S sourceDefinitions = (S) object;

        // Transform
        T targetDefinitions = transform(sourceDefinitions);

        // Write
        File targetFolder = new File(getTargetPath());
        targetFolder.mkdirs();
        File actualOutputFile = new File(targetFolder, inputFileName);
        dmnWriter.write(targetDefinitions, actualOutputFile, new DMNNamespacePrefixMapper(dmnNamespacePrefixMapping.getLeft(), dmnNamespacePrefixMapping.getRight(), getDMNTargetVersion()));

        // Compare
        File expectedOutputFile = new File(resource(getExpectedPath() + inputFileName));
        compareFile(expectedOutputFile, actualOutputFile);
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