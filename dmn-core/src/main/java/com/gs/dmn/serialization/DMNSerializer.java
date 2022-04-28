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

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.xstream.DMNExtensionRegister;
import com.gs.dmn.serialization.xstream.DMNMarshallerFactory;
import com.gs.dmn.serialization.xstream.XStreamMarshaller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DMNSerializer {
    public static boolean isDMNFile(File file) {
        return file != null && file.isFile() && file.getName().endsWith(DMNConstants.DMN_FILE_EXTENSION);
    }

    protected final BuildLogger logger;
    protected final DMNMarshaller dmnMarshaller;

    private final boolean validateSchema;
    private final DMNDialectTransformer dmnTransformer;

    public DMNSerializer(BuildLogger logger) {
        this(logger, new ArrayList<>(), false);
    }

    public DMNSerializer(BuildLogger logger, boolean validateSchema) {
        this(logger, new ArrayList<>(), validateSchema);
    }

    public DMNSerializer(BuildLogger logger, List<DMNExtensionRegister> registers, boolean validateSchema) {
        this.logger = logger;
        this.dmnMarshaller = DMNMarshallerFactory.newMarshallerWithExtensions(registers);
        this.validateSchema = validateSchema;
        this.dmnTransformer = new DMNDialectTransformer(logger);
    }

    public List<TDefinitions> readModels(List<File> files) {
        List<TDefinitions> definitionsList = new ArrayList<>();
        if (files == null) {
            throw new DMNRuntimeException("Missing DMN files");
        } else {
            for (File file: files) {
                if (isDMNFile(file)) {
                    TDefinitions definitions = readModel(file);
                    definitionsList.add(definitions);
                } else {
                    this.logger.warn(String.format("Skipping file '%s", file == null ? null: file.getAbsoluteFile()));
                }
            }
            return definitionsList;
        }
    }

    public List<TDefinitions> readModels(File file) {
        List<TDefinitions> definitionsList = new ArrayList<>();
        if (file == null) {
            throw new DMNRuntimeException("Missing DMN file");
        } else if (isDMNFile(file)) {
            TDefinitions definitions = readModel(file);
            definitionsList.add(definitions);
            return definitionsList;
        } else if (file.isDirectory()) {
            for (File child: file.listFiles()) {
                if (isDMNFile(child)) {
                    TDefinitions definitions = readModel(child);
                    definitionsList.add(definitions);
                }
            }
            return definitionsList;
        } else {
            throw new DMNRuntimeException(String.format("Invalid DMN file %s", file.getAbsoluteFile()));
        }
    }

    public TDefinitions readModel(File input) {
        try {
            this.logger.info(String.format("Reading DMN '%s' ...", input.getAbsolutePath()));

            TDefinitions definitions = transform(unmarshall(input));

            this.logger.info("DMN read.");
            return definitions;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.getAbsolutePath()), e);
        }
    }

    public TDefinitions readModel(InputStream input) {
        try {
            this.logger.info(String.format("Reading DMN '%s' ...", input.toString()));

            TDefinitions definitions = transform(unmarshall(input));

            this.logger.info("DMN read.");
            return definitions;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.toString()), e);
        }
    }

    public TDefinitions readModel(URL input) {
        try {
            this.logger.info(String.format("Reading DMN '%s' ...", input.toString()));

            TDefinitions definitions = transform(unmarshall(input));

            this.logger.info("DMN read.");
            return definitions;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.toString()), e);
        }
    }

    public TDefinitions readModel(Reader input) {
        try {
            this.logger.info(String.format("Reading DMN '%s' ...", input.toString()));

            TDefinitions result = transform(unmarshall(input));

            this.logger.info("DMN read.");
            return result;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.toString()), e);
        }
    }

    public void writeModel(TDefinitions definitions, File output) {
        try (FileOutputStream fos = new FileOutputStream(output)) {
            this.dmnMarshaller.marshal(definitions, fos);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot write DMN to '%s'", output.getPath()), e);
        }
    }

    private TDefinitions transform(TDefinitions definitions) {
        if (definitions == null) {
            return null;
        }

        DMNVersion dmnVersion = XStreamMarshaller.inferDMNVersion(definitions);
        if (dmnVersion == DMNVersion.DMN_11) {
            return this.dmnTransformer.transform11To13Definitions(definitions);
        } else if (dmnVersion == DMNVersion.DMN_12) {
            return this.dmnTransformer.transform12To13Definitions(definitions);
        } else if (dmnVersion == DMNVersion.DMN_13) {
            return definitions;
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported", definitions.getClass()));
        }
    }

    private TDefinitions unmarshall(File input) {
        return this.dmnMarshaller.unmarshal(input);
    }

    private TDefinitions unmarshall(URL input) {
        return this.dmnMarshaller.unmarshal(input);
    }

    private TDefinitions unmarshall(InputStream input) {
        return this.dmnMarshaller.unmarshal(input);
    }

    private TDefinitions unmarshall(Reader input) {
        return this.dmnMarshaller.unmarshal(input);
    }
}
