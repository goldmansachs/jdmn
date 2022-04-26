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
package com.gs.dmn.signavio.serialization.xstream;

import com.gs.dmn.serialization.xstream.DMNExtensionRegister;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.security.WildcardTypePermission;
import org.apache.commons.lang3.StringUtils;

import javax.xml.namespace.QName;

import static com.gs.dmn.signavio.extension.SignavioExtension.SIG_EXT_NAMESPACE;

public class SignavioExtensionRegister implements DMNExtensionRegister {
    private static final String[] ALLOW_LISTED_PACKAGES = new String[] {
        "com.gs.dmn.signavio.serialization.xstream.**"
    };

    private final String namespaceURI;

    public SignavioExtensionRegister(String namespaceURI) {
        if (StringUtils.isBlank(namespaceURI)) {
            this.namespaceURI = SIG_EXT_NAMESPACE;
        } else {
            this.namespaceURI = namespaceURI;
        }
    }

    @Override
    public void registerExtensionConverters(XStream xStream) {
        xStream.processAnnotations(MultiInstanceDecisionLogic.class);
        xStream.processAnnotations(ReferencedService.class);
        xStream.addPermission(new WildcardTypePermission(ALLOW_LISTED_PACKAGES));
    }

    @Override
    public void beforeMarshal(Object o, QNameMap qNameMap) {
        // Register MultiInstanceDecisionLogic extension
        String midClassName = MultiInstanceDecisionLogic.class.getSimpleName();
        qNameMap.registerMapping(new QName(this.namespaceURI, midClassName, "sigExt"), midClassName);
        qNameMap.registerMapping(new QName(this.namespaceURI, "iterationExpression", "sigExt"), "iterationExpression");
        qNameMap.registerMapping(new QName(this.namespaceURI, "iteratorShapeId", "sigExt"), "iteratorShapeId");
        qNameMap.registerMapping(new QName(this.namespaceURI, "aggregationFunction", "sigExt"), "aggregationFunction");
        qNameMap.registerMapping(new QName(this.namespaceURI, "topLevelDecisionId", "sigExt"), "topLevelDecisionId");

        // Register referencedService extension
        qNameMap.registerMapping(new QName(this.namespaceURI, "referencedService", "sigExt"), "referencedService");
        qNameMap.registerMapping(new QName(this.namespaceURI, "href", "sigExt"), "href");
    }
}
