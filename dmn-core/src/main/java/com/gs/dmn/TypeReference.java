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

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TImport;
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.feel.analysis.semantics.type.FEELType;
import com.gs.dmn.serialization.DMNVersion;

import javax.xml.namespace.QName;
import java.util.Objects;

public class TypeReference {

    public static final char TYPE_REFERENCE_SEPARATOR = '.';

    public static String toName(QName qName) {
        return qName == null ? null : qName.getLocalPart();
    }

    public static TypeReference toTypeReference(TDefinitions model, QName qName) {
        if (qName == null) {
            return null;
        } else {
            return toTypeReference(model, qName.getLocalPart());
        }
    }

    public static TypeReference toTypeReference(TDefinitions model, String qName) {
        if (qName == null || qName.isEmpty()) {
            return null;
        }

        // Check user defined types
        if (model != null) {
            // Check the imports
            for (TImport import_: model.getImport()) {
                String importName = import_.getName();
                if (hasPrefix(qName, importName)) {
                    String localPart = qName.substring(qName.indexOf(TYPE_REFERENCE_SEPARATOR) + 1);
                    return new TypeReference(importName, localPart);
                }
            }
            // Check the types defined in the model
            for (TItemDefinition itemDefinition: model.getItemDefinition()) {
                if (Objects.equals(itemDefinition.getName(), qName)) {
                    return new TypeReference(null, qName);
                }
            }
        }

        // Check FEEL types with and without prefix
        if (hasPrefix(qName, DMNVersion.LATEST.getFeelPrefix())) {
            String prefix = DMNVersion.LATEST.getFeelPrefix();
            String localPart = qName.substring(qName.indexOf(TYPE_REFERENCE_SEPARATOR) + 1);
            return new TypeReference(prefix, localPart);
        } else {
            if (FEELType.FEEL_TYPE_NAMES.contains(qName)) {
                return new TypeReference(DMNVersion.LATEST.getFeelPrefix(), qName);
            } else {
                return new TypeReference(null, qName);
            }
        }

    }

    public static TypeReference toTypeReference(String prefix, String name) {
        return new TypeReference(prefix, name);
    }

    private static boolean hasPrefix(String qName, String importName) {
        return qName.startsWith(importName + TYPE_REFERENCE_SEPARATOR);
    }

    private final String prefix;
    private final String name;

    private TypeReference(String prefix, String name) {
        this.prefix = prefix;
        this.name = name;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("QualifiedName(%s, %s)", this.prefix, this.name);
    }
}
