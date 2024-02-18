/*
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TestFilesScripts {
    private static File LOCAL_TCK_FOLDER = new File("C:/Work/Projects/tck/TestCases/compliance-level-3/");

    private static File TEST_CASES_FOLDER = new File("dmn-test-cases/standard/");
    private static File TCK_11_CL2_FOLDER = new File(TEST_CASES_FOLDER, "tck/1.1/cl2/");
    private static File TCK_11_CL3_FOLDER = new File(TEST_CASES_FOLDER, "tck/1.1/cl3/");
    private static File TCK_12_CL2_FOLDER = new File(TEST_CASES_FOLDER, "tck/1.2/cl2/");
    private static File TCK_12_CL3_FOLDER = new File(TEST_CASES_FOLDER, "tck/1.2/cl3/");
    private static File TCK_13_CL2_FOLDER = new File(TEST_CASES_FOLDER, "tck/1.3/cl2/");
    private static File TCK_13_CL3_FOLDER = new File(TEST_CASES_FOLDER, "tck/1.3/cl3/");
    private static File COMPOSITE_FOLDER = new File(TEST_CASES_FOLDER, "composite/1.2");
    private static File PROTO_FOLDER = new File(TEST_CASES_FOLDER, "proto/1.1");

    private static void deleteAlienFilesFromTestFolders(boolean dryRun) throws IOException {
        deleteAlienFilesFromTestFolders(TCK_11_CL2_FOLDER, dryRun);
        deleteAlienFilesFromTestFolders(TCK_11_CL3_FOLDER, dryRun);
        deleteAlienFilesFromTestFolders(TCK_12_CL2_FOLDER, dryRun);
        deleteAlienFilesFromTestFolders(TCK_12_CL3_FOLDER, dryRun);
    }

    private static void deleteAlienFilesFromTestFolders(File rootFolder, boolean dryRun) throws IOException {
        System.out.println("Processing folder '%s'".formatted(rootFolder.getCanonicalPath()));

        // Scan test folders
        for (File testFolder: rootFolder.listFiles()) {
            String testName = testFolder.getName();
            System.out.println("Processing test '%s'".formatted(testName));

            File interpreterFolder = findChild(testFolder, "interpreter", true);
            if (interpreterFolder != null) {
                for (File child: interpreterFolder.listFiles()) {
                    String childName = child.getName();
                    if (child.isFile() && !childName.startsWith(testName)) {
                        deleteFile(child, dryRun);
                    }
                    if (child.isDirectory() && (childName.equals("double-mixed") || childName.equals("mixed") || childName.equals("standard"))) {
                        for (File innerChild: child.listFiles()) {
                            String innerChildName = innerChild.getName();
                            if (innerChild.isFile() && !innerChildName.startsWith(testName)) {
                                deleteFile(innerChild, dryRun);
                            }
                        }
                    }
                }

            }
        }
    }

    private static void deleteEmptyDialectFolders(boolean dryRun) throws IOException {
        deleteEmptyDialectFolders(TCK_11_CL2_FOLDER, dryRun);
        deleteEmptyDialectFolders(TCK_11_CL3_FOLDER, dryRun);
        deleteEmptyDialectFolders(TCK_12_CL2_FOLDER, dryRun);
        deleteEmptyDialectFolders(TCK_12_CL3_FOLDER, dryRun);
        deleteEmptyDialectFolders(COMPOSITE_FOLDER, dryRun);
        deleteEmptyDialectFolders(PROTO_FOLDER, dryRun);
    }

    private static void deleteEmptyDialectFolders(File rootFolder, boolean dryRun) throws IOException {
        System.out.println("Processing folder '%s'".formatted(rootFolder.getCanonicalPath()));

        // Scan test folders
        for (File testFolder: rootFolder.listFiles()) {
            String testName = testFolder.getName();
            System.out.println("Processing test '%s'".formatted(testName));

            File interpreterFolder = findChild(testFolder, "interpreter", true);
            File translatorFolder = findChild(testFolder, "translator", true);
            List<File> folders = new ArrayList<>();
            if (interpreterFolder != null) {
                folders.add(interpreterFolder);
            }
            if (translatorFolder != null) {
                folders.add(translatorFolder);
            }
            for (File folder: folders) {
                for (File child: folder.listFiles()) {
                    if (child.isDirectory()) {
                        String[] list = child.list();
                        if (list == null || list.length == 0) {
                            deleteFile(child, dryRun);
                        }
                    }
                }
            }
        }
    }

    private static void moveInputFilesFromTCKTranslator(boolean dryRun) throws IOException {
        moveInputFilesFromTranslator(TCK_11_CL2_FOLDER, dryRun);
        moveInputFilesFromTranslator(TCK_11_CL3_FOLDER, dryRun);
        moveInputFilesFromTranslator(TCK_12_CL2_FOLDER, dryRun);
        moveInputFilesFromTranslator(TCK_12_CL3_FOLDER, dryRun);
    }

    private static void moveInputFilesFromTranslator(File rootFolder, boolean dryRun) throws IOException {
        // Scan test folders
        System.out.println("Processing folder '%s'".formatted(rootFolder.getCanonicalPath()));

        for (File testFolder: rootFolder.listFiles()) {
            System.out.println("Processing test '%s'".formatted(testFolder.getName()));

            File translatorFolder = findChild(testFolder, "translator", true);
            if (translatorFolder != null) {
                System.out.println("Found translator '%s'".formatted(translatorFolder.getPath()));
                File inputFolder = findChild(translatorFolder, "input", true);
                if (inputFolder != null) {
                    System.out.println("Found input '%s'".formatted(inputFolder.getPath()));
                    // Move files from 'input' one level up
                    for (File testFile: inputFolder.listFiles()) {
                        moveFile(testFile, translatorFolder, dryRun);
                    }
                    // Delete input
                    deleteFile(inputFolder, dryRun);
                }
            }
        }
    }

    private static void findTestsWithBothInterpreterAndTranslator(boolean dryRun) throws IOException {
        findTestsWithBothInterpreterAndTranslator(TCK_11_CL2_FOLDER, dryRun);
        findTestsWithBothInterpreterAndTranslator(TCK_11_CL3_FOLDER, dryRun);
        findTestsWithBothInterpreterAndTranslator(TCK_12_CL2_FOLDER, dryRun);
        findTestsWithBothInterpreterAndTranslator(TCK_12_CL3_FOLDER, dryRun);
    }

    private static void findTestsWithBothInterpreterAndTranslator(File rootFolder, boolean dryRun) throws IOException {
        System.out.println("Processing folder '%s'".formatted(rootFolder.getCanonicalPath()));

        // Scan test folders
        for (File testFolder: rootFolder.listFiles()) {
            String testName = testFolder.getName();

            File interpreterFolder = findChild(testFolder, "interpreter", true);
            File translatorFolder = findChild(testFolder, "translator", true);
            if (interpreterFolder != null && translatorFolder != null) {
                System.out.println("Test '%s' has both interpreter and translator".formatted(testName));
            }
        }
    }

    private static void copyTranslatorIntoInterpreter(boolean dryRun) throws IOException {
        copyTranslatorIntoInterpreter(TCK_11_CL2_FOLDER, dryRun);
        copyTranslatorIntoInterpreter(TCK_11_CL3_FOLDER, dryRun);
        copyTranslatorIntoInterpreter(TCK_12_CL2_FOLDER, dryRun);
        copyTranslatorIntoInterpreter(TCK_12_CL3_FOLDER, dryRun);
    }

    private static void copyTranslatorIntoInterpreter(File rootFolder, boolean dryRun) throws IOException {
        // Scan test folders
        System.out.println("Processing folder '%s'".formatted(rootFolder.getCanonicalPath()));

        for (File testFolder: rootFolder.listFiles()) {
            System.out.println("Processing test '%s'".formatted(testFolder.getName()));

            File translatorFolder = findChild(testFolder, "translator", true);
            File interpreterFolder = findChild(testFolder, "interpreter", true);
            if (translatorFolder != null) {
                System.out.println("Found translator '%s'".formatted(translatorFolder.getPath()));
                File expectedFolder = findChild(translatorFolder, "expected", true);
                if (expectedFolder != null) {
                    System.out.println("Found expected '%s'".formatted(expectedFolder.getPath()));
                    // Copy expected folder
                    copyFolder(expectedFolder, interpreterFolder, dryRun);
                }
            }
        }
    }

    private static void deleteTranslatorFolder(boolean dryRun) throws IOException {
        deleteTranslatorFolder(TCK_11_CL2_FOLDER, dryRun);
        deleteTranslatorFolder(TCK_11_CL3_FOLDER, dryRun);
        deleteTranslatorFolder(TCK_12_CL2_FOLDER, dryRun);
        deleteTranslatorFolder(TCK_12_CL3_FOLDER, dryRun);
    }

    private static void deleteTranslatorFolder(File rootFolder, boolean dryRun) throws IOException {
        // Scan test folders
        System.out.println("Processing folder '%s'".formatted(rootFolder.getCanonicalPath()));

        for (File testFolder: rootFolder.listFiles()) {
            System.out.println("Processing test '%s'".formatted(testFolder.getName()));

            File translatorFolder = findChild(testFolder, "translator", true);
            if (translatorFolder != null) {
                System.out.println("Found translator '%s'".formatted(translatorFolder.getPath()));
                deleteFolder(translatorFolder, dryRun);
            }
        }
    }

    private static void renameInterpreterFolder(boolean dryRun) throws IOException {
        renameInterpreterFolder(TCK_11_CL2_FOLDER, dryRun);
        renameInterpreterFolder(TCK_11_CL3_FOLDER, dryRun);
        renameInterpreterFolder(TCK_12_CL2_FOLDER, dryRun);
        renameInterpreterFolder(TCK_12_CL3_FOLDER, dryRun);
        renameInterpreterFolder(COMPOSITE_FOLDER, dryRun);
        renameInterpreterFolder(PROTO_FOLDER, dryRun);
    }

    private static void renameInterpreterFolder(File rootFolder, boolean dryRun) throws IOException {
        // Scan test folders
        System.out.println("Processing folder '%s'".formatted(rootFolder.getCanonicalPath()));

        for (File testFolder: rootFolder.listFiles()) {
            System.out.println("Processing test '%s'".formatted(testFolder.getName()));

            File interpreterFolder = findChild(testFolder, "interpreter", true);
            if (interpreterFolder != null) {
                System.out.println("Found interpreter '%s'".formatted(interpreterFolder.getPath()));
                renameFolder(interpreterFolder, dryRun);
            }
        }
    }

    private static void copyMissingTestFilesForDialectsFromStandard(boolean dryRun) throws IOException {
        copyMissingTestFilesForDialectsFromStandard(TCK_11_CL2_FOLDER, dryRun);
        copyMissingTestFilesForDialectsFromStandard(TCK_11_CL3_FOLDER, dryRun);
        copyMissingTestFilesForDialectsFromStandard(TCK_12_CL2_FOLDER, dryRun);
        copyMissingTestFilesForDialectsFromStandard(TCK_12_CL3_FOLDER, dryRun);
        copyMissingTestFilesForDialectsFromStandard(COMPOSITE_FOLDER, dryRun);
        copyMissingTestFilesForDialectsFromStandard(PROTO_FOLDER, dryRun);
    }

    private static void copyMissingTestFilesForDialectsFromStandard(File rootFolder, boolean dryRun) throws IOException {
        System.out.println("Processing folder '%s'".formatted(rootFolder.getCanonicalPath()));

        // Scan test folders
        for (File testFolder: rootFolder.listFiles()) {
            String testName = testFolder.getName();
            System.out.println("Processing test '%s'".formatted(testName));

            File translatorFolder = findChild(testFolder, "translator", true);
            if (translatorFolder != null) {
                File standard = findChild(translatorFolder, "standard", true);
                File mixed = findChild(translatorFolder, "mixed", true);
                File doubleMixed = findChild(translatorFolder, "double-mixed", true);
                if (standard != null) {
                    addTestsIfMissing(translatorFolder, standard, mixed, "mixed", dryRun);
                    addTestsIfMissing(translatorFolder, standard, doubleMixed, "double-mixed", dryRun);
                }
            }
        }
    }

    private static void addTestsIfMissing(File translatorFolder, File standard, File dialect, String dialectName, boolean dryRun) throws IOException {
        if (standard != null && dialect == null) {
            System.out.println("Add missing test file for dialect '%s' in folder '%s'".formatted(dialectName, translatorFolder.getPath()));
            if (!dryRun) {
                dialect = new File(translatorFolder, dialectName);
                dialect.mkdir();
                FileUtils.copyDirectory(standard, dialect);
            }
        }
    }

    private static void copyTestsFromTCK(File sourceFolder, File targetFolder, boolean dryRun) throws IOException {
        List<String> testNames = Arrays.asList(
                "0068-feel-equality"
                , "0074-feel-properties"
                , "0081-feel-getentries-function"
                , "0082-feel-coercion"
                , "0087-chapter-11-example"
                , "0093-feel-at-literals"
                , "0098-feel-week-of-year-function"
        );
        List<String> dialects = Arrays.asList(
                "standard",
                "mixed",
                "double-mixed"
        );

        System.out.println("Copy DMN and TCK files for models '%s' and dialects '%s'".formatted(testNames, dialects));
        for (String testName: testNames) {
            File sourceTestFolder = new File(sourceFolder, testName);
            File targetTestFolder = new File(targetFolder, testName);
            File targetTranslatorFolder = new File(targetTestFolder, "translator");
            System.out.println("Processing test folder '%s'".formatted(sourceTestFolder.getCanonicalPath()));

            for (File sourceTestFile: sourceTestFolder.listFiles()) {
                if (isDMN(sourceTestFile) || isTCK(sourceTestFile)) {
                    File targetTestFile = new File(targetTestFolder, sourceTestFile.getName());

                    // Copy file in main folder
                    copyFile(sourceTestFile, targetTestFile, testName, dryRun);

                    if (!dialects.isEmpty()) {
                        if (isDMN(sourceTestFile)) {
                            // Copy DMN file in translator folder
                            File targetTranslatorFile = new File(targetTranslatorFolder, sourceTestFile.getName());
                            copyFile(sourceTestFile, targetTranslatorFile, testName, dryRun);
                        } else if (isTCK(sourceTestFile)) {
                            // Copy TCK file in dialect folder
                            for (String dialect: dialects) {
                                File targetDialectFile = new File(targetTranslatorFolder, "/%s/%s".formatted(dialect, sourceTestFile.getName()));
                                copyFile(sourceTestFile, targetDialectFile, testName, dryRun);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void testFilesDifferences(File tckTestCasesFolder, File jdmnTestCasesFolder) throws IOException {
        Map<String, Integer> modelDiffs = new LinkedHashMap<>();
        Map<String, Integer> testDiffs = new LinkedHashMap<>();

        List<String> testNames = new ArrayList<>();
        List<String> dialects = Arrays.asList(
                "standard",
                "mixed",
                "double-mixed"
        );

        System.out.println("Checking DMN and TCK diffs");
        for (File tckTestFolder: tckTestCasesFolder.listFiles()) {
            String testName = tckTestFolder.getName();
            File jdmnTestFolder = new File(jdmnTestCasesFolder, testName);
            File jdmnTestTranslatorFolder = new File(jdmnTestFolder, "translator");
            if (tckTestFolder.isDirectory()) {
                testNames.add(testName);

//                System.out.println(String.format("Check test '%s'", testName));
                // Compare TCK and jDMN files
                for (File tckTestFile: tckTestFolder.listFiles()) {
                    if (isDMN(tckTestFile)) {
                        // Compare main DMN and translator DMN
                        File jdmnFile = new File(jdmnTestFolder, tckTestFile.getName());
                        File jdmnTranslatorFile = new File(jdmnTestTranslatorFolder, tckTestFile.getName());
                        int diffs = compareFile(tckTestFile, Arrays.asList(jdmnFile, jdmnTranslatorFile), testName);
                        modelDiffs.put(testName, diffs);
                    } else if (isTCK(tckTestFile)) {
                        // Compare main TCK and dialect files
                        List<File> jdmnFiles = new ArrayList<>();
                        File jdmnFile = new File(jdmnTestFolder, tckTestFile.getName());
                        jdmnFiles.add(jdmnFile);
                        // Compare TCK files in dialect folders
                        for (String dialect: dialects) {
                            File jdmnDialectFile = new File(jdmnTestTranslatorFolder, "/%s/%s".formatted(dialect, tckTestFile.getName()));
                            jdmnFiles.add(jdmnDialectFile);
                        }
                        int diffs = compareFile(tckTestFile, jdmnFiles, testName);
                        testDiffs.put(testName, diffs);
                    }
                }
            }
//            System.out.println("");
        }

        Integer totalModelDiffs = modelDiffs.entrySet().stream().map(e -> e.getValue()).reduce(0, Integer::sum);
        Integer totalTestDiffs = testDiffs.values().stream().reduce(0, Integer::sum);
        System.out.println("Found %d DMN diffs and %d TCK diffs".formatted(totalModelDiffs, totalTestDiffs));
        for(String testName: testNames) {
            Integer mDiffs = modelDiffs.get(testName);
            Integer tDiffs = testDiffs.get(testName);
            if (mDiffs != 0 || tDiffs != 0) {
                System.out.println("Test '%s' model diffs = '%d' test diffs = '%d'".formatted(testName, mDiffs, tDiffs));
            }
        }
    }

    private static int compareFile(File tckTestFile, List<File> jdmnTestFiles, String testName) throws IOException {
        int diffs = 0;
        String tckContent = FileUtils.readFileToString(tckTestFile, "UTF-8");
        for (File jdmnTestFile: jdmnTestFiles) {
            if (jdmnTestFile.exists()) {
                String jdmnContent = FileUtils.readFileToString(jdmnTestFile, "UTF-8");
                if (tckContent.equals(jdmnContent)) {
//                    System.out.println(String.format("Files '%s' and '%s' are the same", tckTestFile.getName(), jdmnTestFile.getPath()));
                } else {
                    diffs++;
                    System.out.println("Files '%s' and '%s' are different".formatted(tckTestFile.getName(), displayPath(jdmnTestFile.getPath(), testName)));
                }
            }
        }
        return diffs;
    }

    private static String displayPath(String path, String testName) {
        return path.substring(path.indexOf(testName));
    }

    private static File findChild(File parentFolder, String childName, boolean isDirectory) {
        for (File child: parentFolder.listFiles()) {
            if (childName.equals(child.getName()) && child.isDirectory() == isDirectory) {
                return child;
            }
        }
        return null;
    }

    private static void moveFile(File testFile, File parentFolder, boolean dryRun) throws IOException {
        System.out.println("Move file '%s' to folder '%s'".formatted(testFile.getPath(), parentFolder.getPath()));
        if (!dryRun) {
            FileUtils.moveFile(testFile, new File(parentFolder, testFile.getName()));
        }
    }

    private static void deleteFile(File file, boolean dryRun) throws IOException {
        System.out.println("Delete file '%s'".formatted(file.getPath()));
        if (!dryRun) {
            FileUtils.forceDelete(file);
        }
    }

    private static void deleteFolder(File folder, boolean dryRun) throws IOException {
        System.out.println("Delete folder '%s'".formatted(folder.getPath()));
        if (!dryRun) {
            FileUtils.deleteDirectory(folder);
        }
    }

    private static void renameFolder(File folder, boolean dryRun) throws IOException {
        System.out.println("Rename folder '%s'".formatted(folder.getPath()));
        if (!dryRun) {
            folder.renameTo(new File(folder.getParentFile(), "/translator"));
        }
    }

    private static void copyFolder(File source, File parentFolder, boolean dryRun) throws IOException {
        System.out.println("Copy file '%s' to folder '%s'".formatted(source.getPath(), parentFolder.getPath()));
        if (!dryRun) {
            File newExpectedFolder = new File(parentFolder, source.getName());
            FileUtils.copyDirectory(source, newExpectedFolder);
        }
    }

    private static void copyFile(File sourceFile, File targetFile, String testName, boolean dryRun) throws IOException {
        System.out.println("Copy test file '%s' to '%s".formatted(displayPath(sourceFile.getPath(), testName), displayPath(targetFile.getPath(), testName)));

        if (!dryRun) {
            FileUtils.copyFile(sourceFile, targetFile);
        }
    }

    private static boolean isDMN(File file) {
        String name = file.getName();
        return name.endsWith(".dmn");
    }

    private static boolean isTCK(File file) {
        String name = file.getName();
        return name.endsWith(".xml");
    }

    public static void main(String[] args) throws IOException {
        boolean dryRun = true;

        testFilesDifferences(LOCAL_TCK_FOLDER, TCK_13_CL3_FOLDER);
        System.out.println();
        copyTestsFromTCK(LOCAL_TCK_FOLDER, TCK_13_CL3_FOLDER, dryRun);
        System.out.println();
        testFilesDifferences(LOCAL_TCK_FOLDER, TCK_13_CL3_FOLDER);
    }
}
