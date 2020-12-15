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
import java.util.ArrayList;
import java.util.List;

public class TestFilesScripts {
    private static File TEST_CASES_FOLDER = new File("dmn-test-cases/standard/");
    private static File TCK_11_CL2_FOLDER = new File(TEST_CASES_FOLDER, "tck/1.1/cl2/");
    private static File TCK_11_CL3_FOLDER = new File(TEST_CASES_FOLDER, "tck/1.1/cl3/");
    private static File TCK_12_CL2_FOLDER = new File(TEST_CASES_FOLDER, "tck/1.2/cl2/");
    private static File TCK_12_CL3_FOLDER = new File(TEST_CASES_FOLDER, "tck/1.2/cl3/");
    private static File COMPOSITE_FOLDER = new File(TEST_CASES_FOLDER, "composite/1.2");
    private static File PROTO_FOLDER = new File(TEST_CASES_FOLDER, "proto/1.1");

    private static void deleteAlienFilesFromTestFolders(boolean dryRun) throws IOException {
        deleteAlienFilesFromTestFolders(TCK_11_CL2_FOLDER, dryRun);
        deleteAlienFilesFromTestFolders(TCK_11_CL3_FOLDER, dryRun);
        deleteAlienFilesFromTestFolders(TCK_12_CL2_FOLDER, dryRun);
        deleteAlienFilesFromTestFolders(TCK_12_CL3_FOLDER, dryRun);
    }

    private static void deleteAlienFilesFromTestFolders(File rootFolder, boolean dryRun) throws IOException {
        System.out.println(String.format("Processing folder '%s'", rootFolder.getCanonicalPath()));

        // Scan test folders
        for (File testFolder: rootFolder.listFiles()) {
            String testName = testFolder.getName();
            System.out.println(String.format("Processing test '%s'", testName));

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
        System.out.println(String.format("Processing folder '%s'", rootFolder.getCanonicalPath()));

        // Scan test folders
        for (File testFolder: rootFolder.listFiles()) {
            String testName = testFolder.getName();
            System.out.println(String.format("Processing test '%s'", testName));

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
        System.out.println(String.format("Processing folder '%s'", rootFolder.getCanonicalPath()));

        for (File testFolder: rootFolder.listFiles()) {
            System.out.println(String.format("Processing test '%s'", testFolder.getName()));

            File translatorFolder = findChild(testFolder, "translator", true);
            if (translatorFolder != null) {
                System.out.println(String.format("Found translator '%s'", translatorFolder.getPath()));
                File inputFolder = findChild(translatorFolder, "input", true);
                if (inputFolder != null) {
                    System.out.println(String.format("Found input '%s'", inputFolder.getPath()));
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
        System.out.println(String.format("Processing folder '%s'", rootFolder.getCanonicalPath()));

        // Scan test folders
        for (File testFolder: rootFolder.listFiles()) {
            String testName = testFolder.getName();

            File interpreterFolder = findChild(testFolder, "interpreter", true);
            File translatorFolder = findChild(testFolder, "translator", true);
            if (interpreterFolder != null && translatorFolder != null) {
                System.out.println(String.format("Test '%s' has both interpreter and translator", testName));
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
        System.out.println(String.format("Processing folder '%s'", rootFolder.getCanonicalPath()));

        for (File testFolder: rootFolder.listFiles()) {
            System.out.println(String.format("Processing test '%s'", testFolder.getName()));

            File translatorFolder = findChild(testFolder, "translator", true);
            File interpreterFolder = findChild(testFolder, "interpreter", true);
            if (translatorFolder != null) {
                System.out.println(String.format("Found translator '%s'", translatorFolder.getPath()));
                File expectedFolder = findChild(translatorFolder, "expected", true);
                if (expectedFolder != null) {
                    System.out.println(String.format("Found expected '%s'", expectedFolder.getPath()));
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
        System.out.println(String.format("Processing folder '%s'", rootFolder.getCanonicalPath()));

        for (File testFolder: rootFolder.listFiles()) {
            System.out.println(String.format("Processing test '%s'", testFolder.getName()));

            File translatorFolder = findChild(testFolder, "translator", true);
            if (translatorFolder != null) {
                System.out.println(String.format("Found translator '%s'", translatorFolder.getPath()));
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
        System.out.println(String.format("Processing folder '%s'", rootFolder.getCanonicalPath()));

        for (File testFolder: rootFolder.listFiles()) {
            System.out.println(String.format("Processing test '%s'", testFolder.getName()));

            File interpreterFolder = findChild(testFolder, "interpreter", true);
            if (interpreterFolder != null) {
                System.out.println(String.format("Found interpreter '%s'", interpreterFolder.getPath()));
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
        System.out.println(String.format("Processing folder '%s'", rootFolder.getCanonicalPath()));

        // Scan test folders
        for (File testFolder: rootFolder.listFiles()) {
            String testName = testFolder.getName();
            System.out.println(String.format("Processing test '%s'", testName));

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
            System.out.println(String.format("Add missing test file for dialect '%s' in folder '%s'", dialectName, translatorFolder.getPath()));
            if (!dryRun) {
                dialect = new File(translatorFolder, dialectName);
                dialect.mkdir();
                FileUtils.copyDirectory(standard, dialect);
            }
        }
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
        System.out.println(String.format("Move file '%s' to folder '%s'", testFile.getPath(), parentFolder.getPath()));
        if (!dryRun) {
            FileUtils.moveFile(testFile, new File(parentFolder, testFile.getName()));
        }
    }

    private static void deleteFile(File file, boolean dryRun) throws IOException {
        System.out.println(String.format("Delete file '%s'", file.getPath()));
        if (!dryRun) {
            FileUtils.forceDelete(file);
        }
    }

    private static void deleteFolder(File folder, boolean dryRun) throws IOException {
        System.out.println(String.format("Delete folder '%s'", folder.getPath()));
        if (!dryRun) {
            FileUtils.deleteDirectory(folder);
        }
    }

    private static void renameFolder(File folder, boolean dryRun) throws IOException {
        System.out.println(String.format("Rename folder '%s'", folder.getPath()));
        if (!dryRun) {
            folder.renameTo(new File(folder.getParentFile(), "/translator"));
        }
    }

    private static void copyFolder(File source, File parentFolder, boolean dryRun) throws IOException {
        System.out.println(String.format("Copy file '%s' to folder '%s'", source.getPath(), parentFolder.getPath()));
        if (!dryRun) {
            File newExpectedFolder = new File(parentFolder, source.getName());
            FileUtils.copyDirectory(source, newExpectedFolder);
        }
    }

    public static void main(String[] args) throws IOException {
        boolean dryRun = true;

        copyMissingTestFilesForDialectsFromStandard(dryRun);
    }
}
