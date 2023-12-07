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
package com.gs.dmn.tck.ast.visitor;

import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.tck.ast.*;

public class NopVisitor<C> extends AbstractVisitor<C, TCKBaseElement> {
    public NopVisitor(ErrorHandler errorHandler) {
        super(errorHandler);
    }

    @Override
    public TCKBaseElement visit(TestCases element, C context) {
        return element;
    }

    @Override
    public TCKBaseElement visit(TestCase element, C context) {
        return element;
    }

    @Override
    public TCKBaseElement visit(Labels element, C context) {
        return element;
    }

    @Override
    public TCKBaseElement visit(InputNode element, C context) {
        return element;
    }

    @Override
    public TCKBaseElement visit(ResultNode element, C context) {
        return element;
    }

    @Override
    public TCKBaseElement visit(ValueType element, C context) {
        return element;
    }

    @Override
    public TCKBaseElement visit(List element, C context) {
        return element;
    }

    @Override
    public TCKBaseElement visit(Component element, C context) {
        return element;
    }

    @Override
    public TCKBaseElement visit(ExtensionElements element, C context) {
        return element;
    }

    @Override
    public TCKBaseElement visit(AnySimpleType element, C context) {
        return element;
    }
}
