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
package com.gs.dmn.serialization.xstream.extensions.kie;

import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.xstream.DMNExtensionRegister;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.security.WildcardTypePermission;

import javax.xml.namespace.QName;

public class DecisionServicesRegister implements DMNExtensionRegister {
    private static final String[] ALLOW_LISTED_PACKAGES = new String[]{
            "com.gs.dmn.serialization.xstream.extensions.**"
    };

    @Override
    public void registerExtensionConverters(XStream xStream) {
        xStream.alias("decisionServices", DecisionServices.class);
        xStream.registerConverter(new DecisionServicesConverter(xStream, DMNVersion.DMN_11));
        xStream.addPermission(new WildcardTypePermission(ALLOW_LISTED_PACKAGES));
    }

    @Override
    public void beforeMarshal(Object o, QNameMap qNameMap) {
        qNameMap.registerMapping(new QName("http://www.drools.org/kie/dmn/1.1", "decisionServices", "drools"), "decisionServices");
    }
}
