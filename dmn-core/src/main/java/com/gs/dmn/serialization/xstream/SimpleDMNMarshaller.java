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
package com.gs.dmn.serialization.xstream;

import com.gs.dmn.ast.TDefinitions;

import java.io.*;
import java.net.URL;

public interface SimpleDMNMarshaller {
    TDefinitions unmarshal(String input);

    TDefinitions unmarshal(File input);

    TDefinitions unmarshal(URL input);

    TDefinitions unmarshal(InputStream input);

    TDefinitions unmarshal(Reader input);

    String marshal(Object o);

    void marshal(Object o, File output);

    void marshal(Object o, OutputStream output);

    void marshal(Object o, Writer output);
}
