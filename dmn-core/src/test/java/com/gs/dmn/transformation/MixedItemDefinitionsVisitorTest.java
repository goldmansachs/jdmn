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
package com.gs.dmn.transformation;

import com.gs.dmn.ast.TDMNElement;
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.error.NopErrorHandler;
import com.gs.dmn.log.NopBuildLogger;
import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;

import static org.junit.jupiter.api.Assertions.*;

public class MixedItemDefinitionsVisitorTest {
    private final MixedItemDefinitionsVisitor<?> visitor = new MixedItemDefinitionsVisitor<>(new NopBuildLogger(), new NopErrorHandler());

    @Test
    public void testVisitTypeRef() {
        visitor.visitTypeRef(null, null);
        assertEquals(new QName("correct"), visitor.visitTypeRef(new QName("correct"), null));
        assertEquals(new QName(" XXX"), visitor.visitTypeRef(new QName("sig. XXX"), null));
        assertEquals(new QName(" XXX"), visitor.visitTypeRef(new QName(" sig. XXX"), null));
    }

    @Test
    public void testVisitItemDefinition() {
        TItemDefinition itemDefinition = makeItemDefinition("mixed");
        assertNull(itemDefinition.getTypeRef());
        itemDefinition.accept(visitor, null);
        assertEquals(new QName("feel.Any"), itemDefinition.getTypeRef());
    }

    @Test
    public void testIsMixed() {
        assertFalse(visitor.isMixed(null));
        assertFalse(visitor.isMixed(new TItemDefinition()));
        assertTrue(visitor.isMixed(makeItemDefinition("mixed")));
        assertFalse(visitor.isMixed(makeItemDefinition("other")));
    }

    @Test
    public void testIsEmpty() {
        assertFalse(visitor.isEmpty(null));
        assertTrue(visitor.isEmpty(new TItemDefinition()));
    }

    private TItemDefinition makeItemDefinition(String any) {
        TItemDefinition element = new TItemDefinition();
        element.setExtensionElements(new TDMNElement.ExtensionElements());
        element.getExtensionElements().getAny().add(any);
        return element;
    }
}