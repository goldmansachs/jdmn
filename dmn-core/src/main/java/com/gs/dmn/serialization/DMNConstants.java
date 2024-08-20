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

import java.io.File;

public class DMNConstants {
    public static boolean isDMNFile(File file, String dmnFileExtension) {
        return file != null && file.isFile() && file.getName().endsWith(dmnFileExtension);
    }

    public static boolean isTCKFile(File file, String tckFileExtension) {
        return file != null && file.isFile() && file.getName().endsWith(tckFileExtension);
    }

    public static final String DMN_FILE_EXTENSION = ".dmn";

    public static final String TCK_FILE_EXTENSION = ".xml";

    // XSD
    public static final String XSD_NS = "http://www.w3.org/2001/XMLSchema";
    public static final String XSD_PREFIX = "xsd";

    // XSI
    public static final String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
    public static final String XSI_PREFIX = "xsi";

    private DMNConstants() {
    }
}
