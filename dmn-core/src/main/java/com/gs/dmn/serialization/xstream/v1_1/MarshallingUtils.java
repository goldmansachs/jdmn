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
package com.gs.dmn.serialization.xstream.v1_1;

import com.gs.dmn.ast.*;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MarshallingUtils {
    private final static Pattern QNAME_PAT = Pattern.compile("(\\{([^\\}]*)\\})?(([^:]*):)?(.*)");

    public static QName parseQNameString(String qns) {
        if (qns != null) {
            Matcher m = QNAME_PAT.matcher(qns);
            if (m.matches()) {
                String namespaceURI = m.group(2);
                String prefix = m.group(4);
                String localPart = m.group(5);
                if (prefix != null) {
                    return new QName(namespaceURI, localPart, prefix);
                } else {
                    return new QName(namespaceURI, localPart);
                }
            } else {
                return new QName(qns);
            }
        } else {
            return null;
        }
    }

    public static String formatQName(QName qname) {
        if (!XMLConstants.DEFAULT_NS_PREFIX.equals(qname.getPrefix())) {
            return qname.getPrefix() + ":" + qname.getLocalPart();
        } else {
            return qname.toString();
        }
    }

    public static String formatQName(String str) {
        QName qname = parseQNameString(str);
        if (!XMLConstants.DEFAULT_NS_PREFIX.equals(qname.getPrefix())) {
            return qname.getPrefix() + ":" + qname.getLocalPart();
        } else {
            return qname.toString();
        }
    }

    public static String defineExpressionNodeName(TExpression e) {
        String nodeName = "expression";
        if (e instanceof TContext) {
            nodeName = "context";
        } else if (e instanceof TDecisionTable) {
            nodeName = "decisionTable";
        } else if (e instanceof TFunctionDefinition) {
            nodeName = "functionDefinition";
        } else if (e instanceof TInvocation) {
            nodeName = "invocation";
        } else if (e instanceof TLiteralExpression) {
            nodeName = "literalExpression";
        } else if (e instanceof TRelation) {
            nodeName = "relation";
        } else if (e instanceof TList) {
            nodeName = "list";
        }
        return nodeName;
    }

    private MarshallingUtils() {
    }
}
