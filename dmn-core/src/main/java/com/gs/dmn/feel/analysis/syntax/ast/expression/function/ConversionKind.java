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
package com.gs.dmn.feel.analysis.syntax.ast.expression.function;

public enum ConversionKind {
    NONE(false),
    ELEMENT_TO_SINGLETON_LIST(true),
    SINGLETON_LIST_TO_ELEMENT(true),
    DATE_TO_UTC_MIDNIGHT(true),
    CONFORMS_TO(true);

    public static final ConversionKind[] FUNCTION_RESOLUTION_CANDIDATES = new ConversionKind[] {
            NONE,
            ELEMENT_TO_SINGLETON_LIST,
            SINGLETON_LIST_TO_ELEMENT,
            DATE_TO_UTC_MIDNIGHT,
            CONFORMS_TO
    };

    private final boolean implicit;

    ConversionKind(boolean implicit) {
        this.implicit = implicit;
    }

    public boolean isImplicit() {
        return implicit;
    }
}
