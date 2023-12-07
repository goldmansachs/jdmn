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
package com.gs.dmn.feel.analysis.semantics;

import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.feel.analysis.syntax.ast.visitor.CloneVisitor;
import com.gs.dmn.feel.analysis.syntax.ast.Element;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Name;
import com.gs.dmn.feel.analysis.syntax.ast.expression.textual.ForExpression;

public class UpdatePartialVisitor<T, C> extends CloneVisitor<T, C> {
    private final T partialType;

    public UpdatePartialVisitor(T partialType, ErrorHandler errorHandler) {
        super(errorHandler);
        this.partialType = partialType;
    }

    //
    // Primary expressions
    //
    @Override
    public Element<T> visit(Name<T> element, C context) {
        if (element == null) {
            return null;
        }

        String name = element.getName();
        if (ForExpression.PARTIAL_PARAMETER_NAME.equals(name)) {
            element.setType(this.partialType);
        }
        return element;
    }
}
