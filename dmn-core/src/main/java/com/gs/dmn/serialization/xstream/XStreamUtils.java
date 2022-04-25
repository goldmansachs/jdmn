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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.security.WildcardTypePermission;

import java.util.function.BiFunction;

public class XStreamUtils {
    public static XStream createNonTrustingXStream(HierarchicalStreamDriver hierarchicalStreamDriver,
                                                   ClassLoader classLoader,
                                                   BiFunction<HierarchicalStreamDriver, ClassLoaderReference, XStream> builder) {
        return internalCreateNonTrustingXStream(builder.apply(hierarchicalStreamDriver, new ClassLoaderReference(classLoader)));
    }

    private static XStream internalCreateNonTrustingXStream(XStream xstream) {
        xstream.addPermission(new WildcardTypePermission(ALLOW_LISTED_PACKAGES));
        return xstream;
    }

    private static final String[] ALLOW_LISTED_PACKAGES = new String[] {
        "com.gs.dmn.ast.**"
    };
}
