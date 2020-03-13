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

import static com.gs.dmn.serialization.DMNConstants.XSI_NS;
import static com.gs.dmn.serialization.DMNConstants.XSI_PREFIX;

public class TCKNamespacePrefixMapper extends AbstractNamespacePrefixMapper {
    private final TCKVersion version;

    public TCKNamespacePrefixMapper() {
        this(null, null);
    }

    public TCKNamespacePrefixMapper(String namespace, String prefix) {
        this(namespace, prefix, TCKVersion.LATEST);
    }

    public TCKNamespacePrefixMapper(String namespace, String prefix, TCKVersion version) {
        super(namespace, prefix, version.getNamespaceMap());
        this.namespaceMap.put(XSI_NS, XSI_PREFIX);
        this.version = version;
    }

    public TCKVersion getVersion() {
        return version;
    }
}
