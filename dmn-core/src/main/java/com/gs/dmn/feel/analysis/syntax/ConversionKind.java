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
package com.gs.dmn.feel.analysis.syntax;

public enum ConversionKind {
    NONE(false, false),
    ELEMENT_TO_SINGLETON_LIST(true, false),
    SINGLETON_LIST_TO_ELEMENT(true, false),
    DATE_TO_UTC_MIDNIGHT(true, false),
    CONFORMS_TO(false, true),

    // Convert equivalent CompositeDataTypes (e.g. ItemDefinition and ContentType)
    TO_ITEM_DEFINITION(false, false),
    TO_LIST_OF_ITEM_DEFINITION(false, false);

    public static final ConversionKind[] FUNCTION_RESOLUTION_CANDIDATES = new ConversionKind[] {
            NONE,
            ELEMENT_TO_SINGLETON_LIST,
            SINGLETON_LIST_TO_ELEMENT,
            DATE_TO_UTC_MIDNIGHT
    };

    private final boolean implicit;
    private final boolean error;

    ConversionKind(boolean implicit, boolean error) {
        this.implicit = implicit;
        this.error = error;
    }

    public boolean isImplicit() {
        return implicit;
    }

    public boolean isError() {
        return error;
    }
}
