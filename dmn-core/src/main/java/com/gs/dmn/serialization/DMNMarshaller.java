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

import java.io.Reader;
import java.io.Writer;

public interface DMNMarshaller {
    TDefinitions unmarshal(String input, boolean validateSchema);

    TDefinitions unmarshal(Reader input, boolean validateSchema);

    String marshal(TDefinitions o);

    void marshal(TDefinitions o, Writer output);
}