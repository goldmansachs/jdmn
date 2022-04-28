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

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.xstream.DMNExtensionRegister;
import com.gs.dmn.serialization.xstream.DMNMarshallerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DMNWriter extends DMNSerializer {
    private final DMNMarshaller dmnMarshaller;

    public DMNWriter(BuildLogger logger) {
        this(logger, new ArrayList<>());
    }

    public DMNWriter(BuildLogger logger, List<DMNExtensionRegister> registers) {
        super(logger);
        this.dmnMarshaller = DMNMarshallerFactory.newMarshallerWithExtensions(registers);
    }

    public void writeModel(Object definitions, File output) {
        try (FileOutputStream fos = new FileOutputStream(output)) {
            this.dmnMarshaller.marshal(definitions, fos);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot write DMN to '%s'", output.getPath()), e);
        }
    }
}
