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

public class DMNNamespacePrefixMapper extends AbstractNamespacePrefixMapper {
    private final DMNVersion version;

    public DMNNamespacePrefixMapper() {
        this(null, null);
    }

    public DMNNamespacePrefixMapper(String namespace, String prefix) {
        this(namespace, prefix, DMNVersion.LATEST);
    }

    public DMNNamespacePrefixMapper(String namespace, String prefix, DMNVersion version) {
        super(namespace, prefix, version.getNamespaceToPrefixMap());
        this.version = version;
    }

    public DMNVersion getVersion() {
        return this.version;
    }
}
