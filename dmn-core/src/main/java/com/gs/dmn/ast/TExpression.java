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
package com.gs.dmn.ast;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "@kind")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "literalExpression", value = TLiteralExpression.class),
        @JsonSubTypes.Type(name = "invocation", value = TInvocation.class),
        @JsonSubTypes.Type(name = "decisionTable", value = TDecisionTable.class),
        @JsonSubTypes.Type(name = "context", value = TContext.class),
        @JsonSubTypes.Type(name = "functionDefinition", value = TFunctionDefinition.class),
        @JsonSubTypes.Type(name = "relation", value = TRelation.class),
        @JsonSubTypes.Type(name = "list", value = TList.class),
        @JsonSubTypes.Type(name = "unaryTests", value = TUnaryTests.class)
})
public abstract class TExpression extends TDMNElement {
    private String typeRef;

    public String getTypeRef() {
        return typeRef;
    }

    public void setTypeRef(String value) {
        this.typeRef = value;
    }
}
