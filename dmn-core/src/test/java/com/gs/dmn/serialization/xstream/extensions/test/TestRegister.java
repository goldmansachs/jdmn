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
package com.gs.dmn.serialization.xstream.extensions.test;

import com.gs.dmn.serialization.xstream.DMNExtensionRegister;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.security.WildcardTypePermission;

import javax.xml.namespace.QName;

public class TestRegister implements DMNExtensionRegister {
    private static final String[] ALLOW_LISTED_PACKAGES = new String[] {
        "com.gs.dmn.serialization.xstream.extensions.**"
    };
    public static final String EXTENSION_NAMESPACE_URI = "http://dmnextension.org";
    public static final String EXTENSION_PREFIX = "ext";

    @Override
    public void registerExtensionConverters(XStream xStream) {
        xStream.processAnnotations(AllowedAnswer.class);
        xStream.processAnnotations(Decision.class);
        xStream.processAnnotations(Definitions.class);
        xStream.processAnnotations(ElementCollection.class);
        xStream.processAnnotations(ItemDefinition.class);
        xStream.processAnnotations(LiteralExpression.class);
        xStream.processAnnotations(PerformanceIndicator.class);
        xStream.processAnnotations(TextAnnotation.class);

        xStream.addPermission(new WildcardTypePermission(ALLOW_LISTED_PACKAGES));
    }

    @Override
    public void beforeMarshal(Object o, QNameMap qNameMap) {
        qNameMap.registerMapping(new QName(EXTENSION_NAMESPACE_URI, "eAllowedAnswer", EXTENSION_PREFIX), "eAllowedAnswer");
        qNameMap.registerMapping(new QName(EXTENSION_NAMESPACE_URI, "eDecision", EXTENSION_PREFIX), "eDecision");
        qNameMap.registerMapping(new QName(EXTENSION_NAMESPACE_URI, "eDefinitions", EXTENSION_PREFIX), "eDefinitions");
        qNameMap.registerMapping(new QName(EXTENSION_NAMESPACE_URI, "eElementCollection", EXTENSION_PREFIX), "eElementCollection");
        qNameMap.registerMapping(new QName(EXTENSION_NAMESPACE_URI, "eItemDefinition", EXTENSION_PREFIX), "eItemDefinition");
        qNameMap.registerMapping(new QName(EXTENSION_NAMESPACE_URI, "eLiteralExpression", EXTENSION_PREFIX), "eLiteralExpression");
        qNameMap.registerMapping(new QName(EXTENSION_NAMESPACE_URI, "ePerformanceIndicator", EXTENSION_PREFIX), "ePerformanceIndicator");
        qNameMap.registerMapping(new QName(EXTENSION_NAMESPACE_URI, "eTextAnnotation", EXTENSION_PREFIX), "eTextAnnotation");
    }
}
