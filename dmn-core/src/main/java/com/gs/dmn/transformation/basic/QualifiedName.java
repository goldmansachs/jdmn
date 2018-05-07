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
package com.gs.dmn.transformation.basic;

import javax.xml.namespace.QName;

import static com.gs.dmn.serialization.DMNConstants.*;

public class QualifiedName {
    public static QualifiedName toQualifiedName(QName qName) {
        if (qName == null) {
            return null;
        }

        String namespaceURI = qName.getNamespaceURI();
        if (FEEL_11_NS.equals(namespaceURI)) {
            return new QualifiedName(FEEL_11_PREFIX, qName.getLocalPart());
        } else if (FEEL_12_NS.equals(namespaceURI)) {
            return new QualifiedName(FEEL_12_PREFIX, qName.getLocalPart());
        } else {
            String prefix = qName.getPrefix();
            return new QualifiedName(prefix, qName.getLocalPart());
        }
    }

    public static QualifiedName toQualifiedName(String qName) {
        return qName == null ? null : new QualifiedName(qName);
    }

    private final String namespace;
    private final String localPart;

    public QualifiedName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException(String.format("Invalid qualified name '%s'", name));
        }
        int index = name.indexOf('.');
        if (index != -1) {
            this.namespace = name.substring(0, index);
            this.localPart = name.substring(index + 1);
        } else {
            this.namespace = null;
            this.localPart = name;
        }
    }

    public QualifiedName(String namespace, String localPart) {
        this.namespace = namespace;
        this.localPart = localPart;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getLocalPart() {
        return localPart;
    }

    @Override
    public String toString() {
        return String.format("QualifiedName(%s, %s)", namespace, localPart);
    }
}
