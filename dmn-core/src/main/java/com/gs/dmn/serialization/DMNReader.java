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
package com.gs.dmn.serialization;

import com.gs.dmn.ast.DMNBaseElement;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.xstream.DMNExtensionRegister;
import com.gs.dmn.serialization.xstream.DMNMarshallerFactory;
import com.gs.dmn.serialization.xstream.XStreamMarshaller;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DMNReader extends DMNSerializer {
    public static boolean isDMNFile(File file) {
        return file != null && file.isFile() && file.getName().endsWith(DMNConstants.DMN_FILE_EXTENSION);
    }

    private final boolean validateSchema;
    private final DMNMarshaller dmnMarshaller;
    private final DMNDialectTransformer dmnTransformer = new DMNDialectTransformer(logger);

    public DMNReader(BuildLogger logger, boolean validateSchema) {
        this(logger, validateSchema, new ArrayList<>());
    }

    public DMNReader(BuildLogger logger, boolean validateSchema, List<DMNExtensionRegister> registers) {
        super(logger);
        this.validateSchema = validateSchema;
        this.dmnMarshaller = DMNMarshallerFactory.newMarshallerWithExtensions(registers);
    }

    public List<Pair<TDefinitions, PrefixNamespaceMappings>> readModels(List<File> files) {
        List<Pair<TDefinitions, PrefixNamespaceMappings>> pairs = new ArrayList<>();
        if (files == null) {
            throw new DMNRuntimeException("Missing DMN files");
        } else {
            for (File file: files) {
                if (isDMNFile(file)) {
                    Pair<TDefinitions, PrefixNamespaceMappings> pair = read(file);
                    pairs.add(pair);
                } else {
                    logger.warn(String.format("Skipping file '%s", file == null ? null : file.getAbsoluteFile()));
                }
            }
            return pairs;
        }
    }

    public List<Pair<TDefinitions, PrefixNamespaceMappings>> readModels(File file) {
        List<Pair<TDefinitions, PrefixNamespaceMappings>> pairs = new ArrayList<>();
        if (file == null) {
            throw new DMNRuntimeException("Missing DMN file");
        } else if (isDMNFile(file)) {
            Pair<TDefinitions, PrefixNamespaceMappings> pair = read(file);
            pairs.add(pair);
            return pairs;
        } else if (file.isDirectory()) {
            for (File child: file.listFiles()) {
                if (isDMNFile(child)) {
                    Pair<TDefinitions, PrefixNamespaceMappings> pair = read(child);
                    pairs.add(pair);
                }
            }
            return pairs;
        } else {
            throw new DMNRuntimeException(String.format("Invalid DMN file %s", file.getAbsoluteFile()));
        }
    }

    public com.gs.dmn.ast.TDefinitions readAST(File input) {
        try {
            logger.info(String.format("Reading DMN '%s' ...", input.getAbsolutePath()));

            com.gs.dmn.ast.TDefinitions result = this.dmnMarshaller.unmarshal(input);

            logger.info("DMN read.");
            return result;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.getAbsolutePath()), e);
        }
    }

    public Pair<TDefinitions, PrefixNamespaceMappings> read(File input) {
        try {
            logger.info(String.format("Reading DMN '%s' ...", input.getAbsolutePath()));

            Pair<TDefinitions, PrefixNamespaceMappings> result = transform(readObject(input));

            logger.info("DMN read.");
            return result;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.getAbsolutePath()), e);
        }
    }

    public Pair<TDefinitions, PrefixNamespaceMappings> read(InputStream input) {
        try {
            logger.info(String.format("Reading DMN '%s' ...", input.toString()));

            Pair<TDefinitions, PrefixNamespaceMappings> result = transform(readObject(input));

            logger.info("DMN read.");
            return result;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.toString()), e);
        }
    }

    public Pair<TDefinitions, PrefixNamespaceMappings> read(URL input) {
        try {
            logger.info(String.format("Reading DMN '%s' ...", input.toString()));

            Pair<TDefinitions, PrefixNamespaceMappings> result = transform(readObject(input));

            logger.info("DMN read.");
            return result;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.toString()), e);
        }
    }

    public Pair<TDefinitions, PrefixNamespaceMappings> read(Reader input) {
        try {
            logger.info(String.format("Reading DMN '%s' ...", input.toString()));

            Pair<TDefinitions, PrefixNamespaceMappings> result = transform(readObject(input));

            logger.info("DMN read.");
            return result;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.toString()), e);
        }
    }

    private Pair<TDefinitions, PrefixNamespaceMappings> transform(Object value) {
        if (value == null) {
            return null;
        }

        DMNVersion dmnVersion = XStreamMarshaller.inferDMNVersion((DMNBaseElement) value);
        if (dmnVersion == DMNVersion.DMN_11) {
            return this.dmnTransformer.transform11To13Definitions((TDefinitions) value);
        } else if (dmnVersion == DMNVersion.DMN_12) {
            return this.dmnTransformer.transform12To13Definitions((TDefinitions) value);
        } else if (dmnVersion == DMNVersion.DMN_13) {
            return new Pair<>((TDefinitions) value, new PrefixNamespaceMappings());
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported", value.getClass()));
        }
    }

    private Object readObject(File input) {
        return this.dmnMarshaller.unmarshal(input);
    }

    private Object readObject(URL input) {
        return this.dmnMarshaller.unmarshal(input);
    }

    private Object readObject(InputStream input) {
        return this.dmnMarshaller.unmarshal(input);
    }

    private Object readObject(Reader input) {
        return this.dmnMarshaller.unmarshal(input);
    }
}
