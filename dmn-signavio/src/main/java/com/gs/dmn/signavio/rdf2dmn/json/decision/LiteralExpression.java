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
package com.gs.dmn.signavio.rdf2dmn.json.decision;

import com.gs.dmn.signavio.rdf2dmn.json.Context;
import com.gs.dmn.signavio.rdf2dmn.json.ItemDefinition;
import com.gs.dmn.signavio.rdf2dmn.json.Visitor;
import com.gs.dmn.signavio.rdf2dmn.json.expression.Expression;

public class LiteralExpression extends DecisionExpression {
    private String version;
    private String expressionLanguage;
    private ItemDefinition itemDefinition;
    private Expression text;

    public String getVersion() {
        return version;
    }

    public String getExpressionLanguage() {
        return expressionLanguage;
    }

    public ItemDefinition getItemDefinition() {
        return itemDefinition;
    }

    public Expression getText() {
        return text;
    }

    @Override
    public String accept(Visitor visitor, Context params) {
        return visitor.visit(this, params);
    }
}
