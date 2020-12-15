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
package com.gs.dmn.transformation.basic;

import com.gs.dmn.serialization.DMNVersion;
import org.omg.spec.dmn._20191111.model.TDefinitions;
import org.omg.spec.dmn._20191111.model.TImport;

public class QualifiedName {
    public static QualifiedName toQualifiedName(TDefinitions model, String qName) {
        if (qName == null || qName.isEmpty()) {
            return null;
        }
        if (qName.startsWith(DMNVersion.LATEST.getFeelPrefix() + ".")) {
            String prefix = DMNVersion.LATEST.getFeelPrefix();
            String localPart = qName.substring(qName.indexOf('.') + 1);
            return new QualifiedName(prefix, localPart);
        }
        if (model == null) {
            return new QualifiedName(null, qName);
        } else {
            for (TImport import_: model.getImport()) {
                String importName = import_.getName();
                if (qName.startsWith(importName + '.')) {
                    String localPart = qName.substring(qName.indexOf('.') + 1);
                    return new QualifiedName(importName, localPart);
                }
            }
            return new QualifiedName(null, qName);
        }
    }

    private final String namespace;
    private final String localPart;

    private QualifiedName(String namespace, String localPart) {
        this.namespace = namespace;
        this.localPart = localPart;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getLocalPart() {
        return this.localPart;
    }

    @Override
    public String toString() {
        return String.format("QualifiedName(%s, %s)", this.namespace, this.localPart);
    }
}
