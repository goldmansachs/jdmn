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
package com.gs.dmn.feel.analysis.semantics.environment;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.context.environment.Environment;
import com.gs.dmn.context.environment.EnvironmentFactory;
import com.gs.dmn.context.environment.VariableDeclaration;
import com.gs.dmn.feel.analysis.semantics.type.StringType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class EnvironmentTest {
    private final EnvironmentFactory environmentFactory = StandardEnvironmentFactory.instance();

    @Test
    public void testLookupVariableDeclaration() {
        Environment environment = this.environmentFactory.emptyEnvironment();
        String name = "x";
        environment.addDeclaration(new VariableDeclaration(name, StringType.STRING));

        assertEquals("x", environment.lookupLocalVariableDeclaration(name).getName());
    }

    @Test
    public void testLookupFunctionDeclaration() {
        DMNContext context = this.environmentFactory.getBuiltInContext();

        String functionName = "date";
        List<Declaration> declarations = context.lookupFunctionDeclaration(functionName);
        assertEquals(4, declarations.size());
        assertEquals(functionName, declarations.get(0).getName());
    }
}