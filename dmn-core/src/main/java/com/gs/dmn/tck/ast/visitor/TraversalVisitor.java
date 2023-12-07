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

public class TraversalVisitor<C> extends AbstractVisitor<C, TCKBaseElement> {
    public TraversalVisitor(ErrorHandler errorHandler) {
        super(errorHandler);
    }

    @Override
    public TCKBaseElement visit(TestCases element, C context) {
        if (element != null) {
            Labels labels = element.getLabels();
            if (labels != null) {
                labels.accept(this, context);
            }
            element.getTestCase().forEach( e -> e.accept(this, context) );
        }
        return element;
    }

    @Override
    public TCKBaseElement visit(TestCase element, C context) {
        if (element != null) {
            visitExtensionElements(element.getExtensionElements(), context);
            element.getInputNode().forEach( e -> e.accept(this, context) );
            element.getResultNode().forEach(e -> e.accept(this, context) );
        }
        return element;
    }
    @Override
    public TCKBaseElement visit(Labels element, C context) {
        return element;
    }

    @Override
    public TCKBaseElement visit(InputNode element, C context) {
        if (element != null) {
            visitExtensionElements(element.getExtensionElements(), context);
            visitValue(element.getValue(), context);
            visitComponents(element.getComponent(), context);
            visitList(element.getList(), context);
        }
        return element;
    }

    @Override
    public TCKBaseElement visit(ResultNode element, C context) {
        if (element != null) {
            visitValueType(element.getExpected(), context);
            visitValueType(element.getComputed(), context);
        }
        return element;
    }

    @Override
    public TCKBaseElement visit(ValueType element, C context) {
        if (element != null) {
            visitExtensionElements(element.getExtensionElements(), context);
            visitValue(element.getValue(), context);
            visitComponents(element.getComponent(), context);
            visitList(element.getList(), context);
        }
        return element;
    }

    @Override
    public TCKBaseElement visit(List element, C context) {
        if (element != null) {
            element.getItem().forEach( e -> e.accept(this, context));
        }
        return element;
    }

    @Override
    public TCKBaseElement visit(Component element, C context) {
        if (element != null) {
            visitExtensionElements(element.getExtensionElements(), context);
            visitValue(element.getValue(), context);
            visitComponents(element.getComponent(), context);
            visitList(element.getList(), context);
        }
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

    private void visitExtensionElements(ExtensionElements extensionElements, C context) {
        if (extensionElements != null) {
            extensionElements.accept(this, context);
        }
    }

    private void visitValue(AnySimpleType value, C context) {
        if (value != null) {
            value.accept(this, context);
        }
    }

    private void visitList(List list, C context) {
        if (list != null) {
            list.accept(this, context);
        }
    }

    private void visitComponents(java.util.List<Component> component, C context) {
        if (component != null) {
            component.forEach(e -> e.accept(this, context));
        }
    }

    private void visitValueType(ValueType value, C context) {
        if (value != null) {
            value.accept(this, context);
        }
    }
}
