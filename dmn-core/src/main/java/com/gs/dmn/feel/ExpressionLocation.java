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
package com.gs.dmn.feel;

import com.gs.dmn.ast.TDMNElement;
import com.gs.dmn.ast.TDefinitions;

public class ExpressionLocation<T> extends ModelLocation {
    private final T expression;

    public ExpressionLocation(TDefinitions model, TDMNElement element, T expression) {
        super(model, element);
        this.expression = expression;
    }

    public T getExpression() {
        return expression;
    }
}
