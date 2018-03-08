/**
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
package com.gs.dmn.feel.analysis.semantics.environment;

import com.gs.dmn.runtime.DMNRuntimeException;

public class Conversion {
    private final ConversionKind kind;
    private final String elementType;

    public Conversion(ConversionKind kind, String elementType) {
        this.kind = kind;
        this.elementType = elementType;
    }

    public ConversionKind getKind() {
        return kind;
    }

    public String conversionFunction(Conversion conversion) {
        if (conversion.kind == ConversionKind.NONE) {
            return null;
        } else if (conversion.kind == ConversionKind.ELEMENT_TO_LIST) {
            return "asList";
        } else if (conversion.kind == ConversionKind.LIST_TO_ELEMENT) {
            return String.format("this.<%s>asElement", elementType);
        } else {
            throw new DMNRuntimeException(String.format("Conversion '%s' is not supported yet", conversion));
        }
    }
}
