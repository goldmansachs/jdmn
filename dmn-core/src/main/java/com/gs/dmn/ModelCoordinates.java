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
package com.gs.dmn;

import com.gs.dmn.ast.TDMNElement;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TExpression;
import com.gs.dmn.ast.TLiteralExpression;

public class ModelCoordinates {
    private final TDefinitions model;
    private final TDMNElement element;
    private final Object expression;

    public ModelCoordinates(TDefinitions model, TDMNElement element) {
        this(model, element, null);
    }

    public ModelCoordinates(TDefinitions model, TDMNElement element, Object expression) {
        this.model = model;
        this.element = element;
        this.expression = expression;
    }

    public TDefinitions getModel() {
        return model;
    }

    public TDMNElement getElement() {
        return element;
    }

    public String expressionDescription() {
        if (expression == null) {
            return null;
        } else if (expression instanceof TExpression) {
            return expression instanceof TLiteralExpression ? ((TLiteralExpression) expression).getText() : expression.getClass().getSimpleName();
        } else {
            return expression.toString();
        }
    }
}
